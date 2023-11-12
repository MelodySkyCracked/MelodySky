/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.openal.AL10
 */
package org.newdawn.slick.openal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.AudioImpl;
import org.newdawn.slick.openal.SoundStore;

public class MODSound
extends AudioImpl {
    private SoundStore store;

    public MODSound(SoundStore soundStore, InputStream inputStream) throws IOException {
        this.store = soundStore;
    }

    @Override
    public int playAsMusic(float f, float f2, boolean bl) {
        this.cleanUpSource();
        this.store.setCurrentMusicVolume(f2);
        this.store.setMOD(this);
        return this.store.getSource(0);
    }

    private void cleanUpSource() {
        AL10.alSourceStop((int)this.store.getSource(0));
        IntBuffer intBuffer = BufferUtils.createIntBuffer((int)1);
        for (int i = AL10.alGetSourcei((int)this.store.getSource(0), (int)4117); i > 0; --i) {
            AL10.alSourceUnqueueBuffers((int)this.store.getSource(0), (IntBuffer)intBuffer);
        }
        AL10.alSourcei((int)this.store.getSource(0), (int)4105, (int)0);
    }

    public void poll() {
    }

    @Override
    public int playAsSoundEffect(float f, float f2, boolean bl) {
        return -1;
    }

    @Override
    public void stop() {
        this.store.setMOD(null);
    }

    @Override
    public float getPosition() {
        throw new RuntimeException("Positioning on modules is not currently supported");
    }

    @Override
    public boolean setPosition(float f) {
        throw new RuntimeException("Positioning on modules is not currently supported");
    }
}

