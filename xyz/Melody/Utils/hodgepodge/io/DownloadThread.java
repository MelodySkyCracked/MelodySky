/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.io;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import xyz.Melody.Utils.hodgepodge.function.CatchHandler;
import xyz.Melody.Utils.hodgepodge.function.VoidFunction;

public final class DownloadThread
extends Thread
implements Closeable {
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private final BufferedOutputStream bos = new BufferedOutputStream(this.byteArrayOutputStream);
    private final CatchHandler catchHandler;
    private final byte[] buffer;
    private final InputStream inputStream;
    private VoidFunction endingHandler;
    private int weAreWhere;
    private boolean stop = false;

    public DownloadThread(URL uRL, byte[] byArray, CatchHandler catchHandler) throws IOException {
        this.catchHandler = catchHandler;
        this.buffer = byArray;
        this.inputStream = uRL.openStream();
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (this.stop) {
                    continue;
                }
                int n = this.inputStream.read(this.buffer);
                if (n != -1) {
                    this.bos.write(this.buffer, 0, n);
                    this.bos.flush();
                    this.weAreWhere += n;
                    continue;
                }
                break;
            }
        }
        catch (IOException iOException) {
            this.catchHandler.onCatchException(iOException);
        }
        if (this.endingHandler != null) {
            this.endingHandler.handle();
        }
    }

    public byte[] getDownloadedByteArray() {
        return this.byteArrayOutputStream.toByteArray();
    }

    public int getWeAreWhere() {
        return this.weAreWhere;
    }

    public void setStop(boolean bl) {
        this.stop = bl;
    }

    public void setEndingHandler(VoidFunction voidFunction) {
        this.endingHandler = voidFunction;
    }

    public void reset() {
        this.byteArrayOutputStream.reset();
    }

    @Override
    public void close() throws IOException {
        this.inputStream.close();
        this.bos.close();
        this.byteArrayOutputStream.close();
    }
}

