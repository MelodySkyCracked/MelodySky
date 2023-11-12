/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

public class PolygonTest
extends BasicGame {
    private Polygon poly;
    private boolean in;
    private float y;

    public PolygonTest() {
        super("Polygon Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.poly = new Polygon();
        this.poly.addPoint(300.0f, 100.0f);
        this.poly.addPoint(320.0f, 200.0f);
        this.poly.addPoint(350.0f, 210.0f);
        this.poly.addPoint(280.0f, 250.0f);
        this.poly.addPoint(300.0f, 200.0f);
        this.poly.addPoint(240.0f, 150.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        this.in = this.poly.contains(gameContainer.getInput().getMouseX(), gameContainer.getInput().getMouseY());
        this.poly.setCenterY(0.0f);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        if (this.in) {
            graphics.setColor(Color.red);
            graphics.fill(this.poly);
        }
        graphics.setColor(Color.yellow);
        graphics.fillOval(this.poly.getCenterX() - 3.0f, this.poly.getCenterY() - 3.0f, 6.0f, 6.0f);
        graphics.setColor(Color.white);
        graphics.draw(this.poly);
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new PolygonTest(), 640, 480, false);
            appGameContainer.start();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

