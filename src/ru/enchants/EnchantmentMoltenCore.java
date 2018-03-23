package ru.enchants;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import de.slikey.effectlib.effect.FlameEffect;
import net.minecraft.server.v1_12_R1.EntityExperienceOrb;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.RecipesFurnace;
import ru.main.HardcorePlugin;
import ru.util.MathUtils;
import ru.util.PlayerOptions;
import ru.util.WorldHelper;
import ru.util.WorldHelper.EnumDimension;

public class EnchantmentMoltenCore extends DoncEnchantment {

	public EnchantmentMoltenCore(int id) {
		super(id);
		this.setDesc(
				"позволяет выкапывать ресурсы и моментально их переплавлять. Например, из железной руды будет выпадать сразу слиток, а из песка - стекло.");
		this.setOnLevelUp(
				"выпадает больше опыта (при уровне I - 0 опыта). Уровни III и IV дают возможность добыть сразу несколько слитков железа или золота при условии, что на кирке есть чар на удачу.");
		this.setItemToShow(Material.DIAMOND_PICKAXE);
	}

	public String getName() {
		return "Molten Core";
	}

	public int getMaxLevel() {
		return 4;
	}

	public int getStartLevel() {
		return 1;
	}

	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.TOOL;
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

	public static boolean isBlacklisted(Material m) {
		return m == Material.COAL_ORE || m == Material.DIAMOND_ORE || m == Material.REDSTONE_ORE || m == Material.QUARTZ_ORE || m == Material.LAPIS_ORE
				|| m == Material.EMERALD_ORE || m == Material.GLOWING_REDSTONE_ORE || m == Material.STAINED_CLAY;
	}

	public int tryEnchant(int exp) {
		if(exp >= 30 && MathUtils.chance(30)) {
			return MathUtils.chance(50) ? (MathUtils.chance(30) ? 3 : 2) : 1;
		}
		return 0;
	}

	@EventHandler
	public void proceedMoltenEntity(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
			Player p = (Player) e.getDamager();
			ItemStack item = p.getInventory().getItemInMainHand();
			if(item != null && item.getType() != Material.AIR && EnchantmentManager.hasCustomEnchant(item, EnchantmentManager.CORE)) {
				int level = EnchantmentManager.getCustomEnchant(item, EnchantmentManager.CORE).getLevel();
				if(level >= 2) {
					LivingEntity ent = (LivingEntity) e.getEntity();
					ent.setFireTicks(level * level * 8);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void proceedMoltenCore(BlockBreakEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		if(item != null && item.getType() != Material.AIR && item.getType() != Material.ENCHANTED_BOOK
				&& EnchantmentManager.hasCustomEnchant(item, EnchantmentManager.CORE)) {
			Block b = e.getBlock();
			Material m = b.getType();
			ItemStack itemToDrop = getSmeltResult(new ItemStack(m));
			String core = PlayerOptions.getString(p, PlayerOptions.Option.MoltenCore);
			boolean shift = PlayerOptions.isActive(p, PlayerOptions.Option.MoltenCoreShift);
			boolean miner = core.equalsIgnoreCase("miner");
			boolean on = core.equalsIgnoreCase("on");
			boolean isCave = (p.getLocation().getY() <= 48 && WorldHelper.getPlayerDimension(p) == EnumDimension.OVERWORLD) || WorldHelper.isOre(m);
			if((!shift || p.isSneaking()) && (on || (miner && (isCave || (shift && p.isSneaking())))) && !EnchantmentMoltenCore.isBlacklisted(m)
					&& itemToDrop != null && itemToDrop.getType() != Material.AIR) {
				int level = EnchantmentManager.getCustomEnchant(item, EnchantmentManager.CORE).getLevel();
				e.setDropItems(false);
				Location loc = b.getLocation();
				loc.add(0.5, 0.5, 0.5);
				if(m == Material.CACTUS) {
					itemToDrop.setDurability(DyeColor.GREEN.getDyeData());
				}
				if(m == Material.LOG || m == Material.LOG_2) {
					itemToDrop.setDurability((short) 1);
				}
				boolean ff = level == 4 || (level == 3 && MathUtils.chance(50));
				boolean isOre = b.getType() == Material.GOLD_ORE || b.getType() == Material.IRON_ORE;
				if(level >= 3 && isOre) {
					int count = 1;
					if(item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
						int fortune = item.getEnchantments().get(Enchantment.LOOT_BONUS_BLOCKS);
						int x = ff ? 2 : 1;
						if(fortune > 1) {
							count = fortune == 2 ? (MathUtils.chance(10 * x) ? 2 : 1) : (MathUtils.chance(6 * x) ? 3 : (MathUtils.chance(20 * x) ? 2 : 1));
						}
					}
					for(int i = 0; i < count; i++) {
						b.getWorld().dropItemNaturally(loc, itemToDrop);
					}
				} else {
					b.getWorld().dropItemNaturally(loc, itemToDrop);
				}
				if(level >= 2) {
					int i = 1;
					float f = getSmeltExp(new ItemStack(m));
					f = f * (level - 1.5F);
					if(f <= 0.0F) {
						i = 0;
					} else if(f < 1.0F) {
						int j = floor_float((float) i * f);
						if(j < ceiling_float_int((float) i * f) && Math.random() < (double) ((float) i * f - (float) j)) {
							j++;
						}
						i = j;
					}
					while(i > 0) {
						int k = net.minecraft.server.v1_12_R1.EntityExperienceOrb.getOrbValue(i);
						i -= k;
						net.minecraft.server.v1_12_R1.World world = MinecraftServer.getServer().getWorld();
						world.addEntity(new EntityExperienceOrb(world, loc.getX(), loc.getY() + 0.5D, loc.getZ() + 0.5D, k));
					}
				}
				loc.getWorld().playSound(loc, Sound.BLOCK_FIRE_AMBIENT, 0.4F, 1);
				loc.setY(loc.getY() - 0.5);
				FlameEffect ef = new FlameEffect(HardcorePlugin.instance.em);
				ef.iterations = 1;
				ef.setLocation(loc);
				ef.start();
			}
		}
	}

	private int floor_float(float value) {
		int i = (int) value;
		return value < (float) i ? i - 1 : i;
	}

	public static int ceiling_float_int(float value) {
		int i = (int) value;
		return value > (float) i ? i + 1 : i;
	}

	private ItemStack getSmeltResult(ItemStack item) {
		return CraftItemStack.asBukkitCopy(RecipesFurnace.getInstance().getResult(CraftItemStack.asNMSCopy(item)));
	}

	private float getSmeltExp(ItemStack item) {
		return RecipesFurnace.getInstance().b(CraftItemStack.asNMSCopy(getSmeltResult(item)));
	}

}
