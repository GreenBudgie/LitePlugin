package ru.achievements;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import ru.util.WorldHelper;

public class AchievementEnderPearl extends DoncAchievement {

	public AchievementEnderPearl() {
		this.setName("За гранью");
		this.setTask("Тпшнуться на 150 блоков с помощью эндер перла");
		this.setInfo("Дистанция не учитывает высоту.");
		this.setXp(120);
		this.setItem(Material.ENDER_PEARL);
		this.setDifficulty(Difficulty.EASY);
	}

	@EventHandler
	public void achieve(PlayerTeleportEvent e) {
		if(e.getCause() == TeleportCause.ENDER_PEARL && WorldHelper.distanceNoY(e.getFrom(), e.getTo()) >= 150) {
			this.complete(e.getPlayer());
		}
	}

}
