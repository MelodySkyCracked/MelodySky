/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;

public class NMUPDOWN
extends NMHDR {
    public int iPos;
    public int iDelta;
    public static final int sizeof = OS.NMUPDOWN_sizeof();
}

