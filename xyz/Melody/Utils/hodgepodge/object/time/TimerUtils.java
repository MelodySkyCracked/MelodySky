/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.object.time;

public final class TimerUtils {
    private final boolean autoReset;
    private long lastTime;

    public TimerUtils() {
        this.autoReset = false;
    }

    public TimerUtils(boolean bl) {
        this.autoReset = bl;
    }

    public boolean hasReached(double d) {
        boolean bl;
        boolean bl2 = bl = (double)(this.getCurrentMS() - this.lastTime) >= d;
        if (this.autoReset && bl) {
            this.reset();
            return true;
        }
        return bl;
    }

    public final long getElapsedTime() {
        return this.getCurrentMS() - this.lastTime;
    }

    public void reset() {
        this.lastTime = this.getCurrentMS();
    }

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
}

