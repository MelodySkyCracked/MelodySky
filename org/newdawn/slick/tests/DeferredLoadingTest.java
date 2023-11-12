/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import java.io.IOException;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;

public class DeferredLoadingTest
extends BasicGame {
    private Music music;
    private Sound sound;
    private Image image;
    private Font font;
    private DeferredResource nextResource;
    private boolean started;

    public DeferredLoadingTest() {
        super("Deferred Loading Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        LoadingList.setDeferredLoading(true);
        new Sound("testdata/cbrown01.wav");
        new Sound("testdata/engine.wav");
        this.sound = new Sound("testdata/restart.ogg");
        new Music("testdata/testloop.ogg");
        this.music = new Music("testdata/SMB-X.XM");
        new Image("testdata/cursor.png");
        new Image("testdata/cursor.tga");
        new Image("testdata/cursor.png");
        new Image("testdata/cursor.png");
        new Image("testdata/dungeontiles.gif");
        new Image("testdata/logo.gif");
        this.image = new Image("testdata/logo.tga");
        new Image("testdata/logo.png");
        new Image("testdata/rocket.png");
        new Image("testdata/testpack.png");
        this.font = new AngelCodeFont("testdata/demo.fnt", "testdata/demo_00.tga");
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        if (this.nextResource != null) {
            graphics.drawString("Loading: " + this.nextResource.getDescription(), 100.0f, 100.0f);
        }
        int n = LoadingList.get().getTotalResources();
        int n2 = LoadingList.get().getTotalResources() - LoadingList.get().getRemainingResources();
        float f = (float)n2 / (float)n;
        graphics.fillRect(100.0f, 150.0f, n2 * 40, 20.0f);
        graphics.drawRect(100.0f, 150.0f, n * 40, 20.0f);
        if (this.started) {
            this.image.draw(100.0f, 200.0f);
            this.font.drawString(100.0f, 500.0f, "LOADING COMPLETE");
        }
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        if (this.nextResource != null) {
            try {
                this.nextResource.load();
                try {
                    Thread.sleep(50L);
                }
                catch (Exception exception) {}
            }
            catch (IOException iOException) {
                throw new SlickException("Failed to load: " + this.nextResource.getDescription(), iOException);
            }
            this.nextResource = null;
        }
        if (LoadingList.get().getRemainingResources() > 0) {
            this.nextResource = LoadingList.get().getNext();
        } else if (!this.started) {
            this.started = true;
            this.music.loop();
            this.sound.play();
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new DeferredLoadingTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void keyPressed(int n, char c) {
    }
}

