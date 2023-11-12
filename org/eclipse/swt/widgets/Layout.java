/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class Layout {
    protected abstract Point computeSize(Composite var1, int var2, int var3, boolean var4);

    protected boolean flushCache(Control control) {
        return false;
    }

    protected abstract void layout(Composite var1, boolean var2);
}

