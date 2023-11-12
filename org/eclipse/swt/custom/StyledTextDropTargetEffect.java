/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DropTargetEffect;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class StyledTextDropTargetEffect
extends DropTargetEffect {
    static final int CARET_WIDTH = 2;
    static final int SCROLL_HYSTERESIS = 100;
    static final int SCROLL_TOLERANCE = 20;
    int currentOffset = -1;
    long scrollBeginTime;
    int scrollX = -1;
    int scrollY = -1;
    Listener paintListener = this::lambda$new$0;

    public StyledTextDropTargetEffect(StyledText styledText) {
        super(styledText);
    }

    @Override
    public void dragEnter(DropTargetEvent dropTargetEvent) {
        this.currentOffset = -1;
        this.scrollBeginTime = 0L;
        this.scrollX = -1;
        this.scrollY = -1;
        this.getControl().removeListener(9, this.paintListener);
        this.getControl().addListener(9, this.paintListener);
    }

    @Override
    public void dragLeave(DropTargetEvent dropTargetEvent) {
        StyledText styledText = (StyledText)this.getControl();
        if (this.currentOffset != -1) {
            this.refreshCaret(styledText, this.currentOffset, -1);
        }
        styledText.removeListener(9, this.paintListener);
        this.scrollBeginTime = 0L;
        this.scrollX = -1;
        this.scrollY = -1;
    }

    @Override
    public void dragOver(DropTargetEvent dropTargetEvent) {
        int n = dropTargetEvent.feedback;
        StyledText styledText = (StyledText)this.getControl();
        Point point = styledText.getDisplay().map(null, styledText, dropTargetEvent.x, dropTargetEvent.y);
        if ((n & 8) == 0) {
            this.scrollBeginTime = 0L;
            int n2 = -1;
            this.scrollY = -1;
            this.scrollX = -1;
        } else if (styledText.getCharCount() == 0) {
            this.scrollBeginTime = 0L;
            int n3 = -1;
            this.scrollY = -1;
            this.scrollX = -1;
        } else if (this.scrollX != -1 && this.scrollY != -1 && this.scrollBeginTime != 0L && (point.x >= this.scrollX && point.x <= this.scrollX + 20 || point.y >= this.scrollY && point.y <= this.scrollY + 20)) {
            if (System.currentTimeMillis() >= this.scrollBeginTime) {
                int n4;
                int n5;
                Rectangle rectangle = styledText.getClientArea();
                GC gC = new GC(styledText);
                FontMetrics fontMetrics = gC.getFontMetrics();
                gC.dispose();
                double d = fontMetrics.getAverageCharacterWidth();
                int n6 = (int)(10.0 * d);
                if ((double)point.x < (double)rectangle.x + 3.0 * d) {
                    n5 = styledText.getHorizontalPixel();
                    styledText.setHorizontalPixel(n5 - n6);
                }
                if ((double)point.x > (double)rectangle.width - 3.0 * d) {
                    n5 = styledText.getHorizontalPixel();
                    styledText.setHorizontalPixel(n5 + n6);
                }
                if (point.y < rectangle.y + (n5 = styledText.getLineHeight())) {
                    n4 = styledText.getTopPixel();
                    styledText.setTopPixel(n4 - n5);
                }
                if (point.y > rectangle.height - n5) {
                    n4 = styledText.getTopPixel();
                    styledText.setTopPixel(n4 + n5);
                }
                this.scrollBeginTime = 0L;
                n4 = -1;
                this.scrollY = -1;
                this.scrollX = -1;
            }
        } else {
            this.scrollBeginTime = System.currentTimeMillis() + 100L;
            this.scrollX = point.x;
            this.scrollY = point.y;
        }
        if ((n & 1) != 0) {
            int[] nArray = new int[]{0};
            int n7 = styledText.getOffsetAtPoint(point.x, point.y, nArray, false);
            if ((n7 += nArray[0]) != this.currentOffset) {
                this.refreshCaret(styledText, this.currentOffset, n7);
                this.currentOffset = n7;
            }
        }
    }

    void refreshCaret(StyledText styledText, int n, int n2) {
        if (n != n2) {
            int n3;
            Point point;
            if (n != -1) {
                point = styledText.getLocationAtOffset(n);
                n3 = styledText.getLineHeight(n);
                styledText.redraw(point.x, point.y, 2, n3, false);
            }
            if (n2 != -1) {
                point = styledText.getLocationAtOffset(n2);
                n3 = styledText.getLineHeight(n2);
                styledText.redraw(point.x, point.y, 2, n3, false);
            }
        }
    }

    @Override
    public void dropAccept(DropTargetEvent dropTargetEvent) {
        if (this.currentOffset != -1) {
            StyledText styledText = (StyledText)this.getControl();
            styledText.setSelection(this.currentOffset);
            this.currentOffset = -1;
        }
    }

    private void lambda$new$0(Event event) {
        if (this.currentOffset != -1) {
            StyledText styledText = (StyledText)this.getControl();
            Point point = styledText.getLocationAtOffset(this.currentOffset);
            int n = styledText.getLineHeight(this.currentOffset);
            event.gc.setBackground(event.display.getSystemColor(2));
            event.gc.fillRectangle(point.x, point.y, 2, n);
        }
    }
}

