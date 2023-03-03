package me.chuck.chuckhack.mixin.mixins.chuckhack.events.entity;

import me.chuck.chuckhack.events.bus.Cancellable;
import net.minecraft.entity.Entity;

public class EntityRemovedEvent extends Cancellable {
	public Entity entity;
	
	public EntityRemovedEvent(Entity entity) {
		this.entity = entity;
	}
}
