package ru.achievements;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import ru.bosses.BossManager;

public class AchievementPukSpider extends CounterAchievement {

	public AchievementPukSpider() {
		this.setName("Киллер");
		this.setTask("Убить 10 пукпауков с помощью меча на баню артроза 5");
		this.setXp(1400);
		this.setRequiredProgress(10);
		this.setItem(Material.STRING);
		this.setDifficulty(Difficulty.HARD);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		if(BossManager.pukSpider.isEquals(e.getEntity())) {
			Player p = e.getEntity().getKiller();
			if(p != null && p.getInventory().getItemInMainHand() != null
					&& p.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS) >= 5) {
				this.increaseProgress(p);
			}
		}
	}

}
