/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Control;

public final class FormData {
    public int width = -1;
    public int height = -1;
    public FormAttachment left;
    public FormAttachment right;
    public FormAttachment top;
    public FormAttachment bottom;
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
    FormAttachment cacheLeft;
    FormAttachment cacheRight;
    FormAttachment cacheTop;
    FormAttachment cacheBottom;
    boolean isVisited;
    boolean needed;

    public FormData() {
    }

    public FormData(int n, int n2) {
        this.width = n;
        this.height = n2;
    }

    void computeSize(Control control, int n, int n2, boolean bl) {
        if (this.cacheWidth != -1 && this.cacheHeight != -1) {
            return;
        }
        if (n == this.width && n2 == this.height) {
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
        this.defaultWidth = -1;
        this.defaultHeight = -1;
        this.currentWidth = -1;
        this.currentHeight = -1;
    }

    int getWidth(Control control, boolean bl) {
        this.needed = true;
        this.computeSize(control, this.width, this.height, bl);
        return this.cacheWidth;
    }

    int getHeight(Control control, boolean bl) {
        this.computeSize(control, this.width, this.height, bl);
        return this.cacheHeight;
    }

    FormAttachment getBottomAttachment(Control control, int n, boolean bl) {
        if (this.cacheBottom != null) {
            return this.cacheBottom;
        }
        if (this.isVisited) {
            this.cacheBottom = new FormAttachment(0, this.getHeight(control, bl));
            return this.cacheBottom;
        }
        if (this.bottom == null) {
            if (this.top == null) {
                this.cacheBottom = new FormAttachment(0, this.getHeight(control, bl));
                return this.cacheBottom;
            }
            this.cacheBottom = this.getTopAttachment(control, n, bl).plus(this.getHeight(control, bl));
            return this.cacheBottom;
        }
        Control control2 = this.bottom.control;
        if (control2 != null) {
            if (control2.isDisposed()) {
                control2 = null;
                this.bottom.control = null;
            } else if (control2.getParent() != control.getParent()) {
                control2 = null;
            }
        }
        if (control2 == null) {
            this.cacheBottom = this.bottom;
            return this.cacheBottom;
        }
        this.isVisited = true;
        FormData formData = (FormData)control2.getLayoutData();
        FormAttachment formAttachment = formData.getBottomAttachment(control2, n, bl);
        switch (this.bottom.alignment) {
            case 1024: {
                this.cacheBottom = formAttachment.plus(this.bottom.offset);
                break;
            }
            case 0x1000000: {
                FormAttachment formAttachment2 = formData.getTopAttachment(control2, n, bl);
                FormAttachment formAttachment3 = formAttachment.minus(formAttachment2);
                this.cacheBottom = formAttachment.minus(formAttachment3.minus(this.getHeight(control, bl)).divide(2));
                break;
            }
            default: {
                FormAttachment formAttachment4 = formData.getTopAttachment(control2, n, bl);
                this.cacheBottom = formAttachment4.plus(this.bottom.offset - n);
                break;
            }
        }
        this.isVisited = false;
        return this.cacheBottom;
    }

    FormAttachment getLeftAttachment(Control control, int n, boolean bl) {
        if (this.cacheLeft != null) {
            return this.cacheLeft;
        }
        if (this.isVisited) {
            this.cacheLeft = new FormAttachment(0, 0);
            return this.cacheLeft;
        }
        if (this.left == null) {
            if (this.right == null) {
                this.cacheLeft = new FormAttachment(0, 0);
                return this.cacheLeft;
            }
            this.cacheLeft = this.getRightAttachment(control, n, bl).minus(this.getWidth(control, bl));
            return this.cacheLeft;
        }
        Control control2 = this.left.control;
        if (control2 != null) {
            if (control2.isDisposed()) {
                control2 = null;
                this.left.control = null;
            } else if (control2.getParent() != control.getParent()) {
                control2 = null;
            }
        }
        if (control2 == null) {
            this.cacheLeft = this.left;
            return this.cacheLeft;
        }
        this.isVisited = true;
        FormData formData = (FormData)control2.getLayoutData();
        FormAttachment formAttachment = formData.getLeftAttachment(control2, n, bl);
        switch (this.left.alignment) {
            case 16384: {
                this.cacheLeft = formAttachment.plus(this.left.offset);
                break;
            }
            case 0x1000000: {
                FormAttachment formAttachment2 = formData.getRightAttachment(control2, n, bl);
                FormAttachment formAttachment3 = formAttachment2.minus(formAttachment);
                this.cacheLeft = formAttachment.plus(formAttachment3.minus(this.getWidth(control, bl)).divide(2));
                break;
            }
            default: {
                FormAttachment formAttachment4 = formData.getRightAttachment(control2, n, bl);
                this.cacheLeft = formAttachment4.plus(this.left.offset + n);
            }
        }
        this.isVisited = false;
        return this.cacheLeft;
    }

    String getName() {
        String string = this.getClass().getName();
        int n = string.lastIndexOf(46);
        if (n == -1) {
            return string;
        }
        return string.substring(n + 1, string.length());
    }

    FormAttachment getRightAttachment(Control control, int n, boolean bl) {
        if (this.cacheRight != null) {
            return this.cacheRight;
        }
        if (this.isVisited) {
            this.cacheRight = new FormAttachment(0, this.getWidth(control, bl));
            return this.cacheRight;
        }
        if (this.right == null) {
            if (this.left == null) {
                this.cacheRight = new FormAttachment(0, this.getWidth(control, bl));
                return this.cacheRight;
            }
            this.cacheRight = this.getLeftAttachment(control, n, bl).plus(this.getWidth(control, bl));
            return this.cacheRight;
        }
        Control control2 = this.right.control;
        if (control2 != null) {
            if (control2.isDisposed()) {
                control2 = null;
                this.right.control = null;
            } else if (control2.getParent() != control.getParent()) {
                control2 = null;
            }
        }
        if (control2 == null) {
            this.cacheRight = this.right;
            return this.cacheRight;
        }
        this.isVisited = true;
        FormData formData = (FormData)control2.getLayoutData();
        FormAttachment formAttachment = formData.getRightAttachment(control2, n, bl);
        switch (this.right.alignment) {
            case 131072: {
                this.cacheRight = formAttachment.plus(this.right.offset);
                break;
            }
            case 0x1000000: {
                FormAttachment formAttachment2 = formData.getLeftAttachment(control2, n, bl);
                FormAttachment formAttachment3 = formAttachment.minus(formAttachment2);
                this.cacheRight = formAttachment.minus(formAttachment3.minus(this.getWidth(control, bl)).divide(2));
                break;
            }
            default: {
                FormAttachment formAttachment4 = formData.getLeftAttachment(control2, n, bl);
                this.cacheRight = formAttachment4.plus(this.right.offset - n);
                break;
            }
        }
        this.isVisited = false;
        return this.cacheRight;
    }

    FormAttachment getTopAttachment(Control control, int n, boolean bl) {
        if (this.cacheTop != null) {
            return this.cacheTop;
        }
        if (this.isVisited) {
            this.cacheTop = new FormAttachment(0, 0);
            return this.cacheTop;
        }
        if (this.top == null) {
            if (this.bottom == null) {
                this.cacheTop = new FormAttachment(0, 0);
                return this.cacheTop;
            }
            this.cacheTop = this.getBottomAttachment(control, n, bl).minus(this.getHeight(control, bl));
            return this.cacheTop;
        }
        Control control2 = this.top.control;
        if (control2 != null) {
            if (control2.isDisposed()) {
                control2 = null;
                this.top.control = null;
            } else if (control2.getParent() != control.getParent()) {
                control2 = null;
            }
        }
        if (control2 == null) {
            this.cacheTop = this.top;
            return this.cacheTop;
        }
        this.isVisited = true;
        FormData formData = (FormData)control2.getLayoutData();
        FormAttachment formAttachment = formData.getTopAttachment(control2, n, bl);
        switch (this.top.alignment) {
            case 128: {
                this.cacheTop = formAttachment.plus(this.top.offset);
                break;
            }
            case 0x1000000: {
                FormAttachment formAttachment2 = formData.getBottomAttachment(control2, n, bl);
                FormAttachment formAttachment3 = formAttachment2.minus(formAttachment);
                this.cacheTop = formAttachment.plus(formAttachment3.minus(this.getHeight(control, bl)).divide(2));
                break;
            }
            default: {
                FormAttachment formAttachment4 = formData.getBottomAttachment(control2, n, bl);
                this.cacheTop = formAttachment4.plus(this.top.offset + n);
                break;
            }
        }
        this.isVisited = false;
        return this.cacheTop;
    }

    public String toString() {
        String string = this.getName() + " {";
        if (this.width != -1) {
            string = string + "width=" + this.width + " ";
        }
        if (this.height != -1) {
            string = string + "height=" + this.height + " ";
        }
        if (this.left != null) {
            string = string + "left=" + this.left + " ";
        }
        if (this.right != null) {
            string = string + "right=" + this.right + " ";
        }
        if (this.top != null) {
            string = string + "top=" + this.top + " ";
        }
        if (this.bottom != null) {
            string = string + "bottom=" + this.bottom + " ";
        }
        string = string.trim();
        string = string + "}";
        return string;
    }
}

