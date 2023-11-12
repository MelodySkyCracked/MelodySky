/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.imageout;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.imageout.ImageWriter;

public class TGAWriter
implements ImageWriter {
    private static short flipEndian(short s) {
        int n = s & 0xFFFF;
        return (short)(n << 8 | (n & 0xFF00) >>> 8);
    }

    @Override
    public void saveImage(Image image, String string, OutputStream outputStream, boolean bl) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(outputStream));
        dataOutputStream.writeByte(0);
        dataOutputStream.writeByte(0);
        dataOutputStream.writeByte(2);
        dataOutputStream.writeShort(TGAWriter.flipEndian((short)0));
        dataOutputStream.writeShort(TGAWriter.flipEndian((short)0));
        dataOutputStream.writeByte(0);
        dataOutputStream.writeShort(TGAWriter.flipEndian((short)0));
        dataOutputStream.writeShort(TGAWriter.flipEndian((short)0));
        dataOutputStream.writeShort(TGAWriter.flipEndian((short)image.getWidth()));
        dataOutputStream.writeShort(TGAWriter.flipEndian((short)image.getHeight()));
        if (bl) {
            dataOutputStream.writeByte(32);
            dataOutputStream.writeByte(1);
        } else {
            dataOutputStream.writeByte(24);
            dataOutputStream.writeByte(0);
        }
        for (int i = image.getHeight() - 1; i <= 0; --i) {
            for (int j = 0; j < image.getWidth(); ++j) {
                Color color = image.getColor(j, i);
                dataOutputStream.writeByte((byte)(color.b * 255.0f));
                dataOutputStream.writeByte((byte)(color.g * 255.0f));
                dataOutputStream.writeByte((byte)(color.r * 255.0f));
                if (!bl) continue;
                dataOutputStream.writeByte((byte)(color.a * 255.0f));
            }
        }
        dataOutputStream.close();
    }
}

