package me.chuck.chuckhack.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.chuck.chuckhack.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

@Mixin(value = Minecraft.class, priority = Integer.MAX_VALUE)
public class MixinMinecraft {
	private ServerData oldServerData;
	
	//This fixes the bug where u fall out of the world if u get disconnected and rejoin and teleport
	//Its caused by baritone i think.
    @Inject(method = "getCurrentServerData", at = @At("HEAD"), cancellable = true)
    public void getCurrentServerData(CallbackInfoReturnable<ServerData> cir) {
    	if (Mod.mc.currentServerData != null || Mod.mc.isSingleplayer()) {
    		cir.setReturnValue(Mod.mc.currentServerData);
    		if (Mod.mc.currentServerData != null) {
    			oldServerData = Mod.mc.currentServerData;
    		}
    	} else {
    		cir.setReturnValue(oldServerData);
    	}
    }
}
