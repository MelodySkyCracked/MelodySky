/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.widgets.Control;

public class DragSourceEffect
extends DragSourceAdapter {
    Control control = null;

    public DragSourceEffect(Control control) {
        if (control == null) {
            SWT.error(4);
        }
        this.control = control;
    }

    public Control getControl() {
        return this.control;
    }
}

