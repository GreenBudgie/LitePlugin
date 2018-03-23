package ru.bosses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.google.common.collect.Lists;

import ru.util.MathUtils;

public abstract class Boss {

	private EntityType entity;
	private String name;
	private double spawnChance = 3;
	private List<BossDrop> drops = new ArrayList<BossDrop>();
	private int xpMin = 0;
	private int xpMax = 0;
	private int hp;
	private List<PotionEffect> effects = new ArrayList<PotionEffect>();
	private Map<Attribute, Double> attributes = new HashMap<Attribute, Double>();
	private boolean reflectArrows = true;
	private int dropHandItemChance = 0;
	
	public abstract String getDescription();

	public boolean canDropHandItem() {
		return dropHandItemChance > 0;
	}

	public int getDropHandItemChance() {
		return dropHandItemChance;
	}

	public void setDropHandItemChance(int dropHandItemChance) {
		this.dropHandItemChance = dropHandItemChance;
	}

	public Boss(EntityType e, String name) {
		entity = e;
		this.name = name;
	}

	public void setSpawnChance(double chance) {
		spawnChance = MathUtils.percentDouble(chance);
	}

	public double getSpawnChance() {
		return spawnChance;
	}

	public EntityType getEntity() {
		return entity;
	}

	public void addDrops(BossDrop... drops) {
		this.drops.addAll(Lists.<BossDrop>newArrayList(drops));
	}

	public void addDrop(BossDrop drop) {
		this.drops.add(drop);
	}

	public List<BossDrop> getDrops() {
		return drops;
	}

	public List<ItemStack> getDropsAsItems(int looting) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(BossDrop drop : drops) {
			ItemStack item = drop.getItemStack(looting);
			if(item != null) items.add(item);
		}
		return items;
	}

	public void setXP(int min, int max) {
		if(min < 0 || max < 0) throw new IllegalArgumentException();
		xpMin = min;
		xpMax = max;
	}

	public int getXP() {
		return MathUtils.randomRange(xpMin, xpMax);
	}
	
	public int getMinXp() {
		return xpMin;
	}
	
	public int getMaxXp() {
		return xpMax;
	}

	public void addEffects(PotionEffect... effects) {
		this.effects.addAll(Lists.<PotionEffect>newArrayList(effects));
	}

	public List<PotionEffect> getEffects() {
		return this.effects;
	}

	public int getHP() {
		return hp;
	}

	public void setHP(int hp) {
		this.hp = hp;
	}

	public void addAttribute(Attribute a, double value) {
		attributes.put(a, value);
	}

	public Map<Attribute, Double> getAttributes() {
		return attributes;
	}

	public String getName() {
		return name;
	}

	public boolean isReflectingArrows() {
		return reflectArrows;
	}

	public void setArrowReflect(boolean reflect) {
		reflectArrows = reflect;
	}

	public boolean isEquals(Entity e) {
		if(e == null || !(e instanceof LivingEntity) || e.getCustomName() == null) return false;
		return this.getEntity() == e.getType() && e.getCustomName().equals(this.getName());
	}

	/**
	 * Runs when boss dies
	 * 
	 * @param boss
	 *            Boss who died
	 * @param p
	 *            Player who killed the boss
	 * @param killed
	 *            Determines whether the boss was killed by the player
	 */
	public void onDeath(LivingEntity boss, Player p, boolean killed, EntityDeathEvent e) {
	}

	/**
	 * Runs when boss targets a player
	 * 
	 * @param p
	 *            The player that boss target
	 * @param boss
	 *            The boss
	 */
	public void onSeePlayer(Player p, LivingEntity boss, EntityTargetLivingEntityEvent e) {

	}

	public void onSpawn(LivingEntity boss, CreatureSpawnEvent e) {
	}

	/**
	 * Runs when boss attacks the player
	 * 
	 * @param boss
	 *            Boss that was attacking
	 * @param p
	 *            Player who was attacked
	 * @param damage
	 *            The amount of damage that boss deals to player
	 * @return The amount of damage to apply to player
	 */
	public void onAttack(LivingEntity boss, Player p, double damage, EntityDamageByEntityEvent e) {
	}

	/**
	 * Runs when boss takes damage by any source
	 * 
	 * @param boss
	 *            The boss
	 * @param cause
	 *            The source of damage
	 * @param damage
	 *            The amount of damage that boss took
	 * @param entity
	 *            Determines whether the boss was attacked by entity
	 * @return The amount of damage to apply to boss
	 */
	public void onAnyDamage(LivingEntity boss, DamageCause cause, double damage, boolean entity, EntityDamageEvent e) {
	}

	/**
	 * Runs when player attacks the boss
	 * 
	 * @param p
	 *            Player who was attacking
	 * @param boss
	 *            Boss that was attacked
	 * @param damage
	 *            The amount of damage that player deals to boss
	 * @return The amount of damage to apply to boss
	 */
	public void onDamage(Player p, LivingEntity boss, double damage, EntityDamageByEntityEvent e) {
	}

}
