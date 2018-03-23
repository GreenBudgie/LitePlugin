package ru.achievements;

import org.bukkit.Material;

public class AchievementAll extends ContainerAchievement {

	public AchievementAll() {
		this.setName("������� ���� �� ����������");
		this.setTask("��������� ��� ������");
		this.setXp(35000);
		this.setItem(Material.CONCRETE);
		this.setItemData(15);
		this.setAchievements(AchievementManager.achievements);
		this.setDifficulty(Difficulty.EBAT);
	}

}
