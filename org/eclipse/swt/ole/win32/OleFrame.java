/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.ole.win32;

import java.util.ArrayList;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.LONG;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IOleInPlaceActiveObject;
import org.eclipse.swt.internal.win32.GUITHREADINFO;
import org.eclipse.swt.internal.win32.MENUITEMINFO;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.ll;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

public final class OleFrame
extends Composite {
    private COMObject iOleInPlaceFrame;
    private IOleInPlaceActiveObject objIOleInPlaceActiveObject;
    private OleClientSite currentdoc;
    private int refCount = 0;
    private MenuItem[] fileMenuItems;
    private MenuItem[] containerMenuItems;
    private MenuItem[] windowMenuItems;
    private Listener listener;
    private long shellHandle;
    private long oldMenuHandle;
    private long newMenuHandle;
    private static long lastActivatedMenuHandle;
    private static String CHECK_FOCUS;
    private static String HHOOK;
    private static String HHOOKMSG;
    private static boolean ignoreNextKey;
    private static final short[] ACCENTS;
    private static final String CONSUME_KEY = "org.eclipse.swt.OleFrame.ConsumeKey";
    private static final String ACCEL_KEY_HIT = "org.eclipse.swt.internal.win32.accelKeyHit";

    public OleFrame(Composite composite, int n) {
        super(composite, n);
        this.createCOMInterfaces();
        this.listener = this::lambda$new$0;
        this.addListener(26, this.listener);
        this.addListener(27, this.listener);
        this.addListener(12, this.listener);
        this.addListener(11, this.listener);
        this.addListener(10, this.listener);
        this.AddRef();
        Display display = this.getDisplay();
        OleFrame.initCheckFocus(display);
        OleFrame.initMsgHook(display);
    }

    private static void initCheckFocus(Display display) {
        if (display.getData(CHECK_FOCUS) != null) {
            return;
        }
        display.setData(CHECK_FOCUS, CHECK_FOCUS);
        int n = 50;
        Runnable[] runnableArray = new Runnable[]{null};
        Control[] controlArray = new Control[]{null};
        runnableArray[0] = () -> OleFrame.lambda$initCheckFocus$1(controlArray, display, runnableArray);
        display.timerExec(50, runnableArray[0]);
    }

    private static void initMsgHook(Display display) {
        int n;
        if (display.getData(HHOOK) != null) {
            return;
        }
        Callback callback = new Callback(OleFrame.class, "getMsgProc", 3);
        long l2 = callback.getAddress();
        long l3 = OS.SetWindowsHookEx(3, l2, 0L, n = OS.GetCurrentThreadId());
        if (l3 == 0L) {
            callback.dispose();
            return;
        }
        display.setData(HHOOK, new LONG(l3));
        display.setData(HHOOKMSG, new MSG());
        display.disposeExec(() -> OleFrame.lambda$initMsgHook$2(l3, callback));
    }

    static long getMsgProc(long l2, long l3, long l4) {
        Display display = Display.getCurrent();
        if (display == null) {
            return 0L;
        }
        LONG lONG = (LONG)display.getData(HHOOK);
        if (lONG == null) {
            return 0L;
        }
        if (l2 < 0L || (l3 & 1L) == 0L) {
            return OS.CallNextHookEx(lONG.value, (int)l2, l3, l4);
        }
        MSG mSG = (MSG)display.getData(HHOOKMSG);
        OS.MoveMemory(mSG, l4, MSG.sizeof);
        int n = mSG.message;
        if (256 <= n && n <= 264 && display != null) {
            Widget widget = null;
            long l5 = mSG.hwnd;
            while (l5 != 0L && (widget = display.findWidget(l5)) == null) {
                l5 = OS.GetParent(l5);
            }
            if (widget instanceof OleClientSite) {
                OleClientSite oleClientSite = (OleClientSite)widget;
                if (oleClientSite.handle == l5) {
                    Object object;
                    boolean bl = false;
                    int n2 = OS.GetWindowThreadProcessId(mSG.hwnd, null);
                    GUITHREADINFO gUITHREADINFO = new GUITHREADINFO();
                    gUITHREADINFO.cbSize = GUITHREADINFO.sizeof;
                    boolean bl2 = OS.GetGUIThreadInfo(n2, gUITHREADINFO);
                    int n3 = 30;
                    if (!bl2 || (gUITHREADINFO.flags & 0x1E) == 0) {
                        OleFrame oleFrame = oleClientSite.frame;
                        oleFrame.setData(CONSUME_KEY, null);
                        display.setData(ACCEL_KEY_HIT, Boolean.TRUE);
                        bl = oleFrame.translateOleAccelerator(mSG);
                        if (display.isDisposed()) {
                            return 0L;
                        }
                        display.setData(ACCEL_KEY_HIT, Boolean.FALSE);
                        if (oleFrame.isDisposed()) {
                            return 0L;
                        }
                        object = (String)oleFrame.getData(CONSUME_KEY);
                        if (object != null) {
                            bl = ((String)object).equals("true");
                        }
                        oleFrame.setData(CONSUME_KEY, null);
                    }
                    boolean bl3 = false;
                    block0 : switch (mSG.message) {
                        case 256: 
                        case 260: {
                            switch ((int)mSG.wParam) {
                                case 16: 
                                case 17: 
                                case 18: 
                                case 20: 
                                case 144: 
                                case 145: {
                                    break block0;
                                }
                            }
                            int n4 = OS.MapVirtualKey((int)mSG.wParam, 2);
                            if (n4 == 0) break;
                            boolean bl4 = bl3 = (n4 & Integer.MIN_VALUE) != 0;
                            if (bl3) break;
                            for (Object object2 : (MSG)ACCENTS) {
                                short s = OS.VkKeyScan((short)object2);
                                if (s == -1 || (long)(s & 0xFF) != mSG.wParam) continue;
                                int n5 = s >> 8;
                                if (OS.GetKeyState(16) < 0 != ((n5 & 1) != 0) || OS.GetKeyState(17) < 0 != ((n5 & 2) != 0) || OS.GetKeyState(18) < 0 != ((n5 & 4) != 0)) continue;
                                if ((n5 & 7) == 0) break block0;
                                bl3 = true;
                                break block0;
                            }
                            break;
                        }
                    }
                    if (!(bl || bl3 || ignoreNextKey)) {
                        long l6 = mSG.hwnd;
                        mSG.hwnd = oleClientSite.handle;
                        bl = OS.DispatchMessage(mSG) == 1L;
                        mSG.hwnd = l6;
                    }
                    block6 : switch (mSG.message) {
                        case 256: 
                        case 260: {
                            switch ((int)mSG.wParam) {
                                case 16: 
                                case 17: 
                                case 18: 
                                case 20: 
                                case 144: 
                                case 145: {
                                    break block6;
                                }
                            }
                            ignoreNextKey = bl3;
                            break;
                        }
                    }
                    if (bl) {
                        mSG.message = 0;
                        object = mSG;
                        MSG mSG2 = mSG;
                        long l7 = 0L;
                        mSG2.lParam = 0L;
                        ((MSG)object).wParam = 0L;
                        OS.MoveMemory(l4, mSG, MSG.sizeof);
                        return 0L;
                    }
                }
            }
        }
        return OS.CallNextHookEx(lONG.value, (int)l2, l3, l4);
    }

    int AddRef() {
        return ++this.refCount;
    }

    private int ContextSensitiveHelp(int n) {
        return 0;
    }

    private void createCOMInterfaces() {
        this.iOleInPlaceFrame = new ll(this, new int[]{2, 0, 0, 1, 1, 1, 1, 1, 2, 2, 3, 1, 1, 1, 2});
    }

    private void disposeCOMInterfaces() {
        if (this.iOleInPlaceFrame != null) {
            this.iOleInPlaceFrame.dispose();
        }
        this.iOleInPlaceFrame = null;
    }

    private int GetBorder(long l2) {
        if (l2 == 0L) {
            return -2147024809;
        }
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        OS.MoveMemory(l2, rECT, RECT.sizeof);
        return 0;
    }

    public MenuItem[] getContainerMenus() {
        return this.containerMenuItems;
    }

    public MenuItem[] getFileMenus() {
        return this.fileMenuItems;
    }

    long getIOleInPlaceFrame() {
        return this.iOleInPlaceFrame.getAddress();
    }

    private long getMenuItemID(long l2, int n) {
        long l3 = 0L;
        MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
        mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        mENUITEMINFO.fMask = 7;
        OS.GetMenuItemInfo(l2, n, true, mENUITEMINFO);
        l3 = (mENUITEMINFO.fState & 0x10) == 16 ? mENUITEMINFO.hSubMenu : (long)mENUITEMINFO.wID;
        return l3;
    }

    private int GetWindow(long l2) {
        if (l2 != 0L) {
            OS.MoveMemory(l2, new long[]{this.handle}, C.PTR_SIZEOF);
        }
        return 0;
    }

    public MenuItem[] getWindowMenus() {
        return this.windowMenuItems;
    }

    private int InsertMenus(long l2, long l3) {
        Menu menu = this.getShell().getMenuBar();
        if (menu == null || menu.isDisposed()) {
            OS.MoveMemory(l3, new int[]{0}, 4);
            return 0;
        }
        long l4 = menu.handle;
        MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
        long l5 = OS.GetProcessHeap();
        int n = 128;
        int n2 = 256;
        long l6 = OS.HeapAlloc(l5, 8, 256);
        mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        mENUITEMINFO.fMask = 55;
        mENUITEMINFO.dwTypeData = l6;
        mENUITEMINFO.cch = 128;
        int n3 = 0;
        int n4 = 0;
        if (this.fileMenuItems != null) {
            for (MenuItem menuItem : this.fileMenuItems) {
                if (menuItem == null) continue;
                int n5 = menuItem.getParent().indexOf(menuItem);
                mENUITEMINFO.cch = 128;
                if (!OS.GetMenuItemInfo(l4, n5, true, mENUITEMINFO) || !OS.InsertMenuItem(l2, n4, true, mENUITEMINFO)) continue;
                ++n3;
                ++n4;
            }
        }
        OS.MoveMemory(l3, new int[]{n3}, 4);
        int n6 = 0;
        if (this.containerMenuItems != null) {
            for (MenuItem menuItem : this.containerMenuItems) {
                if (menuItem == null) continue;
                int n7 = menuItem.getParent().indexOf(menuItem);
                mENUITEMINFO.cch = 128;
                if (!OS.GetMenuItemInfo(l4, n7, true, mENUITEMINFO) || !OS.InsertMenuItem(l2, n4, true, mENUITEMINFO)) continue;
                ++n6;
                ++n4;
            }
        }
        OS.MoveMemory(l3 + 8L, new int[]{n6}, 4);
        int n8 = 0;
        if (this.windowMenuItems != null) {
            for (MenuItem menuItem : this.windowMenuItems) {
                if (menuItem == null) continue;
                int n9 = menuItem.getParent().indexOf(menuItem);
                mENUITEMINFO.cch = 128;
                if (!OS.GetMenuItemInfo(l4, n9, true, mENUITEMINFO) || !OS.InsertMenuItem(l2, n4, true, mENUITEMINFO)) continue;
                ++n8;
                ++n4;
            }
        }
        OS.MoveMemory(l3 + 16L, new int[]{n8}, 4);
        if (l6 != 0L) {
            OS.HeapFree(l5, 0, l6);
        }
        return 0;
    }

    void onActivate(Event event) {
        if (this.objIOleInPlaceActiveObject != null) {
            this.objIOleInPlaceActiveObject.OnFrameWindowActivate(true);
        }
    }

    void onDeactivate(Event event) {
        if (this.objIOleInPlaceActiveObject != null) {
            this.objIOleInPlaceActiveObject.OnFrameWindowActivate(false);
        }
    }

    private void onDispose(Event event) {
        this.releaseObjectInterfaces();
        this.currentdoc = null;
        this.Release();
        this.removeListener(26, this.listener);
        this.removeListener(27, this.listener);
        this.removeListener(12, this.listener);
        this.removeListener(11, this.listener);
        this.removeListener(10, this.listener);
    }

    void onFocusIn(Event event) {
        if (lastActivatedMenuHandle != this.newMenuHandle) {
            this.currentdoc.doVerb(-1);
        }
        if (OS.GetMenu(this.shellHandle) != this.newMenuHandle) {
            OS.SetMenu(this.shellHandle, this.newMenuHandle);
        }
    }

    void onFocusOut(Event event) {
        Control control = this.getDisplay().getFocusControl();
        if (OS.GetMenu(this.shellHandle) != this.oldMenuHandle && control != null && control.handle != this.shellHandle) {
            OS.SetMenu(this.shellHandle, this.oldMenuHandle);
        }
    }

    private void onResize(Event event) {
        if (this.objIOleInPlaceActiveObject != null) {
            RECT rECT = new RECT();
            OS.GetClientRect(this.handle, rECT);
            this.objIOleInPlaceActiveObject.ResizeBorder(rECT, this.iOleInPlaceFrame.getAddress(), true);
        }
    }

    private int QueryInterface(long l2, long l3) {
        if (l2 == 0L || l3 == 0L) {
            return -2147024809;
        }
        GUID gUID = new GUID();
        COM.MoveMemory(gUID, l2, GUID.sizeof);
        if (COM.IsEqualGUID(gUID, COM.IIDIUnknown) || COM.IsEqualGUID(gUID, COM.IIDIOleInPlaceFrame)) {
            OS.MoveMemory(l3, new long[]{this.iOleInPlaceFrame.getAddress()}, C.PTR_SIZEOF);
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

    private void releaseObjectInterfaces() {
        if (this.objIOleInPlaceActiveObject != null) {
            this.objIOleInPlaceActiveObject.Release();
        }
        this.objIOleInPlaceActiveObject = null;
    }

    private int RemoveMenus(long l2) {
        int n;
        long l3;
        int n2;
        Menu menu = this.getShell().getMenuBar();
        if (menu == null || menu.isDisposed()) {
            return 1;
        }
        long l4 = menu.handle;
        ArrayList<LONG> arrayList = new ArrayList<LONG>();
        if (this.fileMenuItems != null) {
            for (MenuItem menuItem : this.fileMenuItems) {
                if (menuItem == null || menuItem.isDisposed()) continue;
                n2 = menuItem.getParent().indexOf(menuItem);
                l3 = this.getMenuItemID(l4, n2);
                arrayList.add(new LONG(l3));
            }
        }
        if (this.containerMenuItems != null) {
            for (MenuItem menuItem : this.containerMenuItems) {
                if (menuItem == null || menuItem.isDisposed()) continue;
                n2 = menuItem.getParent().indexOf(menuItem);
                l3 = this.getMenuItemID(l4, n2);
                arrayList.add(new LONG(l3));
            }
        }
        if (this.windowMenuItems != null) {
            for (MenuItem menuItem : this.windowMenuItems) {
                if (menuItem == null || menuItem.isDisposed()) continue;
                n2 = menuItem.getParent().indexOf(menuItem);
                l3 = this.getMenuItemID(l4, n2);
                arrayList.add(new LONG(l3));
            }
        }
        int n3 = n = OS.GetMenuItemCount(l2) - 1;
        while (n >= 0) {
            long l5 = this.getMenuItemID(l2, n);
            if (arrayList.contains(new LONG(l5))) {
                OS.RemoveMenu(l2, n, 1024);
            }
            --n;
        }
        return 0;
    }

    private int RequestBorderSpace(long l2) {
        return 0;
    }

    int SetActiveObject(long l2, long l3) {
        if (this.objIOleInPlaceActiveObject != null) {
            this.objIOleInPlaceActiveObject.Release();
            this.objIOleInPlaceActiveObject = null;
        }
        if (l2 != 0L) {
            this.objIOleInPlaceActiveObject = new IOleInPlaceActiveObject(l2);
            this.objIOleInPlaceActiveObject.AddRef();
        }
        return 0;
    }

    private int SetBorderSpace(long l2) {
        if (this.objIOleInPlaceActiveObject == null) {
            return 0;
        }
        RECT rECT = new RECT();
        if (l2 == 0L || this.currentdoc == null) {
            return 0;
        }
        COM.MoveMemory(rECT, l2, RECT.sizeof);
        this.currentdoc.setBorderSpace(rECT);
        return 0;
    }

    public void setContainerMenus(MenuItem[] menuItemArray) {
        this.containerMenuItems = menuItemArray;
    }

    OleClientSite getCurrentDocument() {
        return this.currentdoc;
    }

    void setCurrentDocument(OleClientSite oleClientSite) {
        this.currentdoc = oleClientSite;
        if (this.currentdoc != null && this.objIOleInPlaceActiveObject != null) {
            RECT rECT = new RECT();
            OS.GetClientRect(this.handle, rECT);
            this.objIOleInPlaceActiveObject.ResizeBorder(rECT, this.iOleInPlaceFrame.getAddress(), true);
        }
    }

    public void setFileMenus(MenuItem[] menuItemArray) {
        this.fileMenuItems = menuItemArray;
    }

    private int SetMenu(long l2, long l3, long l4) {
        Menu menu;
        long l5 = 0L;
        if (this.objIOleInPlaceActiveObject != null) {
            l5 = this.objIOleInPlaceActiveObject.getAddress();
        }
        if ((menu = this.getShell().getMenuBar()) == null || menu.isDisposed()) {
            return COM.OleSetMenuDescriptor(0L, this.getShell().handle, l4, this.iOleInPlaceFrame.getAddress(), l5);
        }
        long l6 = menu.getShell().handle;
        if (l2 == 0L && l3 == 0L) {
            l2 = menu.handle;
        }
        if (l2 == 0L) {
            return -2147467259;
        }
        this.shellHandle = l6;
        this.oldMenuHandle = menu.handle;
        lastActivatedMenuHandle = this.newMenuHandle = l2;
        return COM.OleSetMenuDescriptor(l3, l6, l4, this.iOleInPlaceFrame.getAddress(), l5);
    }

    public void setWindowMenus(MenuItem[] menuItemArray) {
        this.windowMenuItems = menuItemArray;
    }

    private boolean translateOleAccelerator(MSG mSG) {
        if (this.objIOleInPlaceActiveObject == null) {
            return false;
        }
        int n = this.objIOleInPlaceActiveObject.TranslateAccelerator(mSG);
        return n != 1 && n != -2147467263;
    }

    private int TranslateAccelerator(long l2, int n) {
        Menu menu = this.getShell().getMenuBar();
        if (menu == null || menu.isDisposed() || !menu.isEnabled()) {
            return 1;
        }
        if (n < 0) {
            return 1;
        }
        Shell shell = menu.getShell();
        long l3 = shell.handle;
        long l4 = OS.SendMessage(l3, 32769, 0L, 0L);
        if (l4 == 0L) {
            return 1;
        }
        MSG mSG = new MSG();
        OS.MoveMemory(mSG, l2, MSG.sizeof);
        int n2 = OS.TranslateAccelerator(l3, l4, mSG);
        return n2 == 0 ? 1 : 0;
    }

    private static void lambda$initMsgHook$2(long l2, Callback callback) {
        if (l2 != 0L) {
            OS.UnhookWindowsHookEx(l2);
        }
        if (callback != null) {
            callback.dispose();
        }
    }

    private static void lambda$initCheckFocus$1(Control[] controlArray, Display display, Runnable[] runnableArray) {
        if (controlArray[0] instanceof OleClientSite && !controlArray[0].isDisposed()) {
            long l2 = OS.GetFocus();
            while (l2 != 0L) {
                long l3 = OS.GetWindow(l2, 4);
                if (l3 != 0L) {
                    display.timerExec(50, runnableArray[0]);
                    return;
                }
                l2 = OS.GetParent(l2);
            }
        }
        if (controlArray[0] == null || controlArray[0].isDisposed() || !controlArray[0].isFocusControl()) {
            Object object;
            Control control = display.getFocusControl();
            if (control instanceof OleFrame) {
                object = (OleFrame)control;
                control = ((OleFrame)object).getCurrentDocument();
            }
            if (controlArray[0] != control) {
                object = new Event();
                if (controlArray[0] instanceof OleClientSite && !controlArray[0].isDisposed()) {
                    controlArray[0].notifyListeners(16, (Event)object);
                }
                if (control instanceof OleClientSite && !control.isDisposed()) {
                    control.notifyListeners(15, (Event)object);
                }
            }
            controlArray[0] = control;
        }
        display.timerExec(50, runnableArray[0]);
    }

    private void lambda$new$0(Event event) {
        switch (event.type) {
            case 26: {
                this.onActivate(event);
                break;
            }
            case 27: {
                this.onDeactivate(event);
                break;
            }
            case 12: {
                this.onDispose(event);
                break;
            }
            case 10: 
            case 11: {
                this.onResize(event);
                break;
            }
            default: {
                OLE.error(20);
            }
        }
    }

    static int access$000(OleFrame oleFrame, long l2, long l3) {
        return oleFrame.QueryInterface(l2, l3);
    }

    static int access$100(OleFrame oleFrame, long l2) {
        return oleFrame.GetWindow(l2);
    }

    static int access$200(OleFrame oleFrame, int n) {
        return oleFrame.ContextSensitiveHelp(n);
    }

    static int access$300(OleFrame oleFrame, long l2) {
        return oleFrame.GetBorder(l2);
    }

    static int access$400(OleFrame oleFrame, long l2) {
        return oleFrame.RequestBorderSpace(l2);
    }

    static int access$500(OleFrame oleFrame, long l2) {
        return oleFrame.SetBorderSpace(l2);
    }

    static int access$600(OleFrame oleFrame, long l2, long l3) {
        return oleFrame.InsertMenus(l2, l3);
    }

    static int access$700(OleFrame oleFrame, long l2, long l3, long l4) {
        return oleFrame.SetMenu(l2, l3, l4);
    }

    static int access$800(OleFrame oleFrame, long l2) {
        return oleFrame.RemoveMenus(l2);
    }

    static int access$900(OleFrame oleFrame, long l2, int n) {
        return oleFrame.TranslateAccelerator(l2, n);
    }

    static {
        CHECK_FOCUS = "OLE_CHECK_FOCUS";
        HHOOK = "OLE_HHOOK";
        HHOOKMSG = "OLE_HHOOK_MSG";
        ACCENTS = new short[]{126, 96, 39, 94, 34};
    }
}

