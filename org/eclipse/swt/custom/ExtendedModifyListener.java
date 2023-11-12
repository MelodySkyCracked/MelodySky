/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface ExtendedModifyListener
extends SWTEventListener {
    public void modifyText(ExtendedModifyEvent var1);
}

