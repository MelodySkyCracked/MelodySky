/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIScriptableDataType
extends nsISupports {
    public static final String NS_ISCRIPTABLEDATATYPE_IID = "{312e3b94-dc98-4ccc-b2fb-e3406f905cc6}";

    public boolean getIsPointer();

    public boolean getIsUniquePointer();

    public boolean getIsReference();

    public boolean getIsArithmetic();

    public boolean getIsInterfacePointer();

    public boolean getIsArray();

    public boolean getIsDependent();

    public int getDataType();
}

