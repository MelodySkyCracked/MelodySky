/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import java.util.function.Consumer;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.browser.lI;
import org.eclipse.swt.browser.lll;
import org.eclipse.swt.internal.SWTEventListener;

public interface VisibilityWindowListener
extends SWTEventListener {
    public void hide(WindowEvent var1);

    public void show(WindowEvent var1);

    default public VisibilityWindowListener hideAdapter(Consumer consumer) {
        return new lll(this, consumer);
    }

    default public VisibilityWindowListener showAdapter(Consumer consumer) {
        return new lI(this, consumer);
    }
}

