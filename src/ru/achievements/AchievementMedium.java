package ru.achievements;

import org.bukkit.Material;

public class AchievementMedium extends ContainerAchievement {

	public AchievementMedium() {
		this.setName("������������");
		this.setTask("��������� ��� ������� ������");
		this.setXp(3500);
		this.setItem(Material.CONCRETE);
		this.setItemData(4);
		this.setAchievements(Difficulty.MEDIUM);
		this.setDifficulty(Difficulty.HARD);
	}

}
