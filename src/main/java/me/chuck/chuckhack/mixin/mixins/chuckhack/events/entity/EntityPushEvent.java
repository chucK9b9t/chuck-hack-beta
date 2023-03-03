package me.chuck.chuckhack.mixin.mixins.chuckhack.events.entity;

import me.chuck.chuckhack.events.bus.Cancellable;
import net.minecraft.entity.Entity;

public class EntityPushEvent extends Cancellable {
	public Entity entity;
	
	public EntityPushEvent(Entity entity) {
		this.entity = entity;
	}
}
