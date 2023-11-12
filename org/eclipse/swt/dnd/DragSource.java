/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DNDEvent;
import org.eclipse.swt.dnd.DNDListener;
import org.eclipse.swt.dnd.DragSourceEffect;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.OleEnumFORMATETC;
import org.eclipse.swt.dnd.TableDragSourceEffect;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.dnd.TreeDragSourceEffect;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;

public class DragSource
extends Widget {
    Control control;
    Listener controlListener;
    Transfer[] transferAgents = new Transfer[0];
    DragSourceEffect dragEffect;
    Composite topControl;
    long hwndDrag;
    COMIDropSource iDropSource;
    COMIDataObject iDataObject;
    int dataEffect = 0;
    static final String DEFAULT_DRAG_SOURCE_EFFECT = "DEFAULT_DRAG_SOURCE_EFFECT";
    static final int CFSTR_PERFORMEDDROPEFFECT = Transfer.registerType("Performed DropEffect");
    static final TCHAR WindowClass = new TCHAR(0, "#32770", true);

    public DragSource(Control control, int n) {
        super(control, DragSource.checkStyle(n));
        this.control = control;
        if (control.getData("DragSource") != null) {
            DND.error(2000);
        }
        control.setData("DragSource", this);
        this.controlListener = this::lambda$new$0;
        control.addListener(12, this.controlListener);
        control.addListener(29, this.controlListener);
        this.addListener(12, this::lambda$new$1);
        Object object = control.getData(DEFAULT_DRAG_SOURCE_EFFECT);
        if (object instanceof DragSourceEffect) {
            this.dragEffect = (DragSourceEffect)object;
        } else if (control instanceof Tree) {
            this.dragEffect = new TreeDragSourceEffect((Tree)control);
        } else if (control instanceof Table) {
            this.dragEffect = new TableDragSourceEffect((Table)control);
        }
    }

    static int checkStyle(int n) {
        if (n == 0) {
            return 2;
        }
        return n;
    }

    public void addDragListener(DragSourceListener dragSourceListener) {
        if (dragSourceListener == null) {
            DND.error(4);
        }
        DNDListener dNDListener = new DNDListener(dragSourceListener);
        dNDListener.dndWidget = this;
        dNDListener.dndWidget.addListener(2008, dNDListener);
        this.addListener(2001, dNDListener);
        this.addListener(2000, dNDListener);
    }

    private void createCOMInterfaces() {
        this.releaseCOMInterfaces();
        this.iDropSource = new COMIDropSource(this);
        this.iDataObject = new COMIDataObject(this, this.transferAgents);
    }

    @Override
    protected void checkSubclass() {
        String string = this.getClass().getName();
        String string2 = DragSource.class.getName();
        if (!string2.equals(string)) {
            DND.error(43);
        }
    }

    private void releaseCOMInterfaces() {
        if (this.iDropSource != null) {
            this.iDropSource.Release();
        }
        this.iDropSource = null;
        if (this.iDataObject != null) {
            this.iDataObject.Release();
        }
        this.iDataObject = null;
    }

    private void drag(Event event) {
        int n;
        DNDEvent dNDEvent = new DNDEvent();
        dNDEvent.widget = this;
        dNDEvent.x = event.x;
        dNDEvent.y = event.y;
        dNDEvent.time = OS.GetMessageTime();
        dNDEvent.doit = true;
        this.notifyListeners(2008, dNDEvent);
        if (!dNDEvent.doit || this != null) {
            return;
        }
        int[] nArray = new int[]{0};
        int n2 = this.opToOs(this.getStyle());
        Display display = this.control.getDisplay();
        String string = "org.eclipse.swt.internal.win32.runMessagesInIdle";
        Object object = display.getData("org.eclipse.swt.internal.win32.runMessagesInIdle");
        display.setData("org.eclipse.swt.internal.win32.runMessagesInIdle", Boolean.TRUE);
        ImageList imageList = null;
        Image image = dNDEvent.image;
        this.hwndDrag = 0L;
        this.topControl = null;
        if (image != null) {
            imageList = new ImageList(0);
            imageList.add(image);
            this.topControl = this.control.getShell();
            int n3 = dNDEvent.offsetX;
            this.hwndDrag = this.topControl.handle;
            if ((this.topControl.getStyle() & 0x4000000) != 0) {
                n3 = image.getBoundsInPixels().width - n3;
                RECT rECT = new RECT();
                OS.GetClientRect(this.topControl.handle, rECT);
                this.hwndDrag = OS.CreateWindowEx(0x100020, WindowClass, null, 0x44000000, 0, 0, rECT.right - rECT.left, rECT.bottom - rECT.top, this.topControl.handle, 0L, OS.GetModuleHandle(null), null);
                OS.ShowWindow(this.hwndDrag, 5);
            }
            OS.ImageList_BeginDrag(imageList.getHandle(), 0, n3, dNDEvent.offsetY);
            n = 384;
            OS.RedrawWindow(this.topControl.handle, null, 0L, 384);
            POINT pOINT = new POINT();
            pOINT.x = DPIUtil.autoScaleUp(event.x);
            pOINT.y = DPIUtil.autoScaleUp(event.y);
            OS.MapWindowPoints(this.control.handle, 0L, pOINT, 1);
            RECT rECT = new RECT();
            OS.GetWindowRect(this.hwndDrag, rECT);
            OS.ImageList_DragEnter(this.hwndDrag, pOINT.x - rECT.left, pOINT.y - rECT.top);
        }
        String string2 = "org.eclipse.swt.internal.win32.externalEventLoop";
        n = 262401;
        this.createCOMInterfaces();
        display.setData("org.eclipse.swt.internal.win32.externalEventLoop", Boolean.TRUE);
        n = COM.DoDragDrop(this.iDataObject.getAddress(), this.iDropSource.getAddress(), n2, nArray);
        display.setData("org.eclipse.swt.internal.win32.externalEventLoop", Boolean.FALSE);
        if (this.hwndDrag != 0L) {
            OS.ImageList_DragLeave(this.hwndDrag);
            OS.ImageList_EndDrag();
            imageList.dispose();
            if (this.hwndDrag != this.topControl.handle) {
                OS.DestroyWindow(this.hwndDrag);
            }
            this.hwndDrag = 0L;
            this.topControl = null;
        }
        display.setData("org.eclipse.swt.internal.win32.runMessagesInIdle", object);
        this.releaseCOMInterfaces();
        int n4 = this.osToOp(nArray[0]);
        if (this.dataEffect == 2) {
            n4 = n4 == 0 || n4 == 1 ? 8 : 2;
        } else if (this.dataEffect != 0) {
            n4 = this.dataEffect;
        }
        dNDEvent = new DNDEvent();
        dNDEvent.widget = this;
        dNDEvent.time = OS.GetMessageTime();
        dNDEvent.doit = n == 262400;
        dNDEvent.detail = n4;
        this.notifyListeners(2000, dNDEvent);
        this.dataEffect = 0;
    }

    private static int EnumFormatEtc(Transfer[] transferArray, int n, long l2) {
        if (n == 2) {
            return -2147467263;
        }
        TransferData[] transferDataArray = new TransferData[]{};
        for (Transfer transfer : transferArray) {
            if (transfer == null) continue;
            TransferData[] transferDataArray2 = transfer.getSupportedTypes();
            TransferData[] transferDataArray3 = new TransferData[transferDataArray.length + transferDataArray2.length];
            System.arraycopy(transferDataArray, 0, transferDataArray3, 0, transferDataArray.length);
            System.arraycopy(transferDataArray2, 0, transferDataArray3, transferDataArray.length, transferDataArray2.length);
            transferDataArray = transferDataArray3;
        }
        OleEnumFORMATETC oleEnumFORMATETC = new OleEnumFORMATETC();
        oleEnumFORMATETC.AddRef();
        FORMATETC[] fORMATETCArray = new FORMATETC[transferDataArray.length];
        for (int i = 0; i < fORMATETCArray.length; ++i) {
            fORMATETCArray[i] = transferDataArray[i].formatetc;
        }
        oleEnumFORMATETC.setFormats(fORMATETCArray);
        OS.MoveMemory(l2, new long[]{oleEnumFORMATETC.getAddress()}, C.PTR_SIZEOF);
        return 0;
    }

    public Control getControl() {
        return this.control;
    }

    public DragSourceListener[] getDragListeners() {
        Listener[] listenerArray = this.getListeners(2008);
        int n = listenerArray.length;
        DragSourceListener[] dragSourceListenerArray = new DragSourceListener[n];
        int n2 = 0;
        for (Listener listener : listenerArray) {
            if (!(listener instanceof DNDListener)) continue;
            dragSourceListenerArray[n2] = (DragSourceListener)((DNDListener)listener).getEventListener();
            ++n2;
        }
        if (n2 == n) {
            return dragSourceListenerArray;
        }
        Object[] objectArray = new DragSourceListener[n2];
        System.arraycopy(dragSourceListenerArray, 0, objectArray, 0, n2);
        return objectArray;
    }

    public DragSourceEffect getDragSourceEffect() {
        return this.dragEffect;
    }

    public Transfer[] getTransfer() {
        return this.transferAgents;
    }

    private int GiveFeedback(int n) {
        return 262402;
    }

    private int QueryContinueDrag(int n, int n2) {
        if (this.topControl != null && this.topControl.isDisposed()) {
            return 262401;
        }
        if (n != 0) {
            if (this.hwndDrag != 0L) {
                OS.ImageList_DragLeave(this.hwndDrag);
            }
            return 262401;
        }
        int n3 = 19;
        if ((n2 & 0x13) == 0) {
            if (this.hwndDrag != 0L) {
                OS.ImageList_DragLeave(this.hwndDrag);
            }
            return 262400;
        }
        if (this.hwndDrag != 0L) {
            POINT pOINT = new POINT();
            OS.GetCursorPos(pOINT);
            RECT rECT = new RECT();
            OS.GetWindowRect(this.hwndDrag, rECT);
            OS.ImageList_DragMove(pOINT.x - rECT.left, pOINT.y - rECT.top);
        }
        return 0;
    }

    private void onDispose() {
        if (this.control == null) {
            return;
        }
        this.releaseCOMInterfaces();
        if (this.controlListener != null) {
            this.control.removeListener(12, this.controlListener);
            this.control.removeListener(29, this.controlListener);
        }
        this.controlListener = null;
        this.control.setData("DragSource", null);
        this.control = null;
        this.transferAgents = null;
    }

    private int opToOs(int n) {
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

    private int osToOp(int n) {
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

    private static int QueryGetData(Transfer[] transferArray, long l2) {
        if (transferArray == null) {
            return -2147467259;
        }
        TransferData transferData = new TransferData();
        transferData.formatetc = new FORMATETC();
        COM.MoveMemory(transferData.formatetc, l2, FORMATETC.sizeof);
        transferData.type = transferData.formatetc.cfFormat;
        for (Transfer transfer : transferArray) {
            if (transfer == null || !transfer.isSupportedType(transferData)) continue;
            return 0;
        }
        return -2147221404;
    }

    private static int QueryInterface(COMObject cOMObject, long l2, long l3) {
        if (l2 == 0L || l3 == 0L) {
            return -2147024809;
        }
        GUID gUID = new GUID();
        COM.MoveMemory(gUID, l2, GUID.sizeof);
        if (cOMObject != null && COM.IsEqualGUID(gUID, COM.IIDIUnknown) || COM.IsEqualGUID(gUID, COM.IIDIDropSource) && cOMObject instanceof COMIDropSource || COM.IsEqualGUID(gUID, COM.IIDIDataObject) && cOMObject instanceof COMIDataObject) {
            OS.MoveMemory(l3, new long[]{cOMObject.getAddress()}, C.PTR_SIZEOF);
            cOMObject.method1(null);
            return 0;
        }
        OS.MoveMemory(l3, new long[]{0L}, C.PTR_SIZEOF);
        return -2147467262;
    }

    public void removeDragListener(DragSourceListener dragSourceListener) {
        if (dragSourceListener == null) {
            DND.error(4);
        }
        this.removeListener(2008, dragSourceListener);
        this.removeListener(2001, dragSourceListener);
        this.removeListener(2000, dragSourceListener);
    }

    public void setDragSourceEffect(DragSourceEffect dragSourceEffect) {
        this.dragEffect = dragSourceEffect;
    }

    public void setTransfer(Transfer ... transferArray) {
        this.transferAgents = transferArray;
    }

    private void lambda$new$1(Event event) {
        this.onDispose();
    }

    private void lambda$new$0(Event event) {
        if (event.type == 12 && !this.isDisposed()) {
            this.dispose();
        }
        if (event.type == 29 && !this.isDisposed()) {
            this.drag(event);
        }
    }

    static int access$000(COMObject cOMObject, long l2, long l3) {
        return DragSource.QueryInterface(cOMObject, l2, l3);
    }

    static int access$100(DragSource dragSource, int n, int n2) {
        return dragSource.QueryContinueDrag(n, n2);
    }

    static int access$200(DragSource dragSource, int n) {
        return dragSource.GiveFeedback(n);
    }

    static int access$300(Transfer[] transferArray, long l2) {
        return DragSource.QueryGetData(transferArray, l2);
    }

    static int access$400(Transfer[] transferArray, int n, long l2) {
        return DragSource.EnumFormatEtc(transferArray, n, l2);
    }

    static int access$500(DragSource dragSource, int n) {
        return dragSource.osToOp(n);
    }

    private class COMIDataObject
    extends COMObject {
        private long refCount;
        private final Transfer[] transferAgents;
        private Object lastData;
        final DragSource this$0;

        public COMIDataObject(DragSource dragSource, Transfer[] transferArray) {
            this.this$0 = dragSource;
            super(new int[]{2, 0, 0, 2, 2, 1, 2, 3, 2, 4, 1, 1});
            this.refCount = 0L;
            this.lastData = null;
            this.AddRef();
            this.transferAgents = transferArray;
        }

        @Override
        public long method0(long[] lArray) {
            return DragSource.access$000(this, lArray[0], lArray[1]);
        }

        @Override
        public long method1(long[] lArray) {
            return this.AddRef();
        }

        @Override
        public long method2(long[] lArray) {
            return this.Release();
        }

        @Override
        public long method3(long[] lArray) {
            return this.GetData(lArray[0], lArray[1]);
        }

        @Override
        public long method5(long[] lArray) {
            return DragSource.access$300(this.transferAgents, lArray[0]);
        }

        @Override
        public long method7(long[] lArray) {
            return this.SetData(lArray[0], lArray[1], (int)lArray[2]);
        }

        @Override
        public long method8(long[] lArray) {
            return DragSource.access$400(this.transferAgents, (int)lArray[0], lArray[1]);
        }

        public long AddRef() {
            return ++this.refCount;
        }

        public long Release() {
            --this.refCount;
            if (this.refCount == 0L) {
                if (this.this$0.iDataObject == this) {
                    this.this$0.iDataObject = null;
                }
                this.dispose();
                if (COM.FreeUnusedLibraries) {
                    COM.CoFreeUnusedLibraries();
                }
            }
            return this.refCount;
        }

        /*
         * Exception decompiling
         */
        private int GetData(long var1, long var3) {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl45 : IF_ACMPNE - null : Stack underflow
             *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        /*
         * Exception decompiling
         */
        private int SetData(long var1, long var3, int var5) {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl61 : IF_ACMPNE - null : Stack underflow
             *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }
    }

    private class COMIDropSource
    extends COMObject {
        private long refCount;
        final DragSource this$0;

        public COMIDropSource(DragSource dragSource) {
            this.this$0 = dragSource;
            super(new int[]{2, 0, 0, 2, 1});
            this.refCount = 0L;
            this.AddRef();
        }

        @Override
        public long method0(long[] lArray) {
            return DragSource.access$000(this, lArray[0], lArray[1]);
        }

        @Override
        public long method1(long[] lArray) {
            return this.AddRef();
        }

        @Override
        public long method2(long[] lArray) {
            return this.Release();
        }

        @Override
        public long method3(long[] lArray) {
            return DragSource.access$100(this.this$0, (int)lArray[0], (int)lArray[1]);
        }

        @Override
        public long method4(long[] lArray) {
            return DragSource.access$200(this.this$0, (int)lArray[0]);
        }

        public long AddRef() {
            return ++this.refCount;
        }

        public long Release() {
            --this.refCount;
            if (this.refCount == 0L) {
                if (this.this$0.iDropSource == this) {
                    this.this$0.iDropSource = null;
                }
                this.dispose();
                if (COM.FreeUnusedLibraries) {
                    COM.CoFreeUnusedLibraries();
                }
            }
            return this.refCount;
        }
    }
}

