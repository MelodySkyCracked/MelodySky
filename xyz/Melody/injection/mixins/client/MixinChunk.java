/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.util.BlockPos
 *  net.minecraft.world.chunk.Chunk
 */
package xyz.Melody.injection.mixins.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.events.world.BlockChangeEvent;

@Mixin(value={Chunk.class})
public abstract class MixinChunk {
    @Shadow
    public abstract IBlockState func_177435_g(BlockPos var1);

    @Inject(method="setBlockState", at={@At(value="HEAD")}, cancellable=true)
    private void onBlockChange(BlockPos blockPos, IBlockState iBlockState, CallbackInfoReturnable callbackInfoReturnable) {
        IBlockState iBlockState2 = this.func_177435_g(blockPos);
        BlockChangeEvent blockChangeEvent = new BlockChangeEvent(blockPos, iBlockState2, iBlockState);
        EventBus.getInstance().call(blockChangeEvent);
        if (blockChangeEvent.isCancelled()) {
            callbackInfoReturnable.setReturnValue(iBlockState);
        }
    }
}

