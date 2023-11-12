/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;

public class BufferedImageUtil {
    public static Texture getTexture(String string, BufferedImage bufferedImage) throws IOException {
        Texture texture = BufferedImageUtil.getTexture(string, bufferedImage, 3553, 6408, 9729, 9729);
        return texture;
    }

    public static Texture getTexture(String string, BufferedImage bufferedImage, int n) throws IOException {
        Texture texture = BufferedImageUtil.getTexture(string, bufferedImage, 3553, 6408, n, n);
        return texture;
    }

    public static Texture getTexture(String string, BufferedImage bufferedImage, int n, int n2, int n3, int n4) throws IOException {
        ImageIOImageData imageIOImageData = new ImageIOImageData();
        int n5 = 0;
        int n6 = InternalTextureLoader.createTextureID();
        TextureImpl textureImpl = new TextureImpl(string, n, n6);
        Renderer.get().glEnable(3553);
        Renderer.get().glBindTexture(n, n6);
        BufferedImage bufferedImage2 = bufferedImage;
        textureImpl.setWidth(bufferedImage2.getWidth());
        textureImpl.setHeight(bufferedImage2.getHeight());
        n5 = bufferedImage2.getColorModel().hasAlpha() ? 6408 : 6407;
        ByteBuffer byteBuffer = imageIOImageData.imageToByteBuffer(bufferedImage2, false, false, null);
        textureImpl.setTextureHeight(imageIOImageData.getTexHeight());
        textureImpl.setTextureWidth(imageIOImageData.getTexWidth());
        textureImpl.setAlpha(imageIOImageData.getDepth() == 32);
        if (n == 3553) {
            Renderer.get().glTexParameteri(n, 10241, n3);
            Renderer.get().glTexParameteri(n, 10240, n4);
            if (Renderer.get().canTextureMirrorClamp()) {
                Renderer.get().glTexParameteri(3553, 10242, 34627);
                Renderer.get().glTexParameteri(3553, 10243, 34627);
            } else {
                Renderer.get().glTexParameteri(3553, 10242, 10496);
                Renderer.get().glTexParameteri(3553, 10243, 10496);
            }
        }
        Renderer.get().glTexImage2D(n, 0, n2, textureImpl.getTextureWidth(), textureImpl.getTextureHeight(), 0, n5, 5121, byteBuffer);
        return textureImpl;
    }

    private static void copyArea(BufferedImage bufferedImage, int n, int n2, int n3, int n4, int n5, int n6) {
        Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.drawImage((Image)bufferedImage.getSubimage(n, n2, n3, n4), n + n5, n2 + n6, null);
    }
}

