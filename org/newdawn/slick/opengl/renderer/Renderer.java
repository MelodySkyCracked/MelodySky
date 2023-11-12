/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.opengl.renderer;

import org.newdawn.slick.opengl.renderer.DefaultLineStripRenderer;
import org.newdawn.slick.opengl.renderer.ImmediateModeOGLRenderer;
import org.newdawn.slick.opengl.renderer.LineStripRenderer;
import org.newdawn.slick.opengl.renderer.QuadBasedLineStripRenderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.opengl.renderer.VAOGLRenderer;

public class Renderer {
    public static final int IMMEDIATE_RENDERER = 1;
    public static final int VERTEX_ARRAY_RENDERER = 2;
    public static final int DEFAULT_LINE_STRIP_RENDERER = 3;
    public static final int QUAD_BASED_LINE_STRIP_RENDERER = 4;
    private static SGL renderer = new ImmediateModeOGLRenderer();
    private static LineStripRenderer lineStripRenderer = new DefaultLineStripRenderer();

    public static void setRenderer(int n) {
        switch (n) {
            case 1: {
                Renderer.setRenderer(new ImmediateModeOGLRenderer());
                return;
            }
            case 2: {
                Renderer.setRenderer(new VAOGLRenderer());
                return;
            }
        }
        throw new RuntimeException("Unknown renderer type: " + n);
    }

    public static void setLineStripRenderer(int n) {
        switch (n) {
            case 3: {
                Renderer.setLineStripRenderer(new DefaultLineStripRenderer());
                return;
            }
            case 4: {
                Renderer.setLineStripRenderer(new QuadBasedLineStripRenderer());
                return;
            }
        }
        throw new RuntimeException("Unknown line strip renderer type: " + n);
    }

    public static void setLineStripRenderer(LineStripRenderer lineStripRenderer) {
        Renderer.lineStripRenderer = lineStripRenderer;
    }

    public static void setRenderer(SGL sGL) {
        renderer = sGL;
    }

    public static SGL get() {
        return renderer;
    }

    public static LineStripRenderer getLineStripRenderer() {
        return lineStripRenderer;
    }
}

