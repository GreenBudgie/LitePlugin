package ru.achievements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import ru.util.PlayerOptions;

public class AchievementDeath extends ProgressiveAchievement {

	public AchievementDeath() {
		this.setName("Как орез в кс...");
		this.setTask("Умереть всеми возможными способами (кроме /kill и молнии)");
		this.setXp(3000);
		List<String> causes = new ArrayList<String>();
		for(DamageCause dc : DamageCause.values()) {
			if(dc != DamageCause.POISON && dc != DamageCause.CUSTOM && dc != DamageCause.SUICIDE && dc != DamageCause.STARVATION && dc != DamageCause.MELTING
					&& dc != DamageCause.LIGHTNING && dc != DamageCause.BLOCK_EXPLOSION) {
				causes.add(dc.name());
			}
		}
		this.setRequirements(causes);
		this.setItem(Material.RED_MUSHROOM);
		this.setDifficulty(Difficulty.INSANE);
	}

	@EventHandler
	public void achieve(PlayerDeathEvent e) {
		this.setRequirement(e.getEntity(), e.getEntity().getLastDamageCause().getCause().name(), true);
		if(isAllCompleted(e.getEntity())) {
			complete(e.getEntity());
			e.setNewExp(this.getXp());
		}
	}

	public boolean showRequirements(Player p) {
		return PlayerOptions.isActive(p, PlayerOptions.Option.LongRequirements);
	}

}
