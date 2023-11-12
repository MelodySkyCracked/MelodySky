/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIScriptableDataType;
import org.mozilla.interfaces.nsISupports;

public interface nsIScriptableParamInfo
extends nsISupports {
    public static final String NS_ISCRIPTABLEPARAMINFO_IID = "{2309482b-4631-455f-833f-5e4e9ce38589}";

    public boolean getIsIn();

    public boolean getIsOut();

    public boolean getIsRetval();

    public boolean getIsShared();

    public boolean getIsDipper();

    public nsIScriptableDataType getType();
}

