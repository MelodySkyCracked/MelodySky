/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface MouseWheelListener
extends SWTEventListener {
    public void mouseScrolled(MouseEvent var1);
}

