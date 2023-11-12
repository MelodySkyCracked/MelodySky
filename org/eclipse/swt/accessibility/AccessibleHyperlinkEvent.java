/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import java.util.EventObject;
import org.eclipse.swt.accessibility.Accessible;

public class AccessibleHyperlinkEvent
extends EventObject {
    public Accessible accessible;
    public String result;
    public int index;
    static final long serialVersionUID = 6253098373844074544L;

    public AccessibleHyperlinkEvent(Object object) {
        super(object);
    }

    @Override
    public String toString() {
        return "AccessibleHyperlinkEvent {accessible=" + this.accessible + " string=" + this.result + " index=" + this.index;
    }
}

