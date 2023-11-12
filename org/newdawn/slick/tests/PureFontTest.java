/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PureFontTest
extends BasicGame {
    private Font font;
    private Image image;
    private static AppGameContainer container;

    public PureFontTest() {
        super("Hiero Font Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.image = new Image("testdata/sky.jpg");
        this.font = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        this.image.draw(0.0f, 0.0f, 800.0f, 600.0f);
        this.font.drawString(100.0f, 32.0f, "On top of old smokey, all");
        this.font.drawString(100.0f, 80.0f, "covered with sand..");
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            System.exit(0);
        }
    }

    public static void main(String[] stringArray) {
        try {
            container = new AppGameContainer(new PureFontTest());
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

