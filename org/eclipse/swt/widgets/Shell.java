/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.CREATESTRUCT;
import org.eclipse.swt.internal.win32.LOGBRUSH;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MINMAXINFO;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.NMTTDISPINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.STARTUPINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TaskItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class Shell
extends Decorations {
    Menu activeMenu;
    ToolTip[] toolTips;
    long hwndMDIClient;
    long lpstrTip;
    long toolTipHandle;
    long balloonTipHandle;
    long menuItemToolTipHandle;
    int minWidth = -1;
    int minHeight = -1;
    int maxWidth = -1;
    int maxHeight = -1;
    long[] brushes;
    boolean showWithParent;
    boolean fullScreen;
    boolean wasMaximized;
    boolean modified;
    boolean center;
    String toolTitle;
    String balloonTitle;
    long toolIcon;
    long balloonIcon;
    long windowProc;
    Control lastActive;
    static long ToolTipProc;
    static final long DialogProc;
    static final TCHAR DialogClass;
    static final int[] SYSTEM_COLORS;
    static final int BRUSHES_SIZE = 32;

    public Shell() {
        this((Display)null);
    }

    public Shell(int n) {
        this((Display)null, n);
    }

    public Shell(Display display) {
        this(display, 1264);
    }

    public Shell(Display display, int n) {
        this(display, null, n, 0L, false);
    }

    Shell(Display display, Shell shell, int n, long l2, boolean bl) {
        this.checkSubclass();
        if (display == null) {
            display = Display.getCurrent();
        }
        if (display == null) {
            display = Display.getDefault();
        }
        if (!display.isValidThread()) {
            this.error(22);
        }
        if (shell != null && shell.isDisposed()) {
            this.error(5);
        }
        this.center = shell != null && (n & 0x10000000) != 0;
        this.style = Shell.checkStyle(shell, n);
        this.parent = shell;
        this.display = display;
        this.handle = l2;
        if (l2 != 0L && !bl) {
            this.state |= 0x4000;
        }
        this.reskinWidget();
        this.createWidget();
    }

    public Shell(Shell shell) {
        this(shell, 2144);
    }

    public Shell(Shell shell, int n) {
        this(shell != null ? shell.display : null, shell, n, 0L, false);
    }

    public static Shell win32_new(Display display, long l2) {
        return new Shell(display, null, 8, l2, true);
    }

    public static Shell internal_new(Display display, long l2) {
        return new Shell(display, null, 8, l2, false);
    }

    static int checkStyle(Shell shell, int n) {
        n = Decorations.checkStyle(n);
        int n2 = 229376;
        if (((n &= 0xBFFFFFFF) & 0x10000000) != 0) {
            n &= 0xEFFFFFFF;
            if (((n |= shell == null ? 1264 : 2144) & 0x38000) == 0) {
                n |= shell == null ? 65536 : 32768;
            }
        }
        int n3 = n & 0xFFFC7FFF;
        if ((n & 0x20000) != 0) {
            return n3 | 0x20000;
        }
        if ((n & 0x10000) != 0) {
            return n3 | 0x10000;
        }
        if ((n & 0x8000) != 0) {
            return n3 | 0x8000;
        }
        return n3;
    }

    public void addShellListener(ShellListener shellListener) {
        this.checkWidget();
        if (shellListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(shellListener);
        this.addListener(21, typedListener);
        this.addListener(19, typedListener);
        this.addListener(20, typedListener);
        this.addListener(26, typedListener);
        this.addListener(27, typedListener);
    }

    long balloonTipHandle() {
        if (this.balloonTipHandle == 0L) {
            this.createBalloonTipHandle();
        }
        return this.balloonTipHandle;
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        if (l2 == this.toolTipHandle || l2 == this.balloonTipHandle || l2 == this.menuItemToolTipHandle) {
            return OS.CallWindowProc(ToolTipProc, l2, n, l3, l4);
        }
        if (this.hwndMDIClient != 0L) {
            return OS.DefFrameProc(l2, this.hwndMDIClient, n, l3, l4);
        }
        if (this.windowProc != 0L) {
            return OS.CallWindowProc(this.windowProc, l2, n, l3, l4);
        }
        if ((this.style & 4) != 0) {
            int n2 = 3312;
            if ((this.style & 0xCF0) == 0) {
                return OS.DefWindowProc(l2, n, l3, l4);
            }
        }
        if ((this.style & 0x800000) != 0) {
            this.setItemEnabled(61456, false);
        }
        if (this.parent == null) {
            return OS.DefWindowProc(l2, n, l3, l4);
        }
        switch (n) {
            case 7: 
            case 8: {
                return OS.DefWindowProc(l2, n, l3, l4);
            }
        }
        return OS.CallWindowProc(DialogProc, l2, n, l3, l4);
    }

    void center() {
        if (this.parent == null) {
            return;
        }
        Rectangle rectangle = this.getBoundsInPixels();
        Rectangle rectangle2 = this.display.mapInPixels((Control)this.parent, null, this.parent.getClientAreaInPixels());
        int n = Math.max(rectangle2.x, rectangle2.x + (rectangle2.width - rectangle.width) / 2);
        int n2 = Math.max(rectangle2.y, rectangle2.y + (rectangle2.height - rectangle.height) / 2);
        Rectangle rectangle3 = this.parent.getMonitor().getClientArea();
        n = n + rectangle.width > rectangle3.x + rectangle3.width ? Math.max(rectangle3.x, rectangle3.x + rectangle3.width - rectangle.width) : Math.max(n, rectangle3.x);
        n2 = n2 + rectangle.height > rectangle3.y + rectangle3.height ? Math.max(rectangle3.y, rectangle3.y + rectangle3.height - rectangle.height) : Math.max(n2, rectangle3.y);
        this.setLocationInPixels(n, n2);
    }

    public void close() {
        this.checkWidget();
        this.closeWidget();
    }

    void createBalloonTipHandle() {
        this.balloonTipHandle = OS.CreateWindowEx(0, new TCHAR(0, "tooltips_class32", true), null, 67, Integer.MIN_VALUE, 0, Integer.MIN_VALUE, 0, this.handle, 0L, OS.GetModuleHandle(null), null);
        if (this.balloonTipHandle == 0L) {
            this.error(2);
        }
        ToolTipProc = OS.GetWindowLongPtr(this.balloonTipHandle, -4);
        OS.SendMessage(this.balloonTipHandle, 1048, 0L, 32767L);
        this.display.addControl(this.balloonTipHandle, this);
        OS.SetWindowLongPtr(this.balloonTipHandle, -4, this.display.windowProc);
    }

    void setTitleColoring() {
        int n = 0;
        if (OS.WIN32_BUILD >= 19041) {
            n = 20;
        } else {
            if (OS.WIN32_BUILD < 17763) {
                return;
            }
            n = 19;
        }
        int[] nArray = new int[]{1};
        OS.DwmSetWindowAttribute(this.handle, n, nArray, 4);
    }

    @Override
    void createHandle() {
        boolean bl;
        boolean bl2 = bl = this.handle != 0L && (this.state & 0x4000) == 0;
        if (this.handle == 0L || bl) {
            super.createHandle();
        } else {
            this.state |= 2;
            if ((this.style & 0x300) == 0) {
                this.state |= 0x100;
            }
            this.windowProc = OS.GetWindowLongPtr(this.handle, -4);
        }
        if (!bl) {
            if (this.display.useShellTitleColoring) {
                this.setTitleColoring();
            }
            int n = OS.GetWindowLong(this.handle, -16);
            n &= 0xFF3FFFFF;
            n |= Integer.MIN_VALUE;
            if ((this.style & 0x20) != 0) {
                n |= 0xC00000;
            }
            if ((this.style & 8) == 0 && (this.style & 0x810) == 0) {
                n |= 0x800000;
            }
            OS.SetWindowLong(this.handle, -16, n);
            int n2 = 55;
            OS.SetWindowPos(this.handle, 0L, 0, 0, 0, 0, 55);
        }
    }

    void createMenuItemToolTipHandle() {
        this.menuItemToolTipHandle = this.createToolTipHandle(0L);
    }

    void createToolTip(ToolTip toolTip) {
        Object object;
        int n = 0;
        if (this.toolTips == null) {
            this.toolTips = new ToolTip[4];
        }
        while (n < this.toolTips.length && this.toolTips[n] != null) {
            ++n;
        }
        if (n == this.toolTips.length) {
            object = new ToolTip[this.toolTips.length + 4];
            System.arraycopy(this.toolTips, 0, object, 0, this.toolTips.length);
            this.toolTips = object;
        }
        this.toolTips[n] = toolTip;
        toolTip.id = n + 108;
        object = new TOOLINFO();
        object.cbSize = TOOLINFO.sizeof;
        object.hwnd = this.handle;
        object.uId = toolTip.id;
        object.uFlags = 32;
        object.lpszText = -1L;
        OS.SendMessage(toolTip.hwndToolTip(), 1074, 0L, (TOOLINFO)object);
    }

    void createToolTipHandle() {
        this.toolTipHandle = this.createToolTipHandle(this.handle);
    }

    long createToolTipHandle(long l2) {
        long l3 = OS.CreateWindowEx(0, new TCHAR(0, "tooltips_class32", true), null, 3, Integer.MIN_VALUE, 0, Integer.MIN_VALUE, 0, l2, 0L, OS.GetModuleHandle(null), null);
        if (l3 == 0L) {
            this.error(2);
        }
        ToolTipProc = OS.GetWindowLongPtr(l3, -4);
        OS.SendMessage(l3, 1048, 0L, 32767L);
        this.display.addControl(l3, this);
        OS.SetWindowLongPtr(l3, -4, this.display.windowProc);
        return l3;
    }

    @Override
    void deregister() {
        super.deregister();
        if (this.toolTipHandle != 0L) {
            this.display.removeControl(this.toolTipHandle);
        }
        if (this.balloonTipHandle != 0L) {
            this.display.removeControl(this.balloonTipHandle);
        }
        if (this.menuItemToolTipHandle != 0L) {
            this.display.removeControl(this.menuItemToolTipHandle);
        }
    }

    void destroyToolTip(ToolTip toolTip) {
        if (this.toolTips == null) {
            return;
        }
        this.toolTips[toolTip.id - 108] = null;
        if (this.balloonTipHandle != 0L) {
            TOOLINFO tOOLINFO = new TOOLINFO();
            tOOLINFO.cbSize = TOOLINFO.sizeof;
            tOOLINFO.uId = toolTip.id;
            tOOLINFO.hwnd = this.handle;
            OS.SendMessage(this.balloonTipHandle, 1075, 0L, tOOLINFO);
        }
        toolTip.id = -1;
    }

    @Override
    void destroyWidget() {
        this.fixActiveShell();
        super.destroyWidget();
    }

    @Override
    void enableWidget(boolean bl) {
        this.state = bl ? (this.state &= 0xFFFFFFF7) : (this.state |= 8);
        if (Display.TrimEnabled) {
            if (this.isActive()) {
                this.setItemEnabled(61536, bl);
            }
        } else {
            OS.EnableWindow(this.handle, bl);
        }
    }

    @Override
    long findBrush(long l2, int n) {
        long l3;
        if (n == 0) {
            for (int n2 : SYSTEM_COLORS) {
                if (l2 != (long)OS.GetSysColor(n2)) continue;
                return OS.GetSysColorBrush(n2);
            }
        }
        if (this.brushes == null) {
            this.brushes = new long[32];
        }
        LOGBRUSH lOGBRUSH = new LOGBRUSH();
        block9: for (long l4 : this.brushes) {
            if (l4 == 0L) break;
            OS.GetObject(l4, LOGBRUSH.sizeof, lOGBRUSH);
            switch (lOGBRUSH.lbStyle) {
                case 0: {
                    if (n != 0 || (long)lOGBRUSH.lbColor != l2) continue block9;
                    return l4;
                }
                case 3: {
                    if (n != 3 || lOGBRUSH.lbHatch != l2) continue block9;
                    return l4;
                }
            }
        }
        int n3 = this.brushes.length;
        if ((l3 = this.brushes[--n3]) != 0L) {
            OS.DeleteObject(l3);
        }
        System.arraycopy(this.brushes, 0, this.brushes, 1, n3);
        switch (n) {
            case 0: {
                l3 = OS.CreateSolidBrush((int)l2);
                break;
            }
            case 3: {
                l3 = OS.CreatePatternBrush(l2);
            }
        }
        this.brushes[0] = l3;
        return this.brushes[0];
    }

    @Override
    Control findBackgroundControl() {
        return this.background != -1 || this.backgroundImage != null ? this : null;
    }

    @Override
    Cursor findCursor() {
        return this.cursor;
    }

    @Override
    Control findThemeControl() {
        return null;
    }

    ToolTip findToolTip(int n) {
        if (this.toolTips == null) {
            return null;
        }
        return 0 <= (n -= 108) && n < this.toolTips.length ? this.toolTips[n] : null;
    }

    void fixActiveShell() {
        long l2 = OS.GetParent(this.handle);
        if (l2 != 0L && this.handle == OS.GetActiveWindow() && !OS.IsWindowEnabled(l2) && OS.IsWindowVisible(l2)) {
            OS.SetActiveWindow(l2);
        }
    }

    void fixShell(Shell shell, Control control) {
        String string;
        if (this == shell) {
            return;
        }
        if (control == this.lastActive) {
            this.setActiveControl(null);
        }
        if ((string = control.toolTipText) != null) {
            control.setToolTipText(this, null);
            control.setToolTipText(shell, string);
        }
    }

    void fixToolTip() {
        if (this.toolTipHandle == 0L) {
            return;
        }
        TOOLINFO tOOLINFO = new TOOLINFO();
        tOOLINFO.cbSize = TOOLINFO.sizeof;
        if (OS.SendMessage(this.toolTipHandle, 1083, 0L, tOOLINFO) != 0L && (tOOLINFO.uFlags & 1) != 0) {
            OS.SendMessage(this.toolTipHandle, 1075, 0L, tOOLINFO);
            OS.SendMessage(this.toolTipHandle, 1074, 0L, tOOLINFO);
        }
        TOOLINFO tOOLINFO2 = new TOOLINFO();
        tOOLINFO2.cbSize = TOOLINFO.sizeof;
        if (OS.SendMessage(this.menuItemToolTipHandle, 1083, 0L, tOOLINFO2) != 0L && (tOOLINFO2.uFlags & 1) != 0) {
            OS.SendMessage(this.menuItemToolTipHandle, 1075, 0L, tOOLINFO2);
            OS.SendMessage(this.menuItemToolTipHandle, 1074, 0L, tOOLINFO2);
        }
    }

    public void forceActive() {
        this.checkWidget();
        if (!this.isVisible()) {
            return;
        }
        OS.SetForegroundWindow(this.handle);
    }

    @Override
    void forceResize() {
    }

    public int getAlpha() {
        this.checkWidget();
        byte[] byArray = new byte[]{0};
        if (OS.GetLayeredWindowAttributes(this.handle, null, byArray, null)) {
            return byArray[0] & 0xFF;
        }
        return 255;
    }

    @Override
    Rectangle getBoundsInPixels() {
        if (OS.IsIconic(this.handle)) {
            return super.getBoundsInPixels();
        }
        RECT rECT = new RECT();
        OS.GetWindowRect(this.handle, rECT);
        int n = rECT.right - rECT.left;
        int n2 = rECT.bottom - rECT.top;
        return new Rectangle(rECT.left, rECT.top, n, n2);
    }

    ToolTip getCurrentToolTip() {
        ToolTip toolTip;
        if (this.toolTipHandle != 0L && (toolTip = this.getCurrentToolTip(this.toolTipHandle)) != null) {
            return toolTip;
        }
        if (this.balloonTipHandle != 0L && (toolTip = this.getCurrentToolTip(this.balloonTipHandle)) != null) {
            return toolTip;
        }
        if (this.menuItemToolTipHandle != 0L && (toolTip = this.getCurrentToolTip(this.menuItemToolTipHandle)) != null) {
            return toolTip;
        }
        return null;
    }

    ToolTip getCurrentToolTip(long l2) {
        if (l2 == 0L) {
            return null;
        }
        if (OS.SendMessage(l2, 1083, 0L, 0L) != 0L) {
            TOOLINFO tOOLINFO = new TOOLINFO();
            tOOLINFO.cbSize = TOOLINFO.sizeof;
            if (OS.SendMessage(l2, 1083, 0L, tOOLINFO) != 0L && (tOOLINFO.uFlags & 1) == 0) {
                return this.findToolTip((int)tOOLINFO.uId);
            }
        }
        return null;
    }

    public boolean getFullScreen() {
        this.checkWidget();
        return this.fullScreen;
    }

    public int getImeInputMode() {
        this.checkWidget();
        if (!OS.IsDBLocale) {
            return 0;
        }
        long l2 = OS.ImmGetContext(this.handle);
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        boolean bl = OS.ImmGetOpenStatus(l2);
        if (bl) {
            bl = OS.ImmGetConversionStatus(l2, nArray, nArray2);
        }
        OS.ImmReleaseContext(this.handle, l2);
        if (!bl) {
            return 0;
        }
        int n = 0;
        if ((nArray[0] & 0x10) != 0) {
            n |= 0x20;
        }
        if ((nArray[0] & 8) != 0) {
            n |= 2;
        }
        if ((nArray[0] & 2) != 0) {
            return n | 0x10;
        }
        if ((nArray[0] & 1) != 0) {
            return n | 8;
        }
        return n | 4;
    }

    @Override
    Point getLocationInPixels() {
        if (OS.IsIconic(this.handle)) {
            return super.getLocationInPixels();
        }
        RECT rECT = new RECT();
        OS.GetWindowRect(this.handle, rECT);
        return new Point(rECT.left, rECT.top);
    }

    @Override
    public boolean getMaximized() {
        this.checkWidget();
        return !this.fullScreen && super.getMaximized();
    }

    public Point getMaximumSize() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getMaximumSizeInPixels());
    }

    Point getMaximumSizeInPixels() {
        int n = Math.min(Integer.MAX_VALUE, this.maxWidth);
        int n2 = 1248;
        if ((this.style & 8) == 0 && (this.style & 0x4E0) != 0) {
            n = Math.min(n, OS.GetSystemMetrics(59));
        }
        int n3 = Math.min(Integer.MAX_VALUE, this.maxHeight);
        if ((this.style & 8) == 0 && (this.style & 0x4E0) != 0) {
            if ((this.style & 0x10) != 0) {
                n3 = Math.min(n3, OS.GetSystemMetrics(60));
            } else {
                RECT rECT = new RECT();
                int n4 = OS.GetWindowLong(this.handle, -16);
                int n5 = OS.GetWindowLong(this.handle, -20);
                OS.AdjustWindowRectEx(rECT, n4, false, n5);
                n3 = Math.min(n3, rECT.bottom - rECT.top);
            }
        }
        return new Point(n, n3);
    }

    public Point getMinimumSize() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getMinimumSizeInPixels());
    }

    Point getMinimumSizeInPixels() {
        int n = Math.max(0, this.minWidth);
        int n2 = 1248;
        if ((this.style & 8) == 0 && (this.style & 0x4E0) != 0) {
            n = Math.max(n, OS.GetSystemMetrics(34));
        }
        int n3 = Math.max(0, this.minHeight);
        if ((this.style & 8) == 0 && (this.style & 0x4E0) != 0) {
            if ((this.style & 0x10) != 0) {
                n3 = Math.max(n3, OS.GetSystemMetrics(35));
            } else {
                RECT rECT = new RECT();
                int n4 = OS.GetWindowLong(this.handle, -16);
                int n5 = OS.GetWindowLong(this.handle, -20);
                OS.AdjustWindowRectEx(rECT, n4, false, n5);
                n3 = Math.max(n3, rECT.bottom - rECT.top);
            }
        }
        return new Point(n, n3);
    }

    public boolean getModified() {
        this.checkWidget();
        return this.modified;
    }

    @Override
    public Region getRegion() {
        this.checkWidget();
        return this.region;
    }

    @Override
    public Shell getShell() {
        this.checkWidget();
        return this;
    }

    @Override
    Point getSizeInPixels() {
        if (OS.IsIconic(this.handle)) {
            return super.getSizeInPixels();
        }
        RECT rECT = new RECT();
        OS.GetWindowRect(this.handle, rECT);
        int n = rECT.right - rECT.left;
        int n2 = rECT.bottom - rECT.top;
        return new Point(n, n2);
    }

    public Shell[] getShells() {
        Shell[] shellArray;
        this.checkWidget();
        int n = 0;
        Shell[] shellArray2 = shellArray = this.display.getShells();
        for (Composite composite : shellArray) {
            while ((composite = composite.getParent()) != null && composite != this) {
            }
            if (composite != this) continue;
            ++n;
        }
        int n2 = 0;
        Shell[] shellArray3 = new Shell[n];
        Shell[] shellArray4 = shellArray2;
        int n3 = shellArray4.length;
        for (int i = 0; i < n3; ++i) {
            Composite composite;
            Shell shell = composite = shellArray4[i];
            while ((composite = composite.getParent()) != null && composite != this) {
            }
            if (composite != this) continue;
            shellArray3[n2++] = shell;
        }
        return shellArray3;
    }

    public ToolBar getToolBar() {
        this.checkWidget();
        return null;
    }

    @Override
    Composite findDeferredControl() {
        return this.layoutCount > 0 ? this : null;
    }

    public boolean isEnabled() {
        this.checkWidget();
        return this.getEnabled();
    }

    public boolean isVisible() {
        this.checkWidget();
        return this.getVisible();
    }

    long hwndMDIClient() {
        if (this.hwndMDIClient == 0L) {
            int n = 1174405121;
            this.hwndMDIClient = OS.CreateWindowEx(0, new TCHAR(0, "MDICLIENT", true), null, 1174405121, 0, 0, 0, 0, this.handle, 0L, OS.GetModuleHandle(null), new CREATESTRUCT());
        }
        return this.hwndMDIClient;
    }

    long menuItemToolTipHandle() {
        if (this.menuItemToolTipHandle == 0L) {
            this.createMenuItemToolTipHandle();
        }
        return this.menuItemToolTipHandle;
    }

    public void open() {
        Control control;
        this.checkWidget();
        STARTUPINFO sTARTUPINFO = Display.lpStartupInfo;
        if (sTARTUPINFO == null || (sTARTUPINFO.dwFlags & 1) == 0) {
            this.bringToTop();
            if (this.isDisposed()) {
                return;
            }
        }
        OS.SendMessage(this.handle, 295, 3L, 0L);
        this.setVisible(true);
        if (this.isDisposed()) {
            return;
        }
        MSG mSG = new MSG();
        int n = 0x400002;
        OS.PeekMessage(mSG, 0L, 0, 0, 0x400002);
        boolean bl = this.restoreFocus();
        if (!bl) {
            bl = this.traverseGroup(true);
        }
        if (bl && (control = this.display.getFocusControl()) instanceof Button && (control.style & 8) != 0) {
            bl = false;
        }
        if (!bl) {
            if (this.saveDefault != null && !this.saveDefault.isDisposed()) {
                this.saveDefault.setFocus();
            } else {
                this.setFocus();
            }
        }
    }

    @Override
    public boolean print(GC gC) {
        this.checkWidget();
        if (gC == null) {
            this.error(4);
        }
        if (gC.isDisposed()) {
            this.error(5);
        }
        return false;
    }

    @Override
    void register() {
        super.register();
        if (this.toolTipHandle != 0L) {
            this.display.addControl(this.toolTipHandle, this);
        }
        if (this.balloonTipHandle != 0L) {
            this.display.addControl(this.balloonTipHandle, this);
        }
        if (this.menuItemToolTipHandle != 0L) {
            this.display.addControl(this.menuItemToolTipHandle, this);
        }
    }

    void releaseBrushes() {
        if (this.brushes != null) {
            for (long l2 : this.brushes) {
                if (l2 == 0L) continue;
                OS.DeleteObject(l2);
            }
        }
        this.brushes = null;
    }

    @Override
    void releaseChildren(boolean bl) {
        for (Shell widget : this.getShells()) {
            if (widget == null || widget.isDisposed()) continue;
            widget.release(false);
        }
        if (this.toolTips != null) {
            for (Widget widget : this.toolTips) {
                if (widget == null || widget.isDisposed()) continue;
                widget.release(false);
            }
        }
        this.toolTips = null;
        super.releaseChildren(bl);
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.hwndMDIClient = 0L;
    }

    @Override
    void releaseParent() {
    }

    @Override
    void releaseWidget() {
        long l2;
        super.releaseWidget();
        this.releaseBrushes();
        this.activeMenu = null;
        this.display.clearModal(this);
        if (this.lpstrTip != 0L) {
            l2 = OS.GetProcessHeap();
            OS.HeapFree(l2, 0, this.lpstrTip);
        }
        this.lpstrTip = 0L;
        l2 = 0L;
        this.menuItemToolTipHandle = 0L;
        this.balloonTipHandle = 0L;
        this.toolTipHandle = 0L;
        this.lastActive = null;
        Object var3_2 = null;
        this.balloonTitle = var3_2;
        this.toolTitle = var3_2;
    }

    @Override
    void removeMenu(Menu menu) {
        super.removeMenu(menu);
        if (menu == this.activeMenu) {
            this.activeMenu = null;
        }
    }

    public void removeShellListener(ShellListener shellListener) {
        this.checkWidget();
        if (shellListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(21, shellListener);
        this.eventTable.unhook(19, shellListener);
        this.eventTable.unhook(20, shellListener);
        this.eventTable.unhook(26, shellListener);
        this.eventTable.unhook(27, shellListener);
    }

    @Override
    public void requestLayout() {
        this.layout(null, 4);
    }

    @Override
    void reskinChildren(int n) {
        for (Shell widget : this.getShells()) {
            if (widget == null) continue;
            widget.reskin(n);
        }
        if (this.toolTips != null) {
            for (Widget widget : this.toolTips) {
                if (widget == null) continue;
                widget.reskin(n);
            }
        }
        super.reskinChildren(n);
    }

    boolean sendKeyEvent(int n, int n2, long l2, long l3, Event event) {
        return this.isEnabled() && this.isActive() && super.sendKeyEvent(n, n2, l2, l3, event);
    }

    public void setActive() {
        this.checkWidget();
        if (!this.isVisible()) {
            return;
        }
        this.bringToTop();
    }

    void setActiveControl(Control control) {
        this.setActiveControl(control, 0);
    }

    void setActiveControl(Control control, int n) {
        int n2;
        if (control != null && control.isDisposed()) {
            control = null;
        }
        if (this.lastActive != null && this.lastActive.isDisposed()) {
            this.lastActive = null;
        }
        if (this.lastActive == control) {
            return;
        }
        Control[] controlArray = control == null ? new Control[]{} : control.getPath();
        Control[] controlArray2 = this.lastActive == null ? new Control[]{} : this.lastActive.getPath();
        this.lastActive = control;
        int n3 = Math.min(controlArray.length, controlArray2.length);
        for (n2 = 0; n2 < n3 && controlArray[n2] == controlArray2[n2]; ++n2) {
        }
        for (n3 = controlArray2.length - 1; n3 >= n2; --n3) {
            if (controlArray2[n3].isDisposed()) continue;
            controlArray2[n3].sendEvent(27);
        }
        for (n3 = controlArray.length - 1; n3 >= n2; --n3) {
            if (controlArray[n3].isDisposed()) continue;
            Event event = new Event();
            event.detail = n;
            controlArray[n3].sendEvent(26, event);
        }
    }

    public void setAlpha(int n) {
        this.checkWidget();
        int n2 = OS.GetWindowLong(this.handle, -20);
        if ((n &= 0xFF) == 255) {
            OS.SetWindowLong(this.handle, -20, n2 & 0xFFF7FFFF);
            int n3 = 1157;
            OS.RedrawWindow(this.handle, null, 0L, 1157);
        } else {
            OS.SetWindowLong(this.handle, -20, n2 | 0x80000);
            OS.SetLayeredWindowAttributes(this.handle, 0, (byte)n, 2);
        }
    }

    @Override
    void setBoundsInPixels(int n, int n2, int n3, int n4, int n5, boolean bl) {
        int n6;
        if (this.fullScreen) {
            this.setFullScreen(false);
        }
        if (((n6 = OS.GetWindowLong(this.handle, -20)) & 0x80000) != 0) {
            n5 &= 0xFFFFFFDF;
        }
        super.setBoundsInPixels(n, n2, n3, n4, n5, false);
    }

    @Override
    public void setEnabled(boolean bl) {
        this.checkWidget();
        if ((this.state & 8) == 0 == bl) {
            return;
        }
        super.setEnabled(bl);
        if (bl && this.handle == OS.GetActiveWindow() && !this.restoreFocus()) {
            this.traverseGroup(true);
        }
    }

    public void setFullScreen(boolean bl) {
        boolean bl2;
        this.checkWidget();
        if (this.fullScreen == bl) {
            return;
        }
        int n = bl ? 3 : 9;
        int n2 = OS.GetWindowLong(this.handle, -16);
        int n3 = 1248;
        if ((this.style & 0x4E0) != 0) {
            if (bl) {
                n2 &= 0xFF38FFFF;
            } else {
                n2 |= 0xC00000;
                if ((this.style & 0x400) != 0) {
                    n2 |= 0x10000;
                }
                if ((this.style & 0x80) != 0) {
                    n2 |= 0x20000;
                }
                if ((this.style & 0x10) != 0) {
                    n2 |= 0x40000;
                }
            }
        }
        if (bl) {
            this.wasMaximized = this.getMaximized();
        }
        if (!(bl2 = this.isVisible()) && !this.wasMaximized) {
            this.swFlags = n;
        }
        OS.SetWindowLong(this.handle, -16, n2);
        if (this.wasMaximized) {
            OS.ShowWindow(this.handle, 0);
            n = 3;
        }
        if (bl2) {
            OS.ShowWindow(this.handle, n);
        }
        OS.UpdateWindow(this.handle);
        this.fullScreen = bl;
    }

    public void setImeInputMode(int n) {
        int[] nArray;
        int[] nArray2;
        this.checkWidget();
        if (!OS.IsDBLocale) {
            return;
        }
        boolean bl = n != 0;
        long l2 = OS.ImmGetContext(this.handle);
        OS.ImmSetOpenStatus(l2, bl);
        if (bl && OS.ImmGetConversionStatus(l2, nArray2 = new int[]{0}, nArray = new int[]{0})) {
            long l3;
            int n2;
            boolean bl2;
            int n3 = 0;
            int n4 = 3;
            if ((n & 0x10) != 0) {
                n3 = 3;
                n4 = 0;
            } else if ((n & 8) != 0) {
                n3 = 1;
                n4 = 2;
            }
            boolean bl3 = bl2 = (n & 2) != 0;
            if ((n & 8) != 0 && (n2 = OS.PRIMARYLANGID(OS.LOWORD(l3 = OS.GetKeyboardLayout(0)))) == 17) {
                bl2 = true;
            }
            if (bl2) {
                n3 |= 8;
            } else {
                n4 |= 8;
            }
            if ((n & 0x20) != 0) {
                n3 |= 0x10;
            } else {
                n4 |= 0x10;
            }
            int[] nArray3 = nArray2;
            boolean bl4 = false;
            nArray3[0] = nArray3[0] | n3;
            int[] nArray4 = nArray2;
            boolean bl5 = false;
            nArray4[0] = nArray4[0] & ~n4;
            OS.ImmSetConversionStatus(l2, nArray2[0], nArray[0]);
        }
        OS.ImmReleaseContext(this.handle, l2);
    }

    public void setMaximumSize(int n, int n2) {
        this.checkWidget();
        this.setMaximumSizeInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2));
    }

    public void setMaximumSize(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        this.setMaximumSizeInPixels(point.x, point.y);
    }

    void setMaximumSizeInPixels(int n, int n2) {
        int n3;
        int n4;
        Object object;
        int n5 = 0;
        int n6 = 0;
        int n7 = 1248;
        if ((this.style & 8) == 0 && (this.style & 0x4E0) != 0) {
            n5 = OS.GetSystemMetrics(59);
            if ((this.style & 0x10) != 0) {
                n6 = OS.GetSystemMetrics(60);
            } else {
                object = new RECT();
                n4 = OS.GetWindowLong(this.handle, -16);
                n3 = OS.GetWindowLong(this.handle, -20);
                OS.AdjustWindowRectEx((RECT)object, n4, false, n3);
                n6 = ((RECT)object).bottom - ((RECT)object).top;
            }
        }
        this.maxWidth = Math.min(n5, n);
        this.maxHeight = Math.min(n6, n2);
        object = this.getSizeInPixels();
        n4 = Math.min(((Point)object).x, this.maxWidth);
        n3 = Math.min(((Point)object).y, this.maxHeight);
        if (this.maxWidth >= n5) {
            this.maxWidth = -1;
        }
        if (this.maxHeight >= n6) {
            this.maxHeight = -1;
        }
        if (n4 != ((Point)object).x || n3 != ((Point)object).y) {
            this.setSizeInPixels(n4, n3);
        }
    }

    public void setMinimumSize(int n, int n2) {
        this.checkWidget();
        this.setMinimumSizeInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2));
    }

    void setMinimumSizeInPixels(int n, int n2) {
        int n3;
        int n4;
        Object object;
        int n5 = 0;
        int n6 = 0;
        int n7 = 1248;
        if ((this.style & 8) == 0 && (this.style & 0x4E0) != 0) {
            n5 = OS.GetSystemMetrics(34);
            if ((this.style & 0x10) != 0) {
                n6 = OS.GetSystemMetrics(35);
            } else {
                object = new RECT();
                n4 = OS.GetWindowLong(this.handle, -16);
                n3 = OS.GetWindowLong(this.handle, -20);
                OS.AdjustWindowRectEx((RECT)object, n4, false, n3);
                n6 = ((RECT)object).bottom - ((RECT)object).top;
            }
        }
        this.minWidth = Math.max(n5, n);
        this.minHeight = Math.max(n6, n2);
        object = this.getSizeInPixels();
        n4 = Math.max(((Point)object).x, this.minWidth);
        n3 = Math.max(((Point)object).y, this.minHeight);
        if (this.minWidth <= n5) {
            this.minWidth = -1;
        }
        if (this.minHeight <= n6) {
            this.minHeight = -1;
        }
        if (n4 != ((Point)object).x || n3 != ((Point)object).y) {
            this.setSizeInPixels(n4, n3);
        }
    }

    public void setMinimumSize(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        this.setMinimumSizeInPixels(point.x, point.y);
    }

    public void setModified(boolean bl) {
        this.checkWidget();
        this.modified = bl;
    }

    void setItemEnabled(int n, boolean bl) {
        long l2 = OS.GetSystemMenu(this.handle, false);
        if (l2 == 0L) {
            return;
        }
        int n2 = 0;
        if (!bl) {
            n2 = 3;
        }
        OS.EnableMenuItem(l2, n, 0 | n2);
    }

    @Override
    void setParent() {
    }

    @Override
    public void setRegion(Region region) {
        this.checkWidget();
        if ((this.style & 8) == 0) {
            return;
        }
        if (region != null) {
            Rectangle rectangle = region.getBounds();
            this.setSize(rectangle.x + rectangle.width, rectangle.y + rectangle.height);
        }
        super.setRegion(region);
    }

    void setToolTipText(long l2, String string) {
        TOOLINFO tOOLINFO = new TOOLINFO();
        tOOLINFO.cbSize = TOOLINFO.sizeof;
        tOOLINFO.hwnd = this.handle;
        tOOLINFO.uId = l2;
        long l3 = this.toolTipHandle();
        this.maybeEnableDarkSystemTheme(l3);
        if (string == null) {
            OS.SendMessage(l3, 1075, 0L, tOOLINFO);
        } else if (OS.SendMessage(l3, 1077, 0L, tOOLINFO) == 0L) {
            tOOLINFO.uFlags = 17;
            tOOLINFO.lpszText = -1L;
            OS.SendMessage(l3, 1074, 0L, tOOLINFO);
        } else if (OS.SendMessage(l3, 1083, 0L, tOOLINFO) != 0L && tOOLINFO.uId == l2) {
            OS.SendMessage(l3, 1053, 0L, 0L);
        }
    }

    void setToolTipText(NMTTDISPINFO nMTTDISPINFO, char[] cArray) {
        if (!this.hasCursor()) {
            return;
        }
        long l2 = OS.GetProcessHeap();
        if (this.lpstrTip != 0L) {
            OS.HeapFree(l2, 0, this.lpstrTip);
        }
        int n = cArray.length * 2;
        this.lpstrTip = OS.HeapAlloc(l2, 8, n);
        OS.MoveMemory(this.lpstrTip, cArray, n);
        nMTTDISPINFO.lpszText = this.lpstrTip;
    }

    void setToolTipTitle(long l2, String string, int n) {
        if (l2 != this.toolTipHandle && l2 != this.balloonTipHandle && l2 != this.menuItemToolTipHandle) {
            return;
        }
        if (l2 == this.toolTipHandle || l2 == this.menuItemToolTipHandle) {
            if ((string == this.toolTitle || this.toolTitle != null && this.toolTitle.equals(string)) && (long)n == this.toolIcon) {
                return;
            }
            this.toolTitle = string;
            this.toolIcon = n;
        } else if (l2 == this.balloonTipHandle) {
            if ((string == this.balloonTitle || this.balloonTitle != null && this.balloonTitle.equals(string)) && (long)n == this.toolIcon) {
                return;
            }
            this.balloonTitle = string;
            this.balloonIcon = n;
        }
        if (string != null) {
            if (string.length() > 99) {
                string = string.substring(0, 99);
            }
            TCHAR tCHAR = new TCHAR(this.getCodePage(), string, true);
            OS.SendMessage(l2, 1057, (long)n, tCHAR);
        } else {
            OS.SendMessage(l2, 1057, 0L, 0L);
        }
    }

    @Override
    public void setVisible(boolean bl) {
        long l2;
        int n;
        this.checkWidget();
        int n2 = 229376;
        if ((this.style & 0x38000) != 0) {
            if (bl) {
                long l3;
                Control control;
                this.display.setModalShell(this);
                if ((this.style & 0x30000) != 0) {
                    this.display.setModalDialog(null);
                }
                if ((control = this.display._getFocusControl()) != null && !control.isActive()) {
                    this.bringToTop();
                    if (this.isDisposed()) {
                        return;
                    }
                }
                if ((l3 = OS.GetActiveWindow()) == 0L && this.parent != null) {
                    l3 = this.parent.handle;
                }
                if (l3 != 0L) {
                    OS.SendMessage(l3, 31, 0L, 0L);
                }
                OS.ReleaseCapture();
            } else {
                this.display.clearModal(this);
            }
        } else {
            this.updateModal();
        }
        if (this.showWithParent && !bl) {
            OS.ShowOwnedPopups(this.handle, false);
        }
        if (!bl) {
            this.fixActiveShell();
        }
        if (bl && this.center && !this.moved) {
            this.center();
            if (this.isDisposed()) {
                return;
            }
        }
        super.setVisible(bl);
        if (this.isDisposed()) {
            return;
        }
        if (this.showWithParent != bl && (this.showWithParent = bl)) {
            OS.ShowOwnedPopups(this.handle, true);
        }
        if (bl && this.parent != null && (this.parent.state & 0x4000) != 0 && ((n = OS.GetWindowLong(l2 = this.parent.handle, -20)) & 0x80) != 0) {
            OS.SetWindowLong(l2, -20, n & 0xFFFFFF7F);
            OS.ShowWindow(l2, 0);
            OS.ShowWindow(l2, 9);
        }
    }

    @Override
    void subclass() {
        super.subclass();
    }

    long toolTipHandle() {
        if (this.toolTipHandle == 0L) {
            this.createToolTipHandle();
        }
        return this.toolTipHandle;
    }

    @Override
    boolean translateAccelerator(MSG mSG) {
        return !(!this.isEnabled() || !this.isActive() || this.menuBar != null && !this.menuBar.isEnabled() || !this.translateMDIAccelerator(mSG) && !this.translateMenuAccelerator(mSG));
    }

    @Override
    boolean traverseEscape() {
        if (this.parent == null) {
            return false;
        }
        if (!this.isVisible() || !this.isEnabled()) {
            return false;
        }
        this.close();
        return true;
    }

    @Override
    void unsubclass() {
        super.unsubclass();
    }

    void updateModal() {
        if (Display.TrimEnabled) {
            this.setItemEnabled(61536, this.isActive());
        } else {
            OS.EnableWindow(this.handle, this.isActive());
        }
    }

    @Override
    CREATESTRUCT widgetCreateStruct() {
        return null;
    }

    @Override
    long widgetParent() {
        if (this.handle != 0L) {
            return this.handle;
        }
        return this.parent != null ? this.parent.handle : 0L;
    }

    @Override
    int widgetExtStyle() {
        int n = super.widgetExtStyle() & 0xFFFFFFBF;
        if ((this.style & 4) != 0) {
            n |= 0x80;
        }
        if (this.parent == null && (this.style & 0x4000) != 0) {
            int n2 = 1248;
            if ((this.style & 8) != 0 || (this.style & 0x4E0) == 0) {
                n |= 0x80;
            }
        }
        if ((this.style & 0x4000) != 0) {
            n |= 8;
        }
        return n;
    }

    @Override
    TCHAR windowClass() {
        if ((this.style & 4) != 0) {
            int n = 3312;
            if ((this.style & 0xCF0) == 0) {
                return this.display.windowShadowClass;
            }
        }
        return this.parent != null ? DialogClass : super.windowClass();
    }

    @Override
    long windowProc() {
        if (this.windowProc != 0L) {
            return this.windowProc;
        }
        if ((this.style & 4) != 0) {
            int n = 3312;
            if ((this.style & 0xCF0) == 0) {
                return super.windowProc();
            }
        }
        return this.parent != null ? DialogProc : super.windowProc();
    }

    Rectangle getClientRectInWindow() {
        RECT rECT = new RECT();
        OS.GetWindowRect(this.handle, rECT);
        POINT pOINT = new POINT();
        OS.ClientToScreen(this.handle, pOINT);
        POINT pOINT2 = pOINT;
        pOINT2.x -= rECT.left;
        POINT pOINT3 = pOINT;
        pOINT3.y -= rECT.top;
        RECT rECT2 = new RECT();
        OS.GetClientRect(this.handle, rECT2);
        return new Rectangle(pOINT.x + rECT2.left, pOINT.y + rECT2.top, rECT2.right - rECT2.left, rECT2.bottom - rECT2.top);
    }

    void overpaintMenuBorder() {
        if (this.menuBar == null || this.display.menuBarBorderPen == 0L) {
            return;
        }
        Rectangle rectangle = this.getClientRectInWindow();
        long l2 = OS.GetWindowDC(this.handle);
        long l3 = OS.SelectObject(l2, this.display.menuBarBorderPen);
        OS.MoveToEx(l2, rectangle.x, rectangle.y - 1, 0L);
        OS.LineTo(l2, rectangle.x + rectangle.width, rectangle.y - 1);
        OS.SelectObject(l2, l3);
        OS.ReleaseDC(this.handle, l2);
    }

    @Override
    long windowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        if ((this.style & 0x800000) != 0 && n == 161 && l3 == 2L) {
            return 0L;
        }
        if (l2 == this.toolTipHandle || l2 == this.balloonTipHandle || l2 == this.menuItemToolTipHandle) {
            switch (n) {
                case 275: {
                    ToolTip toolTip;
                    if (l3 != 100L || (toolTip = this.getCurrentToolTip(l2)) == null || !toolTip.autoHide) break;
                    toolTip.setVisible(false);
                    break;
                }
                case 513: {
                    ToolTip toolTip = this.getCurrentToolTip(l2);
                    if (toolTip == null) break;
                    toolTip.setVisible(false);
                    toolTip.sendSelectionEvent(13);
                    break;
                }
            }
            return this.callWindowProc(l2, n, l3, l4);
        }
        if (l2 == this.handle && n == Display.TASKBARBUTTONCREATED && this.display.taskBar != null) {
            for (TaskItem taskItem : this.display.taskBar.items) {
                if (taskItem == null || taskItem.shell != this) continue;
                taskItem.recreate();
                break;
            }
        }
        switch (n) {
            case 133: 
            case 134: {
                long l5 = super.windowProc(l2, n, l3, l4);
                this.overpaintMenuBorder();
                return l5;
            }
        }
        return super.windowProc(l2, n, l3, l4);
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle();
        if (this.handle != 0L) {
            return n | 0x40000000;
        }
        return (n &= 0xBFFFFFFF) | 0 | 0xC00000;
    }

    @Override
    LRESULT WM_ACTIVATE(long l2, long l3) {
        ToolTip toolTip;
        LRESULT lRESULT = super.WM_ACTIVATE(l2, l3);
        if (OS.LOWORD(l2) == 0 && (l3 == 0L || l3 != this.toolTipHandle && l3 != this.balloonTipHandle && l3 != this.menuItemToolTipHandle) && (toolTip = this.getCurrentToolTip()) != null) {
            toolTip.setVisible(false);
        }
        return this.parent != null ? LRESULT.ZERO : lRESULT;
    }

    @Override
    LRESULT WM_DESTROY(long l2, long l3) {
        LRESULT lRESULT = super.WM_DESTROY(l2, l3);
        int n = OS.GetWindowLong(this.handle, -16);
        if ((n & 0x40000000) != 0) {
            this.releaseParent();
            this.release(false);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_ERASEBKGND(long l2, long l3) {
        LRESULT lRESULT = super.WM_ERASEBKGND(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (OS.WIN32_VERSION == OS.VERSION(6, 0)) {
            this.drawBackground(l2);
            return LRESULT.ONE;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_ENTERIDLE(long l2, long l3) {
        LRESULT lRESULT = super.WM_ENTERIDLE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        Display display = this.display;
        if (display.runAsyncMessages(false)) {
            display.wakeThread();
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_GETMINMAXINFO(long l2, long l3) {
        LRESULT lRESULT = super.WM_GETMINMAXINFO(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (this.minWidth != -1 || this.minHeight != -1 || this.maxWidth != -1 || this.maxHeight != -1) {
            MINMAXINFO mINMAXINFO = new MINMAXINFO();
            OS.MoveMemory(mINMAXINFO, l3, MINMAXINFO.sizeof);
            if (this.minWidth != -1) {
                mINMAXINFO.ptMinTrackSize_x = this.minWidth;
            }
            if (this.minHeight != -1) {
                mINMAXINFO.ptMinTrackSize_y = this.minHeight;
            }
            if (this.maxWidth != -1) {
                mINMAXINFO.ptMaxTrackSize_x = this.maxWidth;
            }
            if (this.maxHeight != -1) {
                mINMAXINFO.ptMaxTrackSize_y = this.maxHeight;
            }
            OS.MoveMemory(l3, mINMAXINFO, MINMAXINFO.sizeof);
            return LRESULT.ZERO;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_MOUSEACTIVATE(long l2, long l3) {
        long l4;
        Object object;
        LRESULT lRESULT = super.WM_MOUSEACTIVATE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        short s = (short)OS.LOWORD(l3);
        switch (s) {
            case -2: 
            case -1: 
            case 0: {
                break;
            }
            default: {
                Decorations decorations;
                object = this.display._getFocusControl();
                if (object == null || (decorations = ((Control)object).menuShell()).getShell() != this || decorations == this) break;
                this.display.ignoreRestoreFocus = true;
                this.display.lastHittest = s;
                this.display.lastHittestControl = null;
                if (s == 5 || s == 3) {
                    this.display.lastHittestControl = object;
                    return null;
                }
                return new LRESULT(3L);
            }
        }
        if (s == 5) {
            return null;
        }
        object = new POINT();
        if (!OS.GetCursorPos((POINT)object)) {
            int n = OS.GetMessagePos();
            OS.POINTSTOPOINT((POINT)object, n);
        }
        if ((l4 = OS.WindowFromPoint((POINT)object)) == 0L) {
            return null;
        }
        Control control = this.display.findControl(l4);
        if (control != null && (control.state & 2) != 0 && (control.style & 0x80000) != 0) {
            int n = 540672;
            if ((this.style & 0x84000) == 540672 && (s == 18 || s == 1)) {
                return new LRESULT(3L);
            }
        }
        long l5 = this.callWindowProc(this.handle, 33, l2, l3);
        this.setActiveControl(control, 3);
        return new LRESULT(l5);
    }

    @Override
    LRESULT WM_MOVE(long l2, long l3) {
        LRESULT lRESULT = super.WM_MOVE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        ToolTip toolTip = this.getCurrentToolTip();
        if (toolTip != null) {
            toolTip.setVisible(false);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_NCHITTEST(long l2, long l3) {
        if (!OS.IsWindowEnabled(this.handle)) {
            return null;
        }
        if (!this.isEnabled() || !this.isActive()) {
            if (!Display.TrimEnabled) {
                return new LRESULT(0L);
            }
            long l4 = this.callWindowProc(this.handle, 132, l2, l3);
            if (l4 == 1L || l4 == 5L) {
                l4 = 18L;
            }
            return new LRESULT(l4);
        }
        if (this.menuBar != null && !this.menuBar.getEnabled()) {
            long l5 = this.callWindowProc(this.handle, 132, l2, l3);
            if (l5 == 5L) {
                l5 = 18L;
            }
            return new LRESULT(l5);
        }
        return null;
    }

    @Override
    LRESULT WM_NCLBUTTONDOWN(long l2, long l3) {
        LRESULT lRESULT = super.WM_NCLBUTTONDOWN(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (!this.display.ignoreRestoreFocus) {
            return lRESULT;
        }
        Display display = this.display;
        display.lockActiveWindow = true;
        long l4 = this.callWindowProc(this.handle, 161, l2, l3);
        display.lockActiveWindow = false;
        Control control = display.lastHittestControl;
        if (control != null && !control.isDisposed()) {
            control.setFocus();
        }
        display.lastHittestControl = null;
        display.ignoreRestoreFocus = false;
        return new LRESULT(l4);
    }

    @Override
    LRESULT WM_SETCURSOR(long l2, long l3) {
        Control control;
        short s;
        int n = OS.HIWORD(l3);
        if (n == 513) {
            long l4;
            long l5;
            Shell shell;
            if (!Display.TrimEnabled && (shell = this.display.getModalShell()) != null && !this.isActive() && OS.IsWindowEnabled(l5 = shell.handle)) {
                OS.SetActiveWindow(l5);
            }
            if (!OS.IsWindowEnabled(this.handle) && (l4 = OS.GetLastActivePopup(this.handle)) != 0L && l4 != this.handle && this.display.getControl(l4) == null && OS.IsWindowEnabled(l4)) {
                OS.SetActiveWindow(l4);
            }
        }
        if ((s = (short)OS.LOWORD(l3)) == -2 && this == false && (control = this.display.getControl(l2)) == this && this.cursor != null) {
            POINT pOINT = new POINT();
            int n2 = OS.GetMessagePos();
            OS.POINTSTOPOINT(pOINT, n2);
            OS.ScreenToClient(this.handle, pOINT);
            RECT rECT = new RECT();
            OS.GetClientRect(this.handle, rECT);
            if (OS.PtInRect(rECT, pOINT)) {
                OS.SetCursor(this.cursor.handle);
                switch (n) {
                    case 513: 
                    case 516: 
                    case 519: 
                    case 523: {
                        OS.MessageBeep(0);
                    }
                }
                return LRESULT.ONE;
            }
        }
        return super.WM_SETCURSOR(l2, l3);
    }

    @Override
    LRESULT WM_SHOWWINDOW(long l2, long l3) {
        LRESULT lRESULT = super.WM_SHOWWINDOW(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (l3 == 3L) {
            Composite composite = this;
            while (composite != null) {
                Shell shell = ((Control)composite).getShell();
                if (!shell.showWithParent) {
                    return LRESULT.ZERO;
                }
                composite = composite.parent;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_WINDOWPOSCHANGING(long l2, long l3) {
        LRESULT lRESULT = super.WM_WINDOWPOSCHANGING(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        WINDOWPOS wINDOWPOS = new WINDOWPOS();
        OS.MoveMemory(wINDOWPOS, l3, WINDOWPOS.sizeof);
        if ((wINDOWPOS.flags & 1) == 0) {
            wINDOWPOS.cx = Math.max(wINDOWPOS.cx, this.minWidth);
            int n = 1248;
            if ((this.style & 8) == 0 && (this.style & 0x4E0) != 0) {
                wINDOWPOS.cx = Math.max(wINDOWPOS.cx, OS.GetSystemMetrics(34));
            }
            wINDOWPOS.cy = Math.max(wINDOWPOS.cy, this.minHeight);
            if ((this.style & 8) == 0 && (this.style & 0x4E0) != 0) {
                if ((this.style & 0x10) != 0) {
                    wINDOWPOS.cy = Math.max(wINDOWPOS.cy, OS.GetSystemMetrics(35));
                } else {
                    RECT rECT = new RECT();
                    int n2 = OS.GetWindowLong(this.handle, -16);
                    int n3 = OS.GetWindowLong(this.handle, -20);
                    OS.AdjustWindowRectEx(rECT, n2, false, n3);
                    wINDOWPOS.cy = Math.max(wINDOWPOS.cy, rECT.bottom - rECT.top);
                }
            }
            OS.MoveMemory(l3, wINDOWPOS, WINDOWPOS.sizeof);
        }
        return lRESULT;
    }

    static {
        DialogClass = new TCHAR(0, "#32770", true);
        SYSTEM_COLORS = new int[]{15, 5, 18, 8, 13, 0};
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, DialogClass, wNDCLASS);
        DialogProc = wNDCLASS.lpfnWndProc;
    }
}

