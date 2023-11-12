/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.SAFEARRAYBOUND;

public class SAFEARRAY {
    public short cDims;
    public short fFeatures;
    public int cbElements;
    public int cLocks;
    public long pvData;
    public SAFEARRAYBOUND rgsabound;
    public static final int sizeof = OS.SAFEARRAY_sizeof();
}

