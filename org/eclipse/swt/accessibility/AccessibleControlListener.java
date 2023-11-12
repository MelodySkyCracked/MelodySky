/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.internal.SWTEventListener;

public interface AccessibleControlListener
extends SWTEventListener {
    public void getChildAtPoint(AccessibleControlEvent var1);

    public void getLocation(AccessibleControlEvent var1);

    public void getChild(AccessibleControlEvent var1);

    public void getChildCount(AccessibleControlEvent var1);

    public void getDefaultAction(AccessibleControlEvent var1);

    public void getFocus(AccessibleControlEvent var1);

    public void getRole(AccessibleControlEvent var1);

    public void getSelection(AccessibleControlEvent var1);

    public void getState(AccessibleControlEvent var1);

    public void getValue(AccessibleControlEvent var1);

    public void getChildren(AccessibleControlEvent var1);
}

