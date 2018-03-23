package ru.achievements;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class AchievementObsidian extends DoncAchievement {

	public AchievementObsidian() {
		this.setName("Силач");
		this.setTask("Сломать обсидиан рукой");
		this.setXp(60);
		this.setItem(Material.OBSIDIAN);
		this.setDifficulty(Difficulty.DONZ);
	}

	@EventHandler
	public void achieve(BlockBreakEvent e) {
		if(e.getBlock().getType() == Material.OBSIDIAN
				&& (e.getPlayer().getInventory().getItemInMainHand() == null || e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR)) {
			this.complete(e.getPlayer());
		}
	}

}
