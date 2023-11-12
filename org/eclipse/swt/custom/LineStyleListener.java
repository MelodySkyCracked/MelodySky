/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface LineStyleListener
extends SWTEventListener {
    public void lineGetStyle(LineStyleEvent var1);
}

