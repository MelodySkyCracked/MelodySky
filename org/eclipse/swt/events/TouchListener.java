/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.TouchEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface TouchListener
extends SWTEventListener {
    public void touch(TouchEvent var1);
}

