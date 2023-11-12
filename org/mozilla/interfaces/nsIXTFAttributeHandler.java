/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAtom;
import org.mozilla.interfaces.nsISupports;

public interface nsIXTFAttributeHandler
extends nsISupports {
    public static final String NS_IXTFATTRIBUTEHANDLER_IID = "{72152f7f-7e8d-43fd-8477-3f29ae8d240d}";

    public boolean handlesAttribute(nsIAtom var1);

    public void setAttribute(nsIAtom var1, String var2);

    public void removeAttribute(nsIAtom var1);

    public String getAttribute(nsIAtom var1);

    public boolean hasAttribute(nsIAtom var1);

    public long getAttributeCount();

    public nsIAtom getAttributeNameAt(long var1);
}

