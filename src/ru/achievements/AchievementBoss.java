package ru.achievements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import ru.bosses.BossManager;

public class AchievementBoss extends ProgressiveAchievement {

	public AchievementBoss() {
		this.setName("Мачилово");
		this.setTask("Убить каждого босса из плагина.");
		this.setXp(1200);
		this.setItem(Material.DIAMOND_SWORD);
		this.setDifficulty(Difficulty.HARD);
		List<String> req = new ArrayList<String>();
		BossManager.bosses.forEach(boss -> req.add(ChatColor.stripColor(boss.getName())));
		this.setRequirements(req);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		if(BossManager.isBoss(e.getEntity()) && e.getEntity().getKiller() != null) {
			Player p = e.getEntity().getKiller();
			this.setRequirement(p, ChatColor.stripColor(e.getEntity().getCustomName()), true);
			this.tryComplete(p);
		}
	}

}
