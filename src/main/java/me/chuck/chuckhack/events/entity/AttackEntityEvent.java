package me.chuck.chuckhack.events.entity;

import me.chuck.chuckhack.events.bus.Cancellable;
import net.minecraft.entity.Entity;

public class AttackEntityEvent extends Cancellable {
	public Entity target;
	
	public AttackEntityEvent(Entity target) {
		this.target = target;
	}
}
