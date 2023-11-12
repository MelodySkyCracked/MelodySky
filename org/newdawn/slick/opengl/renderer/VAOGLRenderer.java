/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 */
package org.newdawn.slick.opengl.renderer;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.renderer.ImmediateModeOGLRenderer;

public class VAOGLRenderer
extends ImmediateModeOGLRenderer {
    private static final int TOLERANCE = 20;
    public static final int NONE = -1;
    public static final int MAX_VERTS = 5000;
    private int currentType = -1;
    private float[] color = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    private float[] tex = new float[]{0.0f, 0.0f};
    private int vertIndex;
    private float[] verts = new float[15000];
    private float[] cols = new float[20000];
    private float[] texs = new float[15000];
    private FloatBuffer vertices = BufferUtils.createFloatBuffer((int)15000);
    private FloatBuffer colors = BufferUtils.createFloatBuffer((int)20000);
    private FloatBuffer textures = BufferUtils.createFloatBuffer((int)10000);
    private int listMode = 0;

    @Override
    public void initDisplay(int n, int n2) {
        super.initDisplay(n, n2);
        this.startBuffer();
        GL11.glEnableClientState((int)32884);
        GL11.glEnableClientState((int)32888);
        GL11.glEnableClientState((int)32886);
    }

    private void startBuffer() {
        this.vertIndex = 0;
    }

    private void flushBuffer() {
        if (this.vertIndex == 0) {
            return;
        }
        if (this.currentType == -1) {
            return;
        }
        if (this.vertIndex < 20) {
            GL11.glBegin((int)this.currentType);
            for (int i = 0; i < this.vertIndex; ++i) {
                GL11.glColor4f((float)this.cols[i * 4 + 0], (float)this.cols[i * 4 + 1], (float)this.cols[i * 4 + 2], (float)this.cols[i * 4 + 3]);
                GL11.glTexCoord2f((float)this.texs[i * 2 + 0], (float)this.texs[i * 2 + 1]);
                GL11.glVertex3f((float)this.verts[i * 3 + 0], (float)this.verts[i * 3 + 1], (float)this.verts[i * 3 + 2]);
            }
            GL11.glEnd();
            this.currentType = -1;
            return;
        }
        this.vertices.clear();
        this.colors.clear();
        this.textures.clear();
        this.vertices.put(this.verts, 0, this.vertIndex * 3);
        this.colors.put(this.cols, 0, this.vertIndex * 4);
        this.textures.put(this.texs, 0, this.vertIndex * 2);
        this.vertices.flip();
        this.colors.flip();
        this.textures.flip();
        GL11.glVertexPointer((int)3, (int)0, (FloatBuffer)this.vertices);
        GL11.glColorPointer((int)4, (int)0, (FloatBuffer)this.colors);
        GL11.glTexCoordPointer((int)2, (int)0, (FloatBuffer)this.textures);
        GL11.glDrawArrays((int)this.currentType, (int)0, (int)this.vertIndex);
        this.currentType = -1;
    }

    private void applyBuffer() {
        if (this.listMode > 0) {
            return;
        }
        if (this.vertIndex != 0) {
            this.flushBuffer();
            this.startBuffer();
        }
        super.glColor4f(this.color[0], this.color[1], this.color[2], this.color[3]);
    }

    @Override
    public void flush() {
        super.flush();
        this.applyBuffer();
    }

    @Override
    public void glBegin(int n) {
        if (this.listMode > 0) {
            super.glBegin(n);
            return;
        }
        if (this.currentType != n) {
            this.applyBuffer();
            this.currentType = n;
        }
    }

    @Override
    public void glColor4f(float f, float f2, float f3, float f4) {
        this.color[0] = f;
        this.color[1] = f2;
        this.color[2] = f3;
        this.color[3] = f4 *= this.alphaScale;
        if (this.listMode > 0) {
            super.glColor4f(f, f2, f3, f4);
            return;
        }
    }

    @Override
    public void glEnd() {
        if (this.listMode > 0) {
            super.glEnd();
            return;
        }
    }

    @Override
    public void glTexCoord2f(float f, float f2) {
        if (this.listMode > 0) {
            super.glTexCoord2f(f, f2);
            return;
        }
        this.tex[0] = f;
        this.tex[1] = f2;
    }

    @Override
    public void glVertex2f(float f, float f2) {
        if (this.listMode > 0) {
            super.glVertex2f(f, f2);
            return;
        }
        this.glVertex3f(f, f2, 0.0f);
    }

    /*
     * Exception decompiling
     */
    @Override
    public void glVertex3f(float var1, float var2, float var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl142 : RETURN - null : trying to set 0 previously set to 2
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void glBindTexture(int n, int n2) {
        this.applyBuffer();
        super.glBindTexture(n, n2);
    }

    @Override
    public void glBlendFunc(int n, int n2) {
        this.applyBuffer();
        super.glBlendFunc(n, n2);
    }

    @Override
    public void glCallList(int n) {
        this.applyBuffer();
        super.glCallList(n);
    }

    @Override
    public void glClear(int n) {
        this.applyBuffer();
        super.glClear(n);
    }

    @Override
    public void glClipPlane(int n, DoubleBuffer doubleBuffer) {
        this.applyBuffer();
        super.glClipPlane(n, doubleBuffer);
    }

    @Override
    public void glColorMask(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.applyBuffer();
        super.glColorMask(bl, bl2, bl3, bl4);
    }

    @Override
    public void glDisable(int n) {
        this.applyBuffer();
        super.glDisable(n);
    }

    @Override
    public void glEnable(int n) {
        this.applyBuffer();
        super.glEnable(n);
    }

    @Override
    public void glLineWidth(float f) {
        this.applyBuffer();
        super.glLineWidth(f);
    }

    @Override
    public void glPointSize(float f) {
        this.applyBuffer();
        super.glPointSize(f);
    }

    @Override
    public void glPopMatrix() {
        this.applyBuffer();
        super.glPopMatrix();
    }

    @Override
    public void glPushMatrix() {
        this.applyBuffer();
        super.glPushMatrix();
    }

    @Override
    public void glRotatef(float f, float f2, float f3, float f4) {
        this.applyBuffer();
        super.glRotatef(f, f2, f3, f4);
    }

    @Override
    public void glScalef(float f, float f2, float f3) {
        this.applyBuffer();
        super.glScalef(f, f2, f3);
    }

    @Override
    public void glScissor(int n, int n2, int n3, int n4) {
        this.applyBuffer();
        super.glScissor(n, n2, n3, n4);
    }

    @Override
    public void glTexEnvi(int n, int n2, int n3) {
        this.applyBuffer();
        super.glTexEnvi(n, n2, n3);
    }

    @Override
    public void glTranslatef(float f, float f2, float f3) {
        this.applyBuffer();
        super.glTranslatef(f, f2, f3);
    }

    @Override
    public void glEndList() {
        --this.listMode;
        super.glEndList();
    }

    @Override
    public void glNewList(int n, int n2) {
        ++this.listMode;
        super.glNewList(n, n2);
    }

    @Override
    public float[] getCurrentColor() {
        return this.color;
    }

    @Override
    public void glLoadMatrix(FloatBuffer floatBuffer) {
        this.flushBuffer();
        super.glLoadMatrix(floatBuffer);
    }
}

