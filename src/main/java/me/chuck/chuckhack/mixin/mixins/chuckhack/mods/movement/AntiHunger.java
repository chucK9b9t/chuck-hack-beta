package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.movement;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.other.PacketEvent;
import me.chuck.chuckhack.gui.Group;
import net.minecraft.network.play.client.CPacketEntityAction;

public class AntiHunger extends Mod {
	public AntiHunger() {
		super(Group.MOVEMENT, "AntiHunger", "Reduces lost hunger");
	}
	
	@Override
	public void onEnabled() {
		if (mc.player != null && mc.player.isSprinting()) {
			mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
		}
	}
	
    @EventHandler
    private Listener<PacketEvent> packetEvent = new Listener<>(event -> {
    	if (event.packet instanceof CPacketEntityAction) {
        	CPacketEntityAction packet = (CPacketEntityAction)event.packet;
            if (packet.action == CPacketEntityAction.Action.START_SPRINTING || packet.action == CPacketEntityAction.Action.STOP_SPRINTING) {
                event.cancel();
            }
    	}
    });
}
