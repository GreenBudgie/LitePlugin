package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import ru.enchants.EnchantmentManager;

public class AchievementSoulbind extends InventoryBasedAchievement {

	public AchievementSoulbind() {
		this.setName("Без потерь");
		this.setTask("Зачарить или найти фулл сет на Soulbind");
		this.setXp(1000);
		this.setItem(Material.ENDER_CHEST);
		this.setDifficulty(Difficulty.HARD);
	}

	protected void tryComplete(PlayerInventory inv) {
		for(ItemStack s : inv.getArmorContents()) {
			if(s == null || !EnchantmentManager.hasCustomEnchant(s, EnchantmentManager.SOULBIND)) {
				return;
			}
		}
		this.complete((Player) inv.getHolder());
	}
}
