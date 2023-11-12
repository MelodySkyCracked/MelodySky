/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.printing;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.win32.DEVMODE;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PRINTDLG;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PrintDialog
extends Dialog {
    static final TCHAR DialogClass = new TCHAR(0, "#32770", true);
    PrinterData printerData = new PrinterData();

    public PrintDialog(Shell shell) {
        this(shell, 32768);
    }

    public PrintDialog(Shell shell, int n) {
        super(shell, PrintDialog.checkStyle(shell, n));
        this.checkSubclass();
    }

    static int checkBits(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8 = n2 | n3 | n4 | n5 | n6 | n7;
        if ((n & n8) == 0) {
            n |= n2;
        }
        if ((n & n2) != 0) {
            n = n & ~n8 | n2;
        }
        if ((n & n3) != 0) {
            n = n & ~n8 | n3;
        }
        if ((n & n4) != 0) {
            n = n & ~n8 | n4;
        }
        if ((n & n5) != 0) {
            n = n & ~n8 | n5;
        }
        if ((n & n6) != 0) {
            n = n & ~n8 | n6;
        }
        if ((n & n7) != 0) {
            n = n & ~n8 | n7;
        }
        return n;
    }

    static int checkStyle(Shell shell, int n) {
        int n2 = 229376;
        if ((n & 0x10000000) != 0 && ((n &= 0xEFFFFFFF) & 0x38000) == 0) {
            n |= shell == null ? 65536 : 32768;
        }
        if ((n & 0x38000) == 0) {
            n |= 0x10000;
        }
        if (((n &= 0xF7FFFFFF) & 0x6000000) == 0 && shell != null) {
            if ((shell.getStyle() & 0x2000000) != 0) {
                n |= 0x2000000;
            }
            if ((shell.getStyle() & 0x4000000) != 0) {
                n |= 0x4000000;
            }
        }
        return PrintDialog.checkBits(n, 0x2000000, 0x4000000, 0, 0, 0, 0);
    }

    public void setPrinterData(PrinterData printerData) {
        if (printerData == null) {
            printerData = new PrinterData();
        }
        this.printerData = printerData;
    }

    public PrinterData getPrinterData() {
        return this.printerData;
    }

    public int getScope() {
        return this.printerData.scope;
    }

    public void setScope(int n) {
        this.printerData.scope = n;
    }

    public int getStartPage() {
        return this.printerData.startPage;
    }

    public void setStartPage(int n) {
        this.printerData.startPage = n;
    }

    public int getEndPage() {
        return this.printerData.endPage;
    }

    public void setEndPage(int n) {
        this.printerData.endPage = n;
    }

    public boolean getPrintToFile() {
        return this.printerData.printToFile;
    }

    public void setPrintToFile(boolean bl) {
        this.printerData.printToFile = bl;
    }

    @Override
    protected void checkSubclass() {
        String string = this.getClass().getName();
        String string2 = PrintDialog.class.getName();
        if (!string2.equals(string)) {
            SWT.error(43);
        }
    }

    public PrinterData open() {
        Object object;
        Shell shell = this.getParent();
        int n = this.getStyle();
        long l2 = shell.handle;
        long l3 = shell.handle;
        boolean bl = false;
        int n2 = n & 0x6000000;
        int n3 = shell.getStyle() & 0x6000000;
        if (n2 != n3) {
            int n4 = 0x100000;
            if (n2 == 0x4000000) {
                n4 |= 0x400000;
            }
            l2 = OS.CreateWindowEx(n4, DialogClass, null, 0, Integer.MIN_VALUE, 0, Integer.MIN_VALUE, 0, l3, 0L, OS.GetModuleHandle(null), null);
            bl = OS.IsWindowEnabled(l3);
            if (bl) {
                OS.EnableWindow(l3, false);
            }
        }
        PrinterData printerData = null;
        Shell[] shellArray = new PRINTDLG();
        shellArray.lStructSize = PRINTDLG.sizeof;
        shellArray.hwndOwner = l2;
        boolean bl2 = false;
        if (this.printerData.name != null) {
            for (PrinterData printerData2 : Printer.getPrinterList()) {
                if (!printerData2.name.equals(this.printerData.name)) continue;
                bl2 = true;
                break;
            }
            if (bl2) {
                object = new TCHAR(0, this.printerData.name, true);
                int n5 = ((TCHAR)object).length() * 2;
                short[] sArray = new short[4];
                int n6 = sArray.length * 2;
                sArray[1] = (short)sArray.length;
                long l4 = OS.GlobalAlloc(66, n6 + n5);
                long l5 = OS.GlobalLock(l4);
                OS.MoveMemory(l5, sArray, n6);
                OS.MoveMemory(l5 + (long)n6, (TCHAR)object, n5);
                OS.GlobalUnlock(l4);
                shellArray.hDevNames = l4;
            }
        }
        object = shell.getDisplay();
        String string = "org.eclipse.swt.internal.win32.externalEventLoop";
        if (!bl2) {
            shellArray.Flags = 1024;
            ((Display)object).setData("org.eclipse.swt.internal.win32.externalEventLoop", Boolean.TRUE);
            ((Display)object).sendPreExternalEventDispatchEvent();
            bl2 = OS.PrintDlg((PRINTDLG)shellArray);
            ((Display)object).setData("org.eclipse.swt.internal.win32.externalEventLoop", Boolean.FALSE);
            ((Display)object).sendPostExternalEventDispatchEvent();
            if (bl2 && shellArray.hDevNames != 0L) {
                OS.GlobalFree(shellArray.hDevNames);
                shellArray.hDevNames = 0L;
            }
        }
        if (bl2) {
            Shell[] shellArray2;
            long l6;
            long l7;
            byte[] byArray = this.printerData.otherData;
            if (byArray != null && byArray.length != 0) {
                long l8 = OS.GlobalAlloc(66, byArray.length);
                l7 = OS.GlobalLock(l8);
                OS.MoveMemory(l7, byArray, byArray.length);
                OS.GlobalUnlock(l8);
                if (shellArray.hDevMode != 0L) {
                    OS.GlobalFree(shellArray.hDevMode);
                }
                shellArray.hDevMode = l8;
            }
            if ((l6 = shellArray.hDevMode) == 0L) {
                shellArray.hDevMode = l6 = OS.GlobalAlloc(66, DEVMODE.sizeof);
            }
            l7 = OS.GlobalLock(l6);
            DEVMODE dEVMODE = new DEVMODE();
            OS.MoveMemory(dEVMODE, l7, DEVMODE.sizeof);
            if (this.printerData.name != null) {
                int n7 = Math.min(this.printerData.name.length(), 31);
                for (int i = 0; i < n7; ++i) {
                    dEVMODE.dmDeviceName[i] = this.printerData.name.charAt(i);
                }
            }
            DEVMODE dEVMODE2 = dEVMODE;
            dEVMODE2.dmFields |= 1;
            dEVMODE.dmOrientation = (short)(this.printerData.orientation == 1 ? 1 : 2);
            if (this.printerData.copyCount != 1) {
                DEVMODE dEVMODE3 = dEVMODE;
                dEVMODE3.dmFields |= 0x100;
                dEVMODE.dmCopies = (short)this.printerData.copyCount;
            }
            if (this.printerData.collate) {
                DEVMODE dEVMODE4 = dEVMODE;
                dEVMODE4.dmFields |= 0x8000;
                dEVMODE.dmCollate = 1;
            }
            if (this.printerData.duplex != -1) {
                DEVMODE dEVMODE5 = dEVMODE;
                dEVMODE5.dmFields |= 0x1000;
                switch (this.printerData.duplex) {
                    case 2: {
                        dEVMODE.dmDuplex = (short)3;
                        break;
                    }
                    case 1: {
                        dEVMODE.dmDuplex = (short)2;
                        break;
                    }
                    default: {
                        dEVMODE.dmDuplex = 1;
                    }
                }
            }
            OS.MoveMemory(l7, dEVMODE, DEVMODE.sizeof);
            OS.GlobalUnlock(l6);
            shellArray.Flags = 262144;
            if (this.printerData.printToFile) {
                Shell[] shellArray3 = shellArray;
                shellArray3.Flags |= 0x20;
            }
            switch (this.printerData.scope) {
                case 1: {
                    shellArray2 = shellArray;
                    shellArray2.Flags |= 2;
                    break;
                }
                case 2: {
                    shellArray2 = shellArray;
                    shellArray2.Flags |= 1;
                    break;
                }
                default: {
                    shellArray2 = shellArray;
                    shellArray2.Flags |= 0;
                    break;
                }
            }
            shellArray.nMinPage = 1;
            shellArray.nMaxPage = (short)-1;
            shellArray.nFromPage = (short)Math.min(65535, Math.max(1, this.printerData.startPage));
            shellArray.nToPage = (short)Math.min(65535, Math.max(1, this.printerData.endPage));
            shellArray2 = ((Display)object).getShells();
            if ((this.getStyle() & 0x30000) != 0) {
                for (int i = 0; i < shellArray2.length; ++i) {
                    if (shellArray2[i].isEnabled() && shellArray2[i] != shell) {
                        shellArray2[i].setEnabled(false);
                        continue;
                    }
                    shellArray2[i] = null;
                }
            }
            String string2 = "org.eclipse.swt.internal.win32.runMessagesInIdle";
            Object object2 = ((Display)object).getData("org.eclipse.swt.internal.win32.runMessagesInIdle");
            ((Display)object).setData("org.eclipse.swt.internal.win32.runMessagesInIdle", Boolean.TRUE);
            ((Display)object).setData("org.eclipse.swt.internal.win32.externalEventLoop", Boolean.TRUE);
            ((Display)object).sendPreExternalEventDispatchEvent();
            bl2 = OS.PrintDlg((PRINTDLG)shellArray);
            ((Display)object).setData("org.eclipse.swt.internal.win32.externalEventLoop", Boolean.FALSE);
            ((Display)object).sendPostExternalEventDispatchEvent();
            ((Display)object).setData("org.eclipse.swt.internal.win32.runMessagesInIdle", object2);
            if ((this.getStyle() & 0x30000) != 0) {
                for (Shell shell2 : shellArray2) {
                    if (shell2 == null || shell2.isDisposed()) continue;
                    shell2.setEnabled(true);
                }
            }
            if (bl2) {
                short s;
                l6 = shellArray.hDevNames;
                int n8 = OS.GlobalSize(l6) / 2 * 2;
                l7 = OS.GlobalLock(l6);
                short[] sArray = new short[4];
                OS.MoveMemory(sArray, l7, 2 * sArray.length);
                char[] cArray = new char[n8];
                OS.MoveMemory(cArray, l7, n8);
                OS.GlobalUnlock(l6);
                short s2 = sArray[0];
                int n9 = 0;
                while (s2 + n9 < n8 && cArray[s2 + n9] != '\u0000') {
                    ++n9;
                }
                String string3 = new String(cArray, (int)s2, n9);
                short s3 = sArray[1];
                n9 = 0;
                while (s3 + n9 < n8 && cArray[s3 + n9] != '\u0000') {
                    ++n9;
                }
                String string4 = new String(cArray, (int)s3, n9);
                printerData = new PrinterData(string3, string4);
                if ((shellArray.Flags & 2) != 0) {
                    printerData.scope = 1;
                    printerData.startPage = shellArray.nFromPage & 0xFFFF;
                    printerData.endPage = shellArray.nToPage & 0xFFFF;
                } else if ((shellArray.Flags & 1) != 0) {
                    printerData.scope = 2;
                }
                boolean bl3 = printerData.printToFile = (shellArray.Flags & 0x20) != 0;
                if (printerData.printToFile) {
                    printerData.fileName = this.printerData.fileName;
                }
                printerData.copyCount = shellArray.nCopies;
                printerData.collate = (shellArray.Flags & 0x10) != 0;
                l6 = shellArray.hDevMode;
                n8 = OS.GlobalSize(l6);
                l7 = OS.GlobalLock(l6);
                printerData.otherData = new byte[n8];
                OS.MoveMemory(printerData.otherData, l7, n8);
                dEVMODE = new DEVMODE();
                OS.MoveMemory(dEVMODE, l7, DEVMODE.sizeof);
                if ((dEVMODE.dmFields & 1) != 0) {
                    s = dEVMODE.dmOrientation;
                    int n10 = printerData.orientation = s == 2 ? 2 : 1;
                }
                if ((dEVMODE.dmFields & 0x1000) != 0) {
                    s = dEVMODE.dmDuplex;
                    printerData.duplex = s == 1 ? 0 : (s == 3 ? 2 : 1);
                }
                OS.GlobalUnlock(l6);
                this.printerData = printerData;
            }
        }
        if (shellArray.hDevNames != 0L) {
            OS.GlobalFree(shellArray.hDevNames);
            shellArray.hDevNames = 0L;
        }
        if (shellArray.hDevMode != 0L) {
            OS.GlobalFree(shellArray.hDevMode);
            shellArray.hDevMode = 0L;
        }
        if (l3 != l2) {
            if (bl) {
                OS.EnableWindow(l3, true);
            }
            OS.SetActiveWindow(l3);
            OS.DestroyWindow(l2);
        }
        return printerData;
    }
}

