/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TileMapTest
extends BasicGame {
    private TiledMap map;
    private String mapName;
    private String monsterDifficulty;
    private String nonExistingMapProperty;
    private String nonExistingLayerProperty;
    private int updateCounter = 0;
    private static int UPDATE_TIME = 1000;
    private int originalTileID = 0;

    public TileMapTest() {
        super("Tile Map Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.map = new TiledMap("testdata/testmap.tmx", "testdata");
        this.mapName = this.map.getMapProperty("name", "Unknown map name");
        this.monsterDifficulty = this.map.getLayerProperty(0, "monsters", "easy peasy");
        this.nonExistingMapProperty = this.map.getMapProperty("zaphod", "Undefined map property");
        this.nonExistingLayerProperty = this.map.getLayerProperty(1, "beeblebrox", "Undefined layer property");
        this.originalTileID = this.map.getTileId(10, 10, 0);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        this.map.render(10, 10, 4, 4, 15, 15);
        graphics.scale(0.35f, 0.35f);
        this.map.render(1400, 0);
        graphics.resetTransform();
        graphics.drawString("map name: " + this.mapName, 10.0f, 500.0f);
        graphics.drawString("monster difficulty: " + this.monsterDifficulty, 10.0f, 550.0f);
        graphics.drawString("non existing map property: " + this.nonExistingMapProperty, 10.0f, 525.0f);
        graphics.drawString("non existing layer property: " + this.nonExistingLayerProperty, 10.0f, 575.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
        this.updateCounter += n;
        if (this.updateCounter > UPDATE_TIME) {
            this.updateCounter -= UPDATE_TIME;
            int n2 = this.map.getTileId(10, 10, 0);
            if (n2 != this.originalTileID) {
                this.map.setTileId(10, 10, 0, this.originalTileID);
            } else {
                this.map.setTileId(10, 10, 0, 1);
            }
        }
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            System.exit(0);
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new TileMapTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

