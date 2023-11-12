/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.Sys
 *  org.lwjgl.openal.AL10
 *  org.lwjgl.openal.OpenALException
 */
package org.newdawn.slick.openal;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.security.AccessController;
import java.util.HashMap;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.OpenALException;
import org.newdawn.slick.openal.AiffData;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioImpl;
import org.newdawn.slick.openal.DeferredSound;
import org.newdawn.slick.openal.I;
import org.newdawn.slick.openal.MODSound;
import org.newdawn.slick.openal.NullAudio;
import org.newdawn.slick.openal.OggData;
import org.newdawn.slick.openal.OggDecoder;
import org.newdawn.slick.openal.OpenALStreamPlayer;
import org.newdawn.slick.openal.StreamSound;
import org.newdawn.slick.openal.WaveData;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class SoundStore {
    private static SoundStore store = new SoundStore();
    private boolean sounds;
    private boolean music;
    private boolean soundWorks;
    private int sourceCount;
    private HashMap loaded = new HashMap();
    private int currentMusic = -1;
    private IntBuffer sources;
    private int nextSource;
    private boolean inited = false;
    private MODSound mod;
    private OpenALStreamPlayer stream;
    private float musicVolume = 1.0f;
    private float soundVolume = 1.0f;
    private float lastCurrentMusicVolume = 1.0f;
    private boolean paused;
    private boolean deferred;
    private FloatBuffer sourceVel = BufferUtils.createFloatBuffer((int)3).put(new float[]{0.0f, 0.0f, 0.0f});
    private FloatBuffer sourcePos = BufferUtils.createFloatBuffer((int)3);
    private int maxSources = 64;

    private SoundStore() {
    }

    public void clear() {
        store = new SoundStore();
    }

    public void disable() {
        this.inited = true;
    }

    public void setDeferredLoading(boolean bl) {
        this.deferred = bl;
    }

    public boolean isDeferredLoading() {
        return this.deferred;
    }

    public void setMusicOn(boolean bl) {
        if (this.soundWorks) {
            this.music = bl;
            if (bl) {
                this.restartLoop();
                this.setMusicVolume(this.musicVolume);
            } else {
                this.pauseLoop();
            }
        }
    }

    public boolean isMusicOn() {
        return this.music;
    }

    public void setMusicVolume(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        this.musicVolume = f;
        if (this.soundWorks) {
            AL10.alSourcef((int)this.sources.get(0), (int)4106, (float)(this.lastCurrentMusicVolume * this.musicVolume));
        }
    }

    public float getCurrentMusicVolume() {
        return this.lastCurrentMusicVolume;
    }

    public void setCurrentMusicVolume(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        if (this.soundWorks) {
            this.lastCurrentMusicVolume = f;
            AL10.alSourcef((int)this.sources.get(0), (int)4106, (float)(this.lastCurrentMusicVolume * this.musicVolume));
        }
    }

    public void setSoundVolume(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.soundVolume = f;
    }

    public boolean soundWorks() {
        return this.soundWorks;
    }

    public boolean musicOn() {
        return this.music;
    }

    public float getSoundVolume() {
        return this.soundVolume;
    }

    public float getMusicVolume() {
        return this.musicVolume;
    }

    public int getSource(int n) {
        if (!this.soundWorks) {
            return -1;
        }
        if (n < 0) {
            return -1;
        }
        return this.sources.get(n);
    }

    public void setSoundsOn(boolean bl) {
        if (this.soundWorks) {
            this.sounds = bl;
        }
    }

    public boolean soundsOn() {
        return this.sounds;
    }

    public void setMaxSources(int n) {
        this.maxSources = n;
    }

    public void init() {
        if (this.inited) {
            return;
        }
        Log.info("Initialising sounds..");
        this.inited = true;
        AccessController.doPrivileged(new I(this));
        if (this.soundWorks) {
            Buffer buffer;
            this.sourceCount = 0;
            this.sources = BufferUtils.createIntBuffer((int)this.maxSources);
            while (AL10.alGetError() == 0) {
                buffer = BufferUtils.createIntBuffer((int)1);
                try {
                    AL10.alGenSources((IntBuffer)buffer);
                    if (AL10.alGetError() != 0) continue;
                    ++this.sourceCount;
                    this.sources.put(((IntBuffer)buffer).get(0));
                    if (this.sourceCount <= this.maxSources - 1) continue;
                }
                catch (OpenALException openALException) {}
                break;
            }
            Log.info("- " + this.sourceCount + " OpenAL source available");
            if (AL10.alGetError() != 0) {
                this.sounds = false;
                this.music = false;
                this.soundWorks = false;
                Log.error("- AL init failed");
            } else {
                buffer = BufferUtils.createFloatBuffer((int)6).put(new float[]{0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f});
                FloatBuffer floatBuffer = BufferUtils.createFloatBuffer((int)3).put(new float[]{0.0f, 0.0f, 0.0f});
                FloatBuffer floatBuffer2 = BufferUtils.createFloatBuffer((int)3).put(new float[]{0.0f, 0.0f, 0.0f});
                floatBuffer2.flip();
                floatBuffer.flip();
                ((FloatBuffer)buffer).flip();
                AL10.alListener((int)4100, (FloatBuffer)floatBuffer2);
                AL10.alListener((int)4102, (FloatBuffer)floatBuffer);
                AL10.alListener((int)4111, (FloatBuffer)buffer);
                Log.info("- Sounds source generated");
            }
        }
    }

    void stopSource(int n) {
        AL10.alSourceStop((int)this.sources.get(n));
    }

    int playAsSound(int n, float f, float f2, boolean bl) {
        return this.playAsSoundAt(n, f, f2, bl, 0.0f, 0.0f, 0.0f);
    }

    int playAsSoundAt(int n, float f, float f2, boolean bl, float f3, float f4, float f5) {
        if ((f2 *= this.soundVolume) == 0.0f) {
            f2 = 0.001f;
        }
        if (this.soundWorks && this.sounds) {
            int n2 = this.findFreeSource();
            if (n2 == -1) {
                return -1;
            }
            AL10.alSourceStop((int)this.sources.get(n2));
            AL10.alSourcei((int)this.sources.get(n2), (int)4105, (int)n);
            AL10.alSourcef((int)this.sources.get(n2), (int)4099, (float)f);
            AL10.alSourcef((int)this.sources.get(n2), (int)4106, (float)f2);
            AL10.alSourcei((int)this.sources.get(n2), (int)4103, (int)(bl ? 1 : 0));
            this.sourcePos.clear();
            this.sourceVel.clear();
            this.sourceVel.put(new float[]{0.0f, 0.0f, 0.0f});
            this.sourcePos.put(new float[]{f3, f4, f5});
            this.sourcePos.flip();
            this.sourceVel.flip();
            AL10.alSource((int)this.sources.get(n2), (int)4100, (FloatBuffer)this.sourcePos);
            AL10.alSource((int)this.sources.get(n2), (int)4102, (FloatBuffer)this.sourceVel);
            AL10.alSourcePlay((int)this.sources.get(n2));
            return n2;
        }
        return -1;
    }

    boolean isPlaying(int n) {
        int n2 = AL10.alGetSourcei((int)this.sources.get(n), (int)4112);
        return n2 == 4114;
    }

    private int findFreeSource() {
        for (int i = 1; i < this.sourceCount - 1; ++i) {
            int n = AL10.alGetSourcei((int)this.sources.get(i), (int)4112);
            if (n == 4114 || n == 4115) continue;
            return i;
        }
        return -1;
    }

    void playAsMusic(int n, float f, float f2, boolean bl) {
        this.paused = false;
        this.setMOD(null);
        if (this.soundWorks) {
            if (this.currentMusic != -1) {
                AL10.alSourceStop((int)this.sources.get(0));
            }
            this.getMusicSource();
            AL10.alSourcei((int)this.sources.get(0), (int)4105, (int)n);
            AL10.alSourcef((int)this.sources.get(0), (int)4099, (float)f);
            AL10.alSourcei((int)this.sources.get(0), (int)4103, (int)(bl ? 1 : 0));
            this.currentMusic = this.sources.get(0);
            if (!this.music) {
                this.pauseLoop();
            } else {
                AL10.alSourcePlay((int)this.sources.get(0));
            }
        }
    }

    private int getMusicSource() {
        return this.sources.get(0);
    }

    public void setMusicPitch(float f) {
        if (this.soundWorks) {
            AL10.alSourcef((int)this.sources.get(0), (int)4099, (float)f);
        }
    }

    public void pauseLoop() {
        if (this.soundWorks && this.currentMusic != -1) {
            this.paused = true;
            AL10.alSourcePause((int)this.currentMusic);
        }
    }

    public void restartLoop() {
        if (this.music && this.soundWorks && this.currentMusic != -1) {
            this.paused = false;
            AL10.alSourcePlay((int)this.currentMusic);
        }
    }

    boolean isPlaying(OpenALStreamPlayer openALStreamPlayer) {
        return this.stream == openALStreamPlayer;
    }

    public Audio getMOD(String string) throws IOException {
        return this.getMOD(string, ResourceLoader.getResourceAsStream(string));
    }

    public Audio getMOD(InputStream inputStream) throws IOException {
        return this.getMOD(inputStream.toString(), inputStream);
    }

    public Audio getMOD(String string, InputStream inputStream) throws IOException {
        if (!this.soundWorks) {
            return new NullAudio();
        }
        if (!this.inited) {
            throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
        }
        if (this.deferred) {
            return new DeferredSound(string, inputStream, 3);
        }
        return new MODSound(this, inputStream);
    }

    public Audio getAIF(String string) throws IOException {
        return this.getAIF(string, ResourceLoader.getResourceAsStream(string));
    }

    public Audio getAIF(InputStream inputStream) throws IOException {
        return this.getAIF(inputStream.toString(), inputStream);
    }

    public Audio getAIF(String string, InputStream inputStream) throws IOException {
        inputStream = new BufferedInputStream(inputStream);
        if (!this.soundWorks) {
            return new NullAudio();
        }
        if (!this.inited) {
            throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
        }
        if (this.deferred) {
            return new DeferredSound(string, inputStream, 4);
        }
        int n = -1;
        if (this.loaded.get(string) != null) {
            n = (Integer)this.loaded.get(string);
        } else {
            try {
                IntBuffer intBuffer = BufferUtils.createIntBuffer((int)1);
                AiffData aiffData = AiffData.create(inputStream);
                AL10.alGenBuffers((IntBuffer)intBuffer);
                AL10.alBufferData((int)intBuffer.get(0), (int)aiffData.format, (ByteBuffer)aiffData.data, (int)aiffData.samplerate);
                this.loaded.put(string, new Integer(intBuffer.get(0)));
                n = intBuffer.get(0);
            }
            catch (Exception exception) {
                Log.error(exception);
                IOException iOException = new IOException("Failed to load: " + string);
                iOException.initCause(exception);
                throw iOException;
            }
        }
        if (n == -1) {
            throw new IOException("Unable to load: " + string);
        }
        return new AudioImpl(this, n);
    }

    public Audio getWAV(String string) throws IOException {
        return this.getWAV(string, ResourceLoader.getResourceAsStream(string));
    }

    public Audio getWAV(InputStream inputStream) throws IOException {
        return this.getWAV(inputStream.toString(), inputStream);
    }

    public Audio getWAV(String string, InputStream inputStream) throws IOException {
        if (!this.soundWorks) {
            return new NullAudio();
        }
        if (!this.inited) {
            throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
        }
        if (this.deferred) {
            return new DeferredSound(string, inputStream, 2);
        }
        int n = -1;
        if (this.loaded.get(string) != null) {
            n = (Integer)this.loaded.get(string);
        } else {
            try {
                IntBuffer intBuffer = BufferUtils.createIntBuffer((int)1);
                WaveData waveData = WaveData.create(inputStream);
                AL10.alGenBuffers((IntBuffer)intBuffer);
                AL10.alBufferData((int)intBuffer.get(0), (int)waveData.format, (ByteBuffer)waveData.data, (int)waveData.samplerate);
                this.loaded.put(string, new Integer(intBuffer.get(0)));
                n = intBuffer.get(0);
            }
            catch (Exception exception) {
                Log.error(exception);
                IOException iOException = new IOException("Failed to load: " + string);
                iOException.initCause(exception);
                throw iOException;
            }
        }
        if (n == -1) {
            throw new IOException("Unable to load: " + string);
        }
        return new AudioImpl(this, n);
    }

    public Audio getOggStream(String string) throws IOException {
        if (!this.soundWorks) {
            return new NullAudio();
        }
        this.setMOD(null);
        this.setStream(null);
        if (this.currentMusic != -1) {
            AL10.alSourceStop((int)this.sources.get(0));
        }
        this.getMusicSource();
        this.currentMusic = this.sources.get(0);
        return new StreamSound(new OpenALStreamPlayer(this.currentMusic, string));
    }

    public Audio getOggStream(URL uRL) throws IOException {
        if (!this.soundWorks) {
            return new NullAudio();
        }
        this.setMOD(null);
        this.setStream(null);
        if (this.currentMusic != -1) {
            AL10.alSourceStop((int)this.sources.get(0));
        }
        this.getMusicSource();
        this.currentMusic = this.sources.get(0);
        return new StreamSound(new OpenALStreamPlayer(this.currentMusic, uRL));
    }

    public Audio getOgg(String string) throws IOException {
        return this.getOgg(string, ResourceLoader.getResourceAsStream(string));
    }

    public Audio getOgg(InputStream inputStream) throws IOException {
        return this.getOgg(inputStream.toString(), inputStream);
    }

    public Audio getOgg(String string, InputStream inputStream) throws IOException {
        if (!this.soundWorks) {
            return new NullAudio();
        }
        if (!this.inited) {
            throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
        }
        if (this.deferred) {
            return new DeferredSound(string, inputStream, 1);
        }
        int n = -1;
        if (this.loaded.get(string) != null) {
            n = (Integer)this.loaded.get(string);
        } else {
            try {
                IntBuffer intBuffer = BufferUtils.createIntBuffer((int)1);
                OggDecoder oggDecoder = new OggDecoder();
                OggData oggData = oggDecoder.getData(inputStream);
                AL10.alGenBuffers((IntBuffer)intBuffer);
                AL10.alBufferData((int)intBuffer.get(0), (int)(oggData.channels > 1 ? 4355 : 4353), (ByteBuffer)oggData.data, (int)oggData.rate);
                this.loaded.put(string, new Integer(intBuffer.get(0)));
                n = intBuffer.get(0);
            }
            catch (Exception exception) {
                Log.error(exception);
                Sys.alert((String)"Error", (String)("Failed to load: " + string + " - " + exception.getMessage()));
                throw new IOException("Unable to load: " + string);
            }
        }
        if (n == -1) {
            throw new IOException("Unable to load: " + string);
        }
        return new AudioImpl(this, n);
    }

    void setMOD(MODSound mODSound) {
        if (!this.soundWorks) {
            return;
        }
        this.currentMusic = this.sources.get(0);
        this.stopSource(0);
        this.mod = mODSound;
        if (mODSound != null) {
            this.stream = null;
        }
        this.paused = false;
    }

    void setStream(OpenALStreamPlayer openALStreamPlayer) {
        if (!this.soundWorks) {
            return;
        }
        this.currentMusic = this.sources.get(0);
        this.stream = openALStreamPlayer;
        if (openALStreamPlayer != null) {
            this.mod = null;
        }
        this.paused = false;
    }

    public void poll(int n) {
        if (!this.soundWorks) {
            return;
        }
        if (this.paused) {
            return;
        }
        if (this.music) {
            if (this.mod != null) {
                try {
                    this.mod.poll();
                }
                catch (OpenALException openALException) {
                    Log.error("Error with OpenGL MOD Player on this this platform");
                    Log.error(openALException);
                    this.mod = null;
                }
            }
            if (this.stream != null) {
                try {
                    this.stream.update();
                }
                catch (OpenALException openALException) {
                    Log.error("Error with OpenGL Streaming Player on this this platform");
                    Log.error(openALException);
                    this.mod = null;
                }
            }
        }
    }

    public boolean isMusicPlaying() {
        if (!this.soundWorks) {
            return false;
        }
        int n = AL10.alGetSourcei((int)this.sources.get(0), (int)4112);
        return n == 4114 || n == 4115;
    }

    public static SoundStore get() {
        return store;
    }

    public void stopSoundEffect(int n) {
        AL10.alSourceStop((int)n);
    }

    public int getSourceCount() {
        return this.sourceCount;
    }

    static boolean access$002(SoundStore soundStore, boolean bl) {
        soundStore.soundWorks = bl;
        return soundStore.soundWorks;
    }

    static boolean access$102(SoundStore soundStore, boolean bl) {
        soundStore.sounds = bl;
        return soundStore.sounds;
    }

    static boolean access$202(SoundStore soundStore, boolean bl) {
        soundStore.music = bl;
        return soundStore.music;
    }
}

