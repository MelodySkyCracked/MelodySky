/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.CHOOSECOLOR;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ColorDialog
extends Dialog {
    static final int CUSTOM_COLOR_COUNT = 16;
    RGB rgb;
    RGB[] rgbs;
    int[] colors = new int[16];

    public ColorDialog(Shell shell) {
        this(shell, 65536);
    }

    public ColorDialog(Shell shell, int n) {
        super(shell, Dialog.checkStyle(shell, n));
        this.checkSubclass();
    }

    long CCHookProc(long l2, long l3, long l4, long l5) {
        switch ((int)l3) {
            case 272: {
                if (this.title == null || this.title.length() == 0) break;
                TCHAR tCHAR = new TCHAR(0, this.title, true);
                OS.SetWindowText(l2, tCHAR);
                break;
            }
        }
        return 0L;
    }

    public RGB getRGB() {
        return this.rgb;
    }

    public RGB[] getRGBs() {
        return this.rgbs;
    }

    public RGB open() {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        long l2 = this.parent.handle;
        long l3 = this.parent.handle;
        boolean bl = false;
        int n6 = this.style & 0x6000000;
        int n7 = this.parent.style & 0x6000000;
        if (n6 != n7) {
            int n8 = 0x100000;
            if (n6 == 0x4000000) {
                n8 |= 0x400000;
            }
            l2 = OS.CreateWindowEx(n8, Shell.DialogClass, null, 0, Integer.MIN_VALUE, 0, Integer.MIN_VALUE, 0, l3, 0L, OS.GetModuleHandle(null), null);
            bl = OS.IsWindowEnabled(l3);
            if (bl) {
                OS.EnableWindow(l3, false);
            }
        }
        Callback callback = new Callback(this, "CCHookProc", 4);
        long l4 = callback.getAddress();
        Display display = this.parent.display;
        if (display.lpCustColors == 0L) {
            long l5 = OS.GetProcessHeap();
            display.lpCustColors = OS.HeapAlloc(l5, 8, 64);
            for (n5 = 0; n5 < 16; ++n5) {
                this.colors[n5] = 0xFFFFFF;
            }
            OS.MoveMemory(display.lpCustColors, this.colors, 64);
        }
        if (this.rgbs != null) {
            int n9;
            int n10 = this.rgbs.length > 16 ? 16 : this.rgbs.length;
            for (n9 = 0; n9 < n10; ++n9) {
                RGB rGB = this.rgbs[n9];
                n4 = rGB.red & 0xFF;
                n3 = rGB.green << 8 & 0xFF00;
                n2 = rGB.blue << 16 & 0xFF0000;
                this.colors[n9] = n4 | n3 | n2;
            }
            for (n9 = n10; n9 < 16; ++n9) {
                this.colors[n9] = 0xFFFFFF;
            }
            OS.MoveMemory(display.lpCustColors, this.colors, 64);
        }
        CHOOSECOLOR cHOOSECOLOR = new CHOOSECOLOR();
        cHOOSECOLOR.lStructSize = CHOOSECOLOR.sizeof;
        cHOOSECOLOR.Flags = 272;
        cHOOSECOLOR.lpfnHook = l4;
        cHOOSECOLOR.hwndOwner = l2;
        cHOOSECOLOR.lpCustColors = display.lpCustColors;
        if (this.rgb != null) {
            CHOOSECOLOR cHOOSECOLOR2 = cHOOSECOLOR;
            cHOOSECOLOR2.Flags |= 1;
            n5 = this.rgb.red & 0xFF;
            n4 = this.rgb.green << 8 & 0xFF00;
            n3 = this.rgb.blue << 16 & 0xFF0000;
            cHOOSECOLOR.rgbResult = n5 | n4 | n3;
        }
        Dialog dialog = null;
        if ((this.style & 0x30000) != 0) {
            dialog = display.getModalDialog();
            display.setModalDialog(this);
        }
        display.externalEventLoop = true;
        display.sendPreExternalEventDispatchEvent();
        n5 = OS.ChooseColor(cHOOSECOLOR) ? 1 : 0;
        display.externalEventLoop = false;
        display.sendPostExternalEventDispatchEvent();
        if ((this.style & 0x30000) != 0) {
            display.setModalDialog(dialog);
        }
        n4 = 0;
        OS.MoveMemory(this.colors, display.lpCustColors, this.colors.length * 4);
        for (int n11 : this.colors) {
            if (n11 == 0xFFFFFF) continue;
            n4 = 1;
            break;
        }
        if (n4 != 0) {
            this.rgbs = new RGB[16];
            for (int i = 0; i < this.colors.length; ++i) {
                int n11;
                n2 = this.colors[i];
                n = n2 & 0xFF;
                n11 = n2 >> 8 & 0xFF;
                int n12 = n2 >> 16 & 0xFF;
                this.rgbs[i] = new RGB(n, n11, n12);
            }
        }
        if (n5 != 0) {
            int n13 = cHOOSECOLOR.rgbResult & 0xFF;
            n2 = cHOOSECOLOR.rgbResult >> 8 & 0xFF;
            n = cHOOSECOLOR.rgbResult >> 16 & 0xFF;
            this.rgb = new RGB(n13, n2, n);
        }
        callback.dispose();
        if (l3 != l2) {
            if (bl) {
                OS.EnableWindow(l3, true);
            }
            OS.SetActiveWindow(l3);
            OS.DestroyWindow(l2);
        }
        if (n5 == 0) {
            return null;
        }
        return this.rgb;
    }

    public void setRGB(RGB rGB) {
        this.rgb = rGB;
    }

    public void setRGBs(RGB[] rGBArray) {
        if (rGBArray != null) {
            for (RGB rGB : rGBArray) {
                if (rGB != null) continue;
                this.error(5);
            }
        }
        this.rgbs = rGBArray;
    }
}

