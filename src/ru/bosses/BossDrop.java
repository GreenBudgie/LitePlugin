package ru.bosses;

import org.bukkit.inventory.ItemStack;
import ru.util.MathUtils;

public class BossDrop {

	private ItemStack item;
	/**
	 * 0 means 100% rarity
	 */
	private int rarity;
	private int min;
	private int max;
	private boolean looting;
	
	public BossDrop(ItemStack item) {
		this(item, item.getAmount(), item.getAmount(), 0, true);
	}
	
	public BossDrop(ItemStack item, int rarity, boolean looting) {
		this(item, item.getAmount(), item.getAmount(), rarity, looting);
	}
	
	public BossDrop(ItemStack item, int min, int max) {
		this(item, min, max, 0, true);
	}

	public BossDrop(ItemStack item, int min, int max, int rarity, boolean looting) {
		if(item == null || min < 1 || max > item.getType().getMaxStackSize()) throw new IllegalArgumentException();
		this.item = item;
		this.min = min;
		this.max = max;
		this.rarity = (int) MathUtils.percent(rarity);
		this.looting = looting;
	}

	public ItemStack getItemStack() {
		return this.getItemStack(0);
	}

	public ItemStack getItemStack(int looting) {
		ItemStack item = this.item;
		if(rarity == 0 || MathUtils.chance(rarity + (looting * 5))) {
			if(!this.looting || looting == 0) {
				item.setAmount(MathUtils.randomRange(min, max));
				return item;
			} else {
				int mss = item.getType().getMaxStackSize();
				int m = max + looting > mss ? mss : max + looting;
				item.setAmount(MathUtils.randomRange(min, m));
				return item;
			}
		}
		return null;
	}

}
