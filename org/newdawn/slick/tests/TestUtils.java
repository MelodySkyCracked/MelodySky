/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.DisplayMode
 *  org.lwjgl.opengl.GL11
 */
package org.newdawn.slick.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.Log;

public class TestUtils {
    private Texture texture;
    private Audio oggEffect;
    private Audio wavEffect;
    private Audio aifEffect;
    private Audio oggStream;
    private Audio modStream;
    private Font font;

    public void start() {
        this.initGL(800, 600);
        this.init();
        while (true) {
            this.update();
            GL11.glClear((int)16384);
            this.render();
            Display.update();
            Display.sync((int)100);
            if (!Display.isCloseRequested()) continue;
            System.exit(0);
        }
    }

    private void initGL(int n, int n2) {
        try {
            Display.setDisplayMode((DisplayMode)new DisplayMode(n, n2));
            Display.create();
            Display.setVSyncEnabled((boolean)true);
        }
        catch (LWJGLException lWJGLException) {
            lWJGLException.printStackTrace();
            System.exit(0);
        }
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
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)n, (double)n2, (double)0.0, (double)1.0, (double)-1.0);
        GL11.glMatrixMode((int)5888);
    }

    public void init() {
        Log.setVerbose(false);
        java.awt.Font font = new java.awt.Font("Times New Roman", 1, 16);
        this.font = new TrueTypeFont(font, false);
        try {
            this.texture = TextureLoader.getTexture("PNG", new FileInputStream("testdata/rocks.png"));
            System.out.println("Texture loaded: " + this.texture);
            System.out.println(">> Image width: " + this.texture.getImageWidth());
            System.out.println(">> Image height: " + this.texture.getImageWidth());
            System.out.println(">> Texture width: " + this.texture.getTextureWidth());
            System.out.println(">> Texture height: " + this.texture.getTextureHeight());
            System.out.println(">> Texture ID: " + this.texture.getTextureID());
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        try {
            this.oggEffect = AudioLoader.getAudio("OGG", new FileInputStream("testdata/restart.ogg"));
            this.oggStream = AudioLoader.getStreamingAudio("OGG", new File("testdata/bongos.ogg").toURL());
            this.modStream = AudioLoader.getStreamingAudio("MOD", new File("testdata/SMB-X.XM").toURL());
            this.modStream.playAsMusic(1.0f, 1.0f, true);
            this.aifEffect = AudioLoader.getAudio("AIF", new FileInputStream("testdata/burp.aif"));
            this.wavEffect = AudioLoader.getAudio("WAV", new FileInputStream("testdata/cbrown01.wav"));
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public void update() {
        while (Keyboard.next()) {
            if (!Keyboard.getEventKeyState()) continue;
            if (Keyboard.getEventKey() == 16) {
                this.oggEffect.playAsSoundEffect(1.0f, 1.0f, false);
            }
            if (Keyboard.getEventKey() == 17) {
                this.oggStream.playAsMusic(1.0f, 1.0f, true);
            }
            if (Keyboard.getEventKey() == 18) {
                this.modStream.playAsMusic(1.0f, 1.0f, true);
            }
            if (Keyboard.getEventKey() == 19) {
                this.aifEffect.playAsSoundEffect(1.0f, 1.0f, false);
            }
            if (Keyboard.getEventKey() != 20) continue;
            this.wavEffect.playAsSoundEffect(1.0f, 1.0f, false);
        }
        SoundStore.get().poll(0);
    }

    public void render() {
        Color.white.bind();
        this.texture.bind();
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex2f((float)100.0f, (float)100.0f);
        GL11.glTexCoord2f((float)1.0f, (float)0.0f);
        GL11.glVertex2f((float)(100 + this.texture.getTextureWidth()), (float)100.0f);
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex2f((float)(100 + this.texture.getTextureWidth()), (float)(100 + this.texture.getTextureHeight()));
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex2f((float)100.0f, (float)(100 + this.texture.getTextureHeight()));
        GL11.glEnd();
        this.font.drawString(150.0f, 300.0f, "HELLO LWJGL WORLD", Color.yellow);
    }

    public static void main(String[] stringArray) {
        TestUtils testUtils = new TestUtils();
        testUtils.start();
    }
}

