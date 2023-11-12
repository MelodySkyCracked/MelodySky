/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import org.eclipse.swt.accessibility.AccessibleActionEvent;
import org.eclipse.swt.internal.SWTEventListener;

public interface AccessibleActionListener
extends SWTEventListener {
    public void getActionCount(AccessibleActionEvent var1);

    public void doAction(AccessibleActionEvent var1);

    public void getDescription(AccessibleActionEvent var1);

    public void getKeyBinding(AccessibleActionEvent var1);

    public void getName(AccessibleActionEvent var1);
}

