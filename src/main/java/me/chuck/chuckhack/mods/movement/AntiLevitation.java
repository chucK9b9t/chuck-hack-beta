package me.chuck.chuckhack.mods.movement;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.other.IsPotionEffectActiveEvent;
import me.chuck.chuckhack.gui.Group;
import net.minecraft.init.MobEffects;

public class AntiLevitation extends Mod {
	public AntiLevitation() {
		super(Group.MOVEMENT, "AntiLevitation", "Prevents you from levitating");
	}
	
    @EventHandler
    private Listener<IsPotionEffectActiveEvent> IsPotionActive = new Listener<>(event -> {
        if (event.potion == MobEffects.LEVITATION) {
        	event.cancel();
        }
    });
}
