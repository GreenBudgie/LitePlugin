package ru.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import ru.achievements.AchievementManager;
import ru.achievements.DoncAchievement;
import ru.main.HardcorePlugin;
import ru.util.MathUtils;

public class CommandSetAchievement implements CommandExecutor, TabCompleter {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(p.isOp() && HardcorePlugin.TEST) {
			if(args.length > 1) {
				DoncAchievement ach = AchievementManager.getByName(args[0]);
				if(ach != null) {
					boolean val = Boolean.valueOf(args[1]);
					if(val) {
						ach.complete(p);
					} else {
						ach.decomplete(p);
					}
				} else if(args[0].equalsIgnoreCase("all")) {
					boolean val = Boolean.valueOf(args[1]);
					if(val) {
						for(DoncAchievement a : AchievementManager.achievements) {
							a.complete(p);
						}
					} else {
						for(DoncAchievement a : AchievementManager.achievements) {
							a.decomplete(p);
						}
					}
				}
			}
			if(args.length == 1) {
				DoncAchievement ach = AchievementManager.getByName(args[0]);
				if(ach != null) {
					ach.decomplete(p);
					ach.complete(p);
				}
			}
		}
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length == 1) {
			List<String> names = new ArrayList<String>();
			for(DoncAchievement ach : AchievementManager.achievements) {
				names.add(ach.getClass().getSimpleName());
			}
			return MathUtils.getListOfStringsMatchingLastWord(args, names);
		}
		if(args.length == 2) {
			return MathUtils.getListOfStringsMatchingLastWord(args, "true", "false");
		}
		return null;
	}

}
