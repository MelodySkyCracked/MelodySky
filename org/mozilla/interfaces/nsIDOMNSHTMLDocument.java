/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMHTMLCollection;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSHTMLDocument
extends nsISupports {
    public static final String NS_IDOMNSHTMLDOCUMENT_IID = "{79beb289-3644-4b54-9432-9fb993945629}";

    public int getWidth();

    public int getHeight();

    public String getAlinkColor();

    public void setAlinkColor(String var1);

    public String getLinkColor();

    public void setLinkColor(String var1);

    public String getVlinkColor();

    public void setVlinkColor(String var1);

    public String getBgColor();

    public void setBgColor(String var1);

    public String getFgColor();

    public void setFgColor(String var1);

    public String getDomain();

    public void setDomain(String var1);

    public nsIDOMHTMLCollection getEmbeds();

    public String getSelection();

    public nsIDOMDocument open(String var1, boolean var2);

    public void write();

    public void writeln();

    public void clear();

    public void captureEvents(int var1);

    public void releaseEvents(int var1);

    public void routeEvent(nsIDOMEvent var1);

    public String getCompatMode();

    public nsIDOMHTMLCollection getPlugins();

    public String getDesignMode();

    public void setDesignMode(String var1);

    public boolean execCommand(String var1, boolean var2, String var3);

    public boolean execCommandShowHelp(String var1);

    public boolean queryCommandEnabled(String var1);

    public boolean queryCommandIndeterm(String var1);

    public boolean queryCommandState(String var1);

    public boolean queryCommandSupported(String var1);

    public String queryCommandText(String var1);

    public String queryCommandValue(String var1);
}

