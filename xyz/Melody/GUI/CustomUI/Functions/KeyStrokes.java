/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.GameSettings
 *  org.lwjgl.input.Mouse
 */
package xyz.Melody.GUI.CustomUI.Functions;

import java.awt.Color;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.input.Mouse;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.GUI.CustomUI.HUDApi;
import xyz.Melody.GUI.CustomUI.HUDScreen;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.Utils.render.RenderUtil;

public final class KeyStrokes
extends HUDApi {
    int lastA = 0;
    int lastW = 0;
    int lastS = 0;
    int lastD = 0;
    double lastX = 0.0;
    double lastZ = 0.0;

    public KeyStrokes() {
        super("KeyStrokes", 66, 247);
        this.setEnabled(true);
    }

    @EventHandler
    public void onRender(EventRender2D eventRender2D) {
        if (this.mc.field_71462_r instanceof HUDScreen) {
            return;
        }
        this.keyRender();
    }

    @Override
    public void InScreenRender() {
        this.keyRender();
    }

    private void keyRender() {
        GameSettings gameSettings = this.mc.field_71474_y;
        int n = new Color(230, 230, 230, 120).getRGB();
        int n2 = new Color(30, 30, 30, 120).getRGB();
        int n3 = gameSettings.field_74351_w.func_151470_d() ? n : n2;
        int n4 = gameSettings.field_74368_y.func_151470_d() ? n : n2;
        int n5 = gameSettings.field_74370_x.func_151470_d() ? n : n2;
        int n6 = gameSettings.field_74366_z.func_151470_d() ? n : n2;
        int n7 = gameSettings.field_74314_A.func_151470_d() ? n : n2;
        int n8 = Mouse.isButtonDown((int)0) ? n : n2;
        int n9 = Mouse.isButtonDown((int)1) ? n : n2;
        int n10 = gameSettings.field_74351_w.func_151470_d() ? n2 : n;
        int n11 = gameSettings.field_74368_y.func_151470_d() ? n2 : n;
        int n12 = gameSettings.field_74370_x.func_151470_d() ? n2 : n;
        int n13 = gameSettings.field_74366_z.func_151470_d() ? n2 : n;
        int n14 = gameSettings.field_74314_A.func_151470_d() ? n2 : n;
        int n15 = Mouse.isButtonDown((int)0) ? n2 : n;
        int n16 = Mouse.isButtonDown((int)1) ? n2 : n;
        RenderUtil.drawFastRoundedRect(this.x, this.y, this.x + 25, this.y + 25, 2.0f, n3);
        FontLoaders.NMSL20.drawString("W", this.x + 8, this.y + 9, n10);
        RenderUtil.drawFastRoundedRect(this.x, this.y + 29, this.x + 25, this.y + 54, 2.0f, n4);
        FontLoaders.NMSL20.drawString("S", this.x + 10, this.y + 32 + 6, n11);
        RenderUtil.drawFastRoundedRect(this.x - 29, this.y + 29, this.x - 4, this.y + 54, 2.0f, n5);
        FontLoaders.NMSL20.drawString("A", (float)(this.x - 32) + 12.5f, this.y + 32 + 6, n12);
        RenderUtil.drawFastRoundedRect(this.x + 29, this.y + 29, this.x + 54, this.y + 54, 2.0f, n6);
        FontLoaders.NMSL20.drawString("D", this.x + 32 + 6, this.y + 32 + 6, n13);
        RenderUtil.drawFastRoundedRect(this.x - 29, this.y + 58, this.x + 11, this.y + 79, 2.0f, n8);
        FontLoaders.NMSL20.drawString("LMB", this.x - 32 + 13, this.y + 64 + 1, n15);
        RenderUtil.drawFastRoundedRect(this.x + 15, this.y + 58, this.x + 54, this.y + 79, 2.0f, n9);
        FontLoaders.NMSL20.drawString("RMB", this.x + 18 + 6, this.y + 64 + 1, n16);
        RenderUtil.drawFastRoundedRect(this.x - 29, this.y + 81, this.x + 54, this.y + 94, 2.0f, n7);
        FontLoaders.NMSL20.drawString("-----", this.x - 29 + 31, this.y + 83, n14);
    }

    public int flop(int n, int n2) {
        return n2 - n;
    }
}

