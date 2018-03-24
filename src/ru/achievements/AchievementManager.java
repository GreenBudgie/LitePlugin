package ru.achievements;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.main.HardcorePlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AchievementManager {

	public static final AchievementSpider achSpider = new AchievementSpider();
	public static final AchievementEndermite achEndermite = new AchievementEndermite();
	public static final AchievementEnderman achEnderman = new AchievementEnderman();
	public static final AchievementLavaDiamond achLava = new AchievementLavaDiamond();
	public static final AchievementWither achWither = new AchievementWither();
	public static final AchievementDaniyal achDaniyal = new AchievementDaniyal();
	public static final AchievementMusic achMusic = new AchievementMusic();
	public static final AchievementArmor achArmor = new AchievementArmor();
	public static final AchievementMekita achMekita = new AchievementMekita();
	public static final AchievementSword achSword = new AchievementSword();
	public static final AchievementUltraSword achSword2 = new AchievementUltraSword();
	public static final AchievementDeath achDeath = new AchievementDeath();
	public static final AchievementCreeper achCreeper = new AchievementCreeper();
	public static final AchievementGold achGold = new AchievementGold();
	public static final AchievementFall achFall = new AchievementFall();
	public static final AchievementEnchantments achEnch = new AchievementEnchantments();
	public static final AchievementBlaze achBlaze = new AchievementBlaze();
	public static final AchievementSkeleton achSkeleton = new AchievementSkeleton();
	public static final AchievementGuardian achGuard = new AchievementGuardian();
	public static final AchievementShulker achShulker = new AchievementShulker();
	public static final AchievementBlowDragon achBlowDragon = new AchievementBlowDragon();
	public static final AchievementDirt achDirt = new AchievementDirt();
	public static final AchievementBath achBath = new AchievementBath();
	public static final AchievementDanch achDanch = new AchievementDanch();
	public static final AchievementOrez achOrez = new AchievementOrez();
	public static final AchievementBoss achBoss = new AchievementBoss();
	public static final AchievementTame achTame = new AchievementTame();
	public static final AchievementObsidian achObsidian = new AchievementObsidian();
	public static final AchievementStats achStats = new AchievementStats();
	public static final AchievementVlad achVlad = new AchievementVlad();
	public static final AchievementPukSpider achPukSpider = new AchievementPukSpider();
	public static final AchievementBotania achBotania = new AchievementBotania();
	public static final AchievementChickens achChickens = new AchievementChickens();
	public static final AchievementEnderPearl achEnderPearl = new AchievementEnderPearl();
	public static final AchievementCake achCake = new AchievementCake();
	public static final AchievementMagma achMagma = new AchievementMagma();
	public static final AchievementSoulbind achSoulbind = new AchievementSoulbind();
	public static final AchievementNotDaniyal achNotDaniyal = new AchievementNotDaniyal();
	public static final AchievementOrezTupoi achOrezTupoi = new AchievementOrezTupoi();
	public static final AchievementVindicator achVindicator = new AchievementVindicator();
	public static final AchievementBed achBed = new AchievementBed();
	public static final AchievementEnchantedChainmail achEnchantedChainmail = new AchievementEnchantedChainmail();
	public static final AchievementTNTRevenge achRevenge = new AchievementTNTRevenge();
	public static AchievementDonz achDonz;
	public static AchievementEasy achEasy;
	public static AchievementMedium achMedium;
	public static AchievementHard achHard;
	public static AchievementInsane achInsane;
	public static AchievementAll achAll;

	public static List<DoncAchievement> achievements = new ArrayList<DoncAchievement>();
	public static List<ContainerAchievement> contAchievements = new ArrayList<ContainerAchievement>();

	private static File achFile = new File(HardcorePlugin.instance.getDataFolder() + File.separator + "achievements.yml");
	public static YamlConfiguration achInfo = YamlConfiguration.loadConfiguration(achFile);

	public static void init() {
		registerAll(achSpider, achEndermite, achEnderman, achLava, achWither, achDaniyal, achMusic, achArmor, achMekita, achSword, achSword2, achDeath, achCreeper, achGold,
				achFall, achEnch, achBlaze, achSkeleton, achGuard, achShulker, achBlowDragon, achDirt, achBath, achDanch, achOrez, achBoss, achTame, achObsidian, achStats, achVlad,
				achPukSpider, achBotania, achChickens, achEnderPearl, achCake, achMagma, achSoulbind, achNotDaniyal, achOrezTupoi, achVindicator, achBed, achEnchantedChainmail,
				achRevenge);
		sort();
		registerContainers();
	}

	private static void sort() {
		List<DoncAchievement> achs = Lists.<DoncAchievement>newArrayList(achievements);
		List<DoncAchievement> newAchs = new ArrayList<DoncAchievement>();
		DoncAchievement buffer = null;
		int s = achs.size();
		for(int i = 0; i < s; i++) {
			int min = Integer.MAX_VALUE;
			for(DoncAchievement ach : achs) {
				if(ach.getXp() < min) {
					min = ach.getXp();
					buffer = ach;
				}
			}
			newAchs.add(buffer);
			achs.remove(buffer);
		}
		achievements = Lists.<DoncAchievement>newArrayList(newAchs);
	}

	private static void registerContainers() {
		achDonz = new AchievementDonz();
		achEasy = new AchievementEasy();
		achMedium = new AchievementMedium();
		achHard = new AchievementHard();
		achInsane = new AchievementInsane();
		achAll = new AchievementAll();
		contAchievements.addAll(Lists.<ContainerAchievement>newArrayList(achDonz, achEasy, achMedium, achHard, achInsane, achAll));
	}

	private static void registerAll(DoncAchievement... ach) {
		for(DoncAchievement a : ach) {
			Bukkit.getPluginManager().registerEvents(a, HardcorePlugin.instance);
			achievements.add(a);
		}
	}

	public static void save() {
		try {
			achInfo.save(achFile);
		} catch(IOException e) {
		}
	}

	public static void rewrite(Player p) {
		for(DoncAchievement a : achievements) {
			String path = p.getName() + "." + a.getClass().getSimpleName();
			if(!achInfo.contains(path)) {
				achInfo.set(path + ".completed", false);
				if(a instanceof CounterAchievement) {
					achInfo.set(path + ".progress", 0);
				}
				if(a instanceof ProgressiveAchievement) {
					for(String s : ((ProgressiveAchievement) a).getRequirements().keySet()) {
						achInfo.set(path + ".requirements." + s, false);
					}
				}
			}
			if(a instanceof CounterAchievement && !achInfo.contains(path + ".progress")) {
				achInfo.set(path + ".progress", 0);
			}
			if(a instanceof ProgressiveAchievement && !achInfo.contains(path + ".requirements")) {
				for(String s : ((ProgressiveAchievement) a).getRequirements().keySet()) {
					achInfo.set(path + ".requirements." + s, false);
				}
			}
			if(a instanceof ProgressiveAchievement) {
				for(String s : ((ProgressiveAchievement) a).getRequirements().keySet()) {
					if(!achInfo.contains(path + ".requirements." + s)) {
						achInfo.set(path + ".requirements." + s, false);
					}
				}
				for(String s : achInfo.getConfigurationSection(path + ".requirements").getValues(false).keySet()) {
					if(!((ProgressiveAchievement) a).getRequirements().containsKey(s)) {
						achInfo.set(path + ".requirements." + s, null);
					}
				}
			}
		}
		for(String s : achInfo.getConfigurationSection(p.getName()).getValues(false).keySet()) {
			boolean del = true;
			for(DoncAchievement a : achievements) {
				if(a.getClass().getSimpleName().equalsIgnoreCase(s)) {
					del = false;
					break;
				}
			}
			if(del) {
				achInfo.set(p.getName() + "." + s, null);
			}
		}
		save();
	}

	public static void setCompleted(Player p, DoncAchievement ach, boolean val) {
		achInfo.set(p.getName() + "." + ach.getClass().getSimpleName() + ".completed", val);
		save();
	}

	public static List<ContainerAchievement> getContainerAchievements() {
		return contAchievements;
	}

	public static boolean isCompleted(Player p, DoncAchievement ach) {
		if(ach == null) return true;
		if(!achInfo.contains(p.getName() + "." + ach.getClass().getSimpleName() + ".completed")) return false;
		return achInfo.getBoolean(p.getName() + "." + ach.getClass().getSimpleName() + ".completed");
	}

	public static DoncAchievement getByName(String name) {
		for(DoncAchievement a : achievements) {
			if(a.getClass().getSimpleName().equalsIgnoreCase(name)) return a;
		}
		return null;
	}

	public static int getProgress(Player p) {
		int c = 0;
		for(DoncAchievement a : achievements) {
			if(a.isCompleted(p)) c++;
		}
		for(DoncAchievement a : getContainerAchievements()) {
			if(a.isCompleted(p)) c++;
		}
		return c;
	}

}
