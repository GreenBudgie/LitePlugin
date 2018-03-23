package ru.achievements;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class AchievementDaniyal extends DoncAchievement {

	public AchievementDaniyal() {
		this.setName("������");
		this.setTask("������ 70-� �������");
		this.setXp(1000);
		this.setItem(Material.DIAMOND);
		this.setDifficulty(Difficulty.HARD);
	}

	@EventHandler
	public void achieve(PlayerLevelChangeEvent e) {
		if(e.getNewLevel() >= 70) {
			this.complete(e.getPlayer());
		}
	}

}
