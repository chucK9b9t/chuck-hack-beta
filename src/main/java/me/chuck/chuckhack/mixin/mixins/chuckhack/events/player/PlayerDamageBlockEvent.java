package me.chuck.chuckhack.mixin.mixins.chuckhack.events.player;

import me.chuck.chuckhack.events.bus.Cancellable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class PlayerDamageBlockEvent extends Cancellable {
	public BlockPos pos;
	public EnumFacing facing;
	
	public PlayerDamageBlockEvent(BlockPos pos, EnumFacing facing) {
		this.pos = pos;
		this.facing = facing;
	}
}
