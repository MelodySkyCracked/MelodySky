/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.opengl.renderer;

import org.newdawn.slick.opengl.renderer.DefaultLineStripRenderer;
import org.newdawn.slick.opengl.renderer.LineStripRenderer;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class QuadBasedLineStripRenderer
implements LineStripRenderer {
    private SGL GL = Renderer.get();
    public static int MAX_POINTS = 10000;
    private boolean antialias;
    private float width = 1.0f;
    private float[] points;
    private float[] colours;
    private int pts;
    private int cpt;
    private DefaultLineStripRenderer def = new DefaultLineStripRenderer();
    private boolean renderHalf;
    private boolean lineCaps = false;

    public QuadBasedLineStripRenderer() {
        this.points = new float[MAX_POINTS * 2];
        this.colours = new float[MAX_POINTS * 4];
    }

    @Override
    public void setLineCaps(boolean bl) {
        this.lineCaps = bl;
    }

    @Override
    public void start() {
        if (this.width == 1.0f) {
            this.def.start();
            return;
        }
        this.pts = 0;
        this.cpt = 0;
        this.GL.flush();
        float[] fArray = this.GL.getCurrentColor();
        this.color(fArray[0], fArray[1], fArray[2], fArray[3]);
    }

    @Override
    public void end() {
        if (this.width == 1.0f) {
            this.def.end();
            return;
        }
        this.renderLines(this.points, this.pts);
    }

    @Override
    public void vertex(float f, float f2) {
        if (this.width == 1.0f) {
            this.def.vertex(f, f2);
            return;
        }
        this.points[this.pts * 2] = f;
        this.points[this.pts * 2 + 1] = f2;
        ++this.pts;
        int n = this.pts - 1;
        this.color(this.colours[n * 4], this.colours[n * 4 + 1], this.colours[n * 4 + 2], this.colours[n * 4 + 3]);
    }

    @Override
    public void setWidth(float f) {
        this.width = f;
    }

    @Override
    public void setAntiAlias(boolean bl) {
        this.def.setAntiAlias(bl);
        this.antialias = bl;
    }

    public void renderLines(float[] fArray, int n) {
        if (this.antialias) {
            this.GL.glEnable(2881);
            this.renderLinesImpl(fArray, n, this.width + 1.0f);
        }
        this.GL.glDisable(2881);
        this.renderLinesImpl(fArray, n, this.width);
        if (this.antialias) {
            this.GL.glEnable(2881);
        }
    }

    public void renderLinesImpl(float[] fArray, int n, float f) {
        float f2;
        float f3;
        float f4 = f / 2.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        this.GL.glBegin(7);
        for (int i = 0; i < n + 1; ++i) {
            int n2 = i;
            int n3 = i + 1;
            int n4 = i - 1;
            if (n4 < 0) {
                n4 += n;
            }
            if (n3 >= n) {
                n3 -= n;
            }
            if (n2 >= n) {
                n2 -= n;
            }
            float f9 = fArray[n2 * 2];
            f3 = fArray[n2 * 2 + 1];
            float f10 = fArray[n3 * 2];
            float f11 = fArray[n3 * 2 + 1];
            float f12 = f10 - f9;
            float f13 = f11 - f3;
            if (f12 == 0.0f && f13 == 0.0f) continue;
            float f14 = f12 * f12 + f13 * f13;
            float f15 = (float)Math.sqrt(f14);
            f12 *= f4;
            f13 *= f4;
            float f16 = f13 /= f15;
            float f17 = -(f12 /= f15);
            if (i != 0) {
                this.bindColor(n4);
                this.GL.glVertex3f(f5, f6, 0.0f);
                this.GL.glVertex3f(f7, f8, 0.0f);
                this.bindColor(n2);
                this.GL.glVertex3f(f9 + f16, f3 + f17, 0.0f);
                this.GL.glVertex3f(f9 - f16, f3 - f17, 0.0f);
            }
            f5 = f10 - f16;
            f6 = f11 - f17;
            f7 = f10 + f16;
            f8 = f11 + f17;
            if (i >= n - 1) continue;
            this.bindColor(n2);
            this.GL.glVertex3f(f9 + f16, f3 + f17, 0.0f);
            this.GL.glVertex3f(f9 - f16, f3 - f17, 0.0f);
            this.bindColor(n3);
            this.GL.glVertex3f(f10 - f16, f11 - f17, 0.0f);
            this.GL.glVertex3f(f10 + f16, f11 + f17, 0.0f);
        }
        this.GL.glEnd();
        float f18 = f2 = f4 <= 12.5f ? 5.0f : 180.0f / (float)Math.ceil((double)f4 / 2.5);
        if (this.lineCaps) {
            float f19 = fArray[2] - fArray[0];
            float f20 = fArray[3] - fArray[1];
            float f21 = (float)Math.toDegrees(Math.atan2(f20, f19)) + 90.0f;
            if (f19 != 0.0f || f20 != 0.0f) {
                this.GL.glBegin(6);
                this.bindColor(0);
                this.GL.glVertex2f(fArray[0], fArray[1]);
                int n5 = 0;
                while ((float)n5 < 180.0f + f2) {
                    f3 = (float)Math.toRadians(f21 + (float)n5);
                    this.GL.glVertex2f(fArray[0] + (float)(Math.cos(f3) * (double)f4), fArray[1] + (float)(Math.sin(f3) * (double)f4));
                    n5 = (int)((float)n5 + f2);
                }
                this.GL.glEnd();
            }
        }
        if (this.lineCaps) {
            float f22 = fArray[n * 2 - 2] - fArray[n * 2 - 4];
            float f23 = fArray[n * 2 - 1] - fArray[n * 2 - 3];
            float f24 = (float)Math.toDegrees(Math.atan2(f23, f22)) - 90.0f;
            if (f22 != 0.0f || f23 != 0.0f) {
                this.GL.glBegin(6);
                this.bindColor(n - 1);
                this.GL.glVertex2f(fArray[n * 2 - 2], fArray[n * 2 - 1]);
                int n6 = 0;
                while ((float)n6 < 180.0f + f2) {
                    f3 = (float)Math.toRadians(f24 + (float)n6);
                    this.GL.glVertex2f(fArray[n * 2 - 2] + (float)(Math.cos(f3) * (double)f4), fArray[n * 2 - 1] + (float)(Math.sin(f3) * (double)f4));
                    n6 = (int)((float)n6 + f2);
                }
                this.GL.glEnd();
            }
        }
    }

    private void bindColor(int n) {
        if (n < this.cpt) {
            if (this.renderHalf) {
                this.GL.glColor4f(this.colours[n * 4] * 0.5f, this.colours[n * 4 + 1] * 0.5f, this.colours[n * 4 + 2] * 0.5f, this.colours[n * 4 + 3] * 0.5f);
            } else {
                this.GL.glColor4f(this.colours[n * 4], this.colours[n * 4 + 1], this.colours[n * 4 + 2], this.colours[n * 4 + 3]);
            }
        }
    }

    @Override
    public void color(float f, float f2, float f3, float f4) {
        if (this.width == 1.0f) {
            this.def.color(f, f2, f3, f4);
            return;
        }
        this.colours[this.pts * 4] = f;
        this.colours[this.pts * 4 + 1] = f2;
        this.colours[this.pts * 4 + 2] = f3;
        this.colours[this.pts * 4 + 3] = f4;
        ++this.cpt;
    }

    @Override
    public boolean applyGLLineFixes() {
        if (this.width == 1.0f) {
            return this.def.applyGLLineFixes();
        }
        return this.def.applyGLLineFixes();
    }
}

