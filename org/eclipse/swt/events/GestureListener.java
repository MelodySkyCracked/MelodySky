/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.GestureEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface GestureListener
extends SWTEventListener {
    public void gesture(GestureEvent var1);
}

