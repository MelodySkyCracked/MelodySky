/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIPrintSettings;
import org.mozilla.interfaces.nsISupports;

public interface nsIPrintSettingsService
extends nsISupports {
    public static final String NS_IPRINTSETTINGSSERVICE_IID = "{841387c8-72e6-484b-9296-bf6eea80d58a}";

    public nsIPrintSettings getGlobalPrintSettings();

    public nsIPrintSettings getNewPrintSettings();

    public String getDefaultPrinterName();

    public void initPrintSettingsFromPrinter(String var1, nsIPrintSettings var2);

    public void initPrintSettingsFromPrefs(nsIPrintSettings var1, boolean var2, long var3);

    public void savePrintSettingsToPrefs(nsIPrintSettings var1, boolean var2, long var3);
}

