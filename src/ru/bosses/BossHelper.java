package ru.bosses;

import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import ru.main.HardcorePlugin;
import ru.util.EntityHelper;
import ru.util.MathUtils;
import ru.util.WorldHelper;

public final class BossHelper {

	public static void setMaxHP(LivingEntity e, double hp) {
		if(hp <= 0) throw new IllegalArgumentException();
		e.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hp);
	}

	public static void setHp(LivingEntity e, double hp) {
		if(hp <= 0) throw new IllegalArgumentException();
		if(e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() < hp) {
			setMaxHP(e, hp);
		}
		e.setHealth(hp);
	}

	public static void addForceEffect(LivingEntity e, PotionEffect... effects) {
		for(PotionEffect ef : effects) {
			e.addPotionEffect(ef, true);
		}
	}

	public static void addEffect(LivingEntity e, PotionEffect... effects) {
		for(PotionEffect ef : effects) {
			EntityHelper.addNormalPotionEffect(e, ef);
		}
	}
	
	public static void spawnParticlesAround(Entity boss, ParticleEffect effect, int amount) {
		spawnParticlesAround(boss, effect, Color.WHITE, amount);
	}

	public static void spawnParticlesAround(Entity boss, ParticleEffect effect, Color color, int amount) {
		WorldHelper.spawnParticlesAround(boss, effect, color, amount);
	}
	
	public static void teleport(LivingEntity me, int radius) {
		Location tloc = me.getLocation().clone().add(MathUtils.randomRangeDouble(-radius, radius), 0, MathUtils.randomRangeDouble(-radius, radius));
		int y = tloc.getBlockY();
		int h = WorldHelper.getClosestFreeBlockYAbove(tloc, 1);
		if(h == -1 || (h + 1) - y > radius) h = WorldHelper.getClosestFreeBlockYUnder(tloc, 1);
		if(h == -1 || y - (h + 1) > radius) return;
		tloc.setY(h + 1);
		LineEffect ef = new LineEffect(HardcorePlugin.instance.em);
		ef.setLocation(me.getLocation());
		ef.setTargetLocation(tloc);
		ef.particle = ParticleEffect.REDSTONE;
		ef.color = Color.PURPLE;
		ef.particleCount = 30;
		ef.start();
		me.getWorld().playSound(me.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 2F);
		me.teleport(tloc);
	}

}
