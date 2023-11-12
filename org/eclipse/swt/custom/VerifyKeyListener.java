/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface VerifyKeyListener
extends SWTEventListener {
    public void verifyKey(VerifyEvent var1);
}

