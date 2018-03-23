package ru.achievements;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import ru.util.WorldHelper;

public class AchievementLavaDiamond extends DoncAchievement {

	public AchievementLavaDiamond() {
		this.setName("Ночной Кошмар Данияла");
		this.setTask("Просрать алмазный блок");
		this.setInfo("Ачивка выдается всем игрокам в 10 блоках от просранного блока.");
		this.setXp(150);
		this.setItem(Material.DIAMOND_BLOCK);
		this.setDifficulty(Difficulty.EASY);
	}

	@EventHandler
	public void achieve(EntityDamageEvent e) {
		if(e.getEntityType() == EntityType.DROPPED_ITEM) {
			if(((Item) e.getEntity()).getItemStack().getType() == Material.DIAMOND_BLOCK) {
				List<Player> players = WorldHelper.getPlayersDistance(e.getEntity().getLocation(), 10);
				for(Player p : players) {
					this.complete(p);
				}
			}
		}
	}
	
	@EventHandler
	public void achieve2(EntityCombustEvent e) {
		if(e.getEntityType() == EntityType.DROPPED_ITEM) {
			if(((Item) e.getEntity()).getItemStack().getType() == Material.DIAMOND_BLOCK) {
				List<Player> players = WorldHelper.getPlayersDistance(e.getEntity().getLocation(), 10);
				for(Player p : players) {
					this.complete(p);
				}
			}
		}
	}
	
	@EventHandler
	public void achieve2(EntityExplodeEvent e) {
		if(e.getEntityType() == EntityType.DROPPED_ITEM) {
			if(((Item) e.getEntity()).getItemStack().getType() == Material.DIAMOND_BLOCK) {
				List<Player> players = WorldHelper.getPlayersDistance(e.getEntity().getLocation(), 10);
				for(Player p : players) {
					this.complete(p);
				}
			}
		}
	}

}
