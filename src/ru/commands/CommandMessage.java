package ru.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.util.MathUtils;
import ru.util.PlayerOptions;
import ru.util.PlayerOptions.Option;

import java.util.ArrayList;
import java.util.List;

public class CommandMessage implements CommandExecutor, TabCompleter {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(args.length > 1) {
			if(args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("quit")) {
				boolean join = args[0].equalsIgnoreCase("join");
				if(args[1].equalsIgnoreCase("show")) {
					String mes = PlayerOptions.getMessage(p, join);
					if(mes.equals("null")) {
						p.sendMessage(ChatColor.GOLD + "Сообщение еще не установлено!");
					} else {
						p.sendMessage(ChatColor.GREEN + "Твое сообщение: " + ChatColor.RESET + mes);
					}
					return true;
				}
				if(args[1].equalsIgnoreCase("remove")) {
					String mes = PlayerOptions.getMessage(p, join);
					if(mes.equals("null")) {
						p.sendMessage(ChatColor.GOLD + "Сообщение еще не установлено!");
					} else {
						PlayerOptions.setOption(p, join ? Option.JoinMes : Option.QuitMes, "null");
						p.sendMessage(ChatColor.GREEN + "Сообщение удалено.");
					}
					return true;
				}
				if(args.length > 2 && args[1].equalsIgnoreCase("set")) {
					String str = "";
					for(int i = 2; i < args.length; i++) {
						str += args[i] + " ";
					}
					str = str.trim();
					PlayerOptions.setMessage(p, str, join);
					String mes = PlayerOptions.getMessage(p, join);
					p.sendMessage(ChatColor.GREEN + "Сообщение установлено: " + ChatColor.RESET + mes);
					return true;
				}
			}
		}
		return false;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length == 1) {
			return MathUtils.getListOfStringsMatchingLastWord(args, "join", "quit");
		}
		if(args.length == 2) {
			return MathUtils.getListOfStringsMatchingLastWord(args, "set", "remove", "show");
		}
		if(args.length == 3) {
			ArrayList<String> c = new ArrayList<String>();
			for(ChatColor cc : ChatColor.values()) {
				c.add(cc.name());
			}
			return MathUtils.getListOfStringsMatchingLastWord(args, c);
		}
		return null;
	}

}
