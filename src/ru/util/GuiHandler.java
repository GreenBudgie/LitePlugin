package ru.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.commands.CommandAch;
import ru.commands.CommandBoss;
import ru.commands.CommandPlugin;
import ru.enchants.DoncEnchantment;
import ru.enchants.EnchantmentManager;

import java.util.ArrayList;
import java.util.List;

public class GuiHandler implements Listener {

	@EventHandler
	public void invClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack stack = e.getCurrentItem();
		int slot = e.getRawSlot();
		if(e.getInventory().getName().equals(ChatColor.YELLOW + "Настройки")) {
			if(slot == 11) {
				PlayerOptions.toggle(p, PlayerOptions.Option.LongRequirements);
				p.openInventory(generateOptionsInventory(p));
			}
			if(slot == 13) {
				String opt = PlayerOptions.getString(p, PlayerOptions.Option.Flair);
				PlayerOptions.setOption(p, PlayerOptions.Option.Flair, opt.equalsIgnoreCase("on") ? "miner" : (opt.equalsIgnoreCase("miner") ? "off" : "on"));
				p.openInventory(generateOptionsInventory(p));
			}
			if(slot == 15) {
				String opt = PlayerOptions.getString(p, PlayerOptions.Option.MoltenCore);
				PlayerOptions.setOption(p, PlayerOptions.Option.MoltenCore,
						opt.equalsIgnoreCase("on") ? "miner" : (opt.equalsIgnoreCase("miner") ? "off" : "on"));
				p.openInventory(generateOptionsInventory(p));
			}
			if(slot == 6) {
				PlayerOptions.toggle(p, PlayerOptions.Option.MoltenCoreShift);
				p.openInventory(generateOptionsInventory(p));
			}
			if(slot == 23) {
				p.openInventory(CommandPlugin.generatePluginInventory(p));
			}
			if(slot == 21) {
				p.closeInventory();
			}
			e.setCancelled(true);
		}
		if(e.getInventory().getName().startsWith(ChatColor.GREEN + "Ачивки") || e.getInventory().getName().startsWith(ChatColor.RED + "Боссы")) {
			if(stack != null && stack.getType() == Material.WOOD_DOOR) {
				p.openInventory(CommandPlugin.generatePluginInventory(p));
			}
			if(stack != null && stack.getType() == Material.BARRIER) {
				p.closeInventory();
			}
			e.setCancelled(true);
		}
		if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Донцовыщевскии Плагин")) {
			switch(slot) {
			case 3:
				p.openInventory(CommandAch.generateAchievementInventory(p, true));
				break;
			case 4:
				p.openInventory(generateEnchantmentsInventory(p, true));
				break;
			case 5:
				p.openInventory(generateOptionsInventory(p));
				break;
			case 6:
				p.openInventory(CommandBoss.generateBossInventory(p, true));
				break;
			}
			e.setCancelled(true);
		}
		if(e.getInventory().getName().equalsIgnoreCase(ChatColor.AQUA + "Чары")) {
			if(slot == 44 && stack.getType() != Material.AIR) {
				p.openInventory(CommandPlugin.generatePluginInventory(p));
			}
			if(slot == 36) {
				p.closeInventory();
			}
			e.setCancelled(true);
		}
	}

	public static ItemStack getCloseItem() {
		return InventoryHelper.generateItemWithName(Material.BARRIER, ChatColor.RED + "Закрыть");
	}

	public static ItemStack getBackItem() {
		return InventoryHelper.generateItemWithName(Material.WOOD_DOOR, ChatColor.GREEN + "Назад");
	}

	public static Inventory generateEnchantmentsInventory(Player p, boolean plugin) {
		Inventory inv = Bukkit.createInventory(p, 45, ChatColor.AQUA + "Чары");
		DoncEnchantment[] ench = {EnchantmentManager.STEP, EnchantmentManager.FORCE, EnchantmentManager.GATHERING, EnchantmentManager.CORE,
				EnchantmentManager.FLAIR, EnchantmentManager.GREED, EnchantmentManager.TIMBER, EnchantmentManager.MULTISHOT, EnchantmentManager.SOULBIND,
				EnchantmentManager.EVASION};
		int[] slots = {3, 5, 11, 15, 29, 33, 39, 41, 22, 13};
		for(int i = 0; i < ench.length; i++) {
			List<String> lore = new ArrayList<String>();
			lore.addAll(InventoryHelper.splitLongString(ChatColor.YELLOW + "Описание: " + ChatColor.AQUA + ench[i].getDesc(), 30, ChatColor.AQUA));
			if(ench[i].getMaxLevel() > 1) lore.addAll(
					InventoryHelper.splitLongString(ChatColor.YELLOW + "При повышении уровня: " + ChatColor.AQUA + ench[i].getOnLevelUp(), 30, ChatColor.AQUA));
			lore.add(ChatColor.YELLOW + "Макс. уровень: " + ChatColor.AQUA + EnchantmentManager.getRomanNumber(ench[i].getMaxLevel()));
			if(ench[i].isTreasure()) {
				lore.add(ChatColor.GOLD + "Не выпадает в чарке");
			}
			inv.setItem(slots[i], InventoryHelper.generateGlowingItemWithNameAndLore(ench[i].getItemToShow(), ChatColor.GREEN + ench[i].getName(),
					lore.toArray(new String[0])));
		}
		inv.setItem(36, getCloseItem());
		if(plugin) inv.setItem(44, getBackItem());
		return inv;
	}

	public static Inventory generateOptionsInventory(Player p) {
		Inventory inv = Bukkit.createInventory(p, 27, ChatColor.YELLOW + "Настройки");

		boolean death = PlayerOptions.isActive(p, PlayerOptions.Option.LongRequirements);
		ItemStack deathItem = new ItemStack(Material.SIGN);
		ItemMeta deathMeta = deathItem.getItemMeta();
		if(death) {
			deathMeta.addEnchant(Enchantment.OXYGEN, 1, true);
			deathMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		deathMeta.setDisplayName(ChatColor.YELLOW + "Длинные требования: " + (death ? ChatColor.GREEN + "включены" : ChatColor.RED + "выключены"));
		List<String> deathLore = new ArrayList<String>();
		String deathStr = "Если включены, то у всех ачивках с прогрессом будут показываться все требования, даже если их список очень длинный.";
		for(String s : InventoryHelper.splitLongString(deathStr, 30)) {
			deathLore.add(ChatColor.GOLD + s);
		}
		deathMeta.setLore(deathLore);
		deathItem.setItemMeta(deathMeta);

		String flair = PlayerOptions.getString(p, PlayerOptions.Option.Flair);
		boolean miner = flair.equalsIgnoreCase("miner");
		boolean on = miner || flair.equalsIgnoreCase("on");
		String fName = ChatColor.YELLOW + "Flair: "
				+ (miner ? ChatColor.GOLD + "режим шахтера" : (on ? ChatColor.GREEN + "включен" : ChatColor.RED + "выключен"));
		ItemStack fItem = InventoryHelper.generateItemWithName(Material.DIAMOND_HELMET, fName, on);
		if(miner) InventoryHelper.addSplittedLore(fItem, 30, ChatColor.GOLD + "Работает только на высоте менее 48 блоков.", ChatColor.GOLD);

		String core = PlayerOptions.getString(p, PlayerOptions.Option.MoltenCore);
		boolean shift = PlayerOptions.isActive(p, PlayerOptions.Option.MoltenCoreShift);
		boolean coreMiner = core.equalsIgnoreCase("miner");
		boolean coreOn = coreMiner || core.equalsIgnoreCase("on");
		ItemStack coreItem = InventoryHelper.generateItemWithName(coreMiner ? Material.GOLD_PICKAXE : (coreOn ? Material.BLAZE_POWDER : Material.MAGMA_CREAM),
				ChatColor.YELLOW + "Molten Core: "
						+ (coreMiner ? ChatColor.GOLD + "режим шахтера" : (coreOn ? ChatColor.GREEN + "включен" : ChatColor.RED + "выключен")),
				coreOn);
		if(coreMiner) InventoryHelper.addSplittedLore(coreItem, 30,
				ChatColor.GOLD + "Работает только на высоте менее 48 блоков, а также на руды на любой высоте.", ChatColor.GOLD);
		ItemStack shiftItem = InventoryHelper.generateItemWithName(Material.FEATHER,
				ChatColor.YELLOW + "Shift Lock: " + (shift ? ChatColor.GREEN + "включен" : ChatColor.RED + "выключен"), shift);
		InventoryHelper.addSplittedLore(shiftItem, 30,
				ChatColor.GOLD + "Если включен, то Molten Core будет работать только тогда, когда ты стоишь на шифте. При том игнорируется режим шахтера.",
				ChatColor.GOLD);

		inv.setItem(11, deathItem);
		inv.setItem(13, fItem);
		inv.setItem(15, coreItem);
		inv.setItem(6, shiftItem);
		inv.setItem(23, getBackItem());
		inv.setItem(21, getCloseItem());
		return inv;
	}

}
