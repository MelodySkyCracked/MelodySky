/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.image.LEDataInputStream;
import org.eclipse.swt.internal.image.PngFileReadState;
import org.eclipse.swt.internal.image.PngIdatChunk;
import org.eclipse.swt.internal.image.PngIendChunk;
import org.eclipse.swt.internal.image.PngIhdrChunk;
import org.eclipse.swt.internal.image.PngPlteChunk;
import org.eclipse.swt.internal.image.PngTrnsChunk;

class PngChunk {
    byte[] reference;
    static final int LENGTH_OFFSET = 0;
    static final int TYPE_OFFSET = 4;
    static final int DATA_OFFSET = 8;
    static final int TYPE_FIELD_LENGTH = 4;
    static final int LENGTH_FIELD_LENGTH = 4;
    static final int MIN_LENGTH = 12;
    static final int CHUNK_UNKNOWN = -1;
    static final int CHUNK_IHDR = 0;
    static final int CHUNK_PLTE = 1;
    static final int CHUNK_IDAT = 2;
    static final int CHUNK_IEND = 3;
    static final int CHUNK_tRNS = 5;
    static final byte[] TYPE_IHDR = new byte[]{73, 72, 68, 82};
    static final byte[] TYPE_PLTE = new byte[]{80, 76, 84, 69};
    static final byte[] TYPE_IDAT = new byte[]{73, 68, 65, 84};
    static final byte[] TYPE_IEND = new byte[]{73, 69, 78, 68};
    static final byte[] TYPE_tRNS = new byte[]{116, 82, 78, 83};
    static final int[] CRC_TABLE = new int[256];
    int length;

    PngChunk(byte[] byArray) {
        this.setReference(byArray);
        if (byArray.length < 4) {
            SWT.error(40);
        }
        this.length = this.getInt32(0);
    }

    PngChunk(int n) {
        this(new byte[12 + n]);
        this.setLength(n);
    }

    byte[] getReference() {
        return this.reference;
    }

    void setReference(byte[] byArray) {
        this.reference = byArray;
    }

    int getInt16(int n) {
        int n2 = 0;
        n2 |= (this.reference[n] & 0xFF) << 8;
        return n2 |= this.reference[n + 1] & 0xFF;
    }

    void setInt16(int n, int n2) {
        this.reference[n] = (byte)(n2 >> 8 & 0xFF);
        this.reference[n + 1] = (byte)(n2 & 0xFF);
    }

    int getInt32(int n) {
        int n2 = 0;
        n2 |= (this.reference[n] & 0xFF) << 24;
        n2 |= (this.reference[n + 1] & 0xFF) << 16;
        n2 |= (this.reference[n + 2] & 0xFF) << 8;
        return n2 |= this.reference[n + 3] & 0xFF;
    }

    void setInt32(int n, int n2) {
        this.reference[n] = (byte)(n2 >> 24 & 0xFF);
        this.reference[n + 1] = (byte)(n2 >> 16 & 0xFF);
        this.reference[n + 2] = (byte)(n2 >> 8 & 0xFF);
        this.reference[n + 3] = (byte)(n2 & 0xFF);
    }

    int getLength() {
        return this.length;
    }

    void setLength(int n) {
        this.setInt32(0, n);
        this.length = n;
    }

    byte[] getTypeBytes() {
        byte[] byArray = new byte[4];
        System.arraycopy(this.reference, 4, byArray, 0, 4);
        return byArray;
    }

    void setType(byte[] byArray) {
        if (byArray.length != 4) {
            SWT.error(5);
        }
        System.arraycopy(byArray, 0, this.reference, 4, 4);
    }

    byte[] getData() {
        int n = this.getLength();
        if (this.reference.length < 12 + n) {
            SWT.error(6);
        }
        byte[] byArray = new byte[n];
        System.arraycopy(this.reference, 8, byArray, 0, n);
        return byArray;
    }

    void setData(byte[] byArray) {
        this.setLength(byArray.length);
        System.arraycopy(byArray, 0, this.reference, 8, byArray.length);
        this.setCRC(this.computeCRC());
    }

    int getCRC() {
        int n = 8 + this.getLength();
        return this.getInt32(n);
    }

    void setCRC(int n) {
        int n2 = 8 + this.getLength();
        this.setInt32(n2, n);
    }

    int getSize() {
        return 12 + this.getLength();
    }

    int computeCRC() {
        int n = -1;
        int n2 = 4;
        int n3 = 8 + this.getLength();
        for (int i = 4; i < n3; ++i) {
            int n4 = (n ^ this.reference[i]) & 0xFF;
            n = CRC_TABLE[n4] ^ n >> 8 & 0xFFFFFF;
        }
        return ~n;
    }

    boolean isCritical() {
        char c = (char)this.getTypeBytes()[0];
        return 'A' <= c && c <= 'Z';
    }

    int getChunkType() {
        if (this < TYPE_IHDR) {
            return 0;
        }
        if (this < TYPE_PLTE) {
            return 1;
        }
        if (this < TYPE_IDAT) {
            return 2;
        }
        if (this < TYPE_IEND) {
            return 3;
        }
        if (this < TYPE_tRNS) {
            return 5;
        }
        return -1;
    }

    static PngChunk readNextFromStream(LEDataInputStream lEDataInputStream) {
        try {
            int n = 8;
            byte[] byArray = new byte[8];
            int n2 = lEDataInputStream.read(byArray, 0, 8);
            lEDataInputStream.unread(byArray);
            if (n2 != 8) {
                return null;
            }
            PngChunk pngChunk = new PngChunk(byArray);
            int n3 = pngChunk.getSize();
            byte[] byArray2 = new byte[n3];
            n2 = lEDataInputStream.read(byArray2, 0, n3);
            if (n2 != n3) {
                return null;
            }
            switch (pngChunk.getChunkType()) {
                case 0: {
                    return new PngIhdrChunk(byArray2);
                }
                case 1: {
                    return new PngPlteChunk(byArray2);
                }
                case 2: {
                    return new PngIdatChunk(byArray2);
                }
                case 3: {
                    return new PngIendChunk(byArray2);
                }
                case 5: {
                    return new PngTrnsChunk(byArray2);
                }
            }
            return new PngChunk(byArray2);
        }
        catch (IOException iOException) {
            return null;
        }
    }

    /*
     * Exception decompiling
     */
    void validate(PngFileReadState var1, PngIhdrChunk var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl51 : IF_ICMPNE - null : Stack underflow
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

    void contributeToString(StringBuilder stringBuilder) {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("\n\tLength: ");
        stringBuilder.append(this.getLength());
        stringBuilder.append("\n\tType: ");
        for (byte by : this.getTypeBytes()) {
            stringBuilder.append((char)by);
        }
        this.contributeToString(stringBuilder);
        stringBuilder.append("\n\tCRC: ");
        stringBuilder.append(Integer.toHexString(this.getCRC()));
        stringBuilder.append("\n}");
        return stringBuilder.toString();
    }

    static {
        for (int i = 0; i < 256; ++i) {
            PngChunk.CRC_TABLE[i] = i;
            for (int j = 0; j < 8; ++j) {
                PngChunk.CRC_TABLE[i] = (CRC_TABLE[i] & 1) == 0 ? CRC_TABLE[i] >> 1 & Integer.MAX_VALUE : 0xEDB88320 ^ CRC_TABLE[i] >> 1 & Integer.MAX_VALUE;
            }
        }
    }
}

