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
import org.newdawn.slick.tests.lII;

public class ScalableTest
extends BasicGame {
    public ScalableTest() {
        super("Scalable Test For Widescreen");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setColor(new Color(0.4f, 0.6f, 0.8f));
        graphics.fillRect(0.0f, 0.0f, 1024.0f, 568.0f);
        graphics.setColor(Color.white);
        graphics.drawRect(5.0f, 5.0f, 1014.0f, 558.0f);
        graphics.setColor(Color.white);
        graphics.drawString(gameContainer.getInput().getMouseX() + "," + gameContainer.getInput().getMouseY(), 10.0f, 400.0f);
        graphics.setColor(Color.red);
        graphics.fillOval(gameContainer.getInput().getMouseX() - 10, gameContainer.getInput().getMouseY() - 10, 20.0f, 20.0f);
    }

    public static void main(String[] stringArray) {
        try {
            lII lII2 = new lII(new ScalableTest(), 1024, 568, true);
            AppGameContainer appGameContainer = new AppGameContainer(lII2);
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

