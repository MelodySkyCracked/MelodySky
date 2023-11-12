/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MENUBARINFO;
import org.eclipse.swt.internal.win32.MENUINFO;
import org.eclipse.swt.internal.win32.MENUITEMINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class Menu
extends Widget {
    public long handle;
    int x;
    int y;
    long hBrush;
    int foreground = -1;
    int background = -1;
    Image backgroundImage;
    boolean hasLocation;
    MenuItem cascade;
    Decorations parent;
    MenuItem selectedMenuItem;
    static final int ID_TOOLTIP_TIMER = 110;

    public Menu(Control control) {
        this(Menu.checkNull(control).menuShell(), 8);
    }

    public Menu(Decorations decorations, int n) {
        this(decorations, Menu.checkStyle(n), 0L);
    }

    public Menu(Menu menu) {
        this(Menu.checkNull((Menu)menu).parent, 4);
    }

    public Menu(MenuItem menuItem) {
        this(Menu.checkNull((MenuItem)menuItem).parent);
    }

    Menu(Decorations decorations, int n, long l2) {
        super(decorations, Menu.checkStyle(n));
        this.parent = decorations;
        this.handle = l2;
        this.createWidget();
    }

    void _setVisible(boolean bl) {
        if ((this.style & 6) != 0) {
            return;
        }
        long l2 = this.parent.handle;
        if (bl) {
            int n = 0;
            if (OS.GetKeyState(1) >= 0) {
                n |= 2;
            }
            if ((this.style & 0x4000000) != 0) {
                n |= 8;
            }
            if ((this.parent.style & 0x8000000) != 0) {
                n &= 0xFFFFFFF7;
                if ((this.style & 0x2000000) != 0) {
                    n |= 8;
                }
            }
            int n2 = this.x;
            int n3 = this.y;
            if (!this.hasLocation) {
                int n4 = OS.GetMessagePos();
                n2 = OS.GET_X_LPARAM(n4);
                n3 = OS.GET_Y_LPARAM(n4);
            }
            this.hasLocation = false;
            Display display = this.display;
            display.sendPreExternalEventDispatchEvent();
            boolean bl2 = OS.TrackPopupMenu(this.handle, n, n2, n3, 0, l2, null);
            display.sendPostExternalEventDispatchEvent();
            if (!bl2 && OS.GetMenuItemCount(this.handle) == 0) {
                OS.SendMessage(l2, 287, OS.MAKEWPARAM(0, 65535), 0L);
            }
        } else {
            OS.SendMessage(l2, 31, 0L, 0L);
        }
        long l3 = OS.GetFocus();
        if (l3 != 0L) {
            OS.NotifyWinEvent(32773, l3, -4, 0);
        }
    }

    public void addHelpListener(HelpListener helpListener) {
        this.checkWidget();
        if (helpListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(helpListener);
        this.addListener(28, typedListener);
    }

    public void addMenuListener(MenuListener menuListener) {
        this.checkWidget();
        if (menuListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(menuListener);
        this.addListener(23, typedListener);
        this.addListener(22, typedListener);
    }

    static Control checkNull(Control control) {
        if (control == null) {
            SWT.error(4);
        }
        return control;
    }

    static Menu checkNull(Menu menu) {
        if (menu == null) {
            SWT.error(4);
        }
        return menu;
    }

    static MenuItem checkNull(MenuItem menuItem) {
        if (menuItem == null) {
            SWT.error(4);
        }
        return menuItem;
    }

    static int checkStyle(int n) {
        return Widget.checkBits(n, 8, 2, 4, 0, 0, 0);
    }

    void createHandle() {
        if (this.handle != 0L) {
            return;
        }
        this.handle = (this.style & 2) != 0 ? OS.CreateMenu() : OS.CreatePopupMenu();
        if (this.handle == 0L) {
            this.error(2);
        }
        this.updateBackground();
    }

    /*
     * Exception decompiling
     */
    void createItem(MenuItem var1, int var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl90 : IF_ICMPNE - null : Stack underflow
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

    void createWidget() {
        this.checkOrientation(this.parent);
        this.initThemeColors();
        this.createHandle();
        this.parent.addMenu(this);
    }

    int defaultBackground() {
        return OS.GetSysColor(4);
    }

    int defaultForeground() {
        return OS.GetSysColor(7);
    }

    void destroyAccelerators() {
        this.parent.destroyAccelerators();
    }

    void destroyItem(MenuItem menuItem) {
        if (!OS.DeleteMenu(this.handle, menuItem.id, 0)) {
            this.error(15);
        }
        this.redraw();
    }

    @Override
    void destroyWidget() {
        MenuItem menuItem = this.cascade;
        long l2 = this.handle;
        this.releaseHandle();
        if (menuItem != null) {
            menuItem.setMenu(null, true);
        } else if (l2 != 0L) {
            OS.DestroyMenu(l2);
        }
    }

    void fixMenus(Decorations decorations) {
        if (this.isDisposed()) {
            return;
        }
        for (MenuItem menuItem : this.getItems()) {
            menuItem.fixMenus(decorations);
        }
        this.parent.removeMenu(this);
        decorations.addMenu(this);
        this.parent = decorations;
    }

    Color getBackground() {
        this.checkWidget();
        return Color.win32_new(this.display, this.background != -1 ? this.background : this.defaultBackground());
    }

    Image getBackgroundImage() {
        this.checkWidget();
        return this.backgroundImage;
    }

    Rectangle getBounds() {
        this.checkWidget();
        if ((this.style & 2) != 0) {
            if (this.parent.menuBar != this) {
                return new Rectangle(0, 0, 0, 0);
            }
            long l2 = this.parent.handle;
            MENUBARINFO mENUBARINFO = new MENUBARINFO();
            mENUBARINFO.cbSize = MENUBARINFO.sizeof;
            if (OS.GetMenuBarInfo(l2, -3, 0, mENUBARINFO)) {
                int n = mENUBARINFO.right - mENUBARINFO.left;
                int n2 = mENUBARINFO.bottom - mENUBARINFO.top;
                return new Rectangle(mENUBARINFO.left, mENUBARINFO.top, n, n2);
            }
        } else {
            RECT rECT;
            RECT rECT2;
            int n = OS.GetMenuItemCount(this.handle);
            if (n != 0 && OS.GetMenuItemRect(0L, this.handle, 0, rECT2 = new RECT()) && OS.GetMenuItemRect(0L, this.handle, n - 1, rECT = new RECT())) {
                int n3 = rECT2.left - 2;
                int n4 = rECT2.top - 2;
                int n5 = rECT.right - rECT.left + 4;
                int n6 = rECT.bottom - rECT2.top + 4;
                return new Rectangle(n3, n4, n5, n6);
            }
        }
        return new Rectangle(0, 0, 0, 0);
    }

    public MenuItem getDefaultItem() {
        this.checkWidget();
        int n = OS.GetMenuDefaultItem(this.handle, 0, 1);
        if (n == -1) {
            return null;
        }
        MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
        mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        mENUITEMINFO.fMask = 2;
        if (OS.GetMenuItemInfo(this.handle, n, false, mENUITEMINFO)) {
            return this.display.getMenuItem(mENUITEMINFO.wID);
        }
        return null;
    }

    Color getForeground() {
        this.checkWidget();
        return Color.win32_new(this.display, this.foreground != -1 ? this.foreground : this.defaultForeground());
    }

    public MenuItem getItem(int n) {
        this.checkWidget();
        int n2 = 0;
        MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
        mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        mENUITEMINFO.fMask = 32;
        if (!OS.GetMenuItemInfo(this.handle, n, true, mENUITEMINFO)) {
            this.error(6);
        }
        n2 = (int)mENUITEMINFO.dwItemData;
        return this.display.getMenuItem(n2);
    }

    public int getItemCount() {
        this.checkWidget();
        return OS.GetMenuItemCount(this.handle);
    }

    public MenuItem[] getItems() {
        MenuItem[] menuItemArray;
        this.checkWidget();
        int n = 0;
        int n2 = 0;
        int n3 = OS.GetMenuItemCount(this.handle);
        if (n3 < 0) {
            int n4 = OS.GetLastError();
            SWT.error(36, null, " [GetLastError=0x" + Integer.toHexString(n4));
        }
        MenuItem[] menuItemArray2 = new MenuItem[n3];
        MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
        mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        mENUITEMINFO.fMask = 32;
        while (OS.GetMenuItemInfo(this.handle, n, true, mENUITEMINFO)) {
            if (n2 == menuItemArray2.length) {
                menuItemArray = new MenuItem[n2 + 4];
                System.arraycopy(menuItemArray2, 0, menuItemArray, 0, n2);
                menuItemArray2 = menuItemArray;
            }
            if ((menuItemArray = this.display.getMenuItem((int)mENUITEMINFO.dwItemData)) != null) {
                menuItemArray2[n2++] = menuItemArray;
            }
            ++n;
        }
        if (n2 == menuItemArray2.length) {
            return menuItemArray2;
        }
        menuItemArray = new MenuItem[n2];
        System.arraycopy(menuItemArray2, 0, menuItemArray, 0, n2);
        return menuItemArray;
    }

    @Override
    String getNameText() {
        String string = "";
        MenuItem[] menuItemArray = this.getItems();
        int n = menuItemArray.length;
        if (n > 0) {
            for (int i = 0; i <= n - 1; ++i) {
                string = string + (menuItemArray[i] == null ? "null" : menuItemArray[i].getNameText()) + (i < n - 1 ? ", " : "");
            }
        }
        return string;
    }

    public int getOrientation() {
        this.checkWidget();
        return this.style & 0x6000000;
    }

    public Decorations getParent() {
        this.checkWidget();
        return this.parent;
    }

    public MenuItem getParentItem() {
        this.checkWidget();
        return this.cascade;
    }

    public Menu getParentMenu() {
        this.checkWidget();
        if (this.cascade != null) {
            return this.cascade.parent;
        }
        return null;
    }

    public Shell getShell() {
        this.checkWidget();
        return this.parent.getShell();
    }

    public boolean getVisible() {
        Object object;
        Menu[] menuArray;
        this.checkWidget();
        if ((this.style & 2) != 0) {
            return this == this.parent.menuShell().menuBar;
        }
        if ((this.style & 8) != 0) {
            menuArray = this.display.popups;
            if (menuArray == null) {
                return false;
            }
            object = menuArray;
            int n = ((Menu[])object).length;
            for (int i = 0; i < n; ++i) {
                Menu menu = object[i];
                if (menu != this) continue;
                return true;
            }
        }
        menuArray = this.getShell();
        for (object = menuArray.activeMenu; object != null && object != this; object = object.getParentMenu()) {
        }
        return this == object;
    }

    void hideCurrentToolTip() {
        if (this.selectedMenuItem != null) {
            this.selectedMenuItem.hideToolTip();
        }
    }

    public int indexOf(MenuItem menuItem) {
        this.checkWidget();
        if (menuItem == null) {
            this.error(4);
        }
        if (menuItem.isDisposed()) {
            this.error(5);
        }
        if (menuItem.parent != this) {
            return -1;
        }
        int n = 0;
        MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
        mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        mENUITEMINFO.fMask = 32;
        while (OS.GetMenuItemInfo(this.handle, n, true, mENUITEMINFO)) {
            if (mENUITEMINFO.dwItemData == (long)menuItem.id) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    void initThemeColors() {
        if ((this.style & 2) != 0) {
            this.foreground = this.display.menuBarForegroundPixel;
            this.background = this.display.menuBarBackgroundPixel;
        }
    }

    public boolean isVisible() {
        this.checkWidget();
        return this.getVisible();
    }

    void redraw() {
        if (!this.isVisible()) {
            return;
        }
        if ((this.style & 2) != 0) {
            this.display.addBar(this);
        } else {
            this.update();
        }
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.handle = 0L;
        this.cascade = null;
    }

    @Override
    void releaseChildren(boolean bl) {
        for (MenuItem menuItem : this.getItems()) {
            if (menuItem == null || menuItem.isDisposed()) continue;
            menuItem.release(false);
        }
        super.releaseChildren(bl);
    }

    @Override
    void releaseParent() {
        super.releaseParent();
        if ((this.style & 2) != 0) {
            this.display.removeBar(this);
            if (this == this.parent.menuBar) {
                this.parent.setMenuBar(null);
            }
        } else if ((this.style & 8) != 0) {
            this.display.removePopup(this);
        }
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.backgroundImage = null;
        if (this.hBrush != 0L) {
            OS.DeleteObject(this.hBrush);
        }
        this.hBrush = 0L;
        if (this.parent != null) {
            this.parent.removeMenu(this);
        }
        this.parent = null;
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

    public void removeMenuListener(MenuListener menuListener) {
        this.checkWidget();
        if (menuListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(23, menuListener);
        this.eventTable.unhook(22, menuListener);
    }

    @Override
    void reskinChildren(int n) {
        for (MenuItem menuItem : this.getItems()) {
            menuItem.reskin(n);
        }
        super.reskinChildren(n);
    }

    void setBackground(Color color) {
        this.checkWidget();
        int n = -1;
        if (color != null) {
            if (color.isDisposed()) {
                this.error(5);
            }
            n = color.handle;
        }
        if (n == this.background) {
            return;
        }
        this.background = n;
        this.updateBackground();
    }

    void setBackgroundImage(Image image) {
        this.checkWidget();
        if (image != null) {
            if (image.isDisposed()) {
                this.error(5);
            }
            if (image.type != 0) {
                this.error(5);
            }
        }
        if (this.backgroundImage == image) {
            return;
        }
        this.backgroundImage = image;
        this.updateBackground();
    }

    void setForeground(Color color) {
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
        this.updateForeground();
    }

    public void setDefaultItem(MenuItem menuItem) {
        int n;
        this.checkWidget();
        int n2 = -1;
        if (menuItem != null) {
            if (menuItem.isDisposed()) {
                this.error(5);
            }
            if (menuItem.parent != this) {
                return;
            }
            n2 = menuItem.id;
        }
        if (n2 == (n = OS.GetMenuDefaultItem(this.handle, 0, 1))) {
            return;
        }
        OS.SetMenuDefaultItem(this.handle, n2, 0);
        this.redraw();
    }

    public void setEnabled(boolean bl) {
        this.checkWidget();
        this.state &= 0xFFFFFFF7;
        if (!bl) {
            this.state |= 8;
        }
    }

    public void setLocation(int n, int n2) {
        this.checkWidget();
        this.setLocationInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2));
    }

    void setLocationInPixels(int n, int n2) {
        if ((this.style & 6) != 0) {
            return;
        }
        this.x = n;
        this.y = n2;
        this.hasLocation = true;
    }

    public void setLocation(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        this.setLocationInPixels(point.x, point.y);
    }

    public void setOrientation(int n) {
        this.checkWidget();
        if ((this.style & 6) != 0) {
            return;
        }
        this._setOrientation(n);
    }

    void _setOrientation(int n) {
        int n2 = 0x6000000;
        if ((n & 0x6000000) == 0 || (n & 0x6000000) == 0x6000000) {
            return;
        }
        this.style &= 0xF9FFFFFF;
        this.style |= n & 0x6000000;
        this.style &= Integer.MAX_VALUE;
        for (MenuItem menuItem : this.getItems()) {
            menuItem.setOrientation(n);
        }
    }

    public void setVisible(boolean bl) {
        this.checkWidget();
        if ((this.style & 6) != 0) {
            return;
        }
        if (bl) {
            this.display.addPopup(this);
        } else {
            this.display.removePopup(this);
            this._setVisible(false);
        }
    }

    void update() {
        if ((this.style & 2) != 0) {
            if (this == this.parent.menuBar) {
                OS.DrawMenuBar(this.parent.handle);
            }
            return;
        }
        boolean bl = false;
        boolean bl2 = false;
        for (MenuItem menuItem : this.getItems()) {
            if (menuItem.image != null) {
                bl2 = true;
                if (true && bl) break;
            }
            if ((menuItem.style & 0x30) == 0) continue;
            bl = true;
            if (true && bl2) break;
        }
        MENUINFO mENUINFO = new MENUINFO();
        mENUINFO.cbSize = MENUINFO.sizeof;
        mENUINFO.fMask = 16;
        OS.GetMenuInfo(this.handle, mENUINFO);
        if (bl2 && !bl) {
            MENUINFO object3 = mENUINFO;
            object3.dwStyle |= 0x4000000;
        } else {
            MENUINFO mENUINFO2 = mENUINFO;
            mENUINFO2.dwStyle &= 0xFBFFFFFF;
        }
        OS.SetMenuInfo(this.handle, mENUINFO);
    }

    void updateBackground() {
        if (this.hBrush != 0L) {
            OS.DeleteObject(this.hBrush);
        }
        this.hBrush = 0L;
        if (this.backgroundImage != null) {
            this.hBrush = OS.CreatePatternBrush(this.backgroundImage.handle);
        } else if (this.background != -1) {
            this.hBrush = OS.CreateSolidBrush(this.background);
        }
        MENUINFO mENUINFO = new MENUINFO();
        mENUINFO.cbSize = MENUINFO.sizeof;
        mENUINFO.fMask = 2;
        mENUINFO.hbrBack = this.hBrush;
        OS.SetMenuInfo(this.handle, mENUINFO);
    }

    void updateForeground() {
        MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
        mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        int n = 0;
        while (OS.GetMenuItemInfo(this.handle, n, true, mENUITEMINFO)) {
            mENUITEMINFO.fMask = 128;
            mENUITEMINFO.hbmpItem = -1L;
            OS.SetMenuItemInfo(this.handle, n, true, mENUITEMINFO);
            ++n;
        }
        this.redraw();
    }

    LRESULT wmTimer(long l2, long l3) {
        if (l2 == 110L) {
            POINT pOINT = new POINT();
            OS.GetCursorPos(pOINT);
            if (this.selectedMenuItem != null) {
                RECT rECT = new RECT();
                boolean bl = OS.GetMenuItemRect(0L, this.selectedMenuItem.parent.handle, this.selectedMenuItem.index, rECT);
                if (!bl) {
                    return null;
                }
                if (OS.PtInRect(rECT, pOINT)) {
                    this.selectedMenuItem.showTooltip(pOINT.x, pOINT.y + OS.GetSystemMetrics(14) / 2 + 5);
                } else {
                    this.selectedMenuItem.showTooltip((rECT.right + rECT.left) / 2, rECT.bottom + 5);
                }
            }
        }
        return null;
    }
}

