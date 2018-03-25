package ru.util;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class EntityHelper {
    public static String getRussianNameByEntity(EntityType type) {
        switch (type) {
            case DROPPED_ITEM:
                return "Брошенный предмет";
            case EXPERIENCE_ORB:
                return "Частица опыта";
            case AREA_EFFECT_CLOUD:
                return "Эффект от оседающего зелья";
            case ELDER_GUARDIAN:
                return "Древний Страж";
            case WITHER_SKELETON:
                return "Скелет-иссушитель";
            case STRAY:
                return "Зимогор";
            case EGG:
                return "Яйцо";
            case LEASH_HITCH:
                return "Поводок";
            case PAINTING:
                return "Картина";
            case ARROW:
                return "Стрела";
            case SNOWBALL:
                return "Снежок";
            case FIREBALL:
                return "Фаербол";
            case SMALL_FIREBALL:
                return "Маленький фаербол";
            case ENDER_PEARL:
                return "Эндерпёрл";
            case ENDER_SIGNAL:
                return "";
            case SPLASH_POTION:
                return "Брошенное зелье";
            case THROWN_EXP_BOTTLE:
                return "Пузырёк опыта";
            case ITEM_FRAME:
                return "Рамка";
            case WITHER_SKULL:
                return "Череп скелета-иссушителя";
            case PRIMED_TNT:
                return "Активированный ТНТ";
            case FALLING_BLOCK:
                return "Падающий блок";
            case FIREWORK:
                return "Фейрверк";
            case HUSK:
                return "Кадавр";
            case SPECTRAL_ARROW:
                return "Спектральная стрела";
            case SHULKER_BULLET:
                return "Снаряд шалкера";
            case DRAGON_FIREBALL:
                return "Кислота Края";
            case ZOMBIE_VILLAGER:
                return "Зомби-житель";
            case SKELETON_HORSE:
                return "Лошадь-скелет";
            case ZOMBIE_HORSE:
                return "Зомби-лошадь";
            case ARMOR_STAND:
                return "Стойка для брони";
            case DONKEY:
                return "Осёл";
            case MULE:
                return "Мул";
            case EVOKER_FANGS:
                return "Челюсти вызывателя";
            case EVOKER:
                return "Вызыватель";
            case VEX:
                return "Досаждатель";
            case VINDICATOR:
                return "Поборник";
            case ILLUSIONER:
                return "Иллюзионист";
            case MINECART_COMMAND:
                return "Вагонетка с командным блоком";
            case BOAT:
                return "Лодка";
            case MINECART:
                return "Вагонетка";
            case MINECART_CHEST:
                return "Вагонетка с сундуком";
            case MINECART_FURNACE:
                return "Вагонетка с печью";
            case MINECART_TNT:
                return "Вагонетка с ТНТ";
            case MINECART_HOPPER:
                return "Вагонетка с воронкой";
            case MINECART_MOB_SPAWNER:
                return "Вагонетка со спавнером";
            case CREEPER:
                return "Крипер";
            case SKELETON:
                return "Скелет";
            case SPIDER:
                return "Паук";
            case GIANT:
                return "Гигант";
            case ZOMBIE:
                return "Зомби";
            case SLIME:
                return "Слизень";
            case GHAST:
                return "Гаст";
            case PIG_ZOMBIE:
                return "Свинозомби";
            case ENDERMAN:
                return "Эндермен";
            case CAVE_SPIDER:
                return "Пещерный паук";
            case SILVERFISH:
                return "Чешуйница";
            case BLAZE:
                return "Ифрит";
            case MAGMA_CUBE:
                return "Магмовый куб";
            case ENDER_DRAGON:
                return "Дракон Края";
            case WITHER:
                return "Иссушитель";
            case BAT:
                return "Летучая мышь";
            case WITCH:
                return "Ведьма";
            case ENDERMITE:
                return "Эндермит";
            case GUARDIAN:
                return "Страж";
            case SHULKER:
                return "Шалкер";
            case PIG:
                return "Свинья";
            case SHEEP:
                return "Овца";
            case COW:
                return "Корова";
            case CHICKEN:
                return "Курица";
            case SQUID:
                return "Осьминог";
            case WOLF:
                return "Волк";
            case MUSHROOM_COW:
                return "Грибная коровка";
            case SNOWMAN:
                return "Снеговик";
            case OCELOT:
                return "Оцелот";
            case IRON_GOLEM:
                return "Железный голем";
            case HORSE:
                return "Лошадь";
            case RABBIT:
                return "Кролик";
            case POLAR_BEAR:
                return "Полярный медведь";
            case LLAMA:
                return "Лама";
            case LLAMA_SPIT:
                return "Плевок ламы";
            case PARROT:
                return "Попугай";
            case VILLAGER:
                return "Житель";
            case ENDER_CRYSTAL:
                return "Кристалл Края";
            case LINGERING_POTION:
                return "Оседающие зелье";
            case FISHING_HOOK:
                return "Рыболовный крючок";
            case LIGHTNING:
                return "Молния";
            case WEATHER:
                return "Погода";
            case PLAYER:
                return "Игрок";
            case COMPLEX_PART:
                return "";
            case TIPPED_ARROW:
                return "Стрела с эффектом";
            case UNKNOWN:
                return "";
            default:
                return "";
        }
    }
}
