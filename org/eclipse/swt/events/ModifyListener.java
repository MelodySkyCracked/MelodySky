/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface ModifyListener
extends SWTEventListener {
    public void modifyText(ModifyEvent var1);
}

