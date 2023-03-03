package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.misc;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import me.chuck.chuckhack.utils.BaritoneUtil;
import me.chuck.chuckhack.utils.EatingUtil;
import me.chuck.chuckhack.utils.InventoryUtil;
import me.chuck.chuckhack.utils.InventoryUtil.ItemStackUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoEat extends Mod {
	private static boolean paused;
	
	public static Setting hungerSetting = new Setting(Mode.INTEGER, "Hunger", 15, "If hunger goes below or equal to this then it eats");
	public static Setting healthSetting = new Setting(Mode.INTEGER, "Health", 10, "If health goes below or equal to this then it eats");
	public static Setting preferGaps = new Setting(Mode.BOOLEAN, "PreferGaps", false, "Prefers gaps over other food");
	public static Setting pauseBaritone = new Setting(Mode.BOOLEAN, "PauseBaritone", true, "Pauses baritone while eating");
	
	public AutoEat() {
		super(Group.MISC, "AutoEat", "Automatically eats food");
	}
	
	@Override
	public void onDisabled() {
		if (paused) {
			BaritoneUtil.sendCommand("resume");
			paused = false;
		}
	}
	
	@SubscribeEvent
	public void onTick(ClientTickEvent e) {
		if (mc.player == null || EatingUtil.isEating()) {
			return;
		}
		
		if (paused) {
			paused = false;
			BaritoneUtil.sendCommand("resume");
		}
		
		float health = mc.player.getHealth() + mc.player.getAbsorptionAmount();
		int hunger = mc.player.getFoodStats().getFoodLevel();
		
		if (health <= healthSetting.intValue() || hunger <= hungerSetting.intValue()) {
			Item food = null;
			int highest = -1;
			
			for (ItemStackUtil itemStack : InventoryUtil.getAllItems()) {
				if (itemStack.itemStack.getItem() instanceof ItemFood) {
					if (preferGaps.booleanValue() && itemStack.itemStack.getItem() == Items.GOLDEN_APPLE) {
						food = itemStack.itemStack.getItem();
						break;
					}
					
					ItemFood itemFood = (ItemFood)itemStack.itemStack.getItem();
					if (itemFood.healAmount > highest) {
						highest = itemFood.healAmount;
						food = itemStack.itemStack.getItem();
					}
				}
			}
			
			if (food != null) {
				if (hunger == 20 && food != Items.GOLDEN_APPLE) {
					return;
				}
				
				if (pauseBaritone.booleanValue()) {
					paused = true;
					BaritoneUtil.sendCommand("pause");
				}
				
				EatingUtil.eatItem(food, false);
			}
		}
	}
}
