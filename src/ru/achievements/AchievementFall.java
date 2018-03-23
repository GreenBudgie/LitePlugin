package ru.achievements;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class AchievementFall extends DoncAchievement {

	public AchievementFall() {
		this.setName("������");
		this.setTask("������ 1000 ������ ��� ����������");
		this.setXp(270);
		this.setItem(Material.ELYTRA);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	@EventHandler
	public void achieve(PlayerMoveEvent e) {
		if(e.getPlayer().getFallDistance() >= 1000) {
			this.complete(e.getPlayer());
		}
	}

}
