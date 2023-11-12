/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface HelpListener
extends SWTEventListener {
    public void helpRequested(HelpEvent var1);
}

