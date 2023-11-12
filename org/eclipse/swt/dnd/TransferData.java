/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;

public class TransferData {
    public int type;
    public FORMATETC formatetc;
    public STGMEDIUM stgmedium;
    public int result = -2147467259;
    public long pIDataObject;

    static boolean sameType(TransferData transferData, TransferData transferData2) {
        return transferData == transferData2 || transferData != null && transferData2 != null && transferData.type == transferData2.type && transferData.formatetc.cfFormat == transferData2.formatetc.cfFormat && transferData.formatetc.dwAspect == transferData2.formatetc.dwAspect && transferData.formatetc.tymed == transferData2.formatetc.tymed;
    }
}

