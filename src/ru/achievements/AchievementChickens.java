package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

public class AchievementChickens extends CounterAchievement {

	public AchievementChickens() {
		this.setName("Ферма куриц");
		this.setTask("Убить 500 куриц.");
		this.setInfo("Советуется использовать ферму куриц данияла.");
		this.setXp(300);
		this.setRequiredProgress(500);
		this.setItem(Material.FEATHER);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		if(e.getEntityType() == EntityType.CHICKEN) {
			if(e.getEntity().getKiller() != null) {
				this.increaseProgress(e.getEntity().getKiller());
			}
		}
	}

}
