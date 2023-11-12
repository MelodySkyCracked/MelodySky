/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISHistory;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIWebNavigation
extends nsISupports {
    public static final String NS_IWEBNAVIGATION_IID = "{f5d9e7b0-d930-11d3-b057-00a024ffc08c}";
    public static final long LOAD_FLAGS_MASK = 65535L;
    public static final long LOAD_FLAGS_NONE = 0L;
    public static final long LOAD_FLAGS_IS_REFRESH = 16L;
    public static final long LOAD_FLAGS_IS_LINK = 32L;
    public static final long LOAD_FLAGS_BYPASS_HISTORY = 64L;
    public static final long LOAD_FLAGS_REPLACE_HISTORY = 128L;
    public static final long LOAD_FLAGS_BYPASS_CACHE = 256L;
    public static final long LOAD_FLAGS_BYPASS_PROXY = 512L;
    public static final long LOAD_FLAGS_CHARSET_CHANGE = 1024L;
    public static final long LOAD_FLAGS_STOP_CONTENT = 2048L;
    public static final long LOAD_FLAGS_FROM_EXTERNAL = 4096L;
    public static final long LOAD_FLAGS_ALLOW_THIRD_PARTY_FIXUP = 8192L;
    public static final long LOAD_FLAGS_FIRST_LOAD = 16384L;
    public static final long STOP_NETWORK = 1L;
    public static final long STOP_CONTENT = 2L;
    public static final long STOP_ALL = 3L;

    public boolean getCanGoBack();

    public boolean getCanGoForward();

    public void goBack();

    public void goForward();

    public void gotoIndex(int var1);

    public void loadURI(String var1, long var2, nsIURI var4, nsIInputStream var5, nsIInputStream var6);

    public void reload(long var1);

    public void stop(long var1);

    public nsIDOMDocument getDocument();

    public nsIURI getCurrentURI();

    public nsIURI getReferringURI();

    public nsISHistory getSessionHistory();

    public void setSessionHistory(nsISHistory var1);
}

