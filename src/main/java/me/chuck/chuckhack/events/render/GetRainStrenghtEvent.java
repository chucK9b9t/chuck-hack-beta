package me.chuck.chuckhack.events.render;

import me.chuck.chuckhack.events.bus.Cancellable;

public class GetRainStrenghtEvent extends Cancellable {
	public float value;
	
	public GetRainStrenghtEvent(float value) {
		this.value = value;
	}
}
