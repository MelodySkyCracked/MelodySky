/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.SoundStore;

public class SoundPositionTest
extends BasicGame {
    private GameContainer myContainer;
    private Music music;
    private int[] engines = new int[3];

    public SoundPositionTest() {
        super("Music Position Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        SoundStore.get().setMaxSources(32);
        this.myContainer = gameContainer;
        this.music = new Music("testdata/kirby.ogg", true);
        this.music.play();
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.drawString("Position: " + this.music.getPosition(), 100.0f, 100.0f);
        graphics.drawString("Space - Pause/Resume", 100.0f, 130.0f);
        graphics.drawString("Right Arrow - Advance 5 seconds", 100.0f, 145.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 57) {
            if (this.music.playing()) {
                this.music.pause();
            } else {
                this.music.resume();
            }
        }
        if (n == 205) {
            this.music.setPosition(this.music.getPosition() + 5.0f);
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new SoundPositionTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

