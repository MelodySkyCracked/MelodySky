/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GlyphMetrics;

public class TextStyle {
    public Font font;
    public Color foreground;
    public Color background;
    public boolean underline;
    public Color underlineColor;
    public int underlineStyle;
    public boolean strikeout;
    public Color strikeoutColor;
    public int borderStyle;
    public Color borderColor;
    public GlyphMetrics metrics;
    public int rise;
    public Object data;

    public TextStyle() {
    }

    public TextStyle(Font font, Color color, Color color2) {
        if (font != null && font.isDisposed()) {
            SWT.error(5);
        }
        if (color != null && color.isDisposed()) {
            SWT.error(5);
        }
        if (color2 != null && color2.isDisposed()) {
            SWT.error(5);
        }
        this.font = font;
        this.foreground = color;
        this.background = color2;
    }

    public TextStyle(TextStyle textStyle) {
        if (textStyle == null) {
            SWT.error(5);
        }
        this.font = textStyle.font;
        this.foreground = textStyle.foreground;
        this.background = textStyle.background;
        this.underline = textStyle.underline;
        this.underlineColor = textStyle.underlineColor;
        this.underlineStyle = textStyle.underlineStyle;
        this.strikeout = textStyle.strikeout;
        this.strikeoutColor = textStyle.strikeoutColor;
        this.borderStyle = textStyle.borderStyle;
        this.borderColor = textStyle.borderColor;
        this.metrics = textStyle.metrics;
        this.rise = textStyle.rise;
        this.data = textStyle.data;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof TextStyle)) {
            return false;
        }
        TextStyle textStyle = (TextStyle)object;
        if (this.foreground != null ? !this.foreground.equals(textStyle.foreground) : textStyle.foreground != null) {
            return false;
        }
        if (this.background != null ? !this.background.equals(textStyle.background) : textStyle.background != null) {
            return false;
        }
        if (this.font != null ? !this.font.equals(textStyle.font) : textStyle.font != null) {
            return false;
        }
        if (this.metrics != null ? !this.metrics.equals(textStyle.metrics) : textStyle.metrics != null) {
            return false;
        }
        if (this.underline != textStyle.underline) {
            return false;
        }
        if (this.underlineStyle != textStyle.underlineStyle) {
            return false;
        }
        if (this.borderStyle != textStyle.borderStyle) {
            return false;
        }
        if (this.strikeout != textStyle.strikeout) {
            return false;
        }
        if (this.rise != textStyle.rise) {
            return false;
        }
        if (this.underlineColor != null ? !this.underlineColor.equals(textStyle.underlineColor) : textStyle.underlineColor != null) {
            return false;
        }
        if (this.strikeoutColor != null ? !this.strikeoutColor.equals(textStyle.strikeoutColor) : textStyle.strikeoutColor != null) {
            return false;
        }
        if (this.underlineStyle != textStyle.underlineStyle) {
            return false;
        }
        if (this.borderColor != null ? !this.borderColor.equals(textStyle.borderColor) : textStyle.borderColor != null) {
            return false;
        }
        return !(this.data != null ? !this.data.equals(textStyle.data) : textStyle.data != null);
    }

    public int hashCode() {
        int n = 0;
        if (this.foreground != null) {
            n ^= this.foreground.hashCode();
        }
        if (this.background != null) {
            n ^= this.background.hashCode();
        }
        if (this.font != null) {
            n ^= this.font.hashCode();
        }
        if (this.metrics != null) {
            n ^= this.metrics.hashCode();
        }
        if (this.underline) {
            n ^= n << 1;
        }
        if (this.strikeout) {
            n ^= n << 2;
        }
        n ^= this.rise;
        if (this.underlineColor != null) {
            n ^= this.underlineColor.hashCode();
        }
        if (this.strikeoutColor != null) {
            n ^= this.strikeoutColor.hashCode();
        }
        if (this.borderColor != null) {
            n ^= this.borderColor.hashCode();
        }
        return n ^= this.underlineStyle;
    }

    boolean isAdherentBorder(TextStyle textStyle) {
        if (this == textStyle) {
            return true;
        }
        if (textStyle == null) {
            return false;
        }
        if (this.borderStyle != textStyle.borderStyle) {
            return false;
        }
        if (this.borderColor != null) {
            if (!this.borderColor.equals(textStyle.borderColor)) {
                return false;
            }
        } else {
            if (textStyle.borderColor != null) {
                return false;
            }
            if (this.foreground != null ? !this.foreground.equals(textStyle.foreground) : textStyle.foreground != null) {
                return false;
            }
        }
        return true;
    }

    boolean isAdherentUnderline(TextStyle textStyle) {
        if (this == textStyle) {
            return true;
        }
        if (textStyle == null) {
            return false;
        }
        if (this.underline != textStyle.underline) {
            return false;
        }
        if (this.underlineStyle != textStyle.underlineStyle) {
            return false;
        }
        if (this.underlineColor != null) {
            if (!this.underlineColor.equals(textStyle.underlineColor)) {
                return false;
            }
        } else {
            if (textStyle.underlineColor != null) {
                return false;
            }
            if (this.foreground != null ? !this.foreground.equals(textStyle.foreground) : textStyle.foreground != null) {
                return false;
            }
        }
        return true;
    }

    boolean isAdherentStrikeout(TextStyle textStyle) {
        if (this == textStyle) {
            return true;
        }
        if (textStyle == null) {
            return false;
        }
        if (this.strikeout != textStyle.strikeout) {
            return false;
        }
        if (this.strikeoutColor != null) {
            if (!this.strikeoutColor.equals(textStyle.strikeoutColor)) {
                return false;
            }
        } else {
            if (textStyle.strikeoutColor != null) {
                return false;
            }
            if (this.foreground != null ? !this.foreground.equals(textStyle.foreground) : textStyle.foreground != null) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("TextStyle {");
        int n = stringBuilder.length();
        if (this.font != null) {
            if (stringBuilder.length() > n) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("font=");
            stringBuilder.append(this.font);
        }
        if (this.foreground != null) {
            if (stringBuilder.length() > n) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("foreground=");
            stringBuilder.append(this.foreground);
        }
        if (this.background != null) {
            if (stringBuilder.length() > n) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("background=");
            stringBuilder.append(this.background);
        }
        if (this.underline) {
            if (stringBuilder.length() > n) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("underline=");
            switch (this.underlineStyle) {
                case 0: {
                    stringBuilder.append("single");
                    break;
                }
                case 1: {
                    stringBuilder.append("double");
                    break;
                }
                case 3: {
                    stringBuilder.append("squiggle");
                    break;
                }
                case 2: {
                    stringBuilder.append("error");
                    break;
                }
                case 4: {
                    stringBuilder.append("link");
                }
            }
            if (this.underlineColor != null) {
                stringBuilder.append(", underlineColor=");
                stringBuilder.append(this.underlineColor);
            }
        }
        if (this.strikeout) {
            if (stringBuilder.length() > n) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("striked out");
            if (this.strikeoutColor != null) {
                stringBuilder.append(", strikeoutColor=");
                stringBuilder.append(this.strikeoutColor);
            }
        }
        if (this.borderStyle != 0) {
            if (stringBuilder.length() > n) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("border=");
            switch (this.borderStyle) {
                case 1: {
                    stringBuilder.append("solid");
                    break;
                }
                case 4: {
                    stringBuilder.append("dot");
                    break;
                }
                case 2: {
                    stringBuilder.append("dash");
                }
            }
            if (this.borderColor != null) {
                stringBuilder.append(", borderColor=");
                stringBuilder.append(this.borderColor);
            }
        }
        if (this.rise != 0) {
            if (stringBuilder.length() > n) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("rise=");
            stringBuilder.append(this.rise);
        }
        if (this.metrics != null) {
            if (stringBuilder.length() > n) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("metrics=");
            stringBuilder.append(this.metrics);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

