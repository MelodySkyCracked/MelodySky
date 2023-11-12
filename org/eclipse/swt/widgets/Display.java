/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.function.Consumer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.SwtCallable;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.DefaultExceptionHandler;
import org.eclipse.swt.internal.ExceptionStash;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.ICustomDestinationList;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.HIGHCONTRAST;
import org.eclipse.swt.internal.win32.ICONINFO;
import org.eclipse.swt.internal.win32.INPUT;
import org.eclipse.swt.internal.win32.KEYBDINPUT;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.MONITORINFO;
import org.eclipse.swt.internal.win32.MOUSEINPUT;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.NONCLIENTMETRICS;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.STARTUPINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.EventTable;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Synchronizer;
import org.eclipse.swt.widgets.TaskBar;
import org.eclipse.swt.widgets.TouchSource;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.widgets.Widget;

public class Display
extends Device {
    public MSG msg = new MSG();
    static String APP_NAME = "SWT";
    static String APP_VERSION = "";
    String appLocalDir;
    Event[] eventQueue;
    Callback windowCallback;
    long windowProc;
    int threadId;
    TCHAR windowClass;
    TCHAR windowShadowClass;
    TCHAR windowOwnDCClass;
    static int WindowClassCount;
    static final String WindowName = "SWT_Window";
    static final String WindowShadowName = "SWT_WindowShadow";
    static final String WindowOwnDCName = "SWT_WindowOwnDC";
    EventTable eventTable;
    EventTable filterTable;
    boolean useOwnDC;
    boolean externalEventLoop;
    int freeSlot;
    int[] indexTable;
    Control lastControl;
    Control lastGetControl;
    long lastHwnd;
    long lastGetHwnd;
    Control[] controlTable;
    static final int GROW_SIZE = 1024;
    static final int SWT_OBJECT_INDEX;
    static STARTUPINFO lpStartupInfo;
    long hButtonTheme;
    long hButtonThemeDark;
    long hEditTheme;
    long hExplorerBarTheme;
    long hScrollBarTheme;
    long hTabTheme;
    static final char[] EXPLORER;
    static final char[] TREEVIEW;
    static final boolean disableCustomThemeTweaks;
    static final String USE_DARKMODE_EXPLORER_THEME_KEY = "org.eclipse.swt.internal.win32.useDarkModeExplorerTheme";
    boolean useDarkModeExplorerTheme;
    static final String USE_SHELL_TITLE_COLORING = "org.eclipse.swt.internal.win32.useShellTitleColoring";
    boolean useShellTitleColoring;
    static final String MENUBAR_FOREGROUND_COLOR_KEY = "org.eclipse.swt.internal.win32.menuBarForegroundColor";
    int menuBarForegroundPixel = -1;
    static final String MENUBAR_BACKGROUND_COLOR_KEY = "org.eclipse.swt.internal.win32.menuBarBackgroundColor";
    int menuBarBackgroundPixel = -1;
    static final String MENUBAR_BORDER_COLOR_KEY = "org.eclipse.swt.internal.win32.menuBarBorderColor";
    long menuBarBorderPen;
    static final String USE_WS_BORDER_ALL_KEY = "org.eclipse.swt.internal.win32.all.use_WS_BORDER";
    boolean useWsBorderAll = false;
    static final String USE_WS_BORDER_CANVAS_KEY = "org.eclipse.swt.internal.win32.Canvas.use_WS_BORDER";
    boolean useWsBorderCanvas = false;
    static final String USE_WS_BORDER_LABEL_KEY = "org.eclipse.swt.internal.win32.Label.use_WS_BORDER";
    boolean useWsBorderLabel = false;
    static final String USE_WS_BORDER_LIST_KEY = "org.eclipse.swt.internal.win32.List.use_WS_BORDER";
    boolean useWsBorderList = false;
    static final String USE_WS_BORDER_TABLE_KEY = "org.eclipse.swt.internal.win32.Table.use_WS_BORDER";
    boolean useWsBorderTable = false;
    static final String USE_WS_BORDER_TEXT_KEY = "org.eclipse.swt.internal.win32.Text.use_WS_BORDER";
    boolean useWsBorderText = false;
    static final String TABLE_HEADER_LINE_COLOR_KEY = "org.eclipse.swt.internal.win32.Table.headerLineColor";
    int tableHeaderLinePixel = -1;
    static final String LABEL_DISABLED_FOREGROUND_COLOR_KEY = "org.eclipse.swt.internal.win32.Label.disabledForegroundColor";
    int disabledLabelForegroundPixel = -1;
    static final String COMBO_USE_DARK_THEME = "org.eclipse.swt.internal.win32.Combo.useDarkTheme";
    boolean comboUseDarkTheme = false;
    static final String PROGRESSBAR_USE_COLORS = "org.eclipse.swt.internal.win32.ProgressBar.useColors";
    boolean progressbarUseColors = false;
    static final String USE_DARKTHEME_TEXT_ICONS = "org.eclipse.swt.internal.win32.Text.useDarkThemeIcons";
    boolean textUseDarkthemeIcons = false;
    long hIconSearch;
    long hIconCancel;
    int focusEvent;
    Control focusControl;
    Menu[] bars;
    Menu[] popups;
    MenuItem[] items;
    static final int ID_START = 108;
    Callback msgFilterCallback;
    long msgFilterProc;
    long filterHook;
    MSG hookMsg = new MSG();
    boolean runDragDrop = true;
    boolean dragCancelled = false;
    Callback foregroundIdleCallback;
    long foregroundIdleProc;
    long idleHook;
    boolean ignoreNextKey;
    Callback getMsgCallback;
    Callback embeddedCallback;
    long getMsgProc;
    long msgHook;
    long embeddedHwnd;
    long embeddedProc;
    static final String AWT_WINDOW_CLASS = "SunAwtWindow";
    static final short[] ACCENTS;
    Synchronizer synchronizer;
    Consumer runtimeExceptionHandler = DefaultExceptionHandler.RUNTIME_EXCEPTION_HANDLER;
    Consumer errorHandler = DefaultExceptionHandler.RUNTIME_ERROR_HANDLER;
    boolean runMessagesInIdle = false;
    boolean runMessagesInMessageProc = true;
    static final String RUN_MESSAGES_IN_IDLE_KEY = "org.eclipse.swt.internal.win32.runMessagesInIdle";
    static final String RUN_MESSAGES_IN_MESSAGE_PROC_KEY = "org.eclipse.swt.internal.win32.runMessagesInMessageProc";
    static final String USE_OWNDC_KEY = "org.eclipse.swt.internal.win32.useOwnDC";
    static final String ACCEL_KEY_HIT = "org.eclipse.swt.internal.win32.accelKeyHit";
    static final String EXTERNAL_EVENT_LOOP_KEY = "org.eclipse.swt.internal.win32.externalEventLoop";
    static final String APPLOCAL_DIR_KEY = "org.eclipse.swt.internal.win32.appLocalDir";
    Thread thread;
    Runnable[] disposeList;
    Composite[] layoutDeferred;
    int layoutDeferredCount;
    Tray tray;
    int nextTrayId;
    TaskBar taskBar;
    static final String TASKBAR_EVENT = "/SWTINTERNAL_ID";
    static final String LAUNCHER_PREFIX = "--launcher.openFile ";
    long[] timerIds;
    Runnable[] timerList;
    long nextTimerId = 101L;
    static final long SETTINGS_ID = 100L;
    static final int SETTINGS_DELAY = 2000;
    RECT clickRect;
    int clickCount;
    int lastTime;
    int lastButton;
    long lastClickHwnd;
    Point scrollRemainderEvt = new Point(0, 0);
    Point scrollRemainderBar = new Point(0, 0);
    int lastKey;
    int lastMouse;
    int lastAscii;
    boolean lastVirtual;
    boolean lastNull;
    boolean lastDead;
    byte[] keyboard = new byte[256];
    boolean accelKeyHit;
    boolean mnemonicKeyHit;
    boolean lockActiveWindow;
    boolean captureChanged;
    boolean xMouse;
    double magStartDistance;
    double lastDistance;
    double rotationAngle;
    int lastX;
    int lastY;
    TouchSource[] touchSources;
    int nextToolTipId;
    boolean ignoreRestoreFocus;
    Control lastHittestControl;
    int lastHittest;
    Callback messageCallback;
    long hwndMessage;
    long messageProc;
    LOGFONT lfSystemFont;
    Font systemFont;
    Image errorImage;
    Image infoImage;
    Image questionImage;
    Image warningIcon;
    Cursor[] cursors = new Cursor[22];
    Resource[] resources;
    static final int RESOURCE_SIZE = 27;
    ImageList[] imageList;
    ImageList[] toolImageList;
    ImageList[] toolHotImageList;
    ImageList[] toolDisabledImageList;
    long lpCustColors;
    char[] tableBuffer;
    int resizeCount;
    static final int RESIZE_LIMIT = 4;
    Object data;
    String[] keys;
    Object[] values;
    static final int[][] KeyTable;
    static Display Default;
    static Display[] Displays;
    Monitor[] monitors = null;
    int monitorCount = 0;
    Shell[] modalShells;
    Dialog modalDialog;
    static boolean TrimEnabled;
    static final int SWT_GETACCELCOUNT = 32768;
    static final int SWT_GETACCEL = 32769;
    static final int SWT_KEYMSG = 32770;
    static final int SWT_DESTROY = 32771;
    static final int SWT_TRAYICONMSG = 32772;
    static final int SWT_NULL = 32773;
    static final int SWT_RUNASYNC = 32774;
    static int TASKBARCREATED;
    static int TASKBARBUTTONCREATED;
    static int SWT_RESTORECARET;
    static int DI_GETDRAGIMAGE;
    static int SWT_OPENDOC;
    Widget[] skinList = new Widget[1024];
    int skinCount;
    static final String PACKAGE_PREFIX = "org.eclipse.swt.widgets.";

    static void setDevice(Device device) {
        CurrentDevice = device;
    }

    public Display() {
        this(null);
    }

    public Display(DeviceData deviceData) {
        super(deviceData);
    }

    Control _getFocusControl() {
        return this.findControl(OS.GetFocus());
    }

    void addBar(Menu menu) {
        int n;
        if (this.bars == null) {
            this.bars = new Menu[4];
        }
        int n2 = this.bars.length;
        for (n = 0; n < n2; ++n) {
            if (this.bars[n] != menu) continue;
            return;
        }
        for (n = 0; n < n2 && this.bars[n] != null; ++n) {
        }
        if (n == n2) {
            Menu[] menuArray = new Menu[n2 + 4];
            System.arraycopy(this.bars, 0, menuArray, 0, n2);
            this.bars = menuArray;
        }
        this.bars[n] = menu;
    }

    void addControl(long l2, Control control) {
        int n;
        if (l2 == 0L) {
            return;
        }
        if (this.freeSlot == -1) {
            this.freeSlot = n = this.indexTable.length;
            int n2 = n + 1024;
            int[] nArray = new int[n2];
            Control[] controlArray = new Control[n2];
            System.arraycopy(this.indexTable, 0, nArray, 0, this.freeSlot);
            System.arraycopy(this.controlTable, 0, controlArray, 0, this.freeSlot);
            for (int i = this.freeSlot; i < n2 - 1; ++i) {
                nArray[i] = i + 1;
            }
            nArray[n2 - 1] = -1;
            this.indexTable = nArray;
            this.controlTable = controlArray;
        }
        OS.SetProp(l2, SWT_OBJECT_INDEX, this.freeSlot + 1);
        n = this.freeSlot;
        this.freeSlot = this.indexTable[n];
        this.indexTable[n] = -2;
        this.controlTable[n] = control;
    }

    void addSkinnableWidget(Widget widget) {
        if (this.skinCount >= this.skinList.length) {
            Widget[] widgetArray = new Widget[(this.skinList.length + 1) * 3 / 2];
            System.arraycopy(this.skinList, 0, widgetArray, 0, this.skinList.length);
            this.skinList = widgetArray;
        }
        this.skinList[this.skinCount++] = widget;
    }

    public void addFilter(int n, Listener listener) {
        this.checkDevice();
        if (listener == null) {
            this.error(4);
        }
        if (this.filterTable == null) {
            this.filterTable = new EventTable();
        }
        this.filterTable.hook(n, listener);
    }

    void addLayoutDeferred(Composite composite) {
        if (this.layoutDeferred == null) {
            this.layoutDeferred = new Composite[64];
        }
        if (this.layoutDeferredCount == this.layoutDeferred.length) {
            Composite[] compositeArray = new Composite[this.layoutDeferred.length + 64];
            System.arraycopy(this.layoutDeferred, 0, compositeArray, 0, this.layoutDeferred.length);
            this.layoutDeferred = compositeArray;
        }
        this.layoutDeferred[this.layoutDeferredCount++] = composite;
    }

    public void addListener(int n, Listener listener) {
        this.checkDevice();
        if (listener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            this.eventTable = new EventTable();
        }
        this.eventTable.hook(n, listener);
    }

    void addMenuItem(MenuItem menuItem) {
        if (this.items == null) {
            this.items = new MenuItem[64];
        }
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] != null) continue;
            menuItem.id = i + 108;
            this.items[i] = menuItem;
            return;
        }
        menuItem.id = this.items.length + 108;
        MenuItem[] menuItemArray = new MenuItem[this.items.length + 64];
        menuItemArray[this.items.length] = menuItem;
        System.arraycopy(this.items, 0, menuItemArray, 0, this.items.length);
        this.items = menuItemArray;
    }

    void addPopup(Menu menu) {
        int n;
        if (this.popups == null) {
            this.popups = new Menu[4];
        }
        int n2 = this.popups.length;
        for (n = 0; n < n2; ++n) {
            if (this.popups[n] != menu) continue;
            return;
        }
        for (n = 0; n < n2 && this.popups[n] != null; ++n) {
        }
        if (n == n2) {
            Menu[] menuArray = new Menu[n2 + 4];
            System.arraycopy(this.popups, 0, menuArray, 0, n2);
            this.popups = menuArray;
        }
        this.popups[n] = menu;
    }

    int asciiKey(int n) {
        for (int i = 0; i < this.keyboard.length; ++i) {
            this.keyboard[i] = 0;
        }
        if (!OS.GetKeyboardState(this.keyboard)) {
            return 0;
        }
        char[] cArray = new char[]{'\u0000'};
        int n2 = OS.ToUnicode(n, n, this.keyboard, cArray, 1, 0);
        while (n2 == -1) {
            n2 = OS.ToUnicode(n, n, this.keyboard, cArray, 1, 0);
        }
        return n2 != 0 ? cArray[0] : 0;
    }

    public void asyncExec(Runnable runnable) {
        Class<Device> clazz = Device.class;
        synchronized (Device.class) {
            if (this.isDisposed()) {
                this.error(45);
            }
            this.synchronizer.asyncExec(runnable);
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return;
        }
    }

    public void beep() {
        this.checkDevice();
        OS.MessageBeep(0);
    }

    protected void checkSubclass() {
        if (!Display.isValidClass(this.getClass())) {
            this.error(43);
        }
    }

    @Override
    protected void checkDevice() {
        if (this.thread == null) {
            this.error(24);
        }
        if (this.thread != Thread.currentThread()) {
            this.error(22);
        }
        if (this.isDisposed()) {
            this.error(45);
        }
    }

    static void checkDisplay(Thread thread, boolean bl) {
        Class<Device> clazz = Device.class;
        synchronized (Device.class) {
            for (Display display : Displays) {
                if (display == null) continue;
                if (!bl) {
                    SWT.error(20, null, " [multiple displays]");
                }
                if (display.thread != thread) continue;
                SWT.error(22);
            }
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return;
        }
    }

    void clearModal(Shell shell) {
        int n;
        if (this.modalShells == null) {
            return;
        }
        int n2 = this.modalShells.length;
        for (n = 0; n < n2 && this.modalShells[n] != shell; ++n) {
            if (this.modalShells[n] != null) continue;
            return;
        }
        if (n == n2) {
            return;
        }
        System.arraycopy(this.modalShells, n + 1, this.modalShells, n, --n2 - n);
        this.modalShells[n2] = null;
        if (n == 0 && this.modalShells[0] == null) {
            this.modalShells = null;
        }
        for (Shell shell2 : this.getShells()) {
            shell2.updateModal();
        }
    }

    int controlKey(int n) {
        int n2 = (int)OS.CharUpper((short)n);
        if (64 <= n2 && n2 <= 95) {
            return n2 & 0xBF;
        }
        return n;
    }

    public void close() {
        this.checkDevice();
        Event event = new Event();
        this.sendEvent(21, event);
        if (event.doit) {
            this.dispose();
        }
    }

    @Override
    protected void create(DeviceData deviceData) {
        this.checkSubclass();
        this.thread = Thread.currentThread();
        Display.checkDisplay(this.thread, true);
        this.createDisplay(deviceData);
        Display.register(this);
        if (Default == null) {
            Default = this;
        }
    }

    void createDisplay(DeviceData deviceData) {
    }

    static long create32bitDIB(Image image) {
        byte[] byArray;
        Object object;
        int n = -1;
        int n2 = -1;
        long l2 = 0L;
        long l3 = 0L;
        byte[] byArray2 = null;
        switch (image.type) {
            case 1: {
                object = new ICONINFO();
                OS.GetIconInfo(image.handle, (ICONINFO)object);
                l3 = ((ICONINFO)object).hbmColor;
                l2 = ((ICONINFO)object).hbmMask;
                break;
            }
            case 0: {
                object = image.getImageData(DPIUtil.getDeviceZoom());
                l3 = image.handle;
                n2 = ((ImageData)object).alpha;
                byArray2 = ((ImageData)object).alphaData;
                n = ((ImageData)object).transparentPixel;
                break;
            }
        }
        object = new BITMAP();
        OS.GetObject(l3, BITMAP.sizeof, (BITMAP)object);
        int n3 = ((BITMAP)object).bmWidth;
        int n4 = ((BITMAP)object).bmHeight;
        long l4 = OS.GetDC(0L);
        long l5 = OS.CreateCompatibleDC(l4);
        long l6 = OS.SelectObject(l5, l3);
        long l7 = OS.CreateCompatibleDC(l4);
        BITMAPINFOHEADER bITMAPINFOHEADER = new BITMAPINFOHEADER();
        bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
        bITMAPINFOHEADER.biWidth = n3;
        bITMAPINFOHEADER.biHeight = -n4;
        bITMAPINFOHEADER.biPlanes = 1;
        bITMAPINFOHEADER.biBitCount = (short)32;
        bITMAPINFOHEADER.biCompression = 0;
        byte[] byArray3 = new byte[BITMAPINFOHEADER.sizeof];
        OS.MoveMemory(byArray3, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
        long[] lArray = new long[]{0L};
        long l8 = OS.CreateDIBSection(0L, byArray3, 0, lArray, 0L, 0);
        if (l8 == 0L) {
            SWT.error(2);
        }
        long l9 = OS.SelectObject(l7, l8);
        BITMAP bITMAP = new BITMAP();
        OS.GetObject(l8, BITMAP.sizeof, bITMAP);
        int n5 = bITMAP.bmWidthBytes * bITMAP.bmHeight;
        OS.BitBlt(l7, 0, 0, n3, n4, l5, 0, 0, 0xCC0020);
        byte by = 0;
        byte by2 = 0;
        byte by3 = 0;
        if (n != -1) {
            if (((BITMAP)object).bmBitsPixel <= 8) {
                byArray = new byte[4];
                OS.GetDIBColorTable(l5, n, 1, byArray);
                by3 = byArray[0];
                by2 = byArray[1];
                by = byArray[2];
            } else {
                switch (((BITMAP)object).bmBitsPixel) {
                    case 16: {
                        by3 = (byte)((n & 0x1F) << 3);
                        by2 = (byte)((n & 0x3E0) >> 2);
                        by = (byte)((n & 0x7C00) >> 7);
                        break;
                    }
                    case 24: {
                        by3 = (byte)((n & 0xFF0000) >> 16);
                        by2 = (byte)((n & 0xFF00) >> 8);
                        by = (byte)(n & 0xFF);
                        break;
                    }
                    case 32: {
                        by3 = (byte)((n & 0xFF000000) >>> 24);
                        by2 = (byte)((n & 0xFF0000) >> 16);
                        by = (byte)((n & 0xFF00) >> 8);
                    }
                }
            }
        }
        byArray = new byte[n5];
        OS.MoveMemory(byArray, lArray[0], n5);
        if (l2 != 0L) {
            OS.SelectObject(l5, l2);
            int n6 = 0;
            for (int i = 0; i < n4; ++i) {
                for (int j = 0; j < n3; ++j) {
                    if (OS.GetPixel(l5, j, i) != 0) {
                        byte[] byArray4 = byArray;
                        int n7 = n6 + 0;
                        byte[] byArray5 = byArray;
                        int n8 = n6 + 1;
                        byte[] byArray6 = byArray;
                        int n9 = n6 + 2;
                        byte[] byArray7 = byArray;
                        int n10 = n6 + 3;
                        boolean bl = false;
                        byArray7[n10] = 0;
                        byArray6[n9] = 0;
                        byArray5[n8] = 0;
                        byArray4[n7] = 0;
                    } else {
                        byArray[n6 + 3] = -1;
                    }
                    n6 += 4;
                }
            }
        } else if (n != -1) {
            int n11 = 0;
            for (int i = 0; i < n4; ++i) {
                for (int j = 0; j < n3; ++j) {
                    if (byArray[n11] == by3 && byArray[n11 + 1] == by2 && byArray[n11 + 2] == by) {
                        byte[] byArray8 = byArray;
                        int n12 = n11 + 0;
                        byte[] byArray9 = byArray;
                        int n13 = n11 + 1;
                        byte[] byArray10 = byArray;
                        int n14 = n11 + 2;
                        byte[] byArray11 = byArray;
                        int n15 = n11 + 3;
                        boolean bl = false;
                        byArray11[n15] = 0;
                        byArray10[n14] = 0;
                        byArray9[n13] = 0;
                        byArray8[n12] = 0;
                    } else {
                        byArray[n11 + 3] = -1;
                    }
                    n11 += 4;
                }
            }
        } else if (n2 == -1 && byArray2 == null) {
            int n16 = 0;
            for (int i = 0; i < n4; ++i) {
                for (int j = 0; j < n3; ++j) {
                    byArray[n16 + 3] = -1;
                    n16 += 4;
                }
            }
        }
        OS.MoveMemory(lArray[0], byArray, n5);
        OS.SelectObject(l5, l6);
        OS.SelectObject(l7, l9);
        OS.DeleteObject(l5);
        OS.DeleteObject(l7);
        OS.ReleaseDC(0L, l4);
        if (l3 != image.handle && l3 != 0L) {
            OS.DeleteObject(l3);
        }
        if (l2 != 0L) {
            OS.DeleteObject(l2);
        }
        return l8;
    }

    static long create32bitDIB(long l2, int n, byte[] byArray, int n2) {
        byte[] byArray2;
        BITMAP bITMAP = new BITMAP();
        OS.GetObject(l2, BITMAP.sizeof, bITMAP);
        int n3 = bITMAP.bmWidth;
        int n4 = bITMAP.bmHeight;
        long l3 = OS.GetDC(0L);
        long l4 = OS.CreateCompatibleDC(l3);
        long l5 = OS.SelectObject(l4, l2);
        long l6 = OS.CreateCompatibleDC(l3);
        BITMAPINFOHEADER bITMAPINFOHEADER = new BITMAPINFOHEADER();
        bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
        bITMAPINFOHEADER.biWidth = n3;
        bITMAPINFOHEADER.biHeight = -n4;
        bITMAPINFOHEADER.biPlanes = 1;
        bITMAPINFOHEADER.biBitCount = (short)32;
        bITMAPINFOHEADER.biCompression = 0;
        byte[] byArray3 = new byte[BITMAPINFOHEADER.sizeof];
        OS.MoveMemory(byArray3, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
        long[] lArray = new long[]{0L};
        long l7 = OS.CreateDIBSection(0L, byArray3, 0, lArray, 0L, 0);
        if (l7 == 0L) {
            SWT.error(2);
        }
        long l8 = OS.SelectObject(l6, l7);
        BITMAP bITMAP2 = new BITMAP();
        OS.GetObject(l7, BITMAP.sizeof, bITMAP2);
        int n5 = bITMAP2.bmWidthBytes * bITMAP2.bmHeight;
        OS.BitBlt(l6, 0, 0, n3, n4, l4, 0, 0, 0xCC0020);
        byte by = 0;
        byte by2 = 0;
        byte by3 = 0;
        if (n2 != -1) {
            if (bITMAP.bmBitsPixel <= 8) {
                byArray2 = new byte[4];
                OS.GetDIBColorTable(l4, n2, 1, byArray2);
                by3 = byArray2[0];
                by2 = byArray2[1];
                by = byArray2[2];
            } else {
                switch (bITMAP.bmBitsPixel) {
                    case 16: {
                        by3 = (byte)((n2 & 0x1F) << 3);
                        by2 = (byte)((n2 & 0x3E0) >> 2);
                        by = (byte)((n2 & 0x7C00) >> 7);
                        break;
                    }
                    case 24: {
                        by3 = (byte)((n2 & 0xFF0000) >> 16);
                        by2 = (byte)((n2 & 0xFF00) >> 8);
                        by = (byte)(n2 & 0xFF);
                        break;
                    }
                    case 32: {
                        by3 = (byte)((n2 & 0xFF000000) >>> 24);
                        by2 = (byte)((n2 & 0xFF0000) >> 16);
                        by = (byte)((n2 & 0xFF00) >> 8);
                    }
                }
            }
        }
        OS.SelectObject(l4, l5);
        OS.SelectObject(l6, l8);
        OS.DeleteObject(l4);
        OS.DeleteObject(l6);
        OS.ReleaseDC(0L, l3);
        byArray2 = new byte[n5];
        OS.MoveMemory(byArray2, lArray[0], n5);
        if (n != -1) {
            int n6 = 0;
            for (int i = 0; i < n4; ++i) {
                for (int j = 0; j < n3; ++j) {
                    if (n != 0) {
                        byArray2[n6] = (byte)(((byArray2[n6] & 0xFF) * 255 + n / 2) / n);
                        byArray2[n6 + 1] = (byte)(((byArray2[n6 + 1] & 0xFF) * 255 + n / 2) / n);
                        byArray2[n6 + 2] = (byte)(((byArray2[n6 + 2] & 0xFF) * 255 + n / 2) / n);
                    }
                    byArray2[n6 + 3] = (byte)n;
                    n6 += 4;
                }
            }
        } else if (byArray != null) {
            int n7 = 0;
            int n8 = 0;
            for (int i = 0; i < n4; ++i) {
                for (int j = 0; j < n3; ++j) {
                    int n9;
                    if ((n9 = byArray[n8++] & 0xFF) != 0) {
                        byArray2[n7] = (byte)(((byArray2[n7] & 0xFF) * 255 + n9 / 2) / n9);
                        byArray2[n7 + 1] = (byte)(((byArray2[n7 + 1] & 0xFF) * 255 + n9 / 2) / n9);
                        byArray2[n7 + 2] = (byte)(((byArray2[n7 + 2] & 0xFF) * 255 + n9 / 2) / n9);
                    }
                    byArray2[n7 + 3] = (byte)n9;
                    n7 += 4;
                }
            }
        } else if (n2 != -1) {
            int n10 = 0;
            for (int i = 0; i < n4; ++i) {
                for (int j = 0; j < n3; ++j) {
                    byArray2[n10 + 3] = byArray2[n10] == by3 && byArray2[n10 + 1] == by2 && byArray2[n10 + 2] == by ? 0 : -1;
                    n10 += 4;
                }
            }
        }
        OS.MoveMemory(lArray[0], byArray2, n5);
        return l7;
    }

    static Image createIcon(Image image) {
        Device device = image.getDevice();
        ImageData imageData = image.getImageData(DPIUtil.getDeviceZoom());
        if (imageData.alpha == -1 && imageData.alphaData == null) {
            ImageData imageData2 = imageData.getTransparencyMask();
            return new Image(device, imageData, imageData2);
        }
        int n = imageData.width;
        int n2 = imageData.height;
        long l2 = device.internal_new_GC(null);
        long l3 = OS.CreateCompatibleDC(l2);
        long l4 = Display.create32bitDIB(image.handle, imageData.alpha, imageData.alphaData, imageData.transparentPixel);
        long l5 = OS.CreateBitmap(n, n2, 1, 1, null);
        long l6 = OS.SelectObject(l3, l5);
        OS.PatBlt(l3, 0, 0, n, n2, 66);
        OS.SelectObject(l3, l6);
        OS.DeleteDC(l3);
        device.internal_dispose_GC(l2, null);
        ICONINFO iCONINFO = new ICONINFO();
        iCONINFO.fIcon = true;
        iCONINFO.hbmColor = l4;
        iCONINFO.hbmMask = l5;
        long l7 = OS.CreateIconIndirect(iCONINFO);
        if (l7 == 0L) {
            SWT.error(2);
        }
        OS.DeleteObject(l4);
        OS.DeleteObject(l5);
        return Image.win32_new(device, 1, l7);
    }

    static void deregister(Display display) {
        Class<Device> clazz = Device.class;
        synchronized (Device.class) {
            for (int i = 0; i < Displays.length; ++i) {
                if (display != Displays[i]) continue;
                Display.Displays[i] = null;
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    @Override
    protected void destroy() {
        if (this == Default) {
            Default = null;
        }
        Display.deregister(this);
        this.destroyDisplay();
    }

    void destroyDisplay() {
    }

    public void disposeExec(Runnable runnable) {
        this.checkDevice();
        if (this.disposeList == null) {
            this.disposeList = new Runnable[4];
        }
        for (int i = 0; i < this.disposeList.length; ++i) {
            if (this.disposeList[i] != null) continue;
            this.disposeList[i] = runnable;
            return;
        }
        Runnable[] runnableArray = new Runnable[this.disposeList.length + 4];
        System.arraycopy(this.disposeList, 0, runnableArray, 0, this.disposeList.length);
        runnableArray[this.disposeList.length] = runnable;
        this.disposeList = runnableArray;
    }

    void drawMenuBars() {
        if (this.bars == null) {
            return;
        }
        for (Menu menu : this.bars) {
            if (menu == null || menu.isDisposed()) continue;
            menu.update();
        }
        this.bars = null;
    }

    long embeddedProc(long l2, long l3, long l4, long l5) {
        switch ((int)l3) {
            case 32770: {
                MSG mSG = new MSG();
                OS.MoveMemory(mSG, l5, MSG.sizeof);
                OS.TranslateMessage(mSG);
                OS.DispatchMessage(mSG);
                long l6 = OS.GetProcessHeap();
                OS.HeapFree(l6, 0, l5);
                break;
            }
            case 32771: {
                OS.DestroyWindow(l2);
                if (this.embeddedCallback != null) {
                    this.embeddedCallback.dispose();
                }
                if (this.getMsgCallback != null) {
                    this.getMsgCallback.dispose();
                }
                Object var9_6 = null;
                this.getMsgCallback = var9_6;
                this.embeddedCallback = var9_6;
                long l7 = 0L;
                this.getMsgProc = 0L;
                this.embeddedProc = 0L;
                break;
            }
        }
        return OS.DefWindowProc(l2, (int)l3, l4, l5);
    }

    void error(int n) {
        SWT.error(n);
    }

    boolean filters(int n) {
        return this.filterTable != null && this.filterTable.hooks(n);
    }

    Control findControl(long l2) {
        if (l2 == 0L) {
            return null;
        }
        long l3 = 0L;
        do {
            Control control;
            if ((control = this.getControl(l2)) != null) {
                return control;
            }
            l3 = OS.GetWindow(l2, 4);
        } while ((l2 = OS.GetParent(l2)) != 0L && l2 != l3);
        return null;
    }

    public Widget findWidget(long l2) {
        this.checkDevice();
        return this.getControl(l2);
    }

    public Widget findWidget(long l2, long l3) {
        this.checkDevice();
        Control control = this.getControl(l2);
        return control != null ? control.findItem(l3) : null;
    }

    public Widget findWidget(Widget widget, long l2) {
        this.checkDevice();
        if (widget instanceof Control) {
            return this.findWidget(((Control)widget).handle, l2);
        }
        return null;
    }

    long foregroundIdleProc(long l2, long l3, long l4) {
        if (l2 >= 0L && !this.synchronizer.isMessagesEmpty()) {
            this.sendPostExternalEventDispatchEvent();
            if (this.runMessagesInIdle) {
                if (this.runMessagesInMessageProc) {
                    OS.PostMessage(this.hwndMessage, 32774, 0L, 0L);
                } else {
                    this.runAsyncMessages(false);
                }
            }
            MSG mSG = new MSG();
            int n = 458754;
            if (!OS.PeekMessage(mSG, 0L, 0, 0, 458754)) {
                this.wakeThread();
            }
            this.sendPreExternalEventDispatchEvent();
        }
        return OS.CallNextHookEx(this.idleHook, (int)l2, l3, l4);
    }

    public static Display findDisplay(Thread thread) {
        Class<Device> clazz = Device.class;
        synchronized (Device.class) {
            for (Display display : Displays) {
                if (display == null || display.thread != thread) continue;
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return display;
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return null;
        }
    }

    TouchSource findTouchSource(long l2, Monitor monitor) {
        int n;
        if (this.touchSources == null) {
            this.touchSources = new TouchSource[4];
        }
        int n2 = this.touchSources.length;
        for (n = 0; n < n2; ++n) {
            if (this.touchSources[n] == null || this.touchSources[n].handle != l2) continue;
            return this.touchSources[n];
        }
        for (n = 0; n < n2 && this.touchSources[n] != null; ++n) {
        }
        if (n == n2) {
            TouchSource[] touchSourceArray = new TouchSource[n2 + 4];
            System.arraycopy(this.touchSources, 0, touchSourceArray, 0, n2);
            this.touchSources = touchSourceArray;
        }
        this.touchSources[n] = new TouchSource(l2, true, monitor.getBounds());
        return this.touchSources[n];
    }

    public Shell getActiveShell() {
        this.checkDevice();
        Control control = this.findControl(OS.GetActiveWindow());
        return control != null ? control.getShell() : null;
    }

    public Menu getMenuBar() {
        this.checkDevice();
        return null;
    }

    @Override
    public Rectangle getBounds() {
        this.checkDevice();
        return DPIUtil.autoScaleDown(this.getBoundsInPixels());
    }

    Rectangle getBoundsInPixels() {
        this.checkDevice();
        if (OS.GetSystemMetrics(80) < 2) {
            int n = OS.GetSystemMetrics(0);
            int n2 = OS.GetSystemMetrics(1);
            return new Rectangle(0, 0, n, n2);
        }
        int n = OS.GetSystemMetrics(76);
        int n3 = OS.GetSystemMetrics(77);
        int n4 = OS.GetSystemMetrics(78);
        int n5 = OS.GetSystemMetrics(79);
        return new Rectangle(n, n3, n4, n5);
    }

    public static Display getCurrent() {
        return Display.findDisplay(Thread.currentThread());
    }

    int getClickCount(int n, int n2, long l2, long l3) {
        switch (n) {
            case 3: {
                int n3 = OS.GetDoubleClickTime();
                if (this.clickRect == null) {
                    this.clickRect = new RECT();
                }
                int n4 = Math.abs(this.lastTime - this.getLastEventTime());
                POINT pOINT = new POINT();
                OS.POINTSTOPOINT(pOINT, l3);
                this.clickCount = this.lastClickHwnd == l2 && this.lastButton == n2 && n4 <= n3 && OS.PtInRect(this.clickRect, pOINT) ? ++this.clickCount : 1;
            }
            case 8: {
                this.lastButton = n2;
                this.lastClickHwnd = l2;
                this.lastTime = this.getLastEventTime();
                int n5 = OS.GetSystemMetrics(36) / 2;
                int n6 = OS.GetSystemMetrics(37) / 2;
                int n7 = OS.GET_X_LPARAM(l3);
                int n8 = OS.GET_Y_LPARAM(l3);
                OS.SetRect(this.clickRect, n7 - n5, n8 - n6, n7 + n5, n8 + n6);
            }
            case 4: {
                return this.clickCount;
            }
        }
        return 0;
    }

    @Override
    public Rectangle getClientArea() {
        this.checkDevice();
        return DPIUtil.autoScaleDown(this.getClientAreaInPixels());
    }

    Rectangle getClientAreaInPixels() {
        this.checkDevice();
        if (OS.GetSystemMetrics(80) < 2) {
            RECT rECT = new RECT();
            OS.SystemParametersInfo(48, 0, rECT, 0);
            int n = rECT.right - rECT.left;
            int n2 = rECT.bottom - rECT.top;
            return new Rectangle(rECT.left, rECT.top, n, n2);
        }
        int n = OS.GetSystemMetrics(76);
        int n3 = OS.GetSystemMetrics(77);
        int n4 = OS.GetSystemMetrics(78);
        int n5 = OS.GetSystemMetrics(79);
        return new Rectangle(n, n3, n4, n5);
    }

    Control getControl(long l2) {
        if (l2 == 0L) {
            return null;
        }
        if (this.lastControl != null && this.lastHwnd == l2) {
            return this.lastControl;
        }
        if (this.lastGetControl != null && this.lastGetHwnd == l2) {
            return this.lastGetControl;
        }
        int n = (int)OS.GetProp(l2, SWT_OBJECT_INDEX) - 1;
        if (0 <= n && n < this.controlTable.length) {
            this.lastGetHwnd = l2;
            this.lastGetControl = this.controlTable[n];
            return this.lastGetControl;
        }
        return null;
    }

    public Control getCursorControl() {
        this.checkDevice();
        POINT pOINT = new POINT();
        if (!OS.GetCursorPos(pOINT)) {
            return null;
        }
        return this.findControl(OS.WindowFromPoint(pOINT));
    }

    public Point getCursorLocation() {
        this.checkDevice();
        return DPIUtil.autoScaleDown(this.getCursorLocationInPixels());
    }

    Point getCursorLocationInPixels() {
        POINT pOINT = new POINT();
        OS.GetCursorPos(pOINT);
        return new Point(pOINT.x, pOINT.y);
    }

    public Point[] getCursorSizes() {
        this.checkDevice();
        return new Point[]{new Point(OS.GetSystemMetrics(13), OS.GetSystemMetrics(14))};
    }

    public static Display getDefault() {
        Class<Device> clazz = Device.class;
        synchronized (Device.class) {
            if (Default == null) {
                Default = new Display();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return Default;
        }
    }

    @Override
    protected int getDeviceZoom() {
        if (OS.WIN32_VERSION >= OS.VERSION(6, 3)) {
            return this.getPrimaryMonitor().getZoom();
        }
        return super.getDeviceZoom();
    }

    static boolean isValidClass(Class clazz) {
        String string = clazz.getName();
        int n = string.lastIndexOf(46);
        return string.substring(0, n + 1).equals(PACKAGE_PREFIX);
    }

    public Object getData(String string) {
        this.checkDevice();
        if (string == null) {
            this.error(4);
        }
        if (string.equals(RUN_MESSAGES_IN_IDLE_KEY)) {
            return this.runMessagesInIdle;
        }
        if (string.equals(RUN_MESSAGES_IN_MESSAGE_PROC_KEY)) {
            return this.runMessagesInMessageProc;
        }
        if (string.equals(USE_OWNDC_KEY)) {
            return this.useOwnDC;
        }
        if (string.equals(ACCEL_KEY_HIT)) {
            return this.accelKeyHit;
        }
        if (string.equals(APPLOCAL_DIR_KEY)) {
            return this.appLocalDir;
        }
        if (this.keys == null) {
            return null;
        }
        for (int i = 0; i < this.keys.length; ++i) {
            if (!this.keys[i].equals(string)) continue;
            return this.values[i];
        }
        return null;
    }

    public Object getData() {
        this.checkDevice();
        return this.data;
    }

    public int getDismissalAlignment() {
        this.checkDevice();
        return 16384;
    }

    public int getDoubleClickTime() {
        this.checkDevice();
        return OS.GetDoubleClickTime();
    }

    public Control getFocusControl() {
        this.checkDevice();
        if (this.focusControl != null && !this.focusControl.isDisposed()) {
            return this.focusControl;
        }
        return this._getFocusControl();
    }

    String getFontName(LOGFONT lOGFONT) {
        int n;
        char[] cArray = lOGFONT.lfFaceName;
        for (n = 0; n < cArray.length && cArray[n] != '\u0000'; ++n) {
        }
        return new String(cArray, 0, n);
    }

    public boolean getHighContrast() {
        this.checkDevice();
        HIGHCONTRAST hIGHCONTRAST = new HIGHCONTRAST();
        hIGHCONTRAST.cbSize = HIGHCONTRAST.sizeof;
        OS.SystemParametersInfo(66, 0, hIGHCONTRAST, 0);
        return (hIGHCONTRAST.dwFlags & 1) != 0;
    }

    public int getIconDepth() {
        TCHAR tCHAR;
        this.checkDevice();
        if (this.getDepth() >= 24) {
            return 32;
        }
        TCHAR tCHAR2 = new TCHAR(0, "Control Panel\\Desktop\\WindowMetrics", true);
        long[] lArray = new long[]{0L};
        int n = OS.RegOpenKeyEx(-2147483647L, tCHAR2, 0, 131097, lArray);
        if (n != 0) {
            return 4;
        }
        int n2 = 4;
        TCHAR tCHAR3 = new TCHAR(0, "Shell Icon BPP", true);
        int[] nArray = new int[]{0};
        n = OS.RegQueryValueEx(lArray[0], tCHAR3, 0L, null, (TCHAR)null, nArray);
        if (n == 0 && (n = OS.RegQueryValueEx(lArray[0], tCHAR3, 0L, null, tCHAR = new TCHAR(0, nArray[0] / 2), nArray)) == 0) {
            try {
                n2 = Integer.parseInt(tCHAR.toString(0, tCHAR.strlen()));
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        OS.RegCloseKey(lArray[0]);
        return n2;
    }

    public Point[] getIconSizes() {
        this.checkDevice();
        return new Point[]{new Point(OS.GetSystemMetrics(49), OS.GetSystemMetrics(50)), new Point(OS.GetSystemMetrics(11), OS.GetSystemMetrics(12))};
    }

    ImageList getImageList(int n, int n2, int n3) {
        Object object;
        int n4;
        if (this.imageList == null) {
            this.imageList = new ImageList[4];
        }
        int n5 = this.imageList.length;
        for (n4 = 0; n4 < n5 && (object = this.imageList[n4]) != null; ++n4) {
            Point point = ((ImageList)object).getImageSize();
            if (point.x != n2 || point.y != n3 || ((ImageList)object).getStyle() != n) continue;
            ((ImageList)object).addRef();
            return object;
        }
        if (n4 == n5) {
            object = new ImageList[n5 + 4];
            System.arraycopy(this.imageList, 0, object, 0, n5);
            this.imageList = object;
        }
        this.imageList[n4] = object = new ImageList(n, n2, n3);
        ((ImageList)this.imageList[n4]).addRef();
        return object;
    }

    ImageList getImageListToolBar(int n, int n2, int n3) {
        Object object;
        int n4;
        if (this.toolImageList == null) {
            this.toolImageList = new ImageList[4];
        }
        int n5 = this.toolImageList.length;
        for (n4 = 0; n4 < n5 && (object = this.toolImageList[n4]) != null; ++n4) {
            Point point = ((ImageList)object).getImageSize();
            if (point.x != n2 || point.y != n3 || ((ImageList)object).getStyle() != n) continue;
            ((ImageList)object).addRef();
            return object;
        }
        if (n4 == n5) {
            object = new ImageList[n5 + 4];
            System.arraycopy(this.toolImageList, 0, object, 0, n5);
            this.toolImageList = object;
        }
        this.toolImageList[n4] = object = new ImageList(n, n2, n3);
        ((ImageList)this.toolImageList[n4]).addRef();
        return object;
    }

    ImageList getImageListToolBarDisabled(int n, int n2, int n3) {
        Object object;
        int n4;
        if (this.toolDisabledImageList == null) {
            this.toolDisabledImageList = new ImageList[4];
        }
        int n5 = this.toolDisabledImageList.length;
        for (n4 = 0; n4 < n5 && (object = this.toolDisabledImageList[n4]) != null; ++n4) {
            Point point = ((ImageList)object).getImageSize();
            if (point.x != n2 || point.y != n3 || ((ImageList)object).getStyle() != n) continue;
            ((ImageList)object).addRef();
            return object;
        }
        if (n4 == n5) {
            object = new ImageList[n5 + 4];
            System.arraycopy(this.toolDisabledImageList, 0, object, 0, n5);
            this.toolDisabledImageList = object;
        }
        this.toolDisabledImageList[n4] = object = new ImageList(n, n2, n3);
        ((ImageList)this.toolDisabledImageList[n4]).addRef();
        return object;
    }

    ImageList getImageListToolBarHot(int n, int n2, int n3) {
        Object object;
        int n4;
        if (this.toolHotImageList == null) {
            this.toolHotImageList = new ImageList[4];
        }
        int n5 = this.toolHotImageList.length;
        for (n4 = 0; n4 < n5 && (object = this.toolHotImageList[n4]) != null; ++n4) {
            Point point = ((ImageList)object).getImageSize();
            if (point.x != n2 || point.y != n3 || ((ImageList)object).getStyle() != n) continue;
            ((ImageList)object).addRef();
            return object;
        }
        if (n4 == n5) {
            object = new ImageList[n5 + 4];
            System.arraycopy(this.toolHotImageList, 0, object, 0, n5);
            this.toolHotImageList = object;
        }
        this.toolHotImageList[n4] = object = new ImageList(n, n2, n3);
        ((ImageList)this.toolHotImageList[n4]).addRef();
        return object;
    }

    public static boolean isSystemDarkTheme() {
        int[] nArray;
        boolean bl = false;
        if (OS.WIN32_BUILD >= 17763 && (nArray = OS.readRegistryDwords(-2147483647, "Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize", "AppsUseLightTheme")) != null) {
            bl = nArray[0] == 0;
        }
        return bl;
    }

    int getLastEventTime() {
        return OS.GetMessageTime();
    }

    MenuItem getMenuItem(int n) {
        if (this.items == null) {
            return null;
        }
        if (0 <= (n -= 108) && n < this.items.length) {
            return this.items[n];
        }
        return null;
    }

    Shell getModalShell() {
        if (this.modalShells == null) {
            return null;
        }
        int n = this.modalShells.length;
        while (--n >= 0) {
            Shell shell = this.modalShells[n];
            if (shell == null) continue;
            return shell;
        }
        return null;
    }

    Dialog getModalDialog() {
        return this.modalDialog;
    }

    Monitor getMonitor(long l2) {
        MONITORINFO mONITORINFO = new MONITORINFO();
        mONITORINFO.cbSize = MONITORINFO.sizeof;
        OS.GetMonitorInfo(l2, mONITORINFO);
        Monitor monitor = new Monitor();
        monitor.handle = l2;
        Rectangle rectangle = new Rectangle(mONITORINFO.rcMonitor_left, mONITORINFO.rcMonitor_top, mONITORINFO.rcMonitor_right - mONITORINFO.rcMonitor_left, mONITORINFO.rcMonitor_bottom - mONITORINFO.rcMonitor_top);
        monitor.setBounds(DPIUtil.autoScaleDown(rectangle));
        Rectangle rectangle2 = new Rectangle(mONITORINFO.rcWork_left, mONITORINFO.rcWork_top, mONITORINFO.rcWork_right - mONITORINFO.rcWork_left, mONITORINFO.rcWork_bottom - mONITORINFO.rcWork_top);
        monitor.setClientArea(DPIUtil.autoScaleDown(rectangle2));
        if (OS.WIN32_VERSION >= OS.VERSION(6, 3)) {
            int[] nArray = new int[]{0};
            int[] nArray2 = new int[]{0};
            int n = OS.GetDpiForMonitor(monitor.handle, 0, nArray, nArray2);
            monitor.zoom = n = n == 0 ? DPIUtil.mapDPIToZoom(nArray[0]) : 100;
        } else {
            monitor.zoom = this.getDeviceZoom();
        }
        return monitor;
    }

    public Monitor[] getMonitors() {
        this.checkDevice();
        this.monitors = new Monitor[4];
        Callback callback = new Callback(this, "monitorEnumProc", 4);
        OS.EnumDisplayMonitors(0L, null, callback.getAddress(), 0);
        callback.dispose();
        Monitor[] monitorArray = new Monitor[this.monitorCount];
        System.arraycopy(this.monitors, 0, monitorArray, 0, this.monitorCount);
        this.monitors = null;
        this.monitorCount = 0;
        return monitorArray;
    }

    long getMsgProc(long l2, long l3, long l4) {
        if (this.embeddedHwnd == 0L) {
            long l5 = OS.GetModuleHandle(null);
            this.embeddedHwnd = OS.CreateWindowEx(0, this.windowClass, null, 0, 0, 0, 0, 0, 0L, 0L, l5, null);
            this.embeddedCallback = new Callback(this, "embeddedProc", 4);
            this.embeddedProc = this.embeddedCallback.getAddress();
            OS.SetWindowLongPtr(this.embeddedHwnd, -4, this.embeddedProc);
        }
        if (l2 >= 0L && (l3 & 1L) != 0L) {
            MSG mSG = new MSG();
            OS.MoveMemory(mSG, l4, MSG.sizeof);
            block0 : switch (mSG.message) {
                case 256: 
                case 257: 
                case 260: 
                case 261: {
                    Control control = this.findControl(mSG.hwnd);
                    if (control == null) break;
                    long l6 = OS.GetProcessHeap();
                    long l7 = OS.HeapAlloc(l6, 8, MSG.sizeof);
                    OS.MoveMemory(l7, mSG, MSG.sizeof);
                    OS.PostMessage(this.hwndMessage, 32770, l3, l7);
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
                    mSG.message = 0;
                    OS.MoveMemory(l4, mSG, MSG.sizeof);
                    break;
                }
            }
        }
        return OS.CallNextHookEx(this.msgHook, (int)l2, l3, l4);
    }

    public Monitor getPrimaryMonitor() {
        this.checkDevice();
        long l2 = OS.MonitorFromWindow(0L, 1);
        return this.getMonitor(l2);
    }

    public Shell[] getShells() {
        this.checkDevice();
        int n = 0;
        Shell[] shellArray = new Shell[16];
        for (Control control : this.controlTable) {
            int n2;
            if (!(control instanceof Shell)) continue;
            for (n2 = 0; n2 < n && shellArray[n2] != control; ++n2) {
            }
            if (n2 != n) continue;
            if (n == shellArray.length) {
                Shell[] shellArray2 = new Shell[n + 16];
                System.arraycopy(shellArray, 0, shellArray2, 0, n);
                shellArray = shellArray2;
            }
            shellArray[n++] = (Shell)control;
        }
        if (n == shellArray.length) {
            return shellArray;
        }
        Control[] controlArray = new Shell[n];
        System.arraycopy(shellArray, 0, controlArray, 0, n);
        return controlArray;
    }

    public Synchronizer getSynchronizer() {
        this.checkDevice();
        return this.synchronizer;
    }

    public Thread getSyncThread() {
        Class<Device> clazz = Device.class;
        synchronized (Device.class) {
            if (this.isDisposed()) {
                this.error(45);
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return this.synchronizer.syncThread;
        }
    }

    @Override
    public Color getSystemColor(int n) {
        this.checkDevice();
        int n2 = 0;
        switch (n) {
            case 17: {
                n2 = OS.GetSysColor(21);
                break;
            }
            case 39: {
                n2 = OS.GetSysColor(17);
                break;
            }
            case 18: {
                n2 = OS.GetSysColor(16);
                break;
            }
            case 19: {
                n2 = OS.GetSysColor(22);
                break;
            }
            case 20: {
                n2 = OS.GetSysColor(20);
                break;
            }
            case 22: 
            case 38: {
                n2 = OS.GetSysColor(15);
                break;
            }
            case 23: {
                n2 = OS.GetSysColor(6);
                break;
            }
            case 21: 
            case 24: {
                n2 = OS.GetSysColor(8);
                break;
            }
            case 25: {
                n2 = OS.GetSysColor(5);
                break;
            }
            case 26: {
                n2 = OS.GetSysColor(13);
                break;
            }
            case 27: {
                n2 = OS.GetSysColor(14);
                break;
            }
            case 36: {
                n2 = OS.GetSysColor(26);
                break;
            }
            case 28: {
                n2 = OS.GetSysColor(23);
                break;
            }
            case 29: {
                n2 = OS.GetSysColor(24);
                break;
            }
            case 30: {
                n2 = OS.GetSysColor(9);
                break;
            }
            case 31: {
                n2 = OS.GetSysColor(2);
                break;
            }
            case 32: {
                n2 = OS.GetSysColor(27);
                if (n2 != 0) break;
                n2 = OS.GetSysColor(2);
                break;
            }
            case 33: {
                n2 = OS.GetSysColor(19);
                break;
            }
            case 34: {
                n2 = OS.GetSysColor(3);
                break;
            }
            case 35: {
                n2 = OS.GetSysColor(28);
                if (n2 != 0) break;
                n2 = OS.GetSysColor(3);
                break;
            }
            default: {
                return super.getSystemColor(n);
            }
        }
        return Color.win32_new(this, n2);
    }

    public Cursor getSystemCursor(int n) {
        this.checkDevice();
        if (0 > n || n >= this.cursors.length) {
            return null;
        }
        if (this.cursors[n] == null) {
            this.cursors[n] = new Cursor(this, n);
        }
        return this.cursors[n];
    }

    @Override
    public Font getSystemFont() {
        this.checkDevice();
        if (this.systemFont != null) {
            return this.systemFont;
        }
        long l2 = 0L;
        NONCLIENTMETRICS nONCLIENTMETRICS = new NONCLIENTMETRICS();
        nONCLIENTMETRICS.cbSize = NONCLIENTMETRICS.sizeof;
        if (OS.SystemParametersInfo(41, 0, nONCLIENTMETRICS, 0)) {
            LOGFONT lOGFONT = nONCLIENTMETRICS.lfMessageFont;
            l2 = OS.CreateFontIndirect(lOGFONT);
            LOGFONT lOGFONT2 = this.lfSystemFont = l2 != 0L ? lOGFONT : null;
        }
        if (l2 == 0L) {
            l2 = OS.GetStockObject(17);
        }
        if (l2 == 0L) {
            l2 = OS.GetStockObject(13);
        }
        this.systemFont = Font.win32_new(this, l2);
        return this.systemFont;
    }

    public Image getSystemImage(int n) {
        this.checkDevice();
        switch (n) {
            case 1: {
                if (this.errorImage != null) {
                    return this.errorImage;
                }
                long l2 = OS.LoadImage(0L, 32513L, 1, 0, 0, 32768);
                this.errorImage = Image.win32_new(this, 1, l2);
                return this.errorImage;
            }
            case 2: 
            case 16: {
                if (this.infoImage != null) {
                    return this.infoImage;
                }
                long l3 = OS.LoadImage(0L, 32516L, 1, 0, 0, 32768);
                this.infoImage = Image.win32_new(this, 1, l3);
                return this.infoImage;
            }
            case 4: {
                if (this.questionImage != null) {
                    return this.questionImage;
                }
                long l4 = OS.LoadImage(0L, 32514L, 1, 0, 0, 32768);
                this.questionImage = Image.win32_new(this, 1, l4);
                return this.questionImage;
            }
            case 8: {
                if (this.warningIcon != null) {
                    return this.warningIcon;
                }
                long l5 = OS.LoadImage(0L, 32515L, 1, 0, 0, 32768);
                this.warningIcon = Image.win32_new(this, 1, l5);
                return this.warningIcon;
            }
        }
        return null;
    }

    public Menu getSystemMenu() {
        this.checkDevice();
        return null;
    }

    public TaskBar getSystemTaskBar() {
        this.checkDevice();
        if (this.taskBar != null) {
            return this.taskBar;
        }
        if (OS.WIN32_VERSION >= OS.VERSION(6, 1)) {
            try {
                this.taskBar = new TaskBar(this, 0);
            }
            catch (SWTError sWTError) {
                if (sWTError.code == 20) {
                    return null;
                }
                throw sWTError;
            }
        }
        return this.taskBar;
    }

    public Tray getSystemTray() {
        this.checkDevice();
        if (this.tray == null) {
            this.tray = new Tray(this, 0);
        }
        return this.tray;
    }

    public Thread getThread() {
        Class<Device> clazz = Device.class;
        synchronized (Device.class) {
            if (this.isDisposed()) {
                this.error(45);
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return this.thread;
        }
    }

    public boolean getTouchEnabled() {
        this.checkDevice();
        int n = OS.GetSystemMetrics(94);
        return (n & 0xC0) == 192;
    }

    long hButtonTheme() {
        if (this.hButtonTheme != 0L) {
            return this.hButtonTheme;
        }
        char[] cArray = "BUTTON\u0000".toCharArray();
        this.hButtonTheme = OS.OpenThemeData(this.hwndMessage, cArray);
        return this.hButtonTheme;
    }

    long hButtonThemeDark() {
        if (this.hButtonThemeDark != 0L) {
            return this.hButtonThemeDark;
        }
        char[] cArray = "Darkmode_Explorer::BUTTON\u0000".toCharArray();
        this.hButtonThemeDark = OS.OpenThemeData(this.hwndMessage, cArray);
        return this.hButtonThemeDark;
    }

    long hButtonThemeAuto() {
        if (this.useDarkModeExplorerTheme) {
            return this.hButtonThemeDark();
        }
        return this.hButtonTheme();
    }

    long hEditTheme() {
        if (this.hEditTheme != 0L) {
            return this.hEditTheme;
        }
        char[] cArray = "EDIT\u0000".toCharArray();
        this.hEditTheme = OS.OpenThemeData(this.hwndMessage, cArray);
        return this.hEditTheme;
    }

    long hExplorerBarTheme() {
        if (this.hExplorerBarTheme != 0L) {
            return this.hExplorerBarTheme;
        }
        char[] cArray = "EXPLORERBAR\u0000".toCharArray();
        this.hExplorerBarTheme = OS.OpenThemeData(this.hwndMessage, cArray);
        return this.hExplorerBarTheme;
    }

    long hScrollBarTheme() {
        if (this.hScrollBarTheme != 0L) {
            return this.hScrollBarTheme;
        }
        char[] cArray = "SCROLLBAR\u0000".toCharArray();
        this.hScrollBarTheme = OS.OpenThemeData(this.hwndMessage, cArray);
        return this.hScrollBarTheme;
    }

    long hTabTheme() {
        if (this.hTabTheme != 0L) {
            return this.hTabTheme;
        }
        char[] cArray = "TAB\u0000".toCharArray();
        this.hTabTheme = OS.OpenThemeData(this.hwndMessage, cArray);
        return this.hTabTheme;
    }

    void resetThemes() {
        if (this.hButtonTheme != 0L) {
            OS.CloseThemeData(this.hButtonTheme);
            this.hButtonTheme = 0L;
        }
        if (this.hButtonThemeDark != 0L) {
            OS.CloseThemeData(this.hButtonThemeDark);
            this.hButtonThemeDark = 0L;
        }
        if (this.hEditTheme != 0L) {
            OS.CloseThemeData(this.hEditTheme);
            this.hEditTheme = 0L;
        }
        if (this.hExplorerBarTheme != 0L) {
            OS.CloseThemeData(this.hExplorerBarTheme);
            this.hExplorerBarTheme = 0L;
        }
        if (this.hScrollBarTheme != 0L) {
            OS.CloseThemeData(this.hScrollBarTheme);
            this.hScrollBarTheme = 0L;
        }
        if (this.hTabTheme != 0L) {
            OS.CloseThemeData(this.hTabTheme);
            this.hTabTheme = 0L;
        }
    }

    @Override
    public long internal_new_GC(GCData gCData) {
        long l2;
        if (this.isDisposed()) {
            this.error(45);
        }
        if ((l2 = OS.GetDC(0L)) == 0L) {
            this.error(2);
        }
        if (gCData != null) {
            int n = 0x6000000;
            if ((gCData.style & 0x6000000) != 0) {
                gCData.layout = (gCData.style & 0x4000000) != 0 ? 1 : 0;
            } else {
                gCData.style |= 0x2000000;
            }
            gCData.device = this;
            gCData.font = this.getSystemFont();
        }
        return l2;
    }

    @Override
    protected void init() {
        long[] lArray;
        int n;
        this.synchronizer = new Synchronizer(this);
        super.init();
        DPIUtil.setDeviceZoom(this.getDeviceZoom());
        char[] cArray = null;
        if (APP_NAME != null && !"SWT".equalsIgnoreCase(APP_NAME) && OS.WIN32_VERSION >= OS.VERSION(6, 1)) {
            int n2 = APP_NAME.length();
            cArray = new char[n2 + 1];
            APP_NAME.getChars(0, n2, cArray, 0);
            long[] lArray2 = new long[]{0L};
            if (OS.GetCurrentProcessExplicitAppUserModelID(lArray2) != 0) {
                OS.SetCurrentProcessExplicitAppUserModelID(cArray);
            }
            if (lArray2[0] != 0L) {
                OS.CoTaskMemFree(lArray2[0]);
            }
        }
        this.windowCallback = new Callback(this, "windowProc", 4);
        this.windowProc = this.windowCallback.getAddress();
        this.threadId = OS.GetCurrentThreadId();
        this.windowClass = new TCHAR(0, WindowName + WindowClassCount, true);
        this.windowShadowClass = new TCHAR(0, WindowShadowName + WindowClassCount, true);
        this.windowOwnDCClass = new TCHAR(0, WindowOwnDCName + WindowClassCount, true);
        ++WindowClassCount;
        long l2 = OS.GetModuleHandle(null);
        WNDCLASS wNDCLASS = new WNDCLASS();
        wNDCLASS.hInstance = l2;
        wNDCLASS.lpfnWndProc = this.windowProc;
        wNDCLASS.style = 8;
        wNDCLASS.hCursor = OS.LoadCursor(0L, 32512L);
        wNDCLASS.hIcon = OS.LoadIcon(0L, 32512L);
        OS.RegisterClass(this.windowClass, wNDCLASS);
        WNDCLASS wNDCLASS2 = wNDCLASS;
        wNDCLASS2.style |= 0x20000;
        OS.RegisterClass(this.windowShadowClass, wNDCLASS);
        WNDCLASS wNDCLASS3 = wNDCLASS;
        wNDCLASS3.style |= 0x20;
        OS.RegisterClass(this.windowOwnDCClass, wNDCLASS);
        this.hwndMessage = OS.CreateWindowEx(0, this.windowClass, null, 0, 0, 0, 0, 0, 0L, 0L, l2, null);
        String string = "SWT_Window_" + APP_NAME;
        OS.SetWindowText(this.hwndMessage, new TCHAR(0, string, true));
        this.messageCallback = new Callback(this, "messageProc", 4);
        this.messageProc = this.messageCallback.getAddress();
        OS.SetWindowLongPtr(this.hwndMessage, -4, this.messageProc);
        this.msgFilterCallback = new Callback(this, "msgFilterProc", 3);
        this.msgFilterProc = this.msgFilterCallback.getAddress();
        this.filterHook = OS.SetWindowsHookEx(-1, this.msgFilterProc, 0L, this.threadId);
        this.foregroundIdleCallback = new Callback(this, "foregroundIdleProc", 3);
        this.foregroundIdleProc = this.foregroundIdleCallback.getAddress();
        this.idleHook = OS.SetWindowsHookEx(11, this.foregroundIdleProc, 0L, this.threadId);
        TASKBARCREATED = OS.RegisterWindowMessage(new TCHAR(0, "TaskbarCreated", true));
        TASKBARBUTTONCREATED = OS.RegisterWindowMessage(new TCHAR(0, "TaskbarButtonCreated", true));
        SWT_RESTORECARET = OS.RegisterWindowMessage(new TCHAR(0, "SWT_RESTORECARET", true));
        DI_GETDRAGIMAGE = OS.RegisterWindowMessage(new TCHAR(0, "ShellGetDragImage", true));
        SWT_OPENDOC = OS.RegisterWindowMessage(new TCHAR(0, "SWT_OPENDOC", true));
        OS.OleInitialize(0L);
        if (cArray != null && (n = COM.CoCreateInstance(COM.CLSID_DestinationList, 0L, 1, COM.IID_ICustomDestinationList, lArray = new long[]{0L})) == 0) {
            ICustomDestinationList iCustomDestinationList = new ICustomDestinationList(lArray[0]);
            iCustomDestinationList.DeleteList(cArray);
            iCustomDestinationList.Release();
        }
        this.appLocalDir = System.getenv("LOCALAPPDATA") + "\\" + APP_NAME.replaceAll("[\\\\/:*?\"<>|]", "_");
        OS.BufferedPaintInit();
        this.indexTable = new int[1024];
        this.controlTable = new Control[1024];
        for (int i = 0; i < 1023; ++i) {
            this.indexTable[i] = i + 1;
        }
        this.indexTable[1023] = -1;
    }

    @Override
    public void internal_dispose_GC(long l2, GCData gCData) {
        OS.ReleaseDC(0L, l2);
    }

    boolean isValidThread() {
        return this.thread == Thread.currentThread();
    }

    public Point map(Control control, Control control2, Point point) {
        this.checkDevice();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        return DPIUtil.autoScaleDown(this.mapInPixels(control, control2, point));
    }

    Point mapInPixels(Control control, Control control2, Point point) {
        return this.mapInPixels(control, control2, point.x, point.y);
    }

    public Point map(Control control, Control control2, int n, int n2) {
        this.checkDevice();
        n = DPIUtil.autoScaleUp(n);
        n2 = DPIUtil.autoScaleUp(n2);
        return DPIUtil.autoScaleDown(this.mapInPixels(control, control2, n, n2));
    }

    Point mapInPixels(Control control, Control control2, int n, int n2) {
        if (control != null && control.isDisposed()) {
            this.error(5);
        }
        if (control2 != null && control2.isDisposed()) {
            this.error(5);
        }
        if (control == control2) {
            return new Point(n, n2);
        }
        long l2 = control != null ? control.handle : 0L;
        long l3 = control2 != null ? control2.handle : 0L;
        POINT pOINT = new POINT();
        pOINT.x = n;
        pOINT.y = n2;
        OS.MapWindowPoints(l2, l3, pOINT, 1);
        return new Point(pOINT.x, pOINT.y);
    }

    public Rectangle map(Control control, Control control2, Rectangle rectangle) {
        this.checkDevice();
        if (rectangle == null) {
            this.error(4);
        }
        rectangle = DPIUtil.autoScaleUp(rectangle);
        return DPIUtil.autoScaleDown(this.mapInPixels(control, control2, rectangle));
    }

    Rectangle mapInPixels(Control control, Control control2, Rectangle rectangle) {
        return this.mapInPixels(control, control2, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public Rectangle map(Control control, Control control2, int n, int n2, int n3, int n4) {
        this.checkDevice();
        n = DPIUtil.autoScaleUp(n);
        n2 = DPIUtil.autoScaleUp(n2);
        n3 = DPIUtil.autoScaleUp(n3);
        n4 = DPIUtil.autoScaleUp(n4);
        return DPIUtil.autoScaleDown(this.mapInPixels(control, control2, n, n2, n3, n4));
    }

    Rectangle mapInPixels(Control control, Control control2, int n, int n2, int n3, int n4) {
        if (control != null && control.isDisposed()) {
            this.error(5);
        }
        if (control2 != null && control2.isDisposed()) {
            this.error(5);
        }
        if (control == control2) {
            return new Rectangle(n, n2, n3, n4);
        }
        long l2 = control != null ? control.handle : 0L;
        long l3 = control2 != null ? control2.handle : 0L;
        RECT rECT = new RECT();
        rECT.left = n;
        rECT.top = n2;
        rECT.right = n + n3;
        rECT.bottom = n2 + n4;
        OS.MapWindowPoints(l2, l3, rECT, 2);
        return new Rectangle(rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top);
    }

    long messageProc(long l2, long l3, long l4, long l5) {
        switch ((int)l3) {
            case 32774: {
                if (!this.runMessagesInIdle) break;
                this.runAsyncMessages(false);
                break;
            }
            case 32770: {
                boolean bl = false;
                MSG mSG = new MSG();
                OS.MoveMemory(mSG, l5, MSG.sizeof);
                Control control = this.findControl(mSG.hwnd);
                if (control != null) {
                    int n;
                    boolean bl2 = false;
                    block12 : switch (mSG.message) {
                        case 256: 
                        case 260: {
                            switch ((int)mSG.wParam) {
                                case 16: 
                                case 17: 
                                case 18: 
                                case 20: 
                                case 144: 
                                case 145: {
                                    break block12;
                                }
                            }
                            n = OS.MapVirtualKey((int)mSG.wParam, 2);
                            if (n == 0) break;
                            boolean bl3 = bl2 = (n & Integer.MIN_VALUE) != 0;
                            if (bl2) break;
                            for (short s : ACCENTS) {
                                short s2 = OS.VkKeyScan(s);
                                if (s2 == -1 || (long)(s2 & 0xFF) != mSG.wParam) continue;
                                int n2 = s2 >> 8;
                                if (OS.GetKeyState(16) < 0 != ((n2 & 1) != 0) || OS.GetKeyState(17) < 0 != ((n2 & 2) != 0) || OS.GetKeyState(18) < 0 != ((n2 & 4) != 0)) continue;
                                if ((n2 & 7) == 0) break block12;
                                bl2 = true;
                                break block12;
                            }
                            break;
                        }
                    }
                    if (!bl2 && !this.ignoreNextKey) {
                        mSG.hwnd = control.handle;
                        n = 10420227;
                        do {
                            if (bl |= this.filterMessage(mSG)) continue;
                            OS.TranslateMessage(mSG);
                            bl |= OS.DispatchMessage(mSG) == 1L;
                        } while (OS.PeekMessage(mSG, mSG.hwnd, 256, 264, 10420227));
                    }
                    block18 : switch (mSG.message) {
                        case 256: 
                        case 260: {
                            switch ((int)mSG.wParam) {
                                case 16: 
                                case 17: 
                                case 18: 
                                case 20: 
                                case 144: 
                                case 145: {
                                    break block18;
                                }
                            }
                            this.ignoreNextKey = bl2;
                            break;
                        }
                    }
                }
                switch ((int)mSG.wParam) {
                    case 16: 
                    case 17: 
                    case 18: 
                    case 20: 
                    case 144: 
                    case 145: {
                        bl = true;
                    }
                }
                if (bl) {
                    long l6 = OS.GetProcessHeap();
                    OS.HeapFree(l6, 0, l5);
                } else {
                    OS.PostMessage(this.embeddedHwnd, 32770, l4, l5);
                }
                return 0L;
            }
            case 32772: {
                if (this.tray != null) {
                    for (TrayItem trayItem : this.tray.items) {
                        if (trayItem == null || (long)trayItem.id != l4) continue;
                        return trayItem.messageProc(l2, (int)l3, l4, l5);
                    }
                }
                return 0L;
            }
            case 28: {
                long l7;
                Shell shell;
                long l8;
                if (l4 == 0L || this != null || (l8 = OS.GetActiveWindow()) != 0L && OS.IsWindowEnabled(l8)) break;
                Shell shell2 = shell = this.modalDialog != null ? this.modalDialog.parent : this.getModalShell();
                if (shell == null || shell.isDisposed()) break;
                long l9 = shell.handle;
                if (OS.IsWindowEnabled(l9)) {
                    shell.bringToTop();
                    if (shell.isDisposed()) break;
                }
                if ((l7 = OS.GetLastActivePopup(l9)) == 0L || l7 == shell.handle || this.getControl(l7) != null || !OS.IsWindowEnabled(l7)) break;
                OS.SetActiveWindow(l7);
                break;
            }
            case 22: {
                if (l4 == 0L) break;
                this.dispose();
                break;
            }
            case 17: {
                Event event = new Event();
                this.sendEvent(21, event);
                if (event.doit) break;
                return 0L;
            }
            case 26: 
            case 800: {
                OS.SetTimer(this.hwndMessage, 100L, 2000, 0L);
                break;
            }
            case 794: {
                this.resetThemes();
                break;
            }
            case 275: {
                if (l4 == 100L) {
                    OS.KillTimer(this.hwndMessage, 100L);
                    this.runSettings();
                    break;
                }
                this.runTimer(l4);
                break;
            }
            default: {
                Object object;
                int n;
                Object object2;
                if ((int)l3 == TASKBARCREATED && this.tray != null) {
                    object2 = this.tray.items;
                    int n3 = ((TrayItem[])object2).length;
                    for (n = 0; n < n3; ++n) {
                        object = object2[n];
                        if (object == null) continue;
                        ((TrayItem)object).recreate();
                    }
                }
                if ((int)l3 != SWT_OPENDOC || (object2 = this.getSharedData((int)l4, (int)l5)) == null) break;
                if (((String)object2).startsWith(TASKBAR_EVENT)) {
                    String string = ((String)object2).substring(15);
                    n = Integer.parseInt(string);
                    object = this.getMenuItem(n);
                    if (object != null) {
                        ((Widget)object).sendSelectionEvent(13);
                    }
                } else {
                    Event event = new Event();
                    event.text = object2;
                    try {
                        new URI((String)object2);
                        this.sendEvent(54, event);
                    }
                    catch (URISyntaxException uRISyntaxException) {
                        this.sendEvent(46, event);
                    }
                }
                this.wakeThread();
                break;
            }
        }
        return OS.DefWindowProc(l2, (int)l3, l4, l5);
    }

    String getSharedData(int n, int n2) {
        long l2;
        long[] lArray = new long[]{0L};
        if (n == OS.GetCurrentProcessId()) {
            lArray[0] = n2;
        } else {
            l2 = OS.OpenProcess(80, false, n);
            if (l2 == 0L) {
                return null;
            }
            OS.DuplicateHandle(l2, n2, OS.GetCurrentProcess(), lArray, 2, false, 2);
            OS.CloseHandle(l2);
        }
        l2 = OS.MapViewOfFile(lArray[0], 4, 0, 0, 0);
        if (l2 == 0L) {
            return null;
        }
        int n3 = OS.wcslen(l2);
        TCHAR tCHAR = new TCHAR(0, n3);
        int n4 = tCHAR.length() * 2;
        OS.MoveMemory(tCHAR, l2, n4);
        String string = tCHAR.toString(0, n3);
        OS.UnmapViewOfFile(l2);
        if ((long)n2 != lArray[0]) {
            OS.CloseHandle(lArray[0]);
        }
        return string;
    }

    long monitorEnumProc(long l2, long l3, long l4, long l5) {
        if (this.monitorCount >= this.monitors.length) {
            Monitor[] monitorArray = new Monitor[this.monitors.length + 4];
            System.arraycopy(this.monitors, 0, monitorArray, 0, this.monitors.length);
            this.monitors = monitorArray;
        }
        this.monitors[this.monitorCount++] = this.getMonitor(l2);
        return 1L;
    }

    long msgFilterProc(long l2, long l3, long l4) {
        switch ((int)l2) {
            case 16896: {
                if (this.runDragDrop || this.dragCancelled) break;
                OS.MoveMemory(this.hookMsg, l4, MSG.sizeof);
                if (this.hookMsg.message != 512) break;
                this.dragCancelled = true;
                OS.SendMessage(this.hookMsg.hwnd, 31, 0L, 0L);
                break;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 8: {
                OS.MoveMemory(this.hookMsg, l4, MSG.sizeof);
                if (this.hookMsg.message != 0) break;
                MSG mSG = new MSG();
                int n = 10420226;
                if (OS.PeekMessage(mSG, 0L, 0, 0, 10420226) || !this.runAsyncMessages(false)) break;
                this.wakeThread();
                break;
            }
        }
        return OS.CallNextHookEx(this.filterHook, (int)l2, l3, l4);
    }

    int numpadKey(int n) {
        switch (n) {
            case 96: {
                return 48;
            }
            case 97: {
                return 49;
            }
            case 98: {
                return 50;
            }
            case 99: {
                return 51;
            }
            case 100: {
                return 52;
            }
            case 101: {
                return 53;
            }
            case 102: {
                return 54;
            }
            case 103: {
                return 55;
            }
            case 104: {
                return 56;
            }
            case 105: {
                return 57;
            }
            case 106: {
                return 42;
            }
            case 107: {
                return 43;
            }
            case 108: {
                return 0;
            }
            case 109: {
                return 45;
            }
            case 110: {
                return 46;
            }
            case 111: {
                return 47;
            }
        }
        return 0;
    }

    /*
     * Unable to fully structure code
     */
    public boolean post(Event var1_1) {
        var2_2 = Device.class;
        synchronized (Device.class) {
            if (this.isDisposed()) {
                this.error(45);
            }
            if (var1_1 == null) {
                this.error(4);
            }
            var3_3 = var1_1.type;
            switch (var3_3) {
                case 1: 
                case 2: {
                    var4_4 = new KEYBDINPUT();
                    var4_4.wVk = (short)Display.untranslateKey(var1_1.keyCode);
                    if (var4_4.wVk == 0) {
                        var5_6 = var1_1.character;
                        switch (var5_6) {
                            case '\b': {
                                var4_4.wVk = (short)8;
                                break;
                            }
                            case '\r': {
                                var4_4.wVk = (short)13;
                                break;
                            }
                            case '\u007f': {
                                var4_4.wVk = (short)46;
                                break;
                            }
                            case '\u001b': {
                                var4_4.wVk = (short)27;
                                break;
                            }
                            case '\t': {
                                var4_4.wVk = (short)9;
                                break;
                            }
                            case '\n': {
                                // ** MonitorExit[var2_2] (shouldn't be in output)
                                return false;
                            }
                            default: {
                                var4_4.wVk = OS.VkKeyScan((short)var5_6);
                                if (var4_4.wVk == -1) {
                                    // ** MonitorExit[var2_2] (shouldn't be in output)
                                    return false;
                                }
                                var6_12 = var4_4;
                                var6_12.wVk = (short)(var6_12.wVk & 255);
                                break;
                            }
                        }
                    }
                    var4_4.dwFlags = var3_3 == 2 ? 2 : 0;
                    switch (var4_4.wVk) {
                        case 3: 
                        case 33: 
                        case 34: 
                        case 35: 
                        case 36: 
                        case 37: 
                        case 38: 
                        case 39: 
                        case 40: 
                        case 44: 
                        case 45: 
                        case 46: 
                        case 111: 
                        case 144: {
                            var5_7 = var4_4;
                            var5_7.dwFlags |= 1;
                            break;
                        }
                    }
                    var5_8 = new INPUT();
                    var5_8.type = 1;
                    var5_8.ki = var4_4;
                    // ** MonitorExit[var2_2] (shouldn't be in output)
                    return OS.SendInput(1, var5_8, INPUT.sizeof) != 0;
                }
                case 3: 
                case 4: 
                case 5: 
                case 37: {
                    var4_5 = new MOUSEINPUT();
                    if (var3_3 != 5) ** GOTO lbl64
                    var4_5.dwFlags = 49153;
                    var5_9 = OS.GetSystemMetrics(76);
                    var6_13 = OS.GetSystemMetrics(77);
                    var7_14 = OS.GetSystemMetrics(78);
                    var8_15 = OS.GetSystemMetrics(79);
                    var9_16 = var1_1.getLocationInPixels();
                    var4_5.dx = ((var9_16.x - var5_9) * 65535 + var7_14 - 2) / (var7_14 - 1);
                    var4_5.dy = ((var9_16.y - var6_13) * 65535 + var8_15 - 2) / (var8_15 - 1);
                    ** GOTO lbl100
lbl64:
                    // 1 sources

                    if (var3_3 != 37) ** GOTO lbl79
                    var4_5.dwFlags = 2048;
                    switch (var1_1.detail) {
                        case 2: {
                            var4_5.mouseData = var1_1.count * 120;
                            ** GOTO lbl100
                        }
                        case 1: {
                            var5_10 = new int[]{0};
                            OS.SystemParametersInfo(104, 0, var5_10, 0);
                            var4_5.mouseData = var1_1.count * 120 / var5_10[0];
                            ** GOTO lbl100
                        }
                        default: {
                            // ** MonitorExit[var2_2] (shouldn't be in output)
                            return false;
                        }
                    }
lbl79:
                    // 1 sources

                    switch (var1_1.button) {
                        case 1: {
                            var4_5.dwFlags = var3_3 == 3 ? 2 : 4;
                            break;
                        }
                        case 2: {
                            var4_5.dwFlags = var3_3 == 3 ? 32 : 64;
                            break;
                        }
                        case 3: {
                            var4_5.dwFlags = var3_3 == 3 ? 8 : 16;
                            break;
                        }
                        case 4: {
                            var4_5.dwFlags = var3_3 == 3 ? 128 : 256;
                            var4_5.mouseData = 1;
                            break;
                        }
                        case 5: {
                            var4_5.dwFlags = var3_3 == 3 ? 128 : 256;
                            var4_5.mouseData = 2;
                            break;
                        }
                        default: {
                            // ** MonitorExit[var2_2] (shouldn't be in output)
                            return false;
                        }
                    }
lbl100:
                    // 8 sources

                    var5_11 = new INPUT();
                    var5_11.type = 0;
                    var5_11.mi = var4_5;
                    // ** MonitorExit[var2_2] (shouldn't be in output)
                    return OS.SendInput(1, var5_11, INPUT.sizeof) != 0;
                }
            }
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return false;
        }
    }

    void postEvent(Event event) {
        int n;
        if (this.eventQueue == null) {
            this.eventQueue = new Event[4];
        }
        int n2 = this.eventQueue.length;
        for (n = 0; n < n2 && this.eventQueue[n] != null; ++n) {
        }
        if (n == n2) {
            Event[] eventArray = new Event[n2 + 4];
            System.arraycopy(this.eventQueue, 0, eventArray, 0, n2);
            this.eventQueue = eventArray;
        }
        this.eventQueue[n] = event;
    }

    static void register(Display display) {
        Class<Device> clazz = Device.class;
        synchronized (Device.class) {
            for (int i = 0; i < Displays.length; ++i) {
                if (Displays[i] != null) continue;
                Display.Displays[i] = display;
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return;
            }
            Display[] displayArray = new Display[Displays.length + 4];
            System.arraycopy(Displays, 0, displayArray, 0, Displays.length);
            displayArray[Display.Displays.length] = display;
            Displays = displayArray;
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    @Override
    protected void release() {
        block24: {
            ExceptionStash exceptionStash;
            block25: {
                exceptionStash = new ExceptionStash();
                Throwable throwable = null;
                try {
                    try {
                        this.sendEvent(12, new Event());
                    }
                    catch (Error | RuntimeException throwable2) {
                        exceptionStash.stash(throwable2);
                    }
                    for (Shell object : this.getShells()) {
                        try {
                            if (object.isDisposed()) continue;
                            object.dispose();
                        }
                        catch (Error | RuntimeException throwable7) {
                            exceptionStash.stash(throwable7);
                        }
                    }
                    try {
                        if (this.tray != null) {
                            this.tray.dispose();
                        }
                    }
                    catch (Error | RuntimeException throwable4) {
                        exceptionStash.stash(throwable4);
                    }
                    this.tray = null;
                    try {
                        if (this.taskBar != null) {
                            this.taskBar.dispose();
                        }
                    }
                    catch (Error | RuntimeException throwable5) {
                        exceptionStash.stash(throwable5);
                    }
                    this.taskBar = null;
                    while (true) {
                        try {
                            while (this != false) {
                            }
                        }
                        catch (Error | RuntimeException throwable6) {
                            exceptionStash.stash(throwable6);
                            continue;
                        }
                        break;
                    }
                    if (this.disposeList != null) {
                        for (Runnable runnable : this.disposeList) {
                            if (runnable == null) continue;
                            try {
                                runnable.run();
                            }
                            catch (Error | RuntimeException throwable2) {
                                exceptionStash.stash(throwable2);
                            }
                        }
                    }
                    this.disposeList = null;
                    this.synchronizer.releaseSynchronizer();
                    this.synchronizer = null;
                    this.releaseDisplay();
                    super.release();
                    if (exceptionStash == null) break block24;
                    if (throwable == null) break block25;
                }
                catch (Throwable throwable8) {
                    throwable = throwable8;
                    throw throwable8;
                }
                try {
                    exceptionStash.close();
                }
                catch (Throwable throwable9) {
                    throwable.addSuppressed(throwable9);
                }
                break block24;
            }
            exceptionStash.close();
        }
    }

    void releaseDisplay() {
        Resource resource;
        int n;
        if (this.embeddedHwnd != 0L) {
            OS.PostMessage(this.embeddedHwnd, 32771, 0L, 0L);
        }
        if (this.hIconSearch != 0L) {
            OS.DestroyIcon(this.hIconSearch);
        }
        if (this.hIconCancel != 0L) {
            OS.DestroyIcon(this.hIconCancel);
        }
        this.resetThemes();
        if (this.menuBarBorderPen != 0L) {
            OS.DeleteObject(this.menuBarBorderPen);
        }
        this.menuBarBorderPen = 0L;
        if (this.msgHook != 0L) {
            OS.UnhookWindowsHookEx(this.msgHook);
        }
        this.msgHook = 0L;
        if (this.filterHook != 0L) {
            OS.UnhookWindowsHookEx(this.filterHook);
        }
        this.filterHook = 0L;
        this.msgFilterCallback.dispose();
        this.msgFilterCallback = null;
        this.msgFilterProc = 0L;
        if (this.idleHook != 0L) {
            OS.UnhookWindowsHookEx(this.idleHook);
        }
        this.idleHook = 0L;
        this.foregroundIdleCallback.dispose();
        this.foregroundIdleCallback = null;
        this.foregroundIdleProc = 0L;
        OS.KillTimer(this.hwndMessage, 100L);
        if (this.hwndMessage != 0L) {
            OS.DestroyWindow(this.hwndMessage);
        }
        this.hwndMessage = 0L;
        this.messageCallback.dispose();
        this.messageCallback = null;
        this.messageProc = 0L;
        long l2 = OS.GetProcessHeap();
        long l3 = OS.GetModuleHandle(null);
        OS.UnregisterClass(this.windowClass, l3);
        OS.UnregisterClass(this.windowShadowClass, l3);
        OS.UnregisterClass(this.windowOwnDCClass, l3);
        Object var5_3 = null;
        this.windowOwnDCClass = var5_3;
        this.windowShadowClass = var5_3;
        this.windowClass = var5_3;
        this.windowCallback.dispose();
        this.windowCallback = null;
        this.windowProc = 0L;
        if (this.systemFont != null) {
            this.systemFont.dispose();
        }
        this.systemFont = null;
        this.lfSystemFont = null;
        if (this.errorImage != null) {
            this.errorImage.dispose();
        }
        if (this.infoImage != null) {
            this.infoImage.dispose();
        }
        if (this.questionImage != null) {
            this.questionImage.dispose();
        }
        if (this.warningIcon != null) {
            this.warningIcon.dispose();
        }
        Object var6_4 = null;
        this.warningIcon = var6_4;
        this.questionImage = var6_4;
        this.infoImage = var6_4;
        this.errorImage = var6_4;
        Resource[] resourceArray = this.cursors;
        int n2 = resourceArray.length;
        for (n = 0; n < n2; ++n) {
            resource = resourceArray[n];
            if (resource == null) continue;
            resource.dispose();
        }
        this.cursors = null;
        if (this.resources != null) {
            resourceArray = this.resources;
            n2 = resourceArray.length;
            for (n = 0; n < n2; ++n) {
                resource = resourceArray[n];
                if (resource == null) continue;
                resource.dispose();
            }
            this.resources = null;
        }
        if (this.lpCustColors != 0L) {
            OS.HeapFree(l2, 0, this.lpCustColors);
        }
        this.lpCustColors = 0L;
        OS.OleUninitialize();
        OS.BufferedPaintUnInit();
        this.thread = null;
        this.hookMsg = resourceArray = null;
        this.msg = resourceArray;
        this.keyboard = null;
        this.modalDialog = null;
        this.modalShells = null;
        this.data = null;
        this.keys = null;
        this.values = null;
        Object var8_7 = null;
        this.popups = var8_7;
        this.bars = var8_7;
        this.indexTable = null;
        this.timerIds = null;
        this.controlTable = null;
        Object var9_9 = null;
        this.lastHittestControl = var9_9;
        this.lastGetControl = var9_9;
        this.lastControl = var9_9;
        resource = null;
        this.toolDisabledImageList = resource;
        this.toolHotImageList = resource;
        this.toolImageList = resource;
        this.imageList = resource;
        this.timerList = null;
        this.tableBuffer = null;
        Object var11_11 = null;
        this.filterTable = var11_11;
        this.eventTable = var11_11;
        this.items = null;
        this.clickRect = null;
        this.monitors = null;
        this.touchSources = null;
        this.threadId = 0;
    }

    void releaseImageList(ImageList imageList) {
        int n = 0;
        int n2 = this.imageList.length;
        while (n < n2) {
            if (this.imageList[n] == imageList) {
                if (imageList.removeRef() > 0) {
                    return;
                }
                imageList.dispose();
                System.arraycopy(this.imageList, n + 1, this.imageList, n, --n2 - n);
                this.imageList[n2] = null;
                for (int i = 0; i < n2; ++i) {
                    if (this.imageList[i] == null) continue;
                    return;
                }
                this.imageList = null;
                continue;
            }
            ++n;
        }
    }

    void releaseToolImageList(ImageList imageList) {
        int n = 0;
        int n2 = this.toolImageList.length;
        while (n < n2) {
            if (this.toolImageList[n] == imageList) {
                if (imageList.removeRef() > 0) {
                    return;
                }
                imageList.dispose();
                System.arraycopy(this.toolImageList, n + 1, this.toolImageList, n, --n2 - n);
                this.toolImageList[n2] = null;
                for (int i = 0; i < n2; ++i) {
                    if (this.toolImageList[i] == null) continue;
                    return;
                }
                this.toolImageList = null;
                continue;
            }
            ++n;
        }
    }

    void releaseToolHotImageList(ImageList imageList) {
        int n = 0;
        int n2 = this.toolHotImageList.length;
        while (n < n2) {
            if (this.toolHotImageList[n] == imageList) {
                if (imageList.removeRef() > 0) {
                    return;
                }
                imageList.dispose();
                System.arraycopy(this.toolHotImageList, n + 1, this.toolHotImageList, n, --n2 - n);
                this.toolHotImageList[n2] = null;
                for (int i = 0; i < n2; ++i) {
                    if (this.toolHotImageList[i] == null) continue;
                    return;
                }
                this.toolHotImageList = null;
                continue;
            }
            ++n;
        }
    }

    void releaseToolDisabledImageList(ImageList imageList) {
        int n = 0;
        int n2 = this.toolDisabledImageList.length;
        while (n < n2) {
            if (this.toolDisabledImageList[n] == imageList) {
                if (imageList.removeRef() > 0) {
                    return;
                }
                imageList.dispose();
                System.arraycopy(this.toolDisabledImageList, n + 1, this.toolDisabledImageList, n, --n2 - n);
                this.toolDisabledImageList[n2] = null;
                for (int i = 0; i < n2; ++i) {
                    if (this.toolDisabledImageList[i] == null) continue;
                    return;
                }
                this.toolDisabledImageList = null;
                continue;
            }
            ++n;
        }
    }

    public void removeFilter(int n, Listener listener) {
        this.checkDevice();
        if (listener == null) {
            this.error(4);
        }
        if (this.filterTable == null) {
            return;
        }
        this.filterTable.unhook(n, listener);
        if (this.filterTable.size() == 0) {
            this.filterTable = null;
        }
    }

    public void removeListener(int n, Listener listener) {
        this.checkDevice();
        if (listener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(n, listener);
    }

    void removeBar(Menu menu) {
        if (this.bars == null) {
            return;
        }
        for (int i = 0; i < this.bars.length; ++i) {
            if (this.bars[i] != menu) continue;
            this.bars[i] = null;
            return;
        }
    }

    Control removeControl(long l2) {
        if (l2 == 0L) {
            return null;
        }
        Object var3_2 = null;
        this.lastGetControl = var3_2;
        this.lastControl = var3_2;
        Control control = null;
        int n = (int)OS.RemoveProp(l2, SWT_OBJECT_INDEX) - 1;
        if (0 <= n && n < this.controlTable.length) {
            control = this.controlTable[n];
            this.controlTable[n] = null;
            this.indexTable[n] = this.freeSlot;
            this.freeSlot = n;
        }
        return control;
    }

    void removeMenuItem(MenuItem menuItem) {
        if (this.items == null) {
            return;
        }
        this.items[menuItem.id - 108] = null;
    }

    void removePopup(Menu menu) {
        if (this.popups == null) {
            return;
        }
        for (int i = 0; i < this.popups.length; ++i) {
            if (this.popups[i] != menu) continue;
            this.popups[i] = null;
            return;
        }
    }

    boolean runAsyncMessages(boolean bl) {
        return this.synchronizer.runAsyncMessages(bl);
    }

    boolean runDeferredEvents() {
        Event event;
        boolean bl = false;
        while (this.eventQueue != null && (event = this.eventQueue[0]) != null) {
            Widget widget;
            int n = this.eventQueue.length;
            System.arraycopy(this.eventQueue, 1, this.eventQueue, 0, --n);
            this.eventQueue[n] = null;
            Widget widget2 = event.widget;
            if (widget2 == null || widget2.isDisposed() || (widget = event.item) != null && widget.isDisposed()) continue;
            bl = true;
            widget2.sendEvent(event);
        }
        this.eventQueue = null;
        return bl;
    }

    boolean runDeferredLayouts() {
        if (this.layoutDeferredCount != 0) {
            Composite[] compositeArray = this.layoutDeferred;
            int n = this.layoutDeferredCount;
            this.layoutDeferred = null;
            this.layoutDeferredCount = 0;
            for (Composite composite : compositeArray) {
                if (composite.isDisposed()) continue;
                composite.setLayoutDeferred(false);
            }
            return true;
        }
        return false;
    }

    boolean runPopups() {
        Menu menu;
        if (this.popups == null) {
            return false;
        }
        boolean bl = false;
        while (this.popups != null && (menu = this.popups[0]) != null) {
            int n = this.popups.length;
            System.arraycopy(this.popups, 1, this.popups, 0, --n);
            this.popups[n] = null;
            this.runDeferredEvents();
            if (!menu.isDisposed()) {
                menu._setVisible(true);
            }
            bl = true;
        }
        this.popups = null;
        return bl;
    }

    void runSettings() {
        Font font = this.getSystemFont();
        this.saveResources();
        this.sendEvent(39, null);
        Font font2 = this.getSystemFont();
        boolean bl = font.equals(font2);
        for (Shell shell : this.getShells()) {
            if (shell.isDisposed()) continue;
            if (!bl) {
                shell.updateFont(font, font2);
            }
            shell.layout(true, true);
        }
    }

    boolean runSkin() {
        if (this.skinCount > 0) {
            Widget[] widgetArray = this.skinList;
            int n = this.skinCount;
            this.skinList = new Widget[1024];
            this.skinCount = 0;
            if (this.eventTable != null && this.eventTable.hooks(45)) {
                for (int i = 0; i < n; ++i) {
                    Widget widget = widgetArray[i];
                    if (widget == null || widget.isDisposed()) continue;
                    Widget widget2 = widget;
                    widget2.state &= 0xFFDFFFFF;
                    widgetArray[i] = null;
                    Event event = new Event();
                    event.widget = widget;
                    this.sendEvent(45, event);
                }
            }
            return true;
        }
        return false;
    }

    boolean runTimer(long l2) {
        if (this.timerList != null && this.timerIds != null) {
            for (int i = 0; i < this.timerIds.length; ++i) {
                if (this.timerIds[i] != l2) continue;
                OS.KillTimer(this.hwndMessage, this.timerIds[i]);
                this.timerIds[i] = 0L;
                Runnable runnable = this.timerList[i];
                this.timerList[i] = null;
                if (runnable != null) {
                    try {
                        runnable.run();
                    }
                    catch (RuntimeException runtimeException) {
                        this.runtimeExceptionHandler.accept(runtimeException);
                    }
                    catch (Error error) {
                        this.errorHandler.accept(error);
                    }
                }
                return true;
            }
        }
        return false;
    }

    void saveResources() {
        Object object;
        int n = 0;
        if (this.resources == null) {
            this.resources = new Resource[27];
        } else {
            n = this.resources.length;
            object = new Resource[n + 27];
            System.arraycopy(this.resources, 0, object, 0, n);
            this.resources = object;
        }
        if (this.systemFont != null) {
            object = new NONCLIENTMETRICS();
            object.cbSize = NONCLIENTMETRICS.sizeof;
            if (OS.SystemParametersInfo(41, 0, (NONCLIENTMETRICS)object, 0)) {
                LOGFONT lOGFONT = object.lfMessageFont;
                if (this.lfSystemFont == null || lOGFONT.lfCharSet != this.lfSystemFont.lfCharSet || lOGFONT.lfHeight != this.lfSystemFont.lfHeight || lOGFONT.lfWidth != this.lfSystemFont.lfWidth || lOGFONT.lfEscapement != this.lfSystemFont.lfEscapement || lOGFONT.lfOrientation != this.lfSystemFont.lfOrientation || lOGFONT.lfWeight != this.lfSystemFont.lfWeight || lOGFONT.lfItalic != this.lfSystemFont.lfItalic || lOGFONT.lfUnderline != this.lfSystemFont.lfUnderline || lOGFONT.lfStrikeOut != this.lfSystemFont.lfStrikeOut || lOGFONT.lfCharSet != this.lfSystemFont.lfCharSet || lOGFONT.lfOutPrecision != this.lfSystemFont.lfOutPrecision || lOGFONT.lfClipPrecision != this.lfSystemFont.lfClipPrecision || lOGFONT.lfQuality != this.lfSystemFont.lfQuality || lOGFONT.lfPitchAndFamily != this.lfSystemFont.lfPitchAndFamily || !this.getFontName(lOGFONT).equals(this.getFontName(this.lfSystemFont))) {
                    this.resources[n++] = this.systemFont;
                    this.lfSystemFont = lOGFONT;
                    this.systemFont = null;
                }
            }
        }
        if (this.errorImage != null) {
            this.resources[n++] = this.errorImage;
        }
        if (this.infoImage != null) {
            this.resources[n++] = this.infoImage;
        }
        if (this.questionImage != null) {
            this.resources[n++] = this.questionImage;
        }
        if (this.warningIcon != null) {
            this.resources[n++] = this.warningIcon;
        }
        this.warningIcon = object = null;
        this.questionImage = object;
        this.infoImage = object;
        this.errorImage = object;
        for (int i = 0; i < this.cursors.length; ++i) {
            if (this.cursors[i] != null) {
                this.resources[n++] = this.cursors[i];
            }
            this.cursors[i] = null;
        }
        if (n < 27) {
            Resource[] resourceArray = new Resource[n];
            System.arraycopy(this.resources, 0, resourceArray, 0, n);
            this.resources = resourceArray;
        }
    }

    void sendEvent(int n, Event event) {
        if (this.eventTable == null && this.filterTable == null) {
            return;
        }
        if (event == null) {
            event = new Event();
        }
        event.display = this;
        event.type = n;
        if (event.time == 0) {
            event.time = this.getLastEventTime();
        }
        Display display = this;
        if (event != null && this.eventTable != null) {
            this.sendEvent(this.eventTable, event);
        }
    }

    void sendEvent(EventTable eventTable, Event event) {
        int n = event.type;
        this.sendPreEvent(n);
        eventTable.sendEvent(event);
        this.sendPostEvent(n);
    }

    void sendPreEvent(int n) {
        if (n != 50 && n != 51 && n != 52 && n != 53 && this.eventTable != null && this.eventTable.hooks(50)) {
            Event event = new Event();
            event.detail = n;
            this.sendEvent(50, event);
        }
    }

    void sendPostEvent(int n) {
        if (n != 50 && n != 51 && n != 52 && n != 53 && this.eventTable != null && this.eventTable.hooks(51)) {
            Event event = new Event();
            event.detail = n;
            this.sendEvent(51, event);
        }
    }

    public void sendPreExternalEventDispatchEvent() {
        if (this.eventTable != null && this.eventTable.hooks(52)) {
            this.sendEvent(52, null);
        }
    }

    public void sendPostExternalEventDispatchEvent() {
        if (this.eventTable != null && this.eventTable.hooks(53)) {
            this.sendEvent(53, null);
        }
    }

    public void setCursorLocation(int n, int n2) {
        this.checkDevice();
        this.setCursorLocationInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2));
    }

    void setCursorLocationInPixels(int n, int n2) {
        OS.SetCursorPos(n, n2);
    }

    public void setCursorLocation(Point point) {
        this.checkDevice();
        if (point == null) {
            this.error(4);
        }
        this.setCursorLocation(point.x, point.y);
    }

    int _toColorPixel(Object object) {
        if (object == null) {
            return -1;
        }
        return ((Color)object).handle;
    }

    /*
     * Exception decompiling
     */
    public void setData(String var1, Object var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl228 : ICONST_0 - null : trying to set 1 previously set to 2
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

    public void setData(Object object) {
        this.checkDevice();
        this.data = object;
    }

    public static String getAppName() {
        return APP_NAME;
    }

    public static String getAppVersion() {
        return APP_VERSION;
    }

    public static void setAppName(String string) {
        APP_NAME = string;
    }

    public static void setAppVersion(String string) {
        APP_VERSION = string;
    }

    void setModalDialog(Dialog dialog) {
        this.modalDialog = dialog;
        for (Shell shell : this.getShells()) {
            shell.updateModal();
        }
    }

    void setModalShell(Shell shell) {
        int n;
        if (this.modalShells == null) {
            this.modalShells = new Shell[4];
        }
        int n2 = this.modalShells.length;
        for (n = 0; n < n2; ++n) {
            if (this.modalShells[n] == shell) {
                return;
            }
            if (this.modalShells[n] == null) break;
        }
        if (n == n2) {
            Shell[] shellArray = new Shell[n2 + 4];
            System.arraycopy(this.modalShells, 0, shellArray, 0, n2);
            this.modalShells = shellArray;
        }
        this.modalShells[n] = shell;
        for (Shell shell2 : this.getShells()) {
            shell2.updateModal();
        }
    }

    public void setSynchronizer(Synchronizer synchronizer) {
        this.checkDevice();
        if (synchronizer == null) {
            this.error(4);
        }
        if (synchronizer == this.synchronizer) {
            return;
        }
        Class<Device> clazz = Device.class;
        synchronized (Device.class) {
            Synchronizer synchronizer2 = this.synchronizer;
            this.synchronizer = synchronizer;
            // ** MonitorExit[var3_2] (shouldn't be in output)
            if (synchronizer2 != null) {
                synchronizer2.moveAllEventsTo(synchronizer);
            }
            return;
        }
    }

    public final void setRuntimeExceptionHandler(Consumer consumer) {
        this.checkDevice();
        this.runtimeExceptionHandler = Objects.requireNonNull(consumer);
    }

    public final Consumer getRuntimeExceptionHandler() {
        return this.runtimeExceptionHandler;
    }

    public final void setErrorHandler(Consumer consumer) {
        this.checkDevice();
        this.errorHandler = Objects.requireNonNull(consumer);
    }

    public final Consumer getErrorHandler() {
        return this.errorHandler;
    }

    int shiftedKey(int n) {
        for (int i = 0; i < this.keyboard.length; ++i) {
            this.keyboard[i] = 0;
        }
        byte[] byArray = this.keyboard;
        int n2 = 16;
        byArray[16] = (byte)(byArray[16] | 0xFFFFFF80);
        char[] cArray = new char[]{'\u0000'};
        if (OS.ToUnicode(n, n, this.keyboard, cArray, 1, 0) == 1) {
            return cArray[0];
        }
        return 0;
    }

    public boolean sleep() {
        this.checkDevice();
        if (!this.synchronizer.isMessagesEmpty()) {
            return true;
        }
        this.sendPreExternalEventDispatchEvent();
        boolean bl = OS.WaitMessage();
        this.sendPostExternalEventDispatchEvent();
        return bl;
    }

    public void syncExec(Runnable runnable) {
        Class<Device> clazz = Device.class;
        synchronized (Device.class) {
            if (this.isDisposed()) {
                this.error(45);
            }
            Synchronizer synchronizer = this.synchronizer;
            // ** MonitorExit[var3_2] (shouldn't be in output)
            synchronizer.syncExec(runnable);
            return;
        }
    }

    public Object syncCall(SwtCallable swtCallable) throws Exception, Exception {
        Objects.nonNull(swtCallable);
        Object[] objectArray = new Object[]{null};
        Object[] objectArray2 = new Object[]{null};
        this.syncExec(() -> Display.lambda$syncCall$0(objectArray, swtCallable, objectArray2));
        if (objectArray2[0] != null) {
            Exception exception = (Exception)objectArray2[0];
            throw exception;
        }
        return objectArray[0];
    }

    public void timerExec(int n, Runnable runnable) {
        long l2;
        int n2;
        this.checkDevice();
        if (runnable == null) {
            this.error(4);
        }
        if (this.timerList == null) {
            this.timerList = new Runnable[4];
        }
        if (this.timerIds == null) {
            this.timerIds = new long[4];
        }
        for (n2 = 0; n2 < this.timerList.length && this.timerList[n2] != runnable; ++n2) {
        }
        long l3 = 0L;
        if (n2 != this.timerList.length) {
            l3 = this.timerIds[n2];
            if (n < 0) {
                OS.KillTimer(this.hwndMessage, l3);
                this.timerList[n2] = null;
                this.timerIds[n2] = 0L;
                return;
            }
        } else {
            if (n < 0) {
                return;
            }
            for (n2 = 0; n2 < this.timerList.length && this.timerList[n2] != null; ++n2) {
            }
            l3 = this.nextTimerId++;
            if (n2 == this.timerList.length) {
                Runnable[] runnableArray = new Runnable[this.timerList.length + 4];
                System.arraycopy(this.timerList, 0, runnableArray, 0, this.timerList.length);
                this.timerList = runnableArray;
                long[] lArray = new long[this.timerIds.length + 4];
                System.arraycopy(this.timerIds, 0, lArray, 0, this.timerIds.length);
                this.timerIds = lArray;
            }
        }
        if ((l2 = OS.SetTimer(this.hwndMessage, l3, n, 0L)) != 0L) {
            this.timerList[n2] = runnable;
            this.timerIds[n2] = l2;
        }
    }

    boolean translateAccelerator(MSG mSG, Control control) {
        this.accelKeyHit = true;
        boolean bl = control.translateAccelerator(mSG);
        this.accelKeyHit = false;
        return bl;
    }

    static int translateKey(int n) {
        for (int[] nArray : KeyTable) {
            if (nArray[0] != n) continue;
            return nArray[1];
        }
        return 0;
    }

    boolean translateMnemonic(MSG mSG, Control control) {
        switch (mSG.message) {
            case 258: 
            case 262: {
                return control.translateMnemonic(mSG);
            }
        }
        return false;
    }

    boolean translateTraversal(MSG mSG, Control control) {
        switch (mSG.message) {
            case 256: {
                switch ((int)mSG.wParam) {
                    case 9: 
                    case 13: 
                    case 27: 
                    case 33: 
                    case 34: 
                    case 37: 
                    case 38: 
                    case 39: 
                    case 40: {
                        return control.translateTraversal(mSG);
                    }
                }
                break;
            }
            case 260: {
                switch ((int)mSG.wParam) {
                    case 18: {
                        return control.translateTraversal(mSG);
                    }
                }
                break;
            }
        }
        return false;
    }

    static int untranslateKey(int n) {
        for (int[] nArray : KeyTable) {
            if (nArray[1] != n) continue;
            return nArray[0];
        }
        return 0;
    }

    public void update() {
        this.checkDevice();
        if (OS.IsHungAppWindow(this.hwndMessage)) {
            MSG object = new MSG();
            int n = 3;
            OS.PeekMessage(object, this.hwndMessage, 32773, 32773, 3);
        }
        for (Shell shell : this.getShells()) {
            if (shell.isDisposed()) continue;
            shell.update(true);
        }
    }

    public void wake() {
        Class<Device> clazz = Device.class;
        synchronized (Device.class) {
            if (this.isDisposed()) {
                this.error(45);
            }
            if (this.thread == Thread.currentThread()) {
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return;
            }
            this.wakeThread();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    void wakeThread() {
        OS.PostThreadMessage(this.threadId, 0, 0L, 0L);
    }

    long windowProc(long l2, long l3, long l4, long l5) {
        Control control;
        if (this.lastControl != null && this.lastHwnd == l2) {
            return this.lastControl.windowProc(l2, (int)l3, l4, l5);
        }
        int n = (int)OS.GetProp(l2, SWT_OBJECT_INDEX) - 1;
        if (0 <= n && n < this.controlTable.length && (control = this.controlTable[n]) != null) {
            this.lastHwnd = l2;
            this.lastControl = control;
            return control.windowProc(l2, (int)l3, l4, l5);
        }
        return OS.DefWindowProc(l2, (int)l3, l4, l5);
    }

    int textWidth(String string, long l2) {
        long l3 = 0L;
        RECT rECT = new RECT();
        long l4 = OS.GetDC(l2);
        long l5 = OS.SendMessage(l2, 49, 0L, 0L);
        if (l5 != 0L) {
            l3 = OS.SelectObject(l4, l5);
        }
        int n = 3104;
        char[] cArray = string.toCharArray();
        OS.DrawText(l4, cArray, cArray.length, rECT, 3104);
        if (l5 != 0L) {
            OS.SelectObject(l4, l3);
        }
        OS.ReleaseDC(l2, l4);
        return rECT.right - rECT.left;
    }

    String wrapText(String string, long l2, int n) {
        String string2 = "\r\n";
        string = Display.withCrLf(string);
        int n2 = string.length();
        if (n <= 0 || n2 == 0 || n2 == 1) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n3 = 0;
        int n4 = 0;
        while (n3 < n2) {
            boolean bl;
            n4 = string.indexOf("\r\n", n3);
            boolean bl2 = bl = n4 == -1;
            if (bl) {
                n4 = n2;
            }
            int n5 = n4 + 2;
            while (n4 > n3 + 1 && Character.isWhitespace(string.charAt(n4 - 1))) {
                --n4;
            }
            int n6 = n3;
            int n7 = n3;
            int n8 = n3;
            while (n8 < n4) {
                int n9 = n6;
                int n10 = n7;
                n6 = n8;
                while (n8 < n4 && !Character.isWhitespace(string.charAt(n8))) {
                    ++n8;
                }
                n7 = n8 - 1;
                String string3 = string.substring(n3, n7 + 1);
                int n11 = this.textWidth(string3, l2);
                while (n8 < n4 && Character.isWhitespace(string.charAt(n8))) {
                    ++n8;
                }
                if (n11 > n) {
                    if (n9 == n6) {
                        while (n6 < n7 && (n11 = this.textWidth(string3 = string.substring(n3, n6 + 1), l2)) < n) {
                            ++n6;
                        }
                        if (n6 == n9) {
                            ++n6;
                        }
                        n10 = n6 - 1;
                    }
                    string3 = string.substring(n3, n10 + 1);
                    stringBuilder.append(string3);
                    stringBuilder.append("\r\n");
                }
                n8 = n6;
                n3 = n6;
                n7 = n6;
            }
            if (n3 < n4) {
                stringBuilder.append(string.substring(n3, n4));
            }
            if (!bl) {
                stringBuilder.append("\r\n");
            }
            n3 = n5;
        }
        return stringBuilder.toString();
    }

    static String withCrLf(String string) {
        int n = string.length();
        if (n == 0) {
            return string;
        }
        int n2 = string.indexOf(10, 0);
        if (n2 == -1) {
            return string;
        }
        if (n2 > 0 && string.charAt(n2 - 1) == '\r') {
            return string;
        }
        ++n2;
        int n3 = 1;
        while (n2 < n && (n2 = string.indexOf(10, n2)) != -1) {
            ++n3;
            ++n2;
        }
        n2 = 0;
        StringBuilder stringBuilder = new StringBuilder(n3 += n);
        while (n2 < n) {
            int n4 = string.indexOf(10, n2);
            if (n4 == -1) {
                n4 = n;
            }
            stringBuilder.append(string.substring(n2, n4));
            n2 = n4;
            if (n2 >= n) continue;
            stringBuilder.append("\r\n");
            ++n2;
        }
        return stringBuilder.toString();
    }

    static char[] withCrLf(char[] cArray) {
        int n = cArray.length;
        if (n == 0) {
            return cArray;
        }
        int n2 = 0;
        for (int i = 0; i < cArray.length; ++i) {
            if (cArray[i] != '\n' || ++n2 != 1 || i <= 0 || cArray[i - 1] != '\r') continue;
            return cArray;
        }
        if (n2 == 0) {
            return cArray;
        }
        char[] cArray2 = new char[n2 += n];
        int n3 = 0;
        for (int i = 0; i < n && n3 < n2; ++i) {
            if (cArray[i] == '\n') {
                cArray2[n3++] = 13;
            }
            cArray2[n3++] = cArray[i];
        }
        return cArray2;
    }

    private static void lambda$static$1() {
        Display display = Display.getCurrent();
        if (display == null) {
            display = Display.getDefault();
        }
        Display.setDevice(display);
    }

    private static void lambda$syncCall$0(Object[] objectArray, SwtCallable swtCallable, Object[] objectArray2) {
        try {
            objectArray[0] = swtCallable.call();
        }
        catch (Exception exception) {
            objectArray2[0] = exception;
        }
    }

    static {
        SWT_OBJECT_INDEX = OS.GlobalAddAtom(new TCHAR(0, "SWT_OBJECT_INDEX", true));
        lpStartupInfo = new STARTUPINFO();
        Display.lpStartupInfo.cb = STARTUPINFO.sizeof;
        OS.GetStartupInfo(lpStartupInfo);
        EXPLORER = new char[]{'E', 'X', 'P', 'L', 'O', 'R', 'E', 'R', '\u0000'};
        TREEVIEW = new char[]{'T', 'R', 'E', 'E', 'V', 'I', 'E', 'W', '\u0000'};
        disableCustomThemeTweaks = Boolean.valueOf(System.getProperty("org.eclipse.swt.internal.win32.disableCustomThemeTweaks"));
        ACCENTS = new short[]{126, 96, 39, 94, 34};
        KeyTable = new int[][]{{18, 65536}, {16, 131072}, {17, 262144}, {38, 0x1000001}, {40, 0x1000002}, {37, 0x1000003}, {39, 0x1000004}, {33, 0x1000005}, {34, 0x1000006}, {36, 0x1000007}, {35, 0x1000008}, {45, 0x1000009}, {8, 8}, {13, 13}, {46, 127}, {27, 27}, {13, 10}, {9, 9}, {112, 0x100000A}, {113, 0x100000B}, {114, 0x100000C}, {115, 0x100000D}, {116, 0x100000E}, {117, 0x100000F}, {118, 0x1000010}, {119, 0x1000011}, {120, 0x1000012}, {121, 0x1000013}, {122, 0x1000014}, {123, 0x1000015}, {124, 0x1000016}, {125, 0x1000017}, {126, 0x1000018}, {127, 0x1000019}, {128, 0x100001A}, {129, 0x100001B}, {130, 0x100001C}, {131, 0x100001D}, {106, 16777258}, {107, 16777259}, {13, 0x1000050}, {109, 16777261}, {110, 16777262}, {111, 16777263}, {96, 0x1000030}, {97, 0x1000031}, {98, 16777266}, {99, 0x1000033}, {100, 16777268}, {101, 16777269}, {102, 16777270}, {103, 16777271}, {104, 16777272}, {105, 16777273}, {20, 16777298}, {144, 16777299}, {145, 16777300}, {19, 0x1000055}, {3, 16777302}, {44, 16777303}};
        Displays = new Display[1];
        TrimEnabled = false;
        DeviceFinder = Display::lambda$static$1;
    }
}

