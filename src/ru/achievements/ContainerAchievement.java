package ru.achievements;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerAchievement extends DoncAchievement {

	private List<DoncAchievement> achievements = new ArrayList<DoncAchievement>();

	public void tryComplete(Player p) {
		if(getProgress(p) >= getRequiredProgress()) {
			this.complete(p);
		}
	}

	public int getRequiredProgress() {
		return achievements.size();
	}

	public int getProgress(Player p) {
		int pr = 0;
		for(DoncAchievement ach : achievements) {
			if(ach.isCompleted(p)) {
				pr++;
			}
		}
		return pr;
	}

	public void setAchievements(List<DoncAchievement> ach) {
		achievements.addAll(ach);
	}

	public void setAchievements(Difficulty d) {
		for(DoncAchievement ach : AchievementManager.achievements) {
			if(ach.getDifficulty() == d) {
				achievements.add(ach);
			}
		}
	}

	public List<DoncAchievement> getAchievements() {
		return this.achievements;
	}

}
