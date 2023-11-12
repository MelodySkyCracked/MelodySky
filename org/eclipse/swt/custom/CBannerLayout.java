/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.custom.CLayoutData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Scrollable;

class CBannerLayout
extends Layout {
    CBannerLayout() {
    }

    @Override
    protected Point computeSize(Composite composite, int n, int n2, boolean bl) {
        int n3;
        int n4;
        CBanner cBanner = (CBanner)composite;
        Control control = cBanner.left;
        Control control2 = cBanner.right;
        Control control3 = cBanner.bottom;
        boolean bl2 = control != null && control2 != null;
        int n5 = n2;
        int n6 = n;
        Point point = new Point(0, 0);
        if (control3 != null) {
            int n7 = this.computeTrim(control3);
            n4 = n == -1 ? -1 : Math.max(0, n6 - n7);
            point = this.computeChildSize(control3, n4, -1, bl);
        }
        Point point2 = new Point(0, 0);
        if (control2 != null) {
            n4 = this.computeTrim(control2);
            n3 = -1;
            if (cBanner.rightWidth != -1) {
                n3 = cBanner.rightWidth - n4;
                if (control != null) {
                    n3 = Math.min(n3, n6 - cBanner.curve_width + 2 * cBanner.curve_indent - 10 - n4);
                }
                n3 = Math.max(0, n3);
            }
            point2 = this.computeChildSize(control2, n3, -1, bl);
            if (n != -1) {
                n6 -= point2.x + cBanner.curve_width - 2 * cBanner.curve_indent;
            }
        }
        Point point3 = new Point(0, 0);
        if (control != null) {
            n3 = this.computeTrim(control);
            int n8 = n == -1 ? -1 : Math.max(0, n6 - n3);
            point3 = this.computeChildSize(control, n8, -1, bl);
        }
        n6 = point3.x + point2.x;
        n5 = point.y;
        if (control3 != null && (control != null || control2 != null)) {
            n5 += 3;
        }
        n5 = control != null ? (control2 == null ? (n5 += point3.y) : (n5 += Math.max(point3.y, cBanner.rightMinHeight == -1 ? point2.y : cBanner.rightMinHeight))) : (n5 += point2.y);
        if (bl2) {
            n6 += cBanner.curve_width - 2 * cBanner.curve_indent;
            n5 += 7;
        }
        if (n != -1) {
            n6 = n;
        }
        if (n2 != -1) {
            n5 = n2;
        }
        return new Point(n6, n5);
    }

    Point computeChildSize(Control control, int n, int n2, boolean bl) {
        Object object = control.getLayoutData();
        if (object == null || !(object instanceof CLayoutData)) {
            object = new CLayoutData();
            control.setLayoutData(object);
        }
        return ((CLayoutData)object).computeSize(control, n, n2, bl);
    }

    int computeTrim(Control control) {
        if (control instanceof Scrollable) {
            Rectangle rectangle = ((Scrollable)control).computeTrim(0, 0, 0, 0);
            return rectangle.width;
        }
        return control.getBorderWidth() * 2;
    }

    @Override
    protected boolean flushCache(Control control) {
        Object object = control.getLayoutData();
        if (object instanceof CLayoutData) {
            ((CLayoutData)object).flushCache();
        }
        return true;
    }

    @Override
    protected void layout(Composite composite, boolean bl) {
        int n;
        int n2;
        int n3;
        CBanner cBanner = (CBanner)composite;
        Control control = cBanner.left;
        Control control2 = cBanner.right;
        Control control3 = cBanner.bottom;
        Point point = cBanner.getSize();
        boolean bl2 = control != null && control2 != null;
        int n4 = point.x - 2 * cBanner.getBorderWidth();
        int n5 = point.y - 2 * cBanner.getBorderWidth();
        Point point2 = new Point(0, 0);
        if (control3 != null) {
            int n6 = this.computeTrim(control3);
            n3 = Math.max(0, n4 - n6);
            point2 = this.computeChildSize(control3, n3, -1, bl);
            n5 -= point2.y + 1 + 2;
        }
        if (bl2) {
            n5 -= 7;
        }
        n5 = Math.max(0, n5);
        Point point3 = new Point(0, 0);
        if (control2 != null) {
            n3 = this.computeTrim(control2);
            n2 = -1;
            if (cBanner.rightWidth != -1) {
                n2 = cBanner.rightWidth - n3;
                if (control != null) {
                    n2 = Math.min(n2, n4 - cBanner.curve_width + 2 * cBanner.curve_indent - 10 - n3);
                }
                n2 = Math.max(0, n2);
            }
            point3 = this.computeChildSize(control2, n2, -1, bl);
            n4 -= point3.x - cBanner.curve_indent + cBanner.curve_width - cBanner.curve_indent;
        }
        Point point4 = new Point(0, 0);
        if (control != null) {
            n2 = this.computeTrim(control);
            n = Math.max(0, n4 - n2);
            point4 = this.computeChildSize(control, n, -1, bl);
        }
        n2 = 0;
        n = 0;
        int n7 = cBanner.curveStart;
        Rectangle rectangle = null;
        Rectangle rectangle2 = null;
        Rectangle rectangle3 = null;
        if (control3 != null) {
            rectangle3 = new Rectangle(n2, n + point.y - point2.y, point2.x, point2.y);
        }
        if (bl2) {
            n += 4;
        }
        if (control != null) {
            rectangle = new Rectangle(n2, n, point4.x, point4.y);
            cBanner.curveStart = n2 + point4.x - cBanner.curve_indent;
            n2 += point4.x - cBanner.curve_indent + cBanner.curve_width - cBanner.curve_indent;
        }
        if (control2 != null) {
            if (control != null) {
                point3.y = Math.max(point4.y, cBanner.rightMinHeight == -1 ? point3.y : cBanner.rightMinHeight);
            }
            rectangle2 = new Rectangle(n2, n, point3.x, point3.y);
        }
        if (cBanner.curveStart < n7) {
            cBanner.redraw(cBanner.curveStart - 200, 0, n7 + cBanner.curve_width - cBanner.curveStart + 200 + 5, point.y, false);
        }
        if (cBanner.curveStart > n7) {
            cBanner.redraw(n7 - 200, 0, cBanner.curveStart + cBanner.curve_width - n7 + 200 + 5, point.y, false);
        }
        cBanner.update();
        cBanner.curveRect = new Rectangle(cBanner.curveStart, 0, cBanner.curve_width, point.y);
        if (rectangle3 != null) {
            control3.setBounds(rectangle3);
        }
        if (rectangle2 != null) {
            control2.setBounds(rectangle2);
        }
        if (rectangle != null) {
            control.setBounds(rectangle);
        }
    }
}

