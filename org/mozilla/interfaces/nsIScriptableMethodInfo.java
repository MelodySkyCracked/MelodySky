/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIScriptableParamInfo;
import org.mozilla.interfaces.nsISupports;

public interface nsIScriptableMethodInfo
extends nsISupports {
    public static final String NS_ISCRIPTABLEMETHODINFO_IID = "{9228afa2-187c-4feb-9228-5108e640ca33}";

    public boolean getIsGetter();

    public boolean getIsSetter();

    public boolean getIsNotXPCOM();

    public boolean getIsConstructor();

    public boolean getIsHidden();

    public String getName();

    public short getParamCount();

    public nsIScriptableParamInfo getParam(short var1);

    public nsIScriptableParamInfo getResult();
}

