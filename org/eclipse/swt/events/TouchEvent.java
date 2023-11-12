/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Touch;

public class TouchEvent
extends TypedEvent {
    public Touch[] touches;
    public int stateMask;
    public int x;
    public int y;
    static final long serialVersionUID = -8348741538373572182L;

    public TouchEvent(Event event) {
        super(event);
        this.touches = event.touches;
        this.stateMask = event.stateMask;
        this.x = event.x;
        this.y = event.y;
    }

    @Override
    public String toString() {
        String string = super.toString();
        string = string.substring(0, string.length() - 1);
        string = string + " stateMask=0x" + Integer.toHexString(this.stateMask) + " x=" + this.x + " y=" + this.y;
        if (this.touches != null) {
            for (Touch touch : this.touches) {
                string = string + "\n     " + touch.toString();
            }
        }
        return string;
    }
}

