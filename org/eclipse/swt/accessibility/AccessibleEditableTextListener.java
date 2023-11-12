/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import org.eclipse.swt.accessibility.AccessibleEditableTextEvent;
import org.eclipse.swt.accessibility.AccessibleTextAttributeEvent;
import org.eclipse.swt.internal.SWTEventListener;

public interface AccessibleEditableTextListener
extends SWTEventListener {
    public void copyText(AccessibleEditableTextEvent var1);

    public void cutText(AccessibleEditableTextEvent var1);

    public void pasteText(AccessibleEditableTextEvent var1);

    public void replaceText(AccessibleEditableTextEvent var1);

    public void setTextAttributes(AccessibleTextAttributeEvent var1);
}

