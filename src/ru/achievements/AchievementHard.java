package ru.achievements;

import org.bukkit.Material;

public class AchievementHard extends ContainerAchievement {

	public AchievementHard() {
		this.setName("���������� � ����");
		this.setTask("��������� ��� ������� ������");
		this.setXp(10000);
		this.setItem(Material.CONCRETE);
		this.setItemData(14);
		this.setAchievements(Difficulty.HARD);
		this.setDifficulty(Difficulty.INSANE);
	}

}
