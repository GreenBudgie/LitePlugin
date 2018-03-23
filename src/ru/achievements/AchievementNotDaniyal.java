package ru.achievements;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class AchievementNotDaniyal extends DoncAchievement {

	public AchievementNotDaniyal() {
		this.setName("!������");
		this.setTask("�������� � 70-� �������");
		this.setXp(1000);
		this.setItem(Material.EXP_BOTTLE);
		this.setDifficulty(Difficulty.HARD);
	}

	@EventHandler
	public void achieve(PlayerDeathEvent e) {
		if(e.getEntity().getLevel() >= 70) {
			this.complete(e.getEntity());
		}
	}

}
