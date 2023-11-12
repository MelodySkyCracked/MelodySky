/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils;

public final class Base64 {
    private static final int BASELENGTH = 128;
    private static final int LOOKUPLENGTH = 64;
    private static final int TWENTYFOURBITGROUP = 24;
    private static final int EIGHTBIT = 8;
    private static final int SIXTEENBIT = 16;
    private static final int SIXBIT = 6;
    private static final int FOURBYTE = 4;
    private static final int SIGN = -128;
    private static final char PAD = '=';
    private static final boolean fDebug = false;
    private static final byte[] base64Alphabet;
    private static final char[] lookUpBase64Alphabet;

    /*
     * Exception decompiling
     */
    protected static boolean isBase64(char var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl2 : IF_ICMPEQ - null : Stack underflow
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

    public static String encode(byte[] byArray) {
        byte by;
        int n;
        if (byArray == null) {
            return null;
        }
        int n2 = byArray.length * 8;
        if (n2 == 0) {
            return "";
        }
        int n3 = n2 % 24;
        int n4 = n2 / 24;
        int n5 = n3 != 0 ? n4 + 1 : n4;
        char[] cArray = null;
        cArray = new char[n5 * 4];
        byte by2 = 0;
        byte by3 = 0;
        byte by4 = 0;
        byte by5 = 0;
        byte by6 = 0;
        int n6 = 0;
        int n7 = 0;
        for (n = 0; n < n4; ++n) {
            by4 = byArray[n7++];
            by5 = byArray[n7++];
            by6 = byArray[n7++];
            by3 = (byte)(by5 & 0xF);
            by2 = (byte)(by4 & 3);
            by = (by4 & 0xFFFFFF80) == 0 ? (byte)(by4 >> 2) : (byte)(by4 >> 2 ^ 0xC0);
            byte by7 = (by5 & 0xFFFFFF80) == 0 ? (byte)(by5 >> 4) : (byte)(by5 >> 4 ^ 0xF0);
            byte by8 = (by6 & 0xFFFFFF80) == 0 ? (byte)(by6 >> 6) : (byte)(by6 >> 6 ^ 0xFC);
            cArray[n6++] = lookUpBase64Alphabet[by];
            cArray[n6++] = lookUpBase64Alphabet[by7 | by2 << 4];
            cArray[n6++] = lookUpBase64Alphabet[by3 << 2 | by8];
            cArray[n6++] = lookUpBase64Alphabet[by6 & 0x3F];
        }
        if (n3 == 8) {
            by4 = byArray[n7];
            by2 = (byte)(by4 & 3);
            n = (by4 & 0xFFFFFF80) == 0 ? (byte)(by4 >> 2) : (byte)(by4 >> 2 ^ 0xC0);
            cArray[n6++] = lookUpBase64Alphabet[n];
            cArray[n6++] = lookUpBase64Alphabet[by2 << 4];
            cArray[n6++] = 61;
            cArray[n6++] = 61;
        } else if (n3 == 16) {
            by4 = byArray[n7];
            by5 = byArray[n7 + 1];
            by3 = (byte)(by5 & 0xF);
            by2 = (byte)(by4 & 3);
            n = (by4 & 0xFFFFFF80) == 0 ? (byte)(by4 >> 2) : (byte)(by4 >> 2 ^ 0xC0);
            by = (by5 & 0xFFFFFF80) == 0 ? (byte)(by5 >> 4) : (byte)(by5 >> 4 ^ 0xF0);
            cArray[n6++] = lookUpBase64Alphabet[n];
            cArray[n6++] = lookUpBase64Alphabet[by | by2 << 4];
            cArray[n6++] = lookUpBase64Alphabet[by3 << 2];
            cArray[n6++] = 61;
        }
        return new String(cArray);
    }

    /*
     * Exception decompiling
     */
    public static byte[] decode(String var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl66 : IF_ICMPGE - null : Stack underflow
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

    /*
     * Exception decompiling
     */
    protected static int removeWhiteSpace(char[] var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl18 : IF_ICMPEQ - null : Stack underflow
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

    static {
        int n;
        base64Alphabet = new byte[128];
        lookUpBase64Alphabet = new char[64];
        for (n = 0; n < 128; ++n) {
            Base64.base64Alphabet[n] = -1;
        }
        for (n = 90; n >= 65; --n) {
            Base64.base64Alphabet[n] = (byte)(n - 65);
        }
        for (n = 122; n >= 97; --n) {
            Base64.base64Alphabet[n] = (byte)(n - 97 + 26);
        }
        for (n = 57; n >= 48; --n) {
            Base64.base64Alphabet[n] = (byte)(n - 48 + 52);
        }
        Base64.base64Alphabet[43] = 62;
        Base64.base64Alphabet[47] = 63;
        for (n = 0; n <= 25; ++n) {
            Base64.lookUpBase64Alphabet[n] = (char)(65 + n);
        }
        n = 26;
        int n2 = 0;
        while (n <= 51) {
            Base64.lookUpBase64Alphabet[n] = (char)(97 + n2);
            ++n;
            ++n2;
        }
        n = 52;
        n2 = 0;
        while (n <= 61) {
            Base64.lookUpBase64Alphabet[n] = (char)(48 + n2);
            ++n;
            ++n2;
        }
        Base64.lookUpBase64Alphabet[62] = 43;
        Base64.lookUpBase64Alphabet[63] = 47;
    }
}

