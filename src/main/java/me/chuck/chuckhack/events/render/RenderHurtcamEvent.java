package me.chuck.chuckhack.events.render;

import me.chuck.chuckhack.events.bus.Cancellable;

public class RenderHurtcamEvent extends Cancellable {
	public float ticks;
	
	public RenderHurtcamEvent(float ticks) {
		this.ticks = ticks;
	}
}
