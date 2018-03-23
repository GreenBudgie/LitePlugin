package ru.achievements;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AchievementUltraSword extends InventoryBasedAchievement {

	public AchievementUltraSword() {
		this.setName("Кому-то нехуй делать");
		this.setTask("Зачарить золотой меч всеми чарами на максимум");
		this.setInfo("Проклятия и кастомные чары не считаются");
		this.setXp(950);
		this.setItem(Material.GOLD_SWORD);
		this.setDifficulty(Difficulty.HARD);
	}

	protected void tryComplete(PlayerInventory inv) {
		ItemStack[] items = inv.getStorageContents();
		for(ItemStack s : items) {
			try {
				Map<Enchantment, Integer> map = s.getItemMeta().getEnchants();
				boolean flag1 = (map.get(Enchantment.DAMAGE_ALL) != null && map.get(Enchantment.DAMAGE_ALL) == 5);
				boolean flag2 = (map.get(Enchantment.DAMAGE_ARTHROPODS) != null
						&& map.get(Enchantment.DAMAGE_ARTHROPODS) == 5);
				boolean flag3 = (map.get(Enchantment.DAMAGE_UNDEAD) != null && map.get(Enchantment.DAMAGE_UNDEAD) == 5);
				if(s.getType() == Material.GOLD_SWORD && (flag1 || flag2 || flag3)
						&& map.get(Enchantment.KNOCKBACK) == 2 && map.get(Enchantment.FIRE_ASPECT) == 2
						&& map.get(Enchantment.SWEEPING_EDGE) == 3 && map.get(Enchantment.MENDING) == 1
						&& map.get(Enchantment.LOOT_BONUS_MOBS) == 3 && map.get(Enchantment.DURABILITY) == 3) {
					this.complete((Player) inv.getHolder());
				}
			} catch(NullPointerException e) {
			}
		}
	}

}
