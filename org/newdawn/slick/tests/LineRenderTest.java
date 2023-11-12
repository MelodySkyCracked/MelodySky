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
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.opengl.renderer.Renderer;

public class LineRenderTest
extends BasicGame {
    private Polygon polygon = new Polygon();
    private Path path = new Path(100.0f, 100.0f);
    private float width = 10.0f;
    private boolean antialias = true;

    public LineRenderTest() {
        super("LineRenderTest");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.polygon.addPoint(100.0f, 100.0f);
        this.polygon.addPoint(200.0f, 80.0f);
        this.polygon.addPoint(320.0f, 150.0f);
        this.polygon.addPoint(230.0f, 210.0f);
        this.polygon.addPoint(170.0f, 260.0f);
        this.path.curveTo(200.0f, 200.0f, 200.0f, 100.0f, 100.0f, 200.0f);
        this.path.curveTo(400.0f, 100.0f, 400.0f, 200.0f, 200.0f, 100.0f);
        this.path.curveTo(500.0f, 500.0f, 400.0f, 200.0f, 200.0f, 100.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        if (gameContainer.getInput().isKeyPressed(57)) {
            this.antialias = !this.antialias;
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setAntiAlias(this.antialias);
        graphics.setLineWidth(50.0f);
        graphics.setColor(Color.red);
        graphics.draw(this.path);
    }

    public static void main(String[] stringArray) {
        try {
            Renderer.setLineStripRenderer(4);
            Renderer.getLineStripRenderer().setLineCaps(true);
            AppGameContainer appGameContainer = new AppGameContainer(new LineRenderTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

