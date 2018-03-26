package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import ru.util.WorldHelper;

import java.util.List;

public class AchievementWither extends DoncAchievement {

	public AchievementWither() {
		this.setName("И нахуй мне это надо?");
		this.setTask("Убить иссушителя взрывом");
		this.setInfo("Ачивка выдается всем игрокам в 50 блоках от убитого визера.");
		this.setXp(3000);
		this.setItem(Material.TNT);
		this.setDifficulty(Difficulty.INSANE);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		if(e.getEntityType() == EntityType.WITHER
				&& (e.getEntity().getLastDamageCause().getCause() == DamageCause.BLOCK_EXPLOSION
						|| e.getEntity().getLastDamageCause().getCause() == DamageCause.ENTITY_EXPLOSION)) {
			List<Player> players = WorldHelper.getPlayersDistance(e.getEntity().getLocation(), 50);
			for(Player p : players) {
				this.complete(p);
			}
		}
	}

}
