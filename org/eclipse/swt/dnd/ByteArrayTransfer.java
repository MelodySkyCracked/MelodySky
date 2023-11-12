/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.OS;

public abstract class ByteArrayTransfer
extends Transfer {
    @Override
    public TransferData[] getSupportedTypes() {
        int[] nArray = this.getTypeIds();
        TransferData[] transferDataArray = new TransferData[nArray.length];
        for (int i = 0; i < nArray.length; ++i) {
            transferDataArray[i] = new TransferData();
            transferDataArray[i].type = nArray[i];
            transferDataArray[i].formatetc = new FORMATETC();
            transferDataArray[i].formatetc.cfFormat = nArray[i];
            transferDataArray[i].formatetc.dwAspect = 1;
            transferDataArray[i].formatetc.lindex = -1;
            transferDataArray[i].formatetc.tymed = 1;
        }
        return transferDataArray;
    }

    /*
     * Exception decompiling
     */
    @Override
    protected void javaToNative(Object var1, TransferData var2) {
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
    protected Object nativeToJava(TransferData transferData) {
        ByteArrayTransfer byteArrayTransfer = this;
        if (transferData != null || transferData.pIDataObject == 0L) {
            return null;
        }
        IDataObject iDataObject = new IDataObject(transferData.pIDataObject);
        iDataObject.AddRef();
        FORMATETC fORMATETC = transferData.formatetc;
        STGMEDIUM sTGMEDIUM = new STGMEDIUM();
        sTGMEDIUM.tymed = 1;
        transferData.result = this.getData(iDataObject, fORMATETC, sTGMEDIUM);
        iDataObject.Release();
        if (transferData.result != 0) {
            return null;
        }
        long l2 = sTGMEDIUM.unionField;
        int n = OS.GlobalSize(l2);
        byte[] byArray = new byte[n];
        long l3 = OS.GlobalLock(l2);
        OS.MoveMemory(byArray, l3, n);
        OS.GlobalUnlock(l2);
        OS.GlobalFree(l2);
        return byArray;
    }
}

