package ru.util;

import com.google.common.collect.Lists;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.main.HardcorePlugin;

import java.util.*;

public class InventoryHelper {

	public static ItemStack getFirstStack(Inventory inv, Material mat) {
		for(int i = 0; i < inv.getSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if(stack != null) {
				if(stack.getType() == mat) {
					return inv.getItem(i);
				}
			}
		}
		return null;
	}

	public static int getOffHandSlot() {
		return 40;
	}

	public static int getHelmetSlot() {
		return 39;
	}

	public static int getChestplateSlot() {
		return 38;
	}

	public static int getLeggingsSlot() {
		return 37;
	}

	public static int getBootsSlot() {
		return 36;
	}
	
	public static boolean isPickaxe(Material m) {
		return m == Material.WOOD_PICKAXE || m == Material.STONE_PICKAXE || m == Material.IRON_PICKAXE || m == Material.GOLD_PICKAXE || m == Material.DIAMOND_PICKAXE;
	}
	
	public static boolean isSword(Material m) {
		return m == Material.WOOD_SWORD || m == Material.STONE_SWORD || m == Material.IRON_SWORD || m == Material.GOLD_SWORD || m == Material.DIAMOND_SWORD;
	}
	
	public static boolean isHoe(Material m) {
		return m == Material.WOOD_HOE || m == Material.STONE_HOE || m == Material.IRON_HOE || m == Material.GOLD_HOE || m == Material.DIAMOND_HOE;
	}
	
	public static boolean isAxe(Material m) {
		return m == Material.WOOD_AXE || m == Material.STONE_AXE || m == Material.IRON_AXE || m == Material.GOLD_AXE || m == Material.DIAMOND_AXE;
	}

	public static boolean isSpade(Material m) {
		return m == Material.WOOD_SPADE || m == Material.STONE_SPADE || m == Material.IRON_SPADE || m == Material.GOLD_SPADE || m == Material.DIAMOND_SPADE;
	}
	
	public static boolean isArmor(Material m) {
		return m != null && EnchantmentTarget.ARMOR.includes(m);
	}

	public static boolean isArmor(ItemStack s) {
		return s != null && EnchantmentTarget.ARMOR.includes(s);
	}

	public static boolean hasEnchants(ItemStack item) {
		if(item == null) return false;
		if(item.getType() == Material.ENCHANTED_BOOK) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			return meta.hasStoredEnchants() || meta.hasEnchants();
		} else {
			return item.getItemMeta().hasEnchants();
		}
	}
	
	public static List<Material> getAllArmorTypes() {
		return Lists.<Material>newArrayList(Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET,
				Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET, Material.GOLD_BOOTS,
				Material.GOLD_LEGGINGS, Material.GOLD_CHESTPLATE, Material.GOLD_HELMET, Material.IRON_BOOTS, Material.IRON_LEGGINGS, Material.IRON_CHESTPLATE,
				Material.IRON_HELMET, Material.DIAMOND_BOOTS, Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET);
	}

	public static ChatColor getFirstColorInString(String str) {
		return ChatColor.getByChar(ChatColor.getLastColors(str).substring(1, 2));
	}

	public static boolean isBeaconEffect(PotionEffectType ef) {
		PotionEffectType[] beacon = {PotionEffectType.SPEED, PotionEffectType.FAST_DIGGING, PotionEffectType.DAMAGE_RESISTANCE, PotionEffectType.JUMP,
				PotionEffectType.INCREASE_DAMAGE, PotionEffectType.REGENERATION};
		return Lists.<PotionEffectType>newArrayList(beacon).contains(ef);
	}

	public static boolean isBadEffect(PotionEffectType ef) {
		PotionEffectType[] bad = {PotionEffectType.SLOW, PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.GLOWING,
				PotionEffectType.HARM, PotionEffectType.HUNGER, PotionEffectType.LEVITATION, PotionEffectType.POISON, PotionEffectType.SLOW_DIGGING,
				PotionEffectType.UNLUCK, PotionEffectType.WEAKNESS, PotionEffectType.WITHER};
		return Lists.<PotionEffectType>newArrayList(bad).contains(ef);
	}

	public static boolean isGoodEffect(PotionEffectType ef) {
		return !isBadEffect(ef);
	}

	public static boolean haveSpace(Inventory inv, Material m) {
		if(inv.firstEmpty() != -1) return true;
		List<ItemStack> slots = getItems(inv, m);
		for(ItemStack s : slots) {
			if(s.getAmount() != s.getType().getMaxStackSize()) return true;
		}
		return false;
	}

	public static int getBurnTime(ItemStack item) {
		if(item == null || item.getType() == Material.AIR) return 0;
		return TileEntityFurnace.fuelTime(CraftItemStack.asNMSCopy(item));
	}

	public static ItemStack setUnstackable(ItemStack item) {
		return InventoryHelper.setValue(item, ChatColor.DARK_GRAY + "ID", ChatColor.DARK_GRAY + MathUtils.getRandomSequence(16), false);
	}

	public static void sendActionBarMessage(Player p, String text) {
		((CraftPlayer) p).getHandle().playerConnection
				.sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + "\"}"), ChatMessageType.GAME_INFO));
	}

	public static ItemStack addLore(ItemStack item, String... lore) {
		ItemMeta itemMeta = item.getItemMeta();
		List<String> itemLore;
		if(itemMeta.hasLore()) {
			itemLore = itemMeta.getLore();
			itemLore.addAll(Lists.<String>newArrayList(lore));
		} else {
			itemLore = Lists.<String>newArrayList(lore);
		}
		itemMeta.setLore(itemLore);
		item.setItemMeta(itemMeta);
		return item;
	}

	public static ItemStack addLoreStart(ItemStack item, String... lore) {
		ItemMeta itemMeta = item.getItemMeta();
		List<String> itemLore = new ArrayList<String>();
		if(itemMeta.hasLore()) {
			List<String> newLore = Lists.<String>newArrayList(lore);
			itemLore.addAll(newLore);
			itemLore.addAll(itemMeta.getLore());
		} else {
			itemLore = Lists.<String>newArrayList(lore);
		}
		itemMeta.setLore(itemLore);
		item.setItemMeta(itemMeta);
		return item;
	}

	// Get the item lore or return empty list to prevent NullPointerException
	public static List<String> getLore(ItemStack item) {
		return item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList<String>();
	}

	public static String getExactLore(ItemStack item, String oldStringStart) {
		List<String> lore = getLore(item);
		for(String s : lore) {
			if(ChatColor.stripColor(s).startsWith(ChatColor.stripColor(oldStringStart))) {
				return s;
			}
		}
		return "null";
	}

	public static boolean isValue(String s) {
		return s.split(":").length >= 2;
	}

	public static String getValueKey(String s) {
		return isValue(s) ? s.split(":")[0] : null;
	}

	public static String getValue(String s) {
		return isValue(s) ? s.split(":")[1].trim() : null;
	}

	public static boolean changeValue(ItemStack item, String valStart, String newVal) {
		List<String> lore = getLore(item);
		boolean flag = false;
		for(int i = 0; i < lore.size(); i++) {
			String s = lore.get(i);
			String key = getValueKey(s);
			if(key != null && ChatColor.stripColor(key).startsWith(valStart)) {
				lore.set(i, key + ": " + ChatColor.getLastColors(getValue(s)) + newVal);
				flag = true;
			}
		}
		if(flag) setLore(item, lore.toArray(new String[0]));
		return flag;
	}

	public static ItemStack setValue(ItemStack item, String valStart, String newVal, boolean toStart) {
		List<String> lore = getLore(item);
		if(!replaceLore(item, valStart, valStart + ": " + newVal)) {
			if(toStart) {
				addLoreStart(item, valStart + ": " + newVal);
			} else {
				addLore(item, valStart + ": " + newVal);
			}
		}
		return item;
	}

	public static boolean hasLoreStart(ItemStack item, String loreStart) {
		List<String> lore = getLore(item);
		for(String s : lore) {
			if(ChatColor.stripColor(s).startsWith(ChatColor.stripColor(loreStart))) {
				return true;
			}
		}
		return false;
	}
	
	public static String getAttributeName(Attribute att) {
		switch(att) {
		case GENERIC_ARMOR:
			return "generic.armor";
		case GENERIC_ARMOR_TOUGHNESS:
			return "generic.armorToughness";
		case GENERIC_ATTACK_DAMAGE:
			return "generic.attackDamage";
		case GENERIC_ATTACK_SPEED:
			return "generic.attackSpeed";
		case GENERIC_FLYING_SPEED:
			return "generic.flyingSpeed";
		case GENERIC_FOLLOW_RANGE:
			return "generic.followRange";
		case GENERIC_KNOCKBACK_RESISTANCE:
			return "generic.knockbackResistance";
		case GENERIC_LUCK:
			return "generic.luck";
		case GENERIC_MAX_HEALTH:
			return "generic.maxHealth";
		case GENERIC_MOVEMENT_SPEED:
			return "generic.movementSpeed";
		case HORSE_JUMP_STRENGTH:
			return "horse.jumpStrength";
		case ZOMBIE_SPAWN_REINFORCEMENTS:
			return "zombie.spawnReinforcements";
		default:
			return null;
		}
	}
	
	public static ItemStack addAttributes(ItemStack item, CustomAttribute... attributes) {
		net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound nbt = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		NBTTagList modifiers = new NBTTagList();
		for(CustomAttribute att : attributes) {
			NBTTagCompound comp = new NBTTagCompound();
			comp.set("AttributeName", new NBTTagString(att.getName()));
			comp.set("Name", new NBTTagString(att.getName()));
			comp.set("Amount", new NBTTagDouble(att.getValue()));
			comp.set("Operation", new NBTTagInt(att.getOperation().ordinal()));
			comp.set("UUIDLeast", new NBTTagInt(894654));
			comp.set("UUIDMost", new NBTTagInt(2872));
			comp.set("Slot", new NBTTagString(att.getSlot().getName()));
			modifiers.add(comp);
		}
		nbt.set("AttributeModifiers", modifiers);
		nmsItem.setTag(nbt);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}

	public static ItemStack generateItemWithNameAndAttributes(Material m, String name, CustomAttribute... attributes) {
		return setName(addAttributes(new ItemStack(m), attributes), name);
	}


	public static boolean hasValue(ItemStack item, String valStart) {
		List<String> lore = getLore(item);
		for(String s : lore) {
			if(ChatColor.stripColor(s).startsWith(ChatColor.stripColor(valStart))) {
				try {
					return isValue(s);
				} catch(Exception e) {
					return false;
				}
			}
		}
		return false;
	}

	public static String getStringValue(ItemStack item, String oldStringStart) {
		List<String> lore = getLore(item);
		for(String s : lore) {
			if(ChatColor.stripColor(s).startsWith(ChatColor.stripColor(oldStringStart))) {
				try {
					return ChatColor.stripColor(s.split(":")[1].trim());
				} catch(Exception e) {
					HardcorePlugin.error("String is not a value");
					return "null";
				}
			}
		}
		HardcorePlugin.error("String has no value or lore is empty");
		return "null";
	}

	public static int getIntValue(ItemStack item, String oldStringStart) {
		List<String> lore = getLore(item);
		for(String s : lore) {
			if(ChatColor.stripColor(s).startsWith(ChatColor.stripColor(oldStringStart))) {
				try {
					return Integer.valueOf(ChatColor.stripColor(s.split(":")[1].trim()));
				} catch(Exception e) {
					HardcorePlugin.error("String is not integer or not a value");
					return -1;
				}
			}
		}
		HardcorePlugin.error("String has no value or lore is empty");
		return -1;
	}

	public static Map<String, String> getValues(ItemStack item) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> lore = getLore(item);
		for(String s : lore) {
			try {
				String[] val = s.split(":");
				if(!ChatColor.stripColor(val[0]).equals("ID")) {
					map.put(ChatColor.stripColor(val[0]), ChatColor.stripColor(val[1].trim()));
				}
			} catch(Exception e) {
				continue;
			}
		}
		return map;
	}

	public static boolean removeLore(ItemStack item, String oldLoreStart) {
		List<String> lore = getLore(item);
		List<String> newLore = new ArrayList<String>();
		boolean flag = false;
		for(String s : lore) {
			if(!ChatColor.stripColor(s).startsWith(ChatColor.stripColor(oldLoreStart))) {
				newLore.add(s);
			} else {
				flag = true;
			}
		}
		setLore(item, newLore.toArray(new String[0]));
		return flag;
	}

	public static ItemStack replaceLore(ItemStack item, int row, String newString) {
		List<String> lore = getLore(item);
		boolean flag = false;
		if(lore.isEmpty() || lore.size() < row) {
			throw new IllegalArgumentException("(replaceLore) Lore is empty or row is larger than lore size");
		}
		lore.set(row, newString);
		setLore(item, lore.toArray(new String[0]));
		return item;
	}

	public static boolean replaceLore(ItemStack item, String oldStringStart, String newString) {
		List<String> lore = getLore(item);
		boolean flag = false;
		for(int i = 0; i < lore.size(); i++) {
			if(ChatColor.stripColor(lore.get(i)).startsWith(ChatColor.stripColor(oldStringStart))) {
				replaceLore(item, i, newString);
				flag = true;
			}
		}
		return flag;
	}

	public static ItemStack setLore(ItemStack item, String... lore) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setLore(Lists.<String>newArrayList(lore));
		item.setItemMeta(itemMeta);
		return item;
	}

	public static ItemStack addSplittedLore(ItemStack item, int max, String lore) {
		List<String> list = new ArrayList<String>();
		for(String s : InventoryHelper.splitLongString(lore, max)) {
			list.add(s);
		}
		addLore(item, list.toArray(new String[0]));
		return item;
	}

	public static ItemStack addSplittedLore(ItemStack item, int max, String lore, ChatColor color) {
		List<String> list = new ArrayList<String>();
		for(String s : InventoryHelper.splitLongString(lore, max)) {
			list.add(color + s);
		}
		addLore(item, list.toArray(new String[0]));
		return item;
	}

	public static ItemStack setName(ItemStack item, String name) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);
		return item;
	}

	public static ItemStack setItemGlowing(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.addEnchant(Enchantment.OXYGEN, 1, true);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemMeta);
		return item;
	}

	public static ItemStack generateItemWithName(Material m, String name) {
		ItemStack item = new ItemStack(m);
		setName(item, name);
		return item;
	}

	public static ItemStack generateItemWithName(Material m, String name, boolean glow) {
		ItemStack item = new ItemStack(m);
		setName(item, name);
		if(glow) {
			setItemGlowing(item);
		}
		return item;
	}

	public static ItemStack generateItemWithLore(Material m, String... lore) {
		ItemStack item = new ItemStack(m);
		setLore(item, lore);
		return item;
	}

	public static ItemStack generateItemWithNameAndLore(Material m, String name, String... lore) {
		ItemStack item = new ItemStack(m);
		setName(item, name);
		setLore(item, lore);
		return item;
	}

	public static ItemStack generateItemWithNameAndLore(Material m, String name, boolean glow, String... lore) {
		ItemStack item = new ItemStack(m);
		if(glow) {
			setItemGlowing(item);
		}
		setName(item, name);
		setLore(item, lore);
		return item;
	}

	public static ItemStack generatePotion(Color color, PotionEffect... effects) {
		ItemStack item = new ItemStack(Material.POTION);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		for(PotionEffect effect : effects) {
			meta.addCustomEffect(effect, true);
		}
		meta.setColor(color);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack generatePotion(Color color, boolean isSplash, boolean isLingering, PotionEffect... effects) {
		ItemStack item = new ItemStack(isLingering ? Material.LINGERING_POTION : (isSplash ? Material.SPLASH_POTION : Material.POTION));
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		for(PotionEffect effect : effects) {
			meta.addCustomEffect(effect, true);
		}
		meta.setColor(color);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack generatePotion(String name, Color color, PotionEffect... effects) {
		return InventoryHelper.setName(generatePotion(color, effects), name);
	}

	public static ItemStack generatePotion(String name, Color color, boolean isSplash, boolean isLingering, PotionEffect... effects) {
		return InventoryHelper.setName(generatePotion(color, isSplash, isLingering, effects), name);
	}

	public static ItemStack generateGlowingItemWithNameAndLore(Material m, String name, String... lore) {
		ItemStack item = new ItemStack(m);
		setName(item, name);
		setLore(item, lore);
		setItemGlowing(item);
		return item;
	}

	public static void give(Player p, ItemStack item) {
		Item i = p.getWorld().dropItem(p.getLocation(), item);
		i.setPickupDelay(0);
	}

	public static void addItems(Inventory inv, int row, ItemStack... items) {
		for(int i = row; i < row + items.length; i++) {
			inv.setItem(i, items[i]);
		}
	}

	public static void addItems(Inventory inv, int row, List<ItemStack> items) {
		int s = row * 9;
		for(int i = s; i < s + items.size(); i++) {
			inv.setItem(i, items.get(i - s));
		}
	}

	public static boolean haveSpace(Inventory inv, ItemStack s) {
		if(inv.firstEmpty() != -1) return true;
		List<ItemStack> slots = getItems(inv, s.getType());
		for(ItemStack stack : slots) {
			if(stack.getAmount() + s.getAmount() <= s.getType().getMaxStackSize()) return true;
		}
		return false;
	}

	public static ItemStack getFirstStackWithAmountOrLeftHand(Player p, Material mat, int min) {
		ItemStack left = p.getInventory().getItemInOffHand();
		if(left != null && left.getType() == mat && left.getAmount() >= min) {
			return left;
		}
		for(int i = 0; i < p.getInventory().getSize(); i++) {
			ItemStack stack = p.getInventory().getItem(i);
			if(stack != null) {
				if(stack.getType() == mat && stack.getAmount() >= min) {
					return stack;
				}
			}
		}
		return null;
	}

	public static ItemStack getFirstStackWithAmount(Player p, Material mat, int min) {
		for(int i = 0; i < p.getInventory().getSize(); i++) {
			ItemStack stack = p.getInventory().getItem(i);
			if(stack != null) {
				if(stack.getType() == mat && stack.getAmount() >= min) {
					return p.getInventory().getItem(i);
				}
			}
		}
		return null;
	}

	public static int getItemCount(Player p, Material m) {
		PlayerInventory inv = p.getInventory();
		int c = 0;
		for(int i = 0; i < inv.getSize(); i++) {
			if(inv.getItem(i) != null) {
				if(inv.getItem(i).getType() == m) {
					c += inv.getItem(i).getAmount();
				}
			}
		}
		return c;
	}

	public static List<Integer> getSlotsContains(Player p, Material m) {
		PlayerInventory inv = p.getInventory();
		List<Integer> r = new ArrayList<Integer>();
		for(int i = 0; i < inv.getSize(); i++) {
			if(inv.getItem(i) != null) {
				if(inv.getItem(i).getType() == m) {
					r.add(i);
				}
			}
		}
		return r;
	}

	public static List<ItemStack> getItems(Inventory inv, Material m) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for(int i = 0; i < inv.getSize(); i++) {
			if(inv.getItem(i) != null) {
				if(inv.getItem(i).getType() == m) {
					list.add(inv.getItem(i));
				}
			}
		}
		return list;
	}

	public static List<ItemStack> getItemsLimited(Player p, Material m, int max) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		int c = 0;
		for(ItemStack s : getItems(p.getInventory(), m)) {
			list.add(s);
			for(int j = 0; j < s.getAmount(); j++) {
				if(c < max) {
					c++;
				} else {
					break;
				}
			}
		}
		return list;
	}

	public static void removeItemsLimited(Player p, Material m, int max) {
		List<ItemStack> items = getAllStacks(p.getInventory(), m);
		int c = 0;
		while(p.getInventory().contains(m)) {
			ItemStack s = getValidStackFromList(items);
			s.setAmount(s.getAmount() - 1);
			c++;
			if(c >= max) break;
		}
	}

	// Returns true if item is broken
	public static boolean damageItem(ItemStack s, int dmg) {
		if(s == null || s.getType() == Material.AIR) return true;
		net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(s);
		stack.isDamaged(dmg, new Random(), null);
		s.setDurability((short) stack.i());
		if(s.getDurability() > s.getType().getMaxDurability()) {
			s.setAmount(0);
			return true;
		}
		return false;
	}

	public static List<ItemStack> getAllStacks(Inventory inv, Material m) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for(ItemStack s : inv.getStorageContents()) {
			if(s != null && s.getType() == m) {
				list.add(s);
			}
		}
		return list;
	}

	public static ItemStack getValidStackFromList(List<ItemStack> list) {
		for(ItemStack s : list) {
			if(s != null && s.getAmount() != 0) {
				return s;
			}
		}
		return null;
	}

	public static List<ItemStack> getAllStacks(Inventory inv, Material m, int data) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for(ItemStack s : inv.getStorageContents()) {
			if(s != null && s.getType() == m && s.getDurability() == data) {
				list.add(s);
			}
		}
		return list;
	}
	
	public static List<ItemStack> getAllStacksEntire(Inventory inv, Material m, int data) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for(ItemStack s : inv) {
			if(s != null && s.getType() == m && s.getDurability() == data) {
				list.add(s);
			}
		}
		return list;
	}

	public static List<ItemStack> getAllStacksWithAmount(Inventory inv, Material m, int min) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for(ItemStack s : inv.getStorageContents()) {
			if(s != null && s.getType() == m && s.getAmount() >= min) {
				list.add(s);
			}
		}
		return list;
	}

	public static void placeItemsCenter(Inventory inv, List<ItemStack> stacks, int row) {
		if(stacks.size() > 9) throw new IllegalArgumentException("More than 9 stacks are not allowed");
		int h = row * 9;
		int start = (int) Math.round(-(stacks.size() / 2) + 0.5);
		if(stacks.size() % 2 == 0) start++;
		for(int i = 0; i < stacks.size(); i++) {
			inv.setItem(h + start + i + 3, stacks.get(i));
		}
	}

	@Deprecated
	public static void placeItemsCenterUnlimited(Inventory inv, List<ItemStack> stacks, int startRow) {
		int start = (int) Math.round(-(stacks.size() / 2) + 0.5);
		for(int i = 0; i < stacks.size(); i++) {
			int h = (startRow * 9) + ((int) i / 9) * 9;
			inv.setItem(h + start + i + 3, stacks.get(i));
		}
	}

	public static List<String> splitLongString(String str, int max) {
		List<String> list = new ArrayList<String>();
		int prev = 0;
		for(int i = 0; i < str.length(); i++) {
			if(i - prev >= max && str.charAt(i) == ' ') {
				list.add(str.substring(prev, i));
				prev = i;
			}
			if(i == str.length() - 1) {
				list.add(str.substring(prev, i + 1));
			}
		}
		return list;
	}

	public static List<String> splitLongString(String str, int max, ChatColor color) {
		List<String> list = new ArrayList<String>();
		int prev = 0;
		for(int i = 0; i < str.length(); i++) {
			if(i - prev >= max && str.charAt(i) == ' ') {
				list.add((list.isEmpty() ? "" : color) + str.substring(prev, i));
				prev = i;
			}
			if(i == str.length() - 1) {
				list.add((list.isEmpty() ? "" : color) + str.substring(prev, i + 1));
			}
		}
		return list;
	}

	public static void removeItemsExact(PlayerInventory inv, Material itemToRemove, List<String> lore, int minCount, int maxCount) {
		for(ItemStack item : inv.getStorageContents()) {
			if(item != null) {
				if(item.getType() != itemToRemove) continue;
				if(item.getAmount() < minCount || item.getAmount() > maxCount) continue;
				ItemMeta meta = item.getItemMeta();
				boolean argHasLore = lore != null && !lore.isEmpty();
				boolean itemHasLore = meta.hasLore();
				if(!argHasLore && !itemHasLore) {
					item.setAmount(0);
					continue;
				}
				if(lore.size() != meta.getLore().size()) continue;
				for(int i = 0; i < lore.size(); i++) {
					if(!meta.getLore().get(i).equals(lore.get(i))) continue;
				}
				item.setAmount(0);
			}
		}
	}

	public static ChatColor fromBarColor(BarColor color) {
		switch(color) {
		case BLUE:
			return ChatColor.BLUE;
		case GREEN:
			return ChatColor.GREEN;
		case PINK:
			return ChatColor.LIGHT_PURPLE;
		case PURPLE:
			return ChatColor.DARK_PURPLE;
		case RED:
			return ChatColor.RED;
		case WHITE:
			return ChatColor.WHITE;
		case YELLOW:
			return ChatColor.YELLOW;
		default:
			return null;
		}
	}

	public static ChatColor toChatColor(Color c) {
		if(c == Color.BLACK) {
			return ChatColor.BLACK;
		}
		if(c == Color.WHITE) {
			return ChatColor.WHITE;
		}
		if(c == Color.ORANGE) {
			return ChatColor.GOLD;
		}
		if(c == Color.YELLOW) {
			return ChatColor.YELLOW;
		}
		if(c == Color.AQUA) {
			return ChatColor.AQUA;
		}
		if(c == Color.BLUE) {
			return ChatColor.BLUE;
		}
		if(c == Color.FUCHSIA) {
			return ChatColor.LIGHT_PURPLE;
		}
		if(c == Color.PURPLE) {
			return ChatColor.DARK_PURPLE;
		}
		if(c == Color.GRAY) {
			return ChatColor.DARK_GRAY;
		}
		if(c == Color.GREEN) {
			return ChatColor.DARK_GREEN;
		}
		if(c == Color.LIME) {
			return ChatColor.GREEN;
		}
		if(c == Color.MAROON) {
			return ChatColor.DARK_RED;
		}
		if(c == Color.NAVY) {
			return ChatColor.DARK_BLUE;
		}
		if(c == Color.OLIVE) {
			return ChatColor.DARK_GREEN;
		}
		if(c == Color.RED) {
			return ChatColor.RED;
		}
		if(c == Color.SILVER) {
			return ChatColor.GRAY;
		}
		if(c == Color.TEAL) {
			return ChatColor.DARK_AQUA;
		}
		return null;
	}

	public static Color toColor(ChatColor c) {
		if(c == ChatColor.BLACK) {
			return Color.BLACK;
		}
		if(c == ChatColor.WHITE) {
			return Color.WHITE;
		}
		if(c == ChatColor.GOLD) {
			return Color.ORANGE;
		}
		if(c == ChatColor.YELLOW) {
			return Color.YELLOW;
		}
		if(c == ChatColor.AQUA) {
			return Color.AQUA;
		}
		if(c == ChatColor.BLUE) {
			return Color.BLUE;
		}
		if(c == ChatColor.LIGHT_PURPLE) {
			return Color.FUCHSIA;
		}
		if(c == ChatColor.DARK_PURPLE) {
			return Color.PURPLE;
		}
		if(c == ChatColor.DARK_GRAY) {
			return Color.GRAY;
		}
		if(c == ChatColor.DARK_GREEN) {
			return Color.GREEN;
		}
		if(c == ChatColor.GREEN) {
			return Color.LIME;
		}
		if(c == ChatColor.DARK_RED) {
			return Color.MAROON;
		}
		if(c == ChatColor.DARK_BLUE) {
			return Color.NAVY;
		}
		if(c == ChatColor.DARK_GREEN) {
			return Color.OLIVE;
		}
		if(c == ChatColor.RED) {
			return Color.RED;
		}
		if(c == ChatColor.GRAY) {
			return Color.SILVER;
		}
		if(c == ChatColor.DARK_AQUA) {
			return Color.TEAL;
		}
		return null;
	}

}
