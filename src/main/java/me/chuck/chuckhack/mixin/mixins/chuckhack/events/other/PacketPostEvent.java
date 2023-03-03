package me.chuck.chuckhack.mixin.mixins.chuckhack.events.other;

import me.chuck.chuckhack.mixin.mixins.chuckhack.events.bus.Cancellable;
import net.minecraft.network.Packet;

@SuppressWarnings("rawtypes")
public class PacketPostEvent extends Cancellable {
	public Packet packet;
	
	public PacketPostEvent(Packet packet) {
		this.packet = packet;
	}
}
