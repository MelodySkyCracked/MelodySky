/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

public abstract class Dialog {
    int style;
    Shell parent;
    String title;

    public Dialog(Shell shell) {
        this(shell, 32768);
    }

    public Dialog(Shell shell, int n) {
        this.checkParent(shell);
        this.parent = shell;
        this.style = n;
        this.title = "";
    }

    protected void checkSubclass() {
        if (!Display.isValidClass(this.getClass())) {
            this.error(43);
        }
    }

    void checkParent(Shell shell) {
        if (shell == null) {
            this.error(4);
        }
        shell.checkWidget();
    }

    static int checkStyle(Shell shell, int n) {
        int n2 = 229376;
        if ((n & 0x10000000) != 0 && ((n &= 0xEFFFFFFF) & 0x38000) == 0) {
            n |= shell == null ? 65536 : 32768;
        }
        if ((n & 0x38000) == 0) {
            n |= 0x10000;
        }
        if (((n &= 0xF7FFFFFF) & 0x6000000) == 0 && shell != null) {
            if ((shell.style & 0x2000000) != 0) {
                n |= 0x2000000;
            }
            if ((shell.style & 0x4000000) != 0) {
                n |= 0x4000000;
            }
        }
        return Widget.checkBits(n, 0x2000000, 0x4000000, 0, 0, 0, 0);
    }

    void error(int n) {
        SWT.error(n);
    }

    public Shell getParent() {
        return this.parent;
    }

    public int getStyle() {
        return this.style;
    }

    public String getText() {
        return this.title;
    }

    public void setText(String string) {
        if (string == null) {
            this.error(4);
        }
        this.title = string;
    }
}

