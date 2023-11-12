/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIScriptableDataType;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVariant;

public interface nsIScriptableConstant
extends nsISupports {
    public static final String NS_ISCRIPTABLECONSTANT_IID = "{0f6c5b09-88b0-43ca-b55c-578f24f3d810}";

    public String getName();

    public nsIScriptableDataType getType();

    public nsIVariant getValue();
}

