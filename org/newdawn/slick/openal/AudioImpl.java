/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.openal.AL10
 */
package org.newdawn.slick.openal;

import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.SoundStore;

public class AudioImpl
implements Audio {
    private SoundStore store;
    private int buffer;
    private int index = -1;
    private float length;

    AudioImpl(SoundStore soundStore, int n) {
        this.store = soundStore;
        this.buffer = n;
        int n2 = AL10.alGetBufferi((int)n, (int)8196);
        int n3 = AL10.alGetBufferi((int)n, (int)8194);
        int n4 = AL10.alGetBufferi((int)n, (int)8195);
        int n5 = AL10.alGetBufferi((int)n, (int)8193);
        int n6 = n2 / (n3 / 8);
        this.length = (float)n6 / (float)n5 / (float)n4;
    }

    @Override
    public int getBufferID() {
        return this.buffer;
    }

    protected AudioImpl() {
    }

    @Override
    public void stop() {
        if (this.index != -1) {
            this.store.stopSource(this.index);
            this.index = -1;
        }
    }

    @Override
    public boolean isPlaying() {
        if (this.index != -1) {
            return this.store.isPlaying(this.index);
        }
        return false;
    }

    @Override
    public int playAsSoundEffect(float f, float f2, boolean bl) {
        this.index = this.store.playAsSound(this.buffer, f, f2, bl);
        return this.store.getSource(this.index);
    }

    @Override
    public int playAsSoundEffect(float f, float f2, boolean bl, float f3, float f4, float f5) {
        this.index = this.store.playAsSoundAt(this.buffer, f, f2, bl, f3, f4, f5);
        return this.store.getSource(this.index);
    }

    @Override
    public int playAsMusic(float f, float f2, boolean bl) {
        this.store.playAsMusic(this.buffer, f, f2, bl);
        this.index = 0;
        return this.store.getSource(0);
    }

    public static void pauseMusic() {
        SoundStore.get().pauseLoop();
    }

    public static void restartMusic() {
        SoundStore.get().restartLoop();
    }

    @Override
    public boolean setPosition(float f) {
        AL10.alSourcef((int)this.store.getSource(this.index), (int)4132, (float)(f %= this.length));
        return AL10.alGetError() == 0;
    }

    @Override
    public float getPosition() {
        return AL10.alGetSourcef((int)this.store.getSource(this.index), (int)4132);
    }
}

