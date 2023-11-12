/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.OleEnumFORMATETC;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.dnd.l;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.IEnumFORMATETC;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Display;

public class Clipboard {
    private static final int RETRY_LIMIT = 10;
    private Display display;
    private COMObject iDataObject;
    private int refCount;
    private Transfer[] transferAgents = new Transfer[0];
    private Object[] data = new Object[0];
    private int CFSTR_PREFERREDDROPEFFECT;

    public Clipboard(Display display) {
        this.checkSubclass();
        if (display == null && (display = Display.getCurrent()) == null) {
            display = Display.getDefault();
        }
        if (display.getThread() != Thread.currentThread()) {
            DND.error(22);
        }
        this.display = display;
        TCHAR tCHAR = new TCHAR(0, "Preferred DropEffect", true);
        this.CFSTR_PREFERREDDROPEFFECT = OS.RegisterClipboardFormat(tCHAR);
        this.createCOMInterfaces();
        this.AddRef();
    }

    protected void checkSubclass() {
        String string = this.getClass().getName();
        String string2 = Clipboard.class.getName();
        if (!string2.equals(string)) {
            DND.error(43);
        }
    }

    protected void checkWidget() {
        Display display = this.display;
        if (display == null) {
            DND.error(24);
        }
        if (display.getThread() != Thread.currentThread()) {
            DND.error(22);
        }
        if (display.isDisposed()) {
            DND.error(24);
        }
    }

    public void clearContents() {
        this.clearContents(1);
    }

    public void clearContents(int n) {
        this.checkWidget();
        if ((n & 1) != 0 && COM.OleIsCurrentClipboard(this.iDataObject.getAddress()) == 0) {
            COM.OleSetClipboard(0L);
        }
    }

    public void dispose() {
        if (this == null) {
            return;
        }
        if (this.display.getThread() != Thread.currentThread()) {
            DND.error(22);
        }
        int n = 0;
        if (COM.OleIsCurrentClipboard(this.iDataObject.getAddress()) == 0) {
            n = COM.OleFlushClipboard();
        }
        int n2 = 0;
        while (n != 0 && n2++ < 10) {
            try {
                Thread.sleep(25L);
            }
            catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                break;
            }
            if (COM.OleIsCurrentClipboard(this.iDataObject.getAddress()) != 0) break;
            MSG mSG = new MSG();
            OS.PeekMessage(mSG, 0L, 0, 0, 2);
            n = COM.OleFlushClipboard();
        }
        this.Release();
        this.display = null;
    }

    public Object getContents(Transfer transfer) {
        return this.getContents(transfer, 1);
    }

    public Object getContents(Transfer transfer, int n) {
        this.checkWidget();
        if (transfer == null) {
            DND.error(4);
        }
        if ((n & 1) == 0) {
            return null;
        }
        long[] lArray = new long[]{0L};
        int n2 = 0;
        int n3 = COM.OleGetClipboard(lArray);
        while (n3 != 0 && n2++ < 10) {
            try {
                Thread.sleep(50L);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            MSG mSG = new MSG();
            OS.PeekMessage(mSG, 0L, 0, 0, 2);
            n3 = COM.OleGetClipboard(lArray);
        }
        if (n3 != 0) {
            return null;
        }
        IDataObject iDataObject = new IDataObject(lArray[0]);
        for (TransferData transferData : transfer.getSupportedTypes()) {
            if (iDataObject.QueryGetData(transferData.formatetc) != 0) continue;
            transferData.pIDataObject = lArray[0];
            Object object = transfer.nativeToJava(transferData);
            iDataObject.Release();
            return object;
        }
        iDataObject.Release();
        return null;
    }

    public void setContents(Object[] objectArray, Transfer[] transferArray) {
        this.setContents(objectArray, transferArray, 1);
    }

    public void setContents(Object[] objectArray, Transfer[] transferArray, int n) {
        int n2;
        this.checkWidget();
        if (objectArray == null || transferArray == null || objectArray.length != transferArray.length || objectArray.length == 0) {
            DND.error(5);
        }
        for (n2 = 0; n2 < objectArray.length; ++n2) {
            if (objectArray[n2] != null && transferArray[n2] != null && transferArray[n2].validate(objectArray[n2])) continue;
            DND.error(5);
        }
        if ((n & 1) == 0) {
            return;
        }
        this.data = objectArray;
        this.transferAgents = transferArray;
        n2 = COM.OleSetClipboard(this.iDataObject.getAddress());
        int n3 = 0;
        while (n2 != 0 && n3++ < 10) {
            try {
                Thread.sleep(50L);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            MSG mSG = new MSG();
            OS.PeekMessage(mSG, 0L, 0, 0, 2);
            n2 = COM.OleSetClipboard(this.iDataObject.getAddress());
        }
        if (n2 != 0) {
            DND.error(2002);
        }
    }

    private int AddRef() {
        return ++this.refCount;
    }

    private void createCOMInterfaces() {
        this.iDataObject = new l(this, new int[]{2, 0, 0, 2, 2, 1, 2, 3, 2, 4, 1, 1});
    }

    private void disposeCOMInterfaces() {
        if (this.iDataObject != null) {
            this.iDataObject.dispose();
        }
        this.iDataObject = null;
    }

    private int EnumFormatEtc(int n, long l2) {
        if (n == 2) {
            return -2147467263;
        }
        TransferData[] transferDataArray = new TransferData[]{};
        for (Transfer transfer : this.transferAgents) {
            TransferData[] transferDataArray2 = transfer.getSupportedTypes();
            TransferData[] transferDataArray3 = new TransferData[transferDataArray.length + transferDataArray2.length];
            System.arraycopy(transferDataArray, 0, transferDataArray3, 0, transferDataArray.length);
            System.arraycopy(transferDataArray2, 0, transferDataArray3, transferDataArray.length, transferDataArray2.length);
            transferDataArray = transferDataArray3;
        }
        OleEnumFORMATETC oleEnumFORMATETC = new OleEnumFORMATETC();
        oleEnumFORMATETC.AddRef();
        FORMATETC[] fORMATETCArray = new FORMATETC[transferDataArray.length + 1];
        for (int i = 0; i < transferDataArray.length; ++i) {
            fORMATETCArray[i] = transferDataArray[i].formatetc;
        }
        FORMATETC fORMATETC = new FORMATETC();
        fORMATETC.cfFormat = this.CFSTR_PREFERREDDROPEFFECT;
        fORMATETC.dwAspect = 1;
        fORMATETC.lindex = -1;
        fORMATETC.tymed = 1;
        fORMATETCArray[fORMATETCArray.length - 1] = fORMATETC;
        oleEnumFORMATETC.setFormats(fORMATETCArray);
        OS.MoveMemory(l2, new long[]{oleEnumFORMATETC.getAddress()}, C.PTR_SIZEOF);
        return 0;
    }

    private int GetData(long l2, long l3) {
        if (l2 == 0L || l3 == 0L) {
            return -2147024809;
        }
        if (this.QueryGetData(l2) != 0) {
            return -2147221404;
        }
        TransferData transferData = new TransferData();
        transferData.formatetc = new FORMATETC();
        COM.MoveMemory(transferData.formatetc, l2, FORMATETC.sizeof);
        transferData.type = transferData.formatetc.cfFormat;
        transferData.stgmedium = new STGMEDIUM();
        transferData.result = -2147467259;
        if (transferData.type == this.CFSTR_PREFERREDDROPEFFECT) {
            STGMEDIUM sTGMEDIUM = new STGMEDIUM();
            sTGMEDIUM.tymed = 1;
            sTGMEDIUM.unionField = OS.GlobalAlloc(64, 4);
            OS.MoveMemory(sTGMEDIUM.unionField, new int[]{1}, 4);
            sTGMEDIUM.pUnkForRelease = 0L;
            COM.MoveMemory(l3, sTGMEDIUM, STGMEDIUM.sizeof);
            return 0;
        }
        int n = -1;
        for (int i = 0; i < this.transferAgents.length; ++i) {
            if (!this.transferAgents[i].isSupportedType(transferData)) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return -2147221404;
        }
        this.transferAgents[n].javaToNative(this.data[n], transferData);
        COM.MoveMemory(l3, transferData.stgmedium, STGMEDIUM.sizeof);
        return transferData.result;
    }

    private int QueryGetData(long l2) {
        if (this.transferAgents == null) {
            return -2147467259;
        }
        TransferData transferData = new TransferData();
        transferData.formatetc = new FORMATETC();
        COM.MoveMemory(transferData.formatetc, l2, FORMATETC.sizeof);
        transferData.type = transferData.formatetc.cfFormat;
        if (transferData.type == this.CFSTR_PREFERREDDROPEFFECT) {
            return 0;
        }
        for (Transfer transfer : this.transferAgents) {
            if (!transfer.isSupportedType(transferData)) continue;
            return 0;
        }
        return -2147221404;
    }

    private int QueryInterface(long l2, long l3) {
        if (l2 == 0L || l3 == 0L) {
            return -2147024809;
        }
        GUID gUID = new GUID();
        COM.MoveMemory(gUID, l2, GUID.sizeof);
        if (COM.IsEqualGUID(gUID, COM.IIDIUnknown) || COM.IsEqualGUID(gUID, COM.IIDIDataObject)) {
            OS.MoveMemory(l3, new long[]{this.iDataObject.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        OS.MoveMemory(l3, new long[]{0L}, C.PTR_SIZEOF);
        return -2147467262;
    }

    private int Release() {
        --this.refCount;
        if (this.refCount == 0) {
            this.data = new Object[0];
            this.transferAgents = new Transfer[0];
            this.disposeCOMInterfaces();
            if (COM.FreeUnusedLibraries) {
                COM.CoFreeUnusedLibraries();
            }
        }
        return this.refCount;
    }

    public TransferData[] getAvailableTypes() {
        return this.getAvailableTypes(1);
    }

    public TransferData[] getAvailableTypes(int n) {
        this.checkWidget();
        if ((n & 1) == 0) {
            return new TransferData[0];
        }
        FORMATETC[] fORMATETCArray = this._getAvailableTypes();
        TransferData[] transferDataArray = new TransferData[fORMATETCArray.length];
        for (int i = 0; i < fORMATETCArray.length; ++i) {
            transferDataArray[i] = new TransferData();
            transferDataArray[i].type = fORMATETCArray[i].cfFormat;
            transferDataArray[i].formatetc = fORMATETCArray[i];
        }
        return transferDataArray;
    }

    public String[] getAvailableTypeNames() {
        this.checkWidget();
        FORMATETC[] fORMATETCArray = this._getAvailableTypes();
        String[] stringArray = new String[fORMATETCArray.length];
        int n = 128;
        block19: for (int i = 0; i < fORMATETCArray.length; ++i) {
            char[] cArray = new char[128];
            int n2 = OS.GetClipboardFormatName(fORMATETCArray[i].cfFormat, cArray, 128);
            if (n2 != 0) {
                stringArray[i] = new String(cArray, 0, n2);
                continue;
            }
            switch (fORMATETCArray[i].cfFormat) {
                case 15: {
                    stringArray[i] = "CF_HDROP";
                    continue block19;
                }
                case 1: {
                    stringArray[i] = "CF_TEXT";
                    continue block19;
                }
                case 2: {
                    stringArray[i] = "CF_BITMAP";
                    continue block19;
                }
                case 3: {
                    stringArray[i] = "CF_METAFILEPICT";
                    continue block19;
                }
                case 4: {
                    stringArray[i] = "CF_SYLK";
                    continue block19;
                }
                case 5: {
                    stringArray[i] = "CF_DIF";
                    continue block19;
                }
                case 6: {
                    stringArray[i] = "CF_TIFF";
                    continue block19;
                }
                case 7: {
                    stringArray[i] = "CF_OEMTEXT";
                    continue block19;
                }
                case 8: {
                    stringArray[i] = "CF_DIB";
                    continue block19;
                }
                case 9: {
                    stringArray[i] = "CF_PALETTE";
                    continue block19;
                }
                case 10: {
                    stringArray[i] = "CF_PENDATA";
                    continue block19;
                }
                case 11: {
                    stringArray[i] = "CF_RIFF";
                    continue block19;
                }
                case 12: {
                    stringArray[i] = "CF_WAVE";
                    continue block19;
                }
                case 13: {
                    stringArray[i] = "CF_UNICODETEXT";
                    continue block19;
                }
                case 14: {
                    stringArray[i] = "CF_ENHMETAFILE";
                    continue block19;
                }
                case 16: {
                    stringArray[i] = "CF_LOCALE";
                    continue block19;
                }
                case 17: {
                    stringArray[i] = "CF_MAX";
                    continue block19;
                }
                default: {
                    stringArray[i] = "UNKNOWN";
                }
            }
        }
        return stringArray;
    }

    private FORMATETC[] _getAvailableTypes() {
        FORMATETC[] fORMATETCArray = new FORMATETC[]{};
        long[] lArray = new long[]{0L};
        if (COM.OleGetClipboard(lArray) != 0) {
            return fORMATETCArray;
        }
        IDataObject iDataObject = new IDataObject(lArray[0]);
        long[] lArray2 = new long[]{0L};
        int n = iDataObject.EnumFormatEtc(1, lArray2);
        iDataObject.Release();
        if (n != 0) {
            return fORMATETCArray;
        }
        IEnumFORMATETC iEnumFORMATETC = new IEnumFORMATETC(lArray2[0]);
        long l2 = OS.GlobalAlloc(64, FORMATETC.sizeof);
        int[] nArray = new int[]{0};
        iEnumFORMATETC.Reset();
        while (iEnumFORMATETC.Next(1, l2, nArray) == 0 && nArray[0] == 1) {
            FORMATETC fORMATETC = new FORMATETC();
            COM.MoveMemory(fORMATETC, l2, FORMATETC.sizeof);
            FORMATETC[] fORMATETCArray2 = new FORMATETC[fORMATETCArray.length + 1];
            System.arraycopy(fORMATETCArray, 0, fORMATETCArray2, 0, fORMATETCArray.length);
            fORMATETCArray2[fORMATETCArray.length] = fORMATETC;
            fORMATETCArray = fORMATETCArray2;
        }
        OS.GlobalFree(l2);
        iEnumFORMATETC.Release();
        return fORMATETCArray;
    }

    static int access$000(Clipboard clipboard, long l2, long l3) {
        return clipboard.QueryInterface(l2, l3);
    }

    static int access$100(Clipboard clipboard) {
        return clipboard.AddRef();
    }

    static int access$200(Clipboard clipboard) {
        return clipboard.Release();
    }

    static int access$300(Clipboard clipboard, long l2, long l3) {
        return clipboard.GetData(l2, l3);
    }

    static int access$400(Clipboard clipboard, long l2) {
        return clipboard.QueryGetData(l2);
    }

    static int access$500(Clipboard clipboard, int n, long l2) {
        return clipboard.EnumFormatEtc(n, l2);
    }
}

