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
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class GradientImageTest
extends BasicGame {
    private Image image1;
    private Image image2;
    private GradientFill fill;
    private Shape shape;
    private Polygon poly;
    private GameContainer container;
    private float ang;
    private boolean rotating = false;

    public GradientImageTest() {
        super("Gradient Image Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.container = gameContainer;
        this.image1 = new Image("testdata/grass.png");
        this.image2 = new Image("testdata/rocks.png");
        this.fill = new GradientFill(-64.0f, 0.0f, new Color(1.0f, 1.0f, 1.0f, 1.0f), 64.0f, 0.0f, new Color(0, 0, 0, 0));
        this.shape = new Rectangle(336.0f, 236.0f, 128.0f, 128.0f);
        this.poly = new Polygon();
        this.poly.addPoint(320.0f, 220.0f);
        this.poly.addPoint(350.0f, 200.0f);
        this.poly.addPoint(450.0f, 200.0f);
        this.poly.addPoint(480.0f, 220.0f);
        this.poly.addPoint(420.0f, 400.0f);
        this.poly.addPoint(400.0f, 390.0f);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.drawString("R - Toggle Rotationg", 10.0f, 50.0f);
        graphics.drawImage(this.image1, 100.0f, 236.0f);
        graphics.drawImage(this.image2, 600.0f, 236.0f);
        graphics.translate(0.0f, -150.0f);
        graphics.rotate(400.0f, 300.0f, this.ang);
        graphics.texture(this.shape, this.image2);
        graphics.texture(this.shape, this.image1, this.fill);
        graphics.resetTransform();
        graphics.translate(0.0f, 150.0f);
        graphics.rotate(400.0f, 300.0f, this.ang);
        graphics.texture(this.poly, this.image2);
        graphics.texture((Shape)this.poly, this.image1, this.fill);
        graphics.resetTransform();
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
        if (this.rotating) {
            this.ang += (float)n * 0.1f;
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new GradientImageTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 19) {
            boolean bl = this.rotating = !this.rotating;
        }
        if (n == 1) {
            this.container.exit();
        }
    }
}

