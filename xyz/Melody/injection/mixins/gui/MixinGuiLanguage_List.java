/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package xyz.Melody.injection.mixins.gui;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.Melody.Utils.OptifineCompatible;

@Mixin(targets={"net.minecraft.client.gui.GuiLanguage$List"})
public class MixinGuiLanguage_List {
    @Redirect(method="elementClicked", at=@At(value="INVOKE", target="Lnet/minecraft/client/Minecraft;refreshResources()V"))
    private void refresh(Minecraft minecraft) {
        minecraft.func_135016_M().func_110549_a(minecraft.func_110442_L());
        OptifineCompatible.callOptifineReload();
    }
}

