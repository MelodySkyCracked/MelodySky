/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;

public final class RowData {
    public int width = -1;
    public int height = -1;
    public boolean exclude = false;

    public RowData() {
    }

    public RowData(int n, int n2) {
        this.width = n;
        this.height = n2;
    }

    public RowData(Point point) {
        this(point.x, point.y);
    }

    String getName() {
        String string = this.getClass().getName();
        int n = string.lastIndexOf(46);
        if (n == -1) {
            return string;
        }
        return string.substring(n + 1, string.length());
    }

    public String toString() {
        String string = this.getName() + " {";
        if (this.width != -1) {
            string = string + "width=" + this.width + " ";
        }
        if (this.height != -1) {
            string = string + "height=" + this.height + " ";
        }
        if (this.exclude) {
            string = string + "exclude=" + this.exclude + " ";
        }
        string = string.trim();
        string = string + "}";
        return string;
    }
}

