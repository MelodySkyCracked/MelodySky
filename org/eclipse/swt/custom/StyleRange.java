/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.TextStyle;

public class StyleRange
extends TextStyle
implements Cloneable {
    public int start;
    public int length;
    public int fontStyle = 0;

    public StyleRange() {
    }

    public StyleRange(TextStyle textStyle) {
        super(textStyle);
    }

    public StyleRange(int n, int n2, Color color, Color color2) {
        super(null, color, color2);
        this.start = n;
        this.length = n2;
    }

    public StyleRange(int n, int n2, Color color, Color color2, int n3) {
        this(n, n2, color, color2);
        this.fontStyle = n3;
    }

    /*
     * Exception decompiling
     */
    @Override
    public boolean equals(Object var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl27 : ICONST_0 - null : trying to set 0 previously set to 1
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

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.fontStyle;
    }

    boolean isVariableHeight() {
        return this.font != null || this.metrics != null && (this.metrics.ascent != 0 || this.metrics.descent != 0) || this.rise != 0;
    }

    public boolean isUnstyled() {
        return this.font == null && this.rise == 0 && this.metrics == null && this.foreground == null && this.background == null && this.fontStyle == 0 && !this.underline && !this.strikeout && this.borderStyle == 0;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("StyleRange {");
        stringBuilder.append(this.start);
        stringBuilder.append(", ");
        stringBuilder.append(this.length);
        stringBuilder.append(", fontStyle=");
        switch (this.fontStyle) {
            case 1: {
                stringBuilder.append("bold");
                break;
            }
            case 2: {
                stringBuilder.append("italic");
                break;
            }
            case 3: {
                stringBuilder.append("bold-italic");
                break;
            }
            default: {
                stringBuilder.append("normal");
            }
        }
        String string = super.toString();
        int n = string.indexOf(123);
        string = string.substring(n + 1);
        if (string.length() > 1) {
            stringBuilder.append(", ");
        }
        stringBuilder.append(string);
        return stringBuilder.toString();
    }
}

