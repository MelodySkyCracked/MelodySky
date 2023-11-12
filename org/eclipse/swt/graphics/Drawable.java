/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.graphics.GCData;

public interface Drawable {
    public long internal_new_GC(GCData var1);

    public void internal_dispose_GC(long var1, GCData var3);

    default public boolean isAutoScalable() {
        return true;
    }
}

