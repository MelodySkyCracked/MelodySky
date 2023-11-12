/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIScriptableDateFormat
extends nsISupports {
    public static final String NS_ISCRIPTABLEDATEFORMAT_IID = "{0c89efb0-1aae-11d3-9141-006008a6edf6}";
    public static final int dateFormatNone = 0;
    public static final int dateFormatLong = 1;
    public static final int dateFormatShort = 2;
    public static final int dateFormatYearMonth = 3;
    public static final int dateFormatWeekday = 4;
    public static final int timeFormatNone = 0;
    public static final int timeFormatSeconds = 1;
    public static final int timeFormatNoSeconds = 2;
    public static final int timeFormatSecondsForce24Hour = 3;
    public static final int timeFormatNoSecondsForce24Hour = 4;

    public String formatDateTime(String var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9);

    public String formatDate(String var1, int var2, int var3, int var4, int var5);

    public String formatTime(String var1, int var2, int var3, int var4, int var5);
}

