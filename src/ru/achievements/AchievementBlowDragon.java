package ru.achievements;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import ru.util.WorldHelper;

public class AchievementBlowDragon extends DoncAchievement {

	public AchievementBlowDragon() {
		this.setName("Я его не трогал");
		this.setTask("Взорвать эндер дракона крипером");
		this.setInfo("Ачивка выдается всем игрокам в 50 блоках от убитого дракона.");
		this.setXp(2700);
		this.setItem(Material.SKULL_ITEM);
		this.setItemData(4);
		this.setDifficulty(Difficulty.INSANE);
	}

	@EventHandler
	public void achieve(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) e.getEntity();
			if(e.getDamager().getType() == EntityType.CREEPER && ent.getType() == EntityType.ENDER_DRAGON && ent.getHealth() <= e.getDamage()) {
				List<Player> players = WorldHelper.getPlayersDistance(e.getEntity().getLocation(), 50);
				for(Player p : players) {
					this.complete(p);
				}
			}
		}
	}

}
