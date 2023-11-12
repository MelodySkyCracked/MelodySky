/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;

public class CANDIDATEFORM {
    public int dwIndex;
    public int dwStyle;
    public POINT ptCurrentPos = new POINT();
    public RECT rcArea = new RECT();
    public static final int sizeof = OS.CANDIDATEFORM_sizeof();
}

