/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;

public interface nsIDOMEntity
extends nsIDOMNode {
    public static final String NS_IDOMENTITY_IID = "{a6cf9079-15b3-11d2-932e-00805f8add32}";

    public String getPublicId();

    public String getSystemId();

    public String getNotationName();
}

