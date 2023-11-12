/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import java.util.EventObject;

public class AccessibleValueEvent
extends EventObject {
    public Number value;
    static final long serialVersionUID = -465979079760740668L;

    public AccessibleValueEvent(Object object) {
        super(object);
    }

    @Override
    public String toString() {
        return "AccessibleValueEvent {value=" + this.value;
    }
}

