package ru.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.util.MathUtils;
import ru.util.Names;

public class CommandDonc implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String s0 = MathUtils.chance(10) ? MathUtils.choose("����� ", "����� ", "���� ", "������������ ", "� ����� ", "������ ") : "";
		String s1 = MathUtils.choose("����������� ", "����� ", "����������� ", "�������������� ", "���������� ", "������� ", "����� ", "������������� ",
				"������������� ", "�������� ");
		String s1_2 = MathUtils.chance(15) ? MathUtils.choose("����������� ", "����� ", "����������� ", "�������������� ", "���������� ", "������� ", "����� ",
				"������������� ", "������������� ", "�������� ") : "";
		String s2 = MathUtils.choose("����", "����", "�����", "����", "�������", "�������", "���", "����", "����", "���", "���������", "���");
		String s3 = MathUtils.chance(5) ? (", " + MathUtils.choose("�����", "���", "����", "���", "����", "����", "������") + " ��� � "
				+ MathUtils.choose("�����", "���", "����", "���", "�����", "�����", "������") + ".") : "";
		String s = s0 + s1 + (s1_2.equals(s1) ? "" : s1_2) + s2 + s3;
		if(args.length >= 1 && Names.getAllServerNames().contains(args[0])) {
			String s4 = MathUtils.chance(10) ? "������" : "������";
			Bukkit.broadcastMessage(
					Names.formatName(sender.getName()) + ChatColor.YELLOW + " " + s4 + ", ��� " + Names.formatName(args[0]) + " " + ChatColor.YELLOW + s);
		} else {
			Bukkit.broadcastMessage(Names.formatName(sender.getName()) + ChatColor.YELLOW + " " + s);
		}
		return true;
	}

}
