/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import java.util.function.Consumer;
import org.eclipse.swt.browser.I;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.lIl;
import org.eclipse.swt.internal.SWTEventListener;

public interface ProgressListener
extends SWTEventListener {
    public void changed(ProgressEvent var1);

    public void completed(ProgressEvent var1);

    default public ProgressListener changedAdapter(Consumer consumer) {
        return new lIl(this, consumer);
    }

    default public ProgressListener completedAdapter(Consumer consumer) {
        return new I(this, consumer);
    }
}

