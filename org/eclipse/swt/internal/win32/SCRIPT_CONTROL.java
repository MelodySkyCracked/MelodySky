/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class SCRIPT_CONTROL {
    public int uDefaultLanguage;
    public boolean fContextDigits;
    public boolean fInvertPreBoundDir;
    public boolean fInvertPostBoundDir;
    public boolean fLinkStringBefore;
    public boolean fLinkStringAfter;
    public boolean fNeutralOverride;
    public boolean fNumericOverride;
    public boolean fLegacyBidiClass;
    public boolean fMergeNeutralItems;
    public boolean fUseStandardBidi;
    public int fReserved;
    public static final int sizeof = OS.SCRIPT_CONTROL_sizeof();
}

