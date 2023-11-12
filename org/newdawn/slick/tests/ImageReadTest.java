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

public class ImageReadTest
extends BasicGame {
    private Image image;
    private Color[] read = new Color[6];
    private Graphics g;

    public ImageReadTest() {
        super("Image Read Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.image = new Image("testdata/testcard.png");
        this.read[0] = this.image.getColor(0, 0);
        this.read[1] = this.image.getColor(30, 40);
        this.read[2] = this.image.getColor(55, 70);
        this.read[3] = this.image.getColor(80, 90);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        this.g = graphics;
        this.image.draw(100.0f, 100.0f);
        graphics.setColor(Color.white);
        graphics.drawString("Move mouse over test image", 200.0f, 20.0f);
        graphics.setColor(this.read[0]);
        graphics.drawString(this.read[0].toString(), 100.0f, 300.0f);
        graphics.setColor(this.read[1]);
        graphics.drawString(this.read[1].toString(), 150.0f, 320.0f);
        graphics.setColor(this.read[2]);
        graphics.drawString(this.read[2].toString(), 200.0f, 340.0f);
        graphics.setColor(this.read[3]);
        graphics.drawString(this.read[3].toString(), 250.0f, 360.0f);
        if (this.read[4] != null) {
            graphics.setColor(this.read[4]);
            graphics.drawString("On image: " + this.read[4].toString(), 100.0f, 250.0f);
        }
        if (this.read[5] != null) {
            graphics.setColor(Color.white);
            graphics.drawString("On screen: " + this.read[5].toString(), 100.0f, 270.0f);
        }
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
        int n2 = gameContainer.getInput().getMouseX();
        int n3 = gameContainer.getInput().getMouseY();
        this.read[4] = n2 >= 100 && n3 >= 100 && n2 < 200 && n3 < 200 ? this.image.getColor(n2 - 100, n3 - 100) : Color.black;
        this.read[5] = this.g.getPixel(n2, n3);
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new ImageReadTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

