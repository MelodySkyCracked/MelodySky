/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsISHEntry;
import org.mozilla.interfaces.nsISupports;

public interface nsIContentViewer
extends nsISupports {
    public static final String NS_ICONTENTVIEWER_IID = "{6a7ddb40-8a9e-4576-8ad1-71c5641d8780}";

    public nsISupports getContainer();

    public void setContainer(nsISupports var1);

    public void loadStart(nsISupports var1);

    public void loadComplete(long var1);

    public boolean permitUnload();

    public void pageHide(boolean var1);

    public void close(nsISHEntry var1);

    public void destroy();

    public void stop();

    public nsIDOMDocument getDOMDocument();

    public void setDOMDocument(nsIDOMDocument var1);

    public void move(int var1, int var2);

    public void show();

    public void hide();

    public boolean getEnableRendering();

    public void setEnableRendering(boolean var1);

    public boolean getSticky();

    public void setSticky(boolean var1);

    public boolean requestWindowClose();

    public void open(nsISupports var1);

    public void clearHistoryEntry();
}

