package ru.achievements;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import ru.util.WorldHelper;

public class AchievementShulker extends DoncAchievement {

	public AchievementShulker() {
		this.setName("�����");
		this.setTask("��������� ������� ����� �������");
		this.setInfo("������ �������� ���� ������� � 50 ������ �� ������� �������.");
		this.setXp(2800);
		this.setItem(Material.ARROW);
		this.setDifficulty(Difficulty.INSANE);
	}

	@EventHandler
	public void achieve(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) e.getEntity();
			if(e.getDamager().getType() == EntityType.ARROW && ent.getType() == EntityType.SHULKER && ent.getHealth() <= e.getDamage()) {
				Arrow arrow = (Arrow) e.getDamager();
				if(arrow.getShooter() instanceof Skeleton) {
					List<Player> players = WorldHelper.getPlayersDistance(e.getEntity().getLocation(), 50);
					for(Player p : players) {
						this.complete(p);
					}
				}
			}
		}
	}

}
