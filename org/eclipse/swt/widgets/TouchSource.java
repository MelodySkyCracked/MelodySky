/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Rectangle;

public final class TouchSource {
    long handle;
    boolean direct;
    Rectangle bounds;

    TouchSource(long l2, boolean bl, Rectangle rectangle) {
        this.handle = l2;
        this.direct = bl;
        this.bounds = rectangle;
    }

    public boolean isDirect() {
        return this.direct;
    }

    public Rectangle getBounds() {
        return new Rectangle(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
    }

    public String toString() {
        return "TouchSource {handle=" + this.handle + " direct=" + this.direct + " bounds=" + this.bounds;
    }
}

