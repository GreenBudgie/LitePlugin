package ru.achievements;

import org.bukkit.entity.Player;

public abstract class CounterAchievement extends DoncAchievement {

	private int requiredProgress = 0;
	
	public int getProgress(Player p) {
		return AchievementManager.achInfo.getInt(p.getName() + "." + this.getClass().getSimpleName() + ".progress");		
	}
	
	public void increaseProgress(Player p) {
		this.setProgress(p, this.getProgress(p) + 1);
	}
	
	public void setProgress(Player p, int progress) {
		AchievementManager.achInfo.set(p.getName() + "." + this.getClass().getSimpleName() + ".progress", progress);
		AchievementManager.save();
		if(progress >= getRequiredProgress()) {
			this.complete(p);
		}
	}
	
	public int getRequiredProgress() {
		return requiredProgress;
	}
	
	public void setRequiredProgress(int requiedProgress) {
		this.requiredProgress = requiedProgress;
	}
	
	public void complete(Player p) {
		super.complete(p);
	}
	
	public void decomplete(Player p) {
		this.setProgress(p, 0);
		super.decomplete(p);
	}
	
}
