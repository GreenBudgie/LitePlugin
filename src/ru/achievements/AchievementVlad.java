package ru.achievements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import ru.enchants.EnchantmentManager;
import ru.util.InventoryHelper;

public class AchievementVlad extends DoncAchievement {

	public AchievementVlad() {
		this.setName("Влад");
		this.setTask("Выпасть из мира с элитрами в инвентаре, при этом на надев их.");
		this.setInfo("Энчанта Soulbind быть не должно.");
		this.setXp(300);
		this.setItem(Material.ENDER_PORTAL_FRAME);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	@EventHandler
	public void achieve(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if(p.getLastDamageCause().getCause() == DamageCause.VOID) {
			if(p.getInventory().getChestplate() == null || p.getInventory().getChestplate().getType() != Material.ELYTRA) {
				ItemStack elytra = InventoryHelper.getFirstStack(p.getInventory(), Material.ELYTRA);
				if(elytra != null && !EnchantmentManager.hasCustomEnchant(elytra, EnchantmentManager.SOULBIND)) {
					this.complete(p);
				}
			}
		}
	}

}
