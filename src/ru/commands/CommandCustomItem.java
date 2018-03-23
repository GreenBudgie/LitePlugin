package ru.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.items.CustomItem;
import ru.items.CustomItems;
import ru.main.HardcorePlugin;
import ru.util.InventoryHelper;
import ru.util.MathUtils;

public class CommandCustomItem implements CommandExecutor, TabCompleter {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(p.isOp() && HardcorePlugin.TEST) {
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("give")) {
					CustomItem item = CustomItems.getByName(args[1]);
					if(item != null) {
						ItemStack stack = item.getItemStack();
						p.sendMessage(ChatColor.GREEN + "����� " + item.getName());
						p.getInventory().addItem(stack);
					} else {
						p.sendMessage(ChatColor.RED + "������ �������� �� ����������.");
					}
					return true;
				}
			}
			if(args.length == 3) {
				CustomItem item = null;
				ItemStack stack = null;
				if(CustomItems.isCustomItem(p.getInventory().getItemInMainHand())) {
					stack = p.getInventory().getItemInMainHand();
					item = CustomItems.getCustomItem(stack);
				} else {
					if(CustomItems.isCustomItem(p.getInventory().getItemInOffHand())) {
						stack = p.getInventory().getItemInOffHand();
						item = CustomItems.getCustomItem(stack);
					}
				}
				if(item != null && stack != null) {
					if(InventoryHelper.hasValue(stack, args[1])) {
						if(InventoryHelper.changeValue(stack, args[1], args[2])) {
							p.sendMessage(ChatColor.YELLOW + "����������� �������� " + ChatColor.GOLD + args[2] + ChatColor.YELLOW + " ��� " + ChatColor.GOLD
									+ args[1]);
						} else {
							p.sendMessage(ChatColor.DARK_RED + "����������� ������");
						}
					} else {
						p.sendMessage(ChatColor.RED + "������� �� ����� ��������� " + ChatColor.YELLOW + args[1]);
					}
				} else {
					p.sendMessage(ChatColor.RED + "����� ������� ������� � ����");
				}
			}
		}
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length == 1) {
			return MathUtils.getListOfStringsMatchingLastWord(args, "give", "value");
		}
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("give")) {
				List<String> list = new ArrayList<String>();
				for(String s : CustomItems.getAllNames()) {
					list.add(ChatColor.stripColor(s).replaceAll(" ", "_"));
				}
				return MathUtils.getListOfStringsMatchingLastWord(args, list);
			}
			if(args[0].equalsIgnoreCase("value")) {
				Player p = (Player) sender;
				CustomItem item = null;
				ItemStack stack = null;
				if(CustomItems.isCustomItem(p.getInventory().getItemInMainHand())) {
					stack = p.getInventory().getItemInMainHand();
					item = CustomItems.getCustomItem(stack);
				} else {
					if(CustomItems.isCustomItem(p.getInventory().getItemInOffHand())) {
						stack = p.getInventory().getItemInOffHand();
						item = CustomItems.getCustomItem(stack);
					}
				}
				if(item != null && stack != null) {
					return MathUtils.getListOfStringsMatchingLastWord(args, InventoryHelper.getValues(stack).keySet());
				}
			}
		}
		return null;
	}

}
