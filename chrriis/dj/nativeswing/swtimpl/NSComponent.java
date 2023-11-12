/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl;

public interface NSComponent {
    public void initializeNativePeer();

    public void disposeNativePeer();

    public boolean isNativePeerDisposed();

    public boolean isNativePeerInitialized();

    public boolean isNativePeerValid();

    public void runInSequence(Runnable var1);
}

