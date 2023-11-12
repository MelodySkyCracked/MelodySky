/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ITfInputProcessorProfiles
extends IUnknown {
    public ITfInputProcessorProfiles(long l2) {
        super(l2);
    }

    public int GetDefaultLanguageProfile(int n, GUID gUID, GUID gUID2, GUID gUID3) {
        return COM.VtblCall(8, this.address, n, gUID, gUID2, gUID3);
    }
}

