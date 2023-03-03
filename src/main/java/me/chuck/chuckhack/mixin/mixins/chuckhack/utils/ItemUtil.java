package me.chuck.chuckhack.mixin.mixins.chuckhack.utils;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.chuck.chuckhack.Mod;
import net.minecraft.item.ItemStack;

public class ItemUtil extends Mod {

	/**
	 * Get current durability for the itemstack in percentage points
	 */
	public static int getPercentageDurability(ItemStack itemStack) {
		return (int)(((double)getDurability(itemStack) / (double)itemStack.getMaxDamage()) * 100);
	}
	
	/**
	 * Checks if the itemStack has durability like can be broken
	 */
	public static boolean hasDurability(ItemStack itemStack) {
		return itemStack.getMaxDamage() != 0;
	}
	
	
	/**
	 * Gets the durability color like green, yellow, red
	 */
	public static ChatFormatting getDurabilityColor(ItemStack itemStack) {
		ChatFormatting color = ChatFormatting.GREEN;
		int durability = ItemUtil.getPercentageDurability(itemStack);
		
		if (durability < 20) {
			color = ChatFormatting.RED;
		} else if (durability < 60) {
			color = ChatFormatting.GOLD;
		}
		
		return color;
	}
	
	/**
	 * Get durability for the itemStack
	 */
	public static int getDurability(ItemStack itemStack) {
		return itemStack.getMaxDamage() - itemStack.getItemDamage();
	}
}
