package ru.achievements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import ru.util.PlayerOptions;

public class AchievementStats extends ProgressiveAchievement {

	public AchievementStats() {
		this.setName("Путешественник");
		this.setTask("Преодолеть 1км всеми возможными способами");
		this.setXp(300);
		List<String> list = new ArrayList<String>();
		for(Statistic stat : Statistic.values()) {
			if(stat != Statistic.FLY_ONE_CM && stat.name().contains("ONE_CM")) {
				list.add(stat.name());
			}
		}
		this.setRequirements(list);
		this.setItem(Material.DIAMOND_BOOTS);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	@EventHandler
	public void achieve(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if(!this.isCompleted(p)) {
			for(String s : this.getRequirements().keySet()) {
				if(!this.getRequirements(p).getOrDefault(s, false)) {
					Statistic stat = Statistic.valueOf(s);
					int value = p.getStatistic(stat);
					if(value >= 100000) {
						this.setRequirement(p, stat.name(), true);
						this.tryComplete(p);
					}
				}
			}
		}
	}

	public boolean showRequirements(Player p) {
		return PlayerOptions.isActive(p, PlayerOptions.Option.LongRequirements);
	}

}
