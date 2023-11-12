/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;

public final class PaintEvent
extends TypedEvent {
    public GC gc;
    public int x;
    public int y;
    public int width;
    public int height;
    public int count;
    static final long serialVersionUID = 3256446919205992497L;

    public PaintEvent(Event event) {
        super(event);
        this.gc = event.gc;
        this.x = event.x;
        this.y = event.y;
        this.width = event.width;
        this.height = event.height;
        this.count = event.count;
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " gc=" + this.gc + " x=" + this.x + " y=" + this.y + " width=" + this.width + " height=" + this.height + " count=" + this.count;
    }
}

