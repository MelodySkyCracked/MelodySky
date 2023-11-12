/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.internal.SWTEventListener;

public interface DropTargetListener
extends SWTEventListener {
    public void dragEnter(DropTargetEvent var1);

    public void dragLeave(DropTargetEvent var1);

    public void dragOperationChanged(DropTargetEvent var1);

    public void dragOver(DropTargetEvent var1);

    public void drop(DropTargetEvent var1);

    public void dropAccept(DropTargetEvent var1);
}

