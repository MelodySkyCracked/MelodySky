/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;

public class ImageBufferTest
extends BasicGame {
    private Image image;

    public ImageBufferTest() {
        super("Image Buffer Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        ImageBuffer imageBuffer = new ImageBuffer(320, 200);
        for (int i = 0; i < 320; ++i) {
            for (int j = 0; j < 200; ++j) {
                if (j == 20) {
                    imageBuffer.setRGBA(i, j, 255, 255, 255, 255);
                    continue;
                }
                imageBuffer.setRGBA(i, j, i, j, 0, 255);
            }
        }
        this.image = imageBuffer.getImage();
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        this.image.draw(50.0f, 50.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            System.exit(0);
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new ImageBufferTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

