package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.world;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoRespawn extends Mod {
	public AutoRespawn() {
		super(Group.WORLD, "AutoRespawn", "Automatically respawns if you die");
	}
	
	@SubscribeEvent
	public void onTick(ClientTickEvent e) {
		if (mc.player != null && mc.player.isDead) {
			mc.player.respawnPlayer();
		}
	}
}
