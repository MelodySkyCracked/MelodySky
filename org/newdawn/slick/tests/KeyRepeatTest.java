/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class KeyRepeatTest
extends BasicGame {
    private int count;
    private Input input;

    public KeyRepeatTest() {
        super("KeyRepeatTest");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.input = gameContainer.getInput();
        this.input.enableKeyRepeat(300, 100);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.drawString("Key Press Count: " + this.count, 100.0f, 100.0f);
        graphics.drawString("Press Space to Toggle Key Repeat", 100.0f, 150.0f);
        graphics.drawString("Key Repeat Enabled: " + this.input.isKeyRepeatEnabled(), 100.0f, 200.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new KeyRepeatTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void keyPressed(int n, char c) {
        ++this.count;
        if (n == 57) {
            if (this.input.isKeyRepeatEnabled()) {
                this.input.disableKeyRepeat();
            } else {
                this.input.enableKeyRepeat(300, 100);
            }
        }
    }
}

