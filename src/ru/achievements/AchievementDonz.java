package ru.achievements;

import org.bukkit.Material;

public class AchievementDonz extends ContainerAchievement {

	public AchievementDonz() {
		this.setName("����� �� ������");
		this.setTask("��������� ��� �������������� ������");
		this.setXp(200);
		this.setItem(Material.CONCRETE);
		this.setItemData(3);
		this.setAchievements(Difficulty.DONZ);
		this.setDifficulty(Difficulty.EASY);
	}

}
