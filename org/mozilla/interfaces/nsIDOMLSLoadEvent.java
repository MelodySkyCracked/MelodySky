/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMLSInput;

public interface nsIDOMLSLoadEvent
extends nsIDOMEvent {
    public static final String NS_IDOMLSLOADEVENT_IID = "{6c16a810-a37d-4859-b557-337341631aee}";

    public nsIDOMDocument getNewDocument();

    public nsIDOMLSInput getInput();
}

