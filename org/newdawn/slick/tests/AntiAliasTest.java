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

public class AntiAliasTest
extends BasicGame {
    public AntiAliasTest() {
        super("AntiAlias Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameContainer.getGraphics().setBackground(Color.green);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setAntiAlias(true);
        graphics.setColor(Color.red);
        graphics.drawOval(100.0f, 100.0f, 100.0f, 100.0f);
        graphics.fillOval(300.0f, 100.0f, 100.0f, 100.0f);
        graphics.setAntiAlias(false);
        graphics.setColor(Color.red);
        graphics.drawOval(100.0f, 300.0f, 100.0f, 100.0f);
        graphics.fillOval(300.0f, 300.0f, 100.0f, 100.0f);
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new AntiAliasTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

