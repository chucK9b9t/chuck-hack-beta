package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.misc;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.other.PacketEvent;
import me.chuck.chuckhack.gui.Group;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class XCarry extends Mod {
	public XCarry() {
		super(Group.MISC, "XCarry", "Allows you to carry items in ur crafting slots");
	}
	
	@Override
	public void onDisabled() {
        if (mc.world != null) {
            mc.player.connection.sendPacket(new CPacketCloseWindow(mc.player.inventoryContainer.windowId));
        }
	}
	
    @EventHandler
    private Listener<PacketEvent> packetEvent = new Listener<>(event -> {
        if (event.packet instanceof CPacketCloseWindow) {
            CPacketCloseWindow packet = (CPacketCloseWindow)event.packet;
            
            if (packet.windowId == mc.player.inventoryContainer.windowId) {
            	event.cancel();
            }
        }
    });
}
