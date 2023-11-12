/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiTextField
 */
package xyz.Melody.GUI.ClickGui.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.render.RenderUtil;

public class TextField
extends GuiTextField {
    private String waittingText = "";

    public TextField(int n, String string, int n2, int n3, int n4, int n5) {
        super(n, Minecraft.func_71410_x().field_71466_p, n2, n3, n4, n5);
        this.waittingText = string;
    }

    public void drawTextBox(int n, int n2) {
        this.field_146209_f = n + 3;
        this.field_146210_g = n2 + 6;
        this.func_146185_a(false);
        RenderUtil.drawBorderedRect(n, n2, n + this.field_146218_h, n2 + this.field_146219_i, 2.0f, Colors.AQUA.c);
        if (this.func_146179_b().equals("") && !this.func_146206_l()) {
            FontLoaders.NMSL18.drawString(this.waittingText, n + 6, n2 + 7, -1);
        }
        super.func_146194_f();
    }
}

