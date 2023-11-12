/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.Utils.render;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public final class Scissor {
    private static Minecraft mc = Minecraft.func_71410_x();

    public static void start(float f, float f2, float f3, float f4) {
        if (f2 > f4) {
            float f5 = f4;
            f4 = f2;
            f2 = f5;
        }
        GL11.glScissor((int)((int)f), (int)((int)((float)Display.getHeight() - f4)), (int)((int)(f3 - f)), (int)((int)(f4 - f2)));
        GL11.glEnable((int)3089);
    }

    public static void end() {
        GL11.glDisable((int)3089);
    }
}

