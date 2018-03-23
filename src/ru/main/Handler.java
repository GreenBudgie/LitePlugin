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
				str = MathUtils.choose(name + " зашел на серв", name + " вмутнулся на серв", name + " впукнулся на серв",
						"Онлайн серва пополнился " + Names.formatName(p, Case.INSTRUMENTAL));
			} else {
				str = MathUtils.choose(name + " пришел возмущаться", name + " пробомбил и вернулся в майнкрафт");
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
				str = MathUtils.choose(name + " вышел с серва", name + " вымутнулся с серва", name + " выпукнулся с серва",
						Names.formatName(p, Case.ACCUSATIVE) + ChatColor.YELLOW + " заебал серв", name + " ливнул");
			} else {
				str = MathUtils.choose(name + " сгорел и ливнул с серва", name + " резко ливнул с серва", name + " ушел за квасом",
						"У " + Names.formatName(p, Case.GENITIVE) + ChatColor.YELLOW + " вылетел инет (его согнала мамка)");
			}
			e.setQuitMessage(ChatColor.YELLOW + str);
		} else {
			e.setQuitMessage(PlayerOptions.getMessage(p, false));
		}
	}
	
	@EventHandler
	public void noBreakLog(BlockBreakEvent e) {
		if(EnchantmentTimber.isLog(e.getBlock()) && !DoncPlayer.getPlayer(e.getPlayer().getName()).isVip()) {
			e.getPlayer().sendMessage(ChatColor.RED + "ТЫ НЕ ВИП. ТОЛЬКО ИГРОКИ С ВИП СТАТУСОМ МОГУТ РУБИТЬ ДЕРЕВЬЯ.");
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
				map.put("h", "н");
				map.put("e", "е");
				map.put("a", "а");
				map.put("x", "х");
				map.put("H", "Н");
				map.put("E", "Е");
				map.put("A", "А");
				map.put("X", "Х");
				for(String c : map.keySet()) {
					mes = mes.replaceAll(c, map.get(c));
				}
			}
			e.setFormat("<" + Names.formatName(n) + ChatColor.WHITE + "> " + mes);
		} else {
			String n = Names.formatName(e.getPlayer());
			String r = MathUtils.choose(".", "мда", "гг короче", "пуки не смешные", "плагин говно", "майн приелся", "как вам серв еще не надоел", ",",
					"юзлес plagin", "тратите время на хуйню", "чо", "roflanEbalo", "smorc", "го мм", "БЛЯ МНЕ В ТИМЕ ДАУНЫ БЕЗ МИКРО ПОПАЛИСЬ УДАЛЯЮ КС",
					"найс серв конечно", "ало вы идете мм?", "...", "ты вот сам подумай что ты сейчас написал", "мда данчмод", "ДА ЧТО Я ОТРИЦАЮ",
					"КАКИЕ ОТМАЗКИ Я НИКОГДА НЕ ОТМАЗЫВАЮСЬ", "мммм клас", "roflanPominki", "roflanhmm");
			e.setFormat("<" + n + ChatColor.WHITE + "> " + r);
			if(r.equals("мда") || r.equals(".") || r.equals("...") || r.equals("мда данчмод")) AchievementManager.achDanch.increaseProgress(e.getPlayer());
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
			if(e.getEntity().getCustomName() != null && e.getEntity().getCustomName().trim().toLowerCase().startsWith("васечка")) {
				if(e.getDamager() instanceof Player) {
					Player p = (Player) e.getDamager();
					p.damage(e.getDamage() / 2);
					p.sendMessage(ChatColor.RED + MathUtils.choose("Съебался от Васечки", "Васечка кусается", "Нельзя трогать Васечку",
							"Ты блять ахуел? Его нельзя бить", "Пиздить Васечку - преступление"));
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
		e.setKickMessage(ChatColor.RED + "ПАШОЛ НАХУЙ. ТЕБЯ НЕТ В ВАЙТ ЛИСТЕ. КСТАТИ ТЫ ДАУН. УМРИ.");
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
			mes = name + r + "ВЗОРВАЛСЯ В ПИЗДУ";
			break;
		case CONTACT:
			mes = name + r + "ОРУ БЛЯТЬ СДОХ ОТ КАКТУСА";
			break;
		case CRAMMING:
			mes = name + r + "СДОХ ЗАЖАТЫМ В КУРИЦ ДАУН СУКА";
			break;
		case CUSTOM:
			mes = name + r + "СДОХ ПО НЕЯСНЫМ ПРИЧИНАМ";
			break;
		case DRAGON_BREATH:
			mes = name + r + "СДОХ ОТ ПЛОХОГО ЗАПАХА ИЗО РТА";
			break;
		case DROWNING:
			mes = name + r + "РЕШИЛ ЗАЛЕЧЬ НА ДНО";
			break;
		case ENTITY_ATTACK:
			if(p.getKiller() == null) {
				mes = Names.formatName(p.getName(), Case.ACCUSATIVE).toUpperCase() + r + " ЗАМАЧИЛИ";
			}
			if(p.getKiller() instanceof Player) {
				mes = Names.formatName(p.getName(), Case.ACCUSATIVE).toUpperCase() + r + " ЗАМАЧИЛ " + Names.formatName(p.getKiller()).toUpperCase();
			}
			break;
		case ENTITY_EXPLOSION:
			mes = name + r + "ВЗОРВАЛСЯ В ПИЗДУ";
			break;
		case ENTITY_SWEEP_ATTACK:
			mes = name + r + "УМЕР САМЫМ ТУПЫМ ОБРАЗОМ";
			break;
		case FALL:
			mes = name + r + "РАЗБИЛСЯ";
			break;
		case FALLING_BLOCK:
			mes = name + r + "СПЛЮЩИЛСА";
			break;
		case FIRE:
			mes = name + r + "ПОДНЯЛ ТЕМПЕРАТУРУ ТЕЛА ДО ДЕСЯТНАДЦАТИ";
			break;
		case FIRE_TICK:
			mes = name + r + "НЕ УСПЕЛ ДОБЕЖАТЬ ДО ВОДЫ";
			break;
		case FLY_INTO_WALL:
			mes = name + r + "БЫЛ НАКАЗАН ЗА ЧИТЫ";
			break;
		case HOT_FLOOR:
			mes = name + r + "ХАДИЛ ПО МАГМЕ БЕЗ ШИФТА";
			break;
		case LAVA:
			mes = name + r + "ПРОСРАЛ ВСЕ ВЕЩИ В ЛАВЕ";
			break;
		case LIGHTNING:
			mes = name + r + "ОЧЕНЬ ВЕЗУЧИИ";
			break;
		case MAGIC:
			mes = name + r + "УБИТ КУРИЦЕЙ";
			break;
		case MELTING:
			mes = name + r + "РАСПЛАВИЛСА";
			break;
		case POISON:
			mes = name + r + "УМЕР ОТ ЯДА??? СЕРЬЕЗНО???";
			break;
		case PROJECTILE:
			mes = name + r + "УМЕР ОТ КАТАСТРОФИЧЕСКОЙ КОНЦЕНТРАЦИИ СТРЕЛ В ОРГАНИЗМЕ";
			break;
		case STARVATION:
			mes = name + r + "УМЕР ОТ ГОЛОДА БЛЯТЬ КАК";
			break;
		case SUFFOCATION:
			mes = name + r + "ЗАБЫЛ КАК ДЫШАТЬ";
			break;
		case SUICIDE:
			mes = name + r + "ВЫИГРАЛ В СИНЕГО КИТА";
			break;
		case THORNS:
			mes = name + r + "БОЛЬНО УКОЛОЛСА";
			break;
		case VOID:
			mes = name + r + "ВСРАЛ ВСЕ В БЕЗДНЕ. МИНУТА МОЛЧАНИЯ В ЧЕСТЬ ФЕЙЛОВ ОРЕЗА И ВЛАДА НА ПРОШЛЫХ СЕРВАХ.";
			break;
		case WITHER:
			mes = name + r + "ВЫСОХ";
			break;
		}
		e.setDeathMessage(mes);
	}

}
