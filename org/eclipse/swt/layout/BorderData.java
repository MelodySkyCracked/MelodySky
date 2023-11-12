/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.layout;

import java.util.IdentityHashMap;
import java.util.Map;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

public final class BorderData {
    private final Map cachedSize = new IdentityHashMap(1);
    public int hHint = -1;
    public int wHint = -1;
    public int region = 0x1000000;

    public BorderData() {
    }

    public BorderData(int n) {
        this.region = n;
    }

    public BorderData(int n, int n2, int n3) {
        this.region = n;
        this.wHint = n2;
        this.hHint = n3;
    }

    Point getSize(Control control) {
        return this.cachedSize.computeIfAbsent(control, this::lambda$getSize$0);
    }

    Point computeSize(Control control, int n, int n2, boolean bl) {
        if (n == -1) {
            n = this.wHint;
        }
        if (n2 == -1) {
            n2 = this.hHint;
        }
        return control.computeSize(n, n2, bl);
    }

    void flushCache(Control control) {
        this.cachedSize.remove(control);
    }

    public String toString() {
        return "BorderData [region=" + BorderData.getRegionString(this.region) + ", hHint=" + this.hHint + ", wHint=" + this.wHint;
    }

    static String getRegionString(int n) {
        switch (n) {
            case 128: {
                return "SWT.TOP";
            }
            case 131072: {
                return "SWT.RIGHT";
            }
            case 1024: {
                return "SWT.BOTTOM";
            }
            case 16384: {
                return "SWT.LEFT";
            }
            case 0x1000000: {
                return "SWT.CENTER";
            }
        }
        return "SWT.NONE";
    }

    int getRegion() {
        switch (this.region) {
            case 128: 
            case 1024: 
            case 16384: 
            case 131072: 
            case 0x1000000: {
                return this.region;
            }
        }
        return 0;
    }

    private Point lambda$getSize$0(Control control) {
        return control.computeSize(this.wHint, this.hHint, true);
    }
}

