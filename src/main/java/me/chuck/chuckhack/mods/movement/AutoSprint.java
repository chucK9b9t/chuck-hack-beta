package me.chuck.chuckhack.mods.movement;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.player.PlayerUpdateMoveStatePostEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;

public class AutoSprint extends Mod {
	public static Setting holdingSprint = new Setting(Mode.BOOLEAN, "HoldingSprint", false, "Only force sprints when holding the sprint button", "This is useful if having issues with sprinting in vanilla", "As it sets sprinting state every game update");

	public AutoSprint() {
		super(Group.MOVEMENT, "AutoSprint", "Makes you allways sprint when walking");
	}
	
    @EventHandler
    private Listener<PlayerUpdateMoveStatePostEvent> onUpdate = new Listener<>(event -> {
		if (holdingSprint.booleanValue() && mc.gameSettings.keyBindSprint.isKeyDown()) {
			mc.player.setSprinting(true);
		} else if (!holdingSprint.booleanValue()) {
			mc.player.setSprinting(true);
		}
    });
}
