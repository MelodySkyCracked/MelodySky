/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class LocationEvent
extends TypedEvent {
    public String location;
    public boolean top;
    public boolean doit;
    static final long serialVersionUID = 3906644198244299574L;

    public LocationEvent(Widget widget) {
        super(widget);
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " location=" + this.location + " top=" + this.top + " doit=" + this.doit;
    }
}

