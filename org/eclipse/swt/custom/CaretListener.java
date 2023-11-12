/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface CaretListener
extends SWTEventListener {
    public void caretMoved(CaretEvent var1);
}

