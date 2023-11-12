/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.LONG;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.win32.OS;

public class COMObject {
    public long ppVtable;
    private static final int MAX_ARG_COUNT = 12;
    private static final int MAX_VTABLE_LENGTH = 80;
    private static Callback[][] Callbacks = new Callback[80][12];
    private static Map ObjectMap = new HashMap();

    public COMObject(int[] nArray) {
        long[] lArray = new long[nArray.length];
        Callback[][] callbackArray = Callbacks;
        synchronized (Callbacks) {
            int n = nArray.length;
            for (int i = 0; i < n; ++i) {
                if (Callbacks[i][nArray[i]] == null) {
                    COMObject.Callbacks[i][nArray[i]] = new Callback(this.getClass(), "callback" + i, nArray[i] + 1, true, -2147467259L);
                }
                lArray[i] = Callbacks[i][nArray[i]].getAddress();
            }
            // ** MonitorExit[var3_3] (shouldn't be in output)
            long l2 = OS.GlobalAlloc(64, C.PTR_SIZEOF * nArray.length);
            OS.MoveMemory(l2, lArray, C.PTR_SIZEOF * nArray.length);
            this.ppVtable = OS.GlobalAlloc(64, C.PTR_SIZEOF);
            OS.MoveMemory(this.ppVtable, new long[]{l2}, C.PTR_SIZEOF);
            ObjectMap.put(new LONG(this.ppVtable), this);
            return;
        }
    }

    public static GUID IIDFromString(String string) {
        GUID gUID;
        char[] cArray = string.toCharArray();
        if (COM.IIDFromString(cArray, gUID = new GUID()) == 0) {
            return gUID;
        }
        return null;
    }

    static long callback0(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method0(lArray2);
    }

    static long callback1(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method1(lArray2);
    }

    static long callback2(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method2(lArray2);
    }

    static long callback3(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method3(lArray2);
    }

    static long callback4(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method4(lArray2);
    }

    static long callback5(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method5(lArray2);
    }

    static long callback6(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method6(lArray2);
    }

    static long callback7(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method7(lArray2);
    }

    static long callback8(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method8(lArray2);
    }

    static long callback9(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method9(lArray2);
    }

    static long callback10(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method10(lArray2);
    }

    static long callback11(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method11(lArray2);
    }

    static long callback12(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method12(lArray2);
    }

    static long callback13(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method13(lArray2);
    }

    static long callback14(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method14(lArray2);
    }

    static long callback15(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method15(lArray2);
    }

    static long callback16(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method16(lArray2);
    }

    static long callback17(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method17(lArray2);
    }

    static long callback18(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method18(lArray2);
    }

    static long callback19(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method19(lArray2);
    }

    static long callback20(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method20(lArray2);
    }

    static long callback21(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method21(lArray2);
    }

    static long callback22(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method22(lArray2);
    }

    static long callback23(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method23(lArray2);
    }

    static long callback24(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method24(lArray2);
    }

    static long callback25(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method25(lArray2);
    }

    static long callback26(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method26(lArray2);
    }

    static long callback27(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method27(lArray2);
    }

    static long callback28(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method28(lArray2);
    }

    static long callback29(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method29(lArray2);
    }

    static long callback30(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method30(lArray2);
    }

    static long callback31(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method31(lArray2);
    }

    static long callback32(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method32(lArray2);
    }

    static long callback33(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method33(lArray2);
    }

    static long callback34(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method34(lArray2);
    }

    static long callback35(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method35(lArray2);
    }

    static long callback36(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method36(lArray2);
    }

    static long callback37(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method37(lArray2);
    }

    static long callback38(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method38(lArray2);
    }

    static long callback39(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method39(lArray2);
    }

    static long callback40(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method40(lArray2);
    }

    static long callback41(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method41(lArray2);
    }

    static long callback42(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method42(lArray2);
    }

    static long callback43(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method43(lArray2);
    }

    static long callback44(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method44(lArray2);
    }

    static long callback45(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method45(lArray2);
    }

    static long callback46(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method46(lArray2);
    }

    static long callback47(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method47(lArray2);
    }

    static long callback48(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method48(lArray2);
    }

    static long callback49(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method49(lArray2);
    }

    static long callback50(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method50(lArray2);
    }

    static long callback51(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method51(lArray2);
    }

    static long callback52(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method52(lArray2);
    }

    static long callback53(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method53(lArray2);
    }

    static long callback54(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method54(lArray2);
    }

    static long callback55(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method55(lArray2);
    }

    static long callback56(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method56(lArray2);
    }

    static long callback57(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method57(lArray2);
    }

    static long callback58(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method58(lArray2);
    }

    static long callback59(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method59(lArray2);
    }

    static long callback60(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method60(lArray2);
    }

    static long callback61(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method61(lArray2);
    }

    static long callback62(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method62(lArray2);
    }

    static long callback63(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method63(lArray2);
    }

    static long callback64(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method64(lArray2);
    }

    static long callback65(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method65(lArray2);
    }

    static long callback66(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method66(lArray2);
    }

    static long callback67(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method67(lArray2);
    }

    static long callback68(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method68(lArray2);
    }

    static long callback69(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method69(lArray2);
    }

    static long callback70(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method70(lArray2);
    }

    static long callback71(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method71(lArray2);
    }

    static long callback72(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method72(lArray2);
    }

    static long callback73(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method73(lArray2);
    }

    static long callback74(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method74(lArray2);
    }

    static long callback75(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method75(lArray2);
    }

    static long callback76(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method76(lArray2);
    }

    static long callback77(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method77(lArray2);
    }

    static long callback78(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method78(lArray2);
    }

    static long callback79(long[] lArray) {
        long l2 = lArray[0];
        COMObject cOMObject = (COMObject)ObjectMap.get(new LONG(l2));
        if (cOMObject == null) {
            return -2147467259L;
        }
        long[] lArray2 = new long[lArray.length - 1];
        System.arraycopy(lArray, 1, lArray2, 0, lArray2.length);
        return cOMObject.method79(lArray2);
    }

    public void dispose() {
        long[] lArray = new long[]{0L};
        OS.MoveMemory(lArray, this.ppVtable, C.PTR_SIZEOF);
        OS.GlobalFree(lArray[0]);
        OS.GlobalFree(this.ppVtable);
        ObjectMap.remove(new LONG(this.ppVtable));
        this.ppVtable = 0L;
    }

    public long getAddress() {
        return this.ppVtable;
    }

    public long method0(long[] lArray) {
        return -2147467263L;
    }

    public long method1(long[] lArray) {
        return -2147467263L;
    }

    public long method2(long[] lArray) {
        return -2147467263L;
    }

    public long method3(long[] lArray) {
        return -2147467263L;
    }

    public long method4(long[] lArray) {
        return -2147467263L;
    }

    public long method5(long[] lArray) {
        return -2147467263L;
    }

    public long method6(long[] lArray) {
        return -2147467263L;
    }

    public long method7(long[] lArray) {
        return -2147467263L;
    }

    public long method8(long[] lArray) {
        return -2147467263L;
    }

    public long method9(long[] lArray) {
        return -2147467263L;
    }

    public long method10(long[] lArray) {
        return -2147467263L;
    }

    public long method11(long[] lArray) {
        return -2147467263L;
    }

    public long method12(long[] lArray) {
        return -2147467263L;
    }

    public long method13(long[] lArray) {
        return -2147467263L;
    }

    public long method14(long[] lArray) {
        return -2147467263L;
    }

    public long method15(long[] lArray) {
        return -2147467263L;
    }

    public long method16(long[] lArray) {
        return -2147467263L;
    }

    public long method17(long[] lArray) {
        return -2147467263L;
    }

    public long method18(long[] lArray) {
        return -2147467263L;
    }

    public long method19(long[] lArray) {
        return -2147467263L;
    }

    public long method20(long[] lArray) {
        return -2147467263L;
    }

    public long method21(long[] lArray) {
        return -2147467263L;
    }

    public long method22(long[] lArray) {
        return -2147467263L;
    }

    public long method23(long[] lArray) {
        return -2147467263L;
    }

    public long method24(long[] lArray) {
        return -2147467263L;
    }

    public long method25(long[] lArray) {
        return -2147467263L;
    }

    public long method26(long[] lArray) {
        return -2147467263L;
    }

    public long method27(long[] lArray) {
        return -2147467263L;
    }

    public long method28(long[] lArray) {
        return -2147467263L;
    }

    public long method29(long[] lArray) {
        return -2147467263L;
    }

    public long method30(long[] lArray) {
        return -2147467263L;
    }

    public long method31(long[] lArray) {
        return -2147467263L;
    }

    public long method32(long[] lArray) {
        return -2147467263L;
    }

    public long method33(long[] lArray) {
        return -2147467263L;
    }

    public long method34(long[] lArray) {
        return -2147467263L;
    }

    public long method35(long[] lArray) {
        return -2147467263L;
    }

    public long method36(long[] lArray) {
        return -2147467263L;
    }

    public long method37(long[] lArray) {
        return -2147467263L;
    }

    public long method38(long[] lArray) {
        return -2147467263L;
    }

    public long method39(long[] lArray) {
        return -2147467263L;
    }

    public long method40(long[] lArray) {
        return -2147467263L;
    }

    public long method41(long[] lArray) {
        return -2147467263L;
    }

    public long method42(long[] lArray) {
        return -2147467263L;
    }

    public long method43(long[] lArray) {
        return -2147467263L;
    }

    public long method44(long[] lArray) {
        return -2147467263L;
    }

    public long method45(long[] lArray) {
        return -2147467263L;
    }

    public long method46(long[] lArray) {
        return -2147467263L;
    }

    public long method47(long[] lArray) {
        return -2147467263L;
    }

    public long method48(long[] lArray) {
        return -2147467263L;
    }

    public long method49(long[] lArray) {
        return -2147467263L;
    }

    public long method50(long[] lArray) {
        return -2147467263L;
    }

    public long method51(long[] lArray) {
        return -2147467263L;
    }

    public long method52(long[] lArray) {
        return -2147467263L;
    }

    public long method53(long[] lArray) {
        return -2147467263L;
    }

    public long method54(long[] lArray) {
        return -2147467263L;
    }

    public long method55(long[] lArray) {
        return -2147467263L;
    }

    public long method56(long[] lArray) {
        return -2147467263L;
    }

    public long method57(long[] lArray) {
        return -2147467263L;
    }

    public long method58(long[] lArray) {
        return -2147467263L;
    }

    public long method59(long[] lArray) {
        return -2147467263L;
    }

    public long method60(long[] lArray) {
        return -2147467263L;
    }

    public long method61(long[] lArray) {
        return -2147467263L;
    }

    public long method62(long[] lArray) {
        return -2147467263L;
    }

    public long method63(long[] lArray) {
        return -2147467263L;
    }

    public long method64(long[] lArray) {
        return -2147467263L;
    }

    public long method65(long[] lArray) {
        return -2147467263L;
    }

    public long method66(long[] lArray) {
        return -2147467263L;
    }

    public long method67(long[] lArray) {
        return -2147467263L;
    }

    public long method68(long[] lArray) {
        return -2147467263L;
    }

    public long method69(long[] lArray) {
        return -2147467263L;
    }

    public long method70(long[] lArray) {
        return -2147467263L;
    }

    public long method71(long[] lArray) {
        return -2147467263L;
    }

    public long method72(long[] lArray) {
        return -2147467263L;
    }

    public long method73(long[] lArray) {
        return -2147467263L;
    }

    public long method74(long[] lArray) {
        return -2147467263L;
    }

    public long method75(long[] lArray) {
        return -2147467263L;
    }

    public long method76(long[] lArray) {
        return -2147467263L;
    }

    public long method77(long[] lArray) {
        return -2147467263L;
    }

    public long method78(long[] lArray) {
        return -2147467263L;
    }

    public long method79(long[] lArray) {
        return -2147467263L;
    }
}

