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
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.TexCoordGenerator;
import org.newdawn.slick.tests.lIl;

public class TexturePaintTest
extends BasicGame {
    private Polygon poly = new Polygon();
    private Image image;
    private Rectangle texRect = new Rectangle(50.0f, 50.0f, 100.0f, 100.0f);
    private TexCoordGenerator texPaint;

    public TexturePaintTest() {
        super("Texture Paint Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.poly.addPoint(120.0f, 120.0f);
        this.poly.addPoint(420.0f, 100.0f);
        this.poly.addPoint(620.0f, 420.0f);
        this.poly.addPoint(300.0f, 320.0f);
        this.image = new Image("testdata/rocks.png");
        this.texPaint = new lIl(this);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.texture(this.poly, this.image);
        ShapeRenderer.texture(this.poly, this.image, this.texPaint);
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new TexturePaintTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    static Rectangle access$000(TexturePaintTest texturePaintTest) {
        return texturePaintTest.texRect;
    }
}

