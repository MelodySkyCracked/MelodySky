/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.PaintObjectEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface PaintObjectListener
extends SWTEventListener {
    public void paintObject(PaintObjectEvent var1);
}

