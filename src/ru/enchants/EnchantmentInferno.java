package ru.enchants;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ru.util.MathUtils;

public class EnchantmentInferno extends DoncEnchantment {

	public EnchantmentInferno(int id) {
		super(id);
		this.setDesc("позволяет быстрее перемещаться в лаве и не получать урон при ходьбе по магме.");
		this.setOnLevelUp("увеличивается скорость перемещения в лаве.");
		this.setItemToShow(Material.DIAMOND_BOOTS);
	}

	public String getName() {
		return "Inferno Traveller";
	}

	public int getMaxLevel() {
		return 3;
	}

	public int getStartLevel() {
		return 1;
	}

	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.ARMOR_FEET;
	}

	public boolean isTreasure() {
		return false;
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
		if(exp > 4 && MathUtils.chance(15 + exp)) {
			int level = 1;
			if(exp >= 8 && MathUtils.chance(50)) {
				level = 2;
			}
			if(exp >= 25) {
				if(MathUtils.chance(25 + exp)) {
					level = 3;
				} else {
					level = 2;
				}
			}
			return level;
		}
		return 0;
	}

	@EventHandler
	public void proceedTravelDamage(PlayerItemDamageEvent e) {
		ItemStack item = e.getItem();
		Player p = e.getPlayer();
		if(EnchantmentManager.hasCustomEnchant(item, EnchantmentManager.STEP)) {
			DamageCause c = p.getLastDamageCause().getCause();
			if(c == DamageCause.LAVA || c == DamageCause.FIRE || c == DamageCause.FIRE_TICK || c == DamageCause.HOT_FLOOR) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void proceedMagmaDamage(EntityDamageByBlockEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if(EnchantmentManager.hasCustomEnchant(p.getInventory().getBoots(), EnchantmentManager.STEP) && e.getDamager().getType() == Material.MAGMA) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void proceedInfernoTraveller(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		ItemStack s = p.getInventory().getBoots();
		if(s != null && EnchantmentManager.hasCustomEnchant(s, EnchantmentManager.STEP)) {
			int level = EnchantmentManager.getCustomEnchant(s, EnchantmentManager.STEP).getLevel();
			Location loc = p.getLocation();
			loc.setY(loc.getY() + 0.5);
			Block b = loc.getBlock();
			boolean lava = b != null && (b.getType() == Material.LAVA || b.getType() == Material.STATIONARY_LAVA);
			if(lava && p.isSprinting()) {
				p.setVelocity(new Vector((double) (-Math.sin(p.getLocation().getYaw() * 0.017453292F) * 0.5F), -0.05D,
						(double) (Math.cos(p.getLocation().getYaw() * 0.017453292F) * 0.5F)).multiply(level * 0.165));
			}
		}
	}

}
