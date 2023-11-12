/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIJSID
extends nsISupports {
    public static final String NS_IJSID_IID = "{c86ae131-d101-11d2-9841-006008962422}";

    public String getName();

    public String getNumber();

    public boolean getValid();

    public boolean _equals(nsIJSID var1);

    public void initialize(String var1);

    public String toString();
}

