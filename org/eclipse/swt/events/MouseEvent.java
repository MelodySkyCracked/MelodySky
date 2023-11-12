/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Event;

public class MouseEvent
extends TypedEvent {
    public int button;
    public int stateMask;
    public int x;
    public int y;
    public int count;
    static final long serialVersionUID = 3257288037011566898L;

    public MouseEvent(Event event) {
        super(event);
        this.x = event.x;
        this.y = event.y;
        this.button = event.button;
        this.stateMask = event.stateMask;
        this.count = event.count;
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " button=" + this.button + " stateMask=0x" + Integer.toHexString(this.stateMask) + " x=" + this.x + " y=" + this.y + " count=" + this.count;
    }
}

