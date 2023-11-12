/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockGrass
 *  net.minecraft.util.EnumWorldBlockLayer
 */
package xyz.Melody.injection.mixins.XRay;

import net.minecraft.block.BlockGrass;
import net.minecraft.util.EnumWorldBlockLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import xyz.Melody.injection.mixins.XRay.MixinBlock;
import xyz.Melody.module.modules.render.XRay;

@Mixin(value={BlockGrass.class})
public class MixinBlockGrass
extends MixinBlock {
    @Override
    @Overwrite
    public EnumWorldBlockLayer func_180664_k() {
        return XRay.getINSTANCE().isEnabled() ? EnumWorldBlockLayer.TRANSLUCENT : EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
}

