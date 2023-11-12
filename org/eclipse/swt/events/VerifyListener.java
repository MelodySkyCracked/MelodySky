/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface VerifyListener
extends SWTEventListener {
    public void verifyText(VerifyEvent var1);
}

