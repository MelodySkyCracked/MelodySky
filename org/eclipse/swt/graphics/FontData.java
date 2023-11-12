/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;

public final class FontData {
    public LOGFONT data;
    public float height;
    String lang;
    String country;
    String variant;

    public FontData() {
        this.data = new LOGFONT();
        this.data.lfCharSet = 1;
        this.height = 12.0f;
    }

    FontData(LOGFONT lOGFONT, float f) {
        this.data = lOGFONT;
        this.height = f;
    }

    public FontData(String string) {
        if (string == null) {
            SWT.error(4);
        }
        int n = 0;
        int n2 = string.indexOf(124);
        if (n2 == -1) {
            SWT.error(5);
        }
        String string2 = string.substring(n, n2);
        try {
            if (Integer.parseInt(string2) != 1) {
                SWT.error(5);
            }
        }
        catch (NumberFormatException numberFormatException) {
            SWT.error(5);
        }
        n = n2 + 1;
        n2 = string.indexOf(124, n);
        if (n2 == -1) {
            SWT.error(5);
        }
        String string3 = string.substring(n, n2);
        n = n2 + 1;
        if ((n2 = string.indexOf(124, n)) == -1) {
            SWT.error(5);
        }
        float f = 0.0f;
        try {
            f = Float.parseFloat(string.substring(n, n2));
        }
        catch (NumberFormatException numberFormatException) {
            SWT.error(5);
        }
        n = n2 + 1;
        n2 = string.indexOf(124, n);
        if (n2 == -1) {
            SWT.error(5);
        }
        int n3 = 0;
        try {
            n3 = Integer.parseInt(string.substring(n, n2));
        }
        catch (NumberFormatException numberFormatException) {
            SWT.error(5);
        }
        n = n2 + 1;
        n2 = string.indexOf(124, n);
        this.data = new LOGFONT();
        this.data.lfCharSet = 1;
        this.setName(string3);
        this.setHeight(f);
        this.setStyle(n3);
        if (n2 == -1) {
            return;
        }
        String string4 = string.substring(n, n2);
        n = n2 + 1;
        if ((n2 = string.indexOf(124, n)) == -1) {
            return;
        }
        String string5 = string.substring(n, n2);
        if (string4.equals("WINDOWS") && string5.equals("1")) {
            LOGFONT lOGFONT = new LOGFONT();
            try {
                n = n2 + 1;
                n2 = string.indexOf(124, n);
                if (n2 == -1) {
                    return;
                }
                lOGFONT.lfHeight = Integer.parseInt(string.substring(n, n2));
                n = n2 + 1;
                if ((n2 = string.indexOf(124, n)) == -1) {
                    return;
                }
                lOGFONT.lfWidth = Integer.parseInt(string.substring(n, n2));
                n = n2 + 1;
                if ((n2 = string.indexOf(124, n)) == -1) {
                    return;
                }
                lOGFONT.lfEscapement = Integer.parseInt(string.substring(n, n2));
                n = n2 + 1;
                if ((n2 = string.indexOf(124, n)) == -1) {
                    return;
                }
                lOGFONT.lfOrientation = Integer.parseInt(string.substring(n, n2));
                n = n2 + 1;
                if ((n2 = string.indexOf(124, n)) == -1) {
                    return;
                }
                lOGFONT.lfWeight = Integer.parseInt(string.substring(n, n2));
                n = n2 + 1;
                if ((n2 = string.indexOf(124, n)) == -1) {
                    return;
                }
                lOGFONT.lfItalic = Byte.parseByte(string.substring(n, n2));
                n = n2 + 1;
                if ((n2 = string.indexOf(124, n)) == -1) {
                    return;
                }
                lOGFONT.lfUnderline = Byte.parseByte(string.substring(n, n2));
                n = n2 + 1;
                if ((n2 = string.indexOf(124, n)) == -1) {
                    return;
                }
                lOGFONT.lfStrikeOut = Byte.parseByte(string.substring(n, n2));
                n = n2 + 1;
                if ((n2 = string.indexOf(124, n)) == -1) {
                    return;
                }
                lOGFONT.lfCharSet = Byte.parseByte(string.substring(n, n2));
                n = n2 + 1;
                if ((n2 = string.indexOf(124, n)) == -1) {
                    return;
                }
                lOGFONT.lfOutPrecision = Byte.parseByte(string.substring(n, n2));
                n = n2 + 1;
                if ((n2 = string.indexOf(124, n)) == -1) {
                    return;
                }
                lOGFONT.lfClipPrecision = Byte.parseByte(string.substring(n, n2));
                n = n2 + 1;
                if ((n2 = string.indexOf(124, n)) == -1) {
                    return;
                }
                lOGFONT.lfQuality = Byte.parseByte(string.substring(n, n2));
                n = n2 + 1;
                if ((n2 = string.indexOf(124, n)) == -1) {
                    return;
                }
                lOGFONT.lfPitchAndFamily = Byte.parseByte(string.substring(n, n2));
                n = n2 + 1;
            }
            catch (NumberFormatException numberFormatException) {
                this.setName(string3);
                this.setHeight(f);
                this.setStyle(n3);
                return;
            }
            int n4 = Math.min(lOGFONT.lfFaceName.length - 1, string.length() - n);
            string.getChars(n, n + n4, lOGFONT.lfFaceName, 0);
            this.data = lOGFONT;
        }
    }

    public FontData(String string, int n, int n2) {
        if (string == null) {
            SWT.error(4);
        }
        this.data = new LOGFONT();
        this.setName(string);
        this.setHeight(n);
        this.setStyle(n2);
        this.data.lfCharSet = 1;
    }

    FontData(String string, float f, int n) {
        if (string == null) {
            SWT.error(4);
        }
        this.data = new LOGFONT();
        this.setName(string);
        this.setHeight(f);
        this.setStyle(n);
        this.data.lfCharSet = 1;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof FontData)) {
            return false;
        }
        FontData fontData = (FontData)object;
        LOGFONT lOGFONT = fontData.data;
        return this.data.lfCharSet == lOGFONT.lfCharSet && this.height == fontData.height && this.data.lfWidth == lOGFONT.lfWidth && this.data.lfEscapement == lOGFONT.lfEscapement && this.data.lfOrientation == lOGFONT.lfOrientation && this.data.lfWeight == lOGFONT.lfWeight && this.data.lfItalic == lOGFONT.lfItalic && this.data.lfUnderline == lOGFONT.lfUnderline && this.data.lfStrikeOut == lOGFONT.lfStrikeOut && this.data.lfCharSet == lOGFONT.lfCharSet && this.data.lfOutPrecision == lOGFONT.lfOutPrecision && this.data.lfClipPrecision == lOGFONT.lfClipPrecision && this.data.lfQuality == lOGFONT.lfQuality && this.data.lfPitchAndFamily == lOGFONT.lfPitchAndFamily && this.getName().equals(fontData.getName());
    }

    long EnumLocalesProc(long l2) {
        int n = 8;
        TCHAR tCHAR = new TCHAR(0, 8);
        int n2 = 16;
        OS.MoveMemory(tCHAR, l2, 16);
        int n3 = Integer.parseInt(tCHAR.toString(0, tCHAR.strlen()), 16);
        int n4 = OS.GetLocaleInfo(n3, 89, tCHAR, 8);
        if (n4 <= 0 || !this.lang.equals(tCHAR.toString(0, n4 - 1))) {
            return 1L;
        }
        if (!(this.country == null || (n4 = OS.GetLocaleInfo(n3, 90, tCHAR, 8)) > 0 && this.country.equals(tCHAR.toString(0, n4 - 1)))) {
            return 1L;
        }
        n4 = OS.GetLocaleInfo(n3, 4100, tCHAR, 8);
        if (n4 <= 0) {
            return 1L;
        }
        int n5 = Integer.parseInt(tCHAR.toString(0, n4 - 1));
        int[] nArray = new int[8];
        OS.TranslateCharsetInfo(n5, nArray, 2);
        this.data.lfCharSet = (byte)nArray[0];
        return 0L;
    }

    public int getHeight() {
        return (int)(0.5f + this.height);
    }

    float getHeightF() {
        return this.height;
    }

    public String getLocale() {
        String string;
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 95;
        if (this.lang != null) {
            stringBuilder.append(this.lang);
            stringBuilder.append('_');
        }
        if (this.country != null) {
            stringBuilder.append(this.country);
            stringBuilder.append('_');
        }
        if (this.variant != null) {
            stringBuilder.append(this.variant);
        }
        if ((n = (string = stringBuilder.toString()).length()) > 0 && string.charAt(n - 1) == '_') {
            string = string.substring(0, n - 1);
        }
        return string;
    }

    public String getName() {
        int n;
        char[] cArray = this.data.lfFaceName;
        for (n = 0; n < cArray.length && cArray[n] != '\u0000'; ++n) {
        }
        return new String(cArray, 0, n);
    }

    public int getStyle() {
        int n = 0;
        if (this.data.lfWeight == 700) {
            n |= 1;
        }
        if (this.data.lfItalic != 0) {
            n |= 2;
        }
        return n;
    }

    public int hashCode() {
        return this.data.lfCharSet ^ this.getHeight() << 8 ^ this.data.lfWidth ^ this.data.lfEscapement ^ this.data.lfOrientation ^ this.data.lfWeight ^ this.data.lfItalic ^ this.data.lfUnderline ^ this.data.lfStrikeOut ^ this.data.lfCharSet ^ this.data.lfOutPrecision ^ this.data.lfClipPrecision ^ this.data.lfQuality ^ this.data.lfPitchAndFamily ^ this.getName().hashCode();
    }

    public void setHeight(int n) {
        if (n < 0) {
            SWT.error(5);
        }
        this.height = n;
        this.data.lfWidth = 0;
    }

    void setHeight(float f) {
        if (f < 0.0f) {
            SWT.error(5);
        }
        this.height = f;
    }

    public void setLocale(String string) {
        Object var2_2 = null;
        this.variant = var2_2;
        this.country = var2_2;
        this.lang = var2_2;
        if (string != null) {
            int n;
            int n2 = 95;
            int n3 = string.length();
            int n4 = string.indexOf(95);
            if (n4 == -1) {
                n = n4 = n3;
            } else {
                n = string.indexOf(95, n4 + 1);
                if (n == -1) {
                    n = n3;
                }
            }
            if (n4 > 0) {
                this.lang = string.substring(0, n4);
            }
            if (n > n4 + 1) {
                this.country = string.substring(n4 + 1, n);
            }
            if (n3 > n + 1) {
                this.variant = string.substring(n + 1);
            }
        }
        if (this.lang == null) {
            this.data.lfCharSet = 1;
        } else {
            Callback callback = new Callback(this, "EnumLocalesProc", 1);
            OS.EnumSystemLocales(callback.getAddress(), 2);
            callback.dispose();
        }
    }

    public void setName(String string) {
        if (string == null) {
            SWT.error(4);
        }
        char[] cArray = this.data.lfFaceName;
        int n = Math.min(cArray.length - 1, string.length());
        string.getChars(0, n, cArray, 0);
        for (int i = n; i < cArray.length; ++i) {
            cArray[i] = '\u0000';
        }
    }

    public void setStyle(int n) {
        this.data.lfWeight = (n & 1) == 1 ? 700 : 0;
        this.data.lfItalic = (n & 2) == 2 ? (byte)1 : 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("1|");
        String string = this.getName();
        stringBuilder.append(string);
        stringBuilder.append("|");
        stringBuilder.append(this.getHeightF());
        stringBuilder.append("|");
        stringBuilder.append(this.getStyle());
        stringBuilder.append("|");
        stringBuilder.append("WINDOWS|1|");
        stringBuilder.append(this.data.lfHeight);
        stringBuilder.append("|");
        stringBuilder.append(this.data.lfWidth);
        stringBuilder.append("|");
        stringBuilder.append(this.data.lfEscapement);
        stringBuilder.append("|");
        stringBuilder.append(this.data.lfOrientation);
        stringBuilder.append("|");
        stringBuilder.append(this.data.lfWeight);
        stringBuilder.append("|");
        stringBuilder.append(this.data.lfItalic);
        stringBuilder.append("|");
        stringBuilder.append(this.data.lfUnderline);
        stringBuilder.append("|");
        stringBuilder.append(this.data.lfStrikeOut);
        stringBuilder.append("|");
        stringBuilder.append(this.data.lfCharSet);
        stringBuilder.append("|");
        stringBuilder.append(this.data.lfOutPrecision);
        stringBuilder.append("|");
        stringBuilder.append(this.data.lfClipPrecision);
        stringBuilder.append("|");
        stringBuilder.append(this.data.lfQuality);
        stringBuilder.append("|");
        stringBuilder.append(this.data.lfPitchAndFamily);
        stringBuilder.append("|");
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    public static FontData win32_new(LOGFONT lOGFONT, float f) {
        return new FontData(lOGFONT, f);
    }
}

