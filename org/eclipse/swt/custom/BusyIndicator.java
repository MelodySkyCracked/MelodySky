/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import java.util.Objects;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BusyIndicator {
    static int nextBusyId = 1;
    static final String BUSYID_NAME = "SWT BusyIndicator";
    static final String BUSY_CURSOR = "SWT BusyIndicator Cursor";

    public static void showWhile(Display display, Runnable runnable) {
        Shell[] shellArray;
        if (runnable == null) {
            SWT.error(4);
        }
        if (display == null && (display = Display.getCurrent()) == null) {
            runnable.run();
            return;
        }
        Integer n = nextBusyId;
        ++nextBusyId;
        Cursor cursor = display.getSystemCursor(1);
        Shell[] shellArray2 = shellArray = display.getShells();
        Shell[] shellArray3 = shellArray;
        int n2 = shellArray3.length;
        for (int i = 0; i < n2; ++i) {
            Shell shell = shellArray3[i];
            Integer object = (Integer)shell.getData(BUSYID_NAME);
            if (object != null) continue;
            BusyIndicator.setCursorAndId(shell, cursor, n);
        }
        runnable.run();
        shellArray2 = shellArray3 = display.getShells();
        for (Shell shell : shellArray3) {
            Integer n3 = (Integer)shell.getData(BUSYID_NAME);
            if (!Objects.equals(n3, n)) continue;
            BusyIndicator.setCursorAndId(shell, null, null);
        }
    }

    private static void setCursorAndId(Shell shell, Cursor cursor, Integer n) {
        if (!shell.isDisposed()) {
            shell.setCursor(cursor);
        }
        if (!shell.isDisposed()) {
            shell.setData(BUSYID_NAME, n);
        }
    }
}

