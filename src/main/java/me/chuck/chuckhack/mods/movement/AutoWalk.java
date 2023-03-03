package me.chuck.chuckhack.mods.movement;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.player.PlayerUpdateMoveStatePostEvent;
import me.chuck.chuckhack.gui.Group;

public class AutoWalk extends Mod {
	public AutoWalk() {
		super(Group.MOVEMENT, "AutoWalk", "Automatically walks forward");
	}
	
    @EventHandler
    private Listener<PlayerUpdateMoveStatePostEvent> onUpdate = new Listener<>(event -> {
        mc.player.movementInput.moveForward++;
        mc.player.movementInput.forwardKeyDown = true;
    });
}
