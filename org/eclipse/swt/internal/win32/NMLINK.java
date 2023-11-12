/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;

public class NMLINK
extends NMHDR {
    public int mask;
    public int iLink;
    public int state;
    public int stateMask;
    public char[] szID = new char[48];
    public char[] szUrl = new char[2084];
    public static final int sizeof = OS.NMLINK_sizeof();
}

