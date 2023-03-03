package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.world;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.other.PacketEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import me.chuck.chuckhack.utils.PlayerUtil;
import me.chuck.chuckhack.utils.Timer;
import net.minecraft.network.play.server.SPacketSoundEffect;

public class AutoFish extends Mod {
	private static Thread thread;
	private static boolean splash;
	private static Timer timer = new Timer();
	
	public static Setting castDelay = new Setting(Mode.INTEGER, "CastDelay", 500, "How long to wait in ms before casting the rod again");
	public static Setting catchDelay = new Setting(Mode.INTEGER, "CatchDelay", 500, "How long to wait in ms before taking the fish out");
	
	public AutoFish() {
		super(Group.WORLD, "AutoFish", "Automatically fishes for you");
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
		thread = null;
		splash = false;
	}
	
	public void loop() {
		if (mc.player == null) {
			return;
		}
		
		if (mc.player.fishEntity == null) {
			sleep(castDelay.intValue());
			PlayerUtil.rightClick();
			timer.reset();
		} else if (splash) {
			sleep(catchDelay.intValue());
			PlayerUtil.rightClick();
			splash = false;
		}
	}
	
    @EventHandler
    private Listener<PacketEvent> packetEvent = new Listener<>(event -> {
    	if (event.packet instanceof SPacketSoundEffect) {
    		SPacketSoundEffect packet = (SPacketSoundEffect)event.packet;
    		
    		if (packet.getSound().getSoundName().getPath().contains("entity.bobber.splash") && timer.hasPassed(3500)) {
    			splash = true;
    		}
    	}
    });
}
