package ru.achievements;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class AchievementOrez extends DoncAchievement {

	public AchievementOrez() {
		this.setName("Орез");
		this.setTask("Сдохнуть в лаве с чареным алмазным сетом.");
		this.setInfo("Не обязательно просирать броню: главное сдохнуть в блоке лавы в ней. Думай, как можно засейвить.");
		this.setXp(300);
		this.setItem(Material.DIAMOND_CHESTPLATE);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	@EventHandler
	public void achieve(PlayerDeathEvent e) {
		if(e.getEntity().getLastDamageCause().getCause() == DamageCause.LAVA || e.getEntity().getLocation().getBlock().getType() == Material.LAVA) {
			for(ItemStack item : e.getEntity().getInventory().getArmorContents()) {
				if(item != null
						&& (item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.DIAMOND_CHESTPLATE
								|| item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.DIAMOND_BOOTS)
						&& !item.getEnchantments().isEmpty()) {
					this.complete(e.getEntity());
				}
			}
		}
	}

}
