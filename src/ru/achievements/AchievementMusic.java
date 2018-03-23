package ru.achievements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.google.common.collect.Lists;

import ru.util.PlayerOptions;

public class AchievementMusic extends ProgressiveAchievement {

	public AchievementMusic() {
		this.setName("Патимейкер");
		this.setTask("Послушать все пластинки");
		this.setXp(250);
		List<String> records = new ArrayList<String>();
		for(Material m : getAllRecords()) {
			records.add(translateRecord(m));
		}
		this.setRequirements(records);
		this.setItem(Material.JUKEBOX);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	public String translateRecord(Material record) {
		switch(record) {
		case RECORD_10:
			return "ward";
		case RECORD_11:
			return "11";
		case RECORD_12:
			return "wait";
		case RECORD_3:
			return "blocks";
		case RECORD_4:
			return "chirp";
		case RECORD_5:
			return "far";
		case RECORD_6:
			return "mall";
		case RECORD_7:
			return "mellohi";
		case RECORD_8:
			return "stal";
		case RECORD_9:
			return "strad";
		case GOLD_RECORD:
			return "13";
		case GREEN_RECORD:
			return "cat";
		default:
			return "null";
		}
	}

	public List<Material> getAllRecords() {
		List<Material> records = new ArrayList<Material>();
		records.addAll(Lists.<Material>newArrayList(Material.RECORD_10, Material.RECORD_11, Material.RECORD_12, Material.RECORD_3,
				Material.RECORD_4, Material.RECORD_5, Material.RECORD_6, Material.RECORD_7, Material.RECORD_8, Material.RECORD_9,
				Material.GOLD_RECORD, Material.GREEN_RECORD));
		return records;
	}

	@EventHandler
	public void achieve(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem() != null) {
			Block block = e.getClickedBlock();
			if(block.getType() == Material.JUKEBOX && e.getItem().getType().isRecord()) {
				this.setRequirement(e.getPlayer(), this.translateRecord(e.getItem().getType()), true);
				if(isAllCompleted(e.getPlayer())) {
					this.complete(e.getPlayer());
				}
			}
		}
	}
	
	public boolean showRequirements(Player p) {
		return PlayerOptions.isActive(p, PlayerOptions.Option.LongRequirements);
	}

}
