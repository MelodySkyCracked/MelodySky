/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.util.EnumWorldBlockLayer
 */
package xyz.Melody.injection.mixins.XRay;

import net.minecraft.block.Block;
import net.minecraft.util.EnumWorldBlockLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import xyz.Melody.module.modules.render.XRay;

@Mixin(value={Block.class})
public class MixinBlock {
    @Overwrite
    public EnumWorldBlockLayer func_180664_k() {
        if (XRay.getINSTANCE().isEnabled()) {
            return EnumWorldBlockLayer.TRANSLUCENT;
        }
        return EnumWorldBlockLayer.SOLID;
    }
}

