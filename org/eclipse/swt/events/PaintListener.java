/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface PaintListener
extends SWTEventListener {
    public void paintControl(PaintEvent var1);
}

