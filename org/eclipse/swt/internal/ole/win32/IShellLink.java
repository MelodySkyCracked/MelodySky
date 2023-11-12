/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IShellLink
extends IUnknown {
    public IShellLink(long l2) {
        super(l2);
    }

    public int SetDescription(char[] cArray) {
        return COM.VtblCall(7, this.address, cArray);
    }

    public int SetArguments(char[] cArray) {
        return COM.VtblCall(11, this.address, cArray);
    }

    public int SetIconLocation(char[] cArray, int n) {
        return COM.VtblCall(17, this.address, cArray, n);
    }

    public int SetPath(char[] cArray) {
        return COM.VtblCall(20, this.address, cArray);
    }
}

