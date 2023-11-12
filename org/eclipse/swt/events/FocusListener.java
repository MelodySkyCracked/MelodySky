/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import java.util.function.Consumer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.lI;
import org.eclipse.swt.events.lIIl;
import org.eclipse.swt.internal.SWTEventListener;

public interface FocusListener
extends SWTEventListener {
    public void focusGained(FocusEvent var1);

    public void focusLost(FocusEvent var1);

    default public FocusListener focusGainedAdapter(Consumer consumer) {
        return new lI(this, consumer);
    }

    default public FocusListener focusLostAdapter(Consumer consumer) {
        return new lIIl(this, consumer);
    }
}

