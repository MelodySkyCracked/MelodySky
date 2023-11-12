/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

@Deprecated
public class AnimatedProgress
extends Canvas {
    static final int SLEEP = 70;
    static final int DEFAULT_WIDTH = 160;
    static final int DEFAULT_HEIGHT = 18;
    boolean active = false;
    boolean showStripes = false;
    int value;
    int orientation = 256;
    boolean showBorder = false;

    public AnimatedProgress(Composite composite, int n) {
        super(composite, AnimatedProgress.checkStyle(n));
        if ((n & 0x200) != 0) {
            this.orientation = 512;
        }
        this.showBorder = (n & 0x800) != 0;
        this.addControlListener(ControlListener.controlResizedAdapter(this::lambda$new$0));
        this.addPaintListener(this::paint);
        this.addDisposeListener(this::lambda$new$1);
    }

    private static int checkStyle(int n) {
        boolean bl = false;
        return n & 0;
    }

    public synchronized void clear() {
        this.checkWidget();
        if (this.active) {
            this.stop();
        }
        this.showStripes = false;
        this.redraw();
    }

    @Override
    public Point computeSize(int n, int n2, boolean bl) {
        this.checkWidget();
        Point point = null;
        point = this.orientation == 256 ? new Point(160, 18) : new Point(18, 160);
        if (n != -1) {
            point.x = n;
        }
        if (n2 != -1) {
            point.y = n2;
        }
        return point;
    }

    private void drawBevelRect(GC gC, int n, int n2, int n3, int n4, Color color, Color color2) {
        gC.setForeground(color);
        gC.drawLine(n, n2, n + n3 - 1, n2);
        gC.drawLine(n, n2, n, n2 + n4 - 1);
        gC.setForeground(color2);
        gC.drawLine(n + n3, n2, n + n3, n2 + n4);
        gC.drawLine(n, n2 + n4, n + n3, n2 + n4);
    }

    void paint(PaintEvent paintEvent) {
        GC gC = paintEvent.gc;
        Display display = this.getDisplay();
        Rectangle rectangle = this.getClientArea();
        gC.fillRectangle(rectangle);
        if (this.showBorder) {
            this.drawBevelRect(gC, rectangle.x, rectangle.y, rectangle.width - 1, rectangle.height - 1, display.getSystemColor(18), display.getSystemColor(20));
        }
        this.paintStripes(gC);
    }

    void paintStripes(GC gC) {
        int n;
        if (!this.showStripes) {
            return;
        }
        Rectangle rectangle = this.getClientArea();
        rectangle = new Rectangle(rectangle.x + 2, rectangle.y + 2, rectangle.width - 4, rectangle.height - 4);
        gC.setLineWidth(2);
        gC.setClipping(rectangle);
        Color color = this.getDisplay().getSystemColor(26);
        gC.setBackground(color);
        gC.fillRectangle(rectangle);
        gC.setForeground(this.getBackground());
        int n2 = 12;
        int n3 = n = this.value == 0 ? 10 : this.value - 2;
        if (this.orientation == 256) {
            int n4 = rectangle.y - 1;
            int n5 = rectangle.width;
            int n6 = rectangle.height + 2;
            for (int i = 0; i < n5; i += 12) {
                int n7 = i + n;
                gC.drawLine(n7, n4, n7, n6);
            }
        } else {
            int n8 = rectangle.x - 1;
            int n9 = rectangle.width + 2;
            int n10 = rectangle.height;
            for (int i = 0; i < n10; i += 12) {
                int n11 = i + n;
                gC.drawLine(n8, n11, n9, n11);
            }
        }
        if (this.active) {
            this.value = (this.value + 2) % 12;
        }
    }

    public synchronized void start() {
        this.checkWidget();
        if (this.active) {
            return;
        }
        this.active = true;
        this.showStripes = true;
        Display display = this.getDisplay();
        Runnable[] runnableArray = new Runnable[]{null};
        runnableArray[0] = () -> this.lambda$start$2(display, runnableArray);
        display.timerExec(70, runnableArray[0]);
    }

    public synchronized void stop() {
        this.active = false;
    }

    private void lambda$start$2(Display display, Runnable[] runnableArray) {
        if (this.active) {
            GC gC = new GC(this);
            this.paintStripes(gC);
            gC.dispose();
            display.timerExec(70, runnableArray[0]);
        }
    }

    private void lambda$new$1(DisposeEvent disposeEvent) {
        this.stop();
    }

    private void lambda$new$0(ControlEvent controlEvent) {
        this.redraw();
    }
}

