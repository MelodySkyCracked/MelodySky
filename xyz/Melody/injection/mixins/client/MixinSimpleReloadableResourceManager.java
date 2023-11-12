/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.resources.IResourceManager
 *  net.minecraft.client.resources.IResourceManagerReloadListener
 *  net.minecraft.client.resources.SimpleReloadableResourceManager
 *  net.minecraftforge.fml.common.ProgressManager
 *  net.minecraftforge.fml.common.ProgressManager$ProgressBar
 */
package xyz.Melody.injection.mixins.client;

import java.util.List;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraftforge.fml.common.ProgressManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import xyz.Melody.GUI.Menu.GenshinSplashScreen;

@Mixin(value={SimpleReloadableResourceManager.class})
public class MixinSimpleReloadableResourceManager {
    @Shadow
    private List field_110546_b;

    @Overwrite
    private void func_110544_b() {
        ProgressManager.ProgressBar progressBar = ProgressManager.push((String)"Reloading", (int)this.field_110546_b.size());
        for (IResourceManagerReloadListener iResourceManagerReloadListener : this.field_110546_b) {
            progressBar.step(iResourceManagerReloadListener.getClass(), new String[0]);
            if (iResourceManagerReloadListener instanceof TextureManager) {
                GenshinSplashScreen.paused = true;
            }
            iResourceManagerReloadListener.func_110549_a((IResourceManager)((SimpleReloadableResourceManager)this));
            GenshinSplashScreen.paused = false;
        }
        ProgressManager.pop((ProgressManager.ProgressBar)progressBar);
    }
}

