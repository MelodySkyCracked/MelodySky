/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package org.newdawn.slick.tests;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class DistanceFieldTest
extends BasicGame {
    private AngelCodeFont font;

    public DistanceFieldTest() {
        super("DistanceMapTest Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.font = new AngelCodeFont("testdata/distance.fnt", "testdata/distance-dis.png");
        gameContainer.getGraphics().setBackground(Color.black);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        String string = "abc";
        this.font.drawString(610.0f, 100.0f, string);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glAlphaFunc((int)518, (float)0.5f);
        this.font.drawString(610.0f, 150.0f, string);
        GL11.glDisable((int)3008);
        GL11.glEnable((int)3042);
        graphics.translate(-50.0f, -130.0f);
        graphics.scale(10.0f, 10.0f);
        this.font.drawString(0.0f, 0.0f, string);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glAlphaFunc((int)518, (float)0.5f);
        this.font.drawString(0.0f, 26.0f, string);
        GL11.glDisable((int)3008);
        GL11.glEnable((int)3042);
        graphics.resetTransform();
        graphics.setColor(Color.lightGray);
        graphics.drawString("Original Size on Sheet", 620.0f, 210.0f);
        graphics.drawString("10x Scale Up", 40.0f, 575.0f);
        graphics.setColor(Color.darkGray);
        graphics.drawRect(40.0f, 40.0f, 560.0f, 530.0f);
        graphics.drawRect(610.0f, 105.0f, 150.0f, 100.0f);
        graphics.setColor(Color.white);
        graphics.drawString("512x512 Font Sheet", 620.0f, 300.0f);
        graphics.drawString("NEHE Charset", 620.0f, 320.0f);
        graphics.drawString("4096x4096 (8x) Source Image", 620.0f, 340.0f);
        graphics.drawString("ScanSize = 20", 620.0f, 360.0f);
    }

    @Override
    public void keyPressed(int n, char c) {
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new DistanceFieldTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

