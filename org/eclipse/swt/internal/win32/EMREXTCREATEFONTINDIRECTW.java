/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.EMR;
import org.eclipse.swt.internal.win32.EXTLOGFONTW;
import org.eclipse.swt.internal.win32.OS;

public class EMREXTCREATEFONTINDIRECTW {
    public EMR emr = new EMR();
    public int ihFont;
    public EXTLOGFONTW elfw = new EXTLOGFONTW();
    public static final int sizeof = OS.EMREXTCREATEFONTINDIRECTW_sizeof();
}

