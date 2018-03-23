package ru.bosses;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.slikey.effectlib.util.ParticleEffect;
import ru.items.CustomItems;
import ru.util.MathUtils;

public class BossWither extends Boss {

	public BossWither() {
		super(EntityType.WITHER_SKELETON, ChatColor.DARK_GRAY + "Высохши Скелет");
		this.setSpawnChance(7);
		this.setHP(120);
		this.setXP(70, 100);
		this.setDropHandItemChance(10);
		this.addDrop(new BossDrop(new ItemStack(Material.SULPHUR), 4, 6));
		this.addDrop(new BossDrop(new ItemStack(Material.COAL_BLOCK)));
		this.addDrop(new BossDrop(new ItemStack(Material.BONE), 5, 8));
		ItemStack potion = new ItemStack(Material.SPLASH_POTION);
		this.addDrop(new BossDrop(CustomItems.millenniumCoal.getItemStack(), 40, false));
		this.addDrop(new BossDrop(new ItemStack(Material.SKULL_ITEM, 1, (short) 1), 40, false));
		this.addEffects(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
	}

	public void onSpawn(LivingEntity boss, CreatureSpawnEvent e) {
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		item.addEnchantment((Enchantment) MathUtils.choose(Enchantment.DAMAGE_ALL, Enchantment.FIRE_ASPECT), MathUtils.randomRange(1, 2));
		boss.getEquipment().setItemInMainHand(item);
	}

	public void onDamage(Player p, LivingEntity boss, double damage, EntityDamageByEntityEvent e) {
		if(boss.getHealth() <= 75 && MathUtils.chance(35)) {
			Skeleton ent = (Skeleton) boss.getWorld().spawnEntity(boss.getLocation(), EntityType.SKELETON);
			ItemStack item = new ItemStack(Material.BOW);
			item.addEnchantment(Enchantment.ARROW_KNOCKBACK, MathUtils.randomRange(1, 2));
			ent.getEquipment().setItemInMainHand(item);
			ent.getEquipment().setItemInMainHandDropChance(0);
			ent.setCustomName(ChatColor.GRAY + "Подвысохши Скелет");
			ent.setCustomNameVisible(true);
			ent.setVelocity(new Vector(MathUtils.randomRangeDouble(-0.2, 0.2), MathUtils.randomRangeDouble(0, 0.2), MathUtils.randomRangeDouble(-0.2, 0.2)));
			ent.setTarget(p);
			boss.getWorld().playSound(boss.getLocation(), Sound.ITEM_FIRECHARGE_USE, 1, 0.7F);
			BossHelper.spawnParticlesAround(boss, ParticleEffect.REDSTONE, Color.RED, 20);
			BossHelper.teleport(boss, 3);
		}
	}

	public String getDescription() {
		return "При атаке вызывает в подмогу скелетов-лучников. Может поджечь.";
	}
	
}
