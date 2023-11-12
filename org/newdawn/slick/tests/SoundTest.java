/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import java.io.IOException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

public class SoundTest
extends BasicGame {
    private GameContainer myContainer;
    private Sound sound;
    private Sound charlie;
    private Sound burp;
    private Music music;
    private Music musica;
    private Music musicb;
    private Audio engine;
    private int volume = 10;
    private int[] engines = new int[3];

    public SoundTest() {
        super("Sound And Music Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        SoundStore.get().setMaxSources(32);
        this.myContainer = gameContainer;
        this.sound = new Sound("testdata/restart.ogg");
        this.charlie = new Sound("testdata/cbrown01.wav");
        try {
            this.engine = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("testdata/engine.wav"));
        }
        catch (IOException iOException) {
            throw new SlickException("Failed to load engine", iOException);
        }
        this.music = this.musica = new Music("testdata/SMB-X.XM");
        this.musicb = new Music("testdata/kirby.ogg", true);
        this.burp = new Sound("testdata/burp.aif");
        this.music.play();
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
        graphics.drawString("Press + or - to change global volume of music", 100.0f, 270.0f);
        graphics.drawString("Press Y or X to change individual volume of music", 100.0f, 300.0f);
        graphics.drawString("Press N or M to change global volume of sound fx", 100.0f, 330.0f);
        graphics.setColor(Color.blue);
        graphics.drawString("Global Sound Volume Level: " + gameContainer.getSoundVolume(), 150.0f, 390.0f);
        graphics.drawString("Global Music Volume Level: " + gameContainer.getMusicVolume(), 150.0f, 420.0f);
        graphics.drawString("Current Music Volume Level: " + this.music.getVolume(), 150.0f, 450.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
    }

    @Override
    public void keyPressed(int n, char c) {
        int n2;
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
        for (n2 = 0; n2 < 3; ++n2) {
            if (n != 2 + n2) continue;
            if (this.engines[n2] != 0) {
                System.out.println("Stop " + n2);
                SoundStore.get().stopSoundEffect(this.engines[n2]);
                this.engines[n2] = 0;
                continue;
            }
            System.out.println("Start " + n2);
            this.engines[n2] = this.engine.playAsSoundEffect(1.0f, 1.0f, true);
        }
        if (c == '+') {
            ++this.volume;
            this.setVolume();
        }
        if (c == '-') {
            --this.volume;
            this.setVolume();
        }
        if (n == 21) {
            n2 = (int)(this.music.getVolume() * 10.0f);
            if (--n2 < 0) {
                n2 = 0;
            }
            this.music.setVolume((float)n2 / 10.0f);
        }
        if (n == 45) {
            n2 = (int)(this.music.getVolume() * 10.0f);
            if (++n2 > 10) {
                n2 = 10;
            }
            this.music.setVolume((float)n2 / 10.0f);
        }
        if (n == 49) {
            n2 = (int)(this.myContainer.getSoundVolume() * 10.0f);
            if (--n2 < 0) {
                n2 = 0;
            }
            this.myContainer.setSoundVolume((float)n2 / 10.0f);
        }
        if (n == 50) {
            n2 = (int)(this.myContainer.getSoundVolume() * 10.0f);
            if (++n2 > 10) {
                n2 = 10;
            }
            this.myContainer.setSoundVolume((float)n2 / 10.0f);
        }
    }

    private void setVolume() {
        if (this.volume > 10) {
            this.volume = 10;
        } else if (this.volume < 0) {
            this.volume = 0;
        }
        this.myContainer.setMusicVolume((float)this.volume / 10.0f);
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new SoundTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

