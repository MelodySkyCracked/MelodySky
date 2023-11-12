/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.llII;
import org.eclipse.swt.custom.llllI;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class PopupList {
    Shell shell;
    List list;
    int minimumWidth;

    public PopupList(Shell shell) {
        this(shell, 0);
    }

    public PopupList(Shell shell, int n) {
        int n2 = 516;
        if ((n & 0x100) != 0) {
            n2 |= 0x100;
        }
        this.shell = new Shell(shell, PopupList.checkStyle(n));
        this.list = new List(this.shell, n2);
        this.shell.addListener(27, this::lambda$new$0);
        this.shell.addControlListener(ControlListener.controlResizedAdapter(this::lambda$new$1));
        this.list.addMouseListener(new llII(this));
        this.list.addKeyListener(new llllI(this));
    }

    private static int checkStyle(int n) {
        int n2 = 0x6000000;
        return n & 0x6000000;
    }

    public Font getFont() {
        return this.list.getFont();
    }

    public String[] getItems() {
        return this.list.getItems();
    }

    public int getMinimumWidth() {
        return this.minimumWidth;
    }

    public String open(Rectangle rectangle) {
        Point point;
        Point point2 = this.list.computeSize(rectangle.width, -1, false);
        Rectangle rectangle2 = this.shell.getDisplay().getBounds();
        int n = rectangle2.height - (rectangle.y + rectangle.height) - 30;
        int n2 = rectangle.y - 30;
        int n3 = 0;
        if (n2 > n && point2.y > n) {
            if (point2.y > n2) {
                point2.y = n2;
            } else {
                point = point2;
                point.y += 2;
            }
            n3 = rectangle.y - point2.y;
        } else {
            if (point2.y > n) {
                point2.y = n;
            } else {
                point = point2;
                point.y += 2;
            }
            n3 = rectangle.y + rectangle.height;
        }
        point2.x = rectangle.width;
        if (point2.x < this.minimumWidth) {
            point2.x = this.minimumWidth;
        }
        int n4 = rectangle.x + rectangle.width - point2.x;
        this.shell.setBounds(n4, n3, point2.x, point2.y);
        this.shell.open();
        this.list.setFocus();
        Display display = this.shell.getDisplay();
        while (!this.shell.isDisposed() && this.shell.isVisible()) {
            if (display.readAndDispatch()) continue;
            display.sleep();
        }
        String string = null;
        if (!this.shell.isDisposed()) {
            String[] stringArray = this.list.getSelection();
            this.shell.dispose();
            if (stringArray.length != 0) {
                string = stringArray[0];
            }
        }
        return string;
    }

    public void select(String string) {
        String[] stringArray = this.list.getItems();
        if (string != null) {
            for (String string2 : stringArray) {
                if (!string2.startsWith(string)) continue;
                int n = this.list.indexOf(string2);
                this.list.select(n);
                break;
            }
        }
    }

    public void setFont(Font font) {
        this.list.setFont(font);
    }

    public void setItems(String[] stringArray) {
        this.list.setItems(stringArray);
    }

    public void setMinimumWidth(int n) {
        if (n < 0) {
            SWT.error(5);
        }
        this.minimumWidth = n;
    }

    private void lambda$new$1(ControlEvent controlEvent) {
        Rectangle rectangle = this.shell.getClientArea();
        this.list.setSize(rectangle.width, rectangle.height);
    }

    private void lambda$new$0(Event event) {
        this.shell.setVisible(false);
    }
}

