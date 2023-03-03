package me.chuck.chuckhack.mixin.mixins.chuckhack.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.other.IsPotionEffectActiveEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

@Mixin(EntityLivingBase.class)
public class MixinEntityLivingBase {
    @Inject(method = "isPotionActive", at = @At("HEAD"), cancellable = true)
    public void isPotionActive(Potion potionIn, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        IsPotionEffectActiveEvent event = new IsPotionEffectActiveEvent(potionIn);
        Mod.EVENT_BUS.post(event);

        if (event.isCancelled()) {
        	callbackInfoReturnable.setReturnValue(false);
        }
    }
}
