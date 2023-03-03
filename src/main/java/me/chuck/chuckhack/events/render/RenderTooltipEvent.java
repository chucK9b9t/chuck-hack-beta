package me.chuck.chuckhack.events.render;

import me.chuck.chuckhack.events.bus.Cancellable;
import net.minecraft.item.ItemStack;

public class RenderTooltipEvent extends Cancellable {
	public int x, y;
	public ItemStack itemStack;
	
	public RenderTooltipEvent(ItemStack itemStack, int x, int y) {
		this.x = x;
		this.y = y;
		this.itemStack = itemStack;
	}
}
