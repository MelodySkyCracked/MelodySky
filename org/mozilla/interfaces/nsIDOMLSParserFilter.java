/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMLSParserFilter
extends nsISupports {
    public static final String NS_IDOMLSPARSERFILTER_IID = "{10e8893d-ddf5-45d1-8872-615d72065fb4}";
    public static final short FILTER_ACCEPT = 1;
    public static final short FILTER_REJECT = 2;
    public static final short FILTER_SKIP = 3;
    public static final short FILTER_INTERRUPT = 4;

    public int startElement(nsIDOMElement var1);

    public int acceptNode(nsIDOMNode var1);

    public long getWhatToShow();
}

