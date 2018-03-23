package ru.enchants;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ru.util.InventoryHelper;
import ru.util.MathUtils;

public class EnchantmentForce extends DoncEnchantment {

	public EnchantmentForce(int id) {
		super(id);
		this.setDesc("позволяет бегать еще быстрее с помощью спринта. Немного тратится прочность при комбинации с эффектом скорости.");
		this.setOnLevelUp("увеличивается скорость спринта.");
		this.setItemToShow(Material.DIAMOND_LEGGINGS);
	}

	public String getName() {
		return "Sprinting Force";
	}

	public int getMaxLevel() {
		return 3;
	}

	public int getStartLevel() {
		return 1;
	}

	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.ARMOR_LEGS;
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
		if(exp >= 12 && MathUtils.chance(20 + exp / 2)) {
			return exp >= 30 ? (MathUtils.chance(40) ? 2 : 1) : 1;
		}
		return 0;
	}

	@EventHandler
	public boolean proceedForce(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(p.isSprinting()) {
			ItemStack s = p.getInventory().getLeggings();
			if(s != null) {
				if(EnchantmentManager.hasCustomEnchant(s, EnchantmentManager.FORCE)) {
					int level = EnchantmentManager.getCustomEnchant(s, EnchantmentManager.FORCE).getLevel();
					if(level == 1) {
						p.setWalkSpeed(0.25F);
					} else {
						if(level == 2) {
							p.setWalkSpeed(0.28F);
						} else {
							p.setWalkSpeed(0.31F);
						}
						PotionEffect speed = p.getPotionEffect(PotionEffectType.SPEED);
						if(speed != null && p.isOnGround()) {
							double chance = (speed.getAmplifier() + 1) * 0.001 + (level - 1) * 0.001;
							if(Math.random() < chance) {
								InventoryHelper.damageItem(s, 1);
							}
						}
					}
					return true;
				}
			}
		}
		p.setWalkSpeed(0.2F);
		return false;
	}
	
}
