package me.chuck.chuckhack.mods.movement;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.entity.AttackEntityEvent;
import me.chuck.chuckhack.events.other.PacketEvent;
import me.chuck.chuckhack.events.other.PacketPostEvent;
import me.chuck.chuckhack.events.player.PlayerUpdateMoveStatePostEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import me.chuck.chuckhack.mods.combat.KillAura;
import me.chuck.chuckhack.mods.misc.Debug;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class NoSlowDown extends Mod {
	public static Setting items = new Setting(Mode.BOOLEAN, "Items", true, "Doesnt slow u down when using an item");
		public static Setting itemsSpeed = new Setting(items, Mode.DOUBLE, "Speed", 0.2, "Higher = slower", "0.2 = no slow down");
	public static Setting hit = new Setting(Mode.BOOLEAN, "Hit", true, "Doesn't stop u from sprinting when u hit an entity");

	public NoSlowDown() {
		super(Group.MOVEMENT, "NoSlowDown", "Doesnt slow u down for certain things", "Also allows you to set the speed so you can", "Play with the values and make them work for different servers");
	}
	
    @EventHandler
    private Listener<PlayerUpdateMoveStatePostEvent> onUpdateMoveState = new Listener<>(event -> {
    	if (items.booleanValue() && mc.player.isHandActive() && !mc.player.isRiding() && !mc.player.isElytraFlying()) {
            mc.player.movementInput.moveForward /= itemsSpeed.doubleValue();
            mc.player.movementInput.moveStrafe /= itemsSpeed.doubleValue();
    	}
    });
    
    @EventHandler
    private Listener<AttackEntityEvent> onAttackEntity = new Listener<>(event -> {
    	if (hit.booleanValue()) {
    		event.cancel();
    		KillAura.attackTargetEntityWithCurrentItem(event.target);
    	}
    });
}
