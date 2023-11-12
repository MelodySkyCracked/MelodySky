/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.Utils.render.gl;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public final class GLUtil {
    private static final Map glCapMap = new HashMap();

    public static void setGLCap(int n, boolean bl) {
        glCapMap.put(n, GL11.glGetBoolean((int)n));
        if (bl) {
            GL11.glEnable((int)n);
        } else {
            GL11.glDisable((int)n);
        }
    }

    private static void revertGLCap(int n) {
        Boolean bl = (Boolean)glCapMap.get(n);
        if (bl != null) {
            if (bl.booleanValue()) {
                GL11.glEnable((int)n);
            } else {
                GL11.glDisable((int)n);
            }
        }
    }

    public static void glEnable(int n) {
        GLUtil.setGLCap(n, true);
    }

    public static void glDisable(int n) {
        GLUtil.setGLCap(n, false);
    }

    public static void revertAllCaps() {
        for (Integer n : glCapMap.keySet()) {
            GLUtil.revertGLCap(n);
        }
    }
}

