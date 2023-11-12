/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMNode;

public interface nsIDOMCustomEvent
extends nsIDOMEvent {
    public static final String NS_IDOMCUSTOMEVENT_IID = "{55c7af7b-1a64-40bf-87eb-2c2cbee0491b}";

    public void setCurrentTarget(nsIDOMNode var1);

    public void setEventPhase(int var1);
}

