package ru.items;

import org.bukkit.inventory.ItemStack;

/**
 * Represents an ItemStack-CustomItem connection
 */
public class CustomItemStack<T extends CustomItem> {

	private T customItem;
	private ItemStack stack;

	public CustomItemStack(T customItem, ItemStack stack) {
		this.customItem = customItem;
		this.stack = stack;
	}

	public T getCustomItem() {
		return customItem;
	}

	public void setCustomItem(T customItem) {
		this.customItem = customItem;
	}

	public ItemStack getStack() {
		return stack;
	}

	public void setStack(ItemStack stack) {
		this.stack = stack;
	}

}
