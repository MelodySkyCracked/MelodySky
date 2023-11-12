/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;

public class FUNCDESC {
    public int memid;
    public long lprgscode;
    public long lprgelemdescParam;
    public int funckind;
    public int invkind;
    public int callconv;
    public short cParams;
    public short cParamsOpt;
    public short oVft;
    public short cScodes;
    public long elemdescFunc_tdesc_union;
    public short elemdescFunc_tdesc_vt;
    public long elemdescFunc_paramdesc_pparamdescex;
    public short elemdescFunc_paramdesc_wParamFlags;
    public short wFuncFlags;
    public static final int sizeof = COM.FUNCDESC_sizeof();
}

