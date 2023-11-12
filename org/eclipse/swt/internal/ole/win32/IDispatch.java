/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.DISPPARAMS;
import org.eclipse.swt.internal.ole.win32.EXCEPINFO;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.OS;

public class IDispatch
extends IUnknown {
    public IDispatch(long l2) {
        super(l2);
    }

    public int GetIDsOfNames(GUID gUID, String[] stringArray, int n, int n2, int[] nArray) {
        int n3;
        int n4 = stringArray.length;
        long l2 = OS.GetProcessHeap();
        long l3 = OS.HeapAlloc(l2, 8, n4 * C.PTR_SIZEOF);
        long[] lArray = new long[n4];
        for (n3 = 0; n3 < n4; ++n3) {
            int n5 = stringArray[n3].length();
            char[] cArray = new char[n5 + 1];
            stringArray[n3].getChars(0, n5, cArray, 0);
            long l4 = OS.HeapAlloc(l2, 8, cArray.length * 2);
            OS.MoveMemory(l4, cArray, cArray.length * 2);
            OS.MoveMemory(l3 + (long)(C.PTR_SIZEOF * n3), new long[]{l4}, C.PTR_SIZEOF);
            lArray[n3] = l4;
        }
        n3 = COM.VtblCall(5, this.address, new GUID(), l3, n, n2, nArray);
        for (long l5 : lArray) {
            OS.HeapFree(l2, 0, l5);
        }
        OS.HeapFree(l2, 0, l3);
        return n3;
    }

    public int GetTypeInfo(int n, int n2, long[] lArray) {
        return COM.VtblCall(4, this.address, (long)n, n2, lArray);
    }

    public int Invoke(int n, GUID gUID, int n2, int n3, DISPPARAMS dISPPARAMS, long l2, EXCEPINFO eXCEPINFO, int[] nArray) {
        return COM.VtblCall(6, this.address, n, gUID, n2, n3, dISPPARAMS, l2, eXCEPINFO, nArray);
    }
}

