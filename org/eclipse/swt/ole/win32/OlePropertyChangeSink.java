/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.ole.win32;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IConnectionPoint;
import org.eclipse.swt.internal.ole.win32.IConnectionPointContainer;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleControlSite;
import org.eclipse.swt.ole.win32.OleEvent;
import org.eclipse.swt.ole.win32.OleEventTable;
import org.eclipse.swt.ole.win32.OleListener;
import org.eclipse.swt.ole.win32.lI;

final class OlePropertyChangeSink {
    private OleControlSite controlSite;
    private COMObject iPropertyNotifySink;
    private int refCount;
    private int propertyCookie;
    private OleEventTable eventTable;

    OlePropertyChangeSink(OleControlSite oleControlSite) {
        this.controlSite = oleControlSite;
        this.createCOMInterfaces();
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

    void connect(IUnknown iUnknown) {
        long[] lArray = new long[]{0L};
        if (iUnknown.QueryInterface(COM.IIDIConnectionPointContainer, lArray) == 0) {
            IConnectionPointContainer iConnectionPointContainer = new IConnectionPointContainer(lArray[0]);
            if (iConnectionPointContainer.FindConnectionPoint(COM.IIDIPropertyNotifySink, lArray) == 0) {
                IConnectionPoint iConnectionPoint = new IConnectionPoint(lArray[0]);
                int[] nArray = new int[]{0};
                if (iConnectionPoint.Advise(this.iPropertyNotifySink.getAddress(), nArray) == 0) {
                    this.propertyCookie = nArray[0];
                }
                iConnectionPoint.Release();
            }
            iConnectionPointContainer.Release();
        }
    }

    private void createCOMInterfaces() {
        this.iPropertyNotifySink = new lI(this, new int[]{2, 0, 0, 1, 1});
    }

    void disconnect(IUnknown iUnknown) {
        long[] lArray;
        if (this.propertyCookie != 0 && iUnknown != null && iUnknown.QueryInterface(COM.IIDIConnectionPointContainer, lArray = new long[]{0L}) == 0) {
            IConnectionPointContainer iConnectionPointContainer = new IConnectionPointContainer(lArray[0]);
            if (iConnectionPointContainer.FindConnectionPoint(COM.IIDIPropertyNotifySink, lArray) == 0) {
                IConnectionPoint iConnectionPoint = new IConnectionPoint(lArray[0]);
                if (iConnectionPoint.Unadvise(this.propertyCookie) == 0) {
                    this.propertyCookie = 0;
                }
                iConnectionPoint.Release();
            }
            iConnectionPointContainer.Release();
        }
    }

    private void disposeCOMInterfaces() {
        if (this.iPropertyNotifySink != null) {
            this.iPropertyNotifySink.dispose();
        }
        this.iPropertyNotifySink = null;
    }

    private void notifyListener(int n, OleEvent oleEvent) {
        if (oleEvent == null) {
            OLE.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        oleEvent.type = n;
        oleEvent.widget = this.controlSite;
        this.eventTable.sendEvent(oleEvent);
    }

    private int OnChanged(int n) {
        if (this.eventTable == null || !this.eventTable.hooks(n)) {
            return 0;
        }
        OleEvent oleEvent = new OleEvent();
        oleEvent.detail = 1;
        this.notifyListener(n, oleEvent);
        return 0;
    }

    private int OnRequestEdit(int n) {
        if (this.eventTable == null || !this.eventTable.hooks(n)) {
            return 0;
        }
        OleEvent oleEvent = new OleEvent();
        oleEvent.doit = true;
        oleEvent.detail = 0;
        this.notifyListener(n, oleEvent);
        return oleEvent.doit ? 0 : 1;
    }

    private int QueryInterface(long l2, long l3) {
        if (l2 == 0L || l3 == 0L) {
            return -2147024809;
        }
        GUID gUID = new GUID();
        COM.MoveMemory(gUID, l2, GUID.sizeof);
        if (COM.IsEqualGUID(gUID, COM.IIDIUnknown) || COM.IsEqualGUID(gUID, COM.IIDIPropertyNotifySink)) {
            OS.MoveMemory(l3, new long[]{this.iPropertyNotifySink.getAddress()}, C.PTR_SIZEOF);
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

    static int access$000(OlePropertyChangeSink olePropertyChangeSink, long l2, long l3) {
        return olePropertyChangeSink.QueryInterface(l2, l3);
    }

    static int access$100(OlePropertyChangeSink olePropertyChangeSink, int n) {
        return olePropertyChangeSink.OnChanged(n);
    }

    static int access$200(OlePropertyChangeSink olePropertyChangeSink, int n) {
        return olePropertyChangeSink.OnRequestEdit(n);
    }
}

