/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.lI;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.win32.OS;

final class OleEnumFORMATETC {
    private COMObject iEnumFORMATETC;
    private int refCount;
    private int index;
    private FORMATETC[] formats;

    OleEnumFORMATETC() {
        this.createCOMInterfaces();
    }

    int AddRef() {
        return ++this.refCount;
    }

    private void createCOMInterfaces() {
        this.iEnumFORMATETC = new lI(this, new int[]{2, 0, 0, 3, 1, 0, 1});
    }

    private void disposeCOMInterfaces() {
        if (this.iEnumFORMATETC != null) {
            this.iEnumFORMATETC.dispose();
        }
        this.iEnumFORMATETC = null;
    }

    long getAddress() {
        return this.iEnumFORMATETC.getAddress();
    }

    private FORMATETC[] getNextItems(int n) {
        if (this.formats == null || n < 1) {
            return null;
        }
        int n2 = this.index + n - 1;
        if (n2 > this.formats.length - 1) {
            n2 = this.formats.length - 1;
        }
        if (this.index > n2) {
            return null;
        }
        FORMATETC[] fORMATETCArray = new FORMATETC[n2 - this.index + 1];
        for (int i = 0; i < fORMATETCArray.length; ++i) {
            fORMATETCArray[i] = this.formats[this.index];
            ++this.index;
        }
        return fORMATETCArray;
    }

    private int Next(int n, long l2, long l3) {
        if (l2 == 0L) {
            return -2147024809;
        }
        if (l3 == 0L && n != 1) {
            return -2147024809;
        }
        FORMATETC[] fORMATETCArray = this.getNextItems(n);
        if (fORMATETCArray != null) {
            for (int i = 0; i < fORMATETCArray.length; ++i) {
                COM.MoveMemory(l2 + (long)(i * FORMATETC.sizeof), fORMATETCArray[i], FORMATETC.sizeof);
            }
            if (l3 != 0L) {
                OS.MoveMemory(l3, new int[]{fORMATETCArray.length}, 4);
            }
            if (fORMATETCArray.length == n) {
                return 0;
            }
        } else {
            if (l3 != 0L) {
                OS.MoveMemory(l3, new int[]{0}, 4);
            }
            COM.MoveMemory(l2, new FORMATETC(), FORMATETC.sizeof);
        }
        return 1;
    }

    private int QueryInterface(long l2, long l3) {
        if (l2 == 0L || l3 == 0L) {
            return -2147467262;
        }
        GUID gUID = new GUID();
        COM.MoveMemory(gUID, l2, GUID.sizeof);
        if (COM.IsEqualGUID(gUID, COM.IIDIUnknown) || COM.IsEqualGUID(gUID, COM.IIDIEnumFORMATETC)) {
            OS.MoveMemory(l3, new long[]{this.iEnumFORMATETC.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        OS.MoveMemory(l3, new long[]{0L}, C.PTR_SIZEOF);
        return -2147467262;
    }

    int Release() {
        --this.refCount;
        if (this.refCount == 0) {
            this.disposeCOMInterfaces();
            if (COM.FreeUnusedLibraries) {
                COM.CoFreeUnusedLibraries();
            }
        }
        return this.refCount;
    }

    private int Reset() {
        this.index = 0;
        return 0;
    }

    void setFormats(FORMATETC[] fORMATETCArray) {
        this.formats = fORMATETCArray;
        this.index = 0;
    }

    private int Skip(int n) {
        if (n < 1) {
            return -2147024809;
        }
        this.index += n;
        if (this.index > this.formats.length - 1) {
            this.index = this.formats.length - 1;
            return 1;
        }
        return 0;
    }

    static int access$000(OleEnumFORMATETC oleEnumFORMATETC, long l2, long l3) {
        return oleEnumFORMATETC.QueryInterface(l2, l3);
    }

    static int access$100(OleEnumFORMATETC oleEnumFORMATETC, int n, long l2, long l3) {
        return oleEnumFORMATETC.Next(n, l2, l3);
    }

    static int access$200(OleEnumFORMATETC oleEnumFORMATETC, int n) {
        return oleEnumFORMATETC.Skip(n);
    }

    static int access$300(OleEnumFORMATETC oleEnumFORMATETC) {
        return oleEnumFORMATETC.Reset();
    }
}

