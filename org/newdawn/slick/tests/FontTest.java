/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

public class FontTest
extends BasicGame {
    private AngelCodeFont font;
    private AngelCodeFont font2;
    private Image image;
    private static AppGameContainer container;

    public FontTest() {
        super("Font Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
        this.font2 = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
        this.image = new Image("testdata/demo2_00.tga", false);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        this.font.drawString(80.0f, 5.0f, "A Font Example", Color.red);
        this.font.drawString(100.0f, 32.0f, "We - AV - Here is a more complete line that hopefully");
        this.font.drawString(100.0f, 36 + this.font.getHeight("We Here is a more complete line that hopefully"), "will show some kerning.");
        this.font2.drawString(80.0f, 85.0f, "A Font Example", Color.red);
        this.font2.drawString(100.0f, 132.0f, "We - AV - Here is a more complete line that hopefully");
        this.font2.drawString(100.0f, 136 + this.font2.getHeight("We - Here is a more complete line that hopefully"), "will show some kerning.");
        this.image.draw(100.0f, 400.0f);
        String string = "Testing Font";
        this.font2.drawString(100.0f, 300.0f, string);
        graphics.setColor(Color.white);
        graphics.drawRect(100.0f, 300 + this.font2.getYOffset(string), this.font2.getWidth(string), this.font2.getHeight(string) - this.font2.getYOffset(string));
        this.font.drawString(500.0f, 300.0f, string);
        graphics.setColor(Color.white);
        graphics.drawRect(500.0f, 300 + this.font.getYOffset(string), this.font.getWidth(string), this.font.getHeight(string) - this.font.getYOffset(string));
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            System.exit(0);
        }
        if (n == 57) {
            try {
                container.setDisplayMode(640, 480, false);
            }
            catch (SlickException slickException) {
                Log.error(slickException);
            }
        }
    }

    public static void main(String[] stringArray) {
        try {
            container = new AppGameContainer(new FontTest());
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

