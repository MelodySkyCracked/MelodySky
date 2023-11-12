/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import org.eclipse.swt.accessibility.AccessibleAttributeEvent;
import org.eclipse.swt.accessibility.AccessibleTextAttributeEvent;
import org.eclipse.swt.internal.SWTEventListener;

public interface AccessibleAttributeListener
extends SWTEventListener {
    public void getAttributes(AccessibleAttributeEvent var1);

    public void getTextAttributes(AccessibleTextAttributeEvent var1);
}

