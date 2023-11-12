/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDocShell;
import org.mozilla.interfaces.nsISupports;

public interface nsIIFrameBoxObject
extends nsISupports {
    public static final String NS_IIFRAMEBOXOBJECT_IID = "{dd9ab9be-fed3-4bff-a72d-5390d52dd887}";

    public nsIDocShell getDocShell();
}

