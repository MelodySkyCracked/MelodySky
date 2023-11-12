/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import java.util.Objects;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.GestureListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.win32.CREATESTRUCT;
import org.eclipse.swt.internal.win32.DRAWITEMSTRUCT;
import org.eclipse.swt.internal.win32.FLICK_DATA;
import org.eclipse.swt.internal.win32.FLICK_POINT;
import org.eclipse.swt.internal.win32.GESTURECONFIG;
import org.eclipse.swt.internal.win32.HELPINFO;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MEASUREITEMSTRUCT;
import org.eclipse.swt.internal.win32.MENUITEMINFO;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TOUCHINPUT;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Touch;
import org.eclipse.swt.widgets.TouchSource;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public abstract class Control
extends Widget
implements Drawable {
    public long handle;
    Composite parent;
    Cursor cursor;
    Menu menu;
    Menu activeMenu;
    String toolTipText;
    Object layoutData;
    Accessible accessible;
    Image backgroundImage;
    Region region;
    Font font;
    int drawCount;
    int foreground;
    int background;
    int backgroundAlpha = 255;

    Control() {
    }

    public Control(Composite composite, int n) {
        super(composite, n);
        this.parent = composite;
        this.createWidget();
    }

    public void addControlListener(ControlListener controlListener) {
        this.checkWidget();
        if (controlListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(controlListener);
        this.addListener(11, typedListener);
        this.addListener(10, typedListener);
    }

    public void addDragDetectListener(DragDetectListener dragDetectListener) {
        this.checkWidget();
        if (dragDetectListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(dragDetectListener);
        this.addListener(29, typedListener);
    }

    public void addFocusListener(FocusListener focusListener) {
        this.checkWidget();
        if (focusListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(focusListener);
        this.addListener(15, typedListener);
        this.addListener(16, typedListener);
    }

    public void addGestureListener(GestureListener gestureListener) {
        this.checkWidget();
        if (gestureListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(gestureListener);
        this.addListener(48, typedListener);
    }

    public void addHelpListener(HelpListener helpListener) {
        this.checkWidget();
        if (helpListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(helpListener);
        this.addListener(28, typedListener);
    }

    public void addKeyListener(KeyListener keyListener) {
        this.checkWidget();
        if (keyListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(keyListener);
        this.addListener(2, typedListener);
        this.addListener(1, typedListener);
    }

    public void addMenuDetectListener(MenuDetectListener menuDetectListener) {
        this.checkWidget();
        if (menuDetectListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(menuDetectListener);
        this.addListener(35, typedListener);
    }

    public void addMouseListener(MouseListener mouseListener) {
        this.checkWidget();
        if (mouseListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(mouseListener);
        this.addListener(3, typedListener);
        this.addListener(4, typedListener);
        this.addListener(8, typedListener);
    }

    public void addMouseTrackListener(MouseTrackListener mouseTrackListener) {
        this.checkWidget();
        if (mouseTrackListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(mouseTrackListener);
        this.addListener(6, typedListener);
        this.addListener(7, typedListener);
        this.addListener(32, typedListener);
    }

    public void addMouseMoveListener(MouseMoveListener mouseMoveListener) {
        this.checkWidget();
        if (mouseMoveListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(mouseMoveListener);
        this.addListener(5, typedListener);
    }

    public void addMouseWheelListener(MouseWheelListener mouseWheelListener) {
        this.checkWidget();
        if (mouseWheelListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(mouseWheelListener);
        this.addListener(37, typedListener);
    }

    public void addPaintListener(PaintListener paintListener) {
        this.checkWidget();
        if (paintListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(paintListener);
        this.addListener(9, typedListener);
    }

    public void addTouchListener(TouchListener touchListener) {
        this.checkWidget();
        if (touchListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(touchListener);
        this.addListener(47, typedListener);
    }

    public void addTraverseListener(TraverseListener traverseListener) {
        this.checkWidget();
        if (traverseListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(traverseListener);
        this.addListener(31, typedListener);
    }

    int binarySearch(int[] nArray, int n, int n2, int n3) {
        int n4 = n;
        int n5 = n2 - 1;
        while (n4 <= n5) {
            int n6 = n4 + n5 >>> 1;
            if (nArray[n6] == n3) {
                return n6;
            }
            if (nArray[n6] < n3) {
                n4 = n6 + 1;
                continue;
            }
            n5 = n6 - 1;
        }
        return -n4 - 1;
    }

    long borderHandle() {
        return this.handle;
    }

    void checkBackground() {
        Shell shell = this.getShell();
        if (this == shell) {
            return;
        }
        this.state &= 0xFFFFFBFF;
        Composite composite = this.parent;
        while (true) {
            int n;
            if ((n = composite.backgroundMode) != 0 || this.backgroundAlpha == 0) {
                block6: {
                    if (n == 1 || this.backgroundAlpha == 0) {
                        Control control = this;
                        while ((control.state & 0x100) != 0) {
                            control = control.parent;
                            if (control != composite) continue;
                            break block6;
                        }
                        return;
                    }
                }
                this.state |= 0x400;
                return;
            }
            if (composite == shell) {
                return;
            }
            composite = composite.parent;
        }
    }

    void checkBorder() {
        if (this.getBorderWidthInPixels() == 0) {
            this.style &= 0xFFFFF7FF;
        }
    }

    void checkBuffered() {
        this.style &= 0xDFFFFFFF;
    }

    void checkComposited() {
    }

    void checkMirrored() {
        int n;
        if ((this.style & 0x4000000) != 0 && ((n = OS.GetWindowLong(this.handle, -20)) & 0x400000) != 0) {
            this.style |= 0x8000000;
        }
    }

    public Point computeSize(int n, int n2) {
        return this.computeSize(n, n2, true);
    }

    public Point computeSize(int n, int n2, boolean bl) {
        this.checkWidget();
        n = n != -1 ? DPIUtil.autoScaleUp(n) : n;
        n2 = n2 != -1 ? DPIUtil.autoScaleUp(n2) : n2;
        return DPIUtil.autoScaleDown(this.computeSizeInPixels(n, n2, bl));
    }

    Point computeSizeInPixels(int n, int n2, boolean bl) {
        int n3 = 64;
        int n4 = 64;
        if (n != -1) {
            n3 = n;
        }
        if (n2 != -1) {
            n4 = n2;
        }
        int n5 = this.getBorderWidthInPixels();
        return new Point(n3 += n5 * 2, n4 += n5 * 2);
    }

    Widget computeTabGroup() {
        if (this != null) {
            return this;
        }
        return this.parent.computeTabGroup();
    }

    Control computeTabRoot() {
        Control[] controlArray = this.parent._getTabList();
        if (controlArray != null) {
            int n;
            for (n = 0; n < controlArray.length && controlArray[n] != this; ++n) {
            }
            if (n == controlArray.length && this != null) {
                return this;
            }
        }
        return this.parent.computeTabRoot();
    }

    Widget[] computeTabList() {
        if (this != null && this == false && this.getEnabled()) {
            return new Widget[]{this};
        }
        return new Widget[0];
    }

    void createHandle() {
        int n;
        long l2 = this.widgetParent();
        this.handle = OS.CreateWindowEx(this.widgetExtStyle(), this.windowClass(), null, this.widgetStyle(), Integer.MIN_VALUE, 0, Integer.MIN_VALUE, 0, l2, 0L, OS.GetModuleHandle(null), this.widgetCreateStruct());
        if (this.handle == 0L) {
            this.error(2);
        }
        if (((n = OS.GetWindowLong(this.handle, -16)) & 0x40000000) != 0) {
            OS.SetWindowLongPtr(this.handle, -12, this.handle);
        }
    }

    void checkGesture() {
        int n;
        if (OS.WIN32_VERSION >= OS.VERSION(6, 1) && ((n = OS.GetSystemMetrics(94)) & 0xC0) != 0) {
            GESTURECONFIG gESTURECONFIG = new GESTURECONFIG();
            gESTURECONFIG.dwID = 5;
            gESTURECONFIG.dwWant = 1;
            gESTURECONFIG.dwBlock = 0;
            OS.SetGestureConfig(this.handle, 0, 1, gESTURECONFIG, GESTURECONFIG.sizeof);
        }
    }

    void createWidget() {
        this.state |= 0x8000;
        int n = -1;
        this.background = -1;
        this.foreground = -1;
        this.checkOrientation(this.parent);
        this.createHandle();
        this.checkBackground();
        this.checkBuffered();
        this.checkComposited();
        this.register();
        this.subclass();
        this.setDefaultFont();
        this.checkMirrored();
        this.checkBorder();
        this.checkGesture();
        if ((this.state & 0x400) != 0) {
            this.setBackground();
        }
    }

    int defaultBackground() {
        return OS.GetSysColor(15);
    }

    long defaultFont() {
        return this.display.getSystemFont().handle;
    }

    int defaultForeground() {
        return OS.GetSysColor(8);
    }

    void deregister() {
        this.display.removeControl(this.handle);
    }

    @Override
    void destroyWidget() {
        long l2 = this.topHandle();
        this.releaseHandle();
        if (l2 != 0L) {
            OS.DestroyWindow(l2);
        }
    }

    public boolean dragDetect(Event event) {
        this.checkWidget();
        if (event == null) {
            this.error(4);
        }
        Point point = event.getLocationInPixels();
        return this.dragDetect(event.button, event.count, event.stateMask, point.x, point.y);
    }

    public boolean dragDetect(MouseEvent mouseEvent) {
        this.checkWidget();
        if (mouseEvent == null) {
            this.error(4);
        }
        return this.dragDetect(mouseEvent.button, mouseEvent.count, mouseEvent.stateMask, DPIUtil.autoScaleUp(mouseEvent.x), DPIUtil.autoScaleUp(mouseEvent.y));
    }

    boolean dragDetect(int n, int n2, int n3, int n4, int n5) {
        if (n != 1 || n2 != 1) {
            return false;
        }
        boolean bl = this.dragDetect(this.handle, n4, n5, false, null, null);
        if (OS.GetKeyState(1) < 0 && OS.GetCapture() != this.handle) {
            OS.SetCapture(this.handle);
        }
        if (!bl) {
            if (n == 1 && OS.GetKeyState(27) >= 0) {
                int n6 = 0;
                if ((n3 & 0x40000) != 0) {
                    n6 |= 8;
                }
                if ((n3 & 0x20000) != 0) {
                    n6 |= 4;
                }
                if ((n3 & 0x10000) != 0) {
                    n6 |= 0x20;
                }
                if ((n3 & 0x80000) != 0) {
                    n6 |= 1;
                }
                if ((n3 & 0x100000) != 0) {
                    n6 |= 0x10;
                }
                if ((n3 & 0x200000) != 0) {
                    n6 |= 2;
                }
                if ((n3 & 0x800000) != 0) {
                    n6 |= 0x20;
                }
                if ((n3 & 0x2000000) != 0) {
                    n6 |= 0x40;
                }
                long l2 = OS.MAKELPARAM(n4, n5);
                OS.SendMessage(this.handle, 514, (long)n6, l2);
            }
            return false;
        }
        return this.sendDragEvent(n, n3, n4, n5);
    }

    void drawBackground(long l2) {
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        this.drawBackground(l2, rECT);
    }

    void drawBackground(long l2, RECT rECT) {
        this.drawBackground(l2, rECT, -1, 0, 0);
    }

    void drawBackground(long l2, RECT rECT, int n, int n2, int n3) {
        Control control = this.findBackgroundControl();
        if (control != null) {
            if (control.backgroundImage != null) {
                this.fillImageBackground(l2, control, rECT, n2, n3);
                return;
            }
            n = control.getBackgroundPixel();
        }
        if (n == -1 && (this.state & 0x100) != 0 && OS.IsAppThemed() && (control = this.findThemeControl()) != null) {
            this.fillThemeBackground(l2, control, rECT);
            return;
        }
        if (n == -1) {
            n = this.getBackgroundPixel();
        }
        this.fillBackground(l2, n, rECT);
    }

    void drawImageBackground(long l2, long l3, long l4, RECT rECT, int n, int n2) {
        RECT rECT2 = new RECT();
        OS.GetClientRect(l3, rECT2);
        OS.MapWindowPoints(l3, this.handle, rECT2, 2);
        long l5 = this.findBrush(l4, 3);
        POINT pOINT = new POINT();
        OS.GetWindowOrgEx(l2, pOINT);
        OS.SetBrushOrgEx(l2, -rECT2.left - pOINT.x - n, -rECT2.top - pOINT.y - n2, pOINT);
        long l6 = OS.SelectObject(l2, l5);
        OS.PatBlt(l2, rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top, 15728673);
        OS.SetBrushOrgEx(l2, pOINT.x, pOINT.y, null);
        OS.SelectObject(l2, l6);
    }

    void drawThemeBackground(long l2, long l3, RECT rECT) {
    }

    void enableDrag(boolean bl) {
    }

    void maybeEnableDarkSystemTheme() {
        this.maybeEnableDarkSystemTheme(this.handle);
    }

    void enableWidget(boolean bl) {
        OS.EnableWindow(this.handle, bl);
    }

    void fillBackground(long l2, int n, RECT rECT) {
        if (rECT.left > rECT.right || rECT.top > rECT.bottom) {
            return;
        }
        OS.FillRect(l2, rECT, this.findBrush(n, 0));
    }

    void fillImageBackground(long l2, Control control, RECT rECT, int n, int n2) {
        Image image;
        if (rECT.left > rECT.right || rECT.top > rECT.bottom) {
            return;
        }
        if (control != null && (image = control.backgroundImage) != null) {
            control.drawImageBackground(l2, this.handle, image.handle, rECT, n, n2);
        }
    }

    void fillThemeBackground(long l2, Control control, RECT rECT) {
        if (rECT.left > rECT.right || rECT.top > rECT.bottom) {
            return;
        }
        if (control != null) {
            control.drawThemeBackground(l2, this.handle, rECT);
        }
    }

    Control findBackgroundControl() {
        if ((this.background != -1 || this.backgroundImage != null) && this.backgroundAlpha > 0) {
            return this;
        }
        return this.parent != null && (this.state & 0x400) != 0 ? this.parent.findBackgroundControl() : null;
    }

    long findBrush(long l2, int n) {
        return this.parent.findBrush(l2, n);
    }

    Cursor findCursor() {
        if (this.cursor != null) {
            return this.cursor;
        }
        return this.parent.findCursor();
    }

    Control findImageControl() {
        Control control = this.findBackgroundControl();
        return control != null && control.backgroundImage != null ? control : null;
    }

    Control findThemeControl() {
        return this.background == -1 && this.backgroundImage == null ? this.parent.findThemeControl() : null;
    }

    Menu[] findMenus(Control control) {
        if (this.menu != null && this != control) {
            return new Menu[]{this.menu};
        }
        return new Menu[0];
    }

    char findMnemonic(String string) {
        int n = 0;
        int n2 = string.length();
        while (true) {
            if (n < n2 && string.charAt(n) != '&') {
                ++n;
                continue;
            }
            if (++n >= n2) {
                return '\u0000';
            }
            if (string.charAt(n) != '&') {
                return string.charAt(n);
            }
            if (++n >= n2) break;
        }
        return '\u0000';
    }

    void fixChildren(Shell shell, Shell shell2, Decorations decorations, Decorations decorations2, Menu[] menuArray) {
        shell2.fixShell(shell, this);
        decorations2.fixDecorations(decorations, this, menuArray);
    }

    void fixFocus(Control control) {
        Shell shell = this.getShell();
        Control control2 = this;
        while (control2 != shell && (control2 = control2.parent) != null) {
            if (control2 != false) continue;
            return;
        }
        shell.setSavedFocus(control);
        OS.SetFocus(0L);
    }

    void forceResize() {
        if (this.parent == null) {
            return;
        }
        WINDOWPOS[] wINDOWPOSArray = this.parent.lpwp;
        if (wINDOWPOSArray == null) {
            return;
        }
        for (int i = 0; i < wINDOWPOSArray.length; ++i) {
            WINDOWPOS wINDOWPOS = wINDOWPOSArray[i];
            if (wINDOWPOS == null || wINDOWPOS.hwnd != this.handle) continue;
            OS.SetWindowPos(wINDOWPOS.hwnd, 0L, wINDOWPOS.x, wINDOWPOS.y, wINDOWPOS.cx, wINDOWPOS.cy, wINDOWPOS.flags);
            wINDOWPOSArray[i] = null;
            return;
        }
    }

    public Accessible getAccessible() {
        this.checkWidget();
        if (this.accessible == null) {
            this.accessible = this.new_Accessible(this);
        }
        return this.accessible;
    }

    public Color getBackground() {
        this.checkWidget();
        if (this.backgroundAlpha == 0) {
            Color color = Color.win32_new(this.display, this.background, 0);
            return color;
        }
        Control control = this.findBackgroundControl();
        if (control == null) {
            control = this;
        }
        return Color.win32_new(this.display, control.getBackgroundPixel(), this.backgroundAlpha);
    }

    public Image getBackgroundImage() {
        this.checkWidget();
        Control control = this.findBackgroundControl();
        if (control == null) {
            control = this;
        }
        return control.backgroundImage;
    }

    int getBackgroundPixel() {
        return this.background != -1 ? this.background : this.defaultBackground();
    }

    public int getBorderWidth() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getBorderWidthInPixels());
    }

    int getBorderWidthInPixels() {
        long l2 = this.borderHandle();
        int n = OS.GetWindowLong(l2, -20);
        if ((n & 0x200) != 0) {
            return OS.GetSystemMetrics(45);
        }
        if ((n & 0x20000) != 0) {
            return OS.GetSystemMetrics(5);
        }
        int n2 = OS.GetWindowLong(l2, -16);
        if ((n2 & 0x800000) == 0) {
            return 0;
        }
        if (this != null) {
            return OS.GetSystemMetrics(45);
        }
        return OS.GetSystemMetrics(5);
    }

    public Rectangle getBounds() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getBoundsInPixels());
    }

    Rectangle getBoundsInPixels() {
        this.forceResize();
        RECT rECT = new RECT();
        OS.GetWindowRect(this.topHandle(), rECT);
        long l2 = this.parent == null ? 0L : this.parent.handle;
        OS.MapWindowPoints(0L, l2, rECT, 2);
        int n = rECT.right - rECT.left;
        int n2 = rECT.bottom - rECT.top;
        return new Rectangle(rECT.left, rECT.top, n, n2);
    }

    int getCodePage() {
        return 0;
    }

    String getClipboardText() {
        String string = "";
        if (OS.OpenClipboard(0L)) {
            long l2 = OS.GetClipboardData(13);
            if (l2 != 0L) {
                int n = OS.GlobalSize(l2) / 2 * 2;
                long l3 = OS.GlobalLock(l2);
                if (l3 != 0L) {
                    TCHAR tCHAR = new TCHAR(0, n / 2);
                    OS.MoveMemory(tCHAR, l3, n);
                    string = tCHAR.toString(0, tCHAR.strlen());
                    OS.GlobalUnlock(l2);
                }
            }
            OS.CloseClipboard();
        }
        return string;
    }

    public Cursor getCursor() {
        this.checkWidget();
        return this.cursor;
    }

    public boolean getDragDetect() {
        this.checkWidget();
        return (this.state & 0x8000) != 0;
    }

    public boolean getEnabled() {
        this.checkWidget();
        return OS.IsWindowEnabled(this.handle);
    }

    public Font getFont() {
        this.checkWidget();
        if (this.font != null) {
            return this.font;
        }
        long l2 = OS.SendMessage(this.handle, 49, 0L, 0L);
        if (l2 == 0L) {
            l2 = this.defaultFont();
        }
        return Font.win32_new(this.display, l2);
    }

    public Color getForeground() {
        this.checkWidget();
        return Color.win32_new(this.display, this.getForegroundPixel());
    }

    int getForegroundPixel() {
        return this.foreground != -1 ? this.foreground : this.defaultForeground();
    }

    public Object getLayoutData() {
        this.checkWidget();
        return this.layoutData;
    }

    public Point getLocation() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getLocationInPixels());
    }

    Point getLocationInPixels() {
        this.forceResize();
        RECT rECT = new RECT();
        OS.GetWindowRect(this.topHandle(), rECT);
        long l2 = this.parent == null ? 0L : this.parent.handle;
        OS.MapWindowPoints(0L, l2, rECT, 2);
        return new Point(rECT.left, rECT.top);
    }

    @Override
    public Menu getMenu() {
        this.checkWidget();
        return this.menu;
    }

    public Monitor getMonitor() {
        this.checkWidget();
        long l2 = OS.MonitorFromWindow(this.handle, 2);
        return this.display.getMonitor(l2);
    }

    public int getOrientation() {
        this.checkWidget();
        return this.style & 0x6000000;
    }

    public Composite getParent() {
        this.checkWidget();
        return this.parent;
    }

    Control[] getPath() {
        int n = 0;
        Shell shell = this.getShell();
        Control control = this;
        while (control != shell) {
            ++n;
            control = control.parent;
        }
        control = this;
        Control[] controlArray = new Control[n];
        while (control != shell) {
            controlArray[--n] = control;
            control = control.parent;
        }
        return controlArray;
    }

    public Region getRegion() {
        this.checkWidget();
        return this.region;
    }

    public Shell getShell() {
        this.checkWidget();
        return this.parent.getShell();
    }

    public Point getSize() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getSizeInPixels());
    }

    Point getSizeInPixels() {
        this.forceResize();
        RECT rECT = new RECT();
        OS.GetWindowRect(this.topHandle(), rECT);
        int n = rECT.right - rECT.left;
        int n2 = rECT.bottom - rECT.top;
        return new Point(n, n2);
    }

    int getSlightlyDifferentColor(int n) {
        return this.getDifferentColor(n, 0.1);
    }

    int getDifferentColor(int n) {
        return this.getDifferentColor(n, 0.2);
    }

    int getDifferentColor(int n, double d) {
        int n2 = n & 0xFF;
        int n3 = (n & 0xFF00) >> 8;
        int n4 = (n & 0xFF0000) >> 16;
        n2 += (int)this.calcDiff(n2, d);
        n3 += (int)this.calcDiff(n3, d);
        n4 += (int)this.calcDiff(n4, d);
        return n2 & 0xFF | (n3 & 0xFF) << 8 | (n4 & 0xFF) << 16;
    }

    long calcDiff(int n, double d) {
        if (n > 127) {
            return Math.round((double)(n * -1) * d);
        }
        return Math.round((double)(255 - n) * d);
    }

    int getSlightlyDifferentBackgroundColor(int n) {
        int n2 = 8;
        int n3 = n & 0xFF;
        int n4 = (n & 0xFF00) >> 8;
        int n5 = (n & 0xFF0000) >> 16;
        n3 = n3 > 127 ? n3 - 8 : n3 + 8;
        n4 = n4 > 127 ? n4 - 8 : n4 + 8;
        n5 = n5 > 127 ? n5 - 8 : n5 + 8;
        return n3 & 0xFF | (n4 & 0xFF) << 8 | (n5 & 0xFF) << 16;
    }

    public int getTextDirection() {
        this.checkWidget();
        int n = 0x402000;
        int n2 = OS.GetWindowLong(this.handle, -20) & 0x402000;
        return n2 == 0 || n2 == 0x402000 ? 0x2000000 : 0x4000000;
    }

    public String getToolTipText() {
        this.checkWidget();
        return this.toolTipText;
    }

    public boolean getTouchEnabled() {
        this.checkWidget();
        return OS.IsTouchWindow(this.handle, null);
    }

    boolean hasCursor() {
        RECT rECT = new RECT();
        if (!OS.GetClientRect(this.handle, rECT)) {
            return false;
        }
        OS.MapWindowPoints(this.handle, 0L, rECT, 2);
        POINT pOINT = new POINT();
        return OS.GetCursorPos(pOINT) && OS.PtInRect(rECT, pOINT);
    }

    boolean hasCustomBackground() {
        return this.background != -1;
    }

    boolean hasCustomForeground() {
        return this.foreground != -1;
    }

    boolean hasFocus() {
        long l2 = OS.GetFocus();
        while (l2 != 0L) {
            if (l2 == this.handle) {
                return true;
            }
            if (this.display.getControl(l2) != null) {
                return false;
            }
            l2 = OS.GetParent(l2);
        }
        return false;
    }

    @Override
    public long internal_new_GC(GCData gCData) {
        this.checkWidget();
        long l2 = this.handle;
        if (gCData != null && gCData.hwnd != 0L) {
            l2 = gCData.hwnd;
        }
        if (gCData != null) {
            gCData.hwnd = l2;
        }
        long l3 = 0L;
        l3 = gCData == null || gCData.ps == null ? OS.GetDC(l2) : OS.BeginPaint(l2, gCData.ps);
        if (l3 == 0L) {
            this.error(2);
        }
        if (gCData != null) {
            int n;
            Control control;
            int n2;
            int n3 = 0x6000000;
            if ((gCData.style & 0x6000000) != 0) {
                gCData.layout = (gCData.style & 0x4000000) != 0 ? 1 : 0;
            } else {
                n2 = OS.GetLayout(l3);
                gCData.style = (n2 & 1) != 0 ? (gCData.style |= 0xC000000) : (gCData.style |= 0x2000000);
            }
            gCData.device = this.display;
            n2 = this.getForegroundPixel();
            if (n2 != OS.GetTextColor(l3)) {
                gCData.foreground = n2;
            }
            if ((control = this.findBackgroundControl()) == null) {
                control = this;
            }
            if ((n = control.getBackgroundPixel()) != OS.GetBkColor(l3)) {
                gCData.background = n;
            }
            gCData.font = this.font != null ? this.font : Font.win32_new(this.display, OS.SendMessage(l2, 49, 0L, 0L));
            gCData.uiState = (int)OS.SendMessage(l2, 297, 0L, 0L);
        }
        return l3;
    }

    @Override
    public void internal_dispose_GC(long l2, GCData gCData) {
        this.checkWidget();
        long l3 = this.handle;
        if (gCData != null && gCData.hwnd != 0L) {
            l3 = gCData.hwnd;
        }
        if (gCData == null || gCData.ps == null) {
            OS.ReleaseDC(l3, l2);
        } else {
            OS.EndPaint(l3, gCData.ps);
        }
    }

    boolean isFocusAncestor(Control control) {
        while (control != null && control != this && !(control instanceof Shell)) {
            control = control.parent;
        }
        return control == this;
    }

    public boolean isReparentable() {
        this.checkWidget();
        return true;
    }

    @Override
    void mapEvent(long l2, Event event) {
        if (l2 != this.handle) {
            POINT pOINT = new POINT();
            Point point = event.getLocationInPixels();
            pOINT.x = point.x;
            pOINT.y = point.y;
            OS.MapWindowPoints(l2, this.handle, pOINT, 1);
            event.setLocationInPixels(pOINT.x, pOINT.y);
        }
    }

    void markLayout(boolean bl, boolean bl2) {
    }

    Decorations menuShell() {
        return this.parent.menuShell();
    }

    boolean mnemonicHit(char c) {
        return false;
    }

    boolean mnemonicMatch(char c) {
        return false;
    }

    public void moveAbove(Control control) {
        this.checkWidget();
        long l2 = this.topHandle();
        long l3 = 0L;
        if (control != null) {
            if (control.isDisposed()) {
                this.error(5);
            }
            if (this.parent != control.parent) {
                return;
            }
            long l4 = control.topHandle();
            if (l4 == 0L || l4 == l2) {
                return;
            }
            l3 = OS.GetWindow(l4, 3);
            if (l3 == 0L || l3 == l4) {
                l3 = 0L;
            }
        }
        int n = 19;
        OS.SetWindowPos(l2, l3, 0, 0, 0, 0, 19);
    }

    public void moveBelow(Control control) {
        this.checkWidget();
        long l2 = this.topHandle();
        long l3 = 1L;
        if (control != null) {
            if (control.isDisposed()) {
                this.error(5);
            }
            if (this.parent != control.parent) {
                return;
            }
            l3 = control.topHandle();
        } else {
            Shell shell = this.getShell();
            if (this == shell && this.parent != null) {
                long l4;
                long l5 = l4 = this.parent.handle;
                l3 = OS.GetWindow(l4, 3);
                while (l3 != 0L && l3 != l4 && OS.GetWindow(l3, 4) != l5) {
                    l4 = l3;
                    l3 = OS.GetWindow(l4, 3);
                }
                if (l3 == l4) {
                    return;
                }
            }
        }
        if (l3 == 0L || l3 == l2) {
            return;
        }
        int n = 19;
        OS.SetWindowPos(l2, l3, 0, 0, 0, 0, 19);
    }

    Accessible new_Accessible(Control control) {
        return Accessible.internal_new_Accessible(this);
    }

    @Override
    GC new_GC(GCData gCData) {
        return GC.win32_new(this, gCData);
    }

    public void pack() {
        this.checkWidget();
        this.pack(true);
    }

    public void pack(boolean bl) {
        this.checkWidget();
        this.setSize(this.computeSize(-1, -1, bl));
    }

    public boolean print(GC gC) {
        this.checkWidget();
        if (gC == null) {
            this.error(4);
        }
        if (gC.isDisposed()) {
            this.error(5);
        }
        long l2 = this.topHandle();
        long l3 = gC.handle;
        int n = 0;
        long l4 = gC.getGCData().gdipGraphics;
        if (l4 != 0L) {
            long l5 = 0L;
            Gdip.Graphics_SetPixelOffsetMode(l4, 3);
            long l6 = Gdip.Region_new();
            if (l6 == 0L) {
                this.error(2);
            }
            Gdip.Graphics_GetClip(l4, l6);
            if (!Gdip.Region_IsInfinite(l6, l4)) {
                l5 = Gdip.Region_GetHRGN(l6, l4);
            }
            Gdip.Region_delete(l6);
            Gdip.Graphics_SetPixelOffsetMode(l4, 4);
            float[] fArray = null;
            long l7 = Gdip.Matrix_new(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
            if (l7 == 0L) {
                this.error(2);
            }
            Gdip.Graphics_GetTransform(l4, l7);
            if (!Gdip.Matrix_IsIdentity(l7)) {
                fArray = new float[6];
                Gdip.Matrix_GetElements(l7, fArray);
            }
            Gdip.Matrix_delete(l7);
            l3 = Gdip.Graphics_GetHDC(l4);
            n = OS.SaveDC(l3);
            if (fArray != null) {
                OS.SetGraphicsMode(l3, 2);
                OS.SetWorldTransform(l3, fArray);
            }
            if (l5 != 0L) {
                OS.SelectClipRgn(l3, l5);
                OS.DeleteObject(l5);
            }
        }
        int n2 = 384;
        OS.RedrawWindow(l2, null, 0L, 384);
        this.printWidget(l2, l3, gC);
        if (l4 != 0L) {
            OS.RestoreDC(l3, n);
            Gdip.Graphics_ReleaseHDC(l4, l3);
        }
        return true;
    }

    void printWidget(long l2, long l3, GC gC) {
        boolean bl = false;
        if (OS.GetDeviceCaps(gC.handle, 2) != 2) {
            int n;
            long l4;
            boolean bl2;
            long l5;
            long l6 = l5 = OS.GetParent(l2);
            while (OS.GetParent(l5) != 0L && OS.GetWindow(l5, 4) == 0L) {
                l5 = OS.GetParent(l5);
            }
            RECT rECT = new RECT();
            OS.GetWindowRect(l2, rECT);
            boolean bl3 = bl2 = !OS.IsWindowVisible(l2);
            if (!bl2) {
                RECT rECT2 = new RECT();
                OS.GetWindowRect(l5, rECT2);
                OS.IntersectRect(rECT2, rECT, rECT2);
                boolean bl4 = bl2 = !OS.EqualRect(rECT2, rECT);
            }
            if (!bl2) {
                long l7 = OS.CreateRectRgn(0, 0, 0, 0);
                l4 = OS.GetParent(l2);
                while (l4 != l5 && !bl2) {
                    if (OS.GetWindowRgn(l4, l7) != 0) {
                        bl2 = true;
                    }
                    l4 = OS.GetParent(l4);
                }
                OS.DeleteObject(l7);
            }
            int n2 = OS.GetWindowLong(l2, -16);
            int n3 = OS.GetWindowLong(l2, -20);
            l4 = OS.GetWindow(l2, 3);
            if (l4 == 0L || l4 == l2) {
                l4 = 0L;
            }
            if (bl2) {
                n = OS.GetSystemMetrics(76);
                int n4 = OS.GetSystemMetrics(77);
                int n5 = OS.GetSystemMetrics(78);
                int n6 = OS.GetSystemMetrics(79);
                int n7 = 53;
                if ((n2 & 0x10000000) != 0) {
                    OS.DefWindowProc(l2, 11, 0L, 0L);
                }
                OS.SetWindowPos(l2, 0L, n + n5, n4 + n6, 0, 0, 53);
                OS.SetWindowLong(l2, -16, n2 & 0xBFFFFFFF | Integer.MIN_VALUE);
                OS.SetWindowLong(l2, -20, n3 | 0x80);
                Shell shell = this.getShell();
                Control control = shell.savedFocus;
                OS.SetParent(l2, 0L);
                shell.setSavedFocus(control);
                if ((n2 & 0x10000000) != 0) {
                    OS.DefWindowProc(l2, 11, 1L, 0L);
                }
            }
            if ((n2 & 0x10000000) == 0) {
                OS.ShowWindow(l2, 5);
            }
            bl = OS.PrintWindow(l2, l3, 0);
            if ((n2 & 0x10000000) == 0) {
                OS.ShowWindow(l2, 0);
            }
            if (bl2) {
                if ((n2 & 0x10000000) != 0) {
                    OS.DefWindowProc(l2, 11, 0L, 0L);
                }
                OS.SetWindowLong(l2, -16, n2);
                OS.SetWindowLong(l2, -20, n3);
                OS.SetParent(l2, l6);
                OS.MapWindowPoints(0L, l6, rECT, 2);
                n = 49;
                OS.SetWindowPos(l2, l4, rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top, 49);
                if ((n2 & 0x10000000) != 0) {
                    OS.DefWindowProc(l2, 11, 1L, 0L);
                }
            }
        }
        if (!bl) {
            int n = 30;
            OS.SendMessage(l2, 791, l3, 30L);
        }
    }

    public void requestLayout() {
        this.getShell().layout(new Control[]{this}, 4);
    }

    public void redraw() {
        this.checkWidget();
        this.redrawInPixels(null, false);
    }

    public void redraw(int n, int n2, int n3, int n4, boolean bl) {
        this.checkWidget();
        n = DPIUtil.autoScaleUp(n);
        n2 = DPIUtil.autoScaleUp(n2);
        n3 = DPIUtil.autoScaleUp(n3);
        n4 = DPIUtil.autoScaleUp(n4);
        if (n3 <= 0 || n4 <= 0) {
            return;
        }
        RECT rECT = new RECT();
        OS.SetRect(rECT, n, n2, n + n3, n2 + n4);
        this.redrawInPixels(rECT, bl);
    }

    void redrawInPixels(RECT rECT, boolean bl) {
        if (!OS.IsWindowVisible(this.handle)) {
            return;
        }
        int n = 5;
        if (bl) {
            n |= 0x80;
        }
        OS.RedrawWindow(this.handle, rECT, 0L, n);
    }

    boolean redrawChildren() {
        if (!OS.IsWindowVisible(this.handle)) {
            return false;
        }
        Control control = this.findBackgroundControl();
        if (control == null) {
            if ((this.state & 0x100) != 0 && OS.IsAppThemed()) {
                OS.InvalidateRect(this.handle, null, true);
                return true;
            }
        } else if (control.backgroundImage != null) {
            OS.InvalidateRect(this.handle, null, true);
            return true;
        }
        return false;
    }

    void register() {
        this.display.addControl(this.handle, this);
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.handle = 0L;
        this.parent = null;
    }

    @Override
    void releaseParent() {
        this.parent.removeControl(this);
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        if (this.toolTipText != null) {
            this.setToolTipText(this.getShell(), null);
        }
        this.toolTipText = null;
        if (this.menu != null && !this.menu.isDisposed()) {
            this.menu.dispose();
        }
        this.backgroundImage = null;
        this.menu = null;
        this.cursor = null;
        this.unsubclass();
        this.deregister();
        this.layoutData = null;
        if (this.accessible != null) {
            this.accessible.internal_dispose_Accessible();
        }
        this.accessible = null;
        this.region = null;
        this.font = null;
    }

    public void removeControlListener(ControlListener controlListener) {
        this.checkWidget();
        if (controlListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(10, controlListener);
        this.eventTable.unhook(11, controlListener);
    }

    public void removeDragDetectListener(DragDetectListener dragDetectListener) {
        this.checkWidget();
        if (dragDetectListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(29, dragDetectListener);
    }

    public void removeFocusListener(FocusListener focusListener) {
        this.checkWidget();
        if (focusListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(15, focusListener);
        this.eventTable.unhook(16, focusListener);
    }

    public void removeGestureListener(GestureListener gestureListener) {
        this.checkWidget();
        if (gestureListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(48, gestureListener);
    }

    public void removeHelpListener(HelpListener helpListener) {
        this.checkWidget();
        if (helpListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(28, helpListener);
    }

    public void removeKeyListener(KeyListener keyListener) {
        this.checkWidget();
        if (keyListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(2, keyListener);
        this.eventTable.unhook(1, keyListener);
    }

    public void removeMenuDetectListener(MenuDetectListener menuDetectListener) {
        this.checkWidget();
        if (menuDetectListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(35, menuDetectListener);
    }

    public void removeMouseTrackListener(MouseTrackListener mouseTrackListener) {
        this.checkWidget();
        if (mouseTrackListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(6, mouseTrackListener);
        this.eventTable.unhook(7, mouseTrackListener);
        this.eventTable.unhook(32, mouseTrackListener);
    }

    public void removeMouseListener(MouseListener mouseListener) {
        this.checkWidget();
        if (mouseListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(3, mouseListener);
        this.eventTable.unhook(4, mouseListener);
        this.eventTable.unhook(8, mouseListener);
    }

    public void removeMouseMoveListener(MouseMoveListener mouseMoveListener) {
        this.checkWidget();
        if (mouseMoveListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(5, mouseMoveListener);
    }

    public void removeMouseWheelListener(MouseWheelListener mouseWheelListener) {
        this.checkWidget();
        if (mouseWheelListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(37, mouseWheelListener);
    }

    public void removePaintListener(PaintListener paintListener) {
        this.checkWidget();
        if (paintListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(9, paintListener);
    }

    public void removeTouchListener(TouchListener touchListener) {
        this.checkWidget();
        if (touchListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(47, touchListener);
    }

    public void removeTraverseListener(TraverseListener traverseListener) {
        this.checkWidget();
        if (traverseListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(31, traverseListener);
    }

    int resolveTextDirection() {
        return 0;
    }

    void showWidget(boolean bl) {
        long l2 = this.topHandle();
        OS.ShowWindow(l2, bl ? 5 : 0);
        if (this.handle != l2) {
            OS.ShowWindow(this.handle, bl ? 5 : 0);
        }
    }

    @Override
    boolean sendFocusEvent(int n) {
        Shell shell = this.getShell();
        Display display = this.display;
        display.focusEvent = n;
        display.focusControl = this;
        display.focusControl.sendEvent(n);
        display.focusEvent = 0;
        display.focusControl = null;
        if (!shell.isDisposed()) {
            switch (n) {
                case 15: {
                    shell.setActiveControl(this);
                    break;
                }
                case 16: {
                    if (shell == display.getActiveShell()) break;
                    shell.setActiveControl(null);
                }
            }
        }
        return true;
    }

    void sendMove() {
        this.sendEvent(10);
    }

    void sendResize() {
        this.sendEvent(11);
    }

    void sendTouchEvent(TOUCHINPUT[] tOUCHINPUTArray) {
        Event event = new Event();
        POINT pOINT = new POINT();
        OS.GetCursorPos(pOINT);
        OS.ScreenToClient(this.handle, pOINT);
        event.setLocationInPixels(pOINT.x, pOINT.y);
        Touch[] touchArray = new Touch[tOUCHINPUTArray.length];
        Monitor monitor = this.getMonitor();
        for (int i = 0; i < tOUCHINPUTArray.length; ++i) {
            TOUCHINPUT tOUCHINPUT = tOUCHINPUTArray[i];
            TouchSource touchSource = this.display.findTouchSource(tOUCHINPUT.hSource, monitor);
            int n = 0;
            if ((tOUCHINPUT.dwFlags & 2) != 0) {
                n = 1;
            }
            if ((tOUCHINPUT.dwFlags & 4) != 0) {
                n = 4;
            }
            if ((tOUCHINPUT.dwFlags & 1) != 0) {
                n = 2;
            }
            boolean bl = (tOUCHINPUT.dwFlags & 0x10) != 0;
            int n2 = OS.TOUCH_COORD_TO_PIXEL(tOUCHINPUT.x);
            int n3 = OS.TOUCH_COORD_TO_PIXEL(tOUCHINPUT.y);
            touchArray[i] = new Touch(tOUCHINPUT.dwID, touchSource, n, bl, n2, n3);
        }
        event.touches = touchArray;
        this.setInputState(event, 47);
        this.postEvent(47, event);
    }

    void setBackground() {
        Control control = this.findBackgroundControl();
        if (control == null) {
            control = this;
        }
        if (control.backgroundImage != null) {
            Shell shell = this.getShell();
            shell.releaseBrushes();
            this.setBackgroundImage(control.backgroundImage.handle);
        } else {
            this.setBackgroundPixel(control.background == -1 ? control.defaultBackground() : control.background);
        }
    }

    public void setBackground(Color color) {
        this.checkWidget();
        this._setBackground(color);
        if (color != null) {
            this.updateBackgroundMode();
        }
    }

    private void _setBackground(Color color) {
        int n = -1;
        int n2 = 255;
        if (color != null) {
            if (color.isDisposed()) {
                this.error(5);
            }
            n = color.handle;
            n2 = color.getAlpha();
        }
        if (n == this.background && n2 == this.backgroundAlpha) {
            return;
        }
        this.background = n;
        this.backgroundAlpha = n2;
        this.updateBackgroundColor();
    }

    public void setBackgroundImage(Image image) {
        this.checkWidget();
        if (image != null) {
            if (image.isDisposed()) {
                this.error(5);
            }
            if (image.type != 0) {
                this.error(5);
            }
        }
        if (this.backgroundImage == image && this.backgroundAlpha > 0) {
            return;
        }
        this.backgroundAlpha = 255;
        this.backgroundImage = image;
        Shell shell = this.getShell();
        shell.releaseBrushes();
        this.updateBackgroundImage();
    }

    void setBackgroundImage(long l2) {
        int n = 1029;
        OS.RedrawWindow(this.handle, null, 0L, 1029);
    }

    void setBackgroundPixel(int n) {
        int n2 = 1029;
        OS.RedrawWindow(this.handle, null, 0L, 1029);
    }

    public void setBounds(int n, int n2, int n3, int n4) {
        this.checkWidget();
        n = DPIUtil.autoScaleUp(n);
        n2 = DPIUtil.autoScaleUp(n2);
        n3 = DPIUtil.autoScaleUp(n3);
        n4 = DPIUtil.autoScaleUp(n4);
        this.setBoundsInPixels(n, n2, n3, n4);
    }

    void setBoundsInPixels(int n, int n2, int n3, int n4) {
        int n5 = 52;
        this.setBoundsInPixels(n, n2, Math.max(0, n3), Math.max(0, n4), 52);
    }

    void setBoundsInPixels(int n, int n2, int n3, int n4, int n5) {
        this.setBoundsInPixels(n, n2, n3, n4, n5, true);
    }

    void setBoundsInPixels(int n, int n2, int n3, int n4, int n5, boolean bl) {
        if (this.findImageControl() != null) {
            if (this.backgroundImage == null) {
                n5 |= 0x100;
            }
        } else if (OS.GetWindow(this.handle, 5) == 0L && OS.IsAppThemed() && this.findThemeControl() != null) {
            n5 |= 0x100;
        }
        long l2 = this.topHandle();
        if (bl && this.parent != null) {
            this.forceResize();
            if (this.parent.lpwp != null) {
                WINDOWPOS[] wINDOWPOSArray;
                int n6;
                WINDOWPOS[] wINDOWPOSArray2 = this.parent.lpwp;
                for (n6 = 0; n6 < wINDOWPOSArray2.length && wINDOWPOSArray2[n6] != null; ++n6) {
                }
                if (n6 == wINDOWPOSArray2.length) {
                    wINDOWPOSArray = new WINDOWPOS[wINDOWPOSArray2.length + 4];
                    System.arraycopy(wINDOWPOSArray2, 0, wINDOWPOSArray, 0, wINDOWPOSArray2.length);
                    this.parent.lpwp = wINDOWPOSArray;
                    wINDOWPOSArray2 = wINDOWPOSArray;
                }
                wINDOWPOSArray = new WINDOWPOS();
                wINDOWPOSArray.hwnd = l2;
                wINDOWPOSArray.x = n;
                wINDOWPOSArray.y = n2;
                wINDOWPOSArray.cx = n3;
                wINDOWPOSArray.cy = n4;
                wINDOWPOSArray.flags = n5;
                wINDOWPOSArray2[n6] = wINDOWPOSArray;
                return;
            }
        }
        OS.SetWindowPos(l2, 0L, n, n2, n3, n4, n5);
    }

    public void setBounds(Rectangle rectangle) {
        this.checkWidget();
        if (rectangle == null) {
            this.error(4);
        }
        this.setBoundsInPixels(DPIUtil.autoScaleUp(rectangle));
    }

    void setBoundsInPixels(Rectangle rectangle) {
        this.setBoundsInPixels(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void setCapture(boolean bl) {
        this.checkWidget();
        if (bl) {
            OS.SetCapture(this.handle);
        } else if (OS.GetCapture() == this.handle) {
            OS.ReleaseCapture();
        }
    }

    void setCursor() {
        long l2 = OS.MAKELPARAM(1, 512);
        OS.SendMessage(this.handle, 32, this.handle, l2);
    }

    public void setCursor(Cursor cursor) {
        Object object;
        this.checkWidget();
        if (cursor != null && cursor.isDisposed()) {
            this.error(5);
        }
        this.cursor = cursor;
        long l2 = OS.GetCapture();
        if (l2 == 0L) {
            long l3;
            object = new POINT();
            if (!OS.GetCursorPos((POINT)object)) {
                return;
            }
            l2 = l3 = OS.WindowFromPoint((POINT)object);
            while (l3 != 0L && l3 != this.handle) {
                l3 = OS.GetParent(l3);
            }
            if (l3 == 0L) {
                return;
            }
        }
        if ((object = this.display.getControl(l2)) == null) {
            object = this;
        }
        ((Control)object).setCursor();
    }

    void setDefaultFont() {
        long l2 = this.display.getSystemFont().handle;
        OS.SendMessage(this.handle, 48, l2, 0L);
    }

    public void setDragDetect(boolean bl) {
        this.checkWidget();
        this.state = bl ? (this.state |= 0x8000) : (this.state &= 0xFFFF7FFF);
        this.enableDrag(bl);
    }

    public void setEnabled(boolean bl) {
        this.checkWidget();
        Control control = null;
        boolean bl2 = false;
        if (!bl && this.display.focusEvent != 16) {
            control = this.display.getFocusControl();
            bl2 = this.isFocusAncestor(control);
        }
        this.enableWidget(bl);
        if (bl2) {
            this.fixFocus(control);
        }
    }

    public void setFont(Font font) {
        this.checkWidget();
        long l2 = 0L;
        if (font != null) {
            if (font.isDisposed()) {
                this.error(5);
            }
            l2 = font.handle;
        }
        this.font = font;
        if (l2 == 0L) {
            l2 = this.defaultFont();
        }
        OS.SendMessage(this.handle, 48, l2, 1L);
    }

    public void setForeground(Color color) {
        this.checkWidget();
        int n = -1;
        if (color != null) {
            if (color.isDisposed()) {
                this.error(5);
            }
            n = color.handle;
        }
        if (n == this.foreground) {
            return;
        }
        this.foreground = n;
        this.setForegroundPixel(this.foreground);
    }

    void setForegroundPixel(int n) {
        OS.InvalidateRect(this.handle, null, true);
    }

    public void setLayoutData(Object object) {
        this.checkWidget();
        this.layoutData = object;
    }

    public void setLocation(int n, int n2) {
        this.checkWidget();
        n = DPIUtil.autoScaleUp(n);
        n2 = DPIUtil.autoScaleUp(n2);
        this.setLocationInPixels(n, n2);
    }

    void setLocationInPixels(int n, int n2) {
        int n3 = 53;
        this.setBoundsInPixels(n, n2, 0, 0, 53);
    }

    public void setLocation(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        this.setLocationInPixels(point.x, point.y);
    }

    public void setMenu(Menu menu) {
        this.checkWidget();
        if (menu != null) {
            if (menu.isDisposed()) {
                this.error(5);
            }
            if ((menu.style & 8) == 0) {
                this.error(37);
            }
            if (menu.parent != this.menuShell()) {
                this.error(32);
            }
        }
        this.menu = menu;
    }

    public void setOrientation(int n) {
        this.checkWidget();
        int n2 = 0x6000000;
        if ((n & 0x6000000) == 0 || (n & 0x6000000) == 0x6000000) {
            return;
        }
        this.style &= 0xF7FFFFFF;
        this.style &= 0xF9FFFFFF;
        this.style |= n & 0x6000000;
        this.style &= Integer.MAX_VALUE;
        this.updateOrientation();
        this.checkMirrored();
    }

    boolean setRadioFocus(boolean bl) {
        return false;
    }

    boolean setRadioSelection(boolean bl) {
        return false;
    }

    public void setRedraw(boolean bl) {
        int n;
        this.checkWidget();
        if (this.drawCount == 0 && ((n = OS.GetWindowLong(this.handle, -16)) & 0x10000000) == 0) {
            this.state |= 0x10;
        }
        if (bl) {
            if (--this.drawCount == 0) {
                long l2 = this.topHandle();
                OS.SendMessage(l2, 11, 1L, 0L);
                if (this.handle != l2) {
                    OS.SendMessage(this.handle, 11, 1L, 0L);
                }
                if ((this.state & 0x10) != 0) {
                    this.state &= 0xFFFFFFEF;
                    OS.ShowWindow(l2, 0);
                    if (this.handle != l2) {
                        OS.ShowWindow(this.handle, 0);
                    }
                } else {
                    int n2 = 1157;
                    OS.RedrawWindow(l2, null, 0L, 1157);
                }
            }
        } else if (this.drawCount++ == 0) {
            long l3 = this.topHandle();
            OS.SendMessage(l3, 11, 0L, 0L);
            if (this.handle != l3) {
                OS.SendMessage(this.handle, 11, 0L, 0L);
            }
        }
    }

    public void setRegion(Region region) {
        this.checkWidget();
        if (region != null && region.isDisposed()) {
            this.error(5);
        }
        long l2 = 0L;
        if (region != null) {
            l2 = OS.CreateRectRgn(0, 0, 0, 0);
            OS.CombineRgn(l2, region.handle, l2, 2);
        }
        OS.SetWindowRgn(this.handle, l2, true);
        this.region = region;
    }

    public void setSize(int n, int n2) {
        this.checkWidget();
        n = DPIUtil.autoScaleUp(n);
        n2 = DPIUtil.autoScaleUp(n2);
        this.setSizeInPixels(n, n2);
    }

    void setSizeInPixels(int n, int n2) {
        int n3 = 54;
        this.setBoundsInPixels(0, 0, Math.max(0, n), Math.max(0, n2), 54);
    }

    public void setSize(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        this.setSizeInPixels(point.x, point.y);
    }

    public void setTextDirection(int n) {
        this.checkWidget();
        this.updateTextDirection(n &= 0x6000000);
        this.state = n == 0x6000000 ? (this.state |= 0x400000) : (this.state &= 0xFFBFFFFF);
    }

    public void setToolTipText(String string) {
        this.checkWidget();
        if (!Objects.equals(string, this.toolTipText)) {
            this.toolTipText = string;
            this.setToolTipText(this.getShell(), string);
        }
    }

    void setToolTipText(Shell shell, String string) {
        shell.setToolTipText(this.handle, string);
    }

    public void setTouchEnabled(boolean bl) {
        this.checkWidget();
        if (bl) {
            OS.RegisterTouchWindow(this.handle, 0);
        } else {
            OS.UnregisterTouchWindow(this.handle);
        }
    }

    public void setVisible(boolean bl) {
        int n;
        this.checkWidget();
        if (this <= 0 ? (this.state & 0x10) == 0 == bl : ((n = OS.GetWindowLong(this.handle, -16)) & 0x10000000) != 0 == bl) {
            return;
        }
        if (bl) {
            this.sendEvent(22);
            if (this.isDisposed()) {
                return;
            }
        }
        Control control = null;
        boolean bl2 = false;
        if (!bl && this.display.focusEvent != 16) {
            control = this.display.getFocusControl();
            bl2 = this.isFocusAncestor(control);
        }
        if (this <= 0) {
            this.state = bl ? this.state & 0xFFFFFFEF : this.state | 0x10;
        } else {
            this.showWidget(bl);
            if (this.isDisposed()) {
                return;
            }
        }
        if (!bl) {
            this.sendEvent(23);
            if (this.isDisposed()) {
                return;
            }
        }
        if (bl2) {
            this.fixFocus(control);
        }
    }

    void sort(int[] nArray) {
        int n = nArray.length;
        for (int i = n / 2; i > 0; i /= 2) {
            for (int j = i; j < n; ++j) {
                for (int k = j - i; k >= 0; k -= i) {
                    if (nArray[k] > nArray[k + i]) continue;
                    int n2 = nArray[k];
                    nArray[k] = nArray[k + i];
                    nArray[k + i] = n2;
                }
            }
        }
    }

    void subclass() {
        long l2;
        long l3 = this.windowProc();
        if (l3 == (l2 = this.display.windowProc)) {
            return;
        }
        OS.SetWindowLongPtr(this.handle, -4, l2);
    }

    public Point toControl(int n, int n2) {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.toControlInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2)));
    }

    Point toControlInPixels(int n, int n2) {
        POINT pOINT = new POINT();
        pOINT.x = n;
        pOINT.y = n2;
        OS.ScreenToClient(this.handle, pOINT);
        return new Point(pOINT.x, pOINT.y);
    }

    public Point toControl(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        return DPIUtil.autoScaleDown(this.toControlInPixels(point.x, point.y));
    }

    public Point toDisplay(int n, int n2) {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.toDisplayInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2)));
    }

    Point toDisplayInPixels(int n, int n2) {
        POINT pOINT = new POINT();
        pOINT.x = n;
        pOINT.y = n2;
        OS.ClientToScreen(this.handle, pOINT);
        return new Point(pOINT.x, pOINT.y);
    }

    public Point toDisplay(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        return DPIUtil.autoScaleDown(this.toDisplayInPixels(point.x, point.y));
    }

    long topHandle() {
        return this.handle;
    }

    boolean translateAccelerator(MSG mSG) {
        return this.menuShell().translateAccelerator(mSG);
    }

    boolean translateMnemonic(MSG mSG) {
        Decorations decorations;
        if (mSG.wParam < 32L) {
            return false;
        }
        long l2 = mSG.hwnd;
        if (OS.GetKeyState(18) >= 0) {
            long l3 = OS.SendMessage(l2, 135, 0L, 0L);
            if ((l3 & 4L) != 0L) {
                return false;
            }
            if ((l3 & 0x2000L) == 0L) {
                return false;
            }
        }
        if ((decorations = this.menuShell()).isVisible() && decorations.isEnabled()) {
            this.display.lastAscii = (int)mSG.wParam;
            Display display = this.display;
            Display display2 = this.display;
            boolean bl = false;
            display2.lastDead = false;
            display.lastNull = false;
            Event event = new Event();
            event.detail = 128;
            if (this.setKeyState(event, 31, mSG.wParam, mSG.lParam)) {
                Control control = this;
                return event != null || decorations.translateMnemonic(event, this);
            }
        }
        return false;
    }

    /*
     * Exception decompiling
     */
    boolean translateTraversal(MSG var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl277 : ALOAD - null : trying to set 1 previously set to 0
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

    public boolean traverse(int n) {
        this.checkWidget();
        Event event = new Event();
        event.doit = true;
        event.detail = n;
        return this.traverse(event);
    }

    public boolean traverse(int n, Event event) {
        this.checkWidget();
        if (event == null) {
            this.error(4);
        }
        return this.traverse(n, event.character, event.keyCode, event.keyLocation, event.stateMask, event.doit);
    }

    public boolean traverse(int n, KeyEvent keyEvent) {
        this.checkWidget();
        if (keyEvent == null) {
            this.error(4);
        }
        return this.traverse(n, keyEvent.character, keyEvent.keyCode, keyEvent.keyLocation, keyEvent.stateMask, keyEvent.doit);
    }

    /*
     * Exception decompiling
     */
    boolean traverse(int var1, char var2, int var3, int var4, int var5, boolean var6) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl98 : ALOAD - null : trying to set 1 previously set to 0
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

    boolean traverseEscape() {
        return false;
    }

    boolean traverseGroup(boolean bl) {
        int n;
        int n2;
        Control control = this.computeTabRoot();
        Widget widget = this.computeTabGroup();
        Widget[] widgetArray = control.computeTabList();
        int n3 = widgetArray.length;
        for (n2 = 0; n2 < n3 && widgetArray[n2] != widget; ++n2) {
        }
        if (n2 == n3) {
            return false;
        }
        int n4 = n2;
        int n5 = n = bl ? 1 : -1;
        while ((n2 = (n2 + n + n3) % n3) != n4) {
            Widget widget2 = widgetArray[n2];
            if (widget2.isDisposed() || !widget2.setTabGroupFocus()) continue;
            return true;
        }
        return !widget.isDisposed() && widget.setTabGroupFocus();
    }

    boolean traverseItem(boolean bl) {
        int n;
        int n2;
        Control[] controlArray = this.parent._getChildren();
        int n3 = controlArray.length;
        for (n2 = 0; n2 < n3 && controlArray[n2] != this; ++n2) {
        }
        if (n2 == n3) {
            return false;
        }
        int n4 = n2;
        int n5 = n = bl ? 1 : -1;
        while ((n2 = (n2 + n + n3) % n3) != n4) {
            Control control = controlArray[n2];
            if (control.isDisposed() || control == null || control != false) continue;
            return true;
        }
        return false;
    }

    boolean traverseMnemonic(char c) {
        if (this.mnemonicHit(c)) {
            OS.SendMessage(this.handle, 295, 3L, 0L);
            return true;
        }
        return false;
    }

    boolean traversePage(boolean bl) {
        return false;
    }

    boolean traverseReturn() {
        return false;
    }

    void unsubclass() {
        long l2 = this.display.windowProc;
        long l3 = this.windowProc();
        if (l2 == l3) {
            return;
        }
        OS.SetWindowLongPtr(this.handle, -4, l3);
    }

    public void update() {
        this.checkWidget();
        this.update(false);
    }

    void update(boolean bl) {
        int n = 256;
        if (bl) {
            n |= 0x80;
        }
        OS.RedrawWindow(this.handle, null, 0L, n);
    }

    void updateBackgroundColor() {
        Control control = this.findBackgroundControl();
        if (control == null) {
            control = this;
        }
        this.setBackgroundPixel(control.background);
    }

    void updateBackgroundImage() {
        Control control = this.findBackgroundControl();
        Image image = control != null ? control.backgroundImage : this.backgroundImage;
        this.setBackgroundImage(image != null ? image.handle : 0L);
    }

    void updateBackgroundMode() {
        int n = this.state & 0x400;
        this.checkBackground();
        if (n != (this.state & 0x400)) {
            this.setBackground();
        }
    }

    void updateFont(Font font, Font font2) {
        if (this.getFont().equals(font)) {
            this.setFont(font2);
        }
    }

    void updateLayout(boolean bl, boolean bl2) {
    }

    void updateOrientation() {
        int n = OS.GetWindowLong(this.handle, -20);
        n = (this.style & 0x4000000) != 0 ? (n |= 0x400000) : (n &= 0xFFBFFFFF);
        OS.SetWindowLong(this.handle, -20, n &= 0xFFFFDFFF);
        OS.InvalidateRect(this.handle, null, true);
    }

    boolean updateTextDirection(int n) {
        boolean bl;
        if (n == 0x6000000) {
            n = this.resolveTextDirection();
            this.state |= 0x400000;
        } else {
            this.state &= 0xFFBFFFFF;
        }
        if (n == 0) {
            return false;
        }
        int n2 = OS.GetWindowLong(this.handle, -20);
        int n3 = 0x402000;
        boolean bl2 = (n2 & 0x402000) != 0 && (n2 & 0x402000) != 0x402000;
        boolean bl3 = bl = n == 0x4000000;
        if (bl == bl2) {
            return false;
        }
        boolean bl4 = bl2 = (n2 & 0x400000) != 0;
        if (bl != bl2) {
            n2 |= 0x2000;
            this.style |= Integer.MIN_VALUE;
        } else {
            n2 &= 0xFFFFDFFF;
            this.style &= Integer.MAX_VALUE;
        }
        OS.SetWindowLong(this.handle, -20, n2);
        OS.InvalidateRect(this.handle, null, true);
        return true;
    }

    CREATESTRUCT widgetCreateStruct() {
        return null;
    }

    int widgetExtStyle() {
        int n = 0;
        if (this != null && (this.style & 0x800) != 0) {
            n |= 0x200;
        }
        n |= 0x100000;
        if ((this.style & 0x4000000) != 0) {
            n |= 0x400000;
        }
        if ((this.style & Integer.MIN_VALUE) != 0) {
            n |= 0x2000;
        }
        return n;
    }

    long widgetParent() {
        return this.parent.handle;
    }

    int widgetStyle() {
        int n = 0x54000000;
        if (this != null && (this.style & 0x800) != 0) {
            n |= 0x800000;
        }
        return n;
    }

    public boolean setParent(Composite composite) {
        long l2;
        this.checkWidget();
        if (composite == null) {
            this.error(4);
        }
        if (composite.isDisposed()) {
            this.error(5);
        }
        if (this.parent == composite) {
            return true;
        }
        if (!this.isReparentable()) {
            return false;
        }
        this.releaseParent();
        Shell shell = composite.getShell();
        Shell shell2 = this.getShell();
        Decorations decorations = composite.menuShell();
        Decorations decorations2 = this.menuShell();
        if (shell2 != shell || decorations2 != decorations) {
            Menu[] menuArray = shell2.findMenus(this);
            this.fixChildren(shell, shell2, decorations, decorations2, menuArray);
        }
        if (OS.SetParent(l2 = this.topHandle(), composite.handle) == 0L) {
            return false;
        }
        this.parent = composite;
        int n = 19;
        OS.SetWindowPos(l2, 1L, 0, 0, 0, 0, 19);
        this.reskin(1);
        return true;
    }

    abstract TCHAR windowClass();

    abstract long windowProc();

    long windowProc(long l2, int n, long l3, long l4) {
        Display display = this.display;
        LRESULT lRESULT = null;
        switch (n) {
            case 6: {
                lRESULT = this.WM_ACTIVATE(l3, l4);
                break;
            }
            case 533: {
                lRESULT = this.WM_CAPTURECHANGED(l3, l4);
                break;
            }
            case 295: {
                lRESULT = this.WM_CHANGEUISTATE(l3, l4);
                break;
            }
            case 258: {
                lRESULT = this.WM_CHAR(l3, l4);
                break;
            }
            case 771: {
                lRESULT = this.WM_CLEAR(l3, l4);
                break;
            }
            case 16: {
                lRESULT = this.WM_CLOSE(l3, l4);
                break;
            }
            case 273: {
                lRESULT = this.WM_COMMAND(l3, l4);
                break;
            }
            case 123: {
                lRESULT = this.WM_CONTEXTMENU(l3, l4);
                break;
            }
            case 306: 
            case 307: 
            case 308: 
            case 309: 
            case 310: 
            case 311: 
            case 312: {
                lRESULT = this.WM_CTLCOLOR(l3, l4);
                break;
            }
            case 768: {
                lRESULT = this.WM_CUT(l3, l4);
                break;
            }
            case 2: {
                lRESULT = this.WM_DESTROY(l3, l4);
                break;
            }
            case 43: {
                lRESULT = this.WM_DRAWITEM(l3, l4);
                break;
            }
            case 22: {
                lRESULT = this.WM_ENDSESSION(l3, l4);
                break;
            }
            case 289: {
                lRESULT = this.WM_ENTERIDLE(l3, l4);
                break;
            }
            case 20: {
                lRESULT = this.WM_ERASEBKGND(l3, l4);
                break;
            }
            case 281: {
                lRESULT = this.WM_GESTURE(l3, l4);
                break;
            }
            case 135: {
                lRESULT = this.WM_GETDLGCODE(l3, l4);
                break;
            }
            case 49: {
                lRESULT = this.WM_GETFONT(l3, l4);
                break;
            }
            case 61: {
                lRESULT = this.WM_GETOBJECT(l3, l4);
                break;
            }
            case 36: {
                lRESULT = this.WM_GETMINMAXINFO(l3, l4);
                break;
            }
            case 83: {
                lRESULT = this.WM_HELP(l3, l4);
                break;
            }
            case 276: {
                lRESULT = this.WM_HSCROLL(l3, l4);
                break;
            }
            case 646: {
                lRESULT = this.WM_IME_CHAR(l3, l4);
                break;
            }
            case 271: {
                lRESULT = this.WM_IME_COMPOSITION(l3, l4);
                break;
            }
            case 269: {
                lRESULT = this.WM_IME_COMPOSITION_START(l3, l4);
                break;
            }
            case 270: {
                lRESULT = this.WM_IME_ENDCOMPOSITION(l3, l4);
                break;
            }
            case 279: {
                lRESULT = this.WM_INITMENUPOPUP(l3, l4);
                break;
            }
            case 81: {
                lRESULT = this.WM_INPUTLANGCHANGE(l3, l4);
                break;
            }
            case 786: {
                lRESULT = this.WM_HOTKEY(l3, l4);
                break;
            }
            case 256: {
                lRESULT = this.WM_KEYDOWN(l3, l4);
                break;
            }
            case 257: {
                lRESULT = this.WM_KEYUP(l3, l4);
                break;
            }
            case 8: {
                lRESULT = this.WM_KILLFOCUS(l3, l4);
                break;
            }
            case 515: {
                lRESULT = this.WM_LBUTTONDBLCLK(l3, l4);
                break;
            }
            case 513: {
                lRESULT = this.WM_LBUTTONDOWN(l3, l4);
                break;
            }
            case 514: {
                lRESULT = this.WM_LBUTTONUP(l3, l4);
                break;
            }
            case 521: {
                lRESULT = this.WM_MBUTTONDBLCLK(l3, l4);
                break;
            }
            case 519: {
                lRESULT = this.WM_MBUTTONDOWN(l3, l4);
                break;
            }
            case 520: {
                lRESULT = this.WM_MBUTTONUP(l3, l4);
                break;
            }
            case 44: {
                lRESULT = this.WM_MEASUREITEM(l3, l4);
                break;
            }
            case 288: {
                lRESULT = this.WM_MENUCHAR(l3, l4);
                break;
            }
            case 287: {
                lRESULT = this.WM_MENUSELECT(l3, l4);
                break;
            }
            case 33: {
                lRESULT = this.WM_MOUSEACTIVATE(l3, l4);
                break;
            }
            case 673: {
                lRESULT = this.WM_MOUSEHOVER(l3, l4);
                break;
            }
            case 675: {
                lRESULT = this.WM_MOUSELEAVE(l3, l4);
                break;
            }
            case 512: {
                lRESULT = this.WM_MOUSEMOVE(l3, l4);
                break;
            }
            case 522: {
                lRESULT = this.WM_MOUSEWHEEL(l3, l4);
                break;
            }
            case 526: {
                lRESULT = this.WM_MOUSEHWHEEL(l3, l4);
                break;
            }
            case 3: {
                lRESULT = this.WM_MOVE(l3, l4);
                break;
            }
            case 134: {
                lRESULT = this.WM_NCACTIVATE(l3, l4);
                break;
            }
            case 131: {
                lRESULT = this.WM_NCCALCSIZE(l3, l4);
                break;
            }
            case 132: {
                lRESULT = this.WM_NCHITTEST(l3, l4);
                break;
            }
            case 161: {
                lRESULT = this.WM_NCLBUTTONDOWN(l3, l4);
                break;
            }
            case 133: {
                lRESULT = this.WM_NCPAINT(l3, l4);
                break;
            }
            case 78: {
                lRESULT = this.WM_NOTIFY(l3, l4);
                break;
            }
            case 15: {
                lRESULT = this.WM_PAINT(l3, l4);
                break;
            }
            case 529: {
                lRESULT = this.WM_ENTERMENULOOP(l3, l4);
                break;
            }
            case 530: {
                lRESULT = this.WM_EXITMENULOOP(l3, l4);
                break;
            }
            case 561: {
                lRESULT = this.WM_ENTERSIZEMOVE(l3, l4);
                break;
            }
            case 562: {
                lRESULT = this.WM_EXITSIZEMOVE(l3, l4);
                break;
            }
            case 528: {
                lRESULT = this.WM_PARENTNOTIFY(l3, l4);
                break;
            }
            case 770: {
                lRESULT = this.WM_PASTE(l3, l4);
                break;
            }
            case 791: {
                lRESULT = this.WM_PRINT(l3, l4);
                break;
            }
            case 792: {
                lRESULT = this.WM_PRINTCLIENT(l3, l4);
                break;
            }
            case 17: {
                lRESULT = this.WM_QUERYENDSESSION(l3, l4);
                break;
            }
            case 19: {
                lRESULT = this.WM_QUERYOPEN(l3, l4);
                break;
            }
            case 518: {
                lRESULT = this.WM_RBUTTONDBLCLK(l3, l4);
                break;
            }
            case 516: {
                lRESULT = this.WM_RBUTTONDOWN(l3, l4);
                break;
            }
            case 517: {
                lRESULT = this.WM_RBUTTONUP(l3, l4);
                break;
            }
            case 32: {
                lRESULT = this.WM_SETCURSOR(l3, l4);
                break;
            }
            case 7: {
                lRESULT = this.WM_SETFOCUS(l3, l4);
                break;
            }
            case 48: {
                lRESULT = this.WM_SETFONT(l3, l4);
                break;
            }
            case 26: {
                lRESULT = this.WM_SETTINGCHANGE(l3, l4);
                break;
            }
            case 11: {
                lRESULT = this.WM_SETREDRAW(l3, l4);
                break;
            }
            case 24: {
                lRESULT = this.WM_SHOWWINDOW(l3, l4);
                break;
            }
            case 5: {
                lRESULT = this.WM_SIZE(l3, l4);
                break;
            }
            case 262: {
                lRESULT = this.WM_SYSCHAR(l3, l4);
                break;
            }
            case 21: {
                lRESULT = this.WM_SYSCOLORCHANGE(l3, l4);
                break;
            }
            case 274: {
                lRESULT = this.WM_SYSCOMMAND(l3, l4);
                break;
            }
            case 260: {
                lRESULT = this.WM_SYSKEYDOWN(l3, l4);
                break;
            }
            case 261: {
                lRESULT = this.WM_SYSKEYUP(l3, l4);
                break;
            }
            case 715: {
                lRESULT = this.WM_TABLET_FLICK(l3, l4);
                break;
            }
            case 275: {
                lRESULT = this.WM_TIMER(l3, l4);
                break;
            }
            case 576: {
                lRESULT = this.WM_TOUCH(l3, l4);
                break;
            }
            case 772: {
                lRESULT = this.WM_UNDO(l3, l4);
                break;
            }
            case 293: {
                lRESULT = this.WM_UNINITMENUPOPUP(l3, l4);
                break;
            }
            case 296: {
                lRESULT = this.WM_UPDATEUISTATE(l3, l4);
                break;
            }
            case 277: {
                lRESULT = this.WM_VSCROLL(l3, l4);
                break;
            }
            case 71: {
                lRESULT = this.WM_WINDOWPOSCHANGED(l3, l4);
                break;
            }
            case 70: {
                lRESULT = this.WM_WINDOWPOSCHANGING(l3, l4);
                break;
            }
            case 525: {
                lRESULT = this.WM_XBUTTONDBLCLK(l3, l4);
                break;
            }
            case 523: {
                lRESULT = this.WM_XBUTTONDOWN(l3, l4);
                break;
            }
            case 524: {
                lRESULT = this.WM_XBUTTONUP(l3, l4);
                break;
            }
            case 736: {
                lRESULT = this.WM_DPICHANGED(l3, l4);
            }
        }
        if (lRESULT != null) {
            return lRESULT.value;
        }
        display.sendPreExternalEventDispatchEvent();
        long l5 = this.callWindowProc(l2, n, l3, l4);
        display.sendPostExternalEventDispatchEvent();
        return l5;
    }

    LRESULT WM_ACTIVATE(long l2, long l3) {
        return null;
    }

    LRESULT WM_CAPTURECHANGED(long l2, long l3) {
        return this.wmCaptureChanged(this.handle, l2, l3);
    }

    LRESULT WM_CHANGEUISTATE(long l2, long l3) {
        if ((this.state & 0x100000) != 0) {
            return LRESULT.ZERO;
        }
        return null;
    }

    LRESULT WM_CHAR(long l2, long l3) {
        return this.wmChar(this.handle, l2, l3);
    }

    LRESULT WM_CLEAR(long l2, long l3) {
        return null;
    }

    LRESULT WM_CLOSE(long l2, long l3) {
        return null;
    }

    LRESULT WM_COMMAND(long l2, long l3) {
        if (l3 == 0L) {
            int n;
            MenuItem menuItem;
            Decorations decorations = this.menuShell();
            if (decorations.isEnabled() && (menuItem = this.display.getMenuItem(n = OS.LOWORD(l2))) != null && menuItem.isEnabled()) {
                return menuItem.wmCommandChild(l2, l3);
            }
            return null;
        }
        Control control = this.display.getControl(l3);
        if (control == null) {
            return null;
        }
        return control.wmCommandChild(l2, l3);
    }

    LRESULT WM_CONTEXTMENU(long l2, long l3) {
        return this.wmContextMenu(this.handle, l2, l3);
    }

    LRESULT WM_CTLCOLOR(long l2, long l3) {
        Control control = this.display.getControl(l3);
        if (control == null) {
            return null;
        }
        return control.wmColorChild(l2, l3);
    }

    LRESULT WM_CUT(long l2, long l3) {
        return null;
    }

    LRESULT WM_DESTROY(long l2, long l3) {
        OS.KillTimer(this.handle, 110L);
        return null;
    }

    LRESULT WM_DPICHANGED(long l2, long l3) {
        int n;
        int n2 = DPIUtil.mapDPIToZoom(OS.HIWORD(l2));
        int n3 = DPIUtil.getZoomForAutoscaleProperty(n2);
        if (n3 != (n = DPIUtil.getDeviceZoom())) {
            Event event = new Event();
            event.type = 55;
            event.widget = this;
            event.detail = n3;
            event.doit = true;
            this.notifyListeners(55, event);
            return LRESULT.ZERO;
        }
        return LRESULT.ONE;
    }

    LRESULT WM_DRAWITEM(long l2, long l3) {
        DRAWITEMSTRUCT dRAWITEMSTRUCT = new DRAWITEMSTRUCT();
        OS.MoveMemory(dRAWITEMSTRUCT, l3, DRAWITEMSTRUCT.sizeof);
        if (dRAWITEMSTRUCT.CtlType == 1) {
            MenuItem menuItem = this.display.getMenuItem(dRAWITEMSTRUCT.itemID);
            if (menuItem == null) {
                return null;
            }
            return menuItem.wmDrawChild(l2, l3);
        }
        Control control = this.display.getControl(dRAWITEMSTRUCT.hwndItem);
        if (control == null) {
            return null;
        }
        return control.wmDrawChild(l2, l3);
    }

    LRESULT WM_ENDSESSION(long l2, long l3) {
        return null;
    }

    LRESULT WM_ENTERIDLE(long l2, long l3) {
        return null;
    }

    LRESULT WM_ENTERMENULOOP(long l2, long l3) {
        this.display.externalEventLoop = true;
        return null;
    }

    LRESULT WM_ENTERSIZEMOVE(long l2, long l3) {
        this.display.externalEventLoop = true;
        return null;
    }

    LRESULT WM_ERASEBKGND(long l2, long l3) {
        if ((this.state & 0x200) != 0 && this.findImageControl() != null) {
            return LRESULT.ONE;
        }
        if ((this.state & 0x100) != 0 && OS.IsAppThemed() && this.findThemeControl() != null) {
            return LRESULT.ONE;
        }
        return null;
    }

    LRESULT WM_EXITMENULOOP(long l2, long l3) {
        this.display.externalEventLoop = false;
        return null;
    }

    LRESULT WM_EXITSIZEMOVE(long l2, long l3) {
        this.display.externalEventLoop = false;
        return null;
    }

    /*
     * Exception decompiling
     */
    LRESULT WM_GESTURE(long var1, long var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl28 : ACONST_NULL - null : trying to set 0 previously set to 1
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

    LRESULT WM_GETDLGCODE(long l2, long l3) {
        return null;
    }

    LRESULT WM_GETFONT(long l2, long l3) {
        return null;
    }

    LRESULT WM_GETOBJECT(long l2, long l3) {
        long l4;
        if (this.accessible != null && (l4 = this.accessible.internal_WM_GETOBJECT(l2, l3)) != 0L) {
            return new LRESULT(l4);
        }
        return null;
    }

    LRESULT WM_GETMINMAXINFO(long l2, long l3) {
        return null;
    }

    LRESULT WM_HOTKEY(long l2, long l3) {
        return null;
    }

    LRESULT WM_HELP(long l2, long l3) {
        HELPINFO hELPINFO = new HELPINFO();
        OS.MoveMemory(hELPINFO, l3, HELPINFO.sizeof);
        Decorations decorations = this.menuShell();
        if (!decorations.isEnabled()) {
            return null;
        }
        if (hELPINFO.iContextType == 2) {
            MenuItem menuItem = this.display.getMenuItem(hELPINFO.iCtrlId);
            if (menuItem != null && menuItem.isEnabled()) {
                Widget widget = null;
                if (menuItem.hooks(28)) {
                    widget = menuItem;
                } else {
                    Menu menu = menuItem.parent;
                    if (menu.hooks(28)) {
                        widget = menu;
                    }
                }
                if (widget != null) {
                    long l4 = decorations.handle;
                    OS.SendMessage(l4, 31, 0L, 0L);
                    widget.postEvent(28);
                    return LRESULT.ONE;
                }
            }
            return null;
        }
        if (this.hooks(28)) {
            this.postEvent(28);
            return LRESULT.ONE;
        }
        return null;
    }

    LRESULT WM_HSCROLL(long l2, long l3) {
        Control control = this.display.getControl(l3);
        if (control == null) {
            return null;
        }
        return control.wmScrollChild(l2, l3);
    }

    LRESULT WM_IME_CHAR(long l2, long l3) {
        return this.wmIMEChar(this.handle, l2, l3);
    }

    LRESULT WM_IME_COMPOSITION(long l2, long l3) {
        return null;
    }

    LRESULT WM_IME_COMPOSITION_START(long l2, long l3) {
        return null;
    }

    LRESULT WM_IME_ENDCOMPOSITION(long l2, long l3) {
        return null;
    }

    LRESULT WM_UNINITMENUPOPUP(long l2, long l3) {
        Menu menu = this.menuShell().findMenu(l2);
        if (menu != null) {
            Shell shell = this.getShell();
            menu.sendEvent(23);
            if (menu == shell.activeMenu) {
                shell.activeMenu = null;
            }
        }
        return null;
    }

    LRESULT WM_INITMENUPOPUP(long l2, long l3) {
        Menu menu;
        if (this.display.accelKeyHit) {
            return null;
        }
        Shell shell = this.getShell();
        Menu menu2 = shell.activeMenu;
        Menu menu3 = null;
        if (OS.HIWORD(l3) == 0 && (menu3 = this.menuShell().findMenu(l2)) != null) {
            menu3.update();
        }
        for (menu = menu3; menu != null && menu != menu2; menu = menu.getParentMenu()) {
        }
        if (menu == null) {
            menu = shell.activeMenu;
            while (menu != null) {
                Menu menu4;
                menu.sendEvent(23);
                if (menu.isDisposed()) break;
                menu = menu.getParentMenu();
                for (menu4 = menu3; menu4 != null && menu4 != menu; menu4 = menu4.getParentMenu()) {
                }
                if (menu4 == null) continue;
                break;
            }
        }
        if (menu3 != null && menu3.isDisposed()) {
            menu3 = null;
        }
        if ((shell.activeMenu = menu3) != null && menu3 != menu2) {
            menu3.sendEvent(22);
        }
        return null;
    }

    LRESULT WM_INPUTLANGCHANGE(long l2, long l3) {
        this.menuShell().destroyAccelerators();
        return null;
    }

    LRESULT WM_KEYDOWN(long l2, long l3) {
        return this.wmKeyDown(this.handle, l2, l3);
    }

    LRESULT WM_KEYUP(long l2, long l3) {
        return this.wmKeyUp(this.handle, l2, l3);
    }

    LRESULT WM_KILLFOCUS(long l2, long l3) {
        if (l2 == 0L) {
            this.menuShell().setSavedFocus(this);
        }
        return this.wmKillFocus(this.handle, l2, l3);
    }

    LRESULT WM_LBUTTONDBLCLK(long l2, long l3) {
        return this.wmLButtonDblClk(this.handle, l2, l3);
    }

    LRESULT WM_LBUTTONDOWN(long l2, long l3) {
        return this.wmLButtonDown(this.handle, l2, l3);
    }

    LRESULT WM_LBUTTONUP(long l2, long l3) {
        return this.wmLButtonUp(this.handle, l2, l3);
    }

    LRESULT WM_MBUTTONDBLCLK(long l2, long l3) {
        return this.wmMButtonDblClk(this.handle, l2, l3);
    }

    LRESULT WM_MBUTTONDOWN(long l2, long l3) {
        return this.wmMButtonDown(this.handle, l2, l3);
    }

    LRESULT WM_MBUTTONUP(long l2, long l3) {
        return this.wmMButtonUp(this.handle, l2, l3);
    }

    LRESULT WM_MEASUREITEM(long l2, long l3) {
        MEASUREITEMSTRUCT mEASUREITEMSTRUCT = new MEASUREITEMSTRUCT();
        OS.MoveMemory(mEASUREITEMSTRUCT, l3, MEASUREITEMSTRUCT.sizeof);
        if (mEASUREITEMSTRUCT.CtlType == 1) {
            MenuItem menuItem = this.display.getMenuItem(mEASUREITEMSTRUCT.itemID);
            if (menuItem == null) {
                return null;
            }
            return menuItem.wmMeasureChild(l2, l3);
        }
        long l4 = OS.GetDlgItem(this.handle, mEASUREITEMSTRUCT.CtlID);
        Control control = this.display.getControl(l4);
        if (control == null) {
            return null;
        }
        return control.wmMeasureChild(l2, l3);
    }

    LRESULT WM_MENUCHAR(long l2, long l3) {
        int n = OS.HIWORD(l2);
        if (n == 0 || n == 8192) {
            this.display.mnemonicKeyHit = false;
            return new LRESULT(OS.MAKELRESULT(0, 1));
        }
        return null;
    }

    LRESULT WM_MENUSELECT(long l2, long l3) {
        int n = OS.HIWORD(l2);
        Shell shell = this.getShell();
        OS.KillTimer(this.handle, 110L);
        if (this.activeMenu != null) {
            this.activeMenu.hideCurrentToolTip();
        }
        if (n == 65535 && l3 == 0L) {
            for (Menu menu = shell.activeMenu; menu != null; menu = menu.getParentMenu()) {
                this.display.mnemonicKeyHit = true;
                menu.sendEvent(23);
                if (menu.isDisposed()) break;
            }
            shell.activeMenu = null;
            return null;
        }
        if ((n & 0x2000) != 0) {
            return null;
        }
        if ((n & 0x80) != 0) {
            MenuItem menuItem = null;
            Decorations decorations = this.menuShell();
            if ((n & 0x10) != 0) {
                Menu menu;
                int n2 = OS.LOWORD(l2);
                MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
                mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
                mENUITEMINFO.fMask = 4;
                if (OS.GetMenuItemInfo(l3, n2, true, mENUITEMINFO) && (menu = decorations.findMenu(mENUITEMINFO.hSubMenu)) != null) {
                    menuItem = menu.cascade;
                    this.activeMenu = menu;
                    this.activeMenu.selectedMenuItem = menu.cascade;
                    OS.SetTimer(this.handle, 110L, 1045, 0L);
                }
            } else {
                Menu menu = decorations.findMenu(l3);
                if (menu != null) {
                    int n3 = OS.LOWORD(l2);
                    menuItem = this.display.getMenuItem(n3);
                }
                Menu menu2 = this.activeMenu = menu == null ? this.menu : menu;
                if (menuItem != null && this.activeMenu != null) {
                    this.activeMenu.selectedMenuItem = menuItem;
                    OS.SetTimer(this.handle, 110L, 1045, 0L);
                }
            }
            if (menuItem != null) {
                menuItem.sendEvent(30);
            }
        }
        return null;
    }

    LRESULT WM_MOUSEACTIVATE(long l2, long l3) {
        return null;
    }

    LRESULT WM_MOUSEHOVER(long l2, long l3) {
        return this.wmMouseHover(this.handle, l2, l3);
    }

    LRESULT WM_MOUSELEAVE(long l2, long l3) {
        this.getShell().fixToolTip();
        return this.wmMouseLeave(this.handle, l2, l3);
    }

    LRESULT WM_MOUSEMOVE(long l2, long l3) {
        return this.wmMouseMove(this.handle, l2, l3);
    }

    LRESULT WM_MOUSEWHEEL(long l2, long l3) {
        return this.wmMouseWheel(this.handle, l2, l3);
    }

    LRESULT WM_MOUSEHWHEEL(long l2, long l3) {
        return this.wmMouseHWheel(this.handle, l2, l3);
    }

    LRESULT WM_MOVE(long l2, long l3) {
        this.state |= 0x10000;
        if (this.findImageControl() != null) {
            if (this != this.getShell()) {
                this.redrawChildren();
            }
        } else if ((this.state & 0x100) != 0 && OS.IsAppThemed() && OS.IsWindowVisible(this.handle) && this.findThemeControl() != null) {
            this.redrawChildren();
        }
        if ((this.state & 0x20000) == 0) {
            this.sendEvent(10);
        }
        return null;
    }

    LRESULT WM_NCACTIVATE(long l2, long l3) {
        return null;
    }

    LRESULT WM_NCCALCSIZE(long l2, long l3) {
        return null;
    }

    LRESULT WM_NCHITTEST(long l2, long l3) {
        if (!OS.IsWindowEnabled(this.handle)) {
            return null;
        }
        if (this != null) {
            return new LRESULT(-1L);
        }
        return null;
    }

    LRESULT WM_NCLBUTTONDOWN(long l2, long l3) {
        return null;
    }

    LRESULT WM_NCPAINT(long l2, long l3) {
        return this.wmNCPaint(this.handle, l2, l3);
    }

    LRESULT WM_NOTIFY(long l2, long l3) {
        NMHDR nMHDR = new NMHDR();
        OS.MoveMemory(nMHDR, l3, NMHDR.sizeof);
        return this.wmNotify(nMHDR, l2, l3);
    }

    LRESULT WM_PAINT(long l2, long l3) {
        if ((this.state & 0x1000) != 0) {
            return LRESULT.ZERO;
        }
        return this.wmPaint(this.handle, l2, l3);
    }

    LRESULT WM_PARENTNOTIFY(long l2, long l3) {
        return null;
    }

    LRESULT WM_PASTE(long l2, long l3) {
        return null;
    }

    LRESULT WM_PRINT(long l2, long l3) {
        return this.wmPrint(this.handle, l2, l3);
    }

    LRESULT WM_PRINTCLIENT(long l2, long l3) {
        return null;
    }

    LRESULT WM_QUERYENDSESSION(long l2, long l3) {
        return null;
    }

    LRESULT WM_QUERYOPEN(long l2, long l3) {
        return null;
    }

    LRESULT WM_RBUTTONDBLCLK(long l2, long l3) {
        return this.wmRButtonDblClk(this.handle, l2, l3);
    }

    LRESULT WM_RBUTTONDOWN(long l2, long l3) {
        return this.wmRButtonDown(this.handle, l2, l3);
    }

    LRESULT WM_RBUTTONUP(long l2, long l3) {
        return this.wmRButtonUp(this.handle, l2, l3);
    }

    LRESULT WM_SETCURSOR(long l2, long l3) {
        short s = (short)OS.LOWORD(l3);
        if (s == 1) {
            Control control = this.display.getControl(l2);
            if (control == null) {
                return null;
            }
            Cursor cursor = control.findCursor();
            if (cursor != null) {
                OS.SetCursor(cursor.handle);
                return LRESULT.ONE;
            }
        }
        return null;
    }

    LRESULT WM_SETFOCUS(long l2, long l3) {
        return this.wmSetFocus(this.handle, l2, l3);
    }

    LRESULT WM_SETTINGCHANGE(long l2, long l3) {
        return null;
    }

    LRESULT WM_SETFONT(long l2, long l3) {
        return null;
    }

    LRESULT WM_SETREDRAW(long l2, long l3) {
        return null;
    }

    LRESULT WM_SHOWWINDOW(long l2, long l3) {
        return null;
    }

    LRESULT WM_SIZE(long l2, long l3) {
        this.state |= 0x40000;
        if ((this.state & 0x80000) == 0) {
            this.sendEvent(11);
        }
        return null;
    }

    LRESULT WM_SYSCHAR(long l2, long l3) {
        return this.wmSysChar(this.handle, l2, l3);
    }

    LRESULT WM_SYSCOLORCHANGE(long l2, long l3) {
        return null;
    }

    /*
     * Handled duff style switch with additional control
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    LRESULT WM_SYSCOMMAND(long l2, long l3) {
        Decorations decorations;
        if ((l2 & 0xF000L) == 0L) {
            Decorations decorations2 = this.menuShell();
            if (!decorations2.isEnabled()) return LRESULT.ZERO;
            MenuItem menuItem = this.display.getMenuItem(OS.LOWORD(l2));
            if (menuItem == null) return LRESULT.ZERO;
            menuItem.wmCommandChild(l2, l3);
            return LRESULT.ZERO;
        }
        int n = (int)l2 & 0xFFF0;
        int n2 = 0;
        block5: do {
            switch (n2 == 0 ? n : n2) {
                case 61696: {
                    int n3;
                    int n4;
                    MenuItem[] menuItemArray;
                    char c2;
                    if (l3 == 0L) {
                        n2 = 61552;
                        decorations = this.menuShell();
                        Menu menu = decorations.getMenuBar();
                        if (menu != null) continue block5;
                        n2 = 61552;
                        Control control = this.display._getFocusControl();
                        if (control == null) continue block5;
                        if (!control.hooks(1)) {
                            n2 = 61552;
                            if (!control.hooks(2)) continue block5;
                        }
                        this.display.mnemonicKeyHit = false;
                        return LRESULT.ZERO;
                    }
                    if (!this.hooks(1)) {
                        n2 = 61552;
                        if (!this.hooks(2)) continue block5;
                    }
                    if (l3 == 32L) {
                        n2 = 61552;
                        continue block5;
                    }
                    decorations = this.menuShell();
                    Menu menu = decorations.getMenuBar();
                    if (menu != null) {
                        n2 = 61552;
                        c2 = (char)l3;
                        if (c2 == '\u0000') continue block5;
                        c2 = Character.toUpperCase(c2);
                        menuItemArray = menu.getItems();
                        n4 = menuItemArray.length;
                        n3 = 0;
                    } else {
                        this.display.mnemonicKeyHit = false;
                        n2 = 61552;
                        continue block5;
                    }
                }
                case 61472: {
                    this.menuShell().saveFocus();
                    return null;
                }
                default: {
                    return null;
                }
                while (true) {
                    char c;
                    int n3;
                    int n4;
                    MenuItem[] menuItemArray;
                    char c2;
                    n2 = 61552;
                    if (n3 >= n4) continue block5;
                    MenuItem menuItem = menuItemArray[n3];
                    String string = menuItem.getText();
                    char c3 = this.findMnemonic(string);
                    if (string.length() > 0 && c3 == '\u0000' && Character.toUpperCase(c = string.charAt(0)) == c2) {
                        this.display.mnemonicKeyHit = false;
                        return LRESULT.ZERO;
                    }
                    ++n3;
                }
                case 61552: 
                case 61568: 
            }
            break;
        } while (true);
        decorations = this.menuShell();
        if (!decorations.isEnabled()) return LRESULT.ZERO;
        if (decorations.isActive()) return null;
        return LRESULT.ZERO;
    }

    LRESULT WM_SYSKEYDOWN(long l2, long l3) {
        return this.wmSysKeyDown(this.handle, l2, l3);
    }

    LRESULT WM_SYSKEYUP(long l2, long l3) {
        return this.wmSysKeyUp(this.handle, l2, l3);
    }

    LRESULT WM_TABLET_FLICK(long l2, long l3) {
        if (!this.hooks(48) && !this.filters(48)) {
            return null;
        }
        Event event = new Event();
        FLICK_DATA fLICK_DATA = new FLICK_DATA();
        long[] lArray = new long[]{l2};
        OS.MoveMemory(fLICK_DATA, lArray, OS.FLICK_DATA_sizeof());
        FLICK_POINT fLICK_POINT = new FLICK_POINT();
        lArray[0] = l3;
        OS.MoveMemory(fLICK_POINT, lArray, OS.FLICK_POINT_sizeof());
        switch (fLICK_DATA.iFlickDirection) {
            case 0: {
                event.xDirection = 1;
                event.yDirection = 0;
                break;
            }
            case 1: {
                event.xDirection = 1;
                event.yDirection = -1;
                break;
            }
            case 2: {
                event.xDirection = 0;
                event.yDirection = -1;
                break;
            }
            case 3: {
                event.xDirection = -1;
                event.yDirection = -1;
                break;
            }
            case 4: {
                event.xDirection = -1;
                event.yDirection = 0;
                break;
            }
            case 5: {
                event.xDirection = -1;
                event.yDirection = 1;
                break;
            }
            case 6: {
                event.xDirection = 0;
                event.yDirection = 1;
                break;
            }
            case 7: {
                event.xDirection = 1;
                event.yDirection = 1;
            }
        }
        event.setLocationInPixels(fLICK_POINT.x, fLICK_POINT.y);
        event.type = 48;
        event.detail = 16;
        this.setInputState(event, 48);
        this.sendEvent(48, event);
        return event.doit ? null : LRESULT.ONE;
    }

    LRESULT WM_TOUCH(long l2, long l3) {
        LRESULT lRESULT = null;
        if (this.hooks(47) || this.filters(47)) {
            int n = OS.LOWORD(l2);
            long l4 = OS.GetProcessHeap();
            long l5 = OS.HeapAlloc(l4, 8, n * TOUCHINPUT.sizeof);
            if (l5 != 0L) {
                if (OS.GetTouchInputInfo(l3, n, l5, TOUCHINPUT.sizeof)) {
                    TOUCHINPUT[] tOUCHINPUTArray = new TOUCHINPUT[n];
                    for (int i = 0; i < n; ++i) {
                        tOUCHINPUTArray[i] = new TOUCHINPUT();
                        OS.MoveMemory(tOUCHINPUTArray[i], l5 + (long)(i * TOUCHINPUT.sizeof), TOUCHINPUT.sizeof);
                    }
                    this.sendTouchEvent(tOUCHINPUTArray);
                    OS.CloseTouchInputHandle(l3);
                    lRESULT = LRESULT.ZERO;
                }
                OS.HeapFree(l4, 0, l5);
            }
        }
        return lRESULT;
    }

    LRESULT WM_TIMER(long l2, long l3) {
        if (l2 == 110L && this.activeMenu != null) {
            OS.KillTimer(this.handle, 110L);
            this.activeMenu.wmTimer(l2, l3);
        }
        return null;
    }

    LRESULT WM_UNDO(long l2, long l3) {
        return null;
    }

    LRESULT WM_UPDATEUISTATE(long l2, long l3) {
        return null;
    }

    LRESULT WM_VSCROLL(long l2, long l3) {
        Control control = this.display.getControl(l3);
        if (control == null) {
            return null;
        }
        return control.wmScrollChild(l2, l3);
    }

    LRESULT WM_WINDOWPOSCHANGED(long l2, long l3) {
        Display display = this.display;
        ++display.resizeCount;
        long l4 = this.callWindowProc(this.handle, 71, l2, l3);
        LRESULT lRESULT = l4 == 0L ? LRESULT.ZERO : new LRESULT(l4);
        Display display2 = this.display;
        --display2.resizeCount;
        return lRESULT;
    }

    LRESULT WM_WINDOWPOSCHANGING(long l2, long l3) {
        Shell shell;
        if (this <= 0 && (shell = this.getShell()) != this) {
            WINDOWPOS wINDOWPOS = new WINDOWPOS();
            OS.MoveMemory(wINDOWPOS, l3, WINDOWPOS.sizeof);
            if ((wINDOWPOS.flags & 2) == 0 || (wINDOWPOS.flags & 1) == 0) {
                RECT rECT = new RECT();
                OS.GetWindowRect(this.topHandle(), rECT);
                int n = rECT.right - rECT.left;
                int n2 = rECT.bottom - rECT.top;
                if (n != 0 && n2 != 0) {
                    long l4 = this.parent == null ? 0L : this.parent.handle;
                    OS.MapWindowPoints(0L, l4, rECT, 2);
                    long l5 = OS.CreateRectRgn(rECT.left, rECT.top, rECT.right, rECT.bottom);
                    long l6 = OS.CreateRectRgn(wINDOWPOS.x, wINDOWPOS.y, wINDOWPOS.x + wINDOWPOS.cx, wINDOWPOS.y + wINDOWPOS.cy);
                    OS.CombineRgn(l5, l5, l6, 4);
                    int n3 = 1157;
                    OS.RedrawWindow(l4, null, l5, 1157);
                    OS.DeleteObject(l5);
                    OS.DeleteObject(l6);
                }
            }
        }
        return null;
    }

    LRESULT WM_XBUTTONDBLCLK(long l2, long l3) {
        return this.wmXButtonDblClk(this.handle, l2, l3);
    }

    LRESULT WM_XBUTTONDOWN(long l2, long l3) {
        return this.wmXButtonDown(this.handle, l2, l3);
    }

    LRESULT WM_XBUTTONUP(long l2, long l3) {
        return this.wmXButtonUp(this.handle, l2, l3);
    }

    LRESULT wmColorChild(long l2, long l3) {
        Control control = this.findBackgroundControl();
        if (control == null) {
            if ((this.state & 0x100) != 0 && OS.IsAppThemed() && (control = this.findThemeControl()) != null) {
                RECT rECT = new RECT();
                OS.GetClientRect(this.handle, rECT);
                OS.SetTextColor(l2, this.getForegroundPixel());
                OS.SetBkColor(l2, this.getBackgroundPixel());
                this.fillThemeBackground(l2, control, rECT);
                OS.SetBkMode(l2, 1);
                return new LRESULT(OS.GetStockObject(5));
            }
            if (this.foreground == -1) {
                return null;
            }
        }
        if (control == null) {
            control = this;
        }
        int n = this.getForegroundPixel();
        int n2 = control.getBackgroundPixel();
        OS.SetTextColor(l2, n);
        OS.SetBkColor(l2, n2);
        if (control.backgroundImage != null) {
            RECT rECT = new RECT();
            OS.GetClientRect(this.handle, rECT);
            long l4 = control.handle;
            long l5 = control.backgroundImage.handle;
            OS.MapWindowPoints(this.handle, l4, rECT, 2);
            POINT pOINT = new POINT();
            OS.GetWindowOrgEx(l2, pOINT);
            OS.SetBrushOrgEx(l2, -rECT.left - pOINT.x, -rECT.top - pOINT.y, pOINT);
            long l6 = this.findBrush(l5, 3);
            if ((this.state & 0x200) != 0) {
                long l7 = OS.SelectObject(l2, l6);
                OS.MapWindowPoints(l4, this.handle, rECT, 2);
                OS.PatBlt(l2, rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top, 15728673);
                OS.SelectObject(l2, l7);
            }
            OS.SetBkMode(l2, 1);
            return new LRESULT(l6);
        }
        long l8 = this.findBrush(n2, 0);
        if ((this.state & 0x200) != 0) {
            RECT rECT = new RECT();
            OS.GetClientRect(this.handle, rECT);
            long l9 = OS.SelectObject(l2, l8);
            OS.PatBlt(l2, rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top, 15728673);
            OS.SelectObject(l2, l9);
        }
        return new LRESULT(l8);
    }

    LRESULT wmCommandChild(long l2, long l3) {
        return null;
    }

    LRESULT wmDrawChild(long l2, long l3) {
        return null;
    }

    LRESULT wmMeasureChild(long l2, long l3) {
        return null;
    }

    LRESULT wmNotify(NMHDR nMHDR, long l2, long l3) {
        Control control = this.display.getControl(nMHDR.hwndFrom);
        if (control == null) {
            return null;
        }
        return control.wmNotifyChild(nMHDR, l2, l3);
    }

    LRESULT wmNotifyChild(NMHDR nMHDR, long l2, long l3) {
        return null;
    }

    LRESULT wmScrollChild(long l2, long l3) {
        return null;
    }
}

