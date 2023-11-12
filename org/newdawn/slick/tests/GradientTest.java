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
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.opengl.renderer.Renderer;

public class GradientTest
extends BasicGame {
    private GameContainer container;
    private GradientFill gradient;
    private GradientFill gradient2;
    private GradientFill gradient4;
    private Rectangle rect;
    private Rectangle center;
    private RoundedRectangle round;
    private RoundedRectangle round2;
    private Polygon poly;
    private float ang;

    public GradientTest() {
        super("Gradient Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.container = gameContainer;
        this.rect = new Rectangle(400.0f, 100.0f, 200.0f, 150.0f);
        this.round = new RoundedRectangle(150.0f, 100.0f, 200.0f, 150.0f, 50.0f);
        this.round2 = new RoundedRectangle(150.0f, 300.0f, 200.0f, 150.0f, 50.0f);
        this.center = new Rectangle(350.0f, 250.0f, 100.0f, 100.0f);
        this.poly = new Polygon();
        this.poly.addPoint(400.0f, 350.0f);
        this.poly.addPoint(550.0f, 320.0f);
        this.poly.addPoint(600.0f, 380.0f);
        this.poly.addPoint(620.0f, 450.0f);
        this.poly.addPoint(500.0f, 450.0f);
        this.gradient = new GradientFill(0.0f, -75.0f, Color.red, 0.0f, 75.0f, Color.yellow, true);
        this.gradient2 = new GradientFill(0.0f, -75.0f, Color.blue, 0.0f, 75.0f, Color.white, true);
        this.gradient4 = new GradientFill(-50.0f, -40.0f, Color.green, 50.0f, 40.0f, Color.cyan, true);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.rotate(400.0f, 300.0f, this.ang);
        graphics.fill(this.rect, this.gradient);
        graphics.fill(this.round, this.gradient);
        graphics.fill(this.poly, this.gradient2);
        graphics.fill(this.center, this.gradient4);
        graphics.setAntiAlias(true);
        graphics.setLineWidth(10.0f);
        graphics.draw(this.round2, this.gradient2);
        graphics.setLineWidth(2.0f);
        graphics.draw(this.poly, this.gradient);
        graphics.setAntiAlias(false);
        graphics.fill(this.center, this.gradient4);
        graphics.setAntiAlias(true);
        graphics.setColor(Color.black);
        graphics.draw(this.center);
        graphics.setAntiAlias(false);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
        this.ang += (float)n * 0.01f;
    }

    public static void main(String[] stringArray) {
        try {
            Renderer.setRenderer(2);
            AppGameContainer appGameContainer = new AppGameContainer(new GradientTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            this.container.exit();
        }
    }
}

