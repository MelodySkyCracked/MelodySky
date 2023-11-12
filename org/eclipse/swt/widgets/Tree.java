/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import java.io.Serializable;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.HDHITTESTINFO;
import org.eclipse.swt.internal.win32.HDITEM;
import org.eclipse.swt.internal.win32.HDLAYOUT;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMTTCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMTTDISPINFO;
import org.eclipse.swt.internal.win32.NMTVCUSTOMDRAW;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLBARINFO;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.SHDRAGIMAGE;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.internal.win32.TVHITTESTINFO;
import org.eclipse.swt.internal.win32.TVINSERTSTRUCT;
import org.eclipse.swt.internal.win32.TVITEM;
import org.eclipse.swt.internal.win32.TVSORTCB;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class Tree
extends Composite {
    TreeItem[] items;
    TreeColumn[] columns;
    int columnCount;
    ImageList imageList;
    ImageList headerImageList;
    TreeItem currentItem;
    TreeColumn sortColumn;
    RECT focusRect;
    long hwndParent;
    long hwndHeader;
    long hAnchor;
    long hInsert;
    long hSelect;
    int lastID;
    long hFirstIndexOf;
    long hLastIndexOf;
    int lastIndexOf;
    int itemCount;
    int sortDirection;
    boolean dragStarted;
    boolean gestureCompleted;
    boolean insertAfter;
    boolean shrink;
    boolean ignoreShrink;
    boolean ignoreSelect;
    boolean ignoreExpand;
    boolean ignoreDeselect;
    boolean ignoreResize;
    boolean lockSelection;
    boolean oldSelected;
    boolean newSelected;
    boolean ignoreColumnMove;
    boolean linesVisible;
    boolean customDraw;
    boolean painted;
    boolean ignoreItemHeight;
    boolean ignoreCustomDraw;
    boolean ignoreDrawForeground;
    boolean ignoreDrawBackground;
    boolean ignoreDrawFocus;
    boolean ignoreDrawSelection;
    boolean ignoreDrawHot;
    boolean ignoreFullSelection;
    boolean explorerTheme;
    boolean createdAsRTL;
    boolean headerItemDragging;
    int scrollWidth;
    int selectionForeground;
    long headerToolTipHandle;
    long itemToolTipHandle;
    long lastTimerID = -1L;
    int lastTimerCount;
    int headerBackground = -1;
    int headerForeground = -1;
    static final boolean ENABLE_TVS_EX_FADEINOUTEXPANDOS = System.getProperty("org.eclipse.swt.internal.win32.enableFadeInOutExpandos") != null;
    static final int TIMER_MAX_COUNT = 8;
    static final int INSET = 3;
    static final int GRID_WIDTH = 1;
    static final int SORT_WIDTH = 10;
    static final int HEADER_MARGIN = 12;
    static final int HEADER_EXTRA = 3;
    static final int INCREMENT = 5;
    static final int EXPLORER_EXTRA = 2;
    static final int DRAG_IMAGE_SIZE = 301;
    static final long TreeProc;
    static final TCHAR TreeClass;
    static final long HeaderProc;
    static final TCHAR HeaderClass;

    public Tree(Composite composite, int n) {
        super(composite, Tree.checkStyle(n));
    }

    static int checkStyle(int n) {
        if ((n & 0x10) == 0) {
            n |= 0x300;
        }
        if ((n & 0x100) != 0 && (n & 0x200) == 0) {
            n |= 0x200;
        }
        return Widget.checkBits(n, 4, 2, 0, 0, 0, 0);
    }

    @Override
    void _addListener(int n, Listener listener) {
        super._addListener(n, listener);
        switch (n) {
            case 29: {
                if ((this.state & 0x8000) == 0) break;
                int n2 = OS.GetWindowLong(this.handle, -16);
                OS.SetWindowLong(this.handle, -16, n2 &= 0xFFFFFFEF);
                break;
            }
            case 40: 
            case 41: 
            case 42: {
                this.customDraw = true;
                this.style |= 0x20000000;
                if (this.isCustomToolTip()) {
                    this.createItemToolTips();
                }
                OS.SendMessage(this.handle, 4385, 0L, 0L);
                int n3 = OS.GetWindowLong(this.handle, -16);
                if (n == 41) {
                    n3 |= 0x8000;
                }
                if ((this.style & 0x10000) != 0 && n != 41 && !this.explorerTheme) {
                    n3 &= 0xFFFFEFFF;
                }
                if (n3 == OS.GetWindowLong(this.handle, -16)) break;
                OS.SetWindowLong(this.handle, -16, n3);
                OS.InvalidateRect(this.handle, null, true);
                int n4 = (int)OS.SendMessage(this.handle, 4357, 0L, 0L);
                if (n4 == 0 || (n3 & 0x8000) == 0) break;
                OS.ShowScrollBar(this.handle, 0, false);
                break;
            }
        }
    }

    TreeItem _getItem(long l2) {
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 20;
        tVITEM.hItem = l2;
        if (OS.SendMessage(this.handle, 4414, 0L, tVITEM) != 0L) {
            return this._getItem(tVITEM.hItem, (int)tVITEM.lParam);
        }
        return null;
    }

    TreeItem _getItem(long l2, int n) {
        if ((this.style & 0x10000000) == 0) {
            return this.items[n];
        }
        return n != -1 ? this.items[n] : new TreeItem(this, 0, -1L, -1L, l2);
    }

    @Override
    void _removeListener(int n, Listener listener) {
        super._removeListener(n, listener);
        switch (n) {
            case 41: {
                if ((this.style & 0x100) == 0 || (this.state & 0x1000) != 0) break;
                int n2 = OS.GetWindowLong(this.handle, -16);
                OS.SetWindowLong(this.handle, -16, n2 &= 0xFFFF7FFF);
                OS.InvalidateRect(this.handle, null, true);
                break;
            }
        }
    }

    void _setBackgroundPixel(int n) {
        int n2 = (int)OS.SendMessage(this.handle, 4383, 0L, 0L);
        if (n2 != n) {
            if (n2 != -1) {
                OS.SendMessage(this.handle, 4381, 0L, -1L);
            }
            OS.SendMessage(this.handle, 4381, 0L, n);
            if (this.explorerTheme && ENABLE_TVS_EX_FADEINOUTEXPANDOS) {
                int n3 = (int)OS.SendMessage(this.handle, 4397, 0L, 0L);
                n3 = n == -1 && this.findImageControl() == null ? (n3 |= 0x40) : (n3 &= 0xFFFFFFBF);
                OS.SendMessage(this.handle, 4396, 0L, n3);
            }
            if ((this.style & 0x20) != 0) {
                this.setCheckboxImageList();
            }
        }
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

    public void addTreeListener(TreeListener treeListener) {
        this.checkWidget();
        if (treeListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(treeListener);
        this.addListener(17, typedListener);
        this.addListener(18, typedListener);
    }

    @Override
    long borderHandle() {
        return this.hwndParent != 0L ? this.hwndParent : this.handle;
    }

    LRESULT CDDS_ITEMPOSTPAINT(NMTVCUSTOMDRAW nMTVCUSTOMDRAW, long l2, long l3) {
        long l4;
        Object object;
        int n;
        int n2;
        boolean bl;
        if (this.ignoreCustomDraw) {
            return null;
        }
        if (nMTVCUSTOMDRAW.left == nMTVCUSTOMDRAW.right) {
            return new LRESULT(0L);
        }
        long l5 = nMTVCUSTOMDRAW.hdc;
        OS.RestoreDC(l5, -1);
        TreeItem treeItem = this.getItem(nMTVCUSTOMDRAW);
        if (treeItem == null) {
            return null;
        }
        if (nMTVCUSTOMDRAW.left >= nMTVCUSTOMDRAW.right || nMTVCUSTOMDRAW.top >= nMTVCUSTOMDRAW.bottom) {
            return null;
        }
        if (!OS.IsWindowVisible(this.handle)) {
            return null;
        }
        if ((this.style & 0x10000) != 0 || this.findImageControl() != null || this.ignoreDrawSelection || this.explorerTheme) {
            OS.SetBkMode(l5, 1);
        }
        boolean bl2 = this.isItemSelected(nMTVCUSTOMDRAW);
        boolean bl3 = bl = this.explorerTheme && (nMTVCUSTOMDRAW.uItemState & 0x40) != 0;
        if (OS.IsWindowEnabled(this.handle) && this.explorerTheme && ((n2 = OS.GetWindowLong(this.handle, -16)) & 0x200) != 0) {
            OS.SetTextColor(l5, this.getForegroundPixel());
        }
        int[] nArray = null;
        RECT rECT = new RECT();
        OS.GetClientRect(this.scrolledHandle(), rECT);
        if (this.hwndHeader != 0L) {
            OS.MapWindowPoints(this.hwndParent, this.handle, rECT, 2);
            if (this.columnCount != 0) {
                nArray = new int[this.columnCount];
                OS.SendMessage(this.hwndHeader, 4625, (long)this.columnCount, nArray);
            }
        }
        int n3 = -1;
        int n4 = -1;
        if (OS.IsAppThemed() && this.sortColumn != null && this.sortDirection != 0 && this.findImageControl() == null) {
            n3 = this.indexOf(this.sortColumn);
            n4 = this.getSortColumnPixel();
        }
        int n5 = 0;
        Point point = null;
        for (n = 0; n < Math.max(1, this.columnCount); ++n) {
            int n6;
            int n7 = nArray == null ? n : nArray[n];
            int n8 = nMTVCUSTOMDRAW.right - nMTVCUSTOMDRAW.left;
            if (this.columnCount > 0 && this.hwndHeader != 0L) {
                object = new HDITEM();
                ((HDITEM)object).mask = 1;
                OS.SendMessage(this.hwndHeader, 4619, (long)n7, (HDITEM)object);
                n8 = ((HDITEM)object).cxy;
            }
            if (n == 0 && (this.style & 0x10000) != 0) {
                boolean bl4;
                boolean bl5 = bl4 = !this.explorerTheme && !this.ignoreDrawSelection && this.findImageControl() == null;
                if (bl4 || bl2 && !this.ignoreDrawSelection || bl && !this.ignoreDrawHot) {
                    boolean bl6 = true;
                    RECT rECT2 = new RECT();
                    OS.SetRect(rECT2, n8, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.right, nMTVCUSTOMDRAW.bottom);
                    if (this.explorerTheme) {
                        Object object2;
                        RECT rECT3;
                        if (this.hooks(40)) {
                            RECT rECT4 = rECT3 = treeItem.getBounds(n7, true, true, false, false, true, l5);
                            rECT3.left -= 2;
                            object2 = rECT4;
                            ((RECT)object2).right += 3;
                            rECT2.left = rECT4.left;
                            rECT2.right = rECT4.right;
                            if (this.columnCount > 0 && this.hwndHeader != 0L) {
                                HDITEM hDITEM = new HDITEM();
                                hDITEM.mask = 1;
                                OS.SendMessage(this.hwndHeader, 4619, (long)n7, hDITEM);
                                rECT2.right = Math.min(rECT2.right, nMTVCUSTOMDRAW.left + hDITEM.cxy);
                            }
                        }
                        rECT3 = new RECT();
                        OS.SetRect(rECT3, nMTVCUSTOMDRAW.left, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.right, nMTVCUSTOMDRAW.bottom);
                        if (this.columnCount > 0 && this.hwndHeader != 0L) {
                            int n9 = 0;
                            object2 = new HDITEM();
                            ((HDITEM)object2).mask = 1;
                            for (int i = 0; i < this.columnCount; ++i) {
                                OS.SendMessage(this.hwndHeader, 4619, (long)i, (HDITEM)object2);
                                n9 += ((HDITEM)object2).cxy;
                            }
                            if (n9 > rECT.right - rECT.left) {
                                rECT3.left = 0;
                                rECT3.right = n9;
                            } else {
                                rECT3.left = rECT.left;
                                rECT3.right = rECT.right;
                            }
                        }
                        bl6 = false;
                        long l6 = OS.OpenThemeData(this.handle, Display.TREEVIEW);
                        int n10 = n6 = bl2 ? 3 : 2;
                        if (OS.GetFocus() != this.handle && bl2 && !bl) {
                            n6 = 5;
                        }
                        OS.DrawThemeBackground(l6, l5, 1, n6, rECT3, rECT2);
                        OS.CloseThemeData(l6);
                    }
                    if (bl6) {
                        this.fillBackground(l5, OS.GetBkColor(l5), rECT2);
                    }
                }
            }
            if (n5 + n8 > rECT.left) {
                int n11;
                Object object3;
                Object object4;
                Object object5;
                object = new RECT();
                Object object6 = null;
                boolean bl7 = true;
                boolean bl8 = true;
                boolean bl9 = true;
                boolean bl10 = false;
                if (n == 0) {
                    bl8 = false;
                    bl7 = false;
                    bl9 = false;
                    if (this.findImageControl() != null) {
                        if (this.explorerTheme) {
                            if (OS.IsWindowEnabled(this.handle) && !this.hooks(40)) {
                                Object object7;
                                Image image = null;
                                if (n7 == 0) {
                                    image = treeItem.image;
                                } else {
                                    object7 = treeItem.images;
                                    if (object7 != null) {
                                        image = object7[n7];
                                    }
                                }
                                if (image != null) {
                                    object7 = image.getBounds();
                                    if (point == null) {
                                        point = DPIUtil.autoScaleDown(this.getImageSize());
                                    }
                                    if (!this.ignoreDrawForeground) {
                                        GCData gCData = new GCData();
                                        gCData.device = this.display;
                                        GC gC = GC.win32_new(l5, gCData);
                                        RECT rECT5 = treeItem.getBounds(n7, false, true, false, false, true, l5);
                                        gC.setClipping(DPIUtil.autoScaleDown(new Rectangle(rECT5.left, rECT5.top, rECT5.right - rECT5.left, rECT5.bottom - rECT5.top)));
                                        gC.drawImage(image, 0, 0, object7.width, object7.height, DPIUtil.autoScaleDown(rECT5.left), DPIUtil.autoScaleDown(rECT5.top), point.x, point.y);
                                        OS.SelectClipRgn(l5, 0L);
                                        gC.dispose();
                                    }
                                }
                            }
                        } else {
                            bl10 = true;
                            bl7 = true;
                            bl8 = true;
                            object = treeItem.getBounds(n7, true, false, false, false, true, l5);
                            if (this.linesVisible) {
                                Object object8 = object;
                                ++((RECT)object8).right;
                                Object object9 = object;
                                ++((RECT)object9).bottom;
                            }
                        }
                    }
                    if (bl2 && !this.ignoreDrawSelection && !this.ignoreDrawBackground) {
                        if (!this.explorerTheme) {
                            this.fillBackground(l5, OS.GetBkColor(l5), (RECT)object);
                        }
                        bl10 = false;
                    }
                    object6 = object;
                    if (this.hooks(40)) {
                        bl9 = true;
                        bl7 = true;
                        bl8 = true;
                        object = treeItem.getBounds(n7, true, true, false, false, true, l5);
                        object6 = (this.style & 0x10000) != 0 ? object : treeItem.getBounds(n7, true, false, false, false, true, l5);
                    }
                } else {
                    this.selectionForeground = -1;
                    n6 = 0;
                    this.ignoreDrawHot = false;
                    this.ignoreDrawFocus = false;
                    this.ignoreDrawSelection = false;
                    this.ignoreDrawBackground = false;
                    this.ignoreDrawForeground = false;
                    OS.SetRect((RECT)object, n5, nMTVCUSTOMDRAW.top, n5 + n8, nMTVCUSTOMDRAW.bottom);
                    object6 = object;
                }
                n6 = -1;
                int n12 = -1;
                long l7 = treeItem.fontHandle(n7);
                if (this.selectionForeground != -1) {
                    n6 = this.selectionForeground;
                }
                if (OS.IsWindowEnabled(this.handle)) {
                    boolean bl11 = false;
                    if (bl2) {
                        if (n != 0 && (this.style & 0x10000) == 0) {
                            OS.SetTextColor(l5, this.getForegroundPixel());
                            OS.SetBkColor(l5, this.getBackgroundPixel());
                            bl11 = true;
                            bl10 = true;
                        }
                    } else {
                        bl11 = true;
                        bl10 = true;
                    }
                    if (bl11) {
                        int n13 = n6 = treeItem.cellForeground != null ? treeItem.cellForeground[n7] : -1;
                        if (n6 == -1) {
                            n6 = treeItem.foreground;
                        }
                    }
                    if (bl10) {
                        int n14 = n12 = treeItem.cellBackground != null ? treeItem.cellBackground[n7] : -1;
                        if (n12 == -1) {
                            n12 = treeItem.background;
                        }
                        if (n12 == -1 && n7 == n3) {
                            n12 = n4;
                        }
                    }
                } else if (n12 == -1 && n7 == n3) {
                    bl10 = true;
                    n12 = n4;
                }
                if (this.explorerTheme && (bl2 || (nMTVCUSTOMDRAW.uItemState & 0x40) != 0)) {
                    if ((this.style & 0x10000) != 0) {
                        bl10 = false;
                    } else if (n == 0) {
                        bl10 = false;
                        if (!this.hooks(40)) {
                            bl8 = false;
                        }
                    }
                }
                if (bl7) {
                    Object object10;
                    int n15;
                    if (n != 0) {
                        if (this.hooks(41)) {
                            this.sendMeasureItemEvent(treeItem, n7, l5, bl2 ? 2 : 0);
                            if (this.isDisposed() || treeItem.isDisposed()) break;
                        }
                        if (this.hooks(40)) {
                            Object object11;
                            RECT rECT6 = treeItem.getBounds(n7, true, true, true, true, true, l5);
                            n15 = OS.SaveDC(l5);
                            object10 = new GCData();
                            object10.device = this.display;
                            object10.foreground = OS.GetTextColor(l5);
                            object10.background = OS.GetBkColor(l5);
                            if (!bl2 || (this.style & 0x10000) == 0) {
                                if (n6 != -1) {
                                    object10.foreground = n6;
                                }
                                if (n12 != -1) {
                                    object10.background = n12;
                                }
                            }
                            object10.font = treeItem.getFont(n7);
                            object10.uiState = (int)OS.SendMessage(this.handle, 297, 0L, 0L);
                            object5 = GC.win32_new(l5, (GCData)object10);
                            object4 = new Event();
                            ((Event)object4).item = treeItem;
                            ((Event)object4).index = n7;
                            ((Event)object4).gc = object5;
                            object3 = object4;
                            ((Event)object3).detail |= 0x10;
                            if (n12 != -1) {
                                object11 = object4;
                                ((Event)object11).detail |= 8;
                            }
                            if ((this.style & 0x10000) != 0) {
                                int n16;
                                if (bl) {
                                    object11 = object4;
                                    ((Event)object11).detail |= 0x20;
                                }
                                if (bl2) {
                                    object11 = object4;
                                    ((Event)object11).detail |= 2;
                                }
                                if (!this.explorerTheme && OS.SendMessage(this.handle, 4362, 9L, 0L) == nMTVCUSTOMDRAW.dwItemSpec && this.handle == OS.GetFocus() && ((n16 = (int)OS.SendMessage(this.handle, 297, 0L, 0L)) & 1) == 0) {
                                    Object object12 = object4;
                                    ((Event)object12).detail |= 4;
                                }
                            }
                            Rectangle rectangle = new Rectangle(rECT6.left, rECT6.top, rECT6.right - rECT6.left, rECT6.bottom - rECT6.top);
                            ((Event)object4).setBoundsInPixels(rectangle);
                            ((GC)object5).setClipping(DPIUtil.autoScaleDown(rectangle));
                            this.sendEvent(40, (Event)object4);
                            ((Event)object4).gc = null;
                            int n17 = object10.foreground;
                            ((Resource)object5).dispose();
                            OS.RestoreDC(l5, n15);
                            if (this.isDisposed() || treeItem.isDisposed()) break;
                            if (((Event)object4).doit) {
                                this.ignoreDrawForeground = (((Event)object4).detail & 0x10) == 0;
                                boolean bl12 = this.ignoreDrawBackground = (((Event)object4).detail & 8) == 0;
                                if ((this.style & 0x10000) != 0) {
                                    this.ignoreDrawSelection = (((Event)object4).detail & 2) == 0;
                                    this.ignoreDrawFocus = (((Event)object4).detail & 4) == 0;
                                    this.ignoreDrawHot = (((Event)object4).detail & 0x20) == 0;
                                }
                            } else {
                                n11 = 1;
                                this.ignoreDrawHot = true;
                                this.ignoreDrawFocus = true;
                                this.ignoreDrawSelection = true;
                                this.ignoreDrawBackground = true;
                                this.ignoreDrawForeground = true;
                            }
                            if (bl2 && this.ignoreDrawSelection) {
                                this.ignoreDrawHot = true;
                            }
                            if ((this.style & 0x10000) != 0) {
                                if (this.ignoreDrawSelection) {
                                    this.ignoreFullSelection = true;
                                }
                                if (!this.ignoreDrawSelection || !this.ignoreDrawHot) {
                                    if (!bl2 && !bl) {
                                        this.selectionForeground = OS.GetSysColor(14);
                                    } else if (!this.explorerTheme) {
                                        bl10 = true;
                                        this.ignoreDrawBackground = false;
                                        n12 = (this.handle == OS.GetFocus() || this.display.getHighContrast()) && OS.IsWindowEnabled(this.handle) ? OS.GetSysColor(13) : OS.GetSysColor(15);
                                        if (!this.ignoreFullSelection && n7 == this.columnCount - 1) {
                                            RECT rECT7 = new RECT();
                                            OS.SetRect(rECT7, ((RECT)object6).left, ((RECT)object6).top, nMTVCUSTOMDRAW.right, ((RECT)object6).bottom);
                                            object6 = rECT7;
                                        }
                                    } else {
                                        int n18;
                                        RECT rECT8 = new RECT();
                                        OS.SetRect(rECT8, nMTVCUSTOMDRAW.left, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.right, nMTVCUSTOMDRAW.bottom);
                                        if (this.columnCount > 0 && this.hwndHeader != 0L) {
                                            int n19 = 0;
                                            HDITEM hDITEM = new HDITEM();
                                            hDITEM.mask = 1;
                                            for (n18 = 0; n18 < this.columnCount; ++n18) {
                                                OS.SendMessage(this.hwndHeader, 4619, (long)n18, hDITEM);
                                                n19 += hDITEM.cxy;
                                            }
                                            if (n19 > rECT.right - rECT.left) {
                                                rECT8.left = 0;
                                                rECT8.right = n19;
                                            } else {
                                                rECT8.left = rECT.left;
                                                rECT8.right = rECT.right;
                                            }
                                            if (n7 == this.columnCount - 1) {
                                                RECT rECT9 = new RECT();
                                                OS.SetRect(rECT9, ((RECT)object6).left, ((RECT)object6).top, rECT8.right, ((RECT)object6).bottom);
                                                object6 = rECT9;
                                            }
                                        }
                                        long l8 = OS.OpenThemeData(this.handle, Display.TREEVIEW);
                                        int n20 = n18 = bl2 ? 3 : 2;
                                        if (OS.GetFocus() != this.handle && bl2 && !bl) {
                                            n18 = 5;
                                        }
                                        OS.DrawThemeBackground(l8, l5, 1, n18, rECT8, (RECT)object6);
                                        OS.CloseThemeData(l8);
                                    }
                                } else if (bl2) {
                                    this.selectionForeground = n17;
                                    if (!this.explorerTheme && n12 == -1 && OS.IsWindowEnabled(this.handle)) {
                                        Control control = this.findBackgroundControl();
                                        if (control == null) {
                                            control = this;
                                        }
                                        n12 = control.getBackgroundPixel();
                                    }
                                }
                            }
                        }
                        if (this.selectionForeground != -1) {
                            n6 = this.selectionForeground;
                        }
                    }
                    if (!this.ignoreDrawBackground) {
                        if (n12 != -1) {
                            if (bl10) {
                                this.fillBackground(l5, n12, (RECT)object6);
                            }
                        } else {
                            Control control = this.findImageControl();
                            if (control != null) {
                                if (n == 0) {
                                    n15 = Math.min(((RECT)object).right, n8);
                                    OS.SetRect((RECT)object, ((RECT)object).left, ((RECT)object).top, n15, ((RECT)object).bottom);
                                    if (bl10) {
                                        this.fillImageBackground(l5, control, (RECT)object, 0, 0);
                                    }
                                } else if (bl10) {
                                    this.fillImageBackground(l5, control, (RECT)object, 0, 0);
                                }
                            }
                        }
                    }
                    Object object13 = object;
                    ((RECT)object13).left += 2;
                    if (bl9) {
                        int n21;
                        Image image = null;
                        if (n7 == 0) {
                            image = treeItem.image;
                        } else {
                            object10 = treeItem.images;
                            if (object10 != null) {
                                image = object10[n7];
                            }
                        }
                        int n22 = n != 0 ? 3 : 0;
                        int n23 = n21 = n != 0 ? 3 : 5;
                        if (image != null) {
                            object4 = image.getBounds();
                            if (point == null) {
                                point = DPIUtil.autoScaleDown(this.getImageSize());
                            }
                            if (!this.ignoreDrawForeground) {
                                int n24 = ((RECT)object).top + DPIUtil.autoScaleUp((this.getItemHeight() - point.y) / 2);
                                int n25 = Math.max(((RECT)object).left, ((RECT)object).left - n22 + 1);
                                GCData gCData = new GCData();
                                gCData.device = this.display;
                                GC gC = GC.win32_new(l5, gCData);
                                gC.setClipping(DPIUtil.autoScaleDown(new Rectangle(n25, ((RECT)object).top, ((RECT)object).right - n25, ((RECT)object).bottom - ((RECT)object).top)));
                                gC.drawImage(image, 0, 0, ((Rectangle)object4).width, ((Rectangle)object4).height, DPIUtil.autoScaleDown(n25), DPIUtil.autoScaleDown(n24), point.x, point.y);
                                OS.SelectClipRgn(l5, 0L);
                                gC.dispose();
                            }
                            OS.SetRect((RECT)object, ((RECT)object).left + DPIUtil.autoScaleUp(point.x) + n21, ((RECT)object).top, ((RECT)object).right - n22, ((RECT)object).bottom);
                        } else if (n == 0) {
                            if (OS.SendMessage(this.handle, 4360, 0L, 0L) != 0L) {
                                if (point == null) {
                                    point = this.getImageSize();
                                }
                                ((RECT)object).left = Math.min(((RECT)object).left + point.x + n21, ((RECT)object).right);
                            }
                        } else {
                            OS.SetRect((RECT)object, ((RECT)object).left + n21, ((RECT)object).top, ((RECT)object).right - n22, ((RECT)object).bottom);
                        }
                    }
                    if (bl8 && ((RECT)object).left < ((RECT)object).right) {
                        Object object14 = null;
                        if (n7 == 0) {
                            object14 = treeItem.text;
                        } else {
                            object10 = treeItem.strings;
                            if (object10 != null) {
                                object14 = object10[n7];
                            }
                        }
                        if (object14 != null) {
                            if (l7 != -1L) {
                                l7 = OS.SelectObject(l5, l7);
                            }
                            if (n6 != -1) {
                                n6 = OS.SetTextColor(l5, n6);
                            }
                            if (n12 != -1) {
                                n12 = OS.SetBkColor(l5, n12);
                            }
                            int n26 = 2084;
                            if (n != 0) {
                                n26 |= 0x8000;
                            }
                            Object object15 = object5 = this.columns != null ? this.columns[n7] : null;
                            if (object5 != null) {
                                if ((((TreeColumn)object5).style & 0x1000000) != 0) {
                                    n26 |= 1;
                                }
                                if ((((TreeColumn)object5).style & 0x20000) != 0) {
                                    n26 |= 2;
                                }
                            }
                            if (object14 != null && ((String)object14).length() > 8192) {
                                object14 = ((String)object14).substring(0, 8189) + "...";
                            }
                            object4 = ((String)object14).toCharArray();
                            if (!this.ignoreDrawForeground) {
                                OS.DrawText(l5, (char[])object4, ((Object)object4).length, (RECT)object, n26);
                            }
                            OS.DrawText(l5, (char[])object4, ((Object)object4).length, (RECT)object, n26 | 0x400);
                            if (l7 != -1L) {
                                l7 = OS.SelectObject(l5, l7);
                            }
                            if (n6 != -1) {
                                n6 = OS.SetTextColor(l5, n6);
                            }
                            if (n12 != -1) {
                                n12 = OS.SetBkColor(l5, n12);
                            }
                        }
                    }
                }
                if (this.selectionForeground != -1) {
                    n6 = this.selectionForeground;
                }
                if (this.hooks(42)) {
                    int n27;
                    RECT rECT10 = treeItem.getBounds(n7, true, true, false, false, false, l5);
                    int n28 = OS.SaveDC(l5);
                    GCData gCData = new GCData();
                    gCData.device = this.display;
                    gCData.font = treeItem.getFont(n7);
                    gCData.foreground = OS.GetTextColor(l5);
                    gCData.background = OS.GetBkColor(l5);
                    if (bl2 && (this.style & 0x10000) != 0) {
                        if (this.selectionForeground != -1) {
                            gCData.foreground = this.selectionForeground;
                        }
                    } else {
                        if (n6 != -1) {
                            gCData.foreground = n6;
                        }
                        if (n12 != -1) {
                            gCData.background = n12;
                        }
                    }
                    gCData.uiState = (int)OS.SendMessage(this.handle, 297, 0L, 0L);
                    object5 = GC.win32_new(l5, gCData);
                    object4 = new Event();
                    ((Event)object4).item = treeItem;
                    ((Event)object4).index = n7;
                    ((Event)object4).gc = object5;
                    object3 = object4;
                    ((Event)object3).detail |= 0x10;
                    if (n12 != -1) {
                        Object object16 = object4;
                        ((Event)object16).detail |= 8;
                    }
                    if (bl) {
                        Object object17 = object4;
                        ((Event)object17).detail |= 0x20;
                    }
                    if (bl2 && (n == 0 || (this.style & 0x10000) != 0)) {
                        Object object18 = object4;
                        ((Event)object18).detail |= 2;
                    }
                    if (!(this.explorerTheme || OS.SendMessage(this.handle, 4362, 9L, 0L) != nMTVCUSTOMDRAW.dwItemSpec || n != 0 && (this.style & 0x10000) == 0 || this.handle != OS.GetFocus() || ((n27 = (int)OS.SendMessage(this.handle, 297, 0L, 0L)) & 1) != 0)) {
                        Object object19 = object4;
                        ((Event)object19).detail |= 4;
                    }
                    ((Event)object4).setBoundsInPixels(new Rectangle(rECT10.left, rECT10.top, rECT10.right - rECT10.left, rECT10.bottom - rECT10.top));
                    RECT rECT11 = treeItem.getBounds(n7, true, true, true, true, true, l5);
                    int n29 = rECT11.right - rECT11.left;
                    n11 = rECT11.bottom - rECT11.top;
                    ((GC)object5).setClipping(DPIUtil.autoScaleDown(new Rectangle(rECT11.left, rECT11.top, n29, n11)));
                    this.sendEvent(42, (Event)object4);
                    if (gCData.focusDrawn) {
                        this.focusRect = null;
                    }
                    ((Event)object4).gc = null;
                    ((Resource)object5).dispose();
                    OS.RestoreDC(l5, n28);
                    if (this.isDisposed() || treeItem.isDisposed()) break;
                }
            }
            if ((n5 += n8) > rECT.right) break;
        }
        if (this.linesVisible) {
            if ((this.style & 0x10000) != 0 && this.columnCount != 0) {
                HDITEM hDITEM = new HDITEM();
                hDITEM.mask = 1;
                OS.SendMessage(this.hwndHeader, 4619, 0L, hDITEM);
                RECT rECT12 = new RECT();
                OS.SetRect(rECT12, nMTVCUSTOMDRAW.left + hDITEM.cxy, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.right, nMTVCUSTOMDRAW.bottom);
                OS.DrawEdge(l5, rECT12, 8, 8);
            }
            RECT rECT13 = new RECT();
            OS.SetRect(rECT13, nMTVCUSTOMDRAW.left, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.right, nMTVCUSTOMDRAW.bottom);
            OS.DrawEdge(l5, rECT13, 8, 8);
        }
        if (!this.ignoreDrawFocus && this.focusRect != null) {
            OS.DrawFocusRect(l5, this.focusRect);
            this.focusRect = null;
        } else if (!this.explorerTheme && this.handle == OS.GetFocus() && ((n = (int)OS.SendMessage(this.handle, 297, 0L, 0L)) & 1) == 0 && (l4 = OS.SendMessage(this.handle, 4362, 9L, 0L)) == treeItem.handle && !this.ignoreDrawFocus && this.findImageControl() != null) {
            if ((this.style & 0x10000) != 0) {
                object = new RECT();
                OS.SetRect((RECT)object, 0, nMTVCUSTOMDRAW.top, rECT.right + 1, nMTVCUSTOMDRAW.bottom);
                OS.DrawFocusRect(l5, (RECT)object);
            } else {
                int n30 = (int)OS.SendMessage(this.hwndHeader, 4623, 0L, 0L);
                RECT rECT14 = treeItem.getBounds(n30, true, false, false, false, false, l5);
                RECT rECT15 = treeItem.getBounds(n30, true, false, false, false, true, l5);
                OS.IntersectClipRect(l5, rECT15.left, rECT15.top, rECT15.right, rECT15.bottom);
                OS.DrawFocusRect(l5, rECT14);
                OS.SelectClipRgn(l5, 0L);
            }
        }
        return new LRESULT(0L);
    }

    LRESULT CDDS_ITEMPREPAINT(NMTVCUSTOMDRAW nMTVCUSTOMDRAW, long l2, long l3) {
        RECT rECT;
        Object object;
        boolean bl;
        TreeItem treeItem = this.getItem(nMTVCUSTOMDRAW);
        if (treeItem == null) {
            return null;
        }
        long l4 = nMTVCUSTOMDRAW.hdc;
        int n = this.hwndHeader != 0L ? (int)OS.SendMessage(this.hwndHeader, 4623, 0L, 0L) : 0;
        long l5 = treeItem.fontHandle(n);
        if (l5 != -1L) {
            OS.SelectObject(l4, l5);
        }
        if (this.ignoreCustomDraw || nMTVCUSTOMDRAW.left == nMTVCUSTOMDRAW.right) {
            return new LRESULT(l5 == -1L ? 0L : 2L);
        }
        RECT rECT2 = null;
        if (this.columnCount != 0) {
            rECT2 = new RECT();
            HDITEM hDITEM = new HDITEM();
            hDITEM.mask = 1;
            OS.SendMessage(this.hwndHeader, 4619, (long)n, hDITEM);
            OS.SetRect(rECT2, nMTVCUSTOMDRAW.left, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.left + hDITEM.cxy, nMTVCUSTOMDRAW.bottom);
        }
        int n2 = -1;
        int n3 = -1;
        if (OS.IsWindowEnabled(this.handle)) {
            int n4 = n2 = treeItem.cellForeground != null ? treeItem.cellForeground[n] : -1;
            if (n2 == -1) {
                n2 = treeItem.foreground;
            }
            int n5 = n3 = treeItem.cellBackground != null ? treeItem.cellBackground[n] : -1;
            if (n3 == -1) {
                n3 = treeItem.background;
            }
        }
        int n6 = -1;
        if (OS.IsAppThemed() && this.sortColumn != null && this.sortDirection != 0 && this.findImageControl() == null && this.indexOf(this.sortColumn) == n) {
            n6 = this.getSortColumnPixel();
            if (n3 == -1) {
                n3 = n6;
            }
        }
        boolean bl2 = this.isItemSelected(nMTVCUSTOMDRAW);
        boolean bl3 = this.explorerTheme && (nMTVCUSTOMDRAW.uItemState & 0x40) != 0;
        boolean bl4 = bl = this.explorerTheme && (nMTVCUSTOMDRAW.uItemState & 0x10) != 0;
        if (OS.IsWindowVisible(this.handle) && nMTVCUSTOMDRAW.left < nMTVCUSTOMDRAW.right && nMTVCUSTOMDRAW.top < nMTVCUSTOMDRAW.bottom) {
            int n7;
            if (l5 != -1L) {
                OS.SelectObject(l4, l5);
            }
            if (this.linesVisible) {
                object = new RECT();
                OS.SetRect((RECT)object, nMTVCUSTOMDRAW.left, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.right, nMTVCUSTOMDRAW.bottom);
                OS.DrawEdge(l4, (RECT)object, 8, 8);
            }
            object = null;
            Rectangle rectangle = null;
            if (this.hooks(41)) {
                object = this.sendMeasureItemEvent(treeItem, n, l4, bl2 ? 2 : 0);
                rectangle = ((Event)object).getBoundsInPixels();
                if (this.isDisposed() || treeItem.isDisposed()) {
                    return null;
                }
            }
            this.selectionForeground = -1;
            boolean bl5 = false;
            this.ignoreFullSelection = false;
            this.ignoreDrawHot = false;
            this.ignoreDrawFocus = false;
            this.ignoreDrawSelection = false;
            this.ignoreDrawBackground = false;
            this.ignoreDrawForeground = false;
            if (this.hooks(40)) {
                RECT rECT3;
                RECT rECT4;
                int n8;
                int n9;
                Event event;
                RECT rECT5 = new RECT();
                OS.SetRect(rECT5, nMTVCUSTOMDRAW.left, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.right, nMTVCUSTOMDRAW.bottom);
                RECT rECT6 = treeItem.getBounds(n, true, true, true, true, true, l4);
                if (n6 != -1) {
                    this.drawBackground(l4, rECT6, n6, 0, 0);
                } else if (OS.IsWindowEnabled(this.handle) || this.findImageControl() != null) {
                    this.drawBackground(l4, rECT5);
                } else {
                    this.fillBackground(l4, OS.GetBkColor(l4), rECT5);
                }
                int n10 = OS.SaveDC(l4);
                GCData gCData = new GCData();
                gCData.device = this.display;
                gCData.foreground = bl2 && this.explorerTheme ? OS.GetSysColor(8) : OS.GetTextColor(l4);
                gCData.background = OS.GetBkColor(l4);
                if (!bl2) {
                    if (n2 != -1) {
                        gCData.foreground = n2;
                    }
                    if (n3 != -1) {
                        gCData.background = n3;
                    }
                }
                gCData.uiState = (int)OS.SendMessage(this.handle, 297, 0L, 0L);
                gCData.font = treeItem.getFont(n);
                GC gC = GC.win32_new(l4, gCData);
                Event event2 = new Event();
                event2.index = n;
                event2.item = treeItem;
                event2.gc = gC;
                Event event3 = event2;
                event3.detail |= 0x10;
                if (n3 != -1) {
                    event = event2;
                    event.detail |= 8;
                }
                if (bl3) {
                    event = event2;
                    event.detail |= 0x20;
                }
                if (bl2) {
                    event = event2;
                    event.detail |= 2;
                }
                if (!(OS.SendMessage(this.handle, 4362, 9L, 0L) != nMTVCUSTOMDRAW.dwItemSpec || this.handle != OS.GetFocus() || ((n9 = (int)OS.SendMessage(this.handle, 297, 0L, 0L)) & 1) != 0 || this.explorerTheme && bl2)) {
                    bl = true;
                    Event event4 = event2;
                    event4.detail |= 4;
                }
                Rectangle rectangle2 = new Rectangle(rECT6.left, rECT6.top, rECT6.right - rECT6.left, rECT6.bottom - rECT6.top);
                event2.setBoundsInPixels(rectangle2);
                gC.setClipping(DPIUtil.autoScaleDown(rectangle2));
                this.sendEvent(40, event2);
                event2.gc = null;
                int n11 = gCData.foreground;
                gC.dispose();
                OS.RestoreDC(l4, n10);
                if (this.isDisposed() || treeItem.isDisposed()) {
                    return null;
                }
                if (event2.doit) {
                    this.ignoreDrawForeground = (event2.detail & 0x10) == 0;
                    this.ignoreDrawBackground = (event2.detail & 8) == 0;
                    this.ignoreDrawSelection = (event2.detail & 2) == 0;
                    this.ignoreDrawFocus = (event2.detail & 4) == 0;
                    this.ignoreDrawHot = (event2.detail & 0x20) == 0;
                } else {
                    n8 = 1;
                    this.ignoreDrawHot = true;
                    this.ignoreDrawFocus = true;
                    this.ignoreDrawSelection = true;
                    this.ignoreDrawBackground = true;
                    this.ignoreDrawForeground = true;
                }
                if (bl2 && this.ignoreDrawSelection) {
                    this.ignoreDrawHot = true;
                }
                if (!this.ignoreDrawBackground && n3 != -1) {
                    int n12 = n8 = !bl2 && !bl3 ? 1 : 0;
                    if (!this.explorerTheme && bl2) {
                        int n13 = n8 = !this.ignoreDrawSelection ? 1 : 0;
                    }
                    if (n8 != 0) {
                        if (this.columnCount == 0) {
                            if ((this.style & 0x10000) != 0) {
                                this.fillBackground(l4, n3, rECT5);
                            } else {
                                rECT4 = treeItem.getBounds(n, true, false, false, false, true, l4);
                                if (object != null) {
                                    rECT4.right = Math.min(rECT6.right, rectangle.x + rectangle.width);
                                }
                                this.fillBackground(l4, n3, rECT4);
                            }
                        } else {
                            this.fillBackground(l4, n3, rECT6);
                        }
                    }
                }
                if (this.ignoreDrawSelection) {
                    this.ignoreFullSelection = true;
                }
                if (!this.ignoreDrawSelection || !this.ignoreDrawHot) {
                    if (!bl2 && !bl3) {
                        n2 = this.selectionForeground = OS.GetSysColor(14);
                    }
                    if (this.explorerTheme) {
                        if ((this.style & 0x10000) == 0) {
                            int n14;
                            RECT rECT7;
                            RECT rECT8 = treeItem.getBounds(n, true, true, false, false, false, l4);
                            rECT4 = treeItem.getBounds(n, true, true, true, false, true, l4);
                            if (object != null) {
                                rECT8.right = Math.min(rECT4.right, rectangle.x + rectangle.width);
                            } else {
                                rECT3 = rECT8;
                                rECT3.right += 2;
                                rECT7 = rECT4;
                                rECT7.right += 2;
                            }
                            rECT3 = rECT8;
                            rECT3.left -= 2;
                            rECT7 = rECT4;
                            rECT7.left -= 2;
                            long l6 = OS.OpenThemeData(this.handle, Display.TREEVIEW);
                            int n15 = n14 = bl2 ? 3 : 2;
                            if (OS.GetFocus() != this.handle && bl2 && !bl3) {
                                n14 = 5;
                            }
                            OS.DrawThemeBackground(l6, l4, 1, n14, rECT8, rECT4);
                            OS.CloseThemeData(l6);
                        }
                    } else if ((this.style & 0x10000) != 0) {
                        if ((this.style & 0x10000) != 0 && this.columnCount == 0) {
                            this.fillBackground(l4, OS.GetBkColor(l4), rECT5);
                        } else {
                            this.fillBackground(l4, OS.GetBkColor(l4), rECT6);
                        }
                    } else {
                        RECT rECT9 = treeItem.getBounds(n, true, false, false, false, true, l4);
                        if (object != null) {
                            rECT9.right = Math.min(rECT6.right, rectangle.x + rectangle.width);
                        }
                        this.fillBackground(l4, OS.GetBkColor(l4), rECT9);
                    }
                } else {
                    if (bl2 || bl3) {
                        n2 = this.selectionForeground = n11;
                        n8 = 1;
                        this.ignoreDrawHot = true;
                        this.ignoreDrawSelection = true;
                    }
                    if (this.explorerTheme) {
                        nMTVCUSTOMDRAW.uItemState |= 4;
                        int n16 = n8 = n2 == -1 ? this.getForegroundPixel() : n2;
                        if (nMTVCUSTOMDRAW.clrText == n8) {
                            nMTVCUSTOMDRAW.clrText |= 0x20000000;
                            if (nMTVCUSTOMDRAW.clrText == n8) {
                                nMTVCUSTOMDRAW.clrText &= 0xDFFFFFFF;
                            }
                        } else {
                            nMTVCUSTOMDRAW.clrText = n8;
                        }
                        OS.MoveMemory(l3, nMTVCUSTOMDRAW, NMTVCUSTOMDRAW.sizeof);
                    }
                }
                if (bl && !this.ignoreDrawFocus && (this.style & 0x10000) == 0) {
                    RECT rECT10 = treeItem.getBounds(n, true, this.explorerTheme, false, false, true, l4);
                    if (object != null) {
                        rECT10.right = Math.min(rECT6.right, rectangle.x + rectangle.width);
                    }
                    nMTVCUSTOMDRAW.uItemState &= 0xFFFFFFEF;
                    OS.MoveMemory(l3, nMTVCUSTOMDRAW, NMTVCUSTOMDRAW.sizeof);
                    this.focusRect = rECT10;
                }
                if (this.explorerTheme) {
                    if (bl2 || bl3 && this.ignoreDrawHot) {
                        nMTVCUSTOMDRAW.uItemState &= 0xFFFFFFBF;
                    }
                    OS.MoveMemory(l3, nMTVCUSTOMDRAW, NMTVCUSTOMDRAW.sizeof);
                }
                RECT rECT11 = treeItem.getBounds(n, true, true, false, false, false, l4);
                OS.SaveDC(l4);
                OS.SelectClipRgn(l4, 0L);
                if (this.explorerTheme) {
                    rECT4 = rECT11;
                    rECT4.left -= 2;
                    rECT3 = rECT11;
                    rECT3.right += 2;
                }
                rECT4 = rECT11;
                ++rECT4.right;
                if (this.linesVisible) {
                    rECT3 = rECT11;
                    ++rECT3.bottom;
                }
                if (rECT2 != null) {
                    OS.IntersectClipRect(l4, rECT2.left, rECT2.top, rECT2.right, rECT2.bottom);
                }
                OS.ExcludeClipRect(l4, rECT11.left, rECT11.top, rECT11.right, rECT11.bottom);
                return new LRESULT(16L);
            }
            if ((this.style & 0x10000) != 0 && ((n7 = OS.GetWindowLong(this.handle, -16)) & 0x1000) == 0) {
                rECT = new RECT();
                OS.SetRect(rECT, nMTVCUSTOMDRAW.left, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.right, nMTVCUSTOMDRAW.bottom);
                if (bl2) {
                    this.fillBackground(l4, OS.GetBkColor(l4), rECT);
                } else if (OS.IsWindowEnabled(this.handle)) {
                    this.drawBackground(l4, rECT);
                }
                nMTVCUSTOMDRAW.uItemState &= 0xFFFFFFEF;
                OS.MoveMemory(l3, nMTVCUSTOMDRAW, NMTVCUSTOMDRAW.sizeof);
            }
        }
        object = null;
        if (n2 == -1 && n3 == -1 && l5 == -1L) {
            object = new LRESULT(16L);
        } else {
            object = new LRESULT(18L);
            if (l5 != -1L) {
                OS.SelectObject(l4, l5);
            }
            if (OS.IsWindowEnabled(this.handle) && OS.IsWindowVisible(this.handle)) {
                int n17;
                if (n3 != -1 && ((n17 = OS.GetWindowLong(this.handle, -16)) & 0x1000) == 0) {
                    if (this.columnCount != 0 && this.hwndHeader != 0L) {
                        RECT rECT12 = new RECT();
                        HDITEM hDITEM = new HDITEM();
                        hDITEM.mask = 1;
                        OS.SendMessage(this.hwndHeader, 4619, (long)n, hDITEM);
                        OS.SetRect(rECT12, nMTVCUSTOMDRAW.left, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.left + hDITEM.cxy, nMTVCUSTOMDRAW.bottom);
                        if (!OS.IsAppThemed() && OS.TreeView_GetItemRect(this.handle, treeItem.handle, rECT = new RECT(), true)) {
                            rECT12.left = Math.min(rECT.left, rECT12.right);
                        }
                        if ((this.style & 0x10000) != 0) {
                            if (!bl2) {
                                this.fillBackground(l4, n3, rECT12);
                            }
                        } else {
                            this.fillBackground(l4, n3, rECT12);
                        }
                    } else if ((this.style & 0x10000) != 0) {
                        RECT rECT13 = new RECT();
                        OS.SetRect(rECT13, nMTVCUSTOMDRAW.left, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.right, nMTVCUSTOMDRAW.bottom);
                        if (!bl2) {
                            this.fillBackground(l4, n3, rECT13);
                        }
                    }
                }
                if (!bl2) {
                    nMTVCUSTOMDRAW.clrText = n2 == -1 ? this.getForegroundPixel() : n2;
                    nMTVCUSTOMDRAW.clrTextBk = n3 == -1 ? this.getBackgroundPixel() : n3;
                    OS.MoveMemory(l3, nMTVCUSTOMDRAW, NMTVCUSTOMDRAW.sizeof);
                }
            }
        }
        if (OS.IsWindowEnabled(this.handle)) {
            if (this.explorerTheme && this.findImageControl() != null && !bl2 && (nMTVCUSTOMDRAW.uItemState & 0x41) == 0) {
                int n18;
                nMTVCUSTOMDRAW.uItemState |= 4;
                int n19 = n18 = n2 == -1 ? this.getForegroundPixel() : n2;
                if (nMTVCUSTOMDRAW.clrText == n18) {
                    nMTVCUSTOMDRAW.clrText |= 0x20000000;
                    if (nMTVCUSTOMDRAW.clrText == n18) {
                        nMTVCUSTOMDRAW.clrText &= 0xDFFFFFFF;
                    }
                } else {
                    nMTVCUSTOMDRAW.clrText = n18;
                }
                OS.MoveMemory(l3, nMTVCUSTOMDRAW, NMTVCUSTOMDRAW.sizeof);
                if (n3 != -1) {
                    if ((this.style & 0x10000) != 0) {
                        RECT rECT14 = new RECT();
                        if (this.columnCount != 0) {
                            HDITEM hDITEM = new HDITEM();
                            hDITEM.mask = 1;
                            OS.SendMessage(this.hwndHeader, 4619, (long)n, hDITEM);
                            OS.SetRect(rECT14, nMTVCUSTOMDRAW.left, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.left + hDITEM.cxy, nMTVCUSTOMDRAW.bottom);
                        } else {
                            OS.SetRect(rECT14, nMTVCUSTOMDRAW.left, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.right, nMTVCUSTOMDRAW.bottom);
                        }
                        this.fillBackground(l4, n3, rECT14);
                    } else {
                        RECT rECT15 = treeItem.getBounds(n, true, false, true, false, true, l4);
                        this.fillBackground(l4, n3, rECT15);
                    }
                }
            }
        } else if (n6 != -1) {
            RECT rECT16 = new RECT();
            HDITEM hDITEM = new HDITEM();
            hDITEM.mask = 1;
            OS.SendMessage(this.hwndHeader, 4619, (long)n, hDITEM);
            OS.SetRect(rECT16, nMTVCUSTOMDRAW.left, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.left + hDITEM.cxy, nMTVCUSTOMDRAW.bottom);
            this.fillBackground(l4, n6, rECT16);
        }
        OS.SaveDC(l4);
        if (rECT2 != null) {
            long l7 = OS.CreateRectRgn(rECT2.left, rECT2.top, rECT2.right, rECT2.bottom);
            POINT pOINT = new POINT();
            OS.GetWindowOrgEx(l4, pOINT);
            OS.OffsetRgn(l7, -pOINT.x, -pOINT.y);
            OS.SelectClipRgn(l4, l7);
            OS.DeleteObject(l7);
        }
        return object;
    }

    LRESULT CDDS_POSTPAINT(NMTVCUSTOMDRAW nMTVCUSTOMDRAW, long l2, long l3) {
        if (this.ignoreCustomDraw) {
            return null;
        }
        if (OS.IsWindowVisible(this.handle)) {
            Object object;
            int n;
            if (OS.IsAppThemed() && this.sortColumn != null && this.sortDirection != 0 && this.findImageControl() == null && (n = this.indexOf(this.sortColumn)) != -1) {
                int n2 = nMTVCUSTOMDRAW.top;
                long l4 = 0L;
                l4 = OS.WIN32_VERSION >= OS.VERSION(6, 0) ? this.getBottomItem() : OS.SendMessage(this.handle, 4362, 10L, 0L);
                if (l4 != 0L && OS.TreeView_GetItemRect(this.handle, l4, (RECT)(object = new RECT()), false)) {
                    n2 = ((RECT)object).bottom;
                }
                object = new RECT();
                OS.SetRect((RECT)object, nMTVCUSTOMDRAW.left, n2, nMTVCUSTOMDRAW.right, nMTVCUSTOMDRAW.bottom);
                RECT rECT = new RECT();
                OS.SendMessage(this.hwndHeader, 4615, (long)n, rECT);
                ((RECT)object).left = rECT.left;
                ((RECT)object).right = rECT.right;
                this.fillBackground(nMTVCUSTOMDRAW.hdc, this.getSortColumnPixel(), (RECT)object);
            }
            if (this.linesVisible) {
                int n3;
                RECT rECT;
                long l5 = nMTVCUSTOMDRAW.hdc;
                if (this.hwndHeader != 0L) {
                    int n4 = 0;
                    rECT = new RECT();
                    object = new HDITEM();
                    ((HDITEM)object).mask = 1;
                    for (int i = 0; i < this.columnCount; ++i) {
                        n3 = (int)OS.SendMessage(this.hwndHeader, 4623, (long)i, 0L);
                        OS.SendMessage(this.hwndHeader, 4619, (long)n3, (HDITEM)object);
                        OS.SetRect(rECT, n4, nMTVCUSTOMDRAW.top, n4 + ((HDITEM)object).cxy, nMTVCUSTOMDRAW.bottom);
                        OS.DrawEdge(l5, rECT, 8, 4);
                        n4 += ((HDITEM)object).cxy;
                    }
                }
                int n5 = 0;
                rECT = new RECT();
                long l6 = 0L;
                l6 = OS.WIN32_VERSION >= OS.VERSION(6, 0) ? this.getBottomItem() : OS.SendMessage(this.handle, 4362, 10L, 0L);
                if (l6 != 0L && OS.TreeView_GetItemRect(this.handle, l6, rECT, false)) {
                    n5 = rECT.bottom - rECT.top;
                }
                if (n5 == 0) {
                    n5 = (int)OS.SendMessage(this.handle, 4380, 0L, 0L);
                    OS.GetClientRect(this.handle, rECT);
                    OS.SetRect(rECT, rECT.left, rECT.top, rECT.right, rECT.top + n5);
                    OS.DrawEdge(l5, rECT, 8, 8);
                }
                if (n5 != 0) {
                    while (rECT.bottom < nMTVCUSTOMDRAW.bottom) {
                        n3 = rECT.top + n5;
                        OS.SetRect(rECT, rECT.left, n3, rECT.right, n3 + n5);
                        OS.DrawEdge(l5, rECT, 8, 8);
                    }
                }
            }
        }
        return new LRESULT(0L);
    }

    LRESULT CDDS_PREPAINT(NMTVCUSTOMDRAW nMTVCUSTOMDRAW, long l2, long l3) {
        if (this.explorerTheme && (OS.IsWindowEnabled(this.handle) && this.hooks(40) || this.hasCustomBackground() || this.findImageControl() != null)) {
            RECT rECT = new RECT();
            OS.SetRect(rECT, nMTVCUSTOMDRAW.left, nMTVCUSTOMDRAW.top, nMTVCUSTOMDRAW.right, nMTVCUSTOMDRAW.bottom);
            this.drawBackground(nMTVCUSTOMDRAW.hdc, rECT);
        }
        return new LRESULT(48L);
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        long l5;
        if (this.handle == 0L) {
            return 0L;
        }
        if (this.hwndParent != 0L && l2 == this.hwndParent) {
            return OS.DefWindowProc(l2, n, l3, l4);
        }
        if (this.hwndHeader != 0L && l2 == this.hwndHeader) {
            return OS.CallWindowProc(HeaderProc, l2, n, l3, l4);
        }
        switch (n) {
            case 7: {
                if ((this.style & 4) != 0 || (l5 = OS.SendMessage(this.handle, 4362, 9L, 0L)) != 0L || (l5 = OS.SendMessage(this.handle, 4362, 5L, 0L)) == 0L) break;
                TVITEM tVITEM = new TVITEM();
                tVITEM.mask = 24;
                tVITEM.hItem = l5;
                OS.SendMessage(this.handle, 4414, 0L, tVITEM);
                this.hSelect = l5;
                boolean bl = true;
                this.lockSelection = true;
                this.ignoreSelect = true;
                this.ignoreDeselect = true;
                OS.SendMessage(this.handle, 4363, 9L, l5);
                boolean bl2 = false;
                this.lockSelection = false;
                this.ignoreSelect = false;
                this.ignoreDeselect = false;
                this.hSelect = 0L;
                if ((tVITEM.state & 2) != 0) break;
                OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                break;
            }
        }
        l5 = 0L;
        boolean bl = false;
        switch (n) {
            case 256: {
                if (l3 == 17L || l3 == 16L) break;
            }
            case 5: 
            case 257: 
            case 258: 
            case 260: 
            case 261: 
            case 262: 
            case 276: 
            case 277: 
            case 646: {
                boolean bl3 = bl = this.findImageControl() != null && this.getDrawing() && OS.IsWindowVisible(this.handle);
                if (bl) {
                    OS.DefWindowProc(this.handle, 11, 0L, 0L);
                }
            }
            case 48: 
            case 275: 
            case 512: 
            case 513: 
            case 514: 
            case 515: 
            case 516: 
            case 517: 
            case 518: 
            case 519: 
            case 520: 
            case 521: 
            case 522: 
            case 523: 
            case 524: 
            case 525: 
            case 673: 
            case 675: {
                if (this.findImageControl() == null) break;
                l5 = OS.SendMessage(this.handle, 4362, 5L, 0L);
            }
        }
        long l6 = OS.CallWindowProc(TreeProc, l2, n, l3, l4);
        switch (n) {
            case 256: {
                if (l3 == 17L || l3 == 16L) break;
            }
            case 5: 
            case 257: 
            case 258: 
            case 260: 
            case 261: 
            case 262: 
            case 276: 
            case 277: 
            case 646: {
                if (bl) {
                    OS.DefWindowProc(this.handle, 11, 1L, 0L);
                    OS.InvalidateRect(this.handle, null, true);
                    if (this.hwndHeader != 0L) {
                        OS.InvalidateRect(this.hwndHeader, null, true);
                    }
                }
            }
            case 48: 
            case 275: 
            case 512: 
            case 513: 
            case 514: 
            case 515: 
            case 516: 
            case 517: 
            case 518: 
            case 519: 
            case 520: 
            case 521: 
            case 522: 
            case 523: 
            case 524: 
            case 525: 
            case 673: 
            case 675: {
                if (this.findImageControl() != null && l5 != OS.SendMessage(this.handle, 4362, 5L, 0L)) {
                    OS.InvalidateRect(this.handle, null, true);
                }
                this.updateScrollBar();
                break;
            }
            case 15: {
                this.painted = true;
            }
        }
        return l6;
    }

    @Override
    void checkBuffered() {
        int n;
        super.checkBuffered();
        if ((this.style & 0x10000000) != 0) {
            this.style |= 0x20000000;
            OS.SendMessage(this.handle, 4385, 0L, 0L);
        }
        if (OS.IsAppThemed() && ((n = (int)OS.SendMessage(this.handle, 4397, 0L, 0L)) & 4) != 0) {
            this.style |= 0x20000000;
        }
    }

    boolean checkData(TreeItem treeItem, int n, boolean bl) {
        if ((this.style & 0x10000000) == 0) {
            return true;
        }
        if (!treeItem.cached) {
            treeItem.cached = true;
            Event event = new Event();
            event.item = treeItem;
            event.index = n;
            TreeItem treeItem2 = this.currentItem;
            this.currentItem = treeItem;
            long l2 = OS.SendMessage(this.handle, 4362, 5L, 0L);
            this.sendEvent(36, event);
            this.currentItem = treeItem2;
            if (this.isDisposed() || treeItem.isDisposed()) {
                return false;
            }
            if (bl) {
                treeItem.redraw();
            }
            if (l2 != OS.SendMessage(this.handle, 4362, 5L, 0L)) {
                OS.InvalidateRect(this.handle, null, true);
            }
        }
        return true;
    }

    boolean checkScroll(long l2) {
        if (this.getDrawing()) {
            return false;
        }
        long l3 = OS.SendMessage(this.handle, 4362, 0L, 0L);
        long l4 = OS.SendMessage(this.handle, 4362, 3L, l2);
        while (l4 != l3 && l4 != 0L) {
            l4 = OS.SendMessage(this.handle, 4362, 3L, l4);
        }
        return l4 == 0L;
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    public void clear(int n, boolean bl) {
        this.checkWidget();
        long l2 = OS.SendMessage(this.handle, 4362, 0L, 0L);
        if (l2 == 0L) {
            this.error(6);
        }
        if ((l2 = this.findItem(l2, n)) == 0L) {
            this.error(6);
        }
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 20;
        this.clear(l2, tVITEM);
        if (bl) {
            l2 = OS.SendMessage(this.handle, 4362, 4L, l2);
            this.clearAll(l2, tVITEM, bl);
        }
    }

    void clear(long l2, TVITEM tVITEM) {
        tVITEM.hItem = l2;
        TreeItem treeItem = null;
        if (OS.SendMessage(this.handle, 4414, 0L, tVITEM) != 0L) {
            TreeItem treeItem2 = treeItem = tVITEM.lParam != -1L ? this.items[(int)tVITEM.lParam] : null;
        }
        if (treeItem != null) {
            if ((this.style & 0x10000000) != 0 && !treeItem.cached) {
                return;
            }
            treeItem.clear();
            treeItem.redraw();
        }
    }

    public void clearAll(boolean bl) {
        this.checkWidget();
        long l2 = OS.SendMessage(this.handle, 4362, 0L, 0L);
        if (l2 == 0L) {
            return;
        }
        if (bl) {
            boolean bl2 = false;
            for (TreeItem treeItem : this.items) {
                if (treeItem == null || treeItem == this.currentItem) continue;
                treeItem.clear();
                bl2 = true;
            }
            if (bl2) {
                OS.InvalidateRect(this.handle, null, true);
            }
        } else {
            TVITEM tVITEM = new TVITEM();
            tVITEM.mask = 20;
            this.clearAll(l2, tVITEM, bl);
        }
    }

    void clearAll(long l2, TVITEM tVITEM, boolean bl) {
        while (l2 != 0L) {
            this.clear(l2, tVITEM);
            if (bl) {
                long l3 = OS.SendMessage(this.handle, 4362, 4L, l2);
                this.clearAll(l3, tVITEM, bl);
            }
            l2 = OS.SendMessage(this.handle, 4362, 1L, l2);
        }
    }

    long CompareFunc(long l2, long l3, long l4) {
        TreeItem treeItem = this.items[(int)l2];
        TreeItem treeItem2 = this.items[(int)l3];
        String string = treeItem.getText((int)l4);
        String string2 = treeItem2.getText((int)l4);
        return this.sortDirection == 128 ? (long)string.compareTo(string2) : (long)string2.compareTo(string);
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        Object object;
        int n3 = 0;
        int n4 = 0;
        if (this.hwndHeader != 0L) {
            object = new HDITEM();
            ((HDITEM)object).mask = 1;
            for (int i = 0; i < this.columnCount; ++i) {
                OS.SendMessage(this.hwndHeader, 4619, (long)i, (HDITEM)object);
                n3 += ((HDITEM)object).cxy;
            }
            RECT rECT = new RECT();
            OS.GetWindowRect(this.hwndHeader, rECT);
            n4 += rECT.bottom - rECT.top;
        }
        object = new RECT();
        long l2 = OS.SendMessage(this.handle, 4362, 0L, 0L);
        while (l2 != 0L) {
            if ((this.style & 0x10000000) == 0 && !this.painted) {
                TVITEM tVITEM = new TVITEM();
                tVITEM.mask = 17;
                tVITEM.hItem = l2;
                tVITEM.pszText = -1L;
                this.ignoreCustomDraw = true;
                OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                this.ignoreCustomDraw = false;
            }
            if (OS.TreeView_GetItemRect(this.handle, l2, (RECT)object, true)) {
                n3 = Math.max(n3, ((RECT)object).right);
                n4 += ((RECT)object).bottom - ((RECT)object).top;
            }
            l2 = OS.SendMessage(this.handle, 4362, 6L, l2);
        }
        if (n3 == 0) {
            n3 = 64;
        }
        if (n4 == 0) {
            n4 = 64;
        }
        if (n != -1) {
            n3 = n;
        }
        if (n2 != -1) {
            n4 = n2;
        }
        int n5 = this.getBorderWidthInPixels();
        n3 += n5 * 2;
        n4 += n5 * 2;
        if ((this.style & 0x200) != 0) {
            n3 += OS.GetSystemMetrics(2);
        }
        if ((this.style & 0x100) != 0) {
            n4 += OS.GetSystemMetrics(3);
        }
        return new Point(n3, n4);
    }

    @Override
    void createHandle() {
        super.createHandle();
        this.state &= 0xFFFFFEFD;
        if (OS.IsAppThemed()) {
            this.explorerTheme = true;
            OS.SetWindowTheme(this.handle, Display.EXPLORER, null);
            int n = 20;
            if (ENABLE_TVS_EX_FADEINOUTEXPANDOS) {
                n |= 0x40;
            }
            OS.SendMessage(this.handle, 4396, 0L, n);
            this.setForegroundPixel(-1);
        }
        if ((this.style & 0x20) != 0) {
            this.setCheckboxImageList();
        }
        long l2 = OS.GetStockObject(13);
        OS.SendMessage(this.handle, 48, l2, 0L);
        int n = DPIUtil.autoScaleUpUsingNativeDPI(16);
        OS.SendMessage(this.handle, 4359, (long)n, 0L);
        this.createdAsRTL = (this.style & 0x4000000) != 0;
    }

    void createHeaderToolTips() {
        if (this.headerToolTipHandle != 0L) {
            return;
        }
        int n = 0;
        if ((this.style & 0x4000000) != 0) {
            n |= 0x400000;
        }
        this.headerToolTipHandle = OS.CreateWindowEx(n, new TCHAR(0, "tooltips_class32", true), null, 2, Integer.MIN_VALUE, 0, Integer.MIN_VALUE, 0, this.handle, 0L, OS.GetModuleHandle(null), null);
        if (this.headerToolTipHandle == 0L) {
            this.error(2);
        }
        this.maybeEnableDarkSystemTheme(this.headerToolTipHandle);
        OS.SendMessage(this.headerToolTipHandle, 1048, 0L, 32767L);
    }

    void createItem(TreeColumn treeColumn, int n) {
        RECT rECT;
        Object object;
        Object object2;
        Object object3;
        if (this.hwndHeader == 0L) {
            this.createParent();
        }
        if (0 > n || n > this.columnCount) {
            this.error(6);
        }
        if (this.columnCount == this.columns.length) {
            Item[] itemArray = new TreeColumn[this.columns.length + 4];
            System.arraycopy(this.columns, 0, itemArray, 0, this.columns.length);
            this.columns = itemArray;
        }
        for (Item item : this.items) {
            Object[] objectArray;
            if (item == null) continue;
            object3 = ((TreeItem)item).strings;
            if (object3 != null) {
                objectArray = new String[this.columnCount + 1];
                System.arraycopy(object3, 0, objectArray, 0, n);
                System.arraycopy(object3, n, objectArray, n + 1, this.columnCount - n);
                ((TreeItem)item).strings = objectArray;
            }
            if ((objectArray = ((TreeItem)item).images) != null) {
                object2 = new Image[this.columnCount + 1];
                System.arraycopy(objectArray, 0, object2, 0, n);
                System.arraycopy(objectArray, n, object2, n + 1, this.columnCount - n);
                ((TreeItem)item).images = object2;
            }
            if (n == 0 && this.columnCount != 0) {
                if (object3 == null) {
                    String[] stringArray = new String[this.columnCount + 1];
                    ((TreeItem)item).strings = stringArray;
                    stringArray[1] = ((TreeItem)item).text;
                }
                ((TreeItem)item).text = "";
                if (objectArray == null) {
                    Image[] imageArray = new Image[this.columnCount + 1];
                    ((TreeItem)item).images = imageArray;
                    imageArray[1] = ((TreeItem)item).image;
                }
                ((TreeItem)item).image = null;
            }
            if (((TreeItem)item).cellBackground != null) {
                object2 = ((TreeItem)item).cellBackground;
                object = new int[this.columnCount + 1];
                System.arraycopy(object2, 0, object, 0, n);
                System.arraycopy(object2, n, object, n + 1, this.columnCount - n);
                object[n] = -1;
                ((TreeItem)item).cellBackground = object;
            }
            if (((TreeItem)item).cellForeground != null) {
                object2 = ((TreeItem)item).cellForeground;
                object = new int[this.columnCount + 1];
                System.arraycopy(object2, 0, object, 0, n);
                System.arraycopy(object2, n, object, n + 1, this.columnCount - n);
                object[n] = -1;
                ((TreeItem)item).cellForeground = object;
            }
            if (((TreeItem)item).cellFont == null) continue;
            object2 = ((TreeItem)item).cellFont;
            object = new Font[this.columnCount + 1];
            System.arraycopy(object2, 0, object, 0, n);
            System.arraycopy(object2, n, object, n + 1, this.columnCount - n);
            ((TreeItem)item).cellFont = (Font[])object;
        }
        System.arraycopy(this.columns, n, this.columns, n + 1, this.columnCount++ - n);
        this.columns[n] = treeColumn;
        long l2 = OS.GetProcessHeap();
        long l3 = OS.HeapAlloc(l2, 8, 2);
        object3 = new HDITEM();
        object3.mask = 6;
        object3.pszText = l3;
        if ((treeColumn.style & 0x4000) == 16384) {
            object3.fmt = 0;
        }
        if ((treeColumn.style & 0x1000000) == 0x1000000) {
            object3.fmt = 2;
        }
        if ((treeColumn.style & 0x20000) == 131072) {
            object3.fmt = 1;
        }
        OS.SendMessage(this.hwndHeader, 4618, (long)n, (HDITEM)object3);
        if (l3 != 0L) {
            OS.HeapFree(l2, 0, l3);
        }
        if (this.columnCount == 1) {
            int n2;
            this.scrollWidth = 0;
            if ((this.style & 0x100) != 0) {
                int n3 = OS.GetWindowLong(this.handle, -16);
                OS.SetWindowLong(this.handle, -16, n3 |= 0x8000);
            }
            if ((n2 = (int)OS.SendMessage(this.handle, 4357, 0L, 0L)) != 0) {
                OS.ShowScrollBar(this.handle, 0, false);
            }
            this.createItemToolTips();
            if (this.itemToolTipHandle != 0L) {
                OS.SendMessage(this.itemToolTipHandle, 1027, 0L, -1L);
            }
        }
        this.setScrollWidth();
        this.updateImageList();
        this.updateScrollBar();
        if (this.columnCount == 1 && OS.SendMessage(this.handle, 4357, 0L, 0L) != 0L) {
            OS.InvalidateRect(this.handle, null, true);
        }
        if (this.headerToolTipHandle != 0L && OS.SendMessage(this.hwndHeader, 4615, (long)n, rECT = new RECT()) != 0L) {
            int n4;
            object2 = new TOOLINFO();
            object2.cbSize = TOOLINFO.sizeof;
            object2.uFlags = 16;
            object2.hwnd = this.hwndHeader;
            object = object2;
            treeColumn.id = n4 = this.display.nextToolTipId++;
            object.uId = n4;
            object2.left = rECT.left;
            object2.top = rECT.top;
            object2.right = rECT.right;
            object2.bottom = rECT.bottom;
            object2.lpszText = -1L;
            OS.SendMessage(this.headerToolTipHandle, 1074, 0L, (TOOLINFO)object2);
        }
    }

    void createItem(TreeItem treeItem, long l2, long l3, long l4) {
        Object object;
        Object object2;
        boolean bl;
        int n = -1;
        if (treeItem != null) {
            int n2 = n = this.lastID < this.items.length ? this.lastID : 0;
            while (n < this.items.length && this.items[n] != null) {
                ++n;
            }
            if (n == this.items.length) {
                int n3 = 0;
                if (this.getDrawing() && OS.IsWindowVisible(this.handle)) {
                    n3 = this.items.length + 4;
                } else {
                    this.shrink = true;
                    n3 = Math.max(4, this.items.length * 3 / 2);
                }
                this.itemsGrowArray(n3);
            }
            this.lastID = n + 1;
        }
        long l5 = 0L;
        long l6 = OS.SendMessage(this.handle, 4362, 4L, l2);
        boolean bl2 = bl = l6 == 0L;
        if (l4 == 0L) {
            object2 = new TVINSERTSTRUCT();
            ((TVINSERTSTRUCT)object2).hParent = l2;
            ((TVINSERTSTRUCT)object2).hInsertAfter = l3;
            ((TVINSERTSTRUCT)object2).lParam = n;
            ((TVINSERTSTRUCT)object2).pszText = -1L;
            object = object2;
            Object object3 = object2;
            int n4 = -1;
            ((TVINSERTSTRUCT)object3).iSelectedImage = -1;
            ((TVINSERTSTRUCT)object).iImage = -1;
            ((TVINSERTSTRUCT)object2).mask = 55;
            if ((this.style & 0x20) != 0) {
                ((TVINSERTSTRUCT)object2).mask |= 8;
                ((TVINSERTSTRUCT)object2).state = 4096;
                ((TVINSERTSTRUCT)object2).stateMask = 61440;
            }
            this.ignoreCustomDraw = true;
            l5 = OS.SendMessage(this.handle, 4402, 0L, (TVINSERTSTRUCT)object2);
            this.ignoreCustomDraw = false;
            if (l5 == 0L) {
                this.error(14);
            }
        } else {
            object2 = new TVITEM();
            ((TVITEM)object2).mask = 20;
            object = object2;
            l5 = l4;
            ((TVITEM)object).hItem = l4;
            ((TVITEM)object2).lParam = n;
            OS.SendMessage(this.handle, 4415, 0L, (TVITEM)object2);
        }
        if (treeItem != null) {
            treeItem.handle = l5;
            this.items[n] = treeItem;
        }
        if (l6 == 0L && (l3 == -65535L || l3 == -65534L)) {
            long l7;
            this.hLastIndexOf = l7 = (l6 = l5);
            this.hFirstIndexOf = l7;
            boolean bl3 = false;
            this.lastIndexOf = 0;
            this.itemCount = 0;
        }
        if (l6 == this.hFirstIndexOf && this.itemCount != -1) {
            ++this.itemCount;
        }
        if (l4 == 0L) {
            boolean bl4;
            RECT rECT;
            RECT rECT2;
            if (bl && l2 != -65536L && this.getDrawing() && OS.IsWindowVisible(this.handle) && OS.TreeView_GetItemRect(this.handle, l2, rECT2 = new RECT(), false)) {
                OS.InvalidateRect(this.handle, rECT2, true);
            }
            if ((this.style & 0x10000000) != 0 && this.currentItem != null && OS.TreeView_GetItemRect(this.handle, l5, rECT = new RECT(), false) && (bl4 = OS.GetUpdateRect(this.handle, (RECT)(object = new RECT()), true)) && ((RECT)object).top < rECT.bottom) {
                long l8 = OS.CreateRectRgn(0, 0, 0, 0);
                int n5 = OS.GetUpdateRgn(this.handle, l8, true);
                if (n5 != 1) {
                    OS.OffsetRgn(l8, 0, rECT.bottom - rECT.top);
                    OS.InvalidateRgn(this.handle, l8, true);
                }
                OS.DeleteObject(l8);
            }
            if (this.getDrawing()) {
                this.updateScrollBar();
            }
            if (treeItem != null && n == 0) {
                Event event = new Event();
                event.detail = 0;
                this.sendEvent(56, event);
            }
        }
    }

    void createItemToolTips() {
        if (this.itemToolTipHandle != 0L) {
            return;
        }
        int n = OS.GetWindowLong(this.handle, -16);
        OS.SetWindowLong(this.handle, -16, n |= 0x80);
        int n2 = 0;
        if ((this.style & 0x4000000) != 0) {
            n2 |= 0x400000;
        }
        this.itemToolTipHandle = OS.CreateWindowEx(n2 |= 0x20, new TCHAR(0, "tooltips_class32", true), null, 50, Integer.MIN_VALUE, 0, Integer.MIN_VALUE, 0, this.handle, 0L, OS.GetModuleHandle(null), null);
        if (this.itemToolTipHandle == 0L) {
            this.error(2);
        }
        this.maybeEnableDarkSystemTheme(this.itemToolTipHandle);
        OS.SendMessage(this.itemToolTipHandle, 1027, 3L, 0L);
        OS.SendMessage(this.itemToolTipHandle, 1048, 0L, 32767L);
        TOOLINFO tOOLINFO = new TOOLINFO();
        tOOLINFO.cbSize = TOOLINFO.sizeof;
        tOOLINFO.hwnd = this.handle;
        tOOLINFO.uId = this.handle;
        tOOLINFO.uFlags = 272;
        tOOLINFO.lpszText = -1L;
        OS.SendMessage(this.itemToolTipHandle, 1074, 0L, tOOLINFO);
    }

    void createParent() {
        long l2;
        int n;
        this.forceResize();
        RECT rECT = new RECT();
        OS.GetWindowRect(this.handle, rECT);
        OS.MapWindowPoints(0L, this.parent.handle, rECT, 2);
        int n2 = OS.GetWindowLong(this.handle, -16);
        int n3 = super.widgetStyle();
        n3 &= 0xEFFFFFFF;
        if ((n2 & 0x4000000) != 0) {
            n3 |= 0x4000000;
        }
        if ((n2 & 0x800000) != 0) {
            n = n2 & 0xFF7FFFFF;
            OS.SetWindowLong(this.handle, -16, n);
        }
        this.hwndParent = OS.CreateWindowEx(super.widgetExtStyle(), super.windowClass(), null, n3, rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top, this.parent.handle, 0L, OS.GetModuleHandle(null), null);
        if (this.hwndParent == 0L) {
            this.error(2);
        }
        OS.SetWindowLongPtr(this.hwndParent, -12, this.hwndParent);
        this.maybeEnableDarkSystemTheme(this.hwndParent);
        n = 0x100000;
        if ((this.style & 0x4000000) != 0) {
            n |= 0x400000;
        }
        this.hwndHeader = OS.CreateWindowEx(n, HeaderClass, null, 1140850890, 0, 0, 0, 0, this.hwndParent, 0L, OS.GetModuleHandle(null), null);
        if (this.hwndHeader == 0L) {
            this.error(2);
        }
        OS.SetWindowLongPtr(this.hwndHeader, -12, this.hwndHeader);
        this.maybeEnableDarkSystemTheme(this.hwndHeader);
        long l3 = OS.SendMessage(this.handle, 49, 0L, 0L);
        if (l3 != 0L) {
            OS.SendMessage(this.hwndHeader, 48, l3, 0L);
        }
        long l4 = OS.GetWindow(this.handle, 3);
        int n4 = 19;
        OS.SetWindowPos(this.hwndParent, l4, 0, 0, 0, 0, 19);
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 3;
        OS.GetScrollInfo(this.hwndParent, 0, sCROLLINFO);
        sCROLLINFO.nPage = sCROLLINFO.nMax + 1;
        OS.SetScrollInfo(this.hwndParent, 0, sCROLLINFO, true);
        OS.GetScrollInfo(this.hwndParent, 1, sCROLLINFO);
        sCROLLINFO.nPage = sCROLLINFO.nMax + 1;
        OS.SetScrollInfo(this.hwndParent, 1, sCROLLINFO, true);
        this.customDraw = true;
        this.deregister();
        if ((n2 & 0x10000000) != 0) {
            OS.ShowWindow(this.hwndParent, 5);
        }
        if ((l2 = OS.GetFocus()) == this.handle) {
            OS.SetFocus(this.hwndParent);
        }
        OS.SetParent(this.handle, this.hwndParent);
        if (l2 == this.handle) {
            OS.SetFocus(this.handle);
        }
        this.register();
        this.subclass();
    }

    @Override
    void createWidget() {
        super.createWidget();
        this.items = new TreeItem[4];
        this.columns = new TreeColumn[4];
        this.itemCount = -1;
    }

    @Override
    int defaultBackground() {
        return OS.GetSysColor(5);
    }

    @Override
    void deregister() {
        super.deregister();
        if (this.hwndParent != 0L) {
            this.display.removeControl(this.hwndParent);
        }
        if (this.hwndHeader != 0L) {
            this.display.removeControl(this.hwndHeader);
        }
    }

    void deselect(long l2, TVITEM tVITEM, long l3) {
        while (l2 != 0L) {
            if (l2 != l3) {
                tVITEM.hItem = l2;
                OS.SendMessage(this.handle, 4415, 0L, tVITEM);
            }
            long l4 = OS.SendMessage(this.handle, 4362, 4L, l2);
            this.deselect(l4, tVITEM, l3);
            l2 = OS.SendMessage(this.handle, 4362, 1L, l2);
        }
    }

    public void deselect(TreeItem treeItem) {
        this.checkWidget();
        if (treeItem == null) {
            this.error(4);
        }
        if (treeItem.isDisposed()) {
            this.error(5);
        }
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 24;
        tVITEM.stateMask = 2;
        tVITEM.hItem = treeItem.handle;
        OS.SendMessage(this.handle, 4415, 0L, tVITEM);
    }

    public void deselectAll() {
        this.checkWidget();
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 24;
        tVITEM.stateMask = 2;
        if ((this.style & 4) != 0) {
            long l2 = OS.SendMessage(this.handle, 4362, 9L, 0L);
            if (l2 != 0L) {
                tVITEM.hItem = l2;
                OS.SendMessage(this.handle, 4415, 0L, tVITEM);
            }
        } else {
            long l3 = OS.GetWindowLongPtr(this.handle, -4);
            OS.SetWindowLongPtr(this.handle, -4, TreeProc);
            if ((this.style & 0x10000000) != 0) {
                long l4 = OS.SendMessage(this.handle, 4362, 0L, 0L);
                this.deselect(l4, tVITEM, 0L);
            } else {
                for (TreeItem treeItem : this.items) {
                    if (treeItem == null) continue;
                    tVITEM.hItem = treeItem.handle;
                    OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                }
            }
            OS.SetWindowLongPtr(this.handle, -4, l3);
        }
    }

    void destroyItem(TreeColumn treeColumn) {
        Object object;
        int n;
        int n2;
        int n22;
        if (this.hwndHeader == 0L) {
            this.error(15);
        }
        for (n22 = 0; n22 < this.columnCount && this.columns[n22] != treeColumn; ++n22) {
        }
        int[] nArray = new int[this.columnCount];
        OS.SendMessage(this.hwndHeader, 4625, (long)this.columnCount, nArray);
        for (n2 = 0; n2 < this.columnCount && nArray[n2] != n22; ++n2) {
        }
        RECT rECT = new RECT();
        OS.SendMessage(this.hwndHeader, 4615, (long)n22, rECT);
        if (OS.SendMessage(this.hwndHeader, 4610, (long)n22, 0L) == 0L) {
            this.error(15);
        }
        System.arraycopy(this.columns, n22 + 1, this.columns, n22, --this.columnCount - n22);
        this.columns[this.columnCount] = null;
        TreeItem[] object2 = this.items;
        int n3 = object2.length;
        for (n = 0; n < n3; ++n) {
            TreeItem treeItem = object2[n];
            if (treeItem == null) continue;
            if (this.columnCount == 0) {
                treeItem.strings = null;
                treeItem.images = null;
                treeItem.cellBackground = null;
                treeItem.cellForeground = null;
                treeItem.cellFont = null;
                continue;
            }
            if (treeItem.strings != null) {
                object = treeItem.strings;
                if (n22 == 0) {
                    treeItem.text = object[1] != null ? object[1] : "";
                }
                String[] stringArray = new String[this.columnCount];
                System.arraycopy(object, 0, stringArray, 0, n22);
                System.arraycopy(object, n22 + 1, stringArray, n22, this.columnCount - n22);
                treeItem.strings = stringArray;
            } else if (n22 == 0) {
                treeItem.text = "";
            }
            if (treeItem.images != null) {
                object = treeItem.images;
                if (n22 == 0) {
                    treeItem.image = object[1];
                }
                Image[] imageArray = new Image[this.columnCount];
                System.arraycopy(object, 0, imageArray, 0, n22);
                System.arraycopy(object, n22 + 1, imageArray, n22, this.columnCount - n22);
                treeItem.images = imageArray;
            } else if (n22 == 0) {
                treeItem.image = null;
            }
            if (treeItem.cellBackground != null) {
                object = treeItem.cellBackground;
                int[] nArray2 = new int[this.columnCount];
                System.arraycopy(object, 0, nArray2, 0, n22);
                System.arraycopy(object, n22 + 1, nArray2, n22, this.columnCount - n22);
                treeItem.cellBackground = nArray2;
            }
            if (treeItem.cellForeground != null) {
                object = treeItem.cellForeground;
                int[] nArray3 = new int[this.columnCount];
                System.arraycopy(object, 0, nArray3, 0, n22);
                System.arraycopy(object, n22 + 1, nArray3, n22, this.columnCount - n22);
                treeItem.cellForeground = nArray3;
            }
            if (treeItem.cellFont == null) continue;
            object = treeItem.cellFont;
            Font[] fontArray = new Font[this.columnCount];
            System.arraycopy(object, 0, fontArray, 0, n22);
            System.arraycopy(object, n22 + 1, fontArray, n22, this.columnCount - n22);
            treeItem.cellFont = fontArray;
        }
        if (this.columnCount == 0) {
            this.scrollWidth = 0;
            if (!this.hooks(41)) {
                int n4;
                int n32 = OS.GetWindowLong(this.handle, -16);
                if ((this.style & 0x100) != 0) {
                    n4 = n32 & 0xFFFF7FFF;
                }
                OS.SetWindowLong(this.handle, -16, n4);
                OS.InvalidateRect(this.handle, null, true);
            }
            if (this.itemToolTipHandle != 0L) {
                OS.SendMessage(this.itemToolTipHandle, 1027, 3L, 0L);
            }
        } else {
            if (n22 == 0) {
                TreeColumn treeColumn2 = this.columns[0];
                treeColumn2.style &= 0xFEFDBFFF;
                TreeColumn treeColumn3 = this.columns[0];
                treeColumn3.style |= 0x4000;
                HDITEM hDITEM = new HDITEM();
                hDITEM.mask = 36;
                OS.SendMessage(this.hwndHeader, 4619, (long)n22, hDITEM);
                HDITEM hDITEM2 = hDITEM;
                hDITEM2.fmt &= 0xFFFFFFFC;
                object = hDITEM;
                object.fmt |= 0;
                OS.SendMessage(this.hwndHeader, 4620, (long)n22, hDITEM);
            }
            RECT rECT2 = new RECT();
            OS.GetClientRect(this.handle, rECT2);
            rECT2.left = rECT.left;
            OS.InvalidateRect(this.handle, rECT2, true);
        }
        this.setScrollWidth();
        this.updateImageList();
        this.updateScrollBar();
        if (this.columnCount != 0) {
            int[] nArray4 = new int[this.columnCount];
            OS.SendMessage(this.hwndHeader, 4625, (long)this.columnCount, nArray4);
            TreeColumn[] treeColumnArray = new TreeColumn[this.columnCount - n2];
            for (n = n2; n < nArray4.length; ++n) {
                TreeColumn treeColumn4 = this.columns[nArray4[n]];
                treeColumnArray[n - n2] = treeColumn4;
                treeColumn4.updateToolTip(nArray4[n]);
            }
            for (TreeColumn treeColumn5 : treeColumnArray) {
                if (treeColumn5.isDisposed()) continue;
                treeColumn5.sendEvent(10);
            }
        }
        if (this.headerToolTipHandle != 0L) {
            TOOLINFO tOOLINFO = new TOOLINFO();
            tOOLINFO.cbSize = TOOLINFO.sizeof;
            tOOLINFO.uId = treeColumn.id;
            tOOLINFO.hwnd = this.hwndHeader;
            OS.SendMessage(this.headerToolTipHandle, 1075, 0L, tOOLINFO);
        }
    }

    void destroyItem(TreeItem treeItem, long l2) {
        int n;
        long l3 = 0L;
        this.hLastIndexOf = 0L;
        this.hFirstIndexOf = 0L;
        this.itemCount = -1;
        long l4 = 0L;
        boolean bl = false;
        if ((this.style & 0x20000000) == 0 && this.getDrawing() && OS.IsWindowVisible(this.handle)) {
            RECT rECT = new RECT();
            boolean bl2 = bl = !OS.TreeView_GetItemRect(this.handle, l2, rECT, false);
        }
        if (bl) {
            l4 = OS.SendMessage(this.handle, 4362, 3L, l2);
            OS.UpdateWindow(this.handle);
            OS.DefWindowProc(this.handle, 11, 0L, 0L);
        }
        boolean bl3 = true;
        this.lockSelection = true;
        this.ignoreSelect = true;
        this.ignoreDeselect = true;
        long l5 = OS.SendMessage(this.handle, 4377, 0L, 0L);
        if (l5 != 0L) {
            OS.SendMessage(l5, 1052, 0L, 0L);
        }
        boolean bl4 = true;
        this.ignoreShrink = true;
        this.shrink = true;
        OS.SendMessage(this.handle, 4353, 0L, l2);
        this.ignoreShrink = false;
        boolean bl5 = false;
        this.lockSelection = false;
        this.ignoreSelect = false;
        this.ignoreDeselect = false;
        if (bl) {
            RECT rECT;
            OS.DefWindowProc(this.handle, 11, 1L, 0L);
            OS.ValidateRect(this.handle, null);
            if (OS.SendMessage(this.handle, 4362, 4L, l4) == 0L && OS.TreeView_GetItemRect(this.handle, l4, rECT = new RECT(), false)) {
                OS.InvalidateRect(this.handle, rECT, true);
            }
        }
        if ((n = (int)OS.SendMessage(this.handle, 4357, 0L, 0L)) == 0) {
            if (this.imageList != null) {
                OS.SendMessage(this.handle, 4361, 0L, 0L);
                this.display.releaseImageList(this.imageList);
            }
            this.imageList = null;
            if (!(this.hwndParent != 0L || this.linesVisible || this.hooks(41) || this.hooks(40) || this.hooks(42))) {
                this.customDraw = false;
            }
            this.items = new TreeItem[4];
            this.scrollWidth = 0;
            this.setScrollWidth();
        }
        if (this.getDrawing()) {
            this.updateScrollBar();
        }
        if (n == 0) {
            Event event = new Event();
            event.detail = 1;
            this.sendEvent(56, event);
        }
    }

    @Override
    void destroyScrollBar(int n) {
        super.destroyScrollBar(n);
        int n2 = OS.GetWindowLong(this.handle, -16);
        if ((this.style & 0x300) == 0) {
            n2 &= 0xFFCFFFFF;
            n2 |= 0x2000;
        } else if ((this.style & 0x100) == 0) {
            n2 &= 0xFFEFFFFF;
            n2 |= 0x8000;
        }
        OS.SetWindowLong(this.handle, -16, n2);
    }

    @Override
    void enableDrag(boolean bl) {
        int n = OS.GetWindowLong(this.handle, -16);
        n = bl && this.hooks(29) ? (n &= 0xFFFFFFEF) : (n |= 0x10);
        OS.SetWindowLong(this.handle, -16, n);
    }

    @Override
    void enableWidget(boolean bl) {
        super.enableWidget(bl);
        Control control = this.findBackgroundControl();
        if (control == null) {
            control = this;
        }
        if (control.backgroundImage == null) {
            this._setBackgroundPixel(this.hasCustomBackground() ? control.getBackgroundPixel() : -1);
        }
        if (this.hwndParent != 0L) {
            OS.EnableWindow(this.hwndParent, bl);
        }
        this.updateFullSelection();
    }

    int findIndex(long l2, long l3) {
        if (l2 == 0L) {
            return -1;
        }
        if (l2 == this.hFirstIndexOf) {
            if (this.hFirstIndexOf == l3) {
                this.hLastIndexOf = this.hFirstIndexOf;
                this.lastIndexOf = 0;
                return 0;
            }
            if (this.hLastIndexOf == l3) {
                return this.lastIndexOf;
            }
            long l4 = OS.SendMessage(this.handle, 4362, 2L, this.hLastIndexOf);
            if (l4 == l3) {
                this.hLastIndexOf = l4;
                return --this.lastIndexOf;
            }
            long l5 = OS.SendMessage(this.handle, 4362, 1L, this.hLastIndexOf);
            if (l5 == l3) {
                this.hLastIndexOf = l5;
                return ++this.lastIndexOf;
            }
            int n = this.lastIndexOf - 1;
            while (l4 != 0L && l4 != l3) {
                l4 = OS.SendMessage(this.handle, 4362, 2L, l4);
                --n;
            }
            if (l4 == l3) {
                this.hLastIndexOf = l4;
                this.lastIndexOf = n;
                return this.lastIndexOf;
            }
            int n2 = this.lastIndexOf + 1;
            while (l5 != 0L && l5 != l3) {
                l5 = OS.SendMessage(this.handle, 4362, 1L, l5);
                ++n2;
            }
            if (l5 == l3) {
                this.hLastIndexOf = l5;
                this.lastIndexOf = n2;
                return this.lastIndexOf;
            }
            return -1;
        }
        int n = 0;
        long l6 = l2;
        while (l6 != 0L && l6 != l3) {
            l6 = OS.SendMessage(this.handle, 4362, 1L, l6);
            ++n;
        }
        if (l6 == l3) {
            this.itemCount = -1;
            this.hFirstIndexOf = l2;
            this.hLastIndexOf = l6;
            this.lastIndexOf = n;
            return this.lastIndexOf;
        }
        return -1;
    }

    @Override
    Widget findItem(long l2) {
        return this._getItem(l2);
    }

    long findItem(long l2, int n) {
        int n2;
        if (l2 == 0L) {
            return 0L;
        }
        if (l2 == this.hFirstIndexOf) {
            if (n == 0) {
                this.lastIndexOf = 0;
                this.hLastIndexOf = this.hFirstIndexOf;
                return this.hLastIndexOf;
            }
            if (this.lastIndexOf == n) {
                return this.hLastIndexOf;
            }
            if (this.lastIndexOf - 1 == n) {
                --this.lastIndexOf;
                this.hLastIndexOf = OS.SendMessage(this.handle, 4362, 2L, this.hLastIndexOf);
                return this.hLastIndexOf;
            }
            if (this.lastIndexOf + 1 == n) {
                ++this.lastIndexOf;
                this.hLastIndexOf = OS.SendMessage(this.handle, 4362, 1L, this.hLastIndexOf);
                return this.hLastIndexOf;
            }
            if (n < this.lastIndexOf) {
                int n3;
                long l3 = OS.SendMessage(this.handle, 4362, 2L, this.hLastIndexOf);
                for (n3 = this.lastIndexOf - 1; l3 != 0L && n < n3; --n3) {
                    l3 = OS.SendMessage(this.handle, 4362, 2L, l3);
                }
                if (n == n3) {
                    this.lastIndexOf = n3;
                    this.hLastIndexOf = l3;
                    return this.hLastIndexOf;
                }
            } else {
                int n4;
                long l4 = OS.SendMessage(this.handle, 4362, 1L, this.hLastIndexOf);
                for (n4 = this.lastIndexOf + 1; l4 != 0L && n4 < n; ++n4) {
                    l4 = OS.SendMessage(this.handle, 4362, 1L, l4);
                }
                if (n == n4) {
                    this.lastIndexOf = n4;
                    this.hLastIndexOf = l4;
                    return this.hLastIndexOf;
                }
            }
            return 0L;
        }
        long l5 = l2;
        for (n2 = 0; l5 != 0L && n2 < n; ++n2) {
            l5 = OS.SendMessage(this.handle, 4362, 1L, l5);
        }
        if (n == n2) {
            this.itemCount = -1;
            this.lastIndexOf = n2;
            this.hFirstIndexOf = l2;
            this.hLastIndexOf = l5;
            return this.hLastIndexOf;
        }
        return 0L;
    }

    TreeItem getFocusItem() {
        long l2 = OS.SendMessage(this.handle, 4362, 9L, 0L);
        return l2 != 0L ? this._getItem(l2) : null;
    }

    public int getGridLineWidth() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getGridLineWidthInPixels());
    }

    int getGridLineWidthInPixels() {
        return 1;
    }

    public Color getHeaderBackground() {
        this.checkWidget();
        return Color.win32_new(this.display, this.getHeaderBackgroundPixel());
    }

    private int getHeaderBackgroundPixel() {
        return this.headerBackground != -1 ? this.headerBackground : this.defaultBackground();
    }

    public Color getHeaderForeground() {
        this.checkWidget();
        return Color.win32_new(this.display, this.getHeaderForegroundPixel());
    }

    private int getHeaderForegroundPixel() {
        return this.headerForeground != -1 ? this.headerForeground : this.defaultForeground();
    }

    public int getHeaderHeight() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getHeaderHeightInPixels());
    }

    int getHeaderHeightInPixels() {
        if (this.hwndHeader == 0L) {
            return 0;
        }
        RECT rECT = new RECT();
        OS.GetWindowRect(this.hwndHeader, rECT);
        return rECT.bottom - rECT.top;
    }

    Point getImageSize() {
        if (this.imageList != null) {
            return this.imageList.getImageSize();
        }
        return new Point(0, this.getItemHeightInPixels());
    }

    long getBottomItem() {
        long l2 = OS.SendMessage(this.handle, 4362, 5L, 0L);
        if (l2 == 0L) {
            return 0L;
        }
        int n = (int)OS.SendMessage(this.handle, 4368, 0L, 0L);
        for (int i = 0; i <= n; ++i) {
            long l3 = OS.SendMessage(this.handle, 4362, 6L, l2);
            if (l3 == 0L) {
                return l2;
            }
            l2 = l3;
        }
        return l2;
    }

    public TreeColumn getColumn(int n) {
        this.checkWidget();
        if (0 > n || n >= this.columnCount) {
            this.error(6);
        }
        return this.columns[n];
    }

    public int getColumnCount() {
        this.checkWidget();
        return this.columnCount;
    }

    public int[] getColumnOrder() {
        this.checkWidget();
        if (this.columnCount == 0) {
            return new int[0];
        }
        int[] nArray = new int[this.columnCount];
        OS.SendMessage(this.hwndHeader, 4625, (long)this.columnCount, nArray);
        return nArray;
    }

    public TreeColumn[] getColumns() {
        this.checkWidget();
        TreeColumn[] treeColumnArray = new TreeColumn[this.columnCount];
        System.arraycopy(this.columns, 0, treeColumnArray, 0, this.columnCount);
        return treeColumnArray;
    }

    public TreeItem getItem(int n) {
        long l2;
        long l3;
        this.checkWidget();
        if (n < 0) {
            this.error(6);
        }
        if ((l3 = OS.SendMessage(this.handle, 4362, 0L, 0L)) == 0L) {
            this.error(6);
        }
        if ((l2 = this.findItem(l3, n)) == 0L) {
            this.error(6);
        }
        return this._getItem(l2);
    }

    TreeItem getItem(NMTVCUSTOMDRAW nMTVCUSTOMDRAW) {
        int n = (int)nMTVCUSTOMDRAW.lItemlParam;
        if ((this.style & 0x10000000) != 0 && n == -1) {
            TVITEM tVITEM = new TVITEM();
            tVITEM.mask = 20;
            tVITEM.hItem = nMTVCUSTOMDRAW.dwItemSpec;
            OS.SendMessage(this.handle, 4414, 0L, tVITEM);
            n = (int)tVITEM.lParam;
        }
        return this._getItem(nMTVCUSTOMDRAW.dwItemSpec, n);
    }

    public TreeItem getItem(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        return this.getItemInPixels(DPIUtil.autoScaleUp(point));
    }

    /*
     * Exception decompiling
     */
    TreeItem getItemInPixels(Point var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl65 : ALOAD_2 - null : trying to set 3 previously set to 0
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

    public int getItemCount() {
        this.checkWidget();
        long l2 = OS.SendMessage(this.handle, 4362, 0L, 0L);
        if (l2 == 0L) {
            return 0;
        }
        return this.getItemCount(l2);
    }

    int getItemCount(long l2) {
        int n = 0;
        long l3 = l2;
        if (l2 == this.hFirstIndexOf) {
            if (this.itemCount != -1) {
                return this.itemCount;
            }
            l3 = this.hLastIndexOf;
            n = this.lastIndexOf;
        }
        while (l3 != 0L) {
            l3 = OS.SendMessage(this.handle, 4362, 1L, l3);
            ++n;
        }
        if (l2 == this.hFirstIndexOf) {
            this.itemCount = n;
        }
        return n;
    }

    public int getItemHeight() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getItemHeightInPixels());
    }

    int getItemHeightInPixels() {
        return (int)OS.SendMessage(this.handle, 4380, 0L, 0L);
    }

    public TreeItem[] getItems() {
        this.checkWidget();
        long l2 = OS.SendMessage(this.handle, 4362, 0L, 0L);
        if (l2 == 0L) {
            return new TreeItem[0];
        }
        return this.getItems(l2);
    }

    TreeItem[] getItems(long l2) {
        Object object;
        int n = 0;
        long l3 = l2;
        while (l3 != 0L) {
            l3 = OS.SendMessage(this.handle, 4362, 1L, l3);
            ++n;
        }
        int n2 = 0;
        TreeItem[] treeItemArray = new TreeItem[n];
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 20;
        tVITEM.hItem = l2;
        while (tVITEM.hItem != 0L) {
            OS.SendMessage(this.handle, 4414, 0L, tVITEM);
            object = this._getItem(tVITEM.hItem, (int)tVITEM.lParam);
            if (object != null) {
                treeItemArray[n2++] = object;
            }
            tVITEM.hItem = OS.SendMessage(this.handle, 4362, 1L, tVITEM.hItem);
        }
        if (n2 != n) {
            object = new TreeItem[n2];
            System.arraycopy(treeItemArray, 0, object, 0, n2);
            treeItemArray = object;
        }
        return treeItemArray;
    }

    public boolean getLinesVisible() {
        this.checkWidget();
        return this.linesVisible;
    }

    long getNextSelection(long l2) {
        while (l2 != 0L) {
            int n = (int)OS.SendMessage(this.handle, 4391, l2, 2L);
            if ((n & 2) != 0) {
                return l2;
            }
            long l3 = OS.SendMessage(this.handle, 4362, 4L, l2);
            long l4 = this.getNextSelection(l3);
            if (l4 != 0L) {
                return l4;
            }
            l2 = OS.SendMessage(this.handle, 4362, 1L, l2);
        }
        return 0L;
    }

    public TreeItem getParentItem() {
        this.checkWidget();
        return null;
    }

    /*
     * Exception decompiling
     */
    public TreeItem[] getSelection() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Pushing with a non-literal as pushee.
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.ArithmeticOperation.pushDown(ArithmeticOperation.java:341)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.ComparisonOperation.replaceSingleUsageLValues(ComparisonOperation.java:130)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.statement.IfStatement.replaceSingleUsageLValues(IfStatement.java:45)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.condense(Op03SimpleStatement.java:475)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.LValueProp.condenseLValues(LValueProp.java:68)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:504)
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

    public int getSelectionCount() {
        this.checkWidget();
        if ((this.style & 4) == 0) {
            int n = 0;
            long l2 = OS.GetWindowLongPtr(this.handle, -4);
            OS.SetWindowLongPtr(this.handle, -4, TreeProc);
            if ((this.style & 0x10000000) != 0) {
                long l3 = OS.SendMessage(this.handle, 4362, 0L, 0L);
                n = this.getSelection(l3, null, null, 0, -1, false, true);
            } else {
                for (TreeItem treeItem : this.items) {
                    long l4;
                    int n2;
                    if (treeItem == null || ((n2 = (int)OS.SendMessage(this.handle, 4391, l4 = treeItem.handle, 2L)) & 2) == 0) continue;
                    ++n;
                }
            }
            OS.SetWindowLongPtr(this.handle, -4, l2);
            return n;
        }
        long l5 = OS.SendMessage(this.handle, 4362, 9L, 0L);
        if (l5 == 0L) {
            return 0;
        }
        int n = (int)OS.SendMessage(this.handle, 4391, l5, 2L);
        return (n & 2) != 0 ? 1 : 0;
    }

    public TreeColumn getSortColumn() {
        this.checkWidget();
        return this.sortColumn;
    }

    int getSortColumnPixel() {
        int n = OS.IsWindowEnabled(this.handle) || this.hasCustomBackground() ? this.getBackgroundPixel() : OS.GetSysColor(15);
        return this.getSlightlyDifferentBackgroundColor(n);
    }

    public int getSortDirection() {
        this.checkWidget();
        return this.sortDirection;
    }

    public TreeItem getTopItem() {
        this.checkWidget();
        long l2 = OS.SendMessage(this.handle, 4362, 5L, 0L);
        return l2 != 0L ? this._getItem(l2) : null;
    }

    int imageIndex(Image image, int n) {
        int n2;
        if (image == null) {
            return -2;
        }
        if (this.imageList == null) {
            Rectangle rectangle = image.getBoundsInPixels();
            this.imageList = this.display.getImageList(this.style & 0x4000000, rectangle.width, rectangle.height);
        }
        if ((n2 = this.imageList.indexOf(image)) == -1) {
            n2 = this.imageList.add(image);
        }
        if (this.hwndHeader == 0L || OS.SendMessage(this.hwndHeader, 4623, 0L, 0L) == (long)n) {
            long l2 = this.imageList.getHandle();
            long l3 = OS.SendMessage(this.handle, 4360, 0L, 0L);
            if (l3 != l2) {
                OS.SendMessage(this.handle, 4361, 0L, l2);
                this.updateScrollBar();
            }
        }
        return n2;
    }

    int imageIndexHeader(Image image) {
        if (image == null) {
            return -2;
        }
        if (this.headerImageList == null) {
            Rectangle rectangle = image.getBoundsInPixels();
            this.headerImageList = this.display.getImageList(this.style & 0x4000000, rectangle.width, rectangle.height);
            int n = this.headerImageList.indexOf(image);
            if (n == -1) {
                n = this.headerImageList.add(image);
            }
            long l2 = this.headerImageList.getHandle();
            if (this.hwndHeader != 0L) {
                OS.SendMessage(this.hwndHeader, 4616, 0L, l2);
            }
            this.updateScrollBar();
            return n;
        }
        int n = this.headerImageList.indexOf(image);
        if (n != -1) {
            return n;
        }
        return this.headerImageList.add(image);
    }

    public int indexOf(TreeColumn treeColumn) {
        this.checkWidget();
        if (treeColumn == null) {
            this.error(4);
        }
        if (treeColumn.isDisposed()) {
            this.error(5);
        }
        for (int i = 0; i < this.columnCount; ++i) {
            if (this.columns[i] != treeColumn) continue;
            return i;
        }
        return -1;
    }

    public int indexOf(TreeItem treeItem) {
        long l2;
        this.checkWidget();
        if (treeItem == null) {
            this.error(4);
        }
        if (treeItem.isDisposed()) {
            this.error(5);
        }
        return (l2 = OS.SendMessage(this.handle, 4362, 0L, 0L)) == 0L ? -1 : this.findIndex(l2, treeItem.handle);
    }

    boolean isCustomToolTip() {
        return this.hooks(41);
    }

    boolean isItemSelected(NMTVCUSTOMDRAW nMTVCUSTOMDRAW) {
        boolean bl = false;
        if (OS.IsWindowEnabled(this.handle)) {
            TVITEM tVITEM = new TVITEM();
            tVITEM.mask = 24;
            tVITEM.hItem = nMTVCUSTOMDRAW.dwItemSpec;
            OS.SendMessage(this.handle, 4414, 0L, tVITEM);
            if ((tVITEM.state & 0xA) != 0) {
                bl = true;
                if (this.handle == OS.GetFocus()) {
                    if (OS.GetTextColor(nMTVCUSTOMDRAW.hdc) != OS.GetSysColor(14)) {
                        bl = false;
                    } else if (OS.GetBkColor(nMTVCUSTOMDRAW.hdc) != OS.GetSysColor(13)) {
                        bl = false;
                    }
                }
            } else if (nMTVCUSTOMDRAW.dwDrawStage == 65538 && OS.GetTextColor(nMTVCUSTOMDRAW.hdc) == OS.GetSysColor(14) && OS.GetBkColor(nMTVCUSTOMDRAW.hdc) == OS.GetSysColor(13)) {
                bl = true;
            }
        }
        return bl;
    }

    boolean isUseWsBorder() {
        return true;
    }

    int itemsGetFreeCapacity() {
        int n = 0;
        for (TreeItem treeItem : this.items) {
            if (treeItem != null) continue;
            ++n;
        }
        return n;
    }

    void itemsGrowArray(int n) {
        TreeItem[] treeItemArray = new TreeItem[n];
        System.arraycopy(this.items, 0, treeItemArray, 0, this.items.length);
        this.items = treeItemArray;
    }

    void redrawSelection() {
        if ((this.style & 4) != 0) {
            RECT rECT;
            long l2 = OS.SendMessage(this.handle, 4362, 9L, 0L);
            if (l2 != 0L && OS.TreeView_GetItemRect(this.handle, l2, rECT = new RECT(), false)) {
                OS.InvalidateRect(this.handle, rECT, true);
            }
        } else {
            long l3 = OS.SendMessage(this.handle, 4362, 5L, 0L);
            if (l3 != 0L) {
                RECT rECT = new RECT();
                int n = (int)OS.SendMessage(this.handle, 4368, 0L, 0L);
                for (int i = 0; i <= n && l3 != 0L; ++i) {
                    int n2 = (int)OS.SendMessage(this.handle, 4391, l3, 2L);
                    if ((n2 & 2) != 0 && OS.TreeView_GetItemRect(this.handle, l3, rECT, false)) {
                        OS.InvalidateRect(this.handle, rECT, true);
                    }
                    l3 = OS.SendMessage(this.handle, 4362, 6L, l3);
                }
            }
        }
    }

    @Override
    void register() {
        super.register();
        if (this.hwndParent != 0L) {
            this.display.addControl(this.hwndParent, this);
        }
        if (this.hwndHeader != 0L) {
            this.display.addControl(this.hwndHeader, this);
        }
    }

    void releaseItem(long l2, TVITEM tVITEM, boolean bl) {
        if (l2 == this.hAnchor) {
            this.hAnchor = 0L;
        }
        if (l2 == this.hInsert) {
            this.hInsert = 0L;
        }
        tVITEM.hItem = l2;
        if (OS.SendMessage(this.handle, 4414, 0L, tVITEM) != 0L && tVITEM.lParam != -1L) {
            TreeItem treeItem;
            if (tVITEM.lParam < (long)this.lastID) {
                this.lastID = (int)tVITEM.lParam;
            }
            if (bl && (treeItem = this.items[(int)tVITEM.lParam]) != null) {
                treeItem.release(false);
            }
            this.items[(int)tVITEM.lParam] = null;
        }
    }

    void releaseItems(long l2, TVITEM tVITEM) {
        l2 = OS.SendMessage(this.handle, 4362, 4L, l2);
        while (l2 != 0L) {
            this.releaseItems(l2, tVITEM);
            this.releaseItem(l2, tVITEM, true);
            l2 = OS.SendMessage(this.handle, 4362, 1L, l2);
        }
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        long l2 = 0L;
        this.hwndHeader = 0L;
        this.hwndParent = 0L;
    }

    @Override
    void releaseChildren(boolean bl) {
        if (this.items != null) {
            for (Item item : this.items) {
                if (item == null || item.isDisposed()) continue;
                item.release(false);
            }
            this.items = null;
        }
        if (this.columns != null) {
            for (Item item : this.columns) {
                if (item == null || item.isDisposed()) continue;
                item.release(false);
            }
            this.columns = null;
        }
        super.releaseChildren(bl);
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.customDraw = false;
        if (this.imageList != null) {
            OS.SendMessage(this.handle, 4361, 0L, 0L);
            this.display.releaseImageList(this.imageList);
        }
        if (this.headerImageList != null) {
            if (this.hwndHeader != 0L) {
                OS.SendMessage(this.hwndHeader, 4616, 0L, 0L);
            }
            this.display.releaseImageList(this.headerImageList);
        }
        Object var1_1 = null;
        this.headerImageList = var1_1;
        this.imageList = var1_1;
        long l2 = OS.SendMessage(this.handle, 4360, 2L, 0L);
        OS.SendMessage(this.handle, 4361, 2L, 0L);
        if (l2 != 0L) {
            OS.ImageList_Destroy(l2);
        }
        if (this.itemToolTipHandle != 0L) {
            OS.DestroyWindow(this.itemToolTipHandle);
        }
        if (this.headerToolTipHandle != 0L) {
            OS.DestroyWindow(this.headerToolTipHandle);
        }
        long l3 = 0L;
        this.headerToolTipHandle = 0L;
        this.itemToolTipHandle = 0L;
    }

    public void removeAll() {
        int n;
        this.checkWidget();
        long l2 = 0L;
        this.hLastIndexOf = 0L;
        this.hFirstIndexOf = 0L;
        this.itemCount = -1;
        TreeItem[] treeItemArray = this.items;
        int n2 = treeItemArray.length;
        for (n = 0; n < n2; ++n) {
            TreeItem treeItem = treeItemArray[n];
            if (treeItem == null || treeItem.isDisposed()) continue;
            treeItem.release(false);
        }
        boolean bl = true;
        this.ignoreSelect = true;
        this.ignoreDeselect = true;
        int n3 = n2 = this.getDrawing() && OS.IsWindowVisible(this.handle) ? 1 : 0;
        if (n2 != 0) {
            OS.DefWindowProc(this.handle, 11, 0L, 0L);
        }
        n = 1;
        this.ignoreShrink = true;
        this.shrink = true;
        long l3 = OS.SendMessage(this.handle, 4353, 0L, -65536L);
        this.ignoreShrink = false;
        if (n2 != 0) {
            OS.DefWindowProc(this.handle, 11, 1L, 0L);
            OS.InvalidateRect(this.handle, null, true);
        }
        boolean bl2 = false;
        this.ignoreSelect = false;
        this.ignoreDeselect = false;
        if (l3 == 0L) {
            this.error(15);
        }
        if (this.imageList != null) {
            OS.SendMessage(this.handle, 4361, 0L, 0L);
            this.display.releaseImageList(this.imageList);
        }
        this.imageList = null;
        if (!(this.hwndParent != 0L || this.linesVisible || this.hooks(41) || this.hooks(40) || this.hooks(42))) {
            this.customDraw = false;
        }
        long l4 = 0L;
        this.hLastIndexOf = 0L;
        this.hFirstIndexOf = 0L;
        this.hInsert = 0L;
        this.hAnchor = 0L;
        this.itemCount = -1;
        this.items = new TreeItem[4];
        this.scrollWidth = 0;
        this.setScrollWidth();
        this.updateScrollBar();
    }

    public void removeSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            this.error(4);
        }
        this.eventTable.unhook(13, selectionListener);
        this.eventTable.unhook(14, selectionListener);
    }

    public void removeTreeListener(TreeListener treeListener) {
        this.checkWidget();
        if (treeListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(17, treeListener);
        this.eventTable.unhook(18, treeListener);
    }

    @Override
    void reskinChildren(int n) {
        if (this.items != null) {
            for (Item item : this.items) {
                if (item == null) continue;
                item.reskinChildren(n);
            }
        }
        if (this.columns != null) {
            for (Item item : this.columns) {
                if (item == null) continue;
                item.reskinChildren(n);
            }
        }
        super.reskinChildren(n);
    }

    public void setInsertMark(TreeItem treeItem, boolean bl) {
        this.checkWidget();
        long l2 = 0L;
        if (treeItem != null) {
            if (treeItem.isDisposed()) {
                this.error(5);
            }
            l2 = treeItem.handle;
        }
        this.hInsert = l2;
        this.insertAfter = !bl;
        OS.SendMessage(this.handle, 4378, this.insertAfter ? 1L : 0L, this.hInsert);
    }

    public void setItemCount(int n) {
        this.checkWidget();
        n = Math.max(0, n);
        this.setItemCount(n, -65536L);
    }

    void setItemCount(int n, long l2) {
        int n2;
        int n3;
        long l3 = 0L;
        int n4 = 0;
        long l4 = 0L;
        long l5 = -65535L;
        long l6 = OS.SendMessage(this.handle, 4362, 4L, l2);
        for (n3 = 0; l6 != 0L && n3 < n; ++n3) {
            l5 = l6;
            l6 = OS.SendMessage(this.handle, 4362, 1L, l6);
        }
        if (n3 == n && l6 == 0L) {
            return;
        }
        if (n3 == n) {
            l4 = l6;
        } else if (l6 == 0L) {
            l3 = l5;
            n4 = n - n3;
        }
        boolean bl = false;
        if (OS.SendMessage(this.handle, 4357, 0L, 0L) == 0L) {
            boolean bl2 = bl = this.getDrawing() && OS.IsWindowVisible(this.handle);
            if (bl) {
                OS.DefWindowProc(this.handle, 11, 0L, 0L);
            }
        }
        boolean bl3 = false;
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 20;
        if (!bl && (this.style & 0x10000000) != 0 && l2 != -65536L) {
            n2 = (int)OS.SendMessage(this.handle, 4391, l2, 32L);
            boolean bl4 = bl3 = (n2 & 0x20) != 0;
        }
        if (l4 != 0L) {
            while (l4 != 0L) {
                TreeItem treeItem;
                tVITEM.hItem = l4;
                OS.SendMessage(this.handle, 4414, 0L, tVITEM);
                l4 = OS.SendMessage(this.handle, 4362, 1L, l4);
                TreeItem treeItem2 = treeItem = tVITEM.lParam != -1L ? this.items[(int)tVITEM.lParam] : null;
                if (treeItem != null && !treeItem.isDisposed()) {
                    treeItem.dispose();
                    continue;
                }
                this.releaseItem(tVITEM.hItem, tVITEM, false);
                this.destroyItem(null, tVITEM.hItem);
            }
        } else {
            n2 = this.itemsGetFreeCapacity();
            if (n4 > n2) {
                this.itemsGrowArray(this.items.length + n4 - n2);
            }
            if ((this.style & 0x10000000) != 0) {
                for (int i = 0; i < n4; ++i) {
                    if (bl3) {
                        this.ignoreShrink = true;
                    }
                    this.createItem(null, l2, l3, 0L);
                    if (!bl3) continue;
                    this.ignoreShrink = false;
                }
            } else {
                for (int i = 0; i < n4; ++i) {
                    new TreeItem(this, 0, l2, l3, 0L);
                }
            }
        }
        if (bl) {
            OS.DefWindowProc(this.handle, 11, 1L, 0L);
            OS.InvalidateRect(this.handle, null, true);
        }
    }

    void setItemHeight(int n) {
        this.checkWidget();
        if (n < -1) {
            this.error(5);
        }
        OS.SendMessage(this.handle, 4379, (long)n, 0L);
    }

    public void setLinesVisible(boolean bl) {
        this.checkWidget();
        if (this.linesVisible == bl) {
            return;
        }
        this.linesVisible = bl;
        if (this.hwndParent == 0L && this.linesVisible) {
            this.customDraw = true;
        }
        OS.InvalidateRect(this.handle, null, true);
        if (this.hwndHeader != 0L) {
            OS.InvalidateRect(this.hwndHeader, null, true);
        }
    }

    @Override
    long scrolledHandle() {
        if (this.hwndHeader == 0L) {
            return this.handle;
        }
        return this.columnCount == 0 && this.scrollWidth == 0 ? this.handle : this.hwndParent;
    }

    void select(long l2, TVITEM tVITEM) {
        while (l2 != 0L) {
            tVITEM.hItem = l2;
            OS.SendMessage(this.handle, 4415, 0L, tVITEM);
            int n = (int)OS.SendMessage(this.handle, 4391, l2, 32L);
            if ((n & 0x20) != 0) {
                long l3 = OS.SendMessage(this.handle, 4362, 4L, l2);
                this.select(l3, tVITEM);
            }
            l2 = OS.SendMessage(this.handle, 4362, 1L, l2);
        }
    }

    public void select(TreeItem treeItem) {
        long l2;
        boolean bl;
        this.checkWidget();
        if (treeItem == null) {
            this.error(4);
        }
        if (treeItem.isDisposed()) {
            this.error(5);
        }
        if ((this.style & 4) == 0) {
            this.expandToItem(treeItem);
            TVITEM tVITEM = new TVITEM();
            tVITEM.mask = 24;
            tVITEM.stateMask = 2;
            tVITEM.state = 2;
            tVITEM.hItem = treeItem.handle;
            OS.SendMessage(this.handle, 4415, 0L, tVITEM);
            return;
        }
        long l3 = treeItem.handle;
        int n = (int)OS.SendMessage(this.handle, 4391, l3, 2L);
        if ((n & 2) != 0) {
            return;
        }
        SCROLLINFO sCROLLINFO = null;
        int n2 = OS.GetWindowLong(this.handle, -16);
        if ((n2 & 0xA000) == 0) {
            sCROLLINFO = new SCROLLINFO();
            sCROLLINFO.cbSize = SCROLLINFO.sizeof;
            sCROLLINFO.fMask = 23;
            OS.GetScrollInfo(this.handle, 0, sCROLLINFO);
        }
        SCROLLINFO sCROLLINFO2 = new SCROLLINFO();
        sCROLLINFO2.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO2.fMask = 23;
        OS.GetScrollInfo(this.handle, 1, sCROLLINFO2);
        boolean bl2 = bl = this.getDrawing() && OS.IsWindowVisible(this.handle);
        if (bl) {
            OS.UpdateWindow(this.handle);
            OS.DefWindowProc(this.handle, 11, 0L, 0L);
        }
        this.setSelection(treeItem);
        if (sCROLLINFO != null) {
            l2 = OS.MAKELPARAM(4, sCROLLINFO.nPos);
            OS.SendMessage(this.handle, 276, l2, 0L);
        }
        OS.SetScrollInfo(this.handle, 1, sCROLLINFO2, true);
        l2 = OS.MAKELPARAM(4, sCROLLINFO2.nPos);
        OS.SendMessage(this.handle, 277, l2, 0L);
        if (bl) {
            OS.DefWindowProc(this.handle, 11, 1L, 0L);
            OS.InvalidateRect(this.handle, null, true);
            if ((this.style & 0x20000000) == 0) {
                int n3 = this.style;
                this.style |= 0x20000000;
                OS.UpdateWindow(this.handle);
                this.style = n3;
            }
        }
    }

    public void selectAll() {
        this.checkWidget();
        if ((this.style & 4) != 0) {
            return;
        }
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 24;
        tVITEM.state = 2;
        tVITEM.stateMask = 2;
        long l2 = OS.GetWindowLongPtr(this.handle, -4);
        OS.SetWindowLongPtr(this.handle, -4, TreeProc);
        long l3 = OS.SendMessage(this.handle, 4362, 0L, 0L);
        this.select(l3, tVITEM);
        OS.SetWindowLongPtr(this.handle, -4, l2);
    }

    Event sendEraseItemEvent(TreeItem treeItem, NMTTCUSTOMDRAW nMTTCUSTOMDRAW, int n, RECT rECT) {
        int n2 = OS.SaveDC(nMTTCUSTOMDRAW.hdc);
        RECT rECT2 = this.toolTipInset(rECT);
        OS.SetWindowOrgEx(nMTTCUSTOMDRAW.hdc, rECT2.left, rECT2.top, null);
        GCData gCData = new GCData();
        gCData.device = this.display;
        gCData.foreground = OS.GetTextColor(nMTTCUSTOMDRAW.hdc);
        gCData.background = OS.GetBkColor(nMTTCUSTOMDRAW.hdc);
        gCData.font = treeItem.getFont(n);
        gCData.uiState = (int)OS.SendMessage(this.handle, 297, 0L, 0L);
        GC gC = GC.win32_new(nMTTCUSTOMDRAW.hdc, gCData);
        Event event = new Event();
        event.item = treeItem;
        event.index = n;
        event.gc = gC;
        Event event2 = event;
        event2.detail |= 0x10;
        event.setBoundsInPixels(new Rectangle(rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top));
        this.sendEvent(40, event);
        event.gc = null;
        gC.dispose();
        OS.RestoreDC(nMTTCUSTOMDRAW.hdc, n2);
        return event;
    }

    Event sendMeasureItemEvent(TreeItem treeItem, int n, long l2, int n2) {
        RECT rECT = treeItem.getBounds(n, true, true, false, false, false, l2);
        int n3 = OS.SaveDC(l2);
        GCData gCData = new GCData();
        gCData.device = this.display;
        gCData.font = treeItem.getFont(n);
        GC gC = GC.win32_new(l2, gCData);
        Event event = new Event();
        event.item = treeItem;
        event.gc = gC;
        event.index = n;
        event.setBoundsInPixels(new Rectangle(rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top));
        event.detail = n2;
        this.sendEvent(41, event);
        event.gc = null;
        gC.dispose();
        OS.RestoreDC(l2, n3);
        if (this.isDisposed() || treeItem.isDisposed()) {
            return null;
        }
        Rectangle rectangle = event.getBoundsInPixels();
        if (this.hwndHeader != 0L && this.columnCount == 0 && rectangle.x + rectangle.width > this.scrollWidth) {
            this.scrollWidth = rectangle.x + rectangle.width;
            this.setScrollWidth(this.scrollWidth);
        }
        if (rectangle.height > this.getItemHeightInPixels()) {
            this.setItemHeight(rectangle.height);
        }
        return event;
    }

    Event sendPaintItemEvent(TreeItem treeItem, NMTTCUSTOMDRAW nMTTCUSTOMDRAW, int n, RECT rECT) {
        int n2 = OS.SaveDC(nMTTCUSTOMDRAW.hdc);
        RECT rECT2 = this.toolTipInset(rECT);
        OS.SetWindowOrgEx(nMTTCUSTOMDRAW.hdc, rECT2.left, rECT2.top, null);
        GCData gCData = new GCData();
        gCData.device = this.display;
        gCData.font = treeItem.getFont(n);
        gCData.foreground = OS.GetTextColor(nMTTCUSTOMDRAW.hdc);
        gCData.background = OS.GetBkColor(nMTTCUSTOMDRAW.hdc);
        gCData.uiState = (int)OS.SendMessage(this.handle, 297, 0L, 0L);
        GC gC = GC.win32_new(nMTTCUSTOMDRAW.hdc, gCData);
        Event event = new Event();
        event.item = treeItem;
        event.index = n;
        event.gc = gC;
        Event event2 = event;
        event2.detail |= 0x10;
        event.setBoundsInPixels(new Rectangle(rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top));
        this.sendEvent(42, event);
        event.gc = null;
        gC.dispose();
        OS.RestoreDC(nMTTCUSTOMDRAW.hdc, n2);
        return event;
    }

    @Override
    void setBackgroundImage(long l2) {
        super.setBackgroundImage(l2);
        if (l2 != 0L) {
            if (OS.SendMessage(this.handle, 4383, 0L, 0L) == -1L) {
                OS.SendMessage(this.handle, 4381, 0L, -1L);
            }
            this._setBackgroundPixel(-1);
        } else {
            Control control = this.findBackgroundControl();
            if (control == null) {
                control = this;
            }
            if (control.backgroundImage == null) {
                this.setBackgroundPixel(control.getBackgroundPixel());
            }
        }
        this.updateFullSelection();
    }

    @Override
    void setBackgroundPixel(int n) {
        Control control = this.findImageControl();
        if (control != null) {
            this.setBackgroundImage(control.backgroundImage);
            return;
        }
        this._setBackgroundPixel(n);
        this.updateFullSelection();
    }

    @Override
    void setCursor() {
        Cursor cursor = this.findCursor();
        long l2 = cursor == null ? OS.LoadCursor(0L, 32512L) : cursor.handle;
        OS.SetCursor(l2);
    }

    public void setColumnOrder(int[] nArray) {
        int n;
        this.checkWidget();
        if (nArray == null) {
            this.error(4);
        }
        if (this.columnCount == 0) {
            if (nArray.length != 0) {
                this.error(5);
            }
            return;
        }
        if (nArray.length != this.columnCount) {
            this.error(5);
        }
        int[] nArray2 = new int[this.columnCount];
        OS.SendMessage(this.hwndHeader, 4625, (long)this.columnCount, nArray2);
        boolean bl = false;
        boolean[] blArray = new boolean[this.columnCount];
        for (int i = 0; i < nArray.length; ++i) {
            n = nArray[i];
            if (n < 0 || n >= this.columnCount) {
                this.error(6);
            }
            if (blArray[n]) {
                this.error(5);
            }
            blArray[n] = true;
            if (n == nArray2[i]) continue;
            bl = true;
        }
        if (bl) {
            RECT[] rECTArray = new RECT[this.columnCount];
            for (n = 0; n < this.columnCount; ++n) {
                rECTArray[n] = new RECT();
                OS.SendMessage(this.hwndHeader, 4615, (long)n, rECTArray[n]);
            }
            OS.SendMessage(this.hwndHeader, 4626, (long)nArray.length, nArray);
            OS.InvalidateRect(this.handle, null, true);
            this.updateImageList();
            TreeColumn[] treeColumnArray = new TreeColumn[this.columnCount];
            System.arraycopy(this.columns, 0, treeColumnArray, 0, this.columnCount);
            RECT rECT = new RECT();
            for (int i = 0; i < this.columnCount; ++i) {
                TreeColumn treeColumn = treeColumnArray[i];
                if (treeColumn.isDisposed()) continue;
                OS.SendMessage(this.hwndHeader, 4615, (long)i, rECT);
                if (rECT.left == rECTArray[i].left) continue;
                treeColumn.updateToolTip(i);
                treeColumn.sendEvent(10);
            }
        }
    }

    void setCheckboxImageList() {
        long l2;
        int n;
        if ((this.style & 0x20) == 0) {
            return;
        }
        int n2 = 5;
        int n3 = 32;
        if ((this.style & 0x4000000) != 0) {
            n3 |= 0x2000;
        }
        if (!OS.IsAppThemed()) {
            n3 |= 1;
        }
        int n4 = n = (int)OS.SendMessage(this.handle, 4380, 0L, 0L);
        long l3 = OS.ImageList_Create(n, n4, n3, 5, 5);
        long l4 = OS.GetDC(this.handle);
        long l5 = OS.CreateCompatibleDC(l4);
        long l6 = OS.CreateCompatibleBitmap(l4, n * 5, n4);
        long l7 = OS.SelectObject(l5, l6);
        RECT rECT = new RECT();
        OS.SetRect(rECT, 0, 0, n * 5, n4);
        int n5 = 0;
        if (OS.IsAppThemed()) {
            Control control = this.findBackgroundControl();
            if (control == null) {
                control = this;
            }
            n5 = control.getBackgroundPixel();
        } else {
            n5 = 0x20000FF;
            if ((n5 & 0xFFFFFF) == OS.GetSysColor(5)) {
                n5 = 0x200FF00;
            }
        }
        long l8 = OS.CreateSolidBrush(n5);
        OS.FillRect(l5, rECT, l8);
        OS.DeleteObject(l8);
        long l9 = OS.SelectObject(l4, this.defaultFont());
        TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
        OS.GetTextMetrics(l4, tEXTMETRIC);
        OS.SelectObject(l4, l9);
        int n6 = Math.min(tEXTMETRIC.tmHeight, n);
        int n7 = Math.min(tEXTMETRIC.tmHeight, n4);
        if (OS.IsAppThemed()) {
            SIZE sIZE = new SIZE();
            OS.GetThemePartSize(this.display.hButtonTheme(), l5, 3, 0, null, 1, sIZE);
            n6 = Math.min(sIZE.cx, n6);
            n7 = Math.min(sIZE.cy, n7);
        }
        int n8 = (n - n6) / 2;
        int n9 = (n4 - n7) / 2 + 1;
        OS.SetRect(rECT, n8 + n, n9, n8 + n + n6, n9 + n7);
        if (OS.IsAppThemed()) {
            l2 = this.display.hButtonTheme();
            OS.DrawThemeBackground(l2, l5, 3, 1, rECT, null);
            RECT rECT2 = rECT;
            rECT2.left += n;
            RECT rECT3 = rECT;
            rECT3.right += n;
            OS.DrawThemeBackground(l2, l5, 3, 5, rECT, null);
            RECT rECT4 = rECT;
            rECT4.left += n;
            RECT rECT5 = rECT;
            rECT5.right += n;
            OS.DrawThemeBackground(l2, l5, 3, 1, rECT, null);
            RECT rECT6 = rECT;
            rECT6.left += n;
            RECT rECT7 = rECT;
            rECT7.right += n;
            OS.DrawThemeBackground(l2, l5, 3, 9, rECT, null);
        } else {
            OS.DrawFrameControl(l5, rECT, 4, 16384);
            RECT rECT8 = rECT;
            rECT8.left += n;
            RECT rECT9 = rECT;
            rECT9.right += n;
            OS.DrawFrameControl(l5, rECT, 4, 17408);
            RECT rECT10 = rECT;
            rECT10.left += n;
            RECT rECT11 = rECT;
            rECT11.right += n;
            OS.DrawFrameControl(l5, rECT, 4, 16640);
            RECT rECT12 = rECT;
            rECT12.left += n;
            RECT rECT13 = rECT;
            rECT13.right += n;
            OS.DrawFrameControl(l5, rECT, 4, 17664);
        }
        OS.SelectObject(l5, l7);
        OS.DeleteDC(l5);
        OS.ReleaseDC(this.handle, l4);
        if (OS.IsAppThemed()) {
            OS.ImageList_Add(l3, l6, 0L);
        } else {
            OS.ImageList_AddMasked(l3, l6, n5);
        }
        OS.DeleteObject(l6);
        l2 = OS.SendMessage(this.handle, 4360, 2L, 0L);
        OS.SendMessage(this.handle, 4361, 2L, l3);
        if (l2 != 0L) {
            OS.ImageList_Destroy(l2);
        }
    }

    @Override
    public void setFont(Font font) {
        this.checkWidget();
        super.setFont(font);
        if ((this.style & 0x20) != 0) {
            this.setCheckboxImageList();
        }
    }

    @Override
    void setForegroundPixel(int n) {
        if (this.explorerTheme && n == -1) {
            n = this.defaultForeground();
        }
        OS.SendMessage(this.handle, 4382, 0L, n);
    }

    public void setHeaderBackground(Color color) {
        this.checkWidget();
        int n = -1;
        if (color != null) {
            if (color.isDisposed()) {
                this.error(5);
            }
            n = color.handle;
        }
        if (n == this.headerBackground) {
            return;
        }
        this.headerBackground = n;
        if (this == false) {
            OS.InvalidateRect(this.hwndHeader, null, true);
        }
    }

    public void setHeaderForeground(Color color) {
        this.checkWidget();
        int n = -1;
        if (color != null) {
            if (color.isDisposed()) {
                this.error(5);
            }
            n = color.handle;
        }
        if (n == this.headerForeground) {
            return;
        }
        this.headerForeground = n;
        if (this == false) {
            OS.InvalidateRect(this.hwndHeader, null, true);
        }
    }

    public void setHeaderVisible(boolean bl) {
        this.checkWidget();
        if (this.hwndHeader == 0L) {
            if (!bl) {
                return;
            }
            this.createParent();
        }
        int n = OS.GetWindowLong(this.hwndHeader, -16);
        if (bl) {
            if ((n & 8) == 0) {
                return;
            }
            OS.SetWindowLong(this.hwndHeader, -16, n &= 0xFFFFFFF7);
            OS.ShowWindow(this.hwndHeader, 5);
        } else {
            if ((n & 8) != 0) {
                return;
            }
            OS.SetWindowLong(this.hwndHeader, -16, n |= 8);
            OS.ShowWindow(this.hwndHeader, 0);
        }
        this.setScrollWidth();
        this.updateHeaderToolTips();
        this.updateScrollBar();
    }

    @Override
    public void setRedraw(boolean bl) {
        int n;
        boolean bl2;
        this.checkWidget();
        long l2 = 0L;
        boolean bl3 = bl2 = bl && this.drawCount == 1;
        if (bl2) {
            n = (int)OS.SendMessage(this.handle, 4357, 0L, 0L);
            if (n == 0) {
                TVINSERTSTRUCT tVINSERTSTRUCT = new TVINSERTSTRUCT();
                tVINSERTSTRUCT.hInsertAfter = -65535L;
                l2 = OS.SendMessage(this.handle, 4402, 0L, tVINSERTSTRUCT);
            }
            OS.DefWindowProc(this.handle, 11, 1L, 0L);
            this.updateScrollBar();
        }
        super.setRedraw(bl);
        int n2 = n = !bl && this.drawCount == 1 ? 1 : 0;
        if (n != 0) {
            OS.DefWindowProc(this.handle, 11, 0L, 0L);
        }
        if (l2 != 0L) {
            this.ignoreShrink = true;
            OS.SendMessage(this.handle, 4353, 0L, l2);
            this.ignoreShrink = false;
        }
    }

    void setScrollWidth() {
        if (this.hwndHeader == 0L || this.hwndParent == 0L) {
            return;
        }
        int n = 0;
        HDITEM hDITEM = new HDITEM();
        for (int i = 0; i < this.columnCount; ++i) {
            hDITEM.mask = 1;
            OS.SendMessage(this.hwndHeader, 4619, (long)i, hDITEM);
            n += hDITEM.cxy;
        }
        this.setScrollWidth(Math.max(this.scrollWidth, n));
    }

    void setScrollWidth(int n) {
        if (this.hwndHeader == 0L || this.hwndParent == 0L) {
            return;
        }
        int n2 = 0;
        RECT rECT = new RECT();
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 3;
        if (this.columnCount == 0 && n == 0) {
            OS.GetScrollInfo(this.hwndParent, 0, sCROLLINFO);
            sCROLLINFO.nPage = sCROLLINFO.nMax + 1;
            OS.SetScrollInfo(this.hwndParent, 0, sCROLLINFO, true);
            OS.GetScrollInfo(this.hwndParent, 1, sCROLLINFO);
            sCROLLINFO.nPage = sCROLLINFO.nMax + 1;
            OS.SetScrollInfo(this.hwndParent, 1, sCROLLINFO, true);
        } else if ((this.style & 0x100) != 0) {
            OS.GetClientRect(this.hwndParent, rECT);
            OS.GetScrollInfo(this.hwndParent, 0, sCROLLINFO);
            sCROLLINFO.nMax = n;
            sCROLLINFO.nPage = rECT.right - rECT.left + 1;
            OS.SetScrollInfo(this.hwndParent, 0, sCROLLINFO, true);
            sCROLLINFO.fMask = 4;
            OS.GetScrollInfo(this.hwndParent, 0, sCROLLINFO);
            n2 = sCROLLINFO.nPos;
        }
        if (this.horizontalBar != null) {
            this.horizontalBar.setIncrement(5);
            this.horizontalBar.setPageIncrement(sCROLLINFO.nPage);
        }
        OS.GetClientRect(this.hwndParent, rECT);
        long l2 = OS.GetProcessHeap();
        HDLAYOUT hDLAYOUT = new HDLAYOUT();
        hDLAYOUT.prc = OS.HeapAlloc(l2, 8, RECT.sizeof);
        hDLAYOUT.pwpos = OS.HeapAlloc(l2, 8, WINDOWPOS.sizeof);
        OS.MoveMemory(hDLAYOUT.prc, rECT, RECT.sizeof);
        OS.SendMessage(this.hwndHeader, 4613, 0L, hDLAYOUT);
        WINDOWPOS wINDOWPOS = new WINDOWPOS();
        OS.MoveMemory(wINDOWPOS, hDLAYOUT.pwpos, WINDOWPOS.sizeof);
        if (hDLAYOUT.prc != 0L) {
            OS.HeapFree(l2, 0, hDLAYOUT.prc);
        }
        if (hDLAYOUT.pwpos != 0L) {
            OS.HeapFree(l2, 0, hDLAYOUT.pwpos);
        }
        OS.SetWindowPos(this.hwndHeader, 0L, wINDOWPOS.x - n2, wINDOWPOS.y, wINDOWPOS.cx + n2, wINDOWPOS.cy, 16);
        int n3 = wINDOWPOS.cx + (this.columnCount == 0 && n == 0 ? 0 : OS.GetSystemMetrics(2));
        int n4 = rECT.bottom - rECT.top - wINDOWPOS.cy;
        boolean bl = this.ignoreResize;
        this.ignoreResize = true;
        OS.SetWindowPos(this.handle, 0L, wINDOWPOS.x - n2, wINDOWPOS.y + wINDOWPOS.cy, n3 + n2, n4, 20);
        this.ignoreResize = bl;
    }

    void setSelection(long l2, TVITEM tVITEM, TreeItem[] treeItemArray) {
        while (l2 != 0L) {
            TreeItem treeItem;
            int n;
            for (n = 0; n < treeItemArray.length && ((treeItem = treeItemArray[n]) == null || treeItem.handle != l2); ++n) {
            }
            tVITEM.hItem = l2;
            OS.SendMessage(this.handle, 4414, 0L, tVITEM);
            if ((tVITEM.state & 2) != 0) {
                if (n == treeItemArray.length) {
                    tVITEM.state = 0;
                    OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                }
            } else if (n != treeItemArray.length) {
                this.expandToItem(this._getItem(l2));
                tVITEM.state = 2;
                OS.SendMessage(this.handle, 4415, 0L, tVITEM);
            }
            long l3 = OS.SendMessage(this.handle, 4362, 4L, l2);
            this.setSelection(l3, tVITEM, treeItemArray);
            l2 = OS.SendMessage(this.handle, 4362, 1L, l2);
        }
    }

    public void setSelection(TreeItem treeItem) {
        this.checkWidget();
        if (treeItem == null) {
            this.error(4);
        }
        this.setSelection(new TreeItem[]{treeItem});
    }

    public void setSelection(TreeItem[] treeItemArray) {
        int n;
        this.checkWidget();
        if (treeItemArray == null) {
            this.error(4);
        }
        if ((n = treeItemArray.length) == 0 || (this.style & 4) != 0 && n > 1) {
            this.deselectAll();
            return;
        }
        TreeItem treeItem = treeItemArray[0];
        if (treeItem != null) {
            long l2;
            if (treeItem.isDisposed()) {
                this.error(5);
            }
            long l3 = OS.SendMessage(this.handle, 4362, 9L, 0L);
            this.hAnchor = l2 = treeItem.handle;
            long l4 = l2;
            boolean bl = this.checkScroll(l4);
            if (bl) {
                OS.SendMessage(this.handle, 11, 1L, 0L);
                OS.DefWindowProc(this.handle, 11, 0L, 0L);
            }
            this.ignoreSelect = true;
            OS.SendMessage(this.handle, 4363, 9L, l4);
            this.ignoreSelect = false;
            if (OS.SendMessage(this.handle, 4368, 0L, 0L) == 0L) {
                OS.SendMessage(this.handle, 4363, 5L, l4);
                long l5 = OS.SendMessage(this.handle, 4362, 3L, l4);
                if (l5 == 0L) {
                    OS.SendMessage(this.handle, 276, 6L, 0L);
                }
            }
            if (bl) {
                OS.DefWindowProc(this.handle, 11, 1L, 0L);
                OS.SendMessage(this.handle, 11, 0L, 0L);
            }
            if (l3 == l4) {
                TVITEM tVITEM = new TVITEM();
                tVITEM.mask = 24;
                tVITEM.state = 2;
                tVITEM.stateMask = 2;
                tVITEM.hItem = l4;
                OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                this.showItem(l4);
            }
        }
        if ((this.style & 4) != 0) {
            return;
        }
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 24;
        tVITEM.stateMask = 2;
        long l6 = OS.GetWindowLongPtr(this.handle, -4);
        OS.SetWindowLongPtr(this.handle, -4, TreeProc);
        if ((this.style & 0x10000000) != 0) {
            long l7 = OS.SendMessage(this.handle, 4362, 0L, 0L);
            this.setSelection(l7, tVITEM, treeItemArray);
        } else {
            TreeItem[] treeItemArray2 = this.items;
            int n2 = treeItemArray2.length;
            for (int i = 0; i < n2; ++i) {
                int n3;
                TreeItem treeItem2 = treeItem = treeItemArray2[i];
                if (treeItem == null) continue;
                for (n3 = 0; n3 < n && treeItemArray[n3] != treeItem; ++n3) {
                }
                tVITEM.hItem = treeItem.handle;
                OS.SendMessage(this.handle, 4414, 0L, tVITEM);
                if ((tVITEM.state & 2) != 0) {
                    if (n3 != n) continue;
                    tVITEM.state = 0;
                    OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                    continue;
                }
                if (n3 == n) continue;
                this.expandToItem(treeItem);
                tVITEM.state = 2;
                OS.SendMessage(this.handle, 4415, 0L, tVITEM);
            }
        }
        OS.SetWindowLongPtr(this.handle, -4, l6);
    }

    void expandToItem(TreeItem treeItem) {
        TreeItem treeItem2 = treeItem.getParentItem();
        if (treeItem2 != null && !treeItem2.getExpanded()) {
            this.expandToItem(treeItem2);
            treeItem2.setExpanded(true);
            Event event = new Event();
            event.item = treeItem2;
            this.sendEvent(17, event);
        }
    }

    public void setSortColumn(TreeColumn treeColumn) {
        this.checkWidget();
        if (treeColumn != null && treeColumn.isDisposed()) {
            this.error(5);
        }
        if (this.sortColumn != null && !this.sortColumn.isDisposed()) {
            this.sortColumn.setSortDirection(0);
        }
        this.sortColumn = treeColumn;
        if (this.sortColumn != null && this.sortDirection != 0) {
            this.sortColumn.setSortDirection(this.sortDirection);
        }
    }

    public void setSortDirection(int n) {
        this.checkWidget();
        if ((n & 0x480) == 0 && n != 0) {
            return;
        }
        this.sortDirection = n;
        if (this.sortColumn != null && !this.sortColumn.isDisposed()) {
            this.sortColumn.setSortDirection(n);
        }
    }

    public void setTopItem(TreeItem treeItem) {
        long l2;
        long l3;
        this.checkWidget();
        if (treeItem == null) {
            this.error(4);
        }
        if (treeItem.isDisposed()) {
            this.error(5);
        }
        if ((l3 = treeItem.handle) == (l2 = OS.SendMessage(this.handle, 4362, 5L, 0L))) {
            return;
        }
        boolean bl = this.checkScroll(l3);
        boolean bl2 = false;
        if (bl) {
            OS.SendMessage(this.handle, 11, 1L, 0L);
            OS.DefWindowProc(this.handle, 11, 0L, 0L);
        } else {
            boolean bl3 = bl2 = this.getDrawing() && OS.IsWindowVisible(this.handle);
            if (bl2) {
                OS.DefWindowProc(this.handle, 11, 0L, 0L);
            }
        }
        SCROLLINFO sCROLLINFO = null;
        int n = OS.GetWindowLong(this.handle, -16);
        long l4 = OS.SendMessage(this.handle, 4362, 3L, l3);
        if (l4 != 0L && (n & 0xA000) == 0) {
            sCROLLINFO = new SCROLLINFO();
            sCROLLINFO.cbSize = SCROLLINFO.sizeof;
            sCROLLINFO.fMask = 23;
            OS.GetScrollInfo(this.handle, 0, sCROLLINFO);
        }
        OS.SendMessage(this.handle, 4363, 5L, l3);
        if (l4 != 0L) {
            if (sCROLLINFO != null) {
                long l5 = OS.MAKELPARAM(4, sCROLLINFO.nPos);
                OS.SendMessage(this.handle, 276, l5, 0L);
            }
        } else {
            OS.SendMessage(this.handle, 276, 6L, 0L);
        }
        if (bl) {
            OS.DefWindowProc(this.handle, 11, 1L, 0L);
            OS.SendMessage(this.handle, 11, 0L, 0L);
        } else if (bl2) {
            OS.DefWindowProc(this.handle, 11, 1L, 0L);
            OS.InvalidateRect(this.handle, null, true);
        }
        this.updateScrollBar();
    }

    void showItem(long l2) {
        if (OS.SendMessage(this.handle, 4368, 0L, 0L) == 0L) {
            boolean bl = this.checkScroll(l2);
            if (bl) {
                OS.SendMessage(this.handle, 11, 1L, 0L);
                OS.DefWindowProc(this.handle, 11, 0L, 0L);
            }
            OS.SendMessage(this.handle, 4363, 5L, l2);
            OS.SendMessage(this.handle, 276, 6L, 0L);
            if (bl) {
                OS.DefWindowProc(this.handle, 11, 1L, 0L);
                OS.SendMessage(this.handle, 11, 0L, 0L);
            }
        } else {
            boolean bl = true;
            RECT rECT = new RECT();
            if (OS.TreeView_GetItemRect(this.handle, l2, rECT, true)) {
                this.forceResize();
                RECT rECT2 = new RECT();
                OS.GetClientRect(this.handle, rECT2);
                POINT pOINT = new POINT();
                pOINT.x = rECT.left;
                pOINT.y = rECT.top;
                if (OS.PtInRect(rECT2, pOINT)) {
                    pOINT.y = rECT.bottom;
                    if (OS.PtInRect(rECT2, pOINT)) {
                        bl = false;
                    }
                }
            }
            if (bl) {
                boolean bl2 = this.checkScroll(l2);
                if (bl2) {
                    OS.SendMessage(this.handle, 11, 1L, 0L);
                    OS.DefWindowProc(this.handle, 11, 0L, 0L);
                }
                OS.SendMessage(this.handle, 4372, 0L, l2);
                if (bl2) {
                    OS.DefWindowProc(this.handle, 11, 1L, 0L);
                    OS.SendMessage(this.handle, 11, 0L, 0L);
                }
            }
        }
        this.updateScrollBar();
    }

    public void showColumn(TreeColumn treeColumn) {
        this.checkWidget();
        if (treeColumn == null) {
            this.error(4);
        }
        if (treeColumn.isDisposed()) {
            this.error(5);
        }
        if (treeColumn.parent != this) {
            return;
        }
        int n = this.indexOf(treeColumn);
        if (n == -1) {
            return;
        }
        if (0 <= n && n < this.columnCount) {
            this.forceResize();
            RECT rECT = new RECT();
            OS.GetClientRect(this.hwndParent, rECT);
            OS.MapWindowPoints(this.hwndParent, this.handle, rECT, 2);
            RECT rECT2 = new RECT();
            OS.SendMessage(this.hwndHeader, 4615, (long)n, rECT2);
            boolean bl = rECT2.left < rECT.left;
            boolean bl2 = false;
            if (!bl) {
                int n2 = Math.min(rECT.right - rECT.left, rECT2.right - rECT2.left);
                boolean bl3 = bl2 = rECT2.left + n2 > rECT.right;
            }
            if (bl || rECT2.right - rECT2.left > rECT.right - rECT.left) {
                SCROLLINFO sCROLLINFO = new SCROLLINFO();
                sCROLLINFO.cbSize = SCROLLINFO.sizeof;
                sCROLLINFO.fMask = 4;
                sCROLLINFO.nPos = Math.max(0, rECT2.left - 1);
                OS.SetScrollInfo(this.hwndParent, 0, sCROLLINFO, true);
                this.setScrollWidth();
            } else if (bl2) {
                SCROLLINFO sCROLLINFO = new SCROLLINFO();
                sCROLLINFO.cbSize = SCROLLINFO.sizeof;
                sCROLLINFO.fMask = 4;
                int n3 = rECT.right - rECT.left;
                int n4 = rECT2.right - rECT2.left;
                sCROLLINFO.nPos = Math.max(0, n4 + rECT2.left - n3 - 1);
                sCROLLINFO.nPos = Math.min(rECT.right - 1, sCROLLINFO.nPos);
                OS.SetScrollInfo(this.hwndParent, 0, sCROLLINFO, true);
                this.setScrollWidth();
            }
        }
    }

    public void showItem(TreeItem treeItem) {
        this.checkWidget();
        if (treeItem == null) {
            this.error(4);
        }
        if (treeItem.isDisposed()) {
            this.error(5);
        }
        this.showItem(treeItem.handle);
    }

    public void showSelection() {
        this.checkWidget();
        long l2 = 0L;
        if ((this.style & 4) != 0) {
            l2 = OS.SendMessage(this.handle, 4362, 9L, 0L);
            if (l2 == 0L) {
                return;
            }
            int n = (int)OS.SendMessage(this.handle, 4391, l2, 2L);
            if ((n & 2) == 0) {
                return;
            }
        } else {
            long l3 = OS.GetWindowLongPtr(this.handle, -4);
            OS.SetWindowLongPtr(this.handle, -4, TreeProc);
            if ((this.style & 0x10000000) != 0) {
                long l4 = OS.SendMessage(this.handle, 4362, 0L, 0L);
                l2 = this.getNextSelection(l4);
            } else {
                for (int i = 0; i < this.items.length; ++i) {
                    int n;
                    TreeItem treeItem = this.items[i];
                    if (treeItem == null || ((n = (int)OS.SendMessage(this.handle, 4391, treeItem.handle, 2L)) & 2) == 0) continue;
                    l2 = treeItem.handle;
                    break;
                }
            }
            OS.SetWindowLongPtr(this.handle, -4, l3);
        }
        if (l2 != 0L) {
            this.showItem(l2);
        }
    }

    void sort() {
        this.checkWidget();
        if ((this.style & 0x10000000) != 0) {
            return;
        }
        this.sort(-65536L, false);
    }

    void sort(long l2, boolean bl) {
        int n = (int)OS.SendMessage(this.handle, 4357, 0L, 0L);
        if (n == 0 || n == 1) {
            return;
        }
        long l3 = 0L;
        this.hLastIndexOf = 0L;
        this.hFirstIndexOf = 0L;
        n = -1;
        if (this.sortDirection == 128 || this.sortDirection == 0) {
            OS.SendMessage(this.handle, 4371, bl ? 1L : 0L, l2);
        } else {
            Callback callback = new Callback(this, "CompareFunc", 3);
            long l4 = callback.getAddress();
            TVSORTCB tVSORTCB = new TVSORTCB();
            tVSORTCB.hParent = l2;
            tVSORTCB.lpfnCompare = l4;
            tVSORTCB.lParam = this.sortColumn == null ? 0L : (long)this.indexOf(this.sortColumn);
            OS.SendMessage(this.handle, 4373, bl ? 1L : 0L, tVSORTCB);
            callback.dispose();
        }
    }

    @Override
    void subclass() {
        super.subclass();
        if (this.hwndHeader != 0L) {
            OS.SetWindowLongPtr(this.hwndHeader, -4, this.display.windowProc);
        }
    }

    RECT toolTipInset(RECT rECT) {
        RECT rECT2 = new RECT();
        OS.SetRect(rECT2, rECT.left - 1, rECT.top - 1, rECT.right + 1, rECT.bottom + 1);
        return rECT2;
    }

    RECT toolTipRect(RECT rECT) {
        RECT rECT2 = new RECT();
        OS.SetRect(rECT2, rECT.left - 1, rECT.top - 1, rECT.right + 1, rECT.bottom + 1);
        return rECT2;
    }

    /*
     * Exception decompiling
     */
    @Override
    String toolTipText(NMTTDISPINFO var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl149 : ALOAD_0 - null : trying to set 0 previously set to 6
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

    @Override
    long topHandle() {
        return this.hwndParent != 0L ? this.hwndParent : this.handle;
    }

    void updateFullSelection() {
        if ((this.style & 0x10000) != 0) {
            int n;
            int n2 = n = OS.GetWindowLong(this.handle, -16);
            if ((n & 0x1000) != 0) {
                if (!(OS.IsWindowEnabled(this.handle) && this.findImageControl() == null || this.explorerTheme)) {
                    n &= 0xFFFFEFFF;
                }
            } else if (OS.IsWindowEnabled(this.handle) && this.findImageControl() == null && !this.hooks(40) && !this.hooks(42)) {
                n |= 0x1000;
            }
            if (n != n2) {
                OS.SetWindowLong(this.handle, -16, n);
                OS.InvalidateRect(this.handle, null, true);
            }
        }
    }

    void updateHeaderToolTips() {
        if (this.headerToolTipHandle == 0L) {
            return;
        }
        RECT rECT = new RECT();
        TOOLINFO tOOLINFO = new TOOLINFO();
        tOOLINFO.cbSize = TOOLINFO.sizeof;
        tOOLINFO.uFlags = 16;
        tOOLINFO.hwnd = this.hwndHeader;
        tOOLINFO.lpszText = -1L;
        for (int i = 0; i < this.columnCount; ++i) {
            int n;
            TreeColumn treeColumn = this.columns[i];
            if (OS.SendMessage(this.hwndHeader, 4615, (long)i, rECT) == 0L) continue;
            TOOLINFO tOOLINFO2 = tOOLINFO;
            TreeColumn treeColumn2 = treeColumn;
            ++this.display.nextToolTipId;
            treeColumn2.id = n;
            tOOLINFO2.uId = n;
            tOOLINFO.left = rECT.left;
            tOOLINFO.top = rECT.top;
            tOOLINFO.right = rECT.right;
            tOOLINFO.bottom = rECT.bottom;
            OS.SendMessage(this.headerToolTipHandle, 1074, 0L, tOOLINFO);
        }
    }

    void updateImageList() {
        long l2;
        long l3;
        int n;
        if (this.imageList == null) {
            return;
        }
        if (this.hwndHeader == 0L) {
            return;
        }
        int n2 = (int)OS.SendMessage(this.hwndHeader, 4623, 0L, 0L);
        for (n = 0; n < this.items.length; ++n) {
            TreeItem treeItem = this.items[n];
            if (treeItem == null) continue;
            Image image = null;
            if (n2 == 0) {
                image = treeItem.image;
            } else {
                Image[] imageArray = treeItem.images;
                if (imageArray != null) {
                    image = imageArray[n2];
                }
            }
            if (image != null) break;
        }
        if ((l3 = n == this.items.length ? 0L : this.imageList.getHandle()) != (l2 = OS.SendMessage(this.handle, 4360, 0L, 0L))) {
            OS.SendMessage(this.handle, 4361, 0L, l3);
        }
    }

    @Override
    void updateMenuLocation(Event event) {
        Serializable serializable;
        Rectangle rectangle = this.getClientAreaInPixels();
        int n = rectangle.x;
        int n2 = rectangle.y;
        TreeItem treeItem = this.getFocusItem();
        if (treeItem != null) {
            serializable = treeItem.getBoundsInPixels(0);
            if (treeItem.text != null && treeItem.text.length() != 0) {
                serializable = treeItem.getBoundsInPixels();
            }
            n = Math.max(n, serializable.x + serializable.width / 2);
            n = Math.min(n, rectangle.x + rectangle.width);
            n2 = Math.max(n2, serializable.y + serializable.height);
            n2 = Math.min(n2, rectangle.y + rectangle.height);
        }
        serializable = this.toDisplayInPixels(n, n2);
        event.setLocationInPixels(((Point)serializable).x, ((Point)serializable).y);
    }

    @Override
    void updateOrientation() {
        int n;
        super.updateOrientation();
        RECT rECT = new RECT();
        OS.GetWindowRect(this.handle, rECT);
        int n2 = rECT.right - rECT.left;
        int n3 = rECT.bottom - rECT.top;
        OS.SetWindowPos(this.handle, 0L, 0, 0, n2 - 1, n3 - 1, 6);
        OS.SetWindowPos(this.handle, 0L, 0, 0, n2, n3, 6);
        if (this.hwndParent != 0L) {
            n = OS.GetWindowLong(this.hwndParent, -20);
            n = (this.style & 0x4000000) != 0 ? (n |= 0x400000) : (n &= 0xFFBFFFFF);
            OS.SetWindowLong(this.hwndParent, -20, n &= 0xFFFFDFFF);
            rECT = new RECT();
            OS.GetWindowRect(this.hwndParent, rECT);
            n2 = rECT.right - rECT.left;
            n3 = rECT.bottom - rECT.top;
            OS.SetWindowPos(this.hwndParent, 0L, 0, 0, n2 - 1, n3 - 1, 6);
            OS.SetWindowPos(this.hwndParent, 0L, 0, 0, n2, n3, 6);
        }
        if (this.hwndHeader != 0L) {
            n = OS.GetWindowLong(this.hwndHeader, -20);
            n = (this.style & 0x4000000) != 0 ? (n |= 0x400000) : (n &= 0xFFBFFFFF);
            OS.SetWindowLong(this.hwndHeader, -20, n);
            OS.InvalidateRect(this.hwndHeader, null, true);
        }
        if ((this.style & 0x20) != 0) {
            this.setCheckboxImageList();
        }
        if (this.imageList != null) {
            Point point = this.imageList.getImageSize();
            this.display.releaseImageList(this.imageList);
            this.imageList = this.display.getImageList(this.style & 0x4000000, point.x, point.y);
            for (TreeItem object : this.items) {
                int n4;
                Image image;
                if (object == null || (image = object.image) == null || (n4 = this.imageList.indexOf(image)) != -1) continue;
                this.imageList.add(image);
            }
            long l2 = this.imageList.getHandle();
            OS.SendMessage(this.handle, 4361, 0L, l2);
        }
        if (this.hwndHeader != 0L && this.headerImageList != null) {
            Point point = this.headerImageList.getImageSize();
            this.display.releaseImageList(this.headerImageList);
            this.headerImageList = this.display.getImageList(this.style & 0x4000000, point.x, point.y);
            if (this.columns != null) {
                for (int i = 0; i < this.columns.length; ++i) {
                    Image image;
                    TreeColumn treeColumn = this.columns[i];
                    if (treeColumn == null || (image = treeColumn.image) == null) continue;
                    HDITEM hDITEM = new HDITEM();
                    hDITEM.mask = 4;
                    OS.SendMessage(this.hwndHeader, 4619, (long)i, hDITEM);
                    if ((hDITEM.fmt & 0x800) == 0) continue;
                    int n5 = this.headerImageList.indexOf(image);
                    if (n5 == -1) {
                        n5 = this.headerImageList.add(image);
                    }
                    hDITEM.mask = 32;
                    hDITEM.iImage = n5;
                    OS.SendMessage(this.hwndHeader, 4620, (long)i, hDITEM);
                }
            }
            long l3 = this.headerImageList.getHandle();
            OS.SendMessage(this.hwndHeader, 4616, 0L, l3);
        }
    }

    void updateScrollBar() {
        if (this.hwndParent != 0L && (this.columnCount != 0 || this.scrollWidth != 0)) {
            SCROLLINFO sCROLLINFO = new SCROLLINFO();
            sCROLLINFO.cbSize = SCROLLINFO.sizeof;
            sCROLLINFO.fMask = 23;
            int n = (int)OS.SendMessage(this.handle, 4357, 0L, 0L);
            if (n == 0) {
                OS.GetScrollInfo(this.hwndParent, 1, sCROLLINFO);
                sCROLLINFO.nPage = sCROLLINFO.nMax + 1;
                OS.SetScrollInfo(this.hwndParent, 1, sCROLLINFO, true);
            } else {
                OS.GetScrollInfo(this.handle, 1, sCROLLINFO);
                if (sCROLLINFO.nPage == 0) {
                    SCROLLBARINFO sCROLLBARINFO = new SCROLLBARINFO();
                    sCROLLBARINFO.cbSize = SCROLLBARINFO.sizeof;
                    OS.GetScrollBarInfo(this.handle, -5, sCROLLBARINFO);
                    if ((sCROLLBARINFO.rgstate[0] & 0x8000) != 0) {
                        sCROLLINFO.nPage = sCROLLINFO.nMax + 1;
                    }
                }
                OS.SetScrollInfo(this.hwndParent, 1, sCROLLINFO, true);
            }
        }
    }

    @Override
    void unsubclass() {
        super.unsubclass();
        if (this.hwndHeader != 0L) {
            OS.SetWindowLongPtr(this.hwndHeader, -4, HeaderProc);
        }
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle() | 0x20 | 4 | 1 | 0x4000;
        if (OS.IsAppThemed()) {
            n |= 0x200;
            if ((this.style & 0x10000) != 0) {
                n |= 0x1000;
            }
        } else {
            n = (this.style & 0x10000) != 0 ? (n |= 0x1000) : (n |= 2);
        }
        if ((this.style & 0x300) == 0) {
            n &= 0xFFCFFFFF;
            n |= 0x2000;
        } else if ((this.style & 0x100) == 0) {
            n &= 0xFFEFFFFF;
            n |= 0x8000;
        }
        return n | 0x10;
    }

    @Override
    TCHAR windowClass() {
        return TreeClass;
    }

    @Override
    long windowProc() {
        return TreeProc;
    }

    @Override
    long windowProc(long l2, int n, long l3, long l4) {
        long l5;
        if (this.hwndHeader != 0L && l2 == this.hwndHeader) {
            switch (n) {
                case 123: {
                    LRESULT lRESULT = this.wmContextMenu(l2, l3, l4);
                    if (lRESULT == null) break;
                    return lRESULT.value;
                }
                case 675: {
                    this.updateHeaderToolTips();
                    this.updateHeaderToolTips();
                    break;
                }
                case 78: {
                    NMHDR nMHDR = new NMHDR();
                    OS.MoveMemory(nMHDR, l4, NMHDR.sizeof);
                    switch (nMHDR.code) {
                        case -530: 
                        case -522: 
                        case -521: {
                            return OS.SendMessage(this.handle, n, l3, l4);
                        }
                    }
                    break;
                }
                case 32: {
                    short s;
                    if (l3 != l2 || (s = (short)OS.LOWORD(l4)) != 1) break;
                    HDHITTESTINFO hDHITTESTINFO = new HDHITTESTINFO();
                    int n2 = OS.GetMessagePos();
                    POINT pOINT = new POINT();
                    OS.POINTSTOPOINT(pOINT, n2);
                    OS.ScreenToClient(l2, pOINT);
                    hDHITTESTINFO.x = pOINT.x;
                    hDHITTESTINFO.y = pOINT.y;
                    int n3 = (int)OS.SendMessage(this.hwndHeader, 4614, 0L, hDHITTESTINFO);
                    if (0 > n3 || n3 >= this.columnCount || this.columns[n3].resizable || (hDHITTESTINFO.flags & 0xC) == 0) break;
                    OS.SetCursor(OS.LoadCursor(0L, 32512L));
                    return 1L;
                }
            }
            return this.callWindowProc(l2, n, l3, l4);
        }
        if (this.hwndParent != 0L && l2 == this.hwndParent) {
            switch (n) {
                case 3: {
                    this.sendEvent(10);
                    return 0L;
                }
                case 5: {
                    this.setScrollWidth();
                    if (this.ignoreResize) {
                        return 0L;
                    }
                    this.setResizeChildren(false);
                    long l6 = this.callWindowProc(l2, 5, l3, l4);
                    this.sendEvent(11);
                    if (this.isDisposed()) {
                        return 0L;
                    }
                    if (this.layout != null) {
                        this.markLayout(false, false);
                        this.updateLayout(false, false);
                    }
                    this.setResizeChildren(true);
                    this.updateScrollBar();
                    return l6;
                }
                case 133: {
                    LRESULT lRESULT = this.wmNCPaint(l2, l3, l4);
                    if (lRESULT == null) break;
                    return lRESULT.value;
                }
                case 791: {
                    LRESULT lRESULT = this.wmPrint(l2, l3, l4);
                    if (lRESULT == null) break;
                    return lRESULT.value;
                }
                case 21: 
                case 78: 
                case 273: {
                    return OS.SendMessage(this.handle, n, l3, l4);
                }
                case 276: {
                    if (this.horizontalBar != null && (l4 == 0L || l4 == this.hwndParent)) {
                        this.wmScroll(this.horizontalBar, true, this.hwndParent, 276, l3, l4);
                    }
                    this.setScrollWidth();
                    break;
                }
                case 277: {
                    SCROLLINFO sCROLLINFO = new SCROLLINFO();
                    sCROLLINFO.cbSize = SCROLLINFO.sizeof;
                    sCROLLINFO.fMask = 23;
                    OS.GetScrollInfo(this.hwndParent, 1, sCROLLINFO);
                    if (OS.LOWORD(l3) == 5) {
                        sCROLLINFO.nPos = sCROLLINFO.nTrackPos;
                    }
                    OS.SetScrollInfo(this.handle, 1, sCROLLINFO, true);
                    long l7 = OS.SendMessage(this.handle, 277, l3, l4);
                    OS.GetScrollInfo(this.handle, 1, sCROLLINFO);
                    OS.SetScrollInfo(this.hwndParent, 1, sCROLLINFO, true);
                    return l7;
                }
            }
            return this.callWindowProc(l2, n, l3, l4);
        }
        if (n != Display.DI_GETDRAGIMAGE || (this.style & 2) == 0 && !this.hooks(40) && !this.hooks(42)) {
            return super.windowProc(l2, n, l3, l4);
        }
        long l8 = OS.SendMessage(this.handle, 4362, 5L, 0L);
        TreeItem[] treeItemArray = new TreeItem[10];
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 28;
        int n4 = this.getSelection(l8, tVITEM, treeItemArray, 0, 10, false, true);
        if (n4 == 0) {
            return 0L;
        }
        POINT pOINT = new POINT();
        OS.POINTSTOPOINT(pOINT, OS.GetMessagePos());
        OS.MapWindowPoints(0L, this.handle, pOINT, 1);
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        RECT rECT2 = treeItemArray[0].getBounds(0, true, true, false);
        if ((this.style & 0x10000) != 0) {
            int n5 = 301;
            rECT2.left = Math.max(rECT.left, pOINT.x - 150);
            if (rECT.right > rECT2.left + 301) {
                rECT2.right = rECT2.left + 301;
            } else {
                rECT2.right = rECT.right;
                rECT2.left = Math.max(rECT.left, rECT2.right - 301);
            }
        } else {
            rECT2.left = Math.max(rECT2.left, rECT.left);
            rECT2.right = Math.min(rECT2.right, rECT.right);
        }
        long l9 = OS.CreateRectRgn(rECT2.left, rECT2.top, rECT2.right, rECT2.bottom);
        for (int i = 1; i < n4 && rECT2.bottom - rECT2.top <= 301 && rECT2.bottom <= rECT.bottom; ++i) {
            RECT rECT3 = treeItemArray[i].getBounds(0, true, true, false);
            if ((this.style & 0x10000) != 0) {
                rECT3.left = rECT2.left;
                rECT3.right = rECT2.right;
            } else {
                rECT3.left = Math.max(rECT3.left, rECT.left);
                rECT3.right = Math.min(rECT3.right, rECT.right);
            }
            l5 = OS.CreateRectRgn(rECT3.left, rECT3.top, rECT3.right, rECT3.bottom);
            OS.CombineRgn(l9, l9, l5, 2);
            OS.DeleteObject(l5);
            rECT2.bottom = rECT3.bottom;
        }
        OS.GetRgnBox(l9, rECT2);
        long l10 = OS.GetDC(this.handle);
        l5 = OS.CreateCompatibleDC(l10);
        BITMAPINFOHEADER bITMAPINFOHEADER = new BITMAPINFOHEADER();
        bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
        bITMAPINFOHEADER.biWidth = rECT2.right - rECT2.left;
        bITMAPINFOHEADER.biHeight = -(rECT2.bottom - rECT2.top);
        bITMAPINFOHEADER.biPlanes = 1;
        bITMAPINFOHEADER.biBitCount = (short)32;
        bITMAPINFOHEADER.biCompression = 0;
        byte[] byArray = new byte[BITMAPINFOHEADER.sizeof];
        OS.MoveMemory(byArray, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
        long[] lArray = new long[]{0L};
        long l11 = OS.CreateDIBSection(0L, byArray, 0, lArray, 0L, 0);
        if (l11 == 0L) {
            this.error(2);
        }
        long l12 = OS.SelectObject(l5, l11);
        int n6 = 253;
        POINT pOINT2 = new POINT();
        OS.SetWindowOrgEx(l5, rECT2.left, rECT2.top, pOINT2);
        OS.FillRect(l5, rECT2, this.findBrush(253L, 0));
        OS.OffsetRgn(l9, -rECT2.left, -rECT2.top);
        OS.SelectClipRgn(l5, l9);
        OS.PrintWindow(this.handle, l5, 0);
        OS.SetWindowOrgEx(l5, pOINT2.x, pOINT2.y, null);
        OS.SelectObject(l5, l12);
        OS.DeleteDC(l5);
        OS.ReleaseDC(0L, l10);
        OS.DeleteObject(l9);
        SHDRAGIMAGE sHDRAGIMAGE = new SHDRAGIMAGE();
        sHDRAGIMAGE.hbmpDragImage = l11;
        sHDRAGIMAGE.crColorKey = 253;
        sHDRAGIMAGE.sizeDragImage.cx = bITMAPINFOHEADER.biWidth;
        sHDRAGIMAGE.sizeDragImage.cy = -bITMAPINFOHEADER.biHeight;
        sHDRAGIMAGE.ptOffset.x = pOINT.x - rECT2.left;
        sHDRAGIMAGE.ptOffset.y = pOINT.y - rECT2.top;
        if ((this.style & 0x8000000) != 0) {
            sHDRAGIMAGE.ptOffset.x = sHDRAGIMAGE.sizeDragImage.cx - sHDRAGIMAGE.ptOffset.x;
        }
        OS.MoveMemory(l4, sHDRAGIMAGE, SHDRAGIMAGE.sizeof);
        return 1L;
    }

    @Override
    LRESULT WM_CHAR(long l2, long l3) {
        LRESULT lRESULT = super.WM_CHAR(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        switch ((int)l2) {
            case 32: {
                long l4 = OS.SendMessage(this.handle, 4362, 9L, 0L);
                if (l4 != 0L) {
                    Object object;
                    this.hAnchor = l4;
                    OS.SendMessage(this.handle, 4372, 0L, l4);
                    TVITEM tVITEM = new TVITEM();
                    tVITEM.mask = 28;
                    tVITEM.hItem = l4;
                    if ((this.style & 0x20) != 0) {
                        tVITEM.stateMask = 61440;
                        OS.SendMessage(this.handle, 4414, 0L, tVITEM);
                        int n = tVITEM.state >> 12;
                        n = (n & 1) != 0 ? ++n : --n;
                        tVITEM.state = n << 12;
                        OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                        long l5 = OS.SendMessage(this.handle, 4395, l4, 0L);
                        OS.NotifyWinEvent(32773, this.handle, -4, (int)l5);
                    }
                    tVITEM.stateMask = 2;
                    OS.SendMessage(this.handle, 4414, 0L, tVITEM);
                    if ((this.style & 2) != 0 && OS.GetKeyState(17) < 0) {
                        if ((tVITEM.state & 2) != 0) {
                            object = tVITEM;
                            ((TVITEM)object).state &= 0xFFFFFFFD;
                        } else {
                            object = tVITEM;
                            ((TVITEM)object).state |= 2;
                        }
                    } else {
                        object = tVITEM;
                        ((TVITEM)object).state |= 2;
                    }
                    OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                    object = this._getItem(l4, (int)tVITEM.lParam);
                    Event event = new Event();
                    event.item = object;
                    this.sendSelectionEvent(13, event, false);
                    if ((this.style & 0x20) != 0) {
                        event = new Event();
                        event.item = object;
                        event.detail = 32;
                        this.sendSelectionEvent(13, event, false);
                    }
                }
                return LRESULT.ZERO;
            }
            case 13: {
                Event event = new Event();
                long l6 = OS.SendMessage(this.handle, 4362, 9L, 0L);
                if (l6 != 0L) {
                    event.item = this._getItem(l6);
                }
                this.sendSelectionEvent(14, event, false);
                return LRESULT.ZERO;
            }
            case 27: {
                return LRESULT.ZERO;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_ERASEBKGND(long l2, long l3) {
        LRESULT lRESULT = super.WM_ERASEBKGND(l2, l3);
        if ((this.style & 0x20000000) != 0) {
            return LRESULT.ONE;
        }
        if (this.findImageControl() != null) {
            return LRESULT.ONE;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_GETOBJECT(long l2, long l3) {
        if (((this.style & 0x20) != 0 || this.hwndParent != 0L) && this.accessible == null) {
            this.accessible = this.new_Accessible(this);
        }
        return super.WM_GETOBJECT(l2, l3);
    }

    @Override
    LRESULT WM_HSCROLL(long l2, long l3) {
        boolean bl = false;
        if ((this.style & 0x20000000) != 0) {
            boolean bl2 = bl = (this.style & 0x10000000) != 0 || this.hooks(40) || this.hooks(42);
        }
        if (bl) {
            this.style &= 0xDFFFFFFF;
            if (this.explorerTheme) {
                OS.SendMessage(this.handle, 4396, 4L, 0L);
            }
        }
        LRESULT lRESULT = super.WM_HSCROLL(l2, l3);
        if (bl) {
            this.style |= 0x20000000;
            if (this.explorerTheme) {
                OS.SendMessage(this.handle, 4396, 4L, 4L);
            }
        }
        if (lRESULT != null) {
            return lRESULT;
        }
        return lRESULT;
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
            case 32: {
                return LRESULT.ZERO;
            }
            case 107: {
                if (OS.GetKeyState(17) >= 0 || this.hwndHeader == 0L) break;
                TreeColumn[] treeColumnArray = new TreeColumn[this.columnCount];
                System.arraycopy(this.columns, 0, treeColumnArray, 0, this.columnCount);
                for (int i = 0; i < this.columnCount; ++i) {
                    TreeColumn treeColumn = treeColumnArray[i];
                    if (treeColumn.isDisposed() || !treeColumn.getResizable()) continue;
                    treeColumn.pack();
                }
                break;
            }
            case 33: 
            case 34: 
            case 35: 
            case 36: 
            case 38: 
            case 40: {
                long l5;
                OS.SendMessage(this.handle, 295, 3L, 0L);
                if (this.itemToolTipHandle != 0L) {
                    OS.ShowWindow(this.itemToolTipHandle, 0);
                }
                if ((this.style & 4) != 0) break;
                if (OS.GetKeyState(16) < 0 && (l5 = OS.SendMessage(this.handle, 4362, 9L, 0L)) != 0L) {
                    int n;
                    int n2;
                    if (this.hAnchor == 0L) {
                        this.hAnchor = l5;
                    }
                    boolean bl = true;
                    this.ignoreDeselect = true;
                    this.ignoreSelect = true;
                    long l6 = this.callWindowProc(this.handle, 256, l2, l3);
                    boolean bl3 = false;
                    this.ignoreDeselect = false;
                    this.ignoreSelect = false;
                    long l7 = OS.SendMessage(this.handle, 4362, 9L, 0L);
                    TVITEM tVITEM = new TVITEM();
                    tVITEM.mask = 24;
                    tVITEM.stateMask = 2;
                    long l8 = l5;
                    RECT rECT = new RECT();
                    if (!OS.TreeView_GetItemRect(this.handle, this.hAnchor, rECT, false)) {
                        this.hAnchor = l5;
                        OS.TreeView_GetItemRect(this.handle, this.hAnchor, rECT, false);
                    }
                    RECT rECT2 = new RECT();
                    OS.TreeView_GetItemRect(this.handle, l8, rECT2, false);
                    int n3 = n2 = rECT.top < rECT2.top ? 7 : 6;
                    while (l8 != this.hAnchor) {
                        tVITEM.hItem = l8;
                        OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                        l8 = OS.SendMessage(this.handle, 4362, (long)n2, l8);
                    }
                    long l9 = this.hAnchor;
                    OS.TreeView_GetItemRect(this.handle, l7, rECT, false);
                    OS.TreeView_GetItemRect(this.handle, l9, rECT2, false);
                    tVITEM.state = 2;
                    int n4 = n = rECT.top < rECT2.top ? 7 : 6;
                    while (l9 != l7) {
                        tVITEM.hItem = l9;
                        OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                        l9 = OS.SendMessage(this.handle, 4362, (long)n, l9);
                    }
                    tVITEM.hItem = l7;
                    OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                    tVITEM.mask = 20;
                    tVITEM.hItem = l7;
                    OS.SendMessage(this.handle, 4414, 0L, tVITEM);
                    Event event = new Event();
                    event.item = this._getItem(l7, (int)tVITEM.lParam);
                    this.sendSelectionEvent(13, event, false);
                    return new LRESULT(l6);
                }
                if (OS.GetKeyState(17) < 0 && (l5 = OS.SendMessage(this.handle, 4362, 9L, 0L)) != 0L) {
                    TVITEM tVITEM = new TVITEM();
                    tVITEM.mask = 24;
                    tVITEM.stateMask = 2;
                    tVITEM.hItem = l5;
                    OS.SendMessage(this.handle, 4414, 0L, tVITEM);
                    boolean bl = (tVITEM.state & 2) != 0;
                    long l10 = 0L;
                    block6 : switch ((int)l2) {
                        case 38: {
                            l10 = OS.SendMessage(this.handle, 4362, 7L, l5);
                            break;
                        }
                        case 40: {
                            l10 = OS.SendMessage(this.handle, 4362, 6L, l5);
                            break;
                        }
                        case 36: {
                            l10 = OS.SendMessage(this.handle, 4362, 0L, 0L);
                            break;
                        }
                        case 33: {
                            l10 = OS.SendMessage(this.handle, 4362, 5L, 0L);
                            if (l10 != l5) break;
                            OS.SendMessage(this.handle, 277, 2L, 0L);
                            l10 = OS.SendMessage(this.handle, 4362, 5L, 0L);
                            break;
                        }
                        case 34: {
                            long l11;
                            RECT rECT = new RECT();
                            RECT rECT3 = new RECT();
                            OS.GetClientRect(this.handle, rECT3);
                            l10 = OS.SendMessage(this.handle, 4362, 5L, 0L);
                            while ((l11 = OS.SendMessage(this.handle, 4362, 6L, l10)) != 0L && OS.TreeView_GetItemRect(this.handle, l11, rECT, false) && rECT.bottom <= rECT3.bottom) {
                                l10 = l11;
                                if (l10 == l5) {
                                    OS.SendMessage(this.handle, 277, 3L, 0L);
                                }
                                if (l10 != 0L) continue;
                                break block6;
                            }
                            break;
                        }
                        case 35: {
                            l10 = OS.SendMessage(this.handle, 4362, 10L, 0L);
                        }
                    }
                    if (l10 != 0L) {
                        boolean bl4;
                        OS.SendMessage(this.handle, 4372, 0L, l10);
                        tVITEM.hItem = l10;
                        OS.SendMessage(this.handle, 4414, 0L, tVITEM);
                        boolean bl5 = (tVITEM.state & 2) != 0;
                        boolean bl6 = bl4 = !bl5 && this.getDrawing() && OS.IsWindowVisible(this.handle);
                        if (bl4) {
                            OS.UpdateWindow(this.handle);
                            OS.DefWindowProc(this.handle, 11, 0L, 0L);
                        }
                        this.hSelect = l10;
                        this.ignoreSelect = true;
                        OS.SendMessage(this.handle, 4363, 9L, l10);
                        this.ignoreSelect = false;
                        this.hSelect = 0L;
                        if (bl) {
                            tVITEM.state = 2;
                            tVITEM.hItem = l5;
                            OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                        }
                        if (!bl5) {
                            tVITEM.state = 0;
                            tVITEM.hItem = l10;
                            OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                        }
                        if (bl4) {
                            RECT rECT = new RECT();
                            RECT rECT4 = new RECT();
                            OS.TreeView_GetItemRect(this.handle, l5, rECT, false);
                            OS.TreeView_GetItemRect(this.handle, l10, rECT4, false);
                            OS.DefWindowProc(this.handle, 11, 1L, 0L);
                            OS.InvalidateRect(this.handle, rECT, true);
                            OS.InvalidateRect(this.handle, rECT4, true);
                            OS.UpdateWindow(this.handle);
                        }
                        return LRESULT.ZERO;
                    }
                }
                l5 = this.callWindowProc(this.handle, 256, l2, l3);
                this.hAnchor = OS.SendMessage(this.handle, 4362, 9L, 0L);
                return new LRESULT(l5);
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_KILLFOCUS(long l2, long l3) {
        int n;
        boolean bl;
        boolean bl2 = bl = (this.style & 2) != 0;
        if (!bl && this.imageList != null && ((n = OS.GetWindowLong(this.handle, -16)) & 0x1000) == 0) {
            bl = true;
        }
        if (bl) {
            this.redrawSelection();
        }
        return super.WM_KILLFOCUS(l2, l3);
    }

    /*
     * Exception decompiling
     */
    @Override
    LRESULT WM_LBUTTONDBLCLK(long var1, long var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl231 : ALOAD - null : trying to set 3 previously set to 0
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

    @Override
    LRESULT WM_LBUTTONDOWN(long l2, long l3) {
        TVHITTESTINFO tVHITTESTINFO = new TVHITTESTINFO();
        tVHITTESTINFO.x = OS.GET_X_LPARAM(l3);
        tVHITTESTINFO.y = OS.GET_Y_LPARAM(l3);
        OS.SendMessage(this.handle, 4369, 0L, tVHITTESTINFO);
        if (tVHITTESTINFO.hItem == 0L || (tVHITTESTINFO.flags & 0x10) != 0) {
            RECT rECT;
            long l4;
            int n;
            long l5;
            Display display = this.display;
            display.captureChanged = false;
            if (!this.sendMouseEvent(3, 1, this.handle, l3)) {
                if (!display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
                    OS.SetCapture(this.handle);
                }
                return LRESULT.ZERO;
            }
            boolean bl = false;
            boolean bl2 = false;
            long l6 = OS.SendMessage(this.handle, 4362, 9L, 0L);
            if (tVHITTESTINFO.hItem != 0L && (this.style & 2) != 0 && l6 != 0L) {
                TVITEM tVITEM = new TVITEM();
                tVITEM.mask = 24;
                tVITEM.hItem = tVHITTESTINFO.hItem;
                OS.SendMessage(this.handle, 4414, 0L, tVITEM);
                if ((tVITEM.state & 0x20) != 0) {
                    bl = true;
                    tVITEM.stateMask = 2;
                    long l7 = OS.SendMessage(this.handle, 4362, 6L, tVHITTESTINFO.hItem);
                    while (l7 != 0L) {
                        long l8;
                        if (l7 == this.hAnchor) {
                            this.hAnchor = 0L;
                        }
                        tVITEM.hItem = l7;
                        OS.SendMessage(this.handle, 4414, 0L, tVITEM);
                        if ((tVITEM.state & 2) != 0) {
                            bl2 = true;
                        }
                        tVITEM.state = 0;
                        OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                        l7 = l8 = OS.SendMessage(this.handle, 4362, 6L, l7);
                        while (l8 != 0L && l8 != tVHITTESTINFO.hItem) {
                            l8 = OS.SendMessage(this.handle, 4362, 3L, l8);
                        }
                        if (l8 != 0L) continue;
                        break;
                    }
                }
            }
            boolean bl3 = false;
            this.gestureCompleted = false;
            this.dragStarted = false;
            if (bl) {
                this.hSelect = tVHITTESTINFO.hItem;
                boolean bl4 = true;
                this.lockSelection = true;
                this.ignoreSelect = true;
                this.ignoreDeselect = true;
            }
            long l9 = this.callWindowProc(this.handle, 513, l2, l3);
            if (OS.GetFocus() != this.handle) {
                OS.SetFocus(this.handle);
            }
            if (bl) {
                this.hSelect = 0L;
                boolean bl5 = false;
                this.lockSelection = false;
                this.ignoreSelect = false;
                this.ignoreDeselect = false;
            }
            if (l6 != (l5 = OS.SendMessage(this.handle, 4362, 9L, 0L))) {
                this.hAnchor = l5;
            }
            if (this.dragStarted && !display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
                OS.SetCapture(this.handle);
            }
            if ((tVHITTESTINFO.flags & 0x10) != 0 && ((n = OS.GetWindowLong(this.handle, -16)) & 0x1000) == 0 && OS.SendMessage(this.handle, 4360, 0L, 0L) == 0L && (l4 = OS.SendMessage(this.handle, 4362, 9L, 0L)) != 0L && OS.TreeView_GetItemRect(this.handle, l4, rECT = new RECT(), false)) {
                OS.InvalidateRect(this.handle, rECT, true);
            }
            if (bl2) {
                Event event = new Event();
                event.item = this._getItem(tVHITTESTINFO.hItem);
                this.sendSelectionEvent(13, event, false);
            }
            return new LRESULT(l9);
        }
        if ((this.style & 0x20) != 0 && (tVHITTESTINFO.flags & 0x40) != 0) {
            Display display = this.display;
            display.captureChanged = false;
            if (!this.sendMouseEvent(3, 1, this.handle, l3)) {
                if (!display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
                    OS.SetCapture(this.handle);
                }
                return LRESULT.ZERO;
            }
            if (!display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
                OS.SetCapture(this.handle);
            }
            OS.SetFocus(this.handle);
            TVITEM tVITEM = new TVITEM();
            tVITEM.hItem = tVHITTESTINFO.hItem;
            tVITEM.mask = 28;
            tVITEM.stateMask = 61440;
            OS.SendMessage(this.handle, 4414, 0L, tVITEM);
            int n = tVITEM.state >> 12;
            n = (n & 1) != 0 ? ++n : --n;
            tVITEM.state = n << 12;
            OS.SendMessage(this.handle, 4415, 0L, tVITEM);
            long l10 = OS.SendMessage(this.handle, 4395, tVITEM.hItem, 0L);
            OS.NotifyWinEvent(32773, this.handle, -4, (int)l10);
            Event event = new Event();
            event.item = this._getItem(tVITEM.hItem, (int)tVITEM.lParam);
            event.detail = 32;
            this.sendSelectionEvent(13, event, false);
            return LRESULT.ZERO;
        }
        boolean bl = false;
        boolean bl6 = false;
        if (tVHITTESTINFO.hItem != 0L) {
            if ((this.style & 0x10000) != 0) {
                int n = OS.GetWindowLong(this.handle, -16);
                if ((n & 0x1000) == 0) {
                    bl6 = true;
                }
            } else if (this.hooks(41) && (bl = this.hitTestSelection(tVHITTESTINFO.hItem, tVHITTESTINFO.x, tVHITTESTINFO.y)) && (tVHITTESTINFO.flags & 0x46) == 0) {
                bl6 = true;
            }
        }
        if (!bl && (this.style & 0x10000) == 0 && (tVHITTESTINFO.flags & 0x46) == 0) {
            Display display = this.display;
            display.captureChanged = false;
            if (!this.sendMouseEvent(3, 1, this.handle, l3)) {
                if (!display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
                    OS.SetCapture(this.handle);
                }
                return LRESULT.ZERO;
            }
            long l11 = this.callWindowProc(this.handle, 513, l2, l3);
            if (OS.GetFocus() != this.handle) {
                OS.SetFocus(this.handle);
            }
            if (!display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
                OS.SetCapture(this.handle);
            }
            return new LRESULT(l11);
        }
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 24;
        tVITEM.stateMask = 2;
        boolean bl7 = false;
        if ((this.style & 2) != 0) {
            tVITEM.hItem = tVHITTESTINFO.hItem;
            OS.SendMessage(this.handle, 4414, 0L, tVITEM);
            bl7 = (tVITEM.state & 2) != 0;
        }
        long l12 = OS.SendMessage(this.handle, 4362, 9L, 0L);
        if ((this.style & 2) != 0) {
            tVITEM.hItem = l12;
            OS.SendMessage(this.handle, 4414, 0L, tVITEM);
            if (bl7 || (l2 & 8L) != 0L) {
                int n = (int)OS.SendMessage(this.handle, 297, 0L, 0L);
                if ((n & 1) != 0) {
                    OS.SendMessage(this.handle, 295, 3L, 0L);
                }
                OS.UpdateWindow(this.handle);
                OS.DefWindowProc(this.handle, 11, 0L, 0L);
            } else {
                this.deselectAll();
            }
        }
        Display display = this.display;
        display.captureChanged = false;
        if (!this.sendMouseEvent(3, 1, this.handle, l3)) {
            if (!display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
                OS.SetCapture(this.handle);
            }
            return LRESULT.ZERO;
        }
        this.hSelect = tVHITTESTINFO.hItem;
        boolean bl8 = false;
        this.gestureCompleted = false;
        this.dragStarted = false;
        boolean bl9 = true;
        this.ignoreSelect = true;
        this.ignoreDeselect = true;
        long l13 = this.callWindowProc(this.handle, 513, l2, l3);
        if (OS.GetFocus() != this.handle) {
            OS.SetFocus(this.handle);
        }
        long l14 = OS.SendMessage(this.handle, 4362, 9L, 0L);
        if (bl6) {
            if (l12 == 0L || l14 == l12 && tVHITTESTINFO.hItem != l12) {
                OS.SendMessage(this.handle, 4363, 9L, tVHITTESTINFO.hItem);
                l14 = OS.SendMessage(this.handle, 4362, 9L, 0L);
            }
            if (!this.dragStarted && (this.state & 0x8000) != 0 && this.hooks(29)) {
                this.dragStarted = this.dragDetect(this.handle, tVHITTESTINFO.x, tVHITTESTINFO.y, false, null, null);
            }
        }
        boolean bl10 = false;
        this.ignoreSelect = false;
        this.ignoreDeselect = false;
        this.hSelect = 0L;
        if (this.dragStarted && !display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
            OS.SetCapture(this.handle);
        }
        if ((this.style & 4) != 0 && l12 == l14) {
            tVITEM.mask = 24;
            tVITEM.state = 2;
            tVITEM.stateMask = 2;
            tVITEM.hItem = l14;
            OS.SendMessage(this.handle, 4415, 0L, tVITEM);
        }
        if ((this.style & 2) != 0) {
            if (bl7 || (l2 & 8L) != 0L) {
                Object object;
                if (l12 == l14 && l12 == tVHITTESTINFO.hItem) {
                    if ((l2 & 8L) != 0L) {
                        object = tVITEM;
                        ((TVITEM)object).state ^= 2;
                        if (this.dragStarted) {
                            tVITEM.state = 2;
                        }
                        OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                    }
                } else {
                    if ((tVITEM.state & 2) != 0) {
                        tVITEM.state = 2;
                        OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                    }
                    if ((l2 & 8L) != 0L && !this.dragStarted && bl7) {
                        tVITEM.state = 0;
                        tVITEM.hItem = tVHITTESTINFO.hItem;
                        OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                    }
                }
                object = new RECT();
                RECT rECT = new RECT();
                OS.TreeView_GetItemRect(this.handle, l12, (RECT)object, false);
                OS.TreeView_GetItemRect(this.handle, l14, rECT, false);
                OS.DefWindowProc(this.handle, 11, 1L, 0L);
                OS.InvalidateRect(this.handle, (RECT)object, true);
                OS.InvalidateRect(this.handle, rECT, true);
                OS.UpdateWindow(this.handle);
            }
            if (!((l2 & 8L) != 0L || bl7 && this.dragStarted)) {
                tVITEM.state = 0;
                long l15 = OS.GetWindowLongPtr(this.handle, -4);
                OS.SetWindowLongPtr(this.handle, -4, TreeProc);
                if ((this.style & 0x10000000) != 0) {
                    long l16 = OS.SendMessage(this.handle, 4362, 0L, 0L);
                    this.deselect(l16, tVITEM, l14);
                } else {
                    for (TreeItem object : this.items) {
                        if (object == null || object.handle == l14) continue;
                        tVITEM.hItem = object.handle;
                        OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                    }
                }
                tVITEM.hItem = l14;
                tVITEM.state = 2;
                OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                OS.SetWindowLongPtr(this.handle, -4, l15);
                if ((l2 & 4L) != 0L) {
                    RECT rECT;
                    RECT rECT2 = new RECT();
                    if (this.hAnchor == 0L) {
                        this.hAnchor = l14;
                    }
                    if (OS.TreeView_GetItemRect(this.handle, this.hAnchor, rECT2, false) && OS.TreeView_GetItemRect(this.handle, l14, rECT = new RECT(), false)) {
                        long l4;
                        int n = rECT2.top < rECT.top ? 6 : 7;
                        tVITEM.state = 2;
                        TVITEM tVITEM2 = tVITEM;
                        tVITEM2.hItem = l4 = this.hAnchor;
                        long l5 = l4;
                        OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                        while (l5 != l14) {
                            tVITEM.hItem = l5;
                            OS.SendMessage(this.handle, 4415, 0L, tVITEM);
                            l5 = OS.SendMessage(this.handle, 4362, (long)n, l5);
                        }
                    }
                }
            }
        }
        if ((l2 & 4L) == 0L) {
            this.hAnchor = l14;
        }
        if (!this.gestureCompleted) {
            tVITEM.hItem = l14;
            tVITEM.mask = 20;
            OS.SendMessage(this.handle, 4414, 0L, tVITEM);
            Event event = new Event();
            event.item = this._getItem(tVITEM.hItem, (int)tVITEM.lParam);
            this.sendSelectionEvent(13, event, false);
        }
        this.gestureCompleted = false;
        if (this.dragStarted) {
            this.sendDragEvent(1, OS.GET_X_LPARAM(l3), OS.GET_Y_LPARAM(l3));
        } else {
            int n = OS.GetWindowLong(this.handle, -16);
            if ((n & 0x10) == 0) {
                this.sendMouseEvent(4, 1, this.handle, l3);
            }
        }
        this.dragStarted = false;
        return new LRESULT(l13);
    }

    /*
     * Exception decompiling
     */
    @Override
    LRESULT WM_MOUSEMOVE(long var1, long var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl143 : ALOAD - null : trying to set 0 previously set to 6
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

    @Override
    LRESULT WM_MOUSEWHEEL(long l2, long l3) {
        LRESULT lRESULT = super.WM_MOUSEWHEEL(l2, l3);
        if (this.itemToolTipHandle != 0L) {
            OS.ShowWindow(this.itemToolTipHandle, 0);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_MOVE(long l2, long l3) {
        if (this.itemToolTipHandle != 0L) {
            OS.ShowWindow(this.itemToolTipHandle, 0);
        }
        if (this.ignoreResize) {
            return null;
        }
        return super.WM_MOVE(l2, l3);
    }

    @Override
    LRESULT WM_RBUTTONDOWN(long l2, long l3) {
        Display display = this.display;
        display.captureChanged = false;
        if (!this.sendMouseEvent(3, 3, this.handle, l3)) {
            if (!display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
                OS.SetCapture(this.handle);
            }
            return LRESULT.ZERO;
        }
        if (OS.GetFocus() != this.handle) {
            OS.SetFocus(this.handle);
        }
        TVHITTESTINFO tVHITTESTINFO = new TVHITTESTINFO();
        tVHITTESTINFO.x = OS.GET_X_LPARAM(l3);
        tVHITTESTINFO.y = OS.GET_Y_LPARAM(l3);
        OS.SendMessage(this.handle, 4369, 0L, tVHITTESTINFO);
        if (tVHITTESTINFO.hItem != 0L) {
            boolean bl;
            boolean bl2 = bl = (this.style & 0x10000) != 0;
            if (!bl) {
                if (this.hooks(41)) {
                    bl = this.hitTestSelection(tVHITTESTINFO.hItem, tVHITTESTINFO.x, tVHITTESTINFO.y);
                } else {
                    int n = 6;
                    boolean bl3 = bl = (tVHITTESTINFO.flags & 6) != 0;
                }
            }
            if (bl && (l2 & 0xCL) == 0L) {
                TVITEM tVITEM = new TVITEM();
                tVITEM.mask = 24;
                tVITEM.stateMask = 2;
                tVITEM.hItem = tVHITTESTINFO.hItem;
                OS.SendMessage(this.handle, 4414, 0L, tVITEM);
                if ((tVITEM.state & 2) == 0) {
                    this.ignoreSelect = true;
                    OS.SendMessage(this.handle, 4363, 9L, 0L);
                    this.ignoreSelect = false;
                    OS.SendMessage(this.handle, 4363, 9L, tVHITTESTINFO.hItem);
                }
            }
        }
        return LRESULT.ZERO;
    }

    @Override
    LRESULT WM_PAINT(long l2, long l3) {
        int n;
        int n2;
        if ((this.state & 0x1000) != 0) {
            return LRESULT.ZERO;
        }
        if (this.shrink && !this.ignoreShrink && this.items != null) {
            for (n2 = this.items.length - 1; n2 >= 0 && this.items[n2] == null; --n2) {
            }
            if (this.items.length > 4 && this.items.length - ++n2 > 3) {
                n = Math.max(4, (n2 + 3) / 4 * 4);
                TreeItem[] treeItemArray = new TreeItem[n];
                System.arraycopy(this.items, 0, treeItemArray, 0, n2);
                this.items = treeItemArray;
            }
            this.shrink = false;
        }
        if ((this.style & 0x20000000) != 0 || this.findImageControl() != null) {
            n2 = 1;
            if (this.explorerTheme && ((n = (int)OS.SendMessage(this.handle, 4397, 0L, 0L)) & 4) != 0) {
                n2 = 0;
            }
            if (n2 != 0) {
                boolean bl;
                GC gC = null;
                long l4 = 0L;
                PAINTSTRUCT pAINTSTRUCT = new PAINTSTRUCT();
                boolean bl2 = bl = this.hooks(9) || this.filters(9);
                if (bl) {
                    GCData gCData = new GCData();
                    gCData.ps = pAINTSTRUCT;
                    gCData.hwnd = this.handle;
                    gC = GC.win32_new(this, gCData);
                    l4 = gC.handle;
                } else {
                    l4 = OS.BeginPaint(this.handle, pAINTSTRUCT);
                }
                int n3 = pAINTSTRUCT.right - pAINTSTRUCT.left;
                int n4 = pAINTSTRUCT.bottom - pAINTSTRUCT.top;
                if (n3 != 0 && n4 != 0) {
                    long l5 = OS.CreateCompatibleDC(l4);
                    POINT pOINT = new POINT();
                    POINT pOINT2 = new POINT();
                    OS.SetWindowOrgEx(l5, pAINTSTRUCT.left, pAINTSTRUCT.top, pOINT);
                    OS.SetBrushOrgEx(l5, pAINTSTRUCT.left, pAINTSTRUCT.top, pOINT2);
                    long l6 = OS.CreateCompatibleBitmap(l4, n3, n4);
                    long l7 = OS.SelectObject(l5, l6);
                    RECT rECT = new RECT();
                    OS.SetRect(rECT, pAINTSTRUCT.left, pAINTSTRUCT.top, pAINTSTRUCT.right, pAINTSTRUCT.bottom);
                    this.drawBackground(l5, rECT);
                    this.callWindowProc(this.handle, 15, l5, 0L);
                    OS.SetWindowOrgEx(l5, pOINT.x, pOINT.y, null);
                    OS.SetBrushOrgEx(l5, pOINT2.x, pOINT2.y, null);
                    OS.BitBlt(l4, pAINTSTRUCT.left, pAINTSTRUCT.top, n3, n4, l5, 0, 0, 0xCC0020);
                    OS.SelectObject(l5, l7);
                    OS.DeleteObject(l6);
                    OS.DeleteObject(l5);
                    if (bl) {
                        Event event = new Event();
                        event.gc = gC;
                        event.setBoundsInPixels(new Rectangle(pAINTSTRUCT.left, pAINTSTRUCT.top, pAINTSTRUCT.right - pAINTSTRUCT.left, pAINTSTRUCT.bottom - pAINTSTRUCT.top));
                        this.sendEvent(9, event);
                        event.gc = null;
                    }
                }
                if (bl) {
                    gC.dispose();
                } else {
                    OS.EndPaint(this.handle, pAINTSTRUCT);
                }
                return LRESULT.ZERO;
            }
        }
        return super.WM_PAINT(l2, l3);
    }

    @Override
    LRESULT WM_SETCURSOR(long l2, long l3) {
        short s;
        LRESULT lRESULT = super.WM_SETCURSOR(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (OS.WIN32_VERSION >= OS.VERSION(6, 1) && l2 == this.handle && (s = (short)OS.LOWORD(l3)) == 1) {
            OS.SetCursor(OS.LoadCursor(0L, 32512L));
            return LRESULT.ONE;
        }
        return null;
    }

    @Override
    LRESULT WM_SETFOCUS(long l2, long l3) {
        int n;
        boolean bl;
        boolean bl2 = bl = (this.style & 2) != 0;
        if (!bl && this.imageList != null && ((n = OS.GetWindowLong(this.handle, -16)) & 0x1000) == 0) {
            bl = true;
        }
        if (bl) {
            this.redrawSelection();
        }
        return super.WM_SETFOCUS(l2, l3);
    }

    @Override
    LRESULT WM_SETFONT(long l2, long l3) {
        LRESULT lRESULT = super.WM_SETFONT(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (this.hwndHeader != 0L) {
            OS.SendMessage(this.hwndHeader, 48, 0L, l3);
            OS.SendMessage(this.hwndHeader, 48, l2, l3);
        }
        if (this.itemToolTipHandle != 0L) {
            OS.ShowWindow(this.itemToolTipHandle, 0);
            OS.SendMessage(this.itemToolTipHandle, 48, l2, l3);
        }
        if (this.headerToolTipHandle != 0L) {
            OS.SendMessage(this.headerToolTipHandle, 48, l2, l3);
            this.updateHeaderToolTips();
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SETREDRAW(long l2, long l3) {
        long l4;
        LRESULT lRESULT = super.WM_SETREDRAW(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (this.itemToolTipHandle != 0L) {
            OS.ShowWindow(this.itemToolTipHandle, 0);
        }
        return (l4 = OS.DefWindowProc(this.handle, 11, l2, l3)) == 0L ? LRESULT.ZERO : new LRESULT(l4);
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        int n;
        if (this.itemToolTipHandle != 0L) {
            OS.ShowWindow(this.itemToolTipHandle, 0);
        }
        if (((n = OS.GetWindowLong(this.handle, -16)) & 0x8000) != 0) {
            OS.ShowScrollBar(this.handle, 0, false);
        }
        if (this.explorerTheme && (this.style & 0x10000) != 0) {
            OS.InvalidateRect(this.handle, null, false);
        }
        if (this.ignoreResize) {
            return null;
        }
        return super.WM_SIZE(l2, l3);
    }

    @Override
    LRESULT WM_SYSCOLORCHANGE(long l2, long l3) {
        LRESULT lRESULT = super.WM_SYSCOLORCHANGE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (this.explorerTheme && this.foreground == -1) {
            this.setForegroundPixel(-1);
        }
        if ((this.style & 0x20) != 0) {
            this.setCheckboxImageList();
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_VSCROLL(long l2, long l3) {
        boolean bl = false;
        if ((this.style & 0x20000000) != 0) {
            int n = OS.LOWORD(l2);
            switch (n) {
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 6: 
                case 7: {
                    boolean bl2 = bl = (this.style & 0x10000000) != 0 || this.hooks(40) || this.hooks(42);
                }
            }
        }
        if (bl) {
            this.style &= 0xDFFFFFFF;
            if (this.explorerTheme) {
                OS.SendMessage(this.handle, 4396, 4L, 0L);
            }
        }
        LRESULT lRESULT = super.WM_VSCROLL(l2, l3);
        if (bl) {
            this.style |= 0x20000000;
            if (this.explorerTheme) {
                OS.SendMessage(this.handle, 4396, 4L, 4L);
            }
        }
        if (lRESULT != null) {
            return lRESULT;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_TIMER(long l2, long l3) {
        LRESULT lRESULT = super.WM_TIMER(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        long l4 = OS.SendMessage(this.handle, 4397, 0L, 0L);
        if ((l4 & 0x40L) != 0L) {
            if (!OS.IsWindowVisible(this.handle)) {
                this.lastTimerCount = this.lastTimerID == l2 ? ++this.lastTimerCount : 0;
                this.lastTimerID = l2;
                if (this.lastTimerCount >= 8) {
                    OS.CallWindowProc(TreeProc, this.handle, 512, 0L, 0L);
                    this.lastTimerID = -1L;
                    this.lastTimerCount = 0;
                }
            } else {
                this.lastTimerID = -1L;
                this.lastTimerCount = 0;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT wmColorChild(long l2, long l3) {
        if (this.findImageControl() != null) {
            return new LRESULT(OS.GetStockObject(5));
        }
        return null;
    }

    @Override
    LRESULT wmNotify(NMHDR nMHDR, long l2, long l3) {
        LRESULT lRESULT;
        if (nMHDR.hwndFrom == this.itemToolTipHandle && this.itemToolTipHandle != 0L && (lRESULT = this.wmNotifyToolTip(nMHDR, l2, l3)) != null) {
            return lRESULT;
        }
        if (nMHDR.hwndFrom == this.hwndHeader && this.hwndHeader != 0L && (lRESULT = this.wmNotifyHeader(nMHDR, l2, l3)) != null) {
            return lRESULT;
        }
        return super.wmNotify(nMHDR, l2, l3);
    }

    /*
     * Exception decompiling
     */
    @Override
    LRESULT wmNotifyChild(NMHDR var1, long var2, long var4) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl962 : ALOAD_0 - null : trying to set 2 previously set to 0
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

    /*
     * Exception decompiling
     */
    LRESULT wmNotifyHeader(NMHDR var1, long var2, long var4) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl56 : ARETURN - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
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

    LRESULT wmNotifyToolTip(NMHDR nMHDR, long l2, long l3) {
        switch (nMHDR.code) {
            case -12: {
                NMTTCUSTOMDRAW nMTTCUSTOMDRAW = new NMTTCUSTOMDRAW();
                OS.MoveMemory(nMTTCUSTOMDRAW, l3, NMTTCUSTOMDRAW.sizeof);
                return this.wmNotifyToolTip(nMTTCUSTOMDRAW, l3);
            }
            case -521: {
                LRESULT lRESULT = super.wmNotify(nMHDR, l2, l3);
                if (lRESULT != null) {
                    return lRESULT;
                }
                int n = OS.GetMessagePos();
                POINT pOINT = new POINT();
                OS.POINTSTOPOINT(pOINT, n);
                OS.ScreenToClient(this.handle, pOINT);
                int[] nArray = new int[]{0};
                TreeItem[] treeItemArray = new TreeItem[]{null};
                RECT[] rECTArray = new RECT[]{null};
                RECT[] rECTArray2 = new RECT[]{null};
                Tree tree = this;
                int n2 = pOINT.x;
                int n3 = pOINT.y;
                TreeItem[] treeItemArray2 = treeItemArray;
                int[] nArray2 = nArray;
                RECT[] rECTArray3 = rECTArray;
                if (rECTArray2 != false) {
                    RECT rECT = this.toolTipRect(rECTArray2[0]);
                    OS.MapWindowPoints(this.handle, 0L, rECT, 2);
                    int n4 = rECT.right - rECT.left;
                    int n5 = rECT.bottom - rECT.top;
                    int n6 = 21;
                    if (this.isCustomToolTip()) {
                        n6 &= 0xFFFFFFFE;
                    }
                    OS.SetWindowPos(this.itemToolTipHandle, 0L, rECT.left, rECT.top, n4, n5, n6);
                    return LRESULT.ONE;
                }
                return lRESULT;
            }
        }
        return null;
    }

    /*
     * Exception decompiling
     */
    LRESULT wmNotifyToolTip(NMTTCUSTOMDRAW var1, long var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl461 : ACONST_NULL - null : trying to set 6 previously set to 0
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

    static {
        TreeClass = new TCHAR(0, "SysTreeView32", true);
        HeaderClass = new TCHAR(0, "SysHeader32", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, TreeClass, wNDCLASS);
        TreeProc = wNDCLASS.lpfnWndProc;
        OS.GetClassInfo(0L, HeaderClass, wNDCLASS);
        HeaderProc = wNDCLASS.lpfnWndProc;
    }
}

