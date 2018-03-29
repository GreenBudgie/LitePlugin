package ru.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.util.InventoryHelper;

public class CommandPlugin implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		p.openInventory(generatePluginInventory(p));
		return true;
	}

	public static Inventory generatePluginInventory(Player p) {
		Inventory inv = Bukkit.createInventory(p, 9, ChatColor.YELLOW + "Донцовыщевскии Плагин");

		ItemStack ach = InventoryHelper.generateItemWithName(Material.TOTEM, ChatColor.GOLD + "Ачивки");
		ItemStack ench = InventoryHelper.generateItemWithNameAndLore(Material.DIAMOND_SWORD, ChatColor.AQUA + "Чары", ChatColor.GRAY + "Greed III");
		InventoryHelper.setItemGlowing(ench);
		ItemStack opt = InventoryHelper.generateItemWithName(Material.COMMAND, ChatColor.YELLOW + "Настройки");
		ItemStack boss = InventoryHelper.generateItemWithName(Material.MONSTER_EGG, ChatColor.RED + "Боссы");

		inv.setItem(3, ach);
		inv.setItem(4, ench);
		inv.setItem(5, opt);
		inv.setItem(6, boss);

		return inv;
	}

}
