/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IShellItem;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IFileDialog
extends IUnknown {
    public IFileDialog(long l2) {
        super(l2);
    }

    public int Show(long l2) {
        return COM.VtblCall(3, this.address, l2);
    }

    public int SetFileTypes(int n, long[] lArray) {
        return COM.VtblCall(4, this.address, n, lArray);
    }

    public int SetFileTypeIndex(int n) {
        return COM.VtblCall(5, this.address, n);
    }

    public int GetFileTypeIndex(int[] nArray) {
        return COM.VtblCall(6, this.address, nArray);
    }

    public int SetOptions(int n) {
        return COM.VtblCall(9, this.address, n);
    }

    public int GetOptions(int[] nArray) {
        return COM.VtblCall(10, this.address, nArray);
    }

    public int SetDefaultFolder(IShellItem iShellItem) {
        return COM.VtblCall(11, this.address, iShellItem.address);
    }

    public int SetFolder(IShellItem iShellItem) {
        return COM.VtblCall(12, this.address, iShellItem.address);
    }

    public int SetFileName(char[] cArray) {
        return COM.VtblCall(15, this.address, cArray);
    }

    public int SetTitle(char[] cArray) {
        return COM.VtblCall(17, this.address, cArray);
    }

    public int GetResult(long[] lArray) {
        return COM.VtblCall(20, this.address, lArray);
    }

    public int SetDefaultExtension(char[] cArray) {
        return COM.VtblCall(22, this.address, cArray);
    }

    public int ClearClientData() {
        return COM.VtblCall(25, this.address);
    }

    public int GetResults(long[] lArray) {
        return COM.VtblCall(27, this.address, lArray);
    }
}

