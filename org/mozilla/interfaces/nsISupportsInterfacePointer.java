/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsISupportsPrimitive;

public interface nsISupportsInterfacePointer
extends nsISupportsPrimitive {
    public static final String NS_ISUPPORTSINTERFACEPOINTER_IID = "{995ea724-1dd1-11b2-9211-c21bdd3e7ed0}";

    public nsISupports getData();

    public void setData(nsISupports var1);

    public String getDataIID();

    public void setDataIID(String var1);

    public String toString();
}

