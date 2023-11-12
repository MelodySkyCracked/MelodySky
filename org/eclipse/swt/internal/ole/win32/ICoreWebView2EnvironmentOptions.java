/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2EnvironmentOptions
extends IUnknown {
    public ICoreWebView2EnvironmentOptions(long l2) {
        super(l2);
    }

    public int put_AdditionalBrowserArguments(char[] cArray) {
        return COM.VtblCall(4, this.address, cArray);
    }

    public int put_Language(char[] cArray) {
        return COM.VtblCall(6, this.address, cArray);
    }

    public int put_TargetCompatibleBrowserVersion(char[] cArray) {
        return COM.VtblCall(8, this.address, cArray);
    }

    public int put_AllowSingleSignOnUsingOSPrimaryAccount(int[] nArray) {
        return COM.VtblCall(10, this.address, nArray);
    }
}

