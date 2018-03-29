package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AchievementOrezTupoi extends CounterAchievement {

	public AchievementOrezTupoi() {
		this.setName("Ненависть");
		this.setTask("20 раз обозвать ореза тупым хачем в чате.");
		this.setInfo("Подходят любые фразы, однако они должны отличаться друг от друга.");
		this.setXp(50);
		this.setRequiredProgress(20);
		this.setItem(Material.SIGN);
		this.setDifficulty(Difficulty.DONZ);
	}
	
	private Map<String, List<String>> used = new HashMap<String, List<String>>();

	@EventHandler
	public void achieve(AsyncPlayerChatEvent e) {
		String mes = e.getMessage().toLowerCase();
		Player p = e.getPlayer();
		if(mes.contains("орез") || mes.contains("ариф")) {
			String[] strs = {"хач", "даун", "ебанат", "тупой", "донц", "черножопый", "тупоц", "вонючий", "член", "говноед"};
			for(String s : used.getOrDefault(p.getName(), new ArrayList<String>())) {
				if(mes.equalsIgnoreCase(s)) {
					return;
				}
			}
			for(String s : strs) {
				if(mes.contains(s)) {
					this.increaseProgress(p);
					if(this.isCompleted(p)) {
						used.remove(p.getName());
					} else {
						List<String> list = used.getOrDefault(p.getName(), new ArrayList<String>());
						list.add(mes);
						used.put(p.getName(), list);
					}
					break;
				}
			}
		}
	}

}
