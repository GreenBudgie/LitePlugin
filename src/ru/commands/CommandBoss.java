package ru.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

import ru.bosses.Boss;
import ru.bosses.BossManager;
import ru.util.GuiHandler;
import ru.util.InventoryHelper;

public class CommandBoss implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		p.openInventory(generateBossInventory(p, false));
		return true;
	}

	public static Inventory generateBossInventory(Player p, boolean fromPlugin) {
		Inventory inv = Bukkit.createInventory(p, 27, ChatColor.RED + "Боссы");
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(Boss boss : BossManager.bosses) {
			ItemStack item = new ItemStack(Material.MONSTER_EGG);
			SpawnEggMeta meta = (SpawnEggMeta) item.getItemMeta();
			meta.setSpawnedType(boss.getEntity());
			meta.setDisplayName(boss.getName());
			item.setItemMeta(meta);
			InventoryHelper.addLore(item, ChatColor.YELLOW + boss.getEntity().name());
			InventoryHelper.addSplittedLore(item, 30, ChatColor.GOLD + boss.getDescription(), ChatColor.GOLD);
			InventoryHelper.addLore(item, ChatColor.YELLOW + "Хп: " + ChatColor.LIGHT_PURPLE + boss.getHP());
			InventoryHelper.addLore(item,
					ChatColor.YELLOW + "Опыт: " + ChatColor.GREEN + boss.getMinXp() + ChatColor.GOLD + "-" + ChatColor.GREEN + boss.getMaxXp());
			items.add(item);
		}
		InventoryHelper.placeItemsCenter(inv, items, 1);
		if(fromPlugin) {
			inv.setItem(21, GuiHandler.getCloseItem());
			inv.setItem(23, GuiHandler.getBackItem());
		}
		return inv;
	}

}
