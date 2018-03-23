package ru.util;

import org.bukkit.entity.Player;

public class ExperienceHelper {

	public static int getPlayerXP(Player p) {
		return (int) (getExperienceForLevel(p.getLevel()) + p.getExp() * xpBarCap(p));
	}

	public static void drainPlayerXP(Player p, int amount) {
		addPlayerXP(p, -amount);
	}

	public static void addPlayerXP(Player p, int amount) {
		int experience = getPlayerXP(p) + amount;
		p.setTotalExperience(experience);
		p.setLevel(getLevelForExperience(experience));
		int expForLevel = getExperienceForLevel(p.getLevel());
		p.setExp((float) (experience - expForLevel) / (float) xpBarCap(p));
	}

	public static int getExperienceForLevel(int level) {
		if(level == 0) return 0;
		if(level > 0 && level < 17) return (int) (level * level + 6 * level);
		else if(level > 16 && level < 32) return (int) (2.5 * level * level - 40.5 * level + 360);
		else return (int) (4.5 * level * level - 162.5 * level + 2220);
	}

	public static int getLevelForExperience(int experience) {
		int i = 0;
		while(getExperienceForLevel(i) <= experience) {
			i++;
		}
		return i - 1;
	}

	public static int xpBarCap(Player p) {
		int exp = p.getLevel();
		return exp >= 30 ? 112 + (exp - 30) * 9 : (exp >= 15 ? 37 + (exp - 15) * 5 : 7 + exp * 2);
	}

}
