/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;

public class IDataObject
extends IUnknown {
    public IDataObject(long l2) {
        super(l2);
    }

    public int EnumFormatEtc(int n, long[] lArray) {
        return COM.VtblCall(8, this.address, n, lArray);
    }

    public int GetData(FORMATETC fORMATETC, STGMEDIUM sTGMEDIUM) {
        return COM.VtblCall(3, this.address, fORMATETC, sTGMEDIUM);
    }

    public int QueryGetData(FORMATETC fORMATETC) {
        return COM.VtblCall(5, this.address, fORMATETC);
    }
}

