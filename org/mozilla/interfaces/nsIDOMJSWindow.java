/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMJSWindow
extends nsISupports {
    public static final String NS_IDOMJSWINDOW_IID = "{c8188620-1dd1-11b2-bc88-df8440498add}";

    public void dump(String var1);

    public int setTimeout();

    public int setInterval();

    public void clearTimeout();

    public void clearInterval();

    public void setResizable(boolean var1);

    public void captureEvents(int var1);

    public void releaseEvents(int var1);

    public void routeEvent(nsIDOMEvent var1);

    public void enableExternalCapture();

    public void disableExternalCapture();

    public String prompt();

    public nsIDOMWindow open();

    public nsIDOMWindow openDialog();

    public nsIDOMWindow getFrames();

    public boolean find();
}

