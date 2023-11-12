/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioImpl;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.Log;

public class Music {
    private static Music currentMusic;
    private Audio sound;
    private boolean playing;
    private ArrayList listeners;
    private float volume;
    private float fadeStartGain;
    private float fadeEndGain;
    private int fadeTime;
    private int fadeDuration;
    private boolean stopAfterFade;
    private boolean positioning;
    private float requiredPosition;

    public static void poll(int n) {
        if (currentMusic != null) {
            SoundStore.get().poll(n);
            if (!SoundStore.get().isMusicPlaying()) {
                if (!Music.currentMusic.positioning) {
                    Music music = currentMusic;
                    currentMusic = null;
                    music.fireMusicEnded();
                }
            } else {
                currentMusic.update(n);
            }
        }
    }

    public Music(String string) throws SlickException {
        this(string, false);
    }

    public Music(URL uRL) throws SlickException {
        this(uRL, false);
    }

    public Music(InputStream inputStream, String string) throws SlickException {
        block6: {
            this.listeners = new ArrayList();
            this.volume = 1.0f;
            this.requiredPosition = -1.0f;
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
                if (string.toLowerCase().endsWith(".xm") || string.toLowerCase().endsWith(".mod")) {
                    this.sound = SoundStore.get().getMOD(inputStream);
                    break block6;
                }
                if (string.toLowerCase().endsWith(".aif") || string.toLowerCase().endsWith(".aiff")) {
                    this.sound = SoundStore.get().getAIF(inputStream);
                    break block6;
                }
                throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
            }
            catch (Exception exception) {
                Log.error(exception);
                throw new SlickException("Failed to load music: " + string);
            }
        }
    }

    public Music(URL uRL, boolean bl) throws SlickException {
        block6: {
            this.listeners = new ArrayList();
            this.volume = 1.0f;
            this.requiredPosition = -1.0f;
            SoundStore.get().init();
            String string = uRL.getFile();
            try {
                if (string.toLowerCase().endsWith(".ogg")) {
                    this.sound = bl ? SoundStore.get().getOggStream(uRL) : SoundStore.get().getOgg(uRL.openStream());
                    break block6;
                }
                if (string.toLowerCase().endsWith(".wav")) {
                    this.sound = SoundStore.get().getWAV(uRL.openStream());
                    break block6;
                }
                if (string.toLowerCase().endsWith(".xm") || string.toLowerCase().endsWith(".mod")) {
                    this.sound = SoundStore.get().getMOD(uRL.openStream());
                    break block6;
                }
                if (string.toLowerCase().endsWith(".aif") || string.toLowerCase().endsWith(".aiff")) {
                    this.sound = SoundStore.get().getAIF(uRL.openStream());
                    break block6;
                }
                throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
            }
            catch (Exception exception) {
                Log.error(exception);
                throw new SlickException("Failed to load sound: " + uRL);
            }
        }
    }

    public Music(String string, boolean bl) throws SlickException {
        block6: {
            this.listeners = new ArrayList();
            this.volume = 1.0f;
            this.requiredPosition = -1.0f;
            SoundStore.get().init();
            try {
                if (string.toLowerCase().endsWith(".ogg")) {
                    this.sound = bl ? SoundStore.get().getOggStream(string) : SoundStore.get().getOgg(string);
                    break block6;
                }
                if (string.toLowerCase().endsWith(".wav")) {
                    this.sound = SoundStore.get().getWAV(string);
                    break block6;
                }
                if (string.toLowerCase().endsWith(".xm") || string.toLowerCase().endsWith(".mod")) {
                    this.sound = SoundStore.get().getMOD(string);
                    break block6;
                }
                if (string.toLowerCase().endsWith(".aif") || string.toLowerCase().endsWith(".aiff")) {
                    this.sound = SoundStore.get().getAIF(string);
                    break block6;
                }
                throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
            }
            catch (Exception exception) {
                Log.error(exception);
                throw new SlickException("Failed to load sound: " + string);
            }
        }
    }

    public void addListener(MusicListener musicListener) {
        this.listeners.add(musicListener);
    }

    public void removeListener(MusicListener musicListener) {
        this.listeners.remove(musicListener);
    }

    private void fireMusicEnded() {
        this.playing = false;
        for (int i = 0; i < this.listeners.size(); ++i) {
            ((MusicListener)this.listeners.get(i)).musicEnded(this);
        }
    }

    private void fireMusicSwapped(Music music) {
        this.playing = false;
        for (int i = 0; i < this.listeners.size(); ++i) {
            ((MusicListener)this.listeners.get(i)).musicSwapped(this, music);
        }
    }

    public void loop() {
        this.loop(1.0f, 1.0f);
    }

    public void play() {
        this.play(1.0f, 1.0f);
    }

    public void play(float f, float f2) {
        this.startMusic(f, f2, false);
    }

    public void loop(float f, float f2) {
        this.startMusic(f, f2, true);
    }

    private void startMusic(float f, float f2, boolean bl) {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.fireMusicSwapped(this);
        }
        currentMusic = this;
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        this.sound.playAsMusic(f, f2, bl);
        this.playing = true;
        this.setVolume(f2);
        if (this.requiredPosition != -1.0f) {
            this.setPosition(this.requiredPosition);
        }
    }

    public void pause() {
        this.playing = false;
        AudioImpl.pauseMusic();
    }

    public void stop() {
        this.sound.stop();
    }

    public void resume() {
        this.playing = true;
        AudioImpl.restartMusic();
    }

    public boolean playing() {
        return currentMusic == this && this.playing;
    }

    public void setVolume(float f) {
        if (f > 1.0f) {
            f = 1.0f;
        } else if (f < 0.0f) {
            f = 0.0f;
        }
        this.volume = f;
        if (currentMusic == this) {
            SoundStore.get().setCurrentMusicVolume(f);
        }
    }

    public float getVolume() {
        return this.volume;
    }

    public void fade(int n, float f, boolean bl) {
        this.stopAfterFade = bl;
        this.fadeStartGain = this.volume;
        this.fadeEndGain = f;
        this.fadeDuration = n;
        this.fadeTime = n;
    }

    void update(int n) {
        if (!this.playing) {
            return;
        }
        if (this.fadeTime > 0) {
            this.fadeTime -= n;
            if (this.fadeTime < 0) {
                this.fadeTime = 0;
                if (this.stopAfterFade) {
                    this.stop();
                    return;
                }
            }
            float f = (this.fadeEndGain - this.fadeStartGain) * (1.0f - (float)this.fadeTime / (float)this.fadeDuration);
            this.setVolume(this.fadeStartGain + f);
        }
    }

    public boolean setPosition(float f) {
        if (this.playing) {
            this.requiredPosition = -1.0f;
            this.positioning = true;
            this.playing = false;
            boolean bl = this.sound.setPosition(f);
            this.playing = true;
            this.positioning = false;
            return bl;
        }
        this.requiredPosition = f;
        return false;
    }

    public float getPosition() {
        return this.sound.getPosition();
    }
}

