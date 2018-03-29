package ru.bosses;

import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import ru.main.HardcorePlugin;
import ru.util.MathUtils;

import java.util.List;

public class BossListener implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void otherDamage(EntityDamageEvent e) {
		if(BossManager.isBoss(e.getEntity())) {
			for(Boss boss : BossManager.bosses) {
				if(boss.isEquals(e.getEntity())) {
					boolean entity = e.getCause() == DamageCause.ENTITY_ATTACK || e.getCause() == DamageCause.ENTITY_SWEEP_ATTACK;
					boss.onAnyDamage((LivingEntity) e.getEntity(), e.getCause(), e.getDamage(), entity, e);
					e.getEntity().setFireTicks(0);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void damage(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && BossManager.isBoss(e.getEntity())) {
			for(Boss boss : BossManager.bosses) {
				if(boss.isEquals(e.getEntity())) {
					boss.onDamage((Player) e.getDamager(), (LivingEntity) e.getEntity(), e.getDamage(), e);
				}
			}
		}
		if(e.getEntity() instanceof Player && BossManager.isBoss(e.getDamager())) {
			for(Boss boss : BossManager.bosses) {
				if(boss.isEquals(e.getDamager())) {
					boss.onAttack((LivingEntity) e.getDamager(), (Player) e.getEntity(), e.getDamage(), e);
				}
			}
		}
		if(e.getDamager() instanceof Arrow && BossManager.isBoss(e.getEntity())) {
			for(Boss boss : BossManager.bosses) {
				if(boss.isEquals(e.getEntity())) {
					if(boss.isReflectingArrows()) {
						BossHelper.spawnParticlesAround(e.getEntity(), ParticleEffect.REDSTONE, Color.GRAY, 50);
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void spawn(CreatureSpawnEvent e) {
		if(e.getSpawnReason() == SpawnReason.NATURAL || (e.getSpawnReason() != SpawnReason.CUSTOM && BossManager.spawnAlways)) {
			for(Boss boss : BossManager.bosses) {
				if(boss.getEntity() == e.getEntityType() && (MathUtils.chance(boss.getSpawnChance()) || BossManager.spawnAlways)) {
					LivingEntity ent = e.getEntity();
					ent.setMetadata("boss", new FixedMetadataValue(HardcorePlugin.instance, true));
					BossHelper.setHp(ent, boss.getHP());
					BossHelper.addForceEffect(ent, boss.getEffects().toArray(new PotionEffect[0]));
					for(Attribute a : boss.getAttributes().keySet()) {
						ent.getAttribute(a).setBaseValue(boss.getAttributes().get(a));
					}
					ent.setCustomName(boss.getName());
					ent.setCustomNameVisible(true);
					boss.onSpawn(ent, e);
				}
			}
		}
	}

	@EventHandler
	public void death(EntityDeathEvent e) {
		if(BossManager.isBoss(e.getEntity())) {
			for(Boss boss : BossManager.bosses) {
				if(boss.isEquals(e.getEntity())) {
					LivingEntity ent = e.getEntity();
					Player p = ent.getKiller();
					boolean killed = p != null;
					if(!killed) return; //Now boss will drop items only if it was killed by a player, otherwise boss will drop default items
					e.setDroppedExp(boss.getXP());
					List<ItemStack> drops = e.getDrops();
					drops.clear();
					int looting = 0;
					if(killed) {
						ItemStack sword = p.getInventory().getItemInMainHand();
						if(sword != null) {
							looting = sword.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
						}
					}
					drops.addAll(boss.getDropsAsItems(looting));
					if(boss.canDropHandItem() && MathUtils.chance(boss.getDropHandItemChance()) && ent.getEquipment().getItemInMainHand() != null)
						drops.add(ent.getEquipment().getItemInMainHand());
					boss.onDeath(e.getEntity(), p, killed, e);
				}
			}
		}
	}

	@EventHandler
	public void seePlayer(EntityTargetLivingEntityEvent e) {
		if(BossManager.isBoss(e.getEntity()) && e.getTarget() instanceof Player) {
			for(Boss boss : BossManager.bosses) {
				if(boss.isEquals(e.getEntity())) {
					boss.onSeePlayer((Player) e.getTarget(), (LivingEntity) e.getEntity(), e);
				}
			}
		}
	}

	@EventHandler
	public void preventRename(PlayerInteractEntityEvent e) {
		Entity ent = e.getRightClicked();
		if(ent.getCustomName() != null && BossManager.isBoss(ent)) {
			if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.NAME_TAG
					|| e.getPlayer().getInventory().getItemInOffHand().getType() == Material.NAME_TAG) {
				final String name = ent.getCustomName();
				HardcorePlugin.invokeLater(new Runnable() {

					public void run() {
						ent.setCustomName(name);
					}

				});
			}
		}
	}

}
