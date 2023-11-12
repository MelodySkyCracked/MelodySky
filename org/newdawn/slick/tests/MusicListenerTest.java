/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;

public class MusicListenerTest
extends BasicGame
implements MusicListener {
    private boolean musicEnded = false;
    private boolean musicSwapped = false;
    private Music music;
    private Music stream;

    public MusicListenerTest() {
        super("Music Listener Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.music = new Music("testdata/restart.ogg", false);
        this.stream = new Music("testdata/restart.ogg", false);
        this.music.addListener(this);
        this.stream.addListener(this);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void musicEnded(Music music) {
        this.musicEnded = true;
    }

    @Override
    public void musicSwapped(Music music, Music music2) {
        this.musicSwapped = true;
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.drawString("Press M to play music", 100.0f, 100.0f);
        graphics.drawString("Press S to stream music", 100.0f, 150.0f);
        if (this.musicEnded) {
            graphics.drawString("Music Ended", 100.0f, 200.0f);
        }
        if (this.musicSwapped) {
            graphics.drawString("Music Swapped", 100.0f, 250.0f);
        }
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 50) {
            this.musicEnded = false;
            this.musicSwapped = false;
            this.music.play();
        }
        if (n == 31) {
            this.musicEnded = false;
            this.musicSwapped = false;
            this.stream.play();
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new MusicListenerTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

