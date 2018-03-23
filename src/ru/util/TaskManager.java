package ru.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ru.enchants.EnchantmentEvasion;
import ru.items.CustomItem;
import ru.items.CustomItems;
import ru.main.HardcorePlugin;

public class TaskManager {

	private static HardcorePlugin plugin = HardcorePlugin.instance;
	public static int tick = 0;
	public static int sec = 0;
	public static int min = 0;

	public static void init() {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(HardcorePlugin.instance, new Runnable() {

			public void run() {
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

}
