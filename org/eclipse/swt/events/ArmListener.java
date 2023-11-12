/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.ArmEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface ArmListener
extends SWTEventListener {
    public void widgetArmed(ArmEvent var1);
}

