package ru.achievements;

import net.minecraft.server.v1_12_R1.EntityCreeper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import ru.main.HardcorePlugin;
import ru.util.WorldHelper;

import java.util.ArrayList;
import java.util.List;

public class AchievementTNTRevenge extends DoncAchievement {

	private static List<Location> explodeLocations = new ArrayList<Location>();
	private static boolean running = false;

	public AchievementTNTRevenge() {
		this.setName("����� �� �������");
		this.setTask("�������� 5 �������� ������������");
		this.setInfo("������ �������� ���� ������� � 25 ������ �� ���������� ��������");
		this.setXp(300);
		this.setItem(Material.SULPHUR);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		if(e.getEntity() instanceof Creeper && (e.getEntity().getLastDamageCause().getCause() == DamageCause.ENTITY_EXPLOSION) || (
				e.getEntity().getLastDamageCause().getCause() == DamageCause.BLOCK_EXPLOSION)) {
			Location l = e.getEntity().getLocation();
			explodeLocations.add(l);
			if(!running) {
				running = true;
				HardcorePlugin.invokeLater(new Runnable() {

					public void run() {
						if(explodeLocations.size() >= 5) {
							int c = 0;
							for(Location loc : explodeLocations) {
								if(l.distance(loc) <= 16) {
									c++;
								}
								if(c >= 5) {
									for(Player p : WorldHelper.getPlayersDistance(l, 25)) {
										complete(p);
									}
									break;
								}
							}
						}
						running = false;
						explodeLocations.clear();
					}

				}, 2L);
			}
		}
	}

}
