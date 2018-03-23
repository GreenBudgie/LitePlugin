package ru.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.slikey.effectlib.EffectManager;
import ru.achievements.AchievementManager;
import ru.bosses.BossManager;
import ru.commands.CommandAch;
import ru.commands.CommandBoss;
import ru.commands.CommandCEnchant;
import ru.commands.CommandCoords;
import ru.commands.CommandCustomItem;
import ru.commands.CommandDanchmode;
import ru.commands.CommandDonc;
import ru.commands.CommandEnch;
import ru.commands.CommandMark;
import ru.commands.CommandMessage;
import ru.commands.CommandObserve;
import ru.commands.CommandOpt;
import ru.commands.CommandOption;
import ru.commands.CommandPing;
import ru.commands.CommandPlayer;
import ru.commands.CommandPlugin;
import ru.commands.CommandSetAchievement;
import ru.items.CustomItems;
import ru.items.CustomItemsListener;
import ru.util.GuiHandler;
import ru.util.Marks;
import ru.util.Names;
import ru.util.PlayerJumpEvent;
import ru.util.PlayerOptions;
import ru.util.TaskManager;

public class HardcorePlugin extends JavaPlugin {

	public EffectManager em;
	public File posperms = new File(getDataFolder() + File.separator + "perms.yml");
	public YamlConfiguration posPermsInfo = YamlConfiguration.loadConfiguration(posperms);

	public static final boolean TEST = true;

	public static HardcorePlugin instance;

	public void onEnable() {
		instance = this;
		em = new EffectManager(this);
		PlayerJumpEvent.register(this);

		Bukkit.getPluginManager().registerEvents(new Handler(), this);
		Bukkit.getPluginManager().registerEvents(new GuiHandler(), this);
		Bukkit.getPluginManager().registerEvents(new CustomItemsListener(), this);

		this.getCommand("donc").setExecutor(new CommandDonc());
		this.getCommand("pos").setExecutor(new CommandCoords());
		this.getCommand("mark").setExecutor(new CommandMark());
		this.getCommand("option").setExecutor(new CommandOption());
		this.getCommand("setach").setExecutor(new CommandSetAchievement());
		this.getCommand("ach").setExecutor(new CommandAch());
		this.getCommand("cenchant").setExecutor(new CommandCEnchant());
		this.getCommand("plugin").setExecutor(new CommandPlugin());
		this.getCommand("ench").setExecutor(new CommandEnch());
		this.getCommand("player").setExecutor(new CommandPlayer());
		this.getCommand("danchmode").setExecutor(new CommandDanchmode());
		this.getCommand("message").setExecutor(new CommandMessage());
		this.getCommand("obs").setExecutor(new CommandObserve());
		this.getCommand("ping").setExecutor(new CommandPing());
		this.getCommand("opt").setExecutor(new CommandOpt());
		this.getCommand("customitem").setExecutor(new CommandCustomItem());
		this.getCommand("boss").setExecutor(new CommandBoss());
		BossManager.init();
		TaskManager.init();
		AchievementManager.init();
		CustomItems.init();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
			AchievementManager.rewrite(p);
			PlayerOptions.rewrite(p);
		}
		if(!new File(getDataFolder() + File.separator + "players.yml").exists()) {
			Names.defaultValues();
		}
	}

	public static void invokeLater(Runnable task) {
		instance.getServer().getScheduler().scheduleSyncDelayedTask(instance, task);
	}
	
	public static void invokeLater(Runnable task, long delay) {
		instance.getServer().getScheduler().scheduleSyncDelayedTask(instance, task, delay);
	}


	public void onDisable() {
		this.savePosPerm();
		em.dispose();
	}

	public static void puk() {
		log("puk");
	}

	public static void log(Object str) {
		Bukkit.broadcastMessage(String.valueOf(str));
	}

	public static void consoleLog(Object str) {
		instance.getLogger().info(String.valueOf(str));
	}
	
	public static void log() {
		puk();
	}

	public static void log(Object... str) {
		String s = "";
		for(Object ss : str) {
			s += ss.toString() + ", ";
		}
		Bukkit.broadcastMessage(String.valueOf(s));
	}

	public static void error(Object str) {
		Bukkit.broadcastMessage(ChatColor.DARK_RED + "Error in HardcorePlugin (Мекита донц, сообщите ему): " + String.valueOf(str));
	}

	public void registerPlayerPosPerm(Player p) {
		if(!posPermsInfo.contains(p.getName())) {
			posPermsInfo.set(p.getName() + ".public", true);
			posPermsInfo.set(p.getName() + ".whitelist", new ArrayList<String>());
			posPermsInfo.set(p.getName() + ".observing", null);
		}
	}

	public boolean isPublicPosPerm(Player p) {
		return posPermsInfo.getBoolean(p.getName() + ".public");
	}

	public List<String> getPosPermWhitelist(Player p) {
		return posPermsInfo.getStringList(p.getName() + ".whitelist");
	}

	public boolean canSeePosition(Player who, Player whom) {
		return getPosPermWhitelist(whom).contains(who.getName()) || isPublicPosPerm(whom);
	}

	public boolean isObserving(Player p) {
		String s = posPermsInfo.getString(p.getName() + ".observing");
		return s != null && !s.isEmpty();
	}

	public Player getObservingPlayer(Player p) {
		if(!isObserving(p) || !isObservingPlayer(p)) return null;
		String name = posPermsInfo.getString(p.getName() + ".observing");
		return Bukkit.getPlayer(name);
	}

	public boolean posPermWhitelistContains(Player p, String name) {
		return getPosPermWhitelist(p).contains(name);
	}

	public boolean addToPosPermWhitelist(Player p, String name) {
		if(posPermWhitelistContains(p, name)) return false;
		List<String> newList = getPosPermWhitelist(p);
		newList.add(name);
		posPermsInfo.set(p.getName() + ".whitelist", newList);
		savePosPerm();
		return true;
	}

	public boolean removeFromPosPermWhitelist(Player p, String name) {
		if(!posPermWhitelistContains(p, name)) return false;
		List<String> newList = getPosPermWhitelist(p);
		newList.remove(name);
		posPermsInfo.set(p.getName() + ".whitelist", newList);
		savePosPerm();
		return true;
	}

	public void setPosPermWhitelist(Player p, List<String> list) {
		posPermsInfo.set(p.getName() + ".whitelist", list);
		savePosPerm();
	}

	public boolean setPosPermPublic(Player p, boolean publ) {
		if(isPublicPosPerm(p) == publ) return false;
		posPermsInfo.set(p.getName() + ".public", publ);
		savePosPerm();
		return true;
	}

	public Location getObservingCoords(Player p) {
		if(!isObserving(p)) return null;
		String str = posPermsInfo.getString(p.getName() + ".observing");
		String[] rawStr = str.trim().split(" ");
		if(rawStr.length < 1 || rawStr.length > 3) return null;
		if(rawStr.length == 1) {
			Player pl = Bukkit.getPlayer(rawStr[0]);
			if(pl == null) {
				return null;
			} else {
				return pl.getLocation();
			}
		}
		if(rawStr.length == 2) {
			return Marks.getMarkLocation(p.getWorld(), rawStr[1]);
		}
		if(rawStr.length == 3) {
			try {
				int xc = Integer.valueOf(rawStr[0]);
				int yc = Integer.valueOf(rawStr[1]);
				int zc = Integer.valueOf(rawStr[2]);
				return new Location(p.getWorld(), xc, yc, zc);
			} catch(NumberFormatException e) {
			}
		}
		return null;
	}

	public boolean isObservingPlayer(Player p) {
		if(!isObserving(p)) return false;
		String str = posPermsInfo.getString(p.getName() + ".observing");
		String[] str2 = str.trim().split(" ");
		if(str2.length == 1) return true;
		return false;
	}

	public boolean isObservingMark(Player p) {
		if(!isObserving(p)) return false;
		String str = posPermsInfo.getString(p.getName() + ".observing");
		String[] str2 = str.trim().split(" ");
		if(str2.length == 2) return true;
		return false;
	}

	public String getObservingMark(Player p) {
		if(!isObserving(p)) return null;
		String str = posPermsInfo.getString(p.getName() + ".observing");
		String[] str2 = str.trim().split(" ");
		if(str2.length == 2) {
			return str2[1];
		}
		return null;
	}

	public void startObservingPlayer(Player p, Player who) {
		posPermsInfo.set(p.getName() + ".observing", who.getName());
		savePosPerm();
	}

	public void startObservingMark(Player p, String name) {
		if(!Marks.visibleMarkExists(p, name)) {
			p.sendMessage(ChatColor.RED + "Метки " + ChatColor.GOLD + name + ChatColor.RED + " не существует.");
			return;
		}
		posPermsInfo.set(p.getName() + ".observing", "mark " + name);
		p.sendMessage(ChatColor.YELLOW + "Теперь ты видишь координаты метки " + Marks.getMarkFullName(name) + ChatColor.YELLOW + ".");
		savePosPerm();
	}

	public void startObservingCoords(Player p, int x, int y, int z) {
		String pos = String.valueOf(x) + " " + String.valueOf(y) + " " + String.valueOf(z);
		posPermsInfo.set(p.getName() + ".observing", pos);
		savePosPerm();
	}

	public boolean stopObserving(Player p) {
		if(!isObserving(p)) return false;
		posPermsInfo.set(p.getName() + ".observing", null);
		savePosPerm();
		return true;
	}

	public void savePosPerm() {
		try {
			posPermsInfo.save(posperms);
		} catch(IOException e) {
		}
	}

}
