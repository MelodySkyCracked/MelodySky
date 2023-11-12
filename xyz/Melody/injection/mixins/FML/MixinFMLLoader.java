/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.client.FMLClientHandler
 */
package xyz.Melody.injection.mixins.FML;

import net.minecraftforge.fml.client.FMLClientHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Melody.Utils.MelodyForceHider;

@Mixin(value={FMLClientHandler.class}, remap=false)
public class MixinFMLLoader {
    @Inject(method="beginMinecraftLoading", at={@At(value="INVOKE", target="Lnet/minecraftforge/fml/common/Loader;preinitializeMods()V", shift=At.Shift.AFTER)})
    private void onFMLIdentifyMods(CallbackInfo callbackInfo) {
        MelodyForceHider.init();
    }
}

