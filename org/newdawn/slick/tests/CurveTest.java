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
import org.newdawn.slick.geom.Curve;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

public class CurveTest
extends BasicGame {
    private Curve curve;
    private Vector2f p1 = new Vector2f(100.0f, 300.0f);
    private Vector2f c1 = new Vector2f(100.0f, 100.0f);
    private Vector2f c2 = new Vector2f(300.0f, 100.0f);
    private Vector2f p2 = new Vector2f(300.0f, 300.0f);
    private Polygon poly;

    public CurveTest() {
        super("Curve Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameContainer.getGraphics().setBackground(Color.white);
        this.curve = new Curve(this.p2, this.c2, this.c1, this.p1);
        this.poly = new Polygon();
        this.poly.addPoint(500.0f, 200.0f);
        this.poly.addPoint(600.0f, 200.0f);
        this.poly.addPoint(700.0f, 300.0f);
        this.poly.addPoint(400.0f, 300.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    private void drawMarker(Graphics graphics, Vector2f vector2f) {
        graphics.drawRect(vector2f.x - 5.0f, vector2f.y - 5.0f, 10.0f, 10.0f);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setColor(Color.gray);
        this.drawMarker(graphics, this.p1);
        this.drawMarker(graphics, this.p2);
        graphics.setColor(Color.red);
        this.drawMarker(graphics, this.c1);
        this.drawMarker(graphics, this.c2);
        graphics.setColor(Color.black);
        graphics.draw(this.curve);
        graphics.fill(this.curve);
        graphics.draw(this.poly);
        graphics.fill(this.poly);
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new CurveTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

