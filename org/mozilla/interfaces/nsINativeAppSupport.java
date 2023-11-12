/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsINativeAppSupport
extends nsISupports {
    public static final String NS_INATIVEAPPSUPPORT_IID = "{5fdf8480-1f98-11d4-8077-00600811a9c3}";

    public boolean start();

    public void enable();

    public boolean stop();

    public void quit();

    public void onLastWindowClosing();

    public void reOpen();
}

