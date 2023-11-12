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

public class AlphaMapTest
extends BasicGame {
    private Image alphaMap;
    private Image textureMap;

    public AlphaMapTest() {
        super("AlphaMap Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.alphaMap = new Image("testdata/alphamap.png");
        this.textureMap = new Image("testdata/grass.png");
        gameContainer.getGraphics().setBackground(Color.black);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.clearAlphaMap();
        graphics.setDrawMode(Graphics.MODE_NORMAL);
        this.textureMap.draw(10.0f, 50.0f);
        graphics.setColor(Color.red);
        graphics.fillRect(290.0f, 40.0f, 200.0f, 200.0f);
        graphics.setColor(Color.white);
        graphics.setDrawMode(Graphics.MODE_ALPHA_MAP);
        this.alphaMap.draw(300.0f, 50.0f);
        graphics.setDrawMode(Graphics.MODE_ALPHA_BLEND);
        this.textureMap.draw(300.0f, 50.0f);
        graphics.setDrawMode(Graphics.MODE_NORMAL);
    }

    @Override
    public void keyPressed(int n, char c) {
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new AlphaMapTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

