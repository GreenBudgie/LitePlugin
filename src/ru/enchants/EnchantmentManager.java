package ru.enchants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import ru.util.InventoryHelper;
import ru.util.MathUtils;

public class EnchantmentManager {

	private static List<DoncEnchantment> enchants = new ArrayList<DoncEnchantment>();

	public static final EnchantmentForce FORCE = new EnchantmentForce(82);
	public static final EnchantmentInferno STEP = new EnchantmentInferno(83);
	public static final EnchantmentFlair FLAIR = new EnchantmentFlair(84);
	public static final EnchantmentGreed GREED = new EnchantmentGreed(85);
	public static final EnchantmentMoltenCore CORE = new EnchantmentMoltenCore(86);
	public static final EnchantmentGathering GATHERING = new EnchantmentGathering(87);
	public static final EnchantmentSoulbind SOULBIND = new EnchantmentSoulbind(88);
	public static final EnchantmentTimber TIMBER = new EnchantmentTimber(89);
	public static final EnchantmentMultishot MULTISHOT = new EnchantmentMultishot(90);
	public static final EnchantmentEvasion EVASION = new EnchantmentEvasion(91);

	public static List<DoncEnchantment> getEnchantments() {
		return enchants;
	}

	public static String getRomanNumber(int n) {
		switch(n) {
		case 1:
			return "I";
		case 2:
			return "II";
		case 3:
			return "III";
		case 4:
			return "IV";
		case 5:
			return "V";
		case 6:
			return "VI";
		case 7:
			return "VII";
		case 8:
			return "VIII";
		case 9:
			return "IX";
		case 10:
			return "X";
		}
		return "I";
	}

	public static int getByRoman(String r) {
		if(r.equals("I")) return 1;
		if(r.equals("II")) return 2;
		if(r.equals("III")) return 3;
		if(r.equals("IV")) return 4;
		if(r.equals("V")) return 5;
		if(r.equals("VI")) return 6;
		if(r.equals("VII")) return 7;
		if(r.equals("VIII")) return 8;
		if(r.equals("IX")) return 9;
		if(r.equals("X")) return 10;
		return 1;
	}

	public static void enchantBook(ItemStack item, int exp) {
		int chanceToAdd = 50;
		List<DoncEnchantment> enchs = getEnchantsForItem(item, false);
		Collections.shuffle(enchs);
		while(chanceToAdd > 0 && enchs.size() > 0) {
			if(MathUtils.chance(chanceToAdd)) {
				int level = enchs.get(0).tryEnchant(exp);
				if(level <= 0) continue;
				EnchantmentManager.addCustomEnchant(item, new CustomEnchant(enchs.get(0), level), true);
				enchs.remove(0);
				chanceToAdd = (int) Math.floor(chanceToAdd / 2.0);
			} else {
				break;
			}
		}
	}

	public static void enchantItemAsTreasure(ItemStack item, int chanceToAdd, int rolls) {
		rolls = MathUtils.clamp(rolls, 1, 100);
		List<DoncEnchantment> enchs = getEnchantsForItem(item, true);
		Collections.shuffle(enchs);
		while(chanceToAdd > 0 && enchs.size() > 0) {
			if(MathUtils.chance(chanceToAdd)) {
				int level = 0;
				for(int i = 0; i < rolls; i++) {
					level = enchs.get(0).tryEnchant(30);
					if(level > 0) break;
				}
				if(level <= 0) continue;
				EnchantmentManager.addCustomEnchant(item, new CustomEnchant(enchs.get(0), level), true);
				enchs.remove(0);
				chanceToAdd = (int) Math.floor(chanceToAdd / 2.0);
			} else {
				break;
			}
		}
	}

	public static List<DoncEnchantment> getEnchantsForItem(ItemStack item, boolean includeTreasure) {
		List<DoncEnchantment> ench = new ArrayList<DoncEnchantment>();
		for(DoncEnchantment e : getEnchantments()) {
			if((e.canEnchantItem(item) && e.getItemTarget().includes(item)) || item.getType() == Material.ENCHANTED_BOOK) {
				if(!e.isTreasure() || includeTreasure) {
					ench.add(e);
				}
			}
		}
		return ench;
	}

	public static boolean hasCustomEnchants(ItemStack item) {
		return !getCustomEnchantments(item).isEmpty();
	}

	public static boolean hasCustomEnchant(ItemStack item, Enchantment e) {
		if(item == null) return false;
		for(CustomEnchant ce : getCustomEnchantments(item)) {
			if(ce.getEnch().equals(e)) return true;
		}
		return false;
	}

	public static CustomEnchant getCustomEnchant(ItemStack item, Enchantment e) {
		for(CustomEnchant ce : getCustomEnchantments(item)) {
			if(ce.getEnch().equals(e)) return ce;
		}
		return null;
	}

	public static List<CustomEnchant> getCustomEnchantments(ItemStack item) {
		List<CustomEnchant> list = new ArrayList<CustomEnchant>();
		if(item != null) {
			ItemMeta meta = item.getItemMeta();
			if(meta != null && meta.hasLore()) {
				for(String lore : item.getItemMeta().getLore()) {
					CustomEnchant e = getByLore(ChatColor.stripColor(lore));
					if(e != null) {
						list.add(e);
					}
				}
			}
		}
		return list;
	}

	public static ItemStack joinCustomEnchantments(final ItemStack left, final ItemStack right, ItemStack result) {
		List<CustomEnchant> listLeft = EnchantmentManager.getCustomEnchantments(left);
		List<CustomEnchant> listRight = EnchantmentManager.getCustomEnchantments(right);
		boolean book = right.getType() == Material.ENCHANTED_BOOK;
		removeCustomEnchants(result);
		for(CustomEnchant e2 : listRight) {
			addCustomEnchant(result, new CustomEnchant(e2.getEnch(), e2.getLevel()));
		}
		for(CustomEnchant e : listLeft) {
			addCustomEnchant(result, new CustomEnchant(e.getEnch(), e.getLevel()));
			for(CustomEnchant e2 : listRight) {
				if(e2.getEnch().equals(e.getEnch())) {
					int l = e.getLevel();
					int r = e2.getLevel();
					addCustomEnchant(result, new CustomEnchant(e.getEnch(), l));
					if(r > l) {
						addCustomEnchant(result, new CustomEnchant(e.getEnch(), r));
					}
					if(r == l && l < e.getEnch().getMaxLevel()) {
						addCustomEnchant(result, new CustomEnchant(e.getEnch(), l + 1));
					}
				}
			}
		}
		return result;
	}

	public static Map<ItemStack, Integer> getItemsWithEnchantments(Player p) {
		Map<ItemStack, Integer> list = new HashMap<ItemStack, Integer>();
		PlayerInventory inv = p.getInventory();
		for(int i = 0; i < inv.getSize(); i++) {
			ItemStack item = inv.getItem(i);
			if(item != null && hasCustomEnchants(item)) {
				list.put(item, i);
			}
		}
		return list;
	}

	public static int getJoinCost(final ItemStack left, final ItemStack right, final ItemStack result) {
		List<CustomEnchant> listRes = EnchantmentManager.getCustomEnchantments(result);
		List<CustomEnchant> listLeft = EnchantmentManager.getCustomEnchantments(left);
		List<CustomEnchant> listRight = EnchantmentManager.getCustomEnchantments(right);
		int cost = 0;
		for(CustomEnchant e2 : listRight) {
			if(!hasCustomEnchant(left, e2.getEnch())) {
				cost += e2.getLevel();
			}
		}
		for(CustomEnchant e : listLeft) {
			if(!hasCustomEnchant(right, e.getEnch())) {
				cost += e.getLevel();
			}
			for(CustomEnchant e2 : listRight) {
				if(e2.getEnch().equals(e.getEnch())) {
					int l = e.getLevel();
					int r = e2.getLevel();
					if(r == l && l < e.getEnch().getMaxLevel()) {
						cost += r + 1;
					}
				}
			}
		}
		return cost;
	}

	public static CustomEnchant getByLore(String loreLine) {
		String[] str = ChatColor.stripColor(loreLine).trim().split(" ");
		String name;
		int level = 1;
		if(str.length > 1) {
			name = "";
			for(int i = 0; i < str.length; i++) {
				if(i < str.length - 1) {
					name += str[i] + " ";
				} else {
					level = getByRoman(str[i]);
				}
			}
		} else {
			name = str[0];
		}
		name = name.trim();
		Enchantment ench = null;
		for(Enchantment e : getEnchantments()) {
			if(e.getName().startsWith(name)) return new CustomEnchant(e, level);
		}
		return null;
	}

	public static ItemStack removeCustomEnchants(ItemStack item) {
		for(Enchantment e : getEnchantments()) {
			removeCustomEnchant(item, e);
		}
		return item;
	}

	public static ItemStack removeCustomEnchant(ItemStack item, Enchantment e) {
		List<String> newLore = new ArrayList<String>();
		if(item != null) {
			ItemMeta meta = item.getItemMeta();
			if(meta != null && meta.hasLore()) {
				for(String lore : meta.getLore()) {
					if(!ChatColor.stripColor(lore).startsWith(e.getName())) {
						newLore.add(lore);
					}
				}
				meta.setLore(newLore);
				item.setItemMeta(meta);
			}
		}
		return item;
	}

	public static ItemStack addCustomEnchant(ItemStack item, CustomEnchant e, boolean anyItem) {
		if(item != null) {
			if(e != null && e.getEnch() != null && ((e.getEnch().canEnchantItem(item) && e.getEnch().getItemTarget().includes(item)) || anyItem
					|| item.getType() == Material.ENCHANTED_BOOK)) {
				removeCustomEnchant(item, e.getEnch());
				InventoryHelper.addLoreStart(item,
						ChatColor.GRAY + e.getEnch().getName() + (e.getEnch().getMaxLevel() == 1 ? "" : " " + getRomanNumber(e.getLevel())));
			}
		}
		return item;
	}

	public static ItemStack addCustomEnchant(ItemStack item, CustomEnchant e) {
		return addCustomEnchant(item, e, false);
	}

	public static void enchantItem(ItemStack item, int exp) {
		List<DoncEnchantment> ench = getEnchantsForItem(item, false);
		for(DoncEnchantment e : ench) {
			int lvl = e.tryEnchant(exp);
			if(lvl > 0) proceed(item, e, lvl);
		}
	}

	private static void proceed(ItemStack item, Enchantment e, int l) {
		addCustomEnchant(item, new CustomEnchant(e, l));
	}

	public static CustomEnchant getByNameAndLevel(String name, int level) {
		for(Enchantment e : getEnchantments()) {
			if(e.getName().startsWith(name)) {
				if(level < e.getStartLevel()) level = e.getStartLevel();
				if(level > e.getMaxLevel()) level = e.getMaxLevel();
				return new CustomEnchant(e, level);
			}
		}
		return null;
	}

}
