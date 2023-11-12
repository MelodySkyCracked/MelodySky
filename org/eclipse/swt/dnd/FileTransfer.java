/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.CIDA;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;

public class FileTransfer
extends ByteArrayTransfer {
    private static FileTransfer _instance = new FileTransfer();
    private static final String CF_HDROP = "CF_HDROP";
    private static final int CF_HDROPID = 15;
    private static final String CFSTR_SHELLIDLIST = "Shell IDList Array";
    private static final int CFSTR_SHELLIDLISTID = Transfer.registerType("Shell IDList Array");

    private FileTransfer() {
    }

    public static FileTransfer getInstance() {
        return _instance;
    }

    /*
     * Exception decompiling
     */
    @Override
    public void javaToNative(Object var1, TransferData var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl7 : SIPUSH - null : trying to set 1 previously set to 2
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
    public Object nativeToJava(TransferData transferData) {
        FileTransfer fileTransfer = this;
        if (transferData == null || transferData.pIDataObject == 0L) {
            return null;
        }
        IDataObject iDataObject = new IDataObject(transferData.pIDataObject);
        iDataObject.AddRef();
        FORMATETC fORMATETC = new FORMATETC();
        fORMATETC.cfFormat = 15;
        fORMATETC.ptd = 0L;
        fORMATETC.dwAspect = 1;
        fORMATETC.lindex = -1;
        fORMATETC.tymed = 1;
        STGMEDIUM sTGMEDIUM = new STGMEDIUM();
        sTGMEDIUM.tymed = 1;
        transferData.result = this.getData(iDataObject, fORMATETC, sTGMEDIUM);
        iDataObject.Release();
        if (transferData.result != 0) {
            return null;
        }
        int n = OS.DragQueryFile(sTGMEDIUM.unionField, -1, null, 0);
        String[] stringArray = new String[n];
        for (int i = 0; i < n; ++i) {
            int n2 = OS.DragQueryFile(sTGMEDIUM.unionField, i, null, 0);
            char[] cArray = new char[n2 + 1];
            OS.DragQueryFile(sTGMEDIUM.unionField, i, cArray, n2 + 1);
            stringArray[i] = new String(cArray, 0, n2);
        }
        OS.DragFinish(sTGMEDIUM.unionField);
        return stringArray;
    }

    private long generateCidaFromFilepaths(String[] stringArray) {
        int n;
        int n2 = stringArray.length;
        long[] lArray = new long[n2];
        CIDA cIDA = new CIDA();
        cIDA.cidl = n2;
        cIDA.aoffset = n = CIDA.sizeof + 4 * n2;
        int[] nArray = new int[n2];
        int[] nArray2 = new int[n2];
        int n3 = 2;
        for (int i = 0; i < n2; ++i) {
            TCHAR tCHAR = new TCHAR(0, stringArray[i], true);
            long[] lArray2 = new long[]{0L};
            int n4 = COM.PathToPIDL(tCHAR.chars, lArray2);
            if (n4 != 0) {
                long l2 = 0L;
                for (int j = 0; j < n2; ++j) {
                    if (lArray[j] == 0L) continue;
                    OS.CoTaskMemFree(lArray[j]);
                }
                return l2;
            }
            lArray[i] = lArray2[0];
            nArray2[i] = OS.ILGetSize(lArray[i]);
            n3 += nArray2[i];
            if (i == 0) {
                nArray[0] = n + 2;
                continue;
            }
            nArray[i] = nArray[i - 1] + nArray2[i - 1];
        }
        long l3 = OS.GlobalAlloc(64, n + n3);
        if (l3 != 0L) {
            OS.MoveMemory(l3, cIDA, CIDA.sizeof);
            OS.MoveMemory(l3 + (long)CIDA.sizeof, nArray, 4 * cIDA.cidl);
            for (int i = 0; i < n2; ++i) {
                OS.MoveMemory(l3 + (long)nArray[i], lArray[i], nArray2[i]);
            }
        }
        long l4 = l3;
        for (int i = 0; i < n2; ++i) {
            if (lArray[i] == 0L) continue;
            OS.CoTaskMemFree(lArray[i]);
        }
        return l4;
    }

    @Override
    protected int[] getTypeIds() {
        return new int[]{15, CFSTR_SHELLIDLISTID};
    }

    @Override
    protected String[] getTypeNames() {
        return new String[]{CF_HDROP, CFSTR_SHELLIDLIST};
    }

    @Override
    protected boolean validate(Object object) {
        return this.checkFile(object);
    }
}

