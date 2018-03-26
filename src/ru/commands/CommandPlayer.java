package ru.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import ru.util.*;

import java.util.ArrayList;
import java.util.List;

public class CommandPlayer implements CommandExecutor, TabCompleter {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()) {
			ChatColor y = ChatColor.YELLOW;
			if(args.length >= 9 && args[0].equalsIgnoreCase("add")) {
				try {
					Cases cases = new Cases(args[3], args[4], args[5], args[6], args[7], args[8]);
					if(Names.addPlayers(new DoncPlayer(args[1], cases, ChatColor.valueOf(args[2].toUpperCase())))) {
						sender.sendMessage(y + "Игрок успешно добавлен.");
					} else {
						sender.sendMessage(ChatColor.RED + "Игрок уже существует.");
					}
				} catch(Exception e) {
					sender.sendMessage(ChatColor.RED + "Неверные аргументы. Игрок не был добавлен.");
				}
				return true;
			}
			if(args.length == 2 && args[0].equalsIgnoreCase("remove")) {
				if(Names.removePlayer(args[1])) {
					sender.sendMessage(y + "Игрок удален.");
				} else {
					sender.sendMessage(ChatColor.RED + "Игрока не существует.");
				}
				return true;
			}
			if(args.length == 8 && args[0].equalsIgnoreCase("cases")) {
				Cases cases = new Cases(args[2], args[3], args[4], args[5], args[6], args[7]);
				if(Names.changeCases(args[1], cases)) {
					sender.sendMessage(ChatColor.YELLOW + "Падежи изменены.");
				} else {
					sender.sendMessage(ChatColor.RED + "Игрока не существует.");
				}
				return true;
			}
			if(args.length == 3 && args[0].equalsIgnoreCase("color")) {
				try {
					ChatColor color = ChatColor.valueOf(args[2]);
					Names.changeColor(args[1], color);
					sender.sendMessage(ChatColor.YELLOW + "Установлен цвет: " + color + color.name());
				} catch(Exception e) {
					sender.sendMessage(ChatColor.RED + "Неверный цвет");
				}
				return true;
			}
			if(args.length == 3 && args[0].equalsIgnoreCase("vip")) {
				try {
					boolean vip = Boolean.valueOf(args[2]);
					Names.setVip(args[1], vip);
					sender.sendMessage(ChatColor.YELLOW + "Установлен vip-статус: " + vip);
				} catch(Exception e) {
					sender.sendMessage(ChatColor.RED + "Неверный параметр");
				}
				return true;
			}
			if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
				for(DoncPlayer p : Names.getAllPlayers()) {
					sender.sendMessage(ChatColor.YELLOW + p.getServerName() + ": " + p.getNameColor() + p.getDoncName());
				}
				return true;
			}
			if(args.length == 1 && args[0].equalsIgnoreCase("default")) {
				Names.defaultValues();
				sender.sendMessage(y + "Добавлены значения по умолчанию.");
				return true;
			}
			return false;
		}
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(sender.isOp()) {
			if(args.length == 1) {
				return MathUtils.getListOfStringsMatchingLastWord(args, "add", "remove", "color", "cases", "list", "default", "vip");
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("cases") || args[0].equalsIgnoreCase("color")
						|| args[0].equalsIgnoreCase("vip")) {
					return MathUtils.getListOfStringsMatchingLastWord(args, Names.getAllServerNames());
				}
			}
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("color")) {
					ArrayList<String> c = new ArrayList<String>();
					for(ChatColor cc : ChatColor.values()) {
						c.add(cc.name());
					}
					return MathUtils.getListOfStringsMatchingLastWord(args, c);
				}
				if(args[0].equalsIgnoreCase("vip")) {
					return MathUtils.getListOfStringsMatchingLastWord(args, "true", "false");
				}
			}
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("add")) {
					ArrayList<String> c = new ArrayList<String>();
					for(ChatColor cc : ChatColor.values()) {
						c.add(cc.name());
					}
					return MathUtils.getListOfStringsMatchingLastWord(args, c);
				}
			}
			if(args.length >= 3 && args.length < 9) {
				if(args[0].equalsIgnoreCase("cases")) {
					return MathUtils.getListOfStringsMatchingLastWord(args, ChatColor.stripColor(Names.formatName(args[1], Case.byOrdinal(args.length - 3))));
				}
			}
			if(args.length >= 4) {
				if(args[0].equalsIgnoreCase("add")) {
					return MathUtils.getListOfStringsMatchingLastWord(args, args[3]);
				}
			}
		}
		return null;
	}

}
