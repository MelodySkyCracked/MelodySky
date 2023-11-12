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
import org.newdawn.slick.geom.MorphShape;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class MorphShapeTest
extends BasicGame {
    private Shape a;
    private Shape b;
    private Shape c;
    private MorphShape morph;
    private float time;

    public MorphShapeTest() {
        super("MorphShapeTest");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.a = new Rectangle(100.0f, 100.0f, 50.0f, 200.0f);
        this.a = this.a.transform(Transform.createRotateTransform(0.1f, 100.0f, 100.0f));
        this.b = new Rectangle(200.0f, 100.0f, 50.0f, 200.0f);
        this.b = this.b.transform(Transform.createRotateTransform(-0.6f, 100.0f, 100.0f));
        this.c = new Rectangle(300.0f, 100.0f, 50.0f, 200.0f);
        this.c = this.c.transform(Transform.createRotateTransform(-0.2f, 100.0f, 100.0f));
        this.morph = new MorphShape(this.a);
        this.morph.addShape(this.b);
        this.morph.addShape(this.c);
        gameContainer.setVSync(true);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        this.time += (float)n * 0.001f;
        this.morph.setMorphTime(this.time);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setColor(Color.green);
        graphics.draw(this.a);
        graphics.setColor(Color.red);
        graphics.draw(this.b);
        graphics.setColor(Color.blue);
        graphics.draw(this.c);
        graphics.setColor(Color.white);
        graphics.draw(this.morph);
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new MorphShapeTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

