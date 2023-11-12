/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.TEXTMETRIC;

public final class FontMetrics {
    public TEXTMETRIC handle;

    FontMetrics() {
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof FontMetrics)) {
            return false;
        }
        TEXTMETRIC tEXTMETRIC = ((FontMetrics)object).handle;
        return this.handle.tmHeight == tEXTMETRIC.tmHeight && this.handle.tmAscent == tEXTMETRIC.tmAscent && this.handle.tmDescent == tEXTMETRIC.tmDescent && this.handle.tmInternalLeading == tEXTMETRIC.tmInternalLeading && this.handle.tmExternalLeading == tEXTMETRIC.tmExternalLeading && this.handle.tmAveCharWidth == tEXTMETRIC.tmAveCharWidth && this.handle.tmMaxCharWidth == tEXTMETRIC.tmMaxCharWidth && this.handle.tmWeight == tEXTMETRIC.tmWeight && this.handle.tmOverhang == tEXTMETRIC.tmOverhang && this.handle.tmDigitizedAspectX == tEXTMETRIC.tmDigitizedAspectX && this.handle.tmDigitizedAspectY == tEXTMETRIC.tmDigitizedAspectY && this.handle.tmItalic == tEXTMETRIC.tmItalic && this.handle.tmUnderlined == tEXTMETRIC.tmUnderlined && this.handle.tmStruckOut == tEXTMETRIC.tmStruckOut && this.handle.tmPitchAndFamily == tEXTMETRIC.tmPitchAndFamily && this.handle.tmCharSet == tEXTMETRIC.tmCharSet;
    }

    public int getAscent() {
        return DPIUtil.autoScaleDown(this.handle.tmAscent - this.handle.tmInternalLeading);
    }

    public double getAverageCharacterWidth() {
        return this.getAverageCharWidth();
    }

    @Deprecated
    public int getAverageCharWidth() {
        return DPIUtil.autoScaleDown(this.handle.tmAveCharWidth);
    }

    public int getDescent() {
        return DPIUtil.autoScaleDown(this.handle.tmDescent);
    }

    public int getHeight() {
        return DPIUtil.autoScaleDown(this.handle.tmHeight);
    }

    public int getLeading() {
        return this.getHeight() - this.getAscent() - this.getDescent();
    }

    public int hashCode() {
        return this.handle.tmHeight ^ this.handle.tmAscent ^ this.handle.tmDescent ^ this.handle.tmInternalLeading ^ this.handle.tmExternalLeading ^ this.handle.tmAveCharWidth ^ this.handle.tmMaxCharWidth ^ this.handle.tmWeight ^ this.handle.tmOverhang ^ this.handle.tmDigitizedAspectX ^ this.handle.tmDigitizedAspectY ^ this.handle.tmItalic ^ this.handle.tmUnderlined ^ this.handle.tmStruckOut ^ this.handle.tmPitchAndFamily ^ this.handle.tmCharSet;
    }

    public static FontMetrics win32_new(TEXTMETRIC tEXTMETRIC) {
        FontMetrics fontMetrics = new FontMetrics();
        fontMetrics.handle = tEXTMETRIC;
        return fontMetrics;
    }
}

