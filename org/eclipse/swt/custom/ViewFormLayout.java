/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.CLayoutData;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Scrollable;

class ViewFormLayout
extends Layout {
    ViewFormLayout() {
    }

    @Override
    protected Point computeSize(Composite composite, int n, int n2, boolean bl) {
        Point point;
        ViewForm viewForm = (ViewForm)composite;
        Control control = viewForm.topLeft;
        Control control2 = viewForm.topCenter;
        Control control3 = viewForm.topRight;
        Control control4 = viewForm.content;
        Point point2 = new Point(0, 0);
        if (control != null) {
            point2 = this.computeChildSize(control, -1, -1, bl);
        }
        Point point3 = new Point(0, 0);
        if (control2 != null) {
            point3 = this.computeChildSize(control2, -1, -1, bl);
        }
        Point point4 = new Point(0, 0);
        if (control3 != null) {
            point4 = this.computeChildSize(control3, -1, -1, bl);
        }
        Point point5 = new Point(0, 0);
        if (viewForm.separateTopCenter || n != -1 && point2.x + point3.x + point4.x > n) {
            Point point6;
            point5.x = point2.x + point4.x;
            if (point2.x > 0 && point4.x > 0) {
                point6 = point5;
                point6.x += viewForm.horizontalSpacing;
            }
            point5.x = Math.max(point3.x, point5.x);
            point5.y = Math.max(point2.y, point4.y);
            if (control2 != null) {
                point6 = point5;
                point6.y += point3.y;
                if (control != null || control3 != null) {
                    point = point5;
                    point.y += viewForm.verticalSpacing;
                }
            }
        } else {
            point5.x = point2.x + point3.x + point4.x;
            int n3 = -1;
            if (point2.x > 0) {
                ++n3;
            }
            if (point3.x > 0) {
                ++n3;
            }
            if (point4.x > 0) {
                ++n3;
            }
            if (n3 > 0) {
                point = point5;
                point.x += n3 * viewForm.horizontalSpacing;
            }
            point5.y = Math.max(point2.y, Math.max(point3.y, point4.y));
        }
        if (control4 != null) {
            if (control != null || control3 != null || control2 != null) {
                Point point7 = point5;
                ++point7.y;
            }
            Point point8 = new Point(0, 0);
            point8 = this.computeChildSize(control4, -1, -1, bl);
            point5.x = Math.max(point5.x, point8.x);
            point = point5;
            point.y += point8.y;
            if (point5.y > point8.y) {
                Point point9 = point5;
                point9.y += viewForm.verticalSpacing;
            }
        }
        Point point10 = point5;
        point10.x += 2 * viewForm.marginWidth;
        point = point5;
        point.y += 2 * viewForm.marginHeight;
        if (n != -1) {
            point5.x = n;
        }
        if (n2 != -1) {
            point5.y = n2;
        }
        return point5;
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
        ViewForm viewForm = (ViewForm)composite;
        Control control = viewForm.topLeft;
        Control control2 = viewForm.topCenter;
        Control control3 = viewForm.topRight;
        Control control4 = viewForm.content;
        Rectangle rectangle = composite.getClientArea();
        Point point = new Point(0, 0);
        if (control != null && !control.isDisposed()) {
            point = this.computeChildSize(control, -1, -1, bl);
        }
        Point point2 = new Point(0, 0);
        if (control2 != null && !control2.isDisposed()) {
            point2 = this.computeChildSize(control2, -1, -1, bl);
        }
        Point point3 = new Point(0, 0);
        if (control3 != null && !control3.isDisposed()) {
            point3 = this.computeChildSize(control3, -1, -1, bl);
        }
        int n3 = point.x + point2.x + point3.x + 2 * viewForm.marginWidth + 2 * viewForm.highlight;
        int n4 = -1;
        if (point.x > 0) {
            ++n4;
        }
        if (point2.x > 0) {
            ++n4;
        }
        if (point3.x > 0) {
            ++n4;
        }
        if (n4 > 0) {
            n3 += n4 * viewForm.horizontalSpacing;
        }
        int n5 = rectangle.x + rectangle.width - viewForm.marginWidth - viewForm.highlight;
        int n6 = rectangle.y + viewForm.marginHeight + viewForm.highlight;
        boolean bl2 = false;
        if (viewForm.separateTopCenter || n3 > rectangle.width) {
            int n7;
            n2 = Math.max(point3.y, point.y);
            if (control3 != null && !control3.isDisposed()) {
                bl2 = true;
                control3.setBounds(n5 -= point3.x, n6, point3.x, n2);
                n5 -= viewForm.horizontalSpacing;
            }
            if (control != null && !control.isDisposed()) {
                bl2 = true;
                n7 = this.computeTrim(control);
                n = n5 - rectangle.x - viewForm.marginWidth - viewForm.highlight - n7;
                point = this.computeChildSize(control, n, -1, false);
                control.setBounds(rectangle.x + viewForm.marginWidth + viewForm.highlight, n6, point.x, n2);
            }
            if (bl2) {
                n6 += n2 + viewForm.verticalSpacing;
            }
            if (control2 != null && !control2.isDisposed()) {
                bl2 = true;
                n7 = this.computeTrim(control2);
                n = rectangle.width - 2 * viewForm.marginWidth - 2 * viewForm.highlight - n7;
                Point point4 = this.computeChildSize(control2, n, -1, false);
                if (point4.x < point2.x) {
                    point2 = point4;
                }
                control2.setBounds(rectangle.x + rectangle.width - viewForm.marginWidth - viewForm.highlight - point2.x, n6, point2.x, point2.y);
                n6 += point2.y + viewForm.verticalSpacing;
            }
        } else {
            n2 = Math.max(point3.y, Math.max(point2.y, point.y));
            if (control3 != null && !control3.isDisposed()) {
                bl2 = true;
                control3.setBounds(n5 -= point3.x, n6, point3.x, n2);
                n5 -= viewForm.horizontalSpacing;
            }
            if (control2 != null && !control2.isDisposed()) {
                bl2 = true;
                control2.setBounds(n5 -= point2.x, n6, point2.x, n2);
                n5 -= viewForm.horizontalSpacing;
            }
            if (control != null && !control.isDisposed()) {
                bl2 = true;
                Rectangle rectangle2 = control instanceof Composite ? ((Composite)control).computeTrim(0, 0, 0, 0) : new Rectangle(0, 0, 0, 0);
                n = n5 - rectangle.x - viewForm.marginWidth - viewForm.highlight - rectangle2.width;
                int n8 = n2 - rectangle2.height;
                point = this.computeChildSize(control, n, n8, false);
                control.setBounds(rectangle.x + viewForm.marginWidth + viewForm.highlight, n6, point.x, n2);
            }
            if (bl2) {
                n6 += n2 + viewForm.verticalSpacing;
            }
        }
        n2 = viewForm.separator;
        viewForm.separator = -1;
        if (control4 != null && !control4.isDisposed()) {
            if (control != null || control3 != null || control2 != null) {
                viewForm.separator = n6++;
            }
            control4.setBounds(rectangle.x + viewForm.marginWidth + viewForm.highlight, n6, rectangle.width - 2 * viewForm.marginWidth - 2 * viewForm.highlight, rectangle.y + rectangle.height - n6 - viewForm.marginHeight - viewForm.highlight);
        }
        if (n2 != viewForm.separator) {
            int n9;
            if (n2 == -1) {
                n9 = viewForm.separator;
                n = viewForm.separator + 1;
            } else if (viewForm.separator == -1) {
                n9 = n2;
                n = n2 + 1;
            } else {
                n9 = Math.min(viewForm.separator, n2);
                n = Math.max(viewForm.separator, n2);
            }
            viewForm.redraw(viewForm.borderLeft, n9, viewForm.getSize().x - viewForm.borderLeft - viewForm.borderRight, n - n9, false);
        }
    }
}

