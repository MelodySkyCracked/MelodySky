/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class BigSpriteSheetTest
extends BasicGame {
    private Image original;
    private SpriteSheet bigSheet;
    private boolean oldMethod = true;

    public BigSpriteSheetTest() {
        super("Big SpriteSheet Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.original = new BigImage("testdata/bigimage.tga", 2, 256);
        this.bigSheet = new SpriteSheet(this.original, 16, 16);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        if (this.oldMethod) {
            for (int i = 0; i < 43; ++i) {
                for (int j = 0; j < 27; ++j) {
                    this.bigSheet.getSprite(i, j).draw(10 + i * 18, 50 + j * 18);
                }
            }
        } else {
            this.bigSheet.startUse();
            for (int i = 0; i < 43; ++i) {
                for (int j = 0; j < 27; ++j) {
                    this.bigSheet.renderInUse(10 + i * 18, 50 + j * 18, i, j);
                }
            }
            this.bigSheet.endUse();
        }
        graphics.drawString("Press space to toggle rendering method", 10.0f, 30.0f);
        gameContainer.getDefaultFont().drawString(10.0f, 100.0f, "TEST");
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new BigSpriteSheetTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        if (gameContainer.getInput().isKeyPressed(57)) {
            this.oldMethod = !this.oldMethod;
        }
    }
}

