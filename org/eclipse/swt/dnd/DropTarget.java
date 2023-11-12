/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DNDEvent;
import org.eclipse.swt.dnd.DNDListener;
import org.eclipse.swt.dnd.DropTargetEffect;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.I;
import org.eclipse.swt.dnd.TableDropTargetEffect;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.dnd.TreeDropTargetEffect;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;

public class DropTarget
extends Widget {
    Control control;
    Listener controlListener;
    Transfer[] transferAgents = new Transfer[0];
    DropTargetEffect dropEffect;
    TransferData selectedDataType;
    int selectedOperation;
    int keyOperation = -1;
    IDataObject iDataObject;
    COMObject iDropTarget;
    int refCount;
    static final String DEFAULT_DROP_TARGET_EFFECT = "DEFAULT_DROP_TARGET_EFFECT";

    public DropTarget(Control control, int n) {
        super(control, DropTarget.checkStyle(n));
        this.control = control;
        if (control.getData("DropTarget") != null) {
            DND.error(2001);
        }
        control.setData("DropTarget", this);
        this.createCOMInterfaces();
        this.AddRef();
        if (COM.CoLockObjectExternal(this.iDropTarget.getAddress(), true, true) != 0) {
            DND.error(2001);
        }
        if (COM.RegisterDragDrop(control.handle, this.iDropTarget.getAddress()) != 0) {
            DND.error(2001);
        }
        this.controlListener = this::lambda$new$0;
        control.addListener(12, this.controlListener);
        this.addListener(12, this::lambda$new$1);
        Object object = control.getData(DEFAULT_DROP_TARGET_EFFECT);
        if (object instanceof DropTargetEffect) {
            this.dropEffect = (DropTargetEffect)object;
        } else if (control instanceof Table) {
            this.dropEffect = new TableDropTargetEffect((Table)control);
        } else if (control instanceof Tree) {
            this.dropEffect = new TreeDropTargetEffect((Tree)control);
        }
    }

    static int checkStyle(int n) {
        if (n == 0) {
            return 2;
        }
        return n;
    }

    public void addDropListener(DropTargetListener dropTargetListener) {
        if (dropTargetListener == null) {
            DND.error(4);
        }
        DNDListener dNDListener = new DNDListener(dropTargetListener);
        dNDListener.dndWidget = this;
        dNDListener.dndWidget.addListener(2002, dNDListener);
        this.addListener(2003, dNDListener);
        this.addListener(2004, dNDListener);
        this.addListener(2005, dNDListener);
        this.addListener(2006, dNDListener);
        this.addListener(2007, dNDListener);
    }

    int AddRef() {
        return ++this.refCount;
    }

    @Override
    protected void checkSubclass() {
        String string = this.getClass().getName();
        String string2 = DropTarget.class.getName();
        if (!string2.equals(string)) {
            DND.error(43);
        }
    }

    void createCOMInterfaces() {
        boolean bl = C.PTR_SIZEOF == 4;
        this.iDropTarget = new I(this, new int[]{2, 0, 0, bl ? 5 : 4, bl ? 4 : 3, 0, bl ? 5 : 4});
    }

    void disposeCOMInterfaces() {
        if (this.iDropTarget != null) {
            this.iDropTarget.dispose();
        }
        this.iDropTarget = null;
    }

    int DragEnter_64(long l2, int n, long l3, long l4) {
        POINT pOINT = new POINT();
        OS.MoveMemory(pOINT, new long[]{l3}, 8);
        return this.DragEnter(l2, n, pOINT.x, pOINT.y, l4);
    }

    int DragEnter(long l2, int n, int n2, int n3, long l3) {
        n2 = DPIUtil.autoScaleDown(n2);
        n3 = DPIUtil.autoScaleDown(n3);
        this.selectedDataType = null;
        this.selectedOperation = 0;
        if (this.iDataObject != null) {
            this.iDataObject.Release();
        }
        this.iDataObject = null;
        DNDEvent dNDEvent = new DNDEvent();
        DropTarget dropTarget = this;
        DNDEvent dNDEvent2 = dNDEvent;
        long l4 = l2;
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        if (l3 != false) {
            OS.MoveMemory(l3, new int[]{0}, 4);
            return 1;
        }
        this.iDataObject = new IDataObject(l2);
        this.iDataObject.AddRef();
        int n7 = dNDEvent.operations;
        TransferData[] transferDataArray = new TransferData[dNDEvent.dataTypes.length];
        System.arraycopy(dNDEvent.dataTypes, 0, transferDataArray, 0, transferDataArray.length);
        this.notifyListeners(2002, dNDEvent);
        this.refresh();
        if (dNDEvent.detail == 16) {
            dNDEvent.detail = (n7 & 2) != 0 ? 2 : 0;
        }
        this.selectedDataType = null;
        for (TransferData transferData : transferDataArray) {
            if (!TransferData.sameType(transferData, dNDEvent.dataType)) continue;
            this.selectedDataType = transferData;
            break;
        }
        this.selectedOperation = 0;
        if (this.selectedDataType != null && (n7 & dNDEvent.detail) != 0) {
            this.selectedOperation = dNDEvent.detail;
        }
        OS.MoveMemory(l3, new int[]{this.opToOs(this.selectedOperation)}, 4);
        return 0;
    }

    int DragLeave() {
        this.keyOperation = -1;
        if (this.iDataObject == null) {
            return 1;
        }
        DNDEvent dNDEvent = new DNDEvent();
        dNDEvent.widget = this;
        dNDEvent.time = OS.GetMessageTime();
        dNDEvent.detail = 0;
        this.notifyListeners(2003, dNDEvent);
        this.refresh();
        this.iDataObject.Release();
        this.iDataObject = null;
        return 0;
    }

    int DragOver_64(int n, long l2, long l3) {
        POINT pOINT = new POINT();
        OS.MoveMemory(pOINT, new long[]{l2}, 8);
        return this.DragOver(n, pOINT.x, pOINT.y, l3);
    }

    int DragOver(int n, int n2, int n3, long l2) {
        n2 = DPIUtil.autoScaleDown(n2);
        n3 = DPIUtil.autoScaleDown(n3);
        if (this.iDataObject == null) {
            return 1;
        }
        int n4 = this.keyOperation;
        DNDEvent dNDEvent = new DNDEvent();
        DropTarget dropTarget = this;
        DNDEvent dNDEvent2 = dNDEvent;
        long l3 = this.iDataObject.getAddress();
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        if (l2 != false) {
            this.keyOperation = -1;
            OS.MoveMemory(l2, new int[]{0}, 4);
            return 1;
        }
        int n8 = dNDEvent.operations;
        TransferData[] transferDataArray = new TransferData[dNDEvent.dataTypes.length];
        System.arraycopy(dNDEvent.dataTypes, 0, transferDataArray, 0, transferDataArray.length);
        if (this.keyOperation == n4) {
            dNDEvent.type = 2004;
            dNDEvent.dataType = this.selectedDataType;
            dNDEvent.detail = this.selectedOperation;
        } else {
            dNDEvent.type = 2005;
            dNDEvent.dataType = this.selectedDataType;
        }
        this.notifyListeners(dNDEvent.type, dNDEvent);
        this.refresh();
        if (dNDEvent.detail == 16) {
            dNDEvent.detail = (n8 & 2) != 0 ? 2 : 0;
        }
        this.selectedDataType = null;
        for (TransferData transferData : transferDataArray) {
            if (!TransferData.sameType(transferData, dNDEvent.dataType)) continue;
            this.selectedDataType = transferData;
            break;
        }
        this.selectedOperation = 0;
        if (this.selectedDataType != null && (n8 & dNDEvent.detail) == dNDEvent.detail) {
            this.selectedOperation = dNDEvent.detail;
        }
        OS.MoveMemory(l2, new int[]{this.opToOs(this.selectedOperation)}, 4);
        return 0;
    }

    int Drop_64(long l2, int n, long l3, long l4) {
        POINT pOINT = new POINT();
        OS.MoveMemory(pOINT, new long[]{l3}, 8);
        return this.Drop(l2, n, pOINT.x, pOINT.y, l4);
    }

    /*
     * WARNING - void declaration
     */
    int Drop(long l2, int n, int n2, int n3, long l3) {
        void var11_14;
        n2 = DPIUtil.autoScaleDown(n2);
        n3 = DPIUtil.autoScaleDown(n3);
        DNDEvent dNDEvent = new DNDEvent();
        dNDEvent.widget = this;
        dNDEvent.time = OS.GetMessageTime();
        if (this.dropEffect != null) {
            dNDEvent.item = this.dropEffect.getItem(n2, n3);
        }
        dNDEvent.detail = 0;
        this.notifyListeners(2003, dNDEvent);
        this.refresh();
        dNDEvent = new DNDEvent();
        DropTarget dropTarget = this;
        DNDEvent dNDEvent2 = dNDEvent;
        long l4 = l2;
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        if (l3 != false) {
            this.keyOperation = -1;
            OS.MoveMemory(l3, new int[]{0}, 4);
            int n7 = 1;
            if (this.iDataObject != null) {
                this.iDataObject.Release();
                this.iDataObject = null;
            }
            return n7;
        }
        this.keyOperation = -1;
        int n8 = dNDEvent.operations;
        TransferData[] transferDataArray = new TransferData[dNDEvent.dataTypes.length];
        System.arraycopy(dNDEvent.dataTypes, 0, transferDataArray, 0, transferDataArray.length);
        dNDEvent.dataType = this.selectedDataType;
        dNDEvent.detail = this.selectedOperation;
        this.notifyListeners(2007, dNDEvent);
        this.refresh();
        this.selectedDataType = null;
        for (TransferData transferData : transferDataArray) {
            if (!TransferData.sameType(transferData, dNDEvent.dataType)) continue;
            this.selectedDataType = transferData;
            break;
        }
        this.selectedOperation = 0;
        if (this.selectedDataType != null && (n8 & dNDEvent.detail) == dNDEvent.detail) {
            this.selectedOperation = dNDEvent.detail;
        }
        if (this.selectedOperation == 0) {
            OS.MoveMemory(l3, new int[]{0}, 4);
            int n9 = 0;
            if (this.iDataObject != null) {
                this.iDataObject.Release();
                this.iDataObject = null;
            }
            return n9;
        }
        Object var11_12 = null;
        for (Transfer transfer : this.transferAgents) {
            if (transfer == null || !transfer.isSupportedType(this.selectedDataType)) continue;
            Object object = transfer.nativeToJava(this.selectedDataType);
            break;
        }
        if (var11_14 == null) {
            this.selectedOperation = 0;
        }
        dNDEvent.detail = this.selectedOperation;
        dNDEvent.dataType = this.selectedDataType;
        dNDEvent.data = var11_14;
        OS.ImageList_DragShowNolock(false);
        this.notifyListeners(2006, dNDEvent);
        OS.ImageList_DragShowNolock(true);
        this.refresh();
        this.selectedOperation = 0;
        if ((n8 & dNDEvent.detail) == dNDEvent.detail) {
            this.selectedOperation = dNDEvent.detail;
        }
        OS.MoveMemory(l3, new int[]{this.opToOs(this.selectedOperation)}, 4);
        int n7 = 0;
        if (this.iDataObject != null) {
            this.iDataObject.Release();
            this.iDataObject = null;
        }
        return n7;
    }

    public Control getControl() {
        return this.control;
    }

    public DropTargetListener[] getDropListeners() {
        Listener[] listenerArray = this.getListeners(2002);
        int n = listenerArray.length;
        DropTargetListener[] dropTargetListenerArray = new DropTargetListener[n];
        int n2 = 0;
        for (Listener listener : listenerArray) {
            if (!(listener instanceof DNDListener)) continue;
            dropTargetListenerArray[n2] = (DropTargetListener)((DNDListener)listener).getEventListener();
            ++n2;
        }
        if (n2 == n) {
            return dropTargetListenerArray;
        }
        Object[] objectArray = new DropTargetListener[n2];
        System.arraycopy(dropTargetListenerArray, 0, objectArray, 0, n2);
        return objectArray;
    }

    public DropTargetEffect getDropTargetEffect() {
        return this.dropEffect;
    }

    int getOperationFromKeyState(int n) {
        boolean bl;
        boolean bl2 = (n & 8) != 0;
        boolean bl3 = (n & 4) != 0;
        boolean bl4 = bl = (n & 0x20) != 0;
        if (bl) {
            if (bl2 || bl3) {
                return 16;
            }
            return 4;
        }
        if (bl2 && bl3) {
            return 4;
        }
        if (bl2) {
            return 1;
        }
        if (bl3) {
            return 2;
        }
        return 16;
    }

    public Transfer[] getTransfer() {
        return this.transferAgents;
    }

    void onDispose() {
        if (this.control == null) {
            return;
        }
        COM.RevokeDragDrop(this.control.handle);
        if (this.controlListener != null) {
            this.control.removeListener(12, this.controlListener);
        }
        this.controlListener = null;
        this.control.setData("DropTarget", null);
        this.transferAgents = null;
        this.control = null;
        COM.CoLockObjectExternal(this.iDropTarget.getAddress(), false, true);
        this.Release();
        if (this.iDataObject != null) {
            this.iDataObject.Release();
        }
        this.iDataObject = null;
        if (COM.FreeUnusedLibraries) {
            COM.CoFreeUnusedLibraries();
        }
    }

    int opToOs(int n) {
        int n2 = 0;
        if ((n & 1) != 0) {
            n2 |= 1;
        }
        if ((n & 4) != 0) {
            n2 |= 4;
        }
        if ((n & 2) != 0) {
            n2 |= 2;
        }
        return n2;
    }

    int osToOp(int n) {
        int n2 = 0;
        if ((n & 1) != 0) {
            n2 |= 1;
        }
        if ((n & 4) != 0) {
            n2 |= 4;
        }
        if ((n & 2) != 0) {
            n2 |= 2;
        }
        return n2;
    }

    int QueryInterface(long l2, long l3) {
        if (l2 == 0L || l3 == 0L) {
            return -2147024809;
        }
        GUID gUID = new GUID();
        COM.MoveMemory(gUID, l2, GUID.sizeof);
        if (COM.IsEqualGUID(gUID, COM.IIDIUnknown) || COM.IsEqualGUID(gUID, COM.IIDIDropTarget)) {
            OS.MoveMemory(l3, new long[]{this.iDropTarget.getAddress()}, C.PTR_SIZEOF);
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

    void refresh() {
        if (this.control == null || this.control.isDisposed()) {
            return;
        }
        long l2 = this.control.handle;
        RECT rECT = new RECT();
        if (OS.GetUpdateRect(l2, rECT, false)) {
            OS.ImageList_DragShowNolock(false);
            OS.RedrawWindow(l2, rECT, 0L, 257);
            OS.ImageList_DragShowNolock(true);
        }
    }

    public void removeDropListener(DropTargetListener dropTargetListener) {
        if (dropTargetListener == null) {
            DND.error(4);
        }
        this.removeListener(2002, dropTargetListener);
        this.removeListener(2003, dropTargetListener);
        this.removeListener(2004, dropTargetListener);
        this.removeListener(2005, dropTargetListener);
        this.removeListener(2006, dropTargetListener);
        this.removeListener(2007, dropTargetListener);
    }

    public void setDropTargetEffect(DropTargetEffect dropTargetEffect) {
        this.dropEffect = dropTargetEffect;
    }

    public void setTransfer(Transfer ... transferArray) {
        if (transferArray == null) {
            DND.error(4);
        }
        this.transferAgents = transferArray;
    }

    private void lambda$new$1(Event event) {
        this.onDispose();
    }

    private void lambda$new$0(Event event) {
        if (!this.isDisposed()) {
            this.dispose();
        }
    }
}

