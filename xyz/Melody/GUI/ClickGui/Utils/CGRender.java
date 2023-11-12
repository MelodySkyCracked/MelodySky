/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.GUI.ClickGui.Utils;

import java.awt.Color;
import xyz.Melody.Utils.render.ColorUtils;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.shader.GaussianBlur;
import xyz.Melody.module.modules.render.HUD;

public class CGRender {
    private static GaussianBlur blur = new GaussianBlur();
    public static GaussianBlur bgBlur = new GaussianBlur();

    public static void drawBlurRect(float f, float f2, float f3, float f4, int n, int n2, int n3) {
        if (((Boolean)HUD.getInstance().cgblur.getValue()).booleanValue()) {
            blur.blurArea(f, f2, f3, f4, n3, ColorUtils.addAlpha(ColorUtils.darker(new Color(n), 1.0), 110).getRGB(), n2);
        } else {
            RenderUtil.drawFastRoundedRect(f, f2, f3, f4, n3, n);
        }
        RenderUtil.drawFastRoundedRect(f, f2, f3, f4, n3, n);
    }
}

