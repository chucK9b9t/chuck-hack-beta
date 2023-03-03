package me.chuck.chuckhack.mixin.mixins.chuckhack.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.mods.render.Freecam;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "turn", at = @At("HEAD"), cancellable = true)
    public void turn(float yaw, float pitch, CallbackInfo ci) {
    	if (!Freecam.isToggled || Freecam.camera == null) return;
        Entity entity = (Entity)(Object)this;

        if (entity.equals(Mod.mc.player)) {
        	Freecam.camera.turn(yaw, pitch);
        	ci.cancel();
        }
    }
}
