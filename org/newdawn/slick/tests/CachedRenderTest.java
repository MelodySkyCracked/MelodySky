/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.CachedRender;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tests.lIII;

public class CachedRenderTest
extends BasicGame {
    private Runnable operations;
    private CachedRender cached;
    private boolean drawCached;

    public CachedRenderTest() {
        super("Cached Render Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.operations = new lIII(this, gameContainer);
        this.cached = new CachedRender(this.operations);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        if (gameContainer.getInput().isKeyPressed(57)) {
            this.drawCached = !this.drawCached;
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.drawString("Press space to toggle caching", 10.0f, 130.0f);
        if (this.drawCached) {
            graphics.drawString("Drawing from cache", 10.0f, 100.0f);
            this.cached.render();
        } else {
            graphics.drawString("Drawing direct", 10.0f, 100.0f);
            this.operations.run();
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new CachedRenderTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

