package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTameEvent;

import java.util.ArrayList;
import java.util.List;

public class AchievementTame extends ProgressiveAchievement {

	public AchievementTame() {
		this.setName("Друг природы");
		this.setTask("Приручить всех возможных мобов.");
		this.setXp(350);
		this.setItem(Material.LEASH);
		this.setDifficulty(Difficulty.MEDIUM);
		List<String> req = new ArrayList<String>();
		for(EntityType type : EntityType.values()) {
			if(type.getEntityClass() != null) {
				for(Class<?> clazz : type.getEntityClass().getInterfaces()) {
					if(clazz == Tameable.class) {
						req.add(type.name());
						break;
					}
				}
			}
		}
		this.setRequirements(req);
	}

	@EventHandler
	public void achieve(EntityTameEvent e) {
		if(e.getOwner() instanceof Player) {
			Player p = (Player) e.getOwner();
			this.setRequirement(p, e.getEntityType().name(), true);
			this.tryComplete(p);
		}
	}

}
