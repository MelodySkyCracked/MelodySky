/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.OS;

public class RTFTransfer
extends ByteArrayTransfer {
    private static RTFTransfer _instance = new RTFTransfer();
    private static final String CF_RTF = "Rich Text Format";
    private static final int CF_RTFID = Transfer.registerType("Rich Text Format");

    private RTFTransfer() {
    }

    public static RTFTransfer getInstance() {
        return _instance;
    }

    @Override
    public void javaToNative(Object object, TransferData transferData) {
        RTFTransfer rTFTransfer = this;
        if (object == false || !this.isSupportedType(transferData)) {
            DND.error(2003);
        }
        String string = (String)object;
        int n = string.length();
        char[] cArray = new char[n + 1];
        string.getChars(0, n, cArray, 0);
        int n2 = OS.GetACP();
        int n3 = OS.WideCharToMultiByte(n2, 0, cArray, -1, null, 0, null, null);
        if (n3 == 0) {
            transferData.stgmedium = new STGMEDIUM();
            transferData.result = -2147221402;
            return;
        }
        long l2 = OS.GlobalAlloc(64, n3);
        OS.WideCharToMultiByte(n2, 0, cArray, -1, l2, n3, null, null);
        transferData.stgmedium = new STGMEDIUM();
        transferData.stgmedium.tymed = 1;
        transferData.stgmedium.unionField = l2;
        transferData.stgmedium.pUnkForRelease = 0L;
        transferData.result = 0;
    }

    @Override
    public Object nativeToJava(TransferData transferData) {
        if (!this.isSupportedType(transferData) || transferData.pIDataObject == 0L) {
            return null;
        }
        IDataObject iDataObject = new IDataObject(transferData.pIDataObject);
        iDataObject.AddRef();
        STGMEDIUM sTGMEDIUM = new STGMEDIUM();
        FORMATETC fORMATETC = transferData.formatetc;
        sTGMEDIUM.tymed = 1;
        transferData.result = this.getData(iDataObject, fORMATETC, sTGMEDIUM);
        iDataObject.Release();
        if (transferData.result != 0) {
            return null;
        }
        long l2 = sTGMEDIUM.unionField;
        long l3 = OS.GlobalLock(l2);
        if (l3 == 0L) {
            Object var9_7 = null;
            OS.GlobalFree(l2);
            return var9_7;
        }
        int n = OS.GetACP();
        int n2 = OS.MultiByteToWideChar(n, 1, l3, -1, null, 0);
        if (n2 == 0) {
            Object var11_10 = null;
            OS.GlobalUnlock(l2);
            OS.GlobalFree(l2);
            return var11_10;
        }
        char[] cArray = new char[n2 - 1];
        OS.MultiByteToWideChar(n, 1, l3, -1, cArray, cArray.length);
        String string = new String(cArray);
        OS.GlobalUnlock(l2);
        OS.GlobalFree(l2);
        return string;
    }

    @Override
    protected int[] getTypeIds() {
        return new int[]{CF_RTFID};
    }

    @Override
    protected String[] getTypeNames() {
        return new String[]{CF_RTF};
    }

    @Override
    protected boolean validate(Object object) {
        return this.checkRTF(object);
    }
}

