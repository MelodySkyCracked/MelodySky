/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISAXXMLReader;

public interface nsISAXXMLFilter
extends nsISAXXMLReader {
    public static final String NS_ISAXXMLFILTER_IID = "{77a22cf0-6cdf-11da-be43-001422106990}";

    public nsISAXXMLReader getParent();

    public void setParent(nsISAXXMLReader var1);
}

