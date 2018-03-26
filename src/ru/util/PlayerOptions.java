package ru.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.main.HardcorePlugin;

import java.io.File;
import java.io.IOException;

public class PlayerOptions {

	public static HardcorePlugin plugin = HardcorePlugin.instance;
	public static File opt = new File(plugin.getDataFolder() + File.separator + "options.yml");
	public static YamlConfiguration options = YamlConfiguration.loadConfiguration(opt);

	public static enum Option {
		LongRequirements(false),
		Danchmode(false),
		JoinMes("null"),
		QuitMes("null"),
		Flair("miner"), // on, miner, off
		MoltenCore("miner"), // on, miner, off
		CompassBind(true),
		MoltenCoreShift(true);

		private Object defValue;

		private Option(Object defValue) {
			this.defValue = defValue;
		}

		public Object getDefaultValue() {
			return defValue;
		}
	}

	public static boolean isValid(Player p) {
		for(Option o : Option.values()) {
			if(!options.contains(path(p, o))) {
				return false;
			}
		}
		return true;
	}

	public static void rewrite(Player p) {
		for(Option o : Option.values()) {
			if(!options.contains(path(p, o))) {
				setOption(p, o, o.getDefaultValue());
			}
		}
	}

	public static void enable(Player p, Option o, boolean value) {
		if(!(o.getDefaultValue() instanceof Boolean)) return;
		setOption(p, o, value);
	}

	public static void toggle(Player p, Option o) {
		if(!(o.getDefaultValue() instanceof Boolean)) return;
		setOption(p, o, !((boolean) options.get(path(p, o))));
	}

	public static Object getOption(Player p, Option opt) {
		return options.get(path(p, opt));
	}
	
	public static Object getOption(String name, Option opt) {
		return options.get(path(name, opt));
	}

	public static int getInt(Player p, Option opt) {
		if(!(opt.getDefaultValue() instanceof Integer)) {
			setOption(p, opt, opt.getDefaultValue());
		}
		return Integer.valueOf(getString(p, opt));
	}

	public static String getString(Player p, Option opt) {
		if(!(opt.getDefaultValue() instanceof String)) {
			setOption(p, opt, opt.getDefaultValue());
		}
		return getOption(p, opt).toString();
	}

	public static boolean isActive(Player p, Option opt) {
		if(!(opt.getDefaultValue() instanceof Boolean)) {
			setOption(p, opt, opt.getDefaultValue());
		}
		return (boolean) options.get(path(p, opt));
	}

	public static void setOption(Player p, Option opt, Object value) {
		if(opt.getDefaultValue() instanceof Boolean) {
			options.set(path(p, opt), (boolean) value);
			save();
			return;
		}
		if(opt.getDefaultValue() instanceof Integer) {
			options.set(path(p, opt), (int) value);
			save();
			return;
		}
		if(opt.getDefaultValue() instanceof String) {
			options.set(path(p, opt), String.valueOf(value));
			save();
			return;
		}
		options.set(path(p, opt), value);
		save();
	}

	private static String path(Player p, Option opt) {
		return p.getName() + "." + opt.name();
	}
	
	private static String path(String name, Option opt) {
		return name + "." + opt.name();
	}

	public static void setMessage(Player p, String rawStr, boolean join) {
		for(ChatColor c : ChatColor.values()) {
			rawStr = rawStr.replaceAll(c.name(), c + "");
		}
		setOption(p, join ? Option.JoinMes : Option.QuitMes, rawStr);
	}

	public static String getMessage(Player p, boolean join) {
		String str = getOption(p, join ? Option.JoinMes : Option.QuitMes).toString();
		str = str.replaceAll("ME", Names.formatName(p.getName()).toUpperCase());
		str = str.replaceAll("me", Names.formatName(p.getName()));
		return str;
	}

	public static boolean hasMessage(Player p, boolean join) {
		return !getOption(p, join ? Option.JoinMes : Option.QuitMes).toString().equals("null");
	}

	public static void save() {
		try {
			options.save(opt);
		} catch(IOException e) {
		}
	}

}
