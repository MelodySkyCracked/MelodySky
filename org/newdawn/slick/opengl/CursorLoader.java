/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.input.Cursor
 */
package org.newdawn.slick.opengl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.ImageDataFactory;
import org.newdawn.slick.opengl.LoadableImageData;
import org.newdawn.slick.opengl.TGAImageData;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class CursorLoader {
    private static CursorLoader single = new CursorLoader();

    public static CursorLoader get() {
        return single;
    }

    private CursorLoader() {
    }

    public Cursor getCursor(String string, int n, int n2) throws IOException, LWJGLException {
        int n3;
        LoadableImageData loadableImageData = null;
        loadableImageData = ImageDataFactory.getImageDataFor(string);
        loadableImageData.configureEdging(false);
        ByteBuffer byteBuffer = loadableImageData.loadImage(ResourceLoader.getResourceAsStream(string), true, true, null);
        for (n3 = 0; n3 < byteBuffer.limit(); n3 += 4) {
            byte by = byteBuffer.get(n3);
            byte by2 = byteBuffer.get(n3 + 1);
            byte by3 = byteBuffer.get(n3 + 2);
            byte by4 = byteBuffer.get(n3 + 3);
            byteBuffer.put(n3 + 2, by);
            byteBuffer.put(n3 + 1, by2);
            byteBuffer.put(n3, by3);
            byteBuffer.put(n3 + 3, by4);
        }
        try {
            n3 = loadableImageData.getHeight() - n2 - 1;
            if (n3 < 0) {
                n3 = 0;
            }
            return new Cursor(loadableImageData.getTexWidth(), loadableImageData.getTexHeight(), n, n3, 1, byteBuffer.asIntBuffer(), null);
        }
        catch (Throwable throwable) {
            Log.info("Chances are you cursor is too small for this platform");
            throw new LWJGLException(throwable);
        }
    }

    public Cursor getCursor(ByteBuffer byteBuffer, int n, int n2, int n3, int n4) throws IOException, LWJGLException {
        int n5;
        for (n5 = 0; n5 < byteBuffer.limit(); n5 += 4) {
            byte by = byteBuffer.get(n5);
            byte by2 = byteBuffer.get(n5 + 1);
            byte by3 = byteBuffer.get(n5 + 2);
            byte by4 = byteBuffer.get(n5 + 3);
            byteBuffer.put(n5 + 2, by);
            byteBuffer.put(n5 + 1, by2);
            byteBuffer.put(n5, by3);
            byteBuffer.put(n5 + 3, by4);
        }
        try {
            n5 = n4 - n2 - 1;
            if (n5 < 0) {
                n5 = 0;
            }
            return new Cursor(n3, n4, n, n5, 1, byteBuffer.asIntBuffer(), null);
        }
        catch (Throwable throwable) {
            Log.info("Chances are you cursor is too small for this platform");
            throw new LWJGLException(throwable);
        }
    }

    public Cursor getCursor(ImageData imageData, int n, int n2) throws IOException, LWJGLException {
        int n3;
        ByteBuffer byteBuffer = imageData.getImageBufferData();
        for (n3 = 0; n3 < byteBuffer.limit(); n3 += 4) {
            byte by = byteBuffer.get(n3);
            byte by2 = byteBuffer.get(n3 + 1);
            byte by3 = byteBuffer.get(n3 + 2);
            byte by4 = byteBuffer.get(n3 + 3);
            byteBuffer.put(n3 + 2, by);
            byteBuffer.put(n3 + 1, by2);
            byteBuffer.put(n3, by3);
            byteBuffer.put(n3 + 3, by4);
        }
        try {
            n3 = imageData.getHeight() - n2 - 1;
            if (n3 < 0) {
                n3 = 0;
            }
            return new Cursor(imageData.getTexWidth(), imageData.getTexHeight(), n, n3, 1, byteBuffer.asIntBuffer(), null);
        }
        catch (Throwable throwable) {
            Log.info("Chances are you cursor is too small for this platform");
            throw new LWJGLException(throwable);
        }
    }

    public Cursor getAnimatedCursor(String string, int n, int n2, int n3, int n4, int[] nArray) throws IOException, LWJGLException {
        IntBuffer intBuffer = ByteBuffer.allocateDirect(nArray.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        for (int i = 0; i < nArray.length; ++i) {
            intBuffer.put(nArray[i]);
        }
        intBuffer.flip();
        TGAImageData tGAImageData = new TGAImageData();
        ByteBuffer byteBuffer = tGAImageData.loadImage(ResourceLoader.getResourceAsStream(string), false, null);
        return new Cursor(n3, n4, n, n2, nArray.length, byteBuffer.asIntBuffer(), intBuffer);
    }
}

