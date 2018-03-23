package ru.achievements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

public abstract class ProgressiveAchievement extends DoncAchievement {

	private Map<String, Boolean> requirements = new HashMap<String, Boolean>();

	public void setRequirements(List<String> r) {
		for(String s : r) {
			requirements.put(s, false);
		}
	}

	public boolean showRequirements(Player p) {
		return true;
	}

	public Map<String, Boolean> getRequirements() {
		return requirements;
	}

	public Map<String, Boolean> getRequirements(Player p) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for(String s : AchievementManager.achInfo.getConfigurationSection(p.getName() + "." + this.getClass().getSimpleName() + ".requirements")
				.getKeys(false)) {
			map.put(s, AchievementManager.achInfo.getBoolean(p.getName() + "." + this.getClass().getSimpleName() + ".requirements." + s));
		}
		return map;
	}

	public boolean isRequirementCompleted(Player p, String r) {
		return AchievementManager.achInfo.getBoolean(p.getName() + "." + this.getClass().getSimpleName() + ".requirements." + r);
	}

	public int getProgress(Player p) {
		int count = 0;
		for(Boolean b : getRequirements(p).values()) {
			count += b ? 1 : 0;
		}
		return count;
	}

	public int getRequiredProgress() {
		return requirements.size();
	}

	public boolean isAllCompleted(Player p) {
		for(Boolean b : getRequirements(p).values()) {
			if(!b) return false;
		}
		return true;
	}

	public void tryComplete(Player p) {
		if(this.isAllCompleted(p)) this.complete(p);
	}

	public void setRequirement(Player p, String requirement, boolean value) {
		if(!getRequirements(p).containsKey(requirement)) return;
		AchievementManager.achInfo.set(p.getName() + "." + this.getClass().getSimpleName() + ".requirements." + requirement, value);
		AchievementManager.save();
	}

	public void complete(Player p) {
		for(String s : requirements.keySet()) {
			setRequirement(p, s, true);
		}
		super.complete(p);
	}

	public void decomplete(Player p) {
		for(String s : requirements.keySet()) {
			setRequirement(p, s, false);
		}
		super.decomplete(p);
	}

}
