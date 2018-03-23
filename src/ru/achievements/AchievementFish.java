package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class AchievementFish extends CounterAchievement {

	public AchievementFish() {
		this.setName("Самая противная ачивка");
		this.setTask("Съесть стак рыбы фугу");
		this.setXp(200);
		this.setItem(Material.RAW_FISH);
		this.setItemData(3);
		this.setDifficulty(Difficulty.MEDIUM);
		this.setRequiredProgress(64);
	}

	@EventHandler
	public void achieve(PlayerItemConsumeEvent e) {
		Player p = e.getPlayer();
		if(e.getItem().getType() == Material.RAW_FISH && e.getItem().getDurability() == 3) {
			this.increaseProgress(p);
		}
	}

}
