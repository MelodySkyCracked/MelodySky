/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util.pathfinding;

import java.io.Serializable;
import java.util.ArrayList;

public class Path
implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList steps = new ArrayList();

    public int getLength() {
        return this.steps.size();
    }

    public Step getStep(int n) {
        return (Step)this.steps.get(n);
    }

    public int getX(int n) {
        return Step.access$000(this.getStep(n));
    }

    public int getY(int n) {
        return Step.access$100(this.getStep(n));
    }

    public void appendStep(int n, int n2) {
        this.steps.add(new Step(this, n, n2));
    }

    public void prependStep(int n, int n2) {
        this.steps.add(0, new Step(this, n, n2));
    }

    public boolean contains(int n, int n2) {
        return this.steps.contains(new Step(this, n, n2));
    }

    public class Step
    implements Serializable {
        private int x;
        private int y;
        final Path this$0;

        public Step(Path path, int n, int n2) {
            this.this$0 = path;
            this.x = n;
            this.y = n2;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int hashCode() {
            return this.x * this.y;
        }

        public boolean equals(Object object) {
            if (object instanceof Step) {
                Step step = (Step)object;
                return step.x == this.x && step.y == this.y;
            }
            return false;
        }

        static int access$000(Step step) {
            return step.x;
        }

        static int access$100(Step step) {
            return step.y;
        }
    }
}

