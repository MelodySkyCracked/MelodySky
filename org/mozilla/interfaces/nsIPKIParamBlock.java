/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIPKIParamBlock
extends nsISupports {
    public static final String NS_IPKIPARAMBLOCK_IID = "{b6fe3d78-1dd1-11b2-9058-ced9016984c8}";

    public void setISupportAtIndex(int var1, nsISupports var2);

    public nsISupports getISupportAtIndex(int var1);
}

