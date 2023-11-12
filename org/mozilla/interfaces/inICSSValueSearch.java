/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.inISearchProcess;
import org.mozilla.interfaces.nsIDOMDocument;

public interface inICSSValueSearch
extends inISearchProcess {
    public static final String INICSSVALUESEARCH_IID = "{e0d39e48-1dd1-11b2-81bd-9a0c117f0736}";

    public nsIDOMDocument getDocument();

    public void setDocument(nsIDOMDocument var1);

    public String getBaseURL();

    public void setBaseURL(String var1);

    public boolean getReturnRelativeURLs();

    public void setReturnRelativeURLs(boolean var1);

    public boolean getNormalizeChromeURLs();

    public void setNormalizeChromeURLs(boolean var1);

    public void addPropertyCriteria(String var1);

    public String getTextCriteria();

    public void setTextCriteria(String var1);
}

