package ru.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.util.PlayerOptions;

public class CommandDanchmode implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		PlayerOptions.toggle(p, PlayerOptions.Option.Danchmode);
		if(PlayerOptions.isActive(p, PlayerOptions.Option.Danchmode)) {
			p.sendMessage(ChatColor.GREEN + "дюмв лнд емюакед");
		} else {
			p.sendMessage(ChatColor.GOLD + "дюмв лнд дхяюакед");
		}
		return true;
	}

}
