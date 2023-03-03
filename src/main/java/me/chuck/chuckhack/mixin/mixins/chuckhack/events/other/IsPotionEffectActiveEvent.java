package me.chuck.chuckhack.mixin.mixins.chuckhack.events.other;

import me.chuck.chuckhack.mixin.mixins.chuckhack.events.bus.Cancellable;
import net.minecraft.potion.Potion;

public class IsPotionEffectActiveEvent extends Cancellable {
	public Potion potion;
	
	public IsPotionEffectActiveEvent(Potion potion) {
		this.potion = potion;
	}
}
