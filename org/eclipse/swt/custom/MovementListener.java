/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.MovementEvent;
import org.eclipse.swt.internal.SWTEventListener;

public interface MovementListener
extends SWTEventListener {
    public void getNextOffset(MovementEvent var1);

    public void getPreviousOffset(MovementEvent var1);
}

