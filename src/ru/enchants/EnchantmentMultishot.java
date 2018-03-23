package ru.enchants;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import ru.util.InventoryHelper;
import ru.util.MathUtils;

public class EnchantmentMultishot extends DoncEnchantment {

	public EnchantmentMultishot(int id) {
		super(id);
		this.setOnLevelUp("увеличивается количество выпускаемых стрел на 1.");
		this.setDesc("позволяет выпускать сразу несколько стрел.");
	}

	public Material getItemToShow() {
		return Material.BOW;
	}

	public String getName() {
		return "Multishot";
	}

	public int getMaxLevel() {
		return 4;
	}

	public int getStartLevel() {
		return 1;
	}

	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.BOW;
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
		if(exp < 10) return MathUtils.chance(exp + 15) ? 1 : 0;
		if(exp < 20) return MathUtils.chance(exp + 10) ? (MathUtils.chance(exp + 40) ? 2 : 1) : 0;
		if(exp < 30) return MathUtils.chance(exp + 5) ? (MathUtils.chance(exp + 30) ? 3 : 2) : 0;
		if(exp >= 30) return MathUtils.chance(30) ? (MathUtils.chance(20) ? 4 : (MathUtils.chance(70) ? 3 : 2)) : 0;
		return 0;
	}

	@EventHandler
	public void multishot(ProjectileLaunchEvent e) {
		Projectile proj = e.getEntity();
		if(proj instanceof Arrow && proj.getShooter() instanceof Player) {
			Player p = (Player) proj.getShooter();
			Arrow arrow = (Arrow) proj;
			ItemStack bow = p.getInventory().getItemInMainHand();
			if(bow == null || bow.getType() != Material.BOW) {
				bow = p.getInventory().getItemInOffHand();
				if(bow == null || bow.getType() != Material.BOW) {
					return;
				}
			}
			if(EnchantmentManager.hasCustomEnchant(bow, this)) {
				boolean inf = bow.containsEnchantment(Enchantment.ARROW_INFINITE);
				for(int i = 0; i < EnchantmentManager.getCustomEnchant(bow, this).getLevel(); i++) {
					if(!inf) {
						ItemStack item = InventoryHelper.getFirstStack(p.getInventory(), Material.ARROW);
						if(item != null && item.getAmount() > 1) {
							item.setAmount(item.getAmount() - 1);
						} else {
							break;
						}
					}
					Arrow newArrow = p.getWorld().spawnArrow(arrow.getLocation(), arrow.getVelocity(), 3F, 9);
					newArrow.setFireTicks(arrow.getFireTicks());
					newArrow.setShooter(p);
					newArrow.setBounce(arrow.doesBounce());
					newArrow.setCritical(arrow.isCritical());
					newArrow.setKnockbackStrength(arrow.getKnockbackStrength());
					newArrow.setPickupStatus(arrow.getPickupStatus());
				}
			}
		}
	}

}
