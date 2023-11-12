/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import java.util.EventObject;

public class AccessibleEvent
extends EventObject {
    public int childID;
    public String result;
    static final long serialVersionUID = 3257567304224026934L;

    public AccessibleEvent(Object object) {
        super(object);
    }

    @Override
    public String toString() {
        return "AccessibleEvent {childID=" + this.childID + " result=" + this.result;
    }
}

