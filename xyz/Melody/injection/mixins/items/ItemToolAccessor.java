/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemTool
 */
package xyz.Melody.injection.mixins.items;

import net.minecraft.item.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ItemTool.class})
public interface ItemToolAccessor {
    @Accessor(value="damageVsEntity")
    public float getDamageVsEntity();
}

