/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.HDHITTESTINFO;
import org.eclipse.swt.internal.win32.HDITEM;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.LVCOLUMN;
import org.eclipse.swt.internal.win32.LVHITTESTINFO;
import org.eclipse.swt.internal.win32.LVITEM;
import org.eclipse.swt.internal.win32.MEASUREITEMSTRUCT;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMLVCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMTTCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMTTDISPINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.SHDRAGIMAGE;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class Table
extends Composite {
    TableItem[] items;
    int[] keys;
    TableColumn[] columns;
    int columnCount;
    int customCount;
    int keyCount;
    ImageList imageList;
    ImageList headerImageList;
    TableItem currentItem;
    TableColumn sortColumn;
    RECT focusRect;
    boolean[] columnVisible;
    long headerToolTipHandle;
    long hwndHeader;
    long itemToolTipHandle;
    boolean ignoreCustomDraw;
    boolean ignoreDrawForeground;
    boolean ignoreDrawBackground;
    boolean ignoreDrawFocus;
    boolean ignoreDrawSelection;
    boolean ignoreDrawHot;
    boolean customDraw;
    boolean dragStarted;
    boolean explorerTheme;
    boolean firstColumnImage;
    boolean fixScrollWidth;
    boolean tipRequested;
    boolean wasSelected;
    boolean wasResized;
    boolean painted;
    boolean ignoreActivate;
    boolean ignoreSelect;
    boolean ignoreShrink;
    boolean ignoreResize;
    boolean ignoreColumnMove;
    boolean ignoreColumnResize;
    boolean fullRowSelect;
    boolean settingItemHeight;
    boolean headerItemDragging;
    int itemHeight;
    int lastIndexOf;
    int lastWidth;
    int sortDirection;
    int resizeCount;
    int selectionForeground;
    int hotIndex;
    int headerBackground = -1;
    int headerForeground = -1;
    static long HeaderProc;
    static final int INSET = 4;
    static final int GRID_WIDTH = 1;
    static final int SORT_WIDTH = 10;
    static final int HEADER_MARGIN = 12;
    static final int HEADER_EXTRA = 3;
    static final int VISTA_EXTRA = 2;
    static final int EXPLORER_EXTRA = 2;
    static final int H_SCROLL_LIMIT = 32;
    static final int V_SCROLL_LIMIT = 16;
    static final int DRAG_IMAGE_SIZE = 301;
    static boolean COMPRESS_ITEMS;
    static final long TableProc;
    static final TCHAR TableClass;
    static final TCHAR HeaderClass;

    public Table(Composite composite, int n) {
        super(composite, Table.checkStyle(n));
    }

    @Override
    void _addListener(int n, Listener listener) {
        super._addListener(n, listener);
        switch (n) {
            case 40: 
            case 41: 
            case 42: {
                this.setCustomDraw(true);
                this.setBackgroundTransparent(true);
            }
        }
    }

    void _checkShrink() {
        if (this.keys == null) {
            if (!this.ignoreShrink) {
                int n = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
                if (n == 0 && this.items.length > 4) {
                    while (n < this.items.length && this.items[n] != null && !this.items[n].isDisposed()) {
                        ++n;
                    }
                }
                if (this.items.length > 4 && this.items.length - n > 3) {
                    int n2 = Math.max(4, (n + 3) / 4 * 4);
                    TableItem[] tableItemArray = new TableItem[n2];
                    System.arraycopy(this.items, 0, tableItemArray, 0, n);
                    this.items = tableItemArray;
                }
            }
        } else if (!this.ignoreShrink && this.keys.length > 4 && this.keys.length - this.keyCount > 3) {
            int n = Math.max(4, (this.keyCount + 3) / 4 * 4);
            int[] nArray = new int[n];
            System.arraycopy(this.keys, 0, nArray, 0, this.keyCount);
            this.keys = nArray;
            TableItem[] tableItemArray = new TableItem[n];
            System.arraycopy(this.items, 0, tableItemArray, 0, this.keyCount);
            this.items = tableItemArray;
        }
    }

    void _clearItems() {
        this.items = null;
        this.keys = null;
        this.keyCount = 0;
    }

    TableItem _getItem(int n) {
        return this._getItem(n, true);
    }

    TableItem _getItem(int n, boolean bl) {
        return this._getItem(n, bl, -1);
    }

    /*
     * Exception decompiling
     */
    TableItem _getItem(int var1, boolean var2, int var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl190 : ALOAD_0 - null : trying to set 0 previously set to 1
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

    void _getItems(TableItem[] tableItemArray, int n) {
        if (this.keys == null) {
            System.arraycopy(this.items, 0, tableItemArray, 0, n);
        } else {
            for (int i = 0; i < this.keyCount && this.keys[i] < n; ++i) {
                tableItemArray[this.keys[i]] = this.items[this.keys[i]];
            }
        }
    }

    void _initItems() {
        this.items = new TableItem[4];
        if (COMPRESS_ITEMS && (this.style & 0x10000000) != 0) {
            this.keyCount = 0;
            this.keys = new int[4];
        }
    }

    void _insertItem(int n, TableItem tableItem, int n2) {
        if (this.keys == null) {
            System.arraycopy(this.items, n, this.items, n + 1, n2 - n);
            this.items[n] = tableItem;
        } else {
            int n3 = this.binarySearch(this.keys, 0, this.keyCount, n);
            if (n3 < 0) {
                n3 = -n3 - 1;
            }
            System.arraycopy(this.keys, n3, this.keys, n3 + 1, this.keyCount - n3);
            this.keys[n3] = n;
            System.arraycopy(this.items, n3, this.items, n3 + 1, this.keyCount - n3);
            this.items[n3] = tableItem;
            ++this.keyCount;
            int n4 = n3 + 1;
            while (n4 < this.keyCount) {
                int n5;
                int[] nArray = this.keys;
                int n6 = n5 = n4++;
                nArray[n6] = nArray[n6] + 1;
            }
        }
    }

    void _removeItem(int n, int n2) {
        if (this.keys == null) {
            System.arraycopy(this.items, n + 1, this.items, n, --n2 - n);
            this.items[n2] = null;
        } else {
            int n3 = this.binarySearch(this.keys, 0, this.keyCount, n);
            if (n3 < 0) {
                n3 = -n3 - 1;
            } else {
                --this.keyCount;
                System.arraycopy(this.keys, n3 + 1, this.keys, n3, this.keyCount - n3);
                this.keys[this.keyCount] = 0;
                System.arraycopy(this.items, n3 + 1, this.items, n3, this.keyCount - n3);
                this.items[this.keyCount] = null;
            }
            int n4 = n3;
            while (n4 < this.keyCount) {
                int n5;
                int[] nArray = this.keys;
                int n6 = n5 = n4++;
                nArray[n6] = nArray[n6] - 1;
            }
        }
    }

    void _removeItems(int n, int n2, int n3) {
        if (this.keys == null) {
            System.arraycopy(this.items, n2, this.items, n, n3 - n2);
            for (int i = n3 - (n2 - n); i < n3; ++i) {
                this.items[i] = null;
            }
        } else {
            int n4;
            int n5;
            int n6 = n2;
            int n7 = this.binarySearch(this.keys, 0, this.keyCount, n);
            if (n7 < 0) {
                n7 = -n7 - 1;
            }
            if ((n5 = this.binarySearch(this.keys, n7, this.keyCount, n6)) < 0) {
                n5 = -n5 - 1;
            }
            System.arraycopy(this.keys, n5, this.keys, n7, this.keyCount - n5);
            for (n4 = this.keyCount - (n5 - n7); n4 < this.keyCount; ++n4) {
                this.keys[n4] = 0;
            }
            System.arraycopy(this.items, n5, this.items, n7, this.keyCount - n5);
            for (n4 = this.keyCount - (n5 - n7); n4 < this.keyCount; ++n4) {
                this.items[n4] = null;
            }
            this.keyCount -= n5 - n7;
            n4 = n7;
            while (n4 < this.keyCount) {
                int n8;
                int[] nArray = this.keys;
                int n9 = n8 = n4++;
                nArray[n9] = nArray[n9] - (n5 - n7);
            }
        }
    }

    void _setItemCount(int n, int n2) {
        if (this.keys == null) {
            int n3 = Math.max(4, (n + 3) / 4 * 4);
            TableItem[] tableItemArray = new TableItem[n3];
            System.arraycopy(this.items, 0, tableItemArray, 0, Math.min(n, n2));
            this.items = tableItemArray;
        } else {
            int n4 = Math.min(n, n2);
            this.keyCount = this.binarySearch(this.keys, 0, this.keyCount, n4);
            if (this.keyCount < 0) {
                this.keyCount = -this.keyCount - 1;
            }
            int n5 = Math.max(4, (this.keyCount + 3) / 4 * 4);
            int[] nArray = new int[n5];
            System.arraycopy(this.keys, 0, nArray, 0, this.keyCount);
            this.keys = nArray;
            TableItem[] tableItemArray = new TableItem[n5];
            System.arraycopy(this.items, 0, tableItemArray, 0, this.keyCount);
            this.items = tableItemArray;
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

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        return this.callWindowProc(l2, n, l3, l4, false);
    }

    long callWindowProc(long l2, int n, long l3, long l4, boolean bl) {
        long l5;
        int n2;
        if (this.handle == 0L) {
            return 0L;
        }
        if (this.hwndHeader != 0L && l2 == this.hwndHeader) {
            return OS.CallWindowProc(HeaderProc, l2, n, l3, l4);
        }
        int n3 = 0;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        switch (n) {
            case 256: {
                bl3 = true;
            }
            case 71: 
            case 257: 
            case 258: 
            case 260: 
            case 261: 
            case 262: 
            case 276: 
            case 277: 
            case 646: {
                boolean bl5 = bl4 = this.findImageControl() != null && this.getDrawing() && OS.IsWindowVisible(this.handle);
                if (bl4) {
                    OS.DefWindowProc(this.handle, 11, 0L, 0L);
                    OS.SendMessage(this.handle, 4097, 0L, 0xFFFFFFL);
                }
            }
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
                bl2 = true;
            }
            case 48: 
            case 275: {
                if (this.findImageControl() == null) break;
                n3 = (int)OS.SendMessage(this.handle, 4135, 0L, 0L);
            }
        }
        boolean bl6 = this.wasSelected;
        if (bl2) {
            this.wasSelected = false;
        }
        if (bl3) {
            this.ignoreActivate = true;
        }
        boolean bl7 = false;
        if (n == 15 && ((n2 = OS.GetWindowLong(this.handle, -16)) & 0x4000) == 0) {
            l5 = OS.GetParent(this.handle);
            long l6 = 0L;
            while (l5 != 0L) {
                int n4 = OS.GetWindowLong(l5, -20);
                if ((n4 & 0x2000000) != 0) {
                    bl7 = true;
                    break;
                }
                l6 = OS.GetWindow(l5, 4);
                if (l6 != 0L) break;
                l5 = OS.GetParent(l5);
            }
        }
        n2 = 0;
        if ((this.style & 0x100) == 0 || (this.style & 0x200) == 0) {
            switch (n) {
                case 15: 
                case 70: 
                case 133: {
                    int n5 = OS.GetWindowLong(l2, -16);
                    if ((this.style & 0x100) == 0 && (n5 & 0x100000) != 0) {
                        n2 = 1;
                        n5 &= 0xFFEFFFFF;
                    }
                    if ((this.style & 0x200) == 0 && (n5 & 0x200000) != 0) {
                        n2 = 1;
                        n5 &= 0xFFDFFFFF;
                    }
                    if (n2 == 0) break;
                    OS.SetWindowLong(this.handle, -16, n5);
                    break;
                }
            }
        }
        l5 = 0L;
        if (bl7) {
            PAINTSTRUCT pAINTSTRUCT = new PAINTSTRUCT();
            long l7 = OS.BeginPaint(l2, pAINTSTRUCT);
            l5 = OS.CallWindowProc(TableProc, l2, 15, l7, l4);
            OS.EndPaint(l2, pAINTSTRUCT);
        } else {
            l5 = OS.CallWindowProc(TableProc, l2, n, l3, l4);
        }
        if (n2 != 0) {
            int n6 = 1025;
            OS.RedrawWindow(this.handle, null, 0L, 1025);
        }
        if (bl3) {
            this.ignoreActivate = false;
        }
        if (bl2) {
            if (this.wasSelected || bl) {
                Event event = new Event();
                int n7 = (int)OS.SendMessage(this.handle, 4108, -1L, 1L);
                if (n7 != -1) {
                    event.item = this._getItem(n7);
                }
                this.sendSelectionEvent(13, event, false);
            }
            this.wasSelected = bl6;
        }
        switch (n) {
            case 71: 
            case 256: 
            case 257: 
            case 258: 
            case 260: 
            case 261: 
            case 262: 
            case 276: 
            case 277: 
            case 646: {
                if (bl4) {
                    OS.SendMessage(this.handle, 4097, 0L, -1L);
                    OS.DefWindowProc(this.handle, 11, 1L, 0L);
                    OS.InvalidateRect(this.handle, null, true);
                    long l8 = OS.SendMessage(this.handle, 4127, 0L, 0L);
                    if (l8 != 0L) {
                        OS.InvalidateRect(l8, null, true);
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
                if (this.findImageControl() == null || (long)n3 == OS.SendMessage(this.handle, 4135, 0L, 0L)) break;
                OS.InvalidateRect(this.handle, null, true);
                break;
            }
            case 15: {
                this.painted = true;
            }
        }
        return l5;
    }

    static int checkStyle(int n) {
        if ((n & 0x10) == 0) {
            n |= 0x300;
        }
        return Widget.checkBits(n, 4, 2, 0, 0, 0, 0);
    }

    LRESULT CDDS_ITEMPOSTPAINT(NMLVCUSTOMDRAW nMLVCUSTOMDRAW, long l2, long l3) {
        int n;
        int n2;
        long l4 = nMLVCUSTOMDRAW.hdc;
        if (this.explorerTheme && !this.ignoreCustomDraw) {
            this.hotIndex = -1;
            if (this.hooks(40) && nMLVCUSTOMDRAW.left != nMLVCUSTOMDRAW.right) {
                OS.RestoreDC(l4, -1);
            }
        }
        if (!this.ignoreCustomDraw && !this.ignoreDrawFocus && nMLVCUSTOMDRAW.left != nMLVCUSTOMDRAW.right && OS.IsWindowVisible(this.handle) && OS.IsWindowEnabled(this.handle) && !this.explorerTheme && (this.style & 0x10000) != 0 && (int)OS.SendMessage(this.handle, 4096, 0L, 0L) == -1 && ((n2 = (int)OS.SendMessage(this.handle, 4151, 0L, 0L)) & 0x20) == 0 && OS.SendMessage(this.handle, 4108, -1L, 1L) == nMLVCUSTOMDRAW.dwItemSpec && this.handle == OS.GetFocus() && ((n = (int)OS.SendMessage(this.handle, 297, 0L, 0L)) & 1) == 0) {
            RECT rECT = new RECT();
            rECT.left = 0;
            boolean bl = this.ignoreCustomDraw;
            this.ignoreCustomDraw = true;
            OS.SendMessage(this.handle, 4110, nMLVCUSTOMDRAW.dwItemSpec, rECT);
            long l5 = OS.SendMessage(this.handle, 4127, 0L, 0L);
            int n3 = (int)OS.SendMessage(l5, 4623, 0L, 0L);
            RECT rECT2 = new RECT();
            if (n3 == 0) {
                rECT2.left = 2;
                OS.SendMessage(this.handle, 4110, (long)n3, rECT2);
            } else {
                rECT2.top = n3;
                rECT2.left = 1;
                OS.SendMessage(this.handle, 4152, nMLVCUSTOMDRAW.dwItemSpec, rECT2);
            }
            this.ignoreCustomDraw = bl;
            rECT.left = rECT2.left;
            OS.DrawFocusRect(nMLVCUSTOMDRAW.hdc, rECT);
        }
        return null;
    }

    LRESULT CDDS_ITEMPREPAINT(NMLVCUSTOMDRAW nMLVCUSTOMDRAW, long l2, long l3) {
        int n;
        if (!this.ignoreCustomDraw && OS.IsWindowVisible(this.handle) && OS.IsWindowEnabled(this.handle) && !this.explorerTheme && (this.style & 0x10000) != 0 && (int)OS.SendMessage(this.handle, 4096, 0L, 0L) == -1 && ((n = (int)OS.SendMessage(this.handle, 4151, 0L, 0L)) & 0x20) == 0 && (nMLVCUSTOMDRAW.uItemState & 0x10) != 0) {
            nMLVCUSTOMDRAW.uItemState &= 0xFFFFFFEF;
            OS.MoveMemory(l3, nMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
        }
        if (this.explorerTheme && !this.ignoreCustomDraw) {
            int n2 = this.hotIndex = (nMLVCUSTOMDRAW.uItemState & 0x40) != 0 ? (int)nMLVCUSTOMDRAW.dwItemSpec : -1;
            if (this.hooks(40) && nMLVCUSTOMDRAW.left != nMLVCUSTOMDRAW.right) {
                OS.SaveDC(nMLVCUSTOMDRAW.hdc);
                long l4 = OS.CreateRectRgn(0, 0, 0, 0);
                OS.SelectClipRgn(nMLVCUSTOMDRAW.hdc, l4);
                OS.DeleteObject(l4);
            }
        }
        return new LRESULT(48L);
    }

    LRESULT CDDS_POSTPAINT(NMLVCUSTOMDRAW nMLVCUSTOMDRAW, long l2, long l3) {
        int n;
        int n2;
        if (this.ignoreCustomDraw) {
            return null;
        }
        this.customCount = n2 = this.customCount - 1;
        if (n2 == 0 && OS.IsWindowVisible(this.handle) && !this.explorerTheme && (this.style & 0x10000) != 0 && (int)OS.SendMessage(this.handle, 4096, 0L, 0L) == -1 && ((n = (int)OS.SendMessage(this.handle, 4151, 0L, 0L)) & 0x20) == 0) {
            int n3 = 32;
            long l4 = OS.SendMessage(this.handle, 4170, 0L, 0L);
            long l5 = OS.CreateRectRgn(0, 0, 0, 0);
            int n4 = OS.GetUpdateRgn(this.handle, l5, true);
            OS.SendMessage(this.handle, 4150, 32L, 32L);
            OS.ValidateRect(this.handle, null);
            if (n4 != 1) {
                OS.InvalidateRgn(this.handle, l5, true);
            }
            OS.DeleteObject(l5);
            l4 = OS.SendMessage(this.handle, 4170, l4, l4);
        }
        return null;
    }

    LRESULT CDDS_PREPAINT(NMLVCUSTOMDRAW nMLVCUSTOMDRAW, long l2, long l3) {
        int n;
        if (this.ignoreCustomDraw) {
            return new LRESULT(48L);
        }
        if (this.customCount++ == 0 && OS.IsWindowVisible(this.handle) && !this.explorerTheme && (this.style & 0x10000) != 0 && (int)OS.SendMessage(this.handle, 4096, 0L, 0L) == -1 && ((n = (int)OS.SendMessage(this.handle, 4151, 0L, 0L)) & 0x20) != 0) {
            int n2 = 32;
            long l4 = OS.SendMessage(this.handle, 4170, 0L, 0L);
            long l5 = OS.CreateRectRgn(0, 0, 0, 0);
            int n3 = OS.GetUpdateRgn(this.handle, l5, true);
            OS.SendMessage(this.handle, 4150, 32L, 0L);
            OS.ValidateRect(this.handle, null);
            if (n3 != 1) {
                OS.InvalidateRgn(this.handle, l5, true);
            }
            OS.DeleteObject(l5);
            l4 = OS.SendMessage(this.handle, 4170, l4, l4);
        }
        if (OS.IsWindowVisible(this.handle)) {
            RECT rECT = new RECT();
            OS.SetRect(rECT, nMLVCUSTOMDRAW.left, nMLVCUSTOMDRAW.top, nMLVCUSTOMDRAW.right, nMLVCUSTOMDRAW.bottom);
            if (this.explorerTheme && this.columnCount == 0) {
                long l6 = nMLVCUSTOMDRAW.hdc;
                if (OS.IsWindowEnabled(this.handle) || this.findImageControl() != null || this.hasCustomBackground()) {
                    this.drawBackground(l6, rECT);
                } else {
                    this.fillBackground(l6, OS.GetSysColor(15), rECT);
                }
            } else {
                Control control = this.findBackgroundControl();
                if (control != null && control.backgroundImage != null) {
                    this.fillImageBackground(nMLVCUSTOMDRAW.hdc, control, rECT, 0, 0);
                } else {
                    boolean bl = OS.IsWindowEnabled(this.handle);
                    if (bl && (int)OS.SendMessage(this.handle, 4096, 0L, 0L) == -1 || !bl && this.hasCustomBackground()) {
                        int n4;
                        if (control == null) {
                            control = this;
                        }
                        this.fillBackground(nMLVCUSTOMDRAW.hdc, control.getBackgroundPixel(), rECT);
                        if (OS.IsAppThemed() && this.sortColumn != null && this.sortDirection != 0 && (n4 = this.indexOf(this.sortColumn)) != -1) {
                            this.parent.forceResize();
                            int n5 = this.getSortColumnPixel();
                            RECT rECT2 = new RECT();
                            RECT rECT3 = new RECT();
                            OS.GetClientRect(this.handle, rECT2);
                            long l7 = OS.SendMessage(this.handle, 4127, 0L, 0L);
                            if (OS.SendMessage(l7, 4615, (long)n4, rECT3) != 0L) {
                                OS.MapWindowPoints(l7, this.handle, rECT3, 2);
                                rECT2.left = rECT3.left;
                                rECT2.right = rECT3.right;
                                if (OS.IntersectRect(rECT2, rECT2, rECT)) {
                                    this.fillBackground(nMLVCUSTOMDRAW.hdc, n5, rECT2);
                                }
                            }
                        }
                    }
                }
            }
        }
        return new LRESULT(48L);
    }

    LRESULT CDDS_SUBITEMPOSTPAINT(NMLVCUSTOMDRAW nMLVCUSTOMDRAW, long l2, long l3) {
        if (this.ignoreCustomDraw) {
            return null;
        }
        if (nMLVCUSTOMDRAW.left == nMLVCUSTOMDRAW.right) {
            return new LRESULT(0L);
        }
        long l4 = nMLVCUSTOMDRAW.hdc;
        if (this.ignoreDrawForeground) {
            OS.RestoreDC(l4, -1);
        }
        if (OS.IsWindowVisible(this.handle)) {
            int n;
            if ((int)OS.SendMessage(this.handle, 4096, 0L, 0L) != -1 && (this.sortDirection & 0x480) != 0 && this.sortColumn != null && !this.sortColumn.isDisposed() && (n = (int)OS.SendMessage(this.handle, 4270, 0L, 0L)) == -1) {
                int n2 = this.indexOf(this.sortColumn);
                long l5 = OS.CreateRectRgn(0, 0, 0, 0);
                int n3 = OS.GetUpdateRgn(this.handle, l5, true);
                OS.SendMessage(this.handle, 4236, (long)n2, 0L);
                OS.ValidateRect(this.handle, null);
                if (n3 != 1) {
                    OS.InvalidateRgn(this.handle, l5, true);
                }
                OS.DeleteObject(l5);
            }
            if (this.hooks(42)) {
                TableItem tableItem = this._getItem((int)nMLVCUSTOMDRAW.dwItemSpec);
                this.sendPaintItemEvent(tableItem, nMLVCUSTOMDRAW);
            }
            if (!this.ignoreDrawFocus && this.focusRect != null) {
                OS.SetTextColor(nMLVCUSTOMDRAW.hdc, 0);
                OS.SetBkColor(nMLVCUSTOMDRAW.hdc, 0xFFFFFF);
                OS.DrawFocusRect(nMLVCUSTOMDRAW.hdc, this.focusRect);
                this.focusRect = null;
            }
        }
        return null;
    }

    LRESULT CDDS_SUBITEMPREPAINT(NMLVCUSTOMDRAW nMLVCUSTOMDRAW, long l2, long l3) {
        Object object;
        int n;
        int n2;
        int n3;
        TableItem tableItem;
        long l4 = nMLVCUSTOMDRAW.hdc;
        if (this.explorerTheme && !this.ignoreCustomDraw && this.hooks(40) && nMLVCUSTOMDRAW.left != nMLVCUSTOMDRAW.right) {
            OS.RestoreDC(l4, -1);
        }
        if ((tableItem = this._getItem((int)nMLVCUSTOMDRAW.dwItemSpec)) == null || tableItem.isDisposed()) {
            return null;
        }
        long l5 = tableItem.fontHandle(nMLVCUSTOMDRAW.iSubItem);
        if (l5 != -1L) {
            OS.SelectObject(l4, l5);
        }
        if (this.ignoreCustomDraw || nMLVCUSTOMDRAW.left == nMLVCUSTOMDRAW.right) {
            return new LRESULT(l5 == -1L ? 0L : 2L);
        }
        int n4 = 0;
        this.selectionForeground = -1;
        boolean bl = false;
        this.ignoreDrawBackground = false;
        this.ignoreDrawFocus = false;
        this.ignoreDrawSelection = false;
        this.ignoreDrawForeground = false;
        if (OS.IsWindowVisible(this.handle)) {
            Event event = null;
            if (this.hooks(41)) {
                event = this.sendMeasureItemEvent(tableItem, (int)nMLVCUSTOMDRAW.dwItemSpec, nMLVCUSTOMDRAW.iSubItem, nMLVCUSTOMDRAW.hdc);
                if (this.isDisposed() || tableItem.isDisposed()) {
                    return null;
                }
            }
            if (this.hooks(40)) {
                this.sendEraseItemEvent(tableItem, nMLVCUSTOMDRAW, l3, event);
                if (this.isDisposed() || tableItem.isDisposed()) {
                    return null;
                }
                n4 |= 0x10;
            }
            if (this.ignoreDrawForeground || this.hooks(42)) {
                n4 |= 0x10;
            }
        }
        int n5 = n3 = tableItem.cellForeground != null ? tableItem.cellForeground[nMLVCUSTOMDRAW.iSubItem] : -1;
        if (n3 == -1) {
            n3 = tableItem.foreground;
        }
        int n6 = n2 = tableItem.cellBackground != null ? tableItem.cellBackground[nMLVCUSTOMDRAW.iSubItem] : -1;
        if (n2 == -1) {
            n2 = tableItem.background;
        }
        if (this.selectionForeground != -1) {
            n3 = this.selectionForeground;
        }
        boolean bl2 = OS.IsWindowEnabled(this.handle);
        if (OS.IsWindowVisible(this.handle) && bl2 && !this.explorerTheme && !this.ignoreDrawSelection && (this.style & 0x10000) != 0 && ((n = (int)OS.SendMessage(this.handle, 4151, 0L, 0L)) & 0x20) == 0) {
            object = new LVITEM();
            ((LVITEM)object).mask = 8;
            ((LVITEM)object).stateMask = 2;
            ((LVITEM)object).iItem = (int)nMLVCUSTOMDRAW.dwItemSpec;
            long l6 = OS.SendMessage(this.handle, 4171, 0L, (LVITEM)object);
            if (l6 != 0L && (((LVITEM)object).state & 2) != 0) {
                int n7 = -1;
                if (nMLVCUSTOMDRAW.iSubItem == 0) {
                    if (OS.GetFocus() == this.handle || this.display.getHighContrast()) {
                        n7 = OS.GetSysColor(13);
                    } else if ((this.style & 0x8000) == 0) {
                        n7 = OS.GetSysColor(15);
                    }
                } else if (OS.GetFocus() == this.handle || this.display.getHighContrast()) {
                    n3 = OS.GetSysColor(14);
                    n7 = n2 = OS.GetSysColor(13);
                } else if ((this.style & 0x8000) == 0) {
                    n7 = n2 = OS.GetSysColor(15);
                }
                if (n7 != -1) {
                    RECT rECT = tableItem.getBounds((int)nMLVCUSTOMDRAW.dwItemSpec, nMLVCUSTOMDRAW.iSubItem, true, nMLVCUSTOMDRAW.iSubItem != 0, true, false, l4);
                    this.fillBackground(l4, n7, rECT);
                }
            }
        }
        if (!this.ignoreDrawForeground) {
            int n8;
            n = 1;
            if (l5 == -1L && n3 == -1 && n2 == -1 && tableItem.cellForeground == null && tableItem.cellBackground == null && tableItem.cellFont == null && (n8 = (int)OS.SendMessage(this.hwndHeader, 4608, 0L, 0L)) == 1) {
                n = 0;
            }
            if (n != 0) {
                if (l5 == -1L) {
                    l5 = OS.SendMessage(this.handle, 49, 0L, 0L);
                }
                OS.SelectObject(l4, l5);
                if (bl2) {
                    int n9 = nMLVCUSTOMDRAW.clrText = n3 == -1 ? this.getForegroundPixel() : n3;
                    if (n2 == -1) {
                        nMLVCUSTOMDRAW.clrTextBk = -1;
                        if (this.selectionForeground == -1) {
                            object = this.findBackgroundControl();
                            if (object == null) {
                                object = this;
                            }
                            if (((Control)object).backgroundImage == null && (int)OS.SendMessage(this.handle, 4096, 0L, 0L) != -1) {
                                nMLVCUSTOMDRAW.clrTextBk = ((Control)object).getBackgroundPixel();
                            }
                        }
                    } else {
                        nMLVCUSTOMDRAW.clrTextBk = this.selectionForeground != -1 ? -1 : n2;
                    }
                    OS.MoveMemory(l3, nMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
                }
                n4 |= 2;
            }
        }
        if ((bl2 && n2 != -1 || !bl2 && this.hasCustomBackground()) && (n = (int)OS.SendMessage(this.handle, 4270, 0L, 0L)) != -1 && n == nMLVCUSTOMDRAW.iSubItem) {
            long l7 = OS.CreateRectRgn(0, 0, 0, 0);
            int n10 = OS.GetUpdateRgn(this.handle, l7, true);
            OS.SendMessage(this.handle, 4236, -1L, 0L);
            OS.ValidateRect(this.handle, null);
            if (n10 != 1) {
                OS.InvalidateRgn(this.handle, l7, true);
            }
            OS.DeleteObject(l7);
            n4 |= 0x10;
        }
        if (!bl2) {
            nMLVCUSTOMDRAW.clrText = OS.GetSysColor(17);
            if (this.findImageControl() != null || this.hasCustomBackground()) {
                nMLVCUSTOMDRAW.clrTextBk = -1;
            }
            nMLVCUSTOMDRAW.uItemState &= 0xFFFFFFFE;
            OS.MoveMemory(l3, nMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
            n4 |= 2;
        }
        return new LRESULT(n4);
    }

    @Override
    void checkBuffered() {
        super.checkBuffered();
        this.style |= 0x20000000;
    }

    /*
     * Exception decompiling
     */
    boolean checkData(TableItem var1, boolean var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl13 : ICONST_1 - null : trying to set 0 previously set to 3
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
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    public void clear(int n) {
        TableItem tableItem;
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        if (0 > n || n >= n2) {
            this.error(6);
        }
        if ((tableItem = this._getItem(n, false)) != null) {
            if (tableItem != this.currentItem) {
                tableItem.clear();
            }
            if ((this.style & 0x10000000) == 0 && tableItem.cached) {
                LVITEM lVITEM = new LVITEM();
                lVITEM.mask = 17;
                lVITEM.pszText = -1L;
                lVITEM.iItem = n;
                OS.SendMessage(this.handle, 4172, 0L, lVITEM);
                tableItem.cached = false;
            }
            if (this.currentItem == null && this.getDrawing() && OS.IsWindowVisible(this.handle)) {
                OS.SendMessage(this.handle, 4117, (long)n, n);
            }
            this.setScrollWidth(tableItem, false);
        }
    }

    public void clear(int n, int n2) {
        this.checkWidget();
        if (n > n2) {
            return;
        }
        int n3 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        if (0 > n || n > n2 || n2 >= n3) {
            this.error(6);
        }
        if (n == 0 && n2 == n3 - 1) {
            this.clearAll();
        } else {
            LVITEM lVITEM = null;
            boolean bl = false;
            for (int i = n; i <= n2; ++i) {
                TableItem tableItem = this._getItem(i, false);
                if (tableItem == null) continue;
                if (tableItem != this.currentItem) {
                    bl = true;
                    tableItem.clear();
                }
                if ((this.style & 0x10000000) != 0 || !tableItem.cached) continue;
                if (lVITEM == null) {
                    lVITEM = new LVITEM();
                    lVITEM.mask = 17;
                    lVITEM.pszText = -1L;
                }
                lVITEM.iItem = i;
                OS.SendMessage(this.handle, 4172, 0L, lVITEM);
                tableItem.cached = false;
            }
            if (bl) {
                if (this.currentItem == null && this.getDrawing() && OS.IsWindowVisible(this.handle)) {
                    OS.SendMessage(this.handle, 4117, (long)n, n2);
                }
                TableItem tableItem = n == n2 ? this._getItem(n, false) : null;
                this.setScrollWidth(tableItem, false);
            }
        }
    }

    public void clear(int[] nArray) {
        this.checkWidget();
        if (nArray == null) {
            this.error(4);
        }
        if (nArray.length == 0) {
            return;
        }
        int n = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        for (int i = 0; i < nArray.length; ++i) {
            if (0 <= nArray[i] && nArray[i] < n) continue;
            this.error(6);
        }
        LVITEM lVITEM = null;
        boolean bl = false;
        for (int i = 0; i < nArray.length; ++i) {
            int n2 = nArray[i];
            TableItem tableItem = this._getItem(n2, false);
            if (tableItem == null) continue;
            if (tableItem != this.currentItem) {
                bl = true;
                tableItem.clear();
            }
            if ((this.style & 0x10000000) == 0 && tableItem.cached) {
                if (lVITEM == null) {
                    lVITEM = new LVITEM();
                    lVITEM.mask = 17;
                    lVITEM.pszText = -1L;
                }
                lVITEM.iItem = i;
                OS.SendMessage(this.handle, 4172, 0L, lVITEM);
                tableItem.cached = false;
            }
            if (this.currentItem != null || !this.getDrawing() || !OS.IsWindowVisible(this.handle)) continue;
            OS.SendMessage(this.handle, 4117, (long)n2, n2);
        }
        if (bl) {
            this.setScrollWidth(null, false);
        }
    }

    public void clearAll() {
        this.checkWidget();
        LVITEM lVITEM = null;
        boolean bl = false;
        int n = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        for (int i = 0; i < n; ++i) {
            TableItem tableItem = this._getItem(i, false);
            if (tableItem == null) continue;
            if (tableItem != this.currentItem) {
                bl = true;
                tableItem.clear();
            }
            if ((this.style & 0x10000000) != 0 || !tableItem.cached) continue;
            if (lVITEM == null) {
                lVITEM = new LVITEM();
                lVITEM.mask = 17;
                lVITEM.pszText = -1L;
            }
            lVITEM.iItem = i;
            OS.SendMessage(this.handle, 4172, 0L, lVITEM);
            tableItem.cached = false;
        }
        if (bl) {
            if (this.currentItem == null && this.getDrawing() && OS.IsWindowVisible(this.handle)) {
                OS.SendMessage(this.handle, 4117, 0L, n - 1);
            }
            this.setScrollWidth(null, false);
        }
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        int n3;
        if (this.fixScrollWidth) {
            this.setScrollWidth(null, true);
        }
        RECT rECT = new RECT();
        OS.GetWindowRect(this.hwndHeader, rECT);
        int n4 = rECT.bottom - rECT.top;
        int n5 = 0;
        if (n != -1) {
            n5 |= n & 0xFFFF;
        } else {
            int n6 = 0;
            int n7 = (int)OS.SendMessage(this.hwndHeader, 4608, 0L, 0L);
            for (n3 = 0; n3 < n7; ++n3) {
                n6 += (int)OS.SendMessage(this.handle, 4125, (long)n3, 0L);
            }
            n5 |= n6 & 0xFFFF;
        }
        long l2 = OS.SendMessage(this.handle, 4160, -1L, OS.MAKELPARAM(n5, 65535));
        n3 = OS.LOWORD(l2);
        long l3 = OS.SendMessage(this.handle, 4160, 0L, 0L);
        long l4 = OS.SendMessage(this.handle, 4160, 1L, 0L);
        int n8 = OS.HIWORD(l4) - OS.HIWORD(l3);
        n4 += (int)OS.SendMessage(this.handle, 4100, 0L, 0L) * n8;
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
        int n9 = this.getBorderWidthInPixels();
        n3 += n9 * 2;
        n4 += n9 * 2;
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
        long l2;
        long l3;
        super.createHandle();
        this.state &= 0xFFFFFEFD;
        if (OS.IsAppThemed()) {
            this.explorerTheme = true;
            OS.SetWindowTheme(this.handle, Display.EXPLORER, null);
        }
        this.hwndHeader = OS.SendMessage(this.handle, 4127, 0L, 0L);
        this.maybeEnableDarkSystemTheme(this.hwndHeader);
        if ((this.style & 0x20) != 0) {
            int n;
            l3 = OS.SendMessage(this.handle, 4160, 0L, 0L);
            long l4 = OS.SendMessage(this.handle, 4160, 1L, 0L);
            int n2 = n = OS.HIWORD(l4) - OS.HIWORD(l3);
            this.setCheckboxImageList(n2, n, false);
            OS.SendMessage(this.handle, 4107, 61440L, 0L);
        }
        l3 = OS.GetStockObject(13);
        OS.SendMessage(this.handle, 48, l3, 0L);
        LVCOLUMN lVCOLUMN = new LVCOLUMN();
        lVCOLUMN.mask = 6;
        long l5 = OS.GetProcessHeap();
        lVCOLUMN.pszText = l2 = OS.HeapAlloc(l5, 8, 2);
        OS.SendMessage(this.handle, 4193, 0L, lVCOLUMN);
        OS.HeapFree(l5, 0, l2);
        int n = 81920;
        if ((this.style & 0x10000) != 0) {
            n |= 0x20;
        }
        OS.SendMessage(this.handle, 4150, (long)n, n);
        if ((this.style & 0x4000000) != 0) {
            int n3 = OS.GetWindowLong(this.hwndHeader, -20);
            OS.SetWindowLong(this.hwndHeader, -20, n3 | 0x400000);
            long l6 = OS.SendMessage(this.handle, 4174, 0L, 0L);
            int n4 = OS.GetWindowLong(l6, -20);
            OS.SetWindowLong(l6, -20, n4 | 0x400000);
        }
    }

    @Override
    int applyThemeBackground() {
        return -1;
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

    void createItem(TableColumn tableColumn, int n) {
        RECT rECT;
        Object object;
        int n2;
        int n3;
        if (0 > n || n > this.columnCount) {
            this.error(6);
        }
        if ((n3 = (int)OS.SendMessage(this.handle, 4270, 0L, 0L)) >= n) {
            OS.SendMessage(this.handle, 4236, (long)(n3 + 1), 0L);
        }
        if (this.columnCount == this.columns.length) {
            TableColumn[] tableColumnArray = new TableColumn[this.columns.length + 4];
            System.arraycopy(this.columns, 0, tableColumnArray, 0, this.columns.length);
            this.columns = tableColumnArray;
        }
        int n4 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        for (n2 = 0; n2 < n4; ++n2) {
            Object[] objectArray;
            Object[] objectArray2;
            Object[] objectArray3;
            object = this._getItem(n2, false);
            if (object == null) continue;
            String[] stringArray = ((TableItem)object).strings;
            if (stringArray != null) {
                objectArray3 = new String[this.columnCount + 1];
                System.arraycopy(stringArray, 0, objectArray3, 0, n);
                System.arraycopy(stringArray, n, objectArray3, n + 1, this.columnCount - n);
                ((TableItem)object).strings = objectArray3;
            }
            if ((objectArray3 = ((TableItem)object).images) != null) {
                objectArray2 = new Image[this.columnCount + 1];
                System.arraycopy(objectArray3, 0, objectArray2, 0, n);
                System.arraycopy(objectArray3, n, objectArray2, n + 1, this.columnCount - n);
                ((TableItem)object).images = objectArray2;
            }
            if (n == 0 && this.columnCount != 0) {
                if (stringArray == null) {
                    String[] stringArray2 = new String[this.columnCount + 1];
                    ((TableItem)object).strings = stringArray2;
                    stringArray2[1] = ((TableItem)object).text;
                }
                ((TableItem)object).text = "";
                if (objectArray3 == null) {
                    Image[] imageArray = new Image[this.columnCount + 1];
                    ((TableItem)object).images = imageArray;
                    imageArray[1] = ((TableItem)object).image;
                }
                ((TableItem)object).image = null;
            }
            if (((TableItem)object).cellBackground != null) {
                objectArray2 = ((TableItem)object).cellBackground;
                objectArray = new int[this.columnCount + 1];
                System.arraycopy(objectArray2, 0, objectArray, 0, n);
                System.arraycopy(objectArray2, n, objectArray, n + 1, this.columnCount - n);
                objectArray[n] = -1;
                ((TableItem)object).cellBackground = objectArray;
            }
            if (((TableItem)object).cellForeground != null) {
                objectArray2 = ((TableItem)object).cellForeground;
                objectArray = new int[this.columnCount + 1];
                System.arraycopy(objectArray2, 0, objectArray, 0, n);
                System.arraycopy(objectArray2, n, objectArray, n + 1, this.columnCount - n);
                objectArray[n] = -1;
                ((TableItem)object).cellForeground = objectArray;
            }
            if (((TableItem)object).cellFont == null) continue;
            objectArray2 = ((TableItem)object).cellFont;
            objectArray = new Font[this.columnCount + 1];
            System.arraycopy(objectArray2, 0, objectArray, 0, n);
            System.arraycopy(objectArray2, n, objectArray, n + 1, this.columnCount - n);
            ((TableItem)object).cellFont = (Font[])objectArray;
        }
        System.arraycopy(this.columns, n, this.columns, n + 1, this.columnCount++ - n);
        this.columns[n] = tableColumn;
        this.ignoreColumnResize = true;
        if (n == 0) {
            if (this.columnCount > 1) {
                LVCOLUMN lVCOLUMN = new LVCOLUMN();
                lVCOLUMN.mask = 2;
                OS.SendMessage(this.handle, 4193, 1L, lVCOLUMN);
                OS.SendMessage(this.handle, 4191, 1L, lVCOLUMN);
                int n5 = lVCOLUMN.cx;
                int n6 = 1024;
                long l2 = OS.GetProcessHeap();
                int n7 = 2048;
                long l3 = OS.HeapAlloc(l2, 8, 2048);
                lVCOLUMN.mask = 23;
                lVCOLUMN.pszText = l3;
                lVCOLUMN.cchTextMax = 1024;
                OS.SendMessage(this.handle, 4191, 0L, lVCOLUMN);
                OS.SendMessage(this.handle, 4192, 1L, lVCOLUMN);
                lVCOLUMN.fmt = 2048;
                lVCOLUMN.cx = n5;
                lVCOLUMN.iImage = -2;
                LVCOLUMN lVCOLUMN2 = lVCOLUMN;
                LVCOLUMN lVCOLUMN3 = lVCOLUMN;
                boolean bl = false;
                lVCOLUMN3.cchTextMax = 0;
                lVCOLUMN2.pszText = 0L;
                OS.SendMessage(this.handle, 4192, 0L, lVCOLUMN);
                lVCOLUMN.mask = 1;
                lVCOLUMN.fmt = 0;
                OS.SendMessage(this.handle, 4192, 0L, lVCOLUMN);
                if (l3 != 0L) {
                    OS.HeapFree(l2, 0, l3);
                }
            } else {
                OS.SendMessage(this.handle, 4126, 0L, 0L);
            }
            if ((this.style & 0x10000000) == 0) {
                LVITEM lVITEM = new LVITEM();
                lVITEM.mask = 3;
                lVITEM.pszText = -1L;
                lVITEM.iImage = -1;
                int n8 = 0;
                while (n8 < n4) {
                    lVITEM.iItem = n8++;
                    OS.SendMessage(this.handle, 4172, 0L, lVITEM);
                }
            }
        } else {
            n2 = 0;
            if ((tableColumn.style & 0x1000000) == 0x1000000) {
                n2 = 2;
            }
            if ((tableColumn.style & 0x20000) == 131072) {
                n2 = 1;
            }
            object = new LVCOLUMN();
            ((LVCOLUMN)object).mask = 3;
            ((LVCOLUMN)object).fmt = n2;
            OS.SendMessage(this.handle, 4193, (long)n, (LVCOLUMN)object);
        }
        this.ignoreColumnResize = false;
        if (this.headerToolTipHandle != 0L && OS.SendMessage(this.hwndHeader, 4615, (long)n, rECT = new RECT()) != 0L) {
            int n9;
            TOOLINFO tOOLINFO = new TOOLINFO();
            tOOLINFO.cbSize = TOOLINFO.sizeof;
            tOOLINFO.uFlags = 16;
            tOOLINFO.hwnd = this.hwndHeader;
            TOOLINFO tOOLINFO2 = tOOLINFO;
            tableColumn.id = n9 = this.display.nextToolTipId++;
            tOOLINFO2.uId = n9;
            tOOLINFO.left = rECT.left;
            tOOLINFO.top = rECT.top;
            tOOLINFO.right = rECT.right;
            tOOLINFO.bottom = rECT.bottom;
            tOOLINFO.lpszText = -1L;
            OS.SendMessage(this.headerToolTipHandle, 1074, 0L, tOOLINFO);
        }
    }

    void createItem(TableItem tableItem, int n) {
        int n2 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        if (0 > n || n > n2) {
            this.error(6);
        }
        this._checkGrow(n2);
        LVITEM lVITEM = new LVITEM();
        lVITEM.mask = 3;
        lVITEM.iItem = n;
        lVITEM.pszText = -1L;
        lVITEM.iImage = -1;
        this.setDeferResize(true);
        boolean bl = true;
        this.ignoreShrink = true;
        this.ignoreSelect = true;
        int n3 = (int)OS.SendMessage(this.handle, 4173, 0L, lVITEM);
        boolean bl2 = false;
        this.ignoreShrink = false;
        this.ignoreSelect = false;
        if (n3 == -1) {
            this.error(14);
        }
        this._insertItem(n, tableItem, n2);
        this.setDeferResize(false);
        if (n2 == 0) {
            this.setScrollWidth(tableItem, false);
        }
    }

    @Override
    void createWidget() {
        super.createWidget();
        int n = -1;
        this.hotIndex = -1;
        this.itemHeight = -1;
        this._initItems();
        this.columns = new TableColumn[4];
    }

    @Override
    int defaultBackground() {
        return OS.GetSysColor(5);
    }

    @Override
    void deregister() {
        super.deregister();
        if (this.hwndHeader != 0L) {
            this.display.removeControl(this.hwndHeader);
        }
    }

    public void deselect(int[] nArray) {
        this.checkWidget();
        if (nArray == null) {
            this.error(4);
        }
        if (nArray.length == 0) {
            return;
        }
        LVITEM lVITEM = new LVITEM();
        lVITEM.stateMask = 2;
        for (int n : nArray) {
            if (n < 0) continue;
            this.ignoreSelect = true;
            OS.SendMessage(this.handle, 4139, (long)n, lVITEM);
            this.ignoreSelect = false;
        }
    }

    public void deselect(int n) {
        this.checkWidget();
        if (n < 0) {
            return;
        }
        LVITEM lVITEM = new LVITEM();
        lVITEM.stateMask = 2;
        this.ignoreSelect = true;
        OS.SendMessage(this.handle, 4139, (long)n, lVITEM);
        this.ignoreSelect = false;
    }

    public void deselect(int n, int n2) {
        this.checkWidget();
        int n3 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        if (n == 0 && n2 == n3 - 1) {
            this.deselectAll();
        } else {
            int n4;
            LVITEM lVITEM = new LVITEM();
            lVITEM.stateMask = 2;
            n = n4 = Math.max(0, n);
            while (n4 <= n2) {
                this.ignoreSelect = true;
                OS.SendMessage(this.handle, 4139, (long)n4, lVITEM);
                this.ignoreSelect = false;
                ++n4;
            }
        }
    }

    public void deselectAll() {
        this.checkWidget();
        LVITEM lVITEM = new LVITEM();
        lVITEM.mask = 8;
        lVITEM.stateMask = 2;
        this.ignoreSelect = true;
        OS.SendMessage(this.handle, 4139, -1L, lVITEM);
        this.ignoreSelect = false;
    }

    void destroyItem(TableColumn tableColumn) {
        int n;
        int n2;
        int n3;
        int n4;
        for (n4 = 0; n4 < this.columnCount && this.columns[n4] != tableColumn; ++n4) {
        }
        int n5 = (int)OS.SendMessage(this.handle, 4270, 0L, 0L);
        if (n5 == n4) {
            OS.SendMessage(this.handle, 4236, -1L, 0L);
        } else if (n5 > n4) {
            OS.SendMessage(this.handle, 4236, (long)(n5 - 1), 0L);
        }
        int[] nArray = new int[this.columnCount];
        OS.SendMessage(this.handle, 4155, (long)this.columnCount, nArray);
        for (n3 = 0; n3 < this.columnCount && nArray[n3] != n4; ++n3) {
        }
        this.ignoreColumnResize = true;
        boolean bl = false;
        if (n4 == 0) {
            bl = true;
            this.setRedraw(false);
            if (this.columnCount > 1) {
                n4 = 1;
                n2 = 1024;
                long l2 = OS.GetProcessHeap();
                int n6 = 2048;
                long l3 = OS.HeapAlloc(l2, 8, 2048);
                LVCOLUMN lVCOLUMN = new LVCOLUMN();
                lVCOLUMN.mask = 23;
                lVCOLUMN.pszText = l3;
                lVCOLUMN.cchTextMax = 1024;
                OS.SendMessage(this.handle, 4191, 1L, lVCOLUMN);
                LVCOLUMN lVCOLUMN2 = lVCOLUMN;
                lVCOLUMN2.fmt &= 0xFFFFFFFC;
                LVCOLUMN object = lVCOLUMN;
                object.fmt |= 0;
                OS.SendMessage(this.handle, 4192, 0L, lVCOLUMN);
                if (l3 != 0L) {
                    OS.HeapFree(l2, 0, l3);
                }
            } else {
                long l4 = OS.GetProcessHeap();
                long tableItem = OS.HeapAlloc(l4, 8, 2);
                LVCOLUMN lVCOLUMN = new LVCOLUMN();
                lVCOLUMN.mask = 23;
                lVCOLUMN.pszText = tableItem;
                lVCOLUMN.iImage = -2;
                lVCOLUMN.fmt = 0;
                OS.SendMessage(this.handle, 4192, 0L, lVCOLUMN);
                if (tableItem != 0L) {
                    OS.HeapFree(l4, 0, tableItem);
                }
                HDITEM hDITEM = new HDITEM();
                hDITEM.mask = 4;
                hDITEM.fmt = 0;
                long l6 = OS.SendMessage(this.handle, 4127, 0L, 0L);
                OS.SendMessage(l6, 4620, (long)n4, hDITEM);
            }
            this.setRedraw(true);
            if ((this.style & 0x10000000) == 0) {
                LVITEM lVITEM = new LVITEM();
                lVITEM.mask = 3;
                lVITEM.pszText = -1L;
                lVITEM.iImage = -1;
                int n7 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
                int nArray6 = 0;
                while (nArray6 < n7) {
                    lVITEM.iItem = nArray6++;
                    OS.SendMessage(this.handle, 4172, 0L, lVITEM);
                }
            }
        }
        if (this.columnCount > 1 && OS.SendMessage(this.handle, 4124, (long)n4, 0L) == 0L) {
            this.error(15);
        }
        if (bl) {
            n4 = 0;
        }
        System.arraycopy(this.columns, n4 + 1, this.columns, n4, --this.columnCount - n4);
        this.columns[this.columnCount] = null;
        n2 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        for (n = 0; n < n2; ++n) {
            TableItem tableItem = this._getItem(n, false);
            if (tableItem == null) continue;
            if (this.columnCount == 0) {
                tableItem.strings = null;
                tableItem.images = null;
                tableItem.cellBackground = null;
                tableItem.cellForeground = null;
                tableItem.cellFont = null;
                continue;
            }
            if (tableItem.strings != null) {
                String[] stringArray = tableItem.strings;
                if (n4 == 0) {
                    tableItem.text = stringArray[1] != null ? stringArray[1] : "";
                }
                String[] stringArray2 = new String[this.columnCount];
                System.arraycopy(stringArray, 0, stringArray2, 0, n4);
                System.arraycopy(stringArray, n4 + 1, stringArray2, n4, this.columnCount - n4);
                tableItem.strings = stringArray2;
            } else if (n4 == 0) {
                tableItem.text = "";
            }
            if (tableItem.images != null) {
                Image[] imageArray = tableItem.images;
                if (n4 == 0) {
                    tableItem.image = imageArray[1];
                }
                Image[] imageArray2 = new Image[this.columnCount];
                System.arraycopy(imageArray, 0, imageArray2, 0, n4);
                System.arraycopy(imageArray, n4 + 1, imageArray2, n4, this.columnCount - n4);
                tableItem.images = imageArray2;
            } else if (n4 == 0) {
                tableItem.image = null;
            }
            if (tableItem.cellBackground != null) {
                int[] nArray2 = tableItem.cellBackground;
                int[] nArray3 = new int[this.columnCount];
                System.arraycopy(nArray2, 0, nArray3, 0, n4);
                System.arraycopy(nArray2, n4 + 1, nArray3, n4, this.columnCount - n4);
                tableItem.cellBackground = nArray3;
            }
            if (tableItem.cellForeground != null) {
                int[] nArray4 = tableItem.cellForeground;
                int[] nArray5 = new int[this.columnCount];
                System.arraycopy(nArray4, 0, nArray5, 0, n4);
                System.arraycopy(nArray4, n4 + 1, nArray5, n4, this.columnCount - n4);
                tableItem.cellForeground = nArray5;
            }
            if (tableItem.cellFont == null) continue;
            Font[] fontArray = tableItem.cellFont;
            Font[] fontArray2 = new Font[this.columnCount];
            System.arraycopy(fontArray, 0, fontArray2, 0, n4);
            System.arraycopy(fontArray, n4 + 1, fontArray2, n4, this.columnCount - n4);
            tableItem.cellFont = fontArray2;
        }
        if (this.columnCount == 0) {
            this.setScrollWidth(null, true);
        }
        this.updateMoveable();
        this.ignoreColumnResize = false;
        if (this.columnCount != 0) {
            int n9;
            int n6;
            n2 = 0;
            n = nArray[n3];
            int[] nArray2 = new int[this.columnCount];
            int[] nArray7 = nArray;
            int n11 = nArray7.length;
            for (n6 = 0; n6 < n11; ++n6) {
                int n12 = nArray7[n6];
                if (n12 == n) continue;
                int n13 = n12 <= n ? n12 : n12 - 1;
                nArray2[n2++] = n13;
            }
            OS.SendMessage(this.handle, 4155, (long)this.columnCount, nArray);
            for (n9 = 0; n9 < nArray2.length && nArray[n9] == nArray2[n9]; ++n9) {
            }
            if (n9 != nArray2.length) {
                OS.SendMessage(this.handle, 4154, (long)nArray2.length, nArray2);
                OS.InvalidateRect(this.handle, null, true);
            }
            TableColumn[] tableColumnArray = new TableColumn[this.columnCount - n3];
            for (n6 = n3; n6 < nArray2.length; ++n6) {
                TableColumn tableColumn2 = this.columns[nArray2[n6]];
                tableColumnArray[n6 - n3] = tableColumn2;
                tableColumn2.updateToolTip(nArray2[n6]);
            }
            for (TableColumn tableColumn3 : tableColumnArray) {
                if (tableColumn3.isDisposed()) continue;
                tableColumn3.sendEvent(10);
            }
        }
        if (this.headerToolTipHandle != 0L) {
            TOOLINFO tOOLINFO = new TOOLINFO();
            tOOLINFO.cbSize = TOOLINFO.sizeof;
            tOOLINFO.uId = tableColumn.id;
            tOOLINFO.hwnd = OS.SendMessage(this.handle, 4127, 0L, 0L);
            OS.SendMessage(this.headerToolTipHandle, 1075, 0L, tOOLINFO);
        }
    }

    void destroyItem(TableItem tableItem) {
        int n;
        int n2 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        for (n = 0; n < n2 && this._getItem(n, false) != tableItem; ++n) {
        }
        if (n == n2) {
            return;
        }
        this.setDeferResize(true);
        boolean bl = true;
        this.ignoreShrink = true;
        this.ignoreSelect = true;
        long l2 = OS.SendMessage(this.handle, 4104, (long)n, 0L);
        boolean bl2 = false;
        this.ignoreShrink = false;
        this.ignoreSelect = false;
        if (l2 == 0L) {
            this.error(15);
        }
        this._removeItem(n, n2);
        if (--n2 == 0) {
            this.setTableEmpty();
        }
        this.setDeferResize(false);
    }

    void fixCheckboxImageList(boolean bl) {
        if ((this.style & 0x20) == 0) {
            return;
        }
        long l2 = OS.SendMessage(this.handle, 4098, 1L, 0L);
        if (l2 == 0L) {
            return;
        }
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.ImageList_GetIconSize(l2, nArray, nArray2);
        long l3 = OS.SendMessage(this.handle, 4098, 2L, 0L);
        if (l3 == 0L) {
            return;
        }
        int[] nArray3 = new int[]{0};
        int[] nArray4 = new int[]{0};
        OS.ImageList_GetIconSize(l3, nArray3, nArray4);
        if (nArray[0] == nArray3[0] && nArray2[0] == nArray4[0]) {
            return;
        }
        this.setCheckboxImageList(nArray[0], nArray2[0], bl);
    }

    void fixCheckboxImageListColor(boolean bl) {
        if ((this.style & 0x20) == 0) {
            return;
        }
        long l2 = OS.SendMessage(this.handle, 4098, 2L, 0L);
        if (l2 == 0L) {
            return;
        }
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.ImageList_GetIconSize(l2, nArray, nArray2);
        this.setCheckboxImageList(nArray[0], nArray2[0], bl);
    }

    public TableColumn getColumn(int n) {
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
        OS.SendMessage(this.handle, 4155, (long)this.columnCount, nArray);
        return nArray;
    }

    public TableColumn[] getColumns() {
        this.checkWidget();
        TableColumn[] tableColumnArray = new TableColumn[this.columnCount];
        System.arraycopy(this.columns, 0, tableColumnArray, 0, this.columnCount);
        return tableColumnArray;
    }

    int getFocusIndex() {
        return (int)OS.SendMessage(this.handle, 4108, -1L, 1L);
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

    public TableItem getItem(int n) {
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        if (0 > n || n >= n2) {
            this.error(6);
        }
        return this._getItem(n);
    }

    public TableItem getItem(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        return this.getItemInPixels(DPIUtil.autoScaleUp(point));
    }

    /*
     * Exception decompiling
     */
    TableItem getItemInPixels(Point var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl104 : ACONST_NULL - null : trying to set 0 previously set to 3
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
        return (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
    }

    public int getItemHeight() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getItemHeightInPixels());
    }

    int getItemHeightInPixels() {
        if (!this.painted && this.hooks(41)) {
            this.hitTestSelection(0, 0, 0);
        }
        long l2 = OS.SendMessage(this.handle, 4160, 0L, 0L);
        long l3 = OS.SendMessage(this.handle, 4160, 1L, 0L);
        return OS.HIWORD(l3) - OS.HIWORD(l2);
    }

    public TableItem[] getItems() {
        this.checkWidget();
        int n = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        TableItem[] tableItemArray = new TableItem[n];
        if ((this.style & 0x10000000) != 0) {
            for (int i = 0; i < n; ++i) {
                tableItemArray[i] = this._getItem(i);
            }
        } else {
            this._getItems(tableItemArray, n);
        }
        return tableItemArray;
    }

    public boolean getLinesVisible() {
        this.checkWidget();
        return this._getLinesVisible();
    }

    public TableItem[] getSelection() {
        this.checkWidget();
        int n = -1;
        int n2 = 0;
        int n3 = (int)OS.SendMessage(this.handle, 4146, 0L, 0L);
        TableItem[] tableItemArray = new TableItem[n3];
        while ((n = (int)OS.SendMessage(this.handle, 4108, (long)n, 2L)) != -1) {
            tableItemArray[n2++] = this._getItem(n);
        }
        return tableItemArray;
    }

    public int getSelectionCount() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 4146, 0L, 0L);
    }

    public int getSelectionIndex() {
        this.checkWidget();
        int n = (int)OS.SendMessage(this.handle, 4108, -1L, 1L);
        int n2 = (int)OS.SendMessage(this.handle, 4108, -1L, 2L);
        if (n == n2) {
            return n2;
        }
        int n3 = -1;
        while ((n3 = (int)OS.SendMessage(this.handle, 4108, (long)n3, 2L)) != -1) {
            if (n3 != n) continue;
            return n3;
        }
        return n2;
    }

    public int[] getSelectionIndices() {
        this.checkWidget();
        int n = (int)OS.SendMessage(this.handle, 4146, 0L, 0L);
        int[] nArray = new int[n];
        int n2 = -1;
        for (int i = 0; i < n && (n2 = (int)OS.SendMessage(this.handle, 4108, (long)n2, 2L)) != -1; ++i) {
            nArray[i] = n2;
        }
        return nArray;
    }

    public TableColumn getSortColumn() {
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

    public int getTopIndex() {
        this.checkWidget();
        return Math.max(0, (int)OS.SendMessage(this.handle, 4135, 0L, 0L));
    }

    int imageIndex(Image image, int n) {
        if (image == null) {
            return -2;
        }
        if (n == 0) {
            this.firstColumnImage = true;
        } else {
            this.setSubImagesVisible(true);
        }
        if (this.imageList == null) {
            Rectangle rectangle = image.getBoundsInPixels();
            this.imageList = this.display.getImageList(this.style & 0x4000000, rectangle.width, rectangle.height);
            int n2 = this.imageList.indexOf(image);
            if (n2 == -1) {
                n2 = this.imageList.add(image);
            }
            long l2 = this.imageList.getHandle();
            int n3 = this.getTopIndex();
            if (n3 != 0) {
                this.setRedraw(false);
                this.setTopIndex(0);
            }
            OS.SendMessage(this.handle, 4099, 1L, l2);
            if (this.headerImageList != null) {
                long l3 = this.headerImageList.getHandle();
                OS.SendMessage(this.hwndHeader, 4616, 0L, l3);
            }
            this.fixCheckboxImageList(false);
            this.setItemHeight(false);
            if (n3 != 0) {
                this.setTopIndex(n3);
                this.setRedraw(true);
            }
            return n2;
        }
        int n4 = this.imageList.indexOf(image);
        if (n4 != -1) {
            return n4;
        }
        return this.imageList.add(image);
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
            OS.SendMessage(this.hwndHeader, 4616, 0L, l2);
            return n;
        }
        int n = this.headerImageList.indexOf(image);
        if (n != -1) {
            return n;
        }
        return this.headerImageList.add(image);
    }

    public int indexOf(TableColumn tableColumn) {
        this.checkWidget();
        if (tableColumn == null) {
            this.error(4);
        }
        for (int i = 0; i < this.columnCount; ++i) {
            if (this.columns[i] != tableColumn) continue;
            return i;
        }
        return -1;
    }

    public int indexOf(TableItem tableItem) {
        this.checkWidget();
        if (tableItem == null) {
            this.error(4);
        }
        if (this.keys == null) {
            int n = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
            if (1 <= this.lastIndexOf && this.lastIndexOf < n - 1) {
                if (this._getItem(this.lastIndexOf, false) == tableItem) {
                    return this.lastIndexOf;
                }
                if (this._getItem(this.lastIndexOf + 1, false) == tableItem) {
                    return ++this.lastIndexOf;
                }
                if (this._getItem(this.lastIndexOf - 1, false) == tableItem) {
                    return --this.lastIndexOf;
                }
            }
            if (this.lastIndexOf < n / 2) {
                for (int i = 0; i < n; ++i) {
                    if (this._getItem(i, false) != tableItem) continue;
                    this.lastIndexOf = i;
                    return this.lastIndexOf;
                }
            } else {
                for (int i = n - 1; i >= 0; --i) {
                    if (this._getItem(i, false) != tableItem) continue;
                    this.lastIndexOf = i;
                    return this.lastIndexOf;
                }
            }
        } else {
            for (int i = 0; i < this.keyCount; ++i) {
                if (this.items[i] != tableItem) continue;
                return this.keys[i];
            }
        }
        return -1;
    }

    boolean isCustomToolTip() {
        return this.hooks(41);
    }

    /*
     * Exception decompiling
     */
    boolean isOptimizedRedraw() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl22 : IF_ICMPNE - null : Stack underflow
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

    public boolean isSelected(int n) {
        this.checkWidget();
        LVITEM lVITEM = new LVITEM();
        lVITEM.mask = 8;
        lVITEM.stateMask = 2;
        lVITEM.iItem = n;
        long l2 = OS.SendMessage(this.handle, 4171, 0L, lVITEM);
        return l2 != 0L && (lVITEM.state & 2) != 0;
    }

    boolean isUseWsBorder() {
        return super.isUseWsBorder() || this.display != null && this.display.useWsBorderTable;
    }

    @Override
    void register() {
        super.register();
        if (this.hwndHeader != 0L) {
            this.display.addControl(this.hwndHeader, this);
        }
    }

    @Override
    void releaseChildren(boolean bl) {
        int n;
        if (this != null) {
            TableItem tableItem;
            int n2;
            n = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
            if (this.keys == null) {
                for (n2 = 0; n2 < n; ++n2) {
                    tableItem = this._getItem(n2, false);
                    if (tableItem == null || tableItem.isDisposed()) continue;
                    tableItem.release(false);
                }
            } else {
                for (n2 = 0; n2 < this.keyCount; ++n2) {
                    tableItem = this.items[n2];
                    if (tableItem == null || tableItem.isDisposed()) continue;
                    tableItem.release(false);
                }
            }
            this._clearItems();
        }
        if (this.columns != null) {
            for (n = 0; n < this.columnCount; ++n) {
                TableColumn tableColumn = this.columns[n];
                if (tableColumn.isDisposed()) continue;
                tableColumn.release(false);
            }
            this.columns = null;
        }
        super.releaseChildren(bl);
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.customDraw = false;
        this.currentItem = null;
        if (this.imageList != null) {
            OS.SendMessage(this.handle, 4099, 1L, 0L);
            this.display.releaseImageList(this.imageList);
        }
        if (this.headerImageList != null) {
            OS.SendMessage(this.hwndHeader, 4616, 0L, 0L);
            this.display.releaseImageList(this.headerImageList);
        }
        Object var1_1 = null;
        this.headerImageList = var1_1;
        this.imageList = var1_1;
        long l2 = OS.SendMessage(this.handle, 4098, 2L, 0L);
        OS.SendMessage(this.handle, 4099, 2L, 0L);
        if (l2 != 0L) {
            OS.ImageList_Destroy(l2);
        }
        if (this.headerToolTipHandle != 0L) {
            OS.DestroyWindow(this.headerToolTipHandle);
        }
        this.headerToolTipHandle = 0L;
    }

    public void remove(int[] nArray) {
        this.checkWidget();
        if (nArray == null) {
            this.error(4);
        }
        if (nArray.length == 0) {
            return;
        }
        int[] nArray2 = new int[nArray.length];
        System.arraycopy(nArray, 0, nArray2, 0, nArray.length);
        this.sort(nArray2);
        int n = nArray2[nArray2.length - 1];
        int n2 = nArray2[0];
        int n3 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        if (0 > n || n > n2 || n2 >= n3) {
            this.error(6);
        }
        this.setDeferResize(true);
        int n4 = -1;
        for (int n5 : nArray2) {
            if (n5 == n4) continue;
            TableItem tableItem = this._getItem(n5, false);
            if (tableItem != null && !tableItem.isDisposed()) {
                tableItem.release(false);
            }
            boolean bl = true;
            this.ignoreShrink = true;
            this.ignoreSelect = true;
            long l2 = OS.SendMessage(this.handle, 4104, (long)n5, 0L);
            boolean bl2 = false;
            this.ignoreShrink = false;
            this.ignoreSelect = false;
            if (l2 == 0L) {
                this.error(15);
            }
            this._removeItem(n5, n3);
            --n3;
            n4 = n5;
        }
        if (n3 == 0) {
            this.setTableEmpty();
        }
        this.setDeferResize(false);
    }

    public void remove(int n) {
        TableItem tableItem;
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        if (0 > n || n >= n2) {
            this.error(6);
        }
        if ((tableItem = this._getItem(n, false)) != null && !tableItem.isDisposed()) {
            tableItem.release(false);
        }
        this.setDeferResize(true);
        boolean bl = true;
        this.ignoreShrink = true;
        this.ignoreSelect = true;
        long l2 = OS.SendMessage(this.handle, 4104, (long)n, 0L);
        boolean bl2 = false;
        this.ignoreShrink = false;
        this.ignoreSelect = false;
        if (l2 == 0L) {
            this.error(15);
        }
        this._removeItem(n, n2);
        if (--n2 == 0) {
            this.setTableEmpty();
        }
        this.setDeferResize(false);
    }

    public void remove(int n, int n2) {
        this.checkWidget();
        if (n > n2) {
            return;
        }
        int n3 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        if (0 > n || n > n2 || n2 >= n3) {
            this.error(6);
        }
        if (n == 0 && n2 == n3 - 1) {
            this.removeAll();
        } else {
            int n4;
            this.setDeferResize(true);
            for (n4 = n; n4 <= n2; ++n4) {
                TableItem tableItem = this._getItem(n4, false);
                if (tableItem != null && !tableItem.isDisposed()) {
                    tableItem.release(false);
                }
                boolean bl = true;
                this.ignoreShrink = true;
                this.ignoreSelect = true;
                long l2 = OS.SendMessage(this.handle, 4104, (long)n, 0L);
                boolean bl2 = false;
                this.ignoreShrink = false;
                this.ignoreSelect = false;
                if (l2 == 0L) break;
            }
            this._removeItems(n, n4, n3);
            if (n4 <= n2) {
                this.error(15);
            }
            this.setDeferResize(false);
        }
    }

    public void removeAll() {
        this.checkWidget();
        int n = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        for (int i = 0; i < n; ++i) {
            TableItem tableItem = this._getItem(i, false);
            if (tableItem == null || tableItem.isDisposed()) continue;
            tableItem.release(false);
        }
        this.setDeferResize(true);
        n = 1;
        this.ignoreShrink = true;
        this.ignoreSelect = true;
        long l2 = OS.SendMessage(this.handle, 4105, 0L, 0L);
        boolean bl = false;
        this.ignoreShrink = false;
        this.ignoreSelect = false;
        if (l2 == 0L) {
            this.error(15);
        }
        this.setTableEmpty();
        this.setDeferResize(false);
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

    public void select(int[] nArray) {
        int n;
        this.checkWidget();
        if (nArray == null) {
            this.error(4);
        }
        if ((n = nArray.length) == 0 || (this.style & 4) != 0 && n > 1) {
            return;
        }
        LVITEM lVITEM = new LVITEM();
        lVITEM.state = 2;
        lVITEM.stateMask = 2;
        for (int i = n - 1; i >= 0; --i) {
            if (nArray[i] < 0) continue;
            this.ignoreSelect = true;
            OS.SendMessage(this.handle, 4139, (long)nArray[i], lVITEM);
            this.ignoreSelect = false;
        }
    }

    @Override
    void reskinChildren(int n) {
        int n2;
        if (this != null) {
            n2 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
            for (int i = 0; i < n2; ++i) {
                TableItem tableItem = this._getItem(i, false);
                if (tableItem == null) continue;
                tableItem.reskin(n);
            }
        }
        if (this.columns != null) {
            for (n2 = 0; n2 < this.columnCount; ++n2) {
                TableColumn tableColumn = this.columns[n2];
                if (tableColumn.isDisposed()) continue;
                tableColumn.reskin(n);
            }
        }
        super.reskinChildren(n);
    }

    public void select(int n) {
        this.checkWidget();
        if (n < 0) {
            return;
        }
        LVITEM lVITEM = new LVITEM();
        lVITEM.state = 2;
        lVITEM.stateMask = 2;
        this.ignoreSelect = true;
        OS.SendMessage(this.handle, 4139, (long)n, lVITEM);
        this.ignoreSelect = false;
    }

    public void select(int n, int n2) {
        this.checkWidget();
        if (n2 < 0 || n > n2 || (this.style & 4) != 0 && n != n2) {
            return;
        }
        int n3 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        if (n3 == 0 || n >= n3) {
            return;
        }
        n = Math.max(0, n);
        n2 = Math.min(n2, n3 - 1);
        if (n == 0 && n2 == n3 - 1) {
            this.selectAll();
        } else {
            LVITEM lVITEM = new LVITEM();
            lVITEM.state = 2;
            lVITEM.stateMask = 2;
            for (int i = n; i <= n2; ++i) {
                this.ignoreSelect = true;
                OS.SendMessage(this.handle, 4139, (long)i, lVITEM);
                this.ignoreSelect = false;
            }
        }
    }

    public void selectAll() {
        this.checkWidget();
        if ((this.style & 4) != 0) {
            return;
        }
        LVITEM lVITEM = new LVITEM();
        lVITEM.mask = 8;
        lVITEM.state = 2;
        lVITEM.stateMask = 2;
        this.ignoreSelect = true;
        OS.SendMessage(this.handle, 4139, -1L, lVITEM);
        this.ignoreSelect = false;
    }

    void sendEraseItemEvent(TableItem tableItem, NMLVCUSTOMDRAW nMLVCUSTOMDRAW, long l2, Event event) {
        RECT rECT;
        boolean bl;
        Object object;
        int n;
        int n2;
        int n3;
        long l3 = nMLVCUSTOMDRAW.hdc;
        int n4 = n3 = tableItem.cellForeground != null ? tableItem.cellForeground[nMLVCUSTOMDRAW.iSubItem] : -1;
        if (n3 == -1) {
            n3 = tableItem.foreground;
        }
        int n5 = n2 = tableItem.cellBackground != null ? tableItem.cellBackground[nMLVCUSTOMDRAW.iSubItem] : -1;
        if (n2 == -1) {
            n2 = tableItem.background;
        }
        LVITEM lVITEM = new LVITEM();
        lVITEM.mask = 8;
        lVITEM.stateMask = 2;
        lVITEM.iItem = (int)nMLVCUSTOMDRAW.dwItemSpec;
        long l4 = OS.SendMessage(this.handle, 4171, 0L, lVITEM);
        boolean bl2 = l4 != 0L && (lVITEM.state & 2) != 0;
        GCData gCData = new GCData();
        gCData.device = this.display;
        int n6 = -1;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        if (nMLVCUSTOMDRAW.iSubItem == 0 || (this.style & 0x10000) != 0) {
            bl5 = (long)this.hotIndex == nMLVCUSTOMDRAW.dwItemSpec;
            boolean bl7 = bl6 = (nMLVCUSTOMDRAW.uItemState & 0x1000) != 0;
        }
        if (OS.IsWindowEnabled(this.handle)) {
            if (bl2 && (nMLVCUSTOMDRAW.iSubItem == 0 || (this.style & 0x10000) != 0)) {
                if (OS.GetFocus() == this.handle || this.display.getHighContrast()) {
                    bl3 = true;
                    gCData.foreground = OS.GetSysColor(14);
                    n6 = gCData.background = OS.GetSysColor(13);
                } else {
                    bl3 = (this.style & 0x8000) == 0;
                    gCData.foreground = OS.GetTextColor(l3);
                    n6 = gCData.background = OS.GetSysColor(15);
                }
                if (this.explorerTheme) {
                    gCData.foreground = n3 != -1 ? n3 : this.getForegroundPixel();
                }
            } else {
                boolean bl8 = bl4 = n2 != -1;
                if (n3 == -1 || n2 == -1) {
                    Control control = this.findBackgroundControl();
                    if (control == null) {
                        control = this;
                    }
                    if (n3 == -1) {
                        n3 = control.getForegroundPixel();
                    }
                    if (n2 == -1) {
                        n2 = control.getBackgroundPixel();
                    }
                }
                gCData.foreground = n3 != -1 ? n3 : OS.GetTextColor(l3);
                gCData.background = n2 != -1 ? n2 : OS.GetBkColor(l3);
            }
        } else {
            gCData.foreground = OS.GetSysColor(17);
            gCData.background = OS.GetSysColor(15);
            if (bl2) {
                n6 = gCData.background;
            }
        }
        gCData.font = tableItem.getFont(nMLVCUSTOMDRAW.iSubItem);
        gCData.uiState = (int)OS.SendMessage(this.handle, 297, 0L, 0L);
        int n7 = OS.SaveDC(l3);
        GC gC = GC.win32_new(l3, gCData);
        RECT rECT2 = tableItem.getBounds((int)nMLVCUSTOMDRAW.dwItemSpec, nMLVCUSTOMDRAW.iSubItem, true, true, true, true, l3);
        Event event2 = new Event();
        event2.item = tableItem;
        event2.gc = gC;
        event2.index = nMLVCUSTOMDRAW.iSubItem;
        Event event3 = event2;
        event3.detail |= 0x10;
        if (OS.SendMessage(this.handle, 4108, -1L, 1L) == nMLVCUSTOMDRAW.dwItemSpec && (nMLVCUSTOMDRAW.iSubItem == 0 || (this.style & 0x10000) != 0) && this.handle == OS.GetFocus() && ((n = (int)OS.SendMessage(this.handle, 297, 0L, 0L)) & 1) == 0) {
            object = event2;
            ((Event)object).detail |= 4;
        }
        int n8 = n = (event2.detail & 4) != 0 ? 1 : 0;
        if (bl5) {
            object = event2;
            ((Event)object).detail |= 0x20;
        }
        if (bl3) {
            object = event2;
            ((Event)object).detail |= 2;
        }
        if (bl4) {
            object = event2;
            ((Event)object).detail |= 8;
        }
        object = new Rectangle(rECT2.left, rECT2.top, rECT2.right - rECT2.left, rECT2.bottom - rECT2.top);
        event2.setBoundsInPixels((Rectangle)object);
        gC.setClipping(DPIUtil.autoScaleDown((Rectangle)object));
        this.sendEvent(40, event2);
        event2.gc = null;
        int n9 = gCData.foreground;
        gC.dispose();
        OS.RestoreDC(l3, n7);
        if (this.isDisposed() || tableItem.isDisposed()) {
            return;
        }
        if (event2.doit) {
            this.ignoreDrawForeground = (event2.detail & 0x10) == 0;
            this.ignoreDrawBackground = (event2.detail & 8) == 0;
            this.ignoreDrawSelection = (event2.detail & 2) == 0;
            this.ignoreDrawFocus = (event2.detail & 4) == 0;
            this.ignoreDrawHot = (event2.detail & 0x20) == 0;
        } else {
            bl = true;
            this.ignoreDrawHot = true;
            this.ignoreDrawFocus = true;
            this.ignoreDrawSelection = true;
            this.ignoreDrawBackground = true;
            this.ignoreDrawForeground = true;
        }
        if (bl3) {
            if (this.ignoreDrawSelection) {
                this.ignoreDrawHot = true;
                if (nMLVCUSTOMDRAW.iSubItem == 0 || (this.style & 0x10000) != 0) {
                    this.selectionForeground = n9;
                }
                nMLVCUSTOMDRAW.uItemState &= 0xFFFFFFFE;
                OS.MoveMemory(l2, nMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
            }
        } else if (this.ignoreDrawSelection) {
            nMLVCUSTOMDRAW.uItemState |= 1;
            OS.MoveMemory(l2, nMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
        }
        boolean bl9 = bl = (long)nMLVCUSTOMDRAW.iSubItem == OS.SendMessage(this.hwndHeader, 4623, 0L, 0L);
        if (this.ignoreDrawForeground && this.ignoreDrawHot && !bl6 && !this.ignoreDrawBackground && bl4) {
            rECT = tableItem.getBounds((int)nMLVCUSTOMDRAW.dwItemSpec, nMLVCUSTOMDRAW.iSubItem, true, false, true, false, l3);
            this.fillBackground(l3, n2, rECT);
        }
        this.focusRect = null;
        if (!this.ignoreDrawHot || !this.ignoreDrawSelection || !this.ignoreDrawFocus || bl6) {
            boolean bl10 = (this.style & 0x10000) != 0 || !bl;
            RECT rECT3 = tableItem.getBounds((int)nMLVCUSTOMDRAW.dwItemSpec, nMLVCUSTOMDRAW.iSubItem, true, false, bl10, false, l3);
            if ((this.style & 0x10000) == 0) {
                if (event != null) {
                    Rectangle rectangle = event.getBoundsInPixels();
                    rECT3.right = Math.min(rECT2.right, rectangle.x + rectangle.width);
                }
                if (!this.ignoreDrawFocus) {
                    nMLVCUSTOMDRAW.uItemState &= 0xFFFFFFEF;
                    OS.MoveMemory(l2, nMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
                    this.focusRect = rECT3;
                }
            }
            if (this.explorerTheme) {
                boolean bl11;
                boolean bl12 = bl11 = !this.ignoreDrawHot || bl6 || !this.ignoreDrawSelection && n6 != -1;
                if (bl11) {
                    int n10;
                    RECT rECT4 = new RECT();
                    OS.SetRect(rECT4, nMLVCUSTOMDRAW.left, nMLVCUSTOMDRAW.top, nMLVCUSTOMDRAW.right, nMLVCUSTOMDRAW.bottom);
                    RECT rECT5 = new RECT();
                    OS.SetRect(rECT5, nMLVCUSTOMDRAW.left, nMLVCUSTOMDRAW.top, nMLVCUSTOMDRAW.right, nMLVCUSTOMDRAW.bottom);
                    if ((this.style & 0x10000) != 0) {
                        int n11 = (int)OS.SendMessage(this.hwndHeader, 4608, 0L, 0L);
                        int n12 = (int)OS.SendMessage(this.hwndHeader, 4623, (long)(n11 - 1), 0L);
                        RECT rECT6 = new RECT();
                        OS.SendMessage(this.hwndHeader, 4615, (long)n12, rECT6);
                        OS.MapWindowPoints(this.hwndHeader, this.handle, rECT6, 2);
                        rECT5.right = rECT6.right;
                        n12 = (int)OS.SendMessage(this.hwndHeader, 4623, 0L, 0L);
                        OS.SendMessage(this.hwndHeader, 4615, (long)n12, rECT6);
                        OS.MapWindowPoints(this.hwndHeader, this.handle, rECT6, 2);
                        rECT5.left = rECT6.left;
                        rECT4.left = rECT2.left;
                        RECT rECT7 = rECT4;
                        rECT7.right += 2;
                    } else {
                        RECT rECT8 = rECT5;
                        rECT8.right += 2;
                        RECT rECT9 = rECT4;
                        rECT9.right += 2;
                    }
                    long l5 = OS.OpenThemeData(this.handle, Display.TREEVIEW);
                    int n13 = n10 = bl2 ? 3 : 2;
                    if (OS.GetFocus() != this.handle && bl2 && !bl5) {
                        n10 = 5;
                    }
                    if (bl6) {
                        n10 = 3;
                    }
                    OS.DrawThemeBackground(l5, l3, 1, n10, rECT5, rECT4);
                    OS.CloseThemeData(l5);
                }
            } else if (!this.ignoreDrawSelection && n6 != -1) {
                this.fillBackground(l3, n6, rECT3);
            }
        }
        if (n != 0 && this.ignoreDrawFocus) {
            nMLVCUSTOMDRAW.uItemState &= 0xFFFFFFEF;
            OS.MoveMemory(l2, nMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
        }
        if (this.ignoreDrawForeground) {
            rECT = tableItem.getBounds((int)nMLVCUSTOMDRAW.dwItemSpec, nMLVCUSTOMDRAW.iSubItem, true, true, true, false, l3);
            OS.SaveDC(l3);
            OS.SelectClipRgn(l3, 0L);
            OS.ExcludeClipRect(l3, rECT.left, rECT.top, rECT.right, rECT.bottom);
        }
    }

    Event sendEraseItemEvent(TableItem tableItem, NMTTCUSTOMDRAW nMTTCUSTOMDRAW, int n, RECT rECT) {
        int n2 = OS.SaveDC(nMTTCUSTOMDRAW.hdc);
        RECT rECT2 = this.toolTipInset(rECT);
        OS.SetWindowOrgEx(nMTTCUSTOMDRAW.hdc, rECT2.left, rECT2.top, null);
        GCData gCData = new GCData();
        gCData.device = this.display;
        gCData.foreground = OS.GetTextColor(nMTTCUSTOMDRAW.hdc);
        gCData.background = OS.GetBkColor(nMTTCUSTOMDRAW.hdc);
        gCData.font = tableItem.getFont(n);
        gCData.uiState = (int)OS.SendMessage(this.handle, 297, 0L, 0L);
        GC gC = GC.win32_new(nMTTCUSTOMDRAW.hdc, gCData);
        Event event = new Event();
        event.item = tableItem;
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

    Event sendMeasureItemEvent(TableItem tableItem, int n, int n2, long l2) {
        long l3;
        Object object;
        GCData gCData = new GCData();
        gCData.device = this.display;
        gCData.font = tableItem.getFont(n2);
        int n3 = OS.SaveDC(l2);
        GC gC = GC.win32_new(l2, gCData);
        RECT rECT = tableItem.getBounds(n, n2, true, true, false, false, l2);
        Event event = new Event();
        event.item = tableItem;
        event.gc = gC;
        event.index = n2;
        event.setBoundsInPixels(new Rectangle(rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top));
        boolean bl = false;
        if (OS.IsWindowEnabled(this.handle)) {
            boolean bl2;
            object = new LVITEM();
            ((LVITEM)object).mask = 8;
            ((LVITEM)object).stateMask = 2;
            ((LVITEM)object).iItem = n;
            l3 = OS.SendMessage(this.handle, 4171, 0L, (LVITEM)object);
            boolean bl3 = bl2 = l3 != 0L && (((LVITEM)object).state & 2) != 0;
            if (bl2 && (n2 == 0 || (this.style & 0x10000) != 0)) {
                boolean bl4 = bl = OS.GetFocus() == this.handle || this.display.getHighContrast() || (this.style & 0x8000) == 0;
            }
        }
        if (bl) {
            object = event;
            ((Event)object).detail |= 2;
        }
        this.sendEvent(41, event);
        event.gc = null;
        gC.dispose();
        OS.RestoreDC(l2, n3);
        if (!this.isDisposed() && !tableItem.isDisposed()) {
            int n4;
            object = event.getBoundsInPixels();
            if (this.columnCount == 0 && ((Rectangle)object).x + ((Rectangle)object).width > (n4 = (int)OS.SendMessage(this.handle, 4125, 0L, 0L))) {
                this.setScrollWidth(((Rectangle)object).x + ((Rectangle)object).width);
            }
            l3 = OS.SendMessage(this.handle, 4160, 0L, 0L);
            long l4 = OS.SendMessage(this.handle, 4160, 1L, 0L);
            int n5 = OS.HIWORD(l4) - OS.HIWORD(l3);
            if (!this.settingItemHeight && ((Rectangle)object).height > n5) {
                this.settingItemHeight = true;
                this.setItemHeight(((Rectangle)object).height);
                this.settingItemHeight = false;
            }
        }
        return event;
    }

    LRESULT sendMouseDownEvent(int n, int n2, int n3, long l2, long l3) {
        boolean bl;
        int n4;
        Display display = this.display;
        display.captureChanged = false;
        if (!this.sendMouseEvent(n, n2, this.handle, l3)) {
            if (!display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
                OS.SetCapture(this.handle);
            }
            return LRESULT.ZERO;
        }
        LVHITTESTINFO lVHITTESTINFO = new LVHITTESTINFO();
        lVHITTESTINFO.x = OS.GET_X_LPARAM(l3);
        lVHITTESTINFO.y = OS.GET_Y_LPARAM(l3);
        OS.SendMessage(this.handle, 4114, 0L, lVHITTESTINFO);
        if ((this.style & 0x10000) == 0 && this.hooks(41)) {
            if (OS.SendMessage(this.handle, 4153, 0L, lVHITTESTINFO) < 0L) {
                n4 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
                if (n4 != 0) {
                    RECT rECT = new RECT();
                    rECT.left = 1;
                    this.ignoreCustomDraw = true;
                    long l4 = OS.SendMessage(this.handle, 4110, 0L, rECT);
                    this.ignoreCustomDraw = false;
                    if (l4 != 0L) {
                        lVHITTESTINFO.x = rECT.left;
                        OS.SendMessage(this.handle, 4153, 0L, lVHITTESTINFO);
                        if (lVHITTESTINFO.iItem < 0) {
                            lVHITTESTINFO.iItem = -1;
                        }
                        LVHITTESTINFO lVHITTESTINFO2 = lVHITTESTINFO;
                        lVHITTESTINFO2.flags &= 0xFFFFFFF9;
                    }
                }
            } else if (lVHITTESTINFO.iSubItem != 0) {
                lVHITTESTINFO.iItem = -1;
            }
        }
        if (((this.style & 4) != 0 || this.hooks(3) || this.hooks(4)) && lVHITTESTINFO.iItem == -1) {
            if (!display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
                OS.SetCapture(this.handle);
            }
            OS.SetFocus(this.handle);
            return LRESULT.ZERO;
        }
        n4 = 0;
        int n5 = (int)OS.SendMessage(this.handle, 4146, 0L, 0L);
        if (n5 == 1 && lVHITTESTINFO.iItem != -1) {
            LVITEM lVITEM = new LVITEM();
            lVITEM.mask = 8;
            lVITEM.stateMask = 2;
            lVITEM.iItem = lVHITTESTINFO.iItem;
            OS.SendMessage(this.handle, 4171, 0L, lVITEM);
            if ((lVITEM.state & 2) != 0) {
                n4 = 1;
            }
        }
        this.fullRowSelect = false;
        if (lVHITTESTINFO.iItem != -1 && (this.style & 0x10000) == 0 && this.hooks(41)) {
            this.fullRowSelect = this.hitTestSelection(lVHITTESTINFO.iItem, lVHITTESTINFO.x, lVHITTESTINFO.y);
            if (this.fullRowSelect) {
                int n6 = 6;
                if ((lVHITTESTINFO.flags & 6) != 0) {
                    this.fullRowSelect = false;
                }
            }
        }
        boolean bl2 = bl = (this.state & 0x8000) != 0 && this.hooks(29);
        if (!bl) {
            int n7 = 6;
            boolean bl3 = bl = lVHITTESTINFO.iItem == -1 || (lVHITTESTINFO.flags & 6) == 0;
            if (this.fullRowSelect) {
                bl = true;
            }
        }
        if (this.fullRowSelect) {
            OS.UpdateWindow(this.handle);
            OS.DefWindowProc(this.handle, 11, 0L, 0L);
            OS.SendMessage(this.handle, 4150, 32L, 32L);
        }
        this.dragStarted = false;
        display.dragCancelled = false;
        if (!bl) {
            display.runDragDrop = false;
        }
        long l5 = this.callWindowProc(this.handle, n3, l2, l3, n4 != 0);
        if (!bl) {
            display.runDragDrop = true;
        }
        if (this.fullRowSelect) {
            this.fullRowSelect = false;
            OS.DefWindowProc(this.handle, 11, 1L, 0L);
            OS.SendMessage(this.handle, 4150, 32L, 0L);
        }
        if (this.dragStarted || display.dragCancelled) {
            if (!display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
                OS.SetCapture(this.handle);
            }
        } else {
            boolean bl4;
            int n8 = 6;
            boolean bl5 = bl4 = (lVHITTESTINFO.flags & 6) != 0;
            if (!bl4 && (this.style & 2) != 0) {
                boolean bl6 = bl4 = (lVHITTESTINFO.flags & 8) == 0;
            }
            if (bl4) {
                this.sendMouseEvent(4, n2, this.handle, l3);
            }
        }
        return new LRESULT(l5);
    }

    void sendPaintItemEvent(TableItem tableItem, NMLVCUSTOMDRAW nMLVCUSTOMDRAW) {
        int n;
        Object object;
        int n2;
        long l2 = nMLVCUSTOMDRAW.hdc;
        GCData gCData = new GCData();
        gCData.device = this.display;
        gCData.font = tableItem.getFont(nMLVCUSTOMDRAW.iSubItem);
        LVITEM lVITEM = new LVITEM();
        lVITEM.mask = 8;
        lVITEM.stateMask = 2;
        lVITEM.iItem = (int)nMLVCUSTOMDRAW.dwItemSpec;
        long l3 = OS.SendMessage(this.handle, 4171, 0L, lVITEM);
        boolean bl = l3 != 0L && (lVITEM.state & 2) != 0;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        if (nMLVCUSTOMDRAW.iSubItem == 0 || (this.style & 0x10000) != 0) {
            boolean bl5 = bl4 = (long)this.hotIndex == nMLVCUSTOMDRAW.dwItemSpec;
        }
        if (OS.IsWindowEnabled(this.handle)) {
            if (bl && (nMLVCUSTOMDRAW.iSubItem == 0 || (this.style & 0x10000) != 0)) {
                if (OS.GetFocus() == this.handle || this.display.getHighContrast()) {
                    bl2 = true;
                    gCData.foreground = this.selectionForeground != -1 ? this.selectionForeground : OS.GetSysColor(14);
                    gCData.background = OS.GetSysColor(13);
                } else {
                    bl2 = (this.style & 0x8000) == 0;
                    gCData.foreground = OS.GetTextColor(l2);
                    gCData.background = OS.GetSysColor(15);
                }
                if (this.explorerTheme && this.selectionForeground == -1) {
                    int n3 = n2 = tableItem.cellForeground != null ? tableItem.cellForeground[nMLVCUSTOMDRAW.iSubItem] : -1;
                    if (n2 == -1) {
                        n2 = tableItem.foreground;
                    }
                    gCData.foreground = n2 != -1 ? n2 : this.getForegroundPixel();
                }
            } else {
                int n4;
                int n5 = n2 = tableItem.cellForeground != null ? tableItem.cellForeground[nMLVCUSTOMDRAW.iSubItem] : -1;
                if (n2 == -1) {
                    n2 = tableItem.foreground;
                }
                int n6 = n4 = tableItem.cellBackground != null ? tableItem.cellBackground[nMLVCUSTOMDRAW.iSubItem] : -1;
                if (n4 == -1) {
                    n4 = tableItem.background;
                }
                boolean bl6 = bl3 = n4 != -1;
                if (n2 == -1 || n4 == -1) {
                    object = this.findBackgroundControl();
                    if (object == null) {
                        object = this;
                    }
                    if (n2 == -1) {
                        n2 = ((Control)object).getForegroundPixel();
                    }
                    if (n4 == -1) {
                        n4 = ((Control)object).getBackgroundPixel();
                    }
                }
                gCData.foreground = n2 != -1 ? n2 : OS.GetTextColor(l2);
                gCData.background = n4 != -1 ? n4 : OS.GetBkColor(l2);
            }
        } else {
            gCData.foreground = OS.GetSysColor(17);
            gCData.background = OS.GetSysColor(15);
        }
        gCData.uiState = (int)OS.SendMessage(this.handle, 297, 0L, 0L);
        n2 = OS.SaveDC(l2);
        GC gC = GC.win32_new(l2, gCData);
        object = tableItem.getBounds((int)nMLVCUSTOMDRAW.dwItemSpec, nMLVCUSTOMDRAW.iSubItem, true, true, false, false, l2);
        Event event = new Event();
        event.item = tableItem;
        event.gc = gC;
        event.index = nMLVCUSTOMDRAW.iSubItem;
        Event event2 = event;
        event2.detail |= 0x10;
        if (OS.SendMessage(this.handle, 4108, -1L, 1L) == nMLVCUSTOMDRAW.dwItemSpec && (nMLVCUSTOMDRAW.iSubItem == 0 || (this.style & 0x10000) != 0) && this.handle == OS.GetFocus() && ((n = (int)OS.SendMessage(this.handle, 297, 0L, 0L)) & 1) == 0) {
            Event event3 = event;
            event3.detail |= 4;
        }
        if (bl4) {
            Event event4 = event;
            event4.detail |= 0x20;
        }
        if (bl2) {
            Event event5 = event;
            event5.detail |= 2;
        }
        if (bl3) {
            Event event6 = event;
            event6.detail |= 8;
        }
        event.setBoundsInPixels(new Rectangle(((RECT)object).left, ((RECT)object).top, ((RECT)object).right - ((RECT)object).left, ((RECT)object).bottom - ((RECT)object).top));
        RECT rECT = tableItem.getBounds((int)nMLVCUSTOMDRAW.dwItemSpec, nMLVCUSTOMDRAW.iSubItem, true, true, true, true, l2);
        int n7 = rECT.right - rECT.left;
        int n8 = rECT.bottom - rECT.top;
        gC.setClipping(DPIUtil.autoScaleDown(new Rectangle(rECT.left, rECT.top, n7, n8)));
        this.sendEvent(42, event);
        if (gCData.focusDrawn) {
            this.focusRect = null;
        }
        event.gc = null;
        gC.dispose();
        OS.RestoreDC(l2, n2);
    }

    Event sendPaintItemEvent(TableItem tableItem, NMTTCUSTOMDRAW nMTTCUSTOMDRAW, int n, RECT rECT) {
        int n2 = OS.SaveDC(nMTTCUSTOMDRAW.hdc);
        RECT rECT2 = this.toolTipInset(rECT);
        OS.SetWindowOrgEx(nMTTCUSTOMDRAW.hdc, rECT2.left, rECT2.top, null);
        GCData gCData = new GCData();
        gCData.device = this.display;
        gCData.font = tableItem.getFont(n);
        gCData.foreground = OS.GetTextColor(nMTTCUSTOMDRAW.hdc);
        gCData.background = OS.GetBkColor(nMTTCUSTOMDRAW.hdc);
        gCData.uiState = (int)OS.SendMessage(this.handle, 297, 0L, 0L);
        GC gC = GC.win32_new(nMTTCUSTOMDRAW.hdc, gCData);
        Event event = new Event();
        event.item = tableItem;
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
            this.setBackgroundTransparent(true);
        } else if (!(this.hooks(41) || this.hooks(40) || this.hooks(42))) {
            this.setBackgroundTransparent(false);
        }
    }

    @Override
    void setBackgroundPixel(int n) {
        int n2 = (int)OS.SendMessage(this.handle, 4096, 0L, 0L);
        if (n2 != -1) {
            if (this.findImageControl() != null) {
                return;
            }
            if (n == -1) {
                n = this.defaultBackground();
            }
            if (n2 != n) {
                OS.SendMessage(this.handle, 4097, 0L, n);
                OS.SendMessage(this.handle, 4134, 0L, n);
                if ((this.style & 0x20) != 0) {
                    this.fixCheckboxImageListColor(true);
                }
            }
        }
        OS.InvalidateRect(this.handle, null, true);
    }

    void setBackgroundTransparent(boolean bl) {
        int n = (int)OS.SendMessage(this.handle, 4096, 0L, 0L);
        if (bl) {
            if (n != -1) {
                OS.SendMessage(this.handle, 4097, 0L, -1L);
                OS.SendMessage(this.handle, 4134, 0L, -1L);
                OS.InvalidateRect(this.handle, null, true);
                if (!this.explorerTheme && (this.style & 0x10000) != 0) {
                    int n2 = 32;
                    OS.SendMessage(this.handle, 4150, 32L, 0L);
                }
                if ((this.sortDirection & 0x480) != 0 && this.sortColumn != null && !this.sortColumn.isDisposed()) {
                    OS.SendMessage(this.handle, 4236, -1L, 0L);
                    OS.InvalidateRect(this.handle, null, true);
                }
            }
        } else if (n == -1) {
            int n3;
            Control control = this.findBackgroundControl();
            if (control == null) {
                control = this;
            }
            if (control.backgroundImage == null) {
                n3 = control.getBackgroundPixel();
                OS.SendMessage(this.handle, 4097, 0L, n3);
                OS.SendMessage(this.handle, 4134, 0L, n3);
                if ((this.style & 0x20) != 0) {
                    this.fixCheckboxImageListColor(true);
                }
                OS.InvalidateRect(this.handle, null, true);
            }
            if (!(this.explorerTheme || (this.style & 0x10000) == 0 || this.hooks(40) || this.hooks(42))) {
                n3 = 32;
                OS.SendMessage(this.handle, 4150, 32L, 32L);
            }
            if ((this.sortDirection & 0x480) != 0 && this.sortColumn != null && !this.sortColumn.isDisposed() && (n3 = this.indexOf(this.sortColumn)) != -1) {
                OS.SendMessage(this.handle, 4236, (long)n3, 0L);
                OS.InvalidateRect(this.handle, null, true);
            }
        }
    }

    @Override
    void setBoundsInPixels(int n, int n2, int n3, int n4, int n5, boolean bl) {
        this.setDeferResize(true);
        super.setBoundsInPixels(n, n2, n3, n4, n5, false);
        this.setDeferResize(false);
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
        OS.SendMessage(this.handle, 4155, (long)this.columnCount, nArray2);
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
            OS.SendMessage(this.handle, 4154, (long)nArray.length, nArray);
            OS.InvalidateRect(this.handle, null, true);
            TableColumn[] tableColumnArray = new TableColumn[this.columnCount];
            System.arraycopy(this.columns, 0, tableColumnArray, 0, this.columnCount);
            RECT rECT = new RECT();
            for (int i = 0; i < this.columnCount; ++i) {
                TableColumn tableColumn = tableColumnArray[i];
                if (tableColumn.isDisposed()) continue;
                OS.SendMessage(this.hwndHeader, 4615, (long)i, rECT);
                if (rECT.left == rECTArray[i].left) continue;
                tableColumn.updateToolTip(i);
                tableColumn.sendEvent(10);
            }
        }
    }

    void setCustomDraw(boolean bl) {
        if (this.customDraw == bl) {
            return;
        }
        if (!this.customDraw && bl && this.currentItem != null) {
            OS.InvalidateRect(this.handle, null, true);
        }
        this.customDraw = bl;
    }

    void setDeferResize(boolean bl) {
        if (bl) {
            if (this.resizeCount++ == 0) {
                this.wasResized = false;
                if ((this.hooks(41) || this.hooks(40) || this.hooks(42)) && this.drawCount++ == 0 && OS.IsWindowVisible(this.handle)) {
                    OS.DefWindowProc(this.handle, 11, 0L, 0L);
                    OS.SendMessage(this.handle, 4097, 0L, 0xFFFFFFL);
                }
            }
        } else if (--this.resizeCount == 0) {
            if ((this.hooks(41) || this.hooks(40) || this.hooks(42)) && --this.drawCount == 0) {
                OS.SendMessage(this.handle, 4097, 0L, -1L);
                OS.DefWindowProc(this.handle, 11, 1L, 0L);
                int n = 1157;
                OS.RedrawWindow(this.handle, null, 0L, 1157);
            }
            if (this.wasResized) {
                this.wasResized = false;
                this.setResizeChildren(false);
                this.sendEvent(11);
                if (this.isDisposed()) {
                    return;
                }
                if (this.layout != null) {
                    this.markLayout(false, false);
                    this.updateLayout(false, false);
                }
                this.setResizeChildren(true);
            }
        }
    }

    void setCheckboxImageList(int n, int n2, boolean bl) {
        RECT rECT;
        int n3;
        if ((this.style & 0x20) == 0) {
            return;
        }
        int n4 = 8;
        int n5 = 32;
        if ((this.style & 0x4000000) != 0) {
            n5 |= 0x2000;
        }
        if (!OS.IsAppThemed()) {
            n5 |= 1;
        }
        long l2 = OS.ImageList_Create(n, n2, n5, 8, 8);
        long l3 = OS.GetDC(this.handle);
        long l4 = OS.CreateCompatibleDC(l3);
        long l5 = OS.CreateCompatibleBitmap(l3, n * 8, n2);
        long l6 = OS.SelectObject(l4, l5);
        RECT rECT2 = new RECT();
        OS.SetRect(rECT2, 0, 0, n * 8, n2);
        if (OS.IsAppThemed()) {
            Control control = this.findBackgroundControl();
            if (control == null) {
                control = this;
            }
            n3 = control.getBackgroundPixel();
        } else {
            n3 = 0x20000FF;
            if ((n3 & 0xFFFFFF) == OS.GetSysColor(5)) {
                n3 = 0x200FF00;
            }
        }
        long l7 = OS.CreateSolidBrush(n3);
        OS.FillRect(l4, rECT2, l7);
        OS.DeleteObject(l7);
        long l8 = OS.SelectObject(l3, this.defaultFont());
        TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
        OS.GetTextMetrics(l3, tEXTMETRIC);
        OS.SelectObject(l3, l8);
        int n6 = Math.min(tEXTMETRIC.tmHeight, n);
        int n7 = Math.min(tEXTMETRIC.tmHeight, n2);
        if (OS.IsAppThemed()) {
            SIZE sIZE = new SIZE();
            OS.GetThemePartSize(this.display.hButtonTheme(), l4, 3, 0, null, 1, sIZE);
            n6 = Math.min(sIZE.cx, n6);
            n7 = Math.min(sIZE.cy, n7);
        }
        int n8 = (n - n6) / 2;
        int n9 = (n2 - n7) / 2;
        OS.SetRect(rECT2, n8, n9, n8 + n6, n9 + n7);
        if (OS.IsAppThemed()) {
            long l9 = this.display.hButtonTheme();
            OS.DrawThemeBackground(l9, l4, 3, 1, rECT2, null);
            RECT rECT3 = rECT2;
            rECT3.left += n;
            rECT = rECT2;
            rECT.right += n;
            OS.DrawThemeBackground(l9, l4, 3, 5, rECT2, null);
            RECT rECT4 = rECT2;
            rECT4.left += n;
            RECT rECT5 = rECT2;
            rECT5.right += n;
            OS.DrawThemeBackground(l9, l4, 3, 1, rECT2, null);
            RECT rECT6 = rECT2;
            rECT6.left += n;
            RECT rECT7 = rECT2;
            rECT7.right += n;
            OS.DrawThemeBackground(l9, l4, 3, 9, rECT2, null);
            RECT rECT8 = rECT2;
            rECT8.left += n;
            RECT rECT9 = rECT2;
            rECT9.right += n;
            OS.DrawThemeBackground(l9, l4, 3, 4, rECT2, null);
            RECT rECT10 = rECT2;
            rECT10.left += n;
            RECT rECT11 = rECT2;
            rECT11.right += n;
            OS.DrawThemeBackground(l9, l4, 3, 8, rECT2, null);
            RECT rECT12 = rECT2;
            rECT12.left += n;
            RECT rECT13 = rECT2;
            rECT13.right += n;
            OS.DrawThemeBackground(l9, l4, 3, 4, rECT2, null);
            RECT rECT14 = rECT2;
            rECT14.left += n;
            RECT rECT15 = rECT2;
            rECT15.right += n;
            OS.DrawThemeBackground(l9, l4, 3, 12, rECT2, null);
        } else {
            OS.DrawFrameControl(l4, rECT2, 4, 16384);
            RECT rECT16 = rECT2;
            rECT16.left += n;
            RECT rECT17 = rECT2;
            rECT17.right += n;
            OS.DrawFrameControl(l4, rECT2, 4, 17408);
            RECT rECT18 = rECT2;
            rECT18.left += n;
            rECT = rECT2;
            rECT.right += n;
            OS.DrawFrameControl(l4, rECT2, 4, 16640);
            RECT rECT19 = rECT2;
            rECT19.left += n;
            RECT rECT20 = rECT2;
            rECT20.right += n;
            OS.DrawFrameControl(l4, rECT2, 4, 17664);
            RECT rECT21 = rECT2;
            rECT21.left += n;
            RECT rECT22 = rECT2;
            rECT22.right += n;
            OS.DrawFrameControl(l4, rECT2, 4, 16384);
            RECT rECT23 = rECT2;
            rECT23.left += n;
            RECT rECT24 = rECT2;
            rECT24.right += n;
            OS.DrawFrameControl(l4, rECT2, 4, 17408);
            RECT rECT25 = rECT2;
            rECT25.left += n;
            RECT rECT26 = rECT2;
            rECT26.right += n;
            OS.DrawFrameControl(l4, rECT2, 4, 16640);
            RECT rECT27 = rECT2;
            rECT27.left += n;
            RECT rECT28 = rECT2;
            rECT28.right += n;
            OS.DrawFrameControl(l4, rECT2, 4, 17664);
        }
        OS.SelectObject(l4, l6);
        OS.DeleteDC(l4);
        OS.ReleaseDC(this.handle, l3);
        if (OS.IsAppThemed()) {
            OS.ImageList_Add(l2, l5, 0L);
        } else {
            OS.ImageList_AddMasked(l2, l5, n3);
        }
        OS.DeleteObject(l5);
        int n10 = this.getTopIndex();
        if (bl && n10 != 0) {
            this.setRedraw(false);
            this.setTopIndex(0);
        }
        long l10 = OS.SendMessage(this.handle, 4098, 2L, 0L);
        OS.SendMessage(this.handle, 4099, 2L, l2);
        if (l10 != 0L) {
            OS.ImageList_Destroy(l10);
        }
        long l11 = OS.SendMessage(this.handle, 4098, 1L, 0L);
        OS.SendMessage(this.handle, 4099, 1L, l11);
        if (bl && n10 != 0) {
            this.setTopIndex(n10);
            this.setRedraw(true);
        }
    }

    void setFocusIndex(int n) {
        if (n < 0) {
            return;
        }
        LVITEM lVITEM = new LVITEM();
        lVITEM.state = 1;
        lVITEM.stateMask = 1;
        this.ignoreSelect = true;
        OS.SendMessage(this.handle, 4139, (long)n, lVITEM);
        this.ignoreSelect = false;
        OS.SendMessage(this.handle, 4163, 0L, n);
    }

    @Override
    public void setFont(Font font) {
        int n;
        this.checkWidget();
        int n2 = this.getTopIndex();
        if (n2 != 0) {
            this.setRedraw(false);
            this.setTopIndex(0);
        }
        if (this.itemHeight != -1) {
            n = OS.GetWindowLong(this.handle, -16);
            OS.SetWindowLong(this.handle, -16, n | 0x400);
        }
        super.setFont(font);
        if (this.itemHeight != -1) {
            n = OS.GetWindowLong(this.handle, -16);
            OS.SetWindowLong(this.handle, -16, n & 0xFFFFFBFF);
        }
        this.setScrollWidth(null, true);
        if (n2 != 0) {
            this.setTopIndex(n2);
            this.setRedraw(true);
        }
        OS.InvalidateRect(this.hwndHeader, null, true);
    }

    @Override
    void setForegroundPixel(int n) {
        if (n == -1) {
            n = -16777216;
        }
        OS.SendMessage(this.handle, 4132, 0L, n);
        OS.InvalidateRect(this.handle, null, true);
        OS.InvalidateRect(this.hwndHeader, null, true);
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
        int n = OS.GetWindowLong(this.handle, -16);
        n &= 0xFFFFBFFF;
        if (!bl) {
            n |= 0x4000;
        }
        int n2 = this.getTopIndex();
        OS.SetWindowLong(this.handle, -16, n);
        int n3 = this.getTopIndex();
        if (n3 != 0) {
            this.setRedraw(false);
            this.setTopIndex(0);
        }
        this.setTopIndex(n2);
        if (n3 != 0) {
            this.setRedraw(true);
        }
        this.updateHeaderToolTips();
    }

    public void setItemCount(int n) {
        int n2;
        boolean bl;
        this.checkWidget();
        n = Math.max(0, n);
        int n3 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        if (n == n3) {
            return;
        }
        this.setDeferResize(true);
        boolean bl2 = bl = (this.style & 0x10000000) != 0;
        if (!bl) {
            this.setRedraw(false);
        }
        for (n2 = n; n2 < n3; ++n2) {
            TableItem tableItem = this._getItem(n2, false);
            if (tableItem != null && !tableItem.isDisposed()) {
                tableItem.release(false);
            }
            if (bl) continue;
            boolean bl3 = true;
            this.ignoreShrink = true;
            this.ignoreSelect = true;
            long l2 = OS.SendMessage(this.handle, 4104, (long)n, 0L);
            boolean bl4 = false;
            this.ignoreShrink = false;
            this.ignoreSelect = false;
            if (l2 == 0L) break;
        }
        if (n2 < n3) {
            this.error(15);
        }
        this._setItemCount(n, n3);
        if (bl) {
            int n4 = 3;
            OS.SendMessage(this.handle, 4143, (long)n, 3L);
            if (n == 0 && n3 != 0) {
                OS.InvalidateRect(this.handle, null, true);
            }
        } else {
            for (int i = n3; i < n; ++i) {
                new TableItem(this, 0, i, true);
            }
        }
        if (!bl) {
            this.setRedraw(true);
        }
        if (n3 == 0) {
            this.setScrollWidth(null, false);
        }
        this.setDeferResize(false);
    }

    void setItemHeight(boolean bl) {
        int n = this.getTopIndex();
        if (bl && n != 0) {
            this.setRedraw(false);
            this.setTopIndex(0);
        }
        if (this.itemHeight == -1) {
            long l2 = OS.SendMessage(this.handle, 49, 0L, 0L);
            OS.SendMessage(this.handle, 48, l2, 0L);
        } else {
            this.forceResize();
            RECT rECT = new RECT();
            OS.GetWindowRect(this.handle, rECT);
            int n2 = rECT.right - rECT.left;
            int n3 = rECT.bottom - rECT.top;
            int n4 = OS.GetWindowLong(this.handle, -16);
            OS.SetWindowLong(this.handle, -16, n4 | 0x400);
            int n5 = 30;
            this.ignoreResize = true;
            OS.SetWindowPos(this.handle, 0L, 0, 0, n2, n3 + 1, 30);
            OS.SetWindowPos(this.handle, 0L, 0, 0, n2, n3, 30);
            this.ignoreResize = false;
            OS.SetWindowLong(this.handle, -16, n4);
        }
        if (bl && n != 0) {
            this.setTopIndex(n);
            this.setRedraw(true);
        }
    }

    void setItemHeight(int n) {
        this.checkWidget();
        if (n < -1) {
            this.error(5);
        }
        this.itemHeight = n;
        this.setItemHeight(true);
        this.setScrollWidth(null, true);
    }

    public void setLinesVisible(boolean bl) {
        this.checkWidget();
        int n = bl ? 1 : 0;
        OS.SendMessage(this.handle, 4150, 1L, n);
        OS.InvalidateRect(this.hwndHeader, null, true);
    }

    @Override
    public void setRedraw(boolean bl) {
        int n;
        this.checkWidget();
        if (this.drawCount == 0 && ((n = OS.GetWindowLong(this.handle, -16)) & 0x10000000) == 0) {
            this.state |= 0x10;
        }
        if (bl) {
            if (--this.drawCount == 0) {
                this.setScrollWidth(null, true);
                this.setDeferResize(true);
                OS.SendMessage(this.handle, 11, 1L, 0L);
                if (this.hwndHeader != 0L) {
                    OS.SendMessage(this.hwndHeader, 11, 1L, 0L);
                }
                if ((this.state & 0x10) != 0) {
                    this.state &= 0xFFFFFFEF;
                    OS.ShowWindow(this.handle, 0);
                } else {
                    n = 1157;
                    OS.RedrawWindow(this.handle, null, 0L, 1157);
                }
                this.setDeferResize(false);
            }
        } else if (this.drawCount++ == 0) {
            OS.SendMessage(this.handle, 11, 0L, 0L);
            if (this.hwndHeader != 0L) {
                OS.SendMessage(this.hwndHeader, 11, 0L, 0L);
            }
        }
    }

    void setScrollWidth(int n) {
        if (n != (int)OS.SendMessage(this.handle, 4125, 0L, 0L)) {
            boolean bl = false;
            if (this.hooks(41)) {
                boolean bl2 = bl = this.getDrawing() && OS.IsWindowVisible(this.handle);
            }
            if (bl) {
                OS.DefWindowProc(this.handle, 11, 0L, 0L);
            }
            OS.SendMessage(this.handle, 4126, 0L, n);
            if (bl) {
                OS.DefWindowProc(this.handle, 11, 1L, 0L);
                int n2 = 1157;
                OS.RedrawWindow(this.handle, null, 0L, 1157);
            }
        }
    }

    public void setSelection(int[] nArray) {
        this.checkWidget();
        if (nArray == null) {
            this.error(4);
        }
        this.deselectAll();
        int n = nArray.length;
        if (n == 0 || (this.style & 4) != 0 && n > 1) {
            return;
        }
        this.select(nArray);
        int n2 = nArray[0];
        if (n2 != -1) {
            this.setFocusIndex(n2);
        }
        this.showSelection();
    }

    public void setSelection(TableItem tableItem) {
        this.checkWidget();
        if (tableItem == null) {
            this.error(4);
        }
        this.setSelection(new TableItem[]{tableItem});
    }

    public void setSelection(TableItem[] tableItemArray) {
        this.checkWidget();
        if (tableItemArray == null) {
            this.error(4);
        }
        this.deselectAll();
        int n = tableItemArray.length;
        if (n == 0 || (this.style & 4) != 0 && n > 1) {
            return;
        }
        int n2 = -1;
        for (int i = n - 1; i >= 0; --i) {
            int n3 = this.indexOf(tableItemArray[i]);
            if (n3 == -1) continue;
            n2 = n3;
            this.select(n2);
        }
        if (n2 != -1) {
            this.setFocusIndex(n2);
        }
        this.showSelection();
    }

    public void setSelection(int n) {
        this.checkWidget();
        this.deselectAll();
        this.select(n);
        if (n != -1) {
            this.setFocusIndex(n);
        }
        this.showSelection();
    }

    public void setSelection(int n, int n2) {
        this.checkWidget();
        this.deselectAll();
        if (n2 < 0 || n > n2 || (this.style & 4) != 0 && n != n2) {
            return;
        }
        int n3 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
        if (n3 == 0 || n >= n3) {
            return;
        }
        n = Math.max(0, n);
        n2 = Math.min(n2, n3 - 1);
        this.select(n, n2);
        this.setFocusIndex(n);
        this.showSelection();
    }

    public void setSortColumn(TableColumn tableColumn) {
        this.checkWidget();
        if (tableColumn != null && tableColumn.isDisposed()) {
            this.error(5);
        }
        if (this.sortColumn != null && !this.sortColumn.isDisposed()) {
            this.sortColumn.setSortDirection(0);
        }
        this.sortColumn = tableColumn;
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

    void setSubImagesVisible(boolean bl) {
        int n = (int)OS.SendMessage(this.handle, 4151, 0L, 0L);
        if ((n & 2) != 0 == bl) {
            return;
        }
        int n2 = bl ? 2 : 0;
        OS.SendMessage(this.handle, 4150, 2L, n2);
    }

    void setTableEmpty() {
        if (this.imageList != null) {
            long l2 = OS.ImageList_Create(1, 1, 0, 0, 0);
            OS.SendMessage(this.handle, 4099, 1L, l2);
            OS.SendMessage(this.handle, 4099, 1L, 0L);
            if (this.headerImageList != null) {
                long l3 = this.headerImageList.getHandle();
                long l4 = OS.SendMessage(this.handle, 4127, 0L, 0L);
                OS.SendMessage(l4, 4616, 0L, l3);
            }
            OS.ImageList_Destroy(l2);
            this.display.releaseImageList(this.imageList);
            this.imageList = null;
            if (this.itemHeight != -1) {
                this.setItemHeight(false);
            }
        }
        if (!(this.hooks(41) || this.hooks(40) || this.hooks(42))) {
            Control control = this.findBackgroundControl();
            if (control == null) {
                control = this;
            }
            if (control.backgroundImage == null) {
                this.setCustomDraw(false);
                this.setBackgroundTransparent(false);
            }
        }
        this._initItems();
        if (this.columnCount == 0) {
            OS.SendMessage(this.handle, 4126, 0L, 0L);
            this.setScrollWidth(null, false);
        }
    }

    public void setTopIndex(int n) {
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 4135, 0L, 0L);
        if (n == n2) {
            return;
        }
        if (!this.painted && this.hooks(41)) {
            this.hitTestSelection(n, 0, 0);
        }
        if (OS.SendMessage(this.handle, 4136, 0L, 0L) <= 0L) {
            OS.SendMessage(this.handle, 4115, (long)n, 1L);
            if ((long)n != OS.SendMessage(this.handle, 4135, 0L, 0L)) {
                OS.SendMessage(this.handle, 4115, (long)n, 1L);
            }
            return;
        }
        RECT rECT = new RECT();
        rECT.left = 0;
        this.ignoreCustomDraw = true;
        OS.SendMessage(this.handle, 4110, 0L, rECT);
        this.ignoreCustomDraw = false;
        int n3 = (n - n2) * (rECT.bottom - rECT.top);
        OS.SendMessage(this.handle, 4116, 0L, n3);
    }

    public void showColumn(TableColumn tableColumn) {
        int n;
        int n2;
        Object object;
        int n3;
        this.checkWidget();
        if (tableColumn == null) {
            this.error(4);
        }
        if (tableColumn.isDisposed()) {
            this.error(5);
        }
        if (tableColumn.parent != this) {
            return;
        }
        int n4 = this.indexOf(tableColumn);
        if (0 > n4 || n4 >= this.columnCount) {
            return;
        }
        RECT rECT = new RECT();
        rECT.left = 0;
        if (n4 == 0) {
            rECT.top = 1;
            this.ignoreCustomDraw = true;
            OS.SendMessage(this.handle, 4152, -1L, rECT);
            this.ignoreCustomDraw = false;
            rECT.right = rECT.left;
            n3 = (int)OS.SendMessage(this.handle, 4125, 0L, 0L);
            rECT.left = rECT.right - n3;
        } else {
            rECT.top = n4;
            this.ignoreCustomDraw = true;
            OS.SendMessage(this.handle, 4152, -1L, rECT);
            this.ignoreCustomDraw = false;
        }
        n3 = 0;
        if (this != false) {
            object = new SCROLLINFO();
            ((SCROLLINFO)object).cbSize = SCROLLINFO.sizeof;
            ((SCROLLINFO)object).fMask = 4;
            OS.GetScrollInfo(this.handle, 0, (SCROLLINFO)object);
            n3 = ((SCROLLINFO)object).nPos;
        }
        object = new RECT();
        OS.GetClientRect(this.handle, (RECT)object);
        if (rECT.left < ((RECT)object).left) {
            n2 = rECT.left - ((RECT)object).left;
            OS.SendMessage(this.handle, 4116, (long)n2, 0L);
        } else {
            n2 = Math.min(((RECT)object).right - ((RECT)object).left, rECT.right - rECT.left);
            if (rECT.left + n2 > ((RECT)object).right) {
                n = rECT.left + n2 - ((RECT)object).right;
                OS.SendMessage(this.handle, 4116, (long)n, 0L);
            }
        }
        if (this != false) {
            SCROLLINFO sCROLLINFO = new SCROLLINFO();
            sCROLLINFO.cbSize = SCROLLINFO.sizeof;
            sCROLLINFO.fMask = 4;
            OS.GetScrollInfo(this.handle, 0, sCROLLINFO);
            n = sCROLLINFO.nPos;
            if (n < n3) {
                ((RECT)object).right = n3 - n + 1;
                OS.InvalidateRect(this.handle, (RECT)object, true);
            }
        }
    }

    void showItem(int n) {
        long l2;
        if (!this.painted && this.hooks(41)) {
            this.hitTestSelection(n, 0, 0);
        }
        if ((l2 = OS.SendMessage(this.handle, 4136, 0L, 0L)) <= 0L) {
            OS.SendMessage(this.handle, 4115, (long)n, 1L);
            if ((long)n != OS.SendMessage(this.handle, 4135, 0L, 0L)) {
                OS.SendMessage(this.handle, 4115, (long)n, 1L);
            }
        } else {
            long l3 = OS.SendMessage(this.handle, 4135, 0L, 0L);
            if (l3 > (long)n || (long)n >= l3 + l2) {
                OS.SendMessage(this.handle, 4115, (long)n, 0L);
            }
        }
    }

    public void showItem(TableItem tableItem) {
        int n;
        this.checkWidget();
        if (tableItem == null) {
            this.error(4);
        }
        if (tableItem.isDisposed()) {
            this.error(5);
        }
        if ((n = this.indexOf(tableItem)) != -1) {
            this.showItem(n);
        }
    }

    public void showSelection() {
        this.checkWidget();
        int n = (int)OS.SendMessage(this.handle, 4108, -1L, 2L);
        if (n != -1) {
            if (!(this.display.getActiveShell() != this.getShell() || (this.style & 0x10) != 0 || this.verticalBar != null && this.verticalBar.isVisible())) {
                this.showItem(0);
            } else {
                this.showItem(n);
            }
        }
    }

    void sort() {
        this.checkWidget();
    }

    @Override
    void subclass() {
        super.subclass();
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

    @Override
    String toolTipText(NMTTDISPINFO nMTTDISPINFO) {
        long l2 = OS.SendMessage(this.handle, 4174, 0L, 0L);
        if (l2 == nMTTDISPINFO.hwndFrom && this.toolTipText != null) {
            return "";
        }
        if (this.headerToolTipHandle == nMTTDISPINFO.hwndFrom) {
            for (int i = 0; i < this.columnCount; ++i) {
                TableColumn tableColumn = this.columns[i];
                if ((long)tableColumn.id != nMTTDISPINFO.idFrom) continue;
                return tableColumn.toolTipText;
            }
        }
        return super.toolTipText(nMTTDISPINFO);
    }

    @Override
    void unsubclass() {
        super.unsubclass();
    }

    @Override
    void update(boolean bl) {
        long l2 = 0L;
        long l3 = 0L;
        boolean bl2 = this.isOptimizedRedraw();
        if (bl2) {
            l3 = OS.SetWindowLongPtr(this.handle, -4, TableProc);
            l2 = OS.SetWindowLongPtr(this.hwndHeader, -4, HeaderProc);
        }
        super.update(bl);
        if (bl2) {
            OS.SetWindowLongPtr(this.handle, -4, l3);
            OS.SetWindowLongPtr(this.hwndHeader, -4, l2);
        }
    }

    void updateHeaderToolTips() {
        if (this.headerToolTipHandle == 0L) {
            return;
        }
        long l2 = OS.SendMessage(this.handle, 4127, 0L, 0L);
        RECT rECT = new RECT();
        TOOLINFO tOOLINFO = new TOOLINFO();
        tOOLINFO.cbSize = TOOLINFO.sizeof;
        tOOLINFO.uFlags = 16;
        tOOLINFO.hwnd = l2;
        tOOLINFO.lpszText = -1L;
        for (int i = 0; i < this.columnCount; ++i) {
            int n;
            TableColumn tableColumn = this.columns[i];
            if (OS.SendMessage(l2, 4615, (long)i, rECT) == 0L) continue;
            TOOLINFO tOOLINFO2 = tOOLINFO;
            TableColumn tableColumn2 = tableColumn;
            ++this.display.nextToolTipId;
            tableColumn2.id = n;
            tOOLINFO2.uId = n;
            tOOLINFO.left = rECT.left;
            tOOLINFO.top = rECT.top;
            tOOLINFO.right = rECT.right;
            tOOLINFO.bottom = rECT.bottom;
            OS.SendMessage(this.headerToolTipHandle, 1074, 0L, tOOLINFO);
        }
    }

    @Override
    void updateMenuLocation(Event event) {
        Object object;
        Rectangle rectangle = this.getClientAreaInPixels();
        int n = rectangle.x;
        int n2 = rectangle.y;
        int n3 = this.getFocusIndex();
        if (n3 != -1) {
            object = this.getItem(n3);
            Rectangle rectangle2 = ((TableItem)object).getBoundsInPixels(0);
            if (((TableItem)object).text != null && ((TableItem)object).text.length() != 0) {
                rectangle2 = ((TableItem)object).getBoundsInPixels();
            }
            n = Math.max(n, rectangle2.x + rectangle2.width / 2);
            n = Math.min(n, rectangle.x + rectangle.width);
            n2 = Math.max(n2, rectangle2.y + rectangle2.height);
            n2 = Math.min(n2, rectangle.y + rectangle.height);
        }
        object = this.toDisplayInPixels(n, n2);
        event.setLocationInPixels(((Point)object).x, ((Point)object).y);
    }

    void updateMoveable() {
        int n;
        for (n = 0; n < this.columnCount && !this.columns[n].moveable; ++n) {
        }
        int n2 = n < this.columnCount ? 16 : 0;
        OS.SendMessage(this.handle, 4150, 16L, n2);
    }

    @Override
    void updateOrientation() {
        int n;
        Object object;
        int n2;
        super.updateOrientation();
        long l2 = OS.SendMessage(this.handle, 4127, 0L, 0L);
        if (l2 != 0L) {
            int n3 = OS.GetWindowLong(l2, -20);
            n3 = (this.style & 0x4000000) != 0 ? (n3 |= 0x400000) : (n3 &= 0xFFBFFFFF);
            OS.SetWindowLong(l2, -20, n3 &= 0xFFFFDFFF);
            OS.InvalidateRect(l2, null, true);
            RECT rECT = new RECT();
            OS.GetWindowRect(this.handle, rECT);
            n2 = rECT.right - rECT.left;
            int n4 = rECT.bottom - rECT.top;
            OS.SetWindowPos(this.handle, 0L, 0, 0, n2 - 1, n4 - 1, 6);
            OS.SetWindowPos(this.handle, 0L, 0, 0, n2, n4, 6);
        }
        if ((this.style & 0x20) != 0) {
            this.fixCheckboxImageListColor(false);
        }
        if (this.imageList != null) {
            Point point = this.imageList.getImageSize();
            this.display.releaseImageList(this.imageList);
            this.imageList = this.display.getImageList(this.style & 0x4000000, point.x, point.y);
            int n5 = (int)OS.SendMessage(this.handle, 4100, 0L, 0L);
            for (n2 = 0; n2 < n5; ++n2) {
                TableItem tableItem = this._getItem(n2, false);
                if (tableItem == null || (object = tableItem.image) == null || (n = this.imageList.indexOf((Image)object)) != -1) continue;
                this.imageList.add((Image)object);
            }
            long l3 = this.imageList.getHandle();
            OS.SendMessage(this.handle, 4099, 1L, l3);
        }
        if (l2 != 0L && this.headerImageList != null) {
            Point point = this.headerImageList.getImageSize();
            this.display.releaseImageList(this.headerImageList);
            this.headerImageList = this.display.getImageList(this.style & 0x4000000, point.x, point.y);
            if (this.columns != null) {
                for (int i = 0; i < this.columns.length; ++i) {
                    Image image;
                    TableColumn tableColumn = this.columns[i];
                    if (tableColumn == null || (image = tableColumn.image) == null) continue;
                    object = new LVCOLUMN();
                    ((LVCOLUMN)object).mask = 1;
                    OS.SendMessage(l2, 4191, (long)i, (LVCOLUMN)object);
                    if ((((LVCOLUMN)object).fmt & 0x800) == 0) continue;
                    n = this.headerImageList.indexOf(image);
                    if (n == -1) {
                        this.headerImageList.add(image);
                    }
                    ((LVCOLUMN)object).iImage = n;
                    ((LVCOLUMN)object).mask = 16;
                    OS.SendMessage(l2, 4192, (long)i, (LVCOLUMN)object);
                }
            }
            long l4 = this.headerImageList.getHandle();
            OS.SendMessage(l2, 4616, 0L, l4);
        }
    }

    @Override
    boolean updateTextDirection(int n) {
        if (super.updateTextDirection(n)) {
            if (n == 0x6000000 || (this.state & 0x400000) != 0) {
                for (TableItem tableItem : this.items) {
                    if (tableItem == null) continue;
                    tableItem.updateTextDirection(n == 0x6000000 ? 0x6000000 : this.style & Integer.MIN_VALUE);
                }
            }
            OS.InvalidateRect(this.handle, null, true);
            return true;
        }
        return false;
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle() | 0x40;
        if ((this.style & 0x8000) == 0) {
            n |= 8;
        }
        if ((this.style & 4) != 0) {
            n |= 4;
        }
        n |= 0x4001;
        if ((this.style & 0x10000000) != 0) {
            n |= 0x1000;
        }
        return n;
    }

    @Override
    TCHAR windowClass() {
        return TableClass;
    }

    @Override
    long windowProc() {
        return TableProc;
    }

    @Override
    long windowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        if (l2 != this.handle) {
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
                    long l5 = OS.SendMessage(this.handle, 4127, 0L, 0L);
                    int n3 = (int)OS.SendMessage(l5, 4614, 0L, hDHITTESTINFO);
                    if (0 > n3 || n3 >= this.columnCount || this.columns[n3].resizable || (hDHITTESTINFO.flags & 0xC) == 0) break;
                    OS.SetCursor(OS.LoadCursor(0L, 32512L));
                    return 1L;
                }
            }
            return this.callWindowProc(l2, n, l3, l4);
        }
        if (n != Display.DI_GETDRAGIMAGE) {
            return super.windowProc(l2, n, l3, l4);
        }
        int n4 = (int)OS.SendMessage(this.handle, 4135, 0L, 0L);
        int n5 = (int)OS.SendMessage(this.handle, 4108, (long)(n4 - 1), 2L);
        if (n5 == -1) {
            return 0L;
        }
        POINT pOINT = new POINT();
        OS.POINTSTOPOINT(pOINT, OS.GetMessagePos());
        OS.MapWindowPoints(0L, this.handle, pOINT, 1);
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        TableItem tableItem = this._getItem(n5);
        RECT rECT2 = tableItem.getBounds(n5, 0, true, true, true);
        if ((this.style & 0x10000) != 0) {
            int n6 = 301;
            rECT2.left = Math.max(rECT.left, pOINT.x - 150);
            if (rECT.right > rECT2.left + 301) {
                rECT2.right = rECT2.left + 301;
            } else {
                rECT2.right = rECT.right;
                rECT2.left = Math.max(rECT.left, rECT2.right - 301);
            }
        }
        long l6 = OS.CreateRectRgn(rECT2.left, rECT2.top, rECT2.right, rECT2.bottom);
        while ((n5 = (int)OS.SendMessage(this.handle, 4108, (long)n5, 2L)) != -1 && rECT2.bottom - rECT2.top <= 301 && rECT2.bottom <= rECT.bottom) {
            RECT rECT3 = tableItem.getBounds(n5, 0, true, true, true);
            long l7 = OS.CreateRectRgn(rECT2.left, rECT3.top, rECT2.right, rECT3.bottom);
            OS.CombineRgn(l6, l6, l7, 2);
            OS.DeleteObject(l7);
            rECT2.bottom = rECT3.bottom;
        }
        OS.GetRgnBox(l6, rECT2);
        long l8 = OS.GetDC(this.handle);
        long l9 = OS.CreateCompatibleDC(l8);
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
        long l10 = OS.CreateDIBSection(0L, byArray, 0, lArray, 0L, 0);
        if (l10 == 0L) {
            this.error(2);
        }
        long l11 = OS.SelectObject(l9, l10);
        int n7 = 253;
        POINT pOINT2 = new POINT();
        OS.SetWindowOrgEx(l9, rECT2.left, rECT2.top, pOINT2);
        OS.FillRect(l9, rECT2, this.findBrush(253L, 0));
        OS.OffsetRgn(l6, -rECT2.left, -rECT2.top);
        OS.SelectClipRgn(l9, l6);
        OS.PrintWindow(this.handle, l9, 0);
        OS.SetWindowOrgEx(l9, pOINT2.x, pOINT2.y, null);
        OS.SelectObject(l9, l11);
        OS.DeleteDC(l9);
        OS.ReleaseDC(0L, l8);
        OS.DeleteObject(l6);
        SHDRAGIMAGE sHDRAGIMAGE = new SHDRAGIMAGE();
        sHDRAGIMAGE.hbmpDragImage = l10;
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
                if ((this.style & 0x20) != 0) {
                    int n = -1;
                    while ((n = (int)OS.SendMessage(this.handle, 4108, (long)n, 2L)) != -1) {
                        TableItem tableItem;
                        tableItem.setChecked(!(tableItem = this._getItem(n)).getChecked(), true);
                        OS.NotifyWinEvent(32773, this.handle, -4, n + 1);
                    }
                }
                long l4 = this.callWindowProc(this.handle, 256, l2, l3);
                return new LRESULT(l4);
            }
            case 13: {
                int n = (int)OS.SendMessage(this.handle, 4108, -1L, 1L);
                if (n != -1) {
                    Event event = new Event();
                    event.item = this._getItem(n);
                    this.sendSelectionEvent(14, event, false);
                }
                return LRESULT.ZERO;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_CONTEXTMENU(long l2, long l3) {
        if (!this.display.runDragDrop) {
            return LRESULT.ZERO;
        }
        return super.WM_CONTEXTMENU(l2, l3);
    }

    @Override
    LRESULT WM_ERASEBKGND(long l2, long l3) {
        LRESULT lRESULT = super.WM_ERASEBKGND(l2, l3);
        if (this.findImageControl() != null) {
            return LRESULT.ONE;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_GETOBJECT(long l2, long l3) {
        if ((this.style & 0x20) != 0 && this.accessible == null) {
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
            case 32: {
                return LRESULT.ZERO;
            }
            case 107: {
                int n;
                if (OS.GetKeyState(17) >= 0) break;
                for (n = 0; n < this.columnCount && this.columns[n].getResizable(); ++n) {
                }
                if (n == this.columnCount && !this.hooks(41)) break;
                TableColumn[] tableColumnArray = new TableColumn[this.columnCount];
                System.arraycopy(this.columns, 0, tableColumnArray, 0, this.columnCount);
                for (TableColumn tableColumn : tableColumnArray) {
                    if (tableColumn.isDisposed() || !tableColumn.getResizable()) continue;
                    tableColumn.pack();
                }
                return LRESULT.ZERO;
            }
            case 33: 
            case 34: 
            case 35: 
            case 36: {
                long l4;
                long l5 = 0L;
                long l6 = 0L;
                long l7 = OS.SendMessage(this.handle, 4127, 0L, 0L);
                boolean bl = this.isOptimizedRedraw();
                if (bl) {
                    l6 = OS.SetWindowLongPtr(this.handle, -4, TableProc);
                    l5 = OS.SetWindowLongPtr(l7, -4, HeaderProc);
                }
                LRESULT lRESULT2 = lRESULT = (l4 = this.callWindowProc(this.handle, 256, l2, l3)) == 0L ? LRESULT.ZERO : new LRESULT(l4);
                if (bl) {
                    OS.SetWindowLongPtr(this.handle, -4, l6);
                    OS.SetWindowLongPtr(l7, -4, l5);
                }
            }
            case 38: 
            case 40: {
                OS.SendMessage(this.handle, 295, 3L, 0L);
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_KILLFOCUS(long l2, long l3) {
        LRESULT lRESULT = super.WM_KILLFOCUS(l2, l3);
        if (this.imageList != null || (this.style & 0x20) != 0) {
            OS.InvalidateRect(this.handle, null, false);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_LBUTTONDBLCLK(long l2, long l3) {
        TableItem tableItem;
        LVHITTESTINFO lVHITTESTINFO = new LVHITTESTINFO();
        lVHITTESTINFO.x = OS.GET_X_LPARAM(l3);
        lVHITTESTINFO.y = OS.GET_Y_LPARAM(l3);
        int n = (int)OS.SendMessage(this.handle, 4114, 0L, lVHITTESTINFO);
        Display display = this.display;
        display.captureChanged = false;
        this.sendMouseEvent(3, 1, this.handle, l3);
        if (!this.sendMouseEvent(8, 1, this.handle, l3)) {
            if (!display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
                OS.SetCapture(this.handle);
            }
            return LRESULT.ZERO;
        }
        if (lVHITTESTINFO.iItem != -1) {
            this.callWindowProc(this.handle, 515, l2, l3);
        }
        if (!display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
            OS.SetCapture(this.handle);
        }
        if ((this.style & 0x20) != 0 && n != -1 && lVHITTESTINFO.flags == 8 && (tableItem = this._getItem(n)) != null && !tableItem.isDisposed()) {
            tableItem.setChecked(!tableItem.getChecked(), true);
            OS.NotifyWinEvent(32773, this.handle, -4, n + 1);
        }
        return LRESULT.ZERO;
    }

    @Override
    LRESULT WM_LBUTTONDOWN(long l2, long l3) {
        LRESULT lRESULT = this.sendMouseDownEvent(3, 1, 513, l2, l3);
        if (lRESULT == LRESULT.ZERO) {
            return lRESULT;
        }
        if ((this.style & 0x20) != 0) {
            TableItem tableItem;
            LVHITTESTINFO lVHITTESTINFO = new LVHITTESTINFO();
            lVHITTESTINFO.x = OS.GET_X_LPARAM(l3);
            lVHITTESTINFO.y = OS.GET_Y_LPARAM(l3);
            int n = (int)OS.SendMessage(this.handle, 4114, 0L, lVHITTESTINFO);
            if (n != -1 && lVHITTESTINFO.flags == 8 && (tableItem = this._getItem(n)) != null && !tableItem.isDisposed()) {
                tableItem.setChecked(!tableItem.getChecked(), true);
                OS.NotifyWinEvent(32773, this.handle, -4, n + 1);
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_MOUSEHOVER(long l2, long l3) {
        LRESULT lRESULT = super.WM_MOUSEHOVER(l2, l3);
        int n = (int)OS.SendMessage(this.handle, 4151, 0L, 0L);
        int n2 = 200;
        if ((n & 0xC8) != 0) {
            return lRESULT;
        }
        return LRESULT.ZERO;
    }

    @Override
    LRESULT WM_PAINT(long l2, long l3) {
        if ((this.state & 0x1000) != 0) {
            return LRESULT.ZERO;
        }
        this._checkShrink();
        if (this.fixScrollWidth) {
            this.setScrollWidth(null, true);
        }
        return super.WM_PAINT(l2, l3);
    }

    @Override
    LRESULT WM_RBUTTONDBLCLK(long l2, long l3) {
        LVHITTESTINFO lVHITTESTINFO = new LVHITTESTINFO();
        lVHITTESTINFO.x = OS.GET_X_LPARAM(l3);
        lVHITTESTINFO.y = OS.GET_Y_LPARAM(l3);
        OS.SendMessage(this.handle, 4114, 0L, lVHITTESTINFO);
        Display display = this.display;
        display.captureChanged = false;
        this.sendMouseEvent(3, 3, this.handle, l3);
        if (this.sendMouseEvent(8, 3, this.handle, l3) && lVHITTESTINFO.iItem != -1) {
            this.callWindowProc(this.handle, 518, l2, l3);
        }
        if (!display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
            OS.SetCapture(this.handle);
        }
        return LRESULT.ZERO;
    }

    @Override
    LRESULT WM_RBUTTONDOWN(long l2, long l3) {
        return this.sendMouseDownEvent(3, 3, 516, l2, l3);
    }

    @Override
    LRESULT WM_SETFOCUS(long l2, long l3) {
        int n;
        LRESULT lRESULT = super.WM_SETFOCUS(l2, l3);
        if (this.imageList != null || (this.style & 0x20) != 0) {
            OS.InvalidateRect(this.handle, null, false);
        }
        if ((n = (int)OS.SendMessage(this.handle, 4100, 0L, 0L)) == 0) {
            return lRESULT;
        }
        int n2 = (int)OS.SendMessage(this.handle, 4108, -1L, 1L);
        if (n2 == -1) {
            LVITEM lVITEM = new LVITEM();
            lVITEM.state = 1;
            lVITEM.stateMask = 1;
            this.ignoreSelect = true;
            OS.SendMessage(this.handle, 4139, 0L, lVITEM);
            this.ignoreSelect = false;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SETFONT(long l2, long l3) {
        LRESULT lRESULT = super.WM_SETFONT(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        OS.SendMessage(this.hwndHeader, 48, 0L, l3);
        if (this.headerToolTipHandle != 0L) {
            OS.SendMessage(this.headerToolTipHandle, 48, l2, l3);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SETREDRAW(long l2, long l3) {
        LRESULT lRESULT = super.WM_SETREDRAW(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (l2 == 1L && (int)OS.SendMessage(this.handle, 4096, 0L, 0L) != -1 && (this.hooks(41) || this.hooks(40) || this.hooks(42))) {
            OS.SendMessage(this.handle, 4097, 0L, -1L);
        }
        OS.DefWindowProc(this.handle, 11, l2, l3);
        long l4 = this.callWindowProc(this.handle, 11, l2, l3);
        if (l2 == 0L && (int)OS.SendMessage(this.handle, 4096, 0L, 0L) == -1) {
            OS.SendMessage(this.handle, 4097, 0L, 0xFFFFFFL);
        }
        return l4 == 0L ? LRESULT.ZERO : new LRESULT(l4);
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        if (this.ignoreResize) {
            return null;
        }
        if (this.hooks(40) || this.hooks(42)) {
            OS.InvalidateRect(this.handle, null, true);
        }
        if (this.resizeCount != 0) {
            this.wasResized = true;
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
        if (this.findBackgroundControl() == null) {
            this.setBackgroundPixel(this.defaultBackground());
        } else {
            int n = (int)OS.SendMessage(this.handle, 4096, 0L, 0L);
            if (n != -1 && this.findImageControl() == null && (this.style & 0x20) != 0) {
                this.fixCheckboxImageListColor(true);
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_HSCROLL(long l2, long l3) {
        RECT rECT;
        int n;
        int n2 = 0;
        if (this != false) {
            SCROLLINFO sCROLLINFO = new SCROLLINFO();
            sCROLLINFO.cbSize = SCROLLINFO.sizeof;
            sCROLLINFO.fMask = 4;
            OS.GetScrollInfo(this.handle, 0, sCROLLINFO);
            n2 = sCROLLINFO.nPos;
        }
        long l4 = 0L;
        long l5 = 0L;
        long l6 = OS.SendMessage(this.handle, 4127, 0L, 0L);
        boolean bl = this.isOptimizedRedraw();
        if (bl) {
            l5 = OS.SetWindowLongPtr(this.handle, -4, TableProc);
            l4 = OS.SetWindowLongPtr(l6, -4, HeaderProc);
        }
        boolean bl2 = false;
        if (OS.LOWORD(l2) != 8 && this.columnCount > 32 && (n = (int)OS.SendMessage(this.handle, 4136, 0L, 0L)) > 16) {
            boolean bl3 = bl2 = this.getDrawing() && OS.IsWindowVisible(this.handle);
        }
        if (bl2) {
            OS.DefWindowProc(this.handle, 11, 0L, 0L);
        }
        LRESULT lRESULT = super.WM_HSCROLL(l2, l3);
        if (bl2) {
            OS.DefWindowProc(this.handle, 11, 1L, 0L);
            int n3 = 1157;
            OS.RedrawWindow(this.handle, null, 0L, 1157);
            if (OS.WIN32_VERSION >= OS.VERSION(6, 0)) {
                RECT rECT2 = new RECT();
                rECT = new RECT();
                OS.GetClientRect(this.handle, rECT);
                boolean[] blArray = new boolean[this.columnCount];
                for (int i = 0; i < this.columnCount; ++i) {
                    blArray[i] = true;
                    rECT2.top = i;
                    rECT2.left = 0;
                    if (OS.SendMessage(this.handle, 4152, 0L, rECT2) == 0L) continue;
                    rECT2.top = rECT.top;
                    rECT2.bottom = rECT.bottom;
                    blArray[i] = OS.IntersectRect(rECT2, rECT, rECT2);
                }
                this.columnVisible = blArray;
                OS.UpdateWindow(this.handle);
                this.columnVisible = null;
            }
        }
        if (bl) {
            OS.SetWindowLongPtr(this.handle, -4, l5);
            OS.SetWindowLongPtr(l6, -4, l4);
        }
        if (this != false) {
            SCROLLINFO sCROLLINFO = new SCROLLINFO();
            sCROLLINFO.cbSize = SCROLLINFO.sizeof;
            sCROLLINFO.fMask = 4;
            OS.GetScrollInfo(this.handle, 0, sCROLLINFO);
            int n4 = sCROLLINFO.nPos;
            if (n4 < n2) {
                rECT = new RECT();
                OS.GetClientRect(this.handle, rECT);
                rECT.right = n2 - n4 + 1;
                OS.InvalidateRect(this.handle, rECT, true);
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_VSCROLL(long l2, long l3) {
        Object object;
        RECT rECT;
        int n;
        int n2;
        long l4 = 0L;
        long l5 = 0L;
        long l6 = OS.SendMessage(this.handle, 4127, 0L, 0L);
        boolean bl = this.isOptimizedRedraw();
        if (bl) {
            l5 = OS.SetWindowLongPtr(this.handle, -4, TableProc);
            l4 = OS.SetWindowLongPtr(l6, -4, HeaderProc);
        }
        boolean bl2 = false;
        if (OS.LOWORD(l2) != 8 && this.columnCount > 32 && (n2 = (int)OS.SendMessage(this.handle, 4136, 0L, 0L)) > 16) {
            boolean bl3 = bl2 = this.getDrawing() && OS.IsWindowVisible(this.handle);
        }
        if (bl2) {
            OS.DefWindowProc(this.handle, 11, 0L, 0L);
        }
        LRESULT lRESULT = super.WM_VSCROLL(l2, l3);
        if (bl2) {
            OS.DefWindowProc(this.handle, 11, 1L, 0L);
            n = 1157;
            OS.RedrawWindow(this.handle, null, 0L, 1157);
            if (OS.WIN32_VERSION >= OS.VERSION(6, 0)) {
                rECT = new RECT();
                RECT rECT2 = new RECT();
                OS.GetClientRect(this.handle, rECT2);
                object = new boolean[this.columnCount];
                for (int i = 0; i < this.columnCount; ++i) {
                    object[i] = true;
                    rECT.top = i;
                    rECT.left = 0;
                    if (OS.SendMessage(this.handle, 4152, 0L, rECT) == 0L) continue;
                    rECT.top = rECT2.top;
                    rECT.bottom = rECT2.bottom;
                    object[i] = OS.IntersectRect(rECT, rECT2, rECT);
                }
                this.columnVisible = (boolean[])object;
                OS.UpdateWindow(this.handle);
                this.columnVisible = null;
            }
        }
        if (bl) {
            OS.SetWindowLongPtr(this.handle, -4, l5);
            OS.SetWindowLongPtr(l6, -4, l4);
        }
        if (this != false) {
            n = OS.LOWORD(l2);
            switch (n) {
                case 0: 
                case 1: {
                    rECT = new RECT();
                    OS.GetWindowRect(l6, rECT);
                    int n3 = rECT.bottom - rECT.top;
                    object = new RECT();
                    OS.GetClientRect(this.handle, (RECT)object);
                    RECT rECT3 = object;
                    rECT3.top += n3;
                    long l7 = OS.SendMessage(this.handle, 4160, 0L, 0L);
                    long l8 = OS.SendMessage(this.handle, 4160, 1L, 0L);
                    int n4 = OS.HIWORD(l8) - OS.HIWORD(l7);
                    if (n == 1) {
                        ((RECT)object).top = ((RECT)object).bottom - n4 - 1;
                    } else {
                        ((RECT)object).bottom = ((RECT)object).top + n4 + 1;
                    }
                    OS.InvalidateRect(this.handle, (RECT)object, true);
                    break;
                }
                case 2: 
                case 3: {
                    OS.InvalidateRect(this.handle, null, true);
                }
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT wmMeasureChild(long l2, long l3) {
        MEASUREITEMSTRUCT mEASUREITEMSTRUCT = new MEASUREITEMSTRUCT();
        OS.MoveMemory(mEASUREITEMSTRUCT, l3, MEASUREITEMSTRUCT.sizeof);
        if (this.itemHeight == -1) {
            long l4 = OS.SendMessage(this.handle, 4160, 0L, 0L);
            long l5 = OS.SendMessage(this.handle, 4160, 1L, 0L);
            mEASUREITEMSTRUCT.itemHeight = OS.HIWORD(l5) - OS.HIWORD(l4);
        } else {
            mEASUREITEMSTRUCT.itemHeight = this.itemHeight;
        }
        OS.MoveMemory(l3, mEASUREITEMSTRUCT, MEASUREITEMSTRUCT.sizeof);
        return null;
    }

    @Override
    LRESULT wmNotify(NMHDR nMHDR, long l2, long l3) {
        LRESULT lRESULT;
        long l4 = OS.SendMessage(this.handle, 4174, 0L, 0L);
        if (nMHDR.hwndFrom == l4) {
            if (nMHDR.hwndFrom != this.itemToolTipHandle) {
                this.maybeEnableDarkSystemTheme(nMHDR.hwndFrom);
                this.itemToolTipHandle = nMHDR.hwndFrom;
            }
            if ((lRESULT = this.wmNotifyToolTip(nMHDR, l2, l3)) != null) {
                return lRESULT;
            }
        }
        if (nMHDR.hwndFrom == this.hwndHeader && (lRESULT = this.wmNotifyHeader(nMHDR, l2, l3)) != null) {
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
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl812 : ALOAD_0 - null : trying to set 3 previously set to 0
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
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl66 : ARETURN - null : Stack underflow
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
                if (this.toolTipText != null || !this.isCustomToolTip()) break;
                NMTTCUSTOMDRAW nMTTCUSTOMDRAW = new NMTTCUSTOMDRAW();
                OS.MoveMemory(nMTTCUSTOMDRAW, l3, NMTTCUSTOMDRAW.sizeof);
                return this.wmNotifyToolTip(nMTTCUSTOMDRAW, l3);
            }
            case -530: 
            case -521: {
                LRESULT lRESULT = super.wmNotify(nMHDR, l2, l3);
                if (lRESULT != null) {
                    return lRESULT;
                }
                if (nMHDR.code != -521) {
                    this.tipRequested = true;
                }
                long l4 = this.callWindowProc(this.handle, 78, l2, l3);
                if (nMHDR.code != -521) {
                    this.tipRequested = false;
                }
                if (this.toolTipText != null) break;
                if (this.isCustomToolTip()) {
                    LVHITTESTINFO lVHITTESTINFO = new LVHITTESTINFO();
                    int n = OS.GetMessagePos();
                    POINT pOINT = new POINT();
                    OS.POINTSTOPOINT(pOINT, n);
                    OS.ScreenToClient(this.handle, pOINT);
                    lVHITTESTINFO.x = pOINT.x;
                    lVHITTESTINFO.y = pOINT.y;
                    if (OS.SendMessage(this.handle, 4153, 0L, lVHITTESTINFO) >= 0L) {
                        long l5;
                        TableItem tableItem = this._getItem(lVHITTESTINFO.iItem);
                        long l6 = OS.GetDC(this.handle);
                        long l7 = 0L;
                        long l8 = OS.SendMessage(this.handle, 49, 0L, 0L);
                        if (l8 != 0L) {
                            l7 = OS.SelectObject(l6, l8);
                        }
                        if ((l5 = tableItem.fontHandle(lVHITTESTINFO.iSubItem)) != -1L) {
                            l5 = OS.SelectObject(l6, l5);
                        }
                        Event event = this.sendMeasureItemEvent(tableItem, lVHITTESTINFO.iItem, lVHITTESTINFO.iSubItem, l6);
                        if (!this.isDisposed() && !tableItem.isDisposed()) {
                            RECT rECT = new RECT();
                            Rectangle rectangle = event.getBoundsInPixels();
                            OS.SetRect(rECT, rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height);
                            if (nMHDR.code == -521) {
                                RECT rECT2 = this.toolTipRect(rECT);
                                OS.MapWindowPoints(this.handle, 0L, rECT2, 2);
                                long l9 = OS.SendMessage(this.handle, 4174, 0L, 0L);
                                int n2 = 20;
                                int n3 = rECT2.right - rECT2.left;
                                int n4 = rECT2.bottom - rECT2.top;
                                OS.SetWindowPos(l9, 0L, rECT2.left, rECT2.top, n3, n4, 20);
                            } else {
                                NMTTDISPINFO nMTTDISPINFO = new NMTTDISPINFO();
                                OS.MoveMemory(nMTTDISPINFO, l3, NMTTDISPINFO.sizeof);
                                if (nMTTDISPINFO.lpszText != 0L) {
                                    OS.MoveMemory(nMTTDISPINFO.lpszText, new char[1], 2);
                                    OS.MoveMemory(l3, nMTTDISPINFO, NMTTDISPINFO.sizeof);
                                }
                                RECT rECT3 = tableItem.getBounds(lVHITTESTINFO.iItem, lVHITTESTINFO.iSubItem, true, true, true, true, l6);
                                RECT rECT4 = new RECT();
                                OS.GetClientRect(this.handle, rECT4);
                                if (rECT.right > rECT3.right || rECT.right > rECT4.right) {
                                    String string = " ";
                                    if (" " != null) {
                                        Shell shell = this.getShell();
                                        char[] cArray = new char[2];
                                        " ".getChars(0, 1, cArray, 0);
                                        shell.setToolTipText(nMTTDISPINFO, cArray);
                                        OS.MoveMemory(l3, nMTTDISPINFO, NMTTDISPINFO.sizeof);
                                    }
                                }
                            }
                        }
                        if (l5 != -1L) {
                            l5 = OS.SelectObject(l6, l5);
                        }
                        if (l8 != 0L) {
                            OS.SelectObject(l6, l7);
                        }
                        OS.ReleaseDC(this.handle, l6);
                    }
                }
                return new LRESULT(l4);
            }
        }
        return null;
    }

    LRESULT wmNotifyToolTip(NMTTCUSTOMDRAW nMTTCUSTOMDRAW, long l2) {
        switch (nMTTCUSTOMDRAW.dwDrawStage) {
            case 1: {
                if (!this.isCustomToolTip()) break;
                return new LRESULT(18L);
            }
            case 2: {
                Object object;
                LVHITTESTINFO lVHITTESTINFO = new LVHITTESTINFO();
                int n = OS.GetMessagePos();
                POINT pOINT = new POINT();
                OS.POINTSTOPOINT(pOINT, n);
                OS.ScreenToClient(this.handle, pOINT);
                lVHITTESTINFO.x = pOINT.x;
                lVHITTESTINFO.y = pOINT.y;
                if (OS.SendMessage(this.handle, 4153, 0L, lVHITTESTINFO) < 0L) break;
                TableItem tableItem = this._getItem(lVHITTESTINFO.iItem);
                long l3 = OS.GetDC(this.handle);
                long l4 = tableItem.fontHandle(lVHITTESTINFO.iSubItem);
                if (l4 == -1L) {
                    l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
                }
                long l5 = OS.SelectObject(l3, l4);
                boolean bl = true;
                RECT rECT = tableItem.getBounds(lVHITTESTINFO.iItem, lVHITTESTINFO.iSubItem, true, true, false, false, l3);
                if (this.hooks(40)) {
                    object = this.sendEraseItemEvent(tableItem, nMTTCUSTOMDRAW, lVHITTESTINFO.iSubItem, rECT);
                    if (this.isDisposed() || tableItem.isDisposed()) break;
                    boolean bl2 = bl = ((Event)object).doit && (((Event)object).detail & 0x10) != 0;
                }
                if (bl) {
                    Object object2;
                    Object object3;
                    Image image;
                    int n2 = OS.SaveDC(nMTTCUSTOMDRAW.hdc);
                    int n3 = this.getLinesVisible() ? 1 : 0;
                    RECT rECT2 = this.toolTipInset(rECT);
                    OS.SetWindowOrgEx(nMTTCUSTOMDRAW.hdc, rECT2.left, rECT2.top, null);
                    GCData gCData = new GCData();
                    gCData.device = this.display;
                    gCData.foreground = OS.GetTextColor(nMTTCUSTOMDRAW.hdc);
                    gCData.background = OS.GetBkColor(nMTTCUSTOMDRAW.hdc);
                    gCData.font = Font.win32_new(this.display, l4);
                    GC gC = GC.win32_new(nMTTCUSTOMDRAW.hdc, gCData);
                    int n4 = rECT.left;
                    if (lVHITTESTINFO.iSubItem != 0) {
                        n4 -= n3;
                    }
                    if ((image = tableItem.getImage(lVHITTESTINFO.iSubItem)) != null) {
                        object3 = image.getBoundsInPixels();
                        RECT rECT3 = tableItem.getBounds(lVHITTESTINFO.iItem, lVHITTESTINFO.iSubItem, false, true, false, false, l3);
                        object2 = this.imageList == null ? new Point(((Rectangle)object3).width, ((Rectangle)object3).height) : this.imageList.getImageSize();
                        int n5 = rECT3.top + Math.max(0, (rECT3.bottom - rECT3.top - ((Point)object2).y) / 2);
                        object3 = DPIUtil.autoScaleDown((Rectangle)object3);
                        gC.drawImage(image, ((Rectangle)object3).x, ((Rectangle)object3).y, ((Rectangle)object3).width, ((Rectangle)object3).height, DPIUtil.autoScaleDown(n4), DPIUtil.autoScaleDown(n5), DPIUtil.autoScaleDown(((Point)object2).x), DPIUtil.autoScaleDown(((Point)object2).y));
                        n4 += ((Point)object2).x + 4 + (lVHITTESTINFO.iSubItem == 0 ? -2 : 4);
                    } else {
                        n4 += 6;
                    }
                    object3 = tableItem.getText(lVHITTESTINFO.iSubItem);
                    if (object3 != null) {
                        int n6 = 2084;
                        Object object4 = object2 = this.columns != null ? this.columns[lVHITTESTINFO.iSubItem] : null;
                        if (object2 != null) {
                            if ((((TableColumn)object2).style & 0x1000000) != 0) {
                                n6 |= 1;
                            }
                            if ((((TableColumn)object2).style & 0x20000) != 0) {
                                n6 |= 2;
                            }
                        }
                        char[] cArray = ((String)object3).toCharArray();
                        RECT rECT4 = new RECT();
                        OS.SetRect(rECT4, n4, rECT.top, rECT.right, rECT.bottom);
                        OS.DrawText(nMTTCUSTOMDRAW.hdc, cArray, cArray.length, rECT4, n6);
                    }
                    gC.dispose();
                    OS.RestoreDC(nMTTCUSTOMDRAW.hdc, n2);
                }
                if (this.hooks(42)) {
                    object = tableItem.getBounds(lVHITTESTINFO.iItem, lVHITTESTINFO.iSubItem, true, true, false, false, l3);
                    this.sendPaintItemEvent(tableItem, nMTTCUSTOMDRAW, lVHITTESTINFO.iSubItem, (RECT)object);
                }
                OS.SelectObject(l3, l5);
                OS.ReleaseDC(this.handle, l3);
                break;
            }
        }
        return null;
    }

    static {
        COMPRESS_ITEMS = true;
        TableClass = new TCHAR(0, "SysListView32", true);
        HeaderClass = new TCHAR(0, "SysHeader32", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, TableClass, wNDCLASS);
        TableProc = wNDCLASS.lpfnWndProc;
        OS.GetClassInfo(0L, HeaderClass, wNDCLASS);
        HeaderProc = wNDCLASS.lpfnWndProc;
    }
}

