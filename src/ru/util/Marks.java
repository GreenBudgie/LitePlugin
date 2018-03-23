package ru.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ru.main.HardcorePlugin;

public class Marks {

	public static File marksFile = new File(HardcorePlugin.instance.getDataFolder() + File.separator + "marks.yml");
	public static YamlConfiguration marks = YamlConfiguration.loadConfiguration(marksFile);

	public static void save() {
		try {
			marks.save(marksFile);
		} catch(IOException e) {
		}
	}

	public static MarkResult addMark(Player p, String name) {
		return addMark(p, name, p.getLocation());
	}

	public static MarkResult addMark(Player p, String name, Location loc) {
		if(markExists(name)) return MarkResult.EXISTS;
		if(!WorldHelper.isChunkGenerated(loc)) return MarkResult.FAR;
		if(!isValidName(name)) return MarkResult.INVALID;
		marks.set(name + ".location", WorldHelper.locationAsStringNoWorld(loc));
		marks.set(name + ".owner", p.getName());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'в' HH:mm:ss");
		marks.set(name + ".date", dateFormat.format(new Date()));
		marks.set(name + ".changedate", "-1");
		marks.set(name + ".biome", loc.getBlock().getBiome().toString());
		marks.set(name + ".access", MarkAccess.PUBLIC.name());
		save();
		return MarkResult.SUCCESS;
	}

	public static MarkResult removeMark(Player p, String name) {
		if(!markExists(name)) return MarkResult.NOT_EXISTS;
		if(!canChangeMark(p, name)) return MarkResult.NO_ACCESS;
		marks.set(name, null);
		save();
		return MarkResult.SUCCESS;
	}

	public static MarkResult changeMark(Player p, String name) {
		return changeMark(p, name, p.getLocation());
	}

	public static MarkResult changeMark(Player p, String name, Location loc) {
		if(!markExists(name)) return MarkResult.NOT_EXISTS;
		if(!canChangeMark(p, name)) return MarkResult.NO_ACCESS;
		if(!WorldHelper.isChunkGenerated(loc)) return MarkResult.FAR;
		marks.set(name + ".location", WorldHelper.locationAsStringNoWorld(loc));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'в' HH:mm:ss");
		marks.set(name + ".changedate", dateFormat.format(new Date()));
		marks.set(name + ".biome", loc.getBlock().getBiome().name());
		save();
		return MarkResult.SUCCESS;
	}
	
	public static boolean isValidName(String name) {
		return Pattern.matches("^\\w+$", name);
	}

	public static MarkResult setMarkAccess(Player p, String name, MarkAccess access) {
		if(!markExists(name)) return MarkResult.NOT_EXISTS;
		if(!canChangeMark(p, name)) return MarkResult.NO_ACCESS;
		marks.set(name + ".access", access.name());
		save();
		return MarkResult.SUCCESS;
	}
	
	public static boolean visibleMarkExists(Player p, String name) {
		for(String s : getVisibleMarks(p)) {
			if(s.equals(name)) {
				return true;
			}
		}
		return false;
	}
	

	public static boolean markExists(String name) {
		for(String s : getMarks()) {
			if(s.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public static String getMarkString(String name) {
		if(!markExists(name)) return null;
		return marks.getString(name + ".location");
	}
	
	public static String getMarkStringColored(Player p, String name) {
		if(!markExists(name)) return null;
		Location loc = getMarkLocation(p.getWorld(), name);
		return WorldHelper.getColoredLocationCoordinates(loc);
	}

	public static Biome getMarkBiome(String name) {
		if(!markExists(name)) return null;
		return Biome.valueOf(marks.getString(name + ".biome"));
	}

	public static String getMarkFirstDate(String name) {
		if(!markExists(name)) return null;
		return marks.getString(name + ".date");
	}

	public static String getMarkChangeDate(String name) {
		if(!markExists(name)) return null;
		return marks.getString(name + ".changedate");
	}

	public static boolean markChanged(String name) {
		if(!markExists(name)) return false;
		return !getMarkChangeDate(name).equals("-1");
	}

	public static Location getMarkLocation(World world, String name) {
		if(!markExists(name)) return null;
		return WorldHelper.translateToLocation(world, getMarkString(name));
	}

	public static String getMarkOwner(String name) {
		if(!markExists(name)) return null;
		return marks.getString(name + ".owner");
	}

	public static boolean isMarkOwner(Player p, String name) {
		if(!markExists(name)) return false;
		return getMarkOwner(name).equals(p.getName());
	}

	public static MarkAccess getMarkAccess(String name) {
		if(!markExists(name)) return null;
		return MarkAccess.valueOf(marks.getString(name + ".access"));
	}

	public static boolean canSeeMark(Player p, String name) {
		return markExists(name) && (isMarkOwner(p, name) || getMarkAccess(name) != MarkAccess.PRIVATE);
	}

	public static boolean canChangeMark(Player p, String name) {
		return markExists(name) && (isMarkOwner(p, name) || getMarkAccess(name) == MarkAccess.PUBLIC);
	}

	public static ChatColor getMarkColor(String name) {
		if(!markExists(name)) return ChatColor.WHITE;
		return WorldHelper.getDimensionColor(WorldHelper.getDimensionByBiome(getMarkBiome(name)));
	}

	public static String getMarkFullName(String name) {
		return getMarkColor(name) + name;
	}
	
	public static Set<String> getVisibleMarksInDimension(Player p, WorldHelper.EnumDimension dim) {
		Set<String> marks = new HashSet<String>();
		for(String mark : getMarks()) {
			if(canSeeMark(p, mark) && WorldHelper.getDimensionByBiome(getMarkBiome(mark)) == dim) marks.add(mark);
		}
		return marks;
	}
	
	public static Set<String> getPlayerMarks(Player p) {
		Set<String> marks = new HashSet<String>();
		for(String mark : getMarks()) {
			if(getMarkOwner(mark).equals(p.getName())) marks.add(mark);
		}
		return marks;
	}
	
	public static Set<String> getVisiblePlayerMarks(Player p, Player who) {
		Set<String> marks = new HashSet<String>();
		for(String mark : getMarks()) {
			if(getMarkOwner(mark).equals(who.getName()) && canSeeMark(p, mark)) marks.add(mark);
		}
		return marks;
	}
	
	public static Set<String> getEditableMarks(Player p) {
		Set<String> marks = new HashSet<String>();
		for(String mark : getMarks()) {
			if(canChangeMark(p, mark)) marks.add(mark);
		}
		return marks;
	}

	public static Set<String> getVisibleMarks(Player p) {
		Set<String> marks = new HashSet<String>();
		for(String mark : getMarks()) {
			if(canSeeMark(p, mark)) marks.add(mark);
		}
		return marks;
	}

	public static Set<String> getMarksInDimension(WorldHelper.EnumDimension dim) {
		Set<String> marks = new HashSet<String>();
		for(String mark : getMarks()) {
			if(WorldHelper.getDimensionByBiome(getMarkBiome(mark)) == dim) marks.add(mark);
		}
		return marks;
	}

	public static Set<String> getMarks() {
		return marks.getKeys(false);
	}

	public static enum MarkAccess {
		PRIVATE, PROTECTED, PUBLIC;
		
		public String toString() {
			switch(this) {
			case PRIVATE:
				return ChatColor.RED + "приватный";
			case PROTECTED:
				return ChatColor.GOLD + "ограниченный";
			case PUBLIC:
				return ChatColor.GREEN + "публичный";
			}
			return null;
		}
	}

	public static enum MarkResult {
		SUCCESS, EXISTS, NOT_EXISTS, NO_ACCESS, FAR, INVALID;
	}

}
