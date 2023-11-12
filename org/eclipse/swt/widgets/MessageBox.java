/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MessageBox
extends Dialog {
    String message = "";

    public MessageBox(Shell shell) {
        this(shell, 65570);
    }

    public MessageBox(Shell shell, int n) {
        super(shell, Dialog.checkStyle(shell, MessageBox.checkStyle(n)));
        this.checkSubclass();
    }

    static int checkStyle(int n) {
        int n2 = 4064;
        int n3 = n & 0xFE0;
        if (n3 == 32 || n3 == 256 || n3 == 288) {
            return n;
        }
        if (n3 == 64 || n3 == 128 || n3 == 192 || n3 == 448) {
            return n;
        }
        if (n3 == 1280 || n3 == 3584) {
            return n;
        }
        n = n & 0xFFFFF01F | 0x20;
        return n;
    }

    public String getMessage() {
        return this.message;
    }

    public int open() {
        int n = 0;
        if ((this.style & 0x20) == 32) {
            n = 0;
        }
        if ((this.style & 0x120) == 288) {
            n = 1;
        }
        if ((this.style & 0xC0) == 192) {
            n = 4;
        }
        if ((this.style & 0x1C0) == 448) {
            n = 3;
        }
        if ((this.style & 0x500) == 1280) {
            n = 5;
        }
        if ((this.style & 0xE00) == 3584) {
            n = 2;
        }
        if (n == 0) {
            n = 0;
        }
        int n2 = 0;
        if ((this.style & 1) != 0) {
            n2 = 16;
        }
        if ((this.style & 2) != 0) {
            n2 = 64;
        }
        if ((this.style & 4) != 0) {
            n2 = 32;
        }
        if ((this.style & 8) != 0) {
            n2 = 48;
        }
        if ((this.style & 0x10) != 0) {
            n2 = 64;
        }
        int n3 = 0;
        if ((this.style & 0x8000) != 0) {
            n3 = 0;
        }
        if ((this.style & 0x10000) != 0) {
            n3 = 8192;
        }
        if ((this.style & 0x20000) != 0) {
            n3 = 4096;
        }
        int n4 = n | n2 | n3;
        if ((this.style & 0x4000000) != 0) {
            n4 |= 0x180000;
        }
        if ((this.style & 0x6000000) == 0 && this.parent != null && (this.parent.style & 0x8000000) != 0) {
            n4 |= 0x180000;
        }
        if ((n4 & 0x1000) != 0) {
            n4 |= 0x2000;
            n4 &= 0xFFFFEFFF;
            n4 |= 0x40000;
        }
        long l2 = this.parent != null ? this.parent.handle : 0L;
        Display display = this.parent != null ? this.parent.getDisplay() : Display.getCurrent();
        Dialog dialog = null;
        if ((n4 & 0x2000) != 0) {
            dialog = display.getModalDialog();
            display.setModalDialog(this);
        }
        display.sendPreExternalEventDispatchEvent();
        TCHAR tCHAR = new TCHAR(0, this.message, true);
        TCHAR tCHAR2 = new TCHAR(0, this.title, true);
        display.externalEventLoop = true;
        int n5 = OS.MessageBox(l2, tCHAR, tCHAR2, n4);
        display.externalEventLoop = false;
        display.sendPostExternalEventDispatchEvent();
        if ((n4 & 0x2000) != 0) {
            display.setModalDialog(dialog);
        }
        if (n5 != 0) {
            int n6 = n4 & 0xF;
            if (n6 == 0) {
                return 32;
            }
            if (n6 == 1) {
                return n5 == 1 ? 32 : 256;
            }
            if (n6 == 4) {
                return n5 == 6 ? 64 : 128;
            }
            if (n6 == 3) {
                if (n5 == 6) {
                    return 64;
                }
                if (n5 == 7) {
                    return 128;
                }
                return 256;
            }
            if (n6 == 5) {
                return n5 == 4 ? 1024 : 256;
            }
            if (n6 == 2) {
                if (n5 == 4) {
                    return 1024;
                }
                if (n5 == 3) {
                    return 512;
                }
                return 2048;
            }
        }
        return 256;
    }

    public void setMessage(String string) {
        if (string == null) {
            this.error(4);
        }
        this.message = string;
    }
}

