package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AchievementMekita extends InventoryBasedAchievement {

	public AchievementMekita() {
		this.setName("Мекита");
		this.setTask("Заполнить весь инвентарь стаками любых редстоун-предметов");
		this.setInfo("Подходит любой предмет, имеющий в крафте редстоун и находящийся во вкладке \"Редстоун\" в креативе, но не сам редстоун");
		this.setXp(270);
		this.setItem(Material.REDSTONE_BLOCK);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	protected void tryComplete(PlayerInventory inv) {
		boolean valid = true;
		for(ItemStack s : inv.getStorageContents()) {
			if(s == null || !isRedstoneItem(s.getType()) || s.getAmount() < 64) {
				valid = false;
				break;
			}
		}
		if(valid) {
			this.complete((Player) inv.getHolder());
		}
	}

	private boolean isRedstoneItem(Material m) {
		return m == Material.PISTON_BASE || m == Material.PISTON_STICKY_BASE || m == Material.REDSTONE_BLOCK || m == Material.REDSTONE_COMPARATOR
				|| m == Material.DIODE || m == Material.REDSTONE_TORCH_ON || m == Material.REDSTONE_LAMP_OFF || m == Material.DISPENSER
				|| m == Material.DROPPER || m == Material.NOTE_BLOCK || m == Material.OBSERVER;
	}

}
