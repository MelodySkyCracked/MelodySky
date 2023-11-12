/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIScriptableConstant;
import org.mozilla.interfaces.nsIScriptableDataType;
import org.mozilla.interfaces.nsIScriptableMethodInfo;
import org.mozilla.interfaces.nsIScriptableParamInfo;
import org.mozilla.interfaces.nsISupports;

public interface nsIScriptableInterfaceInfo
extends nsISupports {
    public static final String NS_ISCRIPTABLEINTERFACEINFO_IID = "{f902d5ba-2ef6-444e-8a17-52cb70715c10}";

    public void init(String var1);

    public void initWithName(String var1);

    public String getName();

    public String getInterfaceID();

    public boolean getIsValid();

    public boolean getIsScriptable();

    public nsIScriptableInterfaceInfo getParent();

    public int getMethodCount();

    public int getConstantCount();

    public nsIScriptableMethodInfo getMethodInfo(int var1);

    public nsIScriptableMethodInfo getMethodInfoForName(String var1, int[] var2);

    public nsIScriptableConstant getConstant(int var1);

    public nsIScriptableInterfaceInfo getInfoForParam(int var1, nsIScriptableParamInfo var2);

    public String getIIDForParam(int var1, nsIScriptableParamInfo var2);

    public nsIScriptableDataType getTypeForParam(int var1, nsIScriptableParamInfo var2, int var3);

    public short getSizeIsArgNumberForParam(int var1, nsIScriptableParamInfo var2, int var3);

    public short getLengthIsArgNumberForParam(int var1, nsIScriptableParamInfo var2, int var3);

    public short getInterfaceIsArgNumberForParam(int var1, nsIScriptableParamInfo var2);

    public boolean isIID(String var1);

    public boolean getIsFunction();

    public boolean hasAncestor(String var1);
}

