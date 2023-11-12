/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

public class SelectionEvent
extends TypedEvent {
    public Widget item;
    public int detail;
    public int x;
    public int y;
    public int width;
    public int height;
    public int stateMask;
    public String text;
    public boolean doit;
    static final long serialVersionUID = 3976735856884987953L;

    public SelectionEvent(Event event) {
        super(event);
        this.item = event.item;
        this.x = event.x;
        this.y = event.y;
        this.width = event.width;
        this.height = event.height;
        this.detail = event.detail;
        this.stateMask = event.stateMask;
        this.text = event.text;
        this.doit = event.doit;
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " item=" + this.item + " detail=" + this.detail + " x=" + this.x + " y=" + this.y + " width=" + this.width + " height=" + this.height + " stateMask=0x" + Integer.toHexString(this.stateMask) + " text=" + this.text + " doit=" + this.doit;
    }
}

