/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;

public final class GUID {
    public int Data1;
    public short Data2;
    public short Data3;
    public byte[] Data4 = new byte[8];
    public static final int sizeof = COM.GUID_sizeof();
    static final String zeros = "00000000";

    static String toHex(int n, int n2) {
        String string = Integer.toHexString(n).toUpperCase();
        int n3 = string.length();
        if (n3 > n2) {
            string = string.substring(n3 - n2);
        }
        return zeros.substring(0, Math.max(0, n2 - n3)) + string;
    }

    public String toString() {
        return "{" + GUID.toHex(this.Data1, 8) + "-" + GUID.toHex(this.Data2, 4) + "-" + GUID.toHex(this.Data3, 4) + "-" + GUID.toHex(this.Data4[0], 2) + GUID.toHex(this.Data4[1], 2) + "-" + GUID.toHex(this.Data4[2], 2) + GUID.toHex(this.Data4[3], 2) + GUID.toHex(this.Data4[4], 2) + GUID.toHex(this.Data4[5], 2) + GUID.toHex(this.Data4[6], 2) + GUID.toHex(this.Data4[7], 2);
    }
}

