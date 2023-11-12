/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

class CLayoutData {
    int defaultWidth = -1;
    int defaultHeight = -1;
    int currentWhint;
    int currentHhint;
    int currentWidth = -1;
    int currentHeight = -1;

    CLayoutData() {
    }

    Point computeSize(Control control, int n, int n2, boolean bl) {
        if (bl) {
            this.flushCache();
        }
        if (n == -1 && n2 == -1) {
            if (this.defaultWidth == -1 || this.defaultHeight == -1) {
                Point point = control.computeSize(n, n2, bl);
                this.defaultWidth = point.x;
                this.defaultHeight = point.y;
            }
            return new Point(this.defaultWidth, this.defaultHeight);
        }
        if (this.currentWidth == -1 || this.currentHeight == -1 || n != this.currentWhint || n2 != this.currentHhint) {
            Point point = control.computeSize(n, n2, bl);
            this.currentWhint = n;
            this.currentHhint = n2;
            this.currentWidth = point.x;
            this.currentHeight = point.y;
        }
        return new Point(this.currentWidth, this.currentHeight);
    }

    void flushCache() {
        int n = -1;
        this.defaultHeight = -1;
        this.defaultWidth = -1;
        int n2 = -1;
        this.currentHeight = -1;
        this.currentWidth = -1;
    }
}

