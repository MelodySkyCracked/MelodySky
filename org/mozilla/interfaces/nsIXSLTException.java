/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIException;

public interface nsIXSLTException
extends nsIException {
    public static final String NS_IXSLTEXCEPTION_IID = "{e06dfaea-92d5-47f7-a800-c5f5404d8771}";

    public nsIDOMNode getStyleNode();

    public nsIDOMNode getSourceNode();
}

