/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.openal.AL10
 */
package org.newdawn.slick.openal;

import java.io.IOException;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.AudioImpl;
import org.newdawn.slick.openal.OpenALStreamPlayer;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.Log;

public class StreamSound
extends AudioImpl {
    private OpenALStreamPlayer player;

    public StreamSound(OpenALStreamPlayer openALStreamPlayer) {
        this.player = openALStreamPlayer;
    }

    @Override
    public boolean isPlaying() {
        return SoundStore.get().isPlaying(this.player);
    }

    @Override
    public int playAsMusic(float f, float f2, boolean bl) {
        try {
            this.cleanUpSource();
            this.player.setup(f);
            this.player.play(bl);
            SoundStore.get().setStream(this.player);
        }
        catch (IOException iOException) {
            Log.error("Failed to read OGG source: " + this.player.getSource());
        }
        return SoundStore.get().getSource(0);
    }

    private void cleanUpSource() {
        SoundStore soundStore = SoundStore.get();
        AL10.alSourceStop((int)soundStore.getSource(0));
        IntBuffer intBuffer = BufferUtils.createIntBuffer((int)1);
        for (int i = AL10.alGetSourcei((int)soundStore.getSource(0), (int)4117); i > 0; --i) {
            AL10.alSourceUnqueueBuffers((int)soundStore.getSource(0), (IntBuffer)intBuffer);
        }
        AL10.alSourcei((int)soundStore.getSource(0), (int)4105, (int)0);
    }

    @Override
    public int playAsSoundEffect(float f, float f2, boolean bl, float f3, float f4, float f5) {
        return this.playAsMusic(f, f2, bl);
    }

    @Override
    public int playAsSoundEffect(float f, float f2, boolean bl) {
        return this.playAsMusic(f, f2, bl);
    }

    @Override
    public void stop() {
        SoundStore.get().setStream(null);
    }

    @Override
    public boolean setPosition(float f) {
        return this.player.setPosition(f);
    }

    @Override
    public float getPosition() {
        return this.player.getPosition();
    }
}

