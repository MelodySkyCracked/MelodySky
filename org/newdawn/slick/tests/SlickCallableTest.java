/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 */
package org.newdawn.slick.tests;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tests.lI;

public class SlickCallableTest
extends BasicGame {
    private Image image;
    private Image back;
    private float rot;
    private AngelCodeFont font;
    private Animation homer;

    public SlickCallableTest() {
        super("Slick Callable Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.image = new Image("testdata/rocket.png");
        this.back = new Image("testdata/sky.jpg");
        this.font = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
        SpriteSheet spriteSheet = new SpriteSheet("testdata/homeranim.png", 36, 65);
        this.homer = new Animation(spriteSheet, 0, 0, 7, 0, true, 150, true);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.scale(2.0f, 2.0f);
        graphics.fillRect(0.0f, 0.0f, 800.0f, 600.0f, this.back, 0.0f, 0.0f);
        graphics.resetTransform();
        graphics.drawImage(this.image, 100.0f, 100.0f);
        this.image.draw(100.0f, 200.0f, 80.0f, 200.0f);
        this.font.drawString(100.0f, 200.0f, "Text Drawn before the callable");
        lI lI2 = new lI(this);
        lI2.call();
        this.homer.draw(450.0f, 250.0f, 80.0f, 200.0f);
        this.font.drawString(150.0f, 300.0f, "Text Drawn after the callable");
    }

    public void renderGL() {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer((int)4);
        floatBuffer.put(new float[]{5.0f, 5.0f, 10.0f, 0.0f}).flip();
        FloatBuffer floatBuffer2 = BufferUtils.createFloatBuffer((int)4);
        floatBuffer2.put(new float[]{0.8f, 0.1f, 0.0f, 1.0f}).flip();
        GL11.glLight((int)16384, (int)4611, (FloatBuffer)floatBuffer);
        GL11.glEnable((int)16384);
        GL11.glEnable((int)2884);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)2896);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        float f = 0.75f;
        GL11.glFrustum((double)-1.0, (double)1.0, (double)(-f), (double)f, (double)5.0, (double)60.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-40.0f);
        GL11.glRotatef((float)this.rot, (float)0.0f, (float)1.0f, (float)1.0f);
        GL11.glMaterial((int)1028, (int)5634, (FloatBuffer)floatBuffer2);
        this.gear(0.5f, 2.0f, 2.0f, 10, 0.7f);
    }

    private void gear(float f, float f2, float f3, int n, float f4) {
        float f5;
        int n2;
        float f6 = f;
        float f7 = f2 - f4 / 2.0f;
        float f8 = f2 + f4 / 2.0f;
        float f9 = (float)Math.PI * 2 / (float)n / 4.0f;
        GL11.glShadeModel((int)7424);
        GL11.glNormal3f((float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glBegin((int)8);
        for (n2 = 0; n2 <= n; ++n2) {
            f5 = (float)n2 * 2.0f * (float)Math.PI / (float)n;
            GL11.glVertex3f((float)(f6 * (float)Math.cos(f5)), (float)(f6 * (float)Math.sin(f5)), (float)(f3 * 0.5f));
            GL11.glVertex3f((float)(f7 * (float)Math.cos(f5)), (float)(f7 * (float)Math.sin(f5)), (float)(f3 * 0.5f));
            if (n2 >= n) continue;
            GL11.glVertex3f((float)(f6 * (float)Math.cos(f5)), (float)(f6 * (float)Math.sin(f5)), (float)(f3 * 0.5f));
            GL11.glVertex3f((float)(f7 * (float)Math.cos(f5 + 3.0f * f9)), (float)(f7 * (float)Math.sin(f5 + 3.0f * f9)), (float)(f3 * 0.5f));
        }
        GL11.glEnd();
        GL11.glBegin((int)7);
        for (n2 = 0; n2 < n; ++n2) {
            f5 = (float)n2 * 2.0f * (float)Math.PI / (float)n;
            GL11.glVertex3f((float)(f7 * (float)Math.cos(f5)), (float)(f7 * (float)Math.sin(f5)), (float)(f3 * 0.5f));
            GL11.glVertex3f((float)(f8 * (float)Math.cos(f5 + f9)), (float)(f8 * (float)Math.sin(f5 + f9)), (float)(f3 * 0.5f));
            GL11.glVertex3f((float)(f8 * (float)Math.cos(f5 + 2.0f * f9)), (float)(f8 * (float)Math.sin(f5 + 2.0f * f9)), (float)(f3 * 0.5f));
            GL11.glVertex3f((float)(f7 * (float)Math.cos(f5 + 3.0f * f9)), (float)(f7 * (float)Math.sin(f5 + 3.0f * f9)), (float)(f3 * 0.5f));
        }
        GL11.glEnd();
        GL11.glNormal3f((float)0.0f, (float)0.0f, (float)-1.0f);
        GL11.glBegin((int)8);
        for (n2 = 0; n2 <= n; ++n2) {
            f5 = (float)n2 * 2.0f * (float)Math.PI / (float)n;
            GL11.glVertex3f((float)(f7 * (float)Math.cos(f5)), (float)(f7 * (float)Math.sin(f5)), (float)(-f3 * 0.5f));
            GL11.glVertex3f((float)(f6 * (float)Math.cos(f5)), (float)(f6 * (float)Math.sin(f5)), (float)(-f3 * 0.5f));
            GL11.glVertex3f((float)(f7 * (float)Math.cos(f5 + 3.0f * f9)), (float)(f7 * (float)Math.sin(f5 + 3.0f * f9)), (float)(-f3 * 0.5f));
            GL11.glVertex3f((float)(f6 * (float)Math.cos(f5)), (float)(f6 * (float)Math.sin(f5)), (float)(-f3 * 0.5f));
        }
        GL11.glEnd();
        GL11.glBegin((int)7);
        for (n2 = 0; n2 < n; ++n2) {
            f5 = (float)n2 * 2.0f * (float)Math.PI / (float)n;
            GL11.glVertex3f((float)(f7 * (float)Math.cos(f5 + 3.0f * f9)), (float)(f7 * (float)Math.sin(f5 + 3.0f * f9)), (float)(-f3 * 0.5f));
            GL11.glVertex3f((float)(f8 * (float)Math.cos(f5 + 2.0f * f9)), (float)(f8 * (float)Math.sin(f5 + 2.0f * f9)), (float)(-f3 * 0.5f));
            GL11.glVertex3f((float)(f8 * (float)Math.cos(f5 + f9)), (float)(f8 * (float)Math.sin(f5 + f9)), (float)(-f3 * 0.5f));
            GL11.glVertex3f((float)(f7 * (float)Math.cos(f5)), (float)(f7 * (float)Math.sin(f5)), (float)(-f3 * 0.5f));
        }
        GL11.glEnd();
        GL11.glNormal3f((float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glBegin((int)8);
        for (n2 = 0; n2 < n; ++n2) {
            f5 = (float)n2 * 2.0f * (float)Math.PI / (float)n;
            GL11.glVertex3f((float)(f7 * (float)Math.cos(f5)), (float)(f7 * (float)Math.sin(f5)), (float)(f3 * 0.5f));
            GL11.glVertex3f((float)(f7 * (float)Math.cos(f5)), (float)(f7 * (float)Math.sin(f5)), (float)(-f3 * 0.5f));
            float f10 = f8 * (float)Math.cos(f5 + f9) - f7 * (float)Math.cos(f5);
            float f11 = f8 * (float)Math.sin(f5 + f9) - f7 * (float)Math.sin(f5);
            float f12 = (float)Math.sqrt(f10 * f10 + f11 * f11);
            GL11.glNormal3f((float)(f11 /= f12), (float)(-(f10 /= f12)), (float)0.0f);
            GL11.glVertex3f((float)(f8 * (float)Math.cos(f5 + f9)), (float)(f8 * (float)Math.sin(f5 + f9)), (float)(f3 * 0.5f));
            GL11.glVertex3f((float)(f8 * (float)Math.cos(f5 + f9)), (float)(f8 * (float)Math.sin(f5 + f9)), (float)(-f3 * 0.5f));
            GL11.glNormal3f((float)((float)Math.cos(f5)), (float)((float)Math.sin(f5)), (float)0.0f);
            GL11.glVertex3f((float)(f8 * (float)Math.cos(f5 + 2.0f * f9)), (float)(f8 * (float)Math.sin(f5 + 2.0f * f9)), (float)(f3 * 0.5f));
            GL11.glVertex3f((float)(f8 * (float)Math.cos(f5 + 2.0f * f9)), (float)(f8 * (float)Math.sin(f5 + 2.0f * f9)), (float)(-f3 * 0.5f));
            f10 = f7 * (float)Math.cos(f5 + 3.0f * f9) - f8 * (float)Math.cos(f5 + 2.0f * f9);
            f11 = f7 * (float)Math.sin(f5 + 3.0f * f9) - f8 * (float)Math.sin(f5 + 2.0f * f9);
            GL11.glNormal3f((float)f11, (float)(-f10), (float)0.0f);
            GL11.glVertex3f((float)(f7 * (float)Math.cos(f5 + 3.0f * f9)), (float)(f7 * (float)Math.sin(f5 + 3.0f * f9)), (float)(f3 * 0.5f));
            GL11.glVertex3f((float)(f7 * (float)Math.cos(f5 + 3.0f * f9)), (float)(f7 * (float)Math.sin(f5 + 3.0f * f9)), (float)(-f3 * 0.5f));
            GL11.glNormal3f((float)((float)Math.cos(f5)), (float)((float)Math.sin(f5)), (float)0.0f);
        }
        GL11.glVertex3f((float)(f7 * (float)Math.cos(0.0)), (float)(f7 * (float)Math.sin(0.0)), (float)(f3 * 0.5f));
        GL11.glVertex3f((float)(f7 * (float)Math.cos(0.0)), (float)(f7 * (float)Math.sin(0.0)), (float)(-f3 * 0.5f));
        GL11.glEnd();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)8);
        for (n2 = 0; n2 <= n; ++n2) {
            f5 = (float)n2 * 2.0f * (float)Math.PI / (float)n;
            GL11.glNormal3f((float)(-((float)Math.cos(f5))), (float)(-((float)Math.sin(f5))), (float)0.0f);
            GL11.glVertex3f((float)(f6 * (float)Math.cos(f5)), (float)(f6 * (float)Math.sin(f5)), (float)(-f3 * 0.5f));
            GL11.glVertex3f((float)(f6 * (float)Math.cos(f5)), (float)(f6 * (float)Math.sin(f5)), (float)(f3 * 0.5f));
        }
        GL11.glEnd();
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
        this.rot += (float)n * 0.1f;
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new SlickCallableTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

