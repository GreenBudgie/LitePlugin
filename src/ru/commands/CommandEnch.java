package ru.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.util.GuiHandler;

public class CommandEnch implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		p.openInventory(GuiHandler.generateEnchantmentsInventory(p, false));
		return true;
	}
	
}
