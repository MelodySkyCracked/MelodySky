/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.TypedListener;

public class ExpandBar
extends Composite {
    ExpandItem[] items;
    int itemCount;
    ExpandItem focusItem;
    int spacing = 4;
    int yCurrentScroll;
    long hFont;

    public ExpandBar(Composite composite, int n) {
        super(composite, ExpandBar.checkStyle(n));
    }

    public void addExpandListener(ExpandListener expandListener) {
        this.checkWidget();
        if (expandListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(expandListener);
        this.addListener(17, typedListener);
        this.addListener(18, typedListener);
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        return OS.DefWindowProc(l2, n, l3, l4);
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    static int checkStyle(int n) {
        return (n &= 0xFFFFFEFF) | 0x40000;
    }

    /*
     * Exception decompiling
     */
    @Override
    Point computeSizeInPixels(int var1, int var2, boolean var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl21 : IF_ICMPNE - null : Stack underflow
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

    @Override
    void createHandle() {
        super.createHandle();
        this.state &= 0xFFFFFFFD;
        this.state |= 0x2000;
    }

    void createItem(ExpandItem expandItem, int n, int n2) {
        Object object;
        if (0 > n2 || n2 > this.itemCount) {
            this.error(6);
        }
        if (this.itemCount == this.items.length) {
            object = new ExpandItem[this.itemCount + 4];
            System.arraycopy(this.items, 0, object, 0, this.items.length);
            this.items = object;
        }
        System.arraycopy(this.items, n2, this.items, n2 + 1, this.itemCount - n2);
        this.items[n2] = expandItem;
        ++this.itemCount;
        if (this.focusItem == null) {
            this.focusItem = expandItem;
        }
        object = new RECT();
        OS.GetWindowRect(this.handle, (RECT)object);
        expandItem.width = Math.max(0, object.right - object.left - this.spacing * 2);
        this.layoutItems(n2, true);
    }

    /*
     * Exception decompiling
     */
    @Override
    void createWidget() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl8 : IF_ICMPNE - null : Stack underflow
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

    /*
     * Exception decompiling
     */
    @Override
    int defaultBackground() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl2 : IF_ICMPNE - null : Stack underflow
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

    void destroyItem(ExpandItem expandItem) {
        int n;
        for (n = 0; n < this.itemCount && this.items[n] != expandItem; ++n) {
        }
        if (n == this.itemCount) {
            return;
        }
        if (expandItem == this.focusItem) {
            int n2;
            int n3 = n2 = n > 0 ? n - 1 : 1;
            if (n2 < this.itemCount) {
                this.focusItem = this.items[n2];
                this.focusItem.redraw(true);
            } else {
                this.focusItem = null;
            }
        }
        System.arraycopy(this.items, n + 1, this.items, n, --this.itemCount - n);
        this.items[this.itemCount] = null;
        expandItem.redraw(true);
        this.layoutItems(n, true);
    }

    @Override
    void drawThemeBackground(long l2, long l3, RECT rECT) {
        RECT rECT2 = new RECT();
        OS.GetClientRect(this.handle, rECT2);
        OS.MapWindowPoints(this.handle, l3, rECT2, 2);
        OS.DrawThemeBackground(this.display.hExplorerBarTheme(), l2, 5, 0, rECT2, null);
    }

    /*
     * Exception decompiling
     */
    void drawWidget(GC var1, RECT var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl4 : IF_ICMPNE - null : Stack underflow
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

    /*
     * Exception decompiling
     */
    @Override
    Control findBackgroundControl() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl5 : IF_ICMPNE - null : Stack underflow
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

    /*
     * Exception decompiling
     */
    @Override
    Control findThemeControl() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl2 : IF_ICMPNE - null : Stack underflow
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

    int getBandHeight() {
        long l2 = OS.GetDC(this.handle);
        long l3 = OS.SelectObject(l2, this.hFont == 0L ? this.defaultFont() : this.hFont);
        TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
        OS.GetTextMetrics(l2, tEXTMETRIC);
        OS.SelectObject(l2, l3);
        OS.ReleaseDC(this.handle, l2);
        return Math.max(24, tEXTMETRIC.tmHeight + 4);
    }

    public ExpandItem getItem(int n) {
        this.checkWidget();
        if (0 > n || n >= this.itemCount) {
            this.error(6);
        }
        return this.items[n];
    }

    public int getItemCount() {
        this.checkWidget();
        return this.itemCount;
    }

    public ExpandItem[] getItems() {
        this.checkWidget();
        ExpandItem[] expandItemArray = new ExpandItem[this.itemCount];
        System.arraycopy(this.items, 0, expandItemArray, 0, this.itemCount);
        return expandItemArray;
    }

    public int getSpacing() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getSpacingInPixels());
    }

    int getSpacingInPixels() {
        return this.spacing;
    }

    public int indexOf(ExpandItem expandItem) {
        this.checkWidget();
        if (expandItem == null) {
            this.error(4);
        }
        for (int i = 0; i < this.itemCount; ++i) {
            if (this.items[i] != expandItem) continue;
            return i;
        }
        return -1;
    }

    void layoutItems(int n, boolean bl) {
        if (n < this.itemCount) {
            ExpandItem expandItem;
            int n2;
            int n3 = this.spacing - this.yCurrentScroll;
            for (n2 = 0; n2 < n; ++n2) {
                expandItem = this.items[n2];
                if (expandItem.expanded) {
                    n3 += expandItem.height;
                }
                n3 += expandItem.getHeaderHeightInPixels() + this.spacing;
            }
            for (n2 = n; n2 < this.itemCount; ++n2) {
                expandItem = this.items[n2];
                expandItem.setBoundsInPixels(this.spacing, n3, 0, 0, true, false);
                if (expandItem.expanded) {
                    n3 += expandItem.height;
                }
                n3 += expandItem.getHeaderHeightInPixels() + this.spacing;
            }
        }
        if (bl) {
            this.setScrollbar();
        }
    }

    @Override
    void releaseChildren(boolean bl) {
        if (this.items != null) {
            for (ExpandItem expandItem : this.items) {
                if (expandItem == null || expandItem.isDisposed()) continue;
                expandItem.release(false);
            }
            this.items = null;
        }
        this.focusItem = null;
        super.releaseChildren(bl);
    }

    public void removeExpandListener(ExpandListener expandListener) {
        this.checkWidget();
        if (expandListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(17, expandListener);
        this.eventTable.unhook(18, expandListener);
    }

    @Override
    void reskinChildren(int n) {
        if (this.items != null) {
            for (ExpandItem expandItem : this.items) {
                if (expandItem == null) continue;
                expandItem.reskin(n);
            }
        }
        super.reskinChildren(n);
    }

    @Override
    void setBackgroundPixel(int n) {
        super.setBackgroundPixel(n);
        int n2 = 1157;
        OS.RedrawWindow(this.handle, null, 0L, 1157);
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        this.hFont = font != null ? font.handle : 0L;
        this.layoutItems(0, true);
    }

    @Override
    void setForegroundPixel(int n) {
        super.setForegroundPixel(n);
        int n2 = 1157;
        OS.RedrawWindow(this.handle, null, 0L, 1157);
    }

    void setScrollbar() {
        if (this.itemCount == 0) {
            return;
        }
        if ((this.style & 0x200) == 0) {
            return;
        }
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        int n = rECT.bottom - rECT.top;
        ExpandItem expandItem = this.items[this.itemCount - 1];
        int n2 = expandItem.y + this.getBandHeight() + this.spacing;
        if (expandItem.expanded) {
            n2 += expandItem.height;
        }
        if (this.yCurrentScroll > 0 && n > n2) {
            this.yCurrentScroll = Math.max(0, this.yCurrentScroll + n2 - n);
            this.layoutItems(0, false);
        }
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 7;
        sCROLLINFO.nMin = 0;
        sCROLLINFO.nMax = n2 += this.yCurrentScroll;
        sCROLLINFO.nPage = n;
        sCROLLINFO.nPos = Math.min(this.yCurrentScroll, sCROLLINFO.nMax);
        if (sCROLLINFO.nPage != 0) {
            SCROLLINFO sCROLLINFO2 = sCROLLINFO;
            ++sCROLLINFO2.nPage;
        }
        OS.SetScrollInfo(this.handle, 1, sCROLLINFO, true);
    }

    public void setSpacing(int n) {
        this.checkWidget();
        this.setSpacingInPixels(DPIUtil.autoScaleUp(n));
    }

    void setSpacingInPixels(int n) {
        if (n < 0) {
            return;
        }
        if (n == this.spacing) {
            return;
        }
        this.spacing = n;
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        int n2 = Math.max(0, rECT.right - rECT.left - n * 2);
        for (int i = 0; i < this.itemCount; ++i) {
            ExpandItem expandItem = this.items[i];
            if (expandItem.width == n2) continue;
            expandItem.setBoundsInPixels(0, 0, n2, expandItem.height, false, true);
        }
        this.layoutItems(0, true);
        OS.InvalidateRect(this.handle, null, true);
    }

    @Override
    boolean updateTextDirection(int n) {
        if (super.updateTextDirection(n)) {
            for (ExpandItem expandItem : this.items) {
                if (expandItem == null) continue;
                expandItem.updateTextDirection(n == 0x6000000 ? 0x6000000 : this.style & Integer.MIN_VALUE);
            }
            return true;
        }
        return false;
    }

    void showItem(ExpandItem expandItem) {
        Control control = expandItem.control;
        if (control != null && !control.isDisposed()) {
            control.setVisible(expandItem.expanded);
        }
        expandItem.redraw(true);
        int n = this.indexOf(expandItem);
        this.layoutItems(n + 1, true);
    }

    void showFocus(boolean bl) {
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        int n = rECT.bottom - rECT.top;
        int n2 = 0;
        if (bl) {
            if (this.focusItem.y < 0) {
                n2 = Math.min(this.yCurrentScroll, -this.focusItem.y);
            }
        } else {
            int n3 = this.focusItem.y + this.getBandHeight();
            if (this.focusItem.expanded && n >= this.getBandHeight() + this.focusItem.height) {
                n3 += this.focusItem.height;
            }
            if (n3 > n) {
                n2 = n - n3;
            }
        }
        if (n2 != 0) {
            this.yCurrentScroll = Math.max(0, this.yCurrentScroll - n2);
            if ((this.style & 0x200) != 0) {
                SCROLLINFO sCROLLINFO = new SCROLLINFO();
                sCROLLINFO.cbSize = SCROLLINFO.sizeof;
                sCROLLINFO.fMask = 4;
                sCROLLINFO.nPos = this.yCurrentScroll;
                OS.SetScrollInfo(this.handle, 1, sCROLLINFO, true);
            }
            OS.ScrollWindowEx(this.handle, 0, n2, null, null, 0L, null, 3);
            for (int i = 0; i < this.itemCount; ++i) {
                ExpandItem expandItem = this.items[i];
                expandItem.y += n2;
            }
        }
    }

    @Override
    TCHAR windowClass() {
        return this.display.windowClass;
    }

    @Override
    long windowProc() {
        return this.display.windowProc;
    }

    @Override
    LRESULT WM_KEYDOWN(long l2, long l3) {
        LRESULT lRESULT = super.WM_KEYDOWN(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (this.focusItem == null) {
            return lRESULT;
        }
        switch ((int)l2) {
            case 13: 
            case 32: {
                Event event = new Event();
                event.item = this.focusItem;
                this.sendEvent(this.focusItem.expanded ? 18 : 17, event);
                this.focusItem.expanded = !this.focusItem.expanded;
                this.showItem(this.focusItem);
                return LRESULT.ZERO;
            }
            case 38: {
                int n = this.indexOf(this.focusItem);
                if (n <= 0) break;
                this.focusItem.redraw(true);
                this.focusItem = this.items[n - 1];
                this.focusItem.redraw(true);
                this.showFocus(true);
                return LRESULT.ZERO;
            }
            case 40: {
                int n = this.indexOf(this.focusItem);
                if (n >= this.itemCount - 1) break;
                this.focusItem.redraw(true);
                this.focusItem = this.items[n + 1];
                this.focusItem.redraw(true);
                this.showFocus(false);
                return LRESULT.ZERO;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_KILLFOCUS(long l2, long l3) {
        LRESULT lRESULT = super.WM_KILLFOCUS(l2, l3);
        if (this.focusItem != null) {
            this.focusItem.redraw(true);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_LBUTTONDOWN(long l2, long l3) {
        LRESULT lRESULT = super.WM_LBUTTONDOWN(l2, l3);
        if (lRESULT == LRESULT.ZERO) {
            return lRESULT;
        }
        int n = OS.GET_X_LPARAM(l3);
        int n2 = OS.GET_Y_LPARAM(l3);
        for (int i = 0; i < this.itemCount; ++i) {
            ExpandItem expandItem = this.items[i];
            boolean bl = expandItem.isHover(n, n2);
            if (!bl || this.focusItem == expandItem) continue;
            this.focusItem.redraw(true);
            this.focusItem = expandItem;
            this.focusItem.redraw(true);
            this.forceFocus();
            break;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_LBUTTONUP(long l2, long l3) {
        int n;
        LRESULT lRESULT = super.WM_LBUTTONUP(l2, l3);
        if (lRESULT == LRESULT.ZERO) {
            return lRESULT;
        }
        if (this.focusItem == null) {
            return lRESULT;
        }
        int n2 = OS.GET_X_LPARAM(l3);
        boolean bl = this.focusItem.isHover(n2, n = OS.GET_Y_LPARAM(l3));
        if (bl) {
            Event event = new Event();
            event.item = this.focusItem;
            this.sendEvent(this.focusItem.expanded ? 18 : 17, event);
            this.focusItem.expanded = !this.focusItem.expanded;
            this.showItem(this.focusItem);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_MOUSELEAVE(long l2, long l3) {
        LRESULT lRESULT = super.WM_MOUSELEAVE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        for (int i = 0; i < this.itemCount; ++i) {
            ExpandItem expandItem = this.items[i];
            if (!expandItem.hover) continue;
            expandItem.hover = false;
            expandItem.redraw(false);
            break;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_MOUSEMOVE(long l2, long l3) {
        LRESULT lRESULT = super.WM_MOUSEMOVE(l2, l3);
        if (lRESULT == LRESULT.ZERO) {
            return lRESULT;
        }
        int n = OS.GET_X_LPARAM(l3);
        int n2 = OS.GET_Y_LPARAM(l3);
        for (int i = 0; i < this.itemCount; ++i) {
            ExpandItem expandItem = this.items[i];
            boolean bl = expandItem.isHover(n, n2);
            if (expandItem.hover == bl) continue;
            expandItem.hover = bl;
            expandItem.redraw(false);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_MOUSEWHEEL(long l2, long l3) {
        return this.wmScrollWheel(true, l2, l3, false);
    }

    @Override
    LRESULT WM_PAINT(long l2, long l3) {
        if ((this.state & 0x1000) != 0) {
            return LRESULT.ZERO;
        }
        PAINTSTRUCT pAINTSTRUCT = new PAINTSTRUCT();
        GCData gCData = new GCData();
        gCData.ps = pAINTSTRUCT;
        gCData.hwnd = this.handle;
        GC gC = this.new_GC(gCData);
        if (gC != null) {
            int n = pAINTSTRUCT.right - pAINTSTRUCT.left;
            int n2 = pAINTSTRUCT.bottom - pAINTSTRUCT.top;
            if (n != 0 && n2 != 0) {
                RECT rECT = new RECT();
                OS.SetRect(rECT, pAINTSTRUCT.left, pAINTSTRUCT.top, pAINTSTRUCT.right, pAINTSTRUCT.bottom);
                this.drawWidget(gC, rECT);
                if (this.hooks(9) || this.filters(9)) {
                    Event event = new Event();
                    event.gc = gC;
                    event.setBoundsInPixels(new Rectangle(rECT.left, rECT.top, n, n2));
                    this.sendEvent(9, event);
                    event.gc = null;
                }
            }
            gC.dispose();
        }
        return LRESULT.ZERO;
    }

    @Override
    LRESULT WM_PRINTCLIENT(long l2, long l3) {
        LRESULT lRESULT = super.WM_PRINTCLIENT(l2, l3);
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        GCData gCData = new GCData();
        gCData.device = this.display;
        gCData.foreground = this.getForegroundPixel();
        GC gC = GC.win32_new(l2, gCData);
        this.drawWidget(gC, rECT);
        gC.dispose();
        return lRESULT;
    }

    @Override
    LRESULT WM_SETCURSOR(long l2, long l3) {
        LRESULT lRESULT = super.WM_SETCURSOR(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        short s = (short)OS.LOWORD(l3);
        if (s == 1) {
            for (int i = 0; i < this.itemCount; ++i) {
                ExpandItem expandItem = this.items[i];
                if (!expandItem.hover) continue;
                long l4 = OS.LoadCursor(0L, 32649L);
                OS.SetCursor(l4);
                return LRESULT.ONE;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SETFOCUS(long l2, long l3) {
        LRESULT lRESULT = super.WM_SETFOCUS(l2, l3);
        if (this.focusItem != null) {
            this.focusItem.redraw(true);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        LRESULT lRESULT = super.WM_SIZE(l2, l3);
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        int n = Math.max(0, rECT.right - rECT.left - this.spacing * 2);
        for (int i = 0; i < this.itemCount; ++i) {
            ExpandItem expandItem = this.items[i];
            if (expandItem.width == n) continue;
            expandItem.setBoundsInPixels(0, 0, n, expandItem.height, false, true);
        }
        this.setScrollbar();
        OS.InvalidateRect(this.handle, null, true);
        return lRESULT;
    }

    @Override
    LRESULT wmScroll(ScrollBar scrollBar, boolean bl, long l2, int n, long l3, long l4) {
        LRESULT lRESULT = super.wmScroll(scrollBar, true, l2, n, l3, l4);
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 4;
        OS.GetScrollInfo(this.handle, 1, sCROLLINFO);
        int n2 = this.yCurrentScroll - sCROLLINFO.nPos;
        OS.ScrollWindowEx(this.handle, 0, n2, null, null, 0L, null, 3);
        this.yCurrentScroll = sCROLLINFO.nPos;
        if (n2 != 0) {
            for (int i = 0; i < this.itemCount; ++i) {
                ExpandItem expandItem = this.items[i];
                expandItem.y += n2;
            }
        }
        return lRESULT;
    }
}

