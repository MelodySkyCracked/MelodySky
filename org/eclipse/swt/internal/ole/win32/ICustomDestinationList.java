/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IObjectArray;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICustomDestinationList
extends IUnknown {
    public ICustomDestinationList(long l2) {
        super(l2);
    }

    public int SetAppID(char[] cArray) {
        return COM.VtblCall(3, this.address, cArray);
    }

    public int BeginList(int[] nArray, GUID gUID, long[] lArray) {
        return COM.VtblCall(4, this.address, nArray, gUID, lArray);
    }

    public int AppendCategory(char[] cArray, IObjectArray iObjectArray) {
        return COM.VtblCall(5, this.address, cArray, iObjectArray.address);
    }

    public int AddUserTasks(IUnknown iUnknown) {
        return COM.VtblCall(7, this.address, iUnknown.address);
    }

    public int CommitList() {
        return COM.VtblCall(8, this.address);
    }

    public int DeleteList(char[] cArray) {
        return COM.VtblCall(10, this.address, cArray);
    }
}

