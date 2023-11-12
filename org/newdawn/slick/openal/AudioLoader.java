/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.openal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.SoundStore;

public class AudioLoader {
    private static final String AIF = "AIF";
    private static final String WAV = "WAV";
    private static final String OGG = "OGG";
    private static final String MOD = "MOD";
    private static final String XM = "XM";
    private static boolean inited = false;

    private static void init() {
        if (!inited) {
            SoundStore.get().init();
            inited = true;
        }
    }

    public static Audio getAudio(String string, InputStream inputStream) throws IOException {
        AudioLoader.init();
        if (string.equals(AIF)) {
            return SoundStore.get().getAIF(inputStream);
        }
        if (string.equals(WAV)) {
            return SoundStore.get().getWAV(inputStream);
        }
        if (string.equals(OGG)) {
            return SoundStore.get().getOgg(inputStream);
        }
        throw new IOException("Unsupported format for non-streaming Audio: " + string);
    }

    public static Audio getStreamingAudio(String string, URL uRL) throws IOException {
        AudioLoader.init();
        if (string.equals(OGG)) {
            return SoundStore.get().getOggStream(uRL);
        }
        if (string.equals(MOD)) {
            return SoundStore.get().getMOD(uRL.openStream());
        }
        if (string.equals(XM)) {
            return SoundStore.get().getMOD(uRL.openStream());
        }
        throw new IOException("Unsupported format for streaming Audio: " + string);
    }

    public static void update() {
        AudioLoader.init();
        SoundStore.get().poll(0);
    }
}

