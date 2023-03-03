package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.misc;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoSound extends Mod {
	public static Setting portal = new Setting(Mode.BOOLEAN, "Portal", false, "Doesnt play the nether portal sounds");
	
	public NoSound() {
		super(Group.MISC, "NoSound", "Prevents some sounds from playing");
	}
	
	@SubscribeEvent
	public void onSound(PlaySoundEvent event) {
		if (portal.booleanValue() && event.getName().equals("block.portal.ambient") || portal.booleanValue() && event.getName().equals("block.portal.travel") 
		|| portal.booleanValue() && event.getName().equals("block.portal.trigger")) {
			event.setResultSound(null);
		}
	}
}
