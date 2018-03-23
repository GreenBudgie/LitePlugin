package ru.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import ru.main.HardcorePlugin;
import ru.util.Case;
import ru.util.Marks;
import ru.util.Marks.MarkAccess;
import ru.util.Marks.MarkResult;
import ru.util.MathUtils;
import ru.util.Names;
import ru.util.WorldHelper;

public class CommandMark implements CommandExecutor, TabCompleter {

	private HardcorePlugin plugin = HardcorePlugin.instance;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		Location l = p.getLocation();
		ChatColor y = ChatColor.YELLOW;
		int ln = args.length;
		if(ln == 0) {
			String name = l.getBlock().getBiome().name() + "_0";
			for(int i = 0; Marks.markExists(name); i++) {
				name = name.substring(0, name.length() - String.valueOf(i == 0 ? 0 : i - 1).length()) + i;
			}
			if(Marks.addMark(p, name) == Marks.MarkResult.SUCCESS) {
				Marks.setMarkAccess(p, name, MarkAccess.PRIVATE);
				p.sendMessage(y + "Добавлена метка с именем " + Marks.getMarkFullName(name));
			} else {
				p.sendMessage(ChatColor.DARK_RED + "Неизвестная ошибка");
			}
		}
		if(ln >= 1) {
			if(args[0].equalsIgnoreCase("list")) {
				printList(p, args.length >= 2 && args[1].equalsIgnoreCase("all"), args.length >= 2 ? Bukkit.getPlayer(args[1]) : null);
			}
		}
		if(ln >= 2) {
			if(args[0].equalsIgnoreCase("info")) {
				if(Marks.visibleMarkExists(p, args[1])) {
					printInfo(args[1], p);
				} else {
					p.sendMessage(ChatColor.RED + "Метки " + ChatColor.GOLD + args[1] + ChatColor.RED + " не существует!");
				}
			}
			if(args[0].equalsIgnoreCase("add")) {
				if(!Marks.markExists(args[1])) {
					if(args.length == 2) {
						if(Marks.addMark(p, args[1]) == MarkResult.INVALID) {
							p.sendMessage(ChatColor.RED + "Недопустимое имя метки: " + ChatColor.GOLD + args[1]);
						} else {
							p.sendMessage(
									y + "Метка " + Marks.getMarkFullName(args[1]) + y + " добавлена с координатами " + Marks.getMarkStringColored(p, args[1]));
						}
					}
					if(ln >= 3) {
						if(ln >= 5) {
							Location loc = WorldHelper.translateToLocation(p.getWorld(), args[2] + " " + args[3] + " " + args[4]);
							if(loc != null) {
								if(ln >= 6) {
									try {
										Marks.MarkAccess access = Marks.MarkAccess.valueOf(args[5].toUpperCase());
										if(Marks.addMark(p, args[1]) == MarkResult.INVALID) {
											p.sendMessage(ChatColor.RED + "Недопустимое имя метки: " + ChatColor.GOLD + args[1]);
										} else {
											Marks.setMarkAccess(p, args[1], access);
											p.sendMessage(y + "Метка " + Marks.getMarkFullName(args[1]) + y + " добавлена с координатами "
													+ Marks.getMarkStringColored(p, args[1]) + y + ". Доступ: " + Marks.getMarkAccess(args[1]).toString());
										}
									} catch(IllegalArgumentException e) {
										p.sendMessage(ChatColor.RED + "Неизвестный уровень доступа: " + ChatColor.GOLD + args[5] + ChatColor.RED
												+ ". Доступны: " + ChatColor.GOLD + "public" + ChatColor.RED + "," + ChatColor.GOLD + " protected"
												+ ChatColor.RED + "," + ChatColor.GOLD + " private");
									}
								} else {
									MarkResult res = Marks.addMark(p, args[1], loc);
									if(res == Marks.MarkResult.SUCCESS) {
										p.sendMessage(y + "Метка " + Marks.getMarkFullName(args[1]) + y + " добавлена с координатами "
												+ Marks.getMarkStringColored(p, args[1]));
									} else {
										if(res == MarkResult.FAR) {
											p.sendMessage(ChatColor.RED + "Введенные координаты ссылаются на непрогруженный чанк");
										} else {
											p.sendMessage(ChatColor.RED + "Недопустимое имя метки: " + ChatColor.GOLD + args[1]);
										}
									}
								}
							} else {
								p.sendMessage(ChatColor.RED + "Координаты введены неверно");
							}
						} else {
							try {
								Marks.MarkAccess access = Marks.MarkAccess.valueOf(args[2].toUpperCase());
								if(Marks.addMark(p, args[1]) == MarkResult.INVALID) {
									p.sendMessage(ChatColor.RED + "Недопустимое имя метки: " + ChatColor.GOLD + args[1]);
								} else {
									Marks.setMarkAccess(p, args[1], access);
									p.sendMessage(y + "Метка " + Marks.getMarkFullName(args[1]) + y + " добавлена с координатами "
											+ Marks.getMarkStringColored(p, args[1]) + y + ". Доступ: " + Marks.getMarkAccess(args[1]).toString());
								}
							} catch(IllegalArgumentException e) {
								p.sendMessage(ChatColor.RED + "Неизвестный уровень доступа: " + ChatColor.GOLD + args[2] + ChatColor.RED + ". Доступны: "
										+ ChatColor.GOLD + "public" + ChatColor.RED + "," + ChatColor.GOLD + " protected" + ChatColor.RED + "," + ChatColor.GOLD
										+ " private");
							}
						}
					}
				} else {
					p.sendMessage(ChatColor.RED + "Метка " + Marks.getMarkFullName(args[1]) + ChatColor.RED + " уже существует!");
				}
			}
			if(args[0].equalsIgnoreCase("remove")) {
				if(Marks.visibleMarkExists(p, args[1])) {
					String buf = Marks.getMarkFullName(args[1]);
					if(Marks.removeMark(p, args[1]) == Marks.MarkResult.SUCCESS) {
						p.sendMessage(y + "Метка " + buf + y + " удалена");
					} else {
						p.sendMessage(ChatColor.RED + "У тебя нет доступа к метке " + buf);
					}
				} else {
					p.sendMessage(ChatColor.RED + "Метки " + ChatColor.GOLD + args[1] + ChatColor.RED + " не существует!");
				}
			}
			if(args[0].equalsIgnoreCase("change")) {
				if(Marks.visibleMarkExists(p, args[1])) {
					if(ln > 2) {
						Location loc = WorldHelper.translateToLocation(p.getWorld(), args[2] + " " + args[3] + " " + args[4]);
						if(loc != null) {
							Marks.MarkResult res = Marks.changeMark(p, args[1], loc);
							if(res == Marks.MarkResult.SUCCESS) {
								p.sendMessage(y + "Метка " + Marks.getMarkFullName(args[1]) + y + " изменена. Ее координаты: "
										+ Marks.getMarkStringColored(p, args[1]));
							} else {
								if(res == Marks.MarkResult.FAR) {
									p.sendMessage(ChatColor.RED + "Введенные координаты ссылаются на непрогруженный чанк");
								} else {
									p.sendMessage(ChatColor.RED + "У тебя нет доступа к метке " + Marks.getMarkFullName(args[1]));
								}
							}
						} else {
							p.sendMessage(ChatColor.RED + "Координаты введены неверно");
						}
					} else {
						if(Marks.changeMark(p, args[1]) == Marks.MarkResult.SUCCESS) {
							p.sendMessage(
									y + "Метка " + Marks.getMarkFullName(args[1]) + y + " изменена. Ее координаты: " + Marks.getMarkStringColored(p, args[1]));
						} else {
							p.sendMessage(ChatColor.RED + "У тебя нет доступа к метке " + Marks.getMarkFullName(args[1]));
						}
					}
				} else {
					p.sendMessage(ChatColor.RED + "Метки " + ChatColor.GOLD + args[1] + ChatColor.RED + " не существует!");
				}
			}
		}
		if(ln >= 3) {
			if(args[0].equalsIgnoreCase("access")) {
				if(Marks.visibleMarkExists(p, args[1])) {
					try {
						Marks.MarkAccess access = Marks.MarkAccess.valueOf(args[2].toUpperCase());
						Marks.setMarkAccess(p, args[1], access);
						p.sendMessage(y + "Установлен " + Marks.getMarkAccess(args[1]).toString() + y + " уровень доступа для метки "
								+ Marks.getMarkFullName(args[1]));
					} catch(IllegalArgumentException e) {
						p.sendMessage(
								ChatColor.RED + "Неизвестный уровень доступа: " + ChatColor.GOLD + args[2] + ChatColor.RED + ". Доступны: " + ChatColor.GOLD
										+ "public" + ChatColor.RED + "," + ChatColor.GOLD + " protected" + ChatColor.RED + "," + ChatColor.GOLD + " private");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Метки " + ChatColor.GOLD + args[1] + ChatColor.RED + " не существует!");
				}
			}
		}
		return true;
	}

	private void printInfo(String name, Player p) {
		ChatColor y = ChatColor.YELLOW;
		Location loc = Marks.getMarkLocation(p.getWorld(), name);
		p.sendMessage(y + "Метка " + Marks.getMarkFullName(name) + y + ":");
		p.sendMessage(" " + y + "X: " + ChatColor.RED + loc.getBlockX());
		p.sendMessage(" " + y + "Y: " + ChatColor.GREEN + loc.getBlockY());
		p.sendMessage(" " + y + "Z: " + ChatColor.BLUE + loc.getBlockZ());
		WorldHelper.EnumDimension dim = WorldHelper.getDimensionByBiome(Marks.getMarkBiome(name));
		if(dim == WorldHelper.getPlayerDimension(p))
			p.sendMessage(" " + y + "Дистанция: " + ChatColor.AQUA + ChatColor.AQUA + (int) Math.round(loc.distance(p.getLocation())));
		if(dim == WorldHelper.EnumDimension.OVERWORLD) {
			p.sendMessage(" " + y + "Биом: " + ChatColor.GREEN + Marks.getMarkBiome(name));
		}
		p.sendMessage(" " + y + "Создатель: " + Names.formatName(Marks.getMarkOwner(name)));
		p.sendMessage(" " + y + "Доступ: " + Marks.getMarkAccess(name).toString());
		p.sendMessage(" " + y + "Дата создания: " + Marks.getMarkFirstDate(name));
		if(Marks.markChanged(name)) p.sendMessage(" " + y + "Дата последнего изменения: " + Marks.getMarkChangeDate(name));
	}

	private void printList(Player p, boolean all, Player fromPlayer) {
		ChatColor y = ChatColor.YELLOW;
		int i = 1;
		boolean pl = fromPlayer != null;
		Set<String> marks = all ? Marks.getVisibleMarks(p)
				: (pl ? Marks.getVisiblePlayerMarks(p, fromPlayer) : Marks.getVisibleMarksInDimension(p, WorldHelper.getPlayerDimension(p)));
		if(marks.isEmpty()) {
			p.sendMessage(ChatColor.GOLD + "Нет никаких меток");
		} else {
			p.sendMessage(y + (all ? "Все метки:" : (pl ? "Метки " + Names.formatName(fromPlayer.getName(), Case.GENITIVE) + y + ":" : "Метки в этом мире:")));
		}
		for(String name : marks) {
			p.sendMessage(y + String.valueOf(i) + ". " + Marks.getMarkFullName(name) + y
					+ (pl ? "" : (y + " (" + Names.formatName(Marks.getMarkOwner(name)) + y + ")")) + ":");
			Location loc = Marks.getMarkLocation(p.getWorld(), name);
			String dst = WorldHelper.getDimensionByBiome(Marks.getMarkBiome(name)) == WorldHelper.getPlayerDimension(p)
					? (y + " (" + ChatColor.AQUA + (int) Math.round(loc.distance(p.getLocation())) + y + ")") : "";
			p.sendMessage(" " + Marks.getMarkStringColored(p, name) + dst);
			if(marks.size() > i) p.sendMessage("");
			i++;
		}
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(!(sender instanceof Player)) return null;
		Player p = (Player) sender;
		if(args.length == 1) {
			return MathUtils.getListOfStringsMatchingLastWord(args, "access", "add", "remove", "list", "info", "change");
		}
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("access") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("change"))
				return MathUtils.getListOfStringsMatchingLastWord(args, Marks.getEditableMarks(p));
			if(args[0].equalsIgnoreCase("info")) return MathUtils.getListOfStringsMatchingLastWord(args, Marks.getVisibleMarks(p));
			if(args[0].equalsIgnoreCase("list")) {
				List<String> names = new ArrayList<String>();
				for(Player pl : Bukkit.getOnlinePlayers()) {
					names.add(pl.getName());
				}
				names.add("all");
				return MathUtils.getListOfStringsMatchingLastWord(args, names);
			}
		}
		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("access") || args[0].equalsIgnoreCase("add")) {
				return MathUtils.getListOfStringsMatchingLastWord(args, "public", "protected", "private");
			}
		}
		if(args.length == 6) {
			if(args[0].equalsIgnoreCase("add")) {
				return MathUtils.getListOfStringsMatchingLastWord(args, "public", "protected", "private");
			}
		}
		return null;
	}

}
