package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import ru.util.WorldHelper;

import java.util.List;

public class AchievementShulker extends DoncAchievement {

	public AchievementShulker() {
		this.setName("Битва");
		this.setTask("Заставить скелета убить шалкера");
		this.setInfo("Ачивка выдается всем игрокам в 50 блоках от убитого шалкера.");
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
