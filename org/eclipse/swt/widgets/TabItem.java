/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TCITEM;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TabFolder;

public class TabItem
extends Item {
    TabFolder parent;
    Control control;
    String toolTipText;

    public TabItem(TabFolder tabFolder, int n) {
        super(tabFolder, n);
        this.parent = tabFolder;
        this.parent.createItem(this, tabFolder.getItemCount());
    }

    public TabItem(TabFolder tabFolder, int n, int n2) {
        super(tabFolder, n);
        this.parent = tabFolder;
        this.parent.createItem(this, n2);
    }

    void _setText(int n, String string) {
        if (this.image != null && string.indexOf(38) != -1) {
            int n2 = string.length();
            char[] cArray = new char[n2];
            string.getChars(0, n2, cArray, 0);
            int n3 = 0;
            int n4 = 0;
            for (n3 = 0; n3 < n2; ++n3) {
                if (cArray[n3] == '&') continue;
                cArray[n4++] = cArray[n3];
            }
            if (n4 < n3) {
                string = new String(cArray, 0, n4);
            }
        }
        long l2 = this.parent.handle;
        long l3 = OS.GetProcessHeap();
        TCHAR tCHAR = new TCHAR(this.parent.getCodePage(), string, true);
        int n5 = tCHAR.length() * 2;
        long l4 = OS.HeapAlloc(l3, 8, n5);
        OS.MoveMemory(l4, tCHAR, n5);
        TCITEM tCITEM = new TCITEM();
        tCITEM.mask = 1;
        tCITEM.pszText = l4;
        OS.SendMessage(l2, 4925, (long)n, tCITEM);
        OS.HeapFree(l3, 0, l4);
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    @Override
    void destroyWidget() {
        this.parent.destroyItem(this);
        this.releaseHandle();
    }

    public Control getControl() {
        this.checkWidget();
        return this.control;
    }

    public Rectangle getBounds() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getBoundsInPixels());
    }

    Rectangle getBoundsInPixels() {
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return new Rectangle(0, 0, 0, 0);
        }
        RECT rECT = new RECT();
        OS.SendMessage(this.parent.handle, 4874, (long)n, rECT);
        return new Rectangle(rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top);
    }

    public TabFolder getParent() {
        this.checkWidget();
        return this.parent;
    }

    public String getToolTipText() {
        this.checkWidget();
        return this.toolTipText;
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.parent = null;
    }

    @Override
    void releaseParent() {
        super.releaseParent();
        int n = this.parent.indexOf(this);
        if (n == this.parent.getSelectionIndex() && this.control != null) {
            this.control.setVisible(false);
        }
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.control = null;
    }

    public void setControl(Control control) {
        int n;
        this.checkWidget();
        if (control != null) {
            if (control.isDisposed()) {
                this.error(5);
            }
            if (control.parent != this.parent) {
                this.error(32);
            }
        }
        if (this.control != null && this.control.isDisposed()) {
            this.control = null;
        }
        Control control2 = this.control;
        Control control3 = control;
        this.control = control;
        int n2 = this.parent.indexOf(this);
        if (n2 != (n = this.parent.getSelectionIndex()) && control3 != null) {
            Control control4;
            if (n != -1 && (control4 = this.parent.getItem(n).getControl()) == control3) {
                return;
            }
            control3.setVisible(false);
            return;
        }
        if (control3 != null) {
            control3.setBounds(this.parent.getClientAreaInPixels());
            control3.setVisible(true);
        }
        if (control2 != null && control3 != null && control2 != control3) {
            control2.setVisible(false);
        }
    }

    @Override
    public void setImage(Image image) {
        this.checkWidget();
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return;
        }
        super.setImage(image);
        if (this.text.indexOf(38) != -1) {
            this._setText(n, this.text);
        }
        long l2 = this.parent.handle;
        TCITEM tCITEM = new TCITEM();
        tCITEM.mask = 2;
        tCITEM.iImage = this.parent.imageIndex(image);
        OS.SendMessage(l2, 4925, (long)n, tCITEM);
    }

    @Override
    public void setText(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if (string.equals(this.text)) {
            return;
        }
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return;
        }
        super.setText(string);
        int n2 = (this.state & 0x400000) != 0 ? 0x6000000 : this.style & Integer.MIN_VALUE;
        TabItem tabItem = this;
        if (n2 != 0) {
            this._setText(n, string);
        }
    }

    public void setToolTipText(String string) {
        this.checkWidget();
        this.toolTipText = string;
    }
}

