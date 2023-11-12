/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IEnumTfDisplayAttributeInfo
extends IUnknown {
    public IEnumTfDisplayAttributeInfo(long l2) {
        super(l2);
    }

    public int Next(int n, long[] lArray, int[] nArray) {
        return COM.VtblCall(4, this.address, n, lArray, nArray);
    }
}

