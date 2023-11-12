/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import org.eclipse.swt.accessibility.AccessibleTableCellEvent;
import org.eclipse.swt.internal.SWTEventListener;

public interface AccessibleTableCellListener
extends SWTEventListener {
    public void getColumnSpan(AccessibleTableCellEvent var1);

    public void getColumnHeaders(AccessibleTableCellEvent var1);

    public void getColumnIndex(AccessibleTableCellEvent var1);

    public void getRowSpan(AccessibleTableCellEvent var1);

    public void getRowHeaders(AccessibleTableCellEvent var1);

    public void getRowIndex(AccessibleTableCellEvent var1);

    public void getTable(AccessibleTableCellEvent var1);

    public void isSelected(AccessibleTableCellEvent var1);
}

