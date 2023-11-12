/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;

public class VARDESC {
    public int memid;
    public long lpstrSchema;
    public int oInst;
    public long elemdescVar_tdesc_union;
    public short elemdescVar_tdesc_vt;
    public long elemdescVar_paramdesc_pparamdescex;
    public short elemdescVar_paramdesc_wParamFlags;
    public short wVarFlags;
    public int varkind;
    public static final int sizeof = COM.VARDESC_sizeof();
}

