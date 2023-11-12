/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISAXAttributes
extends nsISupports {
    public static final String NS_ISAXATTRIBUTES_IID = "{e347005e-6cd0-11da-be43-001422106990}";

    public int getIndexFromName(String var1, String var2);

    public int getIndexFromQName(String var1);

    public int getLength();

    public String getLocalName(long var1);

    public String getQName(long var1);

    public String getType(long var1);

    public String getTypeFromName(String var1, String var2);

    public String getTypeFromQName(String var1);

    public String getURI(long var1);

    public String getValue(long var1);

    public String getValueFromName(String var1, String var2);

    public String getValueFromQName(String var1);
}

