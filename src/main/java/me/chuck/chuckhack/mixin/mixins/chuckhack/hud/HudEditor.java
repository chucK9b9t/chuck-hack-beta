package me.chuck.chuckhack.mixin.mixins.chuckhack.hud;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.hud.components.ArmorComponent;
import me.chuck.chuckhack.hud.components.ArrayListComponent;
import me.chuck.chuckhack.hud.components.CoordsComponent;
import me.chuck.chuckhack.hud.components.DirectionComponent;
import me.chuck.chuckhack.hud.components.InfoComponent;
import me.chuck.chuckhack.hud.components.LagNotifierComponent;
import me.chuck.chuckhack.hud.components.WatermarkComponent;
import net.minecraftforge.common.MinecraftForge;

public class HudEditor extends Mod {
	public static HudEditor module;
	public static HudEditorGui hudEditorGui = new HudEditorGui();
	
	public HudEditor() {
		super(Group.GUI, "HudEditor", "Change the position of the HUD components");
		initComponents();
		module = this;
	}
	
	@Override
	public void onEnabled() {
		MinecraftForge.EVENT_BUS.register(hudEditorGui);
		mc.displayGuiScreen(hudEditorGui);
	}
	
	@Override
	public void onDisabled() {
		MinecraftForge.EVENT_BUS.unregister(hudEditorGui);
		HudSettings.saveSettings();
	}
	
	public static void initComponents() {
		new ArrayListComponent();
		new WatermarkComponent();
		new ArmorComponent();
		new CoordsComponent();
		new DirectionComponent();
		new InfoComponent();
		new LagNotifierComponent();
	}
}