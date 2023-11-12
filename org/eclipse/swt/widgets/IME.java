/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IEnumTfDisplayAttributeInfo;
import org.eclipse.swt.internal.ole.win32.ITfDisplayAttributeInfo;
import org.eclipse.swt.internal.ole.win32.ITfDisplayAttributeProvider;
import org.eclipse.swt.internal.ole.win32.ITfInputProcessorProfiles;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TF_DISPLAYATTRIBUTE;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

public class IME
extends Widget {
    Canvas parent;
    int caretOffset;
    int startOffset;
    int commitCount;
    String text;
    int[] ranges;
    TextStyle[] styles;
    static final int WM_MSIME_MOUSE = OS.RegisterWindowMessage(new TCHAR(0, "MSIMEMouseOperation", true));
    static final int UNDERLINE_IME_DOT = 65536;
    static final int UNDERLINE_IME_DASH = 131072;
    static final int UNDERLINE_IME_THICK = 196608;

    IME() {
    }

    public IME(Canvas canvas, int n) {
        super(canvas, n);
        this.parent = canvas;
        this.createWidget();
    }

    void createWidget() {
        this.text = "";
        this.startOffset = -1;
        if (this.parent.getIME() == null) {
            this.parent.setIME(this);
        }
    }

    public int getCaretOffset() {
        this.checkWidget();
        return this.startOffset + this.caretOffset;
    }

    public int getCommitCount() {
        this.checkWidget();
        return this.commitCount;
    }

    public int getCompositionOffset() {
        this.checkWidget();
        return this.startOffset;
    }

    TF_DISPLAYATTRIBUTE getDisplayAttribute(short s, int n) {
        long[] lArray = new long[]{0L};
        int n2 = COM.CoCreateInstance(COM.CLSID_TF_InputProcessorProfiles, 0L, 1, COM.IID_ITfInputProcessorProfiles, lArray);
        TF_DISPLAYATTRIBUTE tF_DISPLAYATTRIBUTE = null;
        if (n2 == 0) {
            ITfInputProcessorProfiles iTfInputProcessorProfiles = new ITfInputProcessorProfiles(lArray[0]);
            GUID gUID = new GUID();
            GUID gUID2 = new GUID();
            n2 = iTfInputProcessorProfiles.GetDefaultLanguageProfile(s, COM.GUID_TFCAT_TIP_KEYBOARD, gUID, gUID2);
            if (n2 == 0 && (n2 = COM.CoCreateInstance(gUID, 0L, 1, COM.IID_ITfDisplayAttributeProvider, lArray)) == 0) {
                ITfDisplayAttributeProvider iTfDisplayAttributeProvider = new ITfDisplayAttributeProvider(lArray[0]);
                n2 = iTfDisplayAttributeProvider.EnumDisplayAttributeInfo(lArray);
                if (n2 == 0) {
                    IEnumTfDisplayAttributeInfo iEnumTfDisplayAttributeInfo = new IEnumTfDisplayAttributeInfo(lArray[0]);
                    TF_DISPLAYATTRIBUTE tF_DISPLAYATTRIBUTE2 = new TF_DISPLAYATTRIBUTE();
                    while ((n2 = iEnumTfDisplayAttributeInfo.Next(1, lArray, null)) == 0) {
                        ITfDisplayAttributeInfo iTfDisplayAttributeInfo = new ITfDisplayAttributeInfo(lArray[0]);
                        iTfDisplayAttributeInfo.GetAttributeInfo(tF_DISPLAYATTRIBUTE2);
                        iTfDisplayAttributeInfo.Release();
                        if (tF_DISPLAYATTRIBUTE2.bAttr != n) continue;
                        tF_DISPLAYATTRIBUTE = tF_DISPLAYATTRIBUTE2;
                        break;
                    }
                    iEnumTfDisplayAttributeInfo.Release();
                }
                iTfDisplayAttributeProvider.Release();
            }
            iTfInputProcessorProfiles.Release();
        }
        if (tF_DISPLAYATTRIBUTE == null) {
            tF_DISPLAYATTRIBUTE = new TF_DISPLAYATTRIBUTE();
            switch (n) {
                case 0: {
                    tF_DISPLAYATTRIBUTE.lsStyle = 4;
                    break;
                }
                case 1: 
                case 2: {
                    tF_DISPLAYATTRIBUTE.lsStyle = 1;
                    tF_DISPLAYATTRIBUTE.fBoldLine = n == 1;
                }
            }
        }
        return tF_DISPLAYATTRIBUTE;
    }

    public int[] getRanges() {
        this.checkWidget();
        if (this.ranges == null) {
            return new int[0];
        }
        int[] nArray = new int[this.ranges.length];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = this.ranges[i] + this.startOffset;
        }
        return nArray;
    }

    public TextStyle[] getStyles() {
        this.checkWidget();
        if (this.styles == null) {
            return new TextStyle[0];
        }
        TextStyle[] textStyleArray = new TextStyle[this.styles.length];
        System.arraycopy(this.styles, 0, textStyleArray, 0, this.styles.length);
        return textStyleArray;
    }

    public String getText() {
        this.checkWidget();
        return this.text;
    }

    public boolean getWideCaret() {
        this.checkWidget();
        long l2 = OS.GetKeyboardLayout(0);
        short s = (short)OS.LOWORD(l2);
        return OS.PRIMARYLANGID(s) == 18;
    }

    @Override
    void releaseParent() {
        super.releaseParent();
        if (this == this.parent.getIME()) {
            this.parent.setIME(null);
        }
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.parent = null;
        this.text = null;
        this.styles = null;
        this.ranges = null;
    }

    public void setCompositionOffset(int n) {
        this.checkWidget();
        if (n < 0) {
            return;
        }
        if (this.startOffset != -1) {
            this.startOffset = n;
        }
    }

    LRESULT WM_IME_COMPOSITION(long l2, long l3) {
        if (this != false) {
            return null;
        }
        this.ranges = null;
        this.styles = null;
        boolean bl = false;
        this.commitCount = 0;
        this.caretOffset = 0;
        long l4 = this.parent.handle;
        long l5 = OS.ImmGetContext(l4);
        if (l5 != 0L) {
            int n;
            Object object;
            Object object2;
            String string;
            Object object3;
            Object object4;
            int n2;
            char[] cArray = null;
            if ((l3 & 0x800L) != 0L) {
                n2 = OS.ImmGetCompositionString(l5, 2048, (char[])null, 0);
                if (n2 > 0) {
                    cArray = new char[n2 / 2];
                    OS.ImmGetCompositionString(l5, 2048, cArray, n2);
                    if (this.startOffset == -1) {
                        object4 = new Event();
                        ((Event)object4).detail = 3;
                        this.sendEvent(43, (Event)object4);
                        this.startOffset = ((Event)object4).start;
                    }
                    object4 = new Event();
                    ((Event)object4).detail = 1;
                    ((Event)object4).start = this.startOffset;
                    ((Event)object4).end = this.startOffset + this.text.length();
                    object3 = object4;
                    this.text = string = cArray != null ? new String(cArray) : "";
                    ((Event)object3).text = string;
                    this.commitCount = this.text.length();
                    this.sendEvent(43, (Event)object4);
                    String string2 = this.text;
                    this.text = "";
                    this.startOffset = -1;
                    this.commitCount = 0;
                    if (((Event)object4).doit) {
                        Display display = this.display;
                        display.lastKey = 0;
                        object2 = display;
                        object = display;
                        Display display2 = display;
                        n = 0;
                        display2.lastDead = false;
                        ((Display)object).lastNull = false;
                        ((Display)object2).lastVirtual = false;
                        n2 = string2.length();
                        for (int i = 0; i < n2; ++i) {
                            char c = string2.charAt(i);
                            display.lastAscii = c;
                            object4 = new Event();
                            ((Event)object4).character = c;
                            this.parent.sendEvent(1, (Event)object4);
                        }
                    }
                }
                if ((l3 & 8L) == 0L) {
                    return LRESULT.ONE;
                }
            }
            cArray = null;
            if ((l3 & 8L) != 0L) {
                n2 = OS.ImmGetCompositionString(l5, 8, (char[])null, 0);
                if (n2 > 0) {
                    cArray = new char[n2 / 2];
                    OS.ImmGetCompositionString(l5, 8, cArray, n2);
                    if ((l3 & 0x80L) != 0L) {
                        this.caretOffset = OS.ImmGetCompositionString(l5, 128, (char[])null, 0);
                    }
                    object4 = null;
                    if ((l3 & 0x20L) != 0L && (n2 = OS.ImmGetCompositionString(l5, 32, (int[])null, 0)) > 0) {
                        object4 = new int[n2 / 4];
                        OS.ImmGetCompositionString(l5, 32, (int[])object4, n2);
                    }
                    if ((l3 & 0x10L) != 0L && object4 != null && (n2 = OS.ImmGetCompositionString(l5, 16, (byte[])null, 0)) > 0) {
                        object3 = new byte[n2];
                        OS.ImmGetCompositionString(l5, 16, (byte[])object3, n2);
                        n2 = ((Object)object4).length - 1;
                        this.ranges = new int[n2 * 2];
                        this.styles = new TextStyle[n2];
                        long l6 = OS.GetKeyboardLayout(0);
                        short s = (short)OS.LOWORD(l6);
                        object2 = null;
                        object = null;
                        block19: for (int i = 0; i < n2; ++i) {
                            this.ranges[i * 2] = (int)object4[i];
                            this.ranges[i * 2 + 1] = (int)(object4[i + 1] - true);
                            this.styles[i] = new TextStyle();
                            object = this.styles[i];
                            if (object4[i] < 0 || object4[i] >= ((Object)object3).length || (object2 = this.getDisplayAttribute(s, (int)object3[object4[i]])) == null) continue;
                            switch (((TF_DISPLAYATTRIBUTE)object2).crText.type) {
                                case 2: {
                                    ((TextStyle)object).foreground = Color.win32_new(this.display, ((TF_DISPLAYATTRIBUTE)object2).crText.cr);
                                    break;
                                }
                                case 1: {
                                    n = OS.GetSysColor(((TF_DISPLAYATTRIBUTE)object2).crText.cr);
                                    ((TextStyle)object).foreground = Color.win32_new(this.display, n);
                                    break;
                                }
                            }
                            switch (((TF_DISPLAYATTRIBUTE)object2).crBk.type) {
                                case 2: {
                                    ((TextStyle)object).background = Color.win32_new(this.display, ((TF_DISPLAYATTRIBUTE)object2).crBk.cr);
                                    break;
                                }
                                case 1: {
                                    n = OS.GetSysColor(((TF_DISPLAYATTRIBUTE)object2).crBk.cr);
                                    ((TextStyle)object).background = Color.win32_new(this.display, n);
                                    break;
                                }
                            }
                            switch (((TF_DISPLAYATTRIBUTE)object2).crLine.type) {
                                case 2: {
                                    ((TextStyle)object).underlineColor = Color.win32_new(this.display, ((TF_DISPLAYATTRIBUTE)object2).crLine.cr);
                                    break;
                                }
                                case 1: {
                                    n = OS.GetSysColor(((TF_DISPLAYATTRIBUTE)object2).crLine.cr);
                                    ((TextStyle)object).underlineColor = Color.win32_new(this.display, n);
                                    break;
                                }
                            }
                            ((TextStyle)object).underline = ((TF_DISPLAYATTRIBUTE)object2).lsStyle != 0;
                            switch (((TF_DISPLAYATTRIBUTE)object2).lsStyle) {
                                case 4: {
                                    ((TextStyle)object).underlineStyle = 3;
                                    continue block19;
                                }
                                case 3: {
                                    ((TextStyle)object).underlineStyle = 131072;
                                    continue block19;
                                }
                                case 2: {
                                    ((TextStyle)object).underlineStyle = 65536;
                                    continue block19;
                                }
                                case 1: {
                                    ((TextStyle)object).underlineStyle = ((TF_DISPLAYATTRIBUTE)object2).fBoldLine ? 196608 : 0;
                                }
                            }
                        }
                    }
                }
                OS.ImmReleaseContext(l4, l5);
            }
            n2 = this.startOffset + this.text.length();
            if (this.startOffset == -1) {
                object4 = new Event();
                ((Event)object4).detail = 3;
                this.sendEvent(43, (Event)object4);
                this.startOffset = ((Event)object4).start;
                n2 = ((Event)object4).end;
            }
            object4 = new Event();
            ((Event)object4).detail = 1;
            ((Event)object4).start = this.startOffset;
            ((Event)object4).end = n2;
            object3 = object4;
            this.text = string = cArray != null ? new String(cArray) : "";
            ((Event)object3).text = string;
            this.sendEvent(43, (Event)object4);
            if (this.text.length() == 0) {
                this.startOffset = -1;
                this.ranges = null;
                this.styles = null;
            }
        }
        return LRESULT.ONE;
    }

    LRESULT WM_IME_COMPOSITION_START(long l2, long l3) {
        return this != false ? LRESULT.ONE : null;
    }

    LRESULT WM_IME_ENDCOMPOSITION(long l2, long l3) {
        this.startOffset = -1;
        this.caretOffset = 0;
        return this != false ? LRESULT.ONE : null;
    }

    LRESULT WM_KEYDOWN(long l2, long l3) {
        long l4;
        short s;
        if (l2 == 25L && OS.PRIMARYLANGID(s = (short)OS.LOWORD(l4 = OS.GetKeyboardLayout(0))) == 18) {
            Event event = new Event();
            event.detail = 3;
            this.sendEvent(43, event);
            if (event.start == event.end) {
                event.text = null;
                event.end = event.start + 1;
                this.sendEvent(43, event);
            }
            if (event.text != null && event.text.length() > 0) {
                TCHAR tCHAR;
                long l5;
                long l6;
                long l7;
                int n = event.text.length();
                if (n > 1) {
                    event.end = event.start + 1;
                }
                if ((l7 = OS.ImmEscape(l4, l6 = OS.ImmGetContext(l5 = this.parent.handle), 4104, tCHAR = new TCHAR(0, event.text, true))) != 0L) {
                    this.sendEvent(43, event);
                }
            }
        }
        return null;
    }

    LRESULT WM_KILLFOCUS(long l2, long l3) {
        if (this != false) {
            return null;
        }
        long l4 = this.parent.handle;
        long l5 = OS.ImmGetContext(l4);
        if (l5 != 0L) {
            if (OS.ImmGetOpenStatus(l5)) {
                OS.ImmNotifyIME(l5, 21, 1, 0);
            }
            OS.ImmReleaseContext(l4, l5);
        }
        return null;
    }

    LRESULT WM_LBUTTONDOWN(long l2, long l3) {
        if (this != false) {
            return null;
        }
        long l4 = this.parent.handle;
        long l5 = OS.ImmGetContext(l4);
        if (l5 != 0L) {
            if (OS.ImmGetOpenStatus(l5) && OS.ImmGetCompositionString(l5, 8, (char[])null, 0) > 0) {
                Event event = new Event();
                event.detail = 2;
                event.setLocationInPixels(OS.GET_X_LPARAM(l3), OS.GET_Y_LPARAM(l3));
                this.sendEvent(43, event);
                int n = event.index;
                int n2 = this.text.length();
                if (n != -1 && this.startOffset != -1 && this.startOffset <= n && n < this.startOffset + n2) {
                    long l6 = OS.ImmGetDefaultIMEWnd(l4);
                    n = event.index + event.count - this.startOffset;
                    int n3 = event.count > 0 ? 1 : 2;
                    long l7 = OS.MAKEWPARAM(OS.MAKEWORD(1, n3), n);
                    OS.SendMessage(l6, WM_MSIME_MOUSE, l7, l5);
                } else {
                    OS.ImmNotifyIME(l5, 21, 1, 0);
                }
            }
            OS.ImmReleaseContext(l4, l5);
        }
        return null;
    }
}

