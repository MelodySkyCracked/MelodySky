/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMXPathException
extends nsISupports {
    public static final String NS_IDOMXPATHEXCEPTION_IID = "{75506f89-b504-11d5-a7f2-ca108ab8b6fc}";
    public static final int INVALID_EXPRESSION_ERR = 51;
    public static final int TYPE_ERR = 52;

    public int getCode();
}

