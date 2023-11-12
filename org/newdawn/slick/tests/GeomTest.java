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
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.opengl.renderer.Renderer;

public class GeomTest
extends BasicGame {
    private Shape rect = new Rectangle(100.0f, 100.0f, 100.0f, 100.0f);
    private Shape circle = new Circle(500.0f, 200.0f, 50.0f);
    private Shape rect1 = new Rectangle(150.0f, 120.0f, 50.0f, 100.0f).transform(Transform.createTranslateTransform(50.0f, 50.0f));
    private Shape rect2 = new Rectangle(310.0f, 210.0f, 50.0f, 100.0f).transform(Transform.createRotateTransform((float)Math.toRadians(45.0), 335.0f, 260.0f));
    private Shape circle1 = new Circle(150.0f, 90.0f, 30.0f);
    private Shape circle2 = new Circle(310.0f, 110.0f, 70.0f);
    private Shape circle3 = new Ellipse(510.0f, 150.0f, 70.0f, 70.0f);
    private Shape circle4 = new Ellipse(510.0f, 350.0f, 30.0f, 30.0f).transform(Transform.createTranslateTransform(-510.0f, -350.0f)).transform(Transform.createScaleTransform(2.0f, 2.0f)).transform(Transform.createTranslateTransform(510.0f, 350.0f));
    private Shape roundRect = new RoundedRectangle(50.0f, 175.0f, 100.0f, 100.0f, 20.0f);
    private Shape roundRect2 = new RoundedRectangle(50.0f, 280.0f, 50.0f, 50.0f, 20.0f, 20, 5);

    public GeomTest() {
        super("Geom Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.drawString("Red indicates a collision, green indicates no collision", 50.0f, 420.0f);
        graphics.drawString("White are the targets", 50.0f, 435.0f);
        graphics.pushTransform();
        graphics.translate(100.0f, 100.0f);
        graphics.pushTransform();
        graphics.translate(-50.0f, -50.0f);
        graphics.scale(10.0f, 10.0f);
        graphics.setColor(Color.red);
        graphics.fillRect(0.0f, 0.0f, 5.0f, 5.0f);
        graphics.setColor(Color.white);
        graphics.drawRect(0.0f, 0.0f, 5.0f, 5.0f);
        graphics.popTransform();
        graphics.setColor(Color.green);
        graphics.fillRect(20.0f, 20.0f, 50.0f, 50.0f);
        graphics.popTransform();
        graphics.setColor(Color.white);
        graphics.draw(this.rect);
        graphics.draw(this.circle);
        graphics.setColor(this.rect1.intersects(this.rect) ? Color.red : Color.green);
        graphics.draw(this.rect1);
        graphics.setColor(this.rect2.intersects(this.rect) ? Color.red : Color.green);
        graphics.draw(this.rect2);
        graphics.setColor(this.roundRect.intersects(this.rect) ? Color.red : Color.green);
        graphics.draw(this.roundRect);
        graphics.setColor(this.circle1.intersects(this.rect) ? Color.red : Color.green);
        graphics.draw(this.circle1);
        graphics.setColor(this.circle2.intersects(this.rect) ? Color.red : Color.green);
        graphics.draw(this.circle2);
        graphics.setColor(this.circle3.intersects(this.circle) ? Color.red : Color.green);
        graphics.fill(this.circle3);
        graphics.setColor(this.circle4.intersects(this.circle) ? Color.red : Color.green);
        graphics.draw(this.circle4);
        graphics.fill(this.roundRect2);
        graphics.setColor(Color.blue);
        graphics.draw(this.roundRect2);
        graphics.setColor(Color.blue);
        graphics.draw(new Circle(100.0f, 100.0f, 50.0f));
        graphics.drawRect(50.0f, 50.0f, 100.0f, 100.0f);
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
            Renderer.setRenderer(2);
            AppGameContainer appGameContainer = new AppGameContainer(new GeomTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

