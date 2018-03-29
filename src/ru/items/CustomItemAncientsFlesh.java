package ru.items;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import ru.util.EntityHelper;
import ru.util.WorldHelper;

import java.util.List;

public class CustomItemAncientsFlesh extends CustomItem implements Listener {

	public String getName() {
		return ChatColor.YELLOW + "Flesh of the Ancients";
	}

	public Material getMaterial() {
		return Material.ROTTEN_FLESH;
	}
	
	public boolean isStackable() {
		return true;
	}
	
	@EventHandler
	public void eat(PlayerItemConsumeEvent e) {
		if(this.isEquals(e.getItem())) {
			Player p = e.getPlayer();
			EntityHelper.addNormalPotionEffect(p, new PotionEffect(PotionEffectType.REGENERATION, 100, 0));
			p.getWorld().strikeLightningEffect(p.getLocation());
			List<Entity> list = WorldHelper.getEntitiesDistance(p.getLocation(), 8);
			for(Entity ent : list) {
				if(ent instanceof LivingEntity && ent != p) {
					Location locP = p.getLocation();
					Location locO = ent.getLocation();

					double d5 = locO.getX() - locP.getX();
					double d7 = locO.getY() + ((LivingEntity) ent).getEyeHeight() - locP.getY();
					double d9 = locO.getZ() - locP.getZ();
					double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

					if(d13 != 0.0D) {
						d5 = d5 / d13;
						d7 = d7 / d13;
						d9 = d9 / d13;

						ent.setVelocity(new Vector(d5, d7, d9).multiply(1.5));
					}
				}
			}
		}
	}

}
