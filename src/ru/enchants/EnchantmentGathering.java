package ru.enchants;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftExperienceOrb;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftItem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ru.main.HardcorePlugin;
import ru.util.InventoryHelper;
import ru.util.MathUtils;

public class EnchantmentGathering extends DoncEnchantment {

	public EnchantmentGathering(int id) {
		super(id);
		this.setDesc("позволяет моментально добавлять ресурсы в инвентарь при их добыче, при том с любой дистанции. Удобно при добыче алмазов над лавой.");
		this.setItemToShow(Material.DIAMOND_PICKAXE);
	}

	public String getName() {
		return "Gathering";
	}

	public int getMaxLevel() {
		return 1;
	}

	public int getStartLevel() {
		return 1;
	}

	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.TOOL;
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
		return exp > 15 && MathUtils.chance(exp + 7) ? 1 : 0;
	}

	@EventHandler
	public void proceedGathering(BlockBreakEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		if(item != null && item.getType() != Material.AIR && item.getType() != Material.ENCHANTED_BOOK
				&& EnchantmentManager.hasCustomEnchant(item, EnchantmentManager.GATHERING)) {
			HardcorePlugin.invokeLater(new Runnable() {

				public void run() {
					Collection<Entity> ent = e.getBlock().getWorld().getNearbyEntities(e.getBlock().getLocation(), 1, 1, 1);
					for(Entity entity : ent) {
						if(entity instanceof CraftItem) {
							if(InventoryHelper.haveSpace(p.getInventory(), ((CraftItem) entity).getItemStack().getType())) {
								entity.setVelocity(new Vector(0, 0, 0));
								((CraftItem) entity).setPickupDelay(0);
								entity.teleport(p);
							}
						}
						if(entity instanceof CraftExperienceOrb) {
							entity.setVelocity(new Vector(0, 0, 0));
							entity.teleport(p);
						}
					}
				}

			});
		}
	}

}
