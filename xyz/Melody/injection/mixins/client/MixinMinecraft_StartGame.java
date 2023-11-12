/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package xyz.Melody.injection.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Melody.GUI.Menu.GenshinSplashScreen;

@SideOnly(value=Side.CLIENT)
@Mixin(value={Minecraft.class})
public class MixinMinecraft_StartGame {
    @Inject(method="startGame", at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;textureMapBlocks:Lnet/minecraft/client/renderer/texture/TextureMap;", shift=At.Shift.AFTER)})
    private void textureMapBlocks(CallbackInfo callbackInfo) {
        ++GenshinSplashScreen.progress;
    }

    @Inject(method="startGame", at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;modelManager:Lnet/minecraft/client/resources/model/ModelManager;", shift=At.Shift.AFTER)})
    private void modelManager(CallbackInfo callbackInfo) {
        ++GenshinSplashScreen.progress;
    }

    @Inject(method="startGame", at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;renderItem:Lnet/minecraft/client/renderer/entity/RenderItem;", shift=At.Shift.AFTER)})
    private void renderItem(CallbackInfo callbackInfo) {
        ++GenshinSplashScreen.progress;
    }

    @Inject(method="startGame", at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;renderManager:Lnet/minecraft/client/renderer/entity/RenderManager;", shift=At.Shift.AFTER, ordinal=0)})
    private void renderManager(CallbackInfo callbackInfo) {
        ++GenshinSplashScreen.progress;
    }

    @Inject(method="startGame", at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;itemRenderer:Lnet/minecraft/client/renderer/ItemRenderer;", shift=At.Shift.AFTER, ordinal=0)})
    private void itemRenderer(CallbackInfo callbackInfo) {
        ++GenshinSplashScreen.progress;
    }

    @Inject(method="startGame", at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;", shift=At.Shift.AFTER)})
    private void entityRenderer(CallbackInfo callbackInfo) {
        ++GenshinSplashScreen.progress;
    }

    @Inject(method="startGame", at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;blockRenderDispatcher:Lnet/minecraft/client/renderer/BlockRendererDispatcher;", shift=At.Shift.AFTER)})
    private void blockRenderDispatcher(CallbackInfo callbackInfo) {
        ++GenshinSplashScreen.progress;
    }

    @Inject(method="startGame", at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;", shift=At.Shift.AFTER)})
    private void renderGlobal(CallbackInfo callbackInfo) {
        ++GenshinSplashScreen.progress;
    }

    @Inject(method="startGame", at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;guiAchievement:Lnet/minecraft/client/gui/achievement/GuiAchievement;", shift=At.Shift.AFTER, ordinal=0)})
    private void guiAchievement(CallbackInfo callbackInfo) {
        ++GenshinSplashScreen.progress;
    }

    @Inject(method="startGame", at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;effectRenderer:Lnet/minecraft/client/particle/EffectRenderer;", shift=At.Shift.AFTER, ordinal=0)})
    private void effectRenderer(CallbackInfo callbackInfo) {
        ++GenshinSplashScreen.progress;
    }

    @Inject(method="startGame", at={@At(value="INVOKE", target="Lnet/minecraftforge/fml/common/ProgressManager;pop(Lnet/minecraftforge/fml/common/ProgressManager/ProgressBar;)V", shift=At.Shift.BEFORE, ordinal=0)})
    private void forgeBarPop(CallbackInfo callbackInfo) {
        GenshinSplashScreen.progress += 3;
    }
}

