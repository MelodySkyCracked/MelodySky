/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIPropertyBag;
import org.mozilla.interfaces.nsISupports;

public interface nsIPropertyBag2
extends nsIPropertyBag {
    public static final String NS_IPROPERTYBAG2_IID = "{9bb35f13-0096-4a31-833a-acd97001132d}";

    public int getPropertyAsInt32(String var1);

    public long getPropertyAsUint32(String var1);

    public long getPropertyAsInt64(String var1);

    public double getPropertyAsUint64(String var1);

    public double getPropertyAsDouble(String var1);

    public String getPropertyAsAString(String var1);

    public String getPropertyAsACString(String var1);

    public String getPropertyAsAUTF8String(String var1);

    public boolean getPropertyAsBool(String var1);

    public nsISupports getPropertyAsInterface(String var1, String var2);
}

