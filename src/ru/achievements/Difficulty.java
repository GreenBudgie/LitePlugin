package ru.achievements;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public enum Difficulty {

	DONZ, EASY, MEDIUM, HARD, INSANE, EBAT;

	public String getName() {
		switch(this) {
		case DONZ:
			return "Донц";
		case EASY:
			return "Легко";
		case HARD:
			return "Сложно";
		case INSANE:
			return "ПИЗДЕЦ";
		case MEDIUM:
			return "Средне";
		case EBAT:
			return "ТЫ ЧО ЕБАНУТЫЙ??";
		default:
			return "Error";
		}
	}
	
	public ChatColor color() {
		switch(this) {
		case DONZ:
			return ChatColor.AQUA;
		case EASY:
			return ChatColor.GREEN;
		case HARD:
			return ChatColor.RED;
		case INSANE:
			return ChatColor.DARK_PURPLE;
		case MEDIUM:
			return ChatColor.YELLOW;
		case EBAT:
			return ChatColor.DARK_RED;
		default:
			return ChatColor.GRAY;
		}
	}

	public Color defColor() {
		switch(this.color()) {
		case YELLOW:
			return Color.YELLOW;
		case AQUA:
			return Color.AQUA;
		case GREEN:
			return Color.GREEN;
		case RED:
			return Color.RED;
		case DARK_PURPLE:
			return Color.PURPLE;
		case DARK_RED:
			return Color.BLACK;
		default:
			return Color.GRAY;
		}
	}
}
