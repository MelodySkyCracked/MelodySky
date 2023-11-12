/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import java.io.InputStream;
import java.net.URL;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.Log;

public class Sound {
    private Audio sound;

    public Sound(InputStream inputStream, String string) throws SlickException {
        block6: {
            SoundStore.get().init();
            try {
                if (string.toLowerCase().endsWith(".ogg")) {
                    this.sound = SoundStore.get().getOgg(inputStream);
                    break block6;
                }
                if (string.toLowerCase().endsWith(".wav")) {
                    this.sound = SoundStore.get().getWAV(inputStream);
                    break block6;
                }
                if (string.toLowerCase().endsWith(".aif")) {
                    this.sound = SoundStore.get().getAIF(inputStream);
                    break block6;
                }
                if (string.toLowerCase().endsWith(".xm") || string.toLowerCase().endsWith(".mod")) {
                    this.sound = SoundStore.get().getMOD(inputStream);
                    break block6;
                }
                throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
            }
            catch (Exception exception) {
                Log.error(exception);
                throw new SlickException("Failed to load sound: " + string);
            }
        }
    }

    public Sound(URL uRL) throws SlickException {
        block6: {
            SoundStore.get().init();
            String string = uRL.getFile();
            try {
                if (string.toLowerCase().endsWith(".ogg")) {
                    this.sound = SoundStore.get().getOgg(uRL.openStream());
                    break block6;
                }
                if (string.toLowerCase().endsWith(".wav")) {
                    this.sound = SoundStore.get().getWAV(uRL.openStream());
                    break block6;
                }
                if (string.toLowerCase().endsWith(".aif")) {
                    this.sound = SoundStore.get().getAIF(uRL.openStream());
                    break block6;
                }
                if (string.toLowerCase().endsWith(".xm") || string.toLowerCase().endsWith(".mod")) {
                    this.sound = SoundStore.get().getMOD(uRL.openStream());
                    break block6;
                }
                throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
            }
            catch (Exception exception) {
                Log.error(exception);
                throw new SlickException("Failed to load sound: " + string);
            }
        }
    }

    public Sound(String string) throws SlickException {
        block6: {
            SoundStore.get().init();
            try {
                if (string.toLowerCase().endsWith(".ogg")) {
                    this.sound = SoundStore.get().getOgg(string);
                    break block6;
                }
                if (string.toLowerCase().endsWith(".wav")) {
                    this.sound = SoundStore.get().getWAV(string);
                    break block6;
                }
                if (string.toLowerCase().endsWith(".aif")) {
                    this.sound = SoundStore.get().getAIF(string);
                    break block6;
                }
                if (string.toLowerCase().endsWith(".xm") || string.toLowerCase().endsWith(".mod")) {
                    this.sound = SoundStore.get().getMOD(string);
                    break block6;
                }
                throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
            }
            catch (Exception exception) {
                Log.error(exception);
                throw new SlickException("Failed to load sound: " + string);
            }
        }
    }

    public void play() {
        this.play(1.0f, 1.0f);
    }

    public void play(float f, float f2) {
        this.sound.playAsSoundEffect(f, f2 * SoundStore.get().getSoundVolume(), false);
    }

    public void playAt(float f, float f2, float f3) {
        this.playAt(1.0f, 1.0f, f, f2, f3);
    }

    public void playAt(float f, float f2, float f3, float f4, float f5) {
        this.sound.playAsSoundEffect(f, f2 * SoundStore.get().getSoundVolume(), false, f3, f4, f5);
    }

    public void loop() {
        this.loop(1.0f, 1.0f);
    }

    public void loop(float f, float f2) {
        this.sound.playAsSoundEffect(f, f2 * SoundStore.get().getSoundVolume(), true);
    }

    public boolean playing() {
        return this.sound.isPlaying();
    }

    public void stop() {
        this.sound.stop();
    }
}

