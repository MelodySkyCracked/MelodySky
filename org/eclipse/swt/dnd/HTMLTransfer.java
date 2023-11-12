/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.OS;

public class HTMLTransfer
extends ByteArrayTransfer {
    static HTMLTransfer _instance = new HTMLTransfer();
    static final String HTML_FORMAT = "HTML Format";
    static final int HTML_FORMATID = Transfer.registerType("HTML Format");
    static final String NUMBER = "00000000";
    static final String HEADER = "Version:0.9\r\nStartHTML:00000000\r\nEndHTML:00000000\r\nStartFragment:00000000\r\nEndFragment:00000000\r\n";
    static final String PREFIX = "<html><body><!--StartFragment-->";
    static final String SUFFIX = "<!--EndFragment--></body></html>";
    static final String StartFragment = "StartFragment:";
    static final String EndFragment = "EndFragment:";

    private HTMLTransfer() {
    }

    public static HTMLTransfer getInstance() {
        return _instance;
    }

    @Override
    public void javaToNative(Object object, TransferData transferData) {
        HTMLTransfer hTMLTransfer = this;
        if (object == false || !this.isSupportedType(transferData)) {
            DND.error(2003);
        }
        String string = (String)object;
        int n = string.length();
        char[] cArray = new char[n + 1];
        string.getChars(0, n, cArray, 0);
        int n2 = OS.WideCharToMultiByte(65001, 0, cArray, -1, null, 0, null, null);
        if (n2 == 0) {
            transferData.stgmedium = new STGMEDIUM();
            transferData.result = -2147221402;
            return;
        }
        int n3 = 97;
        int n4 = n3 + 32;
        int n5 = n4 + n2 - 1;
        int n6 = n5 + 32;
        StringBuilder stringBuilder = new StringBuilder(HEADER);
        int n7 = 8;
        int n8 = stringBuilder.toString().indexOf(NUMBER);
        String string2 = Integer.toString(n3);
        stringBuilder.replace(n8 + n7 - string2.length(), n8 + n7, string2);
        n8 = stringBuilder.toString().indexOf(NUMBER, n8);
        string2 = Integer.toString(n6);
        stringBuilder.replace(n8 + n7 - string2.length(), n8 + n7, string2);
        n8 = stringBuilder.toString().indexOf(NUMBER, n8);
        string2 = Integer.toString(n4);
        stringBuilder.replace(n8 + n7 - string2.length(), n8 + n7, string2);
        n8 = stringBuilder.toString().indexOf(NUMBER, n8);
        string2 = Integer.toString(n5);
        stringBuilder.replace(n8 + n7 - string2.length(), n8 + n7, string2);
        stringBuilder.append(PREFIX);
        stringBuilder.append(string);
        stringBuilder.append(SUFFIX);
        n = stringBuilder.length();
        cArray = new char[n + 1];
        stringBuilder.getChars(0, n, cArray, 0);
        n2 = OS.WideCharToMultiByte(65001, 0, cArray, -1, null, 0, null, null);
        long l2 = OS.GlobalAlloc(64, n2);
        OS.WideCharToMultiByte(65001, 0, cArray, -1, l2, n2, null, null);
        transferData.stgmedium = new STGMEDIUM();
        transferData.stgmedium.tymed = 1;
        transferData.stgmedium.unionField = l2;
        transferData.stgmedium.pUnkForRelease = 0L;
        transferData.result = 0;
    }

    @Override
    public Object nativeToJava(TransferData transferData) {
        String string;
        int n;
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
        int n2 = OS.MultiByteToWideChar(65001, 0, l3, -1, null, 0);
        if (n2 == 0) {
            Object var10_9 = null;
            OS.GlobalUnlock(l2);
            OS.GlobalFree(l2);
            return var10_9;
        }
        char[] cArray = new char[n2 - 1];
        OS.MultiByteToWideChar(65001, 0, l3, -1, cArray, cArray.length);
        String string2 = new String(cArray);
        int n3 = 0;
        int n4 = 0;
        int n5 = string2.indexOf(StartFragment) + 14;
        for (n = n5 + 1; n < string2.length(); ++n) {
            string = string2.substring(n5, n);
            try {
                n3 = Integer.parseInt(string);
                continue;
            }
            catch (NumberFormatException numberFormatException) {
                break;
            }
        }
        n5 = string2.indexOf(EndFragment) + 12;
        for (n = n5 + 1; n < string2.length(); ++n) {
            string = string2.substring(n5, n);
            try {
                n4 = Integer.parseInt(string);
                continue;
            }
            catch (NumberFormatException numberFormatException) {
                break;
            }
        }
        if (n4 <= n3 || n4 > C.strlen(l3)) {
            string = null;
            OS.GlobalUnlock(l2);
            OS.GlobalFree(l2);
            return string;
        }
        n2 = OS.MultiByteToWideChar(65001, 0, l3 + (long)n3, n4 - n3, cArray, cArray.length);
        if (n2 == 0) {
            string = null;
            OS.GlobalUnlock(l2);
            OS.GlobalFree(l2);
            return string;
        }
        string = new String(cArray, 0, n2);
        String string3 = "<!--StartFragment -->\r\n";
        int n6 = string.indexOf("<!--StartFragment -->\r\n");
        if (n6 != -1) {
            string = string.substring(n6 += 23);
        }
        String string4 = string;
        OS.GlobalUnlock(l2);
        OS.GlobalFree(l2);
        return string4;
    }

    @Override
    protected int[] getTypeIds() {
        return new int[]{HTML_FORMATID};
    }

    @Override
    protected String[] getTypeNames() {
        return new String[]{HTML_FORMAT};
    }

    @Override
    protected boolean validate(Object object) {
        return this.checkHTML(object);
    }
}

