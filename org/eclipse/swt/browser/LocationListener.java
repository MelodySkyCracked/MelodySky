/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import java.util.function.Consumer;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.l;
import org.eclipse.swt.browser.lIII;
import org.eclipse.swt.internal.SWTEventListener;

public interface LocationListener
extends SWTEventListener {
    public void changing(LocationEvent var1);

    public void changed(LocationEvent var1);

    default public LocationListener changingAdapter(Consumer consumer) {
        return new l(this, consumer);
    }

    default public LocationListener changedAdapter(Consumer consumer) {
        return new lIII(this, consumer);
    }
}

