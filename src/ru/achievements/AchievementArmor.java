package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import ru.main.HardcorePlugin;

public class AchievementArmor extends InventoryBasedAchievement {

	public AchievementArmor() {
		this.setName("Красавоц");
		this.setTask("Одеться в разноцветную кожаную броню");
		this.setXp(60);
		this.setItem(Material.ORANGE_GLAZED_TERRACOTTA);
		this.setDifficulty(Difficulty.DONZ);
	}

	protected void tryComplete(PlayerInventory inv) {
		ItemStack[] armor = inv.getArmorContents();
		boolean valid = true;
		for(ItemStack s : armor) {
			try {
				LeatherArmorMeta meta = (LeatherArmorMeta) s.getItemMeta();
				if(meta.getColor() == HardcorePlugin.instance.getServer().getItemFactory().getDefaultLeatherColor()) {
					valid = false;
					break;
				}
			} catch(Exception e) {
				valid = false;
				break;
			}
		}
		if(valid) {
			this.complete((Player) inv.getHolder());
		}
	}

}
