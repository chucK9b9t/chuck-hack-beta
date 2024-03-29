package me.chuck.chuckhack.mixin.mixins.chuckhack.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.mods.render.Freecam;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.MathHelper;

@Mixin(ViewFrustum.class)
public abstract class MixinViewFrustum {
    @Shadow public RenderChunk[] renderChunks;
    @Shadow protected int countChunksX;
    @Shadow protected int countChunksY;
    @Shadow protected int countChunksZ;

    @Shadow protected abstract int getBaseCoordinate(int p_178157_1_, int p_178157_2_, int p_178157_3_);

    @Inject(method = "updateChunkPositions", at = @At("HEAD"), cancellable = true)
    public void updateChunkPositionsHead(double viewEntityX, double viewEntityZ, CallbackInfo ci) {
        if (!Freecam.isToggled) return;

        EntityPlayerSP player = Mod.mc.player;
        if (player == null) return;

        int centerX = MathHelper.floor(player.posX) - 8;
        int centerZ = MathHelper.floor(player.posZ) - 8;

        int multipliedCountX = this.countChunksX * 16;

        for (int x = 0; x < this.countChunksX; ++x) {
            int posX = this.getBaseCoordinate(centerX, multipliedCountX, x);

            for (int z = 0; z < this.countChunksZ; ++z) {
                int poxZ = this.getBaseCoordinate(centerZ, multipliedCountX, z);

                for (int y = 0; y < this.countChunksY; ++y) {
                    int poxY = y * 16;
                    RenderChunk renderchunk = this.renderChunks[(z * this.countChunksY + y) * this.countChunksX + x];
                    renderchunk.setPosition(posX, poxY, poxZ);
                }
            }
        }

        ci.cancel();
    }
}
