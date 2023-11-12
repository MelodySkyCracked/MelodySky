/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;

public class NMLVODSTATECHANGE
extends NMHDR {
    public int iFrom;
    public int iTo;
    public int uNewState;
    public int uOldState;
    public static final int sizeof = OS.NMLVODSTATECHANGE_sizeof();
}

