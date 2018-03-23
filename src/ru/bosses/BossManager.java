package ru.bosses;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.metadata.MetadataValue;

import com.google.common.collect.Lists;

import ru.main.HardcorePlugin;

public class BossManager {

	public static final List<Boss> bosses = new ArrayList<Boss>();
	public static final boolean spawnAlways = false;

	public static final Boss pukSpider = new BossPukSpider();
	public static final Boss creeper = new BossCreeper();
	public static final Boss wither = new BossWither();
	public static final Boss zombie = new BossZombie();
	public static final Boss blaze = new BossBlaze();
	public static final Boss skeleton = new BossSkeleton();

	public static void init() {
		bosses.addAll(Lists.<Boss>newArrayList(pukSpider, creeper, wither, zombie, blaze, skeleton));

		Bukkit.getPluginManager().registerEvents(new BossListener(), HardcorePlugin.instance);
		for(Boss boss : bosses) {
			if(boss instanceof Listener) {
				Bukkit.getPluginManager().registerEvents((Listener) boss, HardcorePlugin.instance);
			}
		}
	}

	public static boolean isBoss(Entity e) {
		if(!(e instanceof LivingEntity) || !e.hasMetadata("boss")) return false;
		for(MetadataValue value : e.getMetadata("boss")) {
			if(value.asBoolean()) return true;
		}
		return false;
	}

}
