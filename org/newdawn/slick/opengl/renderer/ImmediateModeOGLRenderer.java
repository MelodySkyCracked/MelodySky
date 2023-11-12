/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.EXTSecondaryColor
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 */
package org.newdawn.slick.opengl.renderer;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.EXTSecondaryColor;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.renderer.SGL;

public class ImmediateModeOGLRenderer
implements SGL {
    private int width;
    private int height;
    private float[] current = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    protected float alphaScale = 1.0f;

    @Override
    public void initDisplay(int n, int n2) {
        this.width = n;
        this.height = n2;
        String string = GL11.glGetString((int)7939);
        GL11.glEnable((int)3553);
        GL11.glShadeModel((int)7425);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)2896);
        GL11.glClearColor((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glClearDepth((double)1.0);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glViewport((int)0, (int)0, (int)n, (int)n2);
        GL11.glMatrixMode((int)5888);
    }

    @Override
    public void enterOrtho(int n, int n2) {
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)this.width, (double)this.height, (double)0.0, (double)1.0, (double)-1.0);
        GL11.glMatrixMode((int)5888);
        GL11.glTranslatef((float)((this.width - n) / 2), (float)((this.height - n2) / 2), (float)0.0f);
    }

    @Override
    public void glBegin(int n) {
        GL11.glBegin((int)n);
    }

    @Override
    public void glBindTexture(int n, int n2) {
        GL11.glBindTexture((int)n, (int)n2);
    }

    @Override
    public void glBlendFunc(int n, int n2) {
        GL11.glBlendFunc((int)n, (int)n2);
    }

    @Override
    public void glCallList(int n) {
        GL11.glCallList((int)n);
    }

    @Override
    public void glClear(int n) {
        GL11.glClear((int)n);
    }

    @Override
    public void glClearColor(float f, float f2, float f3, float f4) {
        GL11.glClearColor((float)f, (float)f2, (float)f3, (float)f4);
    }

    @Override
    public void glClipPlane(int n, DoubleBuffer doubleBuffer) {
        GL11.glClipPlane((int)n, (DoubleBuffer)doubleBuffer);
    }

    @Override
    public void glColor4f(float f, float f2, float f3, float f4) {
        this.current[0] = f;
        this.current[1] = f2;
        this.current[2] = f3;
        this.current[3] = f4 *= this.alphaScale;
        GL11.glColor4f((float)f, (float)f2, (float)f3, (float)f4);
    }

    @Override
    public void glColorMask(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        GL11.glColorMask((boolean)bl, (boolean)bl2, (boolean)bl3, (boolean)bl4);
    }

    @Override
    public void glCopyTexImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        GL11.glCopyTexImage2D((int)n, (int)n2, (int)n3, (int)n4, (int)n5, (int)n6, (int)n7, (int)n8);
    }

    @Override
    public void glDeleteTextures(IntBuffer intBuffer) {
        GL11.glDeleteTextures((IntBuffer)intBuffer);
    }

    @Override
    public void glDisable(int n) {
        GL11.glDisable((int)n);
    }

    @Override
    public void glEnable(int n) {
        GL11.glEnable((int)n);
    }

    @Override
    public void glEnd() {
        GL11.glEnd();
    }

    @Override
    public void glEndList() {
        GL11.glEndList();
    }

    @Override
    public int glGenLists(int n) {
        return GL11.glGenLists((int)n);
    }

    @Override
    public void glGetFloat(int n, FloatBuffer floatBuffer) {
        GL11.glGetFloat((int)n, (FloatBuffer)floatBuffer);
    }

    @Override
    public void glGetInteger(int n, IntBuffer intBuffer) {
        GL11.glGetInteger((int)n, (IntBuffer)intBuffer);
    }

    @Override
    public void glGetTexImage(int n, int n2, int n3, int n4, ByteBuffer byteBuffer) {
        GL11.glGetTexImage((int)n, (int)n2, (int)n3, (int)n4, (ByteBuffer)byteBuffer);
    }

    @Override
    public void glLineWidth(float f) {
        GL11.glLineWidth((float)f);
    }

    @Override
    public void glLoadIdentity() {
        GL11.glLoadIdentity();
    }

    @Override
    public void glNewList(int n, int n2) {
        GL11.glNewList((int)n, (int)n2);
    }

    @Override
    public void glPointSize(float f) {
        GL11.glPointSize((float)f);
    }

    @Override
    public void glPopMatrix() {
        GL11.glPopMatrix();
    }

    @Override
    public void glPushMatrix() {
        GL11.glPushMatrix();
    }

    @Override
    public void glReadPixels(int n, int n2, int n3, int n4, int n5, int n6, ByteBuffer byteBuffer) {
        GL11.glReadPixels((int)n, (int)n2, (int)n3, (int)n4, (int)n5, (int)n6, (ByteBuffer)byteBuffer);
    }

    @Override
    public void glRotatef(float f, float f2, float f3, float f4) {
        GL11.glRotatef((float)f, (float)f2, (float)f3, (float)f4);
    }

    @Override
    public void glScalef(float f, float f2, float f3) {
        GL11.glScalef((float)f, (float)f2, (float)f3);
    }

    @Override
    public void glScissor(int n, int n2, int n3, int n4) {
        GL11.glScissor((int)n, (int)n2, (int)n3, (int)n4);
    }

    @Override
    public void glTexCoord2f(float f, float f2) {
        GL11.glTexCoord2f((float)f, (float)f2);
    }

    @Override
    public void glTexEnvi(int n, int n2, int n3) {
        GL11.glTexEnvi((int)n, (int)n2, (int)n3);
    }

    @Override
    public void glTranslatef(float f, float f2, float f3) {
        GL11.glTranslatef((float)f, (float)f2, (float)f3);
    }

    @Override
    public void glVertex2f(float f, float f2) {
        GL11.glVertex2f((float)f, (float)f2);
    }

    @Override
    public void glVertex3f(float f, float f2, float f3) {
        GL11.glVertex3f((float)f, (float)f2, (float)f3);
    }

    @Override
    public void flush() {
    }

    @Override
    public void glTexParameteri(int n, int n2, int n3) {
        GL11.glTexParameteri((int)n, (int)n2, (int)n3);
    }

    @Override
    public float[] getCurrentColor() {
        return this.current;
    }

    @Override
    public void glDeleteLists(int n, int n2) {
        GL11.glDeleteLists((int)n, (int)n2);
    }

    @Override
    public void glClearDepth(float f) {
        GL11.glClearDepth((double)f);
    }

    @Override
    public void glDepthFunc(int n) {
        GL11.glDepthFunc((int)n);
    }

    @Override
    public void glDepthMask(boolean bl) {
        GL11.glDepthMask((boolean)bl);
    }

    @Override
    public void setGlobalAlphaScale(float f) {
        this.alphaScale = f;
    }

    @Override
    public void glLoadMatrix(FloatBuffer floatBuffer) {
        GL11.glLoadMatrix((FloatBuffer)floatBuffer);
    }

    @Override
    public void glGenTextures(IntBuffer intBuffer) {
        GL11.glGenTextures((IntBuffer)intBuffer);
    }

    @Override
    public void glGetError() {
        GL11.glGetError();
    }

    @Override
    public void glTexImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, ByteBuffer byteBuffer) {
        GL11.glTexImage2D((int)n, (int)n2, (int)n3, (int)n4, (int)n5, (int)n6, (int)n7, (int)n8, (ByteBuffer)byteBuffer);
    }

    @Override
    public void glTexSubImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, ByteBuffer byteBuffer) {
        GL11.glTexSubImage2D((int)n, (int)n2, (int)n3, (int)n4, (int)n5, (int)n6, (int)n7, (int)n8, (ByteBuffer)byteBuffer);
    }

    @Override
    public boolean canTextureMirrorClamp() {
        return GLContext.getCapabilities().GL_EXT_texture_mirror_clamp;
    }

    @Override
    public boolean canSecondaryColor() {
        return GLContext.getCapabilities().GL_EXT_secondary_color;
    }

    @Override
    public void glSecondaryColor3ubEXT(byte by, byte by2, byte by3) {
        EXTSecondaryColor.glSecondaryColor3ubEXT((byte)by, (byte)by2, (byte)by3);
    }
}

