/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiNewChat
 */
package xyz.Melody.injection.mixins.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Melody.Utils.shader.GaussianBlur;
import xyz.Melody.module.modules.render.HUD;

@Mixin(value={GuiNewChat.class})
public abstract class MixinGuiNewChat {
    @Shadow
    private Minecraft field_146247_f;
    @Shadow
    private int field_146250_j;
    @Shadow
    private boolean field_146251_k;
    @Shadow
    private final List field_146253_i = Lists.newArrayList();
    private GaussianBlur blur = new GaussianBlur();

    @Shadow
    public abstract int func_146232_i();

    @Shadow
    public abstract boolean func_146241_e();

    @Shadow
    public abstract float func_146244_h();

    @Shadow
    public abstract int func_146228_f();

    @Inject(method="drawChat", at={@At(value="HEAD")}, cancellable=true)
    public void drawChat(int n, CallbackInfo callbackInfo) {
        if (((Boolean)HUD.getInstance().overwriteChat.getValue()).booleanValue()) {
            HUD.getInstance().handleChat(n, this.func_146232_i(), this.field_146253_i, this.func_146241_e(), this.func_146228_f(), this.field_146250_j, this.func_146244_h(), this.field_146251_k);
            callbackInfo.cancel();
        }
    }
}

