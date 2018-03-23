package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import ru.util.WorldHelper;

public class AchievementMagma extends CounterAchievement {

	public AchievementMagma() {
		this.setName("Повелитель магмы");
		this.setTask("Заставить 10 враждебных мобов умереть от магмы.");
		this.setInfo("Выдается всем игрокам в радиусе 20 блоков от убитого моба.");
		this.setXp(250);
		this.setRequiredProgress(10);
		this.setItem(Material.MAGMA);
		this.setDifficulty(Difficulty.MEDIUM);
	}
	
	@EventHandler
	public void achieve(EntityDeathEvent e) {
		Entity ent = e.getEntity();
		if(WorldHelper.isBadMob(ent.getType()) && ent.getLastDamageCause().getCause() == DamageCause.HOT_FLOOR) {
			for(Player p : WorldHelper.getPlayersDistance(ent.getLocation(), 20)) {
				this.increaseProgress(p);
			}
		}
	}

}
