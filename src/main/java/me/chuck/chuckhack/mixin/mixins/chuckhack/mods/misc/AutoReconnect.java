package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.misc;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;

public class AutoReconnect extends Mod {
	public static AutoReconnect module;
	public static Setting delay = new Setting(Mode.DOUBLE, "Delay", 3, "Delay to wait in seconds");

	public AutoReconnect() {
		super(Group.MISC, "AutoReconnect", "Automatically reconnects to the server");
		module = this;
	}
}