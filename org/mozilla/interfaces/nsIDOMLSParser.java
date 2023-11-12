/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDOMConfiguration;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMLSInput;
import org.mozilla.interfaces.nsIDOMLSParserFilter;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMLSParser
extends nsISupports {
    public static final String NS_IDOMLSPARSER_IID = "{2a31a3a0-be68-40af-9f64-914192f0fba2}";
    public static final int ACTION_APPEND_AS_CHILDREN = 1;
    public static final int ACTION_REPLACE_CHILDREN = 2;
    public static final int ACTION_INSERT_BEFORE = 3;
    public static final int ACTION_INSERT_AFTER = 4;
    public static final int ACTION_REPLACE = 5;

    public nsIDOMDOMConfiguration getDomConfig();

    public nsIDOMLSParserFilter getFilter();

    public void setFilter(nsIDOMLSParserFilter var1);

    public boolean getAsync();

    public boolean getBusy();

    public nsIDOMDocument parse(nsIDOMLSInput var1);

    public nsIDOMDocument parseURI(String var1);

    public nsIDOMNode parseWithContext(nsIDOMLSInput var1, nsIDOMNode var2, int var3);

    public void abort();
}

