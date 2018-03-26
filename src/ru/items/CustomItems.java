package ru.items;

import com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import ru.main.HardcorePlugin;

import java.util.ArrayList;
import java.util.List;

public class CustomItems {

	public static List<CustomItem> items = new ArrayList<CustomItem>();

	public static CustomItemMillenniumCoal millenniumCoal = new CustomItemMillenniumCoal();
	public static CustomItemEmpoweredCharge empoweredCharge = new CustomItemEmpoweredCharge();
	public static CustomItemInfusedString infusedString = new CustomItemInfusedString();
	public static CustomItemAncientsFlesh ancientsFlesh = new CustomItemAncientsFlesh();
	public static CustomItemStrongBone strongBone = new CustomItemStrongBone();
	public static CustomItemInnocentFire innocentFire = new CustomItemInnocentFire();

	public static void init() {
		items.addAll(Lists.<CustomItem>newArrayList(millenniumCoal, empoweredCharge, infusedString, ancientsFlesh, strongBone, innocentFire));
		for(CustomItem item : items) {
			if(item instanceof Listener) {
				Bukkit.getPluginManager().registerEvents((Listener) item, HardcorePlugin.instance);
			}
		}
	}

	public static List<CustomItem> getItems() {
		return items;
	}

	public static boolean isCustomItem(ItemStack stack) {
		if(stack != null && stack.hasItemMeta() && stack.getItemMeta().hasDisplayName()) {
			for(CustomItem item : CustomItems.getItems()) {
				if(item.isEquals(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static CustomItem getCustomItem(ItemStack stack) {
		for(CustomItem item : getItems()) {
			if(item.isEquals(stack)) {
				return item;
			}
		}
		return null;
	}

	public static CustomItem getByName(String name) {
		for(CustomItem item : getItems()) {
			if(ChatColor.stripColor(item.getName()).startsWith(name.replaceAll("_", " "))) {
				return item;
			}
		}
		return null;
	}

	public static List<String> getAllNames() {
		List<String> list = new ArrayList<String>();
		for(CustomItem item : getItems()) {
			list.add(item.getName());
		}
		return list;
	}

}
