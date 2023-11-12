/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.opengl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.newdawn.slick.opengl.CompositeIOException;
import org.newdawn.slick.opengl.LoadableImageData;
import org.newdawn.slick.util.Log;

public class CompositeImageData
implements LoadableImageData {
    private ArrayList sources = new ArrayList();
    private LoadableImageData picked;

    public void add(LoadableImageData loadableImageData) {
        this.sources.add(loadableImageData);
    }

    @Override
    public ByteBuffer loadImage(InputStream inputStream) throws IOException {
        return this.loadImage(inputStream, false, null);
    }

    @Override
    public ByteBuffer loadImage(InputStream inputStream, boolean bl, int[] nArray) throws IOException {
        return this.loadImage(inputStream, bl, false, nArray);
    }

    @Override
    public ByteBuffer loadImage(InputStream inputStream, boolean bl, boolean bl2, int[] nArray) throws IOException {
        CompositeIOException compositeIOException = new CompositeIOException();
        ByteBuffer byteBuffer = null;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, inputStream.available());
        bufferedInputStream.mark(inputStream.available());
        for (int i = 0; i < this.sources.size(); ++i) {
            bufferedInputStream.reset();
            try {
                LoadableImageData loadableImageData = (LoadableImageData)this.sources.get(i);
                byteBuffer = loadableImageData.loadImage(bufferedInputStream, bl, bl2, nArray);
                this.picked = loadableImageData;
                break;
            }
            catch (Exception exception) {
                Log.warn(this.sources.get(i).getClass() + " failed to read the data", exception);
                compositeIOException.addException(exception);
                continue;
            }
        }
        if (this.picked == null) {
            throw compositeIOException;
        }
        return byteBuffer;
    }

    private void checkPicked() {
        if (this.picked == null) {
            throw new RuntimeException("Attempt to make use of uninitialised or invalid composite image data");
        }
    }

    @Override
    public int getDepth() {
        this.checkPicked();
        return this.picked.getDepth();
    }

    @Override
    public int getHeight() {
        this.checkPicked();
        return this.picked.getHeight();
    }

    @Override
    public ByteBuffer getImageBufferData() {
        this.checkPicked();
        return this.picked.getImageBufferData();
    }

    @Override
    public int getTexHeight() {
        this.checkPicked();
        return this.picked.getTexHeight();
    }

    @Override
    public int getTexWidth() {
        this.checkPicked();
        return this.picked.getTexWidth();
    }

    @Override
    public int getWidth() {
        this.checkPicked();
        return this.picked.getWidth();
    }

    @Override
    public void configureEdging(boolean bl) {
        for (int i = 0; i < this.sources.size(); ++i) {
            ((LoadableImageData)this.sources.get(i)).configureEdging(bl);
        }
    }
}

