/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.IME;

public class Canvas
extends Composite {
    Caret caret;
    IME ime;

    Canvas() {
    }

    public Canvas(Composite composite, int n) {
        super(composite, n);
    }

    public void drawBackground(GC gC, int n, int n2, int n3, int n4) {
        n = DPIUtil.autoScaleUp(n);
        n2 = DPIUtil.autoScaleUp(n2);
        n3 = DPIUtil.autoScaleUp(n3);
        n4 = DPIUtil.autoScaleUp(n4);
        this.drawBackgroundInPixels(gC, n, n2, n3, n4, 0, 0);
    }

    public Caret getCaret() {
        this.checkWidget();
        return this.caret;
    }

    public IME getIME() {
        this.checkWidget();
        return this.ime;
    }

    boolean isUseWsBorder() {
        return super.isUseWsBorder() || this.display != null && this.display.useWsBorderCanvas;
    }

    @Override
    void releaseChildren(boolean bl) {
        if (this.caret != null) {
            this.caret.release(false);
            this.caret = null;
        }
        if (this.ime != null) {
            this.ime.release(false);
            this.ime = null;
        }
        super.releaseChildren(bl);
    }

    @Override
    void reskinChildren(int n) {
        if (this.caret != null) {
            this.caret.reskin(n);
        }
        if (this.ime != null) {
            this.ime.reskin(n);
        }
        super.reskinChildren(n);
    }

    public void scroll(int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        this.checkWidget();
        n = DPIUtil.autoScaleUp(n);
        n2 = DPIUtil.autoScaleUp(n2);
        n3 = DPIUtil.autoScaleUp(n3);
        n4 = DPIUtil.autoScaleUp(n4);
        n5 = DPIUtil.autoScaleUp(n5);
        n6 = DPIUtil.autoScaleUp(n6);
        this.scrollInPixels(n, n2, n3, n4, n5, n6, bl);
    }

    void scrollInPixels(int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        int n7;
        int n8;
        boolean bl2;
        this.forceResize();
        boolean bl3 = bl2 = this.caret != null && this.caret.isFocusCaret();
        if (bl2) {
            this.caret.killFocus();
        }
        RECT rECT = new RECT();
        OS.SetRect(rECT, n3, n4, n3 + n5, n4 + n6);
        RECT rECT2 = new RECT();
        OS.GetClientRect(this.handle, rECT2);
        if (OS.IntersectRect(rECT2, rECT, rECT2)) {
            n8 = 384;
            OS.RedrawWindow(this.handle, null, 0L, 384);
        }
        n8 = n - n3;
        int n9 = n2 - n4;
        if (this.findImageControl() != null) {
            n7 = 1029;
            if (bl) {
                n7 |= 0x80;
            }
            OS.RedrawWindow(this.handle, rECT, 0L, n7);
            OS.OffsetRect(rECT, n8, n9);
            OS.RedrawWindow(this.handle, rECT, 0L, n7);
        } else {
            n7 = 6;
            OS.ScrollWindowEx(this.handle, n8, n9, rECT, null, 0L, null, 6);
        }
        if (bl) {
            for (Control control : this._getChildren()) {
                Rectangle rectangle = control.getBoundsInPixels();
                if (Math.min(n3 + n5, rectangle.x + rectangle.width) < Math.max(n3, rectangle.x) || Math.min(n4 + n6, rectangle.y + rectangle.height) < Math.max(n4, rectangle.y)) continue;
                control.setLocationInPixels(rectangle.x + n8, rectangle.y + n9);
            }
        }
        if (bl2) {
            this.caret.setFocus();
        }
    }

    public void setCaret(Caret caret) {
        this.checkWidget();
        Caret caret2 = caret;
        Caret caret3 = this.caret;
        this.caret = caret2;
        if (this.hasFocus()) {
            if (caret3 != null) {
                caret3.killFocus();
            }
            if (caret2 != null) {
                if (caret2.isDisposed()) {
                    this.error(5);
                }
                caret2.setFocus();
            }
        }
    }

    @Override
    public void setFont(Font font) {
        this.checkWidget();
        if (this.caret != null) {
            this.caret.setFont(font);
        }
        super.setFont(font);
    }

    public void setIME(IME iME) {
        this.checkWidget();
        if (iME != null && iME.isDisposed()) {
            this.error(5);
        }
        this.ime = iME;
    }

    @Override
    TCHAR windowClass() {
        if (this.display.useOwnDC) {
            return this.display.windowOwnDCClass;
        }
        return super.windowClass();
    }

    @Override
    long windowProc(long l2, int n, long l3, long l4) {
        if (n == Display.SWT_RESTORECARET && (this.state & 2) != 0 && this.caret != null) {
            this.caret.killFocus();
            this.caret.setFocus();
            return 1L;
        }
        return super.windowProc(l2, n, l3, l4);
    }

    @Override
    LRESULT WM_CHAR(long l2, long l3) {
        LRESULT lRESULT = super.WM_CHAR(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (this.caret != null) {
            switch ((int)l2) {
                case 8: 
                case 27: 
                case 127: {
                    break;
                }
                default: {
                    int[] nArray;
                    if (OS.GetKeyState(17) < 0 || !OS.SystemParametersInfo(4128, 0, nArray = new int[]{0}, 0) || nArray[0] == 0) break;
                    OS.SetCursor(0L);
                    break;
                }
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_IME_COMPOSITION(long l2, long l3) {
        LRESULT lRESULT;
        if (this.ime != null && (lRESULT = this.ime.WM_IME_COMPOSITION(l2, l3)) != null) {
            return lRESULT;
        }
        return super.WM_IME_COMPOSITION(l2, l3);
    }

    @Override
    LRESULT WM_IME_COMPOSITION_START(long l2, long l3) {
        LRESULT lRESULT;
        if (this.ime != null && (lRESULT = this.ime.WM_IME_COMPOSITION_START(l2, l3)) != null) {
            return lRESULT;
        }
        return super.WM_IME_COMPOSITION_START(l2, l3);
    }

    @Override
    LRESULT WM_IME_ENDCOMPOSITION(long l2, long l3) {
        LRESULT lRESULT;
        if (this.ime != null && (lRESULT = this.ime.WM_IME_ENDCOMPOSITION(l2, l3)) != null) {
            return lRESULT;
        }
        return super.WM_IME_ENDCOMPOSITION(l2, l3);
    }

    @Override
    LRESULT WM_INPUTLANGCHANGE(long l2, long l3) {
        LRESULT lRESULT = super.WM_INPUTLANGCHANGE(l2, l3);
        if (this.caret != null && this.caret.isFocusCaret()) {
            this.caret.setIMEFont();
            this.caret.resizeIME();
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_KEYDOWN(long l2, long l3) {
        LRESULT lRESULT = super.WM_KEYDOWN(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (this.ime != null) {
            this.ime.WM_KEYDOWN(l2, l3);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_KILLFOCUS(long l2, long l3) {
        Object object;
        if (this.ime != null && (object = this.ime.WM_KILLFOCUS(l2, l3)) != null) {
            return object;
        }
        object = this.caret;
        LRESULT lRESULT = super.WM_KILLFOCUS(l2, l3);
        if (object != null) {
            ((Caret)object).killFocus();
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_LBUTTONDOWN(long l2, long l3) {
        LRESULT lRESULT;
        if (this.ime != null && (lRESULT = this.ime.WM_LBUTTONDOWN(l2, l3)) != null) {
            return lRESULT;
        }
        return super.WM_LBUTTONDOWN(l2, l3);
    }

    @Override
    LRESULT WM_SETFOCUS(long l2, long l3) {
        LRESULT lRESULT = super.WM_SETFOCUS(l2, l3);
        if (this.caret != null && this.caret.isFocusCaret()) {
            this.caret.setFocus();
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        LRESULT lRESULT = super.WM_SIZE(l2, l3);
        if (this.caret != null && this.caret.isFocusCaret()) {
            this.caret.resizeIME();
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_WINDOWPOSCHANGED(long l2, long l3) {
        boolean bl;
        LRESULT lRESULT = super.WM_WINDOWPOSCHANGED(l2, l3);
        boolean bl2 = bl = (this.style & 0x4000000) != 0 && this.caret != null && this.caret.isFocusCaret();
        if (bl) {
            this.caret.setFocus();
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_WINDOWPOSCHANGING(long l2, long l3) {
        boolean bl;
        LRESULT lRESULT = super.WM_WINDOWPOSCHANGING(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        boolean bl2 = bl = (this.style & 0x4000000) != 0 && this.caret != null && this.caret.isFocusCaret();
        if (bl) {
            this.caret.killFocus();
        }
        return lRESULT;
    }
}

