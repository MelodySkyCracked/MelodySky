/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;

public abstract class Transfer {
    private static final int RETRY_LIMIT = 10;

    int getData(IDataObject iDataObject, FORMATETC fORMATETC, STGMEDIUM sTGMEDIUM) {
        if (iDataObject.GetData(fORMATETC, sTGMEDIUM) == 0) {
            return 0;
        }
        try {
            Thread.sleep(50L);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        int n = iDataObject.GetData(fORMATETC, sTGMEDIUM);
        int n2 = 0;
        while (n != 0 && n2++ < 10) {
            MSG mSG = new MSG();
            OS.PeekMessage(mSG, 0L, 0, 0, 2);
            try {
                Thread.sleep(50L);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            n = iDataObject.GetData(fORMATETC, sTGMEDIUM);
        }
        return n;
    }

    public abstract TransferData[] getSupportedTypes();

    public abstract boolean isSupportedType(TransferData var1);

    protected abstract int[] getTypeIds();

    protected abstract String[] getTypeNames();

    protected abstract void javaToNative(Object var1, TransferData var2);

    protected abstract Object nativeToJava(TransferData var1);

    public static int registerType(String string) {
        TCHAR tCHAR = new TCHAR(0, string, true);
        return OS.RegisterClipboardFormat(tCHAR);
    }

    protected boolean validate(Object object) {
        return true;
    }
}

