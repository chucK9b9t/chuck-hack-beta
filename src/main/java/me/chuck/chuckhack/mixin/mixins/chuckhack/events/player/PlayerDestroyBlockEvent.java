package me.chuck.chuckhack.mixin.mixins.chuckhack.events.player;

import me.chuck.chuckhack.mixin.mixins.chuckhack.events.bus.Cancellable;
import net.minecraft.util.math.BlockPos;

public class PlayerDestroyBlockEvent extends Cancellable {
	public BlockPos pos;
	
	public PlayerDestroyBlockEvent(BlockPos pos) {
		this.pos = pos;
	}
}
