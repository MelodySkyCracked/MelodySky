/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAtom;
import org.mozilla.interfaces.nsISupports;

public interface nsIAtomService
extends nsISupports {
    public static final String NS_IATOMSERVICE_IID = "{e5d0d92b-ea45-4622-ab48-302baf2094ee}";

    public nsIAtom getAtom(String var1);

    public nsIAtom getPermanentAtom(String var1);
}

