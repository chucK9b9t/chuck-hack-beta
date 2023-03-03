package me.chuck.chuckhack.events.block;

import me.chuck.chuckhack.events.bus.Cancellable;
import net.minecraft.block.state.IBlockState;

public class CanCollideEvent extends Cancellable {
	public IBlockState state;
	
	public CanCollideEvent(IBlockState state) {
		this.state = state;
	}
}
