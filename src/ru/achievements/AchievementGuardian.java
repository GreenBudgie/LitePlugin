package ru.achievements;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import ru.util.WorldHelper;

public class AchievementGuardian extends DoncAchievement {

	public AchievementGuardian() {
		this.setName("�� �� ��������");
		this.setTask("������� ��������� � ����");
		this.setInfo("������ �������� ���� ������� � 30 ������ �� ������� ���������.");
		this.setXp(920);
		this.setItem(Material.LAVA_BUCKET);
		this.setDifficulty(Difficulty.HARD);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		if(e.getEntityType() == EntityType.GUARDIAN) {
			if(e.getEntity().getLastDamageCause().getCause() == DamageCause.LAVA) {
				List<Player> players = WorldHelper.getPlayersDistance(e.getEntity().getLocation(), 30);
				for(Player p : players) {
					this.complete(p);
				}
			}
		}
	}

}
