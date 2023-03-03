package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.render;

import me.chuck.chuckhack.mixin.mixins.chuckhack.Mod;
import me.chuck.chuckhack.mixin.mixins.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.mixin.mixins.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.mixin.mixins.chuckhack.events.render.SetupFogEvent;
import me.chuck.chuckhack.mixin.mixins.chuckhack.gui.Group;

public class LiquidVision extends Mod {
	public LiquidVision() {
		super(Group.RENDER, "LiquidVision", "Allows you to see clearly in water/lava", "(Stops fog from being rendered)");
	}
	
    @EventHandler
    private Listener<SetupFogEvent> setupFog = new Listener<>(event -> {
    	if (mc.player.ticksExisted < 20) {
    		return;
    	}
    	
    	event.cancel();
    });
}
