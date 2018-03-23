package ru.achievements;

import org.bukkit.Material;

public class AchievementAll extends ContainerAchievement {

	public AchievementAll() {
		this.setName("Чемпион мира по ебанутости");
		this.setTask("Выполнить ВСЕ ачивки");
		this.setXp(35000);
		this.setItem(Material.CONCRETE);
		this.setItemData(15);
		this.setAchievements(AchievementManager.achievements);
		this.setDifficulty(Difficulty.EBAT);
	}

}
