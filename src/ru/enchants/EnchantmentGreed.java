package ru.enchants;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.util.ParticleEffect;
import ru.bosses.BossHelper;
import ru.util.InventoryHelper;
import ru.util.MathUtils;

public class EnchantmentGreed extends DoncEnchantment {

	public EnchantmentGreed(int id) {
		super(id);
		this.setDesc("вампиризм. При атаке любого моба есть шанс, что твои жизни пополнятся. Однако, нужно быть хотя бы немного сытым (6 ед. голода).");
		this.setOnLevelUp("увеличивается шанс пополнения здоровья а также максимально возможное количество пополнения здоровья.");
		this.setItemToShow(Material.DIAMOND_SWORD);
	}

	public String getName() {
		return "Greed";
	}

	public int getMaxLevel() {
		return 3;
	}

	public int getStartLevel() {
		return 1;
	}

	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.WEAPON;
	}

	public boolean isTreasure() {
		return false;
	}

	public boolean isCursed() {
		return false;
	}

	public boolean conflictsWith(Enchantment other) {
		return false;
	}

	public boolean canEnchantItem(ItemStack item) {
		return true;
	}

	public int tryEnchant(int exp) {
		if(exp > 4 && MathUtils.chance(15 + exp)) {
			int level = 1;
			if(exp >= 8 && MathUtils.chance(50)) {
				level = 2;
			}
			if(exp >= 25) {
				if(MathUtils.chance(exp + 10)) {
					level = 3;
				} else {
					level = 2;
				}
			}
			return level;
		}
		return 0;
	}

	@EventHandler
	public void proceedGreed(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity && e.getCause() == DamageCause.ENTITY_ATTACK) {
			Player p = (Player) e.getDamager();
			ItemStack s = p.getInventory().getItemInMainHand();
			if(s != null && s.getType() != Material.AIR && s.getType() != Material.ENCHANTED_BOOK && EnchantmentManager.hasCustomEnchant(s, EnchantmentManager.GREED)) {
				int level = EnchantmentManager.getCustomEnchant(s, EnchantmentManager.GREED).getLevel();
				double mh = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
				if(Math.random() < level * 0.1 && p.getFoodLevel() > 6) {
					if(p.getHealth() < mh) {
						double hp = Math.floor(e.getDamage() * 0.2);
						double fhp = p.getHealth() + (int) Math.ceil(Math.random() * (level * hp));
						p.setHealth(fhp > mh ? mh : fhp);
						p.getWorld().playSound(e.getEntity().getLocation(), Sound.ITEM_HOE_TILL, 1.5F, 0.5F);
						BossHelper.spawnParticlesAround(e.getEntity(), ParticleEffect.REDSTONE, Color.fromRGB(100, 0, 0), 20);
					}
					if(MathUtils.chance(level * level * 1.5)) {
						BossHelper.spawnParticlesAround(e.getEntity(), ParticleEffect.HEART, 8);
						p.getWorld().playSound(e.getEntity().getLocation(), Sound.BLOCK_NOTE_PLING, 1F, 1.3F);
						PotionEffect pef = new PotionEffect(
								(PotionEffectType) MathUtils.choose(PotionEffectType.INCREASE_DAMAGE, PotionEffectType.REGENERATION),
								MathUtils.randomRange(level * 50, level * 100), (MathUtils.chance(level * level) ? 1 : 0));
						InventoryHelper.addNormalPotionEffect(p, pef);
					} else if(MathUtils.chance(level)) {
						BossHelper.spawnParticlesAround(e.getEntity(), ParticleEffect.FLAME, 20);
						p.getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_EVOCATION_ILLAGER_PREPARE_WOLOLO, 1.5F, 1);
						PotionEffect pef = new PotionEffect(PotionEffectType.ABSORPTION, MathUtils.randomRange(level * 150, level * 300), 2);
						InventoryHelper.addNormalPotionEffect(p, pef);
					}
				}
			}
		}
	}

}
