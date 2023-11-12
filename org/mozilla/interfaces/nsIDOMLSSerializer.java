/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDOMConfiguration;
import org.mozilla.interfaces.nsIDOMLSOutput;
import org.mozilla.interfaces.nsIDOMLSSerializerFilter;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMLSSerializer
extends nsISupports {
    public static final String NS_IDOMLSSERIALIZER_IID = "{96ea4792-d362-4c28-a8c2-0337790d648a}";

    public nsIDOMDOMConfiguration getDomConfig();

    public String getNewLine();

    public void setNewLine(String var1);

    public nsIDOMLSSerializerFilter getFilter();

    public void setFilter(nsIDOMLSSerializerFilter var1);

    public boolean write(nsIDOMNode var1, nsIDOMLSOutput var2);

    public boolean writeToURI(nsIDOMNode var1, String var2);

    public String writeToString(nsIDOMNode var1);
}

