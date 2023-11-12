/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

class MouseNavigator {
    private final StyledText parent;
    boolean navigationActivated = false;
    private GC gc;
    private static final int CIRCLE_RADIUS = 15;
    private static final int CENTRAL_POINT_RADIUS = 2;
    private Point originalMouseLocation;
    private final Listener mouseDownListener;
    private final Listener mouseUpListener;
    private final Listener paintListener;
    private final Listener mouseMoveListener;
    private final Listener focusOutListener;
    private boolean hasHBar;
    private boolean hasVBar;
    private Cursor previousCursor;

    MouseNavigator(StyledText styledText) {
        if (styledText == null) {
            SWT.error(4);
        }
        if (styledText.isDisposed()) {
            SWT.error(24);
        }
        this.parent = styledText;
        this.mouseDownListener = this::lambda$new$0;
        this.parent.addListener(3, this.mouseDownListener);
        this.mouseUpListener = this::lambda$new$1;
        this.parent.addListener(4, this.mouseUpListener);
        this.paintListener = this::lambda$new$2;
        this.parent.addListener(9, this.paintListener);
        this.mouseMoveListener = this::lambda$new$3;
        this.parent.addListener(5, this.mouseMoveListener);
        this.focusOutListener = this::lambda$new$4;
        this.parent.addListener(16, this.focusOutListener);
    }

    void onMouseDown(Event event) {
        if (event.button != 2 || this.navigationActivated) {
            return;
        }
        if (!this.parent.isVisible() || !this.parent.getEnabled() || this.parent.middleClickPressed) {
            return;
        }
        this.initBarState();
        if (!this.hasHBar && !this.hasVBar) {
            return;
        }
        this.navigationActivated = true;
        this.previousCursor = this.parent.getCursor();
        this.parent.setCursor(this.parent.getDisplay().getSystemCursor(0));
        this.originalMouseLocation = this.getMouseLocation();
        this.parent.redraw();
    }

    private void initBarState() {
        this.hasHBar = this.computeHasHorizontalBar();
        this.hasVBar = this.computeHasVerticalBar();
    }

    private boolean computeHasHorizontalBar() {
        ScrollBar scrollBar = this.parent.getHorizontalBar();
        boolean bl = scrollBar != null && scrollBar.isVisible();
        boolean bl2 = this.parent.computeSize((int)-1, (int)-1).x > this.parent.getSize().x;
        return bl && bl2;
    }

    private boolean computeHasVerticalBar() {
        ScrollBar scrollBar = this.parent.getVerticalBar();
        boolean bl = scrollBar != null && scrollBar.isEnabled();
        boolean bl2 = this.parent.computeSize((int)-1, (int)-1).y > this.parent.getSize().y;
        return bl && bl2;
    }

    private void onMouseUp(Event event) {
        if (this.computeDist() < 15 && this.computeDist() >= 0) {
            return;
        }
        this.deactivate();
    }

    public int computeDist() {
        if (this.originalMouseLocation == null) {
            return -1;
        }
        Point point = this.getMouseLocation();
        int n = this.originalMouseLocation.x - point.x;
        int n2 = this.originalMouseLocation.y - point.y;
        int n3 = (int)Math.sqrt(n * n + n2 * n2);
        return n3;
    }

    private void deactivate() {
        this.parent.setCursor(this.previousCursor);
        this.navigationActivated = false;
        this.originalMouseLocation = null;
        this.parent.redraw();
    }

    private void onFocusOut(Event event) {
        this.deactivate();
    }

    private void onMouseMove(Event event) {
        ScrollBar scrollBar;
        if (!this.navigationActivated) {
            return;
        }
        Point point = this.getMouseLocation();
        int n = this.originalMouseLocation.x - point.x;
        int n2 = this.originalMouseLocation.y - point.y;
        int n3 = (int)Math.sqrt(n * n + n2 * n2);
        if (n3 < 15) {
            return;
        }
        this.parent.setRedraw(false);
        if (this.hasHBar) {
            scrollBar = this.parent.getHorizontalBar();
            scrollBar.setSelection((int)((double)scrollBar.getSelection() - (double)n * 0.1));
            this.fireSelectionEvent(event, scrollBar);
        }
        if (this.hasVBar) {
            scrollBar = this.parent.getVerticalBar();
            scrollBar.setSelection((int)((double)scrollBar.getSelection() - (double)n2 * 0.1));
            this.fireSelectionEvent(event, scrollBar);
        }
        this.parent.setRedraw(true);
        this.parent.redraw();
    }

    private void fireSelectionEvent(Event event, ScrollBar scrollBar) {
        Event event2 = new Event();
        event2.widget = scrollBar;
        event2.display = this.parent.getDisplay();
        event2.type = 13;
        event2.time = event.time;
        for (Listener listener : scrollBar.getListeners(13)) {
            listener.handleEvent(event2);
        }
    }

    private Point getMouseLocation() {
        Point point = Display.getCurrent().getCursorLocation();
        Point point2 = this.parent.toControl(point);
        return point2;
    }

    private void onPaint(Event event) {
        if (!this.navigationActivated) {
            return;
        }
        Rectangle rectangle = this.parent.getClientArea();
        if (rectangle.width == 0 || rectangle.height == 0) {
            return;
        }
        this.gc = event.gc;
        this.gc.setAntialias(1);
        this.gc.setAdvanced(true);
        Color color = this.gc.getForeground();
        Color color2 = this.gc.getBackground();
        this.gc.setBackground(this.parent.getForeground());
        this.drawCircle();
        this.drawCentralPoint();
        this.drawArrows();
        this.gc.setForeground(color);
        this.gc.setBackground(color2);
    }

    private void drawCircle() {
        this.gc.setBackground(this.parent.getBackground());
        this.gc.setForeground(this.parent.getForeground());
        this.gc.setAlpha(200);
        this.gc.fillOval(this.originalMouseLocation.x - 15, this.originalMouseLocation.y - 15, 30, 30);
        this.gc.setBackground(this.parent.getForeground());
        this.gc.setAlpha(255);
        this.gc.drawOval(this.originalMouseLocation.x - 15, this.originalMouseLocation.y - 15, 30, 30);
    }

    private void drawCentralPoint() {
        this.gc.fillOval(this.originalMouseLocation.x - 2, this.originalMouseLocation.y - 2, 4, 4);
    }

    private void drawArrows() {
        this.gc.setLineWidth(2);
        if (this.hasHBar) {
            this.drawHorizontalArrows();
        }
        if (this.hasVBar) {
            this.drawVerticalArrows();
        }
    }

    private void drawHorizontalArrows() {
        int[] nArray = new int[]{this.originalMouseLocation.x - 6, this.originalMouseLocation.y + 3, this.originalMouseLocation.x - 9, this.originalMouseLocation.y, this.originalMouseLocation.x - 6, this.originalMouseLocation.y - 3};
        this.gc.drawPolyline(nArray);
        nArray[0] = this.originalMouseLocation.x + 7;
        nArray[1] = this.originalMouseLocation.y + 3;
        nArray[2] = this.originalMouseLocation.x + 10;
        nArray[3] = this.originalMouseLocation.y;
        nArray[4] = this.originalMouseLocation.x + 7;
        nArray[5] = this.originalMouseLocation.y - 3;
        this.gc.drawPolyline(nArray);
    }

    private void drawVerticalArrows() {
        int[] nArray = new int[]{this.originalMouseLocation.x - 3, this.originalMouseLocation.y - 6, this.originalMouseLocation.x, this.originalMouseLocation.y - 10, this.originalMouseLocation.x + 3, this.originalMouseLocation.y - 6};
        this.gc.drawPolyline(nArray);
        nArray[0] = this.originalMouseLocation.x - 3;
        nArray[1] = this.originalMouseLocation.y + 7;
        nArray[2] = this.originalMouseLocation.x;
        nArray[3] = this.originalMouseLocation.y + 11;
        nArray[4] = this.originalMouseLocation.x + 3;
        nArray[5] = this.originalMouseLocation.y + 7;
        this.gc.drawPolyline(nArray);
    }

    void dispose() {
        if (this.parent.isDisposed()) {
            return;
        }
        this.parent.removeListener(3, this.mouseDownListener);
        this.parent.removeListener(4, this.mouseUpListener);
        this.parent.removeListener(9, this.paintListener);
        this.parent.removeListener(5, this.mouseMoveListener);
        this.parent.removeListener(7, this.focusOutListener);
    }

    private void lambda$new$4(Event event) {
        this.onFocusOut(event);
    }

    private void lambda$new$3(Event event) {
        this.onMouseMove(event);
    }

    private void lambda$new$2(Event event) {
        this.onPaint(event);
    }

    private void lambda$new$1(Event event) {
        this.onMouseUp(event);
    }

    private void lambda$new$0(Event event) {
        this.onMouseDown(event);
    }
}

