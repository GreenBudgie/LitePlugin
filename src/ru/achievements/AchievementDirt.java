package ru.achievements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AchievementDirt extends ProgressiveAchievement {

	public AchievementDirt() {
		this.setName("Повелитель Грязи");
		this.setTask("Сделать все возможное с блоком земли");
		this.setXp(70);
		List<String> r = new ArrayList<String>();
		r.add("Сломать грязь");
		r.add("Поставить грязь");
		r.add("Разрыхлить грязь");
		r.add("Посадить цветочек на грязь");
		r.add("Скрафтить каменистую землю");
		r.add("Разрыхлить каменистую землю");
		this.setRequirements(r);
		this.setItem(Material.DIRT);
		this.setItemData(1);
		this.setDifficulty(Difficulty.DONZ);
	}

	public boolean showRequirements(Player p) {
		return true;
	}

	@EventHandler
	public void dirtBreak(BlockBreakEvent e) {
		this.setRequirement(e.getPlayer(), "Сломать грязь", true);
		this.tryComplete(e.getPlayer());
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void place(BlockPlaceEvent e) {
		Block b = e.getBlock();
		Material m = b.getType();
		if(m == Material.DIRT && b.getData() == 0) {
			this.setRequirement(e.getPlayer(), "Поставить грязь", true);
			this.tryComplete(e.getPlayer());
		}
		if(m == Material.YELLOW_FLOWER || m == Material.RED_ROSE || m == Material.DOUBLE_PLANT) {
			if(b.getLocation().clone().add(0, -1, 0).getBlock().getType() == Material.DIRT) {
				this.setRequirement(e.getPlayer(), "Посадить цветочек на грязь", true);
				this.tryComplete(e.getPlayer());
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void interact(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.DIRT && e.getItem() != null) {
			Material m = e.getItem().getType();
			if(m == Material.WOOD_HOE || m == Material.STONE_HOE || m == Material.IRON_HOE || m == Material.GOLD_HOE || m == Material.DIAMOND_HOE) {
				if(e.getClickedBlock().getData() == 0) {
					this.setRequirement(e.getPlayer(), "Разрыхлить грязь", true);
					this.tryComplete(e.getPlayer());
				}
				if(e.getClickedBlock().getData() == 1) {
					this.setRequirement(e.getPlayer(), "Разрыхлить каменистую землю", true);
					this.tryComplete(e.getPlayer());
				}
			}
		}
	}
	
	@EventHandler
	public void craft(CraftItemEvent e) {
		ItemStack dirt = e.getCurrentItem();
		if(dirt.getType() == Material.DIRT && dirt.getDurability() == 1) {
			this.setRequirement((Player) e.getWhoClicked(), "Скрафтить каменистую землю", true);
			this.tryComplete((Player) e.getWhoClicked());
		}
	}

}
