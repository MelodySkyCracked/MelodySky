/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2Environment;
import org.eclipse.swt.internal.ole.win32.IStream;

public class ICoreWebView2Environment2
extends ICoreWebView2Environment {
    public ICoreWebView2Environment2(long l2) {
        super(l2);
    }

    public int CreateWebResourceRequest(char[] cArray, char[] cArray2, IStream iStream, char[] cArray3, long[] lArray) {
        return COM.VtblCall(8, this.address, cArray, cArray2, iStream != null ? iStream.address : 0L, cArray3, lArray);
    }
}

