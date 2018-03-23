package ru.achievements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import ru.util.PlayerOptions;

public class AchievementBotania extends ProgressiveAchievement {

	public AchievementBotania() {
		this.setName("Ботаник");
		this.setTask("Собрать все виды маленьких цветочков");
		this.setXp(100);
		List<String> list = new ArrayList<String>();
		list.addAll(Lists.<String>newArrayList("Dandelion", "Poppy", "Blue Orchid", "Allium", "Azure Bluet", "Red Tulip", "Orange Tulip", "White Tulip",
				"Pink Tulip", "Oxeye Daisy"));
		this.setRequirements(list);
		this.setItem(Material.YELLOW_FLOWER);
		this.setDifficulty(Difficulty.EASY);
	}

	public boolean showRequirements(Player p) {
		return PlayerOptions.isActive(p, PlayerOptions.Option.LongRequirements);
	}

	private String getFlowerName(ItemStack item) {
		if(item.getType() == Material.YELLOW_FLOWER) return "Dandelion";
		if(item.getType() == Material.RED_ROSE) {
			switch(item.getDurability()) {
			case 0:
				return "Poppy";
			case 1:
				return "Blue Orchid";
			case 2:
				return "Allium";
			case 3:
				return "Azure Bluet";
			case 4:
				return "Red Tulip";
			case 5:
				return "Orange Tulip";
			case 6:
				return "White Tulip";
			case 7:
				return "Pink Tulip";
			case 8:
				return "Oxeye Daisy";
			}
		}
		return null;
	}

	@EventHandler
	public void achieve(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			String str = getFlowerName(e.getItem().getItemStack());
			if(str != null) {
				this.setRequirement(p, str, true);
				this.tryComplete(p);
			}
		}
	}

}
