package ru.achievements;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

public class AchievementCake extends CounterAchievement {

	public AchievementCake() {
		this.setName("Гурман");
		this.setTask("Откусить 100 кусков торта.");
		this.setXp(250);
		this.setRequiredProgress(100);
		this.setItem(Material.CAKE);
		this.setDifficulty(Difficulty.MEDIUM);
	}
	
	@EventHandler
	public void achieve(PlayerStatisticIncrementEvent e) {
		if(e.getStatistic() == Statistic.CAKE_SLICES_EATEN) {
			this.increaseProgress(e.getPlayer());
		}
	}

}
