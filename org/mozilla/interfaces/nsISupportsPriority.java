/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISupportsPriority
extends nsISupports {
    public static final String NS_ISUPPORTSPRIORITY_IID = "{aa578b44-abd5-4c19-8b14-36d4de6fdc36}";
    public static final int PRIORITY_HIGHEST = -20;
    public static final int PRIORITY_HIGH = -10;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_LOW = 10;
    public static final int PRIORITY_LOWEST = 20;

    public int getPriority();

    public void setPriority(int var1);

    public void adjustPriority(int var1);
}

