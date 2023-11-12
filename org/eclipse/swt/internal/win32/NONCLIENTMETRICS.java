/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.OS;

public class NONCLIENTMETRICS {
    public int cbSize;
    public int iBorderWidth;
    public int iScrollWidth;
    public int iScrollHeight;
    public int iCaptionWidth;
    public int iCaptionHeight;
    public LOGFONT lfCaptionFont = new LOGFONT();
    public int iSmCaptionWidth;
    public int iSmCaptionHeight;
    public LOGFONT lfSmCaptionFont = new LOGFONT();
    public int iMenuWidth;
    public int iMenuHeight;
    public LOGFONT lfMenuFont = new LOGFONT();
    public LOGFONT lfStatusFont = new LOGFONT();
    public LOGFONT lfMessageFont = new LOGFONT();
    public static final int sizeof = OS.NONCLIENTMETRICS_sizeof();
}

