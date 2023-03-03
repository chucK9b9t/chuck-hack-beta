package me.chuck.chuckhack.mixin.mixins.chuckhack.events.other;

import me.chuck.chuckhack.events.bus.Cancellable;
import net.minecraft.network.Packet;

@SuppressWarnings("rawtypes") 
public class PacketServerEvent extends Cancellable {
	public Packet packet;
	
	public PacketServerEvent(Packet packet) {
		this.packet = packet;
	}
}