/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIPrintSettings;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIPrintOptions
extends nsISupports {
    public static final String NS_IPRINTOPTIONS_IID = "{d9948a4d-f49c-4456-938a-acda2c8d7741}";
    public static final short kNativeDataPrintRecord = 0;

    public void showPrintSetupDialog(nsIPrintSettings var1);

    public nsIPrintSettings createPrintSettings();

    public nsISimpleEnumerator availablePrinters();

    public int getPrinterPrefInt(nsIPrintSettings var1, String var2);

    public void displayJobProperties(String var1, nsIPrintSettings var2, boolean[] var3);

    public void setFontNamePointSize(String var1, int var2);
}

