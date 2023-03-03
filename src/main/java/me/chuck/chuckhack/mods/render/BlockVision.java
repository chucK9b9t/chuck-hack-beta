package me.chuck.chuckhack.mods.render;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockVision extends Mod {
	public BlockVision() {
		super(Group.RENDER, "BlockVision", "See clearly when inside blocks");
	}
	
	@SubscribeEvent
	public void onRenderBlockOverlayEvent(RenderBlockOverlayEvent event) {
		if (event.getOverlayType() == OverlayType.BLOCK) {
			event.setCanceled(true);
		}
	}
}
