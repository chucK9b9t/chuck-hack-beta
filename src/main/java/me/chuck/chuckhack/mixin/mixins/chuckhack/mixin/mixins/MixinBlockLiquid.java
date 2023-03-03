package me.chuck.chuckhack.mixin.mixins.chuckhack.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.block.CanCollideEvent;
import me.chuck.chuckhack.events.block.LiquidCollisionEvent;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

@Mixin(BlockLiquid.class)
public class MixinBlockLiquid {
    @Inject(method = "getCollisionBoundingBox", at = @At("HEAD"), cancellable = true)
    public void getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, CallbackInfoReturnable<AxisAlignedBB> info) {
    	LiquidCollisionEvent event = new LiquidCollisionEvent(pos);
    	Mod.EVENT_BUS.post(event);
    	
    	if (event.isCancelled()) {
    		info.setReturnValue(event.boundingBox);
    	}
    }
    
    @Inject(method = "canCollideCheck", at = @At("HEAD"), cancellable = true)
    public void canCollideCheck(IBlockState state, boolean hitIfLiquid, CallbackInfoReturnable<Boolean> info) {
    	CanCollideEvent event = new CanCollideEvent(state);
    	Mod.EVENT_BUS.post(event);
    	
    	if (event.isCancelled()) {
    		info.setReturnValue(true);
    	}
    }
}
