/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.ICONINFO;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.NONCLIENTMETRICS;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.I;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TaskBar;

public class TaskItem
extends Item {
    TaskBar parent;
    Shell shell;
    int progress;
    int progressState = -1;
    Image overlayImage;
    String overlayText = "";
    boolean showingText = false;
    Menu menu;
    static final int PROGRESS_MAX = 100;

    TaskItem(TaskBar taskBar, int n) {
        super(taskBar, n);
        this.parent = taskBar;
        this.parent.createItem(this, -1);
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

    @Override
    public Menu getMenu() {
        this.checkWidget();
        return this.menu;
    }

    public Image getOverlayImage() {
        this.checkWidget();
        return this.overlayImage;
    }

    public String getOverlayText() {
        this.checkWidget();
        return this.overlayText;
    }

    public TaskBar getParent() {
        this.checkWidget();
        return this.parent;
    }

    public int getProgress() {
        this.checkWidget();
        return this.progress;
    }

    public int getProgressState() {
        this.checkWidget();
        return this.progressState;
    }

    void recreate() {
        if (this.showingText) {
            if (this.overlayText.length() != 0) {
                this.updateText();
            }
        } else if (this.overlayImage != null) {
            this.updateImage();
        }
        if (this.progress != 0) {
            this.updateProgress();
        }
        if (this.progressState != -1) {
            this.updateProgressState();
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
        this.overlayImage = null;
        this.overlayText = null;
    }

    public void setMenu(Menu menu) {
        this.checkWidget();
        if (menu != null) {
            if (menu.isDisposed()) {
                this.error(5);
            }
            if ((menu.style & 8) == 0) {
                this.error(37);
            }
        }
        if (this.shell != null) {
            return;
        }
        this.menu = menu;
        this.parent.setMenu(menu);
    }

    public void setOverlayImage(Image image) {
        this.checkWidget();
        if (image != null && image.isDisposed()) {
            this.error(5);
        }
        if (this.shell == null) {
            return;
        }
        this.overlayImage = image;
        if (this.overlayImage != null) {
            this.updateImage();
        } else if (this.overlayText.length() != 0) {
            this.updateText();
        } else {
            this.parent.mTaskbarList3.SetOverlayIcon(this.shell.handle, 0L, 0L);
        }
    }

    public void setOverlayText(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if (this.shell == null) {
            return;
        }
        this.overlayText = string;
        if (string.length() != 0) {
            this.updateText();
        } else if (this.overlayImage != null) {
            this.updateImage();
        } else {
            this.parent.mTaskbarList3.SetOverlayIcon(this.shell.handle, 0L, 0L);
        }
    }

    public void setProgress(int n) {
        this.checkWidget();
        if (this.shell == null) {
            return;
        }
        if (this.progress == (n = Math.max(0, Math.min(n, 100)))) {
            return;
        }
        this.progress = n;
        this.updateProgress();
    }

    public void setProgressState(int n) {
        this.checkWidget();
        if (this.shell == null) {
            return;
        }
        if (this.progressState == n) {
            return;
        }
        this.progressState = n;
        this.updateProgressState();
    }

    void setShell(Shell shell) {
        this.shell = shell;
        this.shell.addListener(12, new I(this));
    }

    void updateImage() {
        this.showingText = false;
        Image image = null;
        long l2 = 0L;
        switch (this.overlayImage.type) {
            case 0: {
                image = Display.createIcon(this.overlayImage);
                l2 = image.handle;
                break;
            }
            case 1: {
                l2 = this.overlayImage.handle;
            }
        }
        this.parent.mTaskbarList3.SetOverlayIcon(this.shell.handle, l2, 0L);
        if (image != null) {
            image.dispose();
        }
    }

    void updateProgress() {
        if (this.progressState == 2) {
            return;
        }
        if (this.progressState == -1) {
            return;
        }
        this.parent.mTaskbarList3.SetProgressValue(this.shell.handle, this.progress, 100L);
    }

    void updateProgressState() {
        int n = 0;
        switch (this.progressState) {
            case 0: {
                n = 2;
                break;
            }
            case 1: {
                n = 4;
                break;
            }
            case 4: {
                n = 8;
                break;
            }
            case 2: {
                n = 1;
            }
        }
        this.parent.mTaskbarList3.SetProgressValue(this.shell.handle, this.progress, 100L);
        this.parent.mTaskbarList3.SetProgressState(this.shell.handle, n);
    }

    void updateText() {
        this.showingText = true;
        int n = 16;
        int n2 = 16;
        long l2 = OS.GetDC(0L);
        BITMAPINFOHEADER bITMAPINFOHEADER = new BITMAPINFOHEADER();
        bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
        bITMAPINFOHEADER.biWidth = 16;
        bITMAPINFOHEADER.biHeight = -16;
        bITMAPINFOHEADER.biPlanes = 1;
        bITMAPINFOHEADER.biBitCount = (short)32;
        bITMAPINFOHEADER.biCompression = 0;
        byte[] byArray = new byte[BITMAPINFOHEADER.sizeof];
        OS.MoveMemory(byArray, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
        long[] lArray = new long[]{0L};
        long l3 = OS.CreateDIBSection(0L, byArray, 0, lArray, 0L, 0);
        if (l3 == 0L) {
            this.error(2);
        }
        long l4 = OS.CreateCompatibleDC(l2);
        long l5 = OS.SelectObject(l4, l3);
        long l6 = OS.CreateBitmap(16, 16, 1, 1, null);
        if (l6 == 0L) {
            this.error(2);
        }
        long l7 = OS.CreateCompatibleDC(l2);
        long l8 = OS.SelectObject(l7, l6);
        OS.PatBlt(l7, 0, 0, 16, 16, 16711778);
        long l9 = OS.SelectObject(l7, OS.GetStockObject(4));
        OS.RoundRect(l7, 0, 0, 16, 16, 8, 8);
        OS.SelectObject(l7, l9);
        long l10 = OS.CreateSolidBrush(OS.GetSysColor(13));
        l9 = OS.SelectObject(l4, l10);
        OS.RoundRect(l4, 0, 0, 16, 16, 8, 8);
        OS.SelectObject(l4, l9);
        OS.DeleteObject(l10);
        int n3 = 2080;
        RECT rECT = new RECT();
        char[] cArray = this.overlayText.toCharArray();
        int n4 = cArray.length;
        long l11 = 0L;
        long l12 = 0L;
        NONCLIENTMETRICS nONCLIENTMETRICS = new NONCLIENTMETRICS();
        nONCLIENTMETRICS.cbSize = NONCLIENTMETRICS.sizeof;
        if (OS.SystemParametersInfo(41, 0, nONCLIENTMETRICS, 0)) {
            LOGFONT lOGFONT = nONCLIENTMETRICS.lfMessageFont;
            lOGFONT.lfHeight = -10;
            l11 = OS.CreateFontIndirect(lOGFONT);
            l12 = OS.SelectObject(l4, l11);
            OS.DrawText(l4, cArray, n4, rECT, 3104);
            if (rECT.right > 14) {
                OS.SelectObject(l4, l12);
                OS.DeleteObject(l11);
                lOGFONT.lfHeight = -8;
                l11 = OS.CreateFontIndirect(lOGFONT);
                OS.SelectObject(l4, l11);
            }
        }
        OS.DrawText(l4, cArray, n4, rECT, 3104);
        OS.OffsetRect(rECT, (16 - rECT.right) / 2, (16 - rECT.bottom) / 2);
        int n5 = OS.SetBkMode(l4, 1);
        OS.SetTextColor(l4, OS.GetSysColor(14));
        OS.DrawText(l4, cArray, n4, rECT, 2080);
        if (l11 != 0L) {
            OS.SelectObject(l4, l12);
            OS.DeleteObject(l11);
        }
        OS.SetBkMode(l4, n5);
        OS.SelectObject(l4, l5);
        OS.DeleteDC(l4);
        OS.SelectObject(l7, l8);
        OS.DeleteDC(l7);
        OS.ReleaseDC(0L, l2);
        ICONINFO iCONINFO = new ICONINFO();
        iCONINFO.fIcon = true;
        iCONINFO.hbmColor = l3;
        iCONINFO.hbmMask = l6;
        long l13 = OS.CreateIconIndirect(iCONINFO);
        if (l13 == 0L) {
            this.error(2);
        }
        OS.DeleteObject(l3);
        OS.DeleteObject(l6);
        this.parent.mTaskbarList3.SetOverlayIcon(this.shell.handle, l13, 0L);
        OS.DestroyIcon(l13);
    }
}

