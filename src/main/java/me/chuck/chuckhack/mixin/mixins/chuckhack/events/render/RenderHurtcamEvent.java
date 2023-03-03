package me.chuck.chuckhack.mixin.mixins.chuckhack.events.render;

import me.chuck.chuckhack.events.bus.Cancellable;

public class RenderHurtcamEvent extends Cancellable {
	public float ticks;
	
	public RenderHurtcamEvent(float ticks) {
		this.ticks = ticks;
	}
}
