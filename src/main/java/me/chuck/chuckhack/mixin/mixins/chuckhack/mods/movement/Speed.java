package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.movement;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.player.PlayerUpdateEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;

public class Speed extends Mod {
	public static Setting speed = new Setting(Mode.DOUBLE, "Speed", 1, "How fast it goes");
		
	public Speed() {
		super(Group.MOVEMENT, "Speed", "Go faster on ground");
	}
	
    @EventHandler
    private Listener<PlayerUpdateEvent> onPlayerUpdate = new Listener<>(event -> {
    	if (!EntitySpeed.isInputting()) {
    		return;
    	}

    	float yaw = calculateYaw();
        mc.renderViewEntity.motionX = -(Math.sin(yaw) * speed.doubleValue());
        mc.renderViewEntity.motionZ = Math.cos(yaw) * speed.doubleValue();
    });

    public static float calculateYaw() {
        float rotationYaw = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        
        float n = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            n = -0.5f;
        } else if (mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        
        if (mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        
        if (mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        
        return rotationYaw * 0.017453292f;
    }
 }