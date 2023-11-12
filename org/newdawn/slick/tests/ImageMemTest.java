/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageMemTest
extends BasicGame {
    public ImageMemTest() {
        super("Image Memory Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        try {
            Image image = new Image(2400, 2400);
            image.getGraphics();
            image.destroy();
            image = new Image(2400, 2400);
            image.getGraphics();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new ImageMemTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

