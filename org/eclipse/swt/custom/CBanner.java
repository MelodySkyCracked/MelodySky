/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CBannerLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;

public class CBanner
extends Composite {
    Control left;
    Control right;
    Control bottom;
    boolean simple = true;
    int[] curve = new int[0];
    int curveStart = 0;
    Rectangle curveRect = new Rectangle(0, 0, 0, 0);
    int curve_width = 5;
    int curve_indent = -2;
    int rightWidth = -1;
    int rightMinWidth = 0;
    int rightMinHeight = 0;
    Cursor resizeCursor;
    boolean dragging = false;
    int rightDragDisplacement = 0;
    Listener listener;
    static final int OFFSCREEN = -200;
    static final int BORDER_BOTTOM = 2;
    static final int BORDER_TOP = 3;
    static final int BORDER_STRIPE = 1;
    static final int CURVE_TAIL = 200;
    static final int BEZIER_RIGHT = 30;
    static final int BEZIER_LEFT = 30;
    static final int MIN_LEFT = 10;
    static int BORDER1 = 20;

    public CBanner(Composite composite, int n) {
        super(composite, CBanner.checkStyle(n));
        super.setLayout(new CBannerLayout());
        this.resizeCursor = this.getDisplay().getSystemCursor(9);
        this.listener = this::lambda$new$0;
        int[] nArray = new int[]{12, 3, 7, 5, 4, 9, 11};
        int[] nArray2 = nArray;
        for (int n2 : nArray) {
            this.addListener(n2, this.listener);
        }
    }

    static int[] bezier(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        double d = n;
        double d2 = 3 * (n3 - n);
        double d3 = 3 * (n + n5 - 2 * n3);
        double d4 = n7 - n + 3 * n3 - 3 * n5;
        double d5 = n2;
        double d6 = 3 * (n4 - n2);
        double d7 = 3 * (n2 + n6 - 2 * n4);
        double d8 = n8 - n2 + 3 * n4 - 3 * n6;
        int[] nArray = new int[2 * n9 + 2];
        for (int i = 0; i <= n9; ++i) {
            double d9 = (double)i / (double)n9;
            nArray[2 * i] = (int)(d + d2 * d9 + d3 * d9 * d9 + d4 * d9 * d9 * d9);
            nArray[2 * i + 1] = (int)(d5 + d6 * d9 + d7 * d9 * d9 + d8 * d9 * d9 * d9);
        }
        return nArray;
    }

    static int checkStyle(int n) {
        return 0;
    }

    public Control getBottom() {
        this.checkWidget();
        return this.bottom;
    }

    @Override
    public Rectangle getClientArea() {
        return new Rectangle(0, 0, 0, 0);
    }

    public Control getLeft() {
        this.checkWidget();
        return this.left;
    }

    public Control getRight() {
        this.checkWidget();
        return this.right;
    }

    public Point getRightMinimumSize() {
        this.checkWidget();
        return new Point(this.rightMinWidth, this.rightMinHeight);
    }

    public int getRightWidth() {
        this.checkWidget();
        if (this.right == null) {
            return 0;
        }
        if (this.rightWidth == -1) {
            Point point = this.right.computeSize(-1, -1, false);
            return point.x;
        }
        return this.rightWidth;
    }

    public boolean getSimple() {
        this.checkWidget();
        return this.simple;
    }

    void onDispose(Event event) {
        this.removeListener(12, this.listener);
        this.notifyListeners(12, event);
        event.type = 0;
        this.resizeCursor = null;
        this.left = null;
        this.right = null;
        this.bottom = null;
    }

    void onMouseDown(int n, int n2) {
        if (this.curveRect.contains(n, n2)) {
            this.dragging = true;
            this.rightDragDisplacement = this.curveStart - n + this.curve_width - this.curve_indent;
        }
    }

    void onMouseExit() {
        if (!this.dragging) {
            this.setCursor(null);
        }
    }

    void onMouseMove(int n, int n2) {
        if (!this.dragging) {
            if (this.curveRect.contains(n, n2)) {
                this.setCursor(this.resizeCursor);
            } else {
                this.setCursor(null);
            }
            return;
        }
        Point point = this.getSize();
        if (0 >= n || n >= point.x) {
            return;
        }
        this.rightWidth = Math.max(0, point.x - n - this.rightDragDisplacement);
        if (this.rightMinWidth == -1) {
            Point point2 = this.right.computeSize(this.rightMinWidth, this.rightMinHeight);
            this.rightWidth = Math.max(point2.x, this.rightWidth);
        } else {
            this.rightWidth = Math.max(this.rightMinWidth, this.rightWidth);
        }
        this.layout(false);
    }

    void onMouseUp() {
        this.dragging = false;
    }

    void onPaint(GC gC) {
        if (this.left == null && this.right == null) {
            return;
        }
        Point point = this.getSize();
        Color color = this.getDisplay().getSystemColor(BORDER1);
        if (this.bottom != null) {
            int n = this.bottom.getBounds().y - 1 - 1;
            gC.setForeground(color);
            gC.drawLine(0, n, point.x, n);
        }
        if (this.left == null || this.right == null) {
            return;
        }
        int[] nArray = new int[this.curve.length + 6];
        int n = 0;
        int n2 = this.curveStart;
        nArray[n++] = n2 + 1;
        nArray[n++] = point.y - 1;
        for (int i = 0; i < this.curve.length / 2; ++i) {
            nArray[n++] = n2 + this.curve[2 * i];
            nArray[n++] = this.curve[2 * i + 1];
        }
        nArray[n++] = n2 + this.curve_width;
        nArray[n++] = 0;
        nArray[n++] = point.x;
        nArray[n++] = 0;
        Color color2 = this.getBackground();
        if (this.getDisplay().getDepth() >= 15) {
            int[] nArray2 = new int[nArray.length];
            n = 0;
            for (int i = 0; i < nArray.length / 2; ++i) {
                nArray2[n] = nArray[n++] - 1;
                nArray2[n] = nArray[n++];
            }
            int[] nArray3 = new int[nArray.length];
            n = 0;
            for (int i = 0; i < nArray.length / 2; ++i) {
                nArray3[n] = nArray[n++] + 1;
                nArray3[n] = nArray[n++];
            }
            RGB rGB = color.getRGB();
            RGB rGB2 = color2.getRGB();
            int n3 = rGB.red + 3 * (rGB2.red - rGB.red) / 4;
            int n4 = rGB.green + 3 * (rGB2.green - rGB.green) / 4;
            int n5 = rGB.blue + 3 * (rGB2.blue - rGB.blue) / 4;
            Color color3 = new Color(n3, n4, n5);
            gC.setForeground(color3);
            gC.drawPolyline(nArray2);
            gC.drawPolyline(nArray3);
            int n6 = Math.max(0, this.curveStart - 200);
            gC.setForeground(color2);
            gC.setBackground(color);
            gC.fillGradientRectangle(n6, point.y - 1, this.curveStart - n6 + 1, 1, false);
        } else {
            int n7 = Math.max(0, this.curveStart - 200);
            gC.setForeground(color);
            gC.drawLine(n7, point.y - 1, this.curveStart + 1, point.y - 1);
        }
        gC.setForeground(color);
        gC.drawPolyline(nArray);
    }

    void onResize() {
        this.updateCurve(this.getSize().y);
    }

    public void setBottom(Control control) {
        this.checkWidget();
        if (control != null && control.getParent() != this) {
            SWT.error(5);
        }
        if (this.bottom != null && !this.bottom.isDisposed()) {
            Point point = this.bottom.getSize();
            this.bottom.setLocation(-200 - point.x, -200 - point.y);
        }
        this.bottom = control;
        this.layout(false);
    }

    @Override
    public void setLayout(Layout layout) {
        this.checkWidget();
    }

    public void setLeft(Control control) {
        this.checkWidget();
        if (control != null && control.getParent() != this) {
            SWT.error(5);
        }
        if (this.left != null && !this.left.isDisposed()) {
            Point point = this.left.getSize();
            this.left.setLocation(-200 - point.x, -200 - point.y);
        }
        this.left = control;
        this.layout(false);
    }

    public void setRight(Control control) {
        this.checkWidget();
        if (control != null && control.getParent() != this) {
            SWT.error(5);
        }
        if (this.right != null && !this.right.isDisposed()) {
            Point point = this.right.getSize();
            this.right.setLocation(-200 - point.x, -200 - point.y);
        }
        this.right = control;
        this.layout(false);
    }

    public void setRightMinimumSize(Point point) {
        this.checkWidget();
        if (point == null || point.x < -1 || point.y < -1) {
            SWT.error(5);
        }
        this.rightMinWidth = point.x;
        this.rightMinHeight = point.y;
        this.layout(false);
    }

    public void setRightWidth(int n) {
        this.checkWidget();
        if (n < -1) {
            SWT.error(5);
        }
        this.rightWidth = n;
        this.layout(false);
    }

    public void setSimple(boolean bl) {
        this.checkWidget();
        if (this.simple != bl) {
            this.simple = bl;
            if (bl) {
                this.curve_width = 5;
                this.curve_indent = -2;
            } else {
                this.curve_width = 50;
                this.curve_indent = 5;
            }
            this.updateCurve(this.getSize().y);
            this.layout(false);
            this.redraw();
        }
    }

    void updateCurve(int n) {
        int n2 = n - 1;
        this.curve = this.simple ? new int[]{0, n2, 1, n2, 2, n2 - 1, 3, n2 - 2, 3, 2, 4, 1, 5, 0} : CBanner.bezier(0, n2 + 1, 30, n2 + 1, this.curve_width - 30, 0, this.curve_width, 0, this.curve_width);
    }

    private void lambda$new$0(Event event) {
        switch (event.type) {
            case 12: {
                this.onDispose(event);
                break;
            }
            case 3: {
                this.onMouseDown(event.x, event.y);
                break;
            }
            case 7: {
                this.onMouseExit();
                break;
            }
            case 5: {
                this.onMouseMove(event.x, event.y);
                break;
            }
            case 4: {
                this.onMouseUp();
                break;
            }
            case 9: {
                this.onPaint(event.gc);
                break;
            }
            case 11: {
                this.onResize();
            }
        }
    }
}

