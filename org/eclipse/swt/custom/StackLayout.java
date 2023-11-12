/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public class StackLayout
extends Layout {
    public int marginWidth = 0;
    public int marginHeight = 0;
    public Control topControl;

    @Override
    protected Point computeSize(Composite composite, int n, int n2, boolean bl) {
        int n3 = 0;
        int n4 = 0;
        for (Control control : composite.getChildren()) {
            Point point = control.computeSize(n, n2, bl);
            n3 = Math.max(point.x, n3);
            n4 = Math.max(point.y, n4);
        }
        int n5 = n3 + 2 * this.marginWidth;
        int n6 = n4 + 2 * this.marginHeight;
        if (n != -1) {
            n5 = n;
        }
        if (n2 != -1) {
            n6 = n2;
        }
        return new Point(n5, n6);
    }

    @Override
    protected boolean flushCache(Control control) {
        return true;
    }

    @Override
    protected void layout(Composite composite, boolean bl) {
        Rectangle rectangle;
        Rectangle rectangle2 = rectangle = composite.getClientArea();
        rectangle.x += this.marginWidth;
        Rectangle rectangle3 = rectangle2;
        rectangle3.y += this.marginHeight;
        Rectangle rectangle4 = rectangle2;
        rectangle4.width -= 2 * this.marginWidth;
        Rectangle rectangle5 = rectangle2;
        rectangle5.height -= 2 * this.marginHeight;
        for (Control control : composite.getChildren()) {
            control.setBounds(rectangle2);
            control.setVisible(control == this.topControl);
        }
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
        if (this.marginWidth != 0) {
            string = string + "marginWidth=" + this.marginWidth;
        }
        if (this.marginHeight != 0) {
            string = string + "marginHeight=" + this.marginHeight;
        }
        if (this.topControl != null) {
            string = string + "topControl=" + this.topControl;
        }
        string = string.trim();
        return string;
    }
}

