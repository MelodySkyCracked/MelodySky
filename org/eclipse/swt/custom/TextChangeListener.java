/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.TextChangedEvent;
import org.eclipse.swt.custom.TextChangingEvent;
import org.eclipse.swt.internal.SWTEventListener;

public interface TextChangeListener
extends SWTEventListener {
    public void textChanging(TextChangingEvent var1);

    public void textChanged(TextChangedEvent var1);

    public void textSet(TextChangedEvent var1);
}

