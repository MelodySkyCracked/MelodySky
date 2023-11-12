/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

import org.spongepowered.asm.lib.ByteVector;
import org.spongepowered.asm.lib.Edge;
import org.spongepowered.asm.lib.Frame;
import org.spongepowered.asm.lib.MethodWriter;

public class Label {
    static final int DEBUG = 1;
    static final int RESOLVED = 2;
    static final int RESIZED = 4;
    static final int PUSHED = 8;
    static final int TARGET = 16;
    static final int STORE = 32;
    static final int REACHABLE = 64;
    static final int JSR = 128;
    static final int RET = 256;
    static final int SUBROUTINE = 512;
    static final int VISITED = 1024;
    static final int VISITED2 = 2048;
    public Object info;
    int status;
    int line;
    int position;
    private int referenceCount;
    private int[] srcAndRefPositions;
    int inputStackTop;
    int outputStackMax;
    Frame frame;
    Label successor;
    Edge successors;
    Label next;

    public int getOffset() {
        if ((this.status & 2) == 0) {
            throw new IllegalStateException("Label offset position has not been resolved yet");
        }
        return this.position;
    }

    void put(MethodWriter methodWriter, ByteVector byteVector, int n, boolean bl) {
        if ((this.status & 2) == 0) {
            if (bl) {
                this.addReference(-1 - n, byteVector.length);
                byteVector.putInt(-1);
            } else {
                this.addReference(n, byteVector.length);
                byteVector.putShort(-1);
            }
        } else if (bl) {
            byteVector.putInt(this.position - n);
        } else {
            byteVector.putShort(this.position - n);
        }
    }

    private void addReference(int n, int n2) {
        if (this.srcAndRefPositions == null) {
            this.srcAndRefPositions = new int[6];
        }
        if (this.referenceCount >= this.srcAndRefPositions.length) {
            int[] nArray = new int[this.srcAndRefPositions.length + 6];
            System.arraycopy(this.srcAndRefPositions, 0, nArray, 0, this.srcAndRefPositions.length);
            this.srcAndRefPositions = nArray;
        }
        this.srcAndRefPositions[this.referenceCount++] = n;
        this.srcAndRefPositions[this.referenceCount++] = n2;
    }

    boolean resolve(MethodWriter methodWriter, int n, byte[] byArray) {
        boolean bl = false;
        this.status |= 2;
        this.position = n;
        int n2 = 0;
        while (n2 < this.referenceCount) {
            int n3;
            int n4 = this.srcAndRefPositions[n2++];
            int n5 = this.srcAndRefPositions[n2++];
            if (n4 >= 0) {
                n3 = n - n4;
                if (n3 < Short.MIN_VALUE || n3 > Short.MAX_VALUE) {
                    int n6 = byArray[n5 - 1] & 0xFF;
                    byArray[n5 - 1] = n6 <= 168 ? (byte)(n6 + 49) : (byte)(n6 + 20);
                    bl = true;
                }
                byArray[n5++] = (byte)(n3 >>> 8);
                byArray[n5] = (byte)n3;
                continue;
            }
            n3 = n + n4 + 1;
            byArray[n5++] = (byte)(n3 >>> 24);
            byArray[n5++] = (byte)(n3 >>> 16);
            byArray[n5++] = (byte)(n3 >>> 8);
            byArray[n5] = (byte)n3;
        }
        return bl;
    }

    Label getFirst() {
        return this.frame == null ? this : this.frame.owner;
    }

    void addToSubroutine(long l2, int n) {
        if ((this.status & 0x400) == 0) {
            this.status |= 0x400;
            this.srcAndRefPositions = new int[n / 32 + 1];
        }
        int n2 = (int)(l2 >>> 32);
        this.srcAndRefPositions[n2] = this.srcAndRefPositions[n2] | (int)l2;
    }

    /*
     * Exception decompiling
     */
    void visitSubroutine(Label var1, long var2, int var4) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl3 : ALOAD - null : trying to set 1 previously set to 0
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

    public String toString() {
        return "L" + System.identityHashCode(this);
    }
}

