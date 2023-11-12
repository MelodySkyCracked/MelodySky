/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Event;

public final class VerifyEvent
extends KeyEvent {
    public int start;
    public int end;
    public String text;
    static final long serialVersionUID = 3257003246269577014L;

    public VerifyEvent(Event event) {
        super(event);
        this.start = event.start;
        this.end = event.end;
        this.text = event.text;
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " start=" + this.start + " end=" + this.end + " text=" + this.text;
    }
}

