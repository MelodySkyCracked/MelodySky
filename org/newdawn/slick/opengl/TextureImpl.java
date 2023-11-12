/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 */
package org.newdawn.slick.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.l;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;

public class TextureImpl
implements Texture {
    protected static SGL GL = Renderer.get();
    static Texture lastBind;
    private int target;
    private int textureID;
    private int height;
    private int width;
    private int texWidth;
    private int texHeight;
    private float widthRatio;
    private float heightRatio;
    private boolean alpha;
    private String ref;
    private String cacheName;
    private ReloadData reloadData;

    public static Texture getLastBind() {
        return lastBind;
    }

    protected TextureImpl() {
    }

    public TextureImpl(String string, int n, int n2) {
        this.target = n;
        this.ref = string;
        this.textureID = n2;
        lastBind = this;
    }

    public void setCacheName(String string) {
        this.cacheName = string;
    }

    @Override
    public boolean hasAlpha() {
        return this.alpha;
    }

    @Override
    public String getTextureRef() {
        return this.ref;
    }

    public void setAlpha(boolean bl) {
        this.alpha = bl;
    }

    public static void bindNone() {
        lastBind = null;
        GL.glDisable(3553);
    }

    public static void unbind() {
        lastBind = null;
    }

    @Override
    public void bind() {
        if (lastBind != this) {
            lastBind = this;
            GL.glEnable(3553);
            GL.glBindTexture(this.target, this.textureID);
        }
    }

    public void setHeight(int n) {
        this.height = n;
        this.setHeight();
    }

    public void setWidth(int n) {
        this.width = n;
        this.setWidth();
    }

    @Override
    public int getImageHeight() {
        return this.height;
    }

    @Override
    public int getImageWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.heightRatio;
    }

    @Override
    public float getWidth() {
        return this.widthRatio;
    }

    @Override
    public int getTextureHeight() {
        return this.texHeight;
    }

    @Override
    public int getTextureWidth() {
        return this.texWidth;
    }

    public void setTextureHeight(int n) {
        this.texHeight = n;
        this.setHeight();
    }

    public void setTextureWidth(int n) {
        this.texWidth = n;
        this.setWidth();
    }

    private void setHeight() {
        if (this.texHeight != 0) {
            this.heightRatio = (float)this.height / (float)this.texHeight;
        }
    }

    private void setWidth() {
        if (this.texWidth != 0) {
            this.widthRatio = (float)this.width / (float)this.texWidth;
        }
    }

    @Override
    public void release() {
        IntBuffer intBuffer = this.createIntBuffer(1);
        intBuffer.put(this.textureID);
        intBuffer.flip();
        GL.glDeleteTextures(intBuffer);
        if (lastBind == this) {
            TextureImpl.bindNone();
        }
        if (this.cacheName != null) {
            InternalTextureLoader.get().clear(this.cacheName);
        } else {
            InternalTextureLoader.get().clear(this.ref);
        }
    }

    @Override
    public int getTextureID() {
        return this.textureID;
    }

    public void setTextureID(int n) {
        this.textureID = n;
    }

    protected IntBuffer createIntBuffer(int n) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * n);
        byteBuffer.order(ByteOrder.nativeOrder());
        return byteBuffer.asIntBuffer();
    }

    @Override
    public byte[] getTextureData() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer((int)((this.hasAlpha() ? 4 : 3) * this.texWidth * this.texHeight));
        this.bind();
        GL.glGetTexImage(3553, 0, this.hasAlpha() ? 6408 : 6407, 5121, byteBuffer);
        byte[] byArray = new byte[byteBuffer.limit()];
        byteBuffer.get(byArray);
        byteBuffer.clear();
        return byArray;
    }

    @Override
    public void setTextureFilter(int n) {
        this.bind();
        GL.glTexParameteri(this.target, 10241, n);
        GL.glTexParameteri(this.target, 10240, n);
    }

    public void setTextureData(int n, int n2, int n3, int n4, ByteBuffer byteBuffer) {
        this.reloadData = new ReloadData(this, null);
        ReloadData.access$102(this.reloadData, n);
        ReloadData.access$202(this.reloadData, n2);
        ReloadData.access$302(this.reloadData, n3);
        ReloadData.access$402(this.reloadData, n4);
        ReloadData.access$502(this.reloadData, byteBuffer);
    }

    public void reload() {
        if (this.reloadData != null) {
            this.textureID = this.reloadData.reload();
        }
    }

    static String access$600(TextureImpl textureImpl) {
        return textureImpl.ref;
    }

    private class ReloadData {
        private int srcPixelFormat;
        private int componentCount;
        private int minFilter;
        private int magFilter;
        private ByteBuffer textureBuffer;
        final TextureImpl this$0;

        private ReloadData(TextureImpl textureImpl) {
            this.this$0 = textureImpl;
        }

        public int reload() {
            Log.error("Reloading texture: " + TextureImpl.access$600(this.this$0));
            return InternalTextureLoader.get().reload(this.this$0, this.srcPixelFormat, this.componentCount, this.minFilter, this.magFilter, this.textureBuffer);
        }

        ReloadData(TextureImpl textureImpl, l l2) {
            this(textureImpl);
        }

        static int access$102(ReloadData reloadData, int n) {
            reloadData.srcPixelFormat = n;
            return reloadData.srcPixelFormat;
        }

        static int access$202(ReloadData reloadData, int n) {
            reloadData.componentCount = n;
            return reloadData.componentCount;
        }

        static int access$302(ReloadData reloadData, int n) {
            reloadData.minFilter = n;
            return reloadData.minFilter;
        }

        static int access$402(ReloadData reloadData, int n) {
            reloadData.magFilter = n;
            return reloadData.magFilter;
        }

        static ByteBuffer access$502(ReloadData reloadData, ByteBuffer byteBuffer) {
            reloadData.textureBuffer = byteBuffer;
            return reloadData.textureBuffer;
        }
    }
}

