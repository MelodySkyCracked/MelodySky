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

public class TransparentColorTest
extends BasicGame {
    private Image image;
    private Image timage;

    public TransparentColorTest() {
        super("Transparent Color Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.image = new Image("testdata/transtest.png");
        this.timage = new Image("testdata/transtest.png", new Color(94, 66, 41, 255));
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.setBackground(Color.red);
        this.image.draw(10.0f, 10.0f);
        this.timage.draw(10.0f, 310.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new TransparentColorTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void keyPressed(int n, char c) {
    }
}

