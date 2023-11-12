/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import java.util.EventObject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

public class TypedEvent
extends EventObject {
    public Display display;
    public Widget widget;
    public int time;
    public Object data;
    static final long serialVersionUID = 3257285846578377524L;

    public TypedEvent(Object object) {
        super(object);
    }

    public TypedEvent(Event event) {
        super(event.widget);
        this.display = event.display;
        this.widget = event.widget;
        this.time = event.time;
        this.data = event.data;
    }

    String getName() {
        String string = this.getClass().getName();
        int n = string.lastIndexOf(46);
        if (n == -1) {
            return string;
        }
        return string.substring(n + 1, string.length());
    }

    @Override
    public String toString() {
        return this.getName() + "{" + this.widget + " time=" + this.time + " data=" + this.data;
    }
}

