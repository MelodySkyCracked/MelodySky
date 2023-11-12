/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.PackedSpriteSheet;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class PackedSheetTest
extends BasicGame {
    private PackedSpriteSheet sheet;
    private GameContainer container;
    private float r = -500.0f;
    private Image rocket;
    private Animation runner;
    private float ang;

    public PackedSheetTest() {
        super("Packed Sprite Sheet Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.container = gameContainer;
        this.sheet = new PackedSpriteSheet("testdata/testpack.def", 2);
        this.rocket = this.sheet.getSprite("rocket");
        SpriteSheet spriteSheet = this.sheet.getSpriteSheet("runner");
        this.runner = new Animation();
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 6; ++j) {
                this.runner.addFrame(spriteSheet.getSprite(j, i), 50);
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        this.rocket.draw((int)this.r, 100.0f);
        this.runner.draw(250.0f, 250.0f);
        graphics.scale(1.2f, 1.2f);
        this.runner.draw(250.0f, 250.0f);
        graphics.scale(1.2f, 1.2f);
        this.runner.draw(250.0f, 250.0f);
        graphics.resetTransform();
        graphics.rotate(670.0f, 470.0f, this.ang);
        this.sheet.getSprite("floppy").draw(600.0f, 400.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
        this.r += (float)n * 0.4f;
        if (this.r > 900.0f) {
            this.r = -500.0f;
        }
        this.ang += (float)n * 0.1f;
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new PackedSheetTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            this.container.exit();
        }
    }
}

