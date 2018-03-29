package ru.enchants;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import ru.util.MathUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentSoulbind extends DoncEnchantment {

	public EnchantmentSoulbind(int id) {
		super(id);
		this.setDesc("Чар для всех категорий. Позволяет предметам сохраняться в инвентаре после смерти.");
	}
	
	public Material getItemToShow() {
		return Material.ENCHANTED_BOOK;
	}

	public String getName() {
		return "Soulbind";
	}

	public int getMaxLevel() {
		return 1;
	}

	public int getStartLevel() {
		return 1;
	}

	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.BREAKABLE;
	}

	public boolean isTreasure() {
		return true;
	}

	public boolean isCursed() {
		return false;
	}

	public boolean conflictsWith(Enchantment other) {
		return false;
	}

	public boolean canEnchantItem(ItemStack item) {
		return true;
	}

	public int tryEnchant(int exp) {
		return MathUtils.chance(30) ? 1 : 0;
	}
	
	private static class SavedItem {

		public ItemStack item;
		public int slot;

		public SavedItem(ItemStack i, int s) {
			item = i;
			slot = s;
		}

	}

	private static Map<String, List<SavedItem>> items = new HashMap<String, List<SavedItem>>();

	@EventHandler
	public void souldbindReturn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if(items.containsKey(p.getName())) {
			for(SavedItem item : items.get(p.getName())) {
				p.getInventory().setItem(item.slot, item.item);
			}
			items.remove(p.getName());
		}
	}

	@EventHandler
	public void souldbindSave(PlayerDeathEvent e) {
		if(!e.getKeepInventory()) {
			Player p = e.getEntity();
			List<SavedItem> saved = new ArrayList<SavedItem>();
			Map<ItemStack, Integer> map = EnchantmentManager.getItemsWithEnchantments(p);
			for(ItemStack item : map.keySet()) {
				if(item != null && item.getType() != Material.ENCHANTED_BOOK && EnchantmentManager.hasCustomEnchant(item, EnchantmentManager.SOULBIND)) {
					saved.add(new SavedItem(item, map.get(item)));
				}
			}
			if(!saved.isEmpty()) {
				List<ItemStack> drops = e.getDrops();
				for(SavedItem item : saved) {
					drops.remove(item.item);
				}
				items.put(p.getName(), saved);
			}
		}
	}
	
}
