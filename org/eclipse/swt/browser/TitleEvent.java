/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class TitleEvent
extends TypedEvent {
    public String title;
    static final long serialVersionUID = 4121132532906340919L;

    public TitleEvent(Widget widget) {
        super(widget);
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " title=" + this.title;
    }
}

