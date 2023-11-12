/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Touch;
import org.eclipse.swt.widgets.Widget;

public class Event {
    public Display display;
    public Widget widget;
    public int type;
    public int detail;
    public Widget item;
    public int index;
    public GC gc;
    public int x;
    public int y;
    public int width;
    public int height;
    public int count;
    public int time;
    public int button;
    public char character;
    public int keyCode;
    public int keyLocation;
    public int stateMask;
    public int start;
    public int end;
    public String text;
    public int[] segments;
    public char[] segmentsChars;
    public boolean doit = true;
    public Object data;
    public Touch[] touches;
    public int xDirection;
    public int yDirection;
    public double magnification;
    public double rotation;

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    Rectangle getBoundsInPixels() {
        return DPIUtil.autoScaleUp(this.getBounds());
    }

    Point getLocation() {
        return new Point(this.x, this.y);
    }

    Point getLocationInPixels() {
        return DPIUtil.autoScaleUp(new Point(this.x, this.y));
    }

    public void setBounds(Rectangle rectangle) {
        this.x = rectangle.x;
        this.y = rectangle.y;
        this.width = rectangle.width;
        this.height = rectangle.height;
    }

    void setBoundsInPixels(Rectangle rectangle) {
        this.setBounds(DPIUtil.autoScaleDown(rectangle));
    }

    void setLocationInPixels(int n, int n2) {
        this.x = DPIUtil.autoScaleDown(n);
        this.y = DPIUtil.autoScaleDown(n2);
    }

    public String toString() {
        return "Event {type=" + this.type + " " + this.widget + " time=" + this.time + " data=" + this.data + " x=" + this.x + " y=" + this.y + " width=" + this.width + " height=" + this.height + " detail=" + this.detail;
    }
}

