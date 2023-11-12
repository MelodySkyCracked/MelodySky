/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;

public interface nsIDOMBeforeUnloadEvent
extends nsIDOMEvent {
    public static final String NS_IDOMBEFOREUNLOADEVENT_IID = "{da19e9dc-dea2-4a1d-a958-9be375c9799c}";

    public String getReturnValue();

    public void setReturnValue(String var1);
}

