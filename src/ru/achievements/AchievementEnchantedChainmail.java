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
		this.setName("Лучшая броня в МАЙНКРАФТЕ");
		this.setTask("Зачарить фул сет кольчуги на 30");
		this.setInfo("МЕКИТА ИДЁТ НАХУЙ");
		this.setXp(400);
		this.setRequirements(Lists.<String>newArrayList("Койф", "Нагрудник", "Поножи", "Ботинки"));
		this.setItem(Material.CHAINMAIL_CHESTPLATE);
		this.setDifficulty(Difficulty.MEDIUM);
	}

	private String getElementNameByMaterial (Material item){
	    switch (item){
            case CHAINMAIL_HELMET: return "Койф";
            case CHAINMAIL_CHESTPLATE: return "Нагрудник";
            case CHAINMAIL_LEGGINGS: return "Поножи";
            case CHAINMAIL_BOOTS: return "Ботинки";
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
