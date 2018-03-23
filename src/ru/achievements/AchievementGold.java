package ru.achievements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

import com.google.common.collect.Lists;

import ru.util.PlayerOptions;

public class AchievementGold extends ProgressiveAchievement {

	public AchievementGold() {
		this.setName("Донц Тупоишии");
		this.setTask("Скрафтить все золотые предметы, меч, и все виды золотой брони.");
		this.setXp(250);
		this.setRequirements(getAllGoldMaterialsAsString());
		this.setItem(Material.GOLD_INGOT);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	private List<Material> getAllGoldMaterials() {
		List<Material> mat = new ArrayList<Material>();
		mat.addAll(Lists.<Material>newArrayList(Material.GOLD_AXE, Material.GOLD_PICKAXE, Material.GOLD_SPADE, Material.GOLD_HOE, Material.GOLD_SWORD,
				Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS));
		return mat;
	}
	
	private List<String> getAllGoldMaterialsAsString() {
		List<String> str = new ArrayList<String>();
		for(Material m : getAllGoldMaterials()) {
			str.add(m.name());
		}
		return str;
	}

	@EventHandler
	public void achieve(CraftItemEvent e) {
		for(Material m : getAllGoldMaterials()) {
			if(m == e.getCurrentItem().getType()) {
				Player p = (Player) e.getWhoClicked();
				this.setRequirement(p, m.name(), true);
				if(this.isAllCompleted(p)) {
					this.complete(p);
				}
			}
		}
	}

	public boolean showRequirements(Player p) {
		return PlayerOptions.isActive(p, PlayerOptions.Option.LongRequirements);
	}

}
