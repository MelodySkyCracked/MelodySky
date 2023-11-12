/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface MenuDetectListener
extends SWTEventListener {
    public void menuDetected(MenuDetectEvent var1);
}

