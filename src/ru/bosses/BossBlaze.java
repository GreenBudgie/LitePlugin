package ru.bosses;

import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import ru.items.CustomItems;
import ru.util.MathUtils;

public class BossBlaze extends Boss implements Listener {

	public BossBlaze() {
		super(EntityType.BLAZE, ChatColor.GOLD + "ВСПОЛОХ");
		this.setSpawnChance(8);
		this.setHP(100);
		this.setXP(120, 150);
		this.addDrop(new BossDrop(new ItemStack(Material.BLAZE_ROD), 1, 3));
		this.addDrop(new BossDrop(new ItemStack(Material.SULPHUR), 1, 4));
		this.addDrop(new BossDrop(CustomItems.innocentFire.getItemStack(), 40, false));
	}

	public void onDamage(Player p, LivingEntity boss, double damage, EntityDamageByEntityEvent e) {
		if(boss.getHealth() <= 50 && MathUtils.chance(30)) {
			p.setFireTicks(p.getFireTicks() + 40);
			BossHelper.spawnParticlesAround(boss, ParticleEffect.FLAME, 30);
		}
	}

	@EventHandler
	public void fireball(ProjectileLaunchEvent e) {
		ProjectileSource src = e.getEntity().getShooter();
		if(src != null && src instanceof Entity && this.isEquals((Entity) e.getEntity().getShooter())) {
			Blaze ent = (Blaze) e.getEntity().getShooter();
			SmallFireball fireball = (SmallFireball) e.getEntity();
			Fireball ball = (Fireball) fireball.getWorld().spawnEntity(fireball.getLocation(), EntityType.FIREBALL);
			ball.setBounce(fireball.doesBounce());
			ball.setShooter(fireball.getShooter());
			ball.setDirection(fireball.getDirection());
			ball.setIsIncendiary(true);
			ball.setYield(1.5F);
			fireball.remove();
		}
	}

	public String getDescription() {
		return "Вместо маленьких фаерболов стреляет большими. Это как гаст, только мощнее, быстрее и опаснее.";
	}

}
