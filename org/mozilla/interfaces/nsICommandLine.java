/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsICommandLine
extends nsISupports {
    public static final String NS_ICOMMANDLINE_IID = "{bc3173bd-aa46-46a0-9d25-d9867a9659b6}";
    public static final long STATE_INITIAL_LAUNCH = 0L;
    public static final long STATE_REMOTE_AUTO = 1L;
    public static final long STATE_REMOTE_EXPLICIT = 2L;

    public int getLength();

    public String getArgument(int var1);

    public int findFlag(String var1, boolean var2);

    public void removeArguments(int var1, int var2);

    public boolean handleFlag(String var1, boolean var2);

    public String handleFlagWithParam(String var1, boolean var2);

    public long getState();

    public boolean getPreventDefault();

    public void setPreventDefault(boolean var1);

    public nsIFile getWorkingDirectory();

    public nsIDOMWindow getWindowContext();

    public nsIFile resolveFile(String var1);

    public nsIURI resolveURI(String var1);
}

