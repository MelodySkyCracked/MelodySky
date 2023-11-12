/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ArmListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.ACCEL;
import org.eclipse.swt.internal.win32.DRAWITEMSTRUCT;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MEASUREITEMSTRUCT;
import org.eclipse.swt.internal.win32.MENUBARINFO;
import org.eclipse.swt.internal.win32.MENUINFO;
import org.eclipse.swt.internal.win32.MENUITEMINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class MenuItem
extends Item {
    Menu parent;
    Menu menu;
    long hBitmap;
    int id;
    int accelerator;
    int userId;
    int index;
    ToolTip itemToolTip;
    static final int MARGIN_WIDTH = 1;
    static final int MARGIN_HEIGHT = 1;

    public MenuItem(Menu menu, int n) {
        super(menu, MenuItem.checkStyle(n));
        this.parent = menu;
        this.index = menu.getItemCount();
        this.parent.createItem(this, this.index);
    }

    public MenuItem(Menu menu, int n, int n2) {
        super(menu, MenuItem.checkStyle(n));
        this.parent = menu;
        this.index = n2;
        this.parent.createItem(this, this.index);
    }

    MenuItem(Menu menu, Menu menu2, int n, int n2) {
        super(menu, MenuItem.checkStyle(n));
        this.parent = menu;
        this.menu = menu2;
        this.index = n2;
        if (menu2 != null) {
            menu2.cascade = this;
        }
        this.display.addMenuItem(this);
    }

    public void addArmListener(ArmListener armListener) {
        this.checkWidget();
        if (armListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(armListener);
        this.addListener(30, typedListener);
    }

    public void addHelpListener(HelpListener helpListener) {
        this.checkWidget();
        if (helpListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(helpListener);
        this.addListener(28, typedListener);
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
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    static int checkStyle(int n) {
        return Widget.checkBits(n, 8, 32, 16, 2, 64, 0);
    }

    @Override
    void destroyWidget() {
        this.parent.destroyItem(this);
        this.releaseHandle();
    }

    boolean fillAccel(ACCEL aCCEL) {
        boolean bl = false;
        aCCEL.fVirt = 0;
        boolean bl2 = false;
        aCCEL.key = 0;
        aCCEL.cmd = 0;
        if (this.accelerator == 0 || this != false) {
            return false;
        }
        if ((this.accelerator & 0x400000) != 0) {
            return false;
        }
        boolean bl3 = true;
        int n = this.accelerator & 0x100FFFF;
        int n2 = Display.untranslateKey(n);
        if (n2 != 0) {
            n = n2;
        } else {
            switch (n) {
                case 27: {
                    n = 27;
                    break;
                }
                case 127: {
                    n = 46;
                    break;
                }
                default: {
                    if (n == 0) {
                        return false;
                    }
                    n2 = OS.VkKeyScan((short)n);
                    if (n2 != -1) {
                        n = n2 & 0xFF;
                        break;
                    }
                    if (n == (int)OS.CharUpper((short)n)) break;
                    bl3 = false;
                }
            }
        }
        aCCEL.key = (short)n;
        aCCEL.cmd = (short)this.id;
        aCCEL.fVirt = (byte)(bl3 ? 1 : 0);
        if ((this.accelerator & 0x10000) != 0) {
            aCCEL.fVirt = (byte)(aCCEL.fVirt | 0x10);
        }
        if ((this.accelerator & 0x20000) != 0) {
            aCCEL.fVirt = (byte)(aCCEL.fVirt | 4);
        }
        if ((this.accelerator & 0x40000) != 0) {
            aCCEL.fVirt = (byte)(aCCEL.fVirt | 8);
        }
        return true;
    }

    void fixMenus(Decorations decorations) {
        if (this.menu != null && !this.menu.isDisposed() && !decorations.isDisposed()) {
            this.menu.fixMenus(decorations);
        }
    }

    public int getAccelerator() {
        this.checkWidget();
        return this.accelerator;
    }

    Rectangle getBounds() {
        this.checkWidget();
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return new Rectangle(0, 0, 0, 0);
        }
        if ((this.parent.style & 2) != 0) {
            Decorations decorations = this.parent.parent;
            if (decorations.menuBar != this.parent) {
                return new Rectangle(0, 0, 0, 0);
            }
            long l2 = decorations.handle;
            MENUBARINFO mENUBARINFO = new MENUBARINFO();
            mENUBARINFO.cbSize = MENUBARINFO.sizeof;
            if (!OS.GetMenuBarInfo(l2, -3, 1, mENUBARINFO)) {
                return new Rectangle(0, 0, 0, 0);
            }
            MENUBARINFO mENUBARINFO2 = new MENUBARINFO();
            mENUBARINFO2.cbSize = MENUBARINFO.sizeof;
            if (!OS.GetMenuBarInfo(l2, -3, n + 1, mENUBARINFO2)) {
                return new Rectangle(0, 0, 0, 0);
            }
            int n2 = mENUBARINFO2.left - mENUBARINFO.left;
            int n3 = mENUBARINFO2.top - mENUBARINFO.top;
            int n4 = mENUBARINFO2.right - mENUBARINFO2.left;
            int n5 = mENUBARINFO2.bottom - mENUBARINFO2.top;
            return new Rectangle(n2, n3, n4, n5);
        }
        long l3 = this.parent.handle;
        RECT rECT = new RECT();
        if (!OS.GetMenuItemRect(0L, l3, 0, rECT)) {
            return new Rectangle(0, 0, 0, 0);
        }
        RECT rECT2 = new RECT();
        if (!OS.GetMenuItemRect(0L, l3, n, rECT2)) {
            return new Rectangle(0, 0, 0, 0);
        }
        int n6 = rECT2.left - rECT.left + 2;
        int n7 = rECT2.top - rECT.top + 2;
        int n8 = rECT2.right - rECT2.left;
        int n9 = rECT2.bottom - rECT2.top;
        return new Rectangle(n6, n7, n8, n9);
    }

    public int getID() {
        this.checkWidget();
        return this.userId;
    }

    @Override
    public Menu getMenu() {
        this.checkWidget();
        return this.menu;
    }

    @Override
    String getNameText() {
        if ((this.style & 2) != 0) {
            return "|";
        }
        return super.getNameText();
    }

    public Menu getParent() {
        this.checkWidget();
        return this.parent;
    }

    public String getToolTipText() {
        this.checkWidget();
        return this.itemToolTip == null || this.itemToolTip.isDisposed() ? null : this.itemToolTip.getMessage();
    }

    void hideToolTip() {
        if (this.itemToolTip == null || this.itemToolTip.isDisposed()) {
            return;
        }
        this.itemToolTip.setVisible(false);
    }

    public boolean isEnabled() {
        return this != false && this.parent.isEnabled();
    }

    @Override
    void releaseChildren(boolean bl) {
        if (this.menu != null) {
            this.menu.release(false);
            this.menu = null;
        }
        super.releaseChildren(bl);
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.parent = null;
        this.id = -1;
    }

    @Override
    void releaseParent() {
        super.releaseParent();
        if (this.menu != null) {
            this.menu.dispose();
        }
        this.menu = null;
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        if (this.hBitmap != 0L) {
            OS.DeleteObject(this.hBitmap);
        }
        this.hBitmap = 0L;
        if (this.accelerator != 0) {
            this.parent.destroyAccelerators();
        }
        this.accelerator = 0;
        if (this.itemToolTip != null && !this.itemToolTip.isDisposed()) {
            this.itemToolTip.setVisible(false);
            this.itemToolTip.dispose();
            this.itemToolTip = null;
        }
        this.display.removeMenuItem(this);
    }

    public void removeArmListener(ArmListener armListener) {
        this.checkWidget();
        if (armListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(30, armListener);
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
        if (this.menu != null) {
            this.menu.reskin(n);
        }
        super.reskinChildren(n);
    }

    /*
     * Exception decompiling
     */
    void selectRadio() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl22 : ILOAD_3 - null : trying to set 1 previously set to 0
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

    public void setAccelerator(int n) {
        this.checkWidget();
        if (this.accelerator == n) {
            return;
        }
        this.accelerator = n;
        this.parent.destroyAccelerators();
    }

    public void setEnabled(boolean bl) {
        MENUITEMINFO mENUITEMINFO;
        int n;
        this.checkWidget();
        if ((this.style & 2) != 0) {
            this.state = bl ? (this.state &= 0xFFFFFFF7) : (this.state |= 8);
        }
        long l2 = this.parent.handle;
        MENUITEMINFO mENUITEMINFO2 = new MENUITEMINFO();
        mENUITEMINFO2.cbSize = MENUITEMINFO.sizeof;
        mENUITEMINFO2.fMask = 1;
        boolean bl2 = OS.GetMenuItemInfo(l2, this.id, false, mENUITEMINFO2);
        if (!bl2) {
            n = OS.GetLastError();
            SWT.error(30, null, " [GetLastError=0x" + Integer.toHexString(n));
        }
        n = 3;
        if (bl) {
            if ((mENUITEMINFO2.fState & 3) == 0) {
                return;
            }
            mENUITEMINFO = mENUITEMINFO2;
            mENUITEMINFO.fState &= 0xFFFFFFFC;
        } else {
            if ((mENUITEMINFO2.fState & 3) == 3) {
                return;
            }
            mENUITEMINFO = mENUITEMINFO2;
            mENUITEMINFO.fState |= 3;
        }
        bl2 = OS.SetMenuItemInfo(l2, this.id, false, mENUITEMINFO2);
        if (!bl2) {
            boolean bl3 = bl2 = this.id == OS.GetMenuDefaultItem(l2, 0, 1);
            if (!bl2) {
                int n2 = OS.GetLastError();
                SWT.error(30, null, " [GetLastError=0x" + Integer.toHexString(n2));
            }
        }
        this.parent.destroyAccelerators();
        this.parent.redraw();
    }

    public void setID(int n) {
        this.checkWidget();
        if (n < 0) {
            this.error(5);
        }
        this.userId = n;
    }

    @Override
    public void setImage(Image image) {
        this.checkWidget();
        if (this.image == image) {
            return;
        }
        if ((this.style & 2) != 0) {
            return;
        }
        super.setImage(image);
        MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
        mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        mENUITEMINFO.fMask = 128;
        if (this.parent.needsMenuCallback()) {
            mENUITEMINFO.hbmpItem = -1L;
        } else if (OS.IsAppThemed()) {
            long l2;
            if (this.hBitmap != 0L) {
                OS.DeleteObject(this.hBitmap);
            }
            MENUITEMINFO mENUITEMINFO2 = mENUITEMINFO;
            this.hBitmap = l2 = image != null ? Display.create32bitDIB(image) : 0L;
            mENUITEMINFO2.hbmpItem = l2;
        } else {
            mENUITEMINFO.hbmpItem = image != null ? -1L : 0L;
        }
        long l3 = this.parent.handle;
        OS.SetMenuItemInfo(l3, this.id, false, mENUITEMINFO);
        this.parent.redraw();
    }

    public void setMenu(Menu menu) {
        this.checkWidget();
        if ((this.style & 0x40) == 0) {
            this.error(27);
        }
        if (menu != null) {
            if (menu.isDisposed()) {
                this.error(5);
            }
            if ((menu.style & 4) == 0) {
                this.error(21);
            }
            if (menu.parent != this.parent.parent) {
                this.error(32);
            }
        }
        this.setMenu(menu, false);
    }

    void setMenu(Menu menu, boolean bl) {
        Menu menu2 = this.menu;
        if (menu2 == menu) {
            return;
        }
        if (menu2 != null) {
            menu2.cascade = null;
        }
        this.menu = menu;
        long l2 = this.parent.handle;
        MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
        mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        mENUITEMINFO.fMask = 32;
        int n = 0;
        while (OS.GetMenuItemInfo(l2, n, true, mENUITEMINFO) && mENUITEMINFO.dwItemData != (long)this.id) {
            ++n;
        }
        if (mENUITEMINFO.dwItemData != (long)this.id) {
            return;
        }
        int n2 = 128;
        long l3 = OS.GetProcessHeap();
        int n3 = 256;
        long l4 = OS.HeapAlloc(l3, 8, 256);
        mENUITEMINFO.fMask = 35;
        MENUITEMINFO mENUITEMINFO2 = mENUITEMINFO;
        mENUITEMINFO2.fMask |= 0xC0;
        mENUITEMINFO.dwTypeData = l4;
        mENUITEMINFO.cch = 128;
        boolean bl2 = OS.GetMenuItemInfo(l2, n, true, mENUITEMINFO);
        if (menu != null) {
            menu.cascade = this;
            MENUITEMINFO mENUITEMINFO3 = mENUITEMINFO;
            mENUITEMINFO3.fMask |= 4;
            mENUITEMINFO.hSubMenu = menu.handle;
        }
        if (bl || menu2 == null) {
            bl2 = OS.SetMenuItemInfo(l2, n, true, mENUITEMINFO);
        } else {
            OS.RemoveMenu(l2, n, 1024);
            bl2 = OS.InsertMenuItem(l2, n, true, mENUITEMINFO);
        }
        if (l4 != 0L) {
            OS.HeapFree(l3, 0, l4);
        }
        if (!bl2) {
            int n4 = OS.GetLastError();
            SWT.error(29, null, " [GetLastError=0x" + Integer.toHexString(n4));
        }
        this.parent.destroyAccelerators();
    }

    void setOrientation(int n) {
        long l2 = this.parent.handle;
        MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
        mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        mENUITEMINFO.fMask = 256;
        mENUITEMINFO.fType = this.widgetStyle();
        OS.SetMenuItemInfo(l2, this.id, false, mENUITEMINFO);
        if (this.menu != null) {
            this.menu._setOrientation(n);
        }
    }

    public void setSelection(boolean bl) {
        this.checkWidget();
        if ((this.style & 0x30) == 0) {
            return;
        }
        long l2 = this.parent.handle;
        MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
        mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        mENUITEMINFO.fMask = 1;
        boolean bl2 = OS.GetMenuItemInfo(l2, this.id, false, mENUITEMINFO);
        if (!bl2) {
            this.error(28);
        }
        MENUITEMINFO mENUITEMINFO2 = mENUITEMINFO;
        mENUITEMINFO2.fState &= 0xFFFFFFF7;
        if (bl) {
            MENUITEMINFO mENUITEMINFO3 = mENUITEMINFO;
            mENUITEMINFO3.fState |= 8;
        }
        if (!(bl2 = OS.SetMenuItemInfo(l2, this.id, false, mENUITEMINFO))) {
            boolean bl3 = bl2 = this.id == OS.GetMenuDefaultItem(l2, 0, 1);
            if (!bl2) {
                int n = OS.GetLastError();
                SWT.error(28, null, " [GetLastError=0x" + Integer.toHexString(n));
            }
        }
        this.parent.redraw();
    }

    @Override
    public void setText(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if ((this.style & 2) != 0) {
            return;
        }
        if (this.text.equals(string)) {
            return;
        }
        super.setText(string);
        long l2 = OS.GetProcessHeap();
        long l3 = 0L;
        MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
        mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        long l4 = this.parent.handle;
        TCHAR tCHAR = new TCHAR(0, string, true);
        int n = tCHAR.length() * 2;
        l3 = OS.HeapAlloc(l2, 8, n);
        OS.MoveMemory(l3, tCHAR, n);
        mENUITEMINFO.fMask = 64;
        mENUITEMINFO.dwTypeData = l3;
        boolean bl = OS.SetMenuItemInfo(l4, this.id, false, mENUITEMINFO);
        if (l3 != 0L) {
            OS.HeapFree(l2, 0, l3);
        }
        if (!bl) {
            int n2 = OS.GetLastError();
            SWT.error(13, null, " [GetLastError=0x" + Integer.toHexString(n2));
        }
        this.parent.redraw();
    }

    public void setToolTipText(String string) {
        this.checkWidget();
        if (string == null && this.itemToolTip != null) {
            if (!this.itemToolTip.isDisposed()) {
                this.itemToolTip.setVisible(false);
                this.itemToolTip.dispose();
            }
            this.itemToolTip = null;
        }
        if (string == null || string.trim().length() == 0 || this.itemToolTip != null && string.equals(this.itemToolTip.getMessage())) {
            return;
        }
        if (this.itemToolTip != null) {
            this.itemToolTip.dispose();
        }
        this.itemToolTip = new MenuItemToolTip(this.getParent().getShell());
        this.itemToolTip.setMessage(string);
        this.itemToolTip.setVisible(false);
    }

    void showTooltip(int n, int n2) {
        if (this.itemToolTip == null || this.itemToolTip.isDisposed()) {
            return;
        }
        this.itemToolTip.setLocationInPixels(n, n2);
        this.itemToolTip.setVisible(true);
    }

    int widgetStyle() {
        int n = 0;
        Decorations decorations = this.parent.parent;
        if ((decorations.style & 0x8000000) != 0) {
            if ((this.parent.style & 0x2000000) != 0) {
                n |= 0x6000;
            }
        } else if ((this.parent.style & 0x4000000) != 0) {
            n |= 0x6000;
        }
        if ((this.style & 2) != 0) {
            return n | 0x800;
        }
        if ((this.style & 0x10) != 0) {
            return n | 0x200;
        }
        return n | 0;
    }

    LRESULT wmCommandChild(long l2, long l3) {
        if ((this.style & 0x20) != 0) {
            this.setSelection(this == false);
        } else if ((this.style & 0x10) != 0) {
            if ((this.parent.getStyle() & 0x400000) != 0) {
                this.setSelection(this == false);
            } else {
                this.selectRadio();
            }
        }
        this.sendSelectionEvent(13);
        return null;
    }

    LRESULT wmDrawChild(long l2, long l3) {
        DRAWITEMSTRUCT dRAWITEMSTRUCT = new DRAWITEMSTRUCT();
        OS.MoveMemory(dRAWITEMSTRUCT, l3, DRAWITEMSTRUCT.sizeof);
        if (this.image != null) {
            GCData gCData = new GCData();
            gCData.device = this.display;
            GC gC = GC.win32_new(dRAWITEMSTRUCT.hDC, gCData);
            int n = (this.parent.style & 2) != 0 ? 2 : dRAWITEMSTRUCT.left;
            Image image = this != false ? this.image : new Image((Device)this.display, this.image, 1);
            gC.drawImage(image, DPIUtil.autoScaleDown(n), DPIUtil.autoScaleDown(dRAWITEMSTRUCT.top + 1));
            if (this.image != image) {
                image.dispose();
            }
            gC.dispose();
        }
        if (this.parent.foreground != -1) {
            OS.SetTextColor(dRAWITEMSTRUCT.hDC, this.parent.foreground);
        }
        return null;
    }

    LRESULT wmMeasureChild(long l2, long l3) {
        MEASUREITEMSTRUCT mEASUREITEMSTRUCT = new MEASUREITEMSTRUCT();
        OS.MoveMemory(mEASUREITEMSTRUCT, l3, MEASUREITEMSTRUCT.sizeof);
        if ((this.parent.style & 2) != 0 && this.parent.needsMenuCallback()) {
            mEASUREITEMSTRUCT.itemWidth = DPIUtil.autoScaleUpUsingNativeDPI(5);
            OS.MoveMemory(l3, mEASUREITEMSTRUCT, MEASUREITEMSTRUCT.sizeof);
            return null;
        }
        int n = 0;
        int n2 = 0;
        if (this.image != null) {
            Rectangle rectangle = this.image.getBoundsInPixels();
            n = rectangle.width;
            n2 = rectangle.height;
        } else {
            MENUINFO mENUINFO = new MENUINFO();
            mENUINFO.cbSize = MENUINFO.sizeof;
            mENUINFO.fMask = 16;
            long l4 = this.parent.handle;
            OS.GetMenuInfo(l4, mENUINFO);
            if ((mENUINFO.dwStyle & 0x4000000) == 0) {
                for (MenuItem menuItem : this.parent.getItems()) {
                    if (menuItem.image == null) continue;
                    Rectangle rectangle = menuItem.image.getBoundsInPixels();
                    n = Math.max(n, rectangle.width);
                }
            }
        }
        if (n != 0 || n2 != 0) {
            mEASUREITEMSTRUCT.itemWidth = n + 2;
            mEASUREITEMSTRUCT.itemHeight = n2 + 2;
            OS.MoveMemory(l3, mEASUREITEMSTRUCT, MEASUREITEMSTRUCT.sizeof);
        }
        return null;
    }

    private static final class MenuItemToolTip
    extends ToolTip {
        public MenuItemToolTip(Shell shell) {
            super(shell, 0);
            this.maybeEnableDarkSystemTheme(this.hwndToolTip());
        }

        @Override
        long hwndToolTip() {
            return this.parent.menuItemToolTipHandle();
        }
    }
}

