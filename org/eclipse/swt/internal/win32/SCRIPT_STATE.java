/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class SCRIPT_STATE {
    public short uBidiLevel;
    public boolean fOverrideDirection;
    public boolean fInhibitSymSwap;
    public boolean fCharShape;
    public boolean fDigitSubstitute;
    public boolean fInhibitLigate;
    public boolean fDisplayZWG;
    public boolean fArabicNumContext;
    public boolean fGcpClusters;
    public boolean fReserved;
    public short fEngineReserved;
    public static final int sizeof = OS.SCRIPT_STATE_sizeof();
}

