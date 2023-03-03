package me.chuck.chuckhack.mixin.mixins.chuckhack.mixin.mixins;

import me.chuck.chuckhack.mixin.mixins.chuckhack.mods.render.Freecam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.renderer.ItemRenderer;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
    @Inject(method = "rotateArm", at = @At("HEAD"), cancellable = true)
    private void rotateArm(float partialTicks, CallbackInfo ci) {
        if (Freecam.isToggled && Freecam.camera != null) {
            ci.cancel();
        }
    }
}
