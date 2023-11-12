/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.custom.StyledTextEvent;
import org.eclipse.swt.custom.StyledTextLineSpacingProvider;
import org.eclipse.swt.custom.TextChangingEvent;
import org.eclipse.swt.custom.lIIIIl;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.IME;

class StyledTextRenderer {
    Device device;
    StyledText styledText;
    StyledTextContent content;
    StyledTextLineSpacingProvider lineSpacingProvider;
    boolean lineSpacingComputing;
    Font regularFont;
    Font boldFont;
    Font italicFont;
    Font boldItalicFont;
    int tabWidth;
    int ascent;
    int descent;
    int averageCharWidth;
    int tabLength;
    int topIndex = -1;
    TextLayout[] layouts;
    int lineCount;
    LineSizeInfo[] lineSizes;
    LineInfo[] lines;
    int maxWidth;
    int maxWidthLineIndex;
    float averageLineHeight;
    int linesInAverageLineHeight;
    boolean idleRunning;
    Bullet[] bullets;
    int[] bulletsIndices;
    int[] redrawLines;
    int[] ranges;
    int styleCount;
    StyleRange[] styles;
    StyleRange[] stylesSet;
    int stylesSetCount = 0;
    boolean hasLinks;
    boolean fixedPitch;
    static final int BULLET_MARGIN = 8;
    static final boolean COMPACT_STYLES = true;
    static final boolean MERGE_STYLES = true;
    static final int GROW = 32;
    static final int IDLE_TIME = 50;
    static final int CACHE_SIZE = 300;
    static final int BACKGROUND = 1;
    static final int ALIGNMENT = 2;
    static final int INDENT = 4;
    static final int JUSTIFY = 8;
    static final int SEGMENTS = 32;
    static final int TABSTOPS = 64;
    static final int WRAP_INDENT = 128;
    static final int SEGMENT_CHARS = 256;
    static final int VERTICAL_INDENT = 512;

    static int cap(TextLayout textLayout, int n) {
        if (textLayout == null) {
            return n;
        }
        return Math.min(textLayout.getText().length() - 1, Math.max(0, n));
    }

    StyledTextRenderer(Device device, StyledText styledText) {
        this.device = device;
        this.styledText = styledText;
    }

    int addMerge(int[] nArray, StyleRange[] styleRangeArray, int n, int n2, int n3) {
        int n4;
        int n5 = this.styleCount << 1;
        StyleRange styleRange = null;
        int n6 = 0;
        int n7 = 0;
        if (n3 < n5) {
            styleRange = this.styles[n3 >> 1];
            n6 = this.ranges[n3];
            n7 = this.ranges[n3 + 1];
        }
        if (n5 + (n4 = n - (n3 - n2)) >= this.ranges.length) {
            int[] nArray2 = new int[this.ranges.length + n4 + 64];
            System.arraycopy(this.ranges, 0, nArray2, 0, n2);
            StyleRange[] styleRangeArray2 = new StyleRange[this.styles.length + (n4 >> 1) + 32];
            System.arraycopy(this.styles, 0, styleRangeArray2, 0, n2 >> 1);
            if (n5 > n3) {
                System.arraycopy(this.ranges, n3, nArray2, n2 + n, n5 - n3);
                System.arraycopy(this.styles, n3 >> 1, styleRangeArray2, n2 + n >> 1, this.styleCount - (n3 >> 1));
            }
            this.ranges = nArray2;
            this.styles = styleRangeArray2;
        } else if (n5 > n3) {
            System.arraycopy(this.ranges, n3, this.ranges, n2 + n, n5 - n3);
            System.arraycopy(this.styles, n3 >> 1, this.styles, n2 + n >> 1, this.styleCount - (n3 >> 1));
        }
        int n8 = n2;
        for (int i = 0; i < n; i += 2) {
            if (n8 > 0 && this.ranges[n8 - 2] + this.ranges[n8 - 1] == nArray[i] && styleRangeArray[i >> 1].similarTo(this.styles[n8 - 2 >> 1])) {
                int n9;
                int[] nArray3 = this.ranges;
                int n10 = n9 = n8 - 1;
                nArray3[n10] = nArray3[n10] + nArray[i + 1];
                continue;
            }
            this.styles[n8 >> 1] = styleRangeArray[i >> 1];
            this.ranges[n8++] = nArray[i];
            this.ranges[n8++] = nArray[i + 1];
        }
        if (styleRange != null && this.ranges[n8 - 2] + this.ranges[n8 - 1] == n6 && styleRange.similarTo(this.styles[n8 - 2 >> 1])) {
            int n11;
            int[] nArray4 = this.ranges;
            int n12 = n11 = n8 - 1;
            nArray4[n12] = nArray4[n12] + n7;
            n3 += 2;
            n += 2;
        }
        if (n5 > n3) {
            System.arraycopy(this.ranges, n2 + n, this.ranges, n8, n5 - n3);
            System.arraycopy(this.styles, n2 + n >> 1, this.styles, n8 >> 1, this.styleCount - (n3 >> 1));
        }
        n4 = n8 - n2 - (n3 - n2);
        this.styleCount += n4 >> 1;
        return n4;
    }

    int addMerge(StyleRange[] styleRangeArray, int n, int n2, int n3) {
        int n4 = n - (n3 - n2);
        StyleRange styleRange = null;
        if (n3 < this.styleCount) {
            styleRange = this.styles[n3];
        }
        if (this.styleCount + n4 >= this.styles.length) {
            StyleRange[] styleRangeArray2 = new StyleRange[this.styles.length + n4 + 32];
            System.arraycopy(this.styles, 0, styleRangeArray2, 0, n2);
            if (this.styleCount > n3) {
                System.arraycopy(this.styles, n3, styleRangeArray2, n2 + n, this.styleCount - n3);
            }
            this.styles = styleRangeArray2;
        } else if (this.styleCount > n3) {
            System.arraycopy(this.styles, n3, this.styles, n2 + n, this.styleCount - n3);
        }
        int n5 = n2;
        for (StyleRange styleRange2 : styleRangeArray) {
            if (n5 > 0) {
                StyleRange styleRange3 = this.styles[n5 - 1];
                if (styleRange3.start + styleRange3.length == styleRange2.start && styleRange2.similarTo(styleRange3)) {
                    StyleRange styleRange4 = styleRange3;
                    styleRange4.length += styleRange2.length;
                    continue;
                }
            }
            this.styles[n5++] = styleRange2;
        }
        StyleRange styleRange5 = this.styles[n5 - 1];
        if (styleRange != null && styleRange5.start + styleRange5.length == styleRange.start && styleRange.similarTo(styleRange5)) {
            StyleRange styleRange6 = styleRange5;
            styleRange6.length += styleRange.length;
            ++n3;
            ++n;
        }
        if (this.styleCount > n3) {
            System.arraycopy(this.styles, n2 + n, this.styles, n5, this.styleCount - n3);
        }
        n4 = n5 - n2 - (n3 - n2);
        this.styleCount += n4;
        return n4;
    }

    void calculate(int n, int n2) {
        int n3 = n + n2;
        if (n < 0 || n3 > this.lineSizes.length) {
            return;
        }
        int n4 = this.styledText.leftMargin + this.styledText.rightMargin + this.styledText.getCaretWidth();
        for (int i = n; i < n3; ++i) {
            LineSizeInfo lineSizeInfo = this.getLineSize(i);
            if (lineSizeInfo.needsRecalculateSize()) {
                TextLayout textLayout = this.getTextLayout(i);
                Rectangle rectangle = textLayout.getBounds();
                lineSizeInfo.width = rectangle.width + n4;
                lineSizeInfo.height = rectangle.height;
                this.averageLineHeight += (float)((lineSizeInfo.height - Math.round(this.averageLineHeight)) / ++this.linesInAverageLineHeight);
                this.disposeTextLayout(textLayout);
            }
            if (lineSizeInfo.width <= this.maxWidth) continue;
            this.maxWidth = lineSizeInfo.width;
            this.maxWidthLineIndex = i;
        }
    }

    LineSizeInfo getLineSize(int n) {
        if (this.lineSizes[n] == null) {
            this.lineSizes[n] = new LineSizeInfo();
        }
        return this.lineSizes[n];
    }

    void calculateClientArea() {
        int n = Math.max(0, this.styledText.getTopIndex());
        int n2 = this.content.getLineCount();
        int n3 = this.styledText.getClientArea().height;
        for (int i = 0; n3 > i && n2 > n && this.lineSizes.length > n; i += this.lineSizes[n++].height) {
            this.calculate(n, 1);
        }
    }

    void calculateIdle() {
        if (this.idleRunning) {
            return;
        }
        lIIIIl lIIIIl2 = new lIIIIl(this);
        Display display = this.styledText.getDisplay();
        display.asyncExec(lIIIIl2);
        this.idleRunning = true;
    }

    void clearLineBackground(int n, int n2) {
        if (this.lines == null) {
            return;
        }
        for (int i = n; i < n + n2; ++i) {
            LineInfo lineInfo = this.lines[i];
            if (lineInfo == null) continue;
            LineInfo lineInfo2 = lineInfo;
            lineInfo2.flags &= 0xFFFFFFFE;
            lineInfo.background = null;
            if (lineInfo.flags != 0) continue;
            this.lines[i] = null;
        }
    }

    void clearLineStyle(int n, int n2) {
        if (this.lines == null) {
            return;
        }
        for (int i = n; i < n + n2; ++i) {
            LineInfo lineInfo = this.lines[i];
            if (lineInfo == null) continue;
            LineInfo lineInfo2 = lineInfo;
            lineInfo2.flags &= 0xFFFFFD31;
            if (lineInfo.flags != 0) continue;
            this.lines[i] = null;
        }
    }

    void copyInto(StyledTextRenderer styledTextRenderer) {
        int n;
        Object[] objectArray;
        Object[] objectArray2;
        if (this.ranges != null) {
            objectArray2 = new int[this.styleCount << 1];
            styledTextRenderer.ranges = objectArray2;
            objectArray = objectArray2;
            System.arraycopy(this.ranges, 0, objectArray, 0, objectArray.length);
        }
        if (this.styles != null) {
            objectArray2 = new StyleRange[this.styleCount];
            styledTextRenderer.styles = (StyleRange[])objectArray2;
            objectArray = objectArray2;
            for (n = 0; n < objectArray.length; ++n) {
                objectArray[n] = (int)((StyleRange)this.styles[n].clone());
            }
            styledTextRenderer.styleCount = this.styleCount;
        }
        if (this.lines != null) {
            objectArray2 = new LineInfo[this.lineCount];
            styledTextRenderer.lines = (LineInfo[])objectArray2;
            objectArray = objectArray2;
            for (n = 0; n < objectArray.length; ++n) {
                objectArray[n] = (int)new LineInfo(this.lines[n]);
            }
            styledTextRenderer.lineCount = this.lineCount;
        }
    }

    void dispose() {
        if (this.boldFont != null) {
            this.boldFont.dispose();
        }
        if (this.italicFont != null) {
            this.italicFont.dispose();
        }
        if (this.boldItalicFont != null) {
            this.boldItalicFont.dispose();
        }
        Object var1_1 = null;
        this.boldItalicFont = var1_1;
        this.italicFont = var1_1;
        this.boldFont = var1_1;
        this.reset();
        this.content = null;
        this.device = null;
        this.styledText = null;
    }

    void disposeTextLayout(TextLayout textLayout) {
        if (this.layouts != null) {
            for (TextLayout textLayout2 : this.layouts) {
                if (textLayout2 != textLayout) continue;
                return;
            }
        }
        textLayout.dispose();
    }

    void drawBullet(Bullet bullet, GC gC, int n, int n2, int n3, int n4, int n5) {
        Font font;
        StyleRange styleRange = bullet.style;
        GlyphMetrics glyphMetrics = styleRange.metrics;
        Color color = styleRange.foreground;
        if (color != null) {
            gC.setForeground(color);
        }
        if ((font = styleRange.font) != null) {
            gC.setFont(font);
        }
        String string = "";
        int n6 = bullet.type & 0xF;
        switch (n6) {
            case 1: {
                string = "\u2022";
                break;
            }
            case 2: {
                string = String.valueOf(n3 + 1);
                break;
            }
            case 4: {
                string = String.valueOf((char)(n3 % 26 + 97));
                break;
            }
            case 8: {
                string = String.valueOf((char)(n3 % 26 + 65));
            }
        }
        if ((bullet.type & 0x10) != 0) {
            string = string + bullet.text;
        }
        Display display = this.styledText.getDisplay();
        TextLayout textLayout = new TextLayout(display);
        textLayout.setText(string);
        textLayout.setAscent(n4);
        textLayout.setDescent(n5);
        styleRange = (StyleRange)styleRange.clone();
        styleRange.metrics = null;
        if (styleRange.font == null) {
            styleRange.font = this.getFont(styleRange.fontStyle);
        }
        textLayout.setStyle(styleRange, 0, string.length());
        int n7 = n + Math.max(0, glyphMetrics.width - textLayout.getBounds().width - 8);
        textLayout.draw(gC, n7, n2);
        textLayout.dispose();
    }

    int drawLine(int n, int n2, int n3, GC gC, Color color, Color color2) {
        Object object;
        int n4;
        Object[] objectArray;
        int n5;
        Object object2;
        TextLayout textLayout = this.getTextLayout(n);
        String string = this.content.getLine(n);
        int n6 = this.content.getOffsetAtLine(n);
        int n7 = string.length();
        Rectangle rectangle = this.styledText.getClientArea();
        Color color3 = this.getLineBackground(n, null);
        StyledTextEvent styledTextEvent = this.styledText.getLineBackgroundData(n6, string);
        if (styledTextEvent != null && styledTextEvent.lineBackground != null) {
            color3 = styledTextEvent.lineBackground;
        }
        int n8 = textLayout.getBounds().height;
        int n9 = textLayout.getVerticalIndent();
        if (color3 != null) {
            if (n9 > 0) {
                gC.setBackground(color);
                gC.fillRectangle(rectangle.x, n3, rectangle.width, n9);
            }
            gC.setBackground(color3);
            gC.fillRectangle(rectangle.x, n3 + n9, rectangle.width, n8 - n9);
        } else {
            gC.setBackground(color);
            this.styledText.drawBackground(gC, rectangle.x, n3, rectangle.width, n8);
        }
        gC.setForeground(color2);
        Point[] pointArray = this.intersectingRelativeNonEmptySelections(n6, n6 + n7);
        if (this.styledText.getBlockSelection() || pointArray.length == 0) {
            textLayout.draw(gC, n2, n3);
        } else {
            object2 = this.styledText.getSelectionForeground();
            Color color4 = this.styledText.getSelectionBackground();
            n5 = (this.styledText.getStyle() & 0x10000) != 0 ? 65536 : 131072;
            objectArray = pointArray;
            n4 = objectArray.length;
            for (object = 0; object < n4; ++object) {
                Point point = objectArray[object];
                int n10 = Math.max(0, point.x);
                int n11 = Math.min(n7, point.y);
                int n12 = n5;
                if (point.x <= n7 && n7 < point.y) {
                    n12 |= 0x100000;
                }
                textLayout.draw(gC, n2, n3, n10, n11 - 1, (Color)object2, color4, n12);
            }
        }
        object2 = null;
        int n13 = -1;
        if (this.bullets != null) {
            if (this.bulletsIndices != null) {
                n5 = n - this.topIndex;
                if (0 <= n5 && n5 < 300) {
                    object2 = this.bullets[n5];
                    n13 = this.bulletsIndices[n5];
                }
            } else {
                Bullet[] bulletArray = this.bullets;
                int n14 = bulletArray.length;
                for (n4 = 0; n4 < n14; ++n4) {
                    object2 = bulletArray[n4];
                    Bullet bullet = object2;
                    n13 = ((Bullet)object2).indexOf(n);
                    if (n13 != -1) break;
                }
            }
        }
        if (n13 != -1 && object2 != null) {
            FontMetrics fontMetrics = textLayout.getLineMetrics(0);
            int n15 = fontMetrics.getAscent() + fontMetrics.getLeading();
            if (((Bullet)object2).type == 32) {
                ((Bullet)object2).style.start = n6;
                this.styledText.paintObject(gC, n2, n3, n15, fontMetrics.getDescent(), ((Bullet)object2).style, (Bullet)object2, n13);
            } else {
                this.drawBullet((Bullet)object2, gC, n2, n3, n13, n15, fontMetrics.getDescent());
            }
        }
        TextStyle[] textStyleArray = textLayout.getStyles();
        objectArray = null;
        for (n4 = 0; n4 < textStyleArray.length; ++n4) {
            if (textStyleArray[n4].metrics == null) continue;
            if (objectArray == null) {
                objectArray = textLayout.getRanges();
            }
            object = objectArray[n4 << 1];
            reference var23_31 = objectArray[(n4 << 1) + 1] - object + true;
            Point point = textLayout.getLocation((int)object, false);
            FontMetrics fontMetrics = textLayout.getLineMetrics(textLayout.getLineIndex((int)object));
            StyleRange styleRange = (StyleRange)((StyleRange)textStyleArray[n4]).clone();
            styleRange.start = object + n6;
            styleRange.length = (int)var23_31;
            int n16 = fontMetrics.getAscent() + fontMetrics.getLeading();
            this.styledText.paintObject(gC, point.x + n2, point.y + n3, n16, fontMetrics.getDescent(), styleRange, null, 0);
        }
        this.disposeTextLayout(textLayout);
        return n8;
    }

    private Point[] intersectingRelativeNonEmptySelections(int n, int n2) {
        int[] nArray = this.styledText.getSelectionRanges();
        int n3 = n2 - n;
        ArrayList<Point> arrayList = new ArrayList<Point>();
        for (int i = 0; i < nArray.length; i += 2) {
            Point point = new Point(nArray[i] - n, nArray[i] + nArray[i + 1] - n);
            if (point.x == point.y || point.x > n3 || point.y < 0) continue;
            arrayList.add(point);
        }
        return arrayList.toArray(new Point[arrayList.size()]);
    }

    int getBaseline() {
        return this.ascent;
    }

    int getCachedLineHeight(int n) {
        return this.getLineHeight(n, false);
    }

    Font getFont(int n) {
        switch (n) {
            case 1: {
                if (this.boldFont != null) {
                    return this.boldFont;
                }
                this.boldFont = new Font(this.device, this.getFontData(n));
                return this.boldFont;
            }
            case 2: {
                if (this.italicFont != null) {
                    return this.italicFont;
                }
                this.italicFont = new Font(this.device, this.getFontData(n));
                return this.italicFont;
            }
            case 3: {
                if (this.boldItalicFont != null) {
                    return this.boldItalicFont;
                }
                this.boldItalicFont = new Font(this.device, this.getFontData(n));
                return this.boldItalicFont;
            }
        }
        return this.regularFont;
    }

    FontData[] getFontData(int n) {
        FontData[] fontDataArray;
        FontData[] fontDataArray2 = fontDataArray = this.regularFont.getFontData();
        for (FontData fontData : fontDataArray) {
            fontData.setStyle(n);
        }
        return fontDataArray2;
    }

    int getHeight() {
        int n = this.getLineHeight();
        if (this.styledText.isFixedLineHeight()) {
            return this.lineCount * n + this.styledText.topMargin + this.styledText.bottomMargin;
        }
        int n2 = 0;
        int n3 = this.styledText.getWrapWidth();
        for (int i = 0; i < this.lineCount; ++i) {
            LineSizeInfo lineSizeInfo = this.getLineSize(i);
            int n4 = lineSizeInfo.height;
            if (lineSizeInfo.needsRecalculateHeight()) {
                if (n3 > 0) {
                    int n5 = this.content.getLine(i).length();
                    n4 = (n5 * this.averageCharWidth / n3 + 1) * n;
                } else {
                    n4 = n;
                }
            }
            n2 += n4;
        }
        return n2 + this.styledText.topMargin + this.styledText.bottomMargin;
    }

    boolean hasLink(int n) {
        String string;
        if (n == -1) {
            return false;
        }
        int n2 = this.content.getLineAtOffset(n);
        int n3 = this.content.getOffsetAtLine(n2);
        StyledTextEvent styledTextEvent = this.styledText.getLineStyleData(n3, string = this.content.getLine(n2));
        if (styledTextEvent != null) {
            StyleRange[] styleRangeArray = styledTextEvent.styles;
            if (styleRangeArray != null) {
                int[] nArray = styledTextEvent.ranges;
                if (nArray != null) {
                    for (int i = 0; i < nArray.length; i += 2) {
                        if (nArray[i] > n || n >= nArray[i] + nArray[i + 1] || !styleRangeArray[i >> 1].underline || styleRangeArray[i >> 1].underlineStyle != 4) continue;
                        return true;
                    }
                } else {
                    for (StyleRange styleRange : styleRangeArray) {
                        if (styleRange.start > n || n >= styleRange.start + styleRange.length || !styleRange.underline || styleRange.underlineStyle != 4) continue;
                        return true;
                    }
                }
            }
        } else if (this.ranges != null) {
            int n4 = this.styleCount << 1;
            int n5 = this.getRangeIndex(n, -1, n4);
            if (n5 >= n4) {
                return false;
            }
            int n6 = this.ranges[n5];
            int n7 = this.ranges[n5 + 1];
            StyleRange styleRange = this.styles[n5 >> 1];
            if (n6 <= n && n < n6 + n7 && styleRange.underline && styleRange.underlineStyle == 4) {
                return true;
            }
        }
        return false;
    }

    int getLineAlignment(int n, int n2) {
        if (this.lines == null) {
            return n2;
        }
        LineInfo lineInfo = this.lines[n];
        if (lineInfo != null && (lineInfo.flags & 2) != 0) {
            return lineInfo.alignment;
        }
        return n2;
    }

    Color getLineBackground(int n, Color color) {
        if (this.lines == null) {
            return color;
        }
        LineInfo lineInfo = this.lines[n];
        if (lineInfo != null && (lineInfo.flags & 1) != 0) {
            return lineInfo.background;
        }
        return color;
    }

    Bullet getLineBullet(int n, Bullet bullet) {
        if (this.bullets == null) {
            return bullet;
        }
        if (this.bulletsIndices != null) {
            return bullet;
        }
        for (Bullet bullet2 : this.bullets) {
            if (bullet2.indexOf(n) == -1) continue;
            return bullet2;
        }
        return bullet;
    }

    int getLineHeight() {
        return this.ascent + this.descent;
    }

    int getLineHeight(int n) {
        return this.getLineHeight(n, true);
    }

    /*
     * Exception decompiling
     */
    int getLineHeight(int var1, boolean var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl34 : ALOAD_3 - null : trying to set 0 previously set to 1
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private int getLineSpacing(int n) {
        Integer n2;
        if (this.styledText.lineSpacing > 0) {
            return this.styledText.lineSpacing;
        }
        if (this.lineSpacingProvider != null && (n2 = this.lineSpacingProvider.getLineSpacing(n)) != null) {
            return n2;
        }
        return 0;
    }

    private StyleRange[] getStylesForLine(int n) {
        int n2 = this.styledText.getOffsetAtLine(n);
        int n3 = this.styledText.getLine(n).length();
        return this.getStyleRanges(n2, n3, false);
    }

    int getLineIndent(int n, int n2) {
        if (this.lines == null) {
            return n2;
        }
        LineInfo lineInfo = this.lines[n];
        if (lineInfo != null && (lineInfo.flags & 4) != 0) {
            return lineInfo.indent;
        }
        return n2;
    }

    int getLineVerticalIndent(int n) {
        if (this.lines == null) {
            return 0;
        }
        LineInfo lineInfo = this.lines[n];
        if (lineInfo != null && (lineInfo.flags & 0x200) != 0) {
            return lineInfo.verticalIndent;
        }
        return 0;
    }

    int getLineWrapIndent(int n, int n2) {
        if (this.lines == null) {
            return n2;
        }
        LineInfo lineInfo = this.lines[n];
        if (lineInfo != null && (lineInfo.flags & 0x80) != 0) {
            return lineInfo.wrapIndent;
        }
        return n2;
    }

    boolean getLineJustify(int n, boolean bl) {
        if (this.lines == null) {
            return bl;
        }
        LineInfo lineInfo = this.lines[n];
        if (lineInfo != null && (lineInfo.flags & 8) != 0) {
            return lineInfo.justify;
        }
        return bl;
    }

    int[] getLineTabStops(int n, int[] nArray) {
        if (this.lines == null) {
            return nArray;
        }
        LineInfo lineInfo = this.lines[n];
        if (lineInfo != null && (lineInfo.flags & 0x40) != 0) {
            return lineInfo.tabStops;
        }
        return nArray;
    }

    StyledTextLineSpacingProvider getLineSpacingProvider() {
        return this.lineSpacingProvider;
    }

    int getRangeIndex(int n, int n2, int n3) {
        if (this.styleCount == 0) {
            return 0;
        }
        if (this.ranges != null) {
            while (n3 - n2 > 2) {
                int n4 = (n3 + n2) / 2 / 2 * 2;
                int n5 = this.ranges[n4] + this.ranges[n4 + 1];
                if (n5 > n) {
                    n3 = n4;
                    continue;
                }
                n2 = n4;
            }
        } else {
            while (n3 - n2 > 1) {
                int n6 = (n3 + n2) / 2;
                int n7 = this.styles[n6].start + this.styles[n6].length;
                if (n7 > n) {
                    n3 = n6;
                    continue;
                }
                n2 = n6;
            }
        }
        return n3;
    }

    int[] getRanges(int n, int n2) {
        int[] nArray;
        if (n2 == 0) {
            return null;
        }
        int n3 = n + n2 - 1;
        if (this.ranges != null) {
            int n4 = this.styleCount << 1;
            int n5 = this.getRangeIndex(n, -1, n4);
            if (n5 >= n4) {
                return null;
            }
            if (this.ranges[n5] > n3) {
                return null;
            }
            int n6 = Math.min(n4 - 2, this.getRangeIndex(n3, n5 - 1, n4));
            if (this.ranges[n6] > n3) {
                n6 = Math.max(n5, n6 - 2);
            }
            nArray = new int[n6 - n5 + 2];
            System.arraycopy(this.ranges, n5, nArray, 0, nArray.length);
        } else {
            int n7 = this.getRangeIndex(n, -1, this.styleCount);
            if (n7 >= this.styleCount) {
                return null;
            }
            if (this.styles[n7].start > n3) {
                return null;
            }
            int n8 = Math.min(this.styleCount - 1, this.getRangeIndex(n3, n7 - 1, this.styleCount));
            if (this.styles[n8].start > n3) {
                n8 = Math.max(n7, n8 - 1);
            }
            nArray = new int[n8 - n7 + 1 << 1];
            int n9 = n7;
            int n10 = 0;
            while (n9 <= n8) {
                StyleRange styleRange = this.styles[n9];
                nArray[n10] = styleRange.start;
                nArray[n10 + 1] = styleRange.length;
                ++n9;
                n10 += 2;
            }
        }
        if (n > nArray[0]) {
            nArray[1] = nArray[0] + nArray[1] - n;
            nArray[0] = n;
        }
        if (n3 < nArray[nArray.length - 2] + nArray[nArray.length - 1] - 1) {
            nArray[nArray.length - 1] = n3 - nArray[nArray.length - 2] + 1;
        }
        return nArray;
    }

    StyleRange[] getStyleRanges(int n, int n2, boolean bl) {
        StyleRange[] styleRangeArray;
        int n3;
        if (n2 == 0) {
            return null;
        }
        int n4 = n + n2 - 1;
        if (this.ranges != null) {
            n3 = this.styleCount << 1;
            int n5 = this.getRangeIndex(n, -1, n3);
            if (n5 >= n3) {
                return null;
            }
            if (this.ranges[n5] > n4) {
                return null;
            }
            int n6 = Math.min(n3 - 2, this.getRangeIndex(n4, n5 - 1, n3));
            if (this.ranges[n6] > n4) {
                n6 = Math.max(n5, n6 - 2);
            }
            styleRangeArray = new StyleRange[(n6 - n5 >> 1) + 1];
            if (bl) {
                int n7 = n5;
                int n8 = 0;
                while (n7 <= n6) {
                    styleRangeArray[n8] = (StyleRange)this.styles[n7 >> 1].clone();
                    styleRangeArray[n8].start = this.ranges[n7];
                    styleRangeArray[n8].length = this.ranges[n7 + 1];
                    n7 += 2;
                    ++n8;
                }
            } else {
                System.arraycopy(this.styles, n5 >> 1, styleRangeArray, 0, styleRangeArray.length);
            }
        } else {
            n3 = this.getRangeIndex(n, -1, this.styleCount);
            if (n3 >= this.styleCount) {
                return null;
            }
            if (this.styles[n3].start > n4) {
                return null;
            }
            int n9 = Math.min(this.styleCount - 1, this.getRangeIndex(n4, n3 - 1, this.styleCount));
            if (this.styles[n9].start > n4) {
                n9 = Math.max(n3, n9 - 1);
            }
            styleRangeArray = new StyleRange[n9 - n3 + 1];
            System.arraycopy(this.styles, n3, styleRangeArray, 0, styleRangeArray.length);
        }
        if (bl || this.ranges == null) {
            StyleRange styleRange = styleRangeArray[0];
            if (n > styleRange.start) {
                styleRange = styleRangeArray[0] = (StyleRange)styleRange.clone();
                styleRange.length = styleRange.start + styleRange.length - n;
                styleRange.start = n;
            }
            styleRange = styleRangeArray[styleRangeArray.length - 1];
            if (n4 < styleRange.start + styleRange.length - 1) {
                StyleRange styleRange2 = (StyleRange)styleRange.clone();
                styleRangeArray[styleRangeArray.length - 1] = styleRange2;
                styleRange = styleRange2;
                styleRange.length = n4 - styleRange.start + 1;
            }
        }
        return styleRangeArray;
    }

    StyleRange getStyleRange(StyleRange styleRange) {
        StyleRange styleRange2;
        if (styleRange.underline && styleRange.underlineStyle == 4) {
            this.hasLinks = true;
        }
        if (styleRange.start == 0 && styleRange.length == 0 && styleRange.fontStyle == 0) {
            return styleRange;
        }
        StyleRange styleRange3 = styleRange2 = (StyleRange)styleRange.clone();
        StyleRange styleRange4 = styleRange2;
        boolean bl = false;
        styleRange3.length = 0;
        styleRange2.start = 0;
        styleRange4.fontStyle = 0;
        if (styleRange4.font == null) {
            styleRange4.font = this.getFont(styleRange.fontStyle);
        }
        return styleRange4;
    }

    TextLayout getTextLayout(int n) {
        TextLayout textLayout;
        block4: {
            if (this.lineSpacingProvider == null) {
                return this.getTextLayout(n, this.styledText.getOrientation(), this.styledText.getWrapWidth(), this.styledText.lineSpacing);
            }
            int n2 = this.styledText.lineSpacing;
            Integer n3 = this.lineSpacingProvider.getLineSpacing(n);
            if (n3 != null && n3 >= 0) {
                n2 = n3;
            }
            StyledTextRenderer styledTextRenderer = this;
            int n4 = n;
            if (n2 == null) {
                return this.getTextLayout(n, this.styledText.getOrientation(), this.styledText.getWrapWidth(), n2);
            }
            textLayout = this.getTextLayout(n, this.styledText.getOrientation(), this.styledText.getWrapWidth(), this.styledText.lineSpacing);
            if (textLayout.getSpacing() == n2) break block4;
            textLayout.setSpacing(n2);
            if (this.lineSpacingComputing) {
                return textLayout;
            }
            this.lineSpacingComputing = true;
            this.styledText.resetCache(n, 1);
            this.styledText.setCaretLocations();
            this.styledText.redraw();
            this.lineSpacingComputing = false;
        }
        return textLayout;
    }

    /*
     * WARNING - void declaration
     */
    TextLayout getTextLayout(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        IME iME;
        int n7;
        int n8;
        TextLayout textLayout = null;
        if (this.styledText != null) {
            int n9;
            int n10;
            int n11;
            int n12 = n11 = this.styledText.topIndex > 0 ? this.styledText.topIndex - 1 : 0;
            if (this.layouts == null || n11 != this.topIndex) {
                int n13;
                TextLayout[] textLayoutArray = new TextLayout[300];
                if (this.layouts != null) {
                    for (n13 = 0; n13 < this.layouts.length; ++n13) {
                        if (this.layouts[n13] == null) continue;
                        n10 = n13 + this.topIndex - n11;
                        if (0 <= n10 && n10 < textLayoutArray.length) {
                            textLayoutArray[n10] = this.layouts[n13];
                            continue;
                        }
                        this.layouts[n13].dispose();
                    }
                }
                if (this.bullets != null && this.bulletsIndices != null && n11 != this.topIndex) {
                    int n14;
                    n13 = n11 - this.topIndex;
                    if (n13 > 0) {
                        if (n13 < this.bullets.length) {
                            System.arraycopy(this.bullets, n13, this.bullets, 0, this.bullets.length - n13);
                            System.arraycopy(this.bulletsIndices, n13, this.bulletsIndices, 0, this.bulletsIndices.length - n13);
                        }
                        n14 = n10 = Math.max(0, this.bullets.length - n13);
                        while (n10 < this.bullets.length) {
                            this.bullets[n10] = null;
                            ++n10;
                        }
                    } else {
                        if (-n13 < this.bullets.length) {
                            System.arraycopy(this.bullets, 0, this.bullets, -n13, this.bullets.length + n13);
                            System.arraycopy(this.bulletsIndices, 0, this.bulletsIndices, -n13, this.bulletsIndices.length + n13);
                        }
                        n10 = Math.min(this.bullets.length, -n13);
                        for (n14 = 0; n14 < n10; ++n14) {
                            this.bullets[n14] = null;
                        }
                    }
                }
                this.topIndex = n11;
                this.layouts = textLayoutArray;
            }
            if (this.layouts != null && 0 <= (n9 = n - n11) && n9 < this.layouts.length) {
                textLayout = this.layouts[n9];
                if (textLayout != null) {
                    if (n < this.lineSizes.length && this.getLineSize(n).canLayout()) {
                        return textLayout;
                    }
                } else {
                    TextLayout textLayout2;
                    TextLayout[] textLayoutArray = this.layouts;
                    n10 = n9;
                    textLayoutArray[n10] = textLayout2 = new TextLayout(this.device);
                    textLayout = textLayout2;
                }
            }
        }
        if (textLayout == null) {
            textLayout = new TextLayout(this.device);
        }
        String string = this.content.getLine(n);
        int n15 = this.content.getOffsetAtLine(n);
        int[] nArray = null;
        char[] cArray = null;
        int n16 = 0;
        int n17 = 0;
        int n18 = 0;
        int n19 = 16384;
        int n20 = n2;
        boolean bl = false;
        int[] nArray2 = new int[]{this.tabWidth};
        Bullet bullet = null;
        int[] nArray3 = null;
        StyleRange[] styleRangeArray = null;
        int n21 = 0;
        int n22 = 0;
        StyledTextEvent styledTextEvent = null;
        if (this.styledText != null) {
            styledTextEvent = this.styledText.getBidiSegments(n15, string);
            if (styledTextEvent != null) {
                nArray = styledTextEvent.segments;
                cArray = styledTextEvent.segmentsChars;
            }
            styledTextEvent = this.styledText.getLineStyleData(n15, string);
            n16 = this.styledText.indent;
            n17 = this.styledText.wrapIndent;
            n19 = this.styledText.alignment;
            if (this.styledText.isAutoDirection()) {
                n20 = 0x6000000;
            } else if ((this.styledText.getStyle() & Integer.MIN_VALUE) != 0) {
                n20 = n2 == 0x4000000 ? 0x2000000 : 0x4000000;
            }
            bl = this.styledText.justify;
            if (this.styledText.tabs != null) {
                nArray2 = this.styledText.tabs;
            }
        }
        if (styledTextEvent != null) {
            int n23;
            n16 = styledTextEvent.indent;
            n18 = styledTextEvent.verticalIndent;
            n17 = styledTextEvent.wrapIndent;
            n19 = styledTextEvent.alignment;
            bl = styledTextEvent.justify;
            bullet = styledTextEvent.bullet;
            nArray3 = styledTextEvent.ranges;
            styleRangeArray = styledTextEvent.styles;
            if (styledTextEvent.tabStops != null) {
                nArray2 = styledTextEvent.tabStops;
            }
            if (styleRangeArray != null) {
                n22 = styleRangeArray.length;
                if (this.styledText.isFixedLineHeight()) {
                    for (n23 = 0; n23 < n22; ++n23) {
                        if (!styleRangeArray[n23].isVariableHeight()) continue;
                        this.styledText.hasStyleWithVariableHeight = true;
                        this.styledText.verticalScrollOffset = -1;
                        this.styledText.redraw();
                        break;
                    }
                }
            }
            if (this.bullets == null || this.bulletsIndices == null) {
                this.bullets = new Bullet[300];
                this.bulletsIndices = new int[300];
            }
            if (0 <= (n23 = n - this.topIndex) && n23 < 300) {
                this.bullets[n23] = bullet;
                this.bulletsIndices[n23] = styledTextEvent.bulletIndex;
            }
        } else {
            Bullet[] bulletArray;
            if (this.lines != null && (bulletArray = this.lines[n]) != null) {
                if ((bulletArray.flags & 4) != 0) {
                    n16 = bulletArray.indent;
                }
                if ((bulletArray.flags & 0x200) != 0) {
                    n18 = bulletArray.verticalIndent;
                }
                if ((bulletArray.flags & 0x80) != 0) {
                    n17 = bulletArray.wrapIndent;
                }
                if ((bulletArray.flags & 2) != 0) {
                    n19 = bulletArray.alignment;
                }
                if ((bulletArray.flags & 8) != 0) {
                    bl = bulletArray.justify;
                }
                if ((bulletArray.flags & 0x20) != 0) {
                    nArray = bulletArray.segments;
                }
                if ((bulletArray.flags & 0x100) != 0) {
                    cArray = bulletArray.segmentsChars;
                }
                if ((bulletArray.flags & 0x40) != 0) {
                    nArray2 = bulletArray.tabStops;
                }
            }
            if (this.bulletsIndices != null) {
                this.bullets = null;
                this.bulletsIndices = null;
            }
            if (this.bullets != null) {
                for (Bullet object2 : this.bullets) {
                    if (object2.indexOf(n) == -1) continue;
                    bullet = object2;
                    break;
                }
            }
            nArray3 = this.ranges;
            styleRangeArray = this.styles;
            n22 = this.styleCount;
            n21 = nArray3 != null ? this.getRangeIndex(n15, -1, n22 << 1) : this.getRangeIndex(n15, -1, n22);
        }
        if (bullet != null) {
            StyleRange styleRange = bullet.style;
            GlyphMetrics glyphMetrics = styleRange.metrics;
            n16 += glyphMetrics.width;
        }
        ArrayList<StyleEntry> arrayList = new ArrayList<StyleEntry>();
        int n24 = 0;
        int n25 = string.length();
        if (styleRangeArray != null) {
            if (nArray3 != null) {
                int n26 = n22 << 1;
                for (n8 = n21; n8 < n26; n8 += 2) {
                    int n13;
                    if (n15 > nArray3[n8]) {
                        n7 = 0;
                        n13 = Math.min(n25, nArray3[n8 + 1] - n15 + nArray3[n8]);
                    } else {
                        n7 = nArray3[n8] - n15;
                        n13 = Math.min(n25, n7 + nArray3[n8 + 1]);
                    }
                    if (n7 < n25) {
                        if (n24 < n7) {
                            arrayList.add(new StyleEntry(null, n24, n7 - 1));
                        }
                        StyleRange n35 = this.getStyleRange(styleRangeArray[n8 >> 1]);
                        int n14 = Math.max(n7, Math.min(n25, n13 + 1));
                        if (n35.metrics != null && string.substring(n7, n14).contains("\t")) {
                            string = string.substring(0, n7) + string.substring(n7, n14).replace('\t', ' ') + (n13 < string.length() ? string.substring(n13 + 1, string.length()) : "");
                        }
                        arrayList.add(new StyleEntry(n35, n7, n13));
                        n24 = Math.max(n24, n13);
                        continue;
                    }
                    break;
                }
            } else {
                void n33;
                int i = n21;
                while (n33 < n22) {
                    if (n15 > styleRangeArray[n33].start) {
                        n8 = 0;
                        n7 = Math.min(n25, styleRangeArray[n33].length - n15 + styleRangeArray[n33].start);
                    } else {
                        n8 = styleRangeArray[n33].start - n15;
                        n7 = Math.min(n25, n8 + styleRangeArray[n33].length);
                    }
                    if (n8 < n25) {
                        if (n24 < n8) {
                            arrayList.add(new StyleEntry(null, n24, n8 - 1));
                        }
                        StyleRange styleRange = this.getStyleRange(styleRangeArray[n33]);
                        int n23 = Math.max(n8, Math.min(n25, n7 + 1));
                        if (styleRange.metrics != null && string.substring(n8, n23).contains("\t")) {
                            string = string.substring(0, n8) + string.substring(n8, n23).replace('\t', ' ') + (n7 < string.length() ? string.substring(n7 + 1, string.length()) : "");
                        }
                        arrayList.add(new StyleEntry(styleRange, n8, n7));
                        n24 = Math.max(n24, n7);
                        ++n33;
                        continue;
                    }
                    break;
                }
            }
        }
        if (n24 < n25) {
            arrayList.add(new StyleEntry(null, n24, n25));
        }
        textLayout.setFont(this.regularFont);
        textLayout.setAscent(this.ascent);
        textLayout.setDescent(this.descent);
        textLayout.setText(string);
        textLayout.setOrientation(n2);
        textLayout.setSegments(nArray);
        textLayout.setSegmentsChars(cArray);
        textLayout.setWidth(n3);
        textLayout.setSpacing(n4);
        textLayout.setTabs(nArray2);
        textLayout.setDefaultTabWidth(this.tabLength);
        textLayout.setIndent(n16);
        textLayout.setVerticalIndent(n18);
        textLayout.setWrapIndent(n17);
        textLayout.setAlignment(n19);
        textLayout.setJustify(bl);
        textLayout.setTextDirection(n20);
        for (StyleEntry styleEntry : arrayList) {
            textLayout.setStyle(styleEntry.style, styleEntry.start, styleEntry.end);
        }
        if (this.styledText != null && this.styledText.ime != null && (n8 = (iME = this.styledText.ime).getCompositionOffset()) != -1) {
            int n26;
            n7 = iME.getCommitCount();
            n6 = iME.getText().length();
            if (n6 != n7 && (n26 = this.content.getLineAtOffset(n8)) == n) {
                TextStyle textStyle;
                int n27;
                int[] nArray4 = iME.getRanges();
                TextStyle[] textStyleArray = iME.getStyles();
                if (nArray4.length > 0) {
                    for (n5 = 0; n5 < textStyleArray.length; ++n5) {
                        n27 = nArray4[n5 * 2] - n15;
                        int n28 = nArray4[n5 * 2 + 1] - n15;
                        textStyle = textStyleArray[n5];
                        for (int i = n27; i <= n28 && 0 <= i && i < n25; ++i) {
                            TextStyle textStyle2 = textLayout.getStyle(StyledTextRenderer.cap(textLayout, i));
                            if (textStyle2 == null && i > 0) {
                                textStyle2 = textLayout.getStyle(StyledTextRenderer.cap(textLayout, i - 1));
                            }
                            if (textStyle2 == null && i + 1 < n25) {
                                textStyle2 = textLayout.getStyle(StyledTextRenderer.cap(textLayout, i + 1));
                            }
                            if (textStyle2 == null) {
                                textLayout.setStyle(textStyle, i, i);
                                continue;
                            }
                            TextStyle textStyle3 = new TextStyle(textStyle);
                            if (textStyle3.font == null) {
                                textStyle3.font = textStyle2.font;
                            }
                            if (textStyle3.foreground == null) {
                                textStyle3.foreground = textStyle2.foreground;
                            }
                            if (textStyle3.background == null) {
                                textStyle3.background = textStyle2.background;
                            }
                            textLayout.setStyle(textStyle3, i, i);
                        }
                    }
                } else {
                    n5 = n8 - n15;
                    n27 = n5 + n6 - 1;
                    TextStyle textStyle4 = textLayout.getStyle(StyledTextRenderer.cap(textLayout, n5));
                    if (textStyle4 == null) {
                        if (n5 > 0) {
                            textStyle4 = textLayout.getStyle(StyledTextRenderer.cap(textLayout, n5 - 1));
                        }
                        if (textStyle4 == null && n27 + 1 < n25) {
                            textStyle4 = textLayout.getStyle(StyledTextRenderer.cap(textLayout, n27 + 1));
                        }
                        if (textStyle4 != null) {
                            textStyle = new TextStyle();
                            textStyle.font = textStyle4.font;
                            textStyle.foreground = textStyle4.foreground;
                            textStyle.background = textStyle4.background;
                            textLayout.setStyle(textStyle, n5, n27);
                        }
                    }
                }
            }
        }
        if (this.styledText != null && this.styledText.isFixedLineHeight()) {
            int n29;
            int n30 = -1;
            n8 = textLayout.getLineCount();
            n7 = this.getLineHeight();
            for (n6 = 0; n6 < n8; ++n6) {
                int n31 = textLayout.getLineBounds((int)n6).height;
                if (n31 <= n7) continue;
                n7 = n31;
                n29 = n6;
            }
            if (n29 != -1) {
                FontMetrics fontMetrics = textLayout.getLineMetrics(n29);
                this.ascent = fontMetrics.getAscent() + fontMetrics.getLeading();
                this.descent = fontMetrics.getDescent();
                if (this.layouts != null) {
                    for (TextLayout textLayout2 : this.layouts) {
                        if (textLayout2 == null || textLayout2 == textLayout) continue;
                        textLayout2.setAscent(this.ascent);
                        textLayout2.setDescent(this.descent);
                    }
                }
                this.styledText.calculateScrollBars();
                if (this.styledText.verticalScrollOffset != 0) {
                    int n32 = this.styledText.topIndex;
                    int n33 = this.styledText.topIndexY;
                    int n34 = this.getLineHeight();
                    n5 = n33 >= 0 ? (n32 - 1) * n34 + n34 - n33 : n32 * n34 - n33;
                    this.styledText.scrollVertical(n5 - this.styledText.verticalScrollOffset, true);
                }
                if (this.styledText.isBidiCaret()) {
                    this.styledText.createCaretBitmaps();
                }
                this.styledText.caretDirection = 0;
                this.styledText.setCaretLocations();
                this.styledText.redraw();
            }
        }
        return textLayout;
    }

    int getWidth() {
        return this.maxWidth;
    }

    void reset() {
        if (this.layouts != null) {
            for (TextLayout textLayout : this.layouts) {
                if (textLayout == null) continue;
                textLayout.dispose();
            }
            this.layouts = null;
        }
        this.topIndex = -1;
        boolean bl = false;
        this.lineCount = 0;
        this.styleCount = 0;
        this.stylesSetCount = 0;
        this.ranges = null;
        this.styles = null;
        this.stylesSet = null;
        this.lines = null;
        this.lineSizes = null;
        this.bullets = null;
        this.bulletsIndices = null;
        this.redrawLines = null;
        this.hasLinks = false;
    }

    void reset(int n, int n2) {
        int n3 = n + n2;
        if (n < 0 || n3 > this.lineSizes.length) {
            return;
        }
        TreeSet<Integer> treeSet = new TreeSet<Integer>();
        for (int i = n; i < n3; ++i) {
            treeSet.add(i);
        }
        this.reset(treeSet);
    }

    void reset(Set set) {
        if (set == null || set.isEmpty()) {
            return;
        }
        int n = 0;
        for (Object object : set) {
            if ((Integer)object < 0 && (Integer)object >= this.lineCount) continue;
            ++n;
            this.getLineSize((Integer)object).resetSize();
        }
        if (this.linesInAverageLineHeight > n) {
            this.linesInAverageLineHeight -= n;
        } else {
            this.linesInAverageLineHeight = 0;
            this.averageLineHeight = 0.0f;
        }
        if (set.contains(this.maxWidthLineIndex)) {
            this.maxWidth = 0;
            this.maxWidthLineIndex = -1;
            if (n != this.lineCount) {
                for (int i = 0; i < this.lineCount; ++i) {
                    Object object;
                    object = this.getLineSize(i);
                    if (((LineSizeInfo)object).width <= this.maxWidth) continue;
                    this.maxWidth = ((LineSizeInfo)object).width;
                    this.maxWidthLineIndex = i;
                }
            }
        }
    }

    void setContent(StyledTextContent styledTextContent) {
        this.reset();
        this.content = styledTextContent;
        this.lineCount = styledTextContent.getLineCount();
        this.lineSizes = new LineSizeInfo[this.lineCount];
        this.maxWidth = 0;
        this.maxWidthLineIndex = -1;
        this.reset(0, this.lineCount);
    }

    void setFont(Font font, int n) {
        StringBuilder stringBuilder;
        TextLayout textLayout = new TextLayout(this.device);
        textLayout.setFont(this.regularFont);
        this.tabLength = n;
        if (font != null) {
            if (this.boldFont != null) {
                this.boldFont.dispose();
            }
            if (this.italicFont != null) {
                this.italicFont.dispose();
            }
            if (this.boldItalicFont != null) {
                this.boldItalicFont.dispose();
            }
            stringBuilder = null;
            this.boldItalicFont = stringBuilder;
            this.italicFont = stringBuilder;
            this.boldFont = stringBuilder;
            this.regularFont = font;
            textLayout.setText("    ");
            textLayout.setFont(font);
            textLayout.setStyle(new TextStyle(this.getFont(0), null, null), 0, 0);
            textLayout.setStyle(new TextStyle(this.getFont(1), null, null), 1, 1);
            textLayout.setStyle(new TextStyle(this.getFont(2), null, null), 2, 2);
            textLayout.setStyle(new TextStyle(this.getFont(3), null, null), 3, 3);
            FontMetrics fontMetrics = textLayout.getLineMetrics(0);
            this.ascent = fontMetrics.getAscent() + fontMetrics.getLeading();
            this.descent = fontMetrics.getDescent();
            this.boldFont.dispose();
            this.italicFont.dispose();
            this.boldItalicFont.dispose();
            Object var6_8 = null;
            this.boldItalicFont = var6_8;
            this.italicFont = var6_8;
            this.boldFont = var6_8;
        }
        textLayout.dispose();
        textLayout = new TextLayout(this.device);
        textLayout.setFont(this.regularFont);
        stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(' ');
        }
        textLayout.setText(stringBuilder.toString());
        this.tabWidth = textLayout.getBounds().width;
        textLayout.dispose();
        if (this.styledText != null) {
            GC gC = new GC(this.styledText);
            this.averageCharWidth = (int)gC.getFontMetrics().getAverageCharacterWidth();
            this.fixedPitch = gC.stringExtent((String)"l").x == gC.stringExtent((String)"W").x;
            gC.dispose();
        }
    }

    void setLineAlignment(int n, int n2, int n3) {
        if (this.lines == null) {
            this.lines = new LineInfo[this.lineCount];
        }
        for (int i = n; i < n + n2; ++i) {
            if (this.lines[i] == null) {
                this.lines[i] = new LineInfo();
            }
            LineInfo lineInfo = this.lines[i];
            lineInfo.flags |= 2;
            this.lines[i].alignment = n3;
        }
    }

    void setLineBackground(int n, int n2, Color color) {
        if (this.lines == null) {
            this.lines = new LineInfo[this.lineCount];
        }
        for (int i = n; i < n + n2; ++i) {
            if (this.lines[i] == null) {
                this.lines[i] = new LineInfo();
            }
            LineInfo lineInfo = this.lines[i];
            lineInfo.flags |= 1;
            this.lines[i].background = color;
        }
    }

    void setLineBullet(int n, int n2, Bullet bullet) {
        int n3;
        if (this.bulletsIndices != null) {
            this.bulletsIndices = null;
            this.bullets = null;
        }
        if (this.bullets == null) {
            if (bullet == null) {
                return;
            }
            Bullet[] bulletArray = new Bullet[1];
            this.bullets = bulletArray;
            bulletArray[0] = bullet;
        }
        for (n3 = 0; n3 < this.bullets.length && bullet != this.bullets[n3]; ++n3) {
        }
        if (bullet != null) {
            if (n3 == this.bullets.length) {
                Bullet[] bulletArray = new Bullet[this.bullets.length + 1];
                System.arraycopy(this.bullets, 0, bulletArray, 0, this.bullets.length);
                bulletArray[n3] = bullet;
                this.bullets = bulletArray;
            }
            bullet.addIndices(n, n2);
        } else {
            this.updateBullets(n, n2, 0, false);
            this.styledText.redrawLinesBullet(this.redrawLines);
            this.redrawLines = null;
        }
    }

    void setLineIndent(int n, int n2, int n3) {
        if (this.lines == null) {
            this.lines = new LineInfo[this.lineCount];
        }
        for (int i = n; i < n + n2; ++i) {
            if (this.lines[i] == null) {
                this.lines[i] = new LineInfo();
            }
            LineInfo lineInfo = this.lines[i];
            lineInfo.flags |= 4;
            this.lines[i].indent = n3;
        }
    }

    void setLineVerticalIndent(int n, int n2) {
        if (this.lines == null) {
            this.lines = new LineInfo[this.lineCount];
        }
        if (this.lines[n] == null) {
            this.lines[n] = new LineInfo();
        }
        LineInfo lineInfo = this.lines[n];
        lineInfo.flags |= 0x200;
        int n3 = n2 - this.lines[n].verticalIndent;
        this.lines[n].verticalIndent = n2;
        LineSizeInfo lineSizeInfo = this.getLineSize(n);
        if (!lineSizeInfo.needsRecalculateHeight()) {
            LineSizeInfo lineSizeInfo2 = lineSizeInfo;
            lineSizeInfo2.height += n3;
        }
    }

    void setLineWrapIndent(int n, int n2, int n3) {
        if (this.lines == null) {
            this.lines = new LineInfo[this.lineCount];
        }
        for (int i = n; i < n + n2; ++i) {
            if (this.lines[i] == null) {
                this.lines[i] = new LineInfo();
            }
            LineInfo lineInfo = this.lines[i];
            lineInfo.flags |= 0x80;
            this.lines[i].wrapIndent = n3;
        }
    }

    void setLineJustify(int n, int n2, boolean bl) {
        if (this.lines == null) {
            this.lines = new LineInfo[this.lineCount];
        }
        for (int i = n; i < n + n2; ++i) {
            if (this.lines[i] == null) {
                this.lines[i] = new LineInfo();
            }
            LineInfo lineInfo = this.lines[i];
            lineInfo.flags |= 8;
            this.lines[i].justify = bl;
        }
    }

    void setLineSegments(int n, int n2, int[] nArray) {
        if (this.lines == null) {
            this.lines = new LineInfo[this.lineCount];
        }
        for (int i = n; i < n + n2; ++i) {
            if (this.lines[i] == null) {
                this.lines[i] = new LineInfo();
            }
            LineInfo lineInfo = this.lines[i];
            lineInfo.flags |= 0x20;
            this.lines[i].segments = nArray;
        }
    }

    void setLineSegmentChars(int n, int n2, char[] cArray) {
        if (this.lines == null) {
            this.lines = new LineInfo[this.lineCount];
        }
        for (int i = n; i < n + n2; ++i) {
            if (this.lines[i] == null) {
                this.lines[i] = new LineInfo();
            }
            LineInfo lineInfo = this.lines[i];
            lineInfo.flags |= 0x100;
            this.lines[i].segmentsChars = cArray;
        }
    }

    void setLineTabStops(int n, int n2, int[] nArray) {
        if (this.lines == null) {
            this.lines = new LineInfo[this.lineCount];
        }
        for (int i = n; i < n + n2; ++i) {
            if (this.lines[i] == null) {
                this.lines[i] = new LineInfo();
            }
            LineInfo lineInfo = this.lines[i];
            lineInfo.flags |= 0x40;
            this.lines[i].tabStops = nArray;
        }
    }

    void setLineSpacingProvider(StyledTextLineSpacingProvider styledTextLineSpacingProvider) {
        this.lineSpacingProvider = styledTextLineSpacingProvider;
    }

    void setStyleRanges(int[] nArray, StyleRange[] styleRangeArray) {
        int n;
        int n2;
        int n3;
        if (styleRangeArray == null) {
            boolean bl = false;
            this.styleCount = 0;
            this.stylesSetCount = 0;
            this.ranges = null;
            this.styles = null;
            this.stylesSet = null;
            this.hasLinks = false;
            return;
        }
        if (nArray == null) {
            nArray = new int[styleRangeArray.length << 1];
            StyleRange[] styleRangeArray2 = new StyleRange[styleRangeArray.length];
            if (this.stylesSet == null) {
                this.stylesSet = new StyleRange[4];
            }
            n3 = 0;
            for (n2 = 0; n2 < styleRangeArray.length; ++n2) {
                StyleRange styleRange = styleRangeArray[n2];
                nArray[n3++] = styleRange.start;
                nArray[n3++] = styleRange.length;
                for (n = 0; n < this.stylesSetCount && !this.stylesSet[n].similarTo(styleRange); ++n) {
                }
                if (n == this.stylesSetCount) {
                    if (this.stylesSetCount == this.stylesSet.length) {
                        StyleRange[] styleRangeArray3 = new StyleRange[this.stylesSetCount + 4];
                        System.arraycopy(this.stylesSet, 0, styleRangeArray3, 0, this.stylesSetCount);
                        this.stylesSet = styleRangeArray3;
                    }
                    this.stylesSet[this.stylesSetCount++] = styleRange;
                }
                styleRangeArray2[n2] = this.stylesSet[n];
            }
            styleRangeArray = styleRangeArray2;
        }
        if (this.styleCount == 0) {
            if (nArray != null) {
                this.ranges = new int[nArray.length];
                System.arraycopy(nArray, 0, this.ranges, 0, this.ranges.length);
            }
            this.styles = new StyleRange[styleRangeArray.length];
            System.arraycopy(styleRangeArray, 0, this.styles, 0, this.styles.length);
            this.styleCount = styleRangeArray.length;
            return;
        }
        if (nArray != null && this.ranges == null) {
            this.ranges = new int[this.styles.length << 1];
            n2 = 0;
            for (int i = 0; i < this.styleCount; ++i) {
                this.ranges[n2++] = this.styles[i].start;
                this.ranges[n2++] = this.styles[i].length;
            }
        }
        if (nArray == null && this.ranges != null) {
            nArray = new int[styleRangeArray.length << 1];
            n2 = 0;
            for (int i = 0; i < styleRangeArray.length; ++i) {
                nArray[n2++] = styleRangeArray[i].start;
                nArray[n2++] = styleRangeArray[i].length;
            }
        }
        if (this.ranges != null) {
            boolean bl;
            n2 = nArray[0];
            int n4 = this.styleCount << 1;
            n3 = this.getRangeIndex(n2, -1, n4);
            boolean bl2 = bl = n3 == n4;
            if (!bl) {
                n = nArray[nArray.length - 2] + nArray[nArray.length - 1];
                int n5 = this.getRangeIndex(n, n3 - 1, n4);
                boolean bl3 = bl = n3 == n5 && this.ranges[n3] >= n;
            }
            if (bl) {
                this.addMerge(nArray, styleRangeArray, nArray.length, n3, n3);
                return;
            }
            n = n3;
            int[] nArray2 = new int[6];
            StyleRange[] styleRangeArray4 = new StyleRange[3];
            for (int i = 0; i < nArray.length; i += 2) {
                int n6 = nArray[i];
                int n7 = n6 + nArray[i + 1];
                if (n6 == n7) continue;
                int n8 = 0;
                int n9 = 0;
                while (n < n4) {
                    if (n6 >= this.ranges[n3] + this.ranges[n3 + 1]) {
                        n3 += 2;
                    }
                    if (this.ranges[n] + this.ranges[n + 1] > n7) break;
                    n += 2;
                }
                if (this.ranges[n3] < n6 && n6 < this.ranges[n3] + this.ranges[n3 + 1]) {
                    styleRangeArray4[n9 >> 1] = this.styles[n3 >> 1];
                    nArray2[n9] = this.ranges[n3];
                    nArray2[n9 + 1] = n6 - this.ranges[n3];
                    n9 += 2;
                }
                styleRangeArray4[n9 >> 1] = styleRangeArray[i >> 1];
                nArray2[n9] = n6;
                nArray2[n9 + 1] = nArray[i + 1];
                n9 += 2;
                if (n < n4 && this.ranges[n] < n7 && n7 < this.ranges[n] + this.ranges[n + 1]) {
                    styleRangeArray4[n9 >> 1] = this.styles[n >> 1];
                    nArray2[n9] = n7;
                    nArray2[n9 + 1] = this.ranges[n] + this.ranges[n + 1] - n7;
                    n9 += 2;
                    n8 = 2;
                }
                int n10 = this.addMerge(nArray2, styleRangeArray4, n9, n3, n + n8);
                n4 += n10;
                n = n3 = n + n10;
            }
        } else {
            int n11 = styleRangeArray[0].start;
            n2 = this.getRangeIndex(n11, -1, this.styleCount);
            int n12 = n3 = n2 == this.styleCount ? 1 : 0;
            if (n3 == 0) {
                int n13 = styleRangeArray[styleRangeArray.length - 1].start + styleRangeArray[styleRangeArray.length - 1].length;
                n = this.getRangeIndex(n13, n2 - 1, this.styleCount);
                int n14 = n3 = n2 == n && this.styles[n2].start >= n13 ? 1 : 0;
            }
            if (n3 != 0) {
                this.addMerge(styleRangeArray, styleRangeArray.length, n2, n2);
                return;
            }
            int n15 = n2;
            StyleRange[] styleRangeArray5 = new StyleRange[3];
            for (StyleRange styleRange : styleRangeArray) {
                StyleRange styleRange2;
                int n16;
                StyleRange[] styleRangeArray6;
                int n17 = styleRange.start;
                int n18 = n17 + styleRange.length;
                if (n17 == n18) continue;
                int n19 = 0;
                int n20 = 0;
                while (n15 < this.styleCount) {
                    if (n17 >= this.styles[n2].start + this.styles[n2].length) {
                        ++n2;
                    }
                    if (this.styles[n15].start + this.styles[n15].length > n18) break;
                    ++n15;
                }
                StyleRange styleRange3 = this.styles[n2];
                if (styleRange3.start < n17 && n17 < styleRange3.start + styleRange3.length) {
                    styleRangeArray6 = styleRangeArray5;
                    n16 = n20++;
                    styleRangeArray6[n16] = styleRange2 = (StyleRange)styleRange3.clone();
                    styleRange3 = styleRange2;
                    styleRange3.length = n17 - styleRange3.start;
                }
                styleRangeArray5[n20++] = styleRange;
                if (n15 < this.styleCount) {
                    styleRange3 = this.styles[n15];
                    if (styleRange3.start < n18 && n18 < styleRange3.start + styleRange3.length) {
                        StyleRange styleRange4;
                        styleRangeArray6 = styleRangeArray5;
                        n16 = n20++;
                        styleRangeArray6[n16] = styleRange2 = (StyleRange)styleRange3.clone();
                        styleRange3 = styleRange4 = styleRange2;
                        styleRange4.length += styleRange3.start - n18;
                        styleRange3.start = n18;
                        n19 = 1;
                    }
                }
                int n21 = this.addMerge(styleRangeArray5, n20, n2, n15 + n19);
                n15 = n2 = n15 + n21;
            }
        }
    }

    void textChanging(TextChangingEvent textChangingEvent) {
        int n = textChangingEvent.start;
        int n2 = textChangingEvent.newCharCount;
        int n3 = textChangingEvent.replaceCharCount;
        int n4 = textChangingEvent.newLineCount;
        int n5 = textChangingEvent.replaceLineCount;
        this.updateRanges(n, n3, n2);
        int n6 = this.content.getLineAtOffset(n);
        if (n3 == this.content.getCharCount()) {
            this.lines = null;
        }
        if (n5 == this.lineCount) {
            this.lineCount = n4;
            this.lineSizes = new LineSizeInfo[this.lineCount];
            this.reset(0, this.lineCount);
        } else {
            int n7;
            int n8;
            Object[] objectArray;
            int n9;
            int n10 = n6 + n5 + 1;
            int n11 = n6 + n4 + 1;
            if (this.lineCount < n6) {
                SWT.error(6, null, "bug 478020: lineCount < startLine: " + this.lineCount + ":" + n6);
            }
            if (this.lineCount < n10) {
                SWT.error(6, null, "bug 478020: lineCount < startIndex: " + this.lineCount + ":" + n10);
            }
            if (this.lineCount + (n9 = n4 - n5) > this.lineSizes.length) {
                objectArray = new LineSizeInfo[this.lineCount + n9 + 32];
                System.arraycopy(this.lineSizes, 0, objectArray, 0, this.lineCount);
                this.lineSizes = objectArray;
            }
            if (this.lines != null && this.lineCount + n9 > this.lines.length) {
                objectArray = new LineInfo[this.lineCount + n9 + 32];
                System.arraycopy(this.lines, 0, objectArray, 0, this.lineCount);
                this.lines = objectArray;
            }
            System.arraycopy(this.lineSizes, n10, this.lineSizes, n11, this.lineCount - n10);
            for (n8 = n6; n8 < n11; ++n8) {
                this.lineSizes[n8] = null;
            }
            for (n8 = this.lineCount + n9; n8 < this.lineCount; ++n8) {
                this.lineSizes[n8] = null;
            }
            if (this.layouts != null) {
                int n12;
                n8 = n6 - this.topIndex;
                n7 = n8 + n5 + 1;
                for (n12 = n8; n12 < n7; ++n12) {
                    if (0 > n12 || n12 >= this.layouts.length) continue;
                    if (this.layouts[n12] != null) {
                        this.layouts[n12].dispose();
                    }
                    this.layouts[n12] = null;
                    if (this.bullets == null || this.bulletsIndices == null) continue;
                    this.bullets[n12] = null;
                }
                if (n9 > 0) {
                    for (n12 = this.layouts.length - 1; n12 >= n7; --n12) {
                        if (0 > n12 || n12 >= this.layouts.length) continue;
                        n11 = n12 + n9;
                        if (0 <= n11 && n11 < this.layouts.length) {
                            this.layouts[n11] = this.layouts[n12];
                            this.layouts[n12] = null;
                            if (this.bullets == null || this.bulletsIndices == null) continue;
                            this.bullets[n11] = this.bullets[n12];
                            this.bulletsIndices[n11] = this.bulletsIndices[n12];
                            this.bullets[n12] = null;
                            continue;
                        }
                        if (this.layouts[n12] != null) {
                            this.layouts[n12].dispose();
                        }
                        this.layouts[n12] = null;
                        if (this.bullets == null || this.bulletsIndices == null) continue;
                        this.bullets[n12] = null;
                    }
                } else if (n9 < 0) {
                    for (n12 = n7; n12 < this.layouts.length; ++n12) {
                        if (0 > n12 || n12 >= this.layouts.length) continue;
                        n11 = n12 + n9;
                        if (0 <= n11 && n11 < this.layouts.length) {
                            this.layouts[n11] = this.layouts[n12];
                            this.layouts[n12] = null;
                            if (this.bullets == null || this.bulletsIndices == null) continue;
                            this.bullets[n11] = this.bullets[n12];
                            this.bulletsIndices[n11] = this.bulletsIndices[n12];
                            this.bullets[n12] = null;
                            continue;
                        }
                        if (this.layouts[n12] != null) {
                            this.layouts[n12].dispose();
                        }
                        this.layouts[n12] = null;
                        if (this.bullets == null || this.bulletsIndices == null) continue;
                        this.bullets[n12] = null;
                    }
                }
            }
            if (n5 != 0 || n4 != 0) {
                n8 = this.content.getOffsetAtLine(n6);
                if (n8 != n) {
                    ++n6;
                }
                this.updateBullets(n6, n5, n4, true);
                if (this.lines != null) {
                    n10 = n6 + n5;
                    n11 = n6 + n4;
                    System.arraycopy(this.lines, n10, this.lines, n11, this.lineCount - n10);
                    for (n7 = n6; n7 < n11; ++n7) {
                        this.lines[n7] = null;
                    }
                    for (n7 = this.lineCount + n9; n7 < this.lineCount; ++n7) {
                        this.lines[n7] = null;
                    }
                }
            }
            this.lineCount += n9;
            if (this.maxWidthLineIndex != -1 && n6 <= this.maxWidthLineIndex && this.maxWidthLineIndex <= n6 + n5) {
                this.maxWidth = 0;
                this.maxWidthLineIndex = -1;
                for (n8 = 0; n8 < this.lineCount; ++n8) {
                    LineSizeInfo lineSizeInfo = this.getLineSize(n8);
                    if (lineSizeInfo.width <= this.maxWidth) continue;
                    this.maxWidth = lineSizeInfo.width;
                    this.maxWidthLineIndex = n8;
                }
            }
        }
    }

    void updateBullets(int n, int n2, int n3, boolean bl) {
        int n4;
        Object object;
        if (this.bullets == null) {
            return;
        }
        if (this.bulletsIndices != null) {
            return;
        }
        for (Bullet bullet : this.bullets) {
            object = bullet.removeIndices(n, n2, n3, bl);
            if (object == null) continue;
            if (this.redrawLines == null) {
                this.redrawLines = object;
                continue;
            }
            int[] nArray = new int[this.redrawLines.length + ((Object)object).length];
            System.arraycopy(this.redrawLines, 0, nArray, 0, this.redrawLines.length);
            System.arraycopy(object, 0, nArray, this.redrawLines.length, ((Object)object).length);
            this.redrawLines = nArray;
        }
        int n5 = 0;
        Bullet[] bulletArray = this.bullets;
        int n6 = bulletArray.length;
        for (n4 = 0; n4 < n6; ++n4) {
            object = bulletArray[n4];
            if (object.size() != 0) continue;
            ++n5;
        }
        if (n5 > 0) {
            if (n5 == this.bullets.length) {
                this.bullets = null;
            } else {
                bulletArray = new Bullet[this.bullets.length - n5];
                n4 = 0;
                for (n6 = 0; n6 < this.bullets.length; ++n6) {
                    object = this.bullets[n6];
                    if (object.size() <= 0) continue;
                    bulletArray[n4++] = object;
                }
                this.bullets = bulletArray;
            }
        }
    }

    void updateRanges(int n, int n2, int n3) {
        if (this.styleCount == 0 || n2 == 0 && n3 == 0) {
            return;
        }
        if (this.ranges != null) {
            int n4 = this.styleCount << 1;
            int n5 = this.getRangeIndex(n, -1, n4);
            if (n5 == n4) {
                return;
            }
            int n6 = n + n2;
            int n7 = this.getRangeIndex(n6, n5 - 1, n4);
            int n8 = n3 - n2;
            if (n5 == n7 && this.ranges[n5] < n && n6 < this.ranges[n7] + this.ranges[n7 + 1]) {
                int[] nArray;
                if (n3 == 0) {
                    int n9;
                    nArray = this.ranges;
                    int n10 = n9 = n5 + 1;
                    nArray[n10] = nArray[n10] - n2;
                    n7 += 2;
                } else {
                    if (n4 + 2 > this.ranges.length) {
                        nArray = new int[this.ranges.length + 64];
                        System.arraycopy(this.ranges, 0, nArray, 0, n4);
                        this.ranges = nArray;
                        StyleRange[] styleRangeArray = new StyleRange[this.styles.length + 32];
                        System.arraycopy(this.styles, 0, styleRangeArray, 0, this.styleCount);
                        this.styles = styleRangeArray;
                    }
                    System.arraycopy(this.ranges, n5 + 2, this.ranges, n5 + 4, n4 - (n5 + 2));
                    System.arraycopy(this.styles, n5 + 2 >> 1, this.styles, n5 + 4 >> 1, this.styleCount - (n5 + 2 >> 1));
                    this.ranges[n5 + 3] = this.ranges[n5] + this.ranges[n5 + 1] - n6;
                    this.ranges[n5 + 2] = n + n3;
                    this.ranges[n5 + 1] = n - this.ranges[n5];
                    this.styles[(n5 >> 1) + 1] = this.styles[n5 >> 1];
                    n4 += 2;
                    ++this.styleCount;
                    n7 += 4;
                }
                if (n8 != 0) {
                    for (int i = n7; i < n4; i += 2) {
                        int n11;
                        int[] nArray2 = this.ranges;
                        int n12 = n11 = i;
                        nArray2[n12] = nArray2[n12] + n8;
                    }
                }
            } else {
                if (this.ranges[n5] < n && n < this.ranges[n5] + this.ranges[n5 + 1]) {
                    this.ranges[n5 + 1] = n - this.ranges[n5];
                    n5 += 2;
                }
                if (n7 < n4 && this.ranges[n7] < n6 && n6 < this.ranges[n7] + this.ranges[n7 + 1]) {
                    this.ranges[n7 + 1] = this.ranges[n7] + this.ranges[n7 + 1] - n6;
                    this.ranges[n7] = n6;
                }
                if (n8 != 0) {
                    for (int i = n7; i < n4; i += 2) {
                        int n13;
                        int[] nArray = this.ranges;
                        int n14 = n13 = i;
                        nArray[n14] = nArray[n14] + n8;
                    }
                }
                System.arraycopy(this.ranges, n7, this.ranges, n5, n4 - n7);
                System.arraycopy(this.styles, n7 >> 1, this.styles, n5 >> 1, this.styleCount - (n7 >> 1));
                this.styleCount -= n7 - n5 >> 1;
            }
        } else {
            int n15 = this.getRangeIndex(n, -1, this.styleCount);
            if (n15 == this.styleCount) {
                return;
            }
            int n16 = n + n2;
            int n17 = this.getRangeIndex(n16, n15 - 1, this.styleCount);
            int n18 = n3 - n2;
            if (n15 == n17 && this.styles[n15].start < n && n16 < this.styles[n17].start + this.styles[n17].length) {
                StyleRange[] styleRangeArray;
                if (n3 == 0) {
                    styleRangeArray = this.styles[n15];
                    styleRangeArray.length -= n2;
                    ++n17;
                } else {
                    if (this.styleCount + 1 > this.styles.length) {
                        styleRangeArray = new StyleRange[this.styles.length + 32];
                        System.arraycopy(this.styles, 0, styleRangeArray, 0, this.styleCount);
                        this.styles = styleRangeArray;
                    }
                    System.arraycopy(this.styles, n15 + 1, this.styles, n15 + 2, this.styleCount - (n15 + 1));
                    this.styles[n15 + 1] = (StyleRange)this.styles[n15].clone();
                    this.styles[n15 + 1].length = this.styles[n15].start + this.styles[n15].length - n16;
                    this.styles[n15 + 1].start = n + n3;
                    this.styles[n15].length = n - this.styles[n15].start;
                    ++this.styleCount;
                    n17 += 2;
                }
                if (n18 != 0) {
                    for (int i = n17; i < this.styleCount; ++i) {
                        StyleRange styleRange = this.styles[i];
                        styleRange.start += n18;
                    }
                }
            } else {
                if (this.styles[n15].start < n && n < this.styles[n15].start + this.styles[n15].length) {
                    this.styles[n15].length = n - this.styles[n15].start;
                    ++n15;
                }
                if (n17 < this.styleCount && this.styles[n17].start < n16 && n16 < this.styles[n17].start + this.styles[n17].length) {
                    this.styles[n17].length = this.styles[n17].start + this.styles[n17].length - n16;
                    this.styles[n17].start = n16;
                }
                if (n18 != 0) {
                    for (int i = n17; i < this.styleCount; ++i) {
                        StyleRange styleRange = this.styles[i];
                        styleRange.start += n18;
                    }
                }
                System.arraycopy(this.styles, n17, this.styles, n15, this.styleCount - n17);
                this.styleCount -= n17 - n15;
            }
        }
    }

    public boolean hasVerticalIndent() {
        return Arrays.stream(this.lines).filter(Objects::nonNull).mapToInt(StyledTextRenderer::lambda$hasVerticalIndent$0).anyMatch(StyledTextRenderer::lambda$hasVerticalIndent$1);
    }

    private static boolean lambda$hasVerticalIndent$1(int n) {
        return n != 0;
    }

    private static int lambda$hasVerticalIndent$0(LineInfo lineInfo) {
        return lineInfo.verticalIndent;
    }

    private static final class StyleEntry {
        public final int start;
        public final int end;
        public final TextStyle style;

        public StyleEntry(TextStyle textStyle, int n, int n2) {
            this.style = textStyle;
            this.start = n;
            this.end = n2;
        }
    }

    static class LineInfo {
        int flags;
        Color background;
        int alignment;
        int indent;
        int wrapIndent;
        boolean justify;
        int[] segments;
        char[] segmentsChars;
        int[] tabStops;
        int verticalIndent;

        public LineInfo() {
        }

        public LineInfo(LineInfo lineInfo) {
            if (lineInfo != null) {
                this.flags = lineInfo.flags;
                this.background = lineInfo.background;
                this.alignment = lineInfo.alignment;
                this.indent = lineInfo.indent;
                this.wrapIndent = lineInfo.wrapIndent;
                this.justify = lineInfo.justify;
                this.segments = lineInfo.segments;
                this.segmentsChars = lineInfo.segmentsChars;
                this.tabStops = lineInfo.tabStops;
                this.verticalIndent = lineInfo.verticalIndent;
            }
        }
    }

    static class LineSizeInfo {
        private static final int RESETED_SIZE = -1;
        int height;
        int width;

        public LineSizeInfo() {
            this.resetSize();
        }

        void resetSize() {
            this.height = -1;
            this.width = -1;
        }

        /*
         * Exception decompiling
         */
        boolean canLayout() {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl2 : IF_ICMPNE - null : Stack underflow
             *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        /*
         * Exception decompiling
         */
        boolean needsRecalculateSize() {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl2 : IF_ICMPNE - null : Stack underflow
             *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }
    }
}

