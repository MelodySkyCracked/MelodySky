/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IOleWindow;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.RECT;

public class IOleInPlaceActiveObject
extends IOleWindow {
    public IOleInPlaceActiveObject(long l2) {
        super(l2);
    }

    public int TranslateAccelerator(MSG mSG) {
        return COM.VtblCall(5, this.address, mSG);
    }

    public void OnFrameWindowActivate(boolean bl) {
        COM.VtblCall(6, this.address, bl ? 1 : 0);
    }

    public int ResizeBorder(RECT rECT, long l2, boolean bl) {
        return COM.VtblCall(8, this.address, rECT, l2, bl ? 1 : 0);
    }
}

