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
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.ResourceLoader;

public class SoundURLTest
extends BasicGame {
    private Sound sound;
    private Sound charlie;
    private Sound burp;
    private Music music;
    private Music musica;
    private Music musicb;
    private Sound engine;
    private int volume = 1;

    public SoundURLTest() {
        super("Sound URL Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.sound = new Sound(ResourceLoader.getResource("testdata/restart.ogg"));
        this.charlie = new Sound(ResourceLoader.getResource("testdata/cbrown01.wav"));
        this.engine = new Sound(ResourceLoader.getResource("testdata/engine.wav"));
        this.music = this.musica = new Music(ResourceLoader.getResource("testdata/restart.ogg"), false);
        this.musicb = new Music(ResourceLoader.getResource("testdata/kirby.ogg"), false);
        this.burp = new Sound(ResourceLoader.getResource("testdata/burp.aif"));
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.drawString("The OGG loop is now streaming from the file, woot.", 100.0f, 60.0f);
        graphics.drawString("Press space for sound effect (OGG)", 100.0f, 100.0f);
        graphics.drawString("Press P to pause/resume music (XM)", 100.0f, 130.0f);
        graphics.drawString("Press E to pause/resume engine sound (WAV)", 100.0f, 190.0f);
        graphics.drawString("Press enter for charlie (WAV)", 100.0f, 160.0f);
        graphics.drawString("Press C to change music", 100.0f, 210.0f);
        graphics.drawString("Press B to burp (AIF)", 100.0f, 240.0f);
        graphics.drawString("Press + or - to change volume of music", 100.0f, 270.0f);
        graphics.setColor(Color.blue);
        graphics.drawString("Music Volume Level: " + (float)this.volume / 10.0f, 150.0f, 300.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            System.exit(0);
        }
        if (n == 57) {
            this.sound.play();
        }
        if (n == 48) {
            this.burp.play();
        }
        if (n == 30) {
            this.sound.playAt(-1.0f, 0.0f, 0.0f);
        }
        if (n == 38) {
            this.sound.playAt(1.0f, 0.0f, 0.0f);
        }
        if (n == 28) {
            this.charlie.play(1.0f, 1.0f);
        }
        if (n == 25) {
            if (this.music.playing()) {
                this.music.pause();
            } else {
                this.music.resume();
            }
        }
        if (n == 46) {
            this.music.stop();
            this.music = this.music == this.musica ? this.musicb : this.musica;
            this.music.loop();
        }
        if (n == 18) {
            if (this.engine.playing()) {
                this.engine.stop();
            } else {
                this.engine.loop();
            }
        }
        if (c == '+') {
            ++this.volume;
            this.setVolume();
        }
        if (c == '-') {
            --this.volume;
            this.setVolume();
        }
    }

    private void setVolume() {
        if (this.volume > 10) {
            this.volume = 10;
        } else if (this.volume < 0) {
            this.volume = 0;
        }
        this.music.setVolume((float)this.volume / 10.0f);
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new SoundURLTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

