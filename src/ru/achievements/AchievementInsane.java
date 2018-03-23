package ru.achievements;

import org.bukkit.Material;

public class AchievementInsane extends ContainerAchievement {

	public AchievementInsane() {
		this.setName("��������� ���� �����");
		this.setTask("��������� ��� ����������� ������");
		this.setXp(20000);
		this.setItem(Material.CONCRETE);
		this.setItemData(10);
		this.setAchievements(Difficulty.INSANE);
		this.setDifficulty(Difficulty.INSANE);
	}

}
