/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.object;

import java.util.List;
import java.util.function.Consumer;
import xyz.Melody.Utils.hodgepodge.object.time.TimerUtils;

public final class DelayList {
    private final TimerUtils timerUtils = new TimerUtils(true);
    private final List currentList;
    private int index;

    public DelayList(List list) {
        this.currentList = list;
    }

    public void forEach(long l2, Consumer consumer) {
        if (l2 < 0L) {
            throw new IllegalArgumentException("Delay cannot be negative");
        }
        while (this.index < this.currentList.size()) {
            this.implement(l2, consumer);
        }
        this.reset();
    }

    public void forEachNoStoppage(int n, Consumer consumer) {
        if (n < 0) {
            throw new IllegalArgumentException("Delay cannot be negative");
        }
        if (this.index < this.currentList.size()) {
            this.implement(n, consumer);
        } else {
            this.reset();
        }
    }

    private void implement(long l2, Consumer consumer) {
        if (l2 == 0L) {
            consumer.accept(this.currentList.get(this.index));
            ++this.index;
        } else if (this.timerUtils.hasReached(l2)) {
            consumer.accept(this.currentList.get(this.index));
            ++this.index;
        }
    }

    public void reset() {
        this.index = 0;
    }

    public List getCurrentList() {
        return this.currentList;
    }
}

