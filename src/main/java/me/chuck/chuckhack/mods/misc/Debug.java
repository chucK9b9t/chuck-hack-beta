package me.chuck.chuckhack.mods.misc;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;

public class Debug extends Mod {
    public static Debug instance;

    public Debug() {
        super(Group.MISC, "Debug", "Sends debug chat messages on what some modules are doing", "Useful for development but dont use it otherwise");
        instance = this;
    }

    public static void debug(String message) {
        if (instance.isOn()) {
            Mod.sendMessage(message, false, "Debug");
        }
    }
}
