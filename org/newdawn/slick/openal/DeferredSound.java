/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.openal;

import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioImpl;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.Log;

public class DeferredSound
extends AudioImpl
implements DeferredResource {
    public static final int OGG = 1;
    public static final int WAV = 2;
    public static final int MOD = 3;
    public static final int AIF = 4;
    private int type;
    private String ref;
    private Audio target;
    private InputStream in;

    public DeferredSound(String string, InputStream inputStream, int n) {
        this.ref = string;
        this.type = n;
        if (string.equals(inputStream.toString())) {
            this.in = inputStream;
        }
        LoadingList.get().add(this);
    }

    private void checkTarget() {
        if (this.target == null) {
            throw new RuntimeException("Attempt to use deferred sound before loading");
        }
    }

    @Override
    public void load() throws IOException {
        boolean bl = SoundStore.get().isDeferredLoading();
        SoundStore.get().setDeferredLoading(false);
        if (this.in != null) {
            switch (this.type) {
                case 1: {
                    this.target = SoundStore.get().getOgg(this.in);
                    break;
                }
                case 2: {
                    this.target = SoundStore.get().getWAV(this.in);
                    break;
                }
                case 3: {
                    this.target = SoundStore.get().getMOD(this.in);
                    break;
                }
                case 4: {
                    this.target = SoundStore.get().getAIF(this.in);
                    break;
                }
                default: {
                    Log.error("Unrecognised sound type: " + this.type);
                    break;
                }
            }
        } else {
            switch (this.type) {
                case 1: {
                    this.target = SoundStore.get().getOgg(this.ref);
                    break;
                }
                case 2: {
                    this.target = SoundStore.get().getWAV(this.ref);
                    break;
                }
                case 3: {
                    this.target = SoundStore.get().getMOD(this.ref);
                    break;
                }
                case 4: {
                    this.target = SoundStore.get().getAIF(this.ref);
                    break;
                }
                default: {
                    Log.error("Unrecognised sound type: " + this.type);
                }
            }
        }
        SoundStore.get().setDeferredLoading(bl);
    }

    @Override
    public boolean isPlaying() {
        this.checkTarget();
        return this.target.isPlaying();
    }

    @Override
    public int playAsMusic(float f, float f2, boolean bl) {
        this.checkTarget();
        return this.target.playAsMusic(f, f2, bl);
    }

    @Override
    public int playAsSoundEffect(float f, float f2, boolean bl) {
        this.checkTarget();
        return this.target.playAsSoundEffect(f, f2, bl);
    }

    @Override
    public int playAsSoundEffect(float f, float f2, boolean bl, float f3, float f4, float f5) {
        this.checkTarget();
        return this.target.playAsSoundEffect(f, f2, bl, f3, f4, f5);
    }

    @Override
    public void stop() {
        this.checkTarget();
        this.target.stop();
    }

    @Override
    public String getDescription() {
        return this.ref;
    }
}

