/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.OS;

public class ITypeInfo
extends IUnknown {
    public ITypeInfo(long l2) {
        super(l2);
    }

    public int GetDocumentation(int n, String[] stringArray, String[] stringArray2, int[] nArray, String[] stringArray3) {
        int n2;
        char[] cArray;
        int n3;
        long[] lArray = null;
        if (stringArray != null) {
            lArray = new long[]{0L};
        }
        long[] lArray2 = null;
        if (stringArray2 != null) {
            lArray2 = new long[]{0L};
        }
        long[] lArray3 = null;
        if (stringArray3 != null) {
            lArray3 = new long[]{0L};
        }
        int n4 = COM.VtblCall(12, this.address, n, lArray, lArray2, nArray, lArray3);
        if (stringArray != null && lArray[0] != 0L) {
            n3 = COM.SysStringByteLen(lArray[0]);
            if (n3 > 0) {
                cArray = new char[(n3 + 1) / 2];
                OS.MoveMemory(cArray, lArray[0], n3);
                stringArray[0] = new String(cArray);
                n2 = stringArray[0].indexOf("\u0000");
                if (n2 > 0) {
                    stringArray[0] = stringArray[0].substring(0, n2);
                }
            }
            COM.SysFreeString(lArray[0]);
        }
        if (stringArray2 != null && lArray2[0] != 0L) {
            n3 = COM.SysStringByteLen(lArray2[0]);
            if (n3 > 0) {
                cArray = new char[(n3 + 1) / 2];
                OS.MoveMemory(cArray, lArray2[0], n3);
                stringArray2[0] = new String(cArray);
                n2 = stringArray2[0].indexOf("\u0000");
                if (n2 > 0) {
                    stringArray2[0] = stringArray2[0].substring(0, n2);
                }
            }
            COM.SysFreeString(lArray2[0]);
        }
        if (stringArray3 != null && lArray3[0] != 0L) {
            n3 = COM.SysStringByteLen(lArray3[0]);
            if (n3 > 0) {
                cArray = new char[(n3 + 1) / 2];
                OS.MoveMemory(cArray, lArray3[0], n3);
                stringArray3[0] = new String(cArray);
                n2 = stringArray3[0].indexOf("\u0000");
                if (n2 > 0) {
                    stringArray3[0] = stringArray3[0].substring(0, n2);
                }
            }
            COM.SysFreeString(lArray3[0]);
        }
        return n4;
    }

    public int GetFuncDesc(int n, long[] lArray) {
        return COM.VtblCall(5, this.address, n, lArray);
    }

    public int GetImplTypeFlags(int n, int[] nArray) {
        return COM.VtblCall(9, this.address, n, nArray);
    }

    public int GetNames(int n, String[] stringArray, int n2, int[] nArray) {
        int n3 = stringArray.length;
        long[] lArray = new long[n3];
        int n4 = COM.VtblCall(7, this.address, n, lArray, n3, nArray);
        if (n4 == 0) {
            for (int i = 0; i < nArray[0]; ++i) {
                int n5 = COM.SysStringByteLen(lArray[i]);
                if (n5 > 0) {
                    char[] cArray = new char[(n5 + 1) / 2];
                    OS.MoveMemory(cArray, lArray[i], n5);
                    stringArray[i] = new String(cArray);
                    int n6 = stringArray[i].indexOf("\u0000");
                    if (n6 > 0) {
                        stringArray[i] = stringArray[i].substring(0, n6);
                    }
                }
                COM.SysFreeString(lArray[i]);
            }
        }
        return n4;
    }

    public int GetRefTypeInfo(int n, long[] lArray) {
        return COM.VtblCall(14, this.address, n, lArray);
    }

    public int GetRefTypeOfImplType(int n, int[] nArray) {
        return COM.VtblCall(8, this.address, n, nArray);
    }

    public int GetTypeAttr(long[] lArray) {
        return COM.VtblCall(3, this.address, lArray);
    }

    public int GetVarDesc(int n, long[] lArray) {
        return COM.VtblCall(6, this.address, n, lArray);
    }

    public int ReleaseFuncDesc(long l2) {
        return COM.VtblCall(20, this.address, l2);
    }

    public int ReleaseTypeAttr(long l2) {
        return COM.VtblCall(19, this.address, l2);
    }

    public int ReleaseVarDesc(long l2) {
        return COM.VtblCall(21, this.address, l2);
    }
}

