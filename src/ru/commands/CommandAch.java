package ru.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.achievements.AchievementManager;
import ru.achievements.ContainerAchievement;
import ru.achievements.DoncAchievement;
import ru.util.GuiHandler;
import ru.util.InventoryHelper;

import java.util.ArrayList;
import java.util.List;

public class CommandAch implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		p.openInventory(generateAchievementInventory(p, false));
		return true;
	}

	public static Inventory generateAchievementInventory(Player p, boolean fromPlugin) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(DoncAchievement a : AchievementManager.achievements) {
			items.add(a.generateItem(p));
		}
		int size = items.size();
		while(size % 9 != 0) {
			size++;
		}
		size += 9;
		List<ContainerAchievement> list = AchievementManager.getContainerAchievements();
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		for(ContainerAchievement ach : list) {
			stacks.add(ach.generateItem(p));
		}
		String title = ChatColor.GREEN + "јчивки " + ChatColor.AQUA + AchievementManager.getProgress(p) + ChatColor.YELLOW + "/" + ChatColor.AQUA
				+ (AchievementManager.achievements.size() + AchievementManager.getContainerAchievements().size());
		size += fromPlugin ? 9 : 0;
		Inventory inv = Bukkit.createInventory(p, size, title);
		InventoryHelper.placeItemsCenter(inv, stacks, size / 9 - (fromPlugin ? 2 : 1));
		for(ItemStack s : items) {
			inv.addItem(s);
		}
		if(fromPlugin) {
			inv.setItem(size - 6, GuiHandler.getCloseItem());
			inv.setItem(size - 4, GuiHandler.getBackItem());
		}
		return inv;
	}

}
