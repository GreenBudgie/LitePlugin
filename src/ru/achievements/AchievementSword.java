package ru.achievements;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AchievementSword extends InventoryBasedAchievement {

	public AchievementSword() {
		this.setName("Бесполезность");
		this.setTask("Зачарить деревянный меч на Остроту V");
		this.setXp(160);
		this.setItem(Material.WOOD_SWORD);
		this.setDifficulty(Difficulty.EASY);
	}

	protected void tryComplete(PlayerInventory inv) {
		ItemStack[] items = inv.getStorageContents();
		for(ItemStack s : items) {
			try {
				if(s.getType() == Material.WOOD_SWORD
						&& s.getItemMeta().getEnchants().get(Enchantment.DAMAGE_ALL) == 5) {
					this.complete((Player) inv.getHolder());
				}
			} catch(NullPointerException e) {
			}
		}
	}

}
