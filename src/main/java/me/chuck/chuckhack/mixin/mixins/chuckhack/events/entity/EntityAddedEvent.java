package me.chuck.chuckhack.mixin.mixins.chuckhack.events.entity;

import me.chuck.chuckhack.events.bus.Cancellable;
import net.minecraft.entity.Entity;

public class EntityAddedEvent extends Cancellable {
	public Entity entity;
	
	public EntityAddedEvent(Entity entity) {
		this.entity = entity;
	}
}
