/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemFood
 */
package xyz.Melody.injection.mixins.items;

import net.minecraft.item.ItemFood;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ItemFood.class})
public interface ItemFoodAccessor {
    @Accessor(value="alwaysEdible")
    public boolean getAlwaysEdible();
}

