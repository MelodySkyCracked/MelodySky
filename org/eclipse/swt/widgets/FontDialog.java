/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.win32.CHOOSEFONT;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class FontDialog
extends Dialog {
    FontData fontData;
    RGB rgb;
    boolean effectsVisible = true;

    public FontDialog(Shell shell) {
        this(shell, 65536);
    }

    public FontDialog(Shell shell, int n) {
        super(shell, Dialog.checkStyle(shell, n));
        this.checkSubclass();
    }

    public boolean getEffectsVisible() {
        return this.effectsVisible;
    }

    @Deprecated
    public FontData getFontData() {
        return this.fontData;
    }

    public FontData[] getFontList() {
        if (this.fontData == null) {
            return null;
        }
        FontData[] fontDataArray = new FontData[]{this.fontData};
        return fontDataArray;
    }

    public RGB getRGB() {
        return this.rgb;
    }

    public FontData open() {
        int n;
        Object object;
        long l2 = this.parent.handle;
        long l3 = this.parent.handle;
        boolean bl = false;
        int n2 = this.style & 0x6000000;
        int n3 = this.parent.style & 0x6000000;
        if (n2 != n3) {
            int n4 = 0x100000;
            if (n2 == 0x4000000) {
                n4 |= 0x400000;
            }
            l2 = OS.CreateWindowEx(n4, Shell.DialogClass, null, 0, Integer.MIN_VALUE, 0, Integer.MIN_VALUE, 0, l3, 0L, OS.GetModuleHandle(null), null);
            bl = OS.IsWindowEnabled(l3);
            if (bl) {
                OS.EnableWindow(l3, false);
            }
        }
        long l4 = OS.GetProcessHeap();
        CHOOSEFONT cHOOSEFONT = new CHOOSEFONT();
        cHOOSEFONT.lStructSize = CHOOSEFONT.sizeof;
        cHOOSEFONT.hwndOwner = l2;
        cHOOSEFONT.Flags = 1;
        if (this.effectsVisible) {
            CHOOSEFONT cHOOSEFONT2 = cHOOSEFONT;
            cHOOSEFONT2.Flags |= 0x100;
        }
        long l5 = OS.HeapAlloc(l4, 8, LOGFONT.sizeof);
        if (this.fontData != null && this.fontData.data != null) {
            object = this.fontData.data;
            n = ((LOGFONT)object).lfHeight;
            long l6 = OS.GetDC(0L);
            int n5 = -((int)(0.5f + this.fontData.height * (float)OS.GetDeviceCaps(l6, 90) / 72.0f));
            OS.ReleaseDC(0L, l6);
            ((LOGFONT)object).lfHeight = n5;
            CHOOSEFONT cHOOSEFONT3 = cHOOSEFONT;
            cHOOSEFONT3.Flags |= 0x40;
            OS.MoveMemory(l5, (LOGFONT)object, LOGFONT.sizeof);
            ((LOGFONT)object).lfHeight = n;
        }
        cHOOSEFONT.lpLogFont = l5;
        if (this.rgb != null) {
            int n6 = this.rgb.red & 0xFF;
            n = this.rgb.green << 8 & 0xFF00;
            int n7 = this.rgb.blue << 16 & 0xFF0000;
            cHOOSEFONT.rgbColors = n6 | n | n7;
        }
        object = null;
        Display display = this.parent.getDisplay();
        if ((this.style & 0x30000) != 0) {
            object = display.getModalDialog();
            display.setModalDialog(this);
        }
        display.externalEventLoop = true;
        display.sendPreExternalEventDispatchEvent();
        boolean bl2 = OS.ChooseFont(cHOOSEFONT);
        display.externalEventLoop = false;
        display.sendPostExternalEventDispatchEvent();
        if ((this.style & 0x30000) != 0) {
            display.setModalDialog((Dialog)object);
        }
        if (bl2) {
            LOGFONT lOGFONT = new LOGFONT();
            OS.MoveMemory(lOGFONT, l5, LOGFONT.sizeof);
            long l7 = OS.GetDC(0L);
            int n8 = OS.GetDeviceCaps(l7, 90);
            int n9 = 0;
            if (lOGFONT.lfHeight > 0) {
                long l8 = OS.CreateFontIndirect(lOGFONT);
                long l9 = OS.SelectObject(l7, l8);
                TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
                OS.GetTextMetrics(l7, tEXTMETRIC);
                OS.SelectObject(l7, l9);
                OS.DeleteObject(l8);
                n9 = lOGFONT.lfHeight - tEXTMETRIC.tmInternalLeading;
            } else {
                n9 = -lOGFONT.lfHeight;
            }
            OS.ReleaseDC(0L, l7);
            float f = (float)n9 * 72.0f / (float)n8;
            this.fontData = FontData.win32_new(lOGFONT, f);
            if (this.effectsVisible) {
                int n10 = cHOOSEFONT.rgbColors & 0xFF;
                int n11 = cHOOSEFONT.rgbColors >> 8 & 0xFF;
                int n12 = cHOOSEFONT.rgbColors >> 16 & 0xFF;
                this.rgb = new RGB(n10, n11, n12);
            }
        }
        if (l5 != 0L) {
            OS.HeapFree(l4, 0, l5);
        }
        if (l3 != l2) {
            if (bl) {
                OS.EnableWindow(l3, true);
            }
            OS.SetActiveWindow(l3);
            OS.DestroyWindow(l2);
        }
        if (!bl2) {
            return null;
        }
        return this.fontData;
    }

    public void setEffectsVisible(boolean bl) {
        this.effectsVisible = bl;
    }

    @Deprecated
    public void setFontData(FontData fontData) {
        this.fontData = fontData;
    }

    public void setFontList(FontData[] fontDataArray) {
        this.fontData = fontDataArray != null && fontDataArray.length > 0 ? fontDataArray[0] : null;
    }

    public void setRGB(RGB rGB) {
        this.rgb = rGB;
    }
}

