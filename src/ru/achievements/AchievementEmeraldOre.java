package ru.achievements;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class AchievementEmeraldOre extends CounterAchievement {

	public AchievementEmeraldOre() {
		this.setName("Мастер по использованию X-Ray");
		this.setTask("Выкопать стак изурмудной руды.");
		this.setXp(1000);
		this.setRequiredProgress(64);
		this.setItem(Material.EMERALD_ORE);
		this.setDifficulty(Difficulty.HARD);
	}
	
	@EventHandler
	public void achieve(BlockBreakEvent e) {
		if(e.getBlock().getType() == Material.EMERALD_ORE) {
			this.increaseProgress(e.getPlayer());
		}
	}

}
