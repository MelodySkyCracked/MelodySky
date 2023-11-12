/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import java.util.EventObject;

public class AccessibleActionEvent
extends EventObject {
    public String result;
    public int count;
    public int index;
    public boolean localized;
    static final long serialVersionUID = 2849066792640153087L;

    public AccessibleActionEvent(Object object) {
        super(object);
    }

    @Override
    public String toString() {
        return "AccessibleActionEvent {string=" + this.result + " count=" + this.count + " index=" + this.index;
    }
}

