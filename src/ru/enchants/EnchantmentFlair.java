package ru.enchants;

import java.lang.reflect.Field;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import ru.util.MathUtils;
import ru.util.PlayerOptions;
import ru.util.WorldHelper;
import ru.util.WorldHelper.EnumDimension;

public class EnchantmentFlair extends DoncEnchantment {

	public EnchantmentFlair(int id) {
		super(id);
		this.setDesc("позволяет автоматически переключаться на инструмент, нужный для добычи того блока, на который ты смотришь.");
		this.setItemToShow(Material.DIAMOND_HELMET);
	}

	public String getName() {
		return "Flair";
	}

	public int getMaxLevel() {
		return 1;
	}

	public int getStartLevel() {
		return 1;
	}

	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.ARMOR_HEAD;
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
		return exp >= 30 && MathUtils.chance(30) ? 1 : 0;
	}

	@EventHandler
	public void proceedFlair(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Player p = e.getPlayer();
			PlayerInventory inv = p.getInventory();
			Block b = e.getClickedBlock();
			Material m = b.getType();
			if(inv.getHelmet() != null) {
				String flair = PlayerOptions.getString(p, PlayerOptions.Option.Flair);
				if((flair.equalsIgnoreCase("on")
						|| (flair.equalsIgnoreCase("miner") && p.getLocation().getY() <= 48 && WorldHelper.getPlayerDimension(p) == EnumDimension.OVERWORLD))
						&& EnchantmentManager.hasCustomEnchant(inv.getHelmet(), this)) {
					if(inv.getItemInMainHand() != null && canBreak(inv.getItemInMainHand(), e.getClickedBlock())) {
						return;
					}
					for(int i = 0; i <= 8; i++) {
						ItemStack item = inv.getItem(i);
						if(item != null) {
							if(canBreak(item, e.getClickedBlock())) {
								inv.setHeldItemSlot(i);
								break;
							}
						}
					}
				}

			}
		}
	}

	@SuppressWarnings("deprecation")
	public static boolean canBreak(ItemStack tool, Block block) {
		Material m = tool.getType();
		if(m == Material.DIAMOND_PICKAXE || m == Material.GOLD_PICKAXE || m == Material.IRON_PICKAXE || m == Material.STONE_PICKAXE
				|| m == Material.WOOD_PICKAXE) {
			Material b = block.getType();
			if(b == Material.QUARTZ_ORE || b == Material.BRICK || b == Material.OBSIDIAN || b == Material.COBBLESTONE_STAIRS || b == Material.SMOOTH_BRICK
					|| b == Material.BRICK_STAIRS || b == Material.SMOOTH_STAIRS || b == Material.NETHER_BRICK || b == Material.NETHER_BRICK_STAIRS
					|| b == Material.ENDER_STONE || b == Material.QUARTZ_BLOCK || b == Material.QUARTZ_STAIRS || b == Material.STAINED_CLAY
					|| b == Material.PRISMARINE || b == Material.HARD_CLAY || b == Material.COAL_BLOCK || b == Material.RED_SANDSTONE_STAIRS
					|| b == Material.PURPUR_BLOCK || b == Material.PURPUR_DOUBLE_SLAB || b == Material.PURPUR_PILLAR || b == Material.PURPUR_SLAB
					|| b == Material.PURPUR_STAIRS || b == Material.END_BRICKS || b == Material.MAGMA || b == Material.RED_NETHER_BRICK
					|| b == Material.BONE_BLOCK || b == Material.CONCRETE || b == Material.NETHER_FENCE || b == Material.ANVIL || b == Material.ENDER_CHEST
					|| b == Material.FURNACE || b == Material.BURNING_FURNACE || b == Material.DROPPER || b == Material.DISPENSER || b == Material.HOPPER
					|| b == Material.OBSERVER || b == Material.GOLD_PLATE || b == Material.IRON_PLATE || b == Material.IRON_TRAPDOOR
					|| b == Material.IRON_DOOR_BLOCK || b == Material.BLACK_SHULKER_BOX || b == Material.BLUE_SHULKER_BOX || b == Material.BROWN_SHULKER_BOX
					|| b == Material.CYAN_SHULKER_BOX || b == Material.GRAY_SHULKER_BOX || b == Material.GREEN_SHULKER_BOX
					|| b == Material.LIGHT_BLUE_SHULKER_BOX || b == Material.LIME_SHULKER_BOX || b == Material.MAGENTA_SHULKER_BOX
					|| b == Material.ORANGE_SHULKER_BOX || b == Material.PINK_SHULKER_BOX || b == Material.PURPLE_SHULKER_BOX || b == Material.RED_SHULKER_BOX
					|| b == Material.SILVER_SHULKER_BOX || b == Material.WHITE_SHULKER_BOX || b == Material.YELLOW_SHULKER_BOX || b == Material.CAULDRON
					|| b == Material.BREWING_STAND || b == Material.SANDSTONE_STAIRS || b == Material.ENCHANTMENT_TABLE || b == Material.IRON_FENCE
					|| b == Material.COBBLE_WALL || b == Material.BLACK_GLAZED_TERRACOTTA || b == Material.BLUE_GLAZED_TERRACOTTA
					|| b == Material.BROWN_GLAZED_TERRACOTTA || b == Material.CYAN_GLAZED_TERRACOTTA || b == Material.GRAY_GLAZED_TERRACOTTA
					|| b == Material.GREEN_GLAZED_TERRACOTTA || b == Material.LIGHT_BLUE_GLAZED_TERRACOTTA || b == Material.LIME_GLAZED_TERRACOTTA
					|| b == Material.MAGENTA_GLAZED_TERRACOTTA || b == Material.ORANGE_GLAZED_TERRACOTTA || b == Material.PINK_GLAZED_TERRACOTTA
					|| b == Material.PURPLE_GLAZED_TERRACOTTA || b == Material.RED_GLAZED_TERRACOTTA || b == Material.SILVER_GLAZED_TERRACOTTA
					|| b == Material.WHITE_GLAZED_TERRACOTTA || b == Material.YELLOW_GLAZED_TERRACOTTA || b == Material.REDSTONE_BLOCK
					|| b == Material.EMERALD_BLOCK) {
				return true;
			}
		}
		if(m == Material.DIAMOND_AXE || m == Material.GOLD_AXE || m == Material.IRON_AXE || m == Material.STONE_AXE || m == Material.WOOD_AXE) {
			Material b = block.getType();
			if(b == Material.NOTE_BLOCK || b == Material.JUKEBOX || b == Material.WALL_SIGN || b == Material.SIGN_POST || b == Material.BIRCH_DOOR
					|| b == Material.ACACIA_DOOR || b == Material.DARK_OAK_DOOR || b == Material.JUNGLE_DOOR || b == Material.SPRUCE_DOOR
					|| b == Material.TRAP_DOOR || b == Material.WOODEN_DOOR || b == Material.TRAPPED_CHEST || b == Material.WORKBENCH
					|| b == Material.FENCE_GATE || b == Material.ACACIA_FENCE_GATE || b == Material.BIRCH_FENCE_GATE || b == Material.DARK_OAK_FENCE_GATE
					|| b == Material.JUNGLE_FENCE_GATE || b == Material.SPRUCE_FENCE_GATE || b == Material.FENCE || b == Material.ACACIA_FENCE
					|| b == Material.BIRCH_FENCE || b == Material.DARK_OAK_FENCE || b == Material.JUNGLE_FENCE || b == Material.SPRUCE_FENCE
					|| b == Material.STANDING_BANNER || b == Material.WALL_BANNER || b == Material.ACACIA_STAIRS || b == Material.BIRCH_WOOD_STAIRS
					|| b == Material.DARK_OAK_STAIRS || b == Material.JUNGLE_WOOD_STAIRS || b == Material.SPRUCE_WOOD_STAIRS || b == Material.WOOD_STAIRS
					|| b == Material.WOOD_STEP || b == Material.WOOD_DOUBLE_STEP) {
				return true;
			}
		}
		if(m == Material.SHEARS) {
			Material b = block.getType();
			if(b == Material.CARPET || b == Material.WOOL) {
				return true;
			}
		}
		int itemId = tool.getTypeId();
		net.minecraft.server.v1_12_R1.Item nmsItem = net.minecraft.server.v1_12_R1.Item.getById(itemId);
		if(nmsItem instanceof net.minecraft.server.v1_12_R1.ItemTool) {
			net.minecraft.server.v1_12_R1.ItemTool item = (net.minecraft.server.v1_12_R1.ItemTool) nmsItem;
			net.minecraft.server.v1_12_R1.Block nmsBlock = net.minecraft.server.v1_12_R1.Block.getById(block.getTypeId());
			try {
				Field field = net.minecraft.server.v1_12_R1.ItemTool.class.getDeclaredField("e");
				field.setAccessible(true);
				@SuppressWarnings("unchecked")
				Set<net.minecraft.server.v1_12_R1.Block> set = (Set<net.minecraft.server.v1_12_R1.Block>) field.get(item);
				if(set.contains(nmsBlock)) {
					return true;
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

}
