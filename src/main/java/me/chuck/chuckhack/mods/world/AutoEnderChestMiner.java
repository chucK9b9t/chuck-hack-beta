package me.chuck.chuckhack.mods.world;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import me.chuck.chuckhack.mods.combat.Surround;
import me.chuck.chuckhack.utils.BlockUtil;
import me.chuck.chuckhack.utils.InventoryUtil;
import me.chuck.chuckhack.utils.MiningUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class AutoEnderChestMiner extends Mod {
	private static Thread thread;
	
	public static Setting delay = new Setting(Mode.INTEGER, "Delay", 350, "Amount to wait after a place/break in milliseconds");
	
	public AutoEnderChestMiner() {
		super(Group.WORLD, "AutoEnderChestMiner", "Places and mines enderchests for obsidian");
	}
	
	@Override
	public void onEnabled() {
		thread = new Thread() {
			public void run() {
				while(thread != null && thread.equals(this)) {
					loop();
					
					Mod.sleep(50);
				}
			}
		};
		
		thread.start();
	}
	
	@Override
	public void onDisabled() {
		Mod.EVENT_BUS.unsubscribe(MiningUtil.miningUtil);
		thread = null;
	}
	
	public void loop() {
		if (mc.player == null) {
			return;
		}
		
		BlockPos[] placements = new BlockPos[] {new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1),
											    new BlockPos(1, 1, 0), new BlockPos(-1, 1, 0), new BlockPos(0, 1, 1), new BlockPos(0, 1, -1)};
		BlockPos availableSpot = null;
		for (BlockPos pos : placements) {
			pos = getPlayerPos().add(pos.getX(), pos.getY(), pos.getZ());
			
			if (getBlock(pos) == Blocks.ENDER_CHEST) {
				MiningUtil.mine(pos, true);
				sleep(delay.intValue());
				return;
			} else if (BlockUtil.canPlaceBlock(pos) && isSolid(pos.add(0, -1, 0))) {
				availableSpot = pos;
				break;
			}
		}

		if (!InventoryUtil.hasBlock(Blocks.ENDER_CHEST)) {
			sendMessage("You dont have any enderchests in your inventory", true);
			disable();
			return;
		} else if (mc.player.posY > 255) {
			sendMessage("Cant place enderchests on the build limit", true);
			disable();
			return;
		}

		if (availableSpot != null) {
			Surround.centerMotionFull();
			BlockUtil.placeBlock(Blocks.ENDER_CHEST, availableSpot, true);
			sleep(delay.intValue());
		} else {
			sendMessage("No spot found to place an enderchest nearby", true);
			disable();
		}
	}
}
