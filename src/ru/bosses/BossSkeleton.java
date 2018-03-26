package ru.bosses;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import ru.items.CustomItems;
import ru.util.MathUtils;

public class BossSkeleton extends Boss implements Listener {

	public BossSkeleton() {
		super(EntityType.SKELETON, ChatColor.DARK_BLUE + "Лютый Скелет");
		this.setSpawnChance(4);
		this.setHP(120);
		this.setXP(50, 80);
		this.setDropHandItemChance(10);
		this.addDrop(new BossDrop(new ItemStack(Material.BONE), 2, 4));
		this.addDrop(new BossDrop(new ItemStack(Material.ARROW), 4, 10));
		this.addDrop(new BossDrop(CustomItems.strongBone.getItemStack(), 20, false));
		this.addEffects(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
		this.addEffects(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 4));
	}

	public void onSpawn(LivingEntity boss, CreatureSpawnEvent e) {
		ItemStack item = new ItemStack(Material.BOW);
		item.addEnchantment((Enchantment) MathUtils.choose(Enchantment.ARROW_DAMAGE), MathUtils.randomRange(1, 2));
		if(MathUtils.chance(50)) item.addEnchantment((Enchantment) MathUtils.choose(Enchantment.ARROW_FIRE), 1);
		if(MathUtils.chance(25)) item.addEnchantment((Enchantment) MathUtils.choose(Enchantment.ARROW_KNOCKBACK), MathUtils.randomRange(1, 2));
		boss.getEquipment().setItemInMainHand(item);
	}

	@EventHandler
	public void shoot(ProjectileLaunchEvent e) {
		Projectile proj = e.getEntity();
		ProjectileSource src = proj.getShooter();
		if(src != null && proj instanceof Arrow && src instanceof Skeleton && isEquals((Skeleton) src)) {
			Arrow arr = (Arrow) proj;
			Arrow arrow1 = (Arrow) arr.getWorld().spawnEntity(arr.getLocation(), EntityType.ARROW);
			arrow1.setVelocity(arr.getVelocity());
			arrow1.setFireTicks(arr.getFireTicks());
			arrow1.setCritical(true);
			arrow1.setKnockbackStrength(arr.getKnockbackStrength());
			arrow1.setPickupStatus(arr.getPickupStatus());
			arrow1.setShooter(arr.getShooter());
			for(int i = 0; i < 6; i++) {
				TippedArrow arrow = arr.getWorld().spawnArrow(arr.getLocation(), arr.getVelocity(), (float) MathUtils.randomRangeDouble(2.5, 3), 6, TippedArrow.class);
				arrow.setVelocity(arr.getVelocity());
				arrow.setCritical(true);
				arrow.setFireTicks(arr.getFireTicks());
				arrow.setKnockbackStrength(arr.getKnockbackStrength());
				arrow.setPickupStatus(arr.getPickupStatus());
				arrow.setShooter(arr.getShooter());
				if(MathUtils.chance(10)) {
					arrow.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, MathUtils.randomRange(200, 300), 0), true);
				}
				if(MathUtils.chance(10)) {
					arrow.addCustomEffect(new PotionEffect(PotionEffectType.POISON, MathUtils.randomRange(200, 300), 0), true);
				}
				if(MathUtils.chance(10)) {
					arrow.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, MathUtils.randomRange(200, 350), 0), true);
				}
				if(MathUtils.chance(10)) {
					arrow.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, MathUtils.randomRange(300, 400), 0), true);
				}
				if(MathUtils.chance(10)) {
					arrow.addCustomEffect(new PotionEffect(PotionEffectType.HUNGER, MathUtils.randomRange(300, 800), 0), true);
				}
				if(MathUtils.chance(10)) {
					arrow.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, MathUtils.randomRange(200, 300), 0), true);
				}
				if(MathUtils.chance(10)) {
					arrow.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, MathUtils.randomRange(300, 500), 0), true);
				}
			}
			arr.remove();
		}
	}
	
	public String getDescription() {
		return "Стреляет большим количеством стрел с рандомными негативными эффектами.";
	}
	
}
