package me.chuck.chuckhack.mixin.mixins.chuckhack.hud.components;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.GuiSettings;
import me.chuck.chuckhack.hud.HudComponent;

public class WatermarkComponent extends HudComponent {
	public WatermarkComponent() {
		super(HudCorner.TOP_LEFT, "Watermark");
	}

	@Override
	public void onRender(float partialTicks) {
		super.onRender(partialTicks);
		drawString(ChatFormatting.BLUE + "ChucK-Hack " + w + " v" + Mod.VERSION, 0, 0, -1, true);
	}
	
	@Override
	public boolean shouldRender() {
		return GuiSettings.waterMark.booleanValue();
	}
}
