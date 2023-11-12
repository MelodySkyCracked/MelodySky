/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 */
package org.newdawn.slick;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.security.AccessController;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.I;
import org.newdawn.slick.Image;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.LineStripRenderer;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.FastTrig;

public class Graphics {
    protected static SGL GL = Renderer.get();
    private static LineStripRenderer LSR = Renderer.getLineStripRenderer();
    public static int MODE_NORMAL = 1;
    public static int MODE_ALPHA_MAP = 2;
    public static int MODE_ALPHA_BLEND = 3;
    public static int MODE_COLOR_MULTIPLY = 4;
    public static int MODE_ADD = 5;
    public static int MODE_SCREEN = 6;
    private static final int DEFAULT_SEGMENTS = 50;
    protected static Graphics currentGraphics = null;
    protected static Font DEFAULT_FONT;
    private float sx = 1.0f;
    private float sy = 1.0f;
    private Font font;
    private Color currentColor = Color.white;
    protected int screenWidth;
    protected int screenHeight;
    private boolean pushed;
    private Rectangle clip;
    private DoubleBuffer worldClip = BufferUtils.createDoubleBuffer((int)4);
    private ByteBuffer readBuffer = BufferUtils.createByteBuffer((int)4);
    private boolean antialias;
    private Rectangle worldClipRecord;
    private int currentDrawingMode = MODE_NORMAL;
    private float lineWidth = 1.0f;
    private ArrayList stack = new ArrayList();
    private int stackIndex;

    public static void setCurrent(Graphics graphics) {
        if (currentGraphics != graphics) {
            if (currentGraphics != null) {
                currentGraphics.disable();
            }
            currentGraphics = graphics;
            currentGraphics.enable();
        }
    }

    public Graphics() {
    }

    public Graphics(int n, int n2) {
        if (DEFAULT_FONT == null) {
            AccessController.doPrivileged(new I(this));
        }
        this.font = DEFAULT_FONT;
        this.screenWidth = n;
        this.screenHeight = n2;
    }

    void setDimensions(int n, int n2) {
        this.screenWidth = n;
        this.screenHeight = n2;
    }

    public void setDrawMode(int n) {
        this.predraw();
        this.currentDrawingMode = n;
        if (this.currentDrawingMode == MODE_NORMAL) {
            GL.glEnable(3042);
            GL.glColorMask(true, true, true, true);
            GL.glBlendFunc(770, 771);
        }
        if (this.currentDrawingMode == MODE_ALPHA_MAP) {
            GL.glDisable(3042);
            GL.glColorMask(false, false, false, true);
        }
        if (this.currentDrawingMode == MODE_ALPHA_BLEND) {
            GL.glEnable(3042);
            GL.glColorMask(true, true, true, false);
            GL.glBlendFunc(772, 773);
        }
        if (this.currentDrawingMode == MODE_COLOR_MULTIPLY) {
            GL.glEnable(3042);
            GL.glColorMask(true, true, true, true);
            GL.glBlendFunc(769, 768);
        }
        if (this.currentDrawingMode == MODE_ADD) {
            GL.glEnable(3042);
            GL.glColorMask(true, true, true, true);
            GL.glBlendFunc(1, 1);
        }
        if (this.currentDrawingMode == MODE_SCREEN) {
            GL.glEnable(3042);
            GL.glColorMask(true, true, true, true);
            GL.glBlendFunc(1, 769);
        }
        this.postdraw();
    }

    public void clearAlphaMap() {
        this.pushTransform();
        GL.glLoadIdentity();
        int n = this.currentDrawingMode;
        this.setDrawMode(MODE_ALPHA_MAP);
        this.setColor(new Color(0, 0, 0, 0));
        this.fillRect(0.0f, 0.0f, this.screenWidth, this.screenHeight);
        this.setColor(this.currentColor);
        this.setDrawMode(n);
        this.popTransform();
    }

    private void predraw() {
        Graphics.setCurrent(this);
    }

    private void postdraw() {
    }

    protected void enable() {
    }

    public void flush() {
        if (currentGraphics == this) {
            currentGraphics.disable();
            currentGraphics = null;
        }
    }

    protected void disable() {
    }

    public Font getFont() {
        return this.font;
    }

    public void setBackground(Color color) {
        this.predraw();
        GL.glClearColor(color.r, color.g, color.b, color.a);
        this.postdraw();
    }

    public Color getBackground() {
        this.predraw();
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer((int)16);
        GL.glGetFloat(3106, floatBuffer);
        this.postdraw();
        return new Color(floatBuffer);
    }

    public void clear() {
        this.predraw();
        GL.glClear(16384);
        this.postdraw();
    }

    public void resetTransform() {
        this.sx = 1.0f;
        this.sy = 1.0f;
        if (this.pushed) {
            this.predraw();
            GL.glPopMatrix();
            this.pushed = false;
            this.postdraw();
        }
    }

    private void checkPush() {
        if (!this.pushed) {
            this.predraw();
            GL.glPushMatrix();
            this.pushed = true;
            this.postdraw();
        }
    }

    public void scale(float f, float f2) {
        this.sx *= f;
        this.sy *= f2;
        this.checkPush();
        this.predraw();
        GL.glScalef(f, f2, 1.0f);
        this.postdraw();
    }

    public void rotate(float f, float f2, float f3) {
        this.checkPush();
        this.predraw();
        this.translate(f, f2);
        GL.glRotatef(f3, 0.0f, 0.0f, 1.0f);
        this.translate(-f, -f2);
        this.postdraw();
    }

    public void translate(float f, float f2) {
        this.checkPush();
        this.predraw();
        GL.glTranslatef(f, f2, 0.0f);
        this.postdraw();
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void resetFont() {
        this.font = DEFAULT_FONT;
    }

    public void setColor(Color color) {
        if (color == null) {
            return;
        }
        this.currentColor = new Color(color);
        this.predraw();
        this.currentColor.bind();
        this.postdraw();
    }

    public Color getColor() {
        return new Color(this.currentColor);
    }

    public void drawLine(float f, float f2, float f3, float f4) {
        float f5 = this.lineWidth - 1.0f;
        if (LSR.applyGLLineFixes()) {
            if (f == f3) {
                float f6;
                if (f2 > f4) {
                    f6 = f4;
                    f4 = f2;
                    f2 = f6;
                }
                f6 = 1.0f / this.sy;
                this.fillRect(f - (f5 /= this.sy) / 2.0f, f2 - f5 / 2.0f, f5 + f6, f4 - f2 + f5 + f6);
                return;
            }
            if (f2 == f4) {
                float f7;
                if (f > f3) {
                    f7 = f3;
                    f3 = f;
                    f = f7;
                }
                f7 = 1.0f / this.sx;
                this.fillRect(f - (f5 /= this.sx) / 2.0f, f2 - f5 / 2.0f, f3 - f + f5 + f7, f5 + f7);
                return;
            }
        }
        this.predraw();
        this.currentColor.bind();
        TextureImpl.bindNone();
        LSR.start();
        LSR.vertex(f, f2);
        LSR.vertex(f3, f4);
        LSR.end();
        this.postdraw();
    }

    public void draw(Shape shape, ShapeFill shapeFill) {
        this.predraw();
        TextureImpl.bindNone();
        ShapeRenderer.draw(shape, shapeFill);
        this.currentColor.bind();
        this.postdraw();
    }

    public void fill(Shape shape, ShapeFill shapeFill) {
        this.predraw();
        TextureImpl.bindNone();
        ShapeRenderer.fill(shape, shapeFill);
        this.currentColor.bind();
        this.postdraw();
    }

    public void draw(Shape shape) {
        this.predraw();
        TextureImpl.bindNone();
        this.currentColor.bind();
        ShapeRenderer.draw(shape);
        this.postdraw();
    }

    public void fill(Shape shape) {
        this.predraw();
        TextureImpl.bindNone();
        this.currentColor.bind();
        ShapeRenderer.fill(shape);
        this.postdraw();
    }

    public void texture(Shape shape, Image image) {
        this.texture(shape, image, 0.01f, 0.01f, false);
    }

    public void texture(Shape shape, Image image, ShapeFill shapeFill) {
        this.texture(shape, image, 0.01f, 0.01f, shapeFill);
    }

    public void texture(Shape shape, Image image, boolean bl) {
        if (bl) {
            this.texture(shape, image, 1.0f, 1.0f, true);
        } else {
            this.texture(shape, image, 0.01f, 0.01f, false);
        }
    }

    public void texture(Shape shape, Image image, float f, float f2) {
        this.texture(shape, image, f, f2, false);
    }

    public void texture(Shape shape, Image image, float f, float f2, boolean bl) {
        this.predraw();
        TextureImpl.bindNone();
        this.currentColor.bind();
        if (bl) {
            ShapeRenderer.textureFit(shape, image, f, f2);
        } else {
            ShapeRenderer.texture(shape, image, f, f2);
        }
        this.postdraw();
    }

    public void texture(Shape shape, Image image, float f, float f2, ShapeFill shapeFill) {
        this.predraw();
        TextureImpl.bindNone();
        this.currentColor.bind();
        ShapeRenderer.texture(shape, image, f, f2, shapeFill);
        this.postdraw();
    }

    public void drawRect(float f, float f2, float f3, float f4) {
        float f5 = this.getLineWidth();
        this.drawLine(f, f2, f + f3, f2);
        this.drawLine(f + f3, f2, f + f3, f2 + f4);
        this.drawLine(f + f3, f2 + f4, f, f2 + f4);
        this.drawLine(f, f2 + f4, f, f2);
    }

    public void clearClip() {
        this.clip = null;
        this.predraw();
        GL.glDisable(3089);
        this.postdraw();
    }

    public void setWorldClip(float f, float f2, float f3, float f4) {
        this.predraw();
        this.worldClipRecord = new Rectangle(f, f2, f3, f4);
        GL.glEnable(12288);
        this.worldClip.put(1.0).put(0.0).put(0.0).put(-f).flip();
        GL.glClipPlane(12288, this.worldClip);
        GL.glEnable(12289);
        this.worldClip.put(-1.0).put(0.0).put(0.0).put(f + f3).flip();
        GL.glClipPlane(12289, this.worldClip);
        GL.glEnable(12290);
        this.worldClip.put(0.0).put(1.0).put(0.0).put(-f2).flip();
        GL.glClipPlane(12290, this.worldClip);
        GL.glEnable(12291);
        this.worldClip.put(0.0).put(-1.0).put(0.0).put(f2 + f4).flip();
        GL.glClipPlane(12291, this.worldClip);
        this.postdraw();
    }

    public void clearWorldClip() {
        this.predraw();
        this.worldClipRecord = null;
        GL.glDisable(12288);
        GL.glDisable(12289);
        GL.glDisable(12290);
        GL.glDisable(12291);
        this.postdraw();
    }

    public void setWorldClip(Rectangle rectangle) {
        if (rectangle == null) {
            this.clearWorldClip();
        } else {
            this.setWorldClip(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        }
    }

    public Rectangle getWorldClip() {
        return this.worldClipRecord;
    }

    public void setClip(int n, int n2, int n3, int n4) {
        this.predraw();
        if (this.clip == null) {
            GL.glEnable(3089);
            this.clip = new Rectangle(n, n2, n3, n4);
        } else {
            this.clip.setBounds(n, n2, n3, n4);
        }
        GL.glScissor(n, this.screenHeight - n2 - n4, n3, n4);
        this.postdraw();
    }

    public void setClip(Rectangle rectangle) {
        if (rectangle == null) {
            this.clearClip();
            return;
        }
        this.setClip((int)rectangle.getX(), (int)rectangle.getY(), (int)rectangle.getWidth(), (int)rectangle.getHeight());
    }

    public Rectangle getClip() {
        return this.clip;
    }

    public void fillRect(float f, float f2, float f3, float f4, Image image, float f5, float f6) {
        int n = (int)Math.ceil(f3 / (float)image.getWidth()) + 2;
        int n2 = (int)Math.ceil(f4 / (float)image.getHeight()) + 2;
        Rectangle rectangle = this.getWorldClip();
        this.setWorldClip(f, f2, f3, f4);
        this.predraw();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                image.draw((float)(i * image.getWidth()) + f - f5, (float)(j * image.getHeight()) + f2 - f6);
            }
        }
        this.postdraw();
        this.setWorldClip(rectangle);
    }

    public void fillRect(float f, float f2, float f3, float f4) {
        this.predraw();
        TextureImpl.bindNone();
        this.currentColor.bind();
        GL.glBegin(7);
        GL.glVertex2f(f, f2);
        GL.glVertex2f(f + f3, f2);
        GL.glVertex2f(f + f3, f2 + f4);
        GL.glVertex2f(f, f2 + f4);
        GL.glEnd();
        this.postdraw();
    }

    public void drawOval(float f, float f2, float f3, float f4) {
        this.drawOval(f, f2, f3, f4, 50);
    }

    public void drawOval(float f, float f2, float f3, float f4, int n) {
        this.drawArc(f, f2, f3, f4, n, 0.0f, 360.0f);
    }

    public void drawArc(float f, float f2, float f3, float f4, float f5, float f6) {
        this.drawArc(f, f2, f3, f4, 50, f5, f6);
    }

    public void drawArc(float f, float f2, float f3, float f4, int n, float f5, float f6) {
        this.predraw();
        TextureImpl.bindNone();
        this.currentColor.bind();
        while (f6 < f5) {
            f6 += 360.0f;
        }
        float f7 = f + f3 / 2.0f;
        float f8 = f2 + f4 / 2.0f;
        LSR.start();
        int n2 = 360 / n;
        for (int i = (int)f5; i < (int)(f6 + (float)n2); i += n2) {
            float f9 = i;
            if (f9 > f6) {
                f9 = f6;
            }
            float f10 = (float)((double)f7 + FastTrig.cos(Math.toRadians(f9)) * (double)f3 / 2.0);
            float f11 = (float)((double)f8 + FastTrig.sin(Math.toRadians(f9)) * (double)f4 / 2.0);
            LSR.vertex(f10, f11);
        }
        LSR.end();
        this.postdraw();
    }

    public void fillOval(float f, float f2, float f3, float f4) {
        this.fillOval(f, f2, f3, f4, 50);
    }

    public void fillOval(float f, float f2, float f3, float f4, int n) {
        this.fillArc(f, f2, f3, f4, n, 0.0f, 360.0f);
    }

    public void fillArc(float f, float f2, float f3, float f4, float f5, float f6) {
        this.fillArc(f, f2, f3, f4, 50, f5, f6);
    }

    public void fillArc(float f, float f2, float f3, float f4, int n, float f5, float f6) {
        float f7;
        float f8;
        float f9;
        int n2;
        this.predraw();
        TextureImpl.bindNone();
        this.currentColor.bind();
        while (f6 < f5) {
            f6 += 360.0f;
        }
        float f10 = f + f3 / 2.0f;
        float f11 = f2 + f4 / 2.0f;
        GL.glBegin(6);
        int n3 = 360 / n;
        GL.glVertex2f(f10, f11);
        for (n2 = (int)f5; n2 < (int)(f6 + (float)n3); n2 += n3) {
            f9 = n2;
            if (f9 > f6) {
                f9 = f6;
            }
            f8 = (float)((double)f10 + FastTrig.cos(Math.toRadians(f9)) * (double)f3 / 2.0);
            f7 = (float)((double)f11 + FastTrig.sin(Math.toRadians(f9)) * (double)f4 / 2.0);
            GL.glVertex2f(f8, f7);
        }
        GL.glEnd();
        if (this.antialias) {
            GL.glBegin(6);
            GL.glVertex2f(f10, f11);
            if (f6 != 360.0f) {
                f6 -= 10.0f;
            }
            for (n2 = (int)f5; n2 < (int)(f6 + (float)n3); n2 += n3) {
                f9 = n2;
                if (f9 > f6) {
                    f9 = f6;
                }
                f8 = (float)((double)f10 + FastTrig.cos(Math.toRadians(f9 + 10.0f)) * (double)f3 / 2.0);
                f7 = (float)((double)f11 + FastTrig.sin(Math.toRadians(f9 + 10.0f)) * (double)f4 / 2.0);
                GL.glVertex2f(f8, f7);
            }
            GL.glEnd();
        }
        this.postdraw();
    }

    public void drawRoundRect(float f, float f2, float f3, float f4, int n) {
        this.drawRoundRect(f, f2, f3, f4, n, 50);
    }

    public void drawRoundRect(float f, float f2, float f3, float f4, int n, int n2) {
        if (n < 0) {
            throw new IllegalArgumentException("corner radius must be > 0");
        }
        if (n == 0) {
            this.drawRect(f, f2, f3, f4);
            return;
        }
        int n3 = (int)Math.min(f3, f4) / 2;
        if (n > n3) {
            n = n3;
        }
        this.drawLine(f + (float)n, f2, f + f3 - (float)n, f2);
        this.drawLine(f, f2 + (float)n, f, f2 + f4 - (float)n);
        this.drawLine(f + f3, f2 + (float)n, f + f3, f2 + f4 - (float)n);
        this.drawLine(f + (float)n, f2 + f4, f + f3 - (float)n, f2 + f4);
        float f5 = n * 2;
        this.drawArc(f + f3 - f5, f2 + f4 - f5, f5, f5, n2, 0.0f, 90.0f);
        this.drawArc(f, f2 + f4 - f5, f5, f5, n2, 90.0f, 180.0f);
        this.drawArc(f + f3 - f5, f2, f5, f5, n2, 270.0f, 360.0f);
        this.drawArc(f, f2, f5, f5, n2, 180.0f, 270.0f);
    }

    public void fillRoundRect(float f, float f2, float f3, float f4, int n) {
        this.fillRoundRect(f, f2, f3, f4, n, 50);
    }

    public void fillRoundRect(float f, float f2, float f3, float f4, int n, int n2) {
        if (n < 0) {
            throw new IllegalArgumentException("corner radius must be > 0");
        }
        if (n == 0) {
            this.fillRect(f, f2, f3, f4);
            return;
        }
        int n3 = (int)Math.min(f3, f4) / 2;
        if (n > n3) {
            n = n3;
        }
        float f5 = n * 2;
        this.fillRect(f + (float)n, f2, f3 - f5, n);
        this.fillRect(f, f2 + (float)n, n, f4 - f5);
        this.fillRect(f + f3 - (float)n, f2 + (float)n, n, f4 - f5);
        this.fillRect(f + (float)n, f2 + f4 - (float)n, f3 - f5, n);
        this.fillRect(f + (float)n, f2 + (float)n, f3 - f5, f4 - f5);
        this.fillArc(f + f3 - f5, f2 + f4 - f5, f5, f5, n2, 0.0f, 90.0f);
        this.fillArc(f, f2 + f4 - f5, f5, f5, n2, 90.0f, 180.0f);
        this.fillArc(f + f3 - f5, f2, f5, f5, n2, 270.0f, 360.0f);
        this.fillArc(f, f2, f5, f5, n2, 180.0f, 270.0f);
    }

    public void setLineWidth(float f) {
        this.predraw();
        this.lineWidth = f;
        LSR.setWidth(f);
        GL.glPointSize(f);
        this.postdraw();
    }

    public float getLineWidth() {
        return this.lineWidth;
    }

    public void resetLineWidth() {
        this.predraw();
        Renderer.getLineStripRenderer().setWidth(1.0f);
        GL.glLineWidth(1.0f);
        GL.glPointSize(1.0f);
        this.postdraw();
    }

    public void setAntiAlias(boolean bl) {
        this.predraw();
        this.antialias = bl;
        LSR.setAntiAlias(bl);
        if (bl) {
            GL.glEnable(2881);
        } else {
            GL.glDisable(2881);
        }
        this.postdraw();
    }

    public boolean isAntiAlias() {
        return this.antialias;
    }

    public void drawString(String string, float f, float f2) {
        this.predraw();
        this.font.drawString(f, f2, string, this.currentColor);
        this.postdraw();
    }

    public void drawImage(Image image, float f, float f2, Color color) {
        this.predraw();
        image.draw(f, f2, color);
        this.currentColor.bind();
        this.postdraw();
    }

    public void drawAnimation(Animation animation, float f, float f2) {
        this.drawAnimation(animation, f, f2, Color.white);
    }

    public void drawAnimation(Animation animation, float f, float f2, Color color) {
        this.predraw();
        animation.draw(f, f2, color);
        this.currentColor.bind();
        this.postdraw();
    }

    public void drawImage(Image image, float f, float f2) {
        this.drawImage(image, f, f2, Color.white);
    }

    public void drawImage(Image image, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.predraw();
        image.draw(f, f2, f3, f4, f5, f6, f7, f8);
        this.currentColor.bind();
        this.postdraw();
    }

    public void drawImage(Image image, float f, float f2, float f3, float f4, float f5, float f6) {
        this.drawImage(image, f, f2, f + (float)image.getWidth(), f2 + (float)image.getHeight(), f3, f4, f5, f6);
    }

    public void copyArea(Image image, int n, int n2) {
        int n3 = image.getTexture().hasAlpha() ? 6408 : 6407;
        image.bind();
        GL.glCopyTexImage2D(3553, 0, n3, n, this.screenHeight - (n2 + image.getHeight()), image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight(), 0);
        image.ensureInverted();
    }

    private int translate(byte by) {
        if (by < 0) {
            return 256 + by;
        }
        return by;
    }

    public Color getPixel(int n, int n2) {
        this.predraw();
        GL.glReadPixels(n, this.screenHeight - n2, 1, 1, 6408, 5121, this.readBuffer);
        this.postdraw();
        return new Color(this.translate(this.readBuffer.get(0)), this.translate(this.readBuffer.get(1)), this.translate(this.readBuffer.get(2)), this.translate(this.readBuffer.get(3)));
    }

    public void getArea(int n, int n2, int n3, int n4, ByteBuffer byteBuffer) {
        if (byteBuffer.capacity() < n3 * n4 * 4) {
            throw new IllegalArgumentException("Byte buffer provided to get area is not big enough");
        }
        this.predraw();
        GL.glReadPixels(n, this.screenHeight - n2 - n4, n3, n4, 6408, 5121, byteBuffer);
        this.postdraw();
    }

    public void drawImage(Image image, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Color color) {
        this.predraw();
        image.draw(f, f2, f3, f4, f5, f6, f7, f8, color);
        this.currentColor.bind();
        this.postdraw();
    }

    public void drawImage(Image image, float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        this.drawImage(image, f, f2, f + (float)image.getWidth(), f2 + (float)image.getHeight(), f3, f4, f5, f6, color);
    }

    public void drawGradientLine(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12) {
        this.predraw();
        TextureImpl.bindNone();
        GL.glBegin(1);
        GL.glColor4f(f3, f4, f5, f6);
        GL.glVertex2f(f, f2);
        GL.glColor4f(f9, f10, f11, f12);
        GL.glVertex2f(f7, f8);
        GL.glEnd();
        this.postdraw();
    }

    public void drawGradientLine(float f, float f2, Color color, float f3, float f4, Color color2) {
        this.predraw();
        TextureImpl.bindNone();
        GL.glBegin(1);
        color.bind();
        GL.glVertex2f(f, f2);
        color2.bind();
        GL.glVertex2f(f3, f4);
        GL.glEnd();
        this.postdraw();
    }

    public void pushTransform() {
        FloatBuffer floatBuffer;
        this.predraw();
        if (this.stackIndex >= this.stack.size()) {
            floatBuffer = BufferUtils.createFloatBuffer((int)18);
            this.stack.add(floatBuffer);
        } else {
            floatBuffer = (FloatBuffer)this.stack.get(this.stackIndex);
        }
        GL.glGetFloat(2982, floatBuffer);
        floatBuffer.put(16, this.sx);
        floatBuffer.put(17, this.sy);
        ++this.stackIndex;
        this.postdraw();
    }

    public void popTransform() {
        if (this.stackIndex == 0) {
            throw new RuntimeException("Attempt to pop a transform that hasn't be pushed");
        }
        this.predraw();
        --this.stackIndex;
        FloatBuffer floatBuffer = (FloatBuffer)this.stack.get(this.stackIndex);
        GL.glLoadMatrix(floatBuffer);
        this.sx = floatBuffer.get(16);
        this.sy = floatBuffer.get(17);
        this.postdraw();
    }

    public void destroy() {
    }
}

