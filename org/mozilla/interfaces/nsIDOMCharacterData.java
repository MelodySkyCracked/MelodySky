/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;

public interface nsIDOMCharacterData
extends nsIDOMNode {
    public static final String NS_IDOMCHARACTERDATA_IID = "{a6cf9072-15b3-11d2-932e-00805f8add32}";

    public String getData();

    public void setData(String var1);

    public long getLength();

    public String substringData(long var1, long var3);

    public void appendData(String var1);

    public void insertData(long var1, String var3);

    public void deleteData(long var1, long var3);

    public void replaceData(long var1, long var3, String var5);
}

