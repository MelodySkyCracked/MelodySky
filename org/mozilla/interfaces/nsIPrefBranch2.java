/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIObserver;
import org.mozilla.interfaces.nsIPrefBranch;

public interface nsIPrefBranch2
extends nsIPrefBranch {
    public static final String NS_IPREFBRANCH2_IID = "{74567534-eb94-4b1c-8f45-389643bfc555}";

    public void addObserver(String var1, nsIObserver var2, boolean var3);

    public void removeObserver(String var1, nsIObserver var2);
}

