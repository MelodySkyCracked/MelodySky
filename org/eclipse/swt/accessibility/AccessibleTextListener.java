/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import org.eclipse.swt.accessibility.AccessibleTextEvent;
import org.eclipse.swt.internal.SWTEventListener;

public interface AccessibleTextListener
extends SWTEventListener {
    public void getCaretOffset(AccessibleTextEvent var1);

    public void getSelectionRange(AccessibleTextEvent var1);
}

