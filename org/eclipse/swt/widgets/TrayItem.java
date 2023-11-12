/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.win32.NOTIFYICONDATA;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TypedListener;

public class TrayItem
extends Item {
    Tray parent;
    int id;
    Image image2;
    Image highlightImage;
    ToolTip toolTip;
    String toolTipText;
    boolean visible = true;

    public TrayItem(Tray tray, int n) {
        super(tray, n);
        this.parent = tray;
        this.parent.createItem(this, tray.getItemCount());
        this.createUpdateWidget(true);
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

    public void addMenuDetectListener(MenuDetectListener menuDetectListener) {
        this.checkWidget();
        if (menuDetectListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(menuDetectListener);
        this.addListener(35, typedListener);
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    void createUpdateWidget(boolean bl) {
        int n;
        int n2;
        NOTIFYICONDATA nOTIFYICONDATA = new NOTIFYICONDATA();
        nOTIFYICONDATA.cbSize = NOTIFYICONDATA.sizeof;
        NOTIFYICONDATA nOTIFYICONDATA2 = nOTIFYICONDATA;
        if (bl) {
            Display display = this.display;
            n = n2 = display.nextTrayId;
            display.nextTrayId = n2 + 1;
        } else {
            n2 = n = this.id;
        }
        this.id = n;
        nOTIFYICONDATA2.uID = n2;
        nOTIFYICONDATA.hWnd = this.display.hwndMessage;
        nOTIFYICONDATA.uFlags = 1;
        nOTIFYICONDATA.uCallbackMessage = 32772;
        OS.Shell_NotifyIcon(bl ? 0 : 1, nOTIFYICONDATA);
    }

    @Override
    void destroyWidget() {
        this.parent.destroyItem(this);
        this.releaseHandle();
    }

    public Image getHighlightImage() {
        this.checkWidget();
        return this.highlightImage;
    }

    public Tray getParent() {
        this.checkWidget();
        return this.parent;
    }

    public ToolTip getToolTip() {
        this.checkWidget();
        return this.toolTip;
    }

    public String getToolTipText() {
        this.checkWidget();
        return this.toolTipText;
    }

    public boolean getVisible() {
        this.checkWidget();
        return this.visible;
    }

    long messageProc(long l2, int n, long l3, long l4) {
        switch ((int)l4) {
            case 513: {
                if (!this.hooks(13)) break;
                OS.SetForegroundWindow(l2);
                this.sendSelectionEvent(13);
                break;
            }
            case 515: 
            case 518: {
                if (!this.hooks(14)) break;
                OS.SetForegroundWindow(l2);
                this.sendSelectionEvent(14);
                break;
            }
            case 517: {
                if (!this.hooks(35)) break;
                OS.SetForegroundWindow(l2);
                this.sendEvent(35);
                if (!this.isDisposed()) break;
                return 0L;
            }
            case 1026: {
                if (this.toolTip == null || this.toolTip.visible) break;
                this.toolTip.visible = true;
                if (!this.toolTip.hooks(22)) break;
                OS.SetForegroundWindow(l2);
                this.toolTip.sendEvent(22);
                if (!this.isDisposed()) break;
                return 0L;
            }
            case 1027: 
            case 1028: 
            case 1029: {
                if (this.toolTip == null) break;
                if (this.toolTip.visible) {
                    this.toolTip.visible = false;
                    if (this.toolTip.hooks(23)) {
                        OS.SetForegroundWindow(l2);
                        this.toolTip.sendEvent(23);
                        if (this.isDisposed()) {
                            return 0L;
                        }
                    }
                }
                if (l4 != 1029L || !this.toolTip.hooks(13)) break;
                OS.SetForegroundWindow(l2);
                this.toolTip.sendSelectionEvent(13);
                if (!this.isDisposed()) break;
                return 0L;
            }
        }
        this.display.wakeThread();
        return 0L;
    }

    void recreate() {
        this.createUpdateWidget(false);
        if (!this.visible) {
            this.setVisible(false);
        }
        if (this.text.length() != 0) {
            this.setText(this.text);
        }
        if (this.image != null) {
            this.setImage(this.image);
        }
        if (this.toolTipText != null) {
            this.setToolTipText(this.toolTipText);
        }
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.parent = null;
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        if (this.toolTip != null) {
            this.toolTip.item = null;
        }
        this.toolTip = null;
        if (this.image2 != null) {
            this.image2.dispose();
        }
        this.image2 = null;
        this.highlightImage = null;
        this.toolTipText = null;
        NOTIFYICONDATA nOTIFYICONDATA = new NOTIFYICONDATA();
        nOTIFYICONDATA.cbSize = NOTIFYICONDATA.sizeof;
        nOTIFYICONDATA.uID = this.id;
        nOTIFYICONDATA.hWnd = this.display.hwndMessage;
        OS.Shell_NotifyIcon(2, nOTIFYICONDATA);
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

    public void removeMenuDetectListener(MenuDetectListener menuDetectListener) {
        this.checkWidget();
        if (menuDetectListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(35, menuDetectListener);
    }

    public void setHighlightImage(Image image) {
        this.checkWidget();
        if (image != null && image.isDisposed()) {
            this.error(5);
        }
        this.highlightImage = image;
    }

    @Override
    public void setImage(Image image) {
        this.checkWidget();
        if (image != null && image.isDisposed()) {
            this.error(5);
        }
        super.setImage(image);
        if (this.image2 != null) {
            this.image2.dispose();
        }
        this.image2 = null;
        long l2 = 0L;
        Image image2 = image;
        if (image2 != null) {
            switch (image2.type) {
                case 0: {
                    this.image2 = Display.createIcon(image);
                    l2 = this.image2.handle;
                    break;
                }
                case 1: {
                    l2 = image2.handle;
                }
            }
        }
        NOTIFYICONDATA nOTIFYICONDATA = new NOTIFYICONDATA();
        nOTIFYICONDATA.cbSize = NOTIFYICONDATA.sizeof;
        nOTIFYICONDATA.uID = this.id;
        nOTIFYICONDATA.hWnd = this.display.hwndMessage;
        nOTIFYICONDATA.hIcon = l2;
        nOTIFYICONDATA.uFlags = 2;
        OS.Shell_NotifyIcon(1, nOTIFYICONDATA);
    }

    public void setToolTip(ToolTip toolTip) {
        this.checkWidget();
        ToolTip toolTip2 = this.toolTip;
        ToolTip toolTip3 = toolTip;
        if (toolTip2 != null) {
            toolTip2.item = null;
        }
        if ((this.toolTip = toolTip3) != null) {
            toolTip3.item = this;
        }
    }

    public void setToolTipText(String string) {
        this.checkWidget();
        this.toolTipText = string;
        NOTIFYICONDATA nOTIFYICONDATA = new NOTIFYICONDATA();
        if (string != null) {
            char[] cArray = nOTIFYICONDATA.szTip;
            int n = Math.min(cArray.length - 1, string.length());
            string.getChars(0, n, cArray, 0);
        }
        nOTIFYICONDATA.cbSize = NOTIFYICONDATA.sizeof;
        nOTIFYICONDATA.uID = this.id;
        nOTIFYICONDATA.hWnd = this.display.hwndMessage;
        nOTIFYICONDATA.uFlags = 4;
        OS.Shell_NotifyIcon(1, nOTIFYICONDATA);
    }

    public void setVisible(boolean bl) {
        this.checkWidget();
        if (this.visible == bl) {
            return;
        }
        if (bl) {
            this.sendEvent(22);
            if (this.isDisposed()) {
                return;
            }
        }
        this.visible = bl;
        NOTIFYICONDATA nOTIFYICONDATA = new NOTIFYICONDATA();
        nOTIFYICONDATA.cbSize = NOTIFYICONDATA.sizeof;
        nOTIFYICONDATA.uID = this.id;
        nOTIFYICONDATA.hWnd = this.display.hwndMessage;
        nOTIFYICONDATA.uFlags = 8;
        nOTIFYICONDATA.dwState = bl ? 0 : 1;
        nOTIFYICONDATA.dwStateMask = 1;
        OS.Shell_NotifyIcon(1, nOTIFYICONDATA);
        if (!bl) {
            this.sendEvent(23);
        }
    }
}

