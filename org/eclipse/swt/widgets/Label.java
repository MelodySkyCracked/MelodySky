/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.BidiUtil;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.DRAWITEMSTRUCT;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;

public class Label
extends Control {
    String text = "";
    Image image;
    boolean isImageMode;
    static final int MARGIN = 4;
    static final long LabelProc;
    static final TCHAR LabelClass;

    public Label(Composite composite, int n) {
        super(composite, Label.checkStyle(n));
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        if (OS.WIN32_VERSION >= OS.VERSION(6, 1)) {
            switch (n) {
                case 515: {
                    return OS.DefWindowProc(l2, n, l3, l4);
                }
            }
        }
        return OS.CallWindowProc(LabelProc, l2, n, l3, l4);
    }

    static int checkStyle(int n) {
        if (((n |= 0x80000) & 2) != 0) {
            n = Widget.checkBits(n, 512, 256, 0, 0, 0, 0);
            return Widget.checkBits(n, 8, 4, 32, 0, 0, 0);
        }
        return Widget.checkBits(n, 16384, 0x1000000, 131072, 0, 0, 0);
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        this.checkWidget();
        int n3 = 0;
        int n4 = 0;
        int n5 = this.getBorderWidthInPixels();
        if ((this.style & 2) != 0) {
            int n6 = OS.GetSystemMetrics(5);
            if ((this.style & 0x100) != 0) {
                n3 = 64;
                n4 = n6 * 2;
            } else {
                n3 = n6 * 2;
                n4 = 64;
            }
            if (n != -1) {
                n3 = n;
            }
            if (n2 != -1) {
                n4 = n2;
            }
            return new Point(n3 += n5 * 2, n4 += n5 * 2);
        }
        if (this.isImageMode) {
            Rectangle rectangle = this.image.getBoundsInPixels();
            n3 += rectangle.width;
            n4 += rectangle.height;
        } else {
            long l2 = OS.GetDC(this.handle);
            long l3 = OS.SendMessage(this.handle, 49, 0L, 0L);
            long l4 = OS.SelectObject(l2, l3);
            int n7 = OS.GetWindowTextLength(this.handle);
            if (n7 == 0) {
                TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
                OS.GetTextMetrics(l2, tEXTMETRIC);
                n4 = Math.max(n4, tEXTMETRIC.tmHeight);
            } else {
                RECT rECT = new RECT();
                int n8 = 9280;
                if ((this.style & 0x40) != 0 && n != -1) {
                    n8 |= 0x10;
                    rECT.right = Math.max(0, n - n3);
                }
                char[] cArray = new char[n7 + 1];
                OS.GetWindowText(this.handle, cArray, n7 + 1);
                OS.DrawText(l2, cArray, n7, rECT, n8);
                n3 += rECT.right - rECT.left;
                n4 = Math.max(n4, rECT.bottom - rECT.top);
            }
            if (l3 != 0L) {
                OS.SelectObject(l2, l4);
            }
            OS.ReleaseDC(this.handle, l2);
        }
        if (n != -1) {
            n3 = n;
        }
        if (n2 != -1) {
            n4 = n2;
        }
        return new Point(n3 += n5 * 2, n4 += n5 * 2);
    }

    @Override
    void createHandle() {
        super.createHandle();
        this.state |= 0x100;
    }

    public int getAlignment() {
        this.checkWidget();
        if ((this.style & 2) != 0) {
            return 0;
        }
        if ((this.style & 0x4000) != 0) {
            return 16384;
        }
        if ((this.style & 0x1000000) != 0) {
            return 0x1000000;
        }
        if ((this.style & 0x20000) != 0) {
            return 131072;
        }
        return 16384;
    }

    public Image getImage() {
        this.checkWidget();
        return this.image;
    }

    @Override
    String getNameText() {
        return this.getText();
    }

    public String getText() {
        this.checkWidget();
        if ((this.style & 2) != 0) {
            return "";
        }
        return this.text;
    }

    boolean isUseWsBorder() {
        return super.isUseWsBorder() || this.display != null && this.display.useWsBorderLabel;
    }

    @Override
    boolean mnemonicHit(char c) {
        Control control = this;
        while (control.parent != null) {
            int n;
            Control[] controlArray = control.parent._getChildren();
            for (n = 0; n < controlArray.length && controlArray[n] != control; ++n) {
            }
            if (++n < controlArray.length && controlArray[n].setFocus()) {
                return true;
            }
            control = control.parent;
        }
        return false;
    }

    @Override
    boolean mnemonicMatch(char c) {
        char c2 = this.findMnemonic(this.getText());
        return c2 != '\u0000' && Character.toUpperCase(c) == Character.toUpperCase(c2);
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.text = null;
        this.image = null;
    }

    @Override
    int resolveTextDirection() {
        return (this.style & 2) != 0 ? 0 : BidiUtil.resolveTextDirection(this.text);
    }

    public void setAlignment(int n) {
        this.checkWidget();
        if ((this.style & 2) != 0) {
            return;
        }
        if ((n & 0x1024000) == 0) {
            return;
        }
        this.style &= 0xFEFDBFFF;
        this.style |= n & 0x1024000;
        this.updateStyleBits(this.getEnabled());
        OS.InvalidateRect(this.handle, null, true);
    }

    @Override
    public void setEnabled(boolean bl) {
        if ((this.style & 2) != 0) {
            return;
        }
        this.updateStyleBits(bl);
        super.setEnabled(bl);
    }

    public void setImage(Image image) {
        this.checkWidget();
        if ((this.style & 2) != 0) {
            return;
        }
        if (image != null && image.isDisposed()) {
            this.error(5);
        }
        this.isImageMode = (this.image = image) != null;
        this.updateStyleBits(this.getEnabled());
        OS.InvalidateRect(this.handle, null, true);
    }

    public void setText(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if ((this.style & 2) != 0) {
            return;
        }
        this.isImageMode = false;
        this.updateStyleBits(this.getEnabled());
        if (string.equals(this.text)) {
            return;
        }
        this.text = string;
        string = Display.withCrLf(string);
        TCHAR tCHAR = new TCHAR(this.getCodePage(), string, true);
        OS.SetWindowText(this.handle, tCHAR);
        if ((this.state & 0x400000) != 0) {
            this.updateTextDirection(0x6000000);
        }
    }

    void updateStyleBits(boolean bl) {
        int n;
        boolean bl2 = this.isImageMode;
        if (!bl2 && this.display.disabledLabelForegroundPixel != -1 && !bl) {
            bl2 = true;
        }
        int n2 = n = OS.GetWindowLong(this.handle, -16);
        n &= 0xFFFFFFF2;
        n &= 0xFFFFFFF0;
        if (bl2) {
            n |= 0xD;
        } else {
            if ((this.style & 0x4000) != 0) {
                n = (this.style & 0x40) != 0 ? (n |= 0) : (n |= 0xC);
            }
            if ((this.style & 0x1000000) != 0) {
                n |= 1;
            }
            if ((this.style & 0x20000) != 0) {
                n |= 2;
            }
        }
        if (n2 != n) {
            OS.SetWindowLong(this.handle, -16, n);
        }
    }

    @Override
    int widgetExtStyle() {
        int n = super.widgetExtStyle() & 0xFFFFFDFF;
        if ((this.style & 0x800) != 0) {
            return n | 0x20000;
        }
        return n;
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle() | 0x100;
        if ((this.style & 2) != 0) {
            return n | 0xD;
        }
        if ((this.style & 0x40) != 0) {
            n |= 0x2000;
        }
        if ((this.style & 0x1000000) != 0) {
            return n | 1;
        }
        if ((this.style & 0x20000) != 0) {
            return n | 2;
        }
        if ((this.style & 0x40) != 0) {
            return n | 0;
        }
        return n | 0xC;
    }

    @Override
    TCHAR windowClass() {
        return LabelClass;
    }

    @Override
    long windowProc() {
        return LabelProc;
    }

    @Override
    LRESULT WM_ERASEBKGND(long l2, long l3) {
        LRESULT lRESULT = super.WM_ERASEBKGND(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        int n = OS.GetWindowLong(this.handle, -16);
        if ((n & 0xD) == 13) {
            return LRESULT.ONE;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        LRESULT lRESULT = super.WM_SIZE(l2, l3);
        if (this.isDisposed()) {
            return lRESULT;
        }
        if ((this.style & 2) != 0) {
            OS.InvalidateRect(this.handle, null, true);
            return lRESULT;
        }
        int n = OS.GetWindowLong(this.handle, -16);
        if ((n & 0xD) == 13) {
            OS.InvalidateRect(this.handle, null, true);
            return lRESULT;
        }
        if ((n & 0xC) != 12) {
            OS.InvalidateRect(this.handle, null, true);
            return lRESULT;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_UPDATEUISTATE(long l2, long l3) {
        boolean bl;
        LRESULT lRESULT = super.WM_UPDATEUISTATE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        boolean bl2 = bl = this.findImageControl() != null;
        if (!bl && (this.state & 0x100) != 0 && OS.IsAppThemed()) {
            boolean bl3 = bl = this.findThemeControl() != null;
        }
        if (bl) {
            OS.InvalidateRect(this.handle, null, false);
            long l4 = OS.DefWindowProc(this.handle, 296, l2, l3);
            return new LRESULT(l4);
        }
        return lRESULT;
    }

    void wmDrawChildSeparator(DRAWITEMSTRUCT dRAWITEMSTRUCT) {
        int n;
        if ((this.style & 0x20) != 0) {
            return;
        }
        RECT rECT = new RECT();
        int n2 = OS.GetSystemMetrics(5);
        int n3 = n = (this.style & 4) != 0 ? 10 : 6;
        if ((this.style & 0x100) != 0) {
            int n4 = dRAWITEMSTRUCT.top + Math.max(n2 * 2, (dRAWITEMSTRUCT.bottom - dRAWITEMSTRUCT.top) / 2);
            OS.SetRect(rECT, dRAWITEMSTRUCT.left, dRAWITEMSTRUCT.top, dRAWITEMSTRUCT.right, n4);
            OS.DrawEdge(dRAWITEMSTRUCT.hDC, rECT, n, 8);
        } else {
            int n5 = dRAWITEMSTRUCT.left + Math.max(n2 * 2, (dRAWITEMSTRUCT.right - dRAWITEMSTRUCT.left) / 2);
            OS.SetRect(rECT, dRAWITEMSTRUCT.left, dRAWITEMSTRUCT.top, n5, dRAWITEMSTRUCT.bottom);
            OS.DrawEdge(dRAWITEMSTRUCT.hDC, rECT, n, 4);
        }
    }

    void wmDrawChildImage(DRAWITEMSTRUCT dRAWITEMSTRUCT) {
        int n = dRAWITEMSTRUCT.right - dRAWITEMSTRUCT.left;
        int n2 = dRAWITEMSTRUCT.bottom - dRAWITEMSTRUCT.top;
        if (n == 0 || n2 == 0) {
            return;
        }
        Rectangle rectangle = this.image.getBoundsInPixels();
        int n3 = 0;
        if ((this.style & 0x1000000) != 0) {
            n3 = Math.max(0, (n - rectangle.width) / 2);
        } else if ((this.style & 0x20000) != 0) {
            n3 = n - rectangle.width;
        }
        GCData gCData = new GCData();
        gCData.device = this.display;
        GC gC = GC.win32_new(dRAWITEMSTRUCT.hDC, gCData);
        Image image = this.getEnabled() ? this.image : new Image((Device)this.display, this.image, 1);
        gC.drawImage(image, DPIUtil.autoScaleDown(n3), DPIUtil.autoScaleDown(Math.max(0, (n2 - rectangle.height) / 2)));
        if (image != this.image) {
            image.dispose();
        }
        gC.dispose();
    }

    void wmDrawChildText(DRAWITEMSTRUCT dRAWITEMSTRUCT) {
        long l2;
        int n = dRAWITEMSTRUCT.right - dRAWITEMSTRUCT.left;
        int n2 = dRAWITEMSTRUCT.bottom - dRAWITEMSTRUCT.top;
        if (n == 0 || n2 == 0) {
            return;
        }
        RECT rECT = new RECT();
        rECT.left = dRAWITEMSTRUCT.left;
        rECT.top = dRAWITEMSTRUCT.top;
        rECT.right = dRAWITEMSTRUCT.right;
        rECT.bottom = dRAWITEMSTRUCT.bottom;
        int n3 = 8256;
        if ((this.style & 0x4000) != 0) {
            n3 |= 0;
        }
        if ((this.style & 0x1000000) != 0) {
            n3 |= 1;
        }
        if ((this.style & 0x20000) != 0) {
            n3 |= 2;
        }
        if ((this.style & 0x40) != 0) {
            n3 |= 0x10;
        }
        if (((l2 = OS.SendMessage(this.handle, 297, 0L, 0L)) & 2L) != 0L) {
            n3 |= 0x100000;
        }
        if (!this.getEnabled()) {
            int n4 = OS.GetSysColor(17);
            if (this.display.disabledLabelForegroundPixel != -1) {
                n4 = this.display.disabledLabelForegroundPixel;
            }
            OS.SetTextColor(dRAWITEMSTRUCT.hDC, n4);
        }
        char[] cArray = this.text.toCharArray();
        OS.DrawText(dRAWITEMSTRUCT.hDC, cArray, cArray.length, rECT, n3);
    }

    @Override
    LRESULT wmDrawChild(long l2, long l3) {
        DRAWITEMSTRUCT dRAWITEMSTRUCT = new DRAWITEMSTRUCT();
        OS.MoveMemory(dRAWITEMSTRUCT, l3, DRAWITEMSTRUCT.sizeof);
        this.drawBackground(dRAWITEMSTRUCT.hDC);
        if ((this.style & 2) != 0) {
            this.wmDrawChildSeparator(dRAWITEMSTRUCT);
        } else if (this.isImageMode) {
            this.wmDrawChildImage(dRAWITEMSTRUCT);
        } else {
            this.wmDrawChildText(dRAWITEMSTRUCT);
        }
        return null;
    }

    static {
        LabelClass = new TCHAR(0, "STATIC", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, LabelClass, wNDCLASS);
        LabelProc = wNDCLASS.lpfnWndProc;
    }
}

