/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class SCRIPT_LOGATTR {
    public boolean fSoftBreak;
    public boolean fWhiteSpace;
    public boolean fCharStop;
    public boolean fWordStop;
    public boolean fInvalid;
    public byte fReserved;
    public static final int sizeof = OS.SCRIPT_LOGATTR_sizeof();
}

