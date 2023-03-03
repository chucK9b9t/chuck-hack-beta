package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.render;

import org.lwjgl.input.Keyboard;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Keybind;
import me.chuck.chuckhack.gui.Keybind.KeybindValue;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import me.chuck.chuckhack.gui.Setting.ValueChangedListener;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class Zoom extends Mod {
	private float oldFov;
	
	public static Setting fov = new Setting(Mode.INTEGER, "FOV", 30, "What to change the FOV to when toggled");
	
	public Zoom() {
		super(Group.RENDER, "Zoom", "Zoom like optifine");
	}
	
	@Override
	public void onEnabled() {
		oldFov = mc.gameSettings.fovSetting;
		update();
	}
	
	@Override
	public void onDisabled() {
		mc.gameSettings.fovSetting = oldFov;
		mc.gameSettings.smoothCamera = false;
		mc.entityRenderer.renderHand = true;
	}
	
	@Override
	public void onPostInit() {
		fov.addValueChangedListener(new ValueChangedListener(this, true) {
			public void valueChanged() {
				update();
			}
		});
	}
	
	public void update() {		
		mc.gameSettings.smoothCamera = true;
		mc.gameSettings.fovSetting = fov.intValue();
		mc.entityRenderer.renderHand = false;
	}
	
	@SubscribeEvent
	public void onKeyPress(KeyInputEvent e) {
		for (KeybindValue keybind : Keybind.keybinds) {
			if (keybind.id.replace("Keybind", "").equals(this.name)) {
				if (!Keyboard.isKeyDown(Keyboard.getKeyIndex(keybind.name))) {
					disable();
					return;
				}
			}
		}
	}
}
