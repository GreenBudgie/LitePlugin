package ru.enchants;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import ru.util.InventoryHelper;
import ru.util.MathUtils;
import ru.util.WorldHelper;

public class EnchantmentTimber extends DoncEnchantment {

	public EnchantmentTimber(int id) {
		super(id);
		this.setDesc("позволяет срубать деревья полностью.");
	}

	public Material getItemToShow() {
		return Material.DIAMOND_AXE;
	}

	public String getName() {
		return "Timber";
	}

	public int getMaxLevel() {
		return 1;
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
		return item.getType() == Material.GOLD_AXE || item.getType() == Material.IRON_AXE || item.getType() == Material.STONE_AXE
				|| item.getType() == Material.DIAMOND_AXE || item.getType() == Material.WOOD_AXE;
	}

	public int tryEnchant(int exp) {
		return exp >= 10 && MathUtils.chance(exp - 5) ? 1 : 0;
	}

	@EventHandler
	public void timber(BlockBreakEvent e) {
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		if(!e.getPlayer().isSneaking() && item != null && item.getType() != Material.ENCHANTED_BOOK && EnchantmentManager.hasCustomEnchant(item, this)
				&& isLog(e.getBlock())) {
			breakChain(e.getBlock(), item, e.getPlayer());
		}
	}

	public static boolean isLog(Block b) {
		return b.getType() == Material.LOG || b.getType() == Material.LOG_2;
	}

	private void breakChain(Block b, ItemStack s, Player p) {
		if(InventoryHelper.damageItem(s, 1)) {
			return;
		}
		((CraftWorld) b.getWorld()).getHandle().setAir(WorldHelper.toBlockPos(b.getLocation()), true);
		List<Block> blocks = WorldHelper.getCuboidAroundNoDown(b.getLocation());
		for(Block block : blocks) {
			if(isLog(block)) {
				breakChain(block, s, p);
			}
		}
	}

}
