package ru.enchants;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import ru.main.HardcorePlugin;

public abstract class DoncEnchantment extends Enchantment implements Listener {

	private String desc = "";
	private String onLevelUp = "";
	private Material itemToShow;

	public DoncEnchantment(int id) {
		super(id);
		EnchantmentManager.getEnchantments().add(this);
		Bukkit.getServer().getPluginManager().registerEvents((Listener) this, HardcorePlugin.instance);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getOnLevelUp() {
		return onLevelUp;
	}

	public void setOnLevelUp(String onLevelUp) {
		this.onLevelUp = onLevelUp;
	}

	public Material getItemToShow() {
		return itemToShow;
	}

	public void setItemToShow(Material itemToShow) {
		this.itemToShow = itemToShow;
	}

	/**
	 * Returns an enchantment level based on given experience (0 if enchantment mustn't be applied)
	 * @param exp Enchantment experience
	 */
	public abstract int tryEnchant(int exp);

}
