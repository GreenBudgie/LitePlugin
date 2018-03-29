package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import ru.util.WorldHelper;

import java.util.List;

public class AchievementEndermite extends DoncAchievement {

	public AchievementEndermite() {
		this.setName("Фиолетовая Мелочь");
		this.setTask("Убить Эндермита Кактусом");
		this.setInfo("Ачивка выдается всем игрокам в 20 блоках от убитого эндермита.");
		this.setXp(200);
		this.setItem(Material.CACTUS);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		if(e.getEntityType() == EntityType.ENDERMITE) {
			if(e.getEntity().getLastDamageCause().getCause() == DamageCause.CONTACT) {
				List<Player> players = WorldHelper.getPlayersDistance(e.getEntity().getLocation(), 20);
				for(Player p : players) {
					this.complete(p);
				}
			}
		}
	}

}
