package ru.achievements;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import ru.util.Names;

import java.util.ArrayList;
import java.util.List;

public class AchievementBath extends ProgressiveAchievement {

	public AchievementBath() {
		this.setName("Банька и парилка, Мекита и погибель");
		this.setTask("Запарить до смерти банными предметами");
		this.setXp(80);
		List<String> r = new ArrayList<String>();
		r.add("Веник");
		r.add("Полотенце");
		r.add("Тапочки");
		this.setRequirements(r);
		this.setItem(Material.WOOD_STAIRS);
		this.setDifficulty(Difficulty.DONZ);
	}

	public boolean showRequirements(Player p) {
		return true;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void grow(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Player killer = p.getKiller();
		if(p.getKiller() != null) {
			ItemStack item = killer.getInventory().getItemInMainHand();
			if(item != null) {
				Material m = item.getType();
				boolean flag = false;
				if(m == Material.SAPLING || m == Material.WHEAT) {
					this.setRequirement(killer, "Веник", true);
					flag = true;
				}
				if(m == Material.PAPER) {
					this.setRequirement(killer, "Полотенце", true);
					flag = true;
				}
				if(m == Material.LEATHER_BOOTS) {
					this.setRequirement(killer, "Тапочки", true);
					flag = true;
				}
				if(flag) {
					e.setDeathMessage(Names.formatName(p.getName()) + ChatColor.RED + " ТРАГИЧЕСКИ ПОГИБ. "
							+ Names.formatName(killer.getName()) + ChatColor.RED + " ЗАПАРИЛ ЕГО ДО СМЕРТИ.");
					this.tryComplete(killer);
				}
			}
		}
	}

}
