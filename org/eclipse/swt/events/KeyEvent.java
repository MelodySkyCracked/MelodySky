/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Event;

public class KeyEvent
extends TypedEvent {
    public char character;
    public int keyCode;
    public int keyLocation;
    public int stateMask;
    public boolean doit;
    static final long serialVersionUID = 3256442491011412789L;

    public KeyEvent(Event event) {
        super(event);
        this.character = event.character;
        this.keyCode = event.keyCode;
        this.keyLocation = event.keyLocation;
        this.stateMask = event.stateMask;
        this.doit = event.doit;
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " character='" + (this.character == '\u0000' ? "\\0" : String.valueOf(this.character)) + "'=0x" + Integer.toHexString(this.character) + " keyCode=0x" + Integer.toHexString(this.keyCode) + " keyLocation=0x" + Integer.toHexString(this.keyLocation) + " stateMask=0x" + Integer.toHexString(this.stateMask) + " doit=" + this.doit;
    }
}

