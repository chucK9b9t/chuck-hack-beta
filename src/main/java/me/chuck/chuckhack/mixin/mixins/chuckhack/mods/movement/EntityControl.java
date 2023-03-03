package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.movement;

import me.chuck.chuckhack.mixin.mixins.chuckhack.Mod;
import me.chuck.chuckhack.mixin.mixins.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.mixin.mixins.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.mixin.mixins.chuckhack.events.entity.EntitySaddledEvent;
import me.chuck.chuckhack.mixin.mixins.chuckhack.events.entity.SteerEntityEvent;
import me.chuck.chuckhack.mixin.mixins.chuckhack.gui.Group;

public class EntityControl extends Mod {
	public EntityControl() {
		super(Group.MOVEMENT, "EntityControl", "Allows you to control entities without saddle");
	}
	
    @EventHandler
    private Listener<SteerEntityEvent> onSteerEntity = new Listener<>(event -> {
    	event.cancel();
    });

    @EventHandler
    private Listener<EntitySaddledEvent> onEntitySaddled = new Listener<>(event -> {
        event.cancel();
    });
}
