/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNamedNodeMap;
import org.mozilla.interfaces.nsIDOMNode;

public interface nsIDOMDocumentType
extends nsIDOMNode {
    public static final String NS_IDOMDOCUMENTTYPE_IID = "{a6cf9077-15b3-11d2-932e-00805f8add32}";

    public String getName();

    public nsIDOMNamedNodeMap getEntities();

    public nsIDOMNamedNodeMap getNotations();

    public String getPublicId();

    public String getSystemId();

    public String getInternalSubset();
}

