package ru.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItemStrongBone extends CustomItem implements Listener {

	public String getName() {
		return ChatColor.WHITE + "" + ChatColor.ITALIC + "Strong Bone";
	}

	public Material getMaterial() {
		return Material.BONE;
	}
	
	public boolean isStackable() {
		return true;
	}
	
	public boolean isGlowing() {
		return false;
	}
	
	public ItemStack getItemStack() {
		ItemStack item = super.getItemStack();
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
		item.setItemMeta(meta);
		return item;
	}
	
}
