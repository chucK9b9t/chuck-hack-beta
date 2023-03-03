package me.chuck.chuckhack.mods.world;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.player.PlayerDamageBlockEvent2;
import me.chuck.chuckhack.events.player.PlayerUpdateEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;

public class SpeedMine extends Mod {
	public static Setting speed = new Setting(Mode.DOUBLE, "Speed", 0.1, "How much more damage it does to the block", "Higher = more");
	public static Setting startDelay = new Setting(Mode.INTEGER, "StartDelay", 0, "How long the start delay will be", "0 = no delay");
	
	public SpeedMine() {
		super(Group.WORLD, "SpeedMine", "Mine blocks faster");
	}

    @EventHandler
    private Listener<PlayerDamageBlockEvent2> onDamageBlock = new Listener<>(event -> {
    	mc.playerController.curBlockDamageMP += speed.doubleValue() / 10;
    });
    
    @EventHandler
    private Listener<PlayerUpdateEvent> onPlayerUpdate = new Listener<>(event -> {
    	mc.playerController.blockHitDelay = startDelay.intValue();
    });
}
