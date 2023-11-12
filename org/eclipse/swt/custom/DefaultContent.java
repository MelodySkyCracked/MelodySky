/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.custom.StyledTextEvent;
import org.eclipse.swt.custom.StyledTextListener;
import org.eclipse.swt.custom.TextChangeListener;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.widgets.TypedListener;

class DefaultContent
implements StyledTextContent {
    private static final String LineDelimiter = System.lineSeparator();
    List textListeners = new ArrayList();
    char[] textStore = new char[0];
    int gapStart = -1;
    int gapEnd = -1;
    int gapLine = -1;
    int highWatermark = 300;
    int lowWatermark = 50;
    int[][] lines = new int[50][2];
    int lineCount = 0;
    int expandExp = 1;
    int replaceExpandExp = 1;

    DefaultContent() {
        this.setText("");
    }

    void addLineIndex(int n, int n2) {
        Object object;
        int n3 = this.lines.length;
        if (this.lineCount == n3) {
            object = new int[n3 + Compatibility.pow2(this.expandExp)][2];
            System.arraycopy(this.lines, 0, object, 0, n3);
            this.lines = object;
            ++this.expandExp;
        }
        object = new int[]{n, n2};
        this.lines[this.lineCount] = (int[])object;
        ++this.lineCount;
    }

    int[][] addLineIndex(int n, int n2, int[][] nArray, int n3) {
        int n4 = nArray.length;
        int[][] nArray2 = nArray;
        if (n3 == n4) {
            nArray2 = new int[n4 + Compatibility.pow2(this.replaceExpandExp)][2];
            ++this.replaceExpandExp;
            System.arraycopy(nArray, 0, nArray2, 0, n4);
        }
        int[] nArray3 = new int[]{n, n2};
        nArray2[n3] = nArray3;
        return nArray2;
    }

    @Override
    public void addTextChangeListener(TextChangeListener textChangeListener) {
        if (textChangeListener == null) {
            this.error(4);
        }
        StyledTextListener styledTextListener = new StyledTextListener(textChangeListener);
        this.textListeners.add(styledTextListener);
    }

    void adjustGap(int n, int n2, int n3) {
        int n4;
        int n5;
        if (n == this.gapStart ? this.lowWatermark <= (n5 = this.gapEnd - this.gapStart - n2) && n5 <= this.highWatermark : n + n2 == this.gapStart && n2 < 0 && this.lowWatermark <= (n4 = this.gapEnd - this.gapStart - n2) && n4 <= this.highWatermark) {
            return;
        }
        this.moveAndResizeGap(n, n2, n3);
    }

    void indexLines() {
        int n;
        int n2 = 0;
        this.lineCount = 0;
        int n3 = this.textStore.length;
        for (n = n2; n < n3; ++n) {
            char c = this.textStore[n];
            if (c == '\r') {
                if (n + 1 < n3 && (c = this.textStore[n + 1]) == '\n') {
                    ++n;
                }
                this.addLineIndex(n2, n - n2 + 1);
                n2 = n + 1;
                continue;
            }
            if (c != '\n') continue;
            this.addLineIndex(n2, n - n2 + 1);
            n2 = n + 1;
        }
        this.addLineIndex(n2, n - n2);
    }

    /*
     * Exception decompiling
     */
    private void validateReplace(int var1, int var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl66 : RETURN - null : trying to set 2 previously set to 1
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

    int[][] indexLines(int n, int n2, int n3) {
        int n4;
        int[][] nArray = new int[n3][2];
        int n5 = 0;
        int n6 = 0;
        this.replaceExpandExp = 1;
        for (n4 = n5; n4 < n2; ++n4) {
            int n7 = n4 + n;
            if (n7 >= this.gapStart && n7 < this.gapEnd) continue;
            char c = this.textStore[n7];
            if (c == '\r') {
                if (n7 + 1 < this.textStore.length && (c = this.textStore[n7 + 1]) == '\n') {
                    ++n4;
                }
                nArray = this.addLineIndex(n5, n4 - n5 + 1, nArray, n6);
                ++n6;
                n5 = n4 + 1;
                continue;
            }
            if (c != '\n') continue;
            nArray = this.addLineIndex(n5, n4 - n5 + 1, nArray, n6);
            ++n6;
            n5 = n4 + 1;
        }
        int[][] nArray2 = new int[n6 + 1][2];
        System.arraycopy(nArray, 0, nArray2, 0, n6);
        int[] nArray3 = new int[]{n5, n4 - n5};
        nArray2[n6] = nArray3;
        return nArray2;
    }

    void insert(int n, String string) {
        int n2;
        int n3;
        int[][] nArray;
        if (string.length() == 0) {
            return;
        }
        int n4 = this.getLineAtOffset(n);
        int n5 = string.length();
        boolean bl = n == this.getCharCount();
        this.adjustGap(n, n5, n4);
        int n6 = this.getOffsetAtLine(n4);
        int n7 = this.getPhysicalLine(n4).length();
        if (n5 > 0) {
            this.gapStart += n5;
            for (int i = 0; i < string.length(); ++i) {
                this.textStore[n + i] = string.charAt(i);
            }
        }
        if ((nArray = this.indexLines(n6, n7, 10))[n3 = nArray.length - 1][1] == 0) {
            n3 = bl ? ++n3 : --n3;
        }
        this.expandLinesBy(n3);
        for (n2 = this.lineCount - 1; n2 > n4; --n2) {
            this.lines[n2 + n3] = this.lines[n2];
        }
        for (n2 = 0; n2 < n3; ++n2) {
            int[] nArray2 = nArray[n2];
            boolean bl2 = false;
            nArray2[0] = nArray2[0] + n6;
            this.lines[n4 + n2] = nArray[n2];
        }
        if (n3 < nArray.length) {
            int[] nArray3 = nArray[n3];
            boolean bl3 = false;
            nArray3[0] = nArray3[0] + n6;
            this.lines[n4 + n3] = nArray[n3];
        }
        this.lineCount += n3;
        this.gapLine = this.getLineAtPhysicalOffset(this.gapStart);
    }

    /*
     * Exception decompiling
     */
    void moveAndResizeGap(int var1, int var2, int var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl23 : IF_ICMPEQ - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
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

    int lineCount(int n, int n2) {
        if (n2 == 0) {
            return 0;
        }
        int n3 = 0;
        int n4 = 0;
        int n5 = n;
        if (n5 >= this.gapStart) {
            n5 += this.gapEnd - this.gapStart;
        }
        while (n4 < n2) {
            if (n5 < this.gapStart || n5 >= this.gapEnd) {
                char c = this.textStore[n5];
                if (c == '\r') {
                    if (n5 + 1 < this.textStore.length && (c = this.textStore[n5 + 1]) == '\n') {
                        ++n5;
                        ++n4;
                    }
                    ++n3;
                } else if (c == '\n') {
                    ++n3;
                }
                ++n4;
            }
            ++n5;
        }
        return n3;
    }

    int lineCount(String string) {
        int n = 0;
        int n2 = string.length();
        for (int i = 0; i < n2; ++i) {
            char c = string.charAt(i);
            if (c == '\r') {
                if (i + 1 < n2 && string.charAt(i + 1) == '\n') {
                    ++i;
                }
                ++n;
                continue;
            }
            if (c != '\n') continue;
            ++n;
        }
        return n;
    }

    @Override
    public int getCharCount() {
        int n = this.gapEnd - this.gapStart;
        return this.textStore.length - n;
    }

    /*
     * Exception decompiling
     */
    @Override
    public String getLine(int var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl31 : IF_ICMPEQ - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
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
    public String getLineDelimiter() {
        return LineDelimiter;
    }

    /*
     * Exception decompiling
     */
    String getFullLine(int var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl22 : IF_ICMPEQ - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
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

    String getPhysicalLine(int n) {
        int n2 = this.lines[n][0];
        int n3 = this.lines[n][1];
        return this.getPhysicalText(n2, n3);
    }

    @Override
    public int getLineCount() {
        return this.lineCount;
    }

    @Override
    public int getLineAtOffset(int n) {
        int n2;
        if (n > this.getCharCount() || n < 0) {
            this.error(5);
        }
        int n3 = n < this.gapStart ? n : n + (this.gapEnd - this.gapStart);
        if (this.lineCount > 0 && n3 == this.lines[n2 = this.lineCount - 1][0] + this.lines[n2][1]) {
            return n2;
        }
        n2 = this.lineCount;
        int n4 = -1;
        int n5 = this.lineCount;
        while (n2 - n4 > 1) {
            n5 = (n2 + n4) / 2;
            int n6 = this.lines[n5][0];
            int n7 = n6 + this.lines[n5][1] - 1;
            if (n3 <= n6) {
                n2 = n5;
                continue;
            }
            if (n3 <= n7) {
                n2 = n5;
                break;
            }
            n4 = n5;
        }
        return n2;
    }

    int getLineAtPhysicalOffset(int n) {
        int n2 = this.lineCount;
        int n3 = -1;
        int n4 = this.lineCount;
        while (n2 - n3 > 1) {
            n4 = (n2 + n3) / 2;
            int n5 = this.lines[n4][0];
            int n6 = n5 + this.lines[n4][1] - 1;
            if (n <= n5) {
                n2 = n4;
                continue;
            }
            if (n <= n6) {
                n2 = n4;
                break;
            }
            n3 = n4;
        }
        return n2;
    }

    @Override
    public int getOffsetAtLine(int n) {
        int n2;
        if (n == 0) {
            return 0;
        }
        if (n >= this.lineCount || n < 0) {
            this.error(5);
        }
        if ((n2 = this.lines[n][0]) > this.gapEnd) {
            return n2 - (this.gapEnd - this.gapStart);
        }
        return n2;
    }

    void expandLinesBy(int n) {
        int n2 = this.lines.length;
        if (n2 - this.lineCount >= n) {
            return;
        }
        int[][] nArray = new int[n2 + Math.max(10, n)][2];
        System.arraycopy(this.lines, 0, nArray, 0, n2);
        this.lines = nArray;
    }

    void error(int n) {
        SWT.error(n);
    }

    String getPhysicalText(int n, int n2) {
        return new String(this.textStore, n, n2);
    }

    /*
     * Exception decompiling
     */
    @Override
    public String getTextRange(int var1, int var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl15 : IF_ICMPEQ - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
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
    public void removeTextChangeListener(TextChangeListener textChangeListener) {
        if (textChangeListener == null) {
            this.error(4);
        }
        for (int i = 0; i < this.textListeners.size(); ++i) {
            TypedListener typedListener = (TypedListener)this.textListeners.get(i);
            if (typedListener.getEventListener() != textChangeListener) continue;
            this.textListeners.remove(i);
            break;
        }
    }

    @Override
    public void replaceTextRange(int n, int n2, String string) {
        this.validateReplace(n, n2);
        StyledTextEvent styledTextEvent = new StyledTextEvent(this);
        styledTextEvent.type = 3003;
        styledTextEvent.start = n;
        styledTextEvent.replaceLineCount = this.lineCount(n, n2);
        styledTextEvent.text = string;
        styledTextEvent.newLineCount = this.lineCount(string);
        styledTextEvent.replaceCharCount = n2;
        styledTextEvent.newCharCount = string.length();
        this.sendTextEvent(styledTextEvent);
        this.delete(n, n2, styledTextEvent.replaceLineCount + 1);
        this.insert(n, string);
        styledTextEvent = new StyledTextEvent(this);
        styledTextEvent.type = 3006;
        this.sendTextEvent(styledTextEvent);
    }

    void sendTextEvent(StyledTextEvent styledTextEvent) {
        for (StyledTextListener styledTextListener : this.textListeners) {
            styledTextListener.handleEvent(styledTextEvent);
        }
    }

    @Override
    public void setText(String string) {
        this.textStore = string.toCharArray();
        this.gapStart = -1;
        this.gapEnd = -1;
        this.expandExp = 1;
        this.indexLines();
        StyledTextEvent styledTextEvent = new StyledTextEvent(this);
        styledTextEvent.type = 3004;
        styledTextEvent.text = "";
        this.sendTextEvent(styledTextEvent);
    }

    void delete(int n, int n2, int n3) {
        int n4;
        int n5;
        if (n2 == 0) {
            return;
        }
        int n6 = this.getLineAtOffset(n);
        int n7 = this.getOffsetAtLine(n6);
        int n8 = this.getLineAtOffset(n + n2);
        String string = "";
        boolean bl = false;
        if (n + n2 < this.getCharCount() && (string = this.getTextRange(n + n2 - 1, 2)).charAt(0) == '\r' && string.charAt(1) == '\n') {
            bl = true;
        }
        this.adjustGap(n + n2, -n2, n6);
        int[][] nArray = this.indexLines(n, n2 + (this.gapEnd - this.gapStart), n3);
        if (n + n2 == this.gapStart) {
            this.gapStart -= n2;
        } else {
            this.gapEnd += n2;
        }
        int n9 = 0;
        for (n5 = n; n5 < this.textStore.length && n9 == 0; ++n5) {
            if (n5 >= this.gapStart && n5 < this.gapEnd || this == (n4 = this.textStore[n5])) continue;
            if (n5 + 1 < this.textStore.length && n4 == 13 && this.textStore[n5 + 1] == '\n') {
                ++n5;
            }
            n9 = 1;
        }
        this.lines[n6][1] = n - n7 + (n5 - n);
        n9 = nArray.length - 1;
        if (bl) {
            --n9;
        }
        for (n4 = n8 + 1; n4 < this.lineCount; ++n4) {
            this.lines[n4 - n9] = this.lines[n4];
        }
        this.lineCount -= n9;
        this.gapLine = this.getLineAtPhysicalOffset(this.gapStart);
    }
}

