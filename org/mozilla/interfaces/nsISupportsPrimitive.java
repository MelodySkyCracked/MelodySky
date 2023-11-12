/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISupportsPrimitive
extends nsISupports {
    public static final String NS_ISUPPORTSPRIMITIVE_IID = "{d0d4b136-1dd1-11b2-9371-f0727ef827c0}";
    public static final int TYPE_ID = 1;
    public static final int TYPE_CSTRING = 2;
    public static final int TYPE_STRING = 3;
    public static final int TYPE_PRBOOL = 4;
    public static final int TYPE_PRUINT8 = 5;
    public static final int TYPE_PRUINT16 = 6;
    public static final int TYPE_PRUINT32 = 7;
    public static final int TYPE_PRUINT64 = 8;
    public static final int TYPE_PRTIME = 9;
    public static final int TYPE_CHAR = 10;
    public static final int TYPE_PRINT16 = 11;
    public static final int TYPE_PRINT32 = 12;
    public static final int TYPE_PRINT64 = 13;
    public static final int TYPE_FLOAT = 14;
    public static final int TYPE_DOUBLE = 15;
    public static final int TYPE_VOID = 16;
    public static final int TYPE_INTERFACE_POINTER = 17;

    public int getType();
}

