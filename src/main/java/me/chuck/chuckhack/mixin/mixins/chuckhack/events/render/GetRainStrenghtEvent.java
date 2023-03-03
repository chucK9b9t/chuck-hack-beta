package me.chuck.chuckhack.mixin.mixins.chuckhack.events.render;

import me.chuck.chuckhack.events.bus.Cancellable;

public class GetRainStrenghtEvent extends Cancellable {
	public float value;
	
	public GetRainStrenghtEvent(float value) {
		this.value = value;
	}
}
