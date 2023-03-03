package me.chuck.chuckhack.mods.movement;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class IceSpeed extends Mod {
	public static Setting speed = new Setting(Mode.DOUBLE, "Speed", 1, "How slippery it will make the ice", "Vanilla value = 0.97");
	
	public IceSpeed() {
		super(Group.MOVEMENT, "IceSpeed", "Move faster on ice");
	}

	@Override
    public void onDisabled() {
        Blocks.ICE.slipperiness = Blocks.PACKED_ICE.slipperiness = Blocks.FROSTED_ICE.slipperiness = 0.97f;
    }
	
	@SubscribeEvent
	public void onTick(ClientTickEvent e) {
		Blocks.ICE.slipperiness = Blocks.PACKED_ICE.slipperiness = Blocks.FROSTED_ICE.slipperiness = (float)speed.doubleValue();
	}
}
