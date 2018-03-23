package ru.achievements;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import ru.util.WorldHelper;

public class AchievementCreeper extends DoncAchievement {

	public AchievementCreeper() {
		this.setName("����������");
		this.setTask("��������� ������� �����������");
		this.setInfo("������ �������� ���� ������� � 50 ������ �� ������� �������.");
		this.setXp(170);
		this.setItem(Material.ANVIL);
		this.setDifficulty(Difficulty.EASY);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		if(e.getEntityType() == EntityType.CREEPER) {
			if(e.getEntity().getLastDamageCause().getCause() == DamageCause.FALLING_BLOCK) {
				List<Player> players = WorldHelper.getPlayersDistance(e.getEntity().getLocation(), 50);
				for(Player p : players) {
					this.complete(p);
				}
			}
		}
	}

}
