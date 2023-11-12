/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDocShell;
import org.mozilla.interfaces.nsISupports;

public interface nsIBrowserBoxObject
extends nsISupports {
    public static final String NS_IBROWSERBOXOBJECT_IID = "{f2504c26-7cf5-426a-86a7-e50998ac57c1}";

    public nsIDocShell getDocShell();
}

