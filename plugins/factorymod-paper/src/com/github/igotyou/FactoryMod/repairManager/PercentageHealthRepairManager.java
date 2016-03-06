package com.github.igotyou.FactoryMod.repairManager;

import java.util.Map.Entry;

import org.bukkit.inventory.ItemStack;

import vg.civcraft.mc.civmodcore.itemHandling.ItemMap;

import com.github.igotyou.FactoryMod.FactoryMod;
import com.github.igotyou.FactoryMod.factories.Factory;
import com.github.igotyou.FactoryMod.utility.LoggingUtils;

public class PercentageHealthRepairManager implements IRepairManager {
	private int health;
	private Factory factory;
	private long breakTime;
	private static final int maximumHealth = 10000;

	public PercentageHealthRepairManager(int initialHealth) {
		health = initialHealth;
		breakTime = 0;
	}

	public boolean atFullHealth() {
		return health >= maximumHealth;
	}
	
	public static int getMaximumHealth() {
		return maximumHealth;
	}

	public boolean inDisrepair() {
		return health <= 0;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}

	public String getHealth() {
		return String.valueOf(health / 100) + "."
				+ String.valueOf(health % 100) + " %";
	}

	public void repair(int amount) {
		health = Math.min(health + amount, maximumHealth);
		breakTime = 0;
	}

	public void breakIt() {
		health = 0;
		breakTime = System.currentTimeMillis();
		FactoryMod
				.getPlugin()
				.getServer()
				.getScheduler()
				.scheduleSyncDelayedTask(FactoryMod.getPlugin(),
						new Runnable() {

							@Override
							public void run() {
								if (factory.getMultiBlockStructure()
										.relevantBlocksDestroyed()) {
									LoggingUtils.log(factory.getLogData()
											+ " removed because blocks were destroyed");
									FactoryMod.getManager().removeFactory(
											factory);
									returnStuff(factory);
								}

							}
						});
	}

	public int getRawHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public static void returnStuff(Factory factory) {
		double rate = FactoryMod.getManager().getEgg(factory.getName())
				.getReturnRate();
		if (rate == 0.0) {
			return;
		}
		for (Entry<ItemStack, Integer> items : FactoryMod.getManager()
				.getTotalSetupCost(factory).getEntrySet()) {
			int returnAmount = (int) (items.getValue() * rate);
			ItemMap im = new ItemMap();
			im.addItemAmount(items.getKey(), returnAmount);
			for (ItemStack is : im.getItemStackRepresentation()) {
				if(is.getDurability() == -1) is.setDurability(0);
				factory.getMultiBlockStructure()
						.getCenter()
						.getWorld()
						.dropItemNaturally(
								factory.getMultiBlockStructure().getCenter(),
								is);
			}
		}
	}

	public long getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(long breakTime) {
		this.breakTime = breakTime;
	}
}
