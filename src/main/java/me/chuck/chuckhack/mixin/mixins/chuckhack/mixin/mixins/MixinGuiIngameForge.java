package me.chuck.chuckhack.mixin.mixins.chuckhack.mixin.mixins;

import me.chuck.chuckhack.mixin.mixins.chuckhack.Mod;
import me.chuck.chuckhack.mixin.mixins.chuckhack.mods.render.Freecam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.GuiIngameForge;

@Mixin(value = GuiIngameForge.class, remap = false)
public class MixinGuiIngameForge {
    @ModifyVariable(method = "renderAir", at = @At(value = "STORE", ordinal = 0))
    private EntityPlayer renderAir$getRenderViewEntity(EntityPlayer renderViewEntity) {
        return getRenderViewEntity(renderViewEntity);
    }

    @ModifyVariable(method = "renderHealth", at = @At(value = "STORE", ordinal = 0))
    private EntityPlayer renderHealth$getRenderViewEntity(EntityPlayer renderViewEntity) {
        return getRenderViewEntity(renderViewEntity);
    }

    @ModifyVariable(method = "renderFood", at = @At(value = "STORE", ordinal = 0))
    private EntityPlayer renderFood$getRenderViewEntity(EntityPlayer renderViewEntity) {
        return getRenderViewEntity(renderViewEntity);
    }

    @ModifyVariable(method = "renderHealthMount", at = @At(value = "STORE", ordinal = 0))
    private EntityPlayer renderHealthMount$getRenderViewEntity(EntityPlayer renderViewEntity) {
        return getRenderViewEntity(renderViewEntity);
    }
    
    private EntityPlayer getRenderViewEntity(EntityPlayer renderViewEntity) {
    	if (Freecam.isToggled && Mod.mc.player != null) {
    		return Mod.mc.player;
    	}
    	
    	return renderViewEntity;
    }
}
