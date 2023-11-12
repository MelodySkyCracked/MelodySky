/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.crash.CrashReport
 *  net.minecraft.item.EnumAction
 *  net.minecraft.item.ItemBlock
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.lwjgl.input.Keyboard
 */
package xyz.Melody.injection.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.Melody.Client;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.events.misc.EventKey;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.GUI.ClickGui.DickGui;
import xyz.Melody.GUI.Menu.GenshinSplashScreen;
import xyz.Melody.injection.mixins.entity.PlayerControllerAccessor;
import xyz.Melody.module.balance.NoHitDelay;
import xyz.Melody.module.modules.render.HUD;
import xyz.Melody.module.modules.render.OldAnimations;

@SideOnly(value=Side.CLIENT)
@Mixin(value={Minecraft.class})
public abstract class MixinMinecraft {
    @Shadow
    public GuiScreen field_71462_r;
    @Shadow
    private int field_71429_W;
    @Shadow
    public WorldClient field_71441_e;
    @Shadow
    public GameSettings field_71474_y;
    GenshinSplashScreen genshit = new GenshinSplashScreen();

    @Inject(method="getLimitFramerate", at={@At(value="HEAD")}, cancellable=true)
    public void getLimitFramerate(CallbackInfoReturnable callbackInfoReturnable) {
        if (HUD.getInstance() != null && ((Boolean)HUD.getInstance().fpsLimit.getValue()).booleanValue()) {
            if (this.field_71441_e != null || this.field_71462_r == null) {
                callbackInfoReturnable.setReturnValue(Client.shouldLimitFps ? ((Double)HUD.getInstance().guiFpsLim.getValue()).intValue() : this.field_71474_y.field_74350_i);
                callbackInfoReturnable.cancel();
                return;
            }
            if (this.field_71462_r instanceof DickGui) {
                callbackInfoReturnable.setReturnValue(90);
                callbackInfoReturnable.cancel();
                return;
            }
            callbackInfoReturnable.setReturnValue(30);
            callbackInfoReturnable.cancel();
        }
    }

    @Inject(method="clickMouse", at={@At(value="HEAD")}, cancellable=true)
    private void onLeftClick(CallbackInfo callbackInfo) {
        if (NoHitDelay.getINSTANCE().isEnabled()) {
            this.field_71429_W = 0;
        }
    }

    @Inject(method="rightClickMouse", at={@At(value="HEAD")})
    public void rightClickMouse(CallbackInfo callbackInfo) {
        if (OldAnimations.getINSTANCE().isEnabled() && ((Boolean)OldAnimations.getINSTANCE().punching.getValue()).booleanValue() && ((PlayerControllerAccessor)Minecraft.func_71410_x().field_71442_b).isHittingBlock() && Minecraft.func_71410_x().field_71439_g.func_70694_bm() != null && (Minecraft.func_71410_x().field_71439_g.func_70694_bm().func_77975_n() != EnumAction.NONE || Minecraft.func_71410_x().field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock)) {
            Minecraft.func_71410_x().field_71442_b.func_78767_c();
        }
    }

    @Inject(method="startGame", at={@At(value="HEAD")})
    private void run(CallbackInfo callbackInfo) {
        Client.preCharset();
        Client.prepareSplashScreen();
    }

    @Inject(method="drawSplashScreen", at={@At(value="HEAD")}, cancellable=true)
    public void drawSplashScreen(TextureManager textureManager, CallbackInfo callbackInfo) {
        this.genshit.start(textureManager, (Minecraft)this, this.genshit);
        callbackInfo.cancel();
    }

    @Inject(method="startGame", at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;ingameGUI:Lnet/minecraft/client/gui/GuiIngame;", shift=At.Shift.BEFORE)})
    private void startGame(CallbackInfo callbackInfo) {
        GenshinSplashScreen.progress += 3;
        Client.instance.start();
    }

    @Inject(method="startGame", at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;serverName:Ljava/lang/String;", shift=At.Shift.BEFORE, ordinal=0)})
    private void forgeBarPop(CallbackInfo callbackInfo) {
        this.genshit.finish();
    }

    @Inject(method="runTick", at={@At(value="INVOKE", target="Lnet/minecraft/client/Minecraft;dispatchKeypresses()V", shift=At.Shift.AFTER)})
    private void onKey(CallbackInfo callbackInfo) {
        if (Keyboard.getEventKeyState() && this.field_71462_r == null) {
            EventBus.getInstance().call(new EventKey(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey()));
        }
    }

    @Inject(method="shutdown", at={@At(value="HEAD")})
    private void onShutdown(CallbackInfo callbackInfo) {
        Client.instance.stop();
    }

    @Inject(method="displayCrashReport", at={@At(value="HEAD")})
    private void onCrashDump(CrashReport crashReport, CallbackInfo callbackInfo) {
        Client.instance.errShutdown();
    }

    @Inject(method="runTick", at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;joinPlayerCounter:I", shift=At.Shift.BEFORE)})
    private void onTick(CallbackInfo callbackInfo) {
        EventTick eventTick = new EventTick();
        EventBus.getInstance().call(eventTick);
    }
}

