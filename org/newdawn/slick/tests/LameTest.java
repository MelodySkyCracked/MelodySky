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
import org.newdawn.slick.geom.Polygon;

public class LameTest
extends BasicGame {
    private Polygon poly = new Polygon();
    private Image image;

    public LameTest() {
        super("Lame Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.poly.addPoint(100.0f, 100.0f);
        this.poly.addPoint(120.0f, 100.0f);
        this.poly.addPoint(120.0f, 120.0f);
        this.poly.addPoint(100.0f, 120.0f);
        this.image = new Image("testdata/rocks.png");
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.texture(this.poly, this.image);
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new LameTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

