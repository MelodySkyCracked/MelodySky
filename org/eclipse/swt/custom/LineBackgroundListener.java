/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.LineBackgroundEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface LineBackgroundListener
extends SWTEventListener {
    public void lineGetBackground(LineBackgroundEvent var1);
}

