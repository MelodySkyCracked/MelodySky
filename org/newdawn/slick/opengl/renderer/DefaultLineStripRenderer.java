/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.opengl.renderer;

import org.newdawn.slick.opengl.renderer.LineStripRenderer;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class DefaultLineStripRenderer
implements LineStripRenderer {
    private SGL GL = Renderer.get();

    @Override
    public void end() {
        this.GL.glEnd();
    }

    @Override
    public void setAntiAlias(boolean bl) {
        if (bl) {
            this.GL.glEnable(2848);
        } else {
            this.GL.glDisable(2848);
        }
    }

    @Override
    public void setWidth(float f) {
        this.GL.glLineWidth(f);
    }

    @Override
    public void start() {
        this.GL.glBegin(3);
    }

    @Override
    public void vertex(float f, float f2) {
        this.GL.glVertex2f(f, f2);
    }

    @Override
    public void color(float f, float f2, float f3, float f4) {
        this.GL.glColor4f(f, f2, f3, f4);
    }

    @Override
    public void setLineCaps(boolean bl) {
    }

    @Override
    public boolean applyGLLineFixes() {
        return true;
    }
}

