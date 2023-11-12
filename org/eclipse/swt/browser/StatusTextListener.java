/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface StatusTextListener
extends SWTEventListener {
    public void changed(StatusTextEvent var1);
}

