package ru.achievements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.slikey.effectlib.effect.SphereEffect;
import de.slikey.effectlib.util.ParticleEffect;
import net.md_5.bungee.api.ChatColor;
import ru.main.HardcorePlugin;
import ru.util.InventoryHelper;
import ru.util.Names;

public abstract class DoncAchievement implements Listener {

	private String name = "AchievementName";
	private String task = "AchievementTask";
	private String info = "";
	private int xp = 0;
	private Difficulty difficulty = Difficulty.MEDIUM;
	private Material item = null;
	private int itemData = 0;
	private Map<String, Boolean> progress = null;
	private int currentProgress = 0;
	private int requiredProgress = 0;

	public void complete(Player p) {
		if(isCompleted(p)) return;
		AchievementManager.setCompleted(p, this, true);
		Bukkit.broadcastMessage(Names.formatName(p.getName()) + ChatColor.YELLOW + " получил ачивку " + getDifficulty().color() + getName());
		p.giveExp(getXp());
		p.getWorld().playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);
		for(ContainerAchievement ach : AchievementManager.getContainerAchievements()) {
			ach.tryComplete(p);
		}
		SphereEffect effect = new SphereEffect(HardcorePlugin.instance.em);
		effect.iterations = 15;
		effect.particles = 80;
		effect.period = 2;
		effect.radiusIncrease = 1;
		effect.radius = 3;
		effect.color = getDifficulty().defColor();
		effect.particle = ParticleEffect.REDSTONE;
		effect.setLocation(p.getLocation());
		effect.start();
	}

	public void decomplete(Player p) {
		AchievementManager.setCompleted(p, this, false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getItemData() {
		return itemData;
	}

	public void setItemData(int itemData) {
		this.itemData = itemData;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public boolean isCompleted(Player p) {
		return AchievementManager.isCompleted(p, this);
	}

	public Material getItem() {
		return item;
	}

	public void setItem(Material item) {
		this.item = item;
	}

	public ItemStack generateItem(Player p) {
		Material mat = getItem();
		if(mat != null && mat != Material.AIR) {
			ItemStack item = new ItemStack(mat);
			item.setDurability((short) getItemData());
			ItemMeta meta = item.getItemMeta();
			boolean flag = isCompleted(p);
			if(flag) {
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				meta.addEnchant(Enchantment.OXYGEN, 1, true);
			}
			meta.setDisplayName((flag ? ChatColor.GREEN : ChatColor.RED) + getName());
			List<String> lore = new ArrayList<String>();
			List<String> str1 = InventoryHelper.splitLongString(getTask(), 30);
			for(String s : str1) {
				lore.add(ChatColor.AQUA + s);
			}
			if(!getInfo().isEmpty()) {
				List<String> str2 = InventoryHelper.splitLongString(getInfo(), 30);
				for(String s : str2) {
					lore.add(ChatColor.YELLOW + s);
				}
			}
			lore.add(ChatColor.DARK_GREEN + "Опыта: " + ChatColor.GREEN + getXp());
			lore.add(ChatColor.YELLOW + "Сложность: " + getDifficulty().color() + getDifficulty().getName());
			if(this instanceof ProgressiveAchievement) {
				ProgressiveAchievement ach = (ProgressiveAchievement) this;
				boolean f = ach.isCompleted(p);
				String pr = (f ? ChatColor.GREEN : ChatColor.GOLD) + String.valueOf(ach.getProgress(p)) + ChatColor.YELLOW + "/" + ChatColor.GREEN
						+ ach.getRequiredProgress();
				lore.add(ChatColor.YELLOW + "Прогресс: " + pr);
				if(ach.showRequirements(p)) {
					for(String s : ach.getRequirements().keySet()) {
						ChatColor c = ach.isRequirementCompleted(p, s) ? ChatColor.GREEN : ChatColor.RED;
						lore.add(ChatColor.YELLOW + "- " + c + s);
					}
				}
			}
			if(this instanceof CounterAchievement) {
				CounterAchievement ach = (CounterAchievement) this;
				boolean f = ach.isCompleted(p);
				String pr = (f ? ChatColor.GREEN : ChatColor.GOLD) + String.valueOf(ach.getProgress(p)) + ChatColor.YELLOW + "/" + ChatColor.GREEN
						+ ach.getRequiredProgress();
				lore.add(ChatColor.YELLOW + "Прогресс: " + pr);
			}
			if(this instanceof ContainerAchievement) {
				ContainerAchievement ach = (ContainerAchievement) this;
				boolean f = ach.isCompleted(p);
				String pr = (f ? ChatColor.GREEN : ChatColor.GOLD) + String.valueOf(ach.getProgress(p)) + ChatColor.YELLOW + "/" + ChatColor.GREEN
						+ ach.getRequiredProgress();
				lore.add(ChatColor.YELLOW + "Прогресс: " + pr);
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}
		return null;
	}

}
