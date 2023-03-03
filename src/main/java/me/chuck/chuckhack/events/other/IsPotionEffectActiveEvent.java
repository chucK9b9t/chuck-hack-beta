package me.chuck.chuckhack.events.other;

import me.chuck.chuckhack.events.bus.Cancellable;
import net.minecraft.potion.Potion;

public class IsPotionEffectActiveEvent extends Cancellable {
	public Potion potion;
	
	public IsPotionEffectActiveEvent(Potion potion) {
		this.potion = potion;
	}
}
