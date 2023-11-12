/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class FlashTest
extends BasicGame {
    private Image image;
    private boolean flash;
    private GameContainer container;

    public FlashTest() {
        super("Flash Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.container = gameContainer;
        this.image = new Image("testdata/logo.tga");
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.drawString("Press space to toggle", 10.0f, 50.0f);
        if (this.flash) {
            this.image.draw(100.0f, 100.0f);
        } else {
            this.image.drawFlash(100.0f, 100.0f, this.image.getWidth(), this.image.getHeight(), new Color(1.0f, 0.0f, 1.0f, 1.0f));
        }
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new FlashTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 57) {
            boolean bl = this.flash = !this.flash;
        }
        if (n == 1) {
            this.container.exit();
        }
    }
}

