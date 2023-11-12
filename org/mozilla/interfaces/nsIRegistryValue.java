/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIRegistryValue
extends nsISupports {
    public static final String NS_IREGISTRYVALUE_IID = "{5316c380-b2f8-11d2-a374-0080c6f80e4b}";

    public String getName();

    public String getNameUTF8();

    public long getType();

    public long getLength();
}

