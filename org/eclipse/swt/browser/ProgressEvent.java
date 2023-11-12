/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class ProgressEvent
extends TypedEvent {
    public int current;
    public int total;
    static final long serialVersionUID = 3977018427045393972L;

    public ProgressEvent(Widget widget) {
        super(widget);
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " current=" + this.current + " total=" + this.total;
    }
}

