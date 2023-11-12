/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.ScrollBar;

class ScrolledCompositeLayout
extends Layout {
    boolean inLayout = false;
    static final int DEFAULT_WIDTH = 64;
    static final int DEFAULT_HEIGHT = 64;

    ScrolledCompositeLayout() {
    }

    @Override
    protected Point computeSize(Composite composite, int n, int n2, boolean bl) {
        ScrolledComposite scrolledComposite = (ScrolledComposite)composite;
        Point point = new Point(64, 64);
        if (scrolledComposite.content != null) {
            Point point2 = scrolledComposite.content.computeSize(n, n2, bl);
            Point point3 = scrolledComposite.content.getSize();
            point.x = scrolledComposite.getExpandHorizontal() ? point2.x : point3.x;
            point.y = scrolledComposite.getExpandVertical() ? point2.y : point3.y;
        }
        point.x = Math.max(point.x, scrolledComposite.minWidth);
        point.y = Math.max(point.y, scrolledComposite.minHeight);
        if (n != -1) {
            point.x = n;
        }
        if (n2 != -1) {
            point.y = n2;
        }
        return point;
    }

    @Override
    protected boolean flushCache(Control control) {
        return true;
    }

    @Override
    protected void layout(Composite composite, boolean bl) {
        int n;
        int n2;
        if (this.inLayout) {
            return;
        }
        ScrolledComposite scrolledComposite = (ScrolledComposite)composite;
        if (scrolledComposite.content == null) {
            return;
        }
        ScrollBar scrollBar = scrolledComposite.getHorizontalBar();
        ScrollBar scrollBar2 = scrolledComposite.getVerticalBar();
        if (scrollBar != null && scrollBar.getSize().y >= scrolledComposite.getSize().y) {
            return;
        }
        if (scrollBar2 != null && scrollBar2.getSize().x >= scrolledComposite.getSize().x) {
            return;
        }
        this.inLayout = true;
        Rectangle rectangle = scrolledComposite.content.getBounds();
        if (!scrolledComposite.alwaysShowScroll) {
            boolean bl2 = scrolledComposite.needHScroll(rectangle, false);
            boolean bl3 = scrolledComposite.needVScroll(rectangle, bl2);
            if (!bl2 && bl3) {
                bl2 = scrolledComposite.needHScroll(rectangle, bl3);
            }
            if (scrollBar != null) {
                scrollBar.setVisible(bl2);
            }
            if (scrollBar2 != null) {
                scrollBar2.setVisible(bl3);
            }
        }
        Rectangle rectangle2 = scrolledComposite.getClientArea();
        if (scrolledComposite.expandHorizontal) {
            rectangle.width = Math.max(scrolledComposite.minWidth, rectangle2.width);
        }
        if (scrolledComposite.expandVertical) {
            rectangle.height = Math.max(scrolledComposite.minHeight, rectangle2.height);
        }
        GC gC = new GC(scrolledComposite);
        if (scrollBar != null) {
            scrollBar.setMaximum(rectangle.width);
            scrollBar.setThumb(Math.min(rectangle.width, rectangle2.width));
            scrollBar.setIncrement((int)gC.getFontMetrics().getAverageCharacterWidth());
            scrollBar.setPageIncrement(rectangle2.width);
            n2 = rectangle.width - rectangle2.width;
            n = scrollBar.getSelection();
            if (n >= n2) {
                if (n2 <= 0) {
                    n = 0;
                    scrollBar.setSelection(0);
                }
                rectangle.x = -n;
            }
        }
        if (scrollBar2 != null) {
            scrollBar2.setMaximum(rectangle.height);
            scrollBar2.setThumb(Math.min(rectangle.height, rectangle2.height));
            scrollBar2.setIncrement(gC.getFontMetrics().getHeight());
            scrollBar2.setPageIncrement(rectangle2.height);
            n2 = rectangle.height - rectangle2.height;
            n = scrollBar2.getSelection();
            if (n >= n2) {
                if (n2 <= 0) {
                    n = 0;
                    scrollBar2.setSelection(0);
                }
                rectangle.y = -n;
            }
        }
        gC.dispose();
        scrolledComposite.content.setBounds(rectangle);
        this.inLayout = false;
    }
}

