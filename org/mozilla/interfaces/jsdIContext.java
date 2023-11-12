/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.jsdIEphemeral;
import org.mozilla.interfaces.jsdIValue;
import org.mozilla.interfaces.nsISupports;

public interface jsdIContext
extends jsdIEphemeral {
    public static final String JSDICONTEXT_IID = "{a2dd25a4-1dd1-11b2-bda6-ed525acd4c35}";
    public static final int OPT_STRICT = 1;
    public static final int OPT_WERR = 2;
    public static final int OPT_VAROBJFIX = 4;
    public static final int OPT_ISUPPORTS = 8;

    public long getOptions();

    public void setOptions(long var1);

    public int getVersion();

    public void setVersion(int var1);

    public long getTag();

    public nsISupports getPrivateData();

    public nsISupports getWrappedContext();

    public jsdIValue getGlobalObject();

    public boolean getScriptsEnabled();

    public void setScriptsEnabled(boolean var1);
}

