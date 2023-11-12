/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIXSLTProcessorObsolete
extends nsISupports {
    public static final String NS_IXSLTPROCESSOROBSOLETE_IID = "{3fbff728-2d20-11d3-aef3-00108300ff91}";

    public void transformDocument(nsIDOMNode var1, nsIDOMNode var2, nsIDOMDocument var3, nsISupports var4);
}

