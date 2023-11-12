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

public class CopyAreaAlphaTest
extends BasicGame {
    private Image textureMap;
    private Image copy;

    public CopyAreaAlphaTest() {
        super("CopyArea Alpha Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.textureMap = new Image("testdata/grass.png");
        gameContainer.getGraphics().setBackground(Color.black);
        this.copy = new Image(100, 100);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.clearAlphaMap();
        graphics.setDrawMode(Graphics.MODE_NORMAL);
        graphics.setColor(Color.white);
        graphics.fillOval(100.0f, 100.0f, 150.0f, 150.0f);
        this.textureMap.draw(10.0f, 50.0f);
        graphics.copyArea(this.copy, 100, 100);
        graphics.setColor(Color.red);
        graphics.fillRect(300.0f, 100.0f, 200.0f, 200.0f);
        this.copy.draw(350.0f, 150.0f);
    }

    @Override
    public void keyPressed(int n, char c) {
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new CopyAreaAlphaTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

