/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.opengl;

import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;

public class TextureLoader {
    public static Texture getTexture(String string, InputStream inputStream) throws IOException {
        return TextureLoader.getTexture(string, inputStream, false, 9729);
    }

    public static Texture getTexture(String string, InputStream inputStream, boolean bl) throws IOException {
        return TextureLoader.getTexture(string, inputStream, bl, 9729);
    }

    public static Texture getTexture(String string, InputStream inputStream, int n) throws IOException {
        return TextureLoader.getTexture(string, inputStream, false, n);
    }

    public static Texture getTexture(String string, InputStream inputStream, boolean bl, int n) throws IOException {
        return InternalTextureLoader.get().getTexture(inputStream, inputStream.toString() + "." + string, bl, n);
    }
}

