package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.movement;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.player.PlayerMotionUpdateEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;

public class Flight extends Mod {
	private static float defaultFlySpeed = -1;
	
	public static Setting speed = new Setting(Mode.DOUBLE, "Speed", 0.05, "Fly speed. Vanilla speed = 0.05");
	public static Setting onlyWhenElytraFlying = new Setting(Mode.BOOLEAN, "OnlyWhenElytraFlying", false, "Only active when elytraflying");
	
	public Flight() {
		super(Group.MOVEMENT, "Flight", "Allows you to fly");
	}
	
    @EventHandler
    private Listener<PlayerMotionUpdateEvent> onMotion = new Listener<>(event -> {
    	if (defaultFlySpeed == -1) {
    		defaultFlySpeed = mc.player.capabilities.getFlySpeed();
    	}
    	
		if (onlyWhenElytraFlying.booleanValue() && !mc.player.isElytraFlying()) {
			mc.player.capabilities.isFlying = false;
			mc.player.capabilities.setFlySpeed(defaultFlySpeed);
		} else {
			mc.player.capabilities.isFlying = true;
			mc.player.capabilities.setFlySpeed((float)speed.doubleValue());
		}
    });
    
    @Override
    public void onDisabled() {
    	mc.player.capabilities.isFlying = false;
    	mc.player.capabilities.setFlySpeed(defaultFlySpeed);
    }
}
