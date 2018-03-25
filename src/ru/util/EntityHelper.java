package ru.util;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class EntityHelper {
    public static String getRussianNameByEntity(EntityType type) {
        switch (type) {
            case DROPPED_ITEM:
                return "��������� �������";
            case EXPERIENCE_ORB:
                return "������� �����";
            case AREA_EFFECT_CLOUD:
                return "������ �� ���������� �����";
            case ELDER_GUARDIAN:
                return "������� �����";
            case WITHER_SKELETON:
                return "������-����������";
            case STRAY:
                return "�������";
            case EGG:
                return "����";
            case LEASH_HITCH:
                return "�������";
            case PAINTING:
                return "�������";
            case ARROW:
                return "������";
            case SNOWBALL:
                return "������";
            case FIREBALL:
                return "�������";
            case SMALL_FIREBALL:
                return "��������� �������";
            case ENDER_PEARL:
                return "��������";
            case ENDER_SIGNAL:
                return "";
            case SPLASH_POTION:
                return "��������� �����";
            case THROWN_EXP_BOTTLE:
                return "������ �����";
            case ITEM_FRAME:
                return "�����";
            case WITHER_SKULL:
                return "����� �������-����������";
            case PRIMED_TNT:
                return "�������������� ���";
            case FALLING_BLOCK:
                return "�������� ����";
            case FIREWORK:
                return "��������";
            case HUSK:
                return "������";
            case SPECTRAL_ARROW:
                return "������������ ������";
            case SHULKER_BULLET:
                return "������ �������";
            case DRAGON_FIREBALL:
                return "������� ����";
            case ZOMBIE_VILLAGER:
                return "�����-������";
            case SKELETON_HORSE:
                return "������-������";
            case ZOMBIE_HORSE:
                return "�����-������";
            case ARMOR_STAND:
                return "������ ��� �����";
            case DONKEY:
                return "���";
            case MULE:
                return "���";
            case EVOKER_FANGS:
                return "������� ����������";
            case EVOKER:
                return "����������";
            case VEX:
                return "�����������";
            case VINDICATOR:
                return "��������";
            case ILLUSIONER:
                return "�����������";
            case MINECART_COMMAND:
                return "��������� � ��������� ������";
            case BOAT:
                return "�����";
            case MINECART:
                return "���������";
            case MINECART_CHEST:
                return "��������� � ��������";
            case MINECART_FURNACE:
                return "��������� � �����";
            case MINECART_TNT:
                return "��������� � ���";
            case MINECART_HOPPER:
                return "��������� � ��������";
            case MINECART_MOB_SPAWNER:
                return "��������� �� ���������";
            case CREEPER:
                return "������";
            case SKELETON:
                return "������";
            case SPIDER:
                return "����";
            case GIANT:
                return "������";
            case ZOMBIE:
                return "�����";
            case SLIME:
                return "�������";
            case GHAST:
                return "����";
            case PIG_ZOMBIE:
                return "����������";
            case ENDERMAN:
                return "��������";
            case CAVE_SPIDER:
                return "�������� ����";
            case SILVERFISH:
                return "���������";
            case BLAZE:
                return "�����";
            case MAGMA_CUBE:
                return "�������� ���";
            case ENDER_DRAGON:
                return "������ ����";
            case WITHER:
                return "����������";
            case BAT:
                return "������� ����";
            case WITCH:
                return "������";
            case ENDERMITE:
                return "��������";
            case GUARDIAN:
                return "�����";
            case SHULKER:
                return "������";
            case PIG:
                return "������";
            case SHEEP:
                return "����";
            case COW:
                return "������";
            case CHICKEN:
                return "������";
            case SQUID:
                return "��������";
            case WOLF:
                return "����";
            case MUSHROOM_COW:
                return "������� �������";
            case SNOWMAN:
                return "��������";
            case OCELOT:
                return "������";
            case IRON_GOLEM:
                return "�������� �����";
            case HORSE:
                return "������";
            case RABBIT:
                return "������";
            case POLAR_BEAR:
                return "�������� �������";
            case LLAMA:
                return "����";
            case LLAMA_SPIT:
                return "������ ����";
            case PARROT:
                return "�������";
            case VILLAGER:
                return "������";
            case ENDER_CRYSTAL:
                return "�������� ����";
            case LINGERING_POTION:
                return "��������� �����";
            case FISHING_HOOK:
                return "���������� ������";
            case LIGHTNING:
                return "������";
            case WEATHER:
                return "������";
            case PLAYER:
                return "�����";
            case COMPLEX_PART:
                return "";
            case TIPPED_ARROW:
                return "������ � ��������";
            case UNKNOWN:
                return "";
            default:
                return "";
        }
    }
}
