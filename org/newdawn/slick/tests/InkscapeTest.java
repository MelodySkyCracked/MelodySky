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
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.svg.InkscapeLoader;
import org.newdawn.slick.svg.SimpleDiagramRenderer;

public class InkscapeTest
extends BasicGame {
    private SimpleDiagramRenderer[] renderer = new SimpleDiagramRenderer[5];
    private float zoom = 1.0f;
    private float x;
    private float y;

    public InkscapeTest() {
        super("Inkscape Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameContainer.getGraphics().setBackground(Color.white);
        InkscapeLoader.RADIAL_TRIANGULATION_LEVEL = 2;
        this.renderer[3] = new SimpleDiagramRenderer(InkscapeLoader.load("testdata/svg/clonetest.svg"));
        gameContainer.getGraphics().setBackground(new Color(0.5f, 0.7f, 1.0f));
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        if (gameContainer.getInput().isKeyDown(16)) {
            this.zoom += (float)n * 0.01f;
            if (this.zoom > 10.0f) {
                this.zoom = 10.0f;
            }
        }
        if (gameContainer.getInput().isKeyDown(30)) {
            this.zoom -= (float)n * 0.01f;
            if (this.zoom < 0.1f) {
                this.zoom = 0.1f;
            }
        }
        if (gameContainer.getInput().isKeyDown(205)) {
            this.x += (float)n * 0.1f;
        }
        if (gameContainer.getInput().isKeyDown(203)) {
            this.x -= (float)n * 0.1f;
        }
        if (gameContainer.getInput().isKeyDown(208)) {
            this.y += (float)n * 0.1f;
        }
        if (gameContainer.getInput().isKeyDown(200)) {
            this.y -= (float)n * 0.1f;
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.scale(this.zoom, this.zoom);
        graphics.translate(this.x, this.y);
        graphics.scale(0.3f, 0.3f);
        graphics.scale(3.3333333f, 3.3333333f);
        graphics.translate(400.0f, 0.0f);
        graphics.translate(100.0f, 300.0f);
        graphics.scale(0.7f, 0.7f);
        graphics.scale(1.4285715f, 1.4285715f);
        graphics.scale(0.5f, 0.5f);
        graphics.translate(-1100.0f, -380.0f);
        this.renderer[3].render(graphics);
        graphics.scale(2.0f, 2.0f);
        graphics.resetTransform();
    }

    public static void main(String[] stringArray) {
        try {
            Renderer.setRenderer(2);
            Renderer.setLineStripRenderer(4);
            AppGameContainer appGameContainer = new AppGameContainer(new InkscapeTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

