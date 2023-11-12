/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVariant;

public interface nsIWritableVariant
extends nsIVariant {
    public static final String NS_IWRITABLEVARIANT_IID = "{5586a590-8c82-11d5-90f3-0010a4e73d9a}";

    public boolean getWritable();

    public void setWritable(boolean var1);

    public void setAsInt8(short var1);

    public void setAsInt16(short var1);

    public void setAsInt32(int var1);

    public void setAsInt64(long var1);

    public void setAsUint8(short var1);

    public void setAsUint16(int var1);

    public void setAsUint32(long var1);

    public void setAsUint64(double var1);

    public void setAsFloat(float var1);

    public void setAsDouble(double var1);

    public void setAsBool(boolean var1);

    public void setAsChar(char var1);

    public void setAsWChar(char var1);

    public void setAsID(String var1);

    public void setAsAString(String var1);

    public void setAsDOMString(String var1);

    public void setAsACString(String var1);

    public void setAsAUTF8String(String var1);

    public void setAsString(String var1);

    public void setAsWString(String var1);

    public void setAsISupports(nsISupports var1);

    public void setAsInterface(String var1, nsISupports var2);

    public void setAsStringWithSize(long var1, String var3);

    public void setAsWStringWithSize(long var1, String var3);

    public void setAsVoid();

    public void setAsEmpty();

    public void setAsEmptyArray();

    public void setFromVariant(nsIVariant var1);
}

