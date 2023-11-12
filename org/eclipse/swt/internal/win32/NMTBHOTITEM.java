/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;

public class NMTBHOTITEM
extends NMHDR {
    public int idOld;
    public int idNew;
    public int dwFlags;
    public static final int sizeof = OS.NMTBHOTITEM_sizeof();
}

