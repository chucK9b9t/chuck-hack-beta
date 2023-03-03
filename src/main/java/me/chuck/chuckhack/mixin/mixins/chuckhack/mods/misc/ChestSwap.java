package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.misc;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import me.chuck.chuckhack.utils.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ChestSwap extends Mod {
	public static Setting delay = new Setting(Mode.INTEGER, "DelayMS", 0, "How long to wait in milliseconds between the inventory clicks");

	public ChestSwap() {
		super(Group.MISC, "ChestSwap", "Switches chestplate with elytra", "Or elytra with chestplate", "Depending which one ur wearing currently");
	}
	
	@Override
	public void onEnabled() {
		new Thread(() -> {
			ItemStack itemStack = InventoryUtil.getItemStack(38);

			if (itemStack.getItem() == Items.ELYTRA) {
				int slot = getChestPlateSlot();

				if (slot != -1) {
					InventoryUtil.clickSlot(slot);
					sleep(delay.intValue());
					InventoryUtil.clickSlot(38);
					sleep(delay.intValue());
					InventoryUtil.clickSlot(slot);
				} else {
					sendMessage("You dont have a chestplate", true);
				}
			} else if (InventoryUtil.hasItem(Items.ELYTRA)) {
				int slot = InventoryUtil.getSlot(Items.ELYTRA);
				InventoryUtil.clickSlot(slot);
				sleep(delay.intValue());
				InventoryUtil.clickSlot(38);
				sleep(delay.intValue());
				InventoryUtil.clickSlot(slot);
			} else {
				sendMessage("You dont have an elytra", true);
			}

			disable();
		}).start();
	}
	
	public int getChestPlateSlot() {
		Item[] items = {Items.DIAMOND_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.IRON_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.LEATHER_CHESTPLATE};
		
		for (Item item : items) {
			if (InventoryUtil.hasItem(item)) {
				return InventoryUtil.getSlot(item);
			}
		}
		
		return -1;
	}
}
