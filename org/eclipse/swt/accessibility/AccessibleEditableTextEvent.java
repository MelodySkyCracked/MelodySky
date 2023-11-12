/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import java.util.EventObject;

public class AccessibleEditableTextEvent
extends EventObject {
    public int start;
    public int end;
    public String string;
    public String result;
    static final long serialVersionUID = -5045447704486894646L;

    public AccessibleEditableTextEvent(Object object) {
        super(object);
    }

    @Override
    public String toString() {
        return "AccessibleEditableTextEvent {start=" + this.start + " end=" + this.end + " string=" + this.string + " result=" + this.result;
    }
}

