/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsICommandParams
extends nsISupports {
    public static final String NS_ICOMMANDPARAMS_IID = "{83f892cf-7ed3-490e-967a-62640f3158e1}";
    public static final short eNoType = 0;
    public static final short eBooleanType = 1;
    public static final short eLongType = 2;
    public static final short eDoubleType = 3;
    public static final short eWStringType = 4;
    public static final short eISupportsType = 5;
    public static final short eStringType = 6;

    public short getValueType(String var1);

    public boolean getBooleanValue(String var1);

    public int getLongValue(String var1);

    public double getDoubleValue(String var1);

    public String getStringValue(String var1);

    public String getCStringValue(String var1);

    public nsISupports getISupportsValue(String var1);

    public void setBooleanValue(String var1, boolean var2);

    public void setLongValue(String var1, int var2);

    public void setDoubleValue(String var1, double var2);

    public void setStringValue(String var1, String var2);

    public void setCStringValue(String var1, String var2);

    public void setISupportsValue(String var1, nsISupports var2);

    public void removeValue(String var1);

    public boolean hasMoreElements();

    public void first();

    public String getNext();
}

