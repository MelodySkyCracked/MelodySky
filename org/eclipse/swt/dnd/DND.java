/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.SWTException;

public class DND {
    public static final int CLIPBOARD = 1;
    public static final int SELECTION_CLIPBOARD = 2;
    public static final int DROP_NONE = 0;
    public static final int DROP_COPY = 1;
    public static final int DROP_MOVE = 2;
    public static final int DROP_LINK = 4;
    public static final int DROP_TARGET_MOVE = 8;
    public static final int DROP_DEFAULT = 16;
    public static final int DragEnd = 2000;
    public static final int DragSetData = 2001;
    public static final int DragEnter = 2002;
    public static final int DragLeave = 2003;
    public static final int DragOver = 2004;
    public static final int DragOperationChanged = 2005;
    public static final int Drop = 2006;
    public static final int DropAccept = 2007;
    public static final int DragStart = 2008;
    public static final int FEEDBACK_NONE = 0;
    public static final int FEEDBACK_SELECT = 1;
    public static final int FEEDBACK_INSERT_BEFORE = 2;
    public static final int FEEDBACK_INSERT_AFTER = 4;
    public static final int FEEDBACK_SCROLL = 8;
    public static final int FEEDBACK_EXPAND = 16;
    public static final int ERROR_CANNOT_INIT_DRAG = 2000;
    public static final int ERROR_CANNOT_INIT_DROP = 2001;
    public static final int ERROR_CANNOT_SET_CLIPBOARD = 2002;
    public static final int ERROR_INVALID_DATA = 2003;
    public static final String DROP_TARGET_KEY = "DropTarget";
    public static final String DRAG_SOURCE_KEY = "DragSource";
    static final String INIT_DRAG_MESSAGE = "Cannot initialize Drag";
    static final String INIT_DROP_MESSAGE = "Cannot initialize Drop";
    static final String CANNOT_SET_CLIPBOARD_MESSAGE = "Cannot set data in clipboard";
    static final String INVALID_DATA_MESSAGE = "Data does not have correct format for type";

    public static void error(int n) {
        DND.error(n, 0);
    }

    public static void error(int n, int n2) {
        switch (n) {
            case 2000: {
                String string = INIT_DRAG_MESSAGE;
                if (n2 != 0) {
                    string = string + " result = " + n2;
                }
                throw new SWTError(n, string);
            }
            case 2001: {
                String string = INIT_DROP_MESSAGE;
                if (n2 != 0) {
                    string = string + " result = " + n2;
                }
                throw new SWTError(n, string);
            }
            case 2002: {
                String string = CANNOT_SET_CLIPBOARD_MESSAGE;
                if (n2 != 0) {
                    string = string + " result = " + n2;
                }
                throw new SWTError(n, string);
            }
            case 2003: {
                String string = INVALID_DATA_MESSAGE;
                if (n2 != 0) {
                    string = string + " result = " + n2;
                }
                throw new SWTException(n, string);
            }
        }
        SWT.error(n);
    }
}

