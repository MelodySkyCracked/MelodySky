/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.LONG;
import org.eclipse.swt.internal.win32.GCP_RESULTS;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Control;

public class BidiUtil {
    public static final int KEYBOARD_NON_BIDI = 0;
    public static final int KEYBOARD_BIDI = 1;
    static int isBidiPlatform = -1;
    public static final int CLASSIN = 1;
    public static final int LINKBEFORE = 2;
    public static final int LINKAFTER = 4;
    static Map languageMap = new HashMap();
    static Map oldProcMap = new HashMap();
    static Callback callback = new Callback(BidiUtil.class, "windowProc", 4);
    static final int GCP_REORDER = 2;
    static final int GCP_GLYPHSHAPE = 16;
    static final int GCP_LIGATE = 32;
    static final int GCP_CLASSIN = 524288;
    static final byte GCPCLASS_ARABIC = 2;
    static final byte GCPCLASS_HEBREW = 2;
    static final byte GCPCLASS_LOCALNUMBER = 4;
    static final byte GCPCLASS_LATINNUMBER = 5;
    static final int GCPGLYPH_LINKBEFORE = 32768;
    static final int GCPGLYPH_LINKAFTER = 16384;
    static final int ETO_CLIPPED = 4;
    static final int ETO_GLYPH_INDEX = 16;
    static final int LANG_ARABIC = 1;
    static final int LANG_HEBREW = 13;
    static final int LANG_FARSI = 41;
    static final int HKL_NEXT = 1;
    static final int HKL_PREV = 0;
    public static final int CLASS_HEBREW = 2;
    public static final int CLASS_ARABIC = 2;
    public static final int CLASS_LOCALNUMBER = 4;
    public static final int CLASS_LATINNUMBER = 5;
    public static final int REORDER = 2;
    public static final int LIGATE = 32;
    public static final int GLYPHSHAPE = 16;

    public static void addLanguageListener(long l2, Runnable runnable) {
        languageMap.put(new LONG(l2), runnable);
        BidiUtil.subclass(l2);
    }

    public static void addLanguageListener(Control control, Runnable runnable) {
        BidiUtil.addLanguageListener(control.handle, runnable);
    }

    static long EnumSystemLanguageGroupsProc(long l2, long l3, long l4, long l5, long l6) {
        if ((int)l2 == 12) {
            isBidiPlatform = 1;
            return 0L;
        }
        if ((int)l2 == 13) {
            isBidiPlatform = 1;
            return 0L;
        }
        return 1L;
    }

    public static void drawGlyphs(GC gC, char[] cArray, int[] nArray, int n, int n2) {
        int n3;
        int n4 = nArray.length;
        if (OS.GetLayout(gC.handle) != 0) {
            BidiUtil.reverse(nArray);
            int n5 = n3 = n4 - 1;
            nArray[n5] = nArray[n5] - 1;
            BidiUtil.reverse(cArray);
        }
        n3 = OS.SetBkMode(gC.handle, 1);
        OS.ExtTextOut(gC.handle, n, n2, 16, null, cArray, cArray.length, nArray);
        OS.SetBkMode(gC.handle, n3);
    }

    public static char[] getRenderInfo(GC gC, String string, int[] nArray, byte[] byArray, int[] nArray2, int n, int[] nArray3) {
        long l2;
        long l3;
        long l4;
        long l5;
        int n2 = OS.GetFontLanguageInfo(gC.handle);
        long l6 = OS.GetProcessHeap();
        boolean bl = OS.GetLayout(gC.handle) != 0;
        char[] cArray = string.toCharArray();
        int n3 = cArray.length;
        boolean bl2 = (n & 2) == 2;
        boolean bl3 = (n & 4) == 4;
        GCP_RESULTS gCP_RESULTS = new GCP_RESULTS();
        gCP_RESULTS.lStructSize = GCP_RESULTS.sizeof;
        gCP_RESULTS.nGlyphs = n3;
        GCP_RESULTS gCP_RESULTS2 = gCP_RESULTS;
        gCP_RESULTS2.lpOrder = l5 = OS.HeapAlloc(l6, 8, n3 * 4);
        long l7 = l5;
        GCP_RESULTS gCP_RESULTS3 = gCP_RESULTS;
        gCP_RESULTS3.lpDx = l4 = OS.HeapAlloc(l6, 8, n3 * 4);
        long l8 = l4;
        GCP_RESULTS gCP_RESULTS4 = gCP_RESULTS;
        gCP_RESULTS4.lpClass = l3 = OS.HeapAlloc(l6, 8, n3);
        long l9 = l3;
        GCP_RESULTS gCP_RESULTS5 = gCP_RESULTS;
        gCP_RESULTS5.lpGlyphs = l2 = OS.HeapAlloc(l6, 8, n3 * 2);
        long l10 = l2;
        int n4 = 0;
        int n5 = 0;
        n4 |= 2;
        if ((n2 & 0x20) == 32) {
            n4 |= 0x20;
            n5 |= 0;
        }
        if ((n2 & 0x10) == 16) {
            n4 |= 0x10;
            if (bl2) {
                n5 |= 0x8000;
            }
            if (bl3) {
                n5 |= 0x4000;
            }
        }
        byte[] byArray2 = bl2 || bl3 ? new byte[]{(byte)n5, (byte)(n5 >> 8)} : new byte[]{(byte)n5};
        OS.MoveMemory(gCP_RESULTS.lpGlyphs, byArray2, byArray2.length);
        if ((n & 1) == 1) {
            n4 |= 0x80000;
            OS.MoveMemory(gCP_RESULTS.lpClass, byArray, byArray.length);
        }
        char[] cArray2 = new char[gCP_RESULTS.nGlyphs];
        int n6 = 0;
        for (int i = 0; i < nArray3.length - 1; ++i) {
            Object[] objectArray;
            int n7;
            int n8 = nArray3[i];
            gCP_RESULTS.nGlyphs = n7 = nArray3[i + 1] - nArray3[i];
            string.getChars(n8, n8 + gCP_RESULTS.nGlyphs, cArray, 0);
            OS.GetCharacterPlacement(gC.handle, cArray, n7, 0, gCP_RESULTS, n4);
            if (nArray2 != null) {
                objectArray = new int[gCP_RESULTS.nGlyphs];
                OS.MoveMemory(objectArray, gCP_RESULTS.lpDx, objectArray.length * 4);
                if (bl) {
                    BidiUtil.reverse(objectArray);
                }
                System.arraycopy(objectArray, 0, nArray2, n6, objectArray.length);
            }
            if (nArray != null) {
                objectArray = new int[n7];
                OS.MoveMemory(objectArray, gCP_RESULTS.lpOrder, objectArray.length * 4);
                BidiUtil.translateOrder(objectArray, n6, bl);
                System.arraycopy(objectArray, 0, nArray, n8, n7);
            }
            if (byArray != null) {
                objectArray = new byte[n7];
                OS.MoveMemory((byte[])objectArray, gCP_RESULTS.lpClass, objectArray.length);
                System.arraycopy(objectArray, 0, byArray, n8, n7);
            }
            objectArray = new char[gCP_RESULTS.nGlyphs];
            OS.MoveMemory((char[])objectArray, gCP_RESULTS.lpGlyphs, objectArray.length * 2);
            if (bl) {
                BidiUtil.reverse((char[])objectArray);
            }
            System.arraycopy(objectArray, 0, cArray2, n6, objectArray.length);
            n6 += objectArray.length;
            GCP_RESULTS gCP_RESULTS6 = gCP_RESULTS;
            gCP_RESULTS6.lpOrder += (long)(n7 * 4);
            GCP_RESULTS gCP_RESULTS7 = gCP_RESULTS;
            gCP_RESULTS7.lpDx += (long)(n7 * 4);
            GCP_RESULTS gCP_RESULTS8 = gCP_RESULTS;
            gCP_RESULTS8.lpClass += (long)n7;
            GCP_RESULTS gCP_RESULTS9 = gCP_RESULTS;
            gCP_RESULTS9.lpGlyphs += (long)(objectArray.length * 2);
        }
        OS.HeapFree(l6, 0, l10);
        OS.HeapFree(l6, 0, l9);
        OS.HeapFree(l6, 0, l8);
        OS.HeapFree(l6, 0, l7);
        return cArray2;
    }

    public static void getOrderInfo(GC gC, String string, int[] nArray, byte[] byArray, int n, int[] nArray2) {
        long l2;
        long l3;
        int n2 = OS.GetFontLanguageInfo(gC.handle);
        long l4 = OS.GetProcessHeap();
        char[] cArray = string.toCharArray();
        int n3 = cArray.length;
        boolean bl = OS.GetLayout(gC.handle) != 0;
        GCP_RESULTS gCP_RESULTS = new GCP_RESULTS();
        gCP_RESULTS.lStructSize = GCP_RESULTS.sizeof;
        gCP_RESULTS.nGlyphs = n3;
        GCP_RESULTS gCP_RESULTS2 = gCP_RESULTS;
        gCP_RESULTS2.lpOrder = l3 = OS.HeapAlloc(l4, 8, n3 * 4);
        long l5 = l3;
        GCP_RESULTS gCP_RESULTS3 = gCP_RESULTS;
        gCP_RESULTS3.lpClass = l2 = OS.HeapAlloc(l4, 8, n3);
        long l6 = l2;
        int n4 = 0;
        n4 |= 2;
        if ((n2 & 0x20) == 32) {
            n4 |= 0x20;
        }
        if ((n2 & 0x10) == 16) {
            n4 |= 0x10;
        }
        if ((n & 1) == 1) {
            n4 |= 0x80000;
            OS.MoveMemory(gCP_RESULTS.lpClass, byArray, byArray.length);
        }
        int n5 = 0;
        for (int i = 0; i < nArray2.length - 1; ++i) {
            Object object;
            int n6;
            int n7 = nArray2[i];
            gCP_RESULTS.nGlyphs = n6 = nArray2[i + 1] - nArray2[i];
            string.getChars(n7, n7 + gCP_RESULTS.nGlyphs, cArray, 0);
            OS.GetCharacterPlacement(gC.handle, cArray, n6, 0, gCP_RESULTS, n4);
            if (nArray != null) {
                object = new int[n6];
                OS.MoveMemory(object, gCP_RESULTS.lpOrder, ((int[])object).length * 4);
                BidiUtil.translateOrder(object, n5, bl);
                System.arraycopy(object, 0, nArray, n7, n6);
            }
            if (byArray != null) {
                object = new byte[n6];
                OS.MoveMemory((byte[])object, gCP_RESULTS.lpClass, ((int[])object).length);
                System.arraycopy(object, 0, byArray, n7, n6);
            }
            n5 += gCP_RESULTS.nGlyphs;
            object = gCP_RESULTS;
            object.lpOrder += (long)(n6 * 4);
            GCP_RESULTS gCP_RESULTS4 = gCP_RESULTS;
            gCP_RESULTS4.lpClass += (long)n6;
        }
        OS.HeapFree(l4, 0, l6);
        OS.HeapFree(l4, 0, l5);
    }

    public static int getFontBidiAttributes(GC gC) {
        int n = 0;
        int n2 = OS.GetFontLanguageInfo(gC.handle);
        if ((n2 & 2) != 0) {
            n |= 2;
        }
        if ((n2 & 0x20) != 0) {
            n |= 0x20;
        }
        if ((n2 & 0x10) != 0) {
            n |= 0x10;
        }
        return n;
    }

    /*
     * Exception decompiling
     */
    public static int getKeyboardLanguage() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl5 : IF_ICMPEQ - null : Stack underflow
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

    static long[] getKeyboardLanguageList() {
        int n = 10;
        long[] lArray = new long[10];
        int n2 = OS.GetKeyboardLayoutList(10, lArray);
        long[] lArray2 = new long[n2];
        System.arraycopy(lArray, 0, lArray2, 0, n2);
        return lArray2;
    }

    /*
     * Exception decompiling
     */
    public static boolean isBidiPlatform() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl13 : IF_ICMPGE - null : Stack underflow
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

    public static void removeLanguageListener(long l2) {
        languageMap.remove(new LONG(l2));
        BidiUtil.unsubclass(l2);
    }

    public static void removeLanguageListener(Control control) {
        BidiUtil.removeLanguageListener(control.handle);
    }

    public static int resolveTextDirection(String string) {
        if (string == null) {
            return 0;
        }
        int n = 0;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            byte by = Character.getDirectionality(c);
            int n2 = BidiUtil.getStrongDirection(by);
            if (n2 != 0) {
                n = n2;
            }
            if (n == 0x4000000) break;
        }
        return n;
    }

    static int getStrongDirection(byte by) {
        switch (by) {
            case 0: {
                return 0x2000000;
            }
            case 1: 
            case 2: {
                return 0x4000000;
            }
        }
        return 0;
    }

    public static void setKeyboardLanguage(int n) {
        if (n == BidiUtil.getKeyboardLanguage()) {
            return;
        }
        boolean bl = n == 1;
        for (long l2 : BidiUtil.getKeyboardLanguageList()) {
            if (bl == l2) continue;
            OS.ActivateKeyboardLayout(l2, 0);
            return;
        }
    }

    public static boolean setOrientation(long l2, int n) {
        int n2 = OS.GetWindowLong(l2, -20);
        n2 = (n & 0x4000000) != 0 ? (n2 |= 0x400000) : (n2 &= 0xFFBFFFFF);
        OS.SetWindowLong(l2, -20, n2);
        return true;
    }

    public static boolean setOrientation(Control control, int n) {
        return BidiUtil.setOrientation(control.handle, n);
    }

    static void subclass(long l2) {
        LONG lONG = new LONG(l2);
        if (oldProcMap.get(lONG) == null) {
            long l3 = OS.GetWindowLongPtr(l2, -4);
            oldProcMap.put(lONG, new LONG(l3));
            OS.SetWindowLongPtr(l2, -4, callback.getAddress());
        }
    }

    static void reverse(char[] cArray) {
        int n = cArray.length;
        for (int i = 0; i <= (n - 1) / 2; ++i) {
            char c = cArray[i];
            cArray[i] = cArray[n - 1 - i];
            cArray[n - 1 - i] = c;
        }
    }

    static void reverse(int[] nArray) {
        int n = nArray.length;
        for (int i = 0; i <= (n - 1) / 2; ++i) {
            int n2 = nArray[i];
            nArray[i] = nArray[n - 1 - i];
            nArray[n - 1 - i] = n2;
        }
    }

    static void translateOrder(int[] nArray, int n, boolean bl) {
        int n2;
        int n3 = 0;
        int n4 = nArray.length;
        if (bl) {
            for (n2 = 0; n2 < n4; ++n2) {
                n3 = Math.max(n3, nArray[n2]);
            }
        }
        n2 = 0;
        while (n2 < n4) {
            int n5;
            if (bl) {
                nArray[n2] = n3 - nArray[n2];
            }
            int n6 = n5 = n2++;
            nArray[n6] = nArray[n6] + n;
        }
    }

    static void unsubclass(long l2) {
        LONG lONG = new LONG(l2);
        if (languageMap.get(lONG) == null) {
            LONG lONG2 = (LONG)oldProcMap.remove(lONG);
            if (lONG2 == null) {
                return;
            }
            OS.SetWindowLongPtr(l2, -4, lONG2.value);
        }
    }

    static long windowProc(long l2, long l3, long l4, long l5) {
        Object object;
        LONG lONG = new LONG(l2);
        switch ((int)l3) {
            case 81: {
                object = (Runnable)languageMap.get(lONG);
                if (object == null) break;
                object.run();
                break;
            }
        }
        object = (LONG)oldProcMap.get(lONG);
        return OS.CallWindowProc(((LONG)object).value, l2, (int)l3, l4, l5);
    }
}

