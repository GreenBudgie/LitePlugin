package ru.achievements;

import org.bukkit.Material;

public class AchievementEasy extends ContainerAchievement {

	public AchievementEasy() {
		this.setName("Начинаем с малого");
		this.setTask("Выполнить все легкие ачивки");
		this.setXp(600);
		this.setItem(Material.CONCRETE);
		this.setItemData(5);
		this.setAchievements(Difficulty.EASY);
		this.setDifficulty(Difficulty.MEDIUM);
	}

}
