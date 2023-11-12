/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import org.eclipse.swt.accessibility.AccessibleValueEvent;
import org.eclipse.swt.internal.SWTEventListener;

public interface AccessibleValueListener
extends SWTEventListener {
    public void getCurrentValue(AccessibleValueEvent var1);

    public void setCurrentValue(AccessibleValueEvent var1);

    public void getMaximumValue(AccessibleValueEvent var1);

    public void getMinimumValue(AccessibleValueEvent var1);
}

