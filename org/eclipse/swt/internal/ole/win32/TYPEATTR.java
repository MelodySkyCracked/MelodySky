/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;

public final class TYPEATTR {
    public int guid_Data1;
    public short guid_Data2;
    public short guid_Data3;
    public byte[] guid_Data4 = new byte[8];
    public int lcid;
    public int dwReserved;
    public int memidConstructor;
    public int memidDestructor;
    public long lpstrSchema;
    public int cbSizeInstance;
    public int typekind;
    public short cFuncs;
    public short cVars;
    public short cImplTypes;
    public short cbSizeVft;
    public short cbAlignment;
    public short wTypeFlags;
    public short wMajorVerNum;
    public short wMinorVerNum;
    public long tdescAlias_unionField;
    public short tdescAlias_vt;
    public int idldescType_dwReserved;
    public short idldescType_wIDLFlags;
    public static final int sizeof = COM.TYPEATTR_sizeof();
}

