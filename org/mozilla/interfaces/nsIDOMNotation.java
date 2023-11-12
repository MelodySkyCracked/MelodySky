/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;

public interface nsIDOMNotation
extends nsIDOMNode {
    public static final String NS_IDOMNOTATION_IID = "{a6cf907e-15b3-11d2-932e-00805f8add32}";

    public String getPublicId();

    public String getSystemId();
}

