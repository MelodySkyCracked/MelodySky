/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIGlobalHistory2
extends nsISupports {
    public static final String NS_IGLOBALHISTORY2_IID = "{cf777d42-1270-4b34-be7b-2931c93feda5}";

    public void addURI(nsIURI var1, boolean var2, boolean var3, nsIURI var4);

    public boolean isVisited(nsIURI var1);

    public void setPageTitle(nsIURI var1, String var2);
}

