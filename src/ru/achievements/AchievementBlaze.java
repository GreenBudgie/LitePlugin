package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import ru.util.WorldHelper;

import java.util.List;

public class AchievementBlaze extends DoncAchievement {

	public AchievementBlaze() {
		this.setName("����������");
		this.setTask("����� ������ �����");
		this.setInfo("������ �������� ���� ������� � 50 ������ �� ������� ������.");
		this.setXp(1100);
		this.setItem(Material.BLAZE_ROD);
		this.setDifficulty(Difficulty.HARD);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		if(e.getEntityType() == EntityType.BLAZE) {
			if(e.getEntity().getLastDamageCause().getCause() == DamageCause.DROWNING) {
				List<Player> players = WorldHelper.getPlayersDistance(e.getEntity().getLocation(), 50);
				for(Player p : players) {
					this.complete(p);
				}
			}
		}
	}

}
