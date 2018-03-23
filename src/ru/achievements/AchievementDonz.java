package ru.achievements;

import org.bukkit.Material;

public class AchievementDonz extends ContainerAchievement {

	public AchievementDonz() {
		this.setName("Легче не бывает");
		this.setTask("Выполнить все донцовыщевские ачивки");
		this.setXp(200);
		this.setItem(Material.CONCRETE);
		this.setItemData(3);
		this.setAchievements(Difficulty.DONZ);
		this.setDifficulty(Difficulty.EASY);
	}

}
