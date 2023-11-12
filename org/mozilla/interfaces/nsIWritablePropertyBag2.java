/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIPropertyBag2;
import org.mozilla.interfaces.nsISupports;

public interface nsIWritablePropertyBag2
extends nsIPropertyBag2 {
    public static final String NS_IWRITABLEPROPERTYBAG2_IID = "{ee42c54a-19d3-472b-8bc3-76318d5ab5f4}";

    public void setPropertyAsInt32(String var1, int var2);

    public void setPropertyAsUint32(String var1, long var2);

    public void setPropertyAsInt64(String var1, long var2);

    public void setPropertyAsUint64(String var1, double var2);

    public void setPropertyAsDouble(String var1, double var2);

    public void setPropertyAsAString(String var1, String var2);

    public void setPropertyAsACString(String var1, String var2);

    public void setPropertyAsAUTF8String(String var1, String var2);

    public void setPropertyAsBool(String var1, boolean var2);

    public void setPropertyAsInterface(String var1, nsISupports var2);
}

