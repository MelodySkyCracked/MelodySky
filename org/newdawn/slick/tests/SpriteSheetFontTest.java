/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.util.Log;

public class SpriteSheetFontTest
extends BasicGame {
    private Font font;
    private static AppGameContainer container;

    public SpriteSheetFontTest() {
        super("SpriteSheetFont Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        SpriteSheet spriteSheet = new SpriteSheet("testdata/spriteSheetFont.png", 32, 32);
        this.font = new SpriteSheetFont(spriteSheet, ' ');
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.setBackground(Color.gray);
        this.font.drawString(80.0f, 5.0f, "A FONT EXAMPLE", Color.red);
        this.font.drawString(100.0f, 50.0f, "A MORE COMPLETE LINE");
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
            container = new AppGameContainer(new SpriteSheetFontTest());
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

