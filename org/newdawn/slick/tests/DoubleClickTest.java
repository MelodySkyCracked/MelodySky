/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class DoubleClickTest
extends BasicGame {
    private String message = "Click or Double Click";

    public DoubleClickTest() {
        super("Double Click Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.drawString(this.message, 100.0f, 100.0f);
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new DoubleClickTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(int n, int n2, int n3, int n4) {
        if (n4 == 1) {
            this.message = "Single Click: " + n + " " + n2 + "," + n3;
        }
        if (n4 == 2) {
            this.message = "Double Click: " + n + " " + n2 + "," + n3;
        }
    }
}

