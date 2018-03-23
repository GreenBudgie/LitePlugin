package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import ru.util.WorldHelper;

public class AchievementSkeleton extends DoncAchievement {

	public AchievementSkeleton() {
		this.setName("Путешествие по измерениям");
		this.setTask("Убить скелета-иссушителя, при этом находясь в энде");
		this.setXp(1100);
		this.setItem(Material.SKULL_ITEM);
		this.setItemData(1);
		this.setDifficulty(Difficulty.HARD);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		WorldHelper.EnumDimension dim = WorldHelper.getDimension(e.getEntity().getLocation());
		if(e.getEntityType() == EntityType.WITHER_SKELETON && dim == WorldHelper.EnumDimension.END) {
			Player p = e.getEntity().getKiller();
			if(p != null) {
				this.complete(p);
			}
		}
	}

}
