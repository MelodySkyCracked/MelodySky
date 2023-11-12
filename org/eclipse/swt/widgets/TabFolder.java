/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import java.io.Serializable;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMTTDISPINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TCHITTESTINFO;
import org.eclipse.swt.internal.win32.TCITEM;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class TabFolder
extends Composite {
    TabItem[] items;
    ImageList imageList;
    static final long TabFolderProc;
    static final TCHAR TabFolderClass;
    boolean createdAsRTL;
    static final int ID_UPDOWN = 1;

    public TabFolder(Composite composite, int n) {
        super(composite, TabFolder.checkStyle(n));
    }

    public void addSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(selectionListener);
        this.addListener(13, typedListener);
        this.addListener(14, typedListener);
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        return OS.CallWindowProc(TabFolderProc, l2, n, l3, l4);
    }

    static int checkStyle(int n) {
        n = Widget.checkBits(n, 128, 1024, 0, 0, 0, 0);
        return n & 0xFFFFFCFF;
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        this.checkWidget();
        Point point = super.computeSizeInPixels(n, n2, bl);
        RECT rECT = new RECT();
        RECT rECT2 = new RECT();
        OS.SendMessage(this.handle, 4904, 0L, rECT);
        int n3 = rECT.left - rECT.right;
        int n4 = (int)OS.SendMessage(this.handle, 4868, 0L, 0L);
        if (n4 != 0) {
            OS.SendMessage(this.handle, 4874, (long)(n4 - 1), rECT2);
            n3 = Math.max(n3, rECT2.right - rECT.right);
        }
        RECT rECT3 = new RECT();
        OS.SetRect(rECT3, 0, 0, n3, point.y);
        OS.SendMessage(this.handle, 4904, 1L, rECT3);
        int n5 = this.getBorderWidthInPixels();
        RECT rECT4 = rECT3;
        rECT4.left -= n5;
        RECT rECT5 = rECT3;
        rECT5.right += n5;
        n3 = rECT3.right - rECT3.left;
        point.x = Math.max(n3, point.x);
        return point;
    }

    @Override
    Rectangle computeTrimInPixels(int n, int n2, int n3, int n4) {
        this.checkWidget();
        RECT rECT = new RECT();
        OS.SetRect(rECT, n, n2, n + n3, n2 + n4);
        OS.SendMessage(this.handle, 4904, 1L, rECT);
        int n5 = this.getBorderWidthInPixels();
        RECT rECT2 = rECT;
        rECT2.left -= n5;
        RECT rECT3 = rECT;
        rECT3.right += n5;
        RECT rECT4 = rECT;
        rECT4.top -= n5;
        RECT rECT5 = rECT;
        rECT5.bottom += n5;
        int n6 = rECT.right - rECT.left;
        int n7 = rECT.bottom - rECT.top;
        return new Rectangle(rECT.left, rECT.top, n6, n7);
    }

    void createItem(TabItem tabItem, int n) {
        TabItem[] tabItemArray;
        int n2 = (int)OS.SendMessage(this.handle, 4868, 0L, 0L);
        if (0 > n || n > n2) {
            this.error(6);
        }
        if (n2 == this.items.length) {
            tabItemArray = new TabItem[this.items.length + 4];
            System.arraycopy(this.items, 0, tabItemArray, 0, this.items.length);
            this.items = tabItemArray;
        }
        if (OS.SendMessage(this.handle, 4926, (long)n, (TCITEM)(tabItemArray = new TCITEM())) == -1L) {
            this.error(14);
        }
        System.arraycopy(this.items, n, this.items, n + 1, n2 - n);
        this.items[n] = tabItem;
        if (n2 == 0) {
            Event event = new Event();
            event.item = this.items[0];
            this.sendSelectionEvent(13, event, true);
        }
    }

    @Override
    void createHandle() {
        super.createHandle();
        this.state &= 0xFFFFFEFD;
        long l2 = OS.SendMessage(this.handle, 4909, 0L, 0L);
        OS.SendMessage(l2, 1048, 0L, 32767L);
        this.createdAsRTL = (this.style & 0x4000000) != 0;
    }

    @Override
    void createWidget() {
        super.createWidget();
        this.items = new TabItem[4];
    }

    void destroyItem(TabItem tabItem) {
        int n;
        int n2 = (int)OS.SendMessage(this.handle, 4868, 0L, 0L);
        for (n = 0; n < n2 && this.items[n] != tabItem; ++n) {
        }
        if (n == n2) {
            return;
        }
        int n3 = (int)OS.SendMessage(this.handle, 4875, 0L, 0L);
        if (OS.SendMessage(this.handle, 4872, (long)n, 0L) == 0L) {
            this.error(15);
        }
        System.arraycopy(this.items, n + 1, this.items, n, --n2 - n);
        this.items[n2] = null;
        if (n2 == 0) {
            if (this.imageList != null) {
                OS.SendMessage(this.handle, 4867, 0L, 0L);
                this.display.releaseImageList(this.imageList);
            }
            this.imageList = null;
            this.items = new TabItem[4];
        }
        if (n2 > 0 && n == n3) {
            this.setSelection(Math.max(0, n3 - 1), true);
        }
    }

    @Override
    void drawThemeBackground(long l2, long l3, RECT rECT) {
        RECT rECT2 = new RECT();
        OS.GetClientRect(this.handle, rECT2);
        OS.MapWindowPoints(this.handle, l3, rECT2, 2);
        if (OS.IntersectRect(new RECT(), rECT2, rECT)) {
            OS.DrawThemeBackground(this.display.hTabTheme(), l2, 10, 0, rECT2, null);
        }
    }

    @Override
    Control findThemeControl() {
        return this;
    }

    @Override
    Rectangle getClientAreaInPixels() {
        this.checkWidget();
        this.forceResize();
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        OS.SendMessage(this.handle, 4904, 0L, rECT);
        int n = rECT.right - rECT.left;
        int n2 = rECT.bottom - rECT.top;
        return new Rectangle(rECT.left, rECT.top, n, n2);
    }

    public TabItem getItem(int n) {
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 4868, 0L, 0L);
        if (0 > n || n >= n2) {
            this.error(6);
        }
        return this.items[n];
    }

    public TabItem getItem(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        TCHITTESTINFO tCHITTESTINFO = new TCHITTESTINFO();
        tCHITTESTINFO.x = point.x;
        tCHITTESTINFO.y = point.y;
        int n = (int)OS.SendMessage(this.handle, 4877, 0L, tCHITTESTINFO);
        if (n == -1) {
            return null;
        }
        return this.items[n];
    }

    public int getItemCount() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 4868, 0L, 0L);
    }

    public TabItem[] getItems() {
        this.checkWidget();
        int n = (int)OS.SendMessage(this.handle, 4868, 0L, 0L);
        TabItem[] tabItemArray = new TabItem[n];
        System.arraycopy(this.items, 0, tabItemArray, 0, n);
        return tabItemArray;
    }

    public TabItem[] getSelection() {
        this.checkWidget();
        int n = (int)OS.SendMessage(this.handle, 4875, 0L, 0L);
        if (n == -1) {
            return new TabItem[0];
        }
        return new TabItem[]{this.items[n]};
    }

    public int getSelectionIndex() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 4875, 0L, 0L);
    }

    int imageIndex(Image image) {
        if (image == null) {
            return -1;
        }
        if (this.imageList == null) {
            Rectangle rectangle = image.getBoundsInPixels();
            this.imageList = this.display.getImageList(this.style & 0x4000000, rectangle.width, rectangle.height);
            int n = this.imageList.add(image);
            long l2 = this.imageList.getHandle();
            OS.SendMessage(this.handle, 4867, 0L, l2);
            return n;
        }
        int n = this.imageList.indexOf(image);
        if (n == -1) {
            n = this.imageList.add(image);
        } else {
            this.imageList.put(n, image);
        }
        return n;
    }

    public int indexOf(TabItem tabItem) {
        this.checkWidget();
        if (tabItem == null) {
            this.error(4);
        }
        int n = (int)OS.SendMessage(this.handle, 4868, 0L, 0L);
        for (int i = 0; i < n; ++i) {
            if (this.items[i] != tabItem) continue;
            return i;
        }
        return -1;
    }

    @Override
    Point minimumSize(int n, int n2, boolean bl) {
        int n3 = 0;
        int n4 = 0;
        for (Control control : this._getChildren()) {
            Serializable serializable;
            int n5;
            int n6 = (int)OS.SendMessage(this.handle, 4868, 0L, 0L);
            for (n5 = 0; n5 < n6 && this.items[n5].control != control; ++n5) {
            }
            if (n5 == n6) {
                serializable = DPIUtil.autoScaleUp(control.getBounds());
                n3 = Math.max(n3, serializable.x + serializable.width);
                n4 = Math.max(n4, serializable.y + serializable.height);
                continue;
            }
            serializable = DPIUtil.autoScaleUp(control.computeSize(DPIUtil.autoScaleDown(n), DPIUtil.autoScaleDown(n2), bl));
            n3 = Math.max(n3, ((Point)serializable).x);
            n4 = Math.max(n4, ((Point)serializable).y);
        }
        return new Point(n3, n4);
    }

    @Override
    boolean mnemonicHit(char c) {
        for (int i = 0; i < this.items.length; ++i) {
            TabItem tabItem = this.items[i];
            if (tabItem == null) continue;
            char c2 = this.findMnemonic(tabItem.getText());
            if (Character.toUpperCase(c) != Character.toUpperCase(c2) || !this.forceFocus()) continue;
            if (i != this.getSelectionIndex()) {
                this.setSelection(i, true);
            }
            return true;
        }
        return false;
    }

    @Override
    boolean mnemonicMatch(char c) {
        for (TabItem tabItem : this.items) {
            if (tabItem == null) continue;
            char c2 = this.findMnemonic(tabItem.getText());
            if (Character.toUpperCase(c) != Character.toUpperCase(c2)) continue;
            return true;
        }
        return false;
    }

    @Override
    void releaseChildren(boolean bl) {
        if (this.items != null) {
            int n = (int)OS.SendMessage(this.handle, 4868, 0L, 0L);
            for (int i = 0; i < n; ++i) {
                TabItem tabItem = this.items[i];
                if (tabItem == null || tabItem.isDisposed()) continue;
                tabItem.release(false);
            }
            this.items = null;
        }
        super.releaseChildren(bl);
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        if (this.imageList != null) {
            OS.SendMessage(this.handle, 4867, 0L, 0L);
            this.display.releaseImageList(this.imageList);
        }
        this.imageList = null;
    }

    @Override
    void removeControl(Control control) {
        super.removeControl(control);
        int n = (int)OS.SendMessage(this.handle, 4868, 0L, 0L);
        for (int i = 0; i < n; ++i) {
            TabItem tabItem = this.items[i];
            if (tabItem.control != control) continue;
            tabItem.setControl(null);
        }
    }

    public void removeSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(13, selectionListener);
        this.eventTable.unhook(14, selectionListener);
    }

    @Override
    void reskinChildren(int n) {
        if (this.items != null) {
            int n2 = (int)OS.SendMessage(this.handle, 4868, 0L, 0L);
            for (int i = 0; i < n2; ++i) {
                TabItem tabItem = this.items[i];
                if (tabItem == null) continue;
                tabItem.reskin(n);
            }
        }
        super.reskinChildren(n);
    }

    public void setSelection(TabItem tabItem) {
        this.checkWidget();
        if (tabItem == null) {
            this.error(4);
        }
        this.setSelection(new TabItem[]{tabItem});
    }

    public void setSelection(TabItem[] tabItemArray) {
        this.checkWidget();
        if (tabItemArray == null) {
            this.error(4);
        }
        if (tabItemArray.length == 0) {
            this.setSelection(-1, false);
        } else {
            for (int i = tabItemArray.length - 1; i >= 0; --i) {
                int n = this.indexOf(tabItemArray[i]);
                if (n == -1) continue;
                this.setSelection(n, false);
            }
        }
    }

    @Override
    public void setFont(Font font) {
        this.checkWidget();
        Rectangle rectangle = this.getClientAreaInPixels();
        super.setFont(font);
        Rectangle rectangle2 = this.getClientAreaInPixels();
        if (!rectangle.equals(rectangle2)) {
            this.sendResize();
            int n = (int)OS.SendMessage(this.handle, 4875, 0L, 0L);
            if (n != -1) {
                TabItem tabItem = this.items[n];
                Control control = tabItem.control;
                if (control != null && !control.isDisposed()) {
                    control.setBoundsInPixels(this.getClientAreaInPixels());
                }
            }
        }
    }

    public void setSelection(int n) {
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 4868, 0L, 0L);
        if (0 > n || n >= n2) {
            return;
        }
        this.setSelection(n, false);
    }

    void setSelection(int n, boolean bl) {
        Widget widget;
        int n2 = (int)OS.SendMessage(this.handle, 4875, 0L, 0L);
        if (n2 == n) {
            return;
        }
        if (n2 != -1) {
            TabItem tabItem = this.items[n2];
            widget = tabItem.control;
            if (widget != null && !widget.isDisposed()) {
                widget.setVisible(false);
            }
        }
        OS.SendMessage(this.handle, 4876, (long)n, 0L);
        int n3 = (int)OS.SendMessage(this.handle, 4875, 0L, 0L);
        if (n3 != -1) {
            widget = this.items[n3];
            Control control = ((TabItem)widget).control;
            if (control != null && !control.isDisposed()) {
                control.setBoundsInPixels(this.getClientAreaInPixels());
                control.setVisible(true);
            }
            if (bl) {
                Event event = new Event();
                event.item = widget;
                this.sendSelectionEvent(13, event, true);
            }
        }
    }

    @Override
    boolean updateTextDirection(int n) {
        if (super.updateTextDirection(n)) {
            if (n != 0x6000000) {
                n = this.style & Integer.MIN_VALUE;
            }
            int n2 = this.items.length;
            for (int i = 0; i < n2 && this.items[i] != null; ++i) {
                this.items[i].updateTextDirection(n);
            }
            return true;
        }
        return false;
    }

    @Override
    String toolTipText(NMTTDISPINFO nMTTDISPINFO) {
        if ((nMTTDISPINFO.uFlags & 1) != 0) {
            return null;
        }
        int n = (int)nMTTDISPINFO.idFrom;
        long l2 = OS.SendMessage(this.handle, 4909, 0L, 0L);
        if (l2 == nMTTDISPINFO.hwndFrom) {
            TabItem tabItem;
            int n2 = -2080374784;
            nMTTDISPINFO.uFlags = (this.style & 0x84000000) != 0 && (this.style & 0x84000000) != -2080374784 ? (nMTTDISPINFO.uFlags |= 4) : (nMTTDISPINFO.uFlags &= 0xFFFFFFFB);
            if (this.toolTipText != null) {
                return "";
            }
            if (0 <= n && n < this.items.length && (tabItem = this.items[n]) != null) {
                return tabItem.toolTipText;
            }
        }
        return super.toolTipText(nMTTDISPINFO);
    }

    @Override
    boolean traversePage(boolean bl) {
        int n = this.getItemCount();
        if (n <= 1) {
            return false;
        }
        int n2 = this.getSelectionIndex();
        if (n2 == -1) {
            n2 = 0;
        } else {
            int n3 = bl ? 1 : -1;
            n2 = (n2 + n3 + n) % n;
        }
        this.setSelection(n2, true);
        if (n2 == this.getSelectionIndex()) {
            OS.SendMessage(this.handle, 295, 3L, 0L);
            return true;
        }
        return false;
    }

    @Override
    void updateOrientation() {
        super.updateOrientation();
        long l2 = OS.GetWindow(this.handle, 5);
        while (l2 != 0L) {
            char[] cArray = new char[128];
            int n = OS.GetClassName(l2, cArray, cArray.length);
            String string = new String(cArray, 0, n);
            if (string.equals("msctls_updown32")) {
                int n2 = OS.GetWindowLong(l2, -20);
                n2 = (this.style & 0x4000000) != 0 ? (n2 |= 0x400000) : (n2 &= 0xFFBFFFFF);
                OS.SetWindowLong(l2, -20, n2 &= 0xFFFFDFFF);
                OS.InvalidateRect(l2, null, true);
                break;
            }
            l2 = OS.GetWindow(l2, 2);
        }
        RECT rECT = new RECT();
        OS.GetWindowRect(this.handle, rECT);
        int n = rECT.right - rECT.left;
        int n3 = rECT.bottom - rECT.top;
        OS.SetWindowPos(this.handle, 0L, 0, 0, n - 1, n3 - 1, 6);
        OS.SetWindowPos(this.handle, 0L, 0, 0, n, n3, 6);
        if (this.imageList != null) {
            TabItem tabItem;
            Point point = this.imageList.getImageSize();
            this.display.releaseImageList(this.imageList);
            this.imageList = this.display.getImageList(this.style & 0x4000000, point.x, point.y);
            long l3 = this.imageList.getHandle();
            OS.SendMessage(this.handle, 4867, 0L, l3);
            TCITEM tCITEM = new TCITEM();
            tCITEM.mask = 2;
            for (int i = 0; i < this.items.length && (tabItem = this.items[i]) != null; ++i) {
                Image image = tabItem.image;
                if (image == null) continue;
                tCITEM.iImage = this.imageIndex(image);
                OS.SendMessage(this.handle, 4925, (long)i, tCITEM);
            }
        }
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle() | 0x2000000;
        if ((this.style & 0x80000) != 0) {
            n |= 0x8000;
        }
        if ((this.style & 0x400) != 0) {
            n |= 2;
        }
        return n | 0 | 0x4000;
    }

    @Override
    TCHAR windowClass() {
        return TabFolderClass;
    }

    @Override
    long windowProc() {
        return TabFolderProc;
    }

    @Override
    LRESULT WM_GETDLGCODE(long l2, long l3) {
        LRESULT lRESULT = super.WM_GETDLGCODE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return new LRESULT(8193L);
    }

    @Override
    LRESULT WM_GETOBJECT(long l2, long l3) {
        if (this.accessible == null) {
            this.accessible = this.new_Accessible(this);
        }
        return super.WM_GETOBJECT(l2, l3);
    }

    @Override
    LRESULT WM_KEYDOWN(long l2, long l3) {
        LRESULT lRESULT = super.WM_KEYDOWN(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        switch ((int)l2) {
            case 37: 
            case 39: {
                boolean bl;
                boolean bl2 = bl = (this.style & 0x4000000) != 0;
                if (bl == this.createdAsRTL) break;
                long l4 = this.callWindowProc(this.handle, 256, l2 == 39L ? 37L : 39L, l3);
                return new LRESULT(l4);
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_MOUSELEAVE(long l2, long l3) {
        LRESULT lRESULT = super.WM_MOUSELEAVE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        TOOLINFO tOOLINFO = new TOOLINFO();
        tOOLINFO.cbSize = TOOLINFO.sizeof;
        long l4 = OS.SendMessage(this.handle, 4909, 0L, 0L);
        if (OS.SendMessage(l4, 1083, 0L, tOOLINFO) != 0L && (tOOLINFO.uFlags & 1) == 0) {
            OS.SendMessage(l4, 1075, 0L, tOOLINFO);
            OS.SendMessage(l4, 1074, 0L, tOOLINFO);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_NCHITTEST(long l2, long l3) {
        LRESULT lRESULT = super.WM_NCHITTEST(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        long l4 = OS.DefWindowProc(this.handle, 132, l2, l3);
        return new LRESULT(l4);
    }

    @Override
    LRESULT WM_NOTIFY(long l2, long l3) {
        LRESULT lRESULT = super.WM_NOTIFY(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return LRESULT.ZERO;
    }

    @Override
    LRESULT WM_PARENTNOTIFY(long l2, long l3) {
        LRESULT lRESULT = super.WM_PARENTNOTIFY(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((this.style & 0x4000000) != 0) {
            int n = OS.LOWORD(l2);
            switch (n) {
                case 1: {
                    int n2 = OS.HIWORD(l2);
                    long l4 = l3;
                    if (n2 != 1) break;
                    int n3 = OS.GetWindowLong(l4, -20);
                    OS.SetWindowLong(l4, -20, n3 | 0x400000);
                    break;
                }
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        LRESULT lRESULT = super.WM_SIZE(l2, l3);
        if (this.isDisposed()) {
            return lRESULT;
        }
        int n = (int)OS.SendMessage(this.handle, 4875, 0L, 0L);
        if (n != -1) {
            TabItem tabItem = this.items[n];
            Control control = tabItem.control;
            if (control != null && !control.isDisposed()) {
                control.setBoundsInPixels(this.getClientAreaInPixels());
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_WINDOWPOSCHANGING(long l2, long l3) {
        int n;
        LRESULT lRESULT = super.WM_WINDOWPOSCHANGING(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (!OS.IsWindowVisible(this.handle)) {
            return lRESULT;
        }
        WINDOWPOS wINDOWPOS = new WINDOWPOS();
        OS.MoveMemory(wINDOWPOS, l3, WINDOWPOS.sizeof);
        if ((wINDOWPOS.flags & 9) != 0) {
            return lRESULT;
        }
        int n2 = OS.GetWindowLong(this.handle, -16);
        if ((n2 & 0x200) != 0) {
            OS.InvalidateRect(this.handle, null, true);
            return lRESULT;
        }
        RECT rECT = new RECT();
        OS.SetRect(rECT, 0, 0, wINDOWPOS.cx, wINDOWPOS.cy);
        OS.SendMessage(this.handle, 131, 0L, rECT);
        int n3 = rECT.right - rECT.left;
        int n4 = rECT.bottom - rECT.top;
        OS.GetClientRect(this.handle, rECT);
        int n5 = rECT.right - rECT.left;
        int n6 = rECT.bottom - rECT.top;
        if (n3 == n5 && n4 == n6) {
            return lRESULT;
        }
        RECT rECT2 = new RECT();
        OS.SendMessage(this.handle, 4904, 0L, rECT2);
        int n7 = -rECT2.right;
        int n8 = -rECT2.bottom;
        if (n3 != n5) {
            n = n5;
            if (n3 < n) {
                n = n3;
            }
            OS.SetRect(rECT, n - n7, 0, n3, n4);
            OS.InvalidateRect(this.handle, rECT, true);
        }
        if (n4 != n6) {
            n = n6;
            if (n4 < n) {
                n = n4;
            }
            if (n3 < n5) {
                n5 -= n7;
            }
            OS.SetRect(rECT, 0, n - n8, n5, n4);
            OS.InvalidateRect(this.handle, rECT, true);
        }
        return lRESULT;
    }

    @Override
    LRESULT wmNotifyChild(NMHDR nMHDR, long l2, long l3) {
        int n = nMHDR.code;
        switch (n) {
            case -552: 
            case -551: {
                Object object;
                TabItem tabItem = null;
                int n2 = (int)OS.SendMessage(this.handle, 4875, 0L, 0L);
                if (n2 != -1) {
                    tabItem = this.items[n2];
                }
                if (tabItem != null && (object = tabItem.control) != null && !object.isDisposed()) {
                    if (n == -551) {
                        ((Control)object).setBoundsInPixels(this.getClientAreaInPixels());
                    }
                    ((Control)object).setVisible(n == -551);
                }
                if (n != -551) break;
                object = new Event();
                ((Event)object).item = tabItem;
                this.sendSelectionEvent(13, (Event)object, false);
                break;
            }
        }
        return super.wmNotifyChild(nMHDR, l2, l3);
    }

    static {
        TabFolderClass = new TCHAR(0, "SysTabControl32", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, TabFolderClass, wNDCLASS);
        TabFolderProc = wNDCLASS.lpfnWndProc;
        wNDCLASS.hInstance = OS.GetModuleHandle(null);
        WNDCLASS wNDCLASS2 = wNDCLASS;
        wNDCLASS2.style &= 0xFFFFBFFC;
        OS.RegisterClass(TabFolderClass, wNDCLASS);
    }
}

