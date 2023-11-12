/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

public final class GridData {
    public int verticalAlignment = 2;
    public int horizontalAlignment = 1;
    public int widthHint = -1;
    public int heightHint = -1;
    public int horizontalIndent = 0;
    public int verticalIndent = 0;
    public int horizontalSpan = 1;
    public int verticalSpan = 1;
    public boolean grabExcessHorizontalSpace = false;
    public boolean grabExcessVerticalSpace = false;
    public int minimumWidth = 0;
    public int minimumHeight = 0;
    public boolean exclude = false;
    public static final int BEGINNING = 1;
    public static final int CENTER = 2;
    public static final int END = 3;
    public static final int FILL = 4;
    public static final int VERTICAL_ALIGN_BEGINNING = 2;
    public static final int VERTICAL_ALIGN_CENTER = 4;
    public static final int VERTICAL_ALIGN_END = 8;
    public static final int VERTICAL_ALIGN_FILL = 16;
    public static final int HORIZONTAL_ALIGN_BEGINNING = 32;
    public static final int HORIZONTAL_ALIGN_CENTER = 64;
    public static final int HORIZONTAL_ALIGN_END = 128;
    public static final int HORIZONTAL_ALIGN_FILL = 256;
    public static final int GRAB_HORIZONTAL = 512;
    public static final int GRAB_VERTICAL = 1024;
    public static final int FILL_VERTICAL = 1040;
    public static final int FILL_HORIZONTAL = 768;
    public static final int FILL_BOTH = 1808;
    int cacheWidth = -1;
    int cacheHeight = -1;
    int defaultWhint;
    int defaultHhint;
    int defaultWidth = -1;
    int defaultHeight = -1;
    int currentWhint;
    int currentHhint;
    int currentWidth = -1;
    int currentHeight = -1;

    public GridData() {
    }

    public GridData(int n) {
        if ((n & 2) != 0) {
            this.verticalAlignment = 1;
        }
        if ((n & 4) != 0) {
            this.verticalAlignment = 2;
        }
        if ((n & 0x10) != 0) {
            this.verticalAlignment = 4;
        }
        if ((n & 8) != 0) {
            this.verticalAlignment = 3;
        }
        if ((n & 0x20) != 0) {
            this.horizontalAlignment = 1;
        }
        if ((n & 0x40) != 0) {
            this.horizontalAlignment = 2;
        }
        if ((n & 0x100) != 0) {
            this.horizontalAlignment = 4;
        }
        if ((n & 0x80) != 0) {
            this.horizontalAlignment = 3;
        }
        this.grabExcessHorizontalSpace = (n & 0x200) != 0;
        this.grabExcessVerticalSpace = (n & 0x400) != 0;
    }

    public GridData(int n, int n2, boolean bl, boolean bl2) {
        this(n, n2, bl, bl2, 1, 1);
    }

    public GridData(int n, int n2, boolean bl, boolean bl2, int n3, int n4) {
        this.horizontalAlignment = n;
        this.verticalAlignment = n2;
        this.grabExcessHorizontalSpace = bl;
        this.grabExcessVerticalSpace = bl2;
        this.horizontalSpan = n3;
        this.verticalSpan = n4;
    }

    public GridData(int n, int n2) {
        this.widthHint = n;
        this.heightHint = n2;
    }

    void computeSize(Control control, int n, int n2, boolean bl) {
        if (this.cacheWidth != -1 && this.cacheHeight != -1) {
            return;
        }
        if (n == this.widthHint && n2 == this.heightHint) {
            if (this.defaultWidth == -1 || this.defaultHeight == -1 || n != this.defaultWhint || n2 != this.defaultHhint) {
                Point point = control.computeSize(n, n2, bl);
                this.defaultWhint = n;
                this.defaultHhint = n2;
                this.defaultWidth = point.x;
                this.defaultHeight = point.y;
            }
            this.cacheWidth = this.defaultWidth;
            this.cacheHeight = this.defaultHeight;
            return;
        }
        if (this.currentWidth == -1 || this.currentHeight == -1 || n != this.currentWhint || n2 != this.currentHhint) {
            Point point = control.computeSize(n, n2, bl);
            this.currentWhint = n;
            this.currentHhint = n2;
            this.currentWidth = point.x;
            this.currentHeight = point.y;
        }
        this.cacheWidth = this.currentWidth;
        this.cacheHeight = this.currentHeight;
    }

    void flushCache() {
        this.cacheHeight = -1;
        this.cacheWidth = -1;
        this.defaultHeight = -1;
        this.defaultWidth = -1;
        this.currentHeight = -1;
        this.currentWidth = -1;
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
        String string = "";
        switch (this.horizontalAlignment) {
            case 4: {
                string = "SWT.FILL";
                break;
            }
            case 1: {
                string = "SWT.BEGINNING";
                break;
            }
            case 16384: {
                string = "SWT.LEFT";
                break;
            }
            case 0x1000008: {
                string = "SWT.END";
                break;
            }
            case 3: {
                string = "GridData.END";
                break;
            }
            case 131072: {
                string = "SWT.RIGHT";
                break;
            }
            case 0x1000000: {
                string = "SWT.CENTER";
                break;
            }
            case 2: {
                string = "GridData.CENTER";
                break;
            }
            default: {
                string = "Undefined " + this.horizontalAlignment;
            }
        }
        String string2 = "";
        switch (this.verticalAlignment) {
            case 4: {
                string2 = "SWT.FILL";
                break;
            }
            case 1: {
                string2 = "SWT.BEGINNING";
                break;
            }
            case 128: {
                string2 = "SWT.TOP";
                break;
            }
            case 0x1000008: {
                string2 = "SWT.END";
                break;
            }
            case 3: {
                string2 = "GridData.END";
                break;
            }
            case 1024: {
                string2 = "SWT.BOTTOM";
                break;
            }
            case 0x1000000: {
                string2 = "SWT.CENTER";
                break;
            }
            case 2: {
                string2 = "GridData.CENTER";
                break;
            }
            default: {
                string2 = "Undefined " + this.verticalAlignment;
            }
        }
        String string3 = this.getName() + " {";
        string3 = string3 + "horizontalAlignment=" + string + " ";
        if (this.horizontalIndent != 0) {
            string3 = string3 + "horizontalIndent=" + this.horizontalIndent + " ";
        }
        if (this.horizontalSpan != 1) {
            string3 = string3 + "horizontalSpan=" + this.horizontalSpan + " ";
        }
        if (this.grabExcessHorizontalSpace) {
            string3 = string3 + "grabExcessHorizontalSpace=" + this.grabExcessHorizontalSpace + " ";
        }
        if (this.widthHint != -1) {
            string3 = string3 + "widthHint=" + this.widthHint + " ";
        }
        if (this.minimumWidth != 0) {
            string3 = string3 + "minimumWidth=" + this.minimumWidth + " ";
        }
        string3 = string3 + "verticalAlignment=" + string2 + " ";
        if (this.verticalIndent != 0) {
            string3 = string3 + "verticalIndent=" + this.verticalIndent + " ";
        }
        if (this.verticalSpan != 1) {
            string3 = string3 + "verticalSpan=" + this.verticalSpan + " ";
        }
        if (this.grabExcessVerticalSpace) {
            string3 = string3 + "grabExcessVerticalSpace=" + this.grabExcessVerticalSpace + " ";
        }
        if (this.heightHint != -1) {
            string3 = string3 + "heightHint=" + this.heightHint + " ";
        }
        if (this.minimumHeight != 0) {
            string3 = string3 + "minimumHeight=" + this.minimumHeight + " ";
        }
        if (this.exclude) {
            string3 = string3 + "exclude=" + this.exclude + " ";
        }
        string3 = string3.trim();
        string3 = string3 + "}";
        return string3;
    }
}

