/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.CONTROLINFO;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IOleControl
extends IUnknown {
    public IOleControl(long l2) {
        super(l2);
    }

    public int GetControlInfo(CONTROLINFO cONTROLINFO) {
        return COM.VtblCall(3, this.address, cONTROLINFO);
    }
}

