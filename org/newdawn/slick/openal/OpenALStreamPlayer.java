/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.openal.AL10
 */
package org.newdawn.slick.openal;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.AudioInputStream;
import org.newdawn.slick.openal.OggInputStream;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class OpenALStreamPlayer {
    public static final int BUFFER_COUNT = 3;
    private static final int sectionSize = 81920;
    private byte[] buffer = new byte[81920];
    private IntBuffer bufferNames;
    private ByteBuffer bufferData = BufferUtils.createByteBuffer((int)81920);
    private IntBuffer unqueued = BufferUtils.createIntBuffer((int)1);
    private int source;
    private int remainingBufferCount;
    private boolean loop;
    private boolean done = true;
    private AudioInputStream audio;
    private String ref;
    private URL url;
    private float pitch;
    private float positionOffset;

    public OpenALStreamPlayer(int n, String string) {
        this.source = n;
        this.ref = string;
        this.bufferNames = BufferUtils.createIntBuffer((int)3);
        AL10.alGenBuffers((IntBuffer)this.bufferNames);
    }

    public OpenALStreamPlayer(int n, URL uRL) {
        this.source = n;
        this.url = uRL;
        this.bufferNames = BufferUtils.createIntBuffer((int)3);
        AL10.alGenBuffers((IntBuffer)this.bufferNames);
    }

    private void initStreams() throws IOException {
        if (this.audio != null) {
            this.audio.close();
        }
        OggInputStream oggInputStream = this.url != null ? new OggInputStream(this.url.openStream()) : new OggInputStream(ResourceLoader.getResourceAsStream(this.ref));
        this.audio = oggInputStream;
        this.positionOffset = 0.0f;
    }

    public String getSource() {
        return this.url == null ? this.ref : this.url.toString();
    }

    private void removeBuffers() {
        IntBuffer intBuffer = BufferUtils.createIntBuffer((int)1);
        for (int i = AL10.alGetSourcei((int)this.source, (int)4117); i > 0; --i) {
            AL10.alSourceUnqueueBuffers((int)this.source, (IntBuffer)intBuffer);
        }
    }

    public void play(boolean bl) throws IOException {
        this.loop = bl;
        this.initStreams();
        this.done = false;
        AL10.alSourceStop((int)this.source);
        this.removeBuffers();
        this.startPlayback();
    }

    public void setup(float f) {
        this.pitch = f;
    }

    public boolean done() {
        return this.done;
    }

    public void update() {
        int n;
        if (this.done) {
            return;
        }
        float f = this.audio.getRate();
        float f2 = this.audio.getChannels() > 1 ? 4.0f : 2.0f;
        for (int i = AL10.alGetSourcei((int)this.source, (int)4118); i > 0; --i) {
            this.unqueued.clear();
            AL10.alSourceUnqueueBuffers((int)this.source, (IntBuffer)this.unqueued);
            n = this.unqueued.get(0);
            float f3 = (float)AL10.alGetBufferi((int)n, (int)8196) / f2 / f;
            this.positionOffset += f3;
            if (this != n) {
                AL10.alSourceQueueBuffers((int)this.source, (IntBuffer)this.unqueued);
                continue;
            }
            --this.remainingBufferCount;
            if (this.remainingBufferCount != 0) continue;
            this.done = true;
        }
        n = AL10.alGetSourcei((int)this.source, (int)4112);
        if (n != 4114) {
            AL10.alSourcePlay((int)this.source);
        }
    }

    public boolean setPosition(float f) {
        try {
            if (this.getPosition() > f) {
                this.initStreams();
            }
            float f2 = this.audio.getRate();
            float f3 = this.audio.getChannels() > 1 ? 4.0f : 2.0f;
            while (this.positionOffset < f) {
                int n = this.audio.read(this.buffer);
                if (n != -1) {
                    float f4 = (float)n / f3 / f2;
                    this.positionOffset += f4;
                    continue;
                }
                if (this.loop) {
                    this.initStreams();
                } else {
                    this.done = true;
                }
                return false;
            }
            this.startPlayback();
            return true;
        }
        catch (IOException iOException) {
            Log.error(iOException);
            return false;
        }
    }

    private void startPlayback() {
        AL10.alSourcei((int)this.source, (int)4103, (int)0);
        AL10.alSourcef((int)this.source, (int)4099, (float)this.pitch);
        this.remainingBufferCount = 3;
        for (int i = 0; i < 3; ++i) {
            this.stream(this.bufferNames.get(i));
        }
        AL10.alSourceQueueBuffers((int)this.source, (IntBuffer)this.bufferNames);
        AL10.alSourcePlay((int)this.source);
    }

    public float getPosition() {
        return this.positionOffset + AL10.alGetSourcef((int)this.source, (int)4132);
    }
}

