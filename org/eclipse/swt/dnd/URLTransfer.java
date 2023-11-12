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

public class URLTransfer
extends ByteArrayTransfer {
    static URLTransfer _instance = new URLTransfer();
    static final String CFSTR_INETURLW = "UniformResourceLocatorW";
    static final int CFSTR_INETURLIDW = Transfer.registerType("UniformResourceLocatorW");
    static final String CFSTR_INETURL = "UniformResourceLocator";
    static final int CFSTR_INETURLID = Transfer.registerType("UniformResourceLocator");

    private URLTransfer() {
    }

    public static URLTransfer getInstance() {
        return _instance;
    }

    @Override
    public void javaToNative(Object object, TransferData transferData) {
        URLTransfer uRLTransfer = this;
        if (object == null || !this.isSupportedType(transferData)) {
            DND.error(2003);
        }
        transferData.result = -2147467259;
        String string = (String)object;
        if (transferData.type == CFSTR_INETURLIDW) {
            int n = string.length();
            char[] cArray = new char[n + 1];
            string.getChars(0, n, cArray, 0);
            int n2 = cArray.length * 2;
            long l2 = OS.GlobalAlloc(64, n2);
            OS.MoveMemory(l2, cArray, n2);
            transferData.stgmedium = new STGMEDIUM();
            transferData.stgmedium.tymed = 1;
            transferData.stgmedium.unionField = l2;
            transferData.stgmedium.pUnkForRelease = 0L;
            transferData.result = 0;
        } else if (transferData.type == CFSTR_INETURLID) {
            int n = string.length();
            char[] cArray = new char[n + 1];
            string.getChars(0, n, cArray, 0);
            int n3 = OS.GetACP();
            int n4 = OS.WideCharToMultiByte(n3, 0, cArray, -1, null, 0, null, null);
            if (n4 == 0) {
                transferData.stgmedium = new STGMEDIUM();
                transferData.result = -2147221402;
                return;
            }
            long l3 = OS.GlobalAlloc(64, n4);
            OS.WideCharToMultiByte(n3, 0, cArray, -1, l3, n4, null, null);
            transferData.stgmedium = new STGMEDIUM();
            transferData.stgmedium.tymed = 1;
            transferData.stgmedium.unionField = l3;
            transferData.stgmedium.pUnkForRelease = 0L;
            transferData.result = 0;
        }
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
        if (transferData.type == CFSTR_INETURLIDW) {
            int n = OS.GlobalSize(l2) / 2 * 2;
            if (n == 0) {
                Object var8_8 = null;
                OS.GlobalFree(l2);
                return var8_8;
            }
            char[] cArray = new char[n / 2];
            long l3 = OS.GlobalLock(l2);
            if (l3 == 0L) {
                Object var11_13 = null;
                OS.GlobalFree(l2);
                return var11_13;
            }
            OS.MoveMemory(cArray, l3, n);
            int n2 = cArray.length;
            for (int i = 0; i < cArray.length; ++i) {
                if (cArray[i] != '\u0000') continue;
                n2 = i;
                break;
            }
            String string = new String(cArray, 0, n2);
            OS.GlobalUnlock(l2);
            OS.GlobalFree(l2);
            return string;
        }
        if (transferData.type == CFSTR_INETURLID) {
            long l4 = OS.GlobalLock(l2);
            if (l4 == 0L) {
                Object var9_11 = null;
                OS.GlobalFree(l2);
                return var9_11;
            }
            int n = OS.GetACP();
            int n3 = OS.MultiByteToWideChar(n, 1, l4, -1, null, 0);
            if (n3 == 0) {
                Object var11_15 = null;
                OS.GlobalUnlock(l2);
                OS.GlobalFree(l2);
                return var11_15;
            }
            char[] cArray = new char[n3 - 1];
            OS.MultiByteToWideChar(n, 1, l4, -1, cArray, cArray.length);
            String string = new String(cArray);
            OS.GlobalUnlock(l2);
            OS.GlobalFree(l2);
            return string;
        }
        OS.GlobalFree(l2);
        return null;
    }

    @Override
    protected int[] getTypeIds() {
        return new int[]{CFSTR_INETURLIDW, CFSTR_INETURLID};
    }

    @Override
    protected String[] getTypeNames() {
        return new String[]{CFSTR_INETURLW, CFSTR_INETURL};
    }

    @Override
    protected boolean validate(Object object) {
        return this.checkURL(object);
    }
}

