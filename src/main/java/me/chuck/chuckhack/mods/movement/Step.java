package me.chuck.chuckhack.mods.movement;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.player.PlayerMotionUpdateEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import me.chuck.chuckhack.mods.render.Freecam;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class Step extends Mod {
	public static Setting speed = new Setting(Mode.DOUBLE, "Speed", 0.25, "How fast it goes forward");
	
	public Step() {
		super(Group.MOVEMENT, "Step", "Allows you to walk up blocks like stairs");
	}
	
    @EventHandler
    private Listener<PlayerMotionUpdateEvent> onMotionUpdate = new Listener<>(event -> {
        if (mc.player.collidedHorizontally && mc.player.onGround && mc.player.fallDistance == 0.0f && !mc.player.isOnLadder() && !mc.player.movementInput.jump) {
            AxisAlignedBB box = mc.player.getEntityBoundingBox().offset(0.0, 0.05, 0.0).grow(0.05);
            if (!mc.world.getCollisionBoxes(mc.player, box.offset(0.0, 1.0, 0.0)).isEmpty()) {
                return;
            }
            
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));
            mc.player.setPosition(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ);

    		double yawRad = Math.toRadians(mc.player.rotationYaw - Freecam.getRotationFromVec(new Vec3d(-mc.player.moveStrafing, 0.0, mc.player.moveForward))[0]);
    		if (EntitySpeed.isInputting()) {
    			mc.player.motionX = -Math.sin(yawRad) * speed.doubleValue();
    			mc.player.motionZ = Math.cos(yawRad) * speed.doubleValue();
    		}
        }
    });
}
