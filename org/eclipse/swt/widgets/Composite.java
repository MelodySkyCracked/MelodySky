/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ExceptionStash;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMTTDISPINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Widget;

public class Composite
extends Scrollable {
    Layout layout;
    WINDOWPOS[] lpwp;
    Control[] tabList;
    int layoutCount;
    int backgroundMode;
    static final int TOOLTIP_LIMIT = 4096;

    Composite() {
    }

    public Composite(Composite composite, int n) {
        super(composite, n);
    }

    Control[] _getChildren() {
        Control[] controlArray;
        int n = 0;
        long l2 = OS.GetWindow(this.handle, 5);
        if (l2 == 0L) {
            return new Control[0];
        }
        while (l2 != 0L) {
            ++n;
            l2 = OS.GetWindow(l2, 2);
        }
        Control[] controlArray2 = new Control[n];
        int n2 = 0;
        l2 = OS.GetWindow(this.handle, 5);
        while (l2 != 0L) {
            controlArray = this.display.getControl(l2);
            if (controlArray != null && controlArray != this) {
                controlArray2[n2++] = controlArray;
            }
            l2 = OS.GetWindow(l2, 2);
        }
        if (n == n2) {
            return controlArray2;
        }
        controlArray = new Control[n2];
        System.arraycopy(controlArray2, 0, controlArray, 0, n2);
        return controlArray;
    }

    Control[] _getTabList() {
        if (this.tabList == null) {
            return this.tabList;
        }
        int n = 0;
        for (Control control : this.tabList) {
            if (control.isDisposed()) continue;
            ++n;
        }
        if (n == this.tabList.length) {
            return this.tabList;
        }
        Control[] controlArray = new Control[n];
        int n2 = 0;
        for (Control control : this.tabList) {
            if (control.isDisposed()) continue;
            controlArray[n2++] = control;
        }
        this.tabList = controlArray;
        return controlArray;
    }

    @Deprecated
    public void changed(Control[] controlArray) {
        this.layout(controlArray, 4);
    }

    @Override
    void checkBuffered() {
        if ((this.state & 2) == 0) {
            super.checkBuffered();
        }
    }

    @Override
    void checkComposited() {
        if ((this.state & 2) != 0 && (this.style & 0x40000000) != 0) {
            long l2 = this.parent.handle;
            int n = OS.GetWindowLong(l2, -20);
            OS.SetWindowLong(l2, -20, n |= 0x2000000);
        }
    }

    @Override
    protected void checkSubclass() {
    }

    @Override
    Widget[] computeTabList() {
        Widget[] widgetArray = super.computeTabList();
        if (widgetArray.length == 0) {
            return widgetArray;
        }
        Control[] controlArray = this.tabList != null ? this._getTabList() : this._getChildren();
        Control[] controlArray2 = controlArray;
        for (Control control : controlArray) {
            Widget[] widgetArray2 = control.computeTabList();
            if (widgetArray2.length == 0) continue;
            Widget[] widgetArray3 = new Widget[widgetArray.length + widgetArray2.length];
            System.arraycopy(widgetArray, 0, widgetArray3, 0, widgetArray.length);
            System.arraycopy(widgetArray2, 0, widgetArray3, widgetArray.length, widgetArray2.length);
            widgetArray = widgetArray3;
        }
        return widgetArray;
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        Point point;
        this.display.runSkin();
        if (this.layout != null) {
            if (n == -1 || n2 == -1) {
                boolean bl2 = (this.state & 0x40) != 0;
                this.state &= 0xFFFFFFBF;
                point = DPIUtil.autoScaleUp(this.layout.computeSize(this, DPIUtil.autoScaleDown(n), DPIUtil.autoScaleDown(n2), bl |= bl2));
            } else {
                point = new Point(n, n2);
            }
        } else {
            point = this.minimumSize(n, n2, bl);
            if (point.x == 0) {
                point.x = 64;
            }
            if (point.y == 0) {
                point.y = 64;
            }
        }
        if (n != -1) {
            point.x = n;
        }
        if (n2 != -1) {
            point.y = n2;
        }
        Rectangle rectangle = DPIUtil.autoScaleUp(this.computeTrim(0, 0, DPIUtil.autoScaleDown(point.x), DPIUtil.autoScaleDown(point.y)));
        return new Point(rectangle.width, rectangle.height);
    }

    void copyArea(GC gC, int n, int n2, int n3, int n4) {
        this.checkWidget();
        if (gC == null) {
            this.error(4);
        }
        if (gC.isDisposed()) {
            this.error(5);
        }
        long l2 = gC.handle;
        int n5 = OS.SaveDC(l2);
        OS.IntersectClipRect(l2, 0, 0, n3, n4);
        POINT pOINT = new POINT();
        long l3 = OS.GetParent(this.handle);
        OS.MapWindowPoints(this.handle, l3, pOINT, 1);
        RECT rECT = new RECT();
        OS.GetWindowRect(this.handle, rECT);
        POINT pOINT2 = new POINT();
        POINT pOINT3 = new POINT();
        OS.SetWindowOrgEx(l2, n += pOINT.x - rECT.left, n2 += pOINT.y - rECT.top, pOINT2);
        OS.SetBrushOrgEx(l2, n, n2, pOINT3);
        int n6 = OS.GetWindowLong(this.handle, -16);
        if ((n6 & 0x10000000) == 0) {
            OS.DefWindowProc(this.handle, 11, 1L, 0L);
        }
        OS.RedrawWindow(this.handle, null, 0L, 384);
        OS.PrintWindow(this.handle, l2, 0);
        if ((n6 & 0x10000000) == 0) {
            OS.DefWindowProc(this.handle, 11, 0L, 0L);
        }
        OS.RestoreDC(l2, n5);
    }

    @Override
    void createHandle() {
        super.createHandle();
        this.state |= 2;
        if ((this.style & 0x300) == 0 || this.findThemeControl() == this.parent) {
            this.state |= 0x100;
        }
        if ((this.style & 0x40000000) != 0) {
            int n = OS.GetWindowLong(this.handle, -20);
            OS.SetWindowLong(this.handle, -20, n |= 0x20);
        }
    }

    @Override
    int applyThemeBackground() {
        return this.backgroundAlpha == 0 || (this.style & 0x300) == 0 || this.findThemeControl() == this.parent ? 1 : 0;
    }

    public void drawBackground(GC gC, int n, int n2, int n3, int n4, int n5, int n6) {
        this.checkWidget();
        n = DPIUtil.autoScaleUp(n);
        n2 = DPIUtil.autoScaleUp(n2);
        n3 = DPIUtil.autoScaleUp(n3);
        n4 = DPIUtil.autoScaleUp(n4);
        n5 = DPIUtil.autoScaleUp(n5);
        n6 = DPIUtil.autoScaleUp(n6);
        this.drawBackgroundInPixels(gC, n, n2, n3, n4, n5, n6);
    }

    void drawBackgroundInPixels(GC gC, int n, int n2, int n3, int n4, int n5, int n6) {
        if (gC == null) {
            this.error(4);
        }
        if (gC.isDisposed()) {
            this.error(5);
        }
        RECT rECT = new RECT();
        OS.SetRect(rECT, n, n2, n + n3, n2 + n4);
        long l2 = gC.handle;
        int n7 = this.background == -1 ? gC.getBackground().handle : -1;
        this.drawBackground(l2, rECT, n7, n5, n6);
    }

    Composite findDeferredControl() {
        return this.layoutCount > 0 ? this : this.parent.findDeferredControl();
    }

    @Override
    Menu[] findMenus(Control control) {
        if (control == this) {
            return new Menu[0];
        }
        Menu[] menuArray = super.findMenus(control);
        for (Control control2 : this._getChildren()) {
            Menu[] menuArray2 = control2.findMenus(control);
            if (menuArray2.length == 0) continue;
            Menu[] menuArray3 = new Menu[menuArray.length + menuArray2.length];
            System.arraycopy(menuArray, 0, menuArray3, 0, menuArray.length);
            System.arraycopy(menuArray2, 0, menuArray3, menuArray.length, menuArray2.length);
            menuArray = menuArray3;
        }
        return menuArray;
    }

    @Override
    void fixChildren(Shell shell, Shell shell2, Decorations decorations, Decorations decorations2, Menu[] menuArray) {
        super.fixChildren(shell, shell2, decorations, decorations2, menuArray);
        for (Control control : this._getChildren()) {
            control.fixChildren(shell, shell2, decorations, decorations2, menuArray);
        }
    }

    void fixTabList(Control control) {
        if (this.tabList == null) {
            return;
        }
        int n = 0;
        for (Control controlArray : this.tabList) {
            if (controlArray != control) continue;
            ++n;
        }
        if (n == 0) {
            return;
        }
        Control[] controlArray = null;
        int n2 = this.tabList.length - n;
        if (n2 != 0) {
            controlArray = new Control[n2];
            int n3 = 0;
            for (Control control2 : this.tabList) {
                if (control2 == control) continue;
                controlArray[n3++] = control2;
            }
        }
        this.tabList = controlArray;
    }

    public int getBackgroundMode() {
        this.checkWidget();
        return this.backgroundMode;
    }

    public Control[] getChildren() {
        this.checkWidget();
        return this._getChildren();
    }

    int getChildrenCount() {
        int n = 0;
        long l2 = OS.GetWindow(this.handle, 5);
        while (l2 != 0L) {
            ++n;
            l2 = OS.GetWindow(l2, 2);
        }
        return n;
    }

    public Layout getLayout() {
        this.checkWidget();
        return this.layout;
    }

    public Control[] getTabList() {
        this.checkWidget();
        Control[] controlArray = this._getTabList();
        if (controlArray == null) {
            Control[] controlArray2;
            int n = 0;
            Control[] controlArray3 = controlArray2 = this._getChildren();
            for (Control control : controlArray2) {
                if (!control.isTabGroup()) continue;
                ++n;
            }
            controlArray = new Control[n];
            int n2 = 0;
            for (Control control : controlArray3) {
                if (!control.isTabGroup()) continue;
                controlArray[n2++] = control;
            }
        }
        return controlArray;
    }

    public boolean getLayoutDeferred() {
        this.checkWidget();
        return this.layoutCount > 0;
    }

    public boolean isLayoutDeferred() {
        this.checkWidget();
        return this.findDeferredControl() != null;
    }

    public void layout() {
        this.checkWidget();
        this.layout(true);
    }

    public void layout(boolean bl) {
        this.checkWidget();
        if (this.layout == null) {
            return;
        }
        this.layout(bl, false);
    }

    public void layout(boolean bl, boolean bl2) {
        this.checkWidget();
        if (this.layout == null && !bl2) {
            return;
        }
        this.markLayout(bl, bl2);
        this.updateLayout(bl2);
    }

    public void layout(Control[] controlArray) {
        this.checkWidget();
        if (controlArray == null) {
            this.error(5);
        }
        this.layout(controlArray, 0);
    }

    public void layout(Control[] controlArray, int n) {
        this.checkWidget();
        if (controlArray != null) {
            Control control;
            int n2;
            for (Control control2 : controlArray) {
                if (control2 == null) {
                    this.error(5);
                }
                if (control2.isDisposed()) {
                    this.error(5);
                }
                n2 = 0;
                control = control2.parent;
                while (control != null) {
                    int n3 = n2 = control == this ? 1 : 0;
                    if (n2 != 0) break;
                    control = ((Composite)control).parent;
                }
                if (n2 != 0) continue;
                this.error(32);
            }
            int n4 = 0;
            Composite[] compositeArray = new Composite[16];
            Control[] controlArray2 = controlArray;
            int n5 = controlArray2.length;
            for (n2 = 0; n2 < n5; ++n2) {
                Control control3 = control = controlArray2[n2];
                Object object = control.parent;
                control.markLayout(false, false);
                while (control != this) {
                    Composite[] compositeArray2;
                    Composite[] compositeArray3;
                    if (object.layout != null) {
                        compositeArray3 = object;
                        compositeArray3.state |= 0x20;
                        if (!object.layout.flushCache(control)) {
                            Composite[] compositeArray4 = object;
                            compositeArray4.state |= 0x40;
                        }
                    }
                    if (n4 == compositeArray.length) {
                        compositeArray3 = new Composite[compositeArray.length + 16];
                        System.arraycopy(compositeArray, 0, compositeArray3, 0, compositeArray.length);
                        compositeArray = compositeArray3;
                    }
                    compositeArray3 = compositeArray;
                    int n6 = n4++;
                    compositeArray3[n6] = compositeArray2 = object;
                    control = compositeArray2;
                    object = control.parent;
                }
            }
            if (!this.display.externalEventLoop && (n & 4) != 0) {
                this.setLayoutDeferred(true);
                this.display.addLayoutDeferred(this);
            }
            for (int i = n4 - 1; i >= 0; --i) {
                compositeArray[i].updateLayout(false);
            }
        } else {
            if (this.layout == null && (n & 1) == 0) {
                return;
            }
            this.markLayout((n & 2) != 0, (n & 1) != 0);
            if (!this.display.externalEventLoop && (n & 4) != 0) {
                this.setLayoutDeferred(true);
                this.display.addLayoutDeferred(this);
            }
            this.updateLayout((n & 1) != 0);
        }
    }

    @Override
    void markLayout(boolean bl, boolean bl2) {
        if (this.layout != null) {
            this.state |= 0x20;
            if (bl) {
                this.state |= 0x40;
            }
        }
        if (bl2) {
            for (Control control : this._getChildren()) {
                control.markLayout(bl, bl2);
            }
        }
    }

    Point minimumSize(int n, int n2, boolean bl) {
        Rectangle rectangle = DPIUtil.autoScaleUp(this.getClientArea());
        int n3 = 0;
        int n4 = 0;
        for (Control control : this._getChildren()) {
            Rectangle rectangle2 = DPIUtil.autoScaleUp(control.getBounds());
            n3 = Math.max(n3, rectangle2.x - rectangle.x + rectangle2.width);
            n4 = Math.max(n4, rectangle2.y - rectangle.y + rectangle2.height);
        }
        return new Point(n3, n4);
    }

    @Override
    boolean redrawChildren() {
        if (!super.redrawChildren()) {
            return false;
        }
        for (Control control : this._getChildren()) {
            control.redrawChildren();
        }
        return true;
    }

    @Override
    void releaseParent() {
        super.releaseParent();
        if ((this.state & 2) != 0 && (this.style & 0x40000000) != 0) {
            long l2 = this.parent.handle;
            long l3 = OS.GetWindow(l2, 5);
            while (l3 != 0L) {
                int n;
                if (l3 != this.handle && ((n = OS.GetWindowLong(l2, -20)) & 0x20) != 0) {
                    return;
                }
                l3 = OS.GetWindow(l3, 2);
            }
            int n = OS.GetWindowLong(l2, -20);
            OS.SetWindowLong(l2, -20, n &= 0xFDFFFFFF);
        }
    }

    @Override
    void releaseChildren(boolean bl) {
        block8: {
            ExceptionStash exceptionStash;
            block9: {
                exceptionStash = new ExceptionStash();
                Throwable throwable = null;
                try {
                    for (Control control : this._getChildren()) {
                        if (control == null || control.isDisposed()) continue;
                        try {
                            control.release(false);
                        }
                        catch (Error | RuntimeException throwable2) {
                            exceptionStash.stash(throwable2);
                        }
                    }
                    super.releaseChildren(bl);
                    if (exceptionStash == null) break block8;
                    if (throwable == null) break block9;
                }
                catch (Throwable throwable3) {
                    throwable = throwable3;
                    throw throwable3;
                }
                try {
                    exceptionStash.close();
                }
                catch (Throwable throwable4) {
                    throwable.addSuppressed(throwable4);
                }
                break block8;
            }
            exceptionStash.close();
        }
    }

    @Override
    void releaseWidget() {
        int n;
        long l2;
        super.releaseWidget();
        if ((this.state & 2) != 0 && (this.style & 0x1000000) != 0 && (l2 = OS.GetWindow(this.handle, 5)) != 0L && (n = OS.GetWindowThreadProcessId(l2, null)) != OS.GetCurrentThreadId()) {
            OS.ShowWindow(l2, 0);
            OS.SetParent(l2, 0L);
        }
        this.layout = null;
        this.tabList = null;
        this.lpwp = null;
    }

    void removeControl(Control control) {
        this.fixTabList(control);
        this.resizeChildren();
    }

    @Override
    void reskinChildren(int n) {
        super.reskinChildren(n);
        for (Control control : this._getChildren()) {
            if (control == null) continue;
            control.reskin(n);
        }
    }

    /*
     * Exception decompiling
     */
    void resizeChildren() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl5 : ALOAD_0 - null : trying to set 2 previously set to 0
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

    void resizeEmbeddedHandle(long l2, int n, int n2) {
        if (l2 == 0L) {
            return;
        }
        int[] nArray = new int[]{0};
        int n3 = OS.GetWindowThreadProcessId(l2, nArray);
        if (n3 != OS.GetCurrentThreadId()) {
            if (nArray[0] == OS.GetCurrentProcessId() && this.display.msgHook == 0L) {
                this.display.getMsgCallback = new Callback(this.display, "getMsgProc", 3);
                this.display.getMsgProc = this.display.getMsgCallback.getAddress();
                this.display.msgHook = OS.SetWindowsHookEx(3, this.display.getMsgProc, OS.GetLibraryHandle(), n3);
                OS.PostThreadMessage(n3, 0, 0L, 0L);
            }
            int n4 = 16436;
            OS.SetWindowPos(l2, 0L, 0, 0, n, n2, 16436);
        }
    }

    @Override
    void sendResize() {
        this.setResizeChildren(false);
        super.sendResize();
        if (this.isDisposed()) {
            return;
        }
        if (this.layout != null) {
            this.markLayout(false, false);
            this.updateLayout(false, false);
        }
        this.setResizeChildren(true);
    }

    public void setBackgroundMode(int n) {
        this.checkWidget();
        this.backgroundMode = n;
        for (Control control : this._getChildren()) {
            control.updateBackgroundMode();
        }
    }

    @Override
    void setBoundsInPixels(int n, int n2, int n3, int n4, int n5, boolean bl) {
        if (this.display.resizeCount > 4) {
            bl = false;
        }
        if (!bl && (this.state & 2) != 0) {
            this.state &= 0xFFFAFFFF;
            this.state |= 0xA0000;
        }
        super.setBoundsInPixels(n, n2, n3, n4, n5, bl);
        if (!bl && (this.state & 2) != 0) {
            boolean bl2 = (this.state & 0x10000) != 0;
            boolean bl3 = (this.state & 0x40000) != 0;
            this.state &= 0xFFF5FFFF;
            if (bl2 && !this.isDisposed()) {
                this.sendMove();
            }
            if (bl3 && !this.isDisposed()) {
                this.sendResize();
            }
        }
    }

    public boolean setFocus() {
        Control[] controlArray;
        this.checkWidget();
        Control[] controlArray2 = controlArray = this._getChildren();
        for (Control control : controlArray) {
            if (!control.setRadioFocus(false)) continue;
            return true;
        }
        for (Control control : controlArray2) {
            if (!control.setFocus()) continue;
            return true;
        }
        return super.setFocus();
    }

    public void setLayout(Layout layout) {
        this.checkWidget();
        this.layout = layout;
    }

    public void setLayoutDeferred(boolean bl) {
        this.checkWidget();
        if (!bl) {
            int n;
            this.layoutCount = n = this.layoutCount - 1;
            if (n == 0 && ((this.state & 0x80) != 0 || (this.state & 0x20) != 0)) {
                this.updateLayout(true);
            }
        } else {
            ++this.layoutCount;
        }
    }

    public void setTabList(Control[] controlArray) {
        this.checkWidget();
        if (controlArray != null) {
            for (Control control : controlArray) {
                if (control == null) {
                    this.error(5);
                }
                if (control.isDisposed()) {
                    this.error(5);
                }
                if (control.parent == this) continue;
                this.error(32);
            }
            Control[] controlArray2 = new Control[controlArray.length];
            System.arraycopy(controlArray, 0, controlArray2, 0, controlArray.length);
            controlArray = controlArray2;
        }
        this.tabList = controlArray;
    }

    void setResizeChildren(boolean bl) {
        if (bl) {
            this.resizeChildren();
        } else {
            if (this.display.resizeCount > 4) {
                return;
            }
            int n = this.getChildrenCount();
            if (n > 1 && this.lpwp == null) {
                this.lpwp = new WINDOWPOS[n];
            }
        }
    }

    @Override
    boolean setTabGroupFocus() {
        Control[] controlArray;
        boolean bl;
        if (this.isTabItem()) {
            return this.setTabItemFocus();
        }
        boolean bl2 = bl = (this.style & 0x80000) == 0;
        if ((this.state & 2) != 0) {
            bl = this.hooksKeys();
            if ((this.style & 0x1000000) != 0) {
                bl = true;
            }
        }
        if (bl && this.setTabItemFocus()) {
            return true;
        }
        Control[] controlArray2 = controlArray = this._getChildren();
        for (Control control : controlArray) {
            if (control.isDisposed() || !control.isTabItem() || !control.setRadioFocus(true)) continue;
            return true;
        }
        for (Control control : controlArray2) {
            if (control.isDisposed() || !control.isTabItem() || control.isTabGroup() || !control.setTabItemFocus()) continue;
            return true;
        }
        return false;
    }

    @Override
    boolean updateTextDirection(int n) {
        super.updateTextDirection(n);
        Control[] controlArray = this._getChildren();
        int n2 = controlArray.length;
        while (n2-- > 0) {
            if (controlArray[n2] == null || controlArray[n2].isDisposed()) continue;
            controlArray[n2].updateTextDirection(n);
        }
        return true;
    }

    String toolTipText(NMTTDISPINFO nMTTDISPINFO) {
        Shell shell = this.getShell();
        if ((nMTTDISPINFO.uFlags & 1) == 0) {
            String string = null;
            ToolTip toolTip = shell.findToolTip((int)nMTTDISPINFO.idFrom);
            if (toolTip != null) {
                string = toolTip.message;
                if (string == null || string.length() == 0) {
                    string = " ";
                }
                if (string.length() > 1024) {
                    string = this.display.wrapText(string, this.handle, toolTip.getWidth());
                }
            }
            return string;
        }
        shell.setToolTipTitle(nMTTDISPINFO.hwndFrom, null, 0);
        OS.SendMessage(nMTTDISPINFO.hwndFrom, 1048, 0L, 32767L);
        Control control = this.display.getControl(nMTTDISPINFO.idFrom);
        return control != null ? control.toolTipText : null;
    }

    boolean translateMnemonic(Event event, Control control) {
        if (super.translateMnemonic(event, control)) {
            return true;
        }
        if (control != null) {
            for (Control control2 : this._getChildren()) {
                if (!control2.translateMnemonic(event, control)) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    boolean translateTraversal(MSG mSG) {
        if ((this.state & 2) != 0) {
            if ((this.style & 0x1000000) != 0) {
                return false;
            }
            switch ((int)mSG.wParam) {
                case 33: 
                case 34: 
                case 37: 
                case 38: 
                case 39: 
                case 40: {
                    int n = (int)OS.SendMessage(mSG.hwnd, 297, 0L, 0L);
                    if ((n & 1) == 0) break;
                    OS.SendMessage(mSG.hwnd, 296, OS.MAKEWPARAM(2, 1), 0L);
                    break;
                }
            }
        }
        return super.translateTraversal(mSG);
    }

    @Override
    void updateBackgroundColor() {
        super.updateBackgroundColor();
        for (Control control : this._getChildren()) {
            if ((control.state & 0x400) == 0) continue;
            control.updateBackgroundColor();
        }
    }

    @Override
    void updateBackgroundImage() {
        super.updateBackgroundImage();
        for (Control control : this._getChildren()) {
            if ((control.state & 0x400) == 0) continue;
            control.updateBackgroundImage();
        }
    }

    @Override
    void updateBackgroundMode() {
        super.updateBackgroundMode();
        for (Control control : this._getChildren()) {
            control.updateBackgroundMode();
        }
    }

    @Override
    void updateFont(Font font, Font font2) {
        super.updateFont(font, font2);
        for (Control control : this._getChildren()) {
            if (control.isDisposed()) continue;
            control.updateFont(font, font2);
        }
    }

    void updateLayout(boolean bl) {
        this.updateLayout(true, bl);
    }

    @Override
    void updateLayout(boolean bl, boolean bl2) {
        Composite composite = this.findDeferredControl();
        if (composite != null) {
            Composite composite2 = composite;
            composite2.state |= 0x80;
            return;
        }
        if ((this.state & 0x20) != 0) {
            boolean bl3 = (this.state & 0x40) != 0;
            this.state &= 0xFFFFFF9F;
            this.display.runSkin();
            if (bl) {
                this.setResizeChildren(false);
            }
            this.layout.layout(this, bl3);
            if (bl) {
                this.setResizeChildren(true);
            }
        }
        if (bl2) {
            this.state &= 0xFFFFFF7F;
            for (Control control : this._getChildren()) {
                control.updateLayout(bl, bl2);
            }
        }
    }

    @Override
    void updateOrientation() {
        Object object;
        Control[] controlArray = this._getChildren();
        RECT[] rECTArray = new RECT[controlArray.length];
        int n = 0;
        while (n < controlArray.length) {
            RECT rECT;
            Control control = controlArray[n];
            object = rECTArray;
            int n2 = n++;
            object[n2] = rECT = new RECT();
            RECT rECT2 = rECT;
            control.forceResize();
            OS.GetWindowRect(control.topHandle(), rECT2);
            OS.MapWindowPoints(0L, this.handle, rECT2, 2);
        }
        n = this.style & 0x6000000;
        super.updateOrientation();
        for (int i = 0; i < controlArray.length; ++i) {
            object = controlArray[i];
            RECT rECT = rECTArray[i];
            ((Control)object).setOrientation(n);
            int n3 = 21;
            OS.SetWindowPos(((Control)object).topHandle(), 0L, rECT.left, rECT.top, 0, 0, 21);
        }
    }

    void updateUIState() {
        long l2 = this.getShell().handle;
        int n = (int)OS.SendMessage(l2, 297, 0L, 0L);
        if ((n & 1) != 0) {
            OS.SendMessage(l2, 295, OS.MAKEWPARAM(2, 1), 0L);
        }
    }

    @Override
    int widgetStyle() {
        return super.widgetStyle() | 0x2000000;
    }

    @Override
    LRESULT WM_ERASEBKGND(long l2, long l3) {
        LRESULT lRESULT = super.WM_ERASEBKGND(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((this.state & 2) != 0 && (this.style & 0x40040000) != 0) {
            return LRESULT.ZERO;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_GETDLGCODE(long l2, long l3) {
        LRESULT lRESULT = super.WM_GETDLGCODE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((this.state & 2) != 0) {
            int n = 0;
            if (this == false) {
                n |= 7;
            }
            if ((this.style & 0x80000) != 0) {
                n |= 0x100;
            }
            if (OS.GetWindow(this.handle, 5) != 0L) {
                n |= 0x100;
            }
            if (n != 0) {
                return new LRESULT(n);
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_GETFONT(long l2, long l3) {
        LRESULT lRESULT = super.WM_GETFONT(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        long l4 = this.callWindowProc(this.handle, 49, l2, l3);
        if (l4 != 0L) {
            return new LRESULT(l4);
        }
        return new LRESULT(this.font != null ? this.font.handle : this.defaultFont());
    }

    @Override
    LRESULT WM_LBUTTONDOWN(long l2, long l3) {
        LRESULT lRESULT = super.WM_LBUTTONDOWN(l2, l3);
        if (lRESULT == LRESULT.ZERO) {
            return lRESULT;
        }
        if ((this.state & 2) != 0 && (this.style & 0x80000) == 0 && this == false && OS.GetWindow(this.handle, 5) == 0L) {
            this.setFocus();
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_NCHITTEST(long l2, long l3) {
        LRESULT lRESULT = super.WM_NCHITTEST(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (OS.IsAppThemed() && (this.state & 2) != 0) {
            long l4 = this.callWindowProc(this.handle, 132, l2, l3);
            if (l4 == 1L) {
                RECT rECT = new RECT();
                OS.GetClientRect(this.handle, rECT);
                POINT pOINT = new POINT();
                pOINT.x = OS.GET_X_LPARAM(l3);
                pOINT.y = OS.GET_Y_LPARAM(l3);
                OS.MapWindowPoints(0L, this.handle, pOINT, 1);
                if (!OS.PtInRect(rECT, pOINT)) {
                    int n = 1025;
                    OS.RedrawWindow(this.handle, null, 0L, 1025);
                }
            }
            return new LRESULT(l4);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_PARENTNOTIFY(long l2, long l3) {
        if ((this.state & 2) != 0 && (this.style & 0x1000000) != 0 && OS.LOWORD(l2) == 1) {
            RECT rECT = new RECT();
            OS.GetClientRect(this.handle, rECT);
            this.resizeEmbeddedHandle(l3, rECT.right - rECT.left, rECT.bottom - rECT.top);
        }
        return super.WM_PARENTNOTIFY(l2, l3);
    }

    @Override
    LRESULT WM_PAINT(long l2, long l3) {
        if ((this.state & 0x1000) != 0) {
            return LRESULT.ZERO;
        }
        if ((this.state & 2) == 0 || (this.state & 0x4000) != 0) {
            return super.WM_PAINT(l2, l3);
        }
        int n = OS.GetWindowLong(this.handle, -16);
        int n2 = n | 0x4000000 | 0x2000000;
        if (n2 != n) {
            OS.SetWindowLong(this.handle, -16, n2);
        }
        PAINTSTRUCT pAINTSTRUCT = new PAINTSTRUCT();
        if (this.hooks(9) || this.filters(9)) {
            boolean bl = false;
            if ((this.style & 0x20000000) != 0 && (this.style & 0x44200000) == 0) {
                bl = true;
            }
            if (bl) {
                long l4 = OS.BeginPaint(this.handle, pAINTSTRUCT);
                int n3 = pAINTSTRUCT.right - pAINTSTRUCT.left;
                int n4 = pAINTSTRUCT.bottom - pAINTSTRUCT.top;
                if (n3 != 0 && n4 != 0) {
                    Object object;
                    long[] lArray = new long[]{0L};
                    boolean bl2 = false;
                    RECT rECT = new RECT();
                    OS.SetRect(rECT, pAINTSTRUCT.left, pAINTSTRUCT.top, pAINTSTRUCT.right, pAINTSTRUCT.bottom);
                    long l5 = OS.BeginBufferedPaint(l4, rECT, 0, null, lArray);
                    GCData gCData = new GCData();
                    gCData.device = this.display;
                    gCData.foreground = this.getForegroundPixel();
                    Control control = this.findBackgroundControl();
                    if (control == null) {
                        control = this;
                    }
                    gCData.background = control.getBackgroundPixel();
                    gCData.font = Font.win32_new(this.display, OS.SendMessage(this.handle, 49, 0L, 0L));
                    gCData.uiState = (int)OS.SendMessage(this.handle, 297, 0L, 0L);
                    if ((this.style & 0x40000) == 0) {
                        object = new RECT();
                        OS.SetRect((RECT)object, pAINTSTRUCT.left, pAINTSTRUCT.top, pAINTSTRUCT.right, pAINTSTRUCT.bottom);
                        this.drawBackground(lArray[0], (RECT)object);
                    }
                    object = GC.win32_new(lArray[0], gCData);
                    Event event = new Event();
                    event.gc = object;
                    event.setBoundsInPixels(new Rectangle(pAINTSTRUCT.left, pAINTSTRUCT.top, n3, n4));
                    this.sendEvent(9, event);
                    if (gCData.focusDrawn && !this.isDisposed()) {
                        this.updateUIState();
                    }
                    ((Resource)object).dispose();
                    OS.EndBufferedPaint(l5, true);
                }
                OS.EndPaint(this.handle, pAINTSTRUCT);
            } else {
                GCData gCData = new GCData();
                gCData.ps = pAINTSTRUCT;
                gCData.hwnd = this.handle;
                GC gC = GC.win32_new(this, gCData);
                long l6 = 0L;
                if (((this.style & 0x60000000) != 0 || (this.style & 0x200000) != 0) && OS.GetRandomRgn(gC.handle, l6 = OS.CreateRectRgn(0, 0, 0, 0), 4) == 1) {
                    if ((OS.GetLayout(gC.handle) & 1) != 0) {
                        int n5 = OS.GetRegionData(l6, 0, null);
                        int[] nArray = new int[n5 / 4];
                        OS.GetRegionData(l6, n5, nArray);
                        long l7 = OS.ExtCreateRegion(new float[]{-1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f}, n5, nArray);
                        OS.DeleteObject(l6);
                        l6 = l7;
                    }
                    POINT pOINT = new POINT();
                    OS.MapWindowPoints(0L, this.handle, pOINT, 1);
                    OS.OffsetRgn(l6, pOINT.x, pOINT.y);
                }
                int n6 = pAINTSTRUCT.right - pAINTSTRUCT.left;
                int n7 = pAINTSTRUCT.bottom - pAINTSTRUCT.top;
                if (n6 != 0 && n7 != 0) {
                    RECT rECT;
                    Object object;
                    GC gC2 = null;
                    Image image = null;
                    if ((this.style & 0x60000000) != 0) {
                        image = new Image((Device)this.display, n6, n7);
                        gC2 = gC;
                        gC = new GC(image, gC2.getStyle() & 0x4000000);
                        object = gC.getGCData();
                        ((GCData)object).uiState = gCData.uiState;
                        gC.setForeground(this.getForeground());
                        gC.setBackground(this.getBackground());
                        gC.setFont(this.getFont());
                        if ((this.style & 0x40000000) != 0) {
                            OS.BitBlt(gC.handle, 0, 0, n6, n7, gC2.handle, pAINTSTRUCT.left, pAINTSTRUCT.top, 0xCC0020);
                        }
                        OS.OffsetRgn(l6, -pAINTSTRUCT.left, -pAINTSTRUCT.top);
                        OS.SelectClipRgn(gC.handle, l6);
                        OS.OffsetRgn(l6, pAINTSTRUCT.left, pAINTSTRUCT.top);
                        OS.SetMetaRgn(gC.handle);
                        OS.SetWindowOrgEx(gC.handle, pAINTSTRUCT.left, pAINTSTRUCT.top, null);
                        OS.SetBrushOrgEx(gC.handle, pAINTSTRUCT.left, pAINTSTRUCT.top, null);
                        if ((this.style & 0x40040000) == 0) {
                            rECT = new RECT();
                            OS.SetRect(rECT, pAINTSTRUCT.left, pAINTSTRUCT.top, pAINTSTRUCT.right, pAINTSTRUCT.bottom);
                            this.drawBackground(gC.handle, rECT);
                        }
                    }
                    object = new Event();
                    ((Event)object).gc = gC;
                    rECT = null;
                    if ((this.style & 0x200000) != 0 && OS.GetRgnBox(l6, rECT = new RECT()) == 3) {
                        int n8 = OS.GetRegionData(l6, 0, null);
                        int[] nArray = new int[n8 / 4];
                        OS.GetRegionData(l6, n8, nArray);
                        int n9 = nArray[2];
                        for (int i = 0; i < n9; ++i) {
                            int n10 = 8 + (i << 2);
                            OS.SetRect(rECT, nArray[n10], nArray[n10 + 1], nArray[n10 + 2], nArray[n10 + 3]);
                            if ((this.style & 0x60040000) == 0) {
                                this.drawBackground(gC.handle, rECT);
                            }
                            ((Event)object).setBoundsInPixels(new Rectangle(rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top));
                            ((Event)object).count = n9 - 1 - i;
                            this.sendEvent(9, (Event)object);
                        }
                    } else {
                        if ((this.style & 0x60040000) == 0) {
                            if (rECT == null) {
                                rECT = new RECT();
                            }
                            OS.SetRect(rECT, pAINTSTRUCT.left, pAINTSTRUCT.top, pAINTSTRUCT.right, pAINTSTRUCT.bottom);
                            this.drawBackground(gC.handle, rECT);
                        }
                        ((Event)object).setBoundsInPixels(new Rectangle(pAINTSTRUCT.left, pAINTSTRUCT.top, n6, n7));
                        this.sendEvent(9, (Event)object);
                    }
                    ((Event)object).gc = null;
                    if ((this.style & 0x60000000) != 0) {
                        if (!gC.isDisposed()) {
                            GCData gCData2 = gC.getGCData();
                            if (gCData2.focusDrawn && !this.isDisposed()) {
                                this.updateUIState();
                            }
                        }
                        gC.dispose();
                        if (!this.isDisposed()) {
                            gC2.drawImage(image, DPIUtil.autoScaleDown(pAINTSTRUCT.left), DPIUtil.autoScaleDown(pAINTSTRUCT.top));
                        }
                        image.dispose();
                        gC = gC2;
                    }
                }
                if (l6 != 0L) {
                    OS.DeleteObject(l6);
                }
                if (gCData.focusDrawn && !this.isDisposed()) {
                    this.updateUIState();
                }
                gC.dispose();
            }
        } else {
            long l8 = OS.BeginPaint(this.handle, pAINTSTRUCT);
            if ((this.style & 0x40040000) == 0) {
                RECT rECT = new RECT();
                OS.SetRect(rECT, pAINTSTRUCT.left, pAINTSTRUCT.top, pAINTSTRUCT.right, pAINTSTRUCT.bottom);
                this.drawBackground(l8, rECT);
            }
            OS.EndPaint(this.handle, pAINTSTRUCT);
        }
        if (!this.isDisposed() && n2 != n && !this.isDisposed()) {
            OS.SetWindowLong(this.handle, -16, n);
        }
        return LRESULT.ZERO;
    }

    @Override
    LRESULT WM_PRINTCLIENT(long l2, long l3) {
        LRESULT lRESULT = super.WM_PRINTCLIENT(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((this.state & 2) != 0) {
            this.forceResize();
            int n = OS.SaveDC(l2);
            RECT rECT = new RECT();
            OS.GetClientRect(this.handle, rECT);
            if ((this.style & 0x40040000) == 0) {
                this.drawBackground(l2, rECT);
            }
            if (this.hooks(9) || this.filters(9)) {
                GCData gCData = new GCData();
                gCData.device = this.display;
                gCData.foreground = this.getForegroundPixel();
                Control control = this.findBackgroundControl();
                if (control == null) {
                    control = this;
                }
                gCData.background = control.getBackgroundPixel();
                gCData.font = Font.win32_new(this.display, OS.SendMessage(this.handle, 49, 0L, 0L));
                gCData.uiState = (int)OS.SendMessage(this.handle, 297, 0L, 0L);
                GC gC = GC.win32_new(l2, gCData);
                Event event = new Event();
                event.gc = gC;
                event.setBoundsInPixels(new Rectangle(rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top));
                this.sendEvent(9, event);
                event.gc = null;
                gC.dispose();
            }
            OS.RestoreDC(l2, n);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SETFONT(long l2, long l3) {
        if (l3 != 0L) {
            OS.InvalidateRect(this.handle, null, true);
        }
        return super.WM_SETFONT(l2, l3);
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        LRESULT lRESULT = null;
        if ((this.state & 0x80000) != 0) {
            lRESULT = super.WM_SIZE(l2, l3);
        } else {
            this.setResizeChildren(false);
            lRESULT = super.WM_SIZE(l2, l3);
            if (this.isDisposed()) {
                return lRESULT;
            }
            if (this.layout != null) {
                this.markLayout(false, false);
                this.updateLayout(false, false);
            }
            this.setResizeChildren(true);
        }
        if (OS.IsWindowVisible(this.handle)) {
            if ((this.state & 2) != 0 && (this.style & 0x100000) == 0 && this.hooks(9)) {
                OS.InvalidateRect(this.handle, null, true);
            }
            if (OS.IsAppThemed() && this.findThemeControl() != null) {
                this.redrawChildren();
            }
        }
        if ((this.state & 2) != 0 && (this.style & 0x1000000) != 0) {
            this.resizeEmbeddedHandle(OS.GetWindow(this.handle, 5), OS.LOWORD(l3), OS.HIWORD(l3));
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SYSCOLORCHANGE(long l2, long l3) {
        LRESULT lRESULT = super.WM_SYSCOLORCHANGE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        long l4 = OS.GetWindow(this.handle, 5);
        while (l4 != 0L) {
            OS.SendMessage(l4, 21, 0L, 0L);
            l4 = OS.GetWindow(l4, 2);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SYSCOMMAND(long l2, long l3) {
        LRESULT lRESULT = super.WM_SYSCOMMAND(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((l2 & 0xF000L) == 0L) {
            return lRESULT;
        }
        int n = (int)l2 & 0xFFF0;
        switch (n) {
            case 61552: 
            case 61568: {
                boolean bl = this.horizontalBar != null && this.horizontalBar.getVisible();
                boolean bl2 = this.verticalBar != null && this.verticalBar.getVisible();
                long l4 = this.callWindowProc(this.handle, 274, l2, l3);
                if (bl != (this.horizontalBar != null && this.horizontalBar.getVisible()) || bl2 != (this.verticalBar != null && this.verticalBar.getVisible())) {
                    int n2 = 1281;
                    OS.RedrawWindow(this.handle, null, 0L, 1281);
                }
                if (l4 == 0L) {
                    return LRESULT.ZERO;
                }
                return new LRESULT(l4);
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_UPDATEUISTATE(long l2, long l3) {
        LRESULT lRESULT = super.WM_UPDATEUISTATE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((this.state & 2) != 0 && this.hooks(9)) {
            OS.InvalidateRect(this.handle, null, true);
        }
        return lRESULT;
    }

    @Override
    LRESULT wmNCPaint(long l2, long l3, long l4) {
        int n;
        LRESULT lRESULT = super.wmNCPaint(l2, l3, l4);
        if (lRESULT != null) {
            return lRESULT;
        }
        long l5 = this.borderHandle();
        if (((this.state & 2) != 0 || l2 == l5 && this.handle != l5) && OS.IsAppThemed() && ((n = OS.GetWindowLong(l2, -20)) & 0x200) != 0) {
            long l6 = 0L;
            int n2 = OS.GetWindowLong(l2, -16);
            if ((n2 & 0x300000) != 0) {
                l6 = this.callWindowProc(l2, 133, l3, l4);
            }
            long l7 = OS.GetWindowDC(l2);
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
            int n3 = OS.GetSystemMetrics(45);
            OS.ExcludeClipRect(l7, n3, n3, rECT.right - n3, rECT.bottom - n3);
            OS.DrawThemeBackground(this.display.hEditTheme(), l7, 1, 1, rECT, null);
            OS.ReleaseDC(l2, l7);
            return new LRESULT(l6);
        }
        return lRESULT;
    }

    @Override
    LRESULT wmNotify(NMHDR nMHDR, long l2, long l3) {
        switch (nMHDR.code) {
            case -522: 
            case -521: {
                int n;
                long l4 = nMHDR.hwndFrom;
                while ((l4 = OS.GetParent(l4)) != 0L && ((n = OS.GetWindowLong(l4, -20)) & 8) == 0) {
                }
                if (l4 != 0L) break;
                if (this.display.getActiveShell() == null) {
                    return LRESULT.ONE;
                }
                this.display.lockActiveWindow = true;
                int n2 = 19;
                long l5 = nMHDR.code == -521 ? -1L : -2L;
                OS.SetWindowPos(nMHDR.hwndFrom, l5, 0, 0, 0, 0, 19);
                this.display.lockActiveWindow = false;
                break;
            }
            case -530: {
                NMTTDISPINFO nMTTDISPINFO = new NMTTDISPINFO();
                OS.MoveMemory(nMTTDISPINFO, l3, NMTTDISPINFO.sizeof);
                String string = this.toolTipText(nMTTDISPINFO);
                if (string == null) break;
                Shell shell = this.getShell();
                if ((string = Display.withCrLf(string)).length() > 4096) {
                    string = string.substring(0, 4096);
                }
                char[] cArray = this.fixMnemonic(string, false, true);
                Widget widget = null;
                long l6 = nMHDR.idFrom;
                if ((nMTTDISPINFO.uFlags & 1) != 0) {
                    widget = this.display.getControl(l6);
                } else if (nMHDR.hwndFrom == shell.toolTipHandle || nMHDR.hwndFrom == shell.balloonTipHandle) {
                    widget = shell.findToolTip((int)nMHDR.idFrom);
                }
                if (widget != null) {
                    int n = widget.getStyle();
                    int n3 = -2080374784;
                    if ((n & 0x84000000) != 0 && (n & 0x84000000) != -2080374784) {
                        NMTTDISPINFO nMTTDISPINFO2 = nMTTDISPINFO;
                        nMTTDISPINFO2.uFlags |= 4;
                    } else {
                        NMTTDISPINFO nMTTDISPINFO3 = nMTTDISPINFO;
                        nMTTDISPINFO3.uFlags &= 0xFFFFFFFB;
                    }
                }
                shell.setToolTipText(nMTTDISPINFO, cArray);
                OS.MoveMemory(l3, nMTTDISPINFO, NMTTDISPINFO.sizeof);
                return LRESULT.ZERO;
            }
        }
        return super.wmNotify(nMHDR, l2, l3);
    }

    @Override
    public String toString() {
        return super.toString() + " [layout=" + this.layout;
    }
}

