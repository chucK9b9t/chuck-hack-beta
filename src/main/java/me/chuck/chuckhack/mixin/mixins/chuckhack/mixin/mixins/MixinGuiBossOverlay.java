package me.chuck.chuckhack.mixin.mixins.chuckhack.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.render.RenderBossHealthEvent;
import net.minecraft.client.gui.GuiBossOverlay;

@Mixin(GuiBossOverlay.class)
public class MixinGuiBossOverlay {
    @Inject(method = "renderBossHealth", at = @At("HEAD"), cancellable = true)
    public void renderBossHealth(CallbackInfo info) {
        RenderBossHealthEvent event = new RenderBossHealthEvent();
        Mod.EVENT_BUS.post(event);
        
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}
