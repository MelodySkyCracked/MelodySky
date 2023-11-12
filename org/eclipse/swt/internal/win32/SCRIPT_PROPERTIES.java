/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class SCRIPT_PROPERTIES {
    public short langid;
    public boolean fNumeric;
    public boolean fComplex;
    public boolean fNeedsWordBreaking;
    public boolean fNeedsCaretInfo;
    public byte bCharSet;
    public boolean fControl;
    public boolean fPrivateUseArea;
    public boolean fNeedsCharacterJustify;
    public boolean fInvalidGlyph;
    public boolean fInvalidLogAttr;
    public boolean fCDM;
    public boolean fAmbiguousCharSet;
    public boolean fClusterSizeVaries;
    public boolean fRejectInvalid;
    public static final int sizeof = OS.SCRIPT_PROPERTIES_sizeof();
}

