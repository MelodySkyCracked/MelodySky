/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IProvideClassInfo;

public class IProvideClassInfo2
extends IProvideClassInfo {
    public IProvideClassInfo2(long l2) {
        super(l2);
    }

    public int GetGUID(int n, GUID gUID) {
        return COM.VtblCall(4, this.address, n, gUID);
    }
}

