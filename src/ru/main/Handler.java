package ru.main;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import ru.achievements.AchievementManager;
import ru.enchants.CustomEnchant;
import ru.enchants.EnchantmentManager;
import ru.enchants.EnchantmentTimber;
import ru.util.Case;
import ru.util.DoncPlayer;
import ru.util.InventoryHelper;
import ru.util.MathUtils;
import ru.util.Names;
import ru.util.PlayerOptions;

public class Handler implements Listener {

	private HardcorePlugin plugin = HardcorePlugin.instance;

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		PlayerOptions.rewrite(p);
		String str = null;
		String name = Names.formatName(p) + ChatColor.YELLOW;
		if(!PlayerOptions.hasMessage(p, true)) {
			if(!PlayerOptions.isActive(p, PlayerOptions.Option.Danchmode) || Names.fillimor.compare(p)) {
				str = MathUtils.choose(name + " ����� �� ����", name + " ��������� �� ����", name + " ��������� �� ����",
						"������ ����� ���������� " + Names.formatName(p, Case.INSTRUMENTAL));
			} else {
				str = MathUtils.choose(name + " ������ �����������", name + " ��������� � �������� � ���������");
			}
			e.setJoinMessage(ChatColor.YELLOW + str);
		} else {
			e.setJoinMessage(PlayerOptions.getMessage(p, true));
		}
		plugin.registerPlayerPosPerm(p);
		plugin.savePosPerm();
		AchievementManager.rewrite(p);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		String str = null;
		String name = Names.formatName(p) + ChatColor.YELLOW;
		if(!PlayerOptions.hasMessage(p, false)) {
			if(!PlayerOptions.isActive(p, PlayerOptions.Option.Danchmode) || Names.fillimor.compare(p)) {
				str = MathUtils.choose(name + " ����� � �����", name + " ���������� � �����", name + " ���������� � �����",
						Names.formatName(p, Case.ACCUSATIVE) + ChatColor.YELLOW + " ������ ����", name + " ������");
			} else {
				str = MathUtils.choose(name + " ������ � ������ � �����", name + " ����� ������ � �����", name + " ���� �� ������",
						"� " + Names.formatName(p, Case.GENITIVE) + ChatColor.YELLOW + " ������� ���� (��� ������� �����)");
			}
			e.setQuitMessage(ChatColor.YELLOW + str);
		} else {
			e.setQuitMessage(PlayerOptions.getMessage(p, false));
		}
	}
	
	@EventHandler
	public void noBreakLog(BlockBreakEvent e) {
		if(EnchantmentTimber.isLog(e.getBlock()) && !DoncPlayer.getPlayer(e.getPlayer().getName()).isVip()) {
			e.getPlayer().sendMessage(ChatColor.RED + "�� �� ���. ������ ������ � ��� �������� ����� ������ �������.");
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void sendMessage(AsyncPlayerChatEvent e) {
		if(!PlayerOptions.isActive(e.getPlayer(), PlayerOptions.Option.Danchmode)) {
			String n = e.getPlayer().getName();
			String mes = e.getMessage();
			if(n.equals("soylamimi")) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("h", "�");
				map.put("e", "�");
				map.put("a", "�");
				map.put("x", "�");
				map.put("H", "�");
				map.put("E", "�");
				map.put("A", "�");
				map.put("X", "�");
				for(String c : map.keySet()) {
					mes = mes.replaceAll(c, map.get(c));
				}
			}
			e.setFormat("<" + Names.formatName(n) + ChatColor.WHITE + "> " + mes);
		} else {
			String n = Names.formatName(e.getPlayer());
			String r = MathUtils.choose(".", "���", "�� ������", "���� �� �������", "������ �����", "���� �������", "��� ��� ���� ��� �� ������", ",",
					"����� plagin", "������� ����� �� �����", "��", "roflanEbalo", "smorc", "�� ��", "��� ��� � ���� ����� ��� ����� �������� ������ ��",
					"���� ���� �������", "��� �� ����� ��?", "...", "�� ��� ��� ������� ��� �� ������ �������", "��� �������", "�� ��� � �������",
					"����� ������� � ������� �� �����������", "���� ����", "roflanPominki", "roflanhmm");
			e.setFormat("<" + n + ChatColor.WHITE + "> " + r);
			if(r.equals("���") || r.equals(".") || r.equals("...") || r.equals("��� �������")) AchievementManager.achDanch.increaseProgress(e.getPlayer());
		}
	}
	
	@EventHandler
	public void dungeonChestFillEnchants(ChunkPopulateEvent e) {
		for(BlockState state : e.getChunk().getTileEntities()) {
			if(state instanceof InventoryHolder) {
				Inventory inv = ((InventoryHolder) state).getInventory();
				for(ItemStack item : inv) {
					if(item != null && InventoryHelper.hasEnchants(item)) {
						EnchantmentManager.enchantItemAsTreasure(item, 75, 3);
					}
				}
			}
		}
	}

	@EventHandler
	public void catchFish(PlayerFishEvent e) {
		if(e.getCaught() instanceof Item) {
			Item item = (Item) e.getCaught();
			if(item != null && item.getItemStack() != null) {
				ItemStack stack = item.getItemStack();
				EnchantmentManager.enchantItemAsTreasure(stack, 75, 1);
			}
		}
	}

	@EventHandler
	public void enchant(EnchantItemEvent e) {
		ItemStack item = e.getItem();
		if(item != null && item.getType() != Material.AIR) {
			if(item.getType() == Material.BOOK) {
				HardcorePlugin.invokeLater(new Runnable() {

					public void run() {
						ItemStack item = e.getInventory().getItem(0);
						EnchantmentManager.enchantBook(item, e.getExpLevelCost());
					}

				});
			} else {
				EnchantmentManager.enchantItem(item, e.getExpLevelCost());
			}
			for(CustomEnchant ench : EnchantmentManager.getCustomEnchantments(item)) {
				AchievementManager.achEnch.setRequirement(e.getEnchanter(), ench.getEnch().getName(), true);
				AchievementManager.achEnch.tryComplete(e.getEnchanter());
			}
		}
	}

	@EventHandler
	public void customEnchantsJoin(PrepareAnvilEvent e) {
		AnvilInventory inv = e.getInventory();
		ItemStack left = inv.getItem(0);
		ItemStack right = inv.getItem(1);
		ItemStack result = e.getResult();
		if(left != null && right != null && result != null) {
			e.setResult(EnchantmentManager.joinCustomEnchantments(left, right, result));
		}
	}

	@EventHandler
	public void protectVasechka(EntityDamageByEntityEvent e) {
		if(e.getEntityType() == EntityType.PARROT) {
			if(e.getEntity().getCustomName() != null && e.getEntity().getCustomName().trim().toLowerCase().startsWith("�������")) {
				if(e.getDamager() instanceof Player) {
					Player p = (Player) e.getDamager();
					p.damage(e.getDamage() / 2);
					p.sendMessage(ChatColor.RED + MathUtils.choose("�������� �� �������", "������� ��������", "������ ������� �������",
							"�� ����� �����? ��� ������ ����", "������� ������� - ������������"));
				}
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void whitelist(AsyncPlayerPreLoginEvent e) {
		for(String name : Names.getAllServerNames()) {
			if(name.equals(e.getName())) return;
		}
		e.setKickMessage(ChatColor.RED + "����� �����. ���� ��� � ���� �����. ������ �� ����. ����.");
		e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST);
	}

	@EventHandler
	public void disableMerge(ItemMergeEvent e) {
		if(e.getEntity().getLocation().distance(e.getTarget().getLocation()) > 1) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void playerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		ChatColor r = ChatColor.RED;
		String name = Names.formatName(p.getName()).toUpperCase() + " ";
		String mes = "";
		switch(p.getLastDamageCause().getCause()) {
		case BLOCK_EXPLOSION:
			mes = name + r + "��������� � �����";
			break;
		case CONTACT:
			mes = name + r + "��� ����� ���� �� �������";
			break;
		case CRAMMING:
			mes = name + r + "���� ������� � ����� ���� ����";
			break;
		case CUSTOM:
			mes = name + r + "���� �� ������� ��������";
			break;
		case DRAGON_BREATH:
			mes = name + r + "���� �� ������� ������ ��� ���";
			break;
		case DROWNING:
			mes = name + r + "����� ������ �� ���";
			break;
		case ENTITY_ATTACK:
			if(p.getKiller() == null) {
				mes = Names.formatName(p.getName(), Case.ACCUSATIVE).toUpperCase() + r + " ��������";
			}
			if(p.getKiller() instanceof Player) {
				mes = Names.formatName(p.getName(), Case.ACCUSATIVE).toUpperCase() + r + " ������� " + Names.formatName(p.getKiller()).toUpperCase();
			}
			break;
		case ENTITY_EXPLOSION:
			mes = name + r + "��������� � �����";
			break;
		case ENTITY_SWEEP_ATTACK:
			mes = name + r + "���� ����� ����� �������";
			break;
		case FALL:
			mes = name + r + "��������";
			break;
		case FALLING_BLOCK:
			mes = name + r + "���������";
			break;
		case FIRE:
			mes = name + r + "������ ����������� ���� �� ������������";
			break;
		case FIRE_TICK:
			mes = name + r + "�� ����� �������� �� ����";
			break;
		case FLY_INTO_WALL:
			mes = name + r + "��� ������� �� ����";
			break;
		case HOT_FLOOR:
			mes = name + r + "����� �� ����� ��� �����";
			break;
		case LAVA:
			mes = name + r + "������� ��� ���� � ����";
			break;
		case LIGHTNING:
			mes = name + r + "����� �������";
			break;
		case MAGIC:
			mes = name + r + "���� �������";
			break;
		case MELTING:
			mes = name + r + "�����������";
			break;
		case POISON:
			mes = name + r + "���� �� ���??? ��������???";
			break;
		case PROJECTILE:
			mes = name + r + "���� �� ���������������� ������������ ����� � ���������";
			break;
		case STARVATION:
			mes = name + r + "���� �� ������ ����� ���";
			break;
		case SUFFOCATION:
			mes = name + r + "����� ��� ������";
			break;
		case SUICIDE:
			mes = name + r + "������� � ������ ����";
			break;
		case THORNS:
			mes = name + r + "������ ��������";
			break;
		case VOID:
			mes = name + r + "����� ��� � ������. ������ �������� � ����� ������ ����� � ����� �� ������� ������.";
			break;
		case WITHER:
			mes = name + r + "�����";
			break;
		}
		e.setDeathMessage(mes);
	}

}
