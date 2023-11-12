/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package xyz.Melody.GUI.CustomUI.Functions;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.GUI.CustomUI.HUDApi;
import xyz.Melody.GUI.CustomUI.HUDScreen;
import xyz.Melody.GUI.Font.CFontRenderer;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.Utils.render.RenderUtil;

public final class FPS
extends HUDApi {
    public FPS() {
        super("FPS", 5, 90);
        this.setEnabled(true);
    }

    @EventHandler
    public void onRender(EventRender2D eventRender2D) {
        if (this.mc.field_71462_r instanceof HUDScreen) {
            return;
        }
        this.fpsRender();
    }

    @Override
    public void InScreenRender() {
        this.fpsRender();
    }

    private void fpsRender() {
        int n = new Color(30, 30, 30, 100).getRGB();
        CFontRenderer cFontRenderer = FontLoaders.NMSL20;
        RenderUtil.drawFastRoundedRect(this.x, this.y, this.x + cFontRenderer.getStringWidth("FPS: " + Minecraft.func_175610_ah()) + 8, this.y + cFontRenderer.getStringHeight("FPS: " + Minecraft.func_175610_ah()) + 6, 1.0f, n);
        FontLoaders.NMSL20.drawString("FPS: " + Minecraft.func_175610_ah(), this.x + 4, this.y + 4, -1);
    }
}

