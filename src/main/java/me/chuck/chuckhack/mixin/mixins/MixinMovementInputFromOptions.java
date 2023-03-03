package me.chuck.chuckhack.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.player.PlayerUpdateMoveStateEvent;
import me.chuck.chuckhack.events.player.PlayerUpdateMoveStatePostEvent;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;

@Mixin(value = MovementInputFromOptions.class, priority = 10000)
public class MixinMovementInputFromOptions extends MovementInput {
	
    @Inject(method = "updatePlayerMoveState", at = @At("HEAD"), cancellable = true)
    public void updatePlayerMoveState(CallbackInfo callback) {
    	PlayerUpdateMoveStateEvent event = new PlayerUpdateMoveStateEvent();
        Mod.EVENT_BUS.post(event);
        
        if (event.isCancelled()) {
        	callback.cancel();
        }
    }
    
    @Inject(method = "updatePlayerMoveState", at = @At("RETURN"), cancellable = true)
    public void updatePlayerMoveStatePost(CallbackInfo callback) {
    	PlayerUpdateMoveStatePostEvent event = new PlayerUpdateMoveStatePostEvent();
        Mod.EVENT_BUS.post(event);
        
        if (event.isCancelled()) {
        	callback.cancel();
        }
    }
}