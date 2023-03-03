package me.chuck.chuckhack.mods.movement;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.block.LiquidCollisionEvent;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.player.PlayerUpdateMoveStatePostEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class Jesus extends Mod {
	public static Setting upSpeed = new Setting(Mode.DOUBLE, "UpSpeed", 0.1, "How fast it goes up when underwater");
	
	public Jesus() {
		super(Group.MOVEMENT, "Jesus", "Walk on water");
	}
	
    @EventHandler
    private Listener<PlayerUpdateMoveStatePostEvent> onUpdateMoveState = new Listener<>(event -> {
    	if (getBlock(getPlayerPos()) == Blocks.WATER) {
    		mc.player.motionY = upSpeed.doubleValue();
    	}
    });
    
    @EventHandler
    private Listener<LiquidCollisionEvent> onLiquidCollision = new Listener<>(event -> {
        if (mc.world != null && mc.player != null) {
            if (mc.player.motionY <= 0) {
                event.boundingBox = Block.FULL_BLOCK_AABB;
                event.cancel();
            }
        }
    });
}