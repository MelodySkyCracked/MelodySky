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
import org.newdawn.slick.util.FastTrig;

public class GraphicsTest
extends BasicGame {
    private boolean clip;
    private float ang;
    private Image image;
    private Polygon poly;
    private GameContainer container;

    public GraphicsTest() {
        super("Graphics Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.container = gameContainer;
        this.image = new Image("testdata/logo.tga", true);
        Image image = new Image("testdata/palette_tool.png");
        gameContainer.setMouseCursor(image, 0, 0);
        gameContainer.setIcons(new String[]{"testdata/icon.tga"});
        gameContainer.setTargetFrameRate(100);
        this.poly = new Polygon();
        float f = 100.0f;
        for (int i = 0; i < 360; i += 30) {
            f = f == 100.0f ? 50.0f : 100.0f;
            this.poly.addPoint((float)FastTrig.cos(Math.toRadians(i)) * f, (float)FastTrig.sin(Math.toRadians(i)) * f);
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.setAntiAlias(true);
        for (int i = 0; i < 360; i += 10) {
            graphics.drawLine(700.0f, 100.0f, (int)(700.0 + Math.cos(Math.toRadians(i)) * 100.0), (int)(100.0 + Math.sin(Math.toRadians(i)) * 100.0));
        }
        graphics.setAntiAlias(false);
        graphics.setColor(Color.yellow);
        graphics.drawString("The Graphics Test!", 300.0f, 50.0f);
        graphics.setColor(Color.white);
        graphics.drawString("Space - Toggles clipping", 400.0f, 80.0f);
        graphics.drawString("Frame rate capped to 100", 400.0f, 120.0f);
        if (this.clip) {
            graphics.setColor(Color.gray);
            graphics.drawRect(100.0f, 260.0f, 400.0f, 100.0f);
            graphics.setClip(100, 260, 400, 100);
        }
        graphics.setColor(Color.yellow);
        graphics.translate(100.0f, 120.0f);
        graphics.fill(this.poly);
        graphics.setColor(Color.blue);
        graphics.setLineWidth(3.0f);
        graphics.draw(this.poly);
        graphics.setLineWidth(1.0f);
        graphics.translate(0.0f, 230.0f);
        graphics.draw(this.poly);
        graphics.resetTransform();
        graphics.setColor(Color.magenta);
        graphics.drawRoundRect(10.0f, 10.0f, 100.0f, 100.0f, 10);
        graphics.fillRoundRect(10.0f, 210.0f, 100.0f, 100.0f, 10);
        graphics.rotate(400.0f, 300.0f, this.ang);
        graphics.setColor(Color.green);
        graphics.drawRect(200.0f, 200.0f, 200.0f, 200.0f);
        graphics.setColor(Color.blue);
        graphics.fillRect(250.0f, 250.0f, 100.0f, 100.0f);
        graphics.drawImage(this.image, 300.0f, 270.0f);
        graphics.setColor(Color.red);
        graphics.drawOval(100.0f, 100.0f, 200.0f, 200.0f);
        graphics.setColor(Color.red.darker());
        graphics.fillOval(300.0f, 300.0f, 150.0f, 100.0f);
        graphics.setAntiAlias(true);
        graphics.setColor(Color.white);
        graphics.setLineWidth(5.0f);
        graphics.drawOval(300.0f, 300.0f, 150.0f, 100.0f);
        graphics.setAntiAlias(true);
        graphics.resetTransform();
        if (this.clip) {
            graphics.clearClip();
        }
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
        this.ang += (float)n * 0.1f;
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            System.exit(0);
        }
        if (n == 57) {
            this.clip = !this.clip;
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new GraphicsTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

