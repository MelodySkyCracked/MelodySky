/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.SegmentEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface SegmentListener
extends SWTEventListener {
    public void getSegments(SegmentEvent var1);
}

