package ru.achievements;

import com.google.common.collect.Lists;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import ru.enchants.EnchantmentManager;

import java.util.ArrayList;
import java.util.List;

public class AchievementEnchantedChainmail extends ProgressiveAchievement {

	public AchievementEnchantedChainmail() {
		this.setName("������ ����� � ����������");
		this.setTask("�������� ��� ��� �������� �� 30");
		this.setInfo("������ �Ĩ� �����");
		this.setXp(400);
		this.setRequirements(Lists.<String>newArrayList("����", "���������", "������", "�������"));
		this.setItem(Material.CHAINMAIL_CHESTPLATE);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	private String getElementNameByMaterial (Material item){
	    switch (item){
            case CHAINMAIL_HELMET: return "����";
            case CHAINMAIL_CHESTPLATE: return "���������";
            case CHAINMAIL_LEGGINGS: return "������";
            case CHAINMAIL_BOOTS: return "�������";
            default: return null;
	    }
    }

    @EventHandler
    public void achieve(EnchantItemEvent e){
        Player p = e.getEnchanter();
        String name = getElementNameByMaterial(e.getItem().getType());
        if ( (name != null) && (e.getExpLevelCost() == 30)){
            this.setRequirement(p,name, true);
            this.tryComplete(p);
        }
    }
}
