/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.ai.EntityLookHelper
 */
package xyz.Melody.injection.mixins.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityLookHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={EntityLiving.class})
public interface EntityLivingAccessor {
    @Accessor(value="lookHelper")
    public void setLookHelper(EntityLookHelper var1);
}

