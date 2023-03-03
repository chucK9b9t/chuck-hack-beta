package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.movement;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HighJump extends Mod {
	public static Setting heightAdd = new Setting(Mode.DOUBLE, "HeightAdd", 0.1, "How much higher to jump than normal");
	
	public HighJump() {
		super(Group.MOVEMENT, "HighJump", "Jump higher than normal");
	}
	
	@SubscribeEvent
	public void onLivingJumpEvent(LivingJumpEvent event) {
		if (!event.getEntity().equals(mc.player)) {
			return;
		}
		
		mc.player.motionY += heightAdd.doubleValue();
		mc.player.velocityChanged = true;
	}
}
