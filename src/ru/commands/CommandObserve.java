package ru.commands;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.main.HardcorePlugin;
import ru.util.*;

import java.util.ArrayList;
import java.util.List;

public class CommandObserve implements CommandExecutor, TabCompleter {

	private HardcorePlugin plugin = HardcorePlugin.instance;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ChatColor y = ChatColor.YELLOW;
		Player p = (Player) sender;
		Location l = p.getLocation();
		if(args[0].equalsIgnoreCase("stop")) {
			if(!plugin.stopObserving(p)) {
				p.sendMessage(ChatColor.GOLD + "�� � ��� �� ���������� �� ������������.");
				return true;
			}
			p.sendMessage(y + "���������� ������.");
			return true;
		}
		if(args.length >= 1) {
			if(args[0].equalsIgnoreCase("bind")) {
				boolean bind = PlayerOptions.isActive(p, PlayerOptions.Option.CompassBind);
				if(bind) {
					p.sendMessage(ChatColor.GOLD + "���� ������ � ��� ���������� �� ������������� �������.");
				} else {
					PlayerOptions.setOption(p, PlayerOptions.Option.CompassBind, true);
					p.sendMessage(ChatColor.YELLOW + "������ ���� ������ ���������� �� ������������� �������.");
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("unbind")) {
				boolean bind = PlayerOptions.isActive(p, PlayerOptions.Option.CompassBind);
				if(!bind) {
					p.sendMessage(ChatColor.GOLD + "���� ������ � ��� �� ���������� �� ������������� �������.");
				} else {
					PlayerOptions.setOption(p, PlayerOptions.Option.CompassBind, false);
					p.sendMessage(ChatColor.YELLOW + "������ ���� ������ �� ���������� �� ������������� �������.");
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("status")) {
				boolean bind = PlayerOptions.isActive(p, PlayerOptions.Option.CompassBind);
				if(bind) {
					p.sendMessage(ChatColor.GREEN + "���� ������ ���������� �� ������������� �������.");
				} else {
					p.sendMessage(ChatColor.RED + "���� ������ �� ���������� �� ������������� �������.");
				}
				return true;
			}
			if(args.length == 1) {
				Player pl = Bukkit.getPlayer(args[0]);
				if(pl != null) {
					if(plugin.canSeePosition(p, pl)) {
						plugin.startObservingPlayer(p, pl);
						p.sendMessage(
								ChatColor.GOLD + "������ �� ���������� �� �������� " + Names.formatName(pl.getName(), Case.GENITIVE) + ChatColor.GOLD + ".");
					} else {
						p.sendMessage(ChatColor.RED + "���� ������ �������� ������� " + Names.formatName(pl.getName(), Case.GENITIVE) + ChatColor.RED + ".");
					}
					return true;
				}
				p.sendMessage(ChatColor.RED + "����� " + ChatColor.GOLD + args[0] + ChatColor.RED + " �� ����������.");
				return true;
			}
			if(args.length >= 2) {
				if(args[0].equalsIgnoreCase("mark")) {
					plugin.startObservingMark(p, args[1]);
					return true;
				}
				try {
					int xc = Integer.valueOf(args[0]);
					int yc = args.length >= 5 ? Integer.valueOf(args[1]) : p.getLocation().getBlockY();
					int zc = Integer.valueOf(args[args.length >= 3 ? 2 : 1]);
					plugin.startObservingCoords(p, xc, yc, zc);
					String pos = String.valueOf(xc) + " " + String.valueOf(yc) + " " + String.valueOf(zc);
					p.sendMessage(ChatColor.GOLD + "������ ���� �������� ���������� " + ChatColor.GREEN + pos + ChatColor.GOLD + ".");
				} catch(NumberFormatException e) {
					p.sendMessage(ChatColor.RED + "���������� ������� �������. �� ����");
				}
				return true;
			}
		}
		return false;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(!(sender instanceof Player)) return null;
		Player p = (Player) sender;
		if(args.length == 1) {
			List<String> names = new ArrayList<String>();
			names.addAll(WorldHelper.getAllPlayerNames());
			names.addAll(Lists.<String>newArrayList("bind", "unbind", "mark", "stop", "status"));
			return MathUtils.getListOfStringsMatchingLastWord(args, names);
		}
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("mark")) {
				return MathUtils.getListOfStringsMatchingLastWord(args, Marks.getVisibleMarks(p));
			}
		}
		return null;
	}

}
