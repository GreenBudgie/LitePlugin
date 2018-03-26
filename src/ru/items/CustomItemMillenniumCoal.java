package ru.items;

import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import ru.util.WorldHelper;

public class CustomItemMillenniumCoal extends CustomItem implements Listener {

	public String getName() {
		return ChatColor.LIGHT_PURPLE + "Millennium Coal";
	}

	public Material getMaterial() {
		return Material.COAL;
	}
	
	public boolean isStackable() {
		return true;
	}
	
	@EventHandler
	public void burn(FurnaceBurnEvent e) {
		if(this.isEquals(e.getFuel())) {
			e.setBurnTime(e.getBurnTime() * 250);
			WorldHelper.spawnParticlesOutline(e.getBlock(), ParticleEffect.FLAME, Color.ORANGE, 30);
			e.getBlock().getWorld().playSound(e.getBlock().getLocation(), Sound.ITEM_FIRECHARGE_USE, 1, 0.5F);
		}
	}

}
