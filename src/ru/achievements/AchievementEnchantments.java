package ru.achievements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import ru.enchants.EnchantmentManager;

public class AchievementEnchantments extends ProgressiveAchievement {

	public AchievementEnchantments() {
		this.setName("Чародей");
		this.setTask("Выбить все кастомные чары в чарке");
		this.setXp(900);
		this.setRequirements(getAllEnchantments());
		this.setItem(Material.ENCHANTMENT_TABLE);
		this.setDifficulty(Difficulty.HARD);
	}
	
	private List<String> getAllEnchantments() {
		List<String> list = new ArrayList<String>();
		for(Enchantment e : EnchantmentManager.getEnchantments()) {
			if(!e.isTreasure()) list.add(e.getName());
		}
		return list;
	}

}
