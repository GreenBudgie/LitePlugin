package ru.bosses;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import ru.items.CustomItems;
import ru.util.InventoryHelper;
import ru.util.MathUtils;

public class BossZombie extends Boss {

	public BossZombie() {
		super(EntityType.ZOMBIE, ChatColor.DARK_GREEN + "Бешоны Зомби");
		this.setSpawnChance(4);
		this.setHP(150);
		this.setXP(70, 100);
		this.setDropHandItemChance(20);
		this.addDrop(new BossDrop(new ItemStack(Material.ROTTEN_FLESH), 5, 7));
		this.addDrop(new BossDrop(new ItemStack(Material.GOLDEN_CARROT), 50, true));
		this.addDrop(new BossDrop(new ItemStack(Material.POTATO_ITEM), 1, 2, 50, true));
		this.addDrop(new BossDrop(new ItemStack(Material.IRON_INGOT), 1, 2, 50, true));
		this.addDrop(new BossDrop(CustomItems.ancientsFlesh.getItemStack(), 30, false));
		this.addEffects(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
		this.addEffects(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
	}

	public void onSpawn(LivingEntity boss, CreatureSpawnEvent e) {
		ItemStack item = new ItemStack(Material.IRON_SWORD);
		item.addEnchantment(Enchantment.DAMAGE_ALL, MathUtils.randomRange(1, 2));
		item.addEnchantment(Enchantment.KNOCKBACK, MathUtils.randomRange(1, 2));
		boss.getEquipment().setItemInMainHand(item);
	}

	public void onDamage(Player p, LivingEntity boss, double damage, EntityDamageByEntityEvent e) {
		if(boss.getHealth() <= 75 && MathUtils.chance(50)) {
			Zombie ent = (Zombie) boss.getWorld().spawnEntity(boss.getLocation(), EntityType.ZOMBIE);
			ItemStack item = new ItemStack(Material.STONE_SWORD);
			item.addEnchantment(Enchantment.FIRE_ASPECT, 1);
			ent.getEquipment().setItemInMainHand(item);
			ent.getEquipment().setItemInMainHandDropChance(0.1F);
			ent.setCustomName(ChatColor.GREEN + "Мелки Апасны Зомби");
			ent.setCustomNameVisible(true);
			ent.setVelocity(new Vector(MathUtils.randomRangeDouble(-0.2, 0.2), MathUtils.randomRangeDouble(0, 0.2), MathUtils.randomRangeDouble(-0.2, 0.2)));
			ent.setTarget(p);
			ent.setBaby(true);
			InventoryHelper.addNormalPotionEffect(ent, new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
		}
	}

	public void onAttack(LivingEntity boss, Player p, double damage, EntityDamageByEntityEvent e) {
		InventoryHelper.addNormalPotionEffect(p, new PotionEffect((PotionEffectType) MathUtils.choose(PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION),
				MathUtils.randomRange(160, 220), 0));
	}
	
	public String getDescription() {
		return "Очень быстрый и прыткий зомби. Может спавнить себе в подмогу маленьких зомби с опасными мечами в руке. При атаке может вызвать тошноту и слепоту.";
	}

}
