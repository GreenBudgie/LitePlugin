package ru.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import ru.main.HardcorePlugin;
import ru.util.Case;
import ru.util.Marks;
import ru.util.MathUtils;
import ru.util.Names;
import ru.util.WorldHelper;

public class CommandCoords implements CommandExecutor, TabCompleter {

	private HardcorePlugin plugin = HardcorePlugin.instance;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ChatColor y = ChatColor.YELLOW;
		Player p = (Player) sender;
		Location l = p.getLocation();
		if(args.length == 0) {
			Bukkit.broadcastMessage(y + "Корды " + Names.formatName(p.getName(), Case.GENITIVE) + y + ":");
			Bukkit.broadcastMessage(y + " X: " + ChatColor.RED + l.getBlockX());
			Bukkit.broadcastMessage(y + " Y: " + ChatColor.GREEN + l.getBlockY());
			Bukkit.broadcastMessage(y + " Z: " + ChatColor.BLUE + l.getBlockZ());
			for(Player pl : WorldHelper.playersNotmeAnyDimension(p)) {
				if(WorldHelper.isInAnotherDimension(p, pl)) {
					pl.sendMessage(ChatColor.GOLD + "Невозможно отобразить дистанцию, так как " + Names.formatName(p.getName()) + " "
							+ WorldHelper.getDimensionNamePrepositional(WorldHelper.getPlayerDimension(p), ChatColor.GOLD));
				} else {
					pl.sendMessage(y + " Дистанция: " + ChatColor.AQUA + (int) l.distance(pl.getLocation()));
				}
			}
			return true;
		}
		if(args.length >= 1) {
			if(args[0].equalsIgnoreCase("alt")) {
				if(args.length == 1) {
					WorldHelper.EnumDimension dim = WorldHelper.getPlayerDimension(p);
					Location loc = p.getLocation();
					if(dim != WorldHelper.EnumDimension.END) {
						if(dim == WorldHelper.EnumDimension.OVERWORLD) {
							p.sendMessage(ChatColor.GREEN + "Координаты на Земле: " + WorldHelper.getColoredLocationCoordinates(loc));
							loc.setX(loc.getX() / 8);
							loc.setZ(loc.getZ() / 8);
							p.sendMessage(ChatColor.RED + "Координаты в Аду: " + WorldHelper.getColoredLocationCoordinates(loc));
						} else {
							p.sendMessage(ChatColor.RED + "Координаты в Аду: " + WorldHelper.getColoredLocationCoordinates(loc));
							loc.setX(loc.getX() * 8);
							loc.setZ(loc.getZ() * 8);
							p.sendMessage(ChatColor.GREEN + "Координаты на Земле: " + WorldHelper.getColoredLocationCoordinates(loc));
						}
					} else {
						p.sendMessage(ChatColor.GOLD + "Ты находишься в энде, вывести координаты противоположного мира невозможно.");
					}
					return true;
				}
				if(args.length >= 3) {
					if(args[1].equalsIgnoreCase("player")) {
						Player pl = Bukkit.getPlayer(args[2]);
						if(pl != null) {
							String name = Names.formatName(pl.getName());
							String name2 = Names.formatName(pl.getName(), Case.GENITIVE);
							if(HardcorePlugin.instance.canSeePosition(p, pl)) {
								WorldHelper.EnumDimension dim = WorldHelper.getPlayerDimension(pl);
								Location loc = pl.getLocation();
								if(dim != WorldHelper.EnumDimension.END) {
									if(dim == WorldHelper.EnumDimension.OVERWORLD) {
										p.sendMessage(ChatColor.GREEN + "Координаты " + name2 + ChatColor.GREEN + " на Земле: "
												+ WorldHelper.getColoredLocationCoordinates(loc));
										loc.setX(loc.getX() / 8);
										loc.setZ(loc.getZ() / 8);
										p.sendMessage(ChatColor.RED + "Координаты в Аду: " + WorldHelper.getColoredLocationCoordinates(loc));
									} else {
										p.sendMessage(ChatColor.RED + "Координаты " + name2 + ChatColor.RED + " в Аду: "
												+ WorldHelper.getColoredLocationCoordinates(loc));
										loc.setX(loc.getX() * 8);
										loc.setZ(loc.getZ() * 8);
										p.sendMessage(ChatColor.GREEN + "Координаты на Земле: " + WorldHelper.getColoredLocationCoordinates(loc));
									}
								} else {
									p.sendMessage(name + ChatColor.RED + " в энде, вывести координаты противоположного мира невозможно.");
								}
							} else {
								p.sendMessage(ChatColor.RED + "Ты не можешь видеть позицию " + name2 + ChatColor.RED + ".");
							}
						} else {
							if(Names.getAllServerNames().contains(args[2])) {
								p.sendMessage(Names.formatName(args[2], Case.GENITIVE) + ChatColor.RED + " нет на серве.");
							} else {
								p.sendMessage(ChatColor.RED + "Игрока " + ChatColor.GOLD + args[2] + ChatColor.RED + " не существует.");
							}
						}
						return true;
					}
					if(args[1].equalsIgnoreCase("coords") && args.length >= 4) {
						Location loc = WorldHelper.translateToLocation(p.getWorld(), args[2] + " " + args[3] + (args.length >= 5 ? " " + args[4] : ""),
								p.getLocation().getBlockY());
						if(loc != null) {
							WorldHelper.EnumDimension dim = WorldHelper.getPlayerDimension(p);
							if(dim != WorldHelper.EnumDimension.END) {
								if(dim == WorldHelper.EnumDimension.OVERWORLD) {
									p.sendMessage(ChatColor.GREEN + "Координаты на Земле: " + WorldHelper.getColoredLocationCoordinates(loc));
									loc.setX(loc.getX() / 8);
									loc.setZ(loc.getZ() / 8);
									p.sendMessage(ChatColor.RED + "Координаты в Аду: " + WorldHelper.getColoredLocationCoordinates(loc));
								} else {
									p.sendMessage(ChatColor.RED + "Координаты в Аду: " + WorldHelper.getColoredLocationCoordinates(loc));
									loc.setX(loc.getX() * 8);
									loc.setZ(loc.getZ() * 8);
									p.sendMessage(ChatColor.GREEN + "Координаты на Земле: " + WorldHelper.getColoredLocationCoordinates(loc));
								}
							} else {
								p.sendMessage(ChatColor.GOLD + "Ты находишься в энде, вывести координаты противоположного мира невозможно.");
							}
						} else {
							p.sendMessage(ChatColor.RED + "Координаты введены неверно.");
						}
						return true;
					}
					if(args[1].equalsIgnoreCase("mark")) {
						if(Marks.visibleMarkExists(p, args[2])) {
							WorldHelper.EnumDimension dim = WorldHelper.getDimensionByBiome(Marks.getMarkBiome(args[2]));
							Location loc = Marks.getMarkLocation(p.getWorld(), args[2]);
							if(dim == WorldHelper.EnumDimension.OVERWORLD) {
								p.sendMessage(ChatColor.GREEN + "Координаты метки " + Marks.getMarkFullName(args[2]) + ChatColor.GREEN + " на Земле: "
										+ WorldHelper.getColoredLocationCoordinates(loc));
								loc.setX(loc.getX() / 8);
								loc.setZ(loc.getZ() / 8);
								p.sendMessage(ChatColor.RED + "Координаты в Аду: " + WorldHelper.getColoredLocationCoordinates(loc));
							} else {
								if(dim == WorldHelper.EnumDimension.HELL) {
									p.sendMessage(ChatColor.RED + "Координаты метки " + Marks.getMarkFullName(args[2]) + ChatColor.RED + " в Аду: "
											+ WorldHelper.getColoredLocationCoordinates(loc));
									loc.setX(loc.getX() * 8);
									loc.setZ(loc.getZ() * 8);
									p.sendMessage(ChatColor.GREEN + "Координаты на Земле: " + WorldHelper.getColoredLocationCoordinates(loc));
								} else {
									p.sendMessage(ChatColor.RED + "Метка находится в энде, вывести координаты противоположного мира невозможно.");
								}
							}
						} else {
							p.sendMessage(ChatColor.RED + "Метки " + ChatColor.GOLD + args[2] + ChatColor.RED + " не существует.");
						}
						return true;
					}
				}
			}
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("public") || args[0].equalsIgnoreCase("protected")) {
				boolean publ = args[0].equalsIgnoreCase("public");
				String str = plugin.isPublicPosPerm(p) ? "публичная" : "защищенная";
				if(!plugin.setPosPermPublic(p, publ)) {
					p.sendMessage(ChatColor.RED + "Донц, твоя позиция и так " + ChatColor.GOLD + str + ChatColor.RED + ".");
					return true;
				}
				str = plugin.isPublicPosPerm(p) ? "публичная" : "защищенная";
				p.sendMessage(y + "Теперь твоя позиция " + ChatColor.GREEN + str + y + ".");
				return true;
			}
			if(args[0].equalsIgnoreCase("status")) {
				boolean publ = plugin.isPublicPosPerm(p);
				if(publ) {
					p.sendMessage(y + "Твою позицию может посмотреть любой донц.");
					return true;
				}
				if(plugin.getPosPermWhitelist(p).isEmpty()) {
					p.sendMessage(ChatColor.GOLD + "Никто не видит твою позицию, скрытное ты гавно.");
					return true;
				}
				String names = "";
				for(String s : plugin.getPosPermWhitelist(p)) {
					names += Names.formatName(s) + y + ", ";
				}
				names = names.substring(0, names.length() - 2) + y + ".";
				if(plugin.getPosPermWhitelist(p).size() == 1) {
					p.sendMessage(y + "Твою позицию может видеть только " + names);
				} else {
					p.sendMessage(y + "Твою позицию могут видеть " + names);
				}
				return true;
			}
			Player pl = Bukkit.getPlayer(args[0]);
			if(pl != null) {
				if(plugin.canSeePosition(p, pl)) {
					Location loc = pl.getLocation();
					p.sendMessage(y + "Корды " + Names.formatName(pl.getName(), Case.GENITIVE) + y + ":");
					p.sendMessage(y + " X: " + ChatColor.RED + loc.getBlockX());
					p.sendMessage(y + " Y: " + ChatColor.GREEN + loc.getBlockY());
					p.sendMessage(y + " Z: " + ChatColor.BLUE + loc.getBlockZ());
					if(WorldHelper.isInAnotherDimension(p, pl)) {
						p.sendMessage(ChatColor.GOLD + "Невозможно отобразить дистанцию, так как " + Names.formatName(pl.getName()) + " "
								+ WorldHelper.getDimensionNamePrepositional(WorldHelper.getPlayerDimension(pl), ChatColor.GOLD));
					} else {
						p.sendMessage(y + " Дистанция: " + ChatColor.AQUA + (int) l.distance(loc));
					}
				} else {
					p.sendMessage(ChatColor.RED + "Тебе нельзя смотреть позицию " + Names.formatName(pl.getName(), Case.GENITIVE) + ChatColor.RED + ".");
				}
				return true;
			}
			p.sendMessage(ChatColor.RED + "Донца " + ChatColor.GOLD + args[0] + ChatColor.RED + " не существует.");
			return true;
		}
		if(args.length >= 2) {
			if(args[0].equalsIgnoreCase("friend")) {
				if(args[1].equalsIgnoreCase("add") && args.length == 3) {
					if(plugin.addToPosPermWhitelist(p, args[2])) {
						p.sendMessage(Names.formatName(args[2]) + y + " теперь может смотреть твою позицию.");
					} else {
						p.sendMessage(Names.formatName(args[2]) + ChatColor.GOLD + " и так может смотреть твою позицию");
					}
					return true;
				}
				if(args[1].equalsIgnoreCase("remove") && args.length == 3) {
					if(plugin.removeFromPosPermWhitelist(p, args[2])) {
						p.sendMessage(Names.formatName(args[2]) + y + " теперь не может смотреть твою позицию.");
					} else {
						p.sendMessage(Names.formatName(args[2]) + ChatColor.GOLD + " и так не может смотреть твою позицию");
					}
					return true;
				}
				if(args[1].equalsIgnoreCase("clear")) {
					List<String> newList = plugin.getPosPermWhitelist(p);
					newList.clear();
					plugin.setPosPermWhitelist(p, newList);
					p.sendMessage(y + "Все твои друзья убраны. Ты донц тупои.");
					return true;
				}
				if(args[1].equalsIgnoreCase("list")) {
					if(plugin.getPosPermWhitelist(p).isEmpty()) {
						p.sendMessage(ChatColor.GOLD + "Никто не видит твою позицию, скрытное ты гавно.");
						return true;
					}
					String names = "";
					for(String s : plugin.getPosPermWhitelist(p)) {
						names += Names.formatName(s) + y + ", ";
					}
					names = names.substring(0, names.length() - 2) + y + ".";
					if(plugin.getPosPermWhitelist(p).size() == 1) {
						p.sendMessage(y + "Твою позицию может видеть только " + names);
					} else {
						p.sendMessage(y + "Твою позицию могут видеть " + names);
					}
					return true;
				}
				if(args[1].equalsIgnoreCase("addall")) {
					boolean add = false;
					for(Player pl : WorldHelper.playersNotmeAnyDimension(p)) {
						if(plugin.addToPosPermWhitelist(p, pl.getName())) {
							p.sendMessage(y + "Добавлен " + Names.formatName(pl.getName()));
							add = true;
						}
					}
					if(!add) {
						p.sendMessage(ChatColor.GOLD + "Никто не был добавлен.");
					}
					return true;
				}
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
			names.addAll(Lists.<String>newArrayList("alt", "friend", "public", "protected", "status"));
			return MathUtils.getListOfStringsMatchingLastWord(args, names);
		}
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("friend")) {
				return MathUtils.getListOfStringsMatchingLastWord(args, "add", "remove", "clear", "list", "addall");
			}
			if(args[0].equalsIgnoreCase("alt")) {
				return MathUtils.getListOfStringsMatchingLastWord(args, "mark", "player", "coords");
			}
		}
		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("alt") && args[1].equalsIgnoreCase("player")) {
				return MathUtils.getListOfStringsMatchingLastWord(args, WorldHelper.getAllPlayerNames());
			}
			if(args[0].equalsIgnoreCase("alt") && args[1].equalsIgnoreCase("mark")) {
				return MathUtils.getListOfStringsMatchingLastWord(args, Marks.getVisibleMarks(p));
			}
			if(args[0].equalsIgnoreCase("friend") && args[1].equalsIgnoreCase("add")) {
				return MathUtils.getListOfStringsMatchingLastWord(args, WorldHelper.getAllPlayerNames());
			}
			if(args[0].equalsIgnoreCase("friend") && args[1].equalsIgnoreCase("remove")) {
				return MathUtils.getListOfStringsMatchingLastWord(args, HardcorePlugin.instance.getPosPermWhitelist(p));
			}
		}
		return null;
	}

}
