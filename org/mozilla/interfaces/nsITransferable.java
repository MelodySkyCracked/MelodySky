/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFormatConverter;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsISupportsArray;

public interface nsITransferable
extends nsISupports {
    public static final String NS_ITRANSFERABLE_IID = "{8b5314bc-db01-11d2-96ce-0060b0fb9956}";
    public static final int kFlavorHasDataProvider = 0;

    public nsISupportsArray flavorsTransferableCanExport();

    public void getTransferData(String var1, nsISupports[] var2, long[] var3);

    public void getAnyTransferData(String[] var1, nsISupports[] var2, long[] var3);

    public boolean isLargeDataSet();

    public nsISupportsArray flavorsTransferableCanImport();

    public void setTransferData(String var1, nsISupports var2, long var3);

    public void addDataFlavor(String var1);

    public void removeDataFlavor(String var1);

    public nsIFormatConverter getConverter();

    public void setConverter(nsIFormatConverter var1);
}

