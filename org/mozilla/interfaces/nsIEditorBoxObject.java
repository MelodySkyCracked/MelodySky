/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDocShell;
import org.mozilla.interfaces.nsISupports;

public interface nsIEditorBoxObject
extends nsISupports {
    public static final String NS_IEDITORBOXOBJECT_IID = "{14b3b669-3414-4548-aa03-edf257d889c8}";

    public nsIDocShell getDocShell();
}

