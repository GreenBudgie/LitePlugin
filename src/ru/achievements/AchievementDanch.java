package ru.achievements;

import org.bukkit.Material;

public class AchievementDanch extends CounterAchievement {

	public AchievementDanch() {
		this.setName("����������");
		this.setTask("46 ��� ����������� ������ ��� '���' � ��������");
		this.setXp(80);
		this.setRequiredProgress(46);
		this.setItem(Material.STONE_BUTTON);
		this.setDifficulty(Difficulty.DONZ);
	}

}
