/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.BlockModelRenderer
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.resources.model.IBakedModel
 *  net.minecraft.util.BlockPos
 *  net.minecraft.world.IBlockAccess
 */
package xyz.Melody.injection.mixins.XRay;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import xyz.Melody.module.modules.render.XRay;

@Mixin(value={BlockModelRenderer.class})
public class MixinBlockModelRenderer {
    @Shadow
    public boolean func_178265_a(IBlockAccess iBlockAccess, IBakedModel iBakedModel, Block block, BlockPos blockPos, WorldRenderer worldRenderer, boolean bl) {
        return false;
    }

    @Shadow
    public boolean func_178267_a(IBlockAccess iBlockAccess, IBakedModel iBakedModel, IBlockState iBlockState, BlockPos blockPos, WorldRenderer worldRenderer, boolean bl) {
        return false;
    }

    @Overwrite
    public boolean func_178259_a(IBlockAccess iBlockAccess, IBakedModel iBakedModel, IBlockState iBlockState, BlockPos blockPos, WorldRenderer worldRenderer) {
        Block block = iBlockState.func_177230_c();
        block.func_180654_a(iBlockAccess, blockPos);
        if (XRay.getINSTANCE().isEnabled()) {
            return this.func_178265_a(iBlockAccess, iBakedModel, block, blockPos, worldRenderer, true);
        }
        return this.func_178267_a(iBlockAccess, iBakedModel, iBlockState, blockPos, worldRenderer, true);
    }
}

