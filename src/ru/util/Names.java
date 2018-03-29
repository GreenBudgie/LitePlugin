package ru.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.main.HardcorePlugin;
import ru.util.PlayerOptions.Option;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Names {

	private static File playersFile = new File(HardcorePlugin.instance.getDataFolder() + File.separator + "players.yml");
	public static YamlConfiguration players = YamlConfiguration.loadConfiguration(playersFile);
	public static final DoncPlayer Forest_engine = new DoncPlayer("Forest_engine", new Cases("Мекита", "Мекиты", "Меките", "Мекиту", "Мекитой", "Меките"),
			ChatColor.GOLD);
	public static final DoncPlayer nikki39 = new DoncPlayer("nikki39", new Cases("Орез", "Ореза", "Орезу", "Ореза", "Орезом", "Орезе"), ChatColor.GREEN, false);
	public static final DoncPlayer fillimor = new DoncPlayer("D_WHYTT", new Cases("Данч", "Данча", "Данчу", "Данча", "Данчем", "Данче"), ChatColor.AQUA);
	public static final DoncPlayer daniyal = new DoncPlayer("daniyal", new Cases("Ял", "Яла", "Ялу", "Яла", "Ялом", "Яле"), ChatColor.BLUE);
	public static final DoncPlayer soylamimi = new DoncPlayer("soylamimi", new Cases("Холоп", "Холопа", "Холопу", "Холопа", "Холопом", "Холопе"),
			ChatColor.LIGHT_PURPLE, false);
	public static final DoncPlayer aks251 = new DoncPlayer("aks251", new Cases("Артроз", "Артроза", "Артрозу", "Артроза", "Артрозом", "Артрозе"),
			ChatColor.RED);
	public static final DoncPlayer CleoSpice = new DoncPlayer("CleoSpice", new Cases("Карпов", "Карпова", "Карпову", "Карпова", "Карповым", "Карпове"),
			ChatColor.DARK_AQUA);
	public static final DoncPlayer Chemist = new DoncPlayer("Chemist", new Cases("Лев", "Льва", "Льву", "Льва", "Львом", "Льве"), ChatColor.DARK_GREEN);

	public static void defaultValues() {
		addPlayers(Forest_engine, nikki39, fillimor, daniyal, soylamimi, aks251, CleoSpice, Chemist);
	}

	public static boolean addPlayers(DoncPlayer... pl) {
		boolean add = false;
		for(DoncPlayer p : pl) {
			if(!players.contains(p.getServerName())) {
				players.set(p.getServerName(), p.serialize());
				save();
				add = true;
			}
		}
		return add;
	}

	public static boolean removePlayer(String name) {
		if(players.contains(name)) {
			players.set(name, null);
			save();
			return true;
		}
		return false;
	}

	public static boolean changeCases(String name, Cases cases) {
		if(players.contains(name)) {
			Map<String, Object> map = cases.serialize();
			for(String str : map.keySet()) {
				players.set(name + "." + str, map.get(str).toString());
			}
			save();
			return true;
		}
		return false;
	}

	public static boolean changeColor(String name, ChatColor color) {
		if(players.contains(name)) {
			players.set(name + ".color", color.name());
			save();
			return true;
		}
		return false;
	}

	public static boolean setVip(String name, boolean vip) {
		if(players.contains(name)) {
			Player p = Bukkit.getPlayer(name);
			if(p != null) {
				if(vip) {
					p.sendMessage(ChatColor.GREEN + "Теперь ты VIP. Поздравляю. Ты больше не тупой.");
				} else {
					p.sendMessage(ChatColor.RED + "ТЫ ТУПОЙ. ТЫ БОЛЬШЕ НЕ ВИП. ДАУН БЛЯТЬ.");
				}
			}
			players.set(name + ".vip", vip);
			save();
			return true;
		}
		return false;
	}

	public static void save() {
		try {
			players.save(playersFile);
		} catch(IOException e) {
		}
	}

	public static String formatName(Player p) {
		return formatName(p.getName());
	}

	public static String formatName(Player p, Case c) {
		return formatName(p.getName(), c);
	}

	public static String formatName(String s) {
		return formatName(s, Case.NOMINATIVE);
	}

	public static String formatName(String s, Case c) {
		for(DoncPlayer p : getAllPlayers()) {
			if(p.getServerName().equalsIgnoreCase(ChatColor.stripColor(s))) {
				if(PlayerOptions.getOption(p.getServerName(), Option.Danchmode) != null
						&& Boolean.valueOf(PlayerOptions.getOption(p.getServerName(), Option.Danchmode).toString())) {
					return p.getNameColor() + p.makeDanchName(c);
				}
				return p.getNameColor() + p.getCases().getByCase(c);
			}
		}
		return s;
	}

	public static List<DoncPlayer> getAllPlayers() {
		List<DoncPlayer> p = new ArrayList<DoncPlayer>();
		for(String s : players.getKeys(false)) {
			if(players.getConfigurationSection(s) == null) try {
				players.load(playersFile);
			} catch(Exception e) {
			}
			p.add(DoncPlayer.deserialize(s, players.getConfigurationSection(s).getValues(false)));
		}
		return p;
	}

	public static boolean contains(String name) {
		return getAllServerNames().contains(name);
	}

	public static List<String> getAllServerNames() {
		List<String> names = new ArrayList<String>();
		for(DoncPlayer p : getAllPlayers()) {
			names.add(p.getServerName());
		}
		return names;
	}

	public static List<String> getAllDoncNames() {
		List<String> names = new ArrayList<String>();
		for(DoncPlayer p : getAllPlayers()) {
			names.add(p.getDoncName());
		}
		return names;
	}

	public static String getRandomName() {
		return MathUtils.choose(getAllDoncNames().toArray(new String[0]));
	}

}
