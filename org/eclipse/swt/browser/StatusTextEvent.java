/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class StatusTextEvent
extends TypedEvent {
    public String text;
    static final long serialVersionUID = 3258407348371600439L;

    public StatusTextEvent(Widget widget) {
        super(widget);
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " text=" + this.text;
    }
}

