/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  org.lwjgl.input.Keyboard
 */
package xyz.Melody.GUI.ClickGui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import xyz.Melody.GUI.Animate.ColorTranslator;
import xyz.Melody.GUI.Animate.NonlinearOpacity;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.render.ColorUtils;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.module.Module;

public class ModBlock {
    private Module module;
    private String name;
    private String tip;
    private float startX = 0.0f;
    private float startY = 0.0f;
    private float endX = 0.0f;
    private float endY = 0.0f;
    private float vStartX = 0.0f;
    private float vStartY = 0.0f;
    private float vEndX = 0.0f;
    private float vEndY = 0.0f;
    private float circleX = 0.0f;
    private NonlinearOpacity circleTranslator = new NonlinearOpacity(0);
    private ColorTranslator ct;

    public ModBlock(Module module) {
        this.module = module;
        this.name = module.getName();
        this.tip = module.getModInfo();
        Color color = new Color(100, 100, 100, 110);
        Color color2 = new Color(80, 80, 180, 110);
        this.ct = new ColorTranslator(this.module.isEnabled() ? color2.getRGB() : color.getRGB());
    }

    public void draw(int n, int n2, int n3, int n4, float f, boolean bl, Minecraft minecraft) {
        this.startX = n + 130;
        this.startY = (float)(n2 + 45 + n4) + f;
        this.endX = n3 - 5;
        this.endY = (float)(n2 + 82 + n4) + f;
        this.vStartX = n + 95;
        this.vStartY = (float)(n2 + 45 + n4) + f;
        this.vEndX = n + 120;
        this.vEndY = (float)(n2 + 82 + n4) + f;
        RenderUtil.drawFastRoundedRect(this.vStartX, this.startY, this.endX, this.endY, 4.0f, ColorUtils.addAlpha(new Color(Colors.GRAY.c), 70).getRGB());
        RenderUtil.drawFastRoundedRect(this.vStartX, this.startY, this.vEndX, this.endY, 4.0f, ColorUtils.addAlpha(new Color(Colors.GRAY.c), 90).getRGB());
        Color color = new Color(100, 100, 100, 245);
        Color color2 = new Color(255, 255, 255, 255);
        Color color3 = new Color(90, 90, 220, 245);
        Color color4 = this.module.isEnabled() ? color3 : color;
        RenderUtil.drawSwitcherBG(this.endX - 30.0f, (float)(n2 + 63 + n4) + f, 6.5f, this.ct.interpolate(color4, 12.0f));
        RenderUtil.drawFilledCircle(this.endX - 37.0f + this.circleX, (float)(n2 + 63 + n4) + f, 5.0f, color2);
        if (bl) {
            FontLoaders.CNMD20.drawString("\u00a7l ...", this.vStartX + 5.0f, this.vStartY + 15.0f, -1);
        }
        if (this.module.getKey() == 0) {
            if (!bl) {
                for (int i = 0; i < 3; ++i) {
                    RenderUtil.drawFilledCircle(this.vStartX + 12.0f, this.vStartY + 12.0f + (float)(i * 6), 2.0f, ColorUtils.darker(new Color(Colors.GRAY.c), -0.2f));
                }
            }
            FontLoaders.NMSL24.drawString(this.name, this.startX + 10.0f, (float)n2 + 59.0f + (float)n4 + f, -1);
        } else {
            if (!bl) {
                for (int i = 0; i < 3; ++i) {
                    RenderUtil.drawFilledCircle(this.vStartX + 12.0f, this.vStartY + 12.0f + (float)(i * 6), 2.0f, ColorUtils.darker(new Color(Colors.MAGENTA.c), -0.2f));
                }
            }
            FontLoaders.NMSL24.drawString(this.name, this.startX + 10.0f, (float)n2 + 54.0f + (float)n4 + f, -1);
            FontLoaders.NMSL18.drawString("\u00a7lBind: " + Keyboard.getKeyName((int)this.module.getKey()), this.startX + 10.0f, (float)n2 + 68.5f + (float)n4 + f, ColorUtils.lighter(new Color(Colors.DARKAQUA.c), 2.0).getRGB());
        }
        FontLoaders.CNMD20.drawString(this.tip, n + 270, (float)(n2 + 60 + n4) + f, -1);
        if (this.module.isEnabled()) {
            this.circleTranslator.interp(14.0f, 2.0f);
        } else {
            this.circleTranslator.interp(0.0f, 2.0f);
        }
        this.circleX = this.circleTranslator.getOpacity();
    }

    public void onMouseClick() {
        this.module.setEnabled(!this.module.isEnabled());
    }

    public Module getModule() {
        return this.module;
    }

    public float getStartX() {
        return this.startX;
    }

    public float getStartY() {
        return this.startY;
    }

    public float getEndX() {
        return this.endX;
    }

    public float getEndY() {
        return this.endY;
    }

    public float getvStartX() {
        return this.vStartX;
    }

    public float getvStartY() {
        return this.vStartY;
    }

    public float getvEndX() {
        return this.vEndX;
    }

    public float getvEndY() {
        return this.vEndY;
    }

    public void setStartY(float f) {
        this.startY = f;
    }
}

