package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import ru.util.WorldHelper;

import java.util.List;

public class AchievementEnderman extends DoncAchievement {

	public AchievementEnderman() {
		this.setName("������������ �������");
		this.setTask("����� ������ �����");
		this.setInfo("������ �������� ���� ������� � 20 ������ �� ������� ���������.");
		this.setXp(150);
		this.setItem(Material.WATER_BUCKET);
		this.setDifficulty(Difficulty.EASY);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		if(e.getEntityType() == EntityType.ENDERMAN) {
			if(e.getEntity().getLastDamageCause().getCause() == DamageCause.DROWNING) {
				List<Player> players = WorldHelper.getPlayersDistance(e.getEntity().getLocation(), 20);
				for(Player p : players) {
					this.complete(p);
				}
			}
		}
	}

}
