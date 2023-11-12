/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.ole.win32;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.DISPPARAMS;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IConnectionPoint;
import org.eclipse.swt.internal.ole.win32.IConnectionPointContainer;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.ole.win32.VARIANT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleControlSite;
import org.eclipse.swt.ole.win32.OleEvent;
import org.eclipse.swt.ole.win32.OleEventTable;
import org.eclipse.swt.ole.win32.OleListener;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.ole.win32.lll;

final class OleEventSink {
    private OleControlSite widget;
    private COMObject iDispatch;
    private int refCount;
    private IUnknown objIUnknown;
    private int eventCookie;
    private GUID eventGuid;
    private OleEventTable eventTable;

    OleEventSink(OleControlSite oleControlSite, long l2, GUID gUID) {
        this.widget = oleControlSite;
        this.eventGuid = gUID;
        this.objIUnknown = new IUnknown(l2);
        this.createCOMInterfaces();
    }

    void connect() {
        long[] lArray = new long[]{0L};
        if (this.objIUnknown.QueryInterface(COM.IIDIConnectionPointContainer, lArray) == 0) {
            IConnectionPointContainer iConnectionPointContainer = new IConnectionPointContainer(lArray[0]);
            long[] lArray2 = new long[]{0L};
            if (iConnectionPointContainer.FindConnectionPoint(this.eventGuid, lArray2) == 0) {
                IConnectionPoint iConnectionPoint = new IConnectionPoint(lArray2[0]);
                int[] nArray = new int[]{0};
                if (iConnectionPoint.Advise(this.iDispatch.getAddress(), nArray) == 0) {
                    this.eventCookie = nArray[0];
                }
                iConnectionPoint.Release();
            }
            iConnectionPointContainer.Release();
        }
    }

    void addListener(int n, OleListener oleListener) {
        if (oleListener == null) {
            OLE.error(4);
        }
        if (this.eventTable == null) {
            this.eventTable = new OleEventTable();
        }
        this.eventTable.hook(n, oleListener);
    }

    int AddRef() {
        return ++this.refCount;
    }

    private void createCOMInterfaces() {
        this.iDispatch = new lll(this, new int[]{2, 0, 0, 1, 3, 4, 8});
    }

    void disconnect() {
        long[] lArray;
        if (this.eventCookie != 0 && this.objIUnknown != null && this.objIUnknown.QueryInterface(COM.IIDIConnectionPointContainer, lArray = new long[]{0L}) == 0) {
            IConnectionPointContainer iConnectionPointContainer = new IConnectionPointContainer(lArray[0]);
            if (iConnectionPointContainer.FindConnectionPoint(this.eventGuid, lArray) == 0) {
                IConnectionPoint iConnectionPoint = new IConnectionPoint(lArray[0]);
                if (iConnectionPoint.Unadvise(this.eventCookie) == 0) {
                    this.eventCookie = 0;
                }
                iConnectionPoint.Release();
            }
            iConnectionPointContainer.Release();
        }
    }

    private void disposeCOMInterfaces() {
        if (this.iDispatch != null) {
            this.iDispatch.dispose();
        }
        this.iDispatch = null;
    }

    private int Invoke(int n, long l2, int n2, int n3, long l3, long l4, long l5, long l6) {
        Object object;
        if (this.eventTable == null || !this.eventTable.hooks(n)) {
            return 0;
        }
        Variant[] variantArray = null;
        if (l3 != 0L) {
            object = new DISPPARAMS();
            COM.MoveMemory((DISPPARAMS)object, l3, DISPPARAMS.sizeof);
            variantArray = new Variant[((DISPPARAMS)object).cArgs];
            int n4 = VARIANT.sizeof;
            long l7 = (((DISPPARAMS)object).cArgs - 1) * n4;
            for (int i = 0; i < ((DISPPARAMS)object).cArgs; ++i) {
                variantArray[i] = new Variant();
                variantArray[i].setData(((DISPPARAMS)object).rgvarg + l7);
                l7 -= (long)n4;
            }
        }
        object = new OleEvent();
        ((OleEvent)object).arguments = variantArray;
        this.notifyListener(n, (OleEvent)object);
        if (variantArray != null) {
            for (Variant variant : variantArray) {
                variant.dispose();
            }
        }
        return 0;
    }

    private void notifyListener(int n, OleEvent oleEvent) {
        if (oleEvent == null) {
            OLE.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        oleEvent.type = n;
        oleEvent.widget = this.widget;
        this.eventTable.sendEvent(oleEvent);
    }

    private int QueryInterface(long l2, long l3) {
        if (l2 == 0L || l3 == 0L) {
            return -2147024809;
        }
        GUID gUID = new GUID();
        COM.MoveMemory(gUID, l2, GUID.sizeof);
        if (COM.IsEqualGUID(gUID, COM.IIDIUnknown) || COM.IsEqualGUID(gUID, COM.IIDIDispatch) || COM.IsEqualGUID(gUID, this.eventGuid)) {
            OS.MoveMemory(l3, new long[]{this.iDispatch.getAddress()}, C.PTR_SIZEOF);
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
        }
        return this.refCount;
    }

    void removeListener(int n, OleListener oleListener) {
        if (oleListener == null) {
            OLE.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(n, oleListener);
    }

    boolean hasListeners() {
        return this.eventTable.hasEntries();
    }

    static int access$000(OleEventSink oleEventSink, long l2, long l3) {
        return oleEventSink.QueryInterface(l2, l3);
    }

    static int access$100(OleEventSink oleEventSink, int n, long l2, int n2, int n3, long l3, long l4, long l5, long l6) {
        return oleEventSink.Invoke(n, l2, n2, n3, l3, l4, l5, l6);
    }
}

