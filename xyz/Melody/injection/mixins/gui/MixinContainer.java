/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.Container
 *  net.minecraft.item.ItemStack
 */
package xyz.Melody.injection.mixins.gui;

import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Melody.module.modules.QOL.AutoEnchantTable;

@Mixin(value={Container.class})
public class MixinContainer {
    @Inject(method="putStacksInSlots", at={@At(value="RETURN")})
    public void putStacksInSlots(ItemStack[] itemStackArray, CallbackInfo callbackInfo) {
        AutoEnchantTable.getINSTANCE().processInventoryContents();
    }
}

