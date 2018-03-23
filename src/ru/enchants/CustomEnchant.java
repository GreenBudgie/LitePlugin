package ru.enchants;

import org.bukkit.enchantments.Enchantment;

public class CustomEnchant {

	private int level;
	private Enchantment ench;
	
	public CustomEnchant(Enchantment e, int l) {
		level = l;
		ench = e;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Enchantment getEnch() {
		return ench;
	}

	public void setEnch(Enchantment ench) {
		this.ench = ench;
	}
	
}
