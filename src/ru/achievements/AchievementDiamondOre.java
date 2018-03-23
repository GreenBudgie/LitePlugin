package ru.achievements;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class AchievementDiamondOre extends CounterAchievement {

	public AchievementDiamondOre() {
		this.setName("Шахтер");
		this.setTask("Выкопать стак алмазной руды.");
		this.setXp(250);
		this.setRequiredProgress(64);
		this.setItem(Material.DIAMOND_ORE);
		this.setDifficulty(Difficulty.MEDIUM);
	}
	
	@EventHandler
	public void achieve(BlockBreakEvent e) {
		if(e.getBlock().getType() == Material.DIAMOND_ORE) {
			this.increaseProgress(e.getPlayer());
		}
	}

}
