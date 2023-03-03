package me.chuck.chuckhack.mods.world;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.utils.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class AutoEnderpearl extends Mod {
	public AutoEnderpearl() {
		super(Group.WORLD, "AutoEnderpearl", "Throws enderpearl if you have one", "Also other players will not see", "That you ever held the pearl in ur hand", "When it throws it");
	}
	
	@Override
	public void onEnabled() {
		if (mc.player == null || !InventoryUtil.hasItem(Items.ENDER_PEARL)) {
			disable();
			return;
		}
		
		int oldSlot = mc.player.inventory.currentItem;
		InventoryUtil.switchItem(InventoryUtil.getSlot(Items.ENDER_PEARL), false);
		mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
		InventoryUtil.switchItem(oldSlot, false);
		disable();
	}
}
