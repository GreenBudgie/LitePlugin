package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class AchievementUselessSlaughterer extends ProgressiveAchievement {

	public AchievementUselessSlaughterer() {
		this.setName("Юзлес живодёр");
		this.setTask("Убить по одному детёнышу каждого мирного существа");
		this.setInfo("Ни опыта, ни лута, так зачем же вы это делаете?");
		this.setXp(1000);
		this.setItem(Material.LEATHER);
		this.setDifficulty(Difficulty.HARD);
		List<String> req = new ArrayList<String>();
        for(EntityType type : EntityType.values()) {
            if(type.getEntityClass() != null) {
                for(Class<?> clazz : type.getEntityClass().getInterfaces()) {
                    if(clazz == Ageable.class || clazz == Animals.class || clazz == Cow.class) {
                        req.add(type.name());
                        break;
                    }
                }
            }
        }
        this.setRequirements(req);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		if(e.getEntity() instanceof Ageable) {
			if(e.getEntity().getKiller() != null) {
			    Ageable ageable = (Ageable)e.getEntity();
			    if (!ageable.isAdult()) {
                    Player p = e.getEntity().getKiller();
                    this.setRequirement(p, e.getEntityType().name(), true);
                    this.tryComplete(p);
                }
            }
		}
	}

}
