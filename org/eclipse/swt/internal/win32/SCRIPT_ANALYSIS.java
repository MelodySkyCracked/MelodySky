/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.SCRIPT_STATE;

public class SCRIPT_ANALYSIS {
    public short eScript;
    public boolean fRTL;
    public boolean fLayoutRTL;
    public boolean fLinkBefore;
    public boolean fLinkAfter;
    public boolean fLogicalOrder;
    public boolean fNoGlyphIndex;
    public SCRIPT_STATE s = new SCRIPT_STATE();
    public static final int sizeof = OS.SCRIPT_ANALYSIS_sizeof();
}

