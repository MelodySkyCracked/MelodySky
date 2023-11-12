/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMLSInput;

public interface nsIDOMLSProgressEvent
extends nsIDOMEvent {
    public static final String NS_IDOMLSPROGRESSEVENT_IID = "{b9a2371f-70e9-4657-b0e8-28e15b40857e}";

    public nsIDOMLSInput getInput();

    public long getPosition();

    public long getTotalSize();
}

