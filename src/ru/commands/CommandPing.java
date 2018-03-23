package ru.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import ru.util.Case;
import ru.util.Names;

public class CommandPing implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(args.length == 0) {
			p.sendMessage(ChatColor.YELLOW + "Твой пинг: " + ChatColor.GREEN + getPing(p));
		}
		if(args.length >= 1) {
			Player p2 = Bukkit.getPlayer(args[0]);
			if(p2 != null) {
				p.sendMessage(
						ChatColor.YELLOW + "Пинг " + Names.formatName(p2.getName(), Case.GENITIVE) + ChatColor.YELLOW + ": " + ChatColor.GREEN + getPing(p2));
			} else {
				if(Names.getAllServerNames().contains(args[0])) {
					p.sendMessage(Names.formatName(args[0]) + ChatColor.RED + " не на серве.");
				} else {
					p.sendMessage(ChatColor.RED + "Игрока " + ChatColor.GOLD + args[0] + ChatColor.RED + " не существует.");
				}
			}
		}
		return true;
	}

	public int getPing(Player p) {
		int ping = p == null ? -1 : ((CraftPlayer) p).getHandle().ping;
		return ping;
	}

}
