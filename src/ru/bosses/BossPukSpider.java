package ru.bosses;

import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.*;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import ru.items.CustomItems;
import ru.util.EntityHelper;
import ru.util.MathUtils;
import ru.util.WorldHelper;

public class BossPukSpider extends Boss {

	public BossPukSpider() {
		super(EntityType.SPIDER, ChatColor.RED + "Пукпаук");
		this.setHP(120);
		this.setSpawnChance(5);
		this.setXP(80, 120);
		this.addDrop(new BossDrop(new ItemStack(Material.STRING), 5, 9));
		this.addDrop(new BossDrop(new ItemStack(Material.SPIDER_EYE), 2, 5));
		this.addDrop(new BossDrop(new ItemStack(Material.GOLD_INGOT), 1, 4));
		this.addDrop(new BossDrop(new ItemStack(Material.WEB)));
		this.addDrop(new BossDrop(CustomItems.infusedString.getItemStack(), 25, false));
		this.addEffects(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0, false, true),
				new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
	}

	public void onDeath(LivingEntity boss, Player p, boolean killed, EntityDeathEvent e) {
		boss.getWorld().playSound(boss.getLocation(), Sound.ENTITY_ENDERMEN_DEATH, 1, 2F);
	}

	public void onAnyDamage(LivingEntity boss, DamageCause cause, double damage, boolean entity, EntityDamageEvent e) {
		if(cause != DamageCause.PROJECTILE) {
			boolean flag = cause == DamageCause.SUFFOCATION;
			if(boss.getHealth() <= 70 || flag) {
				if(MathUtils.chance(flag ? 100 : (entity ? 25 : 0))) {
					WorldHelper.chorusTeleport(boss, boss.getHealth() <= 35 ? 6 : 4);
				}
			}
		}
	}

	public void onDamage(Player p, LivingEntity ent, double damage, EntityDamageByEntityEvent e) {
		if(ent.getHealth() <= 70) {
			EntityHelper.addNormalPotionEffect(ent, new PotionEffect(PotionEffectType.SPEED, 200, 1, false, true));
			if(MathUtils.chance(15)) {
				Location loc = p.getLocation();
				loc.getBlock().setType(Material.WEB);
			}
		}
		if(ent.getHealth() <= 35) {
			EntityHelper.addNormalPotionEffect(ent, new PotionEffect(PotionEffectType.REGENERATION, 80, 4, false, true));
			BossHelper.spawnParticlesAround(ent, ParticleEffect.REDSTONE, Color.RED, 20);
			for(int i = 0; i < 3; i++) {
				if(MathUtils.chance(18)) {
					CaveSpider spider = (CaveSpider) ent.getWorld().spawnEntity(ent.getLocation(), EntityType.CAVE_SPIDER);
					spider.setCustomName(ChatColor.GOLD + "Микро пукпаук");
					spider.setVelocity(
							new Vector(MathUtils.randomRangeDouble(-0.2, 0.2), MathUtils.randomRangeDouble(0, 0.2), MathUtils.randomRangeDouble(-0.2, 0.2)));
					spider.setHealth(8);
					spider.setTarget(p);
					ent.getWorld().playSound(ent.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 1, (float) MathUtils.randomRangeDouble(1.5, 2));
				}
			}
		}
	}

	public void onAttack(LivingEntity boss, Player p, double damage, EntityDamageByEntityEvent e) {
		EntityHelper.addNormalPotionEffect(p, new PotionEffect(PotionEffectType.POISON, 100, 2));
	}

	public String getDescription() {
		return "Быстрый, очень сильный и максимально опасный паук. При атаке может вызывать маленьких паучков, телепортироваться и ставить паутину. Убить его очень сложно, потому что при недостатке хп он начинает регенерацию.";
	}

}
