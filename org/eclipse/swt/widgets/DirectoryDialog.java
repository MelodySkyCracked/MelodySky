/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IFileDialog;
import org.eclipse.swt.internal.ole.win32.IShellItem;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DirectoryDialog
extends Dialog {
    String message = "";
    String filterPath = "";
    String directoryPath;

    public DirectoryDialog(Shell shell) {
        this(shell, 65536);
    }

    public DirectoryDialog(Shell shell, int n) {
        super(shell, Dialog.checkStyle(shell, n));
        this.checkSubclass();
    }

    public String getFilterPath() {
        return this.filterPath;
    }

    public String getMessage() {
        return this.message;
    }

    public String open() {
        this.directoryPath = null;
        long[] lArray = new long[]{0L};
        if (COM.CoCreateInstance(COM.CLSID_FileOpenDialog, 0L, 1, COM.IID_IFileOpenDialog, lArray) == 0) {
            Object object;
            IFileDialog iFileDialog = new IFileDialog(lArray[0]);
            int[] nArray = new int[]{0};
            if (iFileDialog.GetOptions(nArray) == 0) {
                object = nArray;
                boolean bl = false;
                object[0] = object[0] | 0x68;
                iFileDialog.SetOptions(nArray[0]);
            }
            if (this.title == null) {
                this.title = "";
            }
            if (this.title.length() > 0) {
                object = new char[this.title.length() + 1];
                this.title.getChars(0, this.title.length(), (char[])object, 0);
                iFileDialog.SetTitle((char[])object);
            }
            if (this.filterPath != null && this.filterPath.length() > 0) {
                object = this.filterPath.replace('/', '\\');
                char[] cArray = new char[((String)object).length() + 1];
                ((String)object).getChars(0, ((String)object).length(), cArray, 0);
                if (COM.SHCreateItemFromParsingName(cArray, 0L, COM.IID_IShellItem, lArray) == 0) {
                    IShellItem iShellItem = new IShellItem(lArray[0]);
                    iFileDialog.ClearClientData();
                    iFileDialog.SetDefaultFolder(iShellItem);
                    iShellItem.Release();
                }
            }
            object = this.parent.getDisplay();
            long l2 = this.parent.handle;
            ((Display)object).externalEventLoop = true;
            if (iFileDialog.Show(l2) == 0 && iFileDialog.GetResult(lArray) == 0) {
                IShellItem iShellItem = new IShellItem(lArray[0]);
                if (iShellItem.GetDisplayName(-2147123200, lArray) == 0) {
                    long l3 = lArray[0];
                    int n = OS.wcslen(l3);
                    char[] cArray = new char[n];
                    OS.MoveMemory(cArray, l3, n * 2);
                    OS.CoTaskMemFree(l3);
                    this.directoryPath = new String(cArray);
                }
                iShellItem.Release();
            }
            ((Display)object).externalEventLoop = false;
            iFileDialog.Release();
        }
        return this.directoryPath;
    }

    public void setFilterPath(String string) {
        this.filterPath = string;
    }

    public void setMessage(String string) {
        if (string == null) {
            this.error(4);
        }
        this.message = string;
    }
}

