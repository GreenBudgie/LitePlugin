package ru.enchants;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import ru.util.MathUtils;
import ru.util.PlayerJumpEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EnchantmentEvasion extends DoncEnchantment {

	public EnchantmentEvasion(int id) {
		super(id);
		this.setDesc("Позволяет делать дополнительный прыжок в воздухе.");
		this.setOnLevelUp("Увеличивается количество дополнительных прыжков.");
	}

	public Material getItemToShow() {
		return Material.GOLD_BOOTS;
	}

	public String getName() {
		return "Evasion";
	}

	public int getMaxLevel() {
		return 2;
	}

	public int getStartLevel() {
		return 1;
	}

	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.ARMOR_FEET;
	}

	public boolean isTreasure() {
		return true;
	}

	public boolean isCursed() {
		return false;
	}

	public boolean conflictsWith(Enchantment other) {
		return false;
	}

	public boolean canEnchantItem(ItemStack item) {
		return true;
	}

	public int tryEnchant(int exp) {
		return MathUtils.chance(40) ? (MathUtils.chance(30) ? 2 : 1) : 0;
	}

	private static Map<Player, Integer> jumps = new HashMap<Player, Integer>();
	private static Map<Player, Integer> sneaks = new HashMap<Player, Integer>();

	@EventHandler
	public void addJumps(PlayerJumpEvent e) {
		jumps.put(e.getPlayer(), 30);
	}

	@EventHandler
	public void sneak(PlayerToggleSneakEvent e) {
		if(!e.isSneaking()) return;
		Player p = e.getPlayer();
		if(!((CraftPlayer) p).getHandle().isInWater() && jumps.getOrDefault(p, 0) > 0) {
			ItemStack boots = p.getInventory().getBoots();
			if(boots != null && EnchantmentManager.hasCustomEnchant(boots, EnchantmentManager.EVASION)) {
				int jump = EnchantmentManager.getCustomEnchant(boots, EnchantmentManager.EVASION).getLevel();
				int evasions = sneaks.getOrDefault(p, 0);
				if(evasions < jump) {
					Location l = p.getLocation();
					p.setVelocity(
							new Vector((double) (-Math.sin(Math.toRadians(l.getYaw())) * 0.6F), 0.3D, (double) (Math.cos(Math.toRadians(l.getYaw())) * 0.6F)));
					final double t = 0.5;
					for(int i = 0; i < 5; i++) {
						l.getWorld().spawnParticle(Particle.CLOUD,
								l.clone().add(MathUtils.randomRangeDouble(-t, t), MathUtils.randomRangeDouble(-t, t), MathUtils.randomRangeDouble(-t, t)), 0,
								Math.sin(Math.toRadians(l.getYaw())), -0.2, -Math.cos(Math.toRadians(l.getYaw())), 0.3, null);
					}
					sneaks.put(p, evasions + 1);
					jumps.put(p, jumps.getOrDefault(p, 0) + 10);
					p.setFallDistance(p.getFallDistance() / 2F);
					p.setSprinting(true);
				} else {
					jumps.remove(p);
					sneaks.remove(p);
				}
			}
		}
	}

	public static void update() {
		Iterator<Player> iter = jumps.keySet().iterator();
		while(iter.hasNext()) {
			Player pl = iter.next();
			jumps.put(pl, jumps.get(pl) - 1);
			if(jumps.get(pl) <= 0 || pl.isOnGround()) {
				jumps.remove(pl);
				sneaks.remove(pl);
			}
		}
	}

}
