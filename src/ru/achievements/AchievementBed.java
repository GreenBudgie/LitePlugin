package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockExplodeEvent;

import ru.util.WorldHelper;

public class AchievementBed extends CounterAchievement {

	public AchievementBed() {
		this.setName("Ариф давай спать");
		this.setTask("Взорвать 30 кроватей в аду или энде");
		this.setXp(240);
		this.setRequiredProgress(30);
		this.setItem(Material.BED);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	@EventHandler
	public void achieve(BlockExplodeEvent e) {
		Player p = WorldHelper.nearestPlayer(e.getBlock().getLocation());
		if(p != null) {
			this.increaseProgress(p);
		}
	}

}
