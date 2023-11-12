/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Bootstrap;

public class IsoTiledTest
extends BasicGame {
    private TiledMap tilemap;

    public IsoTiledTest() {
        super("Isometric Tiled Map Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.tilemap = new TiledMap("testdata/isoexample.tmx", "testdata/");
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.tilemap.render(350, 150);
    }

    public static void main(String[] stringArray) {
        Bootstrap.runAsApplication(new IsoTiledTest(), 800, 600, false);
    }
}

