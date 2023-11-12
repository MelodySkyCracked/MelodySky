/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsILocale;
import org.mozilla.interfaces.nsISupports;

public interface nsICollation
extends nsISupports {
    public static final String NS_ICOLLATION_IID = "{b0132cc0-3786-4557-9874-910d7def5f93}";
    public static final int kCollationStrengthDefault = 0;
    public static final int kCollationCaseInsensitiveAscii = 1;
    public static final int kCollationAccentInsenstive = 2;
    public static final int kCollationCaseSensitive = 0;
    public static final int kCollationCaseInSensitive = 3;

    public void initialize(nsILocale var1);

    public int compareString(int var1, String var2, String var3);
}

