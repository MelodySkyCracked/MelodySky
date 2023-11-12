/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIUpdatePatch
extends nsISupports {
    public static final String NS_IUPDATEPATCH_IID = "{56863a67-bd69-42de-9f40-583e625b457d}";

    public String getType();

    public void setType(String var1);

    public String getURL();

    public void setURL(String var1);

    public String getHashFunction();

    public void setHashFunction(String var1);

    public String getHashValue();

    public void setHashValue(String var1);

    public long getSize();

    public void setSize(long var1);

    public String getState();

    public void setState(String var1);

    public boolean getSelected();

    public void setSelected(boolean var1);

    public nsIDOMElement serialize(nsIDOMDocument var1);
}

