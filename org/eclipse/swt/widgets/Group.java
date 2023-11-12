/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.BidiUtil;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class Group
extends Composite {
    String text = "";
    static final int CLIENT_INSET = 3;
    static final long GroupProc;
    static final TCHAR GroupClass;

    public Group(Composite composite, int n) {
        super(composite, Group.checkStyle(n));
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        switch (n) {
            case 513: 
            case 515: {
                return OS.DefWindowProc(l2, n, l3, l4);
            }
        }
        return OS.CallWindowProc(GroupProc, l2, n, l3, l4);
    }

    static int checkStyle(int n) {
        return (n |= 0x80000) & 0xFFFFFCFF;
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        this.checkWidget();
        Point point = super.computeSizeInPixels(n, n2, bl);
        int n3 = this.text.length();
        if (n3 != 0) {
            String string = this.fixText(false);
            char[] cArray = (string == null ? this.text : string).toCharArray();
            long l2 = 0L;
            long l3 = OS.GetDC(this.handle);
            long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
            if (l4 != 0L) {
                l2 = OS.SelectObject(l3, l4);
            }
            RECT rECT = new RECT();
            int n4 = 1056;
            OS.DrawText(l3, cArray, cArray.length, rECT, 1056);
            if (l4 != 0L) {
                OS.SelectObject(l3, l2);
            }
            OS.ReleaseDC(this.handle, l3);
            int n5 = OS.IsAppThemed() ? 0 : 1;
            point.x = Math.max(point.x, rECT.right - rECT.left + 18 + n5);
        }
        return point;
    }

    @Override
    Rectangle computeTrimInPixels(int n, int n2, int n3, int n4) {
        this.checkWidget();
        Rectangle rectangle = super.computeTrimInPixels(n, n2, n3, n4);
        long l2 = 0L;
        long l3 = OS.GetDC(this.handle);
        long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
        if (l4 != 0L) {
            l2 = OS.SelectObject(l3, l4);
        }
        TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
        OS.GetTextMetrics(l3, tEXTMETRIC);
        if (l4 != 0L) {
            OS.SelectObject(l3, l2);
        }
        OS.ReleaseDC(this.handle, l3);
        int n5 = OS.IsAppThemed() ? 0 : 1;
        Rectangle rectangle2 = rectangle;
        rectangle2.x -= 3;
        Rectangle rectangle3 = rectangle;
        rectangle3.y -= tEXTMETRIC.tmHeight + n5;
        Rectangle rectangle4 = rectangle;
        rectangle4.width += 6;
        Rectangle rectangle5 = rectangle;
        rectangle5.height += tEXTMETRIC.tmHeight + 3 + n5;
        return rectangle;
    }

    @Override
    void createHandle() {
        Composite composite = this.parent;
        composite.state |= 0x100000;
        super.createHandle();
        Composite composite2 = this.parent;
        composite2.state &= 0xFFEFFFFF;
        this.state |= 0x200;
        this.state &= 0xFFFFFFFD;
    }

    @Override
    void enableWidget(boolean bl) {
        super.enableWidget(bl);
        String string = this.fixText(bl);
        if (string != null) {
            TCHAR tCHAR = new TCHAR(this.getCodePage(), string, true);
            OS.SetWindowText(this.handle, tCHAR);
        }
        if (bl && this.hasCustomForeground()) {
            OS.InvalidateRect(this.handle, null, true);
        }
    }

    String fixText(boolean bl) {
        if (this.text.length() == 0) {
            return null;
        }
        if ((this.style & 0x4000000) != 0) {
            String string = null;
            if (!bl && !OS.IsAppThemed()) {
                string = " " + this.text;
            }
            return (this.style & Integer.MIN_VALUE) == 0 ? string : (string != null ? "\u202a" + string : "\u202a" + this.text);
        }
        if ((this.style & Integer.MIN_VALUE) != 0) {
            return "\u202b" + this.text;
        }
        return null;
    }

    @Override
    Rectangle getClientAreaInPixels() {
        this.checkWidget();
        this.forceResize();
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        long l2 = 0L;
        long l3 = OS.GetDC(this.handle);
        long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
        if (l4 != 0L) {
            l2 = OS.SelectObject(l3, l4);
        }
        TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
        OS.GetTextMetrics(l3, tEXTMETRIC);
        if (l4 != 0L) {
            OS.SelectObject(l3, l2);
        }
        OS.ReleaseDC(this.handle, l3);
        int n = OS.IsAppThemed() ? 0 : 1;
        int n2 = 3;
        int n3 = tEXTMETRIC.tmHeight + n;
        int n4 = Math.max(0, rECT.right - 6);
        int n5 = Math.max(0, rECT.bottom - n3 - 3);
        return new Rectangle(3, n3, n4, n5);
    }

    @Override
    String getNameText() {
        return this.getText();
    }

    public String getText() {
        this.checkWidget();
        return this.text;
    }

    @Override
    boolean mnemonicHit(char c) {
        return this.setFocus();
    }

    @Override
    boolean mnemonicMatch(char c) {
        char c2 = this.findMnemonic(this.getText());
        return c2 != '\u0000' && Character.toUpperCase(c) == Character.toUpperCase(c2);
    }

    @Override
    void printWidget(long l2, long l3, GC gC) {
        int n;
        boolean bl = false;
        if (OS.GetDeviceCaps(gC.handle, 2) != 2) {
            n = OS.GetWindowLong(l2, -16);
            if ((n & 0x10000000) == 0) {
                OS.ShowWindow(l2, 5);
            }
            bl = OS.PrintWindow(l2, l3, 0);
            if ((n & 0x10000000) == 0) {
                OS.ShowWindow(l2, 0);
            }
        }
        if (!bl) {
            n = 14;
            OS.SendMessage(l2, 791, l3, 14L);
            int n2 = OS.SaveDC(l3);
            Control[] controlArray = this._getChildren();
            Rectangle rectangle = this.getBoundsInPixels();
            OS.IntersectClipRect(l3, 0, 0, rectangle.width, rectangle.height);
            for (int i = controlArray.length - 1; i >= 0; --i) {
                Point point = controlArray[i].getLocationInPixels();
                int n3 = OS.GetGraphicsMode(l3);
                if (n3 == 2) {
                    float[] fArray = new float[]{1.0f, 0.0f, 0.0f, 1.0f, point.x, point.y};
                    OS.ModifyWorldTransform(l3, fArray, 2);
                } else {
                    OS.SetWindowOrgEx(l3, -point.x, -point.y, null);
                }
                long l4 = controlArray[i].topHandle();
                int n4 = OS.GetWindowLong(l4, -16);
                if ((n4 & 0x10000000) != 0) {
                    controlArray[i].printWidget(l4, l3, gC);
                }
                if (n3 != 2) continue;
                float[] fArray = new float[]{1.0f, 0.0f, 0.0f, 1.0f, -point.x, -point.y};
                OS.ModifyWorldTransform(l3, fArray, 2);
            }
            OS.RestoreDC(l3, n2);
        }
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.text = null;
    }

    @Override
    int resolveTextDirection() {
        return BidiUtil.resolveTextDirection(this.text);
    }

    @Override
    public void setFont(Font font) {
        this.checkWidget();
        Rectangle rectangle = this.getClientAreaInPixels();
        super.setFont(font);
        Rectangle rectangle2 = this.getClientAreaInPixels();
        if (!rectangle.equals(rectangle2)) {
            this.sendResize();
        }
    }

    /*
     * Exception decompiling
     */
    public void setText(String var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl42 : RETURN - null : trying to set 0 previously set to 1
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
    int widgetStyle() {
        return super.widgetStyle() | 7 | 0x2000000 | 0x4000000;
    }

    @Override
    TCHAR windowClass() {
        return GroupClass;
    }

    @Override
    long windowProc() {
        return GroupProc;
    }

    @Override
    LRESULT WM_ERASEBKGND(long l2, long l3) {
        LRESULT lRESULT = super.WM_ERASEBKGND(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        this.drawBackground(l2);
        return LRESULT.ONE;
    }

    @Override
    LRESULT WM_NCHITTEST(long l2, long l3) {
        LRESULT lRESULT = super.WM_NCHITTEST(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        long l4 = this.callWindowProc(this.handle, 132, l2, l3);
        if (l4 == -1L) {
            l4 = 1L;
        }
        return new LRESULT(l4);
    }

    @Override
    LRESULT WM_MOUSEMOVE(long l2, long l3) {
        LRESULT lRESULT = super.WM_MOUSEMOVE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return LRESULT.ZERO;
    }

    @Override
    LRESULT WM_PAINT(long l2, long l3) {
        LRESULT lRESULT = super.WM_PAINT(l2, l3);
        if (this.hasCustomForeground() && this.text.length() != 0) {
            String string = this.fixText(false);
            char[] cArray = (string == null ? this.text : string).toCharArray();
            long l4 = OS.GetDC(this.handle);
            RECT rECT = new RECT();
            OS.GetClientRect(this.handle, rECT);
            RECT rECT2 = rECT;
            rECT2.left += 9;
            long l5 = 0L;
            long l6 = OS.SendMessage(this.handle, 49, 0L, 0L);
            if (l6 != 0L) {
                l5 = OS.SelectObject(l4, l6);
            }
            OS.DrawText(l4, cArray, cArray.length, rECT, 1056);
            RECT rECT3 = rECT;
            rECT3.right += 3;
            this.drawBackground(l4, rECT);
            OS.SetBkMode(l4, 1);
            OS.SetTextColor(l4, this.getForegroundPixel());
            OS.DrawText(l4, cArray, cArray.length, rECT, 32);
            if (l6 != 0L) {
                OS.SelectObject(l4, l5);
            }
            OS.ReleaseDC(this.handle, l4);
            OS.ValidateRect(this.handle, rECT);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_PRINTCLIENT(long l2, long l3) {
        LRESULT lRESULT = super.WM_PRINTCLIENT(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (OS.IsAppThemed()) {
            int n = OS.SaveDC(l2);
            long l4 = this.callWindowProc(this.handle, 792, l2, l3);
            OS.RestoreDC(l2, n);
            return new LRESULT(l4);
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
        if (!bl) {
            if ((this.state & 0x100) != 0 && OS.IsAppThemed()) {
                boolean bl3 = bl = this.findThemeControl() != null;
            }
            if (!bl) {
                boolean bl4 = bl = this.findBackgroundControl() != null;
            }
        }
        if (bl) {
            OS.InvalidateRect(this.handle, null, false);
            long l4 = OS.DefWindowProc(this.handle, 296, l2, l3);
            return new LRESULT(l4);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_WINDOWPOSCHANGING(long l2, long l3) {
        int n;
        LRESULT lRESULT = super.WM_WINDOWPOSCHANGING(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (!OS.IsWindowVisible(this.handle)) {
            return lRESULT;
        }
        WINDOWPOS wINDOWPOS = new WINDOWPOS();
        OS.MoveMemory(wINDOWPOS, l3, WINDOWPOS.sizeof);
        if ((wINDOWPOS.flags & 9) != 0) {
            return lRESULT;
        }
        RECT rECT = new RECT();
        OS.SetRect(rECT, 0, 0, wINDOWPOS.cx, wINDOWPOS.cy);
        OS.SendMessage(this.handle, 131, 0L, rECT);
        int n2 = rECT.right - rECT.left;
        int n3 = rECT.bottom - rECT.top;
        OS.GetClientRect(this.handle, rECT);
        int n4 = rECT.right - rECT.left;
        int n5 = rECT.bottom - rECT.top;
        if (n2 == n4 && n3 == n5) {
            return lRESULT;
        }
        if (n2 != n4) {
            n = n4;
            if (n2 < n) {
                n = n2;
            }
            OS.SetRect(rECT, n - 3, 0, n2, n3);
            OS.InvalidateRect(this.handle, rECT, true);
        }
        if (n3 != n5) {
            n = n5;
            if (n3 < n) {
                n = n3;
            }
            if (n2 < n4) {
                n4 -= 3;
            }
            OS.SetRect(rECT, 0, n - 3, n4, n3);
            OS.InvalidateRect(this.handle, rECT, true);
        }
        return lRESULT;
    }

    static {
        GroupClass = new TCHAR(0, "SWT_GROUP", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        TCHAR tCHAR = new TCHAR(0, "BUTTON", true);
        OS.GetClassInfo(0L, tCHAR, wNDCLASS);
        GroupProc = wNDCLASS.lpfnWndProc;
        long l2 = OS.GetModuleHandle(null);
        if (!OS.GetClassInfo(l2, GroupClass, wNDCLASS)) {
            wNDCLASS.hInstance = l2;
            WNDCLASS wNDCLASS2 = wNDCLASS;
            wNDCLASS2.style &= 0xFFFFFFFC;
            OS.RegisterClass(GroupClass, wNDCLASS);
        }
    }
}

