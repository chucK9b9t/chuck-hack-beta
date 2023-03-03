package me.chuck.chuckhack.mods.render;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.other.IsPotionEffectActiveEvent;
import me.chuck.chuckhack.events.other.PacketEvent;
import me.chuck.chuckhack.events.render.GetRainStrenghtEvent;
import me.chuck.chuckhack.events.render.RenderArmorLayerEvent;
import me.chuck.chuckhack.events.render.RenderBossHealthEvent;
import me.chuck.chuckhack.events.render.RenderEntityEvent;
import me.chuck.chuckhack.events.render.RenderHurtcamEvent;
import me.chuck.chuckhack.events.render.RenderMapEvent;
import me.chuck.chuckhack.events.render.RenderSignEvent;
import me.chuck.chuckhack.events.render.RenderUpdateLightMapEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRender extends Mod {
	public static Setting noItems = new Setting(Mode.BOOLEAN, "NoItems", false, "Doesnt render items");
	public static Setting fire = new Setting(Mode.BOOLEAN, "Fire", false, "Doesnt render fire overlay");
	public static Setting hurtCamera = new Setting(Mode.BOOLEAN, "HurtCamera", false, "Doesnt render hurtcam effect");
	public static Setting blindness = new Setting(Mode.BOOLEAN, "Blindness", false, "Doesnt render blindness effect");
	public static Setting totemAnimation = new Setting(Mode.BOOLEAN, "TotemAnimation", false, "Doesnt render totem pop animation");
	public static Setting skylight = new Setting(Mode.BOOLEAN, "Skylight", false, "Doesnt render skylight updates");
	public static Setting signText = new Setting(Mode.BOOLEAN, "SignText", false, "Doesnt render text in signs");
	public static Setting noArmor = new Setting(Mode.BOOLEAN, "NoArmor", false, "Doesnt render armor");
	public static Setting maps = new Setting(Mode.BOOLEAN, "Maps", false, "Doesnt render maps");
	public static Setting bossHealth = new Setting(Mode.BOOLEAN, "BossHealth", false, "Doesnt render the boss bar");
	public static Setting weather = new Setting(Mode.BOOLEAN, "Weather", false, "Doesnt render weather");
	public static Setting portal = new Setting(Mode.BOOLEAN, "Portal", false, "Doesnt render the portal effect", "You can also use NoSound in Misc to disable the sound of it");
	
	public NoRender() {
		super(Group.RENDER, "NoRender", "Doesnt render certain things");
	}
	
    @EventHandler
    private Listener<GetRainStrenghtEvent> getRainStrenght = new Listener<>(event -> {
    	if (weather.booleanValue()) {
    		event.value = 0;
    		event.cancel();
    	}
    });
    
    @EventHandler
    private Listener<RenderEntityEvent> onRenderEntity = new Listener<>(event -> {
        if (event.entity instanceof EntityItem && noItems.booleanValue()) {
            event.cancel();
        }
    });
    
    @EventHandler
    private Listener<RenderHurtcamEvent> onRenderHurtcam = new Listener<>(event -> {
    	if (hurtCamera.booleanValue()) {
    		event.cancel();
    	}
    });
    
    @SubscribeEvent
    public void onRenderBlockOverlayEvent(RenderBlockOverlayEvent event) {
        if (fire.booleanValue() && event.getOverlayType() == OverlayType.FIRE) {
        	event.setCanceled(true);
        }
    }
    
    @EventHandler
    private Listener<IsPotionEffectActiveEvent> isPotionActive = new Listener<>(event -> {
        if (event.potion == MobEffects.BLINDNESS && blindness.booleanValue()) {
        	event.cancel();
        }
        
        if (portal.booleanValue() && event.potion == MobEffects.NAUSEA && mc.player != null) {
        	mc.player.removePotionEffect(event.potion);
        }
    });
    
    @EventHandler
    private Listener<PacketEvent> onPacket = new Listener<>(event -> {
        if (event.packet instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus)event.packet;
            
            if (packet.getOpCode() == 35 && totemAnimation.booleanValue()) {
            	event.cancel();
            }
        }
    });
    
    @EventHandler
    private Listener<RenderUpdateLightMapEvent> onSkylightUpdate = new Listener<>(event -> {
    	if (skylight.booleanValue()) {
    		event.cancel();
    	}
    });
    
    @EventHandler
    private Listener<RenderSignEvent> onRenderSign = new Listener<>(event -> {
    	if (signText.booleanValue()) {
    		event.cancel();
    	}
    });
    
    @EventHandler
    private Listener<RenderArmorLayerEvent> onRenderArmorLayer = new Listener<>(event -> {
    	if (noArmor.booleanValue()) {
    		event.cancel();
    	}
    });
    
    @EventHandler
    private Listener<RenderMapEvent> onRenderMap = new Listener<>(event -> {
    	if (maps.booleanValue()) {
    		event.cancel();
    	}
    });
    
    @EventHandler
    private Listener<RenderBossHealthEvent> onRenderBossHealth = new Listener<>(event -> {
    	if (bossHealth.booleanValue()) {
    		event.cancel();
    	}
    });
}
