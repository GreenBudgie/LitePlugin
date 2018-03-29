package ru.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Utility;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DoncPlayer implements ConfigurationSerializable {

	private String serverName;
	private ChatColor nameColor;
	private String doncName;
	private Cases cases;
	private boolean vip = true;

	public DoncPlayer(String serverName, Cases cases, ChatColor nameColor) {
		this.serverName = serverName;
		this.cases = cases;
		doncName = cases.getNominative();
		this.nameColor = nameColor;
	}

	public DoncPlayer(String serverName, Cases cases, ChatColor nameColor, boolean vip) {
		this(serverName, cases, nameColor);
		this.vip = vip;
	}

	public Cases getCases() {
		return cases;
	}

	public boolean compare(Player p) {
		if(p.getName().equalsIgnoreCase(getServerName())) {
			return true;
		}
		return false;
	}

	public String getServerName() {
		return this.serverName;
	}

	public Player asPlayer() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getName().equalsIgnoreCase(this.serverName)) return p;
		}
		return null;
	}

	public String getDoncName() {
		return this.doncName;
	}

	public ChatColor getNameColor() {
		return vip ? nameColor : ChatColor.WHITE;
	}

	public boolean isVip() {
		return vip;
	}

	public static DoncPlayer getPlayer(String name) {
		for(DoncPlayer p : Names.getAllPlayers()) {
			for(String str : p.cases.getCases()) {
				if(str.equalsIgnoreCase(ChatColor.stripColor(name))) {
					return p;
				}
			}
			if(p.serverName.equalsIgnoreCase(ChatColor.stripColor(name))) return p;
		}
		return null;
	}

	public String makeDanchName(Case c) {
		Player pl = asPlayer();
		String n = doncName;
		if(pl != null && PlayerOptions.isActive(pl, PlayerOptions.Option.Danchmode)) {
			for(int i = n.length() - 2; i > 0; i--) {
				if(MathUtils.isVowel(n.charAt(i))) {
					n = n.substring(0, i + 1) + Names.fillimor.getCases().getByCase(c).toLowerCase();
					break;
				}
			}
		}
		return n;
	}

	@Utility
	public Map<String, Object> serialize() {
		Map<String, Object> data = new HashMap<String, Object>();

		data.putAll(cases.serialize());
		data.put("color", getNameColor().name());
		data.put("vip", isVip());

		return data;
	}

	public static DoncPlayer deserialize(String serverName, Map<String, Object> args) {
		Map<String, Object> args2 = new HashMap<String, Object>(args);
		args2.remove("color");
		args2.remove("vip");
		return new DoncPlayer(serverName, Cases.deserialize(args2), ChatColor.valueOf(((String) args.get("color")).toUpperCase()),
				Boolean.valueOf(args.get("vip").toString()));
	}

}
