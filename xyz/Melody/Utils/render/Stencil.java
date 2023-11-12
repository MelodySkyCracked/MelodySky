/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.shader.Framebuffer
 *  org.lwjgl.opengl.EXTFramebufferObject
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.Utils.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class Stencil {
    private static Minecraft mc = Minecraft.func_71410_x();

    public static void checkSetupFBO(Framebuffer framebuffer) {
        if (framebuffer != null && framebuffer.field_147624_h > -1) {
            Stencil.setupFBO(framebuffer);
            framebuffer.field_147624_h = -1;
        }
    }

    public static void setupFBO(Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT((int)framebuffer.field_147624_h);
        int n = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)n);
        EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)34041, (int)Stencil.mc.field_71443_c, (int)Stencil.mc.field_71440_d);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36128, (int)36161, (int)n);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36096, (int)36161, (int)n);
    }

    public static void initStencil() {
        Stencil.initStencil(mc.func_147110_a());
    }

    public static void initStencil(Framebuffer framebuffer) {
        framebuffer.func_147610_a(false);
        Stencil.checkSetupFBO(framebuffer);
        GL11.glClear((int)1024);
        GL11.glEnable((int)2960);
    }

    public static void bindWriteStencilBuffer() {
        GL11.glStencilFunc((int)519, (int)1, (int)1);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
    }

    public static void bindReadStencilBuffer(int n) {
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        GL11.glStencilFunc((int)514, (int)n, (int)1);
        GL11.glStencilOp((int)7680, (int)7680, (int)7680);
    }

    public static void uninitStencil() {
        GL11.glDisable((int)2960);
    }
}

