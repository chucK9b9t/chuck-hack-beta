package me.chuck.chuckhack.mixin.mixins.chuckhack.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.entity.EntityPushEvent;
import me.chuck.chuckhack.events.player.PlayerMotionUpdateEvent;
import me.chuck.chuckhack.events.player.PlayerMoveEvent;
import me.chuck.chuckhack.events.player.PlayerUpdateEvent;
import me.chuck.chuckhack.gui.GuiSettings;
import me.chuck.chuckhack.mods.render.Freecam;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.MoverType;
import net.minecraft.world.World;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends MixinEntityPlayer {
	public MixinEntityPlayerSP(World worldIn) {
		super(worldIn);
	}

	@Inject(method = "pushOutOfBlocks(DDD)Z", at = @At("HEAD"), cancellable = true)
    public void pushOutOfBlocks(double x, double y, double z, CallbackInfoReturnable<Boolean> callbackInfo) {
    	EntityPushEvent event = new EntityPushEvent(null);
    	Mod.EVENT_BUS.post(event);
    	
    	if (event.isCancelled()) {
    		callbackInfo.cancel();
    	}
    }
    
    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    public void move(MoverType type, double x, double y, double z, CallbackInfo callbackInfo) {
    	PlayerMoveEvent event = new PlayerMoveEvent(type, x, y, z);
    	Mod.EVENT_BUS.post(event);
    	
    	if (event.isCancelled()) {
    		super.move(type, event.x, event.y, event.z);
    		callbackInfo.cancel();
    	}
    }
    
    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    public void motionUpdate(CallbackInfo callbackInfo) {
    	PlayerMotionUpdateEvent event = new PlayerMotionUpdateEvent();
    	Mod.EVENT_BUS.post(event);
    	
    	if (event.isCancelled()) {
    		callbackInfo.cancel();
    	}
    }
    
    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void onUpdate(CallbackInfo callbackInfo) {
    	PlayerUpdateEvent event = new PlayerUpdateEvent();
    	Mod.EVENT_BUS.post(event);
    	
    	if (event.isCancelled()) {
    		callbackInfo.cancel();
    	}
    }
    
    @Inject(method = "isCurrentViewEntity", at = @At("RETURN"), cancellable = true)
    protected void isCurrentViewEntity(CallbackInfoReturnable<Boolean> cir) {
        if (Freecam.isToggled && Freecam.camera != null) {
            cir.setReturnValue(Mod.mc.renderViewEntity.equals(Freecam.camera));
        }
    }
    
    @Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V"))
    public void closeScreen(EntityPlayerSP player) {
        if (!GuiSettings.portalGui.booleanValue()) {
        	player.closeScreen();
        }
    }

    @Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
    public void closeScreen(Minecraft minecraft, GuiScreen screen) {
        if (!GuiSettings.portalGui.booleanValue()) {
        	Mod.mc.displayGuiScreen(screen);
        }
    }
}
