/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.internal.SWTEventListener;

public interface DragSourceListener
extends SWTEventListener {
    public void dragStart(DragSourceEvent var1);

    public void dragSetData(DragSourceEvent var1);

    public void dragFinished(DragSourceEvent var1);
}

