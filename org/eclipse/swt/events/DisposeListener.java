/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface DisposeListener
extends SWTEventListener {
    public void widgetDisposed(DisposeEvent var1);
}

