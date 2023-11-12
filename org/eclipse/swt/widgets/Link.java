/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import java.util.Arrays;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.BidiUtil;
import org.eclipse.swt.internal.win32.LITEM;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.NMCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMLINK;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TypedListener;

public class Link
extends Control {
    String text;
    int linkForeground = -1;
    String[] ids;
    char[] mnemonics;
    int nextFocusItem = -1;
    static final long LinkProc;
    static final TCHAR LinkClass;

    public Link(Composite composite, int n) {
        super(composite, n);
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
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        switch (n) {
            case 15: {
                if (l3 == 0L) break;
                OS.SendMessage(l2, 792, l3, 0L);
                return 0L;
            }
        }
        return OS.CallWindowProc(LinkProc, l2, n, l3, l4);
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        int n3;
        int n4;
        this.checkWidget();
        if (this.text.isEmpty()) {
            long l2 = OS.GetDC(this.handle);
            long l3 = OS.SendMessage(this.handle, 49, 0L, 0L);
            long l4 = OS.SelectObject(l2, l3);
            TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
            OS.GetTextMetrics(l2, tEXTMETRIC);
            n4 = 0;
            n3 = tEXTMETRIC.tmHeight;
            if (l3 != 0L) {
                OS.SelectObject(l2, l4);
            }
            OS.ReleaseDC(this.handle, l2);
        } else {
            SIZE sIZE = new SIZE();
            int n5 = n == -1 ? Integer.MAX_VALUE : n;
            OS.SendMessage(this.handle, 1793, (long)n5, sIZE);
            n4 = sIZE.cx;
            n3 = sIZE.cy;
        }
        if (n != -1) {
            n4 = n;
        }
        if (n2 != -1) {
            n3 = n2;
        }
        int n6 = this.getBorderWidthInPixels();
        return new Point(n4 += n6 * 2, n3 += n6 * 2);
    }

    @Override
    void createHandle() {
        super.createHandle();
        this.state |= 0x100;
    }

    @Override
    void createWidget() {
        super.createWidget();
        this.text = "";
        this.ids = new String[0];
        this.mnemonics = new char[0];
    }

    @Override
    void enableWidget(boolean bl) {
        super.enableWidget(bl);
        OS.InvalidateRect(this.handle, null, true);
    }

    int getFocusItem() {
        LITEM lITEM = new LITEM();
        lITEM.mask = 3;
        lITEM.stateMask = 1;
        while (OS.SendMessage(this.handle, 1795, 0L, lITEM) != 0L) {
            if ((lITEM.state & 1) != 0) {
                return lITEM.iLink;
            }
            LITEM lITEM2 = lITEM;
            ++lITEM2.iLink;
        }
        return -1;
    }

    public Color getLinkForeground() {
        this.checkWidget();
        if (this.linkForeground != -1) {
            return Color.win32_new(this.display, this.linkForeground);
        }
        return Color.win32_new(this.display, OS.GetSysColor(26));
    }

    @Override
    String getNameText() {
        return this.getText();
    }

    public String getText() {
        this.checkWidget();
        return this.text;
    }

    /*
     * Exception decompiling
     */
    @Override
    boolean mnemonicHit(char var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl28 : ICONST_0 - null : trying to set 0 previously set to 1
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
    boolean mnemonicMatch(char c) {
        char c2 = Character.toUpperCase(c);
        for (char c3 : this.mnemonics) {
            if (c2 != c3) continue;
            return true;
        }
        return false;
    }

    void parse(String string) {
        int n = string.length();
        this.ids = new String[n / 7];
        this.mnemonics = new char[n / 7];
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        char c = '\u0000';
        block23: for (int i = 0; i < n; ++i) {
            char c2 = Character.toLowerCase(string.charAt(i));
            switch (n2) {
                case 0: {
                    if (c2 == '<') {
                        ++n2;
                        continue block23;
                    }
                    if (c2 != '&') continue block23;
                    n2 = 16;
                    continue block23;
                }
                case 1: {
                    if (c2 != 'a') continue block23;
                    ++n2;
                    continue block23;
                }
                case 2: {
                    switch (c2) {
                        case 'h': {
                            n2 = 7;
                            continue block23;
                        }
                        case '>': {
                            n4 = i + 1;
                            ++n2;
                            continue block23;
                        }
                    }
                    if (Character.isWhitespace(c2)) continue block23;
                    n2 = 13;
                    continue block23;
                }
                case 3: {
                    if (c2 != '<') continue block23;
                    n5 = i;
                    ++n2;
                    continue block23;
                }
                case 4: {
                    n2 = c2 == '/' ? n2 + 1 : 3;
                    continue block23;
                }
                case 5: {
                    n2 = c2 == 'a' ? n2 + 1 : 3;
                    continue block23;
                }
                case 6: {
                    if (c2 == '>') {
                        if (n6 == 0) {
                            n6 = n4;
                            n7 = n5;
                        }
                        this.ids[n3] = string.substring(n6, n7);
                        if (c != '\u0000') {
                            this.mnemonics[n3] = c;
                        }
                        ++n3;
                        c = '\u0000';
                        n7 = '\u0000';
                        n6 = '\u0000';
                        n4 = '\u0000';
                        n5 = '\u0000';
                        n2 = 0;
                        continue block23;
                    }
                    n2 = 3;
                    continue block23;
                }
                case 7: {
                    n2 = c2 == 'r' ? n2 + 1 : 0;
                    continue block23;
                }
                case 8: {
                    n2 = c2 == 'e' ? n2 + 1 : 0;
                    continue block23;
                }
                case 9: {
                    n2 = c2 == 'f' ? n2 + 1 : 0;
                    continue block23;
                }
                case 10: {
                    n2 = c2 == '=' ? n2 + 1 : 0;
                    continue block23;
                }
                case 11: {
                    if (c2 == '\"') {
                        ++n2;
                        n6 = i + 1;
                        continue block23;
                    }
                    n2 = 0;
                    continue block23;
                }
                case 12: {
                    if (c2 != '\"') continue block23;
                    n7 = i;
                    n2 = 2;
                    continue block23;
                }
                case 13: {
                    if (Character.isWhitespace(c2)) {
                        n2 = 0;
                        continue block23;
                    }
                    if (c2 != '=') continue block23;
                    ++n2;
                    continue block23;
                }
                case 14: {
                    n2 = c2 == '\"' ? n2 + 1 : 0;
                    continue block23;
                }
                case 15: {
                    if (c2 != '\"') continue block23;
                    n2 = 2;
                    continue block23;
                }
                case 16: {
                    if (c2 == '<') {
                        n2 = 1;
                        continue block23;
                    }
                    n2 = 0;
                    if (c2 == '&') continue block23;
                    c = Character.toUpperCase(c2);
                    continue block23;
                }
                default: {
                    n2 = 0;
                }
            }
        }
        this.ids = Arrays.copyOf(this.ids, n3);
        this.mnemonics = Arrays.copyOf(this.mnemonics, n3);
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.ids = null;
        this.mnemonics = null;
        this.text = null;
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

    public void setLinkForeground(Color color) {
        this.checkWidget();
        int n = -1;
        if (color != null) {
            if (color.isDisposed()) {
                this.error(5);
            }
            n = color.handle;
        }
        if (n == this.linkForeground) {
            return;
        }
        this.linkForeground = n;
        OS.InvalidateRect(this.handle, null, true);
    }

    public void setText(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if (string.equals(this.text)) {
            return;
        }
        this.text = string;
        if ((this.state & 0x400000) != 0) {
            this.updateTextDirection(0x6000000);
        }
        TCHAR tCHAR = new TCHAR(this.getCodePage(), string, true);
        OS.SetWindowText(this.handle, tCHAR);
        this.parse(string);
    }

    @Override
    int resolveTextDirection() {
        return BidiUtil.resolveTextDirection(this.text);
    }

    @Override
    boolean updateTextDirection(int n) {
        if (super.updateTextDirection(n)) {
            int n2 = 0x6000000;
            this.style &= 0xF7FFFFFF;
            this.style &= 0xF9FFFFFF;
            this.style |= n & 0x6000000;
            this.updateOrientation();
            this.checkMirrored();
            return true;
        }
        return false;
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle();
        return n | 0x10000;
    }

    @Override
    TCHAR windowClass() {
        return LinkClass;
    }

    @Override
    long windowProc() {
        return LinkProc;
    }

    @Override
    LRESULT WM_CHAR(long l2, long l3) {
        LRESULT lRESULT = super.WM_CHAR(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        switch ((int)l2) {
            case 9: 
            case 13: 
            case 32: {
                long l4 = this.callWindowProc(this.handle, 256, l2, l3);
                return new LRESULT(l4);
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_GETDLGCODE(long l2, long l3) {
        long l4 = this.callWindowProc(this.handle, 135, l2, l3);
        int n = this.ids.length;
        if (n == 0) {
            l4 |= 0x100L;
        } else if (n > 1) {
            int n2;
            int n3 = n2 = OS.GetKeyState(16) < 0 ? 0 : n - 1;
            if (this.getFocusItem() != n2) {
                l4 |= 2L;
            }
        }
        return new LRESULT(l4);
    }

    @Override
    LRESULT WM_KEYDOWN(long l2, long l3) {
        LRESULT lRESULT = super.WM_KEYDOWN(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        switch ((int)l2) {
            case 9: 
            case 13: 
            case 32: {
                return LRESULT.ZERO;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_KILLFOCUS(long l2, long l3) {
        this.nextFocusItem = this.getFocusItem();
        return super.WM_KILLFOCUS(l2, l3);
    }

    @Override
    LRESULT WM_NCHITTEST(long l2, long l3) {
        LRESULT lRESULT = super.WM_NCHITTEST(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return new LRESULT(1L);
    }

    @Override
    LRESULT WM_SETCURSOR(long l2, long l3) {
        LRESULT lRESULT = super.WM_SETCURSOR(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        long l4 = this.callWindowProc(this.handle, 32, l2, l3);
        if (l4 == 0L) {
            OS.DefWindowProc(this.handle, 32, l2, l3);
        }
        return LRESULT.ONE;
    }

    @Override
    LRESULT WM_SETFOCUS(long l2, long l3) {
        if (this.ids.length > 1) {
            if (OS.GetKeyState(9) < 0) {
                if (OS.GetKeyState(16) < 0) {
                    this.setFocusItem(this.ids.length - 1);
                }
            } else if (this.nextFocusItem > 0) {
                this.setFocusItem(this.nextFocusItem);
            }
        }
        return super.WM_SETFOCUS(l2, l3);
    }

    @Override
    LRESULT wmNotifyChild(NMHDR nMHDR, long l2, long l3) {
        block0 : switch (nMHDR.code) {
            case -4: 
            case -2: {
                NMLINK nMLINK = new NMLINK();
                OS.MoveMemory(nMLINK, l3, NMLINK.sizeof);
                Event event = new Event();
                event.text = this.ids[nMLINK.iLink];
                this.sendSelectionEvent(13, event, true);
                break;
            }
            case -12: {
                NMCUSTOMDRAW nMCUSTOMDRAW = new NMCUSTOMDRAW();
                OS.MoveMemory(nMCUSTOMDRAW, l3, NMCUSTOMDRAW.sizeof);
                switch (nMCUSTOMDRAW.dwDrawStage) {
                    case 1: {
                        if (OS.IsWindowEnabled(this.handle) && this.linkForeground == -1) break block0;
                        return new LRESULT(32L);
                    }
                    case 65537: {
                        if (!OS.IsWindowEnabled(this.handle)) {
                            OS.SetTextColor(nMCUSTOMDRAW.hdc, OS.GetSysColor(17));
                            break block0;
                        }
                        if (this.linkForeground == -1 || nMCUSTOMDRAW.dwItemSpec == -1L) break block0;
                        OS.SetTextColor(nMCUSTOMDRAW.hdc, this.linkForeground);
                        break block0;
                    }
                }
                break;
            }
        }
        return super.wmNotifyChild(nMHDR, l2, l3);
    }

    static {
        LinkClass = new TCHAR(0, "SysLink", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, LinkClass, wNDCLASS);
        LinkProc = wNDCLASS.lpfnWndProc;
        wNDCLASS.hInstance = OS.GetModuleHandle(null);
        WNDCLASS wNDCLASS2 = wNDCLASS;
        wNDCLASS2.style &= 0xFFFFBFFF;
        WNDCLASS wNDCLASS3 = wNDCLASS;
        wNDCLASS3.style |= 8;
        OS.RegisterClass(LinkClass, wNDCLASS);
    }
}

