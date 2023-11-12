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

public class ImageCornerTest
extends BasicGame {
    private Image image;
    private Image[] images;
    private int width;
    private int height;

    public ImageCornerTest() {
        super("Image Corner Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.image = new Image("testdata/logo.png");
        this.width = this.image.getWidth() / 3;
        this.height = this.image.getHeight() / 3;
        this.images = new Image[]{this.image.getSubImage(0, 0, this.width, this.height), this.image.getSubImage(this.width, 0, this.width, this.height), this.image.getSubImage(this.width * 2, 0, this.width, this.height), this.image.getSubImage(0, this.height, this.width, this.height), this.image.getSubImage(this.width, this.height, this.width, this.height), this.image.getSubImage(this.width * 2, this.height, this.width, this.height), this.image.getSubImage(0, this.height * 2, this.width, this.height), this.image.getSubImage(this.width, this.height * 2, this.width, this.height), this.image.getSubImage(this.width * 2, this.height * 2, this.width, this.height)};
        this.images[0].setColor(2, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[1].setColor(3, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[1].setColor(2, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[2].setColor(3, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[3].setColor(1, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[3].setColor(2, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[4].setColor(1, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[4].setColor(0, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[4].setColor(3, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[4].setColor(2, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[5].setColor(0, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[5].setColor(3, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[6].setColor(1, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[7].setColor(1, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[7].setColor(0, 0.0f, 1.0f, 1.0f, 1.0f);
        this.images[8].setColor(0, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.images[i + j * 3].draw(100 + i * this.width, 100 + j * this.height);
            }
        }
    }

    public static void main(String[] stringArray) {
        boolean bl = false;
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new ImageCornerTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }
}

