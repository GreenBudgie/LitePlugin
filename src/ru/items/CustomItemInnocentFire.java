package ru.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItemInnocentFire extends CustomItem {

	public String getName() {
		return ChatColor.GOLD + "Innocent Fire";
	}

	public Material getMaterial() {
		return Material.BLAZE_POWDER;
	}
	
	public boolean isStackable() {
		return true;
	}
	
	public ItemStack getItemStack() {
		ItemStack item = super.getItemStack();
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.FIRE_ASPECT, 3, true);
		item.setItemMeta(meta);
		return item;
	}
	
	public boolean isGlowing() {
		return false;
	}

}
