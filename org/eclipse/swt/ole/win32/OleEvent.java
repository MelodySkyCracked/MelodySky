/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.ole.win32;

import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Widget;

public class OleEvent {
    public int type;
    public Widget widget;
    public int detail;
    public boolean doit = true;
    public Variant[] arguments;
}

