/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Event;

public final class MenuDetectEvent
extends TypedEvent {
    public int x;
    public int y;
    public boolean doit;
    public int detail;
    private static final long serialVersionUID = -3061660596590828941L;

    public MenuDetectEvent(Event event) {
        super(event);
        this.x = event.x;
        this.y = event.y;
        this.doit = event.doit;
        this.detail = event.detail;
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " x=" + this.x + " y=" + this.y + " doit=" + this.doit + " detail=" + this.detail;
    }
}

