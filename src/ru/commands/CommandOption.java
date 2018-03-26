package ru.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.main.HardcorePlugin;
import ru.util.MathUtils;
import ru.util.PlayerOptions;

import java.util.ArrayList;
import java.util.List;

public class CommandOption implements CommandExecutor, TabCompleter {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(p.isOp() && HardcorePlugin.TEST && args.length >= 1) {
			PlayerOptions.Option opt = PlayerOptions.Option.valueOf(args[0]);
			if(args.length >= 2) {
				try {
					PlayerOptions.setOption(p, opt, Boolean.valueOf(args[1]));
					return true;
				} catch(Exception e) {
				}
				try {
					PlayerOptions.setOption(p, opt, Integer.valueOf(args[1]));
					return true;
				} catch(Exception e) {
				}
				PlayerOptions.setOption(p, opt, args[1]);
			} else {
				p.sendMessage(ChatColor.YELLOW + opt.name() + ": " + PlayerOptions.getOption(p, opt));
			}
		}
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(sender.isOp()) {
			if(args.length == 1) {
				List<String> names = new ArrayList<String>();
				for(PlayerOptions.Option opt : PlayerOptions.Option.values()) {
					names.add(opt.name());
				}
				return MathUtils.getListOfStringsMatchingLastWord(args, names);
			}
		}
		return null;
	}

}
