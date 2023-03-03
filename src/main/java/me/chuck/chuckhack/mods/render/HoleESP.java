package me.chuck.chuckhack.mods.render;

import java.awt.Color;
import java.util.ArrayList;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.player.PlayerUpdateEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import me.chuck.chuckhack.rendering.RenderBlock.BlockColor;
import me.chuck.chuckhack.rendering.RenderUtil;
import me.chuck.chuckhack.utils.BlockUtil;
import me.chuck.chuckhack.utils.Timer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class HoleESP extends Mod {
	private static Timer timer = new Timer();
	private static ArrayList<BlockColor> holes = new ArrayList<BlockColor>();
	
	public static Setting renderMode = new Setting(null, "RenderMode", "Flat", new String[]{"Flat"}, new String[]{"Rectangle"});
	public static Setting bedrock = new Setting(Mode.BOOLEAN, "Bedrock", true, "Renders full bedrock holes");
	public static Setting obbyBedrock = new Setting(Mode.BOOLEAN, "ObbyBedrock", true, "Renders Obsidian & bedrock holes");
	public static Setting obsidian = new Setting(Mode.BOOLEAN, "Obsidian", true, "Renders full obdisian holes");
	public static Setting radius = new Setting(Mode.INTEGER, "Radius", 10, "Radius around the player to search", "For holes");
	public static Setting alpha = new Setting(Mode.INTEGER, "Alpha", 255, "RBG");
	public static Setting width = new Setting(Mode.DOUBLE, "Width", 1, "Line width");
	
	public HoleESP() {
		super(Group.RENDER, "HoleESP", "Render holes that u can go to", "To avoid crystal damage");
	}
	
	@Override
	public void onDisabled() {
		holes.clear();
	}
	
    @EventHandler
    private Listener<PlayerUpdateEvent> onPlayerUpdate = new Listener<>(event -> {
    	if (timer.hasPassed(100) && mc.player.ticksExisted > 10) {
    		timer.reset();
    		holes.clear();
    		
    		outer: for (BlockPos pos : BlockUtil.getAll(radius.intValue())) {
    			if (isSolid(pos) || isSolid(pos.add(0, 1, 0)) || !isSolid(pos.add(0, -1, 0))) {
    				continue;
    			}
    			
    			BlockPos[] list = {pos.add(1, 0, 0), pos.add(-1, 0, 0), pos.add(0, 0, 1), pos.add(0, 0, -1)};
    			for (BlockPos check : list) {
    				if (getBlock(check) != Blocks.OBSIDIAN && getBlock(check) != Blocks.BEDROCK) {
    					continue outer;
    				}
    			}
    			
    			Color color = Color.RED;
				boolean allBedrock = true;
    			for (BlockPos check : list) {
    				if (getBlock(check) != Blocks.BEDROCK) {
    					allBedrock = false;
    				} else {
    					color = Color.YELLOW;
    				}
    			}
    			
    			if (allBedrock) {
    				color = Color.GREEN;
    			}
    			
    			if (color == Color.GREEN && !bedrock.booleanValue() || color == Color.YELLOW && !obbyBedrock.booleanValue() || color == Color.RED && !obsidian.booleanValue()) {
    				continue;
    			}
    			
    			holes.add(new BlockColor(pos, color, 1));
     		}
    	}
    });
    
    @Override
    public void onRenderWorld(float partialTicks) {
    	for (BlockColor b : holes) {
    		if (renderMode.stringValue().equals("Flat")) {
    			RenderUtil.draw2DRec(RenderUtil.getBB(b.pos.add(0, -1, 0), 1), (float)width.doubleValue(), b.color.getRed() / 255.0f, b.color.getGreen() / 255.0f, b.color.getBlue() / 255.0f, alpha.intValue() / 255.0f);
    		} else if (renderMode.stringValue().equals("Rectangle")) {
    			RenderUtil.drawBoundingBox(RenderUtil.getBB(b.pos, 1), (float)width.doubleValue(), b.color.getRed() / 255.0f, b.color.getGreen() / 255.0f, b.color.getBlue() / 255.0f, alpha.intValue() / 255.0f);
    		}
    	}
    }
}
