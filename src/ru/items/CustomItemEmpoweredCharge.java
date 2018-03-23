package ru.items;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.slikey.effectlib.util.ParticleEffect;
import ru.util.WorldHelper;

public class CustomItemEmpoweredCharge extends CustomItem implements Listener {

	public String getName() {
		return ChatColor.GOLD + "Empowered Charge";
	}

	public Material getMaterial() {
		return Material.FIREBALL;
	}
	
	public boolean isStackable() {
		return true;
	}
	
	public void onUseRight(Player p, ItemStack item, PlayerInteractEvent e) {
		e.setUseItemInHand(Result.DENY);
	}

	@EventHandler
	public void slow(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof LivingEntity && e.getDamager() instanceof Player) {
			LivingEntity ent = (LivingEntity) e.getEntity();
			Player p = (Player) e.getDamager();
			ItemStack item = p.getInventory().getItemInMainHand();
			if(this.isEquals(item)) {
				item.setAmount(item.getAmount() - 1);
				WorldHelper.spawnParticlesAround(ent, ParticleEffect.CRIT, Color.RED, 20);
				e.setDamage(50);
			}
		}
	}
	
}
