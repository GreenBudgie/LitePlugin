package ru.items;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.util.ParticleEffect;
import ru.util.InventoryHelper;
import ru.util.WorldHelper;

public class CustomItemInfusedString extends CustomItem implements Listener {

	public String getName() {
		return ChatColor.GRAY + "Infused String";
	}

	public Material getMaterial() {
		return Material.STRING;
	}
	
	public boolean isStackable() {
		return true;
	}
	
	@EventHandler
	public void slow(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof LivingEntity && e.getDamager() instanceof Player) {
			LivingEntity ent = (LivingEntity) e.getEntity();
			Player p = (Player) e.getDamager();
			if(this.isEquals(p.getInventory().getItemInMainHand())) {
				InventoryHelper.addNormalPotionEffect(ent, new PotionEffect(PotionEffectType.SLOW, 60, 0));
				WorldHelper.spawnParticlesAround(ent, ParticleEffect.REDSTONE, Color.GRAY, 30);
			}
		}
	}
	
	public void onPlace(Player p, Block b, ItemStack item, BlockPlaceEvent e) {
		e.setCancelled(true);
	}

}
