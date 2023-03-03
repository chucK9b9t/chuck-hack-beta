package me.chuck.chuckhack.events.other;

import me.chuck.chuckhack.events.bus.Cancellable;
import net.minecraft.network.Packet;

@SuppressWarnings("rawtypes") 
public class PacketEvent extends Cancellable {
	public Packet packet;
	
	public PacketEvent(Packet packet) {
		this.packet = packet;
	}
}