/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 */
package xyz.Melody.injection.mixins.optimizations;

import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={EnumFacing.class})
public class MixinEnumFacing {
    @Shadow
    private int field_176759_h;
    @Shadow
    public static EnumFacing[] field_82609_l;

    @Overwrite
    public EnumFacing func_176734_d() {
        return field_82609_l[this.field_176759_h];
    }
}

