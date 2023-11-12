/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIPrintSettings;
import org.mozilla.interfaces.nsISupports;

public interface nsIPrinterEnumerator
extends nsISupports {
    public static final String NS_IPRINTERENUMERATOR_IID = "{a6cf9128-15b3-11d2-932e-00805f8add32}";

    public String getDefaultPrinterName();

    public void initPrintSettingsFromPrinter(String var1, nsIPrintSettings var2);

    public String[] enumeratePrinters(long[] var1);

    public void displayPropertiesDlg(String var1, nsIPrintSettings var2);
}

