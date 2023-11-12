/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.pathfinding.Lib;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import xyz.Melody.Utils.pathfinding.Lib.PathPos;

public class PathQueue {
    private final PriorityQueue queue = new PriorityQueue<Entry>(Comparator.comparing(PathQueue::lambda$new$0));

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public boolean add(PathPos pathPos, float f) {
        return this.queue.add(new Entry(pathPos, f));
    }

    public PathPos[] toArray() {
        PathPos[] pathPosArray = new PathPos[this.size()];
        Iterator iterator = this.queue.iterator();
        for (int i = 0; i < this.size() && iterator.hasNext(); ++i) {
            pathPosArray[i] = Entry.access$000((Entry)iterator.next());
        }
        return pathPosArray;
    }

    public int size() {
        return this.queue.size();
    }

    public void clear() {
        this.queue.clear();
    }

    public PathPos poll() {
        return Entry.access$000((Entry)this.queue.poll());
    }

    private static Float lambda$new$0(Entry entry) {
        return Float.valueOf(Entry.access$100(entry));
    }

    private static class Entry {
        private PathPos pos;
        private float priority;

        public Entry(PathPos pathPos, float f) {
            this.pos = pathPos;
            this.priority = f;
        }

        static PathPos access$000(Entry entry) {
            return entry.pos;
        }

        static float access$100(Entry entry) {
            return entry.priority;
        }
    }
}

