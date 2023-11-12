/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 */
package org.newdawn.slick.opengl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.DeferredTexture;
import org.newdawn.slick.opengl.EmptyImageData;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.ImageDataFactory;
import org.newdawn.slick.opengl.LoadableImageData;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.ResourceLoader;

public class InternalTextureLoader {
    protected static SGL GL = Renderer.get();
    private static final InternalTextureLoader loader = new InternalTextureLoader();
    private HashMap texturesLinear = new HashMap();
    private HashMap texturesNearest = new HashMap();
    private int dstPixelFormat = 6408;
    private boolean deferred;
    private boolean holdTextureData;

    public static InternalTextureLoader get() {
        return loader;
    }

    private InternalTextureLoader() {
    }

    public void setHoldTextureData(boolean bl) {
        this.holdTextureData = bl;
    }

    public void setDeferredLoading(boolean bl) {
        this.deferred = bl;
    }

    public boolean isDeferredLoading() {
        return this.deferred;
    }

    public void clear(String string) {
        this.texturesLinear.remove(string);
        this.texturesNearest.remove(string);
    }

    public void clear() {
        this.texturesLinear.clear();
        this.texturesNearest.clear();
    }

    public void set16BitMode() {
        this.dstPixelFormat = 32859;
    }

    public static int createTextureID() {
        IntBuffer intBuffer = InternalTextureLoader.createIntBuffer(1);
        GL.glGenTextures(intBuffer);
        return intBuffer.get(0);
    }

    public Texture getTexture(File file, boolean bl, int n) throws IOException {
        String string = file.getAbsolutePath();
        FileInputStream fileInputStream = new FileInputStream(file);
        return this.getTexture(fileInputStream, string, bl, n, null);
    }

    public Texture getTexture(File file, boolean bl, int n, int[] nArray) throws IOException {
        String string = file.getAbsolutePath();
        FileInputStream fileInputStream = new FileInputStream(file);
        return this.getTexture(fileInputStream, string, bl, n, nArray);
    }

    public Texture getTexture(String string, boolean bl, int n) throws IOException {
        InputStream inputStream = ResourceLoader.getResourceAsStream(string);
        return this.getTexture(inputStream, string, bl, n, null);
    }

    public Texture getTexture(String string, boolean bl, int n, int[] nArray) throws IOException {
        InputStream inputStream = ResourceLoader.getResourceAsStream(string);
        return this.getTexture(inputStream, string, bl, n, nArray);
    }

    public Texture getTexture(InputStream inputStream, String string, boolean bl, int n) throws IOException {
        return this.getTexture(inputStream, string, bl, n, null);
    }

    public TextureImpl getTexture(InputStream inputStream, String string, boolean bl, int n, int[] nArray) throws IOException {
        Object object;
        if (this.deferred) {
            return new DeferredTexture(inputStream, string, bl, n, nArray);
        }
        HashMap hashMap = this.texturesLinear;
        if (n == 9728) {
            hashMap = this.texturesNearest;
        }
        String string2 = string;
        if (nArray != null) {
            string2 = string2 + ":" + nArray[0] + ":" + nArray[1] + ":" + nArray[2];
        }
        string2 = string2 + ":" + bl;
        if (this.holdTextureData) {
            object = (TextureImpl)hashMap.get(string2);
            if (object != null) {
                return object;
            }
        } else {
            object = (SoftReference)hashMap.get(string2);
            if (object != null) {
                TextureImpl textureImpl = (TextureImpl)((SoftReference)object).get();
                if (textureImpl != null) {
                    return textureImpl;
                }
                hashMap.remove(string2);
            }
        }
        try {
            GL.glGetError();
        }
        catch (NullPointerException nullPointerException) {
            throw new RuntimeException("Image based resources must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
        }
        object = this.getTexture(inputStream, string, 3553, n, n, bl, nArray);
        ((TextureImpl)object).setCacheName(string2);
        if (this.holdTextureData) {
            hashMap.put(string2, object);
        } else {
            hashMap.put(string2, new SoftReference<Object>(object));
        }
        return object;
    }

    private TextureImpl getTexture(InputStream inputStream, String string, int n, int n2, int n3, boolean bl, int[] nArray) throws IOException {
        LoadableImageData loadableImageData = ImageDataFactory.getImageDataFor(string);
        ByteBuffer byteBuffer = loadableImageData.loadImage(new BufferedInputStream(inputStream), bl, nArray);
        int n4 = InternalTextureLoader.createTextureID();
        TextureImpl textureImpl = new TextureImpl(string, n, n4);
        GL.glBindTexture(n, n4);
        int n5 = loadableImageData.getWidth();
        int n6 = loadableImageData.getHeight();
        boolean bl2 = loadableImageData.getDepth() == 32;
        textureImpl.setTextureWidth(loadableImageData.getTexWidth());
        textureImpl.setTextureHeight(loadableImageData.getTexHeight());
        int n7 = textureImpl.getTextureWidth();
        int n8 = textureImpl.getTextureHeight();
        IntBuffer intBuffer = BufferUtils.createIntBuffer((int)16);
        GL.glGetInteger(3379, intBuffer);
        int n9 = intBuffer.get(0);
        if (n7 > n9 || n8 > n9) {
            throw new IOException("Attempt to allocate a texture to big for the current hardware");
        }
        int n10 = bl2 ? 6408 : 6407;
        int n11 = bl2 ? 4 : 3;
        textureImpl.setWidth(n5);
        textureImpl.setHeight(n6);
        textureImpl.setAlpha(bl2);
        if (this.holdTextureData) {
            textureImpl.setTextureData(n10, n11, n3, n2, byteBuffer);
        }
        GL.glTexParameteri(n, 10241, n3);
        GL.glTexParameteri(n, 10240, n2);
        GL.glTexImage2D(n, 0, this.dstPixelFormat, InternalTextureLoader.get2Fold(n5), InternalTextureLoader.get2Fold(n6), 0, n10, 5121, byteBuffer);
        return textureImpl;
    }

    public Texture createTexture(int n, int n2) throws IOException {
        return this.createTexture(n, n2, 9728);
    }

    public Texture createTexture(int n, int n2, int n3) throws IOException {
        EmptyImageData emptyImageData = new EmptyImageData(n, n2);
        return this.getTexture(emptyImageData, n3);
    }

    public Texture getTexture(ImageData imageData, int n) throws IOException {
        int n2 = 3553;
        ByteBuffer byteBuffer = imageData.getImageBufferData();
        int n3 = InternalTextureLoader.createTextureID();
        TextureImpl textureImpl = new TextureImpl("generated:" + imageData, n2, n3);
        int n4 = n;
        int n5 = n;
        boolean bl = false;
        GL.glBindTexture(n2, n3);
        int n6 = imageData.getWidth();
        int n7 = imageData.getHeight();
        boolean bl2 = imageData.getDepth() == 32;
        textureImpl.setTextureWidth(imageData.getTexWidth());
        textureImpl.setTextureHeight(imageData.getTexHeight());
        int n8 = textureImpl.getTextureWidth();
        int n9 = textureImpl.getTextureHeight();
        int n10 = bl2 ? 6408 : 6407;
        int n11 = bl2 ? 4 : 3;
        textureImpl.setWidth(n6);
        textureImpl.setHeight(n7);
        textureImpl.setAlpha(bl2);
        IntBuffer intBuffer = BufferUtils.createIntBuffer((int)16);
        GL.glGetInteger(3379, intBuffer);
        int n12 = intBuffer.get(0);
        if (n8 > n12 || n9 > n12) {
            throw new IOException("Attempt to allocate a texture to big for the current hardware");
        }
        if (this.holdTextureData) {
            textureImpl.setTextureData(n10, n11, n4, n5, byteBuffer);
        }
        GL.glTexParameteri(n2, 10241, n4);
        GL.glTexParameteri(n2, 10240, n5);
        GL.glTexImage2D(n2, 0, this.dstPixelFormat, InternalTextureLoader.get2Fold(n6), InternalTextureLoader.get2Fold(n7), 0, n10, 5121, byteBuffer);
        return textureImpl;
    }

    public static int get2Fold(int n) {
        int n2;
        for (n2 = 2; n2 < n; n2 *= 2) {
        }
        return n2;
    }

    public static IntBuffer createIntBuffer(int n) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * n);
        byteBuffer.order(ByteOrder.nativeOrder());
        return byteBuffer.asIntBuffer();
    }

    public void reload() {
        Iterator iterator = this.texturesLinear.values().iterator();
        while (iterator.hasNext()) {
            ((TextureImpl)iterator.next()).reload();
        }
        iterator = this.texturesNearest.values().iterator();
        while (iterator.hasNext()) {
            ((TextureImpl)iterator.next()).reload();
        }
    }

    public int reload(TextureImpl textureImpl, int n, int n2, int n3, int n4, ByteBuffer byteBuffer) {
        int n5 = 3553;
        int n6 = InternalTextureLoader.createTextureID();
        GL.glBindTexture(n5, n6);
        GL.glTexParameteri(n5, 10241, n3);
        GL.glTexParameteri(n5, 10240, n4);
        GL.glTexImage2D(n5, 0, this.dstPixelFormat, textureImpl.getTextureWidth(), textureImpl.getTextureHeight(), 0, n, 5121, byteBuffer);
        return n6;
    }
}

