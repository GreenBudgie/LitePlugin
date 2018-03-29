package ru.items;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.util.InventoryHelper;

import java.util.HashMap;
import java.util.Map;

public abstract class CustomItem {

	public abstract String getName();

	public abstract Material getMaterial();

	public void onUseRight(Player p, ItemStack item, PlayerInteractEvent e) {
	}

	public void onUseLeft(Player p, ItemStack item, PlayerInteractEvent e) {
	}

	public void onUseRightAir(Player p, ItemStack item, PlayerInteractEvent e) {
	}

	public void onUseLeftAir(Player p, ItemStack item, PlayerInteractEvent e) {
	}

	public void onUseRightBlock(Player p, ItemStack item, Block b, PlayerInteractEvent e) {
	}

	public void onUseLeftBlock(Player p, ItemStack item, Block b, PlayerInteractEvent e) {
	}

	public void onBreak(Player p, ItemStack item, BlockBreakEvent e) {
	}

	public void onPlace(Player p, Block b, ItemStack item, BlockPlaceEvent e) {
	}

	public void update() {
	}

	public boolean isStackable() {
		return false;
	}

	public Map<String, Object> getFields() {
		return new HashMap<String, Object>();
	}

	public final boolean isEquals(ItemStack item) {
		if(item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return false;
		return getName().equals(item.getItemMeta().getDisplayName());
	}

	public boolean isGlowing() {
		return true;
	}

	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(getMaterial());
		InventoryHelper.setName(item, getName());
		for(String key : getFields().keySet()) {
			InventoryHelper.setValue(item, key, getFields().get(key).toString(), false);
		}
		if(!isStackable()) {
			InventoryHelper.setUnstackable(item);
		}
		if(isGlowing()) {
			InventoryHelper.setItemGlowing(item);
		}
		return item;
	}

}