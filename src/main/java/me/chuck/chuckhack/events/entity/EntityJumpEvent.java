package me.chuck.chuckhack.events.entity;

import me.chuck.chuckhack.events.bus.Cancellable;
import net.minecraft.entity.player.EntityPlayer;

public class EntityJumpEvent extends Cancellable {
	public EntityPlayer entity;
	
	public EntityJumpEvent(EntityPlayer entity) {
		this.entity = entity;
	}
}
