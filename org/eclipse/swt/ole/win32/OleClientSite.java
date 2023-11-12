/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.ole.win32;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ole.win32.CAUUID;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IDispatch;
import org.eclipse.swt.internal.ole.win32.IOleCommandTarget;
import org.eclipse.swt.internal.ole.win32.IOleDocument;
import org.eclipse.swt.internal.ole.win32.IOleDocumentView;
import org.eclipse.swt.internal.ole.win32.IOleInPlaceObject;
import org.eclipse.swt.internal.ole.win32.IOleLink;
import org.eclipse.swt.internal.ole.win32.IOleObject;
import org.eclipse.swt.internal.ole.win32.IPersist;
import org.eclipse.swt.internal.ole.win32.IPersistFile;
import org.eclipse.swt.internal.ole.win32.IPersistStorage;
import org.eclipse.swt.internal.ole.win32.ISpecifyPropertyPages;
import org.eclipse.swt.internal.ole.win32.IStorage;
import org.eclipse.swt.internal.ole.win32.IStream;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.ole.win32.IViewObject2;
import org.eclipse.swt.internal.ole.win32.OLECMD;
import org.eclipse.swt.internal.ole.win32.OLEINPLACEFRAMEINFO;
import org.eclipse.swt.internal.ole.win32.VARIANT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.ole.win32.lII;
import org.eclipse.swt.ole.win32.lIII;
import org.eclipse.swt.ole.win32.lIIl;
import org.eclipse.swt.ole.win32.lIl;
import org.eclipse.swt.ole.win32.llI;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

public class OleClientSite
extends Composite {
    COMObject iOleClientSite;
    private COMObject iAdviseSink;
    private COMObject iOleInPlaceSite;
    private COMObject iOleDocumentSite;
    protected GUID appClsid;
    private GUID objClsid;
    private int refCount;
    protected OleFrame frame;
    protected IUnknown objIUnknown;
    protected IOleObject objIOleObject;
    protected IViewObject2 objIViewObject2;
    protected IOleInPlaceObject objIOleInPlaceObject;
    protected IOleCommandTarget objIOleCommandTarget;
    protected IOleDocumentView objDocumentView;
    protected IStorage tempStorage;
    private int aspect;
    private int type;
    private boolean isStatic;
    boolean isActivated;
    private RECT borderWidths = new RECT();
    private RECT indent = new RECT();
    private boolean inUpdate = false;
    private boolean inInit = true;
    private boolean inDispose = false;
    private static final String WORDPROGID = "Word.Document";
    private Listener listener;
    static final int STATE_NONE = 0;
    static final int STATE_RUNNING = 1;
    static final int STATE_INPLACEACTIVE = 2;
    static final int STATE_UIACTIVE = 3;
    static final int STATE_ACTIVE = 4;
    int state = 0;

    protected OleClientSite(Composite composite, int n) {
        super(composite, n);
        this.createCOMInterfaces();
        while (composite != null) {
            if (composite instanceof OleFrame) {
                this.frame = (OleFrame)composite;
                break;
            }
            composite = composite.getParent();
        }
        if (this.frame == null) {
            OLE.error(5);
        }
        this.frame.AddRef();
        this.aspect = 1;
        this.type = 1;
        this.isStatic = false;
        this.listener = new lII(this);
        this.frame.addListener(11, this.listener);
        this.frame.addListener(10, this.listener);
        this.addListener(12, this.listener);
        this.addListener(15, this.listener);
        this.addListener(16, this.listener);
        this.addListener(9, this.listener);
        this.addListener(31, this.listener);
        this.addListener(1, this.listener);
        this.addListener(26, this.listener);
        this.addListener(27, this.listener);
    }

    public OleClientSite(Composite composite, int n, File file) {
        this(composite, n);
        try {
            String string;
            if (file == null || file.isDirectory() || !file.exists()) {
                OLE.error(5);
            }
            GUID gUID = new GUID();
            char[] cArray = file.getAbsolutePath().toCharArray();
            int n2 = COM.GetClassFile(cArray, gUID);
            if (n2 != 0) {
                OLE.error(1004, n2);
            }
            if ((string = this.getProgID(gUID)) == null) {
                OLE.error(1004, n2);
            }
            this.appClsid = gUID;
            this.OleCreate(this.appClsid, gUID, cArray, file);
        }
        catch (SWTException sWTException) {
            this.dispose();
            this.disposeCOMInterfaces();
            throw sWTException;
        }
    }

    public OleClientSite(Composite composite, int n, String string) {
        this(composite, n);
        try {
            this.appClsid = this.getClassID(string);
            if (this.appClsid == null) {
                OLE.error(1004);
            }
            this.tempStorage = this.createTempStorage();
            long[] lArray = new long[]{0L};
            long l2 = this.isICAClient() ? 0L : this.iOleClientSite.getAddress();
            int n2 = COM.OleCreate(this.appClsid, COM.IIDIUnknown, 1, null, l2, this.tempStorage.getAddress(), lArray);
            if (n2 != 0) {
                OLE.error(1001, n2);
            }
            this.objIUnknown = new IUnknown(lArray[0]);
            this.addObjectReferences();
            if (COM.OleRun(this.objIUnknown.getAddress()) == 0) {
                this.state = 1;
            }
        }
        catch (SWTException sWTException) {
            this.dispose();
            this.disposeCOMInterfaces();
            throw sWTException;
        }
    }

    public OleClientSite(Composite composite, int n, String string, File file) {
        this(composite, n);
        try {
            if (file == null || file.isDirectory() || !file.exists()) {
                OLE.error(5);
            }
            this.appClsid = this.getClassID(string);
            if (this.appClsid == null) {
                OLE.error(1004);
            }
            char[] cArray = file.getAbsolutePath().toCharArray();
            GUID gUID = new GUID();
            COM.GetClassFile(cArray, gUID);
            this.OleCreate(this.appClsid, gUID, cArray, file);
        }
        catch (SWTException sWTException) {
            this.dispose();
            this.disposeCOMInterfaces();
            throw sWTException;
        }
    }

    void OleCreate(GUID gUID, GUID gUID2, char[] cArray, File file) {
        boolean bl = this.isOffice2007(true);
        if (!bl && COM.IsEqualGUID(gUID, gUID2)) {
            this.tempStorage = this.createTempStorage();
            long[] lArray = new long[]{0L};
            int n = COM.OleCreateFromFile(gUID, cArray, COM.IIDIUnknown, 1, null, this.iOleClientSite.getAddress(), this.tempStorage.getAddress(), lArray);
            if (n != 0) {
                OLE.error(1001, n);
            }
            this.objIUnknown = new IUnknown(lArray[0]);
        } else {
            long[] lArray;
            int n;
            int n2;
            long[] lArray2;
            IStorage iStorage = null;
            if (COM.StgIsStorageFile(cArray) == 0) {
                lArray2 = new long[]{0L};
                n2 = 65552;
                n = COM.StgOpenStorage(cArray, 0L, 65552, 0L, 0, lArray2);
                if (n != 0) {
                    OLE.error(1002, n);
                }
                iStorage = new IStorage(lArray2[0]);
            } else {
                lArray2 = new long[]{0L};
                n2 = 4114;
                n = COM.StgCreateDocfile(null, 67112978, 0, lArray2);
                if (n != 0) {
                    OLE.error(1002, n);
                }
                iStorage = new IStorage(lArray2[0]);
                String string = "CONTENTS";
                GUID gUID3 = this.getClassID(WORDPROGID);
                if (gUID3 != null && COM.IsEqualGUID(gUID, gUID3)) {
                    string = "WordDocument";
                }
                if (bl) {
                    string = "Package";
                }
                if ((n = iStorage.CreateStream(string, 4114, 0, 0, lArray2 = new long[]{0L})) != 0) {
                    iStorage.Release();
                    OLE.error(1002, n);
                }
                IStream iStream = new IStream(lArray2[0]);
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    int n3 = 4096;
                    byte[] byArray = new byte[4096];
                    int n4 = 0;
                    while ((n4 = fileInputStream.read(byArray)) > 0) {
                        long l2 = OS.CoTaskMemAlloc(n4);
                        OS.MoveMemory(l2, byArray, n4);
                        n = iStream.Write(l2, n4, null);
                        OS.CoTaskMemFree(l2);
                        if (n == 0) continue;
                        fileInputStream.close();
                        iStream.Release();
                        iStorage.Release();
                        OLE.error(1002, n);
                    }
                    fileInputStream.close();
                    iStream.Commit(0);
                    iStream.Release();
                }
                catch (IOException iOException) {
                    iStream.Release();
                    iStorage.Release();
                    OLE.error(1002);
                }
            }
            this.tempStorage = this.createTempStorage();
            int n5 = iStorage.CopyTo(0, null, null, this.tempStorage.getAddress());
            iStorage.Release();
            if (n5 != 0) {
                OLE.error(1002, n5);
            }
            if ((n5 = COM.CoCreateInstance(gUID, 0L, 3, COM.IIDIUnknown, lArray = new long[]{0L})) != 0) {
                OLE.error(1001, n5);
            }
            this.objIUnknown = new IUnknown(lArray[0]);
            lArray = new long[]{0L};
            n5 = this.objIUnknown.QueryInterface(COM.IIDIPersistStorage, lArray);
            if (n5 != 0) {
                OLE.error(1001, n5);
            }
            IPersistStorage iPersistStorage = new IPersistStorage(lArray[0]);
            n5 = iPersistStorage.Load(this.tempStorage.getAddress());
            iPersistStorage.Release();
            if (n5 != 0) {
                OLE.error(1001, n5);
            }
        }
        this.addObjectReferences();
        if (COM.OleRun(this.objIUnknown.getAddress()) == 0) {
            this.state = 1;
        }
    }

    protected void addObjectReferences() {
        int n;
        Object object;
        long[] lArray = new long[]{0L};
        if (this.objIUnknown.QueryInterface(COM.IIDIPersist, lArray) == 0) {
            IPersist iPersist = new IPersist(lArray[0]);
            object = new GUID();
            if (iPersist.GetClassID((GUID)object) == 0) {
                this.objClsid = object;
            }
            iPersist.Release();
        }
        if ((n = this.objIUnknown.QueryInterface(COM.IIDIViewObject2, lArray = new long[]{0L})) != 0) {
            OLE.error(1003, n);
        }
        this.objIViewObject2 = new IViewObject2(lArray[0]);
        this.objIViewObject2.SetAdvise(this.aspect, 0, this.iAdviseSink.getAddress());
        lArray = new long[]{0L};
        n = this.objIUnknown.QueryInterface(COM.IIDIOleObject, lArray);
        if (n != 0) {
            OLE.error(1003, n);
        }
        this.objIOleObject = new IOleObject(lArray[0]);
        object = new long[]{0L};
        n = this.objIOleObject.GetClientSite((long[])object);
        if (object[0] == 0L) {
            this.objIOleObject.SetClientSite(this.iOleClientSite.getAddress());
        } else {
            this.Release();
        }
        int[] nArray = new int[]{0};
        this.objIOleObject.Advise(this.iAdviseSink.getAddress(), nArray);
        this.objIOleObject.SetHostNames("main", "main");
        COM.OleSetContainedObject(this.objIUnknown.getAddress(), true);
        lArray = new long[]{0L};
        if (this.objIUnknown.QueryInterface(COM.IIDIOleLink, lArray) == 0) {
            IOleLink iOleLink = new IOleLink(lArray[0]);
            long[] lArray2 = new long[]{0L};
            if (iOleLink.GetSourceMoniker(lArray2) == 0) {
                new IUnknown(lArray2[0]).Release();
                this.type = 0;
                iOleLink.BindIfRunning();
            } else {
                this.isStatic = true;
            }
            iOleLink.Release();
        }
    }

    protected int AddRef() {
        return ++this.refCount;
    }

    private int CanInPlaceActivate() {
        if (this.aspect == 1 && this.type == 1) {
            return 0;
        }
        return 1;
    }

    private int ContextSensitiveHelp(int n) {
        return 0;
    }

    protected void createCOMInterfaces() {
        this.iOleClientSite = new lIII(this, new int[]{2, 0, 0, 0, 3, 1, 0, 1, 0});
        this.iAdviseSink = new lIl(this, new int[]{2, 0, 0, 2, 2, 1, 0, 0});
        this.iOleInPlaceSite = new lIIl(this, new int[]{2, 0, 0, 1, 1, 0, 0, 0, 5, 1, 1, 0, 0, 0, 1});
        this.iOleDocumentSite = new llI(this, new int[]{2, 0, 0, 1});
    }

    protected IStorage createTempStorage() {
        long[] lArray = new long[]{0L};
        int n = 67108882;
        int n2 = COM.StgCreateDocfile(null, 67108882, 0, lArray);
        if (n2 != 0) {
            OLE.error(1000, n2);
        }
        return new IStorage(lArray[0]);
    }

    public void deactivateInPlaceClient() {
        if (this.objIOleInPlaceObject != null) {
            this.objIOleInPlaceObject.InPlaceDeactivate();
        }
    }

    private void deleteTempStorage() {
        if (this.tempStorage != null) {
            this.tempStorage.Release();
        }
        this.tempStorage = null;
    }

    protected void disposeCOMInterfaces() {
        if (this.iOleClientSite != null) {
            this.iOleClientSite.dispose();
        }
        this.iOleClientSite = null;
        if (this.iAdviseSink != null) {
            this.iAdviseSink.dispose();
        }
        this.iAdviseSink = null;
        if (this.iOleInPlaceSite != null) {
            this.iOleInPlaceSite.dispose();
        }
        this.iOleInPlaceSite = null;
        if (this.iOleDocumentSite != null) {
            this.iOleDocumentSite.dispose();
        }
        this.iOleDocumentSite = null;
    }

    public int doVerb(int n) {
        if (this.state == 0 && COM.OleRun(this.objIUnknown.getAddress()) == 0) {
            this.state = 1;
        }
        if (this.state == 0 || this.isStatic) {
            return -2147467259;
        }
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        int n2 = this.objIOleObject.DoVerb(n, null, this.iOleClientSite.getAddress(), 0, this.handle, rECT);
        if (this.state != 1 && this.inInit) {
            this.updateStorage();
            this.inInit = false;
        }
        return n2;
    }

    public int exec(int n, int n2, Variant variant, Variant variant2) {
        if (this.objIOleCommandTarget == null) {
            long[] lArray = new long[]{0L};
            if (this.objIUnknown.QueryInterface(COM.IIDIOleCommandTarget, lArray) != 0) {
                return 1003;
            }
            this.objIOleCommandTarget = new IOleCommandTarget(lArray[0]);
        }
        long l2 = 0L;
        if (variant != null) {
            l2 = OS.GlobalAlloc(64, VARIANT.sizeof);
            variant.getData(l2);
        }
        long l3 = 0L;
        if (variant2 != null) {
            l3 = OS.GlobalAlloc(64, VARIANT.sizeof);
            variant2.getData(l3);
        }
        int n3 = this.objIOleCommandTarget.Exec(null, n, n2, l2, l3);
        if (l2 != 0L) {
            COM.VariantClear(l2);
            OS.GlobalFree(l2);
        }
        if (l3 != 0L) {
            variant2.setData(l3);
            COM.VariantClear(l3);
            OS.GlobalFree(l3);
        }
        return n3;
    }

    IDispatch getAutomationObject() {
        long[] lArray = new long[]{0L};
        if (this.objIUnknown.QueryInterface(COM.IIDIDispatch, lArray) != 0) {
            return null;
        }
        return new IDispatch(lArray[0]);
    }

    protected GUID getClassID(String string) {
        int n;
        GUID gUID = new GUID();
        char[] cArray = null;
        if (string != null) {
            n = string.length();
            cArray = new char[n + 1];
            string.getChars(0, n, cArray, 0);
        }
        if (COM.CLSIDFromProgID(cArray, gUID) != 0 && (n = COM.CLSIDFromString(cArray, gUID)) != 0) {
            return null;
        }
        return gUID;
    }

    private int GetContainer(long l2) {
        if (l2 != 0L) {
            OS.MoveMemory(l2, new long[]{0L}, C.PTR_SIZEOF);
        }
        return -2147467262;
    }

    private SIZE getExtent() {
        SIZE sIZE = new SIZE();
        if (this.objIOleObject != null) {
            if (this.objIViewObject2 != null && !COM.OleIsRunning(this.objIOleObject.getAddress())) {
                this.objIViewObject2.GetExtent(this.aspect, -1, 0L, sIZE);
            } else {
                this.objIOleObject.GetExtent(this.aspect, sIZE);
            }
        }
        return this.xFormHimetricToPixels(sIZE);
    }

    public Rectangle getIndent() {
        return new Rectangle(this.indent.left, this.indent.right, this.indent.top, this.indent.bottom);
    }

    public String getProgramID() {
        return this.getProgID(this.appClsid);
    }

    String getProgID(GUID gUID) {
        long[] lArray;
        if (gUID != null && COM.ProgIDFromCLSID(gUID, lArray = new long[]{0L}) == 0) {
            long l2 = lArray[0];
            int n = OS.GlobalSize(l2);
            long l3 = OS.GlobalLock(l2);
            char[] cArray = new char[n];
            OS.MoveMemory(cArray, l3, n);
            OS.GlobalUnlock(l2);
            OS.GlobalFree(l2);
            String string = new String(cArray);
            int n2 = string.indexOf("\u0000");
            return string.substring(0, n2);
        }
        return null;
    }

    int ActivateMe(long l2) {
        Object object;
        if (l2 == 0L) {
            object = new long[]{0L};
            if (this.objIUnknown.QueryInterface(COM.IIDIOleDocument, (long[])object) != 0) {
                return -2147467259;
            }
            IOleDocument iOleDocument = new IOleDocument(object[0]);
            if (iOleDocument.CreateView(this.iOleInPlaceSite.getAddress(), 0L, 0, (long[])object) != 0) {
                return -2147467259;
            }
            iOleDocument.Release();
            this.objDocumentView = new IOleDocumentView(object[0]);
        } else {
            this.objDocumentView = new IOleDocumentView(l2);
            this.objDocumentView.AddRef();
            this.objDocumentView.SetInPlaceSite(this.iOleInPlaceSite.getAddress());
        }
        this.objDocumentView.UIActivate(1);
        object = this.getRect();
        this.objDocumentView.SetRect((RECT)object);
        this.objDocumentView.Show(1);
        return 0;
    }

    protected int GetWindow(long l2) {
        if (l2 == 0L) {
            return -2147024809;
        }
        if (this.frame == null) {
            OS.MoveMemory(l2, new long[]{0L}, C.PTR_SIZEOF);
            return -2147467263;
        }
        OS.MoveMemory(l2, new long[]{this.handle}, C.PTR_SIZEOF);
        return 0;
    }

    RECT getRect() {
        Rectangle rectangle = DPIUtil.autoScaleUp(this.getClientArea());
        RECT rECT = new RECT();
        rECT.left = rectangle.x;
        rECT.top = rectangle.y;
        rECT.right = rectangle.x + rectangle.width;
        rECT.bottom = rectangle.y + rectangle.height;
        return rECT;
    }

    private int GetWindowContext(long l2, long l3, long l4, long l5, long l6) {
        long l7;
        long l8;
        int n;
        if (this.frame == null || l2 == 0L) {
            return -2147467263;
        }
        long l9 = this.frame.getIOleInPlaceFrame();
        OS.MoveMemory(l2, new long[]{l9}, C.PTR_SIZEOF);
        this.frame.AddRef();
        if (l3 != 0L) {
            OS.MoveMemory(l3, new long[]{0L}, C.PTR_SIZEOF);
        }
        RECT rECT = this.getRect();
        if (l4 != 0L) {
            OS.MoveMemory(l4, rECT, RECT.sizeof);
        }
        if (l5 != 0L) {
            OS.MoveMemory(l5, rECT, RECT.sizeof);
        }
        OLEINPLACEFRAMEINFO oLEINPLACEFRAMEINFO = new OLEINPLACEFRAMEINFO();
        oLEINPLACEFRAMEINFO.cb = OLEINPLACEFRAMEINFO.sizeof;
        oLEINPLACEFRAMEINFO.fMDIApp = 0;
        oLEINPLACEFRAMEINFO.hwndFrame = this.frame.handle;
        Shell shell = this.getShell();
        Menu menu = shell.getMenuBar();
        if (menu != null && !menu.isDisposed() && (n = (int)OS.SendMessage(l8 = shell.handle, 32768, 0L, 0L)) != 0 && (l7 = OS.SendMessage(l8, 32769, 0L, 0L)) != 0L) {
            oLEINPLACEFRAMEINFO.cAccelEntries = n;
            oLEINPLACEFRAMEINFO.haccel = l7;
        }
        COM.MoveMemory(l6, oLEINPLACEFRAMEINFO, OLEINPLACEFRAMEINFO.sizeof);
        return 0;
    }

    boolean isICAClient() {
        return this.getProgramID().startsWith("Citrix.ICAClient");
    }

    public boolean isDirty() {
        long[] lArray = new long[]{0L};
        if (this.objIOleObject.QueryInterface(COM.IIDIPersistFile, lArray) != 0) {
            return true;
        }
        IPersistFile iPersistFile = new IPersistFile(lArray[0]);
        int n = iPersistFile.IsDirty();
        iPersistFile.Release();
        return n != 1;
    }

    private int OnClose() {
        return 0;
    }

    private int OnDataChange(long l2, long l3) {
        return 0;
    }

    private void onDispose(Event event) {
        this.inDispose = true;
        this.removeListener(12, this.listener);
        this.removeListener(15, this.listener);
        this.removeListener(16, this.listener);
        this.removeListener(9, this.listener);
        this.removeListener(31, this.listener);
        this.removeListener(1, this.listener);
        if (this.state != 0) {
            this.doVerb(-6);
        }
        this.deactivateInPlaceClient();
        this.releaseObjectInterfaces();
        this.deleteTempStorage();
        this.frame.removeListener(11, this.listener);
        this.frame.removeListener(10, this.listener);
        this.frame.Release();
        this.frame = null;
    }

    void onFocusIn(Event event) {
        long[] lArray;
        if (this.inDispose) {
            return;
        }
        if (this.state != 3 && this.objIUnknown.QueryInterface(COM.IIDIOleInPlaceObject, lArray = new long[]{0L}) == 0) {
            IOleInPlaceObject iOleInPlaceObject = new IOleInPlaceObject(lArray[0]);
            iOleInPlaceObject.Release();
            this.doVerb(-1);
        }
        if (this.objIOleInPlaceObject == null) {
            return;
        }
        if (this == null) {
            return;
        }
        lArray = new long[]{0L};
        this.objIOleInPlaceObject.GetWindow(lArray);
        if (lArray[0] == 0L) {
            return;
        }
        OS.SetFocus(lArray[0]);
    }

    void onFocusOut(Event event) {
    }

    private int OnInPlaceActivate() {
        this.state = 2;
        this.frame.setCurrentDocument(this);
        if (this.objIOleObject == null) {
            return 0;
        }
        long[] lArray = new long[]{0L};
        if (this.objIOleObject.QueryInterface(COM.IIDIOleInPlaceObject, lArray) == 0) {
            this.objIOleInPlaceObject = new IOleInPlaceObject(lArray[0]);
        }
        return 0;
    }

    private int OnInPlaceDeactivate() {
        if (this.objIOleInPlaceObject != null) {
            this.objIOleInPlaceObject.Release();
        }
        this.objIOleInPlaceObject = null;
        this.state = 1;
        this.redraw();
        Shell shell = this.getShell();
        if (this != null || this.frame.isFocusControl()) {
            shell.traverse(16);
        }
        return 0;
    }

    private int OnPosRectChange(long l2) {
        Point point = DPIUtil.autoScaleUp(this.getSize());
        this.setExtent(point.x, point.y);
        return 0;
    }

    private void onPaint(Event event) {
        if (this.state == 1 || this.state == 2) {
            SIZE sIZE = this.getExtent();
            Rectangle rectangle = DPIUtil.autoScaleUp(this.getClientArea());
            RECT rECT = new RECT();
            if (this.getProgramID().startsWith("Excel.Sheet")) {
                rECT.left = rectangle.x;
                rECT.right = rectangle.x + rectangle.height * sIZE.cx / sIZE.cy;
                rECT.top = rectangle.y;
                rECT.bottom = rectangle.y + rectangle.height;
            } else {
                rECT.left = rectangle.x;
                rECT.right = rectangle.x + sIZE.cx;
                rECT.top = rectangle.y;
                rECT.bottom = rectangle.y + sIZE.cy;
            }
            long l2 = OS.GlobalAlloc(64, RECT.sizeof);
            OS.MoveMemory(l2, rECT, RECT.sizeof);
            COM.OleDraw(this.objIUnknown.getAddress(), this.aspect, event.gc.handle, l2);
            OS.GlobalFree(l2);
        }
    }

    private void onResize(Event event) {
        this.setBounds();
    }

    private void OnSave() {
    }

    private int OnShowWindow(int n) {
        return 0;
    }

    private int OnUIActivate() {
        if (this.objIOleInPlaceObject == null) {
            return -2147467259;
        }
        this.state = 3;
        long[] lArray = new long[]{0L};
        if (this.objIOleInPlaceObject.GetWindow(lArray) == 0) {
            OS.SetWindowPos(lArray[0], 0L, 0, 0, 0, 0, 3);
        }
        return 0;
    }

    int OnUIDeactivate(int n) {
        Menu menu;
        if (this.frame == null || this.frame.isDisposed()) {
            return 0;
        }
        this.state = 2;
        this.frame.SetActiveObject(0L, 0L);
        this.redraw();
        Shell shell = this.getShell();
        if (this != null || this.frame.isFocusControl()) {
            shell.traverse(16);
        }
        if ((menu = shell.getMenuBar()) == null || menu.isDisposed()) {
            return 0;
        }
        long l2 = shell.handle;
        OS.SetMenu(l2, menu.handle);
        return COM.OleSetMenuDescriptor(0L, l2, 0L, 0L, 0L);
    }

    private void onTraverse(Event event) {
        switch (event.detail) {
            case 2: 
            case 4: 
            case 8: 
            case 16: 
            case 128: 
            case 256: 
            case 512: {
                event.doit = true;
            }
        }
    }

    private int OnViewChange(int n, int n2) {
        return 0;
    }

    protected int QueryInterface(long l2, long l3) {
        String string;
        if (l2 == 0L || l3 == 0L) {
            return -2147467262;
        }
        GUID gUID = new GUID();
        COM.MoveMemory(gUID, l2, GUID.sizeof);
        if (COM.IsEqualGUID(gUID, COM.IIDIUnknown) || COM.IsEqualGUID(gUID, COM.IIDIOleClientSite)) {
            OS.MoveMemory(l3, new long[]{this.iOleClientSite.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAdviseSink)) {
            OS.MoveMemory(l3, new long[]{this.iAdviseSink.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIOleInPlaceSite)) {
            OS.MoveMemory(l3, new long[]{this.iOleInPlaceSite.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIOleDocumentSite) && !(string = this.getProgramID()).startsWith("PowerPoint")) {
            OS.MoveMemory(l3, new long[]{this.iOleDocumentSite.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        OS.MoveMemory(l3, new long[]{0L}, C.PTR_SIZEOF);
        return -2147467262;
    }

    public int queryStatus(int n) {
        Object object;
        if (this.objIOleCommandTarget == null) {
            object = new long[]{0L};
            if (this.objIUnknown.QueryInterface(COM.IIDIOleCommandTarget, (long[])object) != 0) {
                return 0;
            }
            this.objIOleCommandTarget = new IOleCommandTarget((long)object[0]);
        }
        object = new OLECMD();
        ((OLECMD)object).cmdID = n;
        int n2 = this.objIOleCommandTarget.QueryStatus(null, 1, (OLECMD)object, 0L);
        if (n2 != 0) {
            return 0;
        }
        return ((OLECMD)object).cmdf;
    }

    protected int Release() {
        --this.refCount;
        if (this.refCount == 0) {
            this.disposeCOMInterfaces();
        }
        return this.refCount;
    }

    protected void releaseObjectInterfaces() {
        if (this.objIOleInPlaceObject != null) {
            this.objIOleInPlaceObject.Release();
        }
        this.objIOleInPlaceObject = null;
        if (this.objIOleObject != null) {
            this.objIOleObject.Close(1);
            this.objIOleObject.Release();
        }
        this.objIOleObject = null;
        if (this.objDocumentView != null) {
            this.objDocumentView.Release();
        }
        this.objDocumentView = null;
        if (this.objIViewObject2 != null) {
            this.objIViewObject2.SetAdvise(this.aspect, 0, 0L);
            this.objIViewObject2.Release();
        }
        this.objIViewObject2 = null;
        if (this.objIOleCommandTarget != null) {
            this.objIOleCommandTarget.Release();
        }
        this.objIOleCommandTarget = null;
        if (this.objIUnknown != null) {
            this.objIUnknown.Release();
        }
        this.objIUnknown = null;
        if (COM.FreeUnusedLibraries) {
            COM.CoFreeUnusedLibraries();
        }
    }

    public boolean save(File file, boolean bl) {
        OleClientSite oleClientSite = this;
        if (false == null) {
            return this.saveOffice2007(file);
        }
        if (bl) {
            return this.saveToStorageFile(file);
        }
        return this.saveToTraditionalFile(file);
    }

    private boolean saveFromContents(long l2, File file) {
        boolean bl = false;
        IStream iStream = new IStream(l2);
        iStream.AddRef();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            int n = 4096;
            long l3 = OS.CoTaskMemAlloc(4096);
            int[] nArray = new int[]{0};
            while (iStream.Read(l3, 4096, nArray) == 0 && nArray[0] > 0) {
                byte[] byArray = new byte[nArray[0]];
                OS.MoveMemory(byArray, l3, nArray[0]);
                fileOutputStream.write(byArray);
                bl = true;
            }
            OS.CoTaskMemFree(l3);
            fileOutputStream.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        iStream.Release();
        return bl;
    }

    private int SaveObject() {
        this.updateStorage();
        return 0;
    }

    private boolean saveOffice2007(File file) {
        if (file == null || file.isDirectory()) {
            return false;
        }
        if (this == null) {
            return false;
        }
        boolean bl = false;
        long[] lArray = new long[]{0L};
        IPersistStorage iPersistStorage = null;
        if (this.objIUnknown.QueryInterface(COM.IIDIPersistStorage, lArray) == 0) {
            iPersistStorage = new IPersistStorage(lArray[0]);
            this.tempStorage.AddRef();
            iPersistStorage.HandsOffStorage();
        }
        long[] lArray2 = new long[]{0L};
        int n = 16;
        if (this.tempStorage.OpenStream("Package", 0L, 16, 0, lArray2) == 0) {
            bl = this.saveFromContents(lArray2[0], file);
        }
        if (iPersistStorage != null) {
            iPersistStorage.SaveCompleted(this.tempStorage.getAddress());
            this.tempStorage.Release();
            iPersistStorage.Release();
        }
        return bl;
    }

    private boolean saveToStorageFile(File file) {
        if (file == null || file.isDirectory()) {
            return false;
        }
        if (this == null) {
            return false;
        }
        long[] lArray = new long[]{0L};
        if (this.objIOleObject.QueryInterface(COM.IIDIPersistStorage, lArray) != 0) {
            return false;
        }
        IPersistStorage iPersistStorage = new IPersistStorage(lArray[0]);
        lArray = new long[]{0L};
        char[] cArray = file.getAbsolutePath().toCharArray();
        int n = 69650;
        int n2 = COM.StgCreateDocfile(cArray, 69650, 0, lArray);
        if (n2 != 0) {
            boolean bl = false;
            iPersistStorage.Release();
            return bl;
        }
        IStorage iStorage = new IStorage(lArray[0]);
        if (COM.OleSave(iPersistStorage.getAddress(), iStorage.getAddress(), false) == 0 && iStorage.Commit(0) == 0) {
            boolean bl = true;
            iStorage.Release();
            iPersistStorage.Release();
            return bl;
        }
        iStorage.Release();
        iPersistStorage.Release();
        return false;
    }

    /*
     * Exception decompiling
     */
    private boolean saveToTraditionalFile(File var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl52 : ICONST_0 - null : trying to set 0 previously set to 2
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private int Scroll(long l2) {
        return 0;
    }

    void setBorderSpace(RECT rECT) {
        this.borderWidths = rECT;
        this.setBounds();
    }

    void setBounds() {
        Rectangle rectangle = DPIUtil.autoScaleUp(this.frame.getClientArea());
        this.setBounds(DPIUtil.autoScaleDown(this.borderWidths.left), DPIUtil.autoScaleDown(this.borderWidths.top), DPIUtil.autoScaleDown(rectangle.width - this.borderWidths.left - this.borderWidths.right), DPIUtil.autoScaleDown(rectangle.height - this.borderWidths.top - this.borderWidths.bottom));
        this.setObjectRects();
    }

    private void setExtent(int n, int n2) {
        if (this.objIOleObject == null || this.isStatic || this.inUpdate) {
            return;
        }
        SIZE sIZE = this.getExtent();
        if (n == sIZE.cx && n2 == sIZE.cy) {
            return;
        }
        SIZE sIZE2 = new SIZE();
        sIZE2.cx = n;
        sIZE2.cy = n2;
        sIZE2 = this.xFormPixelsToHimetric(sIZE2);
        boolean bl = COM.OleIsRunning(this.objIOleObject.getAddress());
        if (!bl) {
            COM.OleRun(this.objIOleObject.getAddress());
        }
        if (this.objIOleObject.SetExtent(this.aspect, sIZE2) == 0) {
            this.inUpdate = true;
            this.objIOleObject.Update();
            this.inUpdate = false;
            if (!bl) {
                this.objIOleObject.Close(0);
            }
        }
    }

    public void setIndent(Rectangle rectangle) {
        this.indent = new RECT();
        this.indent.left = rectangle.x;
        this.indent.right = rectangle.width;
        this.indent.top = rectangle.y;
        this.indent.bottom = rectangle.height;
    }

    private void setObjectRects() {
        if (this.objIOleInPlaceObject == null) {
            return;
        }
        RECT rECT = this.getRect();
        this.objIOleInPlaceObject.SetObjectRects(rECT, rECT);
    }

    private int ShowObject() {
        this.setBounds();
        return 0;
    }

    public void showProperties(String string) {
        long[] lArray = new long[]{0L};
        if (this.objIUnknown.QueryInterface(COM.IIDISpecifyPropertyPages, lArray) != 0) {
            return;
        }
        ISpecifyPropertyPages iSpecifyPropertyPages = new ISpecifyPropertyPages(lArray[0]);
        CAUUID cAUUID = new CAUUID();
        int n = iSpecifyPropertyPages.GetPages(cAUUID);
        iSpecifyPropertyPages.Release();
        if (n != 0) {
            return;
        }
        char[] cArray = null;
        if (string != null) {
            cArray = new char[string.length()];
            string.getChars(0, string.length(), cArray, 0);
        }
        n = COM.OleCreatePropertyFrame(this.frame.handle, 10, 10, cArray, 1, new long[]{this.objIUnknown.getAddress()}, cAUUID.cElems, cAUUID.pElems, 2048, 0, 0L);
        OS.CoTaskMemFree(cAUUID.pElems);
    }

    private SIZE xFormHimetricToPixels(SIZE sIZE) {
        long l2 = OS.GetDC(0L);
        int n = OS.GetDeviceCaps(l2, 88);
        int n2 = OS.GetDeviceCaps(l2, 90);
        OS.ReleaseDC(0L, l2);
        int n3 = Compatibility.round(sIZE.cx * n, 2540);
        int n4 = Compatibility.round(sIZE.cy * n2, 2540);
        SIZE sIZE2 = new SIZE();
        sIZE2.cx = n3;
        sIZE2.cy = n4;
        return sIZE2;
    }

    private SIZE xFormPixelsToHimetric(SIZE sIZE) {
        long l2 = OS.GetDC(0L);
        int n = OS.GetDeviceCaps(l2, 88);
        int n2 = OS.GetDeviceCaps(l2, 90);
        OS.ReleaseDC(0L, l2);
        int n3 = Compatibility.round(sIZE.cx * 2540, n);
        int n4 = Compatibility.round(sIZE.cy * 2540, n2);
        SIZE sIZE2 = new SIZE();
        sIZE2.cx = n3;
        sIZE2.cy = n4;
        return sIZE2;
    }

    static void access$000(OleClientSite oleClientSite, Event event) {
        oleClientSite.onResize(event);
    }

    static void access$100(OleClientSite oleClientSite, Event event) {
        oleClientSite.onDispose(event);
    }

    static void access$200(OleClientSite oleClientSite, Event event) {
        oleClientSite.onPaint(event);
    }

    static void access$300(OleClientSite oleClientSite, Event event) {
        oleClientSite.onTraverse(event);
    }

    static int access$400(OleClientSite oleClientSite) {
        return oleClientSite.SaveObject();
    }

    static int access$500(OleClientSite oleClientSite, long l2) {
        return oleClientSite.GetContainer(l2);
    }

    static int access$600(OleClientSite oleClientSite) {
        return oleClientSite.ShowObject();
    }

    static int access$700(OleClientSite oleClientSite, int n) {
        return oleClientSite.OnShowWindow(n);
    }

    static int access$800(OleClientSite oleClientSite, long l2, long l3) {
        return oleClientSite.OnDataChange(l2, l3);
    }

    static int access$900(OleClientSite oleClientSite, int n, int n2) {
        return oleClientSite.OnViewChange(n, n2);
    }

    static void access$1000(OleClientSite oleClientSite) {
        oleClientSite.OnSave();
    }

    static int access$1100(OleClientSite oleClientSite) {
        return oleClientSite.OnClose();
    }

    static int access$1200(OleClientSite oleClientSite, int n) {
        return oleClientSite.ContextSensitiveHelp(n);
    }

    static int access$1300(OleClientSite oleClientSite) {
        return oleClientSite.CanInPlaceActivate();
    }

    static int access$1400(OleClientSite oleClientSite) {
        return oleClientSite.OnInPlaceActivate();
    }

    static int access$1500(OleClientSite oleClientSite) {
        return oleClientSite.OnUIActivate();
    }

    static int access$1600(OleClientSite oleClientSite, long l2, long l3, long l4, long l5, long l6) {
        return oleClientSite.GetWindowContext(l2, l3, l4, l5, l6);
    }

    static int access$1700(OleClientSite oleClientSite, long l2) {
        return oleClientSite.Scroll(l2);
    }

    static int access$1800(OleClientSite oleClientSite) {
        return oleClientSite.OnInPlaceDeactivate();
    }

    static int access$1900(OleClientSite oleClientSite, long l2) {
        return oleClientSite.OnPosRectChange(l2);
    }
}

