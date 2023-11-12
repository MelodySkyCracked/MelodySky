/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import java.util.function.Consumer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.llIII;
import org.eclipse.swt.events.lllI;
import org.eclipse.swt.internal.SWTEventListener;

public interface SelectionListener
extends SWTEventListener {
    public void widgetSelected(SelectionEvent var1);

    public void widgetDefaultSelected(SelectionEvent var1);

    default public SelectionListener widgetSelectedAdapter(Consumer consumer) {
        return new llIII(this, consumer);
    }

    default public SelectionListener widgetDefaultSelectedAdapter(Consumer consumer) {
        return new lllI(this, consumer);
    }
}

