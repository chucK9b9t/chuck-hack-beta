package me.chuck.chuckhack.mods.render;

import java.awt.Color;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import me.chuck.chuckhack.rendering.RenderBlock.BlockColor;
import me.chuck.chuckhack.rendering.Renderer;
import me.chuck.chuckhack.utils.BlockUtil;
import me.chuck.chuckhack.utils.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoTrapIndicator extends Mod {
	public static Setting lineWidth = new Setting(Mode.INTEGER, "LineWidth", 2, "How thicc the rendered rectangles lines are");
	public static Setting radius = new Setting(Mode.INTEGER, "Radius", 30, "The max distance players can be", "For this to work on them");
	
	public AutoTrapIndicator() {
		super(Group.RENDER, "AutoTrapIndicator", "Indicates if players are standing", "In the middle of the block so they can be trapped", "It draws a red rectangle below them");
	}
	
	@Override
	public void onDisabled() {
		Renderer.rectangles.clear();
	}
	
	@SubscribeEvent
	public void onTick(ClientTickEvent e) {
		Renderer.rectangles.clear();
		
		outer: for (EntityPlayer player : PlayerUtil.getAll()) {
			if (mc.player.getDistance(player) > radius.intValue()) {
				continue;
			}
			
			BlockPos p = new BlockPos(player.posX, player.posY, player.posZ);
			for (BlockPos pos : new BlockPos[]{p.add(1, 0, 0), p.add(-1, 0, 0), p.add(0, 0, 1), p.add(0, 0, -1)}) {
				if (!BlockUtil.canPlaceBlock(pos) && !isSolid(pos)) {
					continue outer;
				}
			}
			
			Renderer.rectangles.add(new BlockColor(p.add(0, -1, 0), Color.RED, lineWidth.intValue()));
		}
	}
}
