/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.LWJGLUtil
 */
package org.newdawn.slick.openal;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import org.lwjgl.LWJGLUtil;

public class WaveData {
    public final ByteBuffer data;
    public final int format;
    public final int samplerate;

    private WaveData(ByteBuffer byteBuffer, int n, int n2) {
        this.data = byteBuffer;
        this.format = n;
        this.samplerate = n2;
    }

    public void dispose() {
        this.data.clear();
    }

    public static WaveData create(URL uRL) {
        try {
            return WaveData.create(AudioSystem.getAudioInputStream(new BufferedInputStream(uRL.openStream())));
        }
        catch (Exception exception) {
            LWJGLUtil.log((CharSequence)("Unable to create from: " + uRL));
            exception.printStackTrace();
            return null;
        }
    }

    public static WaveData create(String string) {
        return WaveData.create(WaveData.class.getClassLoader().getResource(string));
    }

    public static WaveData create(InputStream inputStream) {
        try {
            return WaveData.create(AudioSystem.getAudioInputStream(inputStream));
        }
        catch (Exception exception) {
            LWJGLUtil.log((CharSequence)"Unable to create from inputstream");
            exception.printStackTrace();
            return null;
        }
    }

    public static WaveData create(byte[] byArray) {
        try {
            return WaveData.create(AudioSystem.getAudioInputStream(new BufferedInputStream(new ByteArrayInputStream(byArray))));
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static WaveData create(ByteBuffer byteBuffer) {
        try {
            byte[] byArray = null;
            if (byteBuffer.hasArray()) {
                byArray = byteBuffer.array();
            } else {
                byArray = new byte[byteBuffer.capacity()];
                byteBuffer.get(byArray);
            }
            return WaveData.create(byArray);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static WaveData create(AudioInputStream audioInputStream) {
        AudioFormat audioFormat = audioInputStream.getFormat();
        int n = 0;
        if (audioFormat.getChannels() == 1) {
            if (audioFormat.getSampleSizeInBits() == 8) {
                n = 4352;
            } else {
                if (audioFormat.getSampleSizeInBits() != 16) throw new RuntimeException("Illegal sample size");
                n = 4353;
            }
        } else {
            if (audioFormat.getChannels() != 2) throw new RuntimeException("Only mono or stereo is supported");
            if (audioFormat.getSampleSizeInBits() == 8) {
                n = 4354;
            } else {
                if (audioFormat.getSampleSizeInBits() != 16) throw new RuntimeException("Illegal sample size");
                n = 4355;
            }
        }
        byte[] byArray = new byte[audioFormat.getChannels() * (int)audioInputStream.getFrameLength() * audioFormat.getSampleSizeInBits() / 8];
        int n2 = 0;
        try {
            for (int i = 0; (n2 = audioInputStream.read(byArray, i, byArray.length - i)) != -1 && i < byArray.length; i += n2) {
            }
        }
        catch (IOException iOException) {
            return null;
        }
        ByteBuffer byteBuffer = WaveData.convertAudioBytes(byArray, audioFormat.getSampleSizeInBits() == 16);
        WaveData waveData = new WaveData(byteBuffer, n, (int)audioFormat.getSampleRate());
        try {
            audioInputStream.close();
            return waveData;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return waveData;
    }

    private static ByteBuffer convertAudioBytes(byte[] byArray, boolean bl) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(byArray.length);
        byteBuffer.order(ByteOrder.nativeOrder());
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(byArray);
        byteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
        if (bl) {
            ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
            ShortBuffer shortBuffer2 = byteBuffer2.asShortBuffer();
            while (shortBuffer2.hasRemaining()) {
                shortBuffer.put(shortBuffer2.get());
            }
        } else {
            while (byteBuffer2.hasRemaining()) {
                byteBuffer.put(byteBuffer2.get());
            }
        }
        byteBuffer.rewind();
        return byteBuffer;
    }
}

