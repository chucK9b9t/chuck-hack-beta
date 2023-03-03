package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.world;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.player.PlayerMotionUpdateEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import net.minecraft.init.Items;

public class FastUse extends Mod {
	public static Setting xpOnly = new Setting(Mode.BOOLEAN, "XPOnly", false, "Only works for xp");
	
	public FastUse() {
		super(Group.WORLD, "FastUse", "Use items and place blocks with no click delay");
	}
	
    @EventHandler
    private Listener<PlayerMotionUpdateEvent> onPlayerUpdate = new Listener<>(event -> {
    	if (!xpOnly.booleanValue() || xpOnly.booleanValue() && mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE) {
    		mc.rightClickDelayTimer = 0;
    	}
    });
}
