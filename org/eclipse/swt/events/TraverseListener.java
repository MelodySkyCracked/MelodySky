/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface TraverseListener
extends SWTEventListener {
    public void keyTraversed(TraverseEvent var1);
}

