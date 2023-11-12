/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import java.util.function.Consumer;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.lIll;
import org.eclipse.swt.events.lIlll;
import org.eclipse.swt.internal.SWTEventListener;

public interface ControlListener
extends SWTEventListener {
    public void controlMoved(ControlEvent var1);

    public void controlResized(ControlEvent var1);

    default public ControlListener controlMovedAdapter(Consumer consumer) {
        return new lIll(this, consumer);
    }

    public static ControlListener controlResizedAdapter(Consumer consumer) {
        return new lIlll(consumer);
    }
}

