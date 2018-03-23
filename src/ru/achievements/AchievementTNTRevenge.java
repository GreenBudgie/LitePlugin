package ru.achievements;

import net.minecraft.server.v1_12_R1.EntityCreeper;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import ru.util.WorldHelper;

import java.util.List;

public class AchievementTNTRevenge extends DoncAchievement {

	private static int explodedCreepers;

	public AchievementTNTRevenge() {
		this.setName("����� �� �������");
		this.setTask("�������� 5 �������� ��������� ��� ������ ��������");
		this.setInfo("������ �������� ���� ������� � 25 ������ �� ���������� ��������");
		this.setXp(300);
		this.setItem(Material.SULPHUR);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	@EventHandler
	public void achieve(EntityDeathEvent e) {
		if ((e.getEntity() instanceof EntityCreeper) && ((e.getEntity().getLastDamageCause().getCause() == DamageCause.ENTITY_EXPLOSION) || (e.getEntity().getLastDamageCause().getCause() == DamageCause.BLOCK_EXPLOSION)) ){

		}
	}

}
