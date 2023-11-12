/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIRegistryNode
extends nsISupports {
    public static final String NS_IREGISTRYNODE_IID = "{d1b54831-ac07-11d2-805e-00600811a9c3}";

    public String getNameUTF8();

    public String getName();

    public long getKey();
}

