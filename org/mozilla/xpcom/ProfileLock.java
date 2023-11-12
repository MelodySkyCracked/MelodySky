/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.xpcom;

public class ProfileLock {
    private long lock = 0L;

    public ProfileLock(long l2) {
        this.lock = l2;
    }

    public void release() {
        this.releaseNative(this.lock);
        this.lock = 0L;
    }

    private native void releaseNative(long var1);

    public boolean isValid() {
        return this.lock != 0L;
    }

    protected void finalize() throws Throwable {
        this.release();
        super.finalize();
    }
}

