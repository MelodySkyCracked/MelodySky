/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TEXTMETRIC;

public class OUTLINETEXTMETRIC {
    public int otmSize;
    public TEXTMETRIC otmTextMetrics = new TEXTMETRIC();
    public byte otmFiller;
    public byte otmPanoseNumber_bFamilyType;
    public byte otmPanoseNumber_bSerifStyle;
    public byte otmPanoseNumber_bWeight;
    public byte otmPanoseNumber_bProportion;
    public byte otmPanoseNumber_bContrast;
    public byte otmPanoseNumber_bStrokeVariation;
    public byte otmPanoseNumber_bArmStyle;
    public byte otmPanoseNumber_bLetterform;
    public byte otmPanoseNumber_bMidline;
    public byte otmPanoseNumber_bXHeight;
    public int otmfsSelection;
    public int otmfsType;
    public int otmsCharSlopeRise;
    public int otmsCharSlopeRun;
    public int otmItalicAngle;
    public int otmEMSquare;
    public int otmAscent;
    public int otmDescent;
    public int otmLineGap;
    public int otmsCapEmHeight;
    public int otmsXHeight;
    public RECT otmrcFontBox = new RECT();
    public int otmMacAscent;
    public int otmMacDescent;
    public int otmMacLineGap;
    public int otmusMinimumPPEM;
    public POINT otmptSubscriptSize = new POINT();
    public POINT otmptSubscriptOffset = new POINT();
    public POINT otmptSuperscriptSize = new POINT();
    public POINT otmptSuperscriptOffset = new POINT();
    public int otmsStrikeoutSize;
    public int otmsStrikeoutPosition;
    public int otmsUnderscoreSize;
    public int otmsUnderscorePosition;
    public long otmpFamilyName;
    public long otmpFaceName;
    public long otmpStyleName;
    public long otmpFullName;
    public static final int sizeof = OS.OUTLINETEXTMETRIC_sizeof();
}

