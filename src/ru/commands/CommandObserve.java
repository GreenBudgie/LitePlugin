package ru.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.main.HardcorePlugin;

public class CommandObserve implements CommandExecutor {

	private HardcorePlugin plugin = HardcorePlugin.instance;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(ChatColor.RED + "Так, пошел нахуй. Эту команду признали имбой и теперь ее нельзя юзать.");
		return true;
	}

}
