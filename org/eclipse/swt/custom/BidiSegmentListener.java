/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.BidiSegmentEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface BidiSegmentListener
extends SWTEventListener {
    public void lineGetSegments(BidiSegmentEvent var1);
}

