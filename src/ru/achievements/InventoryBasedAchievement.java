package ru.achievements;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.PlayerInventory;

public abstract class InventoryBasedAchievement extends DoncAchievement {

	@EventHandler
	public void achieve(InventoryClickEvent e) {
		tryComplete(e.getWhoClicked().getInventory());
	}
	
	@EventHandler
	public void achieve2(InventoryDragEvent e) {
		tryComplete(e.getWhoClicked().getInventory());
	}
	
	@EventHandler
	public void achieve3(InventoryCloseEvent e) {
		tryComplete(e.getPlayer().getInventory());
	}
	
	@EventHandler
	public void achieve4(PlayerItemHeldEvent e) {
		tryComplete(e.getPlayer().getInventory());
	}
	
	protected abstract void tryComplete(PlayerInventory inv);
	
}
