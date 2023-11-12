/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.ExceptionStash;
import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.internal.WidgetSpy;
import org.eclipse.swt.internal.win32.INITCOMMONCONTROLSEX;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.EventTable;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.TypedListener;

public abstract class Widget {
    int style;
    int state;
    Display display;
    EventTable eventTable;
    Object data;
    static final int DISPOSED = 1;
    static final int CANVAS = 2;
    static final int KEYED_DATA = 4;
    static final int DISABLED = 8;
    static final int HIDDEN = 16;
    static final int LAYOUT_NEEDED = 32;
    static final int LAYOUT_CHANGED = 64;
    static final int LAYOUT_CHILD = 128;
    static final int THEME_BACKGROUND = 256;
    static final int DRAW_BACKGROUND = 512;
    static final int PARENT_BACKGROUND = 1024;
    static final int RELEASED = 2048;
    static final int DISPOSE_SENT = 4096;
    static final int TRACK_MOUSE = 8192;
    static final int FOREIGN_HANDLE = 16384;
    static final int DRAG_DETECT = 32768;
    static final int MOVE_OCCURRED = 65536;
    static final int MOVE_DEFERRED = 131072;
    static final int RESIZE_OCCURRED = 262144;
    static final int RESIZE_DEFERRED = 524288;
    static final int IGNORE_WM_CHANGEUISTATE = 0x100000;
    static final int SKIN_NEEDED = 0x200000;
    static final int HAS_AUTO_DIRECTION = 0x400000;
    static final int MOUSE_OVER = 0x800000;
    static final int CUSTOM_DRAW_ITEM = 0x1000000;
    static final int DEFAULT_WIDTH = 64;
    static final int DEFAULT_HEIGHT = 64;
    static final char LRE = '\u202a';
    static final char RLE = '\u202b';
    static final int AUTO_TEXT_DIRECTION = 0x6000000;

    Widget() {
        this.notifyCreationTracker();
    }

    public Widget(Widget widget, int n) {
        this.checkSubclass();
        this.checkParent(widget);
        this.style = n;
        this.display = widget.display;
        this.reskinWidget();
        this.notifyCreationTracker();
    }

    void _addListener(int n, Listener listener) {
        if (this.eventTable == null) {
            this.eventTable = new EventTable();
        }
        this.eventTable.hook(n, listener);
    }

    void _removeListener(int n, Listener listener) {
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(n, listener);
    }

    public void addListener(int n, Listener listener) {
        this.checkWidget();
        if (listener == null) {
            this.error(4);
        }
        this._addListener(n, listener);
    }

    public void addDisposeListener(DisposeListener disposeListener) {
        this.checkWidget();
        if (disposeListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(disposeListener);
        this.addListener(12, typedListener);
    }

    long callWindowProc(long l2, int n, long l3, long l4) {
        return 0L;
    }

    static int checkBits(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8 = n2 | n3 | n4 | n5 | n6 | n7;
        if ((n & n8) == 0) {
            n |= n2;
        }
        if ((n & n2) != 0) {
            n = n & ~n8 | n2;
        }
        if ((n & n3) != 0) {
            n = n & ~n8 | n3;
        }
        if ((n & n4) != 0) {
            n = n & ~n8 | n4;
        }
        if ((n & n5) != 0) {
            n = n & ~n8 | n5;
        }
        if ((n & n6) != 0) {
            n = n & ~n8 | n6;
        }
        if ((n & n7) != 0) {
            n = n & ~n8 | n7;
        }
        return n;
    }

    void checkOrientation(Widget widget) {
        this.style &= 0xF7FFFFFF;
        if ((this.style & 0x6000000) == 0 && widget != null) {
            if ((widget.style & 0x2000000) != 0) {
                this.style |= 0x2000000;
            }
            if ((widget.style & 0x4000000) != 0) {
                this.style |= 0x4000000;
            }
        }
        this.style = Widget.checkBits(this.style, 0x2000000, 0x4000000, 0, 0, 0, 0);
    }

    void checkOpened() {
    }

    void checkParent(Widget widget) {
        if (widget == null) {
            this.error(4);
        }
        if (widget != false) {
            this.error(5);
        }
        widget.checkWidget();
        widget.checkOpened();
    }

    void maybeEnableDarkSystemTheme(long l2) {
        if (this.display.useDarkModeExplorerTheme) {
            OS.AllowDarkModeForWindow(l2, true);
            OS.SetWindowTheme(l2, Display.EXPLORER, null);
        }
    }

    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    protected void checkWidget() {
        Display display = this.display;
        if (display == null) {
            this.error(24);
        }
        if (display.thread != Thread.currentThread()) {
            this.error(22);
        }
        if ((this.state & 1) != 0) {
            this.error(24);
        }
    }

    void destroyWidget() {
        this.releaseHandle();
    }

    public void dispose() {
        if (this != false) {
            return;
        }
        if (!this.isValidThread()) {
            this.error(22);
        }
        this.release(true);
    }

    boolean dragDetect(long l2, int n, int n2, boolean bl, boolean[] blArray, boolean[] blArray2) {
        if (blArray2 != null) {
            blArray2[0] = false;
        }
        if (blArray != null) {
            blArray[0] = true;
        }
        POINT pOINT = new POINT();
        pOINT.x = n;
        pOINT.y = n2;
        OS.ClientToScreen(l2, pOINT);
        return OS.DragDetect(l2, pOINT);
    }

    void error(int n) {
        SWT.error(n);
    }

    boolean filters(int n) {
        return this.display.filters(n);
    }

    Widget findItem(long l2) {
        return null;
    }

    char[] fixMnemonic(String string) {
        return this.fixMnemonic(string, false, false);
    }

    char[] fixMnemonic(String string, boolean bl) {
        return this.fixMnemonic(string, bl, false);
    }

    char[] fixMnemonic(String string, boolean bl, boolean bl2) {
        char[] cArray = new char[string.length() + 1];
        string.getChars(0, string.length(), cArray, 0);
        int n = 0;
        int n2 = 0;
        while (n < cArray.length) {
            if (cArray[n] == '&') {
                if (n + 1 < cArray.length && cArray[n + 1] == '&') {
                    cArray[n2++] = bl ? 32 : cArray[n];
                    ++n;
                }
                ++n;
                continue;
            }
            if (cArray[n] == '(' && bl2 && n + 4 == string.length() && cArray[n + 1] == '&' && cArray[n + 3] == ')') {
                if (bl) {
                    cArray[n2++] = 32;
                }
                n += 4;
                continue;
            }
            cArray[n2++] = cArray[n++];
        }
        while (n2 < cArray.length) {
            cArray[n2++] = '\u0000';
        }
        return cArray;
    }

    public Object getData() {
        this.checkWidget();
        return (this.state & 4) != 0 ? ((Object[])this.data)[0] : this.data;
    }

    public Object getData(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if ((this.state & 4) != 0) {
            Object[] objectArray = (Object[])this.data;
            for (int i = 1; i < objectArray.length; i += 2) {
                if (!string.equals(objectArray[i])) continue;
                return objectArray[i + 1];
            }
        }
        return null;
    }

    public Display getDisplay() {
        Display display = this.display;
        if (display == null) {
            this.error(24);
        }
        return display;
    }

    public Listener[] getListeners(int n) {
        this.checkWidget();
        if (this.eventTable == null) {
            return new Listener[0];
        }
        return this.eventTable.getListeners(n);
    }

    Menu getMenu() {
        return null;
    }

    String getName() {
        String string = this.getClass().getName();
        int n = string.lastIndexOf(46);
        if (n == -1) {
            return string;
        }
        return string.substring(n + 1, string.length());
    }

    String getNameText() {
        return "";
    }

    public int getStyle() {
        this.checkWidget();
        return this.style;
    }

    public boolean isAutoDirection() {
        return (this.state & 0x400000) != 0;
    }

    public boolean isListening(int n) {
        this.checkWidget();
        return this.hooks(n);
    }

    boolean isValidSubclass() {
        return Display.isValidClass(this.getClass());
    }

    boolean isValidThread() {
        return this.getDisplay().isValidThread();
    }

    void mapEvent(long l2, Event event) {
    }

    GC new_GC(GCData gCData) {
        return null;
    }

    public void notifyListeners(int n, Event event) {
        this.checkWidget();
        if (event == null) {
            event = new Event();
        }
        this.sendEvent(n, event);
    }

    void postEvent(int n) {
        this.sendEvent(n, null, false);
    }

    void postEvent(int n, Event event) {
        this.sendEvent(n, event, false);
    }

    void release(boolean bl) {
        block14: {
            ExceptionStash exceptionStash;
            block15: {
                exceptionStash = new ExceptionStash();
                Throwable throwable = null;
                try {
                    if ((this.state & 0x1000) == 0) {
                        this.state |= 0x1000;
                        try {
                            this.sendEvent(12);
                        }
                        catch (Error | RuntimeException throwable2) {
                            exceptionStash.stash(throwable2);
                        }
                    }
                    if ((this.state & 1) == 0) {
                        try {
                            this.releaseChildren(bl);
                        }
                        catch (Error | RuntimeException throwable3) {
                            exceptionStash.stash(throwable3);
                        }
                    }
                    if ((this.state & 0x800) == 0) {
                        this.state |= 0x800;
                        if (bl) {
                            this.releaseParent();
                            this.releaseWidget();
                            this.destroyWidget();
                        } else {
                            this.releaseWidget();
                            this.releaseHandle();
                        }
                    }
                    this.notifyDisposalTracker();
                    if (exceptionStash == null) break block14;
                    if (throwable == null) break block15;
                }
                catch (Throwable throwable4) {
                    throwable = throwable4;
                    throw throwable4;
                }
                try {
                    exceptionStash.close();
                }
                catch (Throwable throwable5) {
                    throwable.addSuppressed(throwable5);
                }
                break block14;
            }
            exceptionStash.close();
        }
    }

    void releaseChildren(boolean bl) {
    }

    void releaseHandle() {
        this.state |= 1;
        this.display = null;
    }

    void releaseParent() {
    }

    void releaseWidget() {
        this.eventTable = null;
        this.data = null;
    }

    public void removeListener(int n, Listener listener) {
        this.checkWidget();
        if (listener == null) {
            this.error(4);
        }
        this._removeListener(n, listener);
    }

    protected void removeListener(int n, SWTEventListener sWTEventListener) {
        this.checkWidget();
        if (sWTEventListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(n, sWTEventListener);
    }

    public void removeDisposeListener(DisposeListener disposeListener) {
        this.checkWidget();
        if (disposeListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(12, disposeListener);
    }

    public void reskin(int n) {
        this.checkWidget();
        this.reskinWidget();
        if ((n & 1) != 0) {
            this.reskinChildren(n);
        }
    }

    void reskinChildren(int n) {
    }

    void reskinWidget() {
        if ((this.state & 0x200000) != 0x200000) {
            this.state |= 0x200000;
            this.display.addSkinnableWidget(this);
        }
    }

    boolean sendDragEvent(int n, int n2, int n3) {
        Event event = new Event();
        event.button = n;
        event.setLocationInPixels(n2, n3);
        this.setInputState(event, 29);
        this.postEvent(29, event);
        return this != false && event.doit;
    }

    boolean sendDragEvent(int n, int n2, int n3, int n4) {
        Event event = new Event();
        event.button = n;
        event.setLocationInPixels(n3, n4);
        event.stateMask = n2;
        this.postEvent(29, event);
        return this != false && event.doit;
    }

    void sendEvent(Event event) {
        Display display = event.display;
        if (!display.filterEvent(event) && this.eventTable != null) {
            display.sendEvent(this.eventTable, event);
        }
    }

    void sendEvent(int n) {
        this.sendEvent(n, null, true);
    }

    void sendEvent(int n, Event event) {
        this.sendEvent(n, event, true);
    }

    void sendEvent(int n, Event event, boolean bl) {
        if (this.eventTable == null && !this.display.filters(n)) {
            return;
        }
        if (event == null) {
            event = new Event();
        }
        event.type = n;
        event.display = this.display;
        event.widget = this;
        if (event.time == 0) {
            event.time = this.display.getLastEventTime();
        }
        if (bl) {
            this.sendEvent(event);
        } else {
            this.display.postEvent(event);
        }
    }

    void sendSelectionEvent(int n) {
        this.sendSelectionEvent(n, null, false);
    }

    void sendSelectionEvent(int n, Event event, boolean bl) {
        if (this.eventTable == null && !this.display.filters(n)) {
            return;
        }
        if (event == null) {
            event = new Event();
        }
        this.setInputState(event, n);
        this.sendEvent(n, event, bl);
    }

    boolean sendMouseEvent(int n, int n2, long l2, long l3) {
        return this.sendMouseEvent(n, n2, this.display.getClickCount(n, n2, l2, l3), 0, false, l2, l3);
    }

    boolean sendMouseEvent(int n, int n2, int n3, int n4, boolean bl, long l2, long l3) {
        Widget widget = this;
        if (n != null && !this.filters(n)) {
            return true;
        }
        Event event = new Event();
        event.button = n2;
        event.detail = n4;
        event.count = n3;
        event.setLocationInPixels(OS.GET_X_LPARAM(l3), OS.GET_Y_LPARAM(l3));
        this.setInputState(event, n);
        this.mapEvent(l2, event);
        if (bl) {
            this.sendEvent(n, event);
            if (this != false) {
                return false;
            }
        } else {
            this.postEvent(n, event);
        }
        return event.doit;
    }

    public void setData(Object object) {
        this.checkWidget();
        if ((this.state & 4) != 0) {
            ((Object[])this.data)[0] = object;
        } else {
            this.data = object;
        }
    }

    public void setData(String string, Object object) {
        int n;
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        Object[] objectArray = null;
        if ((this.state & 4) != 0) {
            objectArray = (Object[])this.data;
            for (n = 1; n < objectArray.length && !string.equals(objectArray[n]); n += 2) {
            }
        }
        if (object != null) {
            if ((this.state & 4) != 0) {
                if (n == objectArray.length) {
                    Object[] objectArray2 = new Object[objectArray.length + 2];
                    System.arraycopy(objectArray, 0, objectArray2, 0, objectArray.length);
                    this.data = objectArray2;
                    objectArray = objectArray2;
                }
            } else {
                this.data = objectArray = new Object[]{this.data, null, null};
                this.state |= 4;
            }
            objectArray[n] = string;
            objectArray[n + 1] = object;
        } else if ((this.state & 4) != 0 && n != objectArray.length) {
            int n2 = objectArray.length - 2;
            if (n2 == 1) {
                this.data = objectArray[0];
                this.state &= 0xFFFFFFFB;
            } else {
                Object[] objectArray3 = new Object[n2];
                System.arraycopy(objectArray, 0, objectArray3, 0, n);
                System.arraycopy(objectArray, n + 2, objectArray3, n, n2 - n);
                this.data = objectArray3;
            }
        }
        if (string.equals("org.eclipse.swt.skin.class") || string.equals("org.eclipse.swt.skin.id")) {
            this.reskin(1);
        }
    }

    boolean sendFocusEvent(int n) {
        this.sendEvent(n);
        return true;
    }

    int setLocationMask(Event event, int n, long l2, long l3) {
        int n2 = 0;
        if (this.display.lastVirtual) {
            switch (this.display.lastKey) {
                case 16: {
                    if (OS.GetKeyState(160) < 0) {
                        n2 = 16384;
                    }
                    if (OS.GetKeyState(161) >= 0) break;
                    n2 = 131072;
                    break;
                }
                case 144: {
                    n2 = 2;
                    break;
                }
                case 17: 
                case 18: {
                    n2 = (l3 & 0x1000000L) == 0L ? 16384 : 131072;
                    break;
                }
                case 33: 
                case 34: 
                case 35: 
                case 36: 
                case 37: 
                case 38: 
                case 39: 
                case 40: 
                case 45: 
                case 46: {
                    if ((l3 & 0x1000000L) != 0L) break;
                    n2 = 2;
                }
            }
            if (this.display.numpadKey(this.display.lastKey) != 0) {
                n2 = 2;
            }
        } else if (this.display.lastKey == 0x1000050) {
            n2 = 2;
        }
        event.keyLocation = n2;
        return event.keyLocation;
    }

    boolean setTabGroupFocus() {
        return this.setTabItemFocus();
    }

    boolean setTabItemFocus() {
        return false;
    }

    boolean showMenu(int n, int n2) {
        return this.showMenu(n, n2, 0);
    }

    public String toString() {
        String string = "*Disposed*";
        if (this != false) {
            string = "*Wrong Thread*";
            if (this.isValidThread()) {
                string = this.getNameText();
            }
        }
        return this.getName() + " {" + string;
    }

    void updateMenuLocation(Event event) {
    }

    LRESULT wmCaptureChanged(long l2, long l3, long l4) {
        this.display.captureChanged = true;
        return null;
    }

    LRESULT wmChar(long l2, long l3, long l4) {
        this.display.lastAscii = (int)l3;
        this.display.lastNull = l3 == 0L;
        Widget widget = this;
        boolean bl = true;
        int n = 258;
        if (l3 == l4) {
            return LRESULT.ONE;
        }
        return null;
    }

    LRESULT wmContextMenu(long l2, long l3, long l4) {
        if (l3 != l2) {
            return null;
        }
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        if (l4 != -1L) {
            POINT pOINT = new POINT();
            OS.POINTSTOPOINT(pOINT, l4);
            n = pOINT.x;
            n2 = pOINT.y;
            n3 = 0;
            OS.ScreenToClient(l2, pOINT);
            RECT rECT = new RECT();
            OS.GetClientRect(l2, rECT);
            if (!OS.PtInRect(rECT, pOINT)) {
                return null;
            }
        } else {
            int n4 = OS.GetMessagePos();
            n = OS.GET_X_LPARAM(n4);
            n2 = OS.GET_Y_LPARAM(n4);
            n3 = 1;
        }
        Widget widget = this;
        int n5 = n;
        return n2 == n3 ? LRESULT.ZERO : null;
    }

    LRESULT wmIMEChar(long l2, long l3, long l4) {
        Display display = this.display;
        display.lastKey = 0;
        display.lastAscii = (int)l3;
        Display display2 = display;
        Display display3 = display;
        Display display4 = display;
        boolean bl = false;
        display4.lastDead = false;
        display3.lastNull = false;
        display2.lastVirtual = false;
        Widget widget = this;
        boolean bl2 = true;
        int n = 646;
        if (l3 == l4) {
            return LRESULT.ONE;
        }
        this.sendKeyEvent(2, 646, l3, l4);
        Display display5 = display;
        Display display6 = display;
        boolean bl3 = false;
        display6.lastAscii = 0;
        display5.lastKey = 0;
        return LRESULT.ONE;
    }

    LRESULT wmKeyDown(long l2, long l3, long l4) {
        switch ((int)l3) {
            case 16: 
            case 17: 
            case 18: 
            case 20: 
            case 144: 
            case 145: {
                if ((l4 & 0x40000000L) == 0L) break;
                return null;
            }
        }
        Display display = this.display;
        Display display2 = this.display;
        boolean bl = false;
        display2.lastKey = 0;
        display.lastAscii = 0;
        Display display3 = this.display;
        Display display4 = this.display;
        Display display5 = this.display;
        boolean bl2 = false;
        display5.lastDead = false;
        display4.lastNull = false;
        display3.lastVirtual = false;
        int n = OS.MapVirtualKey((int)l3, 2);
        if (2534 <= n && n <= 2543 || 2406 <= n && n <= 2415) {
            n = (int)l3;
        }
        if ((n & Integer.MIN_VALUE) != 0) {
            return null;
        }
        MSG mSG = new MSG();
        int n2 = 2;
        if (OS.PeekMessage(mSG, l2, 259, 259, 2)) {
            this.display.lastDead = true;
            this.display.lastVirtual = n == 0;
            this.display.lastKey = this.display.lastVirtual ? (int)l3 : n;
            return null;
        }
        if (this != false) {
            return LRESULT.ONE;
        }
        boolean bl3 = this.display.lastVirtual = n == 0 || this.display.numpadKey((int)l3) != 0;
        if (this.display.lastVirtual) {
            this.display.lastKey = (int)l3;
            if (this.display.lastKey == 46) {
                this.display.lastAscii = 127;
            }
            if (96 <= this.display.lastKey && this.display.lastKey <= 111) {
                if (this.display.asciiKey(this.display.lastKey) != 0) {
                    return null;
                }
                this.display.lastAscii = this.display.numpadKey(this.display.lastKey);
            }
        } else {
            int n3;
            this.display.lastKey = (int)OS.CharLower((short)n);
            if (l3 == 3L) {
                this.display.lastVirtual = true;
            }
            if ((n3 = this.display.asciiKey((int)l3)) != 0) {
                if (n3 == 32) {
                    return null;
                }
                if (n3 != (int)l3) {
                    return null;
                }
                if (l3 == 3L) {
                    return null;
                }
            }
            if (OS.GetKeyState(17) >= 0) {
                return null;
            }
            if (OS.GetKeyState(16) < 0) {
                this.display.lastAscii = this.display.shiftedKey((int)l3);
                if (this.display.lastAscii == 0) {
                    this.display.lastAscii = n;
                }
            } else {
                this.display.lastAscii = (int)OS.CharLower((short)n);
            }
            if (this.display.lastAscii == 64) {
                return null;
            }
            this.display.lastAscii = this.display.controlKey(this.display.lastAscii);
        }
        Widget widget = this;
        boolean bl4 = true;
        int n4 = 256;
        if (l3 == l4) {
            return LRESULT.ONE;
        }
        return null;
    }

    LRESULT wmKeyUp(long l2, long l3, long l4) {
        Display display = this.display;
        Widget widget = this;
        if (2 != null && !display.filters(2)) {
            Display display2 = display;
            Display display3 = display;
            boolean bl = false;
            display3.lastAscii = 0;
            display2.lastKey = 0;
            Display display4 = display;
            Display display5 = display;
            Display display6 = display;
            boolean bl2 = false;
            display6.lastDead = false;
            display5.lastNull = false;
            display4.lastVirtual = false;
            return null;
        }
        int n = OS.MapVirtualKey((int)l3, 2);
        if ((n & Integer.MIN_VALUE) != 0) {
            return null;
        }
        if (display.lastDead) {
            return null;
        }
        boolean bl = display.lastVirtual = n == 0 || display.numpadKey((int)l3) != 0;
        if (display.lastVirtual) {
            display.lastKey = (int)l3;
        } else {
            if (l3 == 3L) {
                display.lastVirtual = true;
            }
            if (display.lastKey == 0) {
                display.lastAscii = 0;
                Display display7 = display;
                Display display8 = display;
                boolean bl3 = false;
                display8.lastDead = false;
                display7.lastNull = false;
                return null;
            }
        }
        LRESULT lRESULT = null;
        Widget widget2 = this;
        int n2 = 2;
        int n3 = 257;
        if (l3 == l4) {
            lRESULT = LRESULT.ONE;
        }
        Display display9 = display;
        Display display10 = display;
        boolean bl4 = false;
        display10.lastAscii = 0;
        display9.lastKey = 0;
        Display display11 = display;
        Display display12 = display;
        Display display13 = display;
        boolean bl5 = false;
        display13.lastDead = false;
        display12.lastNull = false;
        display11.lastVirtual = false;
        return lRESULT;
    }

    LRESULT wmKillFocus(long l2, long l3, long l4) {
        this.display.scrollRemainderEvt.x = 0;
        this.display.scrollRemainderEvt.y = 0;
        this.display.scrollRemainderBar.x = 0;
        this.display.scrollRemainderBar.y = 0;
        long l5 = this.callWindowProc(l2, 8, l3, l4);
        this.sendFocusEvent(16);
        if (this != false) {
            return LRESULT.ZERO;
        }
        if (l5 == 0L) {
            return LRESULT.ZERO;
        }
        return new LRESULT(l5);
    }

    LRESULT wmLButtonDblClk(long l2, long l3, long l4) {
        LRESULT lRESULT = null;
        Display display = this.display;
        display.captureChanged = false;
        this.sendMouseEvent(3, 1, l2, l4);
        lRESULT = this.sendMouseEvent(8, 1, l2, l4) ? new LRESULT(this.callWindowProc(l2, 515, l3, l4)) : LRESULT.ZERO;
        if (!display.captureChanged && this != false && OS.GetCapture() != l2) {
            OS.SetCapture(l2);
        }
        return lRESULT;
    }

    /*
     * Exception decompiling
     */
    LRESULT wmLButtonDown(long var1, long var3, long var5) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl72 : ALOAD - null : trying to set 0 previously set to 1
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

    LRESULT wmLButtonUp(long l2, long l3, long l4) {
        Display display = this.display;
        LRESULT lRESULT = null;
        lRESULT = this.sendMouseEvent(4, 1, l2, l4) ? new LRESULT(this.callWindowProc(l2, 514, l3, l4)) : LRESULT.ZERO;
        int n = 19;
        if (display.xMouse) {
            n |= 0x60;
        }
        if ((l3 & (long)n) == 0L && OS.GetCapture() == l2) {
            OS.ReleaseCapture();
        }
        return lRESULT;
    }

    LRESULT wmMButtonDblClk(long l2, long l3, long l4) {
        LRESULT lRESULT = null;
        Display display = this.display;
        display.captureChanged = false;
        this.sendMouseEvent(3, 2, l2, l4);
        lRESULT = this.sendMouseEvent(8, 2, l2, l4) ? new LRESULT(this.callWindowProc(l2, 521, l3, l4)) : LRESULT.ZERO;
        if (!display.captureChanged && this != false && OS.GetCapture() != l2) {
            OS.SetCapture(l2);
        }
        return lRESULT;
    }

    LRESULT wmMButtonDown(long l2, long l3, long l4) {
        LRESULT lRESULT = null;
        Display display = this.display;
        display.captureChanged = false;
        lRESULT = this.sendMouseEvent(3, 2, l2, l4) ? new LRESULT(this.callWindowProc(l2, 519, l3, l4)) : LRESULT.ZERO;
        if (!display.captureChanged && this != false && OS.GetCapture() != l2) {
            OS.SetCapture(l2);
        }
        return lRESULT;
    }

    LRESULT wmMButtonUp(long l2, long l3, long l4) {
        Display display = this.display;
        LRESULT lRESULT = null;
        lRESULT = this.sendMouseEvent(4, 2, l2, l4) ? new LRESULT(this.callWindowProc(l2, 520, l3, l4)) : LRESULT.ZERO;
        int n = 19;
        if (display.xMouse) {
            n |= 0x60;
        }
        if ((l3 & (long)n) == 0L && OS.GetCapture() == l2) {
            OS.ReleaseCapture();
        }
        return lRESULT;
    }

    LRESULT wmMouseHover(long l2, long l3, long l4) {
        if (!this.sendMouseEvent(32, 0, l2, l4)) {
            return LRESULT.ZERO;
        }
        return null;
    }

    LRESULT wmMouseLeave(long l2, long l3, long l4) {
        this.state &= 0xFF7FFFFF;
        Widget widget = this;
        if (7 != null && !this.filters(7)) {
            return null;
        }
        int n = OS.GetMessagePos();
        POINT pOINT = new POINT();
        OS.POINTSTOPOINT(pOINT, n);
        OS.ScreenToClient(l2, pOINT);
        l4 = OS.MAKELPARAM(pOINT.x, pOINT.y);
        if (!this.sendMouseEvent(7, 0, l2, l4)) {
            return LRESULT.ZERO;
        }
        return null;
    }

    /*
     * Exception decompiling
     */
    LRESULT wmMouseMove(long var1, long var3, long var5) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl137 : ALOAD - null : trying to set 0 previously set to 3
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

    LRESULT wmMouseWheel(long l2, long l3, long l4) {
        Widget widget = this;
        int n = 37;
        long l5 = l2;
        long l6 = l3;
        return l4 != null ? null : LRESULT.ZERO;
    }

    LRESULT wmMouseHWheel(long l2, long l3, long l4) {
        Widget widget = this;
        int n = 38;
        long l5 = l2;
        long l6 = l3;
        return l4 != null ? null : LRESULT.ZERO;
    }

    LRESULT wmNCPaint(long l2, long l3, long l4) {
        return null;
    }

    LRESULT wmPaint(long l2, long l3, long l4) {
        Widget widget = this;
        if (9 != null && !this.filters(9)) {
            return null;
        }
        long l5 = OS.CreateRectRgn(0, 0, 0, 0);
        OS.GetUpdateRgn(l2, l5, false);
        long l6 = this.callWindowProc(l2, 15, l3, l4);
        GCData gCData = new GCData();
        gCData.hwnd = l2;
        GC gC = this.new_GC(gCData);
        if (gC != null) {
            OS.HideCaret(l2);
            RECT rECT = new RECT();
            OS.GetRgnBox(l5, rECT);
            int n = rECT.right - rECT.left;
            int n2 = rECT.bottom - rECT.top;
            if (n != 0 && n2 != 0) {
                long l7 = gC.handle;
                OS.SelectClipRgn(l7, l5);
                OS.SetMetaRgn(l7);
                Event event = new Event();
                event.gc = gC;
                event.setBoundsInPixels(new Rectangle(rECT.left, rECT.top, n, n2));
                this.sendEvent(9, event);
                event.gc = null;
            }
            gC.dispose();
            OS.ShowCaret(l2);
        }
        OS.DeleteObject(l5);
        if (l6 == 0L) {
            return LRESULT.ZERO;
        }
        return new LRESULT(l6);
    }

    LRESULT wmPrint(long l2, long l3, long l4) {
        int n;
        if ((l4 & 2L) != 0L && OS.IsAppThemed() && ((n = OS.GetWindowLong(l2, -20)) & 0x200) != 0) {
            long l5 = this.callWindowProc(l2, 791, l3, l4);
            RECT rECT = new RECT();
            OS.GetWindowRect(l2, rECT);
            RECT rECT2 = rECT;
            rECT2.right -= rECT.left;
            RECT rECT3 = rECT;
            rECT3.bottom -= rECT.top;
            RECT rECT4 = rECT;
            RECT rECT5 = rECT;
            boolean bl = false;
            rECT5.top = 0;
            rECT4.left = 0;
            int n2 = OS.GetSystemMetrics(45);
            OS.ExcludeClipRect(l3, n2, n2, rECT.right - n2, rECT.bottom - n2);
            OS.DrawThemeBackground(this.display.hEditTheme(), l3, 1, 1, rECT, null);
            return new LRESULT(l5);
        }
        return null;
    }

    LRESULT wmRButtonDblClk(long l2, long l3, long l4) {
        LRESULT lRESULT = null;
        Display display = this.display;
        display.captureChanged = false;
        this.sendMouseEvent(3, 3, l2, l4);
        lRESULT = this.sendMouseEvent(8, 3, l2, l4) ? new LRESULT(this.callWindowProc(l2, 518, l3, l4)) : LRESULT.ZERO;
        if (!display.captureChanged && this != false && OS.GetCapture() != l2) {
            OS.SetCapture(l2);
        }
        return lRESULT;
    }

    LRESULT wmRButtonDown(long l2, long l3, long l4) {
        LRESULT lRESULT = null;
        Display display = this.display;
        display.captureChanged = false;
        lRESULT = this.sendMouseEvent(3, 3, l2, l4) ? new LRESULT(this.callWindowProc(l2, 516, l3, l4)) : LRESULT.ZERO;
        if (!display.captureChanged && this != false && OS.GetCapture() != l2) {
            OS.SetCapture(l2);
        }
        return lRESULT;
    }

    LRESULT wmRButtonUp(long l2, long l3, long l4) {
        Display display = this.display;
        LRESULT lRESULT = null;
        if (this.sendMouseEvent(4, 3, l2, l4)) {
            lRESULT = new LRESULT(this.callWindowProc(l2, 517, l3, l4));
        } else {
            OS.DefWindowProc(l2, 517, l3, l4);
            lRESULT = LRESULT.ZERO;
        }
        int n = 19;
        if (display.xMouse) {
            n |= 0x60;
        }
        if ((l3 & (long)n) == 0L && OS.GetCapture() == l2) {
            OS.ReleaseCapture();
        }
        return lRESULT;
    }

    LRESULT wmSetFocus(long l2, long l3, long l4) {
        long l5 = this.callWindowProc(l2, 7, l3, l4);
        this.sendFocusEvent(15);
        if (this != false) {
            return LRESULT.ZERO;
        }
        if (l5 == 0L) {
            return LRESULT.ZERO;
        }
        return new LRESULT(l5);
    }

    /*
     * Exception decompiling
     */
    LRESULT wmSysChar(long var1, long var3, long var5) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl54 : ILOAD - null : trying to set 1 previously set to 4
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

    LRESULT wmSysKeyDown(long l2, long l3, long l4) {
        int n;
        if (l3 != 121L && (l4 & 0x20000000L) == 0L) {
            return null;
        }
        switch ((int)l3) {
            case 115: {
                long l5 = l2;
                while (OS.GetParent(l5) != 0L && OS.GetWindow(l5, 4) == 0L) {
                    l5 = OS.GetParent(l5);
                }
                n = OS.GetWindowLong(l5, -16);
                if ((n & 0x80000) == 0) break;
                return null;
            }
        }
        switch ((int)l3) {
            case 16: 
            case 17: 
            case 18: 
            case 20: 
            case 144: 
            case 145: {
                if ((l4 & 0x40000000L) == 0L) break;
                return null;
            }
        }
        Display display = this.display;
        Display display2 = this.display;
        n = 0;
        display2.lastKey = 0;
        display.lastAscii = 0;
        Display display3 = this.display;
        Display display4 = this.display;
        Display display5 = this.display;
        boolean bl = false;
        display5.lastDead = false;
        display4.lastNull = false;
        display3.lastVirtual = false;
        int n2 = OS.MapVirtualKey((int)l3, 2);
        boolean bl2 = this.display.lastVirtual = n2 == 0 || this.display.numpadKey((int)l3) != 0;
        if (!this.display.lastVirtual) {
            this.display.lastKey = (int)OS.CharLower((short)n2);
            return null;
        }
        this.display.lastKey = (int)l3;
        if (this.display.lastKey == 46) {
            this.display.lastAscii = 127;
        }
        if (96 <= this.display.lastKey && this.display.lastKey <= 111) {
            switch (this.display.lastKey) {
                case 106: 
                case 107: 
                case 109: 
                case 110: 
                case 111: {
                    return null;
                }
            }
            this.display.lastAscii = this.display.numpadKey(this.display.lastKey);
        }
        Widget widget = this;
        boolean bl3 = true;
        int n3 = 260;
        if (l3 == l4) {
            return LRESULT.ONE;
        }
        return null;
    }

    LRESULT wmSysKeyUp(long l2, long l3, long l4) {
        return this.wmKeyUp(l2, l3, l4);
    }

    LRESULT wmXButtonDblClk(long l2, long l3, long l4) {
        LRESULT lRESULT = null;
        Display display = this.display;
        display.captureChanged = false;
        int n = OS.HIWORD(l3) == 1 ? 4 : 5;
        this.sendMouseEvent(3, n, l2, l4);
        lRESULT = this.sendMouseEvent(8, n, l2, l4) ? new LRESULT(this.callWindowProc(l2, 525, l3, l4)) : LRESULT.ZERO;
        if (!display.captureChanged && this != false && OS.GetCapture() != l2) {
            OS.SetCapture(l2);
        }
        return lRESULT;
    }

    LRESULT wmXButtonDown(long l2, long l3, long l4) {
        LRESULT lRESULT = null;
        Display display = this.display;
        display.captureChanged = false;
        display.xMouse = true;
        int n = OS.HIWORD(l3) == 1 ? 4 : 5;
        lRESULT = this.sendMouseEvent(3, n, l2, l4) ? new LRESULT(this.callWindowProc(l2, 523, l3, l4)) : LRESULT.ZERO;
        if (!display.captureChanged && this != false && OS.GetCapture() != l2) {
            OS.SetCapture(l2);
        }
        return lRESULT;
    }

    LRESULT wmXButtonUp(long l2, long l3, long l4) {
        Display display = this.display;
        LRESULT lRESULT = null;
        int n = OS.HIWORD(l3) == 1 ? 4 : 5;
        lRESULT = this.sendMouseEvent(4, n, l2, l4) ? new LRESULT(this.callWindowProc(l2, 524, l3, l4)) : LRESULT.ZERO;
        int n2 = 19;
        if (display.xMouse) {
            n2 |= 0x60;
        }
        if ((l3 & (long)n2) == 0L && OS.GetCapture() == l2) {
            OS.ReleaseCapture();
        }
        return lRESULT;
    }

    void notifyCreationTracker() {
        if (WidgetSpy.isEnabled) {
            WidgetSpy.getInstance().widgetCreated(this);
        }
    }

    void notifyDisposalTracker() {
        if (WidgetSpy.isEnabled) {
            WidgetSpy.getInstance().widgetDisposed(this);
        }
    }

    static {
        INITCOMMONCONTROLSEX iNITCOMMONCONTROLSEX = new INITCOMMONCONTROLSEX();
        iNITCOMMONCONTROLSEX.dwSize = INITCOMMONCONTROLSEX.sizeof;
        iNITCOMMONCONTROLSEX.dwICC = 65535;
        OS.InitCommonControlsEx(iNITCOMMONCONTROLSEX);
    }

    class MouseWheelData {
        int count;
        int detail;
        final Widget this$0;

        MouseWheelData(Widget widget, boolean bl, ScrollBar scrollBar, long l2, Point point) {
            this.this$0 = widget;
            int n = OS.GET_WHEEL_DELTA_WPARAM(l2);
            if (bl) {
                int[] nArray = new int[]{0};
                OS.SystemParametersInfo(104, 0, nArray, 0);
                if (nArray[0] == -1) {
                    this.detail = 2;
                } else {
                    n *= nArray[0];
                    this.detail = 1;
                }
            } else {
                int[] nArray = new int[]{0};
                OS.SystemParametersInfo(108, 0, nArray, 0);
                n *= nArray[0];
                this.detail = 0;
            }
            if (scrollBar != null) {
                n = this.detail == 2 ? (n *= scrollBar.getPageIncrement()) : (n *= scrollBar.getIncrement());
            }
            if (bl) {
                if ((n ^ point.y) >= 0) {
                    n += point.y;
                }
                point.y = n % 120;
            } else {
                if ((n ^ point.x) >= 0) {
                    n += point.x;
                }
                point.x = n % 120;
            }
            this.count = n / 120;
        }
    }
}

