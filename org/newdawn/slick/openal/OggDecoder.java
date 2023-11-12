/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.openal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.newdawn.slick.openal.OggData;
import org.newdawn.slick.openal.OggInputStream;

public class OggDecoder {
    private int convsize = 16384;
    private byte[] convbuffer = new byte[this.convsize];

    public OggData getData(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IOException("Failed to read OGG, source does not exist?");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OggInputStream oggInputStream = new OggInputStream(inputStream);
        boolean bl = false;
        while (!oggInputStream.atEnd()) {
            byteArrayOutputStream.write(oggInputStream.read());
        }
        OggData oggData = new OggData();
        oggData.channels = oggInputStream.getChannels();
        oggData.rate = oggInputStream.getRate();
        byte[] byArray = byteArrayOutputStream.toByteArray();
        oggData.data = ByteBuffer.allocateDirect(byArray.length);
        oggData.data.put(byArray);
        oggData.data.rewind();
        return oggData;
    }
}

