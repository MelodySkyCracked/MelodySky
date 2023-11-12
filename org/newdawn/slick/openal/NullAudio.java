/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.openal;

import org.newdawn.slick.openal.Audio;

public class NullAudio
implements Audio {
    @Override
    public int getBufferID() {
        return 0;
    }

    @Override
    public float getPosition() {
        return 0.0f;
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int playAsMusic(float f, float f2, boolean bl) {
        return 0;
    }

    @Override
    public int playAsSoundEffect(float f, float f2, boolean bl) {
        return 0;
    }

    @Override
    public int playAsSoundEffect(float f, float f2, boolean bl, float f3, float f4, float f5) {
        return 0;
    }

    @Override
    public boolean setPosition(float f) {
        return false;
    }

    @Override
    public void stop() {
    }
}

