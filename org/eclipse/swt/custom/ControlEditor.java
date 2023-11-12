/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Widget;

public class ControlEditor {
    public int horizontalAlignment = 0x1000000;
    public boolean grabHorizontal = false;
    public int minimumWidth = 0;
    public int verticalAlignment = 0x1000000;
    public boolean grabVertical = false;
    public int minimumHeight = 0;
    Composite parent;
    Control editor;
    private boolean hadFocus;
    private Listener controlListener;
    private Listener scrollbarListener;
    private static final int[] EVENTS = new int[]{1, 2, 3, 4, 11};

    public ControlEditor(Composite composite) {
        ScrollBar scrollBar;
        this.parent = composite;
        this.controlListener = this::lambda$new$0;
        for (int n : EVENTS) {
            composite.addListener(n, this.controlListener);
        }
        this.scrollbarListener = this::scroll;
        Object object = composite.getHorizontalBar();
        if (object != null) {
            ((Widget)object).addListener(13, this.scrollbarListener);
        }
        if ((scrollBar = composite.getVerticalBar()) != null) {
            scrollBar.addListener(13, this.scrollbarListener);
        }
    }

    Rectangle computeBounds() {
        Rectangle rectangle;
        Rectangle rectangle2 = this.parent.getClientArea();
        Rectangle rectangle3 = new Rectangle(rectangle2.x, rectangle2.y, this.minimumWidth, this.minimumHeight);
        if (this.grabHorizontal) {
            rectangle3.width = Math.max(rectangle2.width, this.minimumWidth);
        }
        if (this.grabVertical) {
            rectangle3.height = Math.max(rectangle2.height, this.minimumHeight);
        }
        switch (this.horizontalAlignment) {
            case 131072: {
                rectangle = rectangle3;
                rectangle.x += rectangle2.width - rectangle3.width;
                break;
            }
            case 16384: {
                break;
            }
            default: {
                rectangle = rectangle3;
                rectangle.x += (rectangle2.width - rectangle3.width) / 2;
                break;
            }
        }
        switch (this.verticalAlignment) {
            case 1024: {
                rectangle = rectangle3;
                rectangle.y += rectangle2.height - rectangle3.height;
                break;
            }
            case 128: {
                break;
            }
            default: {
                rectangle = rectangle3;
                rectangle.y += (rectangle2.height - rectangle3.height) / 2;
                break;
            }
        }
        return rectangle3;
    }

    public void dispose() {
        if (this.parent != null && !this.parent.isDisposed()) {
            ScrollBar scrollBar;
            for (int n : EVENTS) {
                this.parent.removeListener(n, this.controlListener);
            }
            Object object = this.parent.getHorizontalBar();
            if (object != null) {
                ((Widget)object).removeListener(13, this.scrollbarListener);
            }
            if ((scrollBar = this.parent.getVerticalBar()) != null) {
                scrollBar.removeListener(13, this.scrollbarListener);
            }
        }
        this.parent = null;
        this.editor = null;
        this.hadFocus = false;
        this.controlListener = null;
        this.scrollbarListener = null;
    }

    public Control getEditor() {
        return this.editor;
    }

    public void layout() {
        if (this.editor == null || this.editor.isDisposed()) {
            return;
        }
        if (this.editor.getVisible()) {
            this.hadFocus = this.editor.isFocusControl();
        }
        this.editor.setBounds(this.computeBounds());
        if (this.hadFocus) {
            if (this.editor == null || this.editor.isDisposed()) {
                return;
            }
            this.editor.setFocus();
        }
    }

    void scroll(Event event) {
        if (this.editor == null || this.editor.isDisposed()) {
            return;
        }
        this.layout();
    }

    public void setEditor(Control control) {
        if (control == null) {
            this.editor = null;
            return;
        }
        this.editor = control;
        this.layout();
        if (this.editor == null || this.editor.isDisposed()) {
            return;
        }
        control.setVisible(true);
    }

    private void lambda$new$0(Event event) {
        this.layout();
    }
}

