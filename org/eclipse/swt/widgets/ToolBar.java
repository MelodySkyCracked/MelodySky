/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMTBCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMTBHOTITEM;
import org.eclipse.swt.internal.win32.NMTOOLBAR;
import org.eclipse.swt.internal.win32.NMTTDISPINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TBBUTTON;
import org.eclipse.swt.internal.win32.TBBUTTONINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;

public class ToolBar
extends Composite {
    int lastFocusId;
    int lastArrowId;
    int lastHotId;
    int _width;
    int _height;
    int _count = -1;
    int _wHint = -1;
    int _hHint = -1;
    long currentToolItemToolTip;
    ToolItem[] items;
    ToolItem[] tabItemList;
    boolean ignoreResize;
    boolean ignoreMouse;
    ImageList imageList;
    ImageList disabledImageList;
    ImageList hotImageList;
    static final long ToolBarProc;
    static final TCHAR ToolBarClass;
    static final int DEFAULT_WIDTH = 24;
    static final int DEFAULT_HEIGHT = 22;

    public ToolBar(Composite composite, int n) {
        super(composite, ToolBar.checkStyle(n));
        if ((n & 0x200) != 0) {
            this.style |= 0x200;
            int n2 = OS.GetWindowLong(this.handle, -16);
            if (OS.IsAppThemed() && (n & 0x20000) != 0) {
                n2 |= 0x1000;
            }
            OS.SetWindowLong(this.handle, -16, n2 | 0x80);
        } else {
            this.style |= 0x100;
        }
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        if (n == 262) {
            return OS.DefWindowProc(l2, n, l3, l4);
        }
        return OS.CallWindowProc(ToolBarProc, l2, n, l3, l4);
    }

    static int checkStyle(int n) {
        if ((n & 0x800000) == 0) {
            n |= 0x80000;
        }
        if ((n & 0x200) != 0) {
            n &= 0xFFFFFFBF;
        }
        return n & 0xFFFFFCFF;
    }

    @Override
    void checkBuffered() {
        super.checkBuffered();
        this.style |= 0x20000000;
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    @Override
    public void layout(boolean bl) {
        this.checkWidget();
        this.clearSizeCache(bl);
        super.layout(bl);
    }

    void clearSizeCache(boolean bl) {
        if (bl) {
            int n = -1;
            this._hHint = -1;
            this._wHint = -1;
            this._count = -1;
        }
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        Object object;
        int n3 = (int)OS.SendMessage(this.handle, 1048, 0L, 0L);
        if (n3 == this._count && n == this._wHint && n2 == this._hHint) {
            return new Point(this._width, this._height);
        }
        this._count = n3;
        this._wHint = n;
        this._hHint = n2;
        int n4 = 0;
        int n5 = 0;
        if ((this.style & 0x200) != 0) {
            object = new RECT();
            TBBUTTON tBBUTTON = new TBBUTTON();
            for (int i = 0; i < n3; ++i) {
                OS.SendMessage(this.handle, 1053, (long)i, (RECT)object);
                n5 = Math.max(n5, ((RECT)object).bottom);
                OS.SendMessage(this.handle, 1047, (long)i, tBBUTTON);
                if ((tBBUTTON.fsStyle & 1) != 0) {
                    TBBUTTONINFO tBBUTTONINFO = new TBBUTTONINFO();
                    tBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
                    tBBUTTONINFO.dwMask = 64;
                    OS.SendMessage(this.handle, 1087, (long)tBBUTTON.idCommand, tBBUTTONINFO);
                    n4 = Math.max(n4, tBBUTTONINFO.cx);
                    continue;
                }
                n4 = Math.max(n4, ((RECT)object).right);
            }
        } else {
            object = new RECT();
            OS.GetWindowRect(this.handle, (RECT)object);
            int n6 = ((RECT)object).right - ((RECT)object).left;
            int n7 = ((RECT)object).bottom - ((RECT)object).top;
            int n8 = this.getBorderWidthInPixels();
            int n9 = n == -1 ? 16383 : n + n8 * 2;
            int n10 = n2 == -1 ? 16383 : n2 + n8 * 2;
            boolean bl2 = this.getDrawing() && OS.IsWindowVisible(this.handle);
            this.ignoreResize = true;
            if (bl2) {
                OS.UpdateWindow(this.handle);
            }
            int n11 = 30;
            OS.SetWindowPos(this.handle, 0L, 0, 0, n9, n10, 30);
            if (n3 != 0) {
                RECT rECT = new RECT();
                OS.SendMessage(this.handle, 1053, (long)(n3 - 1), rECT);
                n4 = Math.max(n4, rECT.right);
                n5 = Math.max(n5, rECT.bottom);
            }
            OS.SetWindowPos(this.handle, 0L, 0, 0, n6, n7, 30);
            if (bl2) {
                OS.ValidateRect(this.handle, null);
            }
            this.ignoreResize = false;
        }
        if (n4 == 0) {
            n4 = 24;
        }
        if (n5 == 0) {
            n5 = 22;
        }
        if (n != -1) {
            n4 = n;
        }
        if (n2 != -1) {
            n5 = n2;
        }
        object = this.computeTrimInPixels(0, 0, n4, n5);
        n4 = ((Rectangle)object).width;
        n5 = ((Rectangle)object).height;
        this._width = n4;
        this._height = n5;
        return new Point(n4, n5);
    }

    @Override
    Rectangle computeTrimInPixels(int n, int n2, int n3, int n4) {
        Rectangle rectangle = super.computeTrimInPixels(n, n2, n3, n4);
        int n5 = OS.GetWindowLong(this.handle, -16);
        if ((n5 & 0x40) == 0) {
            Rectangle rectangle2 = rectangle;
            rectangle2.height += 2;
        }
        return rectangle;
    }

    @Override
    Widget computeTabGroup() {
        int n;
        ToolItem[] toolItemArray = this._getItems();
        if (this.tabItemList == null) {
            for (n = 0; n < toolItemArray.length && toolItemArray[n].control == null; ++n) {
            }
            if (n == toolItemArray.length) {
                return super.computeTabGroup();
            }
        }
        if ((n = (int)OS.SendMessage(this.handle, 1095, 0L, 0L)) == -1) {
            n = this.lastHotId;
        }
        while (n >= 0) {
            ToolItem toolItem = toolItemArray[n];
            if (toolItem.isTabGroup()) {
                return toolItem;
            }
            --n;
        }
        return super.computeTabGroup();
    }

    @Override
    Widget[] computeTabList() {
        ToolItem[] toolItemArray = this._getItems();
        if (this.tabItemList == null) {
            int n;
            for (n = 0; n < toolItemArray.length && toolItemArray[n].control == null; ++n) {
            }
            if (n == toolItemArray.length) {
                return super.computeTabList();
            }
        }
        Widget[] widgetArray = new Widget[]{};
        if (!(this.isTabGroup() && this.isEnabled() && this.isVisible())) {
            return widgetArray;
        }
        ToolItem[] toolItemArray2 = this.tabList != null ? this._getTabItemList() : toolItemArray;
        ToolItem[] toolItemArray3 = toolItemArray2;
        for (ToolItem toolItem : toolItemArray2) {
            Widget[] widgetArray2 = toolItem.computeTabList();
            if (widgetArray2.length == 0) continue;
            Widget[] widgetArray3 = new Widget[widgetArray.length + widgetArray2.length];
            System.arraycopy(widgetArray, 0, widgetArray3, 0, widgetArray.length);
            System.arraycopy(widgetArray2, 0, widgetArray3, widgetArray.length, widgetArray2.length);
            widgetArray = widgetArray3;
        }
        if (widgetArray.length == 0) {
            widgetArray = new Widget[]{this};
        }
        return widgetArray;
    }

    @Override
    void createHandle() {
        super.createHandle();
        this.state &= 0xFFFFFFFD;
        if ((this.style & 0x800000) != 0 && !OS.IsAppThemed()) {
            int n = OS.GetWindowLong(this.handle, -16);
            OS.SetWindowLong(this.handle, -16, n &= 0xFFFF7FFF);
        }
        long l2 = OS.GetStockObject(13);
        OS.SendMessage(this.handle, 48, l2, 0L);
        OS.SendMessage(this.handle, 1054, (long)TBBUTTON.sizeof, 0L);
        OS.SendMessage(this.handle, 1056, 0L, 0L);
        OS.SendMessage(this.handle, 1055, 0L, 0L);
        int n = 137;
        OS.SendMessage(this.handle, 1108, 0L, 137L);
    }

    void createItem(ToolItem toolItem, int n) {
        int n2;
        int n3 = (int)OS.SendMessage(this.handle, 1048, 0L, 0L);
        if (0 > n || n > n3) {
            this.error(6);
        }
        for (n2 = 0; n2 < this.items.length && this.items[n2] != null; ++n2) {
        }
        if (n2 == this.items.length) {
            ToolItem[] toolItemArray = new ToolItem[this.items.length + 4];
            System.arraycopy(this.items, 0, toolItemArray, 0, this.items.length);
            this.items = toolItemArray;
        }
        int n4 = toolItem.widgetStyle();
        TBBUTTON tBBUTTON = new TBBUTTON();
        tBBUTTON.idCommand = n2;
        tBBUTTON.fsStyle = (byte)n4;
        tBBUTTON.fsState = (byte)4;
        if ((n4 & 1) == 0) {
            tBBUTTON.iBitmap = -2;
        }
        if (OS.SendMessage(this.handle, 1091, (long)n, tBBUTTON) == 0L) {
            this.error(14);
        }
        toolItem.id = n2;
        this.items[toolItem.id] = toolItem;
        if ((this.style & 0x200) != 0) {
            this.setRowCount(n3 + 1);
        }
        this.layoutItems();
    }

    @Override
    void createWidget() {
        super.createWidget();
        this.items = new ToolItem[4];
        int n = -1;
        this.lastHotId = -1;
        this.lastArrowId = -1;
        this.lastFocusId = -1;
    }

    @Override
    int applyThemeBackground() {
        return -1;
    }

    void destroyItem(ToolItem toolItem) {
        TBBUTTONINFO tBBUTTONINFO = new TBBUTTONINFO();
        tBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
        tBBUTTONINFO.dwMask = 9;
        int n = (int)OS.SendMessage(this.handle, 1087, (long)toolItem.id, tBBUTTONINFO);
        if ((tBBUTTONINFO.fsStyle & 1) == 0 && tBBUTTONINFO.iImage != -2) {
            if (this.imageList != null) {
                this.imageList.put(tBBUTTONINFO.iImage, null);
            }
            if (this.hotImageList != null) {
                this.hotImageList.put(tBBUTTONINFO.iImage, null);
            }
            if (this.disabledImageList != null) {
                this.disabledImageList.put(tBBUTTONINFO.iImage, null);
            }
        }
        OS.SendMessage(this.handle, 1046, (long)n, 0L);
        if (toolItem.id == this.lastFocusId) {
            this.lastFocusId = -1;
        }
        if (toolItem.id == this.lastArrowId) {
            this.lastArrowId = -1;
        }
        if (toolItem.id == this.lastHotId) {
            this.lastHotId = -1;
        }
        this.items[toolItem.id] = null;
        toolItem.id = -1;
        int n2 = (int)OS.SendMessage(this.handle, 1048, 0L, 0L);
        if (n2 == 0) {
            if (this.imageList != null) {
                OS.SendMessage(this.handle, 1072, 0L, 0L);
                this.display.releaseToolImageList(this.imageList);
            }
            if (this.hotImageList != null) {
                OS.SendMessage(this.handle, 1076, 0L, 0L);
                this.display.releaseToolHotImageList(this.hotImageList);
            }
            if (this.disabledImageList != null) {
                OS.SendMessage(this.handle, 1078, 0L, 0L);
                this.display.releaseToolDisabledImageList(this.disabledImageList);
            }
            Object var5_5 = null;
            this.disabledImageList = var5_5;
            this.hotImageList = var5_5;
            this.imageList = var5_5;
            this.items = new ToolItem[4];
        }
        if ((this.style & 0x200) != 0) {
            this.setRowCount(n2 - 1);
        }
        this.layoutItems();
    }

    @Override
    void enableWidget(boolean bl) {
        super.enableWidget(bl);
        for (ToolItem toolItem : this.items) {
            if (toolItem == null || (toolItem.style & 2) != 0) continue;
            toolItem.updateImages(bl && toolItem.getEnabled());
        }
    }

    ImageList getDisabledImageList() {
        return this.disabledImageList;
    }

    ImageList getHotImageList() {
        return this.hotImageList;
    }

    ImageList getImageList() {
        return this.imageList;
    }

    public ToolItem getItem(int n) {
        TBBUTTON tBBUTTON;
        long l2;
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 1048, 0L, 0L);
        if (0 > n || n >= n2) {
            this.error(6);
        }
        if ((l2 = OS.SendMessage(this.handle, 1047, (long)n, tBBUTTON = new TBBUTTON())) == 0L) {
            this.error(8);
        }
        return this.items[tBBUTTON.idCommand];
    }

    public ToolItem getItem(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        return this.getItemInPixels(DPIUtil.autoScaleUp(point));
    }

    ToolItem getItemInPixels(Point point) {
        for (ToolItem toolItem : this.getItems()) {
            Rectangle rectangle = toolItem.getBoundsInPixels();
            if (!rectangle.contains(point)) continue;
            return toolItem;
        }
        return null;
    }

    public int getItemCount() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 1048, 0L, 0L);
    }

    public ToolItem[] getItems() {
        this.checkWidget();
        return this._getItems();
    }

    ToolItem[] _getItems() {
        int n = (int)OS.SendMessage(this.handle, 1048, 0L, 0L);
        TBBUTTON tBBUTTON = new TBBUTTON();
        ToolItem[] toolItemArray = new ToolItem[n];
        for (int i = 0; i < n; ++i) {
            OS.SendMessage(this.handle, 1047, (long)i, tBBUTTON);
            toolItemArray[i] = this.items[tBBUTTON.idCommand];
        }
        return toolItemArray;
    }

    public int getRowCount() {
        this.checkWidget();
        if ((this.style & 0x200) != 0) {
            return (int)OS.SendMessage(this.handle, 1048, 0L, 0L);
        }
        return (int)OS.SendMessage(this.handle, 1064, 0L, 0L);
    }

    ToolItem[] _getTabItemList() {
        if (this.tabItemList == null) {
            return this.tabItemList;
        }
        int n = 0;
        for (ToolItem toolItem : this.tabItemList) {
            if (toolItem.isDisposed()) continue;
            ++n;
        }
        if (n == this.tabItemList.length) {
            return this.tabItemList;
        }
        ToolItem[] toolItemArray = new ToolItem[n];
        int n2 = 0;
        for (ToolItem toolItem : this.tabItemList) {
            if (toolItem.isDisposed()) continue;
            toolItemArray[n2++] = toolItem;
        }
        this.tabItemList = toolItemArray;
        return toolItemArray;
    }

    public int indexOf(ToolItem toolItem) {
        this.checkWidget();
        if (toolItem == null) {
            this.error(4);
        }
        if (toolItem.isDisposed()) {
            this.error(5);
        }
        return (int)OS.SendMessage(this.handle, 1049, (long)toolItem.id, 0L);
    }

    void layoutItems() {
        int n;
        this.clearSizeCache(true);
        if (OS.IsAppThemed() && (this.style & 0x20000) != 0 && (this.style & 0x200) == 0) {
            int n2;
            n = 0;
            boolean bl = false;
            for (ToolItem toolItem : this.items) {
                if (toolItem == null) continue;
                if (n == 0) {
                    int n3 = n = toolItem.text.length() != 0 ? 1 : 0;
                }
                if (!bl) {
                    boolean bl2 = bl = toolItem.image != null;
                }
                if (n != 0 && bl) break;
            }
            int n4 = n2 = OS.GetWindowLong(this.handle, -16);
            n2 = n != 0 && bl ? (n2 |= 0x1000) : (n2 &= 0xFFFFEFFF);
            if (n2 != n4) {
                this.setDropDownItems(false);
                OS.SetWindowLong(this.handle, -16, n2);
                long l2 = OS.SendMessage(this.handle, 49, 0L, 0L);
                OS.SendMessage(this.handle, 48, l2, 0L);
                this.setDropDownItems(true);
            }
        }
        if ((this.style & 0x40) != 0) {
            OS.SendMessage(this.handle, 1057, 0L, 0L);
        }
        if ((this.style & 0x200) != 0 && (n = (int)OS.SendMessage(this.handle, 1048, 0L, 0L)) > 1) {
            int n5;
            TBBUTTONINFO tBBUTTONINFO = new TBBUTTONINFO();
            tBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
            tBBUTTONINFO.dwMask = 64;
            long l3 = OS.SendMessage(this.handle, 1082, 0L, 0L);
            tBBUTTONINFO.cx = (short)OS.LOWORD(l3);
            int n6 = 0;
            for (n5 = 0; n5 < this.items.length; ++n5) {
                ToolItem[] toolItemArray = this.items[n5];
                if (toolItemArray == null || (toolItemArray.style & 4) == 0) continue;
                n6 = 1;
                break;
            }
            if (n5 < this.items.length) {
                long l4 = OS.SendMessage(this.handle, 1110, 0L, 0L);
                TBBUTTONINFO tBBUTTONINFO2 = tBBUTTONINFO;
                tBBUTTONINFO2.cx = (short)(tBBUTTONINFO2.cx + (short)(OS.LOWORD(l4 + (long)n6) * 2));
            }
            for (ToolItem toolItem : this.items) {
                if (toolItem == null || (toolItem.style & 2) != 0) continue;
                OS.SendMessage(this.handle, 1088, (long)toolItem.id, tBBUTTONINFO);
            }
        }
        if ((this.style & 0x240) != 0 && ((n = OS.GetWindowLong(this.handle, -16)) & 0x1000) != 0) {
            TBBUTTONINFO tBBUTTONINFO = new TBBUTTONINFO();
            tBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
            tBBUTTONINFO.dwMask = 64;
            for (ToolItem toolItem : this.items) {
                if (toolItem == null || toolItem.cx <= 0) continue;
                tBBUTTONINFO.cx = toolItem.cx;
                OS.SendMessage(this.handle, 1088, (long)toolItem.id, tBBUTTONINFO);
            }
        }
        for (ToolItem toolItem : this.items) {
            if (toolItem == null) continue;
            toolItem.resizeControl();
        }
    }

    @Override
    boolean mnemonicHit(char c) {
        int[] nArray = new int[]{0};
        if (OS.SendMessage(this.handle, 1114, (long)c, nArray) == 0L) {
            return false;
        }
        if ((this.style & 0x800000) != 0 && !this.setTabGroupFocus()) {
            return false;
        }
        int n = (int)OS.SendMessage(this.handle, 1049, (long)nArray[0], 0L);
        if (n == -1) {
            return false;
        }
        OS.SendMessage(this.handle, 1096, (long)n, 0L);
        this.items[nArray[0]].click(false);
        return true;
    }

    @Override
    boolean mnemonicMatch(char c) {
        int[] nArray = new int[]{0};
        if (OS.SendMessage(this.handle, 1114, (long)c, nArray) == 0L) {
            return false;
        }
        int n = (int)OS.SendMessage(this.handle, 1049, (long)nArray[0], 0L);
        return n != -1 && this.findMnemonic(this.items[nArray[0]].text) != '\u0000';
    }

    @Override
    void releaseChildren(boolean bl) {
        if (this.items != null) {
            for (ToolItem toolItem : this.items) {
                if (toolItem == null || toolItem.isDisposed()) continue;
                toolItem.release(false);
            }
            this.items = null;
        }
        super.releaseChildren(bl);
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        if (this.imageList != null) {
            OS.SendMessage(this.handle, 1072, 0L, 0L);
            this.display.releaseToolImageList(this.imageList);
        }
        if (this.hotImageList != null) {
            OS.SendMessage(this.handle, 1076, 0L, 0L);
            this.display.releaseToolHotImageList(this.hotImageList);
        }
        if (this.disabledImageList != null) {
            OS.SendMessage(this.handle, 1078, 0L, 0L);
            this.display.releaseToolDisabledImageList(this.disabledImageList);
        }
        Object var1_1 = null;
        this.disabledImageList = var1_1;
        this.hotImageList = var1_1;
        this.imageList = var1_1;
    }

    @Override
    void removeControl(Control control) {
        super.removeControl(control);
        for (ToolItem toolItem : this.items) {
            if (toolItem == null || toolItem.control != control) continue;
            toolItem.setControl(null);
        }
    }

    @Override
    void reskinChildren(int n) {
        if (this.items != null) {
            for (ToolItem toolItem : this.items) {
                if (toolItem == null) continue;
                toolItem.reskin(n);
            }
        }
        super.reskinChildren(n);
    }

    @Override
    void setBackgroundImage(long l2) {
        super.setBackgroundImage(l2);
        this.setBackgroundTransparent(l2 != 0L);
    }

    @Override
    void setBackgroundPixel(int n) {
        super.setBackgroundPixel(n);
        this.setBackgroundTransparent(n != -1);
    }

    void setBackgroundTransparent(boolean bl) {
        if ((this.style & 0x800000) != 0 && !OS.IsAppThemed()) {
            int n = OS.GetWindowLong(this.handle, -16);
            n = !bl && this.findBackgroundControl() == null ? (n &= 0xFFFF7FFF) : (n |= 0x8000);
            OS.SetWindowLong(this.handle, -16, n);
        }
    }

    @Override
    void setBoundsInPixels(int n, int n2, int n3, int n4, int n5) {
        if (this.parent.lpwp != null && this.getDrawing() && OS.IsWindowVisible(this.handle)) {
            this.parent.setResizeChildren(false);
            this.parent.setResizeChildren(true);
        }
        super.setBoundsInPixels(n, n2, n3, n4, n5);
    }

    @Override
    void setDefaultFont() {
        super.setDefaultFont();
        OS.SendMessage(this.handle, 1056, 0L, 0L);
        OS.SendMessage(this.handle, 1055, 0L, 0L);
    }

    void setDropDownItems(boolean bl) {
        if (OS.IsAppThemed()) {
            boolean bl2 = false;
            boolean bl3 = false;
            for (ToolItem toolItem : this.items) {
                if (toolItem == null) continue;
                if (!bl2) {
                    boolean bl4 = bl2 = toolItem.text.length() != 0;
                }
                if (!bl3) {
                    boolean bl5 = bl3 = toolItem.image != null;
                }
                if (bl2 && bl3) break;
            }
            if (bl3 && !bl2) {
                for (ToolItem toolItem : this.items) {
                    TBBUTTONINFO tBBUTTONINFO;
                    if (toolItem == null || (toolItem.style & 4) == 0) continue;
                    TBBUTTONINFO tBBUTTONINFO2 = new TBBUTTONINFO();
                    tBBUTTONINFO2.cbSize = TBBUTTONINFO.sizeof;
                    tBBUTTONINFO2.dwMask = 8;
                    OS.SendMessage(this.handle, 1087, (long)toolItem.id, tBBUTTONINFO2);
                    if (bl) {
                        tBBUTTONINFO = tBBUTTONINFO2;
                        tBBUTTONINFO.fsStyle = (byte)(tBBUTTONINFO.fsStyle | 8);
                    } else {
                        tBBUTTONINFO = tBBUTTONINFO2;
                        tBBUTTONINFO.fsStyle = (byte)(tBBUTTONINFO.fsStyle & 0xFFFFFFF7);
                    }
                    OS.SendMessage(this.handle, 1088, (long)toolItem.id, tBBUTTONINFO2);
                }
            }
        }
    }

    void setDisabledImageList(ImageList imageList) {
        if (this.disabledImageList == imageList) {
            return;
        }
        long l2 = 0L;
        this.disabledImageList = imageList;
        if (this.disabledImageList != null) {
            l2 = this.disabledImageList.getHandle();
        }
        this.setDropDownItems(false);
        OS.SendMessage(this.handle, 1078, 0L, l2);
        this.setDropDownItems(true);
    }

    @Override
    public void setFont(Font font) {
        ToolItem toolItem;
        int n;
        this.checkWidget();
        this.setDropDownItems(false);
        super.setFont(font);
        this.setDropDownItems(true);
        int n2 = 60;
        for (n = 0; n < this.items.length && ((toolItem = this.items[n]) == null || (toolItem.style & 0x3C) == 0); ++n) {
        }
        if (n == this.items.length) {
            OS.SendMessage(this.handle, 1056, 0L, 0L);
            OS.SendMessage(this.handle, 1055, 0L, 0L);
        }
        this.layoutItems();
    }

    void setHotImageList(ImageList imageList) {
        if (this.hotImageList == imageList) {
            return;
        }
        long l2 = 0L;
        this.hotImageList = imageList;
        if (this.hotImageList != null) {
            l2 = this.hotImageList.getHandle();
        }
        this.setDropDownItems(false);
        OS.SendMessage(this.handle, 1076, 0L, l2);
        this.setDropDownItems(true);
    }

    void setImageList(ImageList imageList) {
        if (this.imageList == imageList) {
            return;
        }
        long l2 = 0L;
        this.imageList = imageList;
        if (this.imageList != null) {
            l2 = imageList.getHandle();
        }
        this.setDropDownItems(false);
        OS.SendMessage(this.handle, 1072, 0L, l2);
        this.setDropDownItems(true);
    }

    @Override
    public boolean setParent(Composite composite) {
        this.checkWidget();
        if (!super.setParent(composite)) {
            return false;
        }
        long l2 = composite.handle;
        OS.SendMessage(this.handle, 1061, l2, 0L);
        long l3 = composite.getShell().handle;
        long l4 = OS.SendMessage(this.handle, 1059, 0L, 0L);
        OS.SetWindowLongPtr(l4, -8, l3);
        return true;
    }

    @Override
    public void setRedraw(boolean bl) {
        this.checkWidget();
        this.setDropDownItems(false);
        super.setRedraw(bl);
        this.setDropDownItems(true);
    }

    void setRowCount(int n) {
        if ((this.style & 0x200) != 0) {
            RECT rECT = new RECT();
            OS.GetWindowRect(this.handle, rECT);
            OS.MapWindowPoints(0L, this.parent.handle, rECT, 2);
            this.ignoreResize = true;
            OS.SendMessage(this.handle, 1063, OS.MAKEWPARAM(n += 2, 1), 0L);
            int n2 = 22;
            OS.SetWindowPos(this.handle, 0L, 0, 0, rECT.right - rECT.left, rECT.bottom - rECT.top, 22);
            this.ignoreResize = false;
        }
    }

    void setTabItemList(ToolItem[] toolItemArray) {
        this.checkWidget();
        if (toolItemArray != null) {
            for (ToolItem toolItem : toolItemArray) {
                if (toolItem == null) {
                    this.error(5);
                }
                if (toolItem.isDisposed()) {
                    this.error(5);
                }
                if (toolItem.parent == this) continue;
                this.error(32);
            }
            ToolItem[] toolItemArray2 = new ToolItem[toolItemArray.length];
            System.arraycopy(toolItemArray, 0, toolItemArray2, 0, toolItemArray.length);
            toolItemArray = toolItemArray2;
        }
        this.tabItemList = toolItemArray;
    }

    @Override
    boolean setTabItemFocus() {
        ToolItem toolItem;
        int n;
        for (n = 0; !(n >= this.items.length || (toolItem = this.items[n]) != null && (toolItem.style & 2) == 0 && toolItem.getEnabled()); ++n) {
        }
        return n != this.items.length && super.setTabItemFocus();
    }

    @Override
    boolean updateTextDirection(int n) {
        if (super.updateTextDirection(n)) {
            ToolItem[] toolItemArray = this._getItems();
            int n2 = toolItemArray.length;
            while (n2-- > 0) {
                toolItemArray[n2].updateTextDirection(this.style & Integer.MIN_VALUE);
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
        if (!this.hasCursor()) {
            return "";
        }
        int n = (int)nMTTDISPINFO.idFrom;
        long l2 = OS.SendMessage(this.handle, 1059, 0L, 0L);
        if (l2 == nMTTDISPINFO.hwndFrom) {
            ToolItem toolItem;
            if (this.currentToolItemToolTip != l2) {
                this.maybeEnableDarkSystemTheme(nMTTDISPINFO.hwndFrom);
                this.currentToolItemToolTip = nMTTDISPINFO.hwndFrom;
            }
            int n2 = -2080374784;
            nMTTDISPINFO.uFlags = (this.style & 0x84000000) != 0 && (this.style & 0x84000000) != -2080374784 ? (nMTTDISPINFO.uFlags |= 4) : (nMTTDISPINFO.uFlags &= 0xFFFFFFFB);
            if (this.toolTipText != null) {
                return "";
            }
            if (0 <= n && n < this.items.length && (toolItem = this.items[n]) != null) {
                if (this.lastArrowId != -1) {
                    return "";
                }
                return toolItem.toolTipText;
            }
        }
        return super.toolTipText(nMTTDISPINFO);
    }

    @Override
    void updateOrientation() {
        super.updateOrientation();
        if (this.imageList != null) {
            Point point = this.imageList.getImageSize();
            ImageList imageList = this.display.getImageListToolBar(this.style & 0x4000000, point.x, point.y);
            ImageList imageList2 = this.display.getImageListToolBarHot(this.style & 0x4000000, point.x, point.y);
            ImageList imageList3 = this.display.getImageListToolBarDisabled(this.style & 0x4000000, point.x, point.y);
            TBBUTTONINFO tBBUTTONINFO = new TBBUTTONINFO();
            tBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
            tBBUTTONINFO.dwMask = 1;
            int n = (int)OS.SendMessage(this.handle, 1048, 0L, 0L);
            for (int i = 0; i < n; ++i) {
                ToolItem toolItem = this.items[i];
                if ((toolItem.style & 2) != 0 || toolItem.image == null) continue;
                OS.SendMessage(this.handle, 1087, (long)toolItem.id, tBBUTTONINFO);
                if (tBBUTTONINFO.iImage == -2) continue;
                Image image = this.imageList.get(tBBUTTONINFO.iImage);
                Image image2 = this.hotImageList.get(tBBUTTONINFO.iImage);
                Image image3 = this.disabledImageList.get(tBBUTTONINFO.iImage);
                this.imageList.put(tBBUTTONINFO.iImage, null);
                this.hotImageList.put(tBBUTTONINFO.iImage, null);
                this.disabledImageList.put(tBBUTTONINFO.iImage, null);
                tBBUTTONINFO.iImage = imageList.add(image);
                imageList2.add(image2);
                imageList3.add(image3);
                OS.SendMessage(this.handle, 1088, (long)toolItem.id, tBBUTTONINFO);
            }
            this.display.releaseToolImageList(this.imageList);
            this.display.releaseToolHotImageList(this.hotImageList);
            this.display.releaseToolDisabledImageList(this.disabledImageList);
            OS.SendMessage(this.handle, 1072, 0L, imageList.getHandle());
            OS.SendMessage(this.handle, 1076, 0L, imageList2.getHandle());
            OS.SendMessage(this.handle, 1078, 0L, imageList3.getHandle());
            this.imageList = imageList;
            this.hotImageList = imageList2;
            this.disabledImageList = imageList3;
            OS.InvalidateRect(this.handle, null, true);
        }
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle() | 4 | 0x100 | 0x2000;
        if (OS.IsAppThemed()) {
            n |= 0x8000;
        }
        if ((this.style & 8) == 0) {
            n |= 0x40;
        }
        if ((this.style & 0x40) != 0) {
            n |= 0x200;
        }
        if ((this.style & 0x800000) != 0) {
            n |= 0x800;
        }
        if (!OS.IsAppThemed() && (this.style & 0x20000) != 0) {
            n |= 0x1000;
        }
        return n;
    }

    @Override
    TCHAR windowClass() {
        return ToolBarClass;
    }

    @Override
    long windowProc() {
        return ToolBarProc;
    }

    @Override
    LRESULT WM_CAPTURECHANGED(long l2, long l3) {
        LRESULT lRESULT = super.WM_CAPTURECHANGED(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        for (ToolItem toolItem : this.items) {
            int n;
            if (toolItem == null || ((n = (int)OS.SendMessage(this.handle, 1042, (long)toolItem.id, 0L)) & 2) == 0) continue;
            OS.SendMessage(this.handle, 1041, (long)toolItem.id, n &= 0xFFFFFFFD);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_CHAR(long l2, long l3) {
        LRESULT lRESULT = super.WM_CHAR(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        switch ((int)l2) {
            case 32: {
                TBBUTTON tBBUTTON;
                long l4;
                int n = (int)OS.SendMessage(this.handle, 1095, 0L, 0L);
                if (n == -1 || (l4 = OS.SendMessage(this.handle, 1047, (long)n, tBBUTTON = new TBBUTTON())) == 0L) break;
                this.items[tBBUTTON.idCommand].click(false);
                return LRESULT.ZERO;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_COMMAND(long l2, long l3) {
        LRESULT lRESULT = super.WM_COMMAND(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return LRESULT.ZERO;
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
    LRESULT WM_KEYDOWN(long l2, long l3) {
        LRESULT lRESULT = super.WM_KEYDOWN(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        switch ((int)l2) {
            case 32: {
                return LRESULT.ZERO;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_KILLFOCUS(long l2, long l3) {
        TBBUTTON tBBUTTON;
        int n = (int)OS.SendMessage(this.handle, 1095, 0L, 0L);
        long l4 = OS.SendMessage(this.handle, 1047, (long)n, tBBUTTON = new TBBUTTON());
        if (l4 != 0L) {
            this.lastFocusId = tBBUTTON.idCommand;
        }
        return super.WM_KILLFOCUS(l2, l3);
    }

    @Override
    LRESULT WM_LBUTTONDOWN(long l2, long l3) {
        if (this.ignoreMouse) {
            return null;
        }
        return super.WM_LBUTTONDOWN(l2, l3);
    }

    @Override
    LRESULT WM_LBUTTONUP(long l2, long l3) {
        if (this.ignoreMouse) {
            return null;
        }
        return super.WM_LBUTTONUP(l2, l3);
    }

    @Override
    LRESULT WM_MOUSELEAVE(long l2, long l3) {
        LRESULT lRESULT = super.WM_MOUSELEAVE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        TOOLINFO tOOLINFO = new TOOLINFO();
        tOOLINFO.cbSize = TOOLINFO.sizeof;
        long l4 = OS.SendMessage(this.handle, 1059, 0L, 0L);
        if (OS.SendMessage(l4, 1083, 0L, tOOLINFO) != 0L && (tOOLINFO.uFlags & 1) == 0) {
            OS.SendMessage(l4, 1075, 0L, tOOLINFO);
            OS.SendMessage(l4, 1074, 0L, tOOLINFO);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_MOUSEMOVE(long l2, long l3) {
        if (OS.GetMessagePos() != this.display.lastMouse) {
            this.lastArrowId = -1;
        }
        return super.WM_MOUSEMOVE(l2, l3);
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
    LRESULT WM_SETFOCUS(long l2, long l3) {
        LRESULT lRESULT = super.WM_SETFOCUS(l2, l3);
        if (this.lastFocusId != -1 && this.handle == OS.GetFocus()) {
            int n = (int)OS.SendMessage(this.handle, 1049, (long)this.lastFocusId, 0L);
            OS.SendMessage(this.handle, 1096, (long)n, 0L);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        if (this.ignoreResize) {
            long l4 = this.callWindowProc(this.handle, 5, l2, l3);
            if (l4 == 0L) {
                return LRESULT.ZERO;
            }
            return new LRESULT(l4);
        }
        LRESULT lRESULT = super.WM_SIZE(l2, l3);
        if (this.isDisposed()) {
            return lRESULT;
        }
        if ((this.style & 0x800) != 0 && (this.style & 0x40) != 0) {
            int n;
            RECT rECT = new RECT();
            OS.GetWindowRect(this.handle, rECT);
            int n2 = this.getBorderWidthInPixels() * 2;
            RECT rECT2 = new RECT();
            int n3 = (int)OS.SendMessage(this.handle, 1048, 0L, 0L);
            for (n = 0; n < n3; ++n) {
                OS.SendMessage(this.handle, 1053, (long)n, rECT2);
                OS.MapWindowPoints(this.handle, 0L, rECT2, 2);
                if (rECT2.right > rECT.right - n2 * 2) break;
            }
            int n4 = (int)OS.SendMessage(this.handle, 1109, 0L, 0L);
            n4 = n == n3 ? (n4 |= 0x10) : (n4 &= 0xFFFFFFEF);
            OS.SendMessage(this.handle, 1108, 0L, n4);
        }
        this.layoutItems();
        return lRESULT;
    }

    @Override
    LRESULT WM_WINDOWPOSCHANGING(long l2, long l3) {
        LRESULT lRESULT = super.WM_WINDOWPOSCHANGING(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (this.ignoreResize) {
            return lRESULT;
        }
        if (!this.getDrawing()) {
            return lRESULT;
        }
        if ((this.style & 0x40) == 0) {
            return lRESULT;
        }
        if (!OS.IsWindowVisible(this.handle)) {
            return lRESULT;
        }
        if (OS.SendMessage(this.handle, 1064, 0L, 0L) == 1L) {
            return lRESULT;
        }
        WINDOWPOS wINDOWPOS = new WINDOWPOS();
        OS.MoveMemory(wINDOWPOS, l3, WINDOWPOS.sizeof);
        if ((wINDOWPOS.flags & 9) != 0) {
            return lRESULT;
        }
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        RECT rECT2 = new RECT();
        OS.SetRect(rECT2, 0, 0, wINDOWPOS.cx, wINDOWPOS.cy);
        OS.SendMessage(this.handle, 131, 0L, rECT2);
        int n = rECT.right - rECT.left;
        int n2 = rECT2.right - rECT2.left;
        if (n2 > n) {
            RECT rECT3 = new RECT();
            int n3 = rECT2.bottom - rECT2.top;
            OS.SetRect(rECT3, n - 2, 0, n, n3);
            OS.InvalidateRect(this.handle, rECT3, false);
        }
        return lRESULT;
    }

    @Override
    LRESULT wmCommandChild(long l2, long l3) {
        ToolItem toolItem = this.items[OS.LOWORD(l2)];
        if (toolItem == null) {
            return null;
        }
        return toolItem.wmCommandChild(l2, l3);
    }

    int getForegroundPixel(ToolItem toolItem) {
        if (toolItem != null && toolItem.foreground != -1) {
            return toolItem.foreground;
        }
        return this.getForegroundPixel();
    }

    int getBackgroundPixel(ToolItem toolItem) {
        if (toolItem != null && toolItem.background != -1) {
            return toolItem.background;
        }
        return this.getBackgroundPixel();
    }

    @Override
    LRESULT wmNotifyChild(NMHDR nMHDR, long l2, long l3) {
        switch (nMHDR.code) {
            case -710: {
                NMTOOLBAR nMTOOLBAR = new NMTOOLBAR();
                OS.MoveMemory(nMTOOLBAR, l3, NMTOOLBAR.sizeof);
                ToolItem toolItem = this.items[nMTOOLBAR.iItem];
                if (toolItem == null) break;
                Event event = new Event();
                event.detail = 4;
                int n = (int)OS.SendMessage(this.handle, 1049, (long)nMTOOLBAR.iItem, 0L);
                RECT rECT = new RECT();
                OS.SendMessage(this.handle, 1053, (long)n, rECT);
                event.setLocationInPixels(rECT.left, rECT.bottom);
                toolItem.sendSelectionEvent(13, event, false);
                break;
            }
            case -12: {
                NMTBCUSTOMDRAW nMTBCUSTOMDRAW = new NMTBCUSTOMDRAW();
                OS.MoveMemory(nMTBCUSTOMDRAW, l3, NMTBCUSTOMDRAW.sizeof);
                ToolItem toolItem = this.items[(int)nMTBCUSTOMDRAW.dwItemSpec];
                switch (nMTBCUSTOMDRAW.dwDrawStage) {
                    case 3: {
                        int n = OS.GetWindowLong(this.handle, -16);
                        if ((n & 0x800) == 0) {
                            this.drawBackground(nMTBCUSTOMDRAW.hdc);
                        } else {
                            RECT rECT = new RECT();
                            OS.SetRect(rECT, nMTBCUSTOMDRAW.left, nMTBCUSTOMDRAW.top, nMTBCUSTOMDRAW.right, nMTBCUSTOMDRAW.bottom);
                            this.drawBackground(nMTBCUSTOMDRAW.hdc, rECT);
                        }
                        return new LRESULT(4L);
                    }
                    case 1: {
                        long l4 = 0L;
                        if (this.background != -1 || this.foreground != -1 && OS.IsWindowEnabled(this.handle) || (this.state & 0x1000000) != 0) {
                            l4 = 32L;
                        }
                        return new LRESULT(l4);
                    }
                    case 65537: {
                        long l5 = 0x800000L;
                        nMTBCUSTOMDRAW.clrBtnFace = this.getBackgroundPixel(toolItem);
                        nMTBCUSTOMDRAW.clrText = this.getForegroundPixel(toolItem);
                        OS.MoveMemory(l3, nMTBCUSTOMDRAW, NMTBCUSTOMDRAW.sizeof);
                        if (toolItem != null && toolItem.background != -1) {
                            RECT rECT = new RECT(nMTBCUSTOMDRAW.left, nMTBCUSTOMDRAW.top, nMTBCUSTOMDRAW.right, nMTBCUSTOMDRAW.bottom);
                            OS.SetDCBrushColor(nMTBCUSTOMDRAW.hdc, toolItem.background);
                            OS.FillRect(nMTBCUSTOMDRAW.hdc, rECT, OS.GetStockObject(18));
                            l5 |= 0x400000L;
                        }
                        return new LRESULT(l5);
                    }
                }
                break;
            }
            case -713: {
                NMTBHOTITEM nMTBHOTITEM = new NMTBHOTITEM();
                OS.MoveMemory(nMTBHOTITEM, l3, NMTBHOTITEM.sizeof);
                switch (nMTBHOTITEM.dwFlags) {
                    case 1: {
                        if (this.lastArrowId == -1) break;
                        return LRESULT.ONE;
                    }
                    case 2: {
                        RECT rECT = new RECT();
                        OS.GetClientRect(this.handle, rECT);
                        int n = (int)OS.SendMessage(this.handle, 1049, (long)nMTBHOTITEM.idNew, 0L);
                        RECT rECT2 = new RECT();
                        OS.SendMessage(this.handle, 1053, (long)n, rECT2);
                        if (rECT2.right > rECT.right || rECT2.bottom > rECT.bottom) {
                            return LRESULT.ONE;
                        }
                        this.lastArrowId = nMTBHOTITEM.idNew;
                        break;
                    }
                    default: {
                        this.lastArrowId = -1;
                    }
                }
                if ((nMTBHOTITEM.dwFlags & 0x20) != 0) break;
                this.lastHotId = nMTBHOTITEM.idNew;
                break;
            }
        }
        return super.wmNotifyChild(nMHDR, l2, l3);
    }

    static {
        ToolBarClass = new TCHAR(0, "ToolbarWindow32", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, ToolBarClass, wNDCLASS);
        ToolBarProc = wNDCLASS.lpfnWndProc;
    }
}

