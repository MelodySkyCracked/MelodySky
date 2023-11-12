/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemSword
 */
package xyz.Melody.injection.mixins.items;

import net.minecraft.item.ItemSword;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ItemSword.class})
public interface ItemSwordAccessor {
    @Accessor(value="attackDamage")
    public float getAttackDamage();
}

