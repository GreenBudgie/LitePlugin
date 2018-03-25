package ru.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.enchants.EnchantmentEvasion;
import ru.items.CustomItem;
import ru.items.CustomItems;
import ru.main.HardcorePlugin;

public class TaskManager {

	public static int tick = 0;
	public static int sec = 0;
	public static int min = 0;
	private static HardcorePlugin plugin = HardcorePlugin.instance;

	public static void init() {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(HardcorePlugin.instance, new Runnable() {

			public void run() {
				tickObserve();
				for(CustomItem item : CustomItems.getItems()) {
					item.update();
				}
				EnchantmentEvasion.update();
				if(tick < 19) {
					tick++;
				} else {
					tick = 0;
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(!DoncPlayer.getPlayer(p.getName()).isVip() && MathUtils.chance(5)) {
							p.sendMessage(ChatColor.LIGHT_PURPLE + "------------------------------------");
							p.sendMessage("");
							p.sendMessage(ChatColor.RED
									+ "ВНИМАНИЕ БЛЯТЬ!!!!!!!!! ТЫ ТУПОЙ!!!!!!!!!! У ТЕБЯ НЕТ ВИП СТАТУСА!!!!!!!!!! ТЫ ДОЛЖЕН ЗАПЛАТИТЬ МЕКИТЕ 20 РУБЛЕЙ ИНАЧЕ ТАК И ОСТАНЕШЬСЯ ДАУНОМ!!!!!!");
							p.sendMessage("");
							p.sendMessage(ChatColor.DARK_PURPLE + "------------------------------------");
						}
					}
					if(sec < 59) {
						sec++;
					} else {
						sec = 0;
						if(min < 59) {
							min++;
						} else {
							min = 0;
						}
					}
				}
			}

		}, 0L, 1L);

	}

	public static boolean isSecUpdated() {
		return tick == 0;
	}

	public static boolean isMinUpdated() {
		return sec == 0 && isSecUpdated();
	}

	public static int getFullTicks() {
		return tick + (sec * 20) + (min * 1200);
	}

	private static void tickObserve() {
		for(Player p : plugin.getServer().getOnlinePlayers()) {
			p.setCompassTarget(p.getBedSpawnLocation() == null ? p.getWorld().getSpawnLocation() : p.getBedSpawnLocation());
			if(plugin.isObserving(p)) {
				Player pl = plugin.getObservingPlayer(p);
				if(plugin.isObservingPlayer(p) && pl == null) {
					return;
				}
				if(pl != null && !plugin.canSeePosition(p, pl)) {
					plugin.stopObserving(p);
					p.sendMessage(Names.formatName(pl.getName()) + ChatColor.RED + " запретил смотреть его позицию.");
					return;
				}
				if(plugin.isObservingMark(p) && !Marks.visibleMarkExists(p, plugin.getObservingMark(p))) {
					plugin.stopObserving(p);
					p.sendMessage(ChatColor.RED + "Метка, за который ты наблюдал, была удалена или скрыта.");
					return;
				}
				Location l = plugin.getObservingCoords(p);
				int xc = l.getBlockX();
				int yc = l.getBlockY();
				int zc = l.getBlockZ();
				String dst;
				char arrow = getArrow(p.getLocation(), l);
				boolean here = true;
				if(WorldHelper.isInSameDimension(p.getLocation(), l) && (plugin.isObservingMark(p) ?
						WorldHelper.getDimensionByBiome(Marks.getMarkBiome(plugin.getObservingMark(p))) == WorldHelper.getPlayerDimension(p) :
						true)) {
					dst = String.valueOf((int) l.distance(p.getLocation()));
				} else {
					dst = WorldHelper.getDimensionNamePrepositional(
							plugin.isObservingMark(p) ? WorldHelper.getDimensionByBiome(Marks.getMarkBiome(plugin.getObservingMark(p))) : WorldHelper.getDimension(l),
							ChatColor.GOLD);
					arrow = ' ';
					here = false;
				}
				ChatColor y = ChatColor.YELLOW;
				ChatColor g = ChatColor.GOLD;
				ChatColor r = ChatColor.RED;
				ChatColor gr = ChatColor.GREEN;
				ChatColor b = ChatColor.BLUE;
				String name = "";
				if(pl != null) name = Names.formatName(pl.getName()) + y + ": ";
				if(plugin.isObservingMark(p)) name = Marks.getMarkFullName(plugin.getObservingMark(p)) + y + ": ";
				String str = name + y + xc + y + ", " + y + yc + y + ", " + y + zc + y + " (" + ChatColor.AQUA + dst + y + ") " + ChatColor.AQUA + arrow;
				if(PlayerOptions.isActive(p, PlayerOptions.Option.CompassBind) && here) {
					p.setCompassTarget(l);
				}
				InventoryHelper.sendActionBarMessage(p, str);
			}
		}
	}

	private static char getArrow(Location l1, Location l2) {
		int x1 = l1.getBlockX();
		int z1 = l1.getBlockZ();
		int x2 = l2.getBlockX();
		int z2 = l2.getBlockZ();
		char arrow = '-';
		double d1 = l1.getYaw();
		d1 = d1 % 360.0D;
		double d2 = Math.atan2(z2 - z1, x2 - x1);
		double d0 = Math.PI - ((d1 - 90.0D) * 0.01745329238474369D - d2);
		float f = (float) (d0 / (Math.PI * 2D));
		float res = positiveModulo(f, 1.0F);
		if(res > 1 || ((res < 0.125 && res >= 0) || (res >= 0.875 && res <= 1))) {
			arrow = '\u25B2'; // forward
		}
		if(res >= 0.125 && res < 0.375) {
			arrow = '\u25BA'; // right
		}
		if(res >= 0.375 && res < 0.625) {
			arrow = '\u25BC'; // back
		}
		if(res >= 0.625 && res < 0.875) {
			arrow = '\u25C4'; // left
		}
		return arrow;
	}

	private static float positiveModulo(float numerator, float denominator) {
		return (numerator % denominator + denominator) % denominator;
	}

}
