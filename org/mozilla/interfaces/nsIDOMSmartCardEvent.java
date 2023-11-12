/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;

public interface nsIDOMSmartCardEvent
extends nsIDOMEvent {
    public static final String NS_IDOMSMARTCARDEVENT_IID = "{52bdc7ca-a934-4a40-a2e2-ac83a70b4019}";

    public String getTokenName();
}

