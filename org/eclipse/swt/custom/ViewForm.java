/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewFormLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;

public class ViewForm
extends Composite {
    public int marginWidth = 0;
    public int marginHeight = 0;
    public int horizontalSpacing = 1;
    public int verticalSpacing = 1;
    @Deprecated
    public static RGB borderInsideRGB = new RGB(132, 130, 132);
    @Deprecated
    public static RGB borderMiddleRGB = new RGB(143, 141, 138);
    @Deprecated
    public static RGB borderOutsideRGB = new RGB(171, 168, 165);
    Control topLeft;
    Control topCenter;
    Control topRight;
    Control content;
    boolean separateTopCenter = false;
    boolean showBorder = false;
    int separator = -1;
    int borderTop = 0;
    int borderBottom = 0;
    int borderLeft = 0;
    int borderRight = 0;
    int highlight = 0;
    Point oldSize;
    Color selectionBackground;
    Listener listener;
    static final int OFFSCREEN = -200;
    static final int BORDER1_COLOR = 18;
    static final int SELECTION_BACKGROUND = 25;

    public ViewForm(Composite composite, int n) {
        super(composite, ViewForm.checkStyle(n));
        super.setLayout(new ViewFormLayout());
        this.setBorderVisible((n & 0x800) != 0);
        this.listener = this::lambda$new$0;
        int[] nArray = new int[]{12, 9, 11};
        int[] nArray2 = nArray;
        for (int n2 : nArray) {
            this.addListener(n2, this.listener);
        }
    }

    static int checkStyle(int n) {
        int n2 = 0x6800000;
        return n & 0x6800000 | 0x100000;
    }

    @Override
    public Rectangle computeTrim(int n, int n2, int n3, int n4) {
        this.checkWidget();
        int n5 = n - this.borderLeft - this.highlight;
        int n6 = n2 - this.borderTop - this.highlight;
        int n7 = n3 + this.borderLeft + this.borderRight + 2 * this.highlight;
        int n8 = n4 + this.borderTop + this.borderBottom + 2 * this.highlight;
        return new Rectangle(n5, n6, n7, n8);
    }

    @Override
    public Rectangle getClientArea() {
        Rectangle rectangle;
        this.checkWidget();
        Rectangle rectangle2 = rectangle = super.getClientArea();
        rectangle.x += this.borderLeft;
        Rectangle rectangle3 = rectangle2;
        rectangle3.y += this.borderTop;
        Rectangle rectangle4 = rectangle2;
        rectangle4.width -= this.borderLeft + this.borderRight;
        Rectangle rectangle5 = rectangle2;
        rectangle5.height -= this.borderTop + this.borderBottom;
        return rectangle2;
    }

    public Control getContent() {
        return this.content;
    }

    public Control getTopCenter() {
        return this.topCenter;
    }

    public Control getTopLeft() {
        return this.topLeft;
    }

    public Control getTopRight() {
        return this.topRight;
    }

    void onDispose(Event event) {
        this.removeListener(12, this.listener);
        this.notifyListeners(12, event);
        event.type = 0;
        this.topLeft = null;
        this.topCenter = null;
        this.topRight = null;
        this.content = null;
        this.oldSize = null;
        this.selectionBackground = null;
    }

    void onPaint(GC gC) {
        Color color = gC.getForeground();
        Point point = this.getSize();
        Color color2 = this.getDisplay().getSystemColor(18);
        if (this.showBorder) {
            gC.setForeground(color2);
            gC.drawRectangle(0, 0, point.x - 1, point.y - 1);
            if (this.highlight > 0) {
                boolean bl = true;
                boolean bl2 = true;
                int n = point.x - 1;
                int n2 = point.y - 1;
                int[] nArray = new int[]{1, 1, n, 1, n, n2, 1, n2, 1, 1 + this.highlight, 1 + this.highlight, 1 + this.highlight, 1 + this.highlight, n2 - this.highlight, n - this.highlight, n2 - this.highlight, n - this.highlight, 1 + this.highlight, 1, 1 + this.highlight};
                Color color3 = this.getDisplay().getSystemColor(26);
                gC.setBackground(color3);
                gC.fillPolygon(nArray);
            }
        }
        if (this.separator > -1) {
            gC.setForeground(color2);
            gC.drawLine(this.borderLeft + this.highlight, this.separator, point.x - this.borderLeft - this.borderRight - this.highlight, this.separator);
        }
        gC.setForeground(color);
    }

    void onResize() {
        Point point = this.getSize();
        if (this.oldSize == null || this.oldSize.x == 0 || this.oldSize.y == 0) {
            this.redraw();
        } else {
            int n = 0;
            if (this.oldSize.x < point.x) {
                n = point.x - this.oldSize.x + this.borderRight + this.highlight;
            } else if (this.oldSize.x > point.x) {
                n = this.borderRight + this.highlight;
            }
            this.redraw(point.x - n, 0, n, point.y, false);
            int n2 = 0;
            if (this.oldSize.y < point.y) {
                n2 = point.y - this.oldSize.y + this.borderBottom + this.highlight;
            }
            if (this.oldSize.y > point.y) {
                n2 = this.borderBottom + this.highlight;
            }
            this.redraw(0, point.y - n2, point.x, n2, false);
        }
        this.oldSize = point;
    }

    public void setContent(Control control) {
        this.checkWidget();
        if (control != null && control.getParent() != this) {
            SWT.error(5);
        }
        if (this.content != null && !this.content.isDisposed()) {
            this.content.setBounds(-200, -200, 0, 0);
        }
        this.content = control;
        this.layout(false);
    }

    @Override
    public void setLayout(Layout layout) {
        this.checkWidget();
    }

    void setSelectionBackground(Color color) {
        this.checkWidget();
        if (this.selectionBackground == color) {
            return;
        }
        if (color == null) {
            color = this.getDisplay().getSystemColor(25);
        }
        this.selectionBackground = color;
        this.redraw();
    }

    public void setTopCenter(Control control) {
        this.checkWidget();
        if (control != null && control.getParent() != this) {
            SWT.error(5);
        }
        if (this.topCenter != null && !this.topCenter.isDisposed()) {
            Point point = this.topCenter.getSize();
            this.topCenter.setLocation(-200 - point.x, -200 - point.y);
        }
        this.topCenter = control;
        this.layout(false);
    }

    public void setTopLeft(Control control) {
        this.checkWidget();
        if (control != null && control.getParent() != this) {
            SWT.error(5);
        }
        if (this.topLeft != null && !this.topLeft.isDisposed()) {
            Point point = this.topLeft.getSize();
            this.topLeft.setLocation(-200 - point.x, -200 - point.y);
        }
        this.topLeft = control;
        this.layout(false);
    }

    public void setTopRight(Control control) {
        this.checkWidget();
        if (control != null && control.getParent() != this) {
            SWT.error(5);
        }
        if (this.topRight != null && !this.topRight.isDisposed()) {
            Point point = this.topRight.getSize();
            this.topRight.setLocation(-200 - point.x, -200 - point.y);
        }
        this.topRight = control;
        this.layout(false);
    }

    public void setBorderVisible(boolean bl) {
        this.checkWidget();
        if (this.showBorder == bl) {
            return;
        }
        this.showBorder = bl;
        if (this.showBorder) {
            boolean bl2 = true;
            this.borderBottom = 1;
            this.borderRight = 1;
            this.borderTop = 1;
            this.borderLeft = 1;
            if ((this.getStyle() & 0x800000) == 0) {
                this.highlight = 2;
            }
        } else {
            boolean bl3 = false;
            this.borderRight = 0;
            this.borderLeft = 0;
            this.borderTop = 0;
            this.borderBottom = 0;
            this.highlight = 0;
        }
        this.layout(false);
        this.redraw();
    }

    public void setTopCenterSeparate(boolean bl) {
        this.checkWidget();
        this.separateTopCenter = bl;
        this.layout(false);
    }

    private void lambda$new$0(Event event) {
        switch (event.type) {
            case 12: {
                this.onDispose(event);
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

