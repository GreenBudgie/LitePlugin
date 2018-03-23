package ru.achievements;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import ru.util.WorldHelper;

public class AchievementSpider extends DoncAchievement {

	public AchievementSpider() {
		this.setName("Арахнодонц");
		this.setTask("Утопить паука");
		this.setInfo("Ачивка выдается всем игрокам в 20 блоках от утопленного паука.");
		this.setXp(150);
		this.setItem(Material.WEB);
		this.setDifficulty(Difficulty.EASY);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		if(e.getEntityType() == EntityType.SPIDER || e.getEntityType() == EntityType.CAVE_SPIDER) {
			if(e.getEntity().getLastDamageCause().getCause() == DamageCause.DROWNING) {
				List<Player> players = WorldHelper.getPlayersDistance(e.getEntity().getLocation(), 20);
				for(Player p : players) {
					this.complete(p);
				}
			}
		}
	}

}
