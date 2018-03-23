package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import ru.util.WorldHelper;

public class AchievementVindicator extends DoncAchievement {

	public AchievementVindicator() {
		this.setName("Самое далекое путешествие");
		this.setTask("Затащить поборника в энд и убить его там");
		this.setXp(3500);
		this.setItem(Material.IRON_AXE);
		this.setDifficulty(Difficulty.INSANE);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		WorldHelper.EnumDimension dim = WorldHelper.getDimension(e.getEntity().getLocation());
		if(e.getEntityType() == EntityType.VINDICATOR && dim == WorldHelper.EnumDimension.END) {
			Player p = e.getEntity().getKiller();
			if(p != null) {
				this.complete(p);
			}
		}
	}

}
