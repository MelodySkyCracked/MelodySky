/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.DISPPARAMS;
import org.eclipse.swt.internal.ole.win32.EXCEPINFO;
import org.eclipse.swt.internal.ole.win32.IDispatch;

public class IDispatchEx
extends IDispatch {
    public IDispatchEx(long l2) {
        super(l2);
    }

    public int InvokeEx(int n, int n2, int n3, DISPPARAMS dISPPARAMS, long l2, EXCEPINFO eXCEPINFO, long l3) {
        return COM.VtblCall(8, this.address, n, n2, n3, dISPPARAMS, l2, eXCEPINFO, l3);
    }
}

