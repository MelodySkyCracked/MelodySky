/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Event;

public class GestureEvent
extends TypedEvent {
    public int stateMask;
    public int detail;
    public int x;
    public int y;
    public double rotation;
    public int xDirection;
    public int yDirection;
    public double magnification;
    public boolean doit;
    static final long serialVersionUID = -8348741538373572182L;

    public GestureEvent(Event event) {
        super(event);
        this.stateMask = event.stateMask;
        this.x = event.x;
        this.y = event.y;
        this.detail = event.detail;
        this.rotation = event.rotation;
        this.xDirection = event.xDirection;
        this.yDirection = event.yDirection;
        this.magnification = event.magnification;
        this.doit = event.doit;
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " stateMask=0x" + Integer.toHexString(this.stateMask) + " detail=" + this.detail + " x=" + this.x + " y=" + this.y + " rotation=" + this.rotation + " xDirection=" + this.xDirection + " yDirection=" + this.yDirection + " magnification=" + this.magnification;
    }
}

