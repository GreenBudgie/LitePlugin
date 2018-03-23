package ru.achievements;

import org.bukkit.Material;

public class AchievementInsane extends ContainerAchievement {

	public AchievementInsane() {
		this.setName("Майнкрафт идет нахуй");
		this.setTask("Выполнить все невозможные ачивки");
		this.setXp(20000);
		this.setItem(Material.CONCRETE);
		this.setItemData(10);
		this.setAchievements(Difficulty.INSANE);
		this.setDifficulty(Difficulty.INSANE);
	}

}
