/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMLSException
extends nsISupports {
    public static final String NS_IDOMLSEXCEPTION_IID = "{1cc8e4b3-1dbb-4adc-a913-1527bf67748c}";
    public static final int PARSE_ERR = 81;
    public static final int SERIALIZE_ERR = 82;

    public int getCode();
}

