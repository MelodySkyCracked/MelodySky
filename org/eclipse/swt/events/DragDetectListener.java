/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface DragDetectListener
extends SWTEventListener {
    public void dragDetected(DragDetectEvent var1);
}

