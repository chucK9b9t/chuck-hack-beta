package me.chuck.chuckhack.mods.world;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.entity.GetEntitiesEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;

public class NoEntityTrace extends Mod {
	public static Setting toolsOnly = new Setting(Mode.BOOLEAN, "ToolsOnly", true, "Only works if ur holding a tool", "(Pickaxe, Axe, Shovel)");
	
	public NoEntityTrace() {
		super(Group.WORLD, "NoEntityTrace", "Allows you to mine through entities");
	}
	
	@Override
	public void onEnabled() {
		Mod.EVENT_BUS.subscribe(this);
	}
	
	@Override
	public void onDisabled() {
		Mod.EVENT_BUS.unsubscribe(this);
	}
	
    @EventHandler
    private Listener<GetEntitiesEvent> getEntities = new Listener<>(event -> {
    	Item item = mc.player.getHeldItemMainhand().getItem();
        if (toolsOnly.booleanValue() && !(item instanceof ItemTool)) {
        	return;
        }
    	
        event.cancel();
    });
}
