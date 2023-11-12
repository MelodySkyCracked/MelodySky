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

public class ClipTest
extends BasicGame {
    private float ang = 0.0f;
    private boolean world;
    private boolean clip;

    public ClipTest() {
        super("Clip Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        this.ang += (float)n * 0.01f;
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.drawString("1 - No Clipping", 100.0f, 10.0f);
        graphics.drawString("2 - Screen Clipping", 100.0f, 30.0f);
        graphics.drawString("3 - World Clipping", 100.0f, 50.0f);
        if (this.world) {
            graphics.drawString("WORLD CLIPPING ENABLED", 200.0f, 80.0f);
        }
        if (this.clip) {
            graphics.drawString("SCREEN CLIPPING ENABLED", 200.0f, 80.0f);
        }
        graphics.rotate(400.0f, 400.0f, this.ang);
        if (this.world) {
            graphics.setWorldClip(350.0f, 302.0f, 100.0f, 196.0f);
        }
        if (this.clip) {
            graphics.setClip(350, 302, 100, 196);
        }
        graphics.setColor(Color.red);
        graphics.fillOval(300.0f, 300.0f, 200.0f, 200.0f);
        graphics.setColor(Color.blue);
        graphics.fillRect(390.0f, 200.0f, 20.0f, 400.0f);
        graphics.clearClip();
        graphics.clearWorldClip();
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 2) {
            this.world = false;
            this.clip = false;
        }
        if (n == 3) {
            this.world = false;
            this.clip = true;
        }
        if (n == 4) {
            this.world = true;
            this.clip = false;
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new ClipTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

