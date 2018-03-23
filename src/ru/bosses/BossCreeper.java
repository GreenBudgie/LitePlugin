package ru.bosses;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.util.ParticleEffect;
import ru.items.CustomItems;
import ru.util.InventoryHelper;
import ru.util.MathUtils;

public class BossCreeper extends Boss implements Listener {

	public BossCreeper() {
		super(EntityType.CREEPER, ChatColor.GREEN + "ЕБАНЫ КРИПЕР");
		this.setSpawnChance(4);
		this.setHP(60);
		this.setXP(40, 60);
		this.addDrop(new BossDrop(new ItemStack(Material.SULPHUR), 5, 7));
		this.addDrop(new BossDrop(new ItemStack(Material.TNT)));
		this.addDrop(new BossDrop(new ItemStack(Material.BLAZE_POWDER), 1, 3));
		this.addDrop(new BossDrop(CustomItems.empoweredCharge.getItemStack(), 30, false));
		this.addAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE, 1);
	}

	public void onDamage(Player p, LivingEntity boss, double damage, EntityDamageByEntityEvent e) {
		if(boss.getHealth() <= 35 && MathUtils.chance(30)) {
			InventoryHelper.addNormalPotionEffect(p, new PotionEffect(PotionEffectType.SLOW, MathUtils.randomRange(80, 140), MathUtils.randomRange(0, 1)));
			if(MathUtils.chance(50)) {
				InventoryHelper.addNormalPotionEffect(p, new PotionEffect(PotionEffectType.BLINDNESS, MathUtils.randomRange(80, 140), 0));
			}
			BossHelper.spawnParticlesAround(boss, ParticleEffect.FLAME, 50);
		}
	}

	@EventHandler
	public void explode(ExplosionPrimeEvent e) {
		if(this.isEquals(e.getEntity())) {
			e.setRadius(e.getRadius() * 2);
			e.setFire(true);
		}
	}
	
	public String getDescription() {
		return "Его нельзя оттолкнуть. Может наложить слепоту или медлительность при атаке. Если взорвется - то тебе конец.";
	}

}
