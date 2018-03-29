package ru.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import ru.enchants.CustomEnchant;
import ru.enchants.EnchantmentManager;
import ru.main.HardcorePlugin;
import ru.util.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandCEnchant implements CommandExecutor, TabCompleter {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(p.isOp() && HardcorePlugin.TEST) {
			if(args.length > 1) {
				CustomEnchant e = EnchantmentManager.getByNameAndLevel(args[0], Integer.valueOf(args[1]));
				EnchantmentManager.addCustomEnchant(p.getInventory().getItemInMainHand(), e, args.length > 2 ? Boolean.valueOf(args[2]) : false);
			}
		}
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(sender.isOp()) {
			if(args.length == 1) {
				List<String> names = new ArrayList<String>();
				for(Enchantment ench : EnchantmentManager.getEnchantments()) {
					names.add(ench.getName().split(" ")[0]);
				}
				return MathUtils.getListOfStringsMatchingLastWord(args, names);
			}
		}
		return null;
	}

}
