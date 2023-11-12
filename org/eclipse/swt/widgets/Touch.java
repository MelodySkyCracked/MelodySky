/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.widgets.TouchSource;

public final class Touch {
    public long id;
    public TouchSource source;
    public int state;
    public boolean primary;
    public int x;
    public int y;

    Touch(long l2, TouchSource touchSource, int n, boolean bl, int n2, int n3) {
        this.id = l2;
        this.source = touchSource;
        this.state = n;
        this.primary = bl;
        this.x = n2;
        this.y = n3;
    }

    public String toString() {
        return "Touch {id=" + this.id + " source=" + this.source + " state=" + this.state + " primary=" + this.primary + " x=" + this.x + " y=" + this.y;
    }
}

