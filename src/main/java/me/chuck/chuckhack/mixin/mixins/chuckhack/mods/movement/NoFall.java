package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.movement;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.other.PacketEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.utils.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;

public class NoFall extends Mod {
	public NoFall() {
		super(Group.MOVEMENT, "NoFall", "Prevents fall damage");
	}
	
    @EventHandler
    private Listener<PacketEvent> packetEvent = new Listener<>(event -> {
    	if (event.packet instanceof CPacketPlayer) {
    		if (mc.player.isElytraFlying() || InventoryUtil.getItemStack(38).getItem() == Items.ELYTRA && mc.gameSettings.keyBindJump.isKeyDown()) {
    			return;
    		}
    		
    		CPacketPlayer packet = (CPacketPlayer)event.packet;
			packet.onGround = true;
    	}
    });
}
