/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IOleWindow
extends IUnknown {
    public IOleWindow(long l2) {
        super(l2);
    }

    public int GetWindow(long[] lArray) {
        return COM.VtblCall(3, this.address, lArray);
    }
}

